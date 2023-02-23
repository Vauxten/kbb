package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

    @Override
    public void run(){

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
            if (KingsButBad.king != null) {
                p.sendActionBar(CreateText.addColors("<gray>Current king<gray>: <gradient:#FFFF52:#FFBA52><b>KING " + KingsButBad.king.getName().toUpperCase()));
            } else {
                p.sendActionBar(CreateText.addColors("<gray>Current king<gray>: <gradient:#ff2f00:#fcff3d><b>NO KING! Use /king to claim!"));
            }
            if (p.getGameMode().equals(GameMode.SURVIVAL))
                p.setGameMode(GameMode.ADVENTURE);
        }
    }
}