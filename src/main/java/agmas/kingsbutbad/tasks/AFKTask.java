package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.KingsButBad;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class AFKTask extends BukkitRunnable {
    @Override
    public void run() {
        List<Player> authorities = new ArrayList<>();
        authorities.add(KingsButBad.king);
        authorities.add(KingsButBad.king2);

        for (Player player : authorities) {
            if (player == null) continue;

            Location datedLocation = KingsButBad.datedLocations.get(player);

            System.out.println("Dated location " + datedLocation);

            if (datedLocation == null)
                continue;

            if (player
                    .getLocation()
                    .clone()
                    .setDirection(new Vector(0, 0, 0)).equals(datedLocation)) {
                KingsButBad.king.setHealth(0);
            }
        }
    }
}
