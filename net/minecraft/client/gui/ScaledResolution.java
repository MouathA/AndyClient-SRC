package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.util.*;

public class ScaledResolution
{
    private final double scaledWidthD;
    private final double scaledHeightD;
    private static int scaledWidth;
    private static int scaledHeight;
    private int scaleFactor;
    private static final String __OBFID;
    
    public ScaledResolution(final Minecraft minecraft, final int scaledWidth, final int scaledHeight) {
        ScaledResolution.scaledWidth = scaledWidth;
        ScaledResolution.scaledHeight = scaledHeight;
        this.scaleFactor = 1;
        final boolean unicode = minecraft.isUnicode();
        final int guiScale = minecraft.gameSettings.guiScale;
        if (1000 == 0) {}
        while (this.scaleFactor < 1000 && ScaledResolution.scaledWidth / (this.scaleFactor + 1) >= 320 && ScaledResolution.scaledHeight / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        if (unicode && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            --this.scaleFactor;
        }
        this.scaledWidthD = ScaledResolution.scaledWidth / (double)this.scaleFactor;
        this.scaledHeightD = ScaledResolution.scaledHeight / (double)this.scaleFactor;
        ScaledResolution.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
        ScaledResolution.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
    }
    
    public ScaledResolution(final Minecraft minecraft) {
        ScaledResolution.scaledWidth = minecraft.displayWidth;
        ScaledResolution.scaledHeight = minecraft.displayHeight;
        this.scaleFactor = 1;
        final boolean unicode = minecraft.isUnicode();
        final int guiScale = minecraft.gameSettings.guiScale;
        if (1000 == 0) {}
        while (this.scaleFactor < 1000 && ScaledResolution.scaledWidth / (this.scaleFactor + 1) >= 320 && ScaledResolution.scaledHeight / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        if (unicode && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            --this.scaleFactor;
        }
        this.scaledWidthD = ScaledResolution.scaledWidth / (double)this.scaleFactor;
        this.scaledHeightD = ScaledResolution.scaledHeight / (double)this.scaleFactor;
        ScaledResolution.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
        ScaledResolution.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
    }
    
    public static int getScaledWidth() {
        return ScaledResolution.scaledWidth;
    }
    
    public static int getScaledHeight() {
        return ScaledResolution.scaledHeight;
    }
    
    public double getScaledWidth_double() {
        return this.scaledWidthD;
    }
    
    public double getScaledHeight_double() {
        return this.scaledHeightD;
    }
    
    public ScaledResolution() {
        this(Minecraft.getInstance());
    }
    
    public int getScaleFactor() {
        return this.scaleFactor;
    }
    
    static {
        __OBFID = "CL_00000666";
    }
}
