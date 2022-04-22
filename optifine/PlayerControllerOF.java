package optifine;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;

public class PlayerControllerOF extends PlayerControllerMP
{
    private boolean acting;
    
    public PlayerControllerOF(final Minecraft minecraft, final NetHandlerPlayClient netHandlerPlayClient) {
        super(minecraft, netHandlerPlayClient);
        this.acting = false;
    }
    
    @Override
    public boolean func_180511_b(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.acting = true;
        final boolean func_180511_b = super.func_180511_b(blockPos, enumFacing);
        this.acting = false;
        return func_180511_b;
    }
    
    @Override
    public boolean func_180512_c(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.acting = true;
        final boolean func_180512_c = super.func_180512_c(blockPos, enumFacing);
        this.acting = false;
        return func_180512_c;
    }
    
    @Override
    public boolean sendUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack) {
        this.acting = true;
        final boolean sendUseItem = super.sendUseItem(entityPlayer, world, itemStack);
        this.acting = false;
        return sendUseItem;
    }
    
    @Override
    public boolean func_178890_a(final EntityPlayerSP entityPlayerSP, final WorldClient worldClient, final ItemStack itemStack, final BlockPos blockPos, final EnumFacing enumFacing, final Vec3 vec3) {
        this.acting = true;
        final boolean func_178890_a = super.func_178890_a(entityPlayerSP, worldClient, itemStack, blockPos, enumFacing, vec3);
        this.acting = false;
        return func_178890_a;
    }
    
    public boolean isActing() {
        return this.acting;
    }
}
