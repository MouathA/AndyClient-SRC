package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viaversion.api.connection.*;

public class PlayerPosition extends StoredObject
{
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private int confirmId;
    
    public PlayerPosition(final UserConnection userConnection) {
        super(userConnection);
        this.confirmId = -1;
    }
    
    public void setPos(final double posX, final double posY, final double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
    
    public void setYaw(final float n) {
        this.yaw = n % 360.0f;
    }
    
    public void setPitch(final float n) {
        this.pitch = n % 360.0f;
    }
    
    public double getPosX() {
        return this.posX;
    }
    
    public double getPosY() {
        return this.posY;
    }
    
    public double getPosZ() {
        return this.posZ;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public int getConfirmId() {
        return this.confirmId;
    }
    
    public void setPosX(final double posX) {
        this.posX = posX;
    }
    
    public void setPosY(final double posY) {
        this.posY = posY;
    }
    
    public void setPosZ(final double posZ) {
        this.posZ = posZ;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public void setConfirmId(final int confirmId) {
        this.confirmId = confirmId;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PlayerPosition)) {
            return false;
        }
        final PlayerPosition playerPosition = (PlayerPosition)o;
        return playerPosition.canEqual(this) && Double.compare(this.getPosX(), playerPosition.getPosX()) == 0 && Double.compare(this.getPosY(), playerPosition.getPosY()) == 0 && Double.compare(this.getPosZ(), playerPosition.getPosZ()) == 0 && Float.compare(this.getYaw(), playerPosition.getYaw()) == 0 && Float.compare(this.getPitch(), playerPosition.getPitch()) == 0 && this.isOnGround() == playerPosition.isOnGround() && this.getConfirmId() == playerPosition.getConfirmId();
    }
    
    protected boolean canEqual(final Object o) {
        return o instanceof PlayerPosition;
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.getPosX());
        final int n = 59 + (int)(doubleToLongBits >>> 32 ^ doubleToLongBits);
        final long doubleToLongBits2 = Double.doubleToLongBits(this.getPosY());
        final int n2 = 59 + (int)(doubleToLongBits2 >>> 32 ^ doubleToLongBits2);
        final long doubleToLongBits3 = Double.doubleToLongBits(this.getPosZ());
        final int n3 = 59 + (int)(doubleToLongBits3 >>> 32 ^ doubleToLongBits3);
        final int n4 = 59 + Float.floatToIntBits(this.getYaw());
        final int n5 = 59 + Float.floatToIntBits(this.getPitch());
        final int n6 = 59 + (this.isOnGround() ? 79 : 97);
        final int n7 = 59 + this.getConfirmId();
        return 1;
    }
    
    @Override
    public String toString() {
        return "PlayerPosition(posX=" + this.getPosX() + ", posY=" + this.getPosY() + ", posZ=" + this.getPosZ() + ", yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ", onGround=" + this.isOnGround() + ", confirmId=" + this.getConfirmId() + ")";
    }
}
