package Mood.Cosmetics;

import net.minecraft.client.renderer.entity.*;
import Mood.Cosmetics.Main.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import Mood.Cosmetics.impl.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.block.model.*;

public class ILikeCommandBlocks extends CosmeticBase
{
    private final CosmeticVilligerNose2 EggsModel;
    
    public ILikeCommandBlocks(final RenderPlayer renderPlayer) {
        super(renderPlayer);
        this.EggsModel = new CosmeticVilligerNose2(renderPlayer);
    }
    
    @Override
    public void render(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (GuiCosmetics.ILikeCMDBlocks && abstractClientPlayer instanceof EntityPlayerSP) {
            if (abstractClientPlayer.isSneaking()) {
                GlStateManager.translate(0.0, 0.262, 0.0);
            }
            GlStateManager.rotate(n5, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(n6, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(n4 * 17.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            if (abstractClientPlayer.getUniqueID().toString().contains("dwabdwbo8adb8wbdwa")) {
                this.EggsModel.render(abstractClientPlayer, n, n2, n4, n5, n6, n7);
                GL11.glColor3d(1.0, 1.0, 1.0);
            }
            this.EggsModel.render(abstractClientPlayer, n, n2, n4, n5, n6, n7);
        }
    }
    
    public class CosmeticVilligerNose2 extends CosmeticModelBase
    {
        final ILikeCommandBlocks this$0;
        
        public CosmeticVilligerNose2(final ILikeCommandBlocks this$0, final RenderPlayer renderPlayer) {
            this.this$0 = this$0;
            super(renderPlayer);
        }
        
        @Override
        public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
            GlStateManager.scale(0.25, 0.25, 0.25);
            GlStateManager.translate(2.0, 1.5, 0.0);
            final ItemStack itemStack = new ItemStack(Blocks.command_block);
            Minecraft.getMinecraft().getItemRenderer().renderItem((EntityLivingBase)entity, itemStack, ItemCameraTransforms.TransformType.NONE);
            GlStateManager.translate(-4.0f, 0.0f, 0.0f);
            Minecraft.getMinecraft().getItemRenderer().renderItem((EntityLivingBase)entity, itemStack, ItemCameraTransforms.TransformType.NONE);
            GlStateManager.translate(2.0f, 0.0f, 2.0f);
            Minecraft.getMinecraft().getItemRenderer().renderItem((EntityLivingBase)entity, itemStack, ItemCameraTransforms.TransformType.NONE);
            GlStateManager.translate(0.0f, 0.0f, -4.0f);
            Minecraft.getMinecraft().getItemRenderer().renderItem((EntityLivingBase)entity, itemStack, ItemCameraTransforms.TransformType.NONE);
        }
    }
}
