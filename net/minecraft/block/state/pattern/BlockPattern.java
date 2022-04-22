package net.minecraft.block.state.pattern;

import com.google.common.base.*;
import net.minecraft.world.*;
import com.google.common.cache.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockPattern
{
    private final Predicate[][][] field_177689_a;
    private final int field_177687_b;
    private final int field_177688_c;
    private final int field_177686_d;
    private static final String __OBFID;
    
    public BlockPattern(final Predicate[][][] field_177689_a) {
        this.field_177689_a = field_177689_a;
        this.field_177687_b = field_177689_a.length;
        if (this.field_177687_b > 0) {
            this.field_177688_c = field_177689_a[0].length;
            if (this.field_177688_c > 0) {
                this.field_177686_d = field_177689_a[0][0].length;
            }
            else {
                this.field_177686_d = 0;
            }
        }
        else {
            this.field_177688_c = 0;
            this.field_177686_d = 0;
        }
    }
    
    public int func_177685_b() {
        return this.field_177688_c;
    }
    
    public int func_177684_c() {
        return this.field_177686_d;
    }
    
    private PatternHelper func_177682_a(final BlockPos blockPos, final EnumFacing enumFacing, final EnumFacing enumFacing2, final LoadingCache loadingCache) {
        while (0 < this.field_177686_d) {
            while (0 < this.field_177688_c) {
                while (0 < this.field_177687_b) {
                    if (!this.field_177689_a[0][0][0].apply(loadingCache.getUnchecked(func_177683_a(blockPos, enumFacing, enumFacing2, 0, 0, 0)))) {
                        return null;
                    }
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        return new PatternHelper(blockPos, enumFacing, enumFacing2, loadingCache);
    }
    
    public PatternHelper func_177681_a(final World world, final BlockPos blockPos) {
        final LoadingCache build = CacheBuilder.newBuilder().build(new CacheLoader(world));
        final int max = Math.max(Math.max(this.field_177686_d, this.field_177688_c), this.field_177687_b);
        for (final BlockPos blockPos2 : BlockPos.getAllInBox(blockPos, blockPos.add(max - 1, max - 1, max - 1))) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                final EnumFacing enumFacing = values[0];
                final EnumFacing[] values2 = EnumFacing.values();
                while (0 < values2.length) {
                    final EnumFacing enumFacing2 = values2[0];
                    if (enumFacing2 != enumFacing && enumFacing2 != enumFacing.getOpposite()) {
                        final PatternHelper func_177682_a = this.func_177682_a(blockPos2, enumFacing, enumFacing2, build);
                        if (func_177682_a != null) {
                            return func_177682_a;
                        }
                    }
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
        }
        return null;
    }
    
    protected static BlockPos func_177683_a(final BlockPos blockPos, final EnumFacing enumFacing, final EnumFacing enumFacing2, final int n, final int n2, final int n3) {
        if (enumFacing != enumFacing2 && enumFacing != enumFacing2.getOpposite()) {
            final Vec3i vec3i = new Vec3i(enumFacing.getFrontOffsetX(), enumFacing.getFrontOffsetY(), enumFacing.getFrontOffsetZ());
            final Vec3i vec3i2 = new Vec3i(enumFacing2.getFrontOffsetX(), enumFacing2.getFrontOffsetY(), enumFacing2.getFrontOffsetZ());
            final Vec3i crossProduct = vec3i.crossProduct(vec3i2);
            return blockPos.add(vec3i2.getX() * -n2 + crossProduct.getX() * n + vec3i.getX() * n3, vec3i2.getY() * -n2 + crossProduct.getY() * n + vec3i.getY() * n3, vec3i2.getZ() * -n2 + crossProduct.getZ() * n + vec3i.getZ() * n3);
        }
        throw new IllegalArgumentException("Invalid forwards & up combination");
    }
    
    static {
        __OBFID = "CL_00002024";
    }
    
    static class CacheLoader extends com.google.common.cache.CacheLoader
    {
        private final World field_177680_a;
        private static final String __OBFID;
        
        public CacheLoader(final World field_177680_a) {
            this.field_177680_a = field_177680_a;
        }
        
        public BlockWorldState func_177679_a(final BlockPos blockPos) {
            return new BlockWorldState(this.field_177680_a, blockPos);
        }
        
        @Override
        public Object load(final Object o) {
            return this.func_177679_a((BlockPos)o);
        }
        
        static {
            __OBFID = "CL_00002023";
        }
    }
    
    public static class PatternHelper
    {
        private final BlockPos field_177674_a;
        private final EnumFacing field_177672_b;
        private final EnumFacing field_177673_c;
        private final LoadingCache field_177671_d;
        private static final String __OBFID;
        
        public PatternHelper(final BlockPos field_177674_a, final EnumFacing field_177672_b, final EnumFacing field_177673_c, final LoadingCache field_177671_d) {
            this.field_177674_a = field_177674_a;
            this.field_177672_b = field_177672_b;
            this.field_177673_c = field_177673_c;
            this.field_177671_d = field_177671_d;
        }
        
        public EnumFacing func_177669_b() {
            return this.field_177672_b;
        }
        
        public EnumFacing func_177668_c() {
            return this.field_177673_c;
        }
        
        public BlockWorldState func_177670_a(final int n, final int n2, final int n3) {
            return (BlockWorldState)this.field_177671_d.getUnchecked(BlockPattern.func_177683_a(this.field_177674_a, this.func_177669_b(), this.func_177668_c(), n, n2, n3));
        }
        
        static {
            __OBFID = "CL_00002022";
        }
    }
}
