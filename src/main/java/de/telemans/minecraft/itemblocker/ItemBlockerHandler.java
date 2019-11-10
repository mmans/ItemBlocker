package de.telemans.minecraft.itemblocker;

import com.sun.tools.javac.jvm.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
import java.util.Set;

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
        boolean shouldCancel = handleEvent(evt, evt.getPlayer(), evt.getItem().getItemStack());

        if (shouldCancel){
            evt.getItem().remove();
            evt.setCancelled(true);
        }
//        if (config.isBlocked(item)) {
//            ItemBlockerItemConfig itemConfig = config.getBlockedItemConfig(item);
//
//            if (itemConfig.getMaxAmount() > 0) {
//                // Check if player has less than allowed amount
//                int currentItemAmount = getCurrentInventryAmount(evt.getPlayer(), item);
//                if (currentItemAmount < itemConfig.getMaxAmount()) {
//                    int maxAddition = itemConfig.getMaxAmount() - currentItemAmount;
//                    if (maxAddition < evt.getItem().getItemStack().getAmount()) {
//                        evt.getPlayer().sendMessage(ChatColor.ITALIC.toString() + "Only adding " + maxAddition + " more " + item + "-block(s)");
//                    }
//                    ItemStack addItemStack = new ItemStack(item, maxAddition);
//                    evt.getPlayer().getInventory().addItem(addItemStack);
//                    evt.setCancelled(true);
//                    evt.getItem().remove();
//                    return;
//                }
//            }
//            evt.getPlayer().sendMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + itemConfig.getBlockText());
//            evt.getItem().remove();
//            evt.setCancelled(true);
//        }
    }

    private int getCurrentInventryAmount(HumanEntity player, Material mat) {
        int currentAmount = 0;
        for (ItemStack is : player.getInventory()) {
            if (is != null && is.getType() == mat) {
                currentAmount += is.getAmount();
            }
        }
        return currentAmount;
    }

    @EventHandler
    public void onInventoryDrag(InventoryClickEvent evt) {
        Material placedItem = evt.getCursor().getType();
        boolean shouldCancel = handleEvent(evt, evt.getWhoClicked(), evt.getCursor());

        if (shouldCancel){
            evt.setCancelled(true);
        }
    }

    private boolean handleEvent(Event evt, HumanEntity player, ItemStack item){
        if (config.isBlocked(item.getType())) {
            ItemBlockerItemConfig itemConfig = config.getBlockedItemConfig(item.getType());

            if (itemConfig.getMaxAmount() > 0) {
                // Check if player has less than allowed amount
                int currentItemAmount = getCurrentInventryAmount(player, item.getType());
                if (currentItemAmount < itemConfig.getMaxAmount()) {
                    int maxAddition = itemConfig.getMaxAmount() - currentItemAmount;
                    int willAddAmount = item.getAmount();
                    if (maxAddition < item.getAmount()) {
                        player.sendMessage(ChatColor.ITALIC.toString() + "Only adding " + maxAddition + " more " + item + "-block(s)");
                        willAddAmount = maxAddition;
                    }
                    ItemStack addItemStack = new ItemStack(item.getType(), willAddAmount);
                    player.getInventory().addItem(addItemStack);
                    return true; // Notify caller that event needs to be cancelled
                }
            }
            player.sendMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + itemConfig.getBlockText());
            return true;
        }
        return false;
    }
}

