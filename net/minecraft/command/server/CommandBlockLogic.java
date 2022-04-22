package net.minecraft.command.server;

import java.text.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;
import io.netty.buffer.*;
import net.minecraft.entity.player.*;

public abstract class CommandBlockLogic implements ICommandSender
{
    private static final SimpleDateFormat timestampFormat;
    private int successCount;
    private boolean trackOutput;
    private IChatComponent lastOutput;
    private String commandStored;
    private String customName;
    private final CommandResultStats field_175575_g;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000128";
        timestampFormat = new SimpleDateFormat("HH:mm:ss");
    }
    
    public CommandBlockLogic() {
        this.trackOutput = true;
        this.lastOutput = null;
        this.commandStored = "";
        this.customName = "@";
        this.field_175575_g = new CommandResultStats();
    }
    
    public int getSuccessCount() {
        return this.successCount;
    }
    
    public IChatComponent getLastOutput() {
        return this.lastOutput;
    }
    
    public void writeDataToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setString("Command", this.commandStored);
        nbtTagCompound.setInteger("SuccessCount", this.successCount);
        nbtTagCompound.setString("CustomName", this.customName);
        nbtTagCompound.setBoolean("TrackOutput", this.trackOutput);
        if (this.lastOutput != null && this.trackOutput) {
            nbtTagCompound.setString("LastOutput", IChatComponent.Serializer.componentToJson(this.lastOutput));
        }
        this.field_175575_g.func_179670_b(nbtTagCompound);
    }
    
    public void readDataFromNBT(final NBTTagCompound nbtTagCompound) {
        this.commandStored = nbtTagCompound.getString("Command");
        this.successCount = nbtTagCompound.getInteger("SuccessCount");
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.customName = nbtTagCompound.getString("CustomName");
        }
        if (nbtTagCompound.hasKey("TrackOutput", 1)) {
            this.trackOutput = nbtTagCompound.getBoolean("TrackOutput");
        }
        if (nbtTagCompound.hasKey("LastOutput", 8) && this.trackOutput) {
            this.lastOutput = IChatComponent.Serializer.jsonToComponent(nbtTagCompound.getString("LastOutput"));
        }
        this.field_175575_g.func_179668_a(nbtTagCompound);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return n <= 2;
    }
    
    public void setCommand(final String commandStored) {
        this.commandStored = commandStored;
        this.successCount = 0;
    }
    
    public String getCustomName() {
        return this.commandStored;
    }
    
    public void trigger(final World world) {
        if (world.isRemote) {
            this.successCount = 0;
        }
        final MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server.func_175578_N() && server.isCommandBlockEnabled()) {
            final ICommandManager commandManager = server.getCommandManager();
            this.lastOutput = null;
            this.successCount = commandManager.executeCommand(this, this.commandStored);
        }
        else {
            this.successCount = 0;
        }
    }
    
    @Override
    public String getName() {
        return this.customName;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }
    
    public void func_145754_b(final String customName) {
        this.customName = customName;
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        if (this.trackOutput && this.getEntityWorld() != null && !this.getEntityWorld().isRemote) {
            this.lastOutput = new ChatComponentText("[" + CommandBlockLogic.timestampFormat.format(new Date()) + "] ").appendSibling(chatComponent);
            this.func_145756_e();
        }
    }
    
    @Override
    public boolean sendCommandFeedback() {
        final MinecraftServer server = MinecraftServer.getServer();
        return server == null || !server.func_175578_N() || server.worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput");
    }
    
    @Override
    public void func_174794_a(final CommandResultStats.Type type, final int n) {
        this.field_175575_g.func_179672_a(this, type, n);
    }
    
    public abstract void func_145756_e();
    
    public abstract int func_145751_f();
    
    public abstract void func_145757_a(final ByteBuf p0);
    
    public void func_145750_b(final IChatComponent lastOutput) {
        this.lastOutput = lastOutput;
    }
    
    public void func_175573_a(final boolean trackOutput) {
        this.trackOutput = trackOutput;
    }
    
    public boolean func_175571_m() {
        return this.trackOutput;
    }
    
    public boolean func_175574_a(final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            return false;
        }
        if (entityPlayer.getEntityWorld().isRemote) {
            entityPlayer.func_146095_a(this);
        }
        return true;
    }
    
    public CommandResultStats func_175572_n() {
        return this.field_175575_g;
    }
}
