package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.*;
import net.minecraft.block.*;
import com.google.common.base.*;
import java.util.*;

public class BlockStateMapper
{
    private Map field_178450_a;
    private Set field_178449_b;
    private static final String __OBFID;
    
    public BlockStateMapper() {
        this.field_178450_a = Maps.newIdentityHashMap();
        this.field_178449_b = Sets.newIdentityHashSet();
    }
    
    public void func_178447_a(final Block block, final IStateMapper stateMapper) {
        this.field_178450_a.put(block, stateMapper);
    }
    
    public void registerBuiltInBlocks(final Block... array) {
        Collections.addAll(this.field_178449_b, array);
    }
    
    public Map func_178446_a() {
        final IdentityHashMap identityHashMap = Maps.newIdentityHashMap();
        for (final Block block : Block.blockRegistry) {
            if (!this.field_178449_b.contains(block)) {
                identityHashMap.putAll(((IStateMapper)Objects.firstNonNull(this.field_178450_a.get(block), new DefaultStateMapper())).func_178130_a(block));
            }
        }
        return identityHashMap;
    }
    
    static {
        __OBFID = "CL_00002478";
    }
}
