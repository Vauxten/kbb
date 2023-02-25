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
import org.bukkit.persistence.PersistentDataType;

public class SetMoneyCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (Bukkit.getPlayer(args[0]) != null) {
                Player pe = Bukkit.getPlayer(args[0]);
                pe.getPersistentDataContainer().set(KingsButBad.money, PersistentDataType.DOUBLE,Double.valueOf(args[1]));
            }
        }
        return true;
    }
}