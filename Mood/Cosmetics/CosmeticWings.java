package Mood.Cosmetics;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.entity.*;
import Mood.Cosmetics.Main.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import Mood.Cosmetics.impl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class CosmeticWings extends CosmeticBase
{
    private static ModelRenderer wing;
    private static ModelRenderer wingTip;
    boolean flying;
    private final ModelDragonWings modelDragonWings;
    
    public CosmeticWings(final RenderPlayer renderPlayer) {
        super(renderPlayer);
        this.flying = false;
        (this.modelDragonWings = new ModelDragonWings(renderPlayer)).setTextureOffset("wingTip.bone", 112, 136);
        this.modelDragonWings.setTextureOffset("wing.skin", -56, 88);
        this.modelDragonWings.setTextureOffset("wing.bone", 112, 88);
        this.modelDragonWings.setTextureOffset("wingTip.skin", -56, 144);
        final int textureWidth = this.modelDragonWings.textureWidth;
        final int textureHeight = this.modelDragonWings.textureHeight;
        this.modelDragonWings.textureWidth = 256;
        this.modelDragonWings.textureHeight = 256;
        (CosmeticWings.wing = new ModelRenderer(this.modelDragonWings, "wing")).setRotationPoint(-12.0f, 5.0f, 2.0f);
        CosmeticWings.wing.addBox("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
        CosmeticWings.wing.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        CosmeticWings.wing.isHidden = true;
        (CosmeticWings.wingTip = new ModelRenderer(this.modelDragonWings, "wingTip")).setRotationPoint(-56.0f, 0.0f, 0.0f);
        CosmeticWings.wingTip.isHidden = true;
        CosmeticWings.wingTip.addBox("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
        CosmeticWings.wingTip.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        CosmeticWings.wing.addChild(CosmeticWings.wingTip);
        this.modelDragonWings.textureWidth = textureWidth;
        this.modelDragonWings.textureWidth = textureHeight;
    }
    
    @Override
    public void render(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (GuiCosmetics.CosmeticWings) {
            final String name = abstractClientPlayer.getName();
            Minecraft.getMinecraft();
            if (name.equals(Minecraft.getSession().getUsername())) {
                this.modelDragonWings.render(abstractClientPlayer, n, n2, n4, n5, n6, n7);
                this.modelDragonWings.setRotationAngles(n7, n, n2, n4, n5, n6, abstractClientPlayer);
            }
        }
    }
    
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
    }
    
    static ModelRenderer access$0() {
        return CosmeticWings.wing;
    }
    
    static ModelRenderer access$1() {
        return CosmeticWings.wingTip;
    }
    
    private class ModelDragonWings extends CosmeticModelBase
    {
        final CosmeticWings this$0;
        
        public ModelDragonWings(final CosmeticWings this$0, final RenderPlayer renderPlayer) {
            this.this$0 = this$0;
            super(renderPlayer);
        }
        
        @Override
        public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
            super.render(entity, n, n2, n3, n4, n5, n6);
            final String name = entity.getName();
            Minecraft.getMinecraft();
            if (name.equals(Minecraft.getSession().getUsername())) {
                Minecraft.getMinecraft();
                float n7;
                if (Minecraft.thePlayer.capabilities.isFlying) {
                    n7 = n3 / 200.0f;
                }
                else {
                    n7 = n3 / 80.0f;
                }
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("MooDTool/red.png"));
                if (!entity.onGround || this.this$0.flying) {
                    this.this$0.flying = true;
                }
                GlStateManager.scale(0.15, 0.15, 0.15);
                GlStateManager.translate(0.0, -0.3, 1.1);
                GlStateManager.rotate(50.0f, -50.0f, 0.0f, 0.0f);
                while (0 < 2) {
                    final float n8 = n7 * 9.141593f * 2.0f;
                    CosmeticWings.access$0().rotateAngleX = 0.125f - (float)Math.cos(n8) * 0.2f;
                    CosmeticWings.access$0().rotateAngleY = 0.25f;
                    CosmeticWings.access$0().rotateAngleZ = (float)(Math.sin(n8) + 1.225) * 0.3f;
                    CosmeticWings.access$1().rotateAngleZ = -(float)(Math.sin(n8 + 2.0f) + 0.5) * 0.75f;
                    CosmeticWings.access$0().isHidden = false;
                    CosmeticWings.access$1().isHidden = false;
                    if (!entity.isInvisible()) {
                        CosmeticWings.access$0().render(n6);
                        GlStateManager.blendFunc(1, 1);
                    }
                    CosmeticWings.access$0().isHidden = false;
                    if (!(CosmeticWings.access$1().isHidden = false)) {
                        GlStateManager.scale(-1.0f, 1.0f, 1.0f);
                    }
                    int n9 = 0;
                    ++n9;
                }
            }
        }
    }
}
