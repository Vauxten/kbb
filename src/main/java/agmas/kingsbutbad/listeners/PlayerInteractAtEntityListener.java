package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import com.destroystokyo.paper.Namespaced;
import com.destroystokyo.paper.NamespacedTag;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.units.qual.K;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerInteractAtEntityListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.COOKED_COD)) {
            event.getPlayer().sendMessage(CreateText.addColors("<gray><b>|<green><b> +5 HP<gray><b> |"));
            event.getPlayer().setHealth(event.getPlayer().getHealth() + 5);
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
    }

    @EventHandler
    public void onPlayerQuit(InventoryClickEvent event) {
        if (event.getWhoClicked().hasCooldown(Material.FISHING_ROD) || event.getWhoClicked().hasCooldown(Material.WOODEN_HOE))
            event.setCancelled(true);
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().hasItemFlag(ItemFlag.HIDE_PLACED_ON)) {
                event.setCancelled(true);
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
                if (event.getCurrentItem().getType().equals(Material.COOKED_COD)) {
                    Player p = (Player) event.getWhoClicked();
                    if (p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) >= 15) {
                        p.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(KingsButBad.money, PersistentDataType.DOUBLE, 0.0) - 15.0);
                        p.getInventory().addItem(new ItemStack(Material.COOKED_COD, 16));
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
                                            event.getWhoClicked().getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().get(KingsButBad.money, PersistentDataType.DOUBLE) + 0.1);
                                        }, ii);
                                    }
                            }, iii);
                            iii += i.getAmount();
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
    public void onPlayerQuit(PlayerInteractEvent event) {
        if (event.getItem() != null) {
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
                if (event.getRightClicked().equals(KingsButBad.royalvillager))
                    event.getPlayer().performCommand("king help");

            }, 1);
        }
    }

}
