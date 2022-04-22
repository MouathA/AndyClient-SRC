package net.minecraft.tileentity;

import net.minecraft.command.server.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.command.*;

public class TileEntityCommandBlock extends TileEntity
{
    private final CommandBlockLogic field_145994_a;
    private static final String __OBFID;
    
    public TileEntityCommandBlock() {
        this.field_145994_a = new CommandBlockLogic() {
            private static final String __OBFID;
            final TileEntityCommandBlock this$0;
            
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
                return this.this$0.getWorld();
            }
            
            @Override
            public void setCommand(final String command) {
                super.setCommand(command);
                this.this$0.markDirty();
            }
            
            @Override
            public void func_145756_e() {
                this.this$0.getWorld().markBlockForUpdate(this.this$0.pos);
            }
            
            @Override
            public int func_145751_f() {
                return 0;
            }
            
            @Override
            public void func_145757_a(final ByteBuf byteBuf) {
                byteBuf.writeInt(this.this$0.pos.getX());
                byteBuf.writeInt(this.this$0.pos.getY());
                byteBuf.writeInt(this.this$0.pos.getZ());
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return null;
            }
            
            static {
                __OBFID = "CL_00000348";
            }
        };
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        this.field_145994_a.writeDataToNBT(nbtTagCompound);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.field_145994_a.readDataFromNBT(nbtTagCompound);
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, 2, nbtTagCompound);
    }
    
    public CommandBlockLogic getCommandBlockLogic() {
        return this.field_145994_a;
    }
    
    public CommandResultStats func_175124_c() {
        return this.field_145994_a.func_175572_n();
    }
    
    static {
        __OBFID = "CL_00000347";
    }
}
