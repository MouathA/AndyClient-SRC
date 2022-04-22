package net.minecraft.block.state;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import com.google.common.base.*;

public class BlockWorldState
{
    private final World world;
    private final BlockPos pos;
    private IBlockState field_177514_c;
    private TileEntity field_177511_d;
    private boolean field_177512_e;
    private static final String __OBFID;
    
    public BlockWorldState(final World world, final BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }
    
    public IBlockState func_177509_a() {
        if (this.field_177514_c == null && this.world.isBlockLoaded(this.pos)) {
            this.field_177514_c = this.world.getBlockState(this.pos);
        }
        return this.field_177514_c;
    }
    
    public TileEntity func_177507_b() {
        if (this.field_177511_d == null && !this.field_177512_e) {
            this.field_177511_d = this.world.getTileEntity(this.pos);
            this.field_177512_e = true;
        }
        return this.field_177511_d;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public static Predicate hasState(final Predicate predicate) {
        return new Predicate() {
            private static final String __OBFID;
            private final Predicate val$p_177510_0_;
            
            public boolean func_177503_a(final BlockWorldState blockWorldState) {
                return blockWorldState != null && this.val$p_177510_0_.apply(blockWorldState.func_177509_a());
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_177503_a((BlockWorldState)o);
            }
            
            static {
                __OBFID = "CL_00002025";
            }
        };
    }
    
    static {
        __OBFID = "CL_00002026";
    }
}
