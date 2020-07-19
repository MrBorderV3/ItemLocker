package me.border.itemlocker;

import java.util.ArrayList;
import java.util.List;
import me.border.itemlocker.command.ItemLock;
import me.border.itemlocker.listener.AnvilListener;
import me.border.itemlocker.listener.EnchantmentTableListener;
import me.border.itemlocker.util.Utils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemLocker extends JavaPlugin {
    public static List<String> identifiers = new ArrayList<>();

    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        loadIdentifiers();
        new Utils(this);
        new ItemLock(this);
        getServer().getPluginManager().registerEvents((Listener)new AnvilListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new EnchantmentTableListener(this), (Plugin)this);
    }

    private void loadIdentifiers() {
        identifiers.addAll(getConfig().getStringList("Identifiers"));
    }
}
