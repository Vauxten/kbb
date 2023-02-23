package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

import static net.kyori.adventure.text.minimessage.MiniMessage.*;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(LegacyComponentSerializer.legacySection().serialize(miniMessage().deserialize("<#D49B63>" + event.getPlayer().getName() + " ran away somewhere else..")));
    }

    @EventHandler
    public void cancelDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(LegacyComponentSerializer.legacySection().serialize(miniMessage().deserialize("<#D49B63>" + event.getPlayer().getName() + " was shipped into the kingdom.")));
        KingsButBad.playerRoleHashMap.put(event.getPlayer(), Role.PEASANT);
        RoleManager.givePlayerRole(event.getPlayer());
    }
}
