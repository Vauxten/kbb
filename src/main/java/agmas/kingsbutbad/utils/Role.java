package agmas.kingsbutbad.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public enum Role {

    PEASANT(CreateText.addColors("<#59442B>PEASANT"), CreateText.addColors("<gray>Survive.<gray>"), "<#59442B>PEASANT"),
    KNIGHT(CreateText.addColors("<gray>KNIGHT"), CreateText.addColors("<blue>Keep the kingdom safe!<gray>"), "<gray>KNIGHT"),
    KING(CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>KING<b></gradient>"), CreateText.addColors("<gradient:#FFFF52:#FFBA52><i>Keep the kingdom safe</gradient>"), "<gradient:#FFFF52:#FFBA52><b>KING<b></gradient>");

    public String tag;
    public String objective;
    public String uncompressedColors;

    Role(String tag, String objective, String uncompressedColors) {
        this.tag = tag;
        this.objective = objective;
        this.uncompressedColors = uncompressedColors;
    }
}