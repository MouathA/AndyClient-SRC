package optifine;

import net.minecraft.util.*;

public class CustomColorFader
{
    private Vec3 color;
    private long timeUpdate;
    
    public CustomColorFader() {
        this.color = null;
        this.timeUpdate = System.currentTimeMillis();
    }
    
    public Vec3 getColor(final double n, final double n2, final double n3) {
        if (this.color == null) {
            return this.color = new Vec3(n, n2, n3);
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final long n4 = currentTimeMillis - this.timeUpdate;
        if (n4 == 0L) {
            return this.color;
        }
        this.timeUpdate = currentTimeMillis;
        if (Math.abs(n - this.color.xCoord) < 0.004 && Math.abs(n2 - this.color.yCoord) < 0.004 && Math.abs(n3 - this.color.zCoord) < 0.004) {
            return this.color;
        }
        final double limit = Config.limit(n4 * 0.001, 0.0, 1.0);
        return this.color = new Vec3(this.color.xCoord + (n - this.color.xCoord) * limit, this.color.yCoord + (n2 - this.color.yCoord) * limit, this.color.zCoord + (n3 - this.color.zCoord) * limit);
    }
}
