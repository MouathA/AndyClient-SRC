package Mood.Cosmetics;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import Mood.Cosmetics.impl.*;
import net.minecraft.client.model.*;

public class CosmeticSunglasses extends CosmeticBase
{
    private final Sunglasses sunglassesModel;
    
    public CosmeticSunglasses(final RenderPlayer renderPlayer) {
        super(renderPlayer);
        this.sunglassesModel = new Sunglasses(renderPlayer);
    }
    
    @Override
    public void render(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (abstractClientPlayer.isSneaking()) {
            GlStateManager.translate(0.0, 0.262, 0.0);
        }
        GlStateManager.rotate(n5, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(n6, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0, 0.064, 0.0);
        final String string = abstractClientPlayer.getUniqueID().toString();
        GL11.glColor3d(0.0, 0.0, 0.0);
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("background.jpg"));
        if (string.contains("dwnaodwadowabdoz8uwad")) {
            this.sunglassesModel.render(abstractClientPlayer, n, n2, n4, n5, n6, n7);
            GL11.glColor3d(1.0, 1.0, 1.0);
        }
        if (abstractClientPlayer instanceof EntityPlayerSP) {
            GlStateManager.translate(0.0, 0.0, -0.02);
            this.sunglassesModel.render(abstractClientPlayer, n, n2, n4, n5, n6, n7);
        }
    }
    
    public class Sunglasses extends CosmeticModelBase
    {
        ModelRenderer Glasses1;
        ModelRenderer Glasses2;
        ModelRenderer Glasses3;
        ModelRenderer Glasses4;
        ModelRenderer Glasses5;
        ModelRenderer Glasses6;
        ModelRenderer Glasses7;
        final CosmeticSunglasses this$0;
        
        public Sunglasses(final CosmeticSunglasses this$0, final RenderPlayer renderPlayer) {
            this.this$0 = this$0;
            super(renderPlayer);
            this.textureWidth = 64;
            this.textureHeight = 32;
            (this.Glasses1 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 6);
            this.Glasses1.setRotationPoint(4.0f, -4.0f, -4.0f);
            this.Glasses1.setTextureSize(64, 32);
            this.Glasses1.mirror = true;
            this.setRotation(this.Glasses1, 0.0f, 0.0f, 0.0f);
            (this.Glasses2 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 1);
            this.Glasses2.setRotationPoint(4.0f, -3.0f, 2.0f);
            this.Glasses2.setTextureSize(64, 32);
            this.Glasses2.mirror = true;
            this.setRotation(this.Glasses2, 0.0f, 0.0f, 0.0f);
            (this.Glasses3 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 6);
            this.Glasses3.setRotationPoint(-5.0f, -4.0f, -4.0f);
            this.Glasses3.setTextureSize(64, 32);
            this.Glasses3.mirror = true;
            this.setRotation(this.Glasses3, 0.0f, 0.0f, 0.0f);
            (this.Glasses4 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 1);
            this.Glasses4.setRotationPoint(-5.0f, -3.0f, 2.0f);
            this.Glasses4.setTextureSize(64, 32);
            this.Glasses4.mirror = true;
            this.setRotation(this.Glasses4, 0.0f, 0.0f, 0.0f);
            (this.Glasses5 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 10, 1, 1);
            this.Glasses5.setRotationPoint(-5.0f, -4.0f, -5.0f);
            this.Glasses5.setTextureSize(64, 32);
            this.Glasses5.mirror = true;
            this.setRotation(this.Glasses5, 0.0f, 0.0f, 0.0f);
            (this.Glasses6 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 4, 1, 1);
            this.Glasses6.setRotationPoint(1.0f, -3.0f, -5.0f);
            this.Glasses6.setTextureSize(64, 32);
            this.Glasses6.mirror = true;
            this.setRotation(this.Glasses6, 0.0f, 0.0f, 0.0f);
            (this.Glasses7 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 4, 1, 1);
            this.Glasses7.setRotationPoint(-5.0f, -3.0f, -5.0f);
            this.Glasses7.setTextureSize(64, 32);
            this.Glasses7.mirror = true;
            this.setRotation(this.Glasses7, 0.0f, 0.0f, 0.0f);
        }
        
        @Override
        public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
            GlStateManager.color(0.0f, 0.0f, 0.0f);
            this.Glasses1.render(n6);
            this.Glasses2.render(n6);
            this.Glasses3.render(n6);
            this.Glasses4.render(n6);
            this.Glasses5.render(n6);
            this.Glasses6.render(n6);
            this.Glasses7.render(n6);
        }
        
        private void setRotation(final ModelRenderer modelRenderer, final float rotateAngleX, final float rotateAngleY, final float rotateAngleZ) {
            modelRenderer.rotateAngleX = rotateAngleX;
            modelRenderer.rotateAngleY = rotateAngleY;
            modelRenderer.rotateAngleZ = rotateAngleZ;
        }
    }
}
