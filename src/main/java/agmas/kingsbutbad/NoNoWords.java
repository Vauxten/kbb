package agmas.kingsbutbad;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class NoNoWords {
    public static String[] filter = {
            "niga",
            "niger",
            "niba",
            "niber",
            "noga",
            "noger",
            "nigger",
            "nigga",
            "retard",
            "faggot",
            "fagg",
            "This sentence triggers the chat filter!"
            //"fag", this one's disabled as it disables things such as "of agmass"
    };

    public static HashMap<Player, String> previouslysaid = new HashMap<>();
}
