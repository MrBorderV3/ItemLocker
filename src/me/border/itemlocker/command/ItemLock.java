package me.border.itemlocker.command;

import me.border.itemlocker.ItemLocker;
import me.border.itemlocker.util.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemLock implements CommandExecutor {
    private ItemLocker plugin;

    public ItemLock(ItemLocker plugin){
        this.plugin = plugin;
        plugin.getCommand("itemlock").setExecutor(this);
    }

    public boolean onCommand(final CommandSender sender,final Command cmd,final String label,final String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage(Utils.ucs("notAPlayer"));
            return true;
        }
        Player p = (Player) sender;

        if (p.hasPermission("itemlocker.lock")){
            if (p.getInventory().getItemInMainHand().getType() == Material.AIR){
                p.sendMessage(Utils.ucs("Lock.itemNull"));
                return true;
            }
            if (p.getInventory().getItemInMainHand().getItemMeta() == null){
                p.sendMessage(Utils.ucs("Lock.itemNull"));
                return true;
            }
            ItemStack item = p.getInventory().getItemInMainHand();
            ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
            if (meta.hasLore()){
                List<String> lore = meta.getLore();
                for (String loreLine : lore){
                    if (loreLine.contains(Utils.chat("&cCurse of Locking"))) {
                        p.sendMessage(Utils.ucs("Lock.alreadyLocked"));
                        return true;
                    }
                }
                lore.add(Utils.chat("&cCurse of Locking"));
                meta.setLore(lore);
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
        } else {
            p.sendMessage(Utils.ucs("noPermission"));
        }

        return false;
    }
}
