package Mood.Cosmetics;

import Mood.Cosmetics.impl.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

class HatModel extends CosmeticModelBase
{
    private ModelRenderer witchHat;
    
    public HatModel(final RenderPlayer renderPlayer) {
        super(renderPlayer);
        this.witchHat = new ModelRenderer(this).setTextureSize(64, 128);
        this.witchHat.setTextureOffset(0, 64).addBox(0.0f, 0.0f, 0.0f, 10, 2, 10);
        final ModelRenderer setTextureSize = new ModelRenderer(this).setTextureSize(64, 128);
        setTextureSize.setRotationPoint(1.75f, -4.0f, 2.0f);
        setTextureSize.setTextureOffset(0, 76).addBox(0.0f, 0.0f, 0.0f, 7, 4, 7);
        setTextureSize.rotateAngleX = -0.05235988f;
        setTextureSize.rotateAngleZ = 0.02617994f;
        this.witchHat.addChild(setTextureSize);
        final ModelRenderer setTextureSize2 = new ModelRenderer(this).setTextureSize(64, 128);
        setTextureSize2.setRotationPoint(1.75f, -4.0f, 2.0f);
        setTextureSize2.setTextureOffset(0, 87).addBox(0.0f, 0.0f, 0.0f, 4, 4, 4);
        setTextureSize2.rotateAngleX = -0.10471976f;
        setTextureSize2.rotateAngleZ = 0.05235988f;
        setTextureSize.addChild(setTextureSize2);
        final ModelRenderer setTextureSize3 = new ModelRenderer(this).setTextureSize(64, 128);
        setTextureSize3.setRotationPoint(1.75f, -2.0f, 2.0f);
        setTextureSize3.setTextureOffset(0, 95).addBox(0.0f, 0.0f, 0.0f, 1, 2, 1, 0.25f);
        setTextureSize3.rotateAngleX = -0.20943952f;
        setTextureSize3.rotateAngleZ = 0.10471976f;
        setTextureSize2.addChild(setTextureSize3);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("HexenHut.png"));
        this.witchHat.render(n6);
    }
}
