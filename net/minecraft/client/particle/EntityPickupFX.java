package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class EntityPickupFX extends EntityFX
{
    private Entity field_174840_a;
    private Entity field_174843_ax;
    private int age;
    private int maxAge;
    private float field_174841_aA;
    private RenderManager field_174842_aB;
    private static final String __OBFID;
    
    public EntityPickupFX(final World world, final Entity field_174840_a, final Entity field_174843_ax, final float field_174841_aA) {
        super(world, field_174840_a.posX, field_174840_a.posY, field_174840_a.posZ, field_174840_a.motionX, field_174840_a.motionY, field_174840_a.motionZ);
        this.field_174842_aB = Minecraft.getMinecraft().getRenderManager();
        this.field_174840_a = field_174840_a;
        this.field_174843_ax = field_174843_ax;
        this.maxAge = 3;
        this.field_174841_aA = field_174841_aA;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = (this.age + n) / this.maxAge;
        final float n8 = n7 * n7;
        final double posX = this.field_174840_a.posX;
        final double posY = this.field_174840_a.posY;
        final double posZ = this.field_174840_a.posZ;
        final double n9 = this.field_174843_ax.lastTickPosX + (this.field_174843_ax.posX - this.field_174843_ax.lastTickPosX) * n;
        final double n10 = this.field_174843_ax.lastTickPosY + (this.field_174843_ax.posY - this.field_174843_ax.lastTickPosY) * n + this.field_174841_aA;
        final double n11 = this.field_174843_ax.lastTickPosZ + (this.field_174843_ax.posZ - this.field_174843_ax.lastTickPosZ) * n;
        final double n12 = posX + (n9 - posX) * n8;
        final double n13 = posY + (n10 - posY) * n8;
        final double n14 = posZ + (n11 - posZ) * n8;
        final int brightnessForRender = this.getBrightnessForRender(n);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % 65536 / 1.0f, brightnessForRender / 65536 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_174842_aB.renderEntityWithPosYaw(this.field_174840_a, (float)(n12 - EntityPickupFX.interpPosX), (float)(n13 - EntityPickupFX.interpPosY), (float)(n14 - EntityPickupFX.interpPosZ), this.field_174840_a.rotationYaw, n);
    }
    
    @Override
    public void onUpdate() {
        ++this.age;
        if (this.age == this.maxAge) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
    
    static {
        __OBFID = "CL_00000930";
    }
}
