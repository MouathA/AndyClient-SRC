package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer
{
    private static final String __OBFID;
    
    public void func_180539_a(final TileEntityMobSpawner tileEntityMobSpawner, final double n, final double n2, final double n3, final float n4, final int n5) {
        GlStateManager.translate((float)n + 0.5f, (float)n2, (float)n3 + 0.5f);
        func_147517_a(tileEntityMobSpawner.getSpawnerBaseLogic(), n, n2, n3, n4);
    }
    
    public static void func_147517_a(final MobSpawnerBaseLogic mobSpawnerBaseLogic, final double n, final double n2, final double n3, final float n4) {
        final Entity func_180612_a = mobSpawnerBaseLogic.func_180612_a(mobSpawnerBaseLogic.getSpawnerWorld());
        if (func_180612_a != null) {
            final float n5 = 0.4375f;
            GlStateManager.translate(0.0f, 0.4f, 0.0f);
            GlStateManager.rotate((float)(mobSpawnerBaseLogic.func_177223_e() + (mobSpawnerBaseLogic.func_177222_d() - mobSpawnerBaseLogic.func_177223_e()) * n4) * 10.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-30.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.4f, 0.0f);
            GlStateManager.scale(n5, n5, n5);
            func_180612_a.setLocationAndAngles(n, n2, n3, 0.0f, 0.0f);
            Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(func_180612_a, 0.0, 0.0, 0.0, 0.0f, n4);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.func_180539_a((TileEntityMobSpawner)tileEntity, n, n2, n3, n4, n5);
    }
    
    static {
        __OBFID = "CL_00000968";
    }
}
