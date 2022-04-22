package net.minecraft.client.renderer.texture;

import net.minecraft.client.*;
import net.minecraft.world.*;
import optifine.*;
import shadersmod.client.*;
import net.minecraft.util.*;

public class TextureCompass extends TextureAtlasSprite
{
    public double currentAngle;
    public double angleDelta;
    public static String field_176608_l;
    private static final String __OBFID;
    
    public TextureCompass(final String field_176608_l) {
        super(field_176608_l);
        TextureCompass.field_176608_l = field_176608_l;
    }
    
    @Override
    public void updateAnimation() {
        Minecraft.getMinecraft();
        if (Minecraft.theWorld != null && Minecraft.thePlayer != null) {
            this.updateCompass(Minecraft.theWorld, Minecraft.thePlayer.posX, Minecraft.thePlayer.posZ, Minecraft.thePlayer.rotationYaw, false, false);
        }
        else {
            this.updateCompass(null, 0.0, 0.0, 0.0, true, false);
        }
    }
    
    public void updateCompass(final World world, final double n, final double n2, double n3, final boolean b, final boolean b2) {
        if (!this.framesTextureData.isEmpty()) {
            double currentAngle = 0.0;
            if (world != null && !b) {
                final BlockPos spawnPoint = world.getSpawnPoint();
                final double n4 = spawnPoint.getX() - n;
                final double n5 = spawnPoint.getZ() - n2;
                n3 %= 360.0;
                currentAngle = -((n3 - 90.0) * 3.141592653589793 / 180.0 - Math.atan2(n5, n4));
                if (!world.provider.isSurfaceWorld()) {
                    currentAngle = Math.random() * 3.141592653589793 * 2.0;
                }
            }
            if (b2) {
                this.currentAngle = currentAngle;
            }
            else {
                double n6;
                for (n6 = currentAngle - this.currentAngle; n6 < -3.141592653589793; n6 += 6.283185307179586) {}
                while (n6 >= 3.141592653589793) {
                    n6 -= 6.283185307179586;
                }
                this.angleDelta += MathHelper.clamp_double(n6, -1.0, 1.0) * 0.1;
                this.angleDelta *= 0.8;
                this.currentAngle += this.angleDelta;
            }
            int i;
            for (i = (int)((this.currentAngle / 6.283185307179586 + 1.0) * this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size()) {}
            if (i != this.frameCounter) {
                this.frameCounter = i;
                if (Config.isShaders()) {
                    ShadersTex.uploadTexSub(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
                }
                else {
                    TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
                }
            }
        }
    }
    
    static {
        __OBFID = "CL_00001071";
    }
}
