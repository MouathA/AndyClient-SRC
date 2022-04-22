package DTool.util;

import java.util.*;
import net.minecraft.client.renderer.entity.*;

public class Waypoint
{
    public static ArrayList wayPoints;
    private String name;
    private double posX;
    private double posY;
    private double posZ;
    public double dX;
    public double dY;
    public double dZ;
    public float red;
    public float green;
    public float blue;
    
    static {
        Waypoint.wayPoints = new ArrayList();
    }
    
    public Waypoint(final String name, final double posX, final double posY, final double posZ, final double n, final double n2, final double n3) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.red = new Random().nextInt(255) / 255.0f;
        this.green = new Random().nextInt(255) / 255.0f;
        this.blue = new Random().nextInt(255) / 255.0f;
        System.out.println(String.valueOf(this.red) + " " + this.green + " " + this.blue);
        this.update();
        Waypoint.wayPoints.add(this);
    }
    
    public void update() {
        this.dX = (int)this.posX - RenderManager.renderPosX;
        this.dY = (int)this.posY - RenderManager.renderPosY;
        this.dZ = (int)this.posZ - RenderManager.renderPosZ;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getX() {
        return this.posX;
    }
    
    public double getY() {
        return this.posY;
    }
    
    public double getZ() {
        return this.posZ;
    }
}
