package org.norules.racetimer.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.norules.racetimer.RaceTimer;

import java.util.Map;

public class StopTimerCommand implements CommandExecutor {

    private RaceTimer plugin;

    public StopTimerCommand(RaceTimer plugin) {
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
                    final long totalTime = System.currentTimeMillis() - this.plugin.times.get(target.getName());
                    if (this.plugin.records.records.containsKey(target.getName())) {
                        if (totalTime < this.plugin.records.getTime(target.getName())) {
                            target.sendMessage(ChatColor.DARK_GREEN + "You beat your old time of " + ChatColor.GRAY + this.plugin.convertToTime(this.plugin.records.getTime(target.getName())));
                            target.sendMessage(ChatColor.GREEN + "Your new time is " + ChatColor.GRAY + this.plugin.convertToTime(totalTime));
                            this.plugin.records.add(target.getName(), totalTime);

                            int curPlace = 0;
                            for (final Map.Entry<String, Long> entry : this.plugin.records.records.entrySet()) {
                                if (totalTime > entry.getValue()) {
                                    ++curPlace;
                                }
                                if (curPlace > this.plugin.announceRecordLimit) {
                                    break;
                                }
                            }

                            if (curPlace < this.plugin.announceRecordLimit) {
                                this.plugin.getServer().broadcastMessage(target.getName() + " is now is now #" + (curPlace + 1) + " on the best times list!");
                            }
                        }
                        else {
                            target.sendMessage(ChatColor.GREEN + "Your finishing time was " + ChatColor.GRAY + this.plugin.convertToTime(totalTime));
                        }
                    }
                    else {
                        target.sendMessage(ChatColor.GREEN + "Your finishing time was " + ChatColor.GRAY + this.plugin.convertToTime(totalTime));
                        this.plugin.records.add(target.getName(), totalTime);
                    }
                    this.plugin.records.save();
                    this.plugin.times.remove(target.getName());
                    sender.sendMessage("§eStopped timer for " + target.getName() + ".");
                }
                else {
                    target.sendMessage(ChatColor.RED + "You have not started a timer!");
                }
            } else {
                sender.sendMessage("§cUnknown player '" + args[0] + "'.");
            }
        } else {
            sender.sendMessage("§cUsage: /stoptimerlavaportal <player>");
        }

        return true;
    }

}
