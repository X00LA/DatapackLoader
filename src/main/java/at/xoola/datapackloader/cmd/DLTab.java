package at.xoola.datapackloader.cmd;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class DLTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command,
            @Nonnull String label, @Nonnull String[] args) {
        List<String> options = new ArrayList<>();
        if (!sender.hasPermission("datapackloader.command")) {
            return options;
        }

        if (args.length == 1) {
            if (sender.hasPermission("datapackloader.command.help")) {
                options.add("help");
            }
            if (sender.hasPermission("datapackloader.command.import") && !(sender instanceof Player)) {
                options.add("import");
            }
            if (sender.hasPermission("datapackloader.command.reload")) {
                options.add("reload");
            }
        }

        return options;
    }
}