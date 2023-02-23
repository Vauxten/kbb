package agmas.kingsbutbad;

import agmas.kingsbutbad.commands.AcceptCommand;
import agmas.kingsbutbad.commands.KingCommand;
import agmas.kingsbutbad.listeners.AsyncPlayerChatEventListener;
import agmas.kingsbutbad.listeners.PlayerDeathListener;
import agmas.kingsbutbad.listeners.PlayerInteractAtEntityListener;
import agmas.kingsbutbad.listeners.PlayerJoinListener;
import agmas.kingsbutbad.tasks.MiscTask;
import agmas.kingsbutbad.tasks.RoleTask;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class KingsButBad extends JavaPlugin {

    public static Player king = null;
    public static HashMap<Player, Role> playerRoleHashMap = new HashMap<>();
    public static HashMap<Player, Location> respawnHashMap = new HashMap<>();
    public static HashMap<Player, Role> playerRoleInviteHashMap = new HashMap<>();
    public static LuckPerms api;
    public static Villager royalvillager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities()) {
            if (le.getType().equals(EntityType.VILLAGER)) {
                le.remove();
            }
        }

        royalvillager = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -25, -60, -37), EntityType.VILLAGER);

        royalvillager.setCustomName(CreateText.addColors("<gradient:#FFFF52:#FFBA52>Royal Villager"));
        royalvillager.setCustomNameVisible(true);
        royalvillager.setPersistent(true);
        royalvillager.setInvulnerable(true);
        royalvillager.setAI(false);


        api = LuckPermsProvider.get();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new AsyncPlayerChatEventListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerInteractAtEntityListener(), this);
        MiscTask task = new MiscTask();
        task.runTaskTimer(this, 0, 1);
        RoleTask task2 = new RoleTask();
        task2.runTaskTimer(this, 0, 1);
        this.getCommand("king").setExecutor(new KingCommand());
        this.getCommand("accept").setExecutor(new AcceptCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
