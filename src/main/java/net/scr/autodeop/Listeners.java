package net.scr.autodeop;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Listeners implements Listener {

    private final AutoDeOp plugin;

    public Listeners(AutoDeOp plugin) {
        this.plugin = plugin;
        startOpCheckTask();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            checkPlayerOp(e.getPlayer());
        }, 10L);
    }

    private void startOpCheckTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    checkPlayerOp(p);
                }
            }
        }.runTaskTimer(plugin, 0L, 20L * 2);
    }

    private void checkPlayerOp(Player p) {
        List<String> whitelist = plugin.getConfig().getStringList("whitelist");

        boolean isWhitelisted = whitelist.contains(p.getName()) ||
                whitelist.contains(p.getUniqueId().toString()) ||
                whitelist.contains(p.getName().toLowerCase());

        if (p.isOp() && !isWhitelisted) {
            p.setOp(false);

            String language = plugin.getConfig().getString("language", "ru");
            String banReason = language.equals("en") ? "&cServer hacking attempt" : "&cПопытка взлома сервера";

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "banip " + p.getName() + " " + banReason);
        }

        if(p.hasPermission("op")&& !isWhitelisted) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " parent set " + plugin.getConfig().getString("default_group"));

            String language = plugin.getConfig().getString("language", "ru");
            String banReason = language.equals("en") ? "&cServer hacking attempt" : "&cПопытка взлома сервера";

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "banip " + p.getName() + " " + banReason);
        }
    }
}
