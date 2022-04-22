package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;

public class EntityParticleEmitter extends EntityFX
{
    private Entity field_174851_a;
    private int field_174852_ax;
    private int field_174850_ay;
    private EnumParticleTypes field_174849_az;
    private static final String __OBFID;
    
    public EntityParticleEmitter(final World world, final Entity field_174851_a, final EnumParticleTypes field_174849_az) {
        super(world, field_174851_a.posX, field_174851_a.getEntityBoundingBox().minY + field_174851_a.height / 2.0f, field_174851_a.posZ, field_174851_a.motionX, field_174851_a.motionY, field_174851_a.motionZ);
        this.field_174851_a = field_174851_a;
        this.field_174850_ay = 3;
        this.field_174849_az = field_174849_az;
        this.onUpdate();
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
    }
    
    @Override
    public void onUpdate() {
        while (0 < 16) {
            final double n = this.rand.nextFloat() * 2.0f - 1.0f;
            final double n2 = this.rand.nextFloat() * 2.0f - 1.0f;
            final double n3 = this.rand.nextFloat() * 2.0f - 1.0f;
            if (n * n + n2 * n2 + n3 * n3 <= 1.0) {
                this.worldObj.spawnParticle(this.field_174849_az, false, this.field_174851_a.posX + n * this.field_174851_a.width / 4.0, this.field_174851_a.getEntityBoundingBox().minY + this.field_174851_a.height / 2.0f + n2 * this.field_174851_a.height / 4.0, this.field_174851_a.posZ + n3 * this.field_174851_a.width / 4.0, n, n2 + 0.2, n3, new int[0]);
            }
            int n4 = 0;
            ++n4;
        }
        ++this.field_174852_ax;
        if (this.field_174852_ax >= this.field_174850_ay) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
    
    static {
        __OBFID = "CL_00002574";
    }
}
