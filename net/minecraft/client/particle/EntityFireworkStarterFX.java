package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import Mood.*;
import net.minecraft.util.*;

public class EntityFireworkStarterFX extends EntityFX
{
    private int fireworkAge;
    private final EffectRenderer theEffectRenderer;
    private NBTTagList fireworkExplosions;
    boolean twinkle;
    private static final String __OBFID;
    
    public EntityFireworkStarterFX(final World world, final double n, final double n2, final double n3, final double motionX, final double motionY, final double motionZ, final EffectRenderer theEffectRenderer, final NBTTagCompound nbtTagCompound) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.theEffectRenderer = theEffectRenderer;
        this.particleMaxAge = 8;
        if (nbtTagCompound != null) {
            this.fireworkExplosions = nbtTagCompound.getTagList("Explosions", 10);
            if (this.fireworkExplosions.tagCount() == 0) {
                this.fireworkExplosions = null;
            }
            else {
                this.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;
                while (0 < this.fireworkExplosions.tagCount()) {
                    if (this.fireworkExplosions.getCompoundTagAt(0).getBoolean("Flicker")) {
                        this.twinkle = true;
                        this.particleMaxAge += 15;
                        break;
                    }
                    int n4 = 0;
                    ++n4;
                }
            }
        }
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
    }
    
    @Override
    public void onUpdate() {
        if (this.fireworkAge == 0 && this.fireworkExplosions != null) {
            final boolean func_92037_i = this.func_92037_i();
            if (this.fireworkExplosions.tagCount() < 3) {
                while (0 < this.fireworkExplosions.tagCount()) {
                    if (this.fireworkExplosions.getCompoundTagAt(0).getByte("Type") == 1) {
                        break;
                    }
                    int n = 0;
                    ++n;
                }
            }
            this.worldObj.playSound(this.posX, this.posY, this.posZ, "fireworks." + "largeBlast" + (func_92037_i ? "_far" : ""), 20.0f, 0.95f + this.rand.nextFloat() * 0.1f, true);
        }
        if (this.fireworkAge % 2 == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.tagCount()) {
            final int n2 = this.fireworkAge / 2;
            final NBTTagCompound compoundTag = this.fireworkExplosions.getCompoundTagAt(1);
            final byte byte1 = compoundTag.getByte("Type");
            final boolean boolean1 = compoundTag.getBoolean("Trail");
            final boolean boolean2 = compoundTag.getBoolean("Flicker");
            int[] intArray = compoundTag.getIntArray("Colors");
            final int[] intArray2 = compoundTag.getIntArray("FadeColors");
            if (intArray.length == 0) {
                intArray = new int[] { ItemDye.dyeColors[0] };
            }
            if (byte1 == 1) {
                this.createBall(0.5, 4, intArray, intArray2, boolean1, boolean2);
            }
            else if (byte1 == 2) {
                this.createShaped(0.5, new double[][] { { 0.0, 1.0 }, { 0.3455, 0.309 }, { 0.9511, 0.309 }, { 0.3795918367346939, -0.12653061224489795 }, { 0.6122448979591837, -0.8040816326530612 }, { 0.0, -0.35918367346938773 } }, intArray, intArray2, boolean1, boolean2, false);
            }
            else if (byte1 == 3) {
                this.createShaped(0.5, new double[][] { { 0.0, 0.2 }, { 0.2, 0.2 }, { 0.2, 0.6 }, { 0.6, 0.6 }, { 0.6, 0.2 }, { 0.2, 0.2 }, { 0.2, 0.0 }, { 0.4, 0.0 }, { 0.4, -0.6 }, { 0.2, -0.6 }, { 0.2, -0.4 }, { 0.0, -0.4 } }, intArray, intArray2, boolean1, boolean2, true);
            }
            else if (byte1 == 4) {
                this.createBurst(intArray, intArray2, boolean1, boolean2);
            }
            else {
                this.createBall(0.25, 2, intArray, intArray2, boolean1, boolean2);
            }
            final int n3 = intArray[0];
            final float n4 = ((n3 & 0xFF0000) >> 16) / 255.0f;
            final float n5 = ((n3 & 0xFF00) >> 8) / 255.0f;
            final float n6 = ((n3 & 0xFF) >> 0) / 255.0f;
            final EntityFireworkOverlayFX entityFireworkOverlayFX = new EntityFireworkOverlayFX(this.worldObj, this.posX, this.posY, this.posZ);
            entityFireworkOverlayFX.setRBGColorF(n4, n5, n6);
            this.theEffectRenderer.addEffect(entityFireworkOverlayFX);
        }
        ++this.fireworkAge;
        if (this.fireworkAge > this.particleMaxAge) {
            if (this.twinkle) {
                this.worldObj.playSound(this.posX, this.posY, this.posZ, "fireworks." + (this.func_92037_i() ? "twinkle_far" : "twinkle"), 20.0f, 0.9f + this.rand.nextFloat() * 0.15f, true);
            }
            this.setDead();
        }
    }
    
    private boolean func_92037_i() {
        final Minecraft minecraft = Minecraft.getMinecraft();
        return minecraft == null || minecraft.func_175606_aa() == null || minecraft.func_175606_aa().getDistanceSq(this.posX, this.posY, this.posZ) >= 256.0;
    }
    
    private void createParticle(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int[] array, final int[] array2, final boolean trail, final boolean twinkle) {
        final Client instance = Client.INSTANCE;
        if (!Client.getModuleByName("ItemPhysics").toggled) {
            final EntityFireworkSparkFX entityFireworkSparkFX = new EntityFireworkSparkFX(this.worldObj, n, n2, n3, n4, n5, n6, this.theEffectRenderer);
            entityFireworkSparkFX.setAlphaF(0.99f);
            entityFireworkSparkFX.setTrail(trail);
            entityFireworkSparkFX.setTwinkle(twinkle);
            entityFireworkSparkFX.setColour(array[this.rand.nextInt(array.length)]);
            if (array2 != null && array2.length > 0) {
                entityFireworkSparkFX.setFadeColour(array2[this.rand.nextInt(array2.length)]);
            }
            this.theEffectRenderer.addEffect(entityFireworkSparkFX);
        }
    }
    
    private void createBall(final double n, final int n2, final int[] array, final int[] array2, final boolean b, final boolean b2) {
        final double posX = this.posX;
        final double posY = this.posY;
        final double posZ = this.posZ;
        for (int i = -n2; i <= n2; ++i) {
            for (int j = -n2; j <= n2; ++j) {
                for (int k = -n2; k <= n2; ++k) {
                    final double n3 = j + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                    final double n4 = i + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                    final double n5 = k + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                    final double n6 = MathHelper.sqrt_double(n3 * n3 + n4 * n4 + n5 * n5) / n + this.rand.nextGaussian() * 0.05;
                    this.createParticle(posX, posY, posZ, n3 / n6, n4 / n6, n5 / n6, array, array2, b, b2);
                    if (i != -n2 && i != n2 && j != -n2 && j != n2) {
                        k += n2 * 2 - 1;
                    }
                }
            }
        }
    }
    
    private void createShaped(final double n, final double[][] array, final int[] array2, final int[] array3, final boolean b, final boolean b2, final boolean b3) {
        this.createParticle(this.posX, this.posY, this.posZ, array[0][0] * n, array[0][1] * n, 0.0, array2, array3, b, b2);
        final float n2 = this.rand.nextFloat() * 3.1415927f;
        final double n3 = b3 ? 0.034 : 0.34;
    }
    
    private void createBurst(final int[] array, final int[] array2, final boolean b, final boolean b2) {
        final double n = this.rand.nextGaussian() * 0.05;
        final double n2 = this.rand.nextGaussian() * 0.05;
    }
    
    @Override
    public int getFXLayer() {
        return 0;
    }
    
    static {
        __OBFID = "CL_00000906";
    }
}
