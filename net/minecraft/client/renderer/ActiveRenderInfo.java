package net.minecraft.client.renderer;

import java.nio.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class ActiveRenderInfo
{
    private static final IntBuffer field_178814_a;
    private static final FloatBuffer field_178812_b;
    private static final FloatBuffer field_178813_c;
    private static final FloatBuffer field_178810_d;
    private static Vec3 field_178811_e;
    private static float rotationX;
    private static float rotationXZ;
    private static float rotationZ;
    private static float rotationYZ;
    private static float rotationXY;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000626";
        field_178814_a = GLAllocation.createDirectIntBuffer(16);
        field_178812_b = GLAllocation.createDirectFloatBuffer(16);
        field_178813_c = GLAllocation.createDirectFloatBuffer(16);
        field_178810_d = GLAllocation.createDirectFloatBuffer(3);
        ActiveRenderInfo.field_178811_e = new Vec3(0.0, 0.0, 0.0);
    }
    
    public static void updateRenderInfo(final EntityPlayer entityPlayer, final boolean b) {
        GlStateManager.getFloat(2982, ActiveRenderInfo.field_178812_b);
        GlStateManager.getFloat(2983, ActiveRenderInfo.field_178813_c);
        GL11.glGetInteger(2978, ActiveRenderInfo.field_178814_a);
        GLU.gluUnProject((float)((ActiveRenderInfo.field_178814_a.get(0) + ActiveRenderInfo.field_178814_a.get(2)) / 2), (float)((ActiveRenderInfo.field_178814_a.get(1) + ActiveRenderInfo.field_178814_a.get(3)) / 2), 0.0f, ActiveRenderInfo.field_178812_b, ActiveRenderInfo.field_178813_c, ActiveRenderInfo.field_178814_a, ActiveRenderInfo.field_178810_d);
        ActiveRenderInfo.field_178811_e = new Vec3(ActiveRenderInfo.field_178810_d.get(0), ActiveRenderInfo.field_178810_d.get(1), ActiveRenderInfo.field_178810_d.get(2));
        final int n = b ? 1 : 0;
        final float rotationPitch = entityPlayer.rotationPitch;
        final float rotationYaw = entityPlayer.rotationYaw;
        ActiveRenderInfo.rotationX = MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * (1 - n * 2);
        ActiveRenderInfo.rotationZ = MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * (1 - n * 2);
        ActiveRenderInfo.rotationYZ = -ActiveRenderInfo.rotationZ * MathHelper.sin(rotationPitch * 3.1415927f / 180.0f) * (1 - n * 2);
        ActiveRenderInfo.rotationXY = ActiveRenderInfo.rotationX * MathHelper.sin(rotationPitch * 3.1415927f / 180.0f) * (1 - n * 2);
        ActiveRenderInfo.rotationXZ = MathHelper.cos(rotationPitch * 3.1415927f / 180.0f);
    }
    
    public static Vec3 func_178806_a(final Entity entity, final double n) {
        return new Vec3(entity.prevPosX + (entity.posX - entity.prevPosX) * n + ActiveRenderInfo.field_178811_e.xCoord, entity.prevPosY + (entity.posY - entity.prevPosY) * n + ActiveRenderInfo.field_178811_e.yCoord, entity.prevPosZ + (entity.posZ - entity.prevPosZ) * n + ActiveRenderInfo.field_178811_e.zCoord);
    }
    
    public static Block func_180786_a(final World world, final Entity entity, final float n) {
        final Vec3 func_178806_a = func_178806_a(entity, n);
        final BlockPos blockPos = new BlockPos(func_178806_a);
        final IBlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block.getMaterial().isLiquid()) {
            float n2 = 0.0f;
            if (blockState.getBlock() instanceof BlockLiquid) {
                n2 = BlockLiquid.getLiquidHeightPercent((int)blockState.getValue(BlockLiquid.LEVEL)) - 0.11111111f;
            }
            if (func_178806_a.yCoord >= blockPos.getY() + 1 - n2) {
                block = world.getBlockState(blockPos.offsetUp()).getBlock();
            }
        }
        return block;
    }
    
    public static Vec3 func_178804_a() {
        return ActiveRenderInfo.field_178811_e;
    }
    
    public static float func_178808_b() {
        return ActiveRenderInfo.rotationX;
    }
    
    public static float func_178809_c() {
        return ActiveRenderInfo.rotationXZ;
    }
    
    public static float func_178803_d() {
        return ActiveRenderInfo.rotationZ;
    }
    
    public static float func_178805_e() {
        return ActiveRenderInfo.rotationYZ;
    }
    
    public static float func_178807_f() {
        return ActiveRenderInfo.rotationXY;
    }
}
