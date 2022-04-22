package DTool.util;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import net.minecraft.client.resources.model.*;

public class ItemPhysicUtils
{
    public static Random random;
    public static Minecraft mc;
    public static RenderItem renderItem;
    public static long tick;
    public static double rotation;
    public static final ResourceLocation RES_ITEM_GLINT;
    
    static {
        ItemPhysicUtils.random = new Random();
        ItemPhysicUtils.mc = Minecraft.getMinecraft();
        ItemPhysicUtils.renderItem = ItemPhysicUtils.mc.getRenderItem();
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
    
    private static boolean isBlock(final ItemStack itemStack) {
        Block.getBlockFromItem(itemStack.getItem()).equals("");
        return true;
    }
    
    public static void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        ItemPhysicUtils.rotation = (System.nanoTime() - ItemPhysicUtils.tick) / 3000000.0 * 1.0;
        if (!ItemPhysicUtils.mc.inGameHasFocus) {
            ItemPhysicUtils.rotation = 0.0;
        }
        final EntityItem entityItem = (EntityItem)entity;
        final ItemStack entityItem2 = entityItem.getEntityItem();
        if (entityItem2.getItem() != null) {
            ItemPhysicUtils.random.setSeed(187L);
            if (TextureMap.locationBlocksTexture != null) {
                ItemPhysicUtils.mc.getRenderManager().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                ItemPhysicUtils.mc.getRenderManager().renderEngine.getTexture(TextureMap.locationBlocksTexture).func_174936_b(false, false);
            }
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final IBakedModel itemModel = ItemPhysicUtils.renderItem.getItemModelMesher().getItemModel(entityItem2);
            final int func_177077_a = func_177077_a(entityItem, n, n2, n3, n5, itemModel);
            final BlockPos blockPos = new BlockPos(entityItem);
            if (entityItem.rotationPitch > 360.0f) {
                entityItem.rotationPitch = 0.0f;
            }
            if (entityItem != null && !Double.isNaN(entityItem.func_174872_o()) && !Double.isNaN(entityItem.getAir()) && !Double.isNaN(entityItem.getEntityId()) && entityItem.getPosition() != null) {
                if (entityItem.onGround) {
                    if (entityItem.rotationPitch != 0.0f && entityItem.rotationPitch != 90.0f && entityItem.rotationPitch != 180.0f && entityItem.rotationPitch != 270.0f) {
                        final double formPositiv = formPositiv(entityItem.rotationPitch);
                        final double formPositiv2 = formPositiv(entityItem.rotationPitch - 90.0f);
                        final double formPositiv3 = formPositiv(entityItem.rotationPitch - 180.0f);
                        final double formPositiv4 = formPositiv(entityItem.rotationPitch - 270.0f);
                        if (formPositiv <= formPositiv2 && formPositiv <= formPositiv3 && formPositiv <= formPositiv4) {
                            if (entityItem.rotationPitch < 0.0f) {
                                entityItem.rotationPitch += (float)ItemPhysicUtils.rotation;
                            }
                            else {
                                entityItem.rotationPitch -= (float)ItemPhysicUtils.rotation;
                            }
                        }
                        if (formPositiv2 < formPositiv && formPositiv2 <= formPositiv3 && formPositiv2 <= formPositiv4) {
                            if (entityItem.rotationPitch - 90.0f < 0.0f) {
                                entityItem.rotationPitch += (float)ItemPhysicUtils.rotation;
                            }
                            else {
                                entityItem.rotationPitch -= (float)ItemPhysicUtils.rotation;
                            }
                        }
                        if (formPositiv3 < formPositiv2 && formPositiv3 < formPositiv && formPositiv3 <= formPositiv4) {
                            if (entityItem.rotationPitch - 180.0f < 0.0f) {
                                entityItem.rotationPitch += (float)ItemPhysicUtils.rotation;
                            }
                            else {
                                entityItem.rotationPitch -= (float)ItemPhysicUtils.rotation;
                            }
                        }
                        if (formPositiv4 < formPositiv2 && formPositiv4 < formPositiv3 && formPositiv4 < formPositiv) {
                            if (entityItem.rotationPitch - 270.0f < 0.0f) {
                                entityItem.rotationPitch += (float)ItemPhysicUtils.rotation;
                            }
                            else {
                                entityItem.rotationPitch -= (float)ItemPhysicUtils.rotation;
                            }
                        }
                    }
                }
                else {
                    final BlockPos blockPos2 = new BlockPos(entityItem);
                    blockPos2.add(0, 1, 0);
                    if (entityItem.isInsideOfMaterial(Material.water) | entityItem.worldObj.getBlockState(blockPos2).getBlock().getMaterial() == Material.water | entityItem.isInWater() | entityItem.worldObj.getBlockState(blockPos).getBlock().getMaterial() == Material.water) {
                        entityItem.rotationPitch += (float)(ItemPhysicUtils.rotation / 4.0);
                    }
                    else {
                        entityItem.rotationPitch += (float)(ItemPhysicUtils.rotation * 2.0);
                    }
                }
            }
            GL11.glRotatef(entityItem.rotationYaw, 0.0f, 0.2f, 0.0f);
            GL11.glRotatef(entityItem.rotationPitch + 90.0f, 1.0f, 0.0f, 0.0f);
            while (0 < func_177077_a) {
                if (itemModel.isAmbientOcclusionEnabled()) {
                    GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    ItemPhysicUtils.renderItem.func_180454_a(entityItem2, itemModel);
                }
                else {
                    if (isBlock(entityItem2)) {
                        GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    }
                    else if (entityItem2.getItem() == Items.skull || Block.getBlockFromItem(entityItem2.getItem()) == Blocks.skull) {
                        GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    }
                    ItemPhysicUtils.renderItem.func_180454_a(entityItem2, itemModel);
                    if (!shouldSpreadItems()) {
                        GlStateManager.translate(0.0f, 0.0f, 0.046875f);
                    }
                }
                int n6 = 0;
                ++n6;
            }
            ItemPhysicUtils.mc.getRenderManager().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            ItemPhysicUtils.mc.getRenderManager().renderEngine.getTexture(TextureMap.locationBlocksTexture).func_174935_a();
        }
    }
    
