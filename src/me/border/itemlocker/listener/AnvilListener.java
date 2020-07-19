package me.border.itemlocker.listener;

import java.util.List;
import me.border.itemlocker.ItemLocker;
import me.border.itemlocker.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilListener implements Listener {
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getView().getTopInventory() instanceof org.bukkit.inventory.AnvilInventory) {
            if (!(e.getWhoClicked() instanceof Player))
                return;
            Player p = (Player)e.getWhoClicked();
            if (p.hasPermission("itemlocker.bypass"))
                return;
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTopInventory() instanceof org.bukkit.inventory.AnvilInventory) {
            if (!(e.getWhoClicked() instanceof Player))
                return;
            Player p = (Player)e.getWhoClicked();
            if (p.hasPermission("itemlocker.bypass"))
                return;
            ItemStack currentItem = e.getCurrentItem();
            if (currentItem.hasItemMeta()) {
                ItemMeta currentMeta = currentItem.getItemMeta();
                if (currentMeta.hasLore()) {
                    List<String> list = currentMeta.getLore();
                    for (String loreLine : list) {
                        for (String identifier : getIdentifiers()) {
                            if (ChatColor.stripColor(loreLine).equalsIgnoreCase(identifier)) {
                                p.sendMessage(Utils.ucs("Locked"));
                                e.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
            }
            ItemStack cursorItem = e.getCursor();
            if (!cursorItem.hasItemMeta())
                return;
            ItemMeta cursorMeta = cursorItem.getItemMeta();
            if (!cursorMeta.hasLore())
                return;
            List<String> lore = cursorMeta.getLore();
            for (String loreLine : lore) {
                for (String identifier : getIdentifiers()) {
                    if (ChatColor.stripColor(loreLine).equalsIgnoreCase(identifier)) {
                        p.sendMessage(Utils.ucs("Locked"));
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    private List<String> getIdentifiers() {
        return ItemLocker.identifiers;
    }
}