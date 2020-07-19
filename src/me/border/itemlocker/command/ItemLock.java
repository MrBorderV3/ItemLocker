package me.border.itemlocker.command;

import java.util.ArrayList;
import java.util.List;
import me.border.itemlocker.ItemLocker;
import me.border.itemlocker.util.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemLock implements CommandExecutor {
    private ItemLocker plugin;

    public ItemLock(ItemLocker plugin) {
        this.plugin = plugin;
        plugin.getCommand("itemlock").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.ucs("notAPlayer"));
            return true;
        }
        Player p = (Player)sender;
        if (p.hasPermission("itemlocker.lock")) {
            if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {
                p.sendMessage(Utils.ucs("Lock.itemNull"));
                return true;
            }
            ItemStack item = p.getInventory().getItemInMainHand();
            ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
            if (meta.hasLore()) {
                List<String> list = meta.getLore();
                for (String loreLine : list) {
                    if (loreLine.contains(Utils.chat("&cCurse of Locking"))) {
                        p.sendMessage(Utils.ucs("Lock.alreadyLocked"));
                        return true;
                    }
                }
                list.add(Utils.chat("&cCurse of Locking"));
                meta.setLore(list);
                item.setItemMeta(meta);
                p.updateInventory();
                p.sendMessage(Utils.ucs("Lock.success"));
                return true;
            }
            List<String> lore = new ArrayList<>();
            lore.add(Utils.chat("&cCurse of Locking"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            p.updateInventory();
            p.sendMessage(Utils.ucs("Lock.success"));
            return true;
        }
        p.sendMessage(Utils.ucs("noPermission"));
        return false;
    }
}
