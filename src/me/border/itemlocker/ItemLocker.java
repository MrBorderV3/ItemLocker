package me.border.itemlocker;

import me.border.itemlocker.command.ItemLock;
import me.border.itemlocker.listener.AnvilListener;
import me.border.itemlocker.listener.EnchantmentTableListener;
import me.border.itemlocker.util.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ItemLocker extends JavaPlugin {

    public static List<String> identifiers = new ArrayList<>();

    @Override
    public void onEnable(){
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        loadIdentifiers();
        new Utils(this);
        new ItemLock(this);
        getServer().getPluginManager().registerEvents(new AnvilListener(), this);
        getServer().getPluginManager().registerEvents(new EnchantmentTableListener(this), this);
    }

    private void loadIdentifiers(){
        identifiers.addAll(getConfig().getStringList("Identifiers"));
    }
}
