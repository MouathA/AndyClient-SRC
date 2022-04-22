package net.minecraft.tileentity;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.command.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.event.*;
import net.minecraft.server.*;
import net.minecraft.util.*;

public class TileEntitySign extends TileEntity
{
    public IChatComponent[] signText;
    public int lineBeingEdited;
    private boolean isEditable;
    private EntityPlayer field_145917_k;
    private final CommandResultStats field_174883_i;
    private static final String __OBFID;
    
    public TileEntitySign() {
        this.signText = new IChatComponent[] { new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("") };
        this.lineBeingEdited = -1;
        this.isEditable = true;
        this.field_174883_i = new CommandResultStats();
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        while (0 < 4) {
            nbtTagCompound.setString("Text" + 1, IChatComponent.Serializer.componentToJson(this.signText[0]));
            int n = 0;
            ++n;
        }
        this.field_174883_i.func_179670_b(nbtTagCompound);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.isEditable = false;
        super.readFromNBT(nbtTagCompound);
        final ICommandSender commandSender = new ICommandSender() {
            private static final String __OBFID;
            final TileEntitySign this$0;
            
            @Override
            public String getName() {
                return "Sign";
            }
            
            @Override
            public IChatComponent getDisplayName() {
                return new ChatComponentText(this.getName());
            }
            
            @Override
            public void addChatMessage(final IChatComponent chatComponent) {
            }
            
            @Override
            public boolean canCommandSenderUseCommand(final int n, final String s) {
                return true;
            }
            
            @Override
            public BlockPos getPosition() {
                return this.this$0.pos;
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.pos.getX() + 0.5, this.this$0.pos.getY() + 0.5, this.this$0.pos.getZ() + 0.5);
            }
            
            @Override
            public World getEntityWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return null;
            }
            
            @Override
            public boolean sendCommandFeedback() {
                return false;
            }
            
            @Override
            public void func_174794_a(final CommandResultStats.Type type, final int n) {
            }
            
            static {
                __OBFID = "CL_00002039";
            }
        };
        while (0 < 4) {
            this.signText[0] = ChatComponentProcessor.func_179985_a(commandSender, IChatComponent.Serializer.jsonToComponent(nbtTagCompound.getString("Text" + 1)), null);
            int n = 0;
            ++n;
        }
        this.field_174883_i.func_179668_a(nbtTagCompound);
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final IChatComponent[] array = new IChatComponent[4];
        System.arraycopy(this.signText, 0, array, 0, 4);
        return new S33PacketUpdateSign(this.worldObj, this.pos, array);
    }
    
    public boolean getIsEditable() {
        return this.isEditable;
    }
    
    public void setEditable(final boolean isEditable) {
        if (!(this.isEditable = isEditable)) {
            this.field_145917_k = null;
        }
    }
    
    public void func_145912_a(final EntityPlayer field_145917_k) {
        this.field_145917_k = field_145917_k;
    }
    
    public EntityPlayer func_145911_b() {
        return this.field_145917_k;
    }
    
    public boolean func_174882_b(final EntityPlayer entityPlayer) {
        final ICommandSender commandSender = new ICommandSender(entityPlayer) {
            private static final String __OBFID;
            final TileEntitySign this$0;
            private final EntityPlayer val$p_174882_1_;
            
            @Override
            public String getName() {
                return this.val$p_174882_1_.getName();
            }
            
            @Override
            public IChatComponent getDisplayName() {
                return this.val$p_174882_1_.getDisplayName();
            }
            
            @Override
            public void addChatMessage(final IChatComponent chatComponent) {
            }
            
            @Override
            public boolean canCommandSenderUseCommand(final int n, final String s) {
                return true;
            }
            
            @Override
            public BlockPos getPosition() {
                return this.this$0.pos;
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.pos.getX() + 0.5, this.this$0.pos.getY() + 0.5, this.this$0.pos.getZ() + 0.5);
            }
            
            @Override
            public World getEntityWorld() {
                return this.val$p_174882_1_.getEntityWorld();
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.val$p_174882_1_;
            }
            
            @Override
            public boolean sendCommandFeedback() {
                return false;
            }
            
            @Override
            public void func_174794_a(final CommandResultStats.Type type, final int n) {
                TileEntitySign.access$0(this.this$0).func_179672_a(this, type, n);
            }
            
            static {
                __OBFID = "CL_00002038";
            }
        };
        while (0 < this.signText.length) {
            final ChatStyle chatStyle = (this.signText[0] == null) ? null : this.signText[0].getChatStyle();
            if (chatStyle != null && chatStyle.getChatClickEvent() != null) {
                final ClickEvent chatClickEvent = chatStyle.getChatClickEvent();
                if (chatClickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    MinecraftServer.getServer().getCommandManager().executeCommand(commandSender, chatClickEvent.getValue());
                }
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public CommandResultStats func_174880_d() {
        return this.field_174883_i;
    }
    
    static CommandResultStats access$0(final TileEntitySign tileEntitySign) {
        return tileEntitySign.field_174883_i;
    }
    
    static {
        __OBFID = "CL_00000363";
    }
}
