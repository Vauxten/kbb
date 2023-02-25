package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.Role;
import net.minecraft.world.level.block.BlockPlant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.K;

public class PlayerBlockListeners implements Listener {
    @EventHandler
    public void onPlayerQuit(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.DEEPSLATE_COAL_ORE)) {
            event.setDropItems(false);
            event.setCancelled(true);
            KingsButBad.prisonTimer.put(event.getPlayer(), KingsButBad.prisonTimer.getOrDefault(event.getPlayer(), 0) - 1);
            event.getBlock().setType(Material.DEEPSLATE);
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                event.getBlock().setType(Material.DEEPSLATE_COAL_ORE);
            }, 20 * 4);
        }
        if (event.getBlock().getType().equals(Material.WHEAT_SEEDS) || event.getBlock().getType().equals(Material.WHEAT)) {
            if (event.getPlayer().hasCooldown(Material.WOODEN_HOE)) {
                event.setCancelled(true);
                return;
            }
            event.setDropItems(false);
            event.getPlayer().getInventory().addItem(new ItemStack(Material.WHEAT));
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                event.getBlock().setType(Material.WHEAT);
                BlockState seedstate = event.getBlock().getState();
                Ageable seed = (Ageable) event.getBlock().getBlockData();
                seed.setAge(7);
                seedstate.setBlockData(seed);
                seedstate.update();
            }, 20 * 4);
        }
    }

}
