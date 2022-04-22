package DTool.util;

import net.minecraft.client.renderer.culling.*;
import net.minecraft.util.*;

public class Frustum implements ICamera
{
    private final ClippingHelper clippingHelper;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    
    public Frustum() {
        this(ClippingHelperImpl.getInstance());
    }
    
    public Frustum(final ClippingHelper clippingHelper) {
        this.clippingHelper = clippingHelper;
    }
    
    @Override
    public void setPosition(final double xPosition, final double yPosition, final double zPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zPosition = zPosition;
    }
    
    public boolean isBoxInFrustum(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        return this.clippingHelper.isBoxInFrustum(n - this.xPosition, n2 - this.yPosition, n3 - this.zPosition, n4 - this.xPosition, n5 - this.yPosition, n6 - this.zPosition);
    }
    
    @Override
    public boolean isBoundingBoxInFrustum(final AxisAlignedBB axisAlignedBB) {
        return this.isBoxInFrustum(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
    }
}
