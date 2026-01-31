package at.xoola.datapackloader;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import at.xoola.datapackloader.cmd.DLCmd;
import at.xoola.datapackloader.cmd.DLTab;
import at.xoola.datapackloader.dp.Applier;
import at.xoola.datapackloader.dp.Checker;
import at.xoola.datapackloader.dp.Finder;
import at.xoola.datapackloader.dp.Importer;
import at.xoola.datapackloader.util.GenUtil;
import at.xoola.datapackloader.util.LanguageManager;
import at.xoola.datapackloader.util.LevelChanger;
import at.xoola.datapackloader.util.Messager;
import at.xoola.datapackloader.util.VersionGetter;
import at.xoola.datapackloader.util.WorldsDeleter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Main extends JavaPlugin {


    private static final String separator = FileSystems.getDefault().getSeparator();
    private final GenUtil genUtil = new GenUtil();
    private LanguageManager languageManager;
    private Messager messager;
    private CompletableFuture<Void> mainFuture = CompletableFuture.completedFuture(null);
    private boolean noConfig;
    private PluginCommand dlCommand;

    @Override
    public void onEnable() {
        noConfig = !new File(getDataFolder(), "config.yml").exists();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        
        languageManager = new LanguageManager(this, getLogger());
        messager = new Messager(getLogger(), this, languageManager);
        Configuration config = getConfig();
        if (config.getBoolean("disable-plugin")) {
            getLogger().info(languageManager.getMessage("plugin.disabled"));
            return;
        }

        // Update check (only if enabled)
        if (config.getBoolean("update-check.enabled", false)) {
            new VersionGetter(this, genUtil).getVersion(version -> {
                if (version != null && !this.getDescription().getVersion().equals(version)) {
                    getLogger().info(languageManager.getMessage("plugin.update-available"));
                    getLogger().info("Current: " + this.getDescription().getVersion() + " | Latest: " + version);
                }
            });
        }

        String datapacksFolderPath = getDataFolder().getPath() + separator + "datapacks";
        File datapacksFolder = new File(datapacksFolderPath);
        if (!datapacksFolder.mkdirs() && !datapacksFolder.exists()) {
            throw new RuntimeException("Failed to create 'datapacks' folder!");
        }

        Finder datapackFinder = new Finder(getLogger(), this, separator);
        File[] files = datapacksFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.endsWith(".zip")) {
                    try {
                        datapackFinder.fileWalk(datapacksFolderPath, file, true);
                    } catch (IOException e) {
                        throw new RuntimeException("IOException: Failed to walk .zip file!", e);
                    }
                } else {
                    if (Checker.isDatapack(file.getPath())) {
                        continue;
                    }

                    try {
                        datapackFinder.fileWalk(datapacksFolderPath, file, false);
                    } catch (IOException e) {
                        throw new RuntimeException("IOException: Failed to walk file!", e);
                    }
                }
            }
        }

        Importer importer = new Importer(getLogger(), this, separator);
        mainFuture = mainFuture
                .thenAcceptAsync(declared -> {
                    File[] datapacksFolderFiles = new File(datapacksFolderPath).listFiles();
                    if (datapacksFolderFiles == null || datapacksFolderFiles.length != 0) {
                        return;
                    }

                    if (config.getBoolean("starter-datapack")) {
                        try {
                            importer.importUrl(datapacksFolderPath,
                                    new URI("https://github.com/misode/mcmeta/archive/refs/tags/"
                                            + getServer().getVersion().split("MC: ")[1].split("[)]")[0] + "-data.zip")
                                            .toURL());
                        } catch (IOException e) {
                            throw new RuntimeException("IOException: Failed to import starter datapack!", e);
                        } catch (URISyntaxException e) {
                            throw new RuntimeException(
                                    "URISyntaxException: Failed to convert starter datapack string to URL!", e);
                        }
                    } else {
                        getLogger().info(languageManager.getMessage("plugin.datapacks-folder-empty", 
                            "{path}", datapacksFolderPath, 
                            "{command}", "datapackloader"));
                    }
                });

        mainFuture = mainFuture
                .thenAcceptAsync(imported -> {
                    if (noConfig) {
                        return;
                    }

                    Properties properties = new Properties();
                    try {
                        properties.load(Files.newInputStream(Paths.get("server.properties")));
                    } catch (IOException e) {
                        throw new RuntimeException("IOException: Failed to load 'server.properties'!", e);
                    }

                    boolean importEvent;
                    String levelName = properties.getProperty("level-name");
                    String worldDatapacksPath = getServer().getWorldContainer() + separator + levelName + separator
                            + "datapacks";
                    Applier datapackApplier = new Applier(separator);
                    try {
                        importEvent = datapackApplier.applyDatapacks(datapacksFolder, worldDatapacksPath);
                    } catch (IOException e) {
                        throw new RuntimeException("IOException: Failed to apply datapacks!", e);
                    }

                    if (config.getBoolean("developer-mode") && !config.getBoolean("dev-mode-applied")) {
                        try {
                            new WorldsDeleter().deleteOldWorlds(
                                    Objects.requireNonNull(this.getServer().getWorldContainer().listFiles()),
                                    levelName);
                        } catch (IOException e) {
                            throw new RuntimeException("IOException: Failed to delete old worlds!", e);
                        }

                        try {
                            new LevelChanger(getLogger(), languageManager).changeLevelName();
                        } catch (IOException e) {
                            throw new RuntimeException(
                                    "IOException: Failed to change 'level-name' in 'server.properties'!", e);
                        }

                        config.set("dev-mode-applied", true);
                    } else {
                        config.set("dev-mode-applied", false);
                    }

                    saveConfig();
                    if (importEvent) {
                        getLogger().info(languageManager.getMessage("plugin.stopping-server"));
                        getServer().shutdown();
                    }
                });

        dlCommand = getCommand("datapackloader");
        mainFuture = mainFuture
                .thenAcceptAsync(applied -> {
                    dlCommand.setExecutor(new DLCmd(genUtil, datapacksFolderPath, getLogger(), this, messager, languageManager, separator));
                    dlCommand.setTabCompleter(new DLTab());
                });
    }

    public void reloadPlugin() {
        try {
            reloadConfig();
            languageManager.reload();
            messager = new Messager(getLogger(), this, languageManager);
            getLogger().info("Plugin reloaded successfully.");
        } catch (Exception e) {
            getLogger().severe("Error reloading plugin: " + e.getMessage());
            throw e;
        }
    }
}