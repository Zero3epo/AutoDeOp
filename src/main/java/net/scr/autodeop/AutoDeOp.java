package net.scr.autodeop;

import org.bukkit.plugin.java.JavaPlugin;

public final class AutoDeOp extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
        getCommand("ado").setExecutor(new command(this));
        System.out.println("[AutoDeOp] has been enable");
    }

    @Override
    public void onDisable() {
        System.out.println("[AutoDeOp] has been disable");
    }
}
