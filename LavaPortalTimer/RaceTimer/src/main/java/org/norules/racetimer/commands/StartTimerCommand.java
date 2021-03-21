package org.norules.racetimer.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.norules.racetimer.RaceTimer;

public class StartTimerCommand implements CommandExecutor {

    private RaceTimer plugin;

    public StartTimerCommand(RaceTimer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            sender.sendMessage("§cYou can not use this command.");
            return true;
        }

        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                if (this.plugin.times.containsKey(target.getName())) {
                    target.sendMessage(ChatColor.GREEN + "Timer restarted.");
                }
                else {
                    target.sendMessage(ChatColor.GREEN + "Timer started.");
                }
                this.plugin.times.put(target.getName(), System.currentTimeMillis());

                sender.sendMessage("§aStarted timer for " + target.getName() + ".");
            } else {
                sender.sendMessage("§cUnknown player '" + args[0] + "'.");
            }
        } else {
            sender.sendMessage("§cUsage: /starttimerlavaportal <player>");
        }


        return true;
    }

}
