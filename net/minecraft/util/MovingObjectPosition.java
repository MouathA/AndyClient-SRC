package net.minecraft.util;

import net.minecraft.entity.*;

public class MovingObjectPosition
{
    private BlockPos field_178783_e;
    public MovingObjectType typeOfHit;
    public EnumFacing field_178784_b;
    public Vec3 hitVec;
    public Entity entityHit;
    private static final String __OBFID;
    
    public MovingObjectPosition(final Vec3 vec3, final EnumFacing enumFacing, final BlockPos blockPos) {
        this(MovingObjectType.BLOCK, vec3, enumFacing, blockPos);
    }
    
    public MovingObjectPosition(final Vec3 vec3, final EnumFacing enumFacing) {
        this(MovingObjectType.BLOCK, vec3, enumFacing, BlockPos.ORIGIN);
    }
    
    public MovingObjectPosition(final Entity entity) {
        this(entity, new Vec3(entity.posX, entity.posY, entity.posZ));
    }
    
    public MovingObjectPosition(final MovingObjectType typeOfHit, final Vec3 vec3, final EnumFacing field_178784_b, final BlockPos field_178783_e) {
        this.typeOfHit = typeOfHit;
        this.field_178783_e = field_178783_e;
        this.field_178784_b = field_178784_b;
        this.hitVec = new Vec3(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
    
    public MovingObjectPosition(final Entity entityHit, final Vec3 hitVec) {
        this.typeOfHit = MovingObjectType.ENTITY;
        this.entityHit = entityHit;
        this.hitVec = hitVec;
    }
    
    public BlockPos func_178782_a() {
        return this.field_178783_e;
    }
    
    @Override
    public String toString() {
        return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.field_178783_e + ", f=" + this.field_178784_b + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
    }
    
    static {
        __OBFID = "CL_00000610";
    }
    
    public enum MovingObjectType
    {
        MISS("MISS", 0, "MISS", 0), 
        BLOCK("BLOCK", 1, "BLOCK", 1), 
        ENTITY("ENTITY", 2, "ENTITY", 2);
        
        private static final MovingObjectType[] $VALUES;
        private static final String __OBFID;
        private static final MovingObjectType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00000611";
            ENUM$VALUES = new MovingObjectType[] { MovingObjectType.MISS, MovingObjectType.BLOCK, MovingObjectType.ENTITY };
            $VALUES = new MovingObjectType[] { MovingObjectType.MISS, MovingObjectType.BLOCK, MovingObjectType.ENTITY };
        }
        
        private MovingObjectType(final String s, final int n, final String s2, final int n2) {
        }
    }
}
