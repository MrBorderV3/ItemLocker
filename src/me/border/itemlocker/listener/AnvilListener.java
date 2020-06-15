package me.border.itemlocker.listener;

import me.border.itemlocker.ItemLocker;
import me.border.itemlocker.util.Utils;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AnvilListener implements Listener {
    private ItemLocker plugin;

    public AnvilListener(ItemLocker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        HumanEntity human = e.getWhoClicked();
        if (!(human instanceof Player)) return;

        Player p = (Player) human;
        Inventory inventory = e.getClickedInventory();
        if (!(inventory instanceof AnvilInventory)) return;
        if (p.hasPermission("itemlocker.bypass")) return;

        InventoryView inventoryView = e.getView();
        int slot = e.getRawSlot();
        if (slot != inventoryView.convertSlot(slot)) return;
        if (slot != 2) return;

        ItemStack item = inventory.getItem(0);
        ItemStack mergedWith = inventory.getItem(1);
        ItemStack result = inventory.getItem(2);
        boolean isRepair = false;

        if (item == null) return;
        if (result == null) return;

        if (!isRepair) {
            if (mergedWith != null) {
                if (mergedWith.hasItemMeta()) {
                    if (mergedWith.getItemMeta().hasLore()) {
                        List<String> lore = mergedWith.getItemMeta().getLore();
                        for (String loreLine : lore) {
                            if (loreLine.contains(Utils.chat("&cCurse of Locking"))) {
                                p.sendMessage(Utils.ucs("Locked"));
                                e.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
            }
            if (!item.hasItemMeta()) return;
            if (!item.getItemMeta().hasLore()) return;
            List<String> lore = item.getItemMeta().getLore();
            for (String loreLine : lore) {
                if (loreLine.contains(Utils.chat("&cCurse of Locking"))) {
                    p.sendMessage(Utils.ucs("Locked"));
                    e.setCancelled(true);
                    return;
                }
            }

        }
    }
}
