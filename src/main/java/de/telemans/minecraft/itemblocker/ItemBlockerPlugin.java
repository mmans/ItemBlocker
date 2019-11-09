package de.telemans.minecraft.itemblocker;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class ItemBlockerPlugin extends JavaPlugin {
    private ItemBlockerHandler handler;
    private ItemBlockerConfig config;

    @Override
    public void onEnable() {
        super.onEnable();
        // Load config
        saveDefaultConfig();
        config = new ItemBlockerConfig(getConfig(), this);

        // Register Commands
        getCommand("ibshow").setExecutor(new ItemBlockerCommandExecuter());

        // Register Handler
        handler =  new ItemBlockerHandler(this);
        getServer().getPluginManager().registerEvents(handler, this);
    }

    public ItemBlockerConfig getItemBlockerConfig(){
        return config;
    }
}
