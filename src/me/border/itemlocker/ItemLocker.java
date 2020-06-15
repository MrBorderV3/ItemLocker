package me.border.itemlocker;

import me.border.itemlocker.command.ItemLock;
import me.border.itemlocker.listener.AnvilListener;
import me.border.itemlocker.listener.EnchantmentTableListener;
import me.border.itemlocker.util.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemLocker extends JavaPlugin {

    @Override
    public void onEnable(){
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        new Utils(this);
        new ItemLock(this);
        getServer().getPluginManager().registerEvents(new AnvilListener(this), this);
        getServer().getPluginManager().registerEvents(new EnchantmentTableListener(this), this);
    }
}
