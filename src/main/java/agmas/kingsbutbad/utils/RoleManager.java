package agmas.kingsbutbad.utils;

import agmas.kingsbutbad.KingsButBad;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;


public class RoleManager {

    public static Boolean isKingAtAll(Player p) {
        return KingsButBad.king == p || KingsButBad.king2 == p;
    }

    public static String getKingGender(Player p) {
        if (KingsButBad.king == p)
            return KingsButBad.kinggender;
        if (KingsButBad.king2 == p)
            return KingsButBad.kinggender2;
        return "ERROR";
    }

    public static void setKingGender(Player p, String toset) {
        if (KingsButBad.king == p) {
            switch (toset) {
                case "male":
                    KingsButBad.kinggender = "King";
                    break;
                case "female":
                    KingsButBad.kinggender = "Queen";
                    break;
                case "sussy":
                    KingsButBad.kinggender = "Among Us Impostor";
                    break;
                default:
                    KingsButBad.kinggender = "Monarch";
            }
        }
        if (KingsButBad.king2 == p) {
            switch (toset) {
                case "male":
                    KingsButBad.kinggender2 = "King";
                    break;
                case "female":
                    KingsButBad.kinggender2 = "Queen";
                    break;
                case "sussy":
                    KingsButBad.kinggender2 = "Among Us Impostor";
                    break;
                default:
                    KingsButBad.kinggender2 = "Monarch";
            }
        }
        return;
    }
    public static void setKingGender(Boolean oneortwo, String toset) {
        if (oneortwo) {
            switch (toset) {
                case "male":
                    KingsButBad.kinggender = "King";
                    break;
                case "female":
                    KingsButBad.kinggender = "Queen";
                    break;
                case "sussy":
                    KingsButBad.kinggender = "Among Us Impostor";
                    break;
                default:
                    KingsButBad.kinggender = "Monarch";
            }
        }
        else {
            switch (toset) {
                case "male":
                    KingsButBad.kinggender2 = "King";
                    break;
                case "female":
                    KingsButBad.kinggender2 = "Queen";
                    break;
                case "sussy":
                    KingsButBad.kinggender2 = "Among Us Impostor";
                    break;
                default:
                    KingsButBad.kinggender2 = "Monarch";
            }
        }
        return;
    }
    public static void showKingMessages(Player p, String reason) {
        p.sendTitle(ChatColor.GREEN + "YOU ARE " + LegacyComponentSerializer.legacySection().serialize(miniMessage().deserialize("<gradient:#FFFF52:#FFBA52><b>THE KING!<b></gradient>")), reason);
        p.sendMessage(miniMessage().deserialize("<green><b>You're </green><gradient:#FFFF52:#FFBA52><b>The king!<b></gradient><#AEAEAE> Read <red><b>/ᴋɪɴɢ ʜᴇʟᴘ</b></red><#AEAEAE> for a small tutorial!<reset>"));
    }
    public static void givePlayerRole(Player p) {
        Bukkit.broadcastMessage(p.getName());
        Bukkit.broadcastMessage("Trigged Event");
        KingsButBad.playerRoleHashMap.putIfAbsent(p, Role.PEASANT);
        p.getInventory().clear();
        if (p == KingsButBad.king || p == KingsButBad.king2) {
            if (p == KingsButBad.king) {
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
                p.getInventory().setHelmet(crown);
            } else {
                p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
            }
            ItemStack diamondchest = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemMeta diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setChestplate(diamondchest);
            diamondchest = new ItemStack(Material.DIAMOND_LEGGINGS);
            diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setLeggings(diamondchest);
            diamondchest = new ItemStack(Material.DIAMOND_BOOTS);
            diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setBoots(diamondchest);
            ItemStack blade = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta blademeta = blade.getItemMeta();
            blademeta.setUnbreakable(true);
            blademeta.setDisplayName(CreateText.addColors("<color:#ffff00><b>King's <gradient:#0095ff:#1e00ff>Blade</b></color>"));
            ArrayList<String> bladelore = new ArrayList<>();
            bladelore.add(ChatColor.GRAY + "The blade yielded by kings of the past; does loads of damage.");
            blademeta.setLore(bladelore);
            blademeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
            blademeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            blade.setItemMeta(blademeta);
            p.getInventory().addItem(blade);

            ItemStack handcuffs = new ItemStack(Material.IRON_SHOVEL);
            ItemMeta handmeta = handcuffs.getItemMeta();
            handmeta.setUnbreakable(true);
            handmeta.setDisplayName(CreateText.addColors("<color:#ffff00><b>Handcuffs</color>"));
            handmeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
            handmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            handcuffs.setItemMeta(handmeta);
            p.getInventory().addItem(handcuffs);

            ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
            ItemMeta cardm = card.getItemMeta();
            cardm.setDisplayName(ChatColor.BLUE + "Keycard");
            card.setItemMeta(cardm);
            p.getInventory().addItem(card);
            p.getInventory().addItem(new ItemStack(Material.BOW));
            p.getInventory().addItem(new ItemStack(Material.ARROW, 64));
            p.teleport(new Location(Bukkit.getWorld("world"), -66, -56, 26.5));
            return;
        }
        if (KingsButBad.playerRoleHashMap.get(p) == Role.KNIGHT) {
            ItemStack diamondchest = new ItemStack(Material.IRON_CHESTPLATE);
            ItemMeta diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setChestplate(diamondchest);
            diamondchest = new ItemStack(Material.IRON_HELMET);
            diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setHelmet(diamondchest);
            diamondchest = new ItemStack(Material.IRON_LEGGINGS);
            diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setLeggings(diamondchest);
            diamondchest = new ItemStack(Material.IRON_BOOTS);
            diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setBoots(diamondchest);
            ItemStack blade = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta blademeta = blade.getItemMeta();
            blademeta.setUnbreakable(true);
            blademeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            blade.setItemMeta(blademeta);
            p.getInventory().addItem(blade);
            ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
            ItemMeta cardm = card.getItemMeta();
            cardm.setDisplayName(ChatColor.BLUE + "Keycard");
            card.setItemMeta(cardm);
            p.getInventory().addItem(card);
            p.getInventory().addItem(new ItemStack(Material.BOW));
            p.getInventory().addItem(new ItemStack(Material.ARROW, 64));
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                p.teleport(new Location(Bukkit.getWorld("world"), -56.5, -57, 30));
            }, 10);
        }
        if (KingsButBad.playerRoleHashMap.get(p) == Role.PRISON_GUARD) {
            ItemStack diamondchest = new ItemStack(Material.IRON_CHESTPLATE);
            ItemMeta diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setChestplate(diamondchest);
            diamondchest = new ItemStack(Material.DIAMOND_HELMET);
            diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setHelmet(diamondchest);
            diamondchest = new ItemStack(Material.DIAMOND_LEGGINGS);
            diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setLeggings(diamondchest);
            diamondchest = new ItemStack(Material.GOLDEN_BOOTS);
            diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setBoots(diamondchest);
            ItemStack blade = new ItemStack(Material.NETHERITE_SWORD);
            ItemMeta blademeta = blade.getItemMeta();
            blademeta.setUnbreakable(true);
            blademeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            blade.setItemMeta(blademeta);
            p.getInventory().addItem(blade);
            ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
            ItemMeta cardm = card.getItemMeta();
            cardm.setDisplayName(ChatColor.BLUE + "Keycard");
            card.setItemMeta(cardm);

            p.getInventory().addItem(card);
            p.getInventory().addItem(new ItemStack(Material.BOW));
            p.getInventory().addItem(new ItemStack(Material.ARROW, 64));
            ItemStack handcuffs = new ItemStack(Material.IRON_SHOVEL);
            ItemMeta handmeta = handcuffs.getItemMeta();
            handmeta.setUnbreakable(true);
            handmeta.setDisplayName(CreateText.addColors("<color:#ffff00><b>Handcuffs</color>"));
            handmeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
            handmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            handcuffs.setItemMeta(handmeta);
            p.getInventory().addItem(handcuffs);
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                p.teleport(new Location(Bukkit.getWorld("world"), -137.5, -51, -8));
            }, 10);

        }
        if (KingsButBad.playerRoleHashMap.get(p) == Role.BODYGUARD) {
            ItemStack diamondchest = new ItemStack(Material.NETHERITE_CHESTPLATE);
            ItemMeta diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setChestplate(diamondchest);
            diamondchest = new ItemStack(Material.DIAMOND_HELMET);
            diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setHelmet(diamondchest);
            diamondchest = new ItemStack(Material.DIAMOND_LEGGINGS);
            diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setLeggings(diamondchest);
            diamondchest = new ItemStack(Material.IRON_BOOTS);
            diamondchestmeta = diamondchest.getItemMeta();
            diamondchestmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
            diamondchestmeta.setUnbreakable(true);
            diamondchest.setItemMeta(diamondchestmeta);
            p.getInventory().setBoots(diamondchest);
            ItemStack blade = new ItemStack(Material.NETHERITE_SWORD);
            ItemMeta blademeta = blade.getItemMeta();
            blademeta.setUnbreakable(true);
            blademeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            blade.setItemMeta(blademeta);
            p.getInventory().addItem(blade);
            ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
            ItemMeta cardm = card.getItemMeta();
            cardm.setDisplayName(ChatColor.BLUE + "Keycard");
            card.setItemMeta(cardm);
            p.getInventory().addItem(card);
            p.getInventory().addItem(new ItemStack(Material.BOW));
            p.getInventory().addItem(new ItemStack(Material.ARROW, 64));
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                p.teleport(KingsButBad.bodylink.get(p).getLocation());
            }, 10);
        }
        if (KingsButBad.playerRoleHashMap.get(p) == Role.PRISONER) {
            p.setCooldown(Material.TERRACOTTA, 80);
            KingsButBad.prisonTimer.put(p, 100);
            ItemStack orangechest = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta chestmeta = (LeatherArmorMeta) orangechest.getItemMeta();
            chestmeta.setColor(Color.fromRGB(208, 133, 22));
            chestmeta.setDisplayName("Prisoner Chestplate");
            orangechest.setItemMeta(chestmeta);

            ItemStack orangeleg = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeleg.getItemMeta();
            orangelegItemMeta.setColor(Color.fromRGB(208, 133, 22));
            orangelegItemMeta.setDisplayName("Prisoner Leggings");
            orangeleg.setItemMeta(orangelegItemMeta);

            ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta orangebootItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
            orangebootItemMeta.setColor(Color.fromRGB(40, 20, 2));
            orangebootItemMeta.setDisplayName("Prisoner Boots");
            orangeboot.setItemMeta(orangebootItemMeta);

            p.getInventory().setChestplate(orangechest);
            p.getInventory().setLeggings(orangeleg);
            p.getInventory().setBoots(orangeboot);
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                p.teleport(new Location(Bukkit.getWorld("world"), -139.5, -57, 32.5, 180, 0));
            }, 10);
        }
        if (KingsButBad.playerRoleHashMap.get(p) == Role.PEASANT) {
            Bukkit.broadcastMessage("Trigged Peasant");
            p.getPersistentDataContainer().remove(KingsButBad.wasinPrison);
            Bukkit.broadcastMessage("Triggered wasInPrison Removal");
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                Bukkit.broadcastMessage("Triggered Teleport");
                Bukkit.broadcastMessage(p.getName());
                p.teleport(new Location(Bukkit.getWorld("world"), -120.5, -57, -30.5));
            }, 10);
        }
        p.sendTitle(KingsButBad.playerRoleHashMap.get(p).tag, KingsButBad.playerRoleHashMap.get(p).objective);
    }
}
