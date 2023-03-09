package agmas.kingsbutbad;

import agmas.kingsbutbad.commands.*;
import agmas.kingsbutbad.listeners.*;
import agmas.kingsbutbad.tasks.*;
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
    public static Player king2 = null;
    public static HashMap<Player, Role> playerRoleHashMap = new HashMap<>();
    public static NamespacedKey wasinPrison;
    public static NamespacedKey preffered;
    public static NamespacedKey money;
    public static Boolean joesunlocked = false;
    public static Boolean coalCompactor = false;
    public static HashMap<Player, Integer> prisonTimer = new HashMap<>();
    public static HashMap<Player, Integer> prisonQuota = new HashMap<>();
    public static HashMap<Player, Role> playerRoleInviteHashMap = new HashMap<>();
    public static HashMap<Player, Location> datedLocations = new HashMap<>();
    public static LuckPerms api;
    public static Villager royalvillager;
    public static Villager sewervillager;
    public static HashMap<Player,Player> bodylink = new HashMap<>();
    public static Villager selfdefense;
    public static Boolean mineunlocked = false;
    public static Villager farmerjoe;
    public static Villager minerguard;
    public static Villager bertrude;
    public static Villager lunchlady;
    public static Villager royaltrader;
    public static Villager royalservant;
    public static Villager mopvillager;
    public static Villager archerjohn;
    public static Villager prisonguard;
    public static Villager littlejoes;
    public static Villager miner;
    public static String kinggender = "King";
    public static String kinggender2 = "King";
    public static ArrayList<UUID> prisoners = new ArrayList<>();
    public static int taxesCount = 25;
    public static int cooldown = 20 * 5;
    public static boolean cooldownisreal = true;
    public static Player lastking;
    public static Player lastking2;
    public static HashMap<Player, Integer> currentzone = new HashMap<>();
    public static HashMap<Player, Boolean> soundwaves = new HashMap<>();


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
        new AFKTask().runTaskTimer(this, 0, 6000);
        new LocationTask().runTaskTimer(this, 3000, 6000);

        wasinPrison = new NamespacedKey(this, "inPrison");
        money = new NamespacedKey(this, "money");
        preffered = new NamespacedKey(this, "preffered");
        task2.runTaskTimer(this, 0, 1);
        this.getCommand("king").setExecutor(new KingCommand());
        this.getCommand("accept").setExecutor(new AcceptCommand());
        this.getCommand("setmoney").setExecutor(new SetMoneyCommand());
        this.getCommand("resign").setExecutor(new ResignCommand());
        this.getCommand("discord").setExecutor(new DiscordCommand());
        this.getCommand("soundwaves").setExecutor(new SoundWavesCommand());
        this.getCommand("resetVillavgers").setExecutor(new ResetVillagersCommand());
        while (Bukkit.getWorld("world") == null) {}
        for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities()) {
            if (le.getType().equals(EntityType.VILLAGER)) {
                le.remove();
            }
        }

        for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities()) {
            if (le.getType().equals(EntityType.VILLAGER)) {
                le.damage(99999);
                le.remove();
            }
        }
        setVillagers();
    }

    public static void setVillagers() {
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

        selfdefense = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -122.5, -57, -2.5, 180, 0), EntityType.VILLAGER);

        selfdefense.setProfession(Villager.Profession.LEATHERWORKER);
        selfdefense.setCustomName(CreateText.addColors("<red>Defender Jim"));
        selfdefense.setCustomNameVisible(true);
        selfdefense.setInvulnerable(true);
        selfdefense.setPersistent(true);
        selfdefense.setAI(false);

        minerguard = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -144.5, -57, 12.5, 45, 0), EntityType.VILLAGER);

        minerguard.setCustomName(CreateText.addColors("<gray>Miner"));
        minerguard.setCustomNameVisible(true);
        minerguard.setInvulnerable(true);
        minerguard.setPersistent(true);
        minerguard.setAI(false);
        lunchlady = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -149.5, -57, 2.5, -90, 0), EntityType.VILLAGER);

        lunchlady.setCustomName(CreateText.addColors("<gold>Lunch Lady"));
        lunchlady.setCustomNameVisible(true);
        lunchlady.setInvulnerable(true);
        lunchlady.setPersistent(true);
        lunchlady.setAI(false);

        royaltrader = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -128.5, -57, -6.5, 180, 0), EntityType.VILLAGER);

        royaltrader.setCustomName(CreateText.addColors("<yellow>Royal Trader"));
        royaltrader.setCustomNameVisible(true);
        royaltrader.setInvulnerable(true);
        royaltrader.setPersistent(true);
        royaltrader.setAI(false);
        mopvillager = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -103.21, -57.00, -17.24, 60, 0), EntityType.VILLAGER);

        mopvillager.setCustomName(CreateText.addColors("<yellow>Janitor"));
        mopvillager.setCustomNameVisible(true);
        mopvillager.setInvulnerable(true);
        mopvillager.setPersistent(true);
        mopvillager.setAI(false);

        archerjohn = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -140.30, -59.0, -39.59, -90, 0), EntityType.VILLAGER);

        archerjohn.setCustomName(CreateText.addColors("<green>archer johnm"));
        archerjohn.setCustomNameVisible(true);
        archerjohn.setInvulnerable(true);
        archerjohn.setPersistent(true);
        archerjohn.setAI(false);

        prisonguard = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -136.5, -57.0, -12, -90, 0), EntityType.VILLAGER);

        prisonguard.setCustomName(CreateText.addColors("<blue>Prison Guard"));
        prisonguard.setCustomNameVisible(true);
        prisonguard.setInvulnerable(true);
        prisonguard.setPersistent(true);
        prisonguard.setAI(false);

        royalservant = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -67.5, -57.0, 23.5, -45, 0), EntityType.VILLAGER);

        royalservant.setCustomName(CreateText.addColors("<gold>Royal Servant"));
        royalservant.setCustomNameVisible(true);
        royalservant.setInvulnerable(true);
        royalservant.setPersistent(true);
        royalservant.setAI(false);

        littlejoes = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -113.5, -56.0, -1.5, -180, 0), EntityType.VILLAGER);

        littlejoes.setCustomName(CreateText.addColors("<blue>Little Joes"));
        littlejoes.setCustomNameVisible(true);
        littlejoes.setBaby();
        littlejoes.setAgeLock(true);
        littlejoes.setInvulnerable(true);
        littlejoes.setPersistent(true);
        littlejoes.setAI(false);

        miner = (Villager) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -202.5, -39.0, -244.5, 0, 0), EntityType.VILLAGER);
        miner.setCustomName(CreateText.addColors("<gold>Miner Joseph"));
        miner.setCustomNameVisible(true);
        miner.setInvulnerable(true);
        miner.setPersistent(true);
        miner.setAI(false);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        royalvillager.remove();
        sewervillager.remove();
        selfdefense.remove();
        bertrude.remove();
        miner.remove();
        littlejoes.remove();
        royalservant.remove();
        royaltrader.remove();
        minerguard.remove();
        prisonguard.remove();
        mopvillager.remove();
        archerjohn.remove();
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
