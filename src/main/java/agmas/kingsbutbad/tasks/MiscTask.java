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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;


public class MiscTask extends BukkitRunnable {
    @Override
    public void run(){

        if (KingsButBad.king == null || !KingsButBad.king.isOnline()) {
            if (Bukkit.getServer().getOnlinePlayers().size() != 0) {
                Bukkit.broadcastMessage(CreateText.addColors("<red><b>>><b> NO <gradient:#FFFF52:#FFBA52><b>KING<b></gradient><b><red> FOUND! <#A52727>Choosing a random player..."));
                ArrayList<Player> allPlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());
                int random = new Random().nextInt(allPlayers.size());
                Player picked = allPlayers.get(random);
                RoleManager.showKingMessages(picked, ChatColor.DARK_GRAY + "You were randomly picked");
                KingsButBad.playerRoleHashMap.put(picked, Role.KING);
                KingsButBad.king = picked;
                RoleManager.givePlayerRole(KingsButBad.king);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p != picked) {
                        KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                        RoleManager.givePlayerRole(p);
                    }
                }
            }
        }
        if (KingsButBad.king != null) {
            if (KingsButBad.king.getInventory().getHelmet() == null) {
                    ArrayList<Player> allPlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());
                    if (allPlayers.size() > 1) {
                        Bukkit.broadcastMessage(CreateText.addColors("<red><b>>><b> THE <gradient:#FFFF52:#FFBA52><b>KING<b></gradient><b><red> HAS RESIGNED! <#A52727>Choosing a random player..."));
                        Player picked = KingsButBad.king;
                        while (picked == KingsButBad.king) {
                            int random = new Random().nextInt(allPlayers.size());
                            picked = allPlayers.get(random);
                        }
                        RoleManager.showKingMessages(picked, ChatColor.DARK_GRAY + "You were randomly picked");
                        KingsButBad.playerRoleHashMap.put(picked, Role.KING);
                        KingsButBad.king = picked;
                        RoleManager.givePlayerRole(KingsButBad.king);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p != picked) {
                                KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                                RoleManager.givePlayerRole(p);
                            }
                        }
                    } else {
                        KingsButBad.king.sendMessage(ChatColor.RED + ">> Not enough players to resign!");
                        KingsButBad.king.getInventory().remove(Material.GOLDEN_HELMET);
                        ItemStack crown = new ItemStack(Material.GOLDEN_HELMET);
                        ItemMeta crownmeta = crown.getItemMeta();
                        crownmeta.setUnbreakable(true);
                        crownmeta.setDisplayName(CreateText.addColors("<color:#ffff00><b>King's <gradient:#ff4046:#ffff00>Crown</b></color>"));
                        ArrayList<String> crownlore = new ArrayList<>();
                        crownlore.add(ChatColor.GRAY + "A crown worn by mighty kings while ruling their kingdom.");
                        crownlore.add(ChatColor.GRAY + "");
                        crownlore.add(CreateText.addColors("<color:#ff0400><i><b>Drop <gradient:#ffdd00:#ffff6e>this<color:#ff2f00> to <color:#910005>Resign.</color> </b></i></color>"));
                        crownmeta.setLore(crownlore);
                        crownmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
                        crownmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
                        crown.setItemMeta(crownmeta);
                        KingsButBad.king.getInventory().setHelmet(crown);
                    }
                }
            }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendActionBar(CreateText.addColors("<gray>Current king<gray>: <gradient:#FFFF52:#FFBA52><b>KING " + KingsButBad.king.getName().toUpperCase()));
            p.setWalkSpeed(0.14f);
            if (p.getGameMode().equals(GameMode.SURVIVAL))
                p.setGameMode(GameMode.ADVENTURE);
        }
    }
}