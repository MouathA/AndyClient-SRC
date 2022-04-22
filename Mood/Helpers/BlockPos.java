package Mood.Helpers;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class BlockPos extends Vec3i
{
    public static final net.minecraft.util.BlockPos ORIGIN;
    private static final int NUM_X_BITS;
    private static final int NUM_Z_BITS;
    private static final int NUM_Y_BITS;
    private static final int Y_SHIFT;
    private static final int X_SHIFT;
    private static final long X_MASK;
    private static final long Y_MASK;
    private static final long Z_MASK;
    
    static {
        ORIGIN = new net.minecraft.util.BlockPos(0, 0, 0);
        NUM_X_BITS = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
        NUM_Z_BITS = BlockPos.NUM_X_BITS;
        NUM_Y_BITS = 64 - BlockPos.NUM_X_BITS - BlockPos.NUM_Z_BITS;
        Y_SHIFT = 0 + BlockPos.NUM_Z_BITS;
        X_SHIFT = BlockPos.Y_SHIFT + BlockPos.NUM_Y_BITS;
        X_MASK = (1L << BlockPos.NUM_X_BITS) - 1L;
        Y_MASK = (1L << BlockPos.NUM_Y_BITS) - 1L;
        Z_MASK = (1L << BlockPos.NUM_Z_BITS) - 1L;
    }
    
    public BlockPos(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public BlockPos(final double n, final double n2, final double n3) {
        super(n, n2, n3);
    }
    
    public BlockPos(final Entity entity) {
        this(entity.posX, entity.posY, entity.posZ);
    }
    
    public BlockPos(final Vec3 vec3) {
        this(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
    
    public BlockPos(final Vec3i vec3i) {
        this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }
    
    public BlockPos add(final double n, final double n2, final double n3) {
        return (n == 0.0 && n2 == 0.0 && n3 == 0.0) ? this : new BlockPos(this.getX() + n, this.getY() + n2, this.getZ() + n3);
    }
    
    public BlockPos add(final int n, final int n2, final int n3) {
        return (n == 0 && n2 == 0 && n3 == 0) ? this : new BlockPos(this.getX() + n, this.getY() + n2, this.getZ() + n3);
    }
    
    public BlockPos clone() {
        return new BlockPos(this.getX(), this.getY(), this.getZ());
    }
    
    public BlockPos add(final Vec3i vec3i) {
        return (vec3i.getX() == 0 && vec3i.getY() == 0 && vec3i.getZ() == 0) ? this : new BlockPos(this.getX() + vec3i.getX(), this.getY() + vec3i.getY(), this.getZ() + vec3i.getZ());
    }
    
    public BlockPos subtract(final Vec3i vec3i) {
        return (vec3i.getX() == 0 && vec3i.getY() == 0 && vec3i.getZ() == 0) ? this : new BlockPos(this.getX() - vec3i.getX(), this.getY() - vec3i.getY(), this.getZ() - vec3i.getZ());
    }
    
    public BlockPos up() {
        return this.up(1);
    }
    
    public BlockPos up(final int n) {
        return this.offset(EnumFacing.UP, n);
    }
    
    public BlockPos down() {
        return this.down(1);
    }
    
    public BlockPos down(final int n) {
        return this.offset(EnumFacing.DOWN, n);
    }
    
    public BlockPos north() {
        return this.north(1);
    }
    
    public BlockPos north(final int n) {
        return this.offset(EnumFacing.NORTH, n);
    }
    
    public BlockPos south() {
        return this.south(1);
    }
    
    public BlockPos south(final int n) {
        return this.offset(EnumFacing.SOUTH, n);
    }
    
    public BlockPos west() {
        return this.west(1);
    }
    
    public BlockPos west(final int n) {
        return this.offset(EnumFacing.WEST, n);
    }
    
    public BlockPos east() {
        return this.east(1);
    }
    
    public BlockPos east(final int n) {
        return this.offset(EnumFacing.EAST, n);
    }
    
    public BlockPos offset(final EnumFacing enumFacing) {
        return this.offset(enumFacing, 1);
    }
    
    public BlockPos offset(final EnumFacing enumFacing, final int n) {
        return (n == 0) ? this : new BlockPos(this.getX() + enumFacing.getFrontOffsetX() * n, this.getY() + enumFacing.getFrontOffsetY() * n, this.getZ() + enumFacing.getFrontOffsetZ() * n);
    }
    
    @Override
    public BlockPos crossProduct(final Vec3i vec3i) {
        return new BlockPos(this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(), this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(), this.getX() * vec3i.getY() - this.getY() * vec3i.getX());
    }
    
    public long toLong() {
        return ((long)this.getX() & BlockPos.X_MASK) << BlockPos.X_SHIFT | ((long)this.getY() & BlockPos.Y_MASK) << BlockPos.Y_SHIFT | ((long)this.getZ() & BlockPos.Z_MASK) << 0;
    }
    
    public static BlockPos fromLong(final long n) {
        return new BlockPos((int)(n << 64 - BlockPos.X_SHIFT - BlockPos.NUM_X_BITS >> 64 - BlockPos.NUM_X_BITS), (int)(n << 64 - BlockPos.Y_SHIFT - BlockPos.NUM_Y_BITS >> 64 - BlockPos.NUM_Y_BITS), (int)(n << 64 - BlockPos.NUM_Z_BITS >> 64 - BlockPos.NUM_Z_BITS));
    }
    
    public static Iterable getAllInBox(final BlockPos blockPos, final BlockPos blockPos2) {
        return new Iterable(new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()))) {
            private final BlockPos val$blockpos;
            private final BlockPos val$blockpos1;
            
            @Override
            public Iterator iterator() {
                return new AbstractIterator(this.val$blockpos, this.val$blockpos1) {
                    private BlockPos lastReturned = null;
                    final BlockPos$1 this$1;
                    private final BlockPos val$blockpos;
                    private final BlockPos val$blockpos1;
                    
                    @Override
                    protected BlockPos computeNext() {
                        if (this.lastReturned == null) {
                            return this.lastReturned = this.val$blockpos;
                        }
                        if (this.lastReturned.equals(this.val$blockpos1)) {
                            return (BlockPos)this.endOfData();
                        }
                        int n = this.lastReturned.getX();
                        int n2 = this.lastReturned.getY();
                        int z = this.lastReturned.getZ();
                        if (n < this.val$blockpos1.getX()) {
                            ++n;
                        }
                        else if (n2 < this.val$blockpos1.getY()) {
                            n = this.val$blockpos.getX();
                            ++n2;
                        }
                        else if (z < this.val$blockpos1.getZ()) {
                            n = this.val$blockpos.getX();
                            n2 = this.val$blockpos.getY();
                            ++z;
                        }
                        return this.lastReturned = new BlockPos(n, n2, z);
                    }
                    
                    @Override
                    protected Object computeNext() {
                        return this.computeNext();
                    }
                };
            }
        };
    }
    
    public static Iterable getAllInBoxMutable(final BlockPos blockPos, final BlockPos blockPos2) {
        return new Iterable(new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()))) {
            private final BlockPos val$blockpos;
            private final BlockPos val$blockpos1;
            
            @Override
            public Iterator iterator() {
                return new AbstractIterator(this.val$blockpos, this.val$blockpos1) {
                    private MutableBlockPos theBlockPos = null;
                    final BlockPos$2 this$1;
                    private final BlockPos val$blockpos;
                    private final BlockPos val$blockpos1;
                    
                    @Override
                    protected MutableBlockPos computeNext() {
                        if (this.theBlockPos == null) {
                            return this.theBlockPos = new MutableBlockPos(this.val$blockpos.getX(), this.val$blockpos.getY(), this.val$blockpos.getZ());
                        }
                        if (this.theBlockPos.equals(this.val$blockpos1)) {
                            return (MutableBlockPos)this.endOfData();
                        }
                        int n = this.theBlockPos.getX();
                        int n2 = this.theBlockPos.getY();
                        int z = this.theBlockPos.getZ();
                        if (n < this.val$blockpos1.getX()) {
                            ++n;
                        }
                        else if (n2 < this.val$blockpos1.getY()) {
                            n = this.val$blockpos.getX();
                            ++n2;
                        }
                        else if (z < this.val$blockpos1.getZ()) {
                            n = this.val$blockpos.getX();
                            n2 = this.val$blockpos.getY();
                            ++z;
                        }
                        MutableBlockPos.access$0(this.theBlockPos, n);
                        MutableBlockPos.access$1(this.theBlockPos, n2);
                        MutableBlockPos.access$2(this.theBlockPos, z);
                        return this.theBlockPos;
                    }
                    
                    @Override
                    protected Object computeNext() {
                        return this.computeNext();
                    }
                };
            }
        };
    }
    
    @Override
    public Vec3i crossProduct(final Vec3i vec3i) {
        return this.crossProduct(vec3i);
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    public static final class MutableBlockPos extends BlockPos
    {
        private int x;
        private int y;
        private int z;
        
        public MutableBlockPos() {
            this(0, 0, 0);
        }
        
        public MutableBlockPos(final int x, final int y, final int z) {
            super(0, 0, 0);
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        @Override
        public int getX() {
            return this.x;
        }
        
        @Override
        public int getY() {
            return this.y;
        }
        
        @Override
        public int getZ() {
            return this.z;
        }
        
        public MutableBlockPos func_181079_c(final int x, final int y, final int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }
        
        static void access$0(final MutableBlockPos mutableBlockPos, final int x) {
            mutableBlockPos.x = x;
        }
        
        static void access$1(final MutableBlockPos mutableBlockPos, final int y) {
            mutableBlockPos.y = y;
        }
        
        static void access$2(final MutableBlockPos mutableBlockPos, final int z) {
            mutableBlockPos.z = z;
        }
    }
}
