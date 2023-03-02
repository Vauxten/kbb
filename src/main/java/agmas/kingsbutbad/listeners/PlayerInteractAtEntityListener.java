package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.tasks.MiscTask;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import com.destroystokyo.paper.Namespaced;
import com.destroystokyo.paper.NamespacedTag;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.units.qual.K;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlayerInteractAtEntityListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.COOKED_COD)) {
            event.getPlayer().sendMessage(CreateText.addColors("<gray><b>|<green><b> +5 HP<gray><b> |"));
            event.getPlayer().setHealth(Math.min(event.getPlayer().getHealth() + 5, event.getPlayer().getMaxHealth()));
        }
        if (event.getItem().getType().equals(Material.GOLDEN_APPLE)) {
            event.getPlayer().sendMessage(CreateText.addColors("<gray><b>|<green><b> +15 HP<gray><b> |"));
            event.getPlayer().setHealth(Math.min(event.getPlayer().getHealth() + 15, event.getPlayer().getMaxHealth()));
        }
        if (event.getItem().getType().equals(Material.BEETROOT_SOUP)) {
            event.getPlayer().sendMessage(CreateText.addColors("<gray><b>|<green><b> +2 HP<gray><b> |"));
            event.getPlayer().setHealth(Math.min(event.getPlayer().getHealth() + 2, event.getPlayer().getMaxHealth()));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerToggleSneakEvent event) {
        if (event.isSneaking()) {
            for (Entity e : event.getPlayer().getPassengers()) {
                e.leaveVehicle();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(ProjectileHitEvent event) {
        if (event.getHitEntity() != null) {
            if (event.getEntity().getShooter() != null) {
                if (event.getEntity().getShooter() instanceof Player p) {
                    if (event.getHitEntity() instanceof  Player d) {
                        if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PEASANT)) {
                            if (KingsButBad.playerRoleHashMap.get(d).isPowerful) {
                                p.sendTitle(ChatColor.RED + "!!! You're now a criminal !!!", ChatColor.GRAY + "You hit someone of authority.");
                                KingsButBad.playerRoleHashMap.put(p, Role.CRIMINAl);
                                p.playSound(p, Sound.ENTITY_SILVERFISH_DEATH, 1, 0.5f);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(EntityDismountEvent event) {
        if (event.getDismounted().getType().equals(EntityType.HORSE)) {
            event.getDismounted().remove();
        }
        if (event.getDismounted().getType().equals(EntityType.PLAYER)) {
            if (!event.getDismounted().isSneaking())
                event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerQuit(EntityDamageEvent event) {
        if (event.getEntity().getType().equals(EntityType.ITEM_FRAME)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerQuit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player d) {
            if (event.getDamager() instanceof Player p) {
                p.setCooldown(Material.RED_STAINED_GLASS, 20 * 6);
                d.setCooldown(Material.RED_STAINED_GLASS, 20 * 6);
                if (p.getInventory().getItemInMainHand().getType().equals(Material.IRON_SHOVEL)) {
                    event.setCancelled(true);
                    p.addPassenger(d);
                }
                if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PEASANT)) {
                    if (KingsButBad.playerRoleHashMap.get(d).isPowerful) {
                        p.sendTitle(ChatColor.RED + "!!! You're now a criminal !!!", ChatColor.GRAY + "You hit someone of authority.");
                        KingsButBad.playerRoleHashMap.put(p, Role.CRIMINAl);
                        p.playSound(p, Sound.ENTITY_SILVERFISH_DEATH, 1, 0.5f);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerFishEvent event) {
        if (!event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            return;
        }
        event.getCaught().remove();
        if (event.getPlayer().hasCooldown(Material.FISHING_ROD)) {
            return;
        }
        event.getPlayer().getInventory().addItem(new ItemStack(Material.SALMON));
        if (event.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
            event.getPlayer().getInventory().addItem(new ItemStack(Material.SALMON, new Random().nextInt(0, 3)));
        }
    }

    @EventHandler
    public void onPlayerQuit(EntityTargetEvent event) {
        if (event.getReason().equals(EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY)) {
            return;
        }
        if (event.getTarget() instanceof Player p) {
            if (!KingsButBad.playerRoleHashMap.get(p).equals(Role.CRIMINAl)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(InventoryClickEvent event) {
        if (event.getWhoClicked().hasCooldown(Material.FISHING_ROD) || event.getWhoClicked().hasCooldown(Material.WOODEN_HOE))
            event.setCancelled(true);
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().hasItemFlag(ItemFlag.HIDE_PLACED_ON)) {
                event.setCancelled(true);
                if (event.getCurrentItem().getType().equals(Material.DEEPSLATE_COAL_ORE)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 150.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 150.0);
                        p.sendMessage(ChatColor.RED + "Bought the coal compactor");
                        KingsButBad.coalCompactor = true;
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.LIGHT_BLUE_WOOL)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 200.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 200.0);
                        Bukkit.broadcastMessage(CreateText.addColors("<color:#ffff00><b>The" + RoleManager.getKingGender(p) + "<blue> has bought <gold>Little Joe's Shack"));
                        KingsButBad.joesunlocked = true;
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.IRON_SHOVEL)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 150.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 150.0);
                        LivingEntity le = (LivingEntity) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -117.220, -57, -10.131), EntityType.ZOMBIE);
                        le.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                        le.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        le.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        le.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                        le.setCustomName(ChatColor.BLUE + "Royal Patroller");
                        le.setCustomNameVisible(true);
                        le.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                        le.addPotionEffect(PotionEffectType.SPEED.createEffect(9999999, 0));
                        le.addPotionEffect(PotionEffectType.REGENERATION.createEffect(9999999, 0));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.MAP)) {
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(ChatColor.GOLD + "PRISON'S STATS");
                    Integer prisonercount = 0;
                    Integer guardcount = 0;
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                            prisonercount++;
                        }
                        if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISON_GUARD)) {
                            guardcount++;
                        }
                    }
                    event.getWhoClicked().sendMessage(ChatColor.GOLD + "Prisoners we are current holding: " + prisonercount);
                    event.getWhoClicked().sendMessage(ChatColor.BLUE + "It is currently" + MiscTask.bossbar.getTitle());
                    event.getWhoClicked().sendMessage(ChatColor.LIGHT_PURPLE + "We have " + guardcount + " loyal guards!");
                }
                if (event.getCurrentItem().getType().equals(Material.RED_CONCRETE)) {

                    Player p = (Player) event.getWhoClicked();
                    Bukkit.broadcastMessage(CreateText.addColors("<red>>> <b>" + p.getName() + "<gold> </b> turned themselves in, for some reason.."));
                    KingsButBad.prisonTimer.put(p, 100);
                    KingsButBad.playerRoleHashMap.put(p, Role.PRISONER);
                    event.setCancelled(true);
                    event.getWhoClicked().getInventory().clear();
                    event.getWhoClicked().getPersistentDataContainer().set(KingsButBad.wasinPrison, PersistentDataType.INTEGER, 1);
                    RoleManager.givePlayerRole(p);
                }
                if (event.getCurrentItem().getType().equals(Material.GOLDEN_APPLE)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 150.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 150.0);
                        p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.LEATHER_CHESTPLATE)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 50.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 50.0);
                        p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                        p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                        p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                        p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.IRON_SWORD)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 300.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 300.0);
                        p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.CHAINMAIL_HELMET)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 2500.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 2500.0);
                        ItemStack orangechest = new ItemStack(Material.LEATHER_CHESTPLATE);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        LeatherArmorMeta chestmeta = (LeatherArmorMeta) orangechest.getItemMeta();
                        chestmeta.setColor(Color.RED);
                        chestmeta.setDisplayName("Armor " + ChatColor.RED + ChatColor.MAGIC + "[CONTRABAND]");
                        orangechest.setItemMeta(chestmeta);

                        ItemStack orangeleg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                        ItemMeta orangelegItemMeta = orangeleg.getItemMeta();
                        orangelegItemMeta.setDisplayName("Armor " + ChatColor.RED + ChatColor.MAGIC + "[CONTRABAND]");
                        orangeleg.setItemMeta(orangelegItemMeta);


                        p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                        p.getInventory().setChestplate(orangechest);
                        p.getInventory().setLeggings(orangeleg);
                        p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.WOODEN_SWORD)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 30.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 30.0);
                        p.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.STICK)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 200.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 200.0);
                        ItemStack card = new ItemStack(Material.STICK);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Adrenaline Shot");
                        card.setItemMeta(cardm);
                        p.getInventory().addItem(card);
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.STONE_AXE)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 150.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 250.0);
                        p.getInventory().addItem(new ItemStack(Material.STONE_AXE));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.ARROW)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 50.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 50.0);
                        p.getInventory().addItem(new ItemStack(Material.ARROW, 32));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.BOW)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 100.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 100.0);
                        p.getInventory().addItem(new ItemStack(Material.BOW));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.YELLOW_CONCRETE)) {
                    Player p = (Player) event.getWhoClicked();
                    ArrayList<String> dialouges = new ArrayList<>(){};
                    dialouges.add("Kid, you really make me remember that person who tried to talk to me.. that one time... that they.. talked to me.. darn, i sure am lonely and an outcast (laugh track)");
                    dialouges.add("After so many years in the agency, it's finnally fun to see someone who isn't about to be put on a mission to their death! Hi, kid!");
                    dialouges.add("Gosh, I sure am old!");
                    dialouges.add("the IRS are on our asses");
                    dialouges.add("Earth isn't really, we all live on one big flat world, one big, blocky, green, flat landscape. The walls are obstructing our views. There is nothing beyond us. Wake up. BTU THAT'S JUST A THEORY! A GAME THEORY!!!!!!!!!!!!!!!!!!!!!!1");
                    dialouges.add("Hi, I'm arthur Join! (laugh track)");
                    dialouges.add("God, I sure am comedical! (Laugh track)");

                    p.sendMessage(CreateText.addColors("<green>archer johnm <gray><b>>></gray><white> " + dialouges.get(new Random().nextInt(0, dialouges.size()))));
                }
                if (event.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK)) {
                    Player p = (Player) event.getWhoClicked();
                    if (event.getCurrentItem().getItemMeta() != null) {
                        ItemMeta im = event.getCurrentItem().getItemMeta();
                        if (im.getDisplayName().equals(ChatColor.GOLD + "+1 Power Level")) {
                            if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 400.0) {
                                p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 400.0);
                                for (ItemStack i : p.getInventory()) {
                                    if (i != null) {
                                        if (i.getType().equals(Material.BOW)) {
                                            if (i.getEnchantments().containsKey(Enchantment.ARROW_DAMAGE)) {
                                                i.addEnchantment(Enchantment.ARROW_DAMAGE, i.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) + 1);
                                            } else {
                                                i.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (im.getDisplayName().equals(ChatColor.GOLD + "Fortune 1 On all Hoes")) {
                            if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 400.0) {
                                p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 400.0);
                                for (ItemStack i : p.getInventory()) {
                                    if (i != null) {
                                        if (i.getType().equals(Material.WOODEN_HOE)) {
                                            i.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
                                        }
                                    }
                                }
                            }
                        }
                        if (im.getDisplayName().equals(ChatColor.GOLD + "Lure 2 On all Fishing Rods")) {
                            if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 400.0) {
                                p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 400.0);
                                for (ItemStack i : p.getInventory()) {
                                    if (i != null) {
                                        if (i.getType().equals(Material.FISHING_ROD)) {
                                            i.addEnchantment(Enchantment.LURE, 2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.TRIPWIRE_HOOK)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 1500.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 1500.0);
                        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Keycard");
                        card.setItemMeta(cardm);
                        p.getInventory().addItem(card);
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.PAPER)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 1500.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 1500.0);
                        ItemStack card = new ItemStack(Material.PAPER);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Get-Out-Of-Jail-Free Card");
                        card.setItemMeta(cardm);
                        p.getInventory().addItem(card);
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.COOKED_COD)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 15) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 15.0);
                        p.getInventory().addItem(new ItemStack(Material.COOKED_COD, 16));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.GOLDEN_CARROT)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 32.0) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 32.0);
                        p.getInventory().addItem(new ItemStack(Material.GOLDEN_CARROT, 16));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.FISHING_ROD)) {
                    if (!event.getWhoClicked().getInventory().contains(Material.FISHING_ROD)) {
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.FISHING_ROD));
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.WOODEN_HOE)) {
                    if (!event.getWhoClicked().getInventory().contains(Material.WOODEN_HOE)) {
                        ItemStack woodenhoe = new ItemStack(Material.WOODEN_HOE);
                        ItemMeta woodenhoemeta = woodenhoe.getItemMeta();
                        woodenhoemeta.setDestroyableKeys(Collections.singleton(NamespacedKey.minecraft("wheat")));
                        woodenhoe.setItemMeta(woodenhoemeta);
                        event.getWhoClicked().getInventory().addItem(woodenhoe);
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.IRON_PICKAXE)) {
                    if (!event.getWhoClicked().getInventory().contains(Material.IRON_PICKAXE)) {
                        ItemStack woodenhoe = new ItemStack(Material.IRON_PICKAXE);
                        ItemMeta woodenhoemeta = woodenhoe.getItemMeta();
                        woodenhoemeta.setDestroyableKeys(Collections.singleton(NamespacedKey.minecraft("deepslate_coal_ore")));
                        woodenhoe.setItemMeta(woodenhoemeta);
                        event.getWhoClicked().getInventory().addItem(woodenhoe);
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.BONE)) {
                    if (!event.getWhoClicked().getInventory().contains(Material.BONE)) {
                        ItemStack woodenhoe = new ItemStack(Material.BONE);
                        ItemMeta woodenhoemeta = woodenhoe.getItemMeta();
                        woodenhoemeta.setDestroyableKeys(Collections.singleton(NamespacedKey.minecraft("brown_concrete_powder")));
                        woodenhoe.setItemMeta(woodenhoemeta);
                        event.getWhoClicked().getInventory().addItem(woodenhoe);
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.WHEAT)) {
                    Integer iii = 0;
                    for (ItemStack i : event.getWhoClicked().getInventory()) {
                        if (i != null && i.getType().equals(Material.WHEAT)) {
                            Integer originalamount = i.getAmount();
                            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                                    for (int ii = 1; ii < i.getAmount() + 1; ii++) {
                                        Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                                            i.setAmount(i.getAmount() - 1);
                                            Player p = (Player) event.getWhoClicked();
                                            p.setCooldown(Material.WOODEN_HOE, 20);
                                            p.playSound(p, Sound.ENTITY_ITEM_PICKUP, 1, 1);
                                            event.getWhoClicked().getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().get(KingsButBad.money, PersistentDataType.DOUBLE) + 0.5);
                                        }, ii);
                                    }
                            }, iii);
                            iii += originalamount;
                        }
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.BROWN_CONCRETE)) {
                    Integer iii = 0;
                    for (ItemStack i : event.getWhoClicked().getInventory()) {
                        if (i != null && i.getType().equals(Material.BROWN_DYE)) {
                            Integer originalamount = i.getAmount();
                            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                                for (int ii = 1; ii < i.getAmount() + 1; ii++) {
                                    Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                                        i.setAmount(i.getAmount() - 1);
                                        Player p = (Player) event.getWhoClicked();
                                        p.setCooldown(Material.BONE, 20);
                                        p.playSound(p, Sound.ENTITY_ITEM_PICKUP, 1, 1);
                                        event.getWhoClicked().getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().get(KingsButBad.money, PersistentDataType.DOUBLE) + 15.5);
                                    }, ii);
                                }
                            }, iii);
                            iii += originalamount;
                        }
                    }
                }
                if (event.getCurrentItem().getType().equals(Material.WATER_BUCKET)) {
                    Integer iii = 0;
                    for (ItemStack i : event.getWhoClicked().getInventory()) {
                        if (i != null && i.getType().equals(Material.SALMON)) {
                            Integer originalamount = i.getAmount();
                            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                                    for (int ii = 1; ii < i.getAmount() + 1; ii++) {
                                        Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                                            i.setAmount(i.getAmount() - 1);
                                            Player p = (Player) event.getWhoClicked();
                                            p.setCooldown(Material.FISHING_ROD, 20);
                                            p.playSound(p, Sound.ENTITY_ITEM_PICKUP, 1, 1);
                                            event.getWhoClicked().getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().get(KingsButBad.money, PersistentDataType.DOUBLE) + 100);
                                        }, ii);
                                    }
                            }, iii);
                            iii += originalamount;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(EntityMountEvent event) {
        if (event.getMount().getCustomName().endsWith("'s horse")) {
            String playername = event.getMount().getCustomName().split("'")[0];
            if (event.getEntity() instanceof Player p) {
                if (!p.getName().equals(playername)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            if (event.getItem().getType().equals(Material.CLAY_BALL) && !event.getPlayer().isInsideVehicle()) {
                Horse horse = (Horse) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.HORSE);
                horse.setCustomName(event.getPlayer().getName() + "'s horse");
                horse.addPassenger(event.getPlayer());
                horse.setTamed(true);
                horse.getInventory().setArmor(new ItemStack(Material.IRON_HORSE_ARMOR));
                horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
            }
            if (event.getItem().getItemMeta() != null) {
                if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Adrenaline Shot")) {
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                    event.getPlayer().addPotionEffect(PotionEffectType.LUCK.createEffect(20 * 20, 0));
                }
            }
        }
        if (event.getClickedBlock() != null) {
            ArrayList<Material> untouchables = new ArrayList<>(){};
            untouchables.add(Material.SWEET_BERRY_BUSH);
            untouchables.add(Material.SPRUCE_TRAPDOOR);
            untouchables.add(Material.DARK_OAK_DOOR);
            untouchables.add(Material.SPRUCE_DOOR);
            if (event.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
                if (untouchables.contains(event.getClickedBlock().getType())) {
                    event.setCancelled(true);
                }
            }
            if (event.getClickedBlock().getType().equals(Material.BARREL)) {
                event.setCancelled(true);
            }
            if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.FARMLAND)
                event.setCancelled(true);
            if (event.getClickedBlock().getType().equals(Material.IRON_DOOR)) {
                BlockState state = event.getClickedBlock().getState();
                Door openable = (Door) state.getBlockData();

                if (event.getItem() != null) {
                    if (event.getItem().getItemMeta() != null) {
                        if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Keycard")) {
                            event.setCancelled(true);
                            if (!openable.isOpen()) {
                                event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
                                openable.setOpen(true);
                            } else {
                                openable.setOpen(false);
                                event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);

                            }
                            state.setBlockData(openable);
                            state.update();
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            event.setCancelled(true);
        }
        if (event.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
            event.setCancelled(true);
        }
        if (event.getRightClicked().getType().equals(EntityType.VILLAGER)) {
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                event.getPlayer().closeInventory();
                event.setCancelled(true);
                event.setCancelled(true);
                if (event.getRightClicked().equals(KingsButBad.selfdefense)) {
                    Inventory inv = Bukkit.createInventory(null, 9);
                    ItemStack cod = new ItemStack(Material.LEATHER_CHESTPLATE);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Peasant's Armor");
                    ArrayList<String> codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "A set of armor to");
                    codlore.add(ChatColor.GRAY + "defend yourself from threats.");
                    codlore.add(ChatColor.GREEN + "$50");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(1, cod);

                    cod = new ItemStack(Material.WOODEN_SWORD);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Wooden Sword");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "A weak sword to defend yourself.");
                    codlore.add(ChatColor.GREEN + "$30");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(3, cod);

                    cod = new ItemStack(Material.GOLDEN_APPLE);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Golden Apple");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "A strong tool to keep yourself safe.");
                    codlore.add(ChatColor.GREEN + "$150");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(5, cod);
                    cod = new ItemStack(Material.STICK);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Adrenaline");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "Inject yourself with this to gain");
                    codlore.add(ChatColor.GRAY + "~20s of stamina invulnerability!");
                    codlore.add(ChatColor.GREEN + "$200");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(7, cod);
                    event.getPlayer().openInventory(inv);
                }
                if (event.getRightClicked().equals(KingsButBad.royalservant)) {
                    if (RoleManager.isKingAtAll(event.getPlayer())) {
                        Inventory inv = Bukkit.createInventory(null, 9);
                        ItemStack cod = new ItemStack(Material.IRON_SHOVEL);
                        cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        ItemMeta codmeta = cod.getItemMeta();
                        codmeta.setDisplayName(ChatColor.GOLD + "Hire Royal Patroler");
                        ArrayList<String> codlore = new ArrayList<>();
                        codlore.add(ChatColor.GRAY + "Hire a Royal Patroler.");
                        codlore.add(ChatColor.GRAY + "Patrolers find and kill criminals.");
                        codlore.add(ChatColor.GREEN + "$150");
                        codmeta.setLore(codlore);
                        cod.setItemMeta(codmeta);
                        inv.setItem(1, cod);
                        if (!KingsButBad.coalCompactor) {
                            cod = new ItemStack(Material.DEEPSLATE_COAL_ORE);
                            cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                            codmeta = cod.getItemMeta();
                            codmeta.setDisplayName(ChatColor.GOLD + "Coal Compactor");
                            codlore = new ArrayList<>();
                            codlore.add(ChatColor.GRAY + "Gain 5$ every time a prisoner mines coal.");
                            codlore.add(ChatColor.GREEN + "$150");
                            codmeta.setLore(codlore);
                            cod.setItemMeta(codmeta);
                            inv.setItem(3, cod);
                        }
                        if (!KingsButBad.joesunlocked) {
                            cod = new ItemStack(Material.LIGHT_BLUE_WOOL);
                            cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                            codmeta = cod.getItemMeta();
                            codmeta.setDisplayName(ChatColor.GOLD + "Buy Little Joe's Shack");
                            codlore = new ArrayList<>();
                            codlore.add(ChatColor.GRAY + "Allows people to shop from Little Joe's to upgrade");
                            codlore.add(ChatColor.GRAY + "Tools.");
                            codlore.add(ChatColor.GREEN + "$200");
                            codmeta.setLore(codlore);
                            cod.setItemMeta(codmeta);
                            inv.setItem(5, cod);
                        }
                        event.getPlayer().openInventory(inv);
                    }
                }
                if (event.getRightClicked().equals(KingsButBad.littlejoes)) {
                    Inventory inv = Bukkit.createInventory(null, 9);
                    ItemStack cod = new ItemStack(Material.GOLDEN_CARROT, 16);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Joe's Snax");
                    ArrayList<String> codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "small man, small bites");
                    codlore.add(ChatColor.GREEN + "$32");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(1, cod);

                    cod = new ItemStack(Material.ENCHANTED_BOOK);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Fortune 1 On all Hoes");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GREEN + "Applies to every Hoe in your inventory");
                    codlore.add(ChatColor.GREEN + "$400");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(3, cod);
                    cod = new ItemStack(Material.ENCHANTED_BOOK);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Lure 2 On all Fishing Rods");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GREEN + "Applies to every Fishing Rod in your inventory");
                    codlore.add(ChatColor.GREEN + "$400");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(5, cod);
                    event.getPlayer().openInventory(inv);
                }
                if (event.getRightClicked().equals(KingsButBad.royaltrader)) {
                    Inventory inv = Bukkit.createInventory(null, 9);
                    ItemStack cod = new ItemStack(Material.PAPER);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Get-Out-Of-Jail-Free Card");
                    ArrayList<String> codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "Stops you from going to jail as a criminal");
                    codlore.add(ChatColor.GRAY + "if you die.");
                    codlore.add(CreateText.addColors("<gray>(note; this will still <obfuscated>LOGFEB31-BRTRUD.. 1: Prisoner Emily, We need you. 1: Don't worry. You'll be let out actually. 1: The warden knows about this. 1: Apparently, they want you to go to a... Storage facility? Strange. 1: They want you to go... on the 31st of feburary? 1: Yeah.. That's not even a day.. 1: Shit. He's calling, I better stop before we get caught talking.</obfuscated>"));
                    codlore.add(ChatColor.GREEN + "$1500");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(1, cod);

                    cod = new ItemStack(Material.PLAYER_HEAD);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    SkullMeta sm = (SkullMeta) codmeta;
                    sm.setOwningPlayer(Bukkit.getOfflinePlayer("Piggopet"));
                    sm.setDisplayName(ChatColor.GOLD + "Piggopet's Head");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "piggopet reference");
                    codlore.add(ChatColor.GREEN + "$priceless");
                    sm.setLore(codlore);
                    cod.setItemMeta(sm);
                    inv.setItem(3, cod);

                    cod = new ItemStack(Material.CHAINMAIL_HELMET);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Stronger Gear");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "There's a tag of something on here;");
                    codlore.add(ChatColor.GRAY + "But it's definetly not from this kingdom.");
                    codlore.add(ChatColor.GREEN + "$2500");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(5, cod);

                    cod = new ItemStack(Material.IRON_SWORD);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Iron Sword");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "A simple sword for simple soldiers");
                    codlore.add(ChatColor.GREEN + "$300");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(7, cod);
                    event.getPlayer().openInventory(inv);
                }
                if (event.getRightClicked().equals(KingsButBad.sewervillager)) {
                    Inventory inv = Bukkit.createInventory(null, 9);
                    ItemStack cod = new ItemStack(Material.TRIPWIRE_HOOK);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Fake Key");
                    ArrayList<String> codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "Used for breaking into the castle,");
                    codlore.add(ChatColor.GRAY + "or to let people out of prison.");
                    codlore.add(ChatColor.GREEN + "$1500");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(1, cod);

                    cod = new ItemStack(Material.STONE_AXE);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Stone Axe");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "A strong weapon, weilded by generations");
                    codlore.add(ChatColor.GRAY + "years ago in the days of PrisonButBad.");
                    codlore.add(ChatColor.GREEN + "$100");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(3, cod);

                    cod = new ItemStack(Material.GOLDEN_APPLE);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Golden Apple");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "i forged this from among us");
                    codlore.add(ChatColor.GREEN + "$150");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(5, cod);
                    cod = new ItemStack(Material.BROWN_CANDLE);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "beanms");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "The most important item in the lore.");
                    codlore.add(ChatColor.GRAY + "(fact checked by NoahTheSpacyCat)");
                    codlore.add(ChatColor.GRAY + "");
                    codlore.add(ChatColor.RED + "OUT OF STOCK!");
                    codlore.add(ChatColor.GREEN + "$1");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(7, cod);
                    event.getPlayer().openInventory(inv);
                }
                if (event.getRightClicked().equals(KingsButBad.archerjohn)) {
                    Inventory inv = Bukkit.createInventory(null, 9);
                    ItemStack cod = new ItemStack(Material.ARROW);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    cod.setAmount(32);
                    ItemMeta codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Bunch o' arrows");
                    ArrayList<String> codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "silly little arrows");
                    codlore.add(ChatColor.GREEN + "$50");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(1, cod);

                    cod = new ItemStack(Material.BOW);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Bow");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "A bow. You hold it, pull it back, rip the space-time");
                    codlore.add(ChatColor.GRAY + "continum, and shoot! Atleast, that's what i do.");
                    codlore.add(ChatColor.GREEN + "$100");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(3, cod);

                    cod = new ItemStack(Material.ENCHANTED_BOOK);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "+1 Power Level");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GREEN + "Applies to every bow in your inventory");
                    codlore.add(ChatColor.GREEN + "$400");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(5, cod);
                    cod = new ItemStack(Material.YELLOW_CONCRETE);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Talk");
                    codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "Talk to archer john. See how he's doing.");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(7, cod);
                    event.getPlayer().openInventory(inv);
                }
                if (event.getRightClicked().equals(KingsButBad.bertrude)) {
                    event.getPlayer().sendMessage("hello i am bertrude from the hit server prison but bad");
                    Inventory inv = Bukkit.createInventory(null, 9);
                    ItemStack hoe = new ItemStack(Material.FISHING_ROD);
                    hoe.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta hoemeta = hoe.getItemMeta();
                    hoemeta.setDisplayName(ChatColor.BLUE + "Get Fishing Rod");
                    hoe.setItemMeta(hoemeta);
                    inv.setItem(2, hoe);
                    ItemStack wheat = new ItemStack(Material.WATER_BUCKET);
                    wheat.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta wheatmeta = wheat.getItemMeta();
                    wheatmeta.setDisplayName(ChatColor.GOLD + "Sell Fish");
                    wheat.setItemMeta(wheatmeta);
                    inv.setItem(3, wheat);
                    ItemStack cod = new ItemStack(Material.COOKED_COD);
                    cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta codmeta = cod.getItemMeta();
                    codmeta.setDisplayName(ChatColor.GOLD + "Cooked Cod");
                    cod.setAmount(16);
                    ArrayList<String> codlore = new ArrayList<>();
                    codlore.add(ChatColor.GRAY + "ripe and ready, come buy my fish ;) -bertrude");
                    codlore.add(ChatColor.GREEN + "$15");
                    codmeta.setLore(codlore);
                    cod.setItemMeta(codmeta);
                    inv.setItem(5, cod);
                    event.getPlayer().openInventory(inv);
                }
                if (event.getRightClicked().equals(KingsButBad.lunchlady)) {
                    if (MiscTask.bossbar.getTitle().equals("Lunch") || MiscTask.bossbar.getTitle().equals("Breakfast")) {
                        event.getPlayer().sendMessage(CreateText.addColors("<gold>Lunch Lady <white><b>>> Here's your lunch."));
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.BEETROOT_SOUP));
                    }
                }
                if (event.getRightClicked().equals(KingsButBad.farmerjoe)) {
                    Inventory inv = Bukkit.createInventory(null, 9);
                    ItemStack hoe = new ItemStack(Material.WOODEN_HOE);
                    hoe.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta hoemeta = hoe.getItemMeta();
                    hoemeta.setDisplayName(ChatColor.BLUE + "Get Hoe");
                    hoe.setItemMeta(hoemeta);
                    inv.setItem(2, hoe);
                    ItemStack wheat = new ItemStack(Material.WHEAT);
                    wheat.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta wheatmeta = wheat.getItemMeta();
                    wheatmeta.setDisplayName(ChatColor.GOLD + "Sell Wheat");
                    wheat.setItemMeta(wheatmeta);
                    inv.setItem(6, wheat);
                    event.getPlayer().openInventory(inv);
                }
                if (event.getRightClicked().equals(KingsButBad.minerguard)) {
                    Inventory inv = Bukkit.createInventory(null, 9);
                    ItemStack hoe = new ItemStack(Material.IRON_PICKAXE);
                    hoe.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta hoemeta = hoe.getItemMeta();
                    hoemeta.setDisplayName(ChatColor.BLUE + "Get Pickaxe");
                    hoe.setItemMeta(hoemeta);
                    inv.setItem(4, hoe);
                    event.getPlayer().openInventory(inv);
                }
                if (event.getRightClicked().equals(KingsButBad.prisonguard)) {
                    Inventory inv = Bukkit.createInventory(null, 9);
                    ItemStack hoe = new ItemStack(Material.RED_CONCRETE);
                    hoe.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta hoemeta = hoe.getItemMeta();
                    hoemeta.setDisplayName(ChatColor.BLUE + "Turn Yourself In" + ChatColor.GRAY + " (for some reason)");
                    hoe.setItemMeta(hoemeta);
                    inv.setItem(2, hoe);
                    event.getPlayer().openInventory(inv);

                    hoe = new ItemStack(Material.MAP);
                    hoe.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    hoemeta = hoe.getItemMeta();
                    hoemeta.setDisplayName(ChatColor.BLUE + "Prison Stats");
                    hoe.setItemMeta(hoemeta);
                    inv.setItem(6, hoe);
                    event.getPlayer().openInventory(inv);
                }
                if (event.getRightClicked().equals(KingsButBad.mopvillager)) {
                    Inventory inv = Bukkit.createInventory(null, 9);
                    ItemStack hoe = new ItemStack(Material.BONE);
                    hoe.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta hoemeta = hoe.getItemMeta();
                    hoemeta.setDisplayName(ChatColor.BLUE + "Get Mop");
                    hoe.setItemMeta(hoemeta);
                    inv.setItem(2, hoe);
                    ItemStack wheat = new ItemStack(Material.BROWN_CONCRETE);
                    wheat.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    ItemMeta wheatmeta = wheat.getItemMeta();
                    wheatmeta.setDisplayName(ChatColor.GOLD + "Sell... the shit? i guess????");
                    wheat.setItemMeta(wheatmeta);
                    inv.setItem(6, wheat);
                    event.getPlayer().openInventory(inv);
                }
                if (event.getRightClicked().equals(KingsButBad.royalvillager))
                    event.getPlayer().performCommand("king help");

            }, 1);
        }
    }

}
