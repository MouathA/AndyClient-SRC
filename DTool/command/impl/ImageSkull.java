package DTool.command.impl;

import DTool.command.*;
import java.awt.*;
import java.net.*;
import net.minecraft.init.*;
import java.util.*;
import Mood.*;

public class ImageSkull extends Command
{
    public ImageSkull() {
        super("ImageSkull", "ImageSkull", "ImageSkull", new String[] { "ImageSkull" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length == 1) {
            if (array[0].equalsIgnoreCase("kereses")) {
                if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI("https://www.google.com/search?q=inurl%3A%22https://media.mojang.com/%22&source=lnms&tbm=isch&sa=X&biw=1920&bih=919"));
                }
                else {
                    Segito.msg("Hiba t\u00f6rt\u00e9nt.");
                }
            }
            else {
                HackedItemUtils.addItem(HackedItemUtils.createItem(Items.skull, 1, 3, "{SkullOwner:{Id:\"" + UUID.randomUUID().toString() + "\",Properties:{textures:[0:{Value:\"" + Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"" + array[0] + "\"}}}").getBytes()) + "\"}]}}}"));
                Segito.msg("§7Az§e Item§7 sikeresen le lett k\u00e9rve!");
            }
        }
        else {
            Segito.msg("Haszn\u00e1lat:§b -ImageSkull <kereses/link>");
        }
    }
}
