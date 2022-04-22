package DTool.util;

import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class Location
{
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    
    public Location(final double x, final double y, final double z, final float yaw, final float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public Location(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }
    
    public Location(final BlockPos blockPos) {
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }
    
    public Location(final EntityLivingBase entityLivingBase) {
        this.x = entityLivingBase.posX;
        this.y = entityLivingBase.posY;
        this.z = entityLivingBase.posZ;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }
    
    public Location(final int n, final int n2, final int n3) {
        this.x = n;
        this.y = n2;
        this.z = n3;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }
    
    public Location add(final int n, final int n2, final int n3) {
        this.x += n;
        this.y += n2;
        this.z += n3;
        return this;
    }
    
    public Location add(final double n, final double n2, final double n3) {
        this.x += n;
        this.y += n2;
        this.z += n3;
        return this;
    }
    
    public Location subtract(final int n, final int n2, final int n3) {
        this.x -= n;
        this.y -= n2;
        this.z -= n3;
        return this;
    }
    
    public Location subtract(final double n, final double n2, final double n3) {
        this.x -= n;
        this.y -= n2;
        this.z -= n3;
        return this;
    }
    
    public Block getBlock() {
        Minecraft.getMinecraft();
        return Minecraft.theWorld.getBlockState(this.toBlockPos()).getBlock();
    }
    
    public double getX() {
        return this.x;
    }
    
    public Location setX(final double x) {
        this.x = x;
        return this;
    }
    
    public double getY() {
        return this.y;
    }
    
    public Location setY(final double y) {
        this.y = y;
        return this;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public Location setZ(final double z) {
        this.z = z;
        return this;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public Location setYaw(final float yaw) {
        this.yaw = yaw;
        return this;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public Location setPitch(final float pitch) {
        this.pitch = pitch;
        return this;
    }
    
    public static Location fromBlockPos(final BlockPos blockPos) {
        return new Location(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    
    public BlockPos toBlockPos() {
        return new BlockPos(this.getX(), this.getY(), this.getZ());
    }
    
    public double distanceTo(final Location location) {
        final double n = location.x - this.x;
        final double n2 = location.z - this.z;
        final double n3 = location.y - this.y;
        return Math.sqrt(n * n + n3 * n3 + n2 * n2);
    }
    
    public double distanceToXZ(final Location location) {
        final double n = location.x - this.x;
        final double n2 = location.z - this.z;
        return Math.sqrt(n * n + n2 * n2);
    }
    
    public double distanceToY(final Location location) {
        final double n = location.y - this.y;
        return Math.sqrt(n * n);
    }
    
    public Vec3 toVector() {
        return new Vec3(this.x, this.y, this.z);
    }
}
