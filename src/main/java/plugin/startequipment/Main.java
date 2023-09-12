package plugin.startequipment;

import org.bukkit.plugin.java.JavaPlugin;
import plugin.startequipment.commands.Starterkit;
import plugin.startequipment.util.Listeners;

public final class Main extends JavaPlugin {

    public String prefix;
    public static Main instance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveConfig();
        instance = this;

        if (!this.getConfig().contains("prefix")) {
            this.getConfig().set("prefix", "§7[§6Starterkit§7]");
            saveConfig();
        }

        prefix = this.getConfig().getString("prefix") + " ";

        getCommand("starterkit").setExecutor(new Starterkit());
        getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();
    }
}
