package Mood.Matrix.DefaultParticles;

import net.minecraft.client.gui.*;
import java.util.*;

public class Particle
{
    public float x;
    public float y;
    public float radius;
    public float speed;
    public float ticks;
    public float opacity;
    
    public Particle(final ScaledResolution scaledResolution, final float radius, final float speed) {
        this.x = new Random().nextFloat() * ScaledResolution.getScaledWidth();
        this.y = new Random().nextFloat() * ScaledResolution.getScaledHeight();
        this.ticks = new Random().nextFloat() * ScaledResolution.getScaledHeight() / 2.0f;
        this.radius = radius;
        this.speed = speed;
    }
}
