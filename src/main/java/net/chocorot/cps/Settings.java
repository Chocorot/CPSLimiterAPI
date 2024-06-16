package net.chocorot.cps;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Settings {

    private static YamlConfiguration settings;

    public void initialize(YamlConfiguration pluginConfig) {
        settings = pluginConfig;
    }

    public static void set(String key, String value) {
        settings.set(key, value);
    }

    public static String getString(String key) {
        return settings.getString(key);
    }

    public static void saveConfig() {
        try {
            settings.save(new File(Main.getPlugin().getDataFolder(), "settings.yml"));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean contains(String key) {
        return settings.contains(key);
    }
}
