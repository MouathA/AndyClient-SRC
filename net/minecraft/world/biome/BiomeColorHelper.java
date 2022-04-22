package net.minecraft.world.biome;

import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public class BiomeColorHelper
{
    private static final ColorResolver field_180291_a;
    private static final ColorResolver field_180289_b;
    private static final ColorResolver field_180290_c;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002149";
        field_180291_a = new ColorResolver() {
            private static final String __OBFID;
            
            @Override
            public int func_180283_a(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
                return biomeGenBase.func_180627_b(blockPos);
            }
            
            static {
                __OBFID = "CL_00002148";
            }
        };
        field_180289_b = new ColorResolver() {
            private static final String __OBFID;
            
            @Override
            public int func_180283_a(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
                return biomeGenBase.func_180625_c(blockPos);
            }
            
            static {
                __OBFID = "CL_00002147";
            }
        };
        field_180290_c = new ColorResolver() {
            private static final String __OBFID;
            
            @Override
            public int func_180283_a(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
                return biomeGenBase.waterColorMultiplier;
            }
            
            static {
                __OBFID = "CL_00002146";
            }
        };
    }
    
    private static int func_180285_a(final IBlockAccess blockAccess, final BlockPos blockPos, final ColorResolver colorResolver) {
        for (final BlockPos.MutableBlockPos mutableBlockPos : BlockPos.getAllInBoxMutable(blockPos.add(-1, 0, -1), blockPos.add(1, 0, 1))) {
            colorResolver.func_180283_a(blockAccess.getBiomeGenForCoords(mutableBlockPos), mutableBlockPos);
        }
        return 0;
    }
    
    public static int func_180286_a(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_180285_a(blockAccess, blockPos, BiomeColorHelper.field_180291_a);
    }
    
    public static int func_180287_b(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_180285_a(blockAccess, blockPos, BiomeColorHelper.field_180289_b);
    }
    
    public static int func_180288_c(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_180285_a(blockAccess, blockPos, BiomeColorHelper.field_180290_c);
    }
    
    interface ColorResolver
    {
        int func_180283_a(final BiomeGenBase p0, final BlockPos p1);
    }
}
