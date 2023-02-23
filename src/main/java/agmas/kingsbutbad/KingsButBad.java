package agmas.kingsbutbad;

import agmas.kingsbutbad.listeners.AsyncPlayerChatEventListener;
import agmas.kingsbutbad.listeners.PlayerJoinListener;
import agmas.kingsbutbad.tasks.MiscTask;
import agmas.kingsbutbad.tasks.RoleTask;
import agmas.kingsbutbad.utils.Role;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class KingsButBad extends JavaPlugin {

    public static Player king = null;
    public static HashMap<Player, Role> playerRoleHashMap = new HashMap<>();
    public static LuckPerms api;

    @Override
    public void onEnable() {
        // Plugin startup logic
        api = LuckPermsProvider.get();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new AsyncPlayerChatEventListener(), this);
        MiscTask task = new MiscTask();
        task.runTaskTimer(this, 0, 1);
        RoleTask task2 = new RoleTask();
        task2.runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
