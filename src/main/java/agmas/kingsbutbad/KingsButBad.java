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
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;

public final class KingsButBad extends JavaPlugin {

    public static Player king = null;
    public static Player king2 = null;
    public static HashMap<Player, Role> roles = new HashMap<>();
    public static NamespacedKey wasInPrison;
    public static NamespacedKey money;
    public static Boolean joesUnlocked = false;
    public static Boolean coalCompactor = false;
    public static HashMap<Player, Integer> prisonTimer = new HashMap<>();
    public static HashMap<Player, Integer> prisonQuota = new HashMap<>();
    public static HashMap<Player, Role> invitations = new HashMap<>();
    public static HashMap<Player, Location> datedLocations = new HashMap<>();
    public static LuckPerms api;
    public static Villager royalVillager;
    public static Villager sewerVillager;
    public static HashMap<Player, Player> bodyLink = new HashMap<>();
    public static Villager selfDefense;
    public static Boolean mineUnlocked = false;
    public static Villager farmerJoe;
    public static Villager minerGuard;
    public static Villager bertrude;
    public static Villager lunchLady;
    public static Villager royalTrader;
    public static Villager royalServant;
    public static Villager mopVillager;
    public static Villager archerJohn;
    public static Villager prisonGuard;
    public static Villager littleJoes;
    public static HashMap<Player, String> princeGender = new HashMap<>();
    public static Villager servant;
    public static Villager miner;
    public static String kingGender = "King";
    public static String kingGender2 = "King";
    public static int taxesCount = 25;
    public static int cooldown = 20 * 5;
    public static Player lastKing;
    public static Player lastKing2;
    public static HashMap<Player, Integer> currentZone = new HashMap<>();
    public static HashMap<Player, Integer> thirst = new HashMap<>();
    public static HashMap<Player, Boolean> soundWaves = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        api = LuckPermsProvider.get();

        wasInPrison = new NamespacedKey(this, "inPrison");
        money = new NamespacedKey(this, "money");

        registerEvents();
        registerCommands();
        runTasks();
        deleteVillagers();
        setupVillagers();
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new AsyncPlayerChatEventListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerInteractAtEntityListener(), this);
        pm.registerEvents(new PlayerBlockListeners(), this);
    }

    private void runTasks() {
        new MiscTask().runTaskTimer(this, 0, 1);
        new FailsafeTask().runTaskTimer(this, 0, 1);
        new RoleTask().runTaskTimer(this, 0, 1);
        new AFKTask().runTaskTimer(this, 3000, 6000);
        new LocationTask().runTaskTimer(this, 0, 6000);
    }

    private void registerCommands() {
        getCommand("king").setExecutor(new KingCommand());
        getCommand("accept").setExecutor(new AcceptCommand());
        getCommand("setmoney").setExecutor(new SetMoneyCommand());
        getCommand("resign").setExecutor(new ResignCommand());
        getCommand("discord").setExecutor(new DiscordCommand());
        getCommand("soundwaves").setExecutor(new SoundWavesCommand());
        getCommand("resetVillagers").setExecutor(new ResetVillagersCommand());
        getCommand("pay").setExecutor(new PayCommand());
    }

    private void deleteVillagers() {
        for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities()) {
            if (le.getType().equals(EntityType.VILLAGER)) {
                le.remove();
            }
        }
    }

    private static Villager createVillager(Location location, String name, Villager.Profession profession) {
        Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(location, EntityType.VILLAGER);

        villager.setCustomName(CreateText.addColors(name));
        villager.setCustomNameVisible(true);
        villager.setInvulnerable(true);
        villager.setPersistent(true);
        villager.setAI(false);

        return villager;
    }

    private static Villager createVillager(Location location, String name) {
        return createVillager(location, name, Villager.Profession.NONE);
    }

    public static void setupVillagers() {
        World world = Bukkit.getWorld("world");

        royalVillager = createVillager(
                new Location(world, -63.5, -57, 23.5, 42, 0),
                "<gradient:#FFFF52:#FFBA52>Royal Villager"
        );

        farmerJoe = createVillager(
                new Location(world, -100.5, -57, 2.5, 140, 0),
                "<blue>Farmer Joe"
        );

        bertrude = createVillager(
                new Location(world, -112.5, -57, -5.5, -180, 0),
                "bertrude"
        );

        sewerVillager = createVillager(
                new Location(world, -78.5, -62, -28.5, -90, 0),
                "<gray>Shady Slim",
                Villager.Profession.TOOLSMITH
        );

        selfDefense = createVillager(
                new Location(world, -122.5, -57, -2.5, 180, 0),
                "<red>Defender Jim",
                Villager.Profession.LEATHERWORKER
        );

        minerGuard = createVillager(
                new Location(world, -144.5, -57, 12.5, 45, 0),
                "<gray>Miner"
        );

        lunchLady = createVillager(
                new Location(world, -149.5, -57, 2.5, -90, 0),
                "<gold>Lunch Lady"
        );

        royalTrader = createVillager(
                new Location(world, -128.5, -57, -6.5, 180, 0),
                "<yellow>Royal Trader"
        );

        mopVillager = createVillager(
                new Location(world, -103.21, -57.00, -17.24, 60, 0),
                "<yellow>Janitor"
        );

        archerJohn = createVillager(
                new Location(Bukkit.getWorld("world"), -140.30, -59.0, -39.59, -90, 0),
                "<green>archer johnm"
        );

        prisonGuard = createVillager(
                new Location(world, -136.5, -57.0, -12, -90, 0),
                "<blue>Prison Guard"
        );

        royalServant = createVillager(
                new Location(world, -67.5, -57.0, 23.5, -45, 0),
                "<gold>Royal Servant"
        );

        littleJoes = createVillager(
                new Location(world, -113.5, -56.0, -1.5, -180, 0),
                "<blue>Little Joes"
        );

        miner = createVillager(
                new Location(world, -202.5, -39.0, -244.5, 0, 0),
                "<gold>Miner Joseph"
        );

        servant = createVillager(
                new Location(world, -67.5, -57.0, 2.5, 0, 0),
                "<gray>Servant Application"
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        royalVillager.remove();
        sewerVillager.remove();
        selfDefense.remove();
        bertrude.remove();
        miner.remove();
        littleJoes.remove();
        royalServant.remove();
        royalTrader.remove();
        minerGuard.remove();
        prisonGuard.remove();
        mopVillager.remove();
        archerJohn.remove();
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

        if (player.getLocation().getX() > dim[1] || player.getLocation().getX() < dim[0])
            return false;

        dim[0] = loc1.getZ();
        dim[1] = loc2.getZ();
        Arrays.sort(dim);

        if (player.getLocation().getZ() > dim[1] || player.getLocation().getZ() < dim[0])
            return false;

        return true;
    }

}
