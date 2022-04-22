package Mood.hologram;

import java.text.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Hologram
{
    private String text;
    private String date;
    private double x;
    private double y;
    private double z;
    private ItemStack item;
    DateFormat df;
    Date today;
    String renderDate;
    DateFormat dff;
    Date todayy;
    String renderTime;
    
    public Hologram(final String text, final double x, final double y, final double z) {
        this.df = new SimpleDateFormat("dd/MM/yyyy");
        this.today = Calendar.getInstance().getTime();
        this.renderDate = this.df.format(this.today);
        this.dff = new SimpleDateFormat("HH:mm:ss");
        this.todayy = Calendar.getInstance().getTime();
        this.renderTime = this.dff.format(this.todayy);
        this.setText(text);
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setDate(String.valueOf(String.valueOf(String.valueOf(String.valueOf(this.renderDate))) + " | " + this.renderTime));
    }
    
    public void spawnHologram() {
        this.item = new ItemStack(Items.armor_stand);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("CustomName", this.text);
        nbtTagCompound.setInteger("CustomNameVisible", 1);
        nbtTagCompound.setInteger("Invisible", 1);
        final NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagDouble(this.x));
        list.appendTag(new NBTTagDouble(this.y));
        list.appendTag(new NBTTagDouble(this.z));
        nbtTagCompound.setTag("Pos", list);
        tagCompound.setTag("EntityTag", nbtTagCompound);
        this.item.setTagCompound(tagCompound);
        Minecraft.getMinecraft();
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, this.item));
        this.item.setTagCompound(null);
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public void setItem(final ItemStack item) {
        this.item = item;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public String getText() {
        return this.text;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setDate(final String date) {
        this.date = date;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
}
