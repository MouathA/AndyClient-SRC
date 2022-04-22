package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation textureTrappedDouble;
    private static final ResourceLocation textureChristmasDouble;
    private static final ResourceLocation textureNormalDouble;
    private static final ResourceLocation textureTrapped;
    private static final ResourceLocation textureChristmas;
    private static final ResourceLocation textureNormal;
    private ModelChest simpleChest;
    private ModelChest largeChest;
    private boolean isChristams;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000965";
        textureTrappedDouble = new ResourceLocation("textures/entity/chest/trapped_double.png");
        textureChristmasDouble = new ResourceLocation("textures/entity/chest/christmas_double.png");
        textureNormalDouble = new ResourceLocation("textures/entity/chest/normal_double.png");
        textureTrapped = new ResourceLocation("textures/entity/chest/trapped.png");
        textureChristmas = new ResourceLocation("textures/entity/chest/christmas.png");
        textureNormal = new ResourceLocation("textures/entity/chest/normal.png");
    }
    
    public TileEntityChestRenderer() {
        this.simpleChest = new ModelChest();
        this.largeChest = new ModelLargeChest();
        final Calendar instance = Calendar.getInstance();
        if (instance.get(2) + 1 == 12 && instance.get(5) >= 24 && instance.get(5) <= 26) {
            this.isChristams = true;
        }
    }
    
    public void func_180538_a(final TileEntityChest tileEntityChest, final double n, final double n2, final double n3, final float n4, final int n5) {
        if (tileEntityChest.hasWorldObj()) {
            final Block blockType = tileEntityChest.getBlockType();
            tileEntityChest.getBlockMetadata();
            if (blockType instanceof BlockChest && !false) {
                ((BlockChest)blockType).checkForSurroundingChests(tileEntityChest.getWorld(), tileEntityChest.getPos(), tileEntityChest.getWorld().getBlockState(tileEntityChest.getPos()));
                tileEntityChest.getBlockMetadata();
            }
            tileEntityChest.checkForAdjacentChests();
        }
        if (tileEntityChest.adjacentChestZNeg == null && tileEntityChest.adjacentChestXNeg == null) {
            ModelChest modelChest;
            if (tileEntityChest.adjacentChestXPos == null && tileEntityChest.adjacentChestZPos == null) {
                modelChest = this.simpleChest;
                if (n5 >= 0) {
                    this.bindTexture(TileEntityChestRenderer.DESTROY_STAGES[n5]);
                    GlStateManager.matrixMode(5890);
                    GlStateManager.scale(4.0f, 4.0f, 1.0f);
                    GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
                    GlStateManager.matrixMode(5888);
                }
                else if (tileEntityChest.getChestType() == 1) {
                    this.bindTexture(TileEntityChestRenderer.textureTrapped);
                }
                else if (this.isChristams) {
                    this.bindTexture(TileEntityChestRenderer.textureChristmas);
                }
                else {
                    this.bindTexture(TileEntityChestRenderer.textureNormal);
                }
            }
            else {
                modelChest = this.largeChest;
                if (n5 >= 0) {
                    this.bindTexture(TileEntityChestRenderer.DESTROY_STAGES[n5]);
                    GlStateManager.matrixMode(5890);
                    GlStateManager.scale(8.0f, 4.0f, 1.0f);
                    GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
                    GlStateManager.matrixMode(5888);
                }
                else if (tileEntityChest.getChestType() == 1) {
                    this.bindTexture(TileEntityChestRenderer.textureTrappedDouble);
                }
                else if (this.isChristams) {
                    this.bindTexture(TileEntityChestRenderer.textureChristmasDouble);
                }
                else {
                    this.bindTexture(TileEntityChestRenderer.textureNormalDouble);
                }
            }
            if (n5 < 0) {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            }
            GlStateManager.translate((float)n, (float)n2 + 1.0f, (float)n3 + 1.0f);
            GlStateManager.scale(1.0f, -1.0f, -1.0f);
            GlStateManager.translate(0.5f, 0.5f, 0.5f);
            if (0 == 2) {}
            if (0 == 3) {}
            if (0 == 4) {}
            if (0 == 5) {}
            if (0 == 2 && tileEntityChest.adjacentChestXPos != null) {
                GlStateManager.translate(1.0f, 0.0f, 0.0f);
            }
            if (0 == 5 && tileEntityChest.adjacentChestZPos != null) {
                GlStateManager.translate(0.0f, 0.0f, -1.0f);
            }
            GlStateManager.rotate(-90, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, -0.5f);
            float n6 = tileEntityChest.prevLidAngle + (tileEntityChest.lidAngle - tileEntityChest.prevLidAngle) * n4;
            if (tileEntityChest.adjacentChestZNeg != null) {
                final float n7 = tileEntityChest.adjacentChestZNeg.prevLidAngle + (tileEntityChest.adjacentChestZNeg.lidAngle - tileEntityChest.adjacentChestZNeg.prevLidAngle) * n4;
                if (n7 > n6) {
                    n6 = n7;
                }
            }
            if (tileEntityChest.adjacentChestXNeg != null) {
                final float n8 = tileEntityChest.adjacentChestXNeg.prevLidAngle + (tileEntityChest.adjacentChestXNeg.lidAngle - tileEntityChest.adjacentChestXNeg.prevLidAngle) * n4;
                if (n8 > n6) {
                    n6 = n8;
                }
            }
            final float n9 = 1.0f - n6;
            modelChest.chestLid.rotateAngleX = -((1.0f - n9 * n9 * n9) * 3.1415927f / 2.0f);
            modelChest.renderAll();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (n5 >= 0) {
                GlStateManager.matrixMode(5890);
                GlStateManager.matrixMode(5888);
            }
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.func_180538_a((TileEntityChest)tileEntity, n, n2, n3, n4, n5);
    }
}
