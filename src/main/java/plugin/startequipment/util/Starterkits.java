package plugin.startequipment.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class Starterkits {

    public static Inventory createInv(String name) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, ChatColor.BLUE + name);

        return inv;
    }
}
