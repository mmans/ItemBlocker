package de.telemans.minecraft.itemblocker;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemBlockerHandler implements Listener {
    private JavaPlugin plugin;

    ItemBlockerHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent evt) {
        int itemid = evt.getItem().getEntityId();
        Material item = evt.getItem().getItemStack().getType();
        String playername = evt.getPlayer().getDisplayName();

        if (item == Material.SAND) {

            plugin.getLogger().info("Player " + playername + " Picked up a " + item + " (" + itemid + ")");

            if (!evt.getPlayer().isOp()) {
                evt.getPlayer().sendMessage("§c Sorry " + item + " items are not allowed!");
                evt.getItem().remove();
                evt.setCancelled(true);
            }
        }    }
}

