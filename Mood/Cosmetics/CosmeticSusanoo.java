package Mood.Cosmetics;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import Mood.Cosmetics.Main.*;
import net.minecraft.client.entity.*;
import Mood.Cosmetics.impl.*;
import net.minecraft.client.model.*;

public class CosmeticSusanoo extends CosmeticBase
{
    private final NAME2 nameModel;
    
    public CosmeticSusanoo(final RenderPlayer renderPlayer) {
        super(renderPlayer);
        this.nameModel = new NAME2(renderPlayer);
    }
    
    @Override
    public void render(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        GlStateManager.color(1.0f, 0.0f, 0.0f);
        GlStateManager.blendFunc(1, 1);
        if (abstractClientPlayer.isSneaking()) {
            GlStateManager.rotate(20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(0.0, 0.2, -0.05);
        }
        GlStateManager.translate(0.0, -0.2, 0.0);
        if (abstractClientPlayer.getUniqueID().toString().contains("dwjandowpiahnbdwoiuadw")) {
            this.nameModel.render(abstractClientPlayer, n, n2, n4, n5, n6, n7);
            GlStateManager.color(1.0f, 1.0f, 1.0f);
        }
        if (GuiCosmetics.Susanoo && abstractClientPlayer instanceof EntityPlayerSP) {
            GlStateManager.color(255.0f, 0.0f, 0.0f);
            this.nameModel.render(abstractClientPlayer, n, n2, n4, n5, n6, n7);
        }
    }
    
    class NAME2 extends CosmeticModelBase
    {
        ModelRenderer Back;
        ModelRenderer upperlane;
        ModelRenderer upperlaneonedown;
        ModelRenderer upperlanetwodown;
        ModelRenderer left1;
        ModelRenderer left2;
        ModelRenderer left3;
        ModelRenderer right1;
        ModelRenderer right2;
        ModelRenderer right3;
        final CosmeticSusanoo this$0;
        
        public NAME2(final CosmeticSusanoo this$0, final RenderPlayer renderPlayer) {
            this.this$0 = this$0;
            super(renderPlayer);
            this.textureWidth = 0;
            this.textureHeight = 0;
            (this.Back = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 2, 15, 1);
            this.Back.setRotationPoint(-1.0f, 0.0f, 6.0f);
            this.Back.setTextureSize(64, 32);
            this.Back.mirror = true;
            (this.upperlane = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 22, 1, 1);
            this.upperlane.setRotationPoint(-11.0f, 3.0f, 6.0f);
            this.upperlane.setTextureSize(64, 32);
            this.upperlane.mirror = true;
            (this.upperlaneonedown = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 22, 1, 1);
            this.upperlaneonedown.setRotationPoint(-11.0f, 6.0f, 6.0f);
            this.upperlaneonedown.setTextureSize(64, 32);
            this.upperlaneonedown.mirror = true;
            (this.upperlanetwodown = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 22, 1, 1);
            this.upperlanetwodown.setRotationPoint(-11.0f, 10.0f, 6.0f);
            this.upperlanetwodown.setTextureSize(64, 32);
            this.upperlanetwodown.mirror = true;
            (this.left1 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 9);
            this.left1.setRotationPoint(10.0f, 3.0f, -3.0f);
            this.left1.setTextureSize(64, 32);
            this.left1.mirror = true;
            (this.left2 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 9);
            this.left2.setRotationPoint(10.0f, 6.0f, -3.0f);
            this.left2.setTextureSize(64, 32);
            this.left2.mirror = true;
            (this.left3 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 9);
            this.left3.setRotationPoint(10.0f, 10.0f, -3.0f);
            this.left3.setTextureSize(64, 32);
            this.left3.mirror = true;
            (this.right1 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 9);
            this.right1.setRotationPoint(-11.0f, 3.0f, -3.0f);
            this.right1.setTextureSize(64, 32);
            this.right1.mirror = true;
            (this.right2 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 9);
            this.right2.setRotationPoint(-11.0f, 6.0f, -3.0f);
            this.right2.setTextureSize(64, 32);
            this.right2.mirror = true;
            (this.right3 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 9);
            this.right3.setRotationPoint(-11.0f, 10.0f, -3.0f);
            this.right3.setTextureSize(64, 32);
            this.right3.mirror = true;
        }
        
        @Override
        public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
            this.Back.render(n6);
            this.right1.render(n6);
            this.right2.render(n6);
            this.right3.render(n6);
            this.upperlane.render(n6);
            this.upperlaneonedown.render(n6);
            this.upperlanetwodown.render(n6);
            this.left1.render(n6);
            this.left2.render(n6);
            this.left3.render(n6);
        }
    }
}
