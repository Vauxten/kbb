package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerDeathEvent event) {
        event.getDrops().clear();
        event.setDeathMessage(ChatColor.GRAY + event.getDeathMessage());
        if (KingsButBad.playerRoleHashMap.get(event.getPlayer()).equals(Role.CRIMINAl)) {
            Bukkit.broadcastMessage(CreateText.addColors("<red>>> <b>Criminal "  + event.getPlayer().getName() + "<gold> </b>has been successfully captured!"));
            KingsButBad.prisonTimer.put(event.getPlayer(), (20 * 60) * 5);
            KingsButBad.playerRoleHashMap.put(event.getPlayer(), Role.PRISONER);
            event.setCancelled(true);
            event.getPlayer().getInventory().clear();
            event.getPlayer().getPersistentDataContainer().set(KingsButBad.wasinPrison, PersistentDataType.INTEGER, 1);
            RoleManager.givePlayerRole(event.getPlayer());
        }
    }

}
