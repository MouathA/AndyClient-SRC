package Mood.hologram;

import net.minecraft.client.*;
import java.util.*;

public class HologramManager
{
    public ArrayList spawnedHolograms;
    
    public HologramManager() {
        this.spawnedHolograms = new ArrayList();
    }
    
    public void spawnHologram(final String s, String replace, String replace2, String replace3) {
        replace = replace.replace("~", new StringBuilder().append(Minecraft.thePlayer.posX).toString());
        replace2 = replace2.replace("~", new StringBuilder().append(Minecraft.thePlayer.posY).toString());
        replace3 = replace3.replace("~", new StringBuilder().append(Minecraft.thePlayer.posZ).toString());
        final Hologram hologram = new Hologram(s, Double.valueOf(replace), Double.valueOf(replace2), Double.valueOf(replace3));
        hologram.spawnHologram();
        this.spawnedHolograms.add(hologram);
    }
    
    public String getPosFromHologram(final String s) {
        return String.valueOf(String.valueOf(String.valueOf(String.valueOf(this.getHologramByText(s).getX())))) + " " + this.getHologramByText(s).getY() + " " + this.getHologramByText(s).getZ();
    }
    
    public Hologram getHologramByText(final String s) {
        for (final Hologram hologram : this.spawnedHolograms) {
            if (!hologram.getText().equalsIgnoreCase(s)) {
                continue;
            }
            return hologram;
        }
        return null;
    }
    
    public void listHolograms() {
        for (Hologram hologram : this.spawnedHolograms) {}
    }
}
