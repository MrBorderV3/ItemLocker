package me.border.itemlocker.listener;

import me.border.itemlocker.ItemLocker;
import me.border.itemlocker.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AnvilListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;
        Player p = (Player) e.getWhoClicked();
        Inventory inventory = e.getClickedInventory();
        if (!(inventory instanceof AnvilInventory))
            return;
        if (p.hasPermission("itemlocker.bypass"))
            return;
        InventoryView inventoryView = e.getView();
        int slot = e.getRawSlot();
        if (slot != inventoryView.convertSlot(slot)) return;
        if (slot != 2) return;

        ItemStack item = inventory.getItem(0);
        ItemStack mergedWith = inventory.getItem(1);
        ItemStack result = inventory.getItem(2);
        if (item == null)
            return;
        if (result == null)
            return;

        if (mergedWith != null) {
            if (mergedWith.hasItemMeta()) {
                ItemMeta meta = mergedWith.getItemMeta();
                if (meta.hasLore()) {
                    List<String> lore = meta.getLore();
                    for (String loreLine : lore) {
                        for (String identifier : getIdentifiers()) {
                            if (ChatColor.stripColor(loreLine).contains(identifier)) {
                                p.sendMessage(Utils.ucs("Locked"));
                                e.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
            }
        }
        if (!item.hasItemMeta())
            return;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore())
            return;
        List<String> lore = meta.getLore();
        for (String loreLine : lore) {
            for (String identifier : getIdentifiers()) {
                if (ChatColor.stripColor(loreLine).contains(identifier)) {
                    p.sendMessage(Utils.ucs("Locked"));
                    e.setCancelled(true);
                    return;
                }
            }
        }

    }

    private List<String> getIdentifiers(){
        return ItemLocker.identifiers;
    }
}
