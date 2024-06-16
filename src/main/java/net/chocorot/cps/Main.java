package net.chocorot.cps;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    private static Main plugin;
    private File settingsFile;
    private YamlConfiguration settings;

    @Override
    public void onEnable() {
        plugin = this;

        this.getServer().getPluginManager().registerEvents(new CPSLimiterListener(), this);

        this.getCommand("settings").setExecutor(new SettingCommand());

        // YAML file
        configSetup();
    }

    private void configSetup() {
        // Create a new file instance for your config
        settingsFile = new File(getDataFolder(), "settings.yml");

        // Check if the file exists, if not, create it
        if (!settingsFile.exists()) {
            settingsFile.getParentFile().mkdirs();
            saveResource("settings.yml", false);
        }

        // Load the config
        settings = loadConfig(settingsFile);

        // You can now access the config using the 'config' variable
        // For example, config.getString("some_key");

        // Pass the config to another class or use a getter method
        new Settings().initialize(settings);
    }

    private YamlConfiguration loadConfig(File configFile) {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource(configFile.getName(), false);
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig(YamlConfiguration config, File configFile) {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        saveConfig(settings, settingsFile);
    }
}
