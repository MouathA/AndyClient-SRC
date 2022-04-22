package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class ModelGuardian extends ModelBase
{
    private ModelRenderer guardianBody;
    private ModelRenderer guardianEye;
    private ModelRenderer[] guardianSpines;
    private ModelRenderer[] guardianTail;
    private static final String __OBFID;
    
    public ModelGuardian() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.guardianSpines = new ModelRenderer[12];
        this.guardianBody = new ModelRenderer(this);
        this.guardianBody.setTextureOffset(0, 0).addBox(-6.0f, 10.0f, -8.0f, 12, 12, 16);
        this.guardianBody.setTextureOffset(0, 28).addBox(-8.0f, 10.0f, -6.0f, 2, 12, 12);
        this.guardianBody.setTextureOffset(0, 28).addBox(6.0f, 10.0f, -6.0f, 2, 12, 12, true);
        this.guardianBody.setTextureOffset(16, 40).addBox(-6.0f, 8.0f, -6.0f, 12, 2, 12);
        this.guardianBody.setTextureOffset(16, 40).addBox(-6.0f, 22.0f, -6.0f, 12, 2, 12);
        while (0 < this.guardianSpines.length) {
            (this.guardianSpines[0] = new ModelRenderer(this, 0, 0)).addBox(-1.0f, -4.5f, -1.0f, 2, 9, 2);
            this.guardianBody.addChild(this.guardianSpines[0]);
            int n = 0;
            ++n;
        }
        (this.guardianEye = new ModelRenderer(this, 8, 0)).addBox(-1.0f, 15.0f, 0.0f, 2, 2, 1);
        this.guardianBody.addChild(this.guardianEye);
        this.guardianTail = new ModelRenderer[3];
        (this.guardianTail[0] = new ModelRenderer(this, 40, 0)).addBox(-2.0f, 14.0f, 7.0f, 4, 4, 8);
        (this.guardianTail[1] = new ModelRenderer(this, 0, 54)).addBox(0.0f, 14.0f, 0.0f, 3, 3, 7);
        this.guardianTail[2] = new ModelRenderer(this);
        this.guardianTail[2].setTextureOffset(41, 32).addBox(0.0f, 14.0f, 0.0f, 2, 2, 6);
        this.guardianTail[2].setTextureOffset(25, 19).addBox(1.0f, 10.5f, 3.0f, 1, 9, 9);
        this.guardianBody.addChild(this.guardianTail[0]);
        this.guardianTail[0].addChild(this.guardianTail[1]);
        this.guardianTail[1].addChild(this.guardianTail[2]);
    }
    
    public int func_178706_a() {
        return 54;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.guardianBody.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        final EntityGuardian entityGuardian = (EntityGuardian)entity;
        final float n7 = n3 - entityGuardian.ticksExisted;
        this.guardianBody.rotateAngleY = n4 / 57.295776f;
        this.guardianBody.rotateAngleX = n5 / 57.295776f;
        final float[] array = { 1.75f, 0.25f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.5f, 1.25f, 0.75f, 0.0f, 0.0f };
        final float[] array2 = { 0.0f, 0.0f, 0.0f, 0.0f, 0.25f, 1.75f, 1.25f, 0.75f, 0.0f, 0.0f, 0.0f, 0.0f };
        final float[] array3 = { 0.0f, 0.0f, 0.25f, 1.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.75f, 1.25f };
        final float[] array4 = { 0.0f, 0.0f, 8.0f, -8.0f, -8.0f, 8.0f, 8.0f, -8.0f, 0.0f, 0.0f, 8.0f, -8.0f };
        final float[] array5 = { -8.0f, -8.0f, -8.0f, -8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 8.0f, 8.0f, 8.0f, 8.0f };
        final float[] array6 = { 8.0f, -8.0f, 0.0f, 0.0f, -8.0f, -8.0f, 8.0f, 8.0f, 8.0f, -8.0f, 0.0f, 0.0f };
        final float n8 = (1.0f - entityGuardian.func_175469_o(n7)) * 0.55f;
        this.guardianEye.rotationPointZ = -8.25f;
        Entity entity2 = Minecraft.getMinecraft().func_175606_aa();
        if (entityGuardian.func_175474_cn()) {
            entity2 = entityGuardian.func_175466_co();
        }
        if (entity2 != null) {
            final Vec3 func_174824_e = entity2.func_174824_e(0.0f);
            final Vec3 func_174824_e2 = entity.func_174824_e(0.0f);
            if (func_174824_e.yCoord - func_174824_e2.yCoord > 0.0) {
                this.guardianEye.rotationPointY = 0.0f;
            }
            else {
                this.guardianEye.rotationPointY = 1.0f;
            }
            final Vec3 look = entity.getLook(0.0f);
            final double dotProduct = new Vec3(look.xCoord, 0.0, look.zCoord).dotProduct(new Vec3(func_174824_e2.xCoord - func_174824_e.xCoord, 0.0, func_174824_e2.zCoord - func_174824_e.zCoord).normalize().rotateYaw(1.5707964f));
            this.guardianEye.rotationPointX = MathHelper.sqrt_float((float)Math.abs(dotProduct)) * 2.0f * (float)Math.signum(dotProduct);
        }
        this.guardianEye.showModel = true;
        final float func_175471_a = entityGuardian.func_175471_a(n7);
        this.guardianTail[0].rotateAngleY = MathHelper.sin(func_175471_a) * 3.1415927f * 0.05f;
        this.guardianTail[1].rotateAngleY = MathHelper.sin(func_175471_a) * 3.1415927f * 0.1f;
        this.guardianTail[1].rotationPointX = -1.5f;
        this.guardianTail[1].rotationPointY = 0.5f;
        this.guardianTail[1].rotationPointZ = 14.0f;
        this.guardianTail[2].rotateAngleY = MathHelper.sin(func_175471_a) * 3.1415927f * 0.15f;
        this.guardianTail[2].rotationPointX = 0.5f;
        this.guardianTail[2].rotationPointY = 0.5f;
        this.guardianTail[2].rotationPointZ = 6.0f;
    }
    
    static {
        __OBFID = "CL_00002628";
    }
}
