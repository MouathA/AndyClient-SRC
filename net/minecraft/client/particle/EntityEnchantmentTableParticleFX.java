package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntityEnchantmentTableParticleFX extends EntityFX
{
    private float field_70565_a;
    private double field_70568_aq;
    private double field_70567_ar;
    private double field_70566_as;
    private static final String __OBFID;
    
    protected EntityEnchantmentTableParticleFX(final World world, final double field_70568_aq, final double field_70567_ar, final double field_70566_as, final double motionX, final double motionY, final double motionZ) {
        super(world, field_70568_aq, field_70567_ar, field_70566_as, motionX, motionY, motionZ);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.field_70568_aq = field_70568_aq;
        this.field_70567_ar = field_70567_ar;
        this.field_70566_as = field_70566_as;
        final double n = field_70568_aq + motionX;
        this.prevPosX = n;
        this.posX = n;
        final double n2 = field_70567_ar + motionY;
        this.prevPosY = n2;
        this.posY = n2;
        final double n3 = field_70566_as + motionZ;
        this.prevPosZ = n3;
        this.posZ = n3;
        final float n4 = this.rand.nextFloat() * 0.6f + 0.4f;
        final float n5 = this.rand.nextFloat() * 0.5f + 0.2f;
        this.particleScale = n5;
        this.field_70565_a = n5;
        final float particleRed = 1.0f * n4;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGreen *= 0.9f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + 30;
        this.noClip = true;
        this.setParticleTextureIndex((int)(Math.random() * 26.0 + 1.0 + 224.0));
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        final int brightnessForRender = super.getBrightnessForRender(n);
        final float n2 = this.particleAge / (float)this.particleMaxAge;
        final float n3 = n2 * n2;
        final float n4 = n3 * n3;
        final int n5 = brightnessForRender & 0xFF;
        final int n6 = 240 + (int)(n4 * 15.0f * 16.0f);
        return n5 | 0xF00000;
    }
    
    @Override
    public float getBrightness(final float n) {
        final float brightness = super.getBrightness(n);
        final float n2 = this.particleAge / (float)this.particleMaxAge;
        final float n3 = n2 * n2;
        final float n4 = n3 * n3;
        return brightness * (1.0f - n4) + n4;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final float n = 1.0f - this.particleAge / (float)this.particleMaxAge;
        final float n2 = 1.0f - n;
        final float n3 = n2 * n2;
        final float n4 = n3 * n3;
        this.posX = this.field_70568_aq + this.motionX * n;
        this.posY = this.field_70567_ar + this.motionY * n - n4 * 1.2f;
        this.posZ = this.field_70566_as + this.motionZ * n;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }
    
    static {
        __OBFID = "CL_00000902";
    }
    
    public static class EnchantmentTable implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityEnchantmentTableParticleFX(world, n2, n3, n4, n5, n6, n7);
        }
        
        static {
            __OBFID = "CL_00002605";
        }
    }
}
