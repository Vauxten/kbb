package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
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
import java.util.Objects;
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

        KingsButBad.cooldown -= 1;

        if (KingsButBad.joesunlocked) {
            KingsButBad.littlejoes.teleport(new Location(Bukkit.getWorld("world"), -113.5, -56.0, -1.5, -180, 0));
        } else {
            KingsButBad.littlejoes.teleport(new Location(Bukkit.getWorld("world"), -105.5, -63.0, -77, 0, 0));
        }
        if (Bukkit.getWorld("world").getTime() > 0 && Bukkit.getWorld("world").getTime() < 2000) {
            timer1 = 0;
            timer2 = 2500;
            bossbar.setColor(BarColor.RED);
            bossbar.setTitle("ROLL CALL");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                    WorldBorder rollborder = Bukkit.createWorldBorder();
                    rollborder.setCenter(new Location(Bukkit.getWorld("world"), -140, -57, 15));
                    rollborder.setSize(3);
                    rollborder.setDamageAmount(0.4);
                    rollborder.setDamageBuffer(0);
                    if (KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -139, -57, 16), new Location(Bukkit.getWorld("world"), -142, -57, 13))) {

                        if (!rollborder.isInside(p.getLocation())) {
                            p.damage(1);
                        }
                        p.setWorldBorder(rollborder);
                    } else {
                        if (!Objects.equals(p.getWorldBorder(), rollborder)) {
                            p.setWorldBorder(null);
                        }
                    }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 2000 && Bukkit.getWorld("world").getTime() < 4000) {
            timer1 = 2000;
            timer2 = 4000;
            bossbar.setColor(BarColor.WHITE);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                    WorldBorder rollborder = Bukkit.createWorldBorder();
                    rollborder.setCenter(new Location(Bukkit.getWorld("world"), -153, -58, 3));
                    rollborder.setSize(18);
                    rollborder.setDamageAmount(0.4);
                    rollborder.setDamageBuffer(0);
                    if (KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -144, -58, 5), new Location(Bukkit.getWorld("world"), -155, -53, -10))) {
                        if (!rollborder.isInside(p.getLocation())) {
                            p.damage(1);
                        }
                        p.setWorldBorder(rollborder);
                    } else {
                        if (!Objects.equals(p.getWorldBorder(), rollborder)) {
                            p.setWorldBorder(null);
                        }
                    }
                }
            }
            bossbar.setTitle("Breakfast");
        }
        if (Bukkit.getWorld("world").getTime() > 4000 && Bukkit.getWorld("world").getTime() < 7000) {
            timer1 = 4000;
            timer2 = 7000;
            bossbar.setColor(BarColor.WHITE);
            bossbar.setTitle("Free Time");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                        if (p.getWorldBorder() != null) {
                            p.setWorldBorder(null);
                        }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() >= 7000 && Bukkit.getWorld("world").getTime() <= 7005) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                    KingsButBad.prisonQuota.put(p, 30);
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() == 10000) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.prisonQuota.get(p) > 0) {
                    p.sendTitle(ChatColor.RED + "MISSED QUOTA.", ChatColor.DARK_RED + "+80s to prison time.");
                    KingsButBad.prisonTimer.put(p, KingsButBad.prisonTimer.get(p) + 80);
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 7000 && Bukkit.getWorld("world").getTime() < 10000) {
            timer1 = 7000;
            timer2 = 10000;
            bossbar.setColor(BarColor.WHITE);
            bossbar.setTitle("Job Time");
            for (Player p : Bukkit.getOnlinePlayers()) {
                WorldBorder rollborder = Bukkit.createWorldBorder();
                rollborder.setCenter(new Location(Bukkit.getWorld("world"), -150, -49, 13));
                rollborder.setSize(15);
                rollborder.setDamageAmount(0.4);
                rollborder.setDamageBuffer(0);

                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                    if (KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -142, -50, 6), new Location(Bukkit.getWorld("world"), -157, -58, 20))) {
                        if (!rollborder.isInside(p.getLocation())) {
                            p.damage(1);
                        }
                        p.setWorldBorder(rollborder);
                    } else {
                        if (!Objects.equals(p.getWorldBorder(), rollborder)) {
                            p.setWorldBorder(null);
                        }
                    }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 10000 && Bukkit.getWorld("world").getTime() < 13000) {
            timer1 = 10000;
            timer2 = 13000;
            bossbar.setColor(BarColor.WHITE);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                    WorldBorder rollborder = Bukkit.createWorldBorder();
                    rollborder.setCenter(new Location(Bukkit.getWorld("world"), -153, -58, 3));
                    rollborder.setSize(18);
                    rollborder.setDamageAmount(0.4);
                    rollborder.setDamageBuffer(0);

                    if (KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -144, -58, 5), new Location(Bukkit.getWorld("world"), -155, -53, -10))) {
                        if (!rollborder.isInside(p.getLocation())) {
                            p.damage(1);
                        }
                        p.setWorldBorder(rollborder);
                    } else {
                        if (!Objects.equals(p.getWorldBorder(), rollborder)) {
                            p.setWorldBorder(null);
                        }
                    }
                }
            }
            bossbar.setTitle("Lunch");
        }
        if (Bukkit.getWorld("world").getTime() > 13000 && Bukkit.getWorld("world").getTime() < 15000) {
            timer1 = 13000;
            timer2 = 15000;
            bossbar.setColor(BarColor.RED);
            bossbar.setTitle("EVENING ROLL CALL");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                    WorldBorder rollborder = Bukkit.createWorldBorder();
                    rollborder.setCenter(new Location(Bukkit.getWorld("world"), -140, -57, 15));
                    rollborder.setSize(3);
                    rollborder.setDamageAmount(0.4);
                    rollborder.setDamageBuffer(0);

                    if (KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -139, -57, 16), new Location(Bukkit.getWorld("world"), -142, -57, 13))) {
                        if (!rollborder.isInside(p.getLocation())) {
                            p.damage(1);
                        }
                        p.setWorldBorder(rollborder);
                    }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 15000 && Bukkit.getWorld("world").getTime() < 18000) {
            timer1 = 15000;
            timer2 = 18000;
            bossbar.setColor(BarColor.PINK);
            bossbar.setTitle("Cell Time");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                    if (p.getWorldBorder() != null) {
                        p.setWorldBorder(null);
                    }
                }
            }
            Integer prisonersnotincell = 0;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                    if (!KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -136, -53, -6), new Location(Bukkit.getWorld("world"), -132, -57, 23))) {
                        p.sendTitle("", CreateText.addColors("<red><b>GET IN YOUR CELL, FILTH!"), 0, 20, 0);
                        prisonersnotincell++;
                    }
                }
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISON_GUARD)) {
                    if (prisonersnotincell != 0) {
                        p.sendTitle("", CreateText.addColors("<red><b>" + prisonersnotincell + " PRISONERS ARE NOT IN THEIR CELLS!"), 0, 20, 0);
                        prisonersnotincell++;
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
        } else {
            Bukkit.getWorld("world").getBlockAt(-138, -51, -16).setType(Material.AIR);
        }
        if (KingsButBad.king == null || !KingsButBad.king.isOnline() || KingsButBad.king.isDead()) {
            KingsButBad.king = null;
            KingsButBad.king2 = null;
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
                KingsButBad.cooldown = 20 * 5;
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
            for (Entity e : p.getPassengers()) {
                LivingEntity le = (LivingEntity) e;
                le.setNoDamageTicks(3);
            }
            if (p.isInsideVehicle()) {
                if (p.getVehicle().isSneaking()) {
                    p.leaveVehicle();
                }
            }

            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.BODYGUARD)) {
                WorldBorder kingborder = Bukkit.createWorldBorder();
                kingborder.setCenter(KingsButBad.bodylink.get(p).getLocation());
                kingborder.setSize(8);
                kingborder.setDamageAmount(0.4);
                kingborder.setDamageBuffer(0);
                if (!kingborder.isInside(p.getLocation())) {
                    p.damage(1);
                }
                p.setWorldBorder(kingborder);
            }
            if (!KingsButBad.playerRoleHashMap.get(p).equals(Role.BODYGUARD) && !KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                if (p.getWorldBorder() != null) {
                    p.setWorldBorder(null);
                }
            }

            KingsButBad.currentzone.putIfAbsent(p, 0);

            if (KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -133, -57, -18), new Location(Bukkit.getWorld("world"), -159, -26, 33))) {
                    if (KingsButBad.currentzone.get(p) != 1) {
                    p.sendTitle("", ChatColor.GOLD + "-= The Prison =-");
                    bossbar.addPlayer(p);
                    KingsButBad.currentzone.put(p, 1);
                }
            }
            else if (KingsButBad.isInside(p, new Location(p.getWorld(), -86, -63, -1), new Location(p.getWorld(), -45, -33, 39))) {
                if (KingsButBad.currentzone.get(p) != 2) {
                    p.sendTitle("", ChatColor.GRAY + "-= The Castle =-");
                    bossbar.removePlayer(p);
                    KingsButBad.currentzone.put(p, 2);
                }
            }
            else {
                if (KingsButBad.currentzone.get(p) != 0) {
                    p.sendTitle("", ChatColor.GREEN + "-= The Outside =-");
                    bossbar.removePlayer(p);
                    KingsButBad.currentzone.put(p, 0);
                }
            }

            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISON_GUARD)) {
                if (!bossbar.getPlayers().contains(p) && !bossbar.getTitle().equals("LIGHTS OUT")) {
                    p.sendTitle("", ChatColor.RED + "Stay in the prison!");
                    for (Entity pe : p.getPassengers()) {
                        pe.leaveVehicle();
                    }
                    p.teleport(new Location(Bukkit.getWorld("world"), -137.5, -51, -8));
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
                        if (!RoleManager.isKingAtAll(p)) {
                            if (KingsButBad.playerRoleHashMap.get(p) != Role.BODYGUARD) {
                                p.setWalkSpeed(0.16f);
                            } else {
                                p.setWalkSpeed(0.2f);
                            }
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

            if (KingsButBad.isInside(p, new Location(Bukkit.getWorld("world"), -98, -58, -38), new Location(Bukkit.getWorld("world"), -101, -59, -41))) {
                if (KingsButBad.mineunlocked) {
                    p.teleport(new Location(Bukkit.getWorld("world"), -203.5, -39, -236.5));
                } else {
                    p.teleport(new Location(Bukkit.getWorld("world"), -98.5, -57, -34));
                    p.sendMessage(ChatColor.RED + "That's not unlocked!");
                }
            }

            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.KNIGHT)) {
                Boolean hasHorseSpawned = false;
                for (Entity e : Bukkit.getWorld("world").getEntities()) {
                    if (e.getCustomName() != null) {
                        if (e.getCustomName().equals(p.getName() + "'s horse"))
                            hasHorseSpawned = true;
                    }
                }
                if (!hasHorseSpawned) {
                    if (!p.getInventory().contains(Material.CLAY_BALL)) {
                        ItemStack diamondchest = new ItemStack(Material.CLAY_BALL);
                        ItemMeta diamondchestmeta = diamondchest.getItemMeta();
                        diamondchestmeta.setDisplayName(CreateText.addColors("<gray>Spawn <gold>Horse"));
                        diamondchest.setItemMeta(diamondchestmeta);
                        p.getInventory().addItem(diamondchest);
                    }
                } else {
                    p.getInventory().remove(Material.CLAY_BALL);
                }
            }

            String actiobarextras = "";

            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.CRIMINAl)) {
                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                p.addPotionEffect(PotionEffectType.GLOWING.createEffect(40, 0));
            }


            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.KING)) {
                p.addPotionEffect(PotionEffectType.GLOWING.createEffect(40, 0));
            }


            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISON_GUARD)) {
                if (bossbar.getPlayers().contains(p) && bossbar.getTitle().equals("LIGHTS OUT")) {
                    actiobarextras += ChatColor.GRAY + " | " + CreateText.addColors("<red>It's lights out! <blue>You can leave the <gold>prison.");
                }
            }

            if (KingsButBad.king != null) {
                KingsButBad.lastking = KingsButBad.king;
            }

            if (KingsButBad.king2 != null) {
                KingsButBad.lastking2 = KingsButBad.king2;
            }

            Role.KING.tag = CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>"+ KingsButBad.kinggender.toUpperCase() + "<b></gradient>");
            Role.KING.uncompressedColors = "<gradient:#FFFF52:#FFBA52><b>"+ KingsButBad.kinggender.toUpperCase() + "<b></gradient>";
            DecimalFormat df = new DecimalFormat("#0.0");

            if (RoleManager.isKingAtAll(p)) {
                if (KingsButBad.isInside(p, new Location(p.getWorld(), -65, -48, 27),  new Location(p.getWorld(), -68, -57, 24))) {
                    actiobarextras += ChatColor.GRAY + " | " + CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>+0.25$ for sitting on the throne.");
                    p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().get(KingsButBad.money, PersistentDataType.DOUBLE)  + 0.25);
                }
            }

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
                if (!DisguiseAPI.isDisguised(p)) {
                    PlayerDisguise prisoner = new PlayerDisguise("leonrobiclone");
                    prisoner.setName("Prisoner " + new Random().nextInt(1000, 9999));
                    DisguiseAPI.disguiseEntity(p, prisoner);
                    DisguiseAPI.setActionBarShown(p, false);
                }
                p.setFoodLevel(6);
                stamina.put(p, 0.99f);
                KingsButBad.prisonQuota.putIfAbsent(p, 0);
                KingsButBad.prisonTimer.put(p, KingsButBad.prisonTimer.getOrDefault(p, 0) - 1);
                String tooltip = "";
                if (KingsButBad.prisonQuota.get(p) > 0) {
                    tooltip += CreateText.addColors("<gray> | <gold>MINE <red><b>" + KingsButBad.prisonQuota.get(p) + "<gold></b> BLOCKS! <gray>or +80s");
                }
                if (p.getLocation().getPitch() < 30) {
                    tooltip += CreateText.addColors("<gray> | <red>Tip: Look down to go faster.");
                    p.setWalkSpeed(0.02f);
                } else {
                    p.setWalkSpeed(0.1f);
                }
                p.sendActionBar(CreateText.addColors("<gray>Sentence Left: <red><b>" + (KingsButBad.prisonTimer.get(p) / 20) + "<gold> seconds") + tooltip);
                if (KingsButBad.prisonTimer.get(p) <= 0) {
                    KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                    RoleManager.givePlayerRole(p);
                    p.removePotionEffect(PotionEffectType.WEAKNESS);
                    p.getPersistentDataContainer().remove(KingsButBad.wasinPrison);
                    Bukkit.broadcastMessage(CreateText.addColors("<gold>>> " + p.getName() + " served their prison sentence."));
                }
            } else {
                if (DisguiseAPI.isDisguised(p)) {
                    DisguiseAPI.undisguiseToAll(p);
                }

                if (KingsButBad.king != null) {
                    if (KingsButBad.king2 == null) {
                        p.sendActionBar(CreateText.addColors("<gray>Current king<gray>: <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kinggender.toUpperCase() + " " + KingsButBad.king.getName().toUpperCase()) + actiobarextras);
                    } else {
                        p.sendActionBar(CreateText.addColors("<gray>Current king<gray>: <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kinggender.toUpperCase() + " " + KingsButBad.king.getName().toUpperCase() + "<dark_gray></b> &</gray>" + "<gradient:#FFFF52:#FFBA52><b> " + KingsButBad.kinggender2.toUpperCase() + " " + KingsButBad.king2.getName().toUpperCase()) + actiobarextras);
                    }
                } else {
                    String iscool = "<gradient:#ff2f00:#fcff3d><b>NO KING! Use /king to claim!";
                    if (KingsButBad.cooldown > 0) {
                        iscool = "<gradient:#ff2f00:#fcff3d><b>On Cooldown... <gray>[" + (KingsButBad.cooldown / 20) + "]";
                    }
                    p.sendActionBar(CreateText.addColors("<gray>Current king<gray>: " + iscool) + actiobarextras);
                }
            }
        }
    }
}