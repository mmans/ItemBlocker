package de.telemans.minecraft.itemblocker;

import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBlockerPlugin extends JavaPlugin {
    private final ItemBlockerHandler handler = new ItemBlockerHandler(this);
    private final Map<Material, ItemBlockerConfig> blockedItems = new HashMap<>();

    @Override
    public void onEnable() {
        super.onEnable();
        getLogger().info("▄▄▄▄▄▄▄▄ .▄▄▌  ▄▄▄ .• ▌ ▄ ·.  ▄▄▄·  ▐ ▄ .▄▄ ·");
        getLogger().info("•██  ▀▄.▀·██•  ▀▄.▀··██ ▐███▪▐█ ▀█ •█▌▐█▐█ ▀.");
        getLogger().info("▐█.▪▐▀▀▪▄██▪  ▐▀▀▪▄▐█ ▌▐▌▐█·▄█▀▀█ ▐█▐▐▌▄▀▀▀█▄");
        getLogger().info("▐█▌·▐█▄▄▌▐█▌▐▌▐█▄▄▌██ ██▌▐█▌▐█ ▪▐▌██▐█▌▐█▄▪▐█");
        getLogger().info("▀▀▀  ▀▀▀ .▀▀▀  ▀▀▀ ▀▀  █▪▀▀▀ ▀  ▀ ▀▀ █▪ ▀▀▀▀");
        getLogger().info("Enabled ItemBlockerPlugin! Welcome my friend!");

        // Load config
        saveDefaultConfig();
        loadConfig();

        // Register Commands
        getCommand("ibshow").setExecutor(new ItemBlockerCommandExecuter());

        // Register Handler
        getServer().getPluginManager().registerEvents(handler, this);
    }

    private void loadConfig() {
        Object items = getConfig().get("items");

        if (items instanceof MemorySection){
            MemorySection itemlist = (MemorySection) items;
            for (String item : itemlist.getKeys(false)){
                Material mat = Material.matchMaterial(item);
                if (mat != null){

                } else {
                    getLogger().severe("Material " + item + " not found!");
                }
            }
        } else{
            getLogger().severe("items section not well formatted!");
        }

        getLogger().info(items.getClass().getCanonicalName());

//        for (Map<String, Object> item : items) {
//            if (item.containsKey("blockid"))
//
//
//            for (String a : item.keySet()) {
//
//                getLogger().info(a  + " -> " + item.get(a));
//            }
//        }
    }
}
