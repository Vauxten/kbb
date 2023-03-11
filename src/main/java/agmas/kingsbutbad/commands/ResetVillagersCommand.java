package agmas.kingsbutbad.commands;
import agmas.kingsbutbad.KingsButBad;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class ResetVillagersCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        KingsButBad.royalVillager.remove();
        KingsButBad.sewerVillager.remove();
        KingsButBad.selfDefense.remove();
        KingsButBad.bertrude.remove();
        KingsButBad.miner.remove();
        KingsButBad.littleJoes.remove();
        KingsButBad.royalServant.remove();
        KingsButBad.royalTrader.remove();
        KingsButBad.minerGuard.remove();
        KingsButBad.prisonGuard.remove();
        KingsButBad.mopVillager.remove();
        KingsButBad.archerJohn.remove();
        for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities()) {
            if (le.getType().equals(EntityType.VILLAGER)) {
                le.damage(999999);
                le.remove();
            }
        }
        KingsButBad.setupVillagers();
        return true;
    }
}