package me.border.itemlocker.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.border.itemlocker.ItemLocker;
import me.border.itemlocker.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class EnchantmentTableListener implements Listener {
    private ItemLocker plugin;

    private List<UUID> cancelDoubleSending = new ArrayList<>();

    public EnchantmentTableListener(ItemLocker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnchant(PrepareItemEnchantEvent e) {
        ItemStack item = e.getItem();
        Player p = e.getEnchanter();
        if (p.hasPermission("itemlocker.bypass"))
            return;
        UUID uuid = p.getUniqueId();
        if (!item.hasItemMeta())
            return;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore())
            return;
        List<String> lore = meta.getLore();
        for (String loreLine : lore) {
            for (String identifier : getIdentifiers()) {
                if (ChatColor.stripColor(loreLine).equalsIgnoreCase(identifier)) {
                    if (!this.cancelDoubleSending.contains(uuid)) {
                        p.sendMessage(Utils.ucs("Locked"));
                        this.cancelDoubleSending.add(uuid);
                        this.plugin.getServer().getScheduler().runTaskLater((Plugin)this.plugin, () -> this.cancelDoubleSending.remove(uuid), 2L);
                    }
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    private List<String> getIdentifiers() {
        return ItemLocker.identifiers;
    }
}
