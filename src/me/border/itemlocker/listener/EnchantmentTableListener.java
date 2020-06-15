package me.border.itemlocker.listener;

import me.border.itemlocker.ItemLocker;
import me.border.itemlocker.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        if (p.hasPermission("itemlocker.bypass")) return;
        final UUID uuid = p.getUniqueId();

        if (!item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return;

        List<String> lore = meta.getLore();
        for (String loreLine : lore) {
            if (loreLine.contains(Utils.chat("&cCurse of Locking"))) {
                if (!cancelDoubleSending.contains(uuid)) {
                    p.sendMessage(Utils.ucs("Locked"));
                    cancelDoubleSending.add(uuid);
                    plugin.getServer().getScheduler().runTaskLater(plugin, () -> cancelDoubleSending.remove(uuid), 2L);
                }
                e.setCancelled(true);
                return;
            }
        }
    }
}