    public static int func_177077_a(final EntityItem entityItem, final double n, final double n2, final double n3, final float n4, final IBakedModel bakedModel) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        if (entityItem2.getItem() == null) {
            return 0;
        }
        final boolean ambientOcclusionEnabled = bakedModel.isAmbientOcclusionEnabled();
        final int func_177078_a = func_177078_a(entityItem2);
        GlStateManager.translate((float)n, (float)n2 + 0.0f + 0.25f, (float)n3);
        final float n5 = 0.0f;
        if (ambientOcclusionEnabled || (ItemPhysicUtils.mc.getRenderManager().renderEngine != null && ItemPhysicUtils.mc.gameSettings.fancyGraphics)) {
            GlStateManager.rotate(n5, 0.0f, 1.0f, 0.0f);
        }
        if (!ambientOcclusionEnabled) {
            GlStateManager.translate(-0.0f * (func_177078_a - 1) * 0.5f, -0.0f * (func_177078_a - 1) * 0.5f, -0.046875f * (func_177078_a - 1) * 0.5f);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return func_177078_a;
    }
    
    public static boolean shouldSpreadItems() {
        return true;
    }
    
    public static double formPositiv(final float n) {
        if (n > 0.0f) {
            return n;
        }
        return -n;
    }
    
    public static int func_177078_a(final ItemStack itemStack) {
        if (itemStack.animationsToGo <= 48) {
            if (itemStack.animationsToGo <= 32) {
                if (itemStack.animationsToGo <= 16) {
                    if (itemStack.animationsToGo > 1) {}
                }
            }
        }
        return 2;
    }
    
    public static byte getMiniBlockCount(final ItemStack itemStack, final byte b) {
        return b;
    }
    
    public static byte getMiniItemCount(final ItemStack itemStack, final byte b) {
        return b;
    }
}
