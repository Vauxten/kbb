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
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public final class KingsButBad extends JavaPlugin {

    public static Player king = null;
    public static HashMap<Player, Role> playerRoleHashMap = new HashMap<>();
    public static NamespacedKey wasinPrison;
    public static HashMap<Player, Integer> prisonTimer = new HashMap<>();
    public static HashMap<Player, Role> playerRoleInviteHashMap = new HashMap<>();
    public static LuckPerms api;
    public static Villager royalvillager;
    public static Villager sewervillager;
    public static ArrayList<UUID> prisoners = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic



        api = LuckPermsProvider.get();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new AsyncPlayerChatEventListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerInteractAtEntityListener(), this);
        MiscTask task = new MiscTask();
        task.runTaskTimer(this, 0, 1);
        RoleTask task2 = new RoleTask();

        wasinPrison = new NamespacedKey(this, "inPrison");
        task2.runTaskTimer(this, 0, 1);
        this.getCommand("king").setExecutor(new KingCommand());
        this.getCommand("accept").setExecutor(new AcceptCommand());
        while (Bukkit.getWorld("world") == null) {}
        for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities()) {
            if (le.getType().equals(EntityType.VILLAGER)) {
                le.remove();
            }
        }

        royalvillager = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -25, -60, -37), EntityType.VILLAGER);

        royalvillager.setCustomName(CreateText.addColors("<gradient:#FFFF52:#FFBA52>Royal Villager"));
        royalvillager.setCustomNameVisible(true);
        royalvillager.setInvulnerable(true);
        royalvillager.setPersistent(true);
        royalvillager.setAI(false);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static boolean isInside(Player player, Location loc1, Location loc2)
    {
        double[] dim = new double[2];

        dim[0] = loc1.getX();
        dim[1] = loc2.getX();
        Arrays.sort(dim);
        if(player.getLocation().getX() > dim[1] || player.getLocation().getX() < dim[0])
            return false;

        dim[0] = loc1.getZ();
        dim[1] = loc2.getZ();
        Arrays.sort(dim);
        if(player.getLocation().getZ() > dim[1] || player.getLocation().getZ() < dim[0])
            return false;


        return true;
    }

}
