package org.norules.racetimer.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.norules.racetimer.RaceTimer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class RecordsCommand implements CommandExecutor {

    private RaceTimer plugin;

    public RecordsCommand(RaceTimer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;

        final int recordSize = plugin.records.getSize();
        int listSize = 10;
        if (recordSize < 10) {
            listSize = recordSize;
        }
        final ArrayList<String> players = new ArrayList<String>();
        final ArrayList<Long> theTimes = new ArrayList<Long>();
        for (final Map.Entry<String, Long> entry : plugin.records.records.entrySet()) {
            theTimes.add(entry.getValue());
        }
        Collections.sort(theTimes);
        for (int i = 0; i < listSize; ++i) {
            for (final Map.Entry<String, Long> entry2 : plugin.records.records.entrySet()) {
                if (entry2.getValue().equals(theTimes.get(i))) {
                    players.add(entry2.getKey());
                }
            }
        }

        sender.sendMessage(ChatColor.GOLD + "----- Lava Portal Records -----");
        for (int i = 0; i < listSize; ++i) {
            sender.sendMessage(ChatColor.YELLOW + " " + (i + 1) + ". " + players.get(i) + ChatColor.GRAY + " - " + plugin.convertToTime(theTimes.get(i)));
        }

        return true;
    }

}
