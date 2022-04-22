package Mood.hologram.impl;

import java.awt.image.*;
import java.awt.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class PictureHologramLoader
{
    public int pictures;
    private int i;
    private String[] lines;
    private final Color[] colors;
    private static final char TRANSPARENT_CHAR;
    
    public PictureHologramLoader(final BufferedImage bufferedImage, final int n, final char c) {
        this.i = 0;
        this.colors = new Color[] { new Color(0, 0, 0), new Color(0, 0, 170), new Color(0, 170, 0), new Color(0, 170, 170), new Color(170, 0, 0), new Color(170, 0, 170), new Color(255, 170, 0), new Color(170, 170, 170), new Color(85, 85, 85), new Color(85, 85, 255), new Color(85, 255, 85), new Color(85, 255, 255), new Color(255, 85, 85), new Color(255, 85, 255), new Color(255, 255, 85), new Color(255, 255, 255) };
        this.pictures = n - 1;
        this.lines = this.toImgMessage(this.toChatColorArray(bufferedImage, n), c);
    }
    
    public PictureHologramLoader(final PictureHologramColor[][] array, final char c) {
        this.i = 0;
        this.colors = new Color[] { new Color(0, 0, 0), new Color(0, 0, 170), new Color(0, 170, 0), new Color(0, 170, 170), new Color(170, 0, 0), new Color(170, 0, 170), new Color(255, 170, 0), new Color(170, 170, 170), new Color(85, 85, 85), new Color(85, 85, 255), new Color(85, 255, 85), new Color(85, 255, 255), new Color(255, 85, 85), new Color(255, 85, 255), new Color(255, 255, 85), new Color(255, 255, 255) };
        this.lines = this.toImgMessage(array, c);
    }
    
    private PictureHologramColor[][] toChatColorArray(final BufferedImage bufferedImage, final int n) {
        final double n2 = bufferedImage.getHeight() / bufferedImage.getWidth();
        if ((int)(n / n2) > 10) {}
        final BufferedImage resizeImage = this.resizeImage(bufferedImage, (int)(n / n2), n);
        final PictureHologramColor[][] array = new PictureHologramColor[resizeImage.getWidth()][resizeImage.getHeight()];
        while (0 < resizeImage.getWidth()) {
            while (0 < resizeImage.getHeight()) {
                array[0][0] = this.getClosestChatColor(new Color(resizeImage.getRGB(0, 0), true));
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        return array;
    }
    
    private String[] toImgMessage(final PictureHologramColor[][] array, final char c) {
        final String[] array2 = new String[array[0].length];
        while (0 < array[0].length) {
            String string = "";
            while (0 < array.length) {
                string = String.valueOf(string) + ((array[0][0] != null) ? (String.valueOf(array[0][0].toString()) + c) : Character.valueOf(' '));
                int n = 0;
                ++n;
            }
            array2[0] = String.valueOf(string) + PictureHologramColor.RESET;
            int n2 = 0;
            ++n2;
        }
        return array2;
    }
    
    private BufferedImage resizeImage(final BufferedImage bufferedImage, final int n, final int n2) {
        int n3 = n;
        int n4 = n2;
        if (bufferedImage.getWidth() > bufferedImage.getHeight()) {
            n4 = (int)(n * (bufferedImage.getHeight() / (double)bufferedImage.getWidth()));
        }
        else {
            n3 = (int)(n2 * (bufferedImage.getWidth() / (double)bufferedImage.getHeight()));
        }
        final BufferedImage bufferedImage2 = new BufferedImage(n3, n4, 3);
        final Graphics2D graphics = bufferedImage2.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(bufferedImage, 0, 0, n3, n4, null);
        graphics.dispose();
        return bufferedImage2;
    }
    
    private double getDistance(final Color color, final Color color2) {
        final double n = (color.getRed() + color2.getRed()) / 2.0;
        final double n2 = color.getRed() - color2.getRed();
        final double n3 = color.getGreen() - color2.getGreen();
        final int n4 = color.getBlue() - color2.getBlue();
        return (2.0 + n / 256.0) * n2 * n2 + 4.0 * n3 * n3 + (2.0 + (255.0 - n) / 256.0) * n4 * n4;
    }
    
    private boolean areIdentical(final Color color, final Color color2) {
        return Math.abs(color.getRed() - color2.getRed()) <= 5 && Math.abs(color.getGreen() - color2.getGreen()) <= 5 && Math.abs(color.getBlue() - color2.getBlue()) <= 5;
    }
    
    private PictureHologramColor getClosestChatColor(final Color color) {
        if (color.getAlpha() < 128) {
            return null;
        }
        double n = -1.0;
        int n2 = 0;
        while (0 < this.colors.length) {
            if (this.areIdentical(this.colors[0], color)) {
                return PictureHologramColor.values()[0];
            }
            ++n2;
        }
        while (0 < this.colors.length) {
            final double distance = this.getDistance(color, this.colors[0]);
            if (distance < n || n == -1.0) {
                n = distance;
            }
            ++n2;
        }
        return PictureHologramColor.values()[0];
    }
    
    private String center(final String s, final int n) {
        if (s.length() > n) {
            return s.substring(0, n);
        }
        if (s.length() == n) {
            return s;
        }
        final int n2 = (n - s.length()) / 2;
        final StringBuilder sb = new StringBuilder();
        while (0 < n2) {
            sb.append(" ");
            int n3 = 0;
            ++n3;
        }
        return String.valueOf(sb.toString()) + s;
    }
    
    public String[] getLines() {
        return this.lines;
    }
    
    public ItemStack[] getArmorStands(final double n, final double n2, final double n3) {
        final ItemStack[] array = new ItemStack[this.getLines().length];
        Collections.reverse(Arrays.asList(this.getLines()));
        while (0 < this.getLines().length) {
            final ItemStack itemStack = new ItemStack(Items.armor_stand);
            final NBTTagCompound tagCompound = new NBTTagCompound();
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            final NBTTagList list = new NBTTagList();
            list.appendTag(new NBTTagDouble(n));
            list.appendTag(new NBTTagDouble(n2 + 0.2 * 0));
            list.appendTag(new NBTTagDouble(n3));
            nbtTagCompound.setTag("Pos", list);
            nbtTagCompound.setString("CustomName", this.getLines()[0]);
            nbtTagCompound.setInteger("CustomNameVisible", 1);
            nbtTagCompound.setInteger("Invisible", 1);
            nbtTagCompound.setInteger("NoGravity", 1);
            tagCompound.setTag("EntityTag", nbtTagCompound);
            itemStack.setTagCompound(tagCompound);
            itemStack.setStackDisplayName("§cPictureHologram:§a #" + 0);
            array[0] = itemStack;
            int n4 = 0;
            ++n4;
        }
        return array;
    }
    
    static {
        TRANSPARENT_CHAR = ' ';
    }
}
