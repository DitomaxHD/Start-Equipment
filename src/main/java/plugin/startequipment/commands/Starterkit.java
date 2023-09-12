package plugin.startequipment.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import plugin.startequipment.Main;
import plugin.startequipment.util.Listeners;
import plugin.startequipment.util.Starterkits;

public class Starterkit implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p)) return true;

        Main instance = Main.instance;
        String prefix = instance.prefix;


        if (strings.length == 0) {p.sendMessage(prefix + "§cVerwendung§7: /starterkit [create/delete, edit, activate/deactivate]"); return true;}

        //starterkit create [index]
        if (strings[0].equalsIgnoreCase("create")) {
            if (!p.hasPermission("starterkit.create")) {p.sendMessage(prefix + "§cDu hast nicht die rechte um dies zu tun§7!"); return true;}

            if (strings.length == 2) {
                String index = strings[1];

                if (!instance.getConfig().contains("kits." + index)) {
                    instance.getConfig().set("kits." + index, "");
                    instance.saveConfig();
                    p.sendMessage(prefix + "§7Du hast erfolgreich ein leeres Starterkit erstellt!");
                    return true;

                }else {
                    p.sendMessage(prefix + "§cEs gibt bereits ein Starterkit mit diesem Namen§7!");
                    return true;
                }

            }else {
                p.sendMessage(prefix + "§cVerwendung§7: /starterkit create [index]");
                return true;
            }
        }

        //starterkit delete [index]
        if (strings[0].equalsIgnoreCase("delete")) {
            if (!p.hasPermission("starterkit.delete")) {p.sendMessage(prefix + "§cDu hast nicht die rechte um dies zu tun§7!"); return true;}

            if (strings.length == 2) {
                String index = strings[1];

                if (instance.getConfig().contains("kits." + index)) {
                    instance.getConfig().set("kits." + index, null);
                    instance.saveConfig();
                    p.sendMessage(prefix + "§7Du hast erfolgreich ein Starterkit gelöscht!");
                    return true;

                }else {
                    p.sendMessage(prefix + "§7Dieses Starterkit existiert nicht!");
                }

            }else {
                p.sendMessage(prefix + "§cVerwendung§7: /starterkit create [index]");
                return true;
            }
        }

        //starterkit edit [index]
        if (strings[0].equalsIgnoreCase("edit")) {
            if (!p.hasPermission("starterkit.edit")) {p.sendMessage(prefix + "§cDu hast nicht die rechte um dies zu tun§7!"); return true;}

            if (strings.length == 2) {
                String index = strings[1];

                instance.getConfig().set("edit." + p.getUniqueId().toString(), index);
                instance.saveConfig();

                if (instance.getConfig().contains("kits." + index)) {
                    Inventory inv = Starterkits.createInv(index);
                    Listeners.setInventory(inv);
                    for (int i = 0; i != 27; i++) {
                        if (instance.getConfig().contains("kits." + index + "." + i)) {
                            ItemStack item = instance.getConfig().getItemStack("kits." + index + "." + i);
                            inv.setItem(i, item);
                        }
                    }
                    p.openInventory(inv);
                    return true;

                }else {
                    p.sendMessage(prefix + "§cDieses Starterkit existiert nicht§7!");
                    return true;
                }

            }else {
                p.sendMessage(prefix + "§cVerwendung§7: /starterkit edit [index]");
                return true;
            }
        }

        //starterkit activate [index]
        if (strings[0].equalsIgnoreCase("activate")) {
            if (!p.hasPermission("starterkit.activate")) {p.sendMessage(prefix + "§cDu hast nicht die rechte um dies zu tun§7!"); return true;}

            if (strings.length == 2) {
                String index = strings[1];

                if (instance.getConfig().contains("kits." + index)) {
                    int number = 0;
                    while (instance.getConfig().contains("active." + number)) {
                        number++;
                    }
                    instance.getConfig().set("active." + number, index);
                    instance.saveConfig();
                    p.sendMessage(prefix + "Du hast das Starterkit: §6" + index + " §7aktiviert!");
                    return true;

                }else {
                    p.sendMessage(prefix + "Dieses Starterkit existiert nicht!");
                    return true;
                }

            }else {
                p.sendMessage(prefix + "§cVerwendung§7: /starterkit activate [index]");
                return true;
            }
        }

        //starterkit deactivate [index]
        if (strings[0].equalsIgnoreCase("deactivate")) {
            if (!p.hasPermission("starterkit.deactivate")) {p.sendMessage(prefix + "§cDu hast nicht die rechte um dies zu tun§7!"); return true;}

            if (strings.length == 2) {
                String index = strings[1];

                boolean successfull = false;
                for (int i = 0; i != 501; i++) {
                    if (instance.getConfig().contains("active." + i)) {
                        if (instance.getConfig().getString("active." + i).equalsIgnoreCase(index)) {
                            instance.getConfig().set("active." + i , null);
                            instance.saveConfig();
                            successfull = true;
                        }
                    }
                }

                if (successfull) {
                    successfull = false;
                    p.sendMessage(prefix + "Du hast das Starterkit: §6" + index + " §7deaktiviert!");
                    return true;

                }else {
                    p.sendMessage(prefix + "Dieses Starterkit existiert nicht!");
                    return true;
                }

            }else {
                p.sendMessage(prefix + "§cVerwendung§7: /starterkit deactivate [index]");
                return true;
            }
        }

        p.sendMessage(prefix + "§cVerwendung§7: /starterkit [create/delete, edit, activate/deactivate]");
        return false;
    }
}
