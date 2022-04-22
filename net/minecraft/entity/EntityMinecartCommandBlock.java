package net.minecraft.entity;

import net.minecraft.entity.item.*;
import net.minecraft.command.server.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;

public class EntityMinecartCommandBlock extends EntityMinecart
{
    private final CommandBlockLogic field_145824_a;
    private int field_145823_b;
    private static final String __OBFID;
    
    public EntityMinecartCommandBlock(final World world) {
        super(world);
        this.field_145824_a = new CommandBlockLogic() {
            private static final String __OBFID;
            final EntityMinecartCommandBlock this$0;
            
            @Override
            public void func_145756_e() {
                this.this$0.getDataWatcher().updateObject(23, this.getCustomName());
                this.this$0.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(this.getLastOutput()));
            }
            
            @Override
            public int func_145751_f() {
                return 1;
            }
            
            @Override
            public void func_145757_a(final ByteBuf byteBuf) {
                byteBuf.writeInt(this.this$0.getEntityId());
            }
            
            @Override
            public BlockPos getPosition() {
                return new BlockPos(this.this$0.posX, this.this$0.posY + 0.5, this.this$0.posZ);
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.posX, this.this$0.posY, this.this$0.posZ);
            }
            
            @Override
            public World getEntityWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.this$0;
            }
            
            static {
                __OBFID = "CL_00001673";
            }
        };
        this.field_145823_b = 0;
    }
    
    public EntityMinecartCommandBlock(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.field_145824_a = new CommandBlockLogic() {
            private static final String __OBFID;
            final EntityMinecartCommandBlock this$0;
            
            @Override
            public void func_145756_e() {
                this.this$0.getDataWatcher().updateObject(23, this.getCustomName());
                this.this$0.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(this.getLastOutput()));
            }
            
            @Override
            public int func_145751_f() {
                return 1;
            }
            
            @Override
            public void func_145757_a(final ByteBuf byteBuf) {
                byteBuf.writeInt(this.this$0.getEntityId());
            }
            
            @Override
            public BlockPos getPosition() {
                return new BlockPos(this.this$0.posX, this.this$0.posY + 0.5, this.this$0.posZ);
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.posX, this.this$0.posY, this.this$0.posZ);
            }
            
            @Override
            public World getEntityWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.this$0;
            }
            
            static {
                __OBFID = "CL_00001673";
            }
        };
        this.field_145823_b = 0;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(23, "");
        this.getDataWatcher().addObject(24, "");
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.field_145824_a.readDataFromNBT(nbtTagCompound);
        this.getDataWatcher().updateObject(23, this.func_145822_e().getCustomName());
        this.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(this.func_145822_e().getLastOutput()));
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        this.field_145824_a.writeDataToNBT(nbtTagCompound);
    }
    
    @Override
    public EnumMinecartType func_180456_s() {
        return EnumMinecartType.COMMAND_BLOCK;
    }
    
    @Override
    public IBlockState func_180457_u() {
        return Blocks.command_block.getDefaultState();
    }
    
    public CommandBlockLogic func_145822_e() {
        return this.field_145824_a;
    }
    
    @Override
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
        if (b && this.ticksExisted - this.field_145823_b >= 4) {
            this.func_145822_e().trigger(this.worldObj);
            this.field_145823_b = this.ticksExisted;
        }
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        this.field_145824_a.func_175574_a(entityPlayer);
        return false;
    }
    
    @Override
    public void func_145781_i(final int n) {
        super.func_145781_i(n);
        if (n == 24) {
            this.field_145824_a.func_145750_b(IChatComponent.Serializer.jsonToComponent(this.getDataWatcher().getWatchableObjectString(24)));
        }
        else if (n == 23) {
            this.field_145824_a.setCommand(this.getDataWatcher().getWatchableObjectString(23));
        }
    }
    
    static {
        __OBFID = "CL_00001672";
    }
}
