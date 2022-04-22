package Mood.events;

public class EventPreMotionUpdates extends PlayerUpdateEvent
{
    public double x;
    public double y;
    public double z;
    private double oldX;
    private double oldY;
    private double oldZ;
    private float oldYaw;
    private float oldPitch;
    private boolean sprinting;
    private boolean wasSprinting;
    private boolean wasSneaking;
    
    public EventPreMotionUpdates(final double x, final double y, final double z, final double oldX, final double oldY, final double oldZ, final float yaw, final float pitch, final float oldYaw, final float oldPitch, final boolean sprinting, final boolean wasSprinting, final boolean sneaking, final boolean wasSneaking, final boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.oldX = oldX;
        this.oldY = oldY;
        this.oldZ = oldZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.oldYaw = oldYaw;
        this.oldPitch = oldPitch;
        this.sprinting = sprinting;
        this.wasSprinting = wasSprinting;
        this.sneaking = sneaking;
        this.wasSneaking = wasSneaking;
        this.onGround = onGround;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public double getOldX() {
        return this.oldX;
    }
    
    public double getOldY() {
        return this.oldY;
    }
    
    public double getOldZ() {
        return this.oldZ;
    }
    
    public double getOldYaw() {
        return this.oldYaw;
    }
    
    public double getOldPitch() {
        return this.oldPitch;
    }
    
    public boolean isSprinting() {
        return this.sprinting;
    }
    
    public void setSprinting(final boolean sprinting) {
        this.sprinting = sprinting;
    }
    
    public boolean wasSprinting() {
        return this.wasSprinting;
    }
    
    public boolean wasSneaking() {
        return this.wasSneaking;
    }
    
    public boolean isMoving() {
        final double n = this.x - this.oldX;
        final double n2 = this.y - this.oldY;
        final double n3 = this.z - this.oldZ;
        return n * n + n2 * n2 + n3 * n3 > 9.0E-4;
    }
    
    public boolean isRotating() {
        final double n = this.yaw - this.oldYaw;
        final double n2 = this.pitch - this.oldPitch;
        return n != 0.0 || n2 != 0.0;
    }
}
