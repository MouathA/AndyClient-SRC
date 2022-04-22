package Mood.Matrix.DefaultParticles;

import java.util.concurrent.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import Mood.Helpers.*;
import org.lwjgl.opengl.*;
import DTool.util.*;
import java.util.*;

public class ParticleEngine
{
    public CopyOnWriteArrayList particles;
    public float lastMouseX;
    public float lastMouseY;
    
    public ParticleEngine() {
        this.particles = Lists.newCopyOnWriteArrayList();
    }
    
    public void render(final float n, final float n2) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        final float n3 = ScaledResolution.getScaledWidth() / 2 - n;
        final float n4 = ScaledResolution.getScaledHeight() / 2 - n2;
        this.particles.size();
        while (this.particles.size() < (int)(ScaledResolution.getScaledWidth() / 19.2f)) {
            this.particles.add(new Particle(scaledResolution, new Random().nextFloat() * 2.0f + 2.0f, new Random().nextFloat() * 5.0f + 5.0f));
        }
        final ArrayList arrayList = Lists.newArrayList();
        for (final Particle particle : this.particles) {
            if (particle.opacity < 32.0f) {
                final Particle particle2 = particle;
                particle2.opacity += 2.0f;
            }
            if (particle.opacity > 32.0f) {
                particle.opacity = 32.0f;
            }
            final Color color = new Color(255, 255, 255, (int)particle.opacity);
            RenderUtils.drawBorderedCircle(particle.x + Math.sin(particle.ticks / 2.0f) * 50.0 + -n3 / 5.0f, particle.ticks * particle.speed * particle.ticks / 10.0f + -n4 / 5.0f, particle.radius * (particle.opacity / 32.0f), color.getRGB(), color.getRGB());
            final Particle particle3 = particle;
            particle3.ticks += (float)0.05;
            if (particle.ticks * particle.speed * particle.ticks / 10.0f + -n4 / 5.0f > ScaledResolution.getScaledHeight() || particle.ticks * particle.speed * particle.ticks / 10.0f + -n4 / 5.0f < 0.0f || particle.x + Math.sin(particle.ticks / 2.0f) * 50.0 + -n3 / 5.0f > ScaledResolution.getScaledWidth() || particle.x + Math.sin(particle.ticks / 2.0f) * 50.0 + -n3 / 5.0f < 0.0) {
                arrayList.add(particle);
            }
        }
        this.particles.removeAll(arrayList);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.lastMouseX = (float)GLUtils.getMouseX();
        this.lastMouseY = (float)GLUtils.getMouseY();
    }
}
