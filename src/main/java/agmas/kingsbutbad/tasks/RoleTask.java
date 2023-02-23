package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


// role related stuff here

public class RoleTask extends BukkitRunnable {
    @Override
    public void run(){

        for (Player p : Bukkit.getOnlinePlayers()) {

            KingsButBad.playerRoleHashMap.putIfAbsent(p, Role.PEASANT);
            if (KingsButBad.api.getPlayerAdapter(Player.class).getUser(p).getCachedData().getMetaData().getPrefix() != null) {
                p.setPlayerListName(CreateText.addColors("<dark_gray>[" + KingsButBad.api.getPlayerAdapter(Player.class).getUser(p).getCachedData().getMetaData().getPrefix() + "<dark_gray>] " + "[" + KingsButBad.playerRoleHashMap.get(p).uncompressedColors + "<dark_gray>] ") + KingsButBad.playerRoleHashMap.get(p).chatColor + p.getName());
            } else {
                p.setPlayerListName(CreateText.addColors("<dark_gray>[" + KingsButBad.playerRoleHashMap.get(p).uncompressedColors + "<dark_gray>] <white>") + KingsButBad.playerRoleHashMap.get(p).chatColor + p.getName());
            }
            p.setDisplayName(p.getPlayerListName());
            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.KING) && KingsButBad.king != p) {
                KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                RoleManager.givePlayerRole(p);
            }
        }
    }
}