package DTool.modules.visuals;

import DTool.modules.*;
import java.util.concurrent.*;
import Mood.Helpers.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import font.jello.Utils.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.util.*;
import font.jello.*;

public class NemVagyokEgyBuziGyerek extends Module
{
    public double scale;
    public int amount;
    public double ttl;
    private static transient CopyOnWriteArrayList effects;
    private static transient RandomObjectArraylist strings;
    private static transient RandomObjectArraylist colors;
    
    static {
        NemVagyokEgyBuziGyerek.effects = new CopyOnWriteArrayList();
        NemVagyokEgyBuziGyerek.strings = new RandomObjectArraylist((Object[])new String[] { "Gyere ide te buzi!", "Hopp", "Csicska langos", "Horog Pite", "-12HP", "Asszony Veres!", "Porgo Rugas", "1 Hit Kid", "Szajba elvezes", "Respect+", "Szajba hugyozas", "Neger Veres!", "Punci ero!", "MesterMc-s Mozdulat", "AHK", "Bolondos Aura", "26-OS Kar enabled" });
        NemVagyokEgyBuziGyerek.colors = new RandomObjectArraylist((Object[])new Integer[] { 16711680, 65280, 255, 16776960, 16711935, 65535, 16777215 });
    }
    
    public NemVagyokEgyBuziGyerek() {
        super("IdontGayMan", 0, Category.Visuals);
        this.scale = 0.1;
        this.amount = 1;
        this.ttl = 0.1;
    }
    
    @Override
    public void onRender() {
        if (!this.isEnable()) {
            return;
        }
        for (final FX fx : NemVagyokEgyBuziGyerek.effects) {
            if (System.currentTimeMillis() > fx.ttl && fx.opacity <= 5.0) {
                NemVagyokEgyBuziGyerek.effects.remove(fx);
            }
            else {
                if (System.currentTimeMillis() > fx.ttl) {
                    final FX fx2 = fx;
                    fx2.opacity -= fx.opacity / 80.0;
                }
                final FX fx3 = fx;
                fx3.x += fx.motX / 10.0;
                final FX fx4 = fx;
                fx4.y += fx.motY / 10.0;
                final FX fx5 = fx;
                fx5.z += fx.motZ / 10.0;
                final FX fx6 = fx;
                fx6.motX -= fx.motX / 50.0;
                final FX fx7 = fx;
                fx7.motY -= fx.motY / 50.0;
                final FX fx8 = fx;
                fx8.motZ -= fx.motZ / 50.0;
                final String text = fx.text;
                GL11.glPolygonOffset(1.0f, -1100000.0f);
                final double scale = fx.scale;
                GlStateManager.scale(1.0 / scale, 1.0 / scale, 1.0 / scale);
                GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
                final double x = fx.x;
                NemVagyokEgyBuziGyerek.mc.getRenderManager();
                final double n = (x - RenderManager.renderPosX) * scale;
                final double y = fx.y;
                NemVagyokEgyBuziGyerek.mc.getRenderManager();
                final double n2 = (y - RenderManager.renderPosY) * -scale;
                final double z = fx.z;
                NemVagyokEgyBuziGyerek.mc.getRenderManager();
                GlStateManager.translate(n, n2, (z - RenderManager.renderPosZ) * -scale);
                GlStateManager.rotate((float)fx.yaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate((float)fx.pitch, 1.0f, 0.0f, 0.0f);
                final JelloFontRenderer superherofx1 = FontUtil.superherofx1;
                GlStateManager.color(1.0f, 1.0f, 1.0f, (float)(fx.opacity / 100.0));
                final JelloFontRenderer jelloFontRenderer = superherofx1;
                final String s = text;
                final double x2 = fx.x;
                final Minecraft mc = NemVagyokEgyBuziGyerek.mc;
                final double n3 = x2 - Minecraft.fontRendererObj.getStringWidth(text) / 2;
                NemVagyokEgyBuziGyerek.mc.getRenderManager();
                jelloFontRenderer.drawString(s, (float)(n3 - RenderManager.renderPosX), 0.0f, fx.color);
                GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.color(1.0f, 1.0f, 1.0f, (float)(fx.opacity / 100.0));
                final JelloFontRenderer jelloFontRenderer2 = superherofx1;
                final String s2 = text;
                final double x3 = fx.x;
                final Minecraft mc2 = NemVagyokEgyBuziGyerek.mc;
                final double n4 = x3 - Minecraft.fontRendererObj.getStringWidth(text) / 2;
                NemVagyokEgyBuziGyerek.mc.getRenderManager();
                jelloFontRenderer2.drawString(s2, (float)(n4 - RenderManager.renderPosX), 0.0f, fx.color);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        if (NemVagyokEgyBuziGyerek.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            while (0 < this.amount) {
                NemVagyokEgyBuziGyerek.effects.add(new FX((long)this.ttl, NemVagyokEgyBuziGyerek.mc.objectMouseOver.entityHit.posX + new Random().nextInt(3) + new Random().nextDouble() - 2.0, NemVagyokEgyBuziGyerek.mc.objectMouseOver.entityHit.posY + new Random().nextInt(2) + new Random().nextDouble() - 0.5, NemVagyokEgyBuziGyerek.mc.objectMouseOver.entityHit.posZ + new Random().nextInt(3) + new Random().nextDouble() - 2.0, new Random().nextInt(360), new Random().nextInt(180) - 90, 1.0 / this.scale, (String)NemVagyokEgyBuziGyerek.strings.getRandomObject(), (int)NemVagyokEgyBuziGyerek.colors.getRandomObject()));
                final short n5 = 1;
            }
        }
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    public class FX
    {
        public long ttl;
        public double x;
        public double y;
        public double z;
        public double yaw;
        public double pitch;
        public double scale;
        public double motX;
        public double motY;
        public double motZ;
        public double opacity;
        public String text;
        public int color;
        final NemVagyokEgyBuziGyerek this$0;
        
        public FX(final NemVagyokEgyBuziGyerek this$0, final long n, final double x, final double y, final double z, final double yaw, final double pitch, final double scale, final String text, final int color) {
            this.this$0 = this$0;
            this.ttl = System.currentTimeMillis() + n;
            this.x = x;
            this.y = y;
            this.z = z;
            this.motX = new Random().nextInt(3) - 1.0;
            this.motY = new Random().nextDouble();
            this.motZ = new Random().nextInt(3) - 1.0;
            this.yaw = yaw;
            this.pitch = pitch;
            this.scale = scale;
            this.text = text;
            this.color = color;
            this.opacity = 100.0;
        }
    }
}
