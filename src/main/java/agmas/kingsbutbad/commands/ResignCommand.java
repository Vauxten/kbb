package agmas.kingsbutbad.commands;
import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class ResignCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof  Player p) {
            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.PRISONER)) {
                sender.sendMessage(ChatColor.RED + "You're stuck here, filth.");
                return true;
            }
            if (KingsButBad.playerRoleHashMap.get(p).equals(Role.KING)) {
                sender.sendMessage(ChatColor.GOLD + "Drop your crown instead!");
                return true;
            }
                KingsButBad.playerRoleHashMap.put(p, Role.PEASANT);
                RoleManager.givePlayerRole(p);
        }
        return true;
    }
}