package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.K;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;


public class MiscTask extends BukkitRunnable {

    public static HashMap<Player, Float> stamina = new HashMap<>();

    HashMap<Player, Boolean> regenstamina = new HashMap<>();
    ArrayList<Location> cells = new ArrayList<Location>();

    @Override
    public void run(){
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!KingsButBad.playerRoleHashMap.containsKey(p)) {
                KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                RoleManager.givePlayerRole(p);
            }
        }
        cells.clear();
        cells.add(new Location(Bukkit.getWorld("world"), -136, -57, 20));
        cells.add(new Location(Bukkit.getWorld("world"), -136, -57, 14));
        cells.add(new Location(Bukkit.getWorld("world"), -136, -57, 8));
        cells.add(new Location(Bukkit.getWorld("world"), -136, -57, 2));
        cells.add(new Location(Bukkit.getWorld("world"), -136, -57, -4));
        if (Bukkit.getWorld("world").getTime() <= 18000) {
            Bukkit.getWorld("world").getBlockAt(-138, -51, -16).setType(Material.REDSTONE_BLOCK);
            for (Location l : cells) {
                BlockState state = l.getBlock().getState();
                Door openable = (Door) state.getBlockData();
                openable.setOpen(true);
                state.setBlockData(openable);
                state.update();
            }
        } else {
            Bukkit.getWorld("world").getBlockAt(-138, -51, -16).setType(Material.AIR);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                    if (!KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -136, -53, -6), new Location(Bukkit.getWorld("world"), -132, -57, 23))) {
                        p.sendMessage(CreateText.addColors("<red><b>>> GET IN YOUR CELL, FILTH!"));
                        Location randomcell = cells.get(new Random().nextInt(0, cells.size()));
                        randomcell.setX(-132);
                        p.teleport(randomcell);
                    }
                }
            }
            for (Location l : cells) {
                BlockState state = l.getBlock().getState();
                Door openable = (Door) state.getBlockData();
                openable.setOpen(false);
                state.setBlockData(openable);
                state.update();
            }
        }
        if (KingsButBad.king == null || !KingsButBad.king.isOnline() || KingsButBad.king.isDead()) {
            KingsButBad.king = null;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (KingsButBad.playerRoleHashMap.get(p) != Role.PEASANT) {
                        KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                        RoleManager.givePlayerRole(p);
                    }
                }
        }
        if (KingsButBad.king != null) {
            if (KingsButBad.king.getInventory().getHelmet() == null) {
                KingsButBad.king.setItemOnCursor(new ItemStack(Material.AIR));
                KingsButBad.king = null;
                        Bukkit.broadcastMessage(CreateText.addColors("<red><b>>><b> THE <gradient:#FFFF52:#FFBA52><b>KING<b></gradient><b><red> HAS RESIGNED! <#A52727>Use /king to become the king.."));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (KingsButBad.playerRoleHashMap.get(p) != Role.PEASANT) {
                        KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                        RoleManager.givePlayerRole(p);
                    }
                }
                }
            }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setLevel(0);

            if (!stamina.containsKey(p)) {
                stamina.put(p, 0.99f);
            }
            if (!regenstamina.containsKey(p)) {
                regenstamina.put(p, false);
            }
            if (stamina.get(p) <= 0f) {
                stamina.put(p, 0f);

            }
            p.setExp(stamina.get(p));
            p.setMaxHealth(20);
            if (stamina.get(p) >= 0.99) {
                regenstamina.put(p, false);
            }
            if (regenstamina.get(p)) {
                p.setFoodLevel(6);
                p.setWalkSpeed(0.09f);
                p.setFreezeTicks(30);
            } else {
                p.setFreezeTicks(0);
                p.setFoodLevel(20);
                if (KingsButBad.king != null) {
                    if (!KingsButBad.king.equals(p)) {
                        p.setWalkSpeed(0.16f);
                    } else {
                        p.setWalkSpeed(0.2f);
                    }
                } else {
                    p.setWalkSpeed(0.16f);
                }
            }
            if (p.isSprinting()) {
                if (stamina.get(p) <= 0) {
                    p.setFoodLevel(6);
                    regenstamina.put(p, true);

                    p.sendTitle(ChatColor.RED + "", ChatColor.DARK_RED + "regenerating stamina..", 20, 20, 20);
                } else {

                    stamina.put(p, stamina.get(p) - 0.015f);
                }
            } else {
                if (stamina.get(p) < 0.99f) {
                    stamina.put(p, stamina.get(p) + 0.01f);
                }
            }
            String actiobarextras = "";

            if (p.getGameMode().equals(GameMode.SURVIVAL))
                p.setGameMode(GameMode.ADVENTURE);
            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(99999, 255));
                p.setFoodLevel(8);
                KingsButBad.prisonTimer.put(p, KingsButBad.prisonTimer.get(p) - 1);
                p.sendActionBar(CreateText.addColors("<gray>Sentence Left: <red><b>" + (KingsButBad.prisonTimer.get(p) / 20) + "<gold> seconds left."));
                if (KingsButBad.prisonTimer.get(p) <= 0) {
                    KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                    RoleManager.givePlayerRole(p);
                    p.getPersistentDataContainer().set(KingsButBad.wasinPrison, PersistentDataType.INTEGER, 0);
                    Bukkit.broadcastMessage(CreateText.addColors("<gold>>> " + p.getName() + " served their prison sentence."));
                }
            } else {
                if (KingsButBad.king != null) {
                    p.sendActionBar(CreateText.addColors("<gray>Current king<gray>: <gradient:#FFFF52:#FFBA52><b>KING " + KingsButBad.king.getName().toUpperCase()) + actiobarextras);
                } else {
                    p.sendActionBar(CreateText.addColors("<gray>Current king<gray>: <gradient:#ff2f00:#fcff3d><b>NO KING! Use /king to claim!") + actiobarextras);
                }
            }
        }
    }
}