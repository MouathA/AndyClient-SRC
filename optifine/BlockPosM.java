package optifine;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class BlockPosM extends BlockPos
{
    private int mx;
    private int my;
    private int mz;
    private int level;
    private BlockPosM[] facings;
    private boolean needsUpdate;
    
    public BlockPosM(final int n, final int n2, final int n3) {
        this(n, n2, n3, 0);
    }
    
    public BlockPosM(final double n, final double n2, final double n3) {
        this(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3));
    }
    
    public BlockPosM(final int mx, final int my, final int mz, final int level) {
        super(0, 0, 0);
        this.mx = mx;
        this.my = my;
        this.mz = mz;
        this.level = level;
    }
    
    @Override
    public int getX() {
        return this.mx;
    }
    
    @Override
    public int getY() {
        return this.my;
    }
    
    @Override
    public int getZ() {
        return this.mz;
    }
    
    public void setXyz(final int mx, final int my, final int mz) {
        this.mx = mx;
        this.my = my;
        this.mz = mz;
        this.needsUpdate = true;
    }
    
    public void setXyz(final double n, final double n2, final double n3) {
        this.setXyz(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3));
    }
    
    @Override
    public BlockPos offset(final EnumFacing enumFacing) {
        if (this.level <= 0) {
            return super.offset(enumFacing, 1);
        }
        if (this.facings == null) {
            this.facings = new BlockPosM[EnumFacing.VALUES.length];
        }
        if (this.needsUpdate) {
            this.update();
        }
        final int index = enumFacing.getIndex();
        BlockPosM blockPosM = this.facings[index];
        if (blockPosM == null) {
            blockPosM = new BlockPosM(this.mx + enumFacing.getFrontOffsetX(), this.my + enumFacing.getFrontOffsetY(), this.mz + enumFacing.getFrontOffsetZ(), this.level - 1);
            this.facings[index] = blockPosM;
        }
        return blockPosM;
    }
    
    @Override
    public BlockPos offset(final EnumFacing enumFacing, final int n) {
        return (n == 1) ? this.offset(enumFacing) : super.offset(enumFacing, n);
    }
    
    private void update() {
        while (0 < 6) {
            final BlockPosM blockPosM = this.facings[0];
            if (blockPosM != null) {
                final EnumFacing enumFacing = EnumFacing.VALUES[0];
                blockPosM.setXyz(this.mx + enumFacing.getFrontOffsetX(), this.my + enumFacing.getFrontOffsetY(), this.mz + enumFacing.getFrontOffsetZ());
            }
            int n = 0;
            ++n;
        }
        this.needsUpdate = false;
    }
    
    public static Iterable getAllInBoxMutable(final BlockPos blockPos, final BlockPos blockPos2) {
        return new Iterable(new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()))) {
            private final BlockPos val$posFrom;
            private final BlockPos val$posTo;
            
            @Override
            public Iterator iterator() {
                return new AbstractIterator(this.val$posFrom, this.val$posTo) {
                    private BlockPosM theBlockPosM = null;
                    final BlockPosM$1 this$1;
                    private final BlockPos val$posFrom;
                    private final BlockPos val$posTo;
                    
                    protected BlockPosM computeNext0() {
                        if (this.theBlockPosM == null) {
                            return this.theBlockPosM = new BlockPosM(this.val$posFrom.getX(), this.val$posFrom.getY(), this.val$posFrom.getZ(), 3);
                        }
                        if (this.theBlockPosM.equals(this.val$posTo)) {
                            return (BlockPosM)this.endOfData();
                        }
                        int n = this.theBlockPosM.getX();
                        int n2 = this.theBlockPosM.getY();
                        int z = this.theBlockPosM.getZ();
                        if (n < this.val$posTo.getX()) {
                            ++n;
                        }
                        else if (n2 < this.val$posTo.getY()) {
                            n = this.val$posFrom.getX();
                            ++n2;
                        }
                        else if (z < this.val$posTo.getZ()) {
                            n = this.val$posFrom.getX();
                            n2 = this.val$posFrom.getY();
                            ++z;
                        }
                        this.theBlockPosM.setXyz(n, n2, z);
                        return this.theBlockPosM;
                    }
                    
                    @Override
                    protected Object computeNext() {
                        return this.computeNext0();
                    }
                };
            }
        };
    }
    
    public BlockPos getImmutable() {
        return new BlockPos(this.getX(), this.getY(), this.getZ());
    }
}
