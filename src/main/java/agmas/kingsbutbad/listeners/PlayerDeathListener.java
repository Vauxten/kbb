package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerRespawnEvent event) {
        if (!KingsButBad.king.equals(event.getPlayer())) {
            RoleManager.givePlayerRole(event.getPlayer());
        } else {
            KingsButBad.king.setItemOnCursor(new ItemStack(Material.AIR));
            KingsButBad.king = null;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p) != Role.PEASANT) {
                    KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                    RoleManager.givePlayerRole(p);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerDeathEvent event) {
        event.getDrops().clear();

        event.setDeathMessage(ChatColor.GRAY + event.getDeathMessage());
        if (KingsButBad.playerRoleHashMap.get(event.getPlayer()).equals(Role.CRIMINAl)) {
            if (!event.getPlayer().getInventory().contains(Material.PAPER)) {
                Bukkit.broadcastMessage(CreateText.addColors("<red>>> <b>Criminal " + event.getPlayer().getName() + "<gold> </b>has been successfully captured!"));
                KingsButBad.prisonTimer.put(event.getPlayer(), 100);
                KingsButBad.playerRoleHashMap.put(event.getPlayer(), Role.PRISONER);
                event.setCancelled(true);
                event.getPlayer().getInventory().clear();
                event.getPlayer().getPersistentDataContainer().set(KingsButBad.wasinPrison, PersistentDataType.INTEGER, 1);
                RoleManager.givePlayerRole(event.getPlayer());
            } else {
                Bukkit.broadcastMessage(CreateText.addColors("<red>>> <b>Criminal " + event.getPlayer().getName() + "<gold> </b>used their get-out-of-jail-free card."));
            }
        }
    }

}
