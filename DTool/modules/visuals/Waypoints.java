package DTool.modules.visuals;

import DTool.modules.*;
import DTool.util.*;
import org.lwjgl.opengl.*;
import Mood.Helpers.*;
import java.util.*;

public class Waypoints extends Module
{
    public Waypoints() {
        super("Waypoints", 0, Category.Visuals);
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
        for (final Waypoint waypoint : Waypoint.wayPoints) {
            waypoint.update();
            GL11.glDisable(2896);
            RenderUtils.drawESP(waypoint.dX, waypoint.dY, waypoint.dZ, waypoint.red, waypoint.green, waypoint.blue);
            GL11.glEnable(2896);
            RenderUtils.drawWayPointTracer(waypoint);
        }
    }
}
