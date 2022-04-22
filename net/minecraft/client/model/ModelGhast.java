package net.minecraft.client.model;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class ModelGhast extends ModelBase
{
    ModelRenderer body;
    ModelRenderer[] tentacles;
    private static final String __OBFID;
    
    public ModelGhast() {
        this.tentacles = new ModelRenderer[9];
        (this.body = new ModelRenderer(this, 0, 0)).addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16);
        final ModelRenderer body = this.body;
        body.rotationPointY += 8;
        final Random random = new Random(1660L);
        while (0 < this.tentacles.length) {
            this.tentacles[0] = new ModelRenderer(this, 0, 0);
            final float rotationPointX = ((0 - 0 * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            final float rotationPointZ = (0 / 2.0f * 2.0f - 1.0f) * 5.0f;
            this.tentacles[0].addBox(-1.0f, 0.0f, -1.0f, 2, random.nextInt(7) + 8, 2);
            this.tentacles[0].rotationPointX = rotationPointX;
            this.tentacles[0].rotationPointZ = rotationPointZ;
            this.tentacles[0].rotationPointY = 15;
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        while (0 < this.tentacles.length) {
            this.tentacles[0].rotateAngleX = 0.2f * MathHelper.sin(n3 * 0.3f + 0) + 0.4f;
            int n7 = 0;
            ++n7;
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        GlStateManager.translate(0.0f, 0.6f, 0.0f);
        this.body.render(n6);
        final ModelRenderer[] tentacles = this.tentacles;
        while (0 < tentacles.length) {
            tentacles[0].render(n6);
            int n7 = 0;
            ++n7;
        }
    }
    
    static {
        __OBFID = "CL_00000839";
    }
}
