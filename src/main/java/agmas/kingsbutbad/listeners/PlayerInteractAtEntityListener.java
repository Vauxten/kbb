package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.checkerframework.checker.units.qual.K;

public class PlayerInteractAtEntityListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType().equals(EntityType.VILLAGER)) {
            if (event.getRightClicked().equals(KingsButBad.royalvillager))
                event.getPlayer().performCommand("king help");
            event.setCancelled(true);
        }
    }

}
