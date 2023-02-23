package agmas.kingsbutbad.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public enum Role {

    PRISONER(CreateText.addColors("<gold>PRISONER"), CreateText.addColors("<gray>You got caught! Stay for 5 minutes.<gray>"), "<gold>PRISONER", false, ChatColor.DARK_GRAY),
    PEASANT(CreateText.addColors("<#59442B>PEASANT"), CreateText.addColors("<gray>Survive.<gray>"), "<#59442B>PEASANT", false, ChatColor.DARK_GRAY),
    CRIMINAl(CreateText.addColors("<red>CRIMINAL"), CreateText.addColors("<gray>Don't get caught!<gray>"), "<red>CRIMINAL", false, ChatColor.RED),
    KNIGHT(CreateText.addColors("<gray>KNIGHT"), CreateText.addColors("<blue>Keep the kingdom safe!<gray>"), "<gray>KNIGHT", true, ChatColor.WHITE),
    KING(CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>KING<b></gradient>"), CreateText.addColors("<gradient:#FFFF52:#FFBA52><i>Live the royal life</gradient>"), "<gradient:#FFFF52:#FFBA52><b>KING<b></gradient>", true, ChatColor.RED);

    public String tag;
    public String objective;
    public String uncompressedColors;
    public Boolean isPowerful;
    public ChatColor chatColor;

    Role(String tag, String objective, String uncompressedColors, Boolean isPowerful, ChatColor chatColor) {
        this.tag = tag;
        this.objective = objective;
        this.uncompressedColors = uncompressedColors;
        this.isPowerful = isPowerful;
        this.chatColor = chatColor;
    }
}