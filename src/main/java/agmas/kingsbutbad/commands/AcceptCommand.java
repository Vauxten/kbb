package agmas.kingsbutbad.commands;
import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.K;

public class AcceptCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            switch (KingsButBad.playerRoleInviteHashMap.get(p)) {
                case KING -> {
                    KingsButBad.playerRoleHashMap.put(p, Role.KING);
                    RoleManager.showKingMessages(p, CreateText.addColors("<dark_gray>You were sidekicked; Welcome, <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kinggender2.toUpperCase()));
                    KingsButBad.king2 = p;
                    RoleManager.givePlayerRole(p);
                }
                case KNIGHT -> {
                    KingsButBad.playerRoleHashMap.put(p, Role.KNIGHT);
                    RoleManager.givePlayerRole(p);
                }
                case PRISON_GUARD -> {
                    KingsButBad.playerRoleHashMap.put(p, Role.PRISON_GUARD);
                    RoleManager.givePlayerRole(p);
                }
                case BODYGUARD -> {
                    KingsButBad.playerRoleHashMap.put(p, Role.BODYGUARD);
                    RoleManager.givePlayerRole(p);
                }
                default ->
                        p.sendMessage(CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kinggender.toUpperCase() + KingsButBad.king.getName().toUpperCase() + "</b><red> hasn't invited you to being any roles."));
            }
        }
        return true;
    }
}