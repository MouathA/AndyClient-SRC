package net.minecraft.entity.ai;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public abstract class EntityAIMoveToBlock extends EntityAIBase
{
    private final EntityCreature field_179495_c;
    private final double field_179492_d;
    protected int field_179496_a;
    private int field_179493_e;
    private int field_179490_f;
    protected BlockPos field_179494_b;
    private boolean field_179491_g;
    private int field_179497_h;
    private static final String __OBFID;
    
    public EntityAIMoveToBlock(final EntityCreature field_179495_c, final double field_179492_d, final int field_179497_h) {
        this.field_179494_b = BlockPos.ORIGIN;
        this.field_179495_c = field_179495_c;
        this.field_179492_d = field_179492_d;
        this.field_179497_h = field_179497_h;
        this.setMutexBits(5);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.field_179496_a > 0) {
            --this.field_179496_a;
            return false;
        }
        this.field_179496_a = 200 + this.field_179495_c.getRNG().nextInt(200);
        return this.func_179489_g();
    }
    
    @Override
    public boolean continueExecuting() {
        return this.field_179493_e >= -this.field_179490_f && this.field_179493_e <= 1200 && this.func_179488_a(this.field_179495_c.worldObj, this.field_179494_b);
    }
    
    @Override
    public void startExecuting() {
        this.field_179495_c.getNavigator().tryMoveToXYZ((float)this.field_179494_b.getX() + 0.5, this.field_179494_b.getY() + 1, (float)this.field_179494_b.getZ() + 0.5, this.field_179492_d);
        this.field_179493_e = 0;
        this.field_179490_f = this.field_179495_c.getRNG().nextInt(this.field_179495_c.getRNG().nextInt(1200) + 1200) + 1200;
    }
    
    @Override
    public void resetTask() {
    }
    
    @Override
    public void updateTask() {
        if (this.field_179495_c.func_174831_c(this.field_179494_b.offsetUp()) > 1.0) {
            this.field_179491_g = false;
            ++this.field_179493_e;
            if (this.field_179493_e % 40 == 0) {
                this.field_179495_c.getNavigator().tryMoveToXYZ((float)this.field_179494_b.getX() + 0.5, this.field_179494_b.getY() + 1, (float)this.field_179494_b.getZ() + 0.5, this.field_179492_d);
            }
        }
        else {
            this.field_179491_g = true;
            --this.field_179493_e;
        }
    }
    
    protected boolean func_179487_f() {
        return this.field_179491_g;
    }
    
    private boolean func_179489_g() {
        final int field_179497_h = this.field_179497_h;
        final BlockPos blockPos = new BlockPos(this.field_179495_c);
        while (0 <= 1) {
            while (0 < field_179497_h) {
                while (0 <= 0) {
                    for (int i = (0 < 0 && 0 > 0 && false) ? 1 : 0; i <= 0; i = ((i > 0) ? (-i) : (1 - i))) {
                        final BlockPos add = blockPos.add(0, -1, i);
                        if (this.field_179495_c.func_180485_d(add) && this.func_179488_a(this.field_179495_c.worldObj, add)) {
                            this.field_179494_b = add;
                            return true;
                        }
                    }
                    final boolean b = 0 <= 0;
                }
                int n = 0;
                ++n;
            }
            final boolean b2 = 0 <= 0;
        }
        return false;
    }
    
    protected abstract boolean func_179488_a(final World p0, final BlockPos p1);
    
    static {
        __OBFID = "CL_00002252";
    }
}
