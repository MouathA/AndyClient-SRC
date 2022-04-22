package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;

public class DefaultStateMapper extends StateMapperBase
{
    private static final String __OBFID;
    
    @Override
    protected ModelResourceLocation func_178132_a(final IBlockState blockState) {
        return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(blockState.getBlock()), this.func_178131_a(blockState.getProperties()));
    }
    
    static {
        __OBFID = "CL_00002477";
    }
}
