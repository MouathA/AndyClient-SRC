package net.minecraft.pathfinding;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class PathNavigateClimber extends PathNavigateGround
{
    private BlockPos field_179696_f;
    private static final String __OBFID;
    
    public PathNavigateClimber(final EntityLiving entityLiving, final World world) {
        super(entityLiving, world);
    }
    
    @Override
    public PathEntity func_179680_a(final BlockPos field_179696_f) {
        this.field_179696_f = field_179696_f;
        return super.func_179680_a(field_179696_f);
    }
    
    @Override
    public PathEntity getPathToEntityLiving(final Entity entity) {
        this.field_179696_f = new BlockPos(entity);
        return super.getPathToEntityLiving(entity);
    }
    
    @Override
    public boolean tryMoveToEntityLiving(final Entity entity, final double speed) {
        final PathEntity pathToEntityLiving = this.getPathToEntityLiving(entity);
        if (pathToEntityLiving != null) {
            return this.setPath(pathToEntityLiving, speed);
        }
        this.field_179696_f = new BlockPos(entity);
        this.speed = speed;
        return true;
    }
    
    @Override
    public void onUpdateNavigation() {
        if (!this.noPath()) {
            super.onUpdateNavigation();
        }
        else if (this.field_179696_f != null) {
            final double n = this.theEntity.width * this.theEntity.width;
            if (this.theEntity.func_174831_c(this.field_179696_f) >= n && (this.theEntity.posY <= this.field_179696_f.getY() || this.theEntity.func_174831_c(new BlockPos(this.field_179696_f.getX(), MathHelper.floor_double(this.theEntity.posY), this.field_179696_f.getZ())) >= n)) {
                this.theEntity.getMoveHelper().setMoveTo(this.field_179696_f.getX(), this.field_179696_f.getY(), this.field_179696_f.getZ(), this.speed);
            }
            else {
                this.field_179696_f = null;
            }
        }
    }
    
    static {
        __OBFID = "CL_00002245";
    }
}
