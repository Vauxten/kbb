package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;


public class MiscTask extends BukkitRunnable {
    @Override
    public void run(){
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getGameMode().equals(GameMode.SURVIVAL))
                p.setGameMode(GameMode.ADVENTURE);
        }
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
    }
}