package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viaversion.api.connection.*;

public class PlayerAbilities extends StoredObject
{
    private boolean sprinting;
    private boolean allowFly;
    private boolean flying;
    private boolean invincible;
    private boolean creative;
    private float flySpeed;
    private float walkSpeed;
    
    public PlayerAbilities(final UserConnection userConnection) {
        super(userConnection);
    }
    
    public byte getFlags() {
        if (this.invincible) {
            final byte b = 8;
        }
        if (this.allowFly) {
            final byte b2 = 4;
        }
        if (this.flying) {
            final byte b3 = 2;
        }
        if (this.creative) {
            final byte b4 = 1;
        }
        return 0;
    }
    
    public boolean isSprinting() {
        return this.sprinting;
    }
    
    public boolean isAllowFly() {
        return this.allowFly;
    }
    
    public boolean isFlying() {
        return this.flying;
    }
    
    public boolean isInvincible() {
        return this.invincible;
    }
    
    public boolean isCreative() {
        return this.creative;
    }
    
    public float getFlySpeed() {
        return this.flySpeed;
    }
    
    public float getWalkSpeed() {
        return this.walkSpeed;
    }
    
    public void setSprinting(final boolean sprinting) {
        this.sprinting = sprinting;
    }
    
    public void setAllowFly(final boolean allowFly) {
        this.allowFly = allowFly;
    }
    
    public void setFlying(final boolean flying) {
        this.flying = flying;
    }
    
    public void setInvincible(final boolean invincible) {
        this.invincible = invincible;
    }
    
    public void setCreative(final boolean creative) {
        this.creative = creative;
    }
    
    public void setFlySpeed(final float flySpeed) {
        this.flySpeed = flySpeed;
    }
    
    public void setWalkSpeed(final float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PlayerAbilities)) {
            return false;
        }
        final PlayerAbilities playerAbilities = (PlayerAbilities)o;
        return playerAbilities.canEqual(this) && this.isSprinting() == playerAbilities.isSprinting() && this.isAllowFly() == playerAbilities.isAllowFly() && this.isFlying() == playerAbilities.isFlying() && this.isInvincible() == playerAbilities.isInvincible() && this.isCreative() == playerAbilities.isCreative() && Float.compare(this.getFlySpeed(), playerAbilities.getFlySpeed()) == 0 && Float.compare(this.getWalkSpeed(), playerAbilities.getWalkSpeed()) == 0;
    }
    
    protected boolean canEqual(final Object o) {
        return o instanceof PlayerAbilities;
    }
    
    @Override
    public int hashCode() {
        final int n = 59 + (this.isSprinting() ? 79 : 97);
        final int n2 = 59 + (this.isAllowFly() ? 79 : 97);
        final int n3 = 59 + (this.isFlying() ? 79 : 97);
        final int n4 = 59 + (this.isInvincible() ? 79 : 97);
        final int n5 = 59 + (this.isCreative() ? 79 : 97);
        final int n6 = 59 + Float.floatToIntBits(this.getFlySpeed());
        final int n7 = 59 + Float.floatToIntBits(this.getWalkSpeed());
        return 1;
    }
    
    @Override
    public String toString() {
        return "PlayerAbilities(sprinting=" + this.isSprinting() + ", allowFly=" + this.isAllowFly() + ", flying=" + this.isFlying() + ", invincible=" + this.isInvincible() + ", creative=" + this.isCreative() + ", flySpeed=" + this.getFlySpeed() + ", walkSpeed=" + this.getWalkSpeed() + ")";
    }
}
