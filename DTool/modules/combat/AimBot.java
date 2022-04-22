package DTool.modules.combat;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;

public class AimBot extends Module
{
    private int ticks;
    
    public AimBot() {
        super("AimBot", 0, Category.Combat);
        this.ticks = 0;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            ++this.ticks;
            if (this.ticks >= 20 - this.speed()) {
                this.ticks = 0;
                final Minecraft mc = AimBot.mc;
                for (final EntityLivingBase next : Minecraft.theWorld.loadedEntityList) {
                    final EntityLivingBase entityLivingBase;
                    if (next instanceof EntityLivingBase && !((entityLivingBase = next) instanceof EntityPlayerSP)) {
                        final Minecraft mc2 = AimBot.mc;
                        if (Minecraft.thePlayer.getDistanceToEntity(entityLivingBase) > 30.0f) {
                            continue;
                        }
                        if (entityLivingBase.isInvisible()) {
                            break;
                        }
                        if (!entityLivingBase.isEntityAlive()) {
                            continue;
                        }
                        faceEntity(entityLivingBase);
                    }
                }
            }
        }
    }
    
    public static synchronized void faceEntity(final EntityLivingBase entityLivingBase) {
        final float[] rotationsNeeded = getRotationsNeeded(entityLivingBase);
        if (rotationsNeeded != null) {
            final Minecraft mc = AimBot.mc;
            Minecraft.thePlayer.rotationYaw = rotationsNeeded[0];
            final Minecraft mc2 = AimBot.mc;
            Minecraft.thePlayer.rotationPitch = rotationsNeeded[1] + 8.0f;
        }
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    private static float[] getRotationsNeeded(final EntityLivingBase entityLivingBase) {
        if (entityLivingBase == null) {
            return null;
        }
        final double posX = entityLivingBase.posX;
        final Minecraft mc = AimBot.mc;
        final double n = posX - Minecraft.thePlayer.posX;
        final double posZ = entityLivingBase.posZ;
        final Minecraft mc2 = AimBot.mc;
        final double n2 = posZ - Minecraft.thePlayer.posZ;
        double n4;
        if (entityLivingBase instanceof EntityLivingBase) {
            final double n3 = entityLivingBase.posY + entityLivingBase.getEyeHeight();
            final Minecraft mc3 = AimBot.mc;
            final double posY = Minecraft.thePlayer.posY;
            final Minecraft mc4 = AimBot.mc;
            n4 = n3 - (posY + Minecraft.thePlayer.getEyeHeight());
        }
        else {
            final double n5 = (entityLivingBase.boundingBox.minY + entityLivingBase.boundingBox.maxY) / 2.0;
            final Minecraft mc5 = AimBot.mc;
            final double posY2 = Minecraft.thePlayer.posY;
            final Minecraft mc6 = AimBot.mc;
            n4 = n5 - (posY2 + Minecraft.thePlayer.getEyeHeight());
        }
        final double n6 = MathHelper.sqrt_double(n * n + n2 * n2);
        final float n7 = (float)(Math.atan2(n2, n) * 180.0 / 3.141592653589793) - 90.0f;
        final float n8 = (float)(-Math.atan2(n4, n6) * 180.0 / 3.141592653589793);
        final float[] array = new float[2];
        final int n9 = 0;
        final Minecraft mc7 = AimBot.mc;
        final float rotationYaw = Minecraft.thePlayer.rotationYaw;
        final float n10 = n7;
        final Minecraft mc8 = AimBot.mc;
        array[n9] = rotationYaw + MathHelper.wrapAngleTo180_float(n10 - Minecraft.thePlayer.rotationYaw);
        final int n11 = 1;
        final Minecraft mc9 = AimBot.mc;
        final float rotationPitch = Minecraft.thePlayer.rotationPitch;
        final float n12 = n8;
        final Minecraft mc10 = AimBot.mc;
        array[n11] = rotationPitch + MathHelper.wrapAngleTo180_float(n12 - Minecraft.thePlayer.rotationPitch);
        return array;
    }
    
    private int speed() {
        return 18;
    }
}
