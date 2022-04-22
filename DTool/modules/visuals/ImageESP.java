package DTool.modules.visuals;

import DTool.modules.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class ImageESP extends Module
{
    private final ResourceLocation MiaKhalifa;
    private final ResourceLocation DCWITHMILANESP;
    private final ResourceLocation Idk;
    
    public ImageESP() {
        super("PicESP", 0, Category.Visuals);
        this.MiaKhalifa = new ResourceLocation("MooDTool/Tesco/Mia_Khalifa.png");
        this.DCWITHMILANESP = new ResourceLocation("MooDTool/Tesco/DCWITHMILANWITHESP.png");
        this.Idk = new ResourceLocation("MooDTool/Tesco/random.jpeg");
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onRender() {
        if (!this.isEnable()) {
            return;
        }
        final ResourceLocation dcwithmilanesp = this.DCWITHMILANESP;
        for (final EntityPlayer entityPlayer : Minecraft.theWorld.playerEntities) {
            if (entityPlayer.isEntityAlive()) {
                final EntityPlayer entityPlayer2 = entityPlayer;
                final Minecraft mc = ImageESP.mc;
                if (entityPlayer2 == Minecraft.thePlayer || entityPlayer.isInvisible()) {
                    continue;
                }
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
                final Minecraft mc2 = ImageESP.mc;
                final float clamp_float = MathHelper.clamp_float(Minecraft.thePlayer.getDistanceToEntity(entityPlayer), 20.0f, Float.MAX_VALUE);
                final double n4 = 0.005 * clamp_float;
                GlStateManager.translate(n, n2, n3);
                Minecraft.getMinecraft().getRenderManager();
                GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                GlStateManager.scale(-0.1, -0.1, 0.0);
                ImageESP.mc.getTextureManager().bindTexture(dcwithmilanesp);
                Gui.drawScaledCustomSizeModalRect(entityPlayer.width / 2.0f - clamp_float / 3.0f, -entityPlayer.height - clamp_float, 0.0f, 0.0f, 1.0, 1.0, 252.0 * (n4 / 2.0), 476.0 * (n4 / 2.0), 1.0f, 1.0f);
                GL11.glEnable(2929);
            }
        }
    }
    
    public static double interp(final double n, final double n2) {
        return n2 + (n - n2) * Minecraft.getMinecraft().timer.renderPartialTicks;
    }
}
