package net.minecraft.util;

import net.minecraft.entity.*;
import java.util.*;
import com.google.common.collect.*;

public class BlockPos extends Vec3i
{
    public static final BlockPos ORIGIN;
    private static final int field_177990_b;
    private static final int field_177991_c;
    private static final int field_177989_d;
    private static final int field_177987_f;
    private static final int field_177988_g;
    private static final long field_177994_h;
    private static final long field_177995_i;
    private static final long field_177993_j;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002334";
        ORIGIN = new BlockPos(0, 0, 0);
        field_177990_b = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
        field_177991_c = BlockPos.field_177990_b;
        field_177989_d = 64 - BlockPos.field_177990_b - BlockPos.field_177991_c;
        field_177987_f = 0 + BlockPos.field_177991_c;
        field_177988_g = BlockPos.field_177987_f + BlockPos.field_177989_d;
        field_177994_h = (1L << BlockPos.field_177990_b) - 1L;
        field_177995_i = (1L << BlockPos.field_177989_d) - 1L;
        field_177993_j = (1L << BlockPos.field_177991_c) - 1L;
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
        return new BlockPos(this.getX() + n, this.getY() + n2, this.getZ() + n3);
    }
    
    public BlockPos add(final int n, final int n2, final int n3) {
        return new BlockPos(this.getX() + n, this.getY() + n2, this.getZ() + n3);
    }
    
    public BlockPos add(final Vec3i vec3i) {
        return new BlockPos(this.getX() + vec3i.getX(), this.getY() + vec3i.getY(), this.getZ() + vec3i.getZ());
    }
    
    public BlockPos subtract(final Vec3i vec3i) {
        return new BlockPos(this.getX() - vec3i.getX(), this.getY() - vec3i.getY(), this.getZ() - vec3i.getZ());
    }
    
    public BlockPos multiply(final int n) {
        return new BlockPos(this.getX() * n, this.getY() * n, this.getZ() * n);
    }
    
    public BlockPos offsetUp() {
        return this.offsetUp(1);
    }
    
    public BlockPos offsetUp(final int n) {
        return this.offset(EnumFacing.UP, n);
    }
    
    public BlockPos offsetDown() {
        return this.offsetDown(1);
    }
    
    public BlockPos offsetDown(final int n) {
        return this.offset(EnumFacing.DOWN, n);
    }
    
    public BlockPos offsetNorth() {
        return this.offsetNorth(1);
    }
    
    public BlockPos offsetNorth(final int n) {
        return this.offset(EnumFacing.NORTH, n);
    }
    
    public BlockPos offsetSouth() {
        return this.offsetSouth(1);
    }
    
    public BlockPos offsetSouth(final int n) {
        return this.offset(EnumFacing.SOUTH, n);
    }
    
    public BlockPos offsetWest() {
        return this.offsetWest(1);
    }
    
    public BlockPos offsetWest(final int n) {
        return this.offset(EnumFacing.WEST, n);
    }
    
    public BlockPos offsetEast() {
        return this.offsetEast(1);
    }
    
    public BlockPos offsetEast(final int n) {
        return this.offset(EnumFacing.EAST, n);
    }
    
    public BlockPos offset(final EnumFacing enumFacing) {
        return this.offset(enumFacing, 1);
    }
    
    public BlockPos offset(final EnumFacing enumFacing, final int n) {
        return new BlockPos(this.getX() + enumFacing.getFrontOffsetX() * n, this.getY() + enumFacing.getFrontOffsetY() * n, this.getZ() + enumFacing.getFrontOffsetZ() * n);
    }
    
    public BlockPos crossProductBP(final Vec3i vec3i) {
        return new BlockPos(this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(), this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(), this.getX() * vec3i.getY() - this.getY() * vec3i.getX());
    }
    
    public long toLong() {
        return ((long)this.getX() & BlockPos.field_177994_h) << BlockPos.field_177988_g | ((long)this.getY() & BlockPos.field_177995_i) << BlockPos.field_177987_f | ((long)this.getZ() & BlockPos.field_177993_j) << 0;
    }
    
    public static BlockPos fromLong(final long n) {
        return new BlockPos((int)(n << 64 - BlockPos.field_177988_g - BlockPos.field_177990_b >> 64 - BlockPos.field_177990_b), (int)(n << 64 - BlockPos.field_177987_f - BlockPos.field_177989_d >> 64 - BlockPos.field_177989_d), (int)(n << 64 - BlockPos.field_177991_c >> 64 - BlockPos.field_177991_c));
    }
    
    public static Iterable getAllInBox(final BlockPos blockPos, final BlockPos blockPos2) {
        return new Iterable(new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()))) {
            private static final String __OBFID;
            private final BlockPos val$var2;
            private final BlockPos val$var3;
            
            @Override
            public Iterator iterator() {
                return new AbstractIterator(this.val$var2, this.val$var3) {
                    private BlockPos lastReturned = null;
                    private static final String __OBFID;
                    final BlockPos$1 this$1;
                    private final BlockPos val$var2;
                    private final BlockPos val$var3;
                    
                    protected BlockPos computeNext0() {
                        if (this.lastReturned == null) {
                            return this.lastReturned = this.val$var2;
                        }
                        if (this.lastReturned.equals(this.val$var3)) {
                            return (BlockPos)this.endOfData();
                        }
                        int n = this.lastReturned.getX();
                        int n2 = this.lastReturned.getY();
                        int z = this.lastReturned.getZ();
                        if (n < this.val$var3.getX()) {
                            ++n;
                        }
                        else if (n2 < this.val$var3.getY()) {
                            n = this.val$var2.getX();
                            ++n2;
                        }
                        else if (z < this.val$var3.getZ()) {
                            n = this.val$var2.getX();
                            n2 = this.val$var2.getY();
                            ++z;
                        }
                        return this.lastReturned = new BlockPos(n, n2, z);
                    }
                    
                    @Override
                    protected Object computeNext() {
                        return this.computeNext0();
                    }
                    
                    static {
                        __OBFID = "CL_00002332";
                    }
                };
            }
            
            static {
                __OBFID = "CL_00002333";
            }
        };
    }
    
    public static Iterable getAllInBoxMutable(final BlockPos blockPos, final BlockPos blockPos2) {
        return new Iterable(new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()))) {
            private static final String __OBFID;
            private final BlockPos val$var2;
            private final BlockPos val$var3;
            
            @Override
            public Iterator iterator() {
                return new AbstractIterator(this.val$var2, this.val$var3) {
                    private MutableBlockPos theBlockPos = null;
                    private static final String __OBFID;
                    final BlockPos$2 this$1;
                    private final BlockPos val$var2;
                    private final BlockPos val$var3;
                    
                    protected MutableBlockPos computeNext0() {
                        if (this.theBlockPos == null) {
                            return this.theBlockPos = new MutableBlockPos(this.val$var2.getX(), this.val$var2.getY(), this.val$var2.getZ(), null);
                        }
                        if (this.theBlockPos.equals(this.val$var3)) {
                            return (MutableBlockPos)this.endOfData();
                        }
                        int x = this.theBlockPos.getX();
                        int y = this.theBlockPos.getY();
                        int z = this.theBlockPos.getZ();
                        if (x < this.val$var3.getX()) {
                            ++x;
                        }
                        else if (y < this.val$var3.getY()) {
                            x = this.val$var2.getX();
                            ++y;
                        }
                        else if (z < this.val$var3.getZ()) {
                            x = this.val$var2.getX();
                            y = this.val$var2.getY();
                            ++z;
                        }
                        this.theBlockPos.x = x;
                        this.theBlockPos.y = y;
                        this.theBlockPos.z = z;
                        return this.theBlockPos;
                    }
                    
                    @Override
                    protected Object computeNext() {
                        return this.computeNext0();
                    }
                    
                    static {
                        __OBFID = "CL_00002330";
                    }
                };
            }
            
            static {
                __OBFID = "CL_00002331";
            }
        };
    }
    
    @Override
    public Vec3i crossProduct(final Vec3i vec3i) {
        return this.crossProductBP(vec3i);
    }
    
    public BlockPos down(final int n) {
        return this.offset(EnumFacing.DOWN, n);
    }
    
    public BlockPos up(final int n) {
        return this.offset(EnumFacing.UP, n);
    }
    
    public BlockPos down() {
        return this.down(1);
    }
    
    public BlockPos up() {
        return this.up(1);
    }
    
    public static final class MutableBlockPos extends BlockPos
    {
        public int x;
        public int y;
        public int z;
        private static final String __OBFID;
        
        private MutableBlockPos(final int x, final int y, final int z) {
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
        
        @Override
        public Vec3i crossProduct(final Vec3i vec3i) {
            return super.crossProductBP(vec3i);
        }
        
        MutableBlockPos(final int n, final int n2, final int n3, final Object o) {
            this(n, n2, n3);
        }
        
        static {
            __OBFID = "CL_00002329";
        }
    }
}
