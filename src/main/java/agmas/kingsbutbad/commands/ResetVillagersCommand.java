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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ResetVillagersCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        KingsButBad.royalvillager.remove();
        KingsButBad.sewervillager.remove();
        KingsButBad.selfdefense.remove();
        KingsButBad.bertrude.remove();
        KingsButBad.miner.remove();
        KingsButBad.littlejoes.remove();
        KingsButBad.royalservant.remove();
        KingsButBad.royaltrader.remove();
        KingsButBad.minerguard.remove();
        KingsButBad.prisonguard.remove();
        KingsButBad.mopvillager.remove();
        KingsButBad.archerjohn.remove();
        for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities()) {
            if (le.getType().equals(EntityType.VILLAGER)) {
                le.damage(999999);
                le.remove();
            }
        }
        KingsButBad.setVillagers();
        return true;
    }
}