package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.Role;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.units.qual.K;

public class PlayerInteractAtEntityListener implements Listener {
    @EventHandler
    public void onPlayerQuit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player d) {
            if (event.getDamager() instanceof Player p) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PEASANT)) {
                    if (KingsButBad.playerRoleHashMap.get(d).isPowerful) {
                        p.sendTitle(ChatColor.RED + "!!! You're now a criminal !!!", ChatColor.GRAY + "You hit someone of authority.");
                        KingsButBad.playerRoleHashMap.put(p, Role.CRIMINAl);
                        p.playSound(p, Sound.ENTITY_SILVERFISH_DEATH, 1, 0.5f);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerInteractEvent event) {
        if (event.getClickedBlock().getType().equals(Material.IRON_DOOR)) {
            BlockState state = event.getClickedBlock().getState();
            Door openable = (Door) state.getBlockData();

            if (event.getItem() != null) {
                if (event.getItem().getItemMeta() != null) {
                    if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Keycard")) {
                        event.setCancelled(true);
                        if (!openable.isOpen()) {
                            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
                            openable.setOpen(true);
                        } else {
                            openable.setOpen(false);
                            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);

                        }
                        state.setBlockData(openable);
                        state.update();
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType().equals(EntityType.VILLAGER)) {
            if (event.getRightClicked().equals(KingsButBad.royalvillager))
                event.getPlayer().performCommand("king help");
            event.setCancelled(true);
        }
    }

}
