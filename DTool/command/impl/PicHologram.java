package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import Mood.hologram.impl.*;
import java.net.*;
import javax.imageio.*;
import Mood.*;
import java.awt.image.*;

public class PicHologram extends Command
{
    public PicHologram() {
        super("picturehologram", "picturehologram", "picturehologram", new String[] { "p" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 1) {
            Segito.msg("-picturehologram§b <betolt> <link> <magass\u00e1g> <hossz\u00fas\u00e1g>");
        }
        else if (array.length != 0) {
            if (array[0].equalsIgnoreCase("betolt")) {
                if (Minecraft.playerController.isNotCreative()) {
                    Segito.msg("Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                    Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
                    return;
                }
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
                new PictureHologram().init();
                final BufferedImage read = ImageIO.read(new URL(array[1]));
                PictureHologram.client.setLoc();
                PictureHologram.client.setup(array[1], Integer.valueOf(array[2].replace("%", String.valueOf(read.getHeight()))), Integer.valueOf(array[3].replace("%", String.valueOf(read.getWidth()))));
            }
            else if (array.length > 0) {
                final StringBuilder sb = new StringBuilder();
                while (3 < array.length) {
                    sb.append(" ").append(array[3]);
                    int n = 0;
                    ++n;
                }
                Client.getInstance().getHologramManager().spawnHologram(sb.toString().trim(), array[0], array[1], array[2]);
            }
            else {
                Segito.msg("hologram", true);
            }
        }
    }
}
