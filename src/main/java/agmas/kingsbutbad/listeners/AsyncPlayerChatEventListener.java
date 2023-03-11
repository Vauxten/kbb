package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.NoNoWords;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

public class AsyncPlayerChatEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerChatEvent event) {
        event.setMessage(KingsButBad.roles.get(event.getPlayer()).chatColor + event.getMessage());
        if (KingsButBad.king != null) {
            event.setMessage(event.getMessage().replace(KingsButBad.king.getName(), CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingGender + " " + KingsButBad.king.getName() + "<b></gradient>") + KingsButBad.roles.get(event.getPlayer()).chatColor));
        }
        if (KingsButBad.king2 != null) {
            event.setMessage(event.getMessage().replace(KingsButBad.king2.getName(), CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingGender2 + " " + KingsButBad.king2.getName() + "<b></gradient>") + KingsButBad.roles.get(event.getPlayer()).chatColor));
        }
        if (KingsButBad.roles.get(event.getPlayer()).isPowerful) {
            String b = event.getMessage();
            b.replace(" i ", " I ");
            if (!b.endsWith(".") && !b.endsWith("!") && !b.endsWith("?"))
               b += ".";
            b = b.substring(0, 1).toUpperCase() + b.substring(1);
            event.setMessage(b);
            for (Player p : Bukkit.getOnlinePlayers()) {
                switch (KingsButBad.roles.get(p)) {
                    case KNIGHT:
                        event.setMessage(event.getMessage().replace(p.getName(), CreateText.addColors("<gray>Knight " + p.getName()) +  KingsButBad.roles.get(event.getPlayer()).chatColor));
                        break;
                    case PRISON_GUARD:
                        event.setMessage(event.getMessage().replace(p.getName(), CreateText.addColors("<blue>Prison Guard " + p.getName()) +  KingsButBad.roles.get(event.getPlayer()).chatColor));
                        break;
                    case PEASANT:
                        event.setMessage(event.getMessage().replace(p.getName(), CreateText.addColors("<#59442B>Peasant " + p.getName()) + KingsButBad.roles.get(event.getPlayer()).chatColor));
                        break;
                    case CRIMINAl:
                        event.setMessage(event.getMessage().replace(p.getName(), CreateText.addColors("<red>Criminal " + p.getName()) + KingsButBad.roles.get(event.getPlayer()).chatColor));
                        break;
                    case PRISONER:
                        event.setMessage(event.getMessage().replace(p.getName(), CreateText.addColors("<gold>Prisoner " + p.getName()) + KingsButBad.roles.get(event.getPlayer()).chatColor));
                }

            }
        }
        event.setCancelled(true);
        event.setMessage(event.getMessage() + " ");
        if (NoNoWords.previouslysaid.containsKey(event.getPlayer())) {
            if (NoNoWords.previouslysaid.get(event.getPlayer()).equalsIgnoreCase(event.getMessage())) {
                event.getPlayer().sendMessage(ChatColor.RED + "No Spamming!");
                return;
            }
        }
        NoNoWords.previouslysaid.put(event.getPlayer(), event.getMessage());
        event.setFormat("%1$s" + ChatColor.GRAY + ": %2$s");
        event.setMessage(NoNoWords.filtermsg(event.getMessage()));
        if (!NoNoWords.isClean(event.getMessage())) {
            event.setMessage(KingsButBad.roles.get(event.getMessage()).chatColor + "I LOVE THIS GAME!! It's so cool. It's so amazing. The work done here is great! The people who helped commiting @ the github, agmass and the dev team is so awesome, i would never trigger the chat filter, and i definetly am not right now! Thank you for making such a great server! I, " + event.getPlayer().getName() + " LOVE this server.");
        }
        Integer zone = KingsButBad.currentZone.get(event.getPlayer());
        if (KingsButBad.isInside(event.getPlayer(), new Location(event.getPlayer().getWorld(), -74, -54, 25), new Location(event.getPlayer().getWorld(), -72, -58, 23)) && KingsButBad.roles.get(event.getPlayer()).equals(Role.KING)) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p, Sound.ENTITY_BEE_LOOP_AGGRESSIVE, 1, 0.75f);
                p.sendTitle(ChatColor.BLUE + "INTERCOM " + ChatColor.WHITE + ">>", ChatColor.GOLD + event.getMessage());
                p.sendMessage(ChatColor.BLUE + "INTERCOM " + ChatColor.WHITE + ">> " + ChatColor.GOLD + event.getMessage());
            }
            return;
        }
        int hearcount = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            Location originalplayerloc = event.getPlayer().getEyeLocation();
            Vector dir = p.getEyeLocation().toVector().subtract(event.getPlayer().getEyeLocation().toVector());
            if (p.equals(event.getPlayer())) {
                continue;
            }
            RayTraceResult rtr = event.getPlayer().getWorld().rayTrace(originalplayerloc, dir, 25, FluidCollisionMode.NEVER, true, 2.0, Predicate.isEqual(p));
            if (KingsButBad.soundWaves.containsKey(event.getPlayer())) {
                Color[] a = new Color[] {
                        Color.AQUA,
                        Color.BLUE,
                        Color.BLACK,
                        Color.FUCHSIA,
                        Color.GRAY,
                        Color.LIME,
                        Color.OLIVE,
                        Color.NAVY,
                        Color.PURPLE,
                        Color.ORANGE,
                        Color.RED,
                        Color.TEAL,
                        Color.YELLOW,
                        Color.WHITE
                };
                Color b = a[new Random().nextInt(0,a.length)];
                Location point1 = originalplayerloc;
                Location point2 = rtr.getHitPosition().toLocation(p.getWorld());
                double space = 0.5;
                World world = point1.getWorld();
                double distance = point1.distance(point2);
                Vector p1 = point1.toVector();
                Vector p2 = point2.toVector();
                Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
                double length = 0;
                int i = 0;
                int ii = 0;
                ArrayList<Location> locations = new ArrayList<>();
                for (; length < distance; p1.add(vector)) {
                    locations.add(new Location(p.getWorld(), p1.getX(), p1.getY(), p1.getZ()));
                    length += space;
                }
                for (Location l : locations) {
                    Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> {
                        event.getPlayer().spawnParticle(Particle.REDSTONE, l.getX(), l.getY(), l.getZ(), 1, new Particle.DustOptions(b, 1));
                        event.getPlayer().playSound(l, Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 1);
                        if (KingsButBad.soundWaves.containsKey(p)) {
                            p.spawnParticle(Particle.REDSTONE, l.getX(), l.getY(), l.getZ(), 1, new Particle.DustOptions(b, 1));
                            p.playSound(l, Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 1);
                        }
                    }, i);
                    ii++;
                    if (ii >= 2) {
                        ii = 0;
                        i++;
                    }
                }
            }
            if (rtr != null) {
                if (KingsButBad.soundWaves.containsKey(event.getPlayer())) {

                }
                if (rtr.getHitEntity() != null) {
                    if (rtr.getHitEntity().equals(p)) {
                        hearcount++;
                        if (!DisguiseAPI.isDisguised(event.getPlayer())) {
                            p.sendMessage(event.getPlayer().getPlayerListName() + ChatColor.GRAY + ": " + event.getMessage());
                        } else {
                            p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.DARK_GRAY + "] " + DisguiseAPI.getDisguise(event.getPlayer()).getWatcher().getCustomName() + ChatColor.GRAY + ": " + event.getMessage());
                        }
                        continue;
                    }
                }
            }
            if (ChatColor.stripColor(event.getMessage()).contains(p.getName())) {
                event.getPlayer().sendMessage(ChatColor.RED + p.getName() + " isn't in range and can't hear you!");
            }
        }
        if (hearcount != 0) {
        if (!DisguiseAPI.isDisguised(event.getPlayer())) {
            event.getPlayer().sendMessage(event.getPlayer().getPlayerListName() + ChatColor.GRAY + ": " + event.getMessage());
        } else {
            event.getPlayer().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.DARK_GRAY + "] " + DisguiseAPI.getDisguise(event.getPlayer()).getWatcher().getCustomName() + ChatColor.GRAY + ": " + event.getMessage());
        } } else {
            event.getPlayer().sendMessage(ChatColor.RED + "But nobody heard...");
        }
    }
}
