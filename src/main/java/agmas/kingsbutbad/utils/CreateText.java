package agmas.kingsbutbad.utils;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class CreateText {
    public static String addColors(String a) {
        return LegacyComponentSerializer.legacySection().serialize(miniMessage().deserialize(a));
    }
}
