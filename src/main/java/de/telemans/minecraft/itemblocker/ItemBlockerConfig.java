package de.telemans.minecraft.itemblocker;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class ItemBlockerConfig {

    private JavaPlugin plugin;
    private String defaultBlockText = "Change Me!";
    private Boolean disablePluginForOps = false;
    private Map<Material, ItemBlockerItemConfig> blockedItems = new HashMap<>();

    ItemBlockerConfig(ConfigurationSection config, JavaPlugin plugin) {
        this.plugin = plugin;

        // PARSE GLOBAL CONFIG
        defaultBlockText = config.getString("default_blocktext", "Sorry, these items are disabled on this server :(");
        disablePluginForOps = config.getBoolean("disable_plugin_for_ops", false);

        // PARSE BLOCKED ITEMS
        Object items = config.get("items");

        if (items instanceof MemorySection) {
            MemorySection itemlist = (MemorySection) items;
            for (String item : itemlist.getKeys(false)) {
                Material mat = Material.matchMaterial(item);
                if (mat != null) {
                    addMaterialConfig(mat, itemlist.getConfigurationSection(item));
                } else {
                    plugin.getLogger().severe("Material " + item + " not found!");
                }
            }
        } else {
            plugin.getLogger().severe("items section not well formatted!");
        }
    }

    private void addMaterialConfig(Material mat, ConfigurationSection config) {
        ItemBlockerItemConfig ic = new ItemBlockerItemConfig();
        ic.setBlockText(config.getString("block_text", defaultBlockText));
        ic.setMaxAmount(config.getInt("max_amount", 0));
        blockedItems.put(mat, ic);
    }

    public boolean isBlocked(Material mat) {
        return blockedItems.containsKey(mat);
    }

    public boolean isDisabledForOps() {
        return disablePluginForOps;
    }

    public ItemBlockerItemConfig getBlockedItemConfig(Material mat) {
        return blockedItems.get(mat);
    }
}
