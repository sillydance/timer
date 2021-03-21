package org.norules.racetimer;

import org.bukkit.plugin.java.*;
import java.util.logging.*;
import org.bukkit.event.*;
import java.text.*;

import org.norules.racetimer.commands.RecordsCommand;
import org.norules.racetimer.commands.StartTimerCommand;
import org.norules.racetimer.commands.StopTimerCommand;
import org.norules.racetimer.util.*;
import org.bukkit.plugin.*;
import java.io.*;
import java.util.*;

public class RaceTimer extends JavaPlugin
{
    Logger log;
    public Hashtable<String, Long> times;
    DecimalFormat df1;
    public ListStore records;
    public int announceRecordLimit;

    public RaceTimer() {
        this.times = new Hashtable<>();
        this.df1 = new DecimalFormat("#.0");
        this.announceRecordLimit = 10;
    }

    public void onEnable() {
        this.log = this.getLogger();
        final String pluginFolder = this.getDataFolder().getAbsolutePath();
        new File(pluginFolder).mkdirs();
        (this.records = new ListStore(new File(pluginFolder + File.separator + "times.txt"))).load();

        getCommand("starttimer").setExecutor(new StartTimerCommand(this));
        getCommand("stoptimer").setExecutor(new StopTimerCommand(this));
        getCommand("records").setExecutor(new RecordsCommand(this));
    }

    public void onDisable() {
        this.records.save();
    }

    public String convertToTime(final long ms) {
        int secs = (int)(ms / 1000L);
        int mins = secs / 60;
        int hours = mins / 60;
        secs %= 60;
        mins %= 60;
        hours %= 24;
        String secsS = Integer.toString(secs);
        String minsS = Integer.toString(mins);
        String hoursS = Integer.toString(hours);
        if (secs < 10) {
            secsS = "0" + secsS;
        }
        if (mins < 10) {
            minsS = "0" + minsS;
        }
        if (hours < 10) {
            hoursS = "0" + hoursS;
        }
        return hoursS + ":" + minsS + ":" + secsS;
    }

}
