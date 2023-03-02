package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.NoNoWords;
import agmas.kingsbutbad.tasks.MiscTask;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(AsyncPlayerChatEvent event) {
        event.setMessage(KingsButBad.playerRoleHashMap.get(event.getPlayer()).chatColor + event.getMessage());
        if (KingsButBad.king != null) {
            event.setMessage(event.getMessage().replace(KingsButBad.king.getName(), CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kinggender + " " + KingsButBad.king.getName() + "<b></gradient>") + KingsButBad.playerRoleHashMap.get(event.getPlayer()).chatColor));
        }
        if (KingsButBad.king2 != null) {
            event.setMessage(event.getMessage().replace(KingsButBad.king2.getName(), CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kinggender2 + " " + KingsButBad.king2.getName() + "<b></gradient>") + KingsButBad.playerRoleHashMap.get(event.getPlayer()).chatColor));
        }
        if (KingsButBad.playerRoleHashMap.get(event.getPlayer()).isPowerful) {
            String b = event.getMessage();
            b.replace(" i ", " I ");
            if (!b.endsWith(".") && !b.endsWith("!") && !b.endsWith("?"))
               b += ".";
            b = b.substring(0, 1).toUpperCase() + b.substring(1);
            event.setMessage(b);
            for (Player p : Bukkit.getOnlinePlayers()) {
                switch (KingsButBad.playerRoleHashMap.get(p)) {
                    case KNIGHT:
                        event.setMessage(event.getMessage().replace(p.getName(), CreateText.addColors("<gray>Knight " + p.getName()) +  KingsButBad.playerRoleHashMap.get(event.getPlayer()).chatColor));
                        break;
                    case PRISON_GUARD:
                        event.setMessage(event.getMessage().replace(p.getName(), CreateText.addColors("<blue>Prison Guard " + p.getName()) +  KingsButBad.playerRoleHashMap.get(event.getPlayer()).chatColor));
                        break;
                    case PEASANT:
                        event.setMessage(event.getMessage().replace(p.getName(), CreateText.addColors("<#59442B>Peasant " + p.getName()) + KingsButBad.playerRoleHashMap.get(event.getPlayer()).chatColor));
                        break;
                    case CRIMINAl:
                        event.setMessage(event.getMessage().replace(p.getName(), CreateText.addColors("<red>Criminal " + p.getName()) + KingsButBad.playerRoleHashMap.get(event.getPlayer()).chatColor));
                        break;
                    case PRISONER:
                        event.setMessage(event.getMessage().replace(p.getName(), CreateText.addColors("<gold>Prisoner " + p.getName()) + KingsButBad.playerRoleHashMap.get(event.getPlayer()).chatColor));
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
        for (String i : NoNoWords.filter) {
            if (event.getMessage().contains(i)) {
                event.setMessage("God, I love KingsButBad! It's amazing! I love it so much, i would never say anything bad about it, yet again trigger the chat filter. That would be a shame! Golly jee.");
            }
        }
        if (MiscTask.bossbar.getPlayers().contains(event.getPlayer())) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (MiscTask.bossbar.getPlayers().contains(p)) {
                    if (!DisguiseAPI.isDisguised(event.getPlayer())) {
                        p.sendMessage(event.getPlayer().getPlayerListName() + ChatColor.GRAY + ": " + event.getMessage());

                    } else {
                        p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.DARK_GRAY + "] " + DisguiseAPI.getDisguise(event.getPlayer()).getWatcher().getCustomName() + ChatColor.GRAY + ": " + event.getMessage());
                    }
                }
            }
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!MiscTask.bossbar.getPlayers().contains(p)) {
                    p.sendMessage(event.getPlayer().getPlayerListName() + ChatColor.GRAY + ": " + event.getMessage());
                }
            }
        }
    }
}
