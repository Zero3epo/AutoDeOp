package net.scr.autodeop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class command implements CommandExecutor {

    private final AutoDeOp plugin;

    public command(AutoDeOp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // Получаем язык из конфига
        String language = plugin.getConfig().getString("language", "ru");

        plugin.reloadConfig();

        List<String> list = plugin.getConfig().getStringList("whitelist");

        if (!sender.isOp()) {
            String noPermission = language.equals("en") ? "You don't have permission to use this command!" : "У вас нет прав для использования этой команды!";
            sender.sendMessage(ChatColor.RED + noPermission);
            return true;
        }

        if(sender instanceof Player) {
            Player p = (Player) sender;

            if (args == null || args.length == 0) {
                String usage = language.equals("en") ? "Usage: /ado help" : "Использование: /ado help";
                p.sendMessage(ChatColor.RED + usage);
                return true;
            }

            if(Objects.equals(args[0], "add")) {
                if(args.length < 2) {
                    String usage = language.equals("en") ? "Usage: /ado add <nickname>" : "Использование: /ado add <ник>";
                    p.sendMessage(ChatColor.RED + usage);
                    return true;
                }
                String playerName = args[1];
                if(playerName != null && !playerName.isEmpty()) {
                    list.add(playerName);
                    plugin.getConfig().set("whitelist", list);
                    plugin.saveConfig();
                    String message = language.equals("en") ? "Player " + playerName + " added to whitelist" : "Игрок " + playerName + " добавлен в вайтлист";
                    p.sendMessage(ChatColor.GREEN + message);
                }
                return true;
            }else if(Objects.equals(args[0], "remove")) {
                if(args.length < 2) {
                    String usage = language.equals("en") ? "Usage: /ado remove <nickname>" : "Использование: /ado remove <ник>";
                    p.sendMessage(ChatColor.RED + usage);
                    return true;
                }
                String playerName = args[1];
                if(playerName != null && !playerName.isEmpty()) {
                    list.remove(playerName);
                    plugin.getConfig().set("whitelist", list);
                    plugin.saveConfig();
                    String message = language.equals("en") ? "Player " + playerName + " removed from whitelist" : "Игрок " + playerName + " удален из вайтлиста";
                    p.sendMessage(ChatColor.RED + message);
                }
                return true;
            }else if(Objects.equals(args[0], "help")) {
                if (language.equals("en")) {
                    p.sendMessage(ChatColor.GOLD + "AutoDeOp Commands:");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&7&0&4&F&Fa&x&F&2&0&8&F&Fd&x&E&D&0&C&F&Fo &x&E&2&1&5&F&F{&x&D&D&1&9&F&Fa&x&D&8&1&D&F&Fd&x&D&3&2&1&F&Fd&x&C&E&2&5&F&F/&x&C&9&2&9&F&Fr&x&C&3&2&E&F&Fe&x&B&E&3&2&F&Fm&x&B&9&3&6&F&Fo&x&B&4&3&A&F&Fv&x&A&F&3&E&F&Fe&x&A&A&4&2&F&F} &x&9&F&4&B&F&Fn&x&9&A&4&F&F&Fi&x&9&5&5&3&F&Fc&x&8&B&5&B&F&Fk &x&8&1&6&3&F&F- &x&7&B&6&8&F&Fa&x&7&6&6&C&F&Fd&x&7&1&7&0&F&Fd&x&6&C&7&4&F&F/&x&6&7&7&8&F&Fr&x&6&2&7&C&F&Fe&x&5&D&8&0&F&Fm&x&5&7&8&5&F&Fo&x&5&2&8&9&F&Fv&x&4&D&8&D&F&Fe&x&4&8&9&1&F&F &x&4&3&9&5&F&Fp&x&3&E&9&9&F&Fl&x&3&3&A&2&F&Fa&x&2&E&A&6&F&Fy&x&2&9&A&A&F&Fe&x&2&4&A&E&F&Fr &x&1&F&B&2&F&Ft&x&1&A&B&6&F&Fo &x&0&F&B&F&F&Fw&x&0&A&C&3&F&Fh&x&0&5&C&7&F&Fi&x&0&0&C&B&F&Ft&x&0&0&C&B&F&Fe&x&0&0&C&B&F&Fl&x&0&0&C&B&F&Fi&x&0&0&C&B&F&Fs&x&0&0&C&B&F&Ft"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&6&0&5&F&Fa&x&F&0&0&A&F&Fd&x&E&A&0&F&F&Fo &x&D&E&1&8&F&Fl&x&D&8&1&D&F&Fi&x&D&2&2&2&F&Fs&x&C&C&2&7&F&Ft &x&C&0&3&0&F&F- &x&B&4&3&A&F&Fs&x&A&E&3&F&F&Fh&x&A&8&4&4&F&Fo&x&A&2&4&9&F&Fw&x&9&C&4&D&F&F &x&9&6&5&2&F&Fp&x&9&0&5&7&F&Fl&x&8&A&5&C&F&Fa&x&8&4&6&1&F&Fy&x&7&E&6&6&F&Fe&x&7&8&6&A&F&Fr&x&6&C&7&4&F&Fs &x&6&6&7&9&F&Fi&x&6&0&7&E&F&Fn &x&5&A&8&3&F&Fw&x&5&4&8&7&F&Fh&x&4&E&8&C&F&Fi&x&4&8&9&1&F&Ft&x&3&C&9&B&F&Fe&x&3&0&A&4&F&Fl&x&2&A&A&9&F&Fi&x&2&4&A&E&F&Fs&x&1&E&B&3&F&Ft"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&5&0&6&F&Fa&x&E&D&0&C&F&Fd&x&E&6&1&2&F&Fo &x&D&7&1&E&F&Fh&x&D&0&2&4&F&Fe&x&C&8&2&A&F&Fl&x&C&1&3&0&F&Fp &x&B&2&3&C&F&F- &x&A&3&4&8&F&Fs&x&9&C&4&E&F&Fh&x&9&4&5&4&F&Fo&x&8&D&5&A&F&Fw&x&8&5&6&0&F&F &x&7&E&6&6&F&Ft&x&7&7&6&B&F&Fh&x&6&F&7&1&F&Fi&x&6&8&7&7&F&Fs &x&6&0&7&D&F&Fh&x&5&9&8&3&F&Fe&x&4&A&8&F&F&Fl&x&4&3&9&5&F&Fp&x&3&B&9&B&F&F &x&3&4&A&1&F&Fm&x&2&5&A&D&F&Fe&x&1&E&B&3&F&Fs&x&1&6&B&9&F&Fs&x&0&F&B&F&F&Fa&x&0&7&C&5&F&Fg&x&0&0&C&B&F&Fe"));
                } else {
                    p.sendMessage(ChatColor.GOLD + "Команды AutoDeOp:");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&7&0&4&F&Fa&x&F&2&0&8&F&Fd&x&E&D&0&C&F&Fo &x&E&2&1&5&F&F{&x&D&D&1&9&F&Fa&x&D&8&1&D&F&Fd&x&D&3&2&1&F&Fd&x&C&E&2&5&F&F/&x&C&9&2&9&F&Fr&x&C&3&2&E&F&Fe&x&B&E&3&2&F&Fm&x&B&9&3&6&F&Fo&x&B&4&3&A&F&Fv&x&A&F&3&E&F&Fe&x&A&A&4&2&F&F} &x&9&F&4&B&F&Fн&x&9&A&4&F&F&Fи&x&9&5&5&3&F&Fк &x&8&B&5&B&F&F- &x&8&1&6&3&F&Fр&x&7&B&6&8&F&Fе&x&7&6&6&C&F&Fд&x&7&1&7&0&F&Fа&x&6&C&7&4&F&Fк&x&6&7&7&8&F&Fт&x&6&2&7&C&F&Fи&x&5&D&8&0&F&Fр&x&5&7&8&5&F&Fо&x&5&2&8&9&F&Fв&x&4&D&8&D&F&Fа&x&4&8&9&1&F&Fн&x&4&3&9&5&F&Fи&x&3&E&9&9&F&Fе &x&3&3&A&2&F&Fс&x&2&E&A&6&F&Fп&x&2&9&A&A&F&Fи&x&2&4&A&E&F&Fс&x&1&F&B&2&F&Fк&x&1&A&B&6&F&Fа &x&0&F&B&F&F&Fо&x&0&A&C&3&F&Fп&x&0&5&C&7&F&Fк&x&0&0&C&B&F&Fи"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&6&0&5&F&Fa&x&F&0&0&A&F&Fd&x&E&A&0&F&F&Fo &x&D&E&1&8&F&Fl&x&D&8&1&D&F&Fi&x&D&2&2&2&F&Fs&x&C&C&2&7&F&Ft &x&C&0&3&0&F&F- &x&B&4&3&A&F&Fо&x&A&E&3&F&F&Fт&x&A&8&4&4&F&Fо&x&A&2&4&9&F&Fб&x&9&C&4&D&F&Fр&x&9&6&5&2&F&Fа&x&9&0&5&7&F&Fж&x&8&A&5&C&F&Fе&x&8&4&6&1&F&Fн&x&7&E&6&6&F&Fи&x&7&8&6&A&F&Fе &x&6&C&7&4&F&Fи&x&6&6&7&9&F&Fг&x&6&0&7&E&F&Fр&x&5&A&8&3&F&Fо&x&5&4&8&7&F&Fк&x&4&E&8&C&F&Fо&x&4&8&9&1&F&Fв &x&3&C&9&B&F&Fв &x&3&0&A&4&F&Fв&x&2&A&A&9&F&Fа&x&2&4&A&E&F&Fй&x&1&E&B&3&F&Fт&x&1&8&B&8&F&Fл&x&1&2&B&D&F&Fи&x&0&C&C&1&F&Fс&x&0&6&C&6&F&Fт&x&0&0&C&B&F&Fе"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&5&0&6&F&Fa&x&E&D&0&C&F&Fd&x&E&6&1&2&F&Fo &x&D&7&1&E&F&Fh&x&D&0&2&4&F&Fe&x&C&8&2&A&F&Fl&x&C&1&3&0&F&Fp &x&B&2&3&C&F&F- &x&A&3&4&8&F&Fо&x&9&C&4&E&F&Fт&x&9&4&5&4&F&Fо&x&8&D&5&A&F&Fб&x&8&5&6&0&F&Fр&x&7&E&6&6&F&Fа&x&7&7&6&B&F&Fж&x&6&F&7&1&F&Fе&x&6&8&7&7&F&Fн&x&6&0&7&D&F&Fи&x&5&9&8&3&F&Fе &x&4&A&8&F&F&Fэ&x&4&3&9&5&F&Fт&x&3&B&9&B&F&Fо&x&3&4&A&1&F&Fй &x&2&5&A&D&F&Fп&x&1&E&B&3&F&Fо&x&1&6&B&9&F&Fм&x&0&F&B&F&F&Fо&x&0&7&C&5&F&Fщ&x&0&0&C&B&F&Fи"));
                }
                return true;
            }else if(Objects.equals(args[0], "list")) {
                String listTitle = language.equals("en") ? "List of players with OP:" : "Список игроков с опкой:";
                p.sendMessage(ChatColor.WHITE + listTitle);
                if(list != null && !list.isEmpty()) {
                    for (String item : list) {
                        if(item != null) {
                            p.sendMessage(ChatColor.GREEN +  item);
                        }
                    }
                } else {
                    String emptyList = language.equals("en") ? "List is empty" : "Список пуст";
                    p.sendMessage(ChatColor.GRAY + emptyList);
                }
                return true;
            }else if (Objects.equals(args[0], "reload")) {
                plugin.reloadConfig();
                String reload = language.equals("en") ? "The config has been successfully reloaded." : "Конфиг успешно перезагружен";
                p.sendMessage(ChatColor.GREEN + reload);
            }else {
                String usage = language.equals("en") ? "Usage: /ado help" : "Использование: /ado help";
                p.sendMessage(ChatColor.RED + usage);
                return true;
            }


        } else {
            if(args == null || args.length == 0) {
                Bukkit.getLogger().info(ChatColor.RED + "Использование: /ado help");
                return true;
            }

            if(Objects.equals(args[0], "add")) {
                if(args.length < 2) {
                    String usage = language.equals("en") ? "Usage: /ado add <nickname>" : "Использование: /ado add <ник>";
                    Bukkit.getLogger().info(ChatColor.RED + usage);
                    return true;
                }
                String playerName = args[1];
                if(playerName != null && !playerName.isEmpty()) {
                    list.add(playerName);
                    plugin.getConfig().set("whitelist", list);
                    plugin.saveConfig();
                    String message = language.equals("en") ? "Player " + playerName + " added to whitelist" : "Игрок " + playerName + " добавлен в вайтлист";
                    Bukkit.getLogger().info(ChatColor.GREEN + message);
                }
                return true;
            }else if(Objects.equals(args[0], "remove")) {
                if(args.length < 2) {
                    String usage = language.equals("en") ? "Usage: /ado remove <nickname>" : "Использование: /ado remove <ник>";
                    Bukkit.getLogger().info(ChatColor.RED + usage);
                    return true;
                }
                String playerName = args[1];
                if(playerName != null && !playerName.isEmpty()) {
                    list.remove(playerName);
                    plugin.getConfig().set("whitelist", list);
                    plugin.saveConfig();
                    String message = language.equals("en") ? "Player " + playerName + " removed from whitelist" : "Игрок " + playerName + " удален из вайтлиста";
                    Bukkit.getLogger().info(ChatColor.RED + message);
                }
                return true;
            }else if(Objects.equals(args[0], "help")) {
                if (language.equals("en")) {
                    Bukkit.getLogger().info(ChatColor.GOLD + "AutoDeOp Commands:");
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&7&0&4&F&Fa&x&F&2&0&8&F&Fd&x&E&D&0&C&F&Fo &x&E&2&1&5&F&F{&x&D&D&1&9&F&Fa&x&D&8&1&D&F&Fd&x&D&3&2&1&F&Fd&x&C&E&2&5&F&F/&x&C&9&2&9&F&Fr&x&C&3&2&E&F&Fe&x&B&E&3&2&F&Fm&x&B&9&3&6&F&Fo&x&B&4&3&A&F&Fv&x&A&F&3&E&F&Fe&x&A&A&4&2&F&F} &x&9&F&4&B&F&Fn&x&9&A&4&F&F&Fi&x&9&5&5&3&F&Fc&x&8&B&5&B&F&Fk &x&8&1&6&3&F&F- &x&7&B&6&8&F&Fa&x&7&6&6&C&F&Fd&x&7&1&7&0&F&Fd&x&6&C&7&4&F&F/&x&6&7&7&8&F&Fr&x&6&2&7&C&F&Fe&x&5&D&8&0&F&Fm&x&5&7&8&5&F&Fo&x&5&2&8&9&F&Fv&x&4&D&8&D&F&Fe&x&4&8&9&1&F&F &x&4&3&9&5&F&Fp&x&3&E&9&9&F&Fl&x&3&3&A&2&F&Fa&x&2&E&A&6&F&Fy&x&2&9&A&A&F&Fe&x&2&4&A&E&F&Fr &x&1&F&B&2&F&Ft&x&1&A&B&6&F&Fo &x&0&F&B&F&F&Fw&x&0&A&C&3&F&Fh&x&0&5&C&7&F&Fi&x&0&0&C&B&F&Ft&x&0&0&C&B&F&Fe&x&0&0&C&B&F&Fl&x&0&0&C&B&F&Fi&x&0&0&C&B&F&Fs&x&0&0&C&B&F&Ft"));
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&6&0&5&F&Fa&x&F&0&0&A&F&Fd&x&E&A&0&F&F&Fo &x&D&E&1&8&F&Fl&x&D&8&1&D&F&Fi&x&D&2&2&2&F&Fs&x&C&C&2&7&F&Ft &x&C&0&3&0&F&F- &x&B&4&3&A&F&Fs&x&A&E&3&F&F&Fh&x&A&8&4&4&F&Fo&x&A&2&4&9&F&Fw&x&9&C&4&D&F&F &x&9&6&5&2&F&Fp&x&9&0&5&7&F&Fl&x&8&A&5&C&F&Fa&x&8&4&6&1&F&Fy&x&7&E&6&6&F&Fe&x&7&8&6&A&F&Fr&x&6&C&7&4&F&Fs &x&6&6&7&9&F&Fi&x&6&0&7&E&F&Fn &x&5&A&8&3&F&Fw&x&5&4&8&7&F&Fh&x&4&E&8&C&F&Fi&x&4&8&9&1&F&Ft&x&3&C&9&B&F&Fe&x&3&0&A&4&F&Fl&x&2&A&A&9&F&Fi&x&2&4&A&E&F&Fs&x&1&E&B&3&F&Ft"));
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&5&0&6&F&Fa&x&E&D&0&C&F&Fd&x&E&6&1&2&F&Fo &x&D&7&1&E&F&Fh&x&D&0&2&4&F&Fe&x&C&8&2&A&F&Fl&x&C&1&3&0&F&Fp &x&B&2&3&C&F&F- &x&A&3&4&8&F&Fs&x&9&C&4&E&F&Fh&x&9&4&5&4&F&Fo&x&8&D&5&A&F&Fw&x&8&5&6&0&F&F &x&7&E&6&6&F&Ft&x&7&7&6&B&F&Fh&x&6&F&7&1&F&Fi&x&6&8&7&7&F&Fs &x&6&0&7&D&F&Fh&x&5&9&8&3&F&Fe&x&4&A&8&F&F&Fl&x&4&3&9&5&F&Fp&x&3&B&9&B&F&F &x&3&4&A&1&F&Fm&x&2&5&A&D&F&Fe&x&1&E&B&3&F&Fs&x&1&6&B&9&F&Fs&x&0&F&B&F&F&Fa&x&0&7&C&5&F&Fg&x&0&0&C&B&F&Fe"));
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&2&0&8&F&Fa&x&E&9&1&0&F&Fd&x&D&F&1&7&F&Fo &x&C&C&2&7&F&Fr&x&C&2&2&F&F&Fe&x&B&8&3&7&F&Fl&x&A&E&3&E&F&Fo&x&A&5&4&6&F&Fa&x&9&B&4&E&F&Fd &x&8&8&5&E&F&F- &x&7&4&6&D&F&Fr&x&6&B&7&5&F&Fe&x&6&1&7&D&F&Fl&x&5&7&8&5&F&Fo&x&4&E&8&D&F&Fa&x&4&4&9&4&F&Fd &x&3&0&A&4&F&Fc&x&2&7&A&C&F&Fo&x&1&D&B&4&F&Fn&x&1&3&B&B&F&Ff&x&0&A&C&3&F&Fi&x&0&0&C&B&F&Fg"));
                } else {
                    Bukkit.getLogger().info(ChatColor.GOLD + "Команды AutoDeOp:");
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&7&0&4&F&Fa&x&F&2&0&8&F&Fd&x&E&D&0&C&F&Fo &x&E&2&1&5&F&F{&x&D&D&1&9&F&Fa&x&D&8&1&D&F&Fd&x&D&3&2&1&F&Fd&x&C&E&2&5&F&F/&x&C&9&2&9&F&Fr&x&C&3&2&E&F&Fe&x&B&E&3&2&F&Fm&x&B&9&3&6&F&Fo&x&B&4&3&A&F&Fv&x&A&F&3&E&F&Fe&x&A&A&4&2&F&F} &x&9&F&4&B&F&Fн&x&9&A&4&F&F&Fи&x&9&5&5&3&F&Fк &x&8&B&5&B&F&F- &x&8&1&6&3&F&Fр&x&7&B&6&8&F&Fе&x&7&6&6&C&F&Fд&x&7&1&7&0&F&Fа&x&6&C&7&4&F&Fк&x&6&7&7&8&F&Fт&x&6&2&7&C&F&Fи&x&5&D&8&0&F&Fр&x&5&7&8&5&F&Fо&x&5&2&8&9&F&Fв&x&4&D&8&D&F&Fа&x&4&8&9&1&F&Fн&x&4&3&9&5&F&Fи&x&3&E&9&9&F&Fе &x&3&3&A&2&F&Fс&x&2&E&A&6&F&Fп&x&2&9&A&A&F&Fи&x&2&4&A&E&F&Fс&x&1&F&B&2&F&Fк&x&1&A&B&6&F&Fа &x&0&F&B&F&F&Fо&x&0&A&C&3&F&Fп&x&0&5&C&7&F&Fк&x&0&0&C&B&F&Fи"));
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&6&0&5&F&Fa&x&F&0&0&A&F&Fd&x&E&A&0&F&F&Fo &x&D&E&1&8&F&Fl&x&D&8&1&D&F&Fi&x&D&2&2&2&F&Fs&x&C&C&2&7&F&Ft &x&C&0&3&0&F&F- &x&B&4&3&A&F&Fо&x&A&E&3&F&F&Fт&x&A&8&4&4&F&Fо&x&A&2&4&9&F&Fб&x&9&C&4&D&F&Fр&x&9&6&5&2&F&Fа&x&9&0&5&7&F&Fж&x&8&A&5&C&F&Fе&x&8&4&6&1&F&Fн&x&7&E&6&6&F&Fи&x&7&8&6&A&F&Fе &x&6&C&7&4&F&Fи&x&6&6&7&9&F&Fг&x&6&0&7&E&F&Fр&x&5&A&8&3&F&Fо&x&5&4&8&7&F&Fк&x&4&E&8&C&F&Fо&x&4&8&9&1&F&Fв &x&3&C&9&B&F&Fв &x&3&0&A&4&F&Fв&x&2&A&A&9&F&Fа&x&2&4&A&E&F&Fй&x&1&E&B&3&F&Fт&x&1&8&B&8&F&Fл&x&1&2&B&D&F&Fи&x&0&C&C&1&F&Fс&x&0&6&C&6&F&Fт&x&0&0&C&B&F&Fе"));
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&5&0&6&F&Fa&x&E&D&0&C&F&Fd&x&E&6&1&2&F&Fo &x&D&7&1&E&F&Fh&x&D&0&2&4&F&Fe&x&C&8&2&A&F&Fl&x&C&1&3&0&F&Fp &x&B&2&3&C&F&F- &x&A&3&4&8&F&Fо&x&9&C&4&E&F&Fт&x&9&4&5&4&F&Fо&x&8&D&5&A&F&Fб&x&8&5&6&0&F&Fр&x&7&E&6&6&F&Fа&x&7&7&6&B&F&Fж&x&6&F&7&1&F&Fе&x&6&8&7&7&F&Fн&x&6&0&7&D&F&Fи&x&5&9&8&3&F&Fе &x&4&A&8&F&F&Fэ&x&4&3&9&5&F&Fт&x&3&B&9&B&F&Fо&x&3&4&A&1&F&Fй &x&2&5&A&D&F&Fп&x&1&E&B&3&F&Fо&x&1&6&B&9&F&Fм&x&0&F&B&F&F&Fо&x&0&7&C&5&F&Fщ&x&0&0&C&B&F&Fи"));
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&x&F&C&0&0&F&F/&x&F&4&0&6&F&Fa&x&E&D&0&C&F&Fd&x&E&5&1&2&F&Fo &x&D&6&1&F&F&Fr&x&C&E&2&5&F&Fe&x&C&7&2&B&F&Fl&x&B&F&3&1&F&Fo&x&B&7&3&7&F&Fa&x&B&0&3&E&F&Fd &x&A&0&4&A&F&F- &x&9&1&5&6&F&Fп&x&8&9&5&C&F&Fе&x&8&2&6&2&F&Fр&x&7&A&6&9&F&Fе&x&7&3&6&F&F&Fз&x&6&B&7&5&F&Fа&x&6&3&7&B&F&Fг&x&5&C&8&1&F&Fр&x&5&4&8&7&F&Fу&x&4&C&8&D&F&Fз&x&4&5&9&4&F&Fк&x&3&D&9&A&F&Fа &x&2&E&A&6&F&Fк&x&2&6&A&C&F&Fо&x&1&F&B&2&F&Fн&x&1&7&B&9&F&Fф&x&0&F&B&F&F&Fи&x&0&8&C&5&F&Fг&x&0&0&C&B&F&Fа"));
                }
                return true;
            }else if(Objects.equals(args[0], "list")) {
                String listTitle = language.equals("en") ? "List of players with OP:" : "Список игроков с опкой:";
                Bukkit.getLogger().info(ChatColor.WHITE + listTitle);
                if(list != null && !list.isEmpty()) {
                    for (String item : list) {
                        if(item != null) {
                            Bukkit.getLogger().info(ChatColor.GREEN +  item);
                        }
                    }
                } else {
                    String emptyList = language.equals("en") ? "List is empty" : "Список пуст";
                    Bukkit.getLogger().info(ChatColor.GRAY + emptyList);
                }
                return true;
            }else if(Objects.equals(args[0], "reload")) {
                    plugin.reloadConfig();
                    String reload = language.equals("en") ? "The config has been successfully reloaded." : "Конфиг успешно перезагружен";
                    Bukkit.getLogger().info(ChatColor.GREEN + reload);
            }else {
                String usage = language.equals("en") ? "Usage: /ado help" : "Использование: /ado help";
                Bukkit.getLogger().info(ChatColor.RED + usage);
                return true;
            }
        }
        return true;
    }
}