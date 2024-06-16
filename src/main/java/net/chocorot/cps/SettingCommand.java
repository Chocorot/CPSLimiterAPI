package net.chocorot.cps;

import net.chocorot.cps.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SettingCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String setting = strings[0];
        String value;

        if (strings.length == 1)
            return false;

        if (isNumeric(strings[1])) {
            value = strings[1];
        }else {
            commandSender.sendMessage(ChatColor.RED + "Syntax Error");
            return false;
        }

        if (Objects.equals(setting, "time")){
            Settings.set("time", value);
            commandSender.sendMessage(ChatColor.GREEN + "Time: " + value);
        } else if (Objects.equals(setting, "count")) {
            Settings.set("count", value);
            commandSender.sendMessage(ChatColor.GREEN + "Count: " + value);
        }else {
            commandSender.sendMessage(ChatColor.RED + "Syntax Error");
        }
        Settings.saveConfig();
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            List<String> option = new ArrayList<>();
            String[] opt = {"time", "count"};
            Collections.addAll(option, opt);
            return option;
        }
        return null;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
