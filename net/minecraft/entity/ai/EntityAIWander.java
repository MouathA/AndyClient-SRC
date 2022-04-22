package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIWander extends EntityAIBase
{
    private EntityCreature entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    private int field_179481_f;
    private boolean field_179482_g;
    private static final String __OBFID;
    
    public EntityAIWander(final EntityCreature entityCreature, final double n) {
        this(entityCreature, n, 120);
    }
    
    public EntityAIWander(final EntityCreature entity, final double speed, final int field_179481_f) {
        this.entity = entity;
        this.speed = speed;
        this.field_179481_f = field_179481_f;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.field_179482_g) {
            if (this.entity.getAge() >= 100) {
                return false;
            }
            if (this.entity.getRNG().nextInt(this.field_179481_f) != 0) {
                return false;
            }
        }
        final Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
        if (randomTarget == null) {
            return false;
        }
        this.xPosition = randomTarget.xCoord;
        this.yPosition = randomTarget.yCoord;
        this.zPosition = randomTarget.zCoord;
        this.field_179482_g = false;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entity.getNavigator().noPath();
    }
    
    @Override
    public void startExecuting() {
        this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
    
    public void func_179480_f() {
        this.field_179482_g = true;
    }
    
    public void func_179479_b(final int field_179481_f) {
        this.field_179481_f = field_179481_f;
    }
    
    static {
        __OBFID = "CL_00001608";
    }
}
