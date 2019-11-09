package de.telemans.minecraft.itemblocker;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class ItemBlockerHandler implements Listener {
    private ItemBlockerPlugin plugin;
    private ItemBlockerConfig config;

    ItemBlockerHandler(ItemBlockerPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getItemBlockerConfig();
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent evt) {

        if (config.isDisabledForOps()) {
            return;
        }

        Material item = evt.getItem().getItemStack().getType();

        if (config.isBlocked(item)) {
            ItemBlockerItemConfig itemConfig = config.getBlockedItemConfig(item);

            if (itemConfig.getMaxAmount() > 0) {
                // Check if player has less than allowed amount
                int currentItemAmount = getCurrentInventryAmount(evt.getPlayer(), item);
                if (currentItemAmount < itemConfig.getMaxAmount()) {
                    int maxAddition = itemConfig.getMaxAmount() - currentItemAmount;
                    if (maxAddition < evt.getItem().getItemStack().getAmount()){
                        evt.getPlayer().sendMessage(ChatColor.ITALIC.toString() + "Only adding " + maxAddition + " more " + item + "-block(s)");
                    }
                    ItemStack addItemStack = new ItemStack(item, maxAddition);
                    evt.getPlayer().getInventory().addItem(addItemStack);
                    evt.setCancelled(true);
                    evt.getItem().remove();
                    return;
                }
            }
            evt.getPlayer().sendMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + itemConfig.getBlockText());
            evt.getItem().remove();
            evt.setCancelled(true);
        }
    }

    private int getCurrentInventryAmount(Player player, Material mat) {
        int currentAmount = 0;
        for (ItemStack is : player.getInventory()) {
            if (is != null && is.getType() == mat) {
                currentAmount += is.getAmount();
            }
        }
        return currentAmount;
    }

    @EventHandler
    public void onInventoryChange(InventoryCreativeEvent evt) {
        Map<Material, Integer> uniqueInventory = new HashMap<>();

        plugin.getLogger().info(("Cursor = " + evt.getCursor()));
        // TODO: Only react on cursor!!!

        for (ItemStack is : evt.getInventory()){
            if (is != null){
                if (!uniqueInventory.containsKey(is.getType())){
                    uniqueInventory.put(is.getType(), is.getAmount());
                } else {
                    uniqueInventory.put(is.getType(), uniqueInventory.get(is.getType())+ is.getAmount());
                }
            }
        }

        for (Map.Entry<Material, Integer> a : uniqueInventory.entrySet()){
            plugin.getLogger().info(a.getKey() + " --> " + a.getValue());
        }

    }
}

