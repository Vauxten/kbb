package agmas.kingsbutbad;

import agmas.kingsbutbad.commands.AcceptCommand;
import agmas.kingsbutbad.commands.KingCommand;
import agmas.kingsbutbad.commands.ResignCommand;
import agmas.kingsbutbad.commands.SetMoneyCommand;
import agmas.kingsbutbad.listeners.*;
import agmas.kingsbutbad.tasks.FailsafeTask;
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
    public static NamespacedKey money;
    public static HashMap<Player, Integer> prisonTimer = new HashMap<>();
    public static HashMap<Player, Role> playerRoleInviteHashMap = new HashMap<>();
    public static LuckPerms api;
    public static Villager royalvillager;
    public static Villager sewervillager;
    public static Villager selfdefense;
    public static Villager farmerjoe;
    public static Villager minerguard;
    public static Villager bertrude;
    public static String kinggender = "King";
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
        pm.registerEvents(new PlayerBlockListeners(), this);
        MiscTask task = new MiscTask();
        task.runTaskTimer(this, 0, 1);
        FailsafeTask task3 = new FailsafeTask();
        task3.runTaskTimer(this, 0, 1);
        RoleTask task2 = new RoleTask();

        wasinPrison = new NamespacedKey(this, "inPrison");
        money = new NamespacedKey(this, "money");
        task2.runTaskTimer(this, 0, 1);
        this.getCommand("king").setExecutor(new KingCommand());
        this.getCommand("accept").setExecutor(new AcceptCommand());
        this.getCommand("setmoney").setExecutor(new SetMoneyCommand());
        this.getCommand("resign").setExecutor(new ResignCommand());
        while (Bukkit.getWorld("world") == null) {}
        for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities()) {
            if (le.getType().equals(EntityType.VILLAGER)) {
                le.remove();
            }
        }

            royalvillager = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -63.5, -57, 23.5, 42, 0), EntityType.VILLAGER);

            royalvillager.setCustomName(CreateText.addColors("<gradient:#FFFF52:#FFBA52>Royal Villager"));
            royalvillager.setCustomNameVisible(true);
            royalvillager.setInvulnerable(true);
            royalvillager.setPersistent(true);
            royalvillager.setAI(false);
        farmerjoe = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -100.5, -57, 2.5, 140, 0), EntityType.VILLAGER);

        farmerjoe.setProfession(Villager.Profession.FARMER);
        farmerjoe.setCustomName(CreateText.addColors("<blue>Farmer Joe"));
        farmerjoe.setCustomNameVisible(true);
        farmerjoe.setInvulnerable(true);
        farmerjoe.setPersistent(true);
        farmerjoe.setAI(false);
        bertrude = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -112.5, -57, -5.5, -180, 0), EntityType.VILLAGER);

        bertrude.setCustomName(CreateText.addColors("bertrude"));
        bertrude.setCustomNameVisible(true);
        bertrude.setInvulnerable(true);
        bertrude.setPersistent(true);
        bertrude.setAI(false);

        sewervillager = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -78.5, -62, -28.5, -90, 0), EntityType.VILLAGER);

        sewervillager.setProfession(Villager.Profession.TOOLSMITH);
        sewervillager.setCustomName(CreateText.addColors("<gray>Shady Slim"));
        sewervillager.setCustomNameVisible(true);
        sewervillager.setInvulnerable(true);
        sewervillager.setPersistent(true);
        sewervillager.setAI(false);

        selfdefense = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -142.5, -57, 12.5, -45, 0), EntityType.VILLAGER);

        selfdefense.setProfession(Villager.Profession.LEATHERWORKER);
        selfdefense.setCustomName(CreateText.addColors("<red>Defender Jim"));
        selfdefense.setCustomNameVisible(true);
        selfdefense.setInvulnerable(true);
        selfdefense.setPersistent(true);
        selfdefense.setAI(false);

        minerguard = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -122.5, -57, -2.5, 180, 0), EntityType.VILLAGER);

        minerguard.setCustomName(CreateText.addColors("<gray>Miner"));
        minerguard.setCustomNameVisible(true);
        minerguard.setInvulnerable(true);
        minerguard.setPersistent(true);
        minerguard.setAI(false);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        royalvillager.remove();
        sewervillager.remove();
        selfdefense.remove();
        bertrude.remove();
        for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities()) {
            if (le.getType().equals(EntityType.VILLAGER)) {
                le.remove();
            }
        }
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
