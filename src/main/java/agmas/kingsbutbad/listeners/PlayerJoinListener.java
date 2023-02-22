package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import static net.kyori.adventure.text.minimessage.MiniMessage.*;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        event.setQuitMessage(LegacyComponentSerializer.legacySection().serialize(miniMessage().deserialize("<#D49B63>" + event.getPlayer().getName() + " ran away somewhere else..")));
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(LegacyComponentSerializer.legacySection().serialize(miniMessage().deserialize("<#D49B63>" + event.getPlayer().getName() + " was shipped into the kingdom.")));
        if (KingsButBad.king == null) {
            KingsButBad.playerRoleHashMap.put(event.getPlayer(), Role.KING);
            RoleManager.showKingMessages(event.getPlayer(), ChatColor.DARK_GRAY + "You were the first player to join");
            RoleManager.givePlayerRole(event.getPlayer());
        } else {
            KingsButBad.playerRoleHashMap.put(event.getPlayer(), Role.PEASANT);
            RoleManager.givePlayerRole(event.getPlayer());
        }
    }
}
