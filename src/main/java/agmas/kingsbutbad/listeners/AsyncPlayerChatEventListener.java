package agmas.kingsbutbad.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(AsyncPlayerChatEvent event) {
        event.setMessage(event.getMessage() + " ");
        event.setFormat("%1$s" + ChatColor.GRAY + ": %2$s");
    }
}
