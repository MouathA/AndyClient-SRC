package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.init.*;
import com.mojang.authlib.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class LayerCustomHead implements LayerRenderer
{
    private final ModelRenderer field_177209_a;
    private static final String __OBFID;
    
    public LayerCustomHead(final ModelRenderer field_177209_a) {
        this.field_177209_a = field_177209_a;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final ItemStack currentArmor = entityLivingBase.getCurrentArmor(3);
        if (currentArmor != null && currentArmor.getItem() != null) {
            final Item item = currentArmor.getItem();
            final Minecraft minecraft = Minecraft.getMinecraft();
            if (entityLivingBase.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            final boolean b = entityLivingBase instanceof EntityVillager || (entityLivingBase instanceof EntityZombie && ((EntityZombie)entityLivingBase).isVillager());
            if (!b && entityLivingBase.isChild()) {
                final float n8 = 2.0f;
                final float n9 = 1.4f;
                GlStateManager.scale(n9 / n8, n9 / n8, n9 / n8);
                GlStateManager.translate(0.0f, 16.0f * n7, 0.0f);
            }
            this.field_177209_a.postRender(0.0625f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (item instanceof ItemBlock) {
                final float n10 = 0.625f;
                GlStateManager.translate(0.0f, -0.25f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.scale(n10, -n10, -n10);
                if (b) {
                    GlStateManager.translate(0.0f, 0.1875f, 0.0f);
                }
                minecraft.getItemRenderer().renderItem(entityLivingBase, currentArmor, ItemCameraTransforms.TransformType.HEAD);
            }
            else if (item == Items.skull) {
                final float n11 = 1.1875f;
                GlStateManager.scale(n11, -n11, -n11);
                if (b) {
                    GlStateManager.translate(0.0f, 0.0625f, 0.0f);
                }
                GameProfile gameProfile = null;
                if (currentArmor.hasTagCompound()) {
                    final NBTTagCompound tagCompound = currentArmor.getTagCompound();
                    if (tagCompound.hasKey("SkullOwner", 10)) {
                        gameProfile = NBTUtil.readGameProfileFromNBT(tagCompound.getCompoundTag("SkullOwner"));
                    }
                    else if (tagCompound.hasKey("SkullOwner", 8)) {
                        gameProfile = TileEntitySkull.updateGameprofile(new GameProfile(null, tagCompound.getString("SkullOwner")));
                        tagCompound.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), gameProfile));
                    }
                }
                TileEntitySkullRenderer.instance.renderSkull(-0.5f, 0.0f, -0.5f, EnumFacing.UP, 180.0f, currentArmor.getMetadata(), gameProfile, -1);
            }
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    static {
        __OBFID = "CL_00002422";
    }
}
