package DTool.modules.visuals;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class IamFuckingSanic extends Module
{
    private final ResourceLocation SANIC;
    
    public IamFuckingSanic() {
        super("IamFuckingSanic", 0, Category.Visuals);
        this.SANIC = new ResourceLocation("MooDTool/SANIC.png");
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = IamFuckingSanic.mc;
            if (!Minecraft.thePlayer.isSneaking()) {
                final Minecraft mc2 = IamFuckingSanic.mc;
                if (Minecraft.thePlayer.moveForward == 0.0f) {
                    final Minecraft mc3 = IamFuckingSanic.mc;
                    if (Minecraft.thePlayer.moveStrafing == 0.0f) {
                        return;
                    }
                }
                final Minecraft mc4 = IamFuckingSanic.mc;
                if (Minecraft.thePlayer.moveForward > 0.0f) {
                    final Minecraft mc5 = IamFuckingSanic.mc;
                    if (!Minecraft.thePlayer.isCollidedHorizontally) {
                        final Minecraft mc6 = IamFuckingSanic.mc;
                        Minecraft.thePlayer.setSprinting(true);
                    }
                }
                final Minecraft mc7 = IamFuckingSanic.mc;
                if (!Minecraft.thePlayer.onGround) {
                    return;
                }
                final Minecraft mc8 = IamFuckingSanic.mc;
                final EntityPlayerSP thePlayer = Minecraft.thePlayer;
                thePlayer.motionY += 0.0;
                final Minecraft mc9 = IamFuckingSanic.mc;
                final EntityPlayerSP thePlayer2 = Minecraft.thePlayer;
                thePlayer2.motionX *= 3.8;
                final Minecraft mc10 = IamFuckingSanic.mc;
                final EntityPlayerSP thePlayer3 = Minecraft.thePlayer;
                thePlayer3.motionZ *= 3.8;
                final Minecraft mc11 = IamFuckingSanic.mc;
                final double pow = Math.pow(Minecraft.thePlayer.motionX, 2.0);
                final Minecraft mc12 = IamFuckingSanic.mc;
                final double sqrt = Math.sqrt(pow + Math.pow(Minecraft.thePlayer.motionZ, 2.0));
                final double n = 0.6600000262260437;
                if (sqrt > n) {
                    final Minecraft mc13 = IamFuckingSanic.mc;
                    final EntityPlayerSP thePlayer4 = Minecraft.thePlayer;
                    final Minecraft mc14 = IamFuckingSanic.mc;
                    thePlayer4.motionX = Minecraft.thePlayer.motionX / sqrt * n;
                    final Minecraft mc15 = IamFuckingSanic.mc;
                    final EntityPlayerSP thePlayer5 = Minecraft.thePlayer;
                    final Minecraft mc16 = IamFuckingSanic.mc;
                    thePlayer5.motionZ = Minecraft.thePlayer.motionZ / sqrt * n;
                }
            }
        }
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onRender() {
        if (!this.isEnable()) {
            return;
        }
        final ResourceLocation sanic = this.SANIC;
        for (final EntityPlayer entityPlayer : Minecraft.theWorld.playerEntities) {
            final String name = entityPlayer.getName();
            Minecraft.getMinecraft();
            if (name.equals(Minecraft.getSession().getUsername())) {
                final double interp = interp(entityPlayer.posX, entityPlayer.lastTickPosX);
                Minecraft.getMinecraft().getRenderManager();
                final double n = interp - RenderManager.renderPosX;
                final double interp2 = interp(entityPlayer.posY, entityPlayer.lastTickPosY);
                Minecraft.getMinecraft().getRenderManager();
                final double n2 = interp2 - RenderManager.renderPosY;
                final double interp3 = interp(entityPlayer.posZ, entityPlayer.lastTickPosZ);
                Minecraft.getMinecraft().getRenderManager();
                final double n3 = interp3 - RenderManager.renderPosZ;
                GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
                GL11.glDisable(2929);
                final Minecraft mc = IamFuckingSanic.mc;
                final float clamp_float = MathHelper.clamp_float(Minecraft.thePlayer.getDistanceToEntity(entityPlayer), 20.0f, Float.MAX_VALUE);
                final double n4 = 0.005 * clamp_float;
                GlStateManager.translate(n, n2, n3);
                Minecraft.getMinecraft().getRenderManager();
                GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                GlStateManager.scale(-0.1, -0.1, 0.0);
                IamFuckingSanic.mc.getTextureManager().bindTexture(sanic);
                Gui.drawScaledCustomSizeModalRect(entityPlayer.width / 2.0f - clamp_float / 3.0f, -entityPlayer.height + 11.0f - clamp_float, 0.0f, 0.0f, 1.0, 1.0, 252.0 * (n4 / 2.0), 200.0 * (n4 / 2.0), 1.0f, 1.0f);
                GL11.glEnable(2929);
            }
        }
    }
    
    public static double interp(final double n, final double n2) {
        return n2 + (n - n2) * Minecraft.getMinecraft().timer.renderPartialTicks;
    }
}
