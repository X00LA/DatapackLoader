package at.xoola.datapackloader.cmd;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.xoola.datapackloader.Main;
import at.xoola.datapackloader.dp.Importer;
import at.xoola.datapackloader.util.GenUtil;
import at.xoola.datapackloader.util.LanguageManager;
import at.xoola.datapackloader.util.Messager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DLCmd implements CommandExecutor {

    private static CompletableFuture<Void> commandFuture = CompletableFuture.completedFuture(null);
    private final GenUtil genUtil;
    private final String datapacksFolderPath;
    private final Logger logger;
    private final Main main;
    private final Messager messager;
    private final LanguageManager languageManager;
    private final String separator;

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,
            @Nonnull String[] args) {
        if (genUtil.checkDisallowed(sender, "datapackloader.command")) {
            return true;
        }

        if (args.length == 0) {
            commandFuture = commandFuture
                    .thenAcceptAsync(processed -> messager.sendInvalidMessage(sender, "datapackloader"));
            return true;
        }

        if (args[0].equals("help")) {
            if (genUtil.checkDisallowed(sender, "datapackloader.command.help")) {
                return true;
            }

            commandFuture = commandFuture
                    .thenAcceptAsync(processed -> messager.sendHelpMessage(sender));
            return true;
        } else if (args[0].equals("import")) {
            if (sender instanceof Player) {
                commandFuture = commandFuture
                        .thenAcceptAsync(processed -> messager.sendOnlyConsoleMessage(sender));
                return true;
            }

            if (args.length != 2) {
                commandFuture = commandFuture
                        .thenAcceptAsync(processed -> messager.sendInvalidMessage(sender, "datapackloader"));
                return true;
            }

            if (!args[1].endsWith(".zip")) {
                commandFuture = commandFuture
                        .thenAcceptAsync(processed -> messager.sendZipMessage(sender));
                return false;
            }

            commandFuture = commandFuture
                    .thenAcceptAsync(processed -> {
                        try {
                            URL url = new URI(args[1]).toURL();
                            new Importer(logger, main, separator).importUrl(datapacksFolderPath, url);
                            logger.info(languageManager.getMessage("plugin.import-success"));
                        } catch (IOException e) {
                            throw new RuntimeException("IOException: Failed to import datapack.", e);
                        } catch (URISyntaxException e) {
                            throw new RuntimeException("URISyntaxException: Invalid URL.", e);
                        }
                    })
                    .exceptionally(e -> {
                        logger.severe(e.getMessage());
                        return null;
                    });

            return true;
        } else if (args[0].equals("reload")) {
            if (genUtil.checkDisallowed(sender, "datapackloader.command.reload")) {
                return true;
            }

            commandFuture = commandFuture
                    .thenAcceptAsync(processed -> {
                        try {
                            main.reloadPlugin();
                            messager.sendReloadSuccess(sender);
                        } catch (Exception e) {
                            messager.sendReloadError(sender);
                            logger.severe("Error during reload: " + e.getMessage());
                        }
                    });
            return true;
        }

        commandFuture = commandFuture
                .thenAcceptAsync(processed -> messager.sendInvalidMessage(sender, "datapackloader"));
        return true;
    }
}
