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
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;


public class MiscTask extends BukkitRunnable {

    public static HashMap<Player, Float> stamina = new HashMap<>();

    HashMap<Player, Boolean> regenstamina = new HashMap<>();
    ArrayList<Location> cells = new ArrayList<Location>();
    public static BossBar bossbar = Bukkit.createBossBar("??? TIME", BarColor.WHITE, BarStyle.SOLID);
    Integer timer1 = 0;
    Integer timer2 = 1000;

    @Override
    public void run(){
        if (Bukkit.getWorld("world").getTime() > 0 && Bukkit.getWorld("world").getTime() < 2000) {
            timer1 = 0;
            timer2 = 2500;
            bossbar.setColor(BarColor.RED);
            bossbar.setTitle("ROLL CALL");
        }
        if (Bukkit.getWorld("world").getTime() > 2000 && Bukkit.getWorld("world").getTime() < 4000) {
            timer1 = 2000;
            timer2 = 4000;
            bossbar.setColor(BarColor.WHITE);
            bossbar.setTitle("Breakfast");
        }
        if (Bukkit.getWorld("world").getTime() > 4000 && Bukkit.getWorld("world").getTime() < 7000) {
            timer1 = 4000;
            timer2 = 7000;
            bossbar.setColor(BarColor.WHITE);
            bossbar.setTitle("Free Time");
        }
        if (Bukkit.getWorld("world").getTime() > 7000 && Bukkit.getWorld("world").getTime() < 10000) {
            timer1 = 7000;
            timer2 = 10000;
            bossbar.setColor(BarColor.WHITE);
            bossbar.setTitle("Job Time");
        }
        if (Bukkit.getWorld("world").getTime() > 10000 && Bukkit.getWorld("world").getTime() < 13000) {
            timer1 = 10000;
            timer2 = 13000;
            bossbar.setColor(BarColor.WHITE);
            bossbar.setTitle("Lunch");
        }
        if (Bukkit.getWorld("world").getTime() > 13000 && Bukkit.getWorld("world").getTime() < 15000) {
            timer1 = 13000;
            timer2 = 15000;
            bossbar.setColor(BarColor.RED);
            bossbar.setTitle("EVENING ROLL CALL");
        }
        if (Bukkit.getWorld("world").getTime() > 15000 && Bukkit.getWorld("world").getTime() < 18000) {
            timer1 = 15000;
            timer2 = 18000;
            bossbar.setColor(BarColor.PINK);
            bossbar.setTitle("Cell Time");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                    if (!KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -136, -53, -6), new Location(Bukkit.getWorld("world"), -132, -57, 23))) {
                        p.sendTitle("", CreateText.addColors("<red><b>GET IN YOUR CELL, FILTH!"), 0, 20, 0);
                    }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 18000 && Bukkit.getWorld("world").getTime() < 24000) {
            timer1 = 18000;
            timer2 = 24000;
            bossbar.setColor(BarColor.RED);
            bossbar.setTitle("LIGHTS OUT");
            Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 1);
        }
        bossbar.setProgress(((float) Bukkit.getWorld("world").getTime() - (float) timer1) / ((float) timer2 - (float) timer1));
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.getPersistentDataContainer().has(KingsButBad.money)) {
                p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, 0.0);
            }
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
                        Bukkit.broadcastMessage(CreateText.addColors("<red><b>>><b> THE <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kinggender.toUpperCase() + "<b></gradient><b><red> HAS RESIGNED! <#A52727>Use /king to become the king.."));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (KingsButBad.playerRoleHashMap.get(p) != Role.PEASANT) {
                        KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                        RoleManager.givePlayerRole(p);
                    }
                }
                }
            }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISON_GUARD)) {
                if (!bossbar.getPlayers().contains(p)) {
                    p.sendTitle("", ChatColor.RED + "Stay in the prison!");
                    p.teleport(new Location(Bukkit.getWorld("world"), -137.5, -51, -8));
                }
            }
            if (KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -133, -57, -18), new Location(Bukkit.getWorld("world"), -159, -26, 33))) {
                if (!bossbar.getPlayers().contains(p)) {
                    p.sendTitle("", ChatColor.GOLD + "-= The Prison =-");
                    bossbar.addPlayer(p);
                    p.sendMessage(ChatColor.RED + "> You will only be able to see messages from people in the prison!");
                }
            }
            else {
                if (bossbar.getPlayers().contains(p)) {
                    p.sendTitle("", ChatColor.GREEN + "-= The Outside =-");
                    bossbar.removePlayer(p);
                    p.sendMessage(ChatColor.RED + "> You will only be able to see messages from people in the outside!");
                }
            }
            p.setLevel(0);

            if (p.hasPotionEffect(PotionEffectType.LUCK)) {
                stamina.put(p, 0.99f);
            }
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
                p.setFoodLevel(19);
                if (!KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
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
            }
            if (p.isSprinting()) {
                if (stamina.get(p) <= 0) {
                    p.setFoodLevel(6);
                    regenstamina.put(p, true);

                    p.sendTitle(ChatColor.RED + "", ChatColor.DARK_RED + "regenerating stamina..", 20, 20, 20);
                } else {

                    stamina.put(p, stamina.get(p) - 0.01f);
                }
            } else {
                if (stamina.get(p) < 0.99f) {
                    stamina.put(p, stamina.get(p) + 0.01f);
                }
            }
            String actiobarextras = "";

            Role.KING.tag = CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>"+ KingsButBad.kinggender.toUpperCase() + "<b></gradient>");
            Role.KING.uncompressedColors = "<gradient:#FFFF52:#FFBA52><b>"+ KingsButBad.kinggender.toUpperCase() + "<b></gradient>";
            DecimalFormat df = new DecimalFormat("#0.0");
            actiobarextras += ChatColor.GRAY + " | " + CreateText.addColors("<color:#26ff00><b>$</b><gradient:#26ff00:#61ffc0>" +df.format(p.getPersistentDataContainer().get(KingsButBad.money, PersistentDataType.DOUBLE)));
            if (p.getGameMode().equals(GameMode.SURVIVAL))
                p.setGameMode(GameMode.ADVENTURE);
            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                if (!p.hasCooldown(Material.TERRACOTTA)) {
                    if (!KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -167, -64, 40), new Location(Bukkit.getWorld("world"), -129, -33, -26))) {
                        KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                        p.removePotionEffect(PotionEffectType.WEAKNESS);
                        p.getPersistentDataContainer().remove(KingsButBad.wasinPrison);
                        Bukkit.broadcastMessage(CreateText.addColors("<red><b>>> " + p.getName() + " has escaped the prison!"));
                        p.sendTitle(ChatColor.RED + "!!! You're now a criminal !!!", ChatColor.GRAY + "You escaped");
                        KingsButBad.playerRoleHashMap.put(p, Role.CRIMINAl);
                        p.playSound(p, Sound.ENTITY_SILVERFISH_DEATH, 1, 0.5f);
                    }
                }
                p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(40, 255));
                p.setFoodLevel(6);
                stamina.put(p, 0.99f);
                String tooltip = "";
                if (p.getLocation().getPitch() < 30) {
                    tooltip = CreateText.addColors("<gray> | <red>Tip: Look down to go faster.");
                    p.setWalkSpeed(0.06f);
                } else {
                    p.setWalkSpeed(0.1f);
                }
                p.sendActionBar(CreateText.addColors("<gray>Sentence Left: <red><b>" + KingsButBad.prisonTimer.get(p) + "<gold> blocks to mine.") + tooltip);
                if (KingsButBad.prisonTimer.get(p) <= 0) {
                    KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                    RoleManager.givePlayerRole(p);
                    p.removePotionEffect(PotionEffectType.WEAKNESS);
                    p.getPersistentDataContainer().remove(KingsButBad.wasinPrison);
                    Bukkit.broadcastMessage(CreateText.addColors("<gold>>> " + p.getName() + " served their prison sentence."));
                }
            } else {
                if (KingsButBad.king != null) {
                    p.sendActionBar(CreateText.addColors("<gray>Current king<gray>: <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kinggender.toUpperCase() + " " + KingsButBad.king.getName().toUpperCase()) + actiobarextras);
                } else {
                    p.sendActionBar(CreateText.addColors("<gray>Current king<gray>: <gradient:#ff2f00:#fcff3d><b>NO KING! Use /king to claim!") + actiobarextras);
                }
            }
        }
    }
}