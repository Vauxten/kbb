package agmas.kingsbutbad.tasks;
//important stuff here

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.Role;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FailsafeTask extends BukkitRunnable {
    @Override
    public void run(){

        for (Player p : Bukkit.getOnlinePlayers()) {
            KingsButBad.playerRoleHashMap.putIfAbsent(p, Role.PEASANT);
            if (p.getGameMode().equals(GameMode.SURVIVAL))
                p.setGameMode(GameMode.ADVENTURE);
            if (p.getLocation().getY() <= -60 && p.getLocation().getBlock().getType().equals(Material.WATER)) {
                p.setNoDamageTicks(0);
                p.damage(1);
            }
        }
    }
}