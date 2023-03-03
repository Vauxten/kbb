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
import org.bukkit.enchantments.Enchantment;
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

import java.util.Random;

public class PlayerBlockListeners implements Listener {

    @EventHandler
    public void onPlayerQuit(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.DEEPSLATE_COAL_ORE)) {
            event.setDropItems(false);
            event.setCancelled(true);
            KingsButBad.prisonQuota.put(event.getPlayer(), KingsButBad.prisonQuota.get(event.getPlayer()) - 1);
            event.getBlock().setType(Material.DEEPSLATE);
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                event.getBlock().setType(Material.DEEPSLATE_COAL_ORE);
                if (KingsButBad.coalCompactor) {
                    KingsButBad.king.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, KingsButBad.king.getPersistentDataContainer().get(KingsButBad.money, PersistentDataType.DOUBLE) + 5);
                    KingsButBad.king.sendMessage(ChatColor.GREEN + "+5$ Prisoner mined a block");
                    KingsButBad.king2.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, KingsButBad.king2.getPersistentDataContainer().get(KingsButBad.money, PersistentDataType.DOUBLE) + 5);
                    KingsButBad.king2.sendMessage(ChatColor.GREEN + "+5$ Prisoner mined a block");
                }
            }, 20 * 4);
        }
        if (event.getBlock().getType().equals(Material.BROWN_CONCRETE_POWDER)) {
            event.setDropItems(false);
            event.setCancelled(true);
            if (event.getPlayer().hasCooldown(Material.BONE)) {
                return;
            }
            event.getBlock().setType(Material.BEDROCK);
            event.getPlayer().getInventory().addItem(new ItemStack(Material.BROWN_DYE));
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                event.getBlock().setType(Material.BROWN_CONCRETE_POWDER);
            }, 20 * 4);
        }
        if (event.getBlock().getType().equals(Material.COAL_ORE)) {
            event.setDropItems(false);
            event.setCancelled(true);
            if (event.getPlayer().hasCooldown(Material.STONE_PICKAXE)) {
                return;
            }
            event.getBlock().setType(Material.CHISELED_STONE_BRICKS);
            event.getPlayer().getInventory().addItem(new ItemStack(Material.COAL_ORE));
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                event.getBlock().setType(Material.COAL_ORE);
            }, 20 * 4);
        }
        if (event.getBlock().getType().equals(Material.WHEAT_SEEDS) || event.getBlock().getType().equals(Material.WHEAT)) {
            if (event.getPlayer().hasCooldown(Material.WOODEN_HOE)) {
                event.setCancelled(true);
                return;
            }
            event.setDropItems(false);
            event.getPlayer().getInventory().addItem(new ItemStack(Material.WHEAT));
            if (event.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
                event.getPlayer().getInventory().addItem(new ItemStack(Material.WHEAT, new Random().nextInt(0, 5)));
            }
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
