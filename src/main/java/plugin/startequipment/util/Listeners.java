package plugin.startequipment.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugin.startequipment.Main;

public class Listeners implements Listener{

    public Main instance = Main.instance;
    public static Inventory inventory;

    @EventHandler
    public void InventoryCloseEvent(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if (instance.getConfig().contains("edit." + p.getUniqueId().toString())) {
            String index = instance.getConfig().getString("edit." + p.getUniqueId().toString());
            Inventory inv = e.getInventory();

            if (inv.equals(inventory)) {
                for (int i = 0; i != 27; i++) {
                    int slot = i;
                    instance.getConfig().set("kits." + index + "." + slot, null);
                    instance.saveConfig();
                }
                for (int i = 0; i != 27; i++) {
                    if (inv.getItem(i) != null) {
                        int slot = i;
                        ItemStack item = inv.getItem(i);

                        instance.getConfig().set("kits." + index + "." + slot, item);
                        instance.saveConfig();
                    }
                }
            }
        }

        instance.getConfig().set("edit." + p.getUniqueId().toString(), null);
        instance.saveConfig();
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPlayedBefore()) {
            for (int i = 0; i != 501; i++) {
                if (instance.getConfig().contains("active." + i)) {
                    String activeKit = instance.getConfig().getString("active." + i);
                    for (int o = 0; o != 27; o++) {
                        if (instance.getConfig().contains("kits." + activeKit)) {
                            if (instance.getConfig().getItemStack("kits." + activeKit + "." + o) != null) {
                                p.getInventory().addItem(instance.getConfig().getItemStack("kits." + activeKit + "." + o));
                            }

                        }
                    }
                }
            }
        }
    }

    public static void setInventory(Inventory inv) {
        inventory = inv;
    }

}
