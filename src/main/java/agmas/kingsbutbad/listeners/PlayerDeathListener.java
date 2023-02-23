package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerDeathEvent event) {
        event.getDrops().clear();
        event.setDeathMessage(ChatColor.GRAY + event.getDeathMessage());
    }

}
