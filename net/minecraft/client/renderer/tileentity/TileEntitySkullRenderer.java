package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.*;
import net.minecraft.util.*;
import com.mojang.authlib.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.tileentity.*;

public class TileEntitySkullRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147537_c;
    private static final ResourceLocation field_147534_d;
    private static final ResourceLocation field_147535_e;
    private static final ResourceLocation field_147532_f;
    public static TileEntitySkullRenderer instance;
    private final ModelSkeletonHead field_178467_h;
    private final ModelSkeletonHead field_178468_i;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000971";
        field_147537_c = new ResourceLocation("textures/entity/skeleton/skeleton.png");
        field_147534_d = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
        field_147535_e = new ResourceLocation("textures/entity/zombie/zombie.png");
        field_147532_f = new ResourceLocation("textures/entity/creeper/creeper.png");
    }
    
    public TileEntitySkullRenderer() {
        this.field_178467_h = new ModelSkeletonHead(0, 0, 64, 32);
        this.field_178468_i = new ModelHumanoidHead();
    }
    
    public void func_180542_a(final TileEntitySkull tileEntitySkull, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderSkull((float)n, (float)n2, (float)n3, EnumFacing.getFront(tileEntitySkull.getBlockMetadata() & 0x7), tileEntitySkull.getSkullRotation() * 360 / 16.0f, tileEntitySkull.getSkullType(), tileEntitySkull.getPlayerProfile(), n5);
    }
    
    @Override
    public void setRendererDispatcher(final TileEntityRendererDispatcher rendererDispatcher) {
        super.setRendererDispatcher(rendererDispatcher);
        TileEntitySkullRenderer.instance = this;
    }
    
    public void renderSkull(final float n, final float n2, final float n3, final EnumFacing enumFacing, float n4, final int n5, final GameProfile gameProfile, final int n6) {
        ModelSkeletonHead modelSkeletonHead = this.field_178467_h;
        if (n6 >= 0) {
            this.bindTexture(TileEntitySkullRenderer.DESTROY_STAGES[n6]);
            GlStateManager.matrixMode(5890);
            GlStateManager.scale(4.0f, 2.0f, 1.0f);
            GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.matrixMode(5888);
        }
        else {
            switch (n5) {
                default: {
                    this.bindTexture(TileEntitySkullRenderer.field_147537_c);
                    break;
                }
                case 1: {
                    this.bindTexture(TileEntitySkullRenderer.field_147534_d);
                    break;
                }
                case 2: {
                    this.bindTexture(TileEntitySkullRenderer.field_147535_e);
                    modelSkeletonHead = this.field_178468_i;
                    break;
                }
                case 3: {
                    modelSkeletonHead = this.field_178468_i;
                    ResourceLocation resourceLocation = DefaultPlayerSkin.getDefaultSkinLegacy();
                    if (gameProfile != null) {
                        final Minecraft minecraft = Minecraft.getMinecraft();
                        final Map loadSkinFromCache = minecraft.getSkinManager().loadSkinFromCache(gameProfile);
                        if (loadSkinFromCache.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                            resourceLocation = minecraft.getSkinManager().loadSkin(loadSkinFromCache.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
                        }
                        else {
                            resourceLocation = DefaultPlayerSkin.func_177334_a(EntityPlayer.getUUID(gameProfile));
                        }
                    }
                    this.bindTexture(resourceLocation);
                    break;
                }
                case 4: {
                    this.bindTexture(TileEntitySkullRenderer.field_147532_f);
                    break;
                }
            }
        }
        if (enumFacing != EnumFacing.UP) {
            switch (SwitchEnumFacing.field_178458_a[enumFacing.ordinal()]) {
                case 1: {
                    GlStateManager.translate(n + 0.5f, n2 + 0.25f, n3 + 0.74f);
                    break;
                }
                case 2: {
                    GlStateManager.translate(n + 0.5f, n2 + 0.25f, n3 + 0.26f);
                    n4 = 180.0f;
                    break;
                }
                case 3: {
                    GlStateManager.translate(n + 0.74f, n2 + 0.25f, n3 + 0.5f);
                    n4 = 270.0f;
                    break;
                }
                default: {
                    GlStateManager.translate(n + 0.26f, n2 + 0.25f, n3 + 0.5f);
                    n4 = 90.0f;
                    break;
                }
            }
        }
        else {
            GlStateManager.translate(n + 0.5f, n2, n3 + 0.5f);
        }
        final float n7 = 0.0625f;
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        modelSkeletonHead.render(null, 0.0f, 0.0f, 0.0f, n4, 0.0f, n7);
        if (n6 >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.matrixMode(5888);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.func_180542_a((TileEntitySkull)tileEntity, n, n2, n3, n4, n5);
    }
    
    static final class SwitchEnumFacing
    {
        private static final String __OBFID;
        
        static {
            __OBFID = "CL_00002468";
            (SwitchEnumFacing.field_178458_a = new int[EnumFacing.values().length])[EnumFacing.NORTH.ordinal()] = 1;
            SwitchEnumFacing.field_178458_a[EnumFacing.SOUTH.ordinal()] = 2;
            SwitchEnumFacing.field_178458_a[EnumFacing.WEST.ordinal()] = 3;
            SwitchEnumFacing.field_178458_a[EnumFacing.EAST.ordinal()] = 4;
        }
    }
}
