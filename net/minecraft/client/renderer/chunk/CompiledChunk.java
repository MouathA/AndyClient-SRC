package net.minecraft.client.renderer.chunk;

import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.tileentity.*;

public class CompiledChunk
{
    public static final CompiledChunk field_178502_a;
    private final boolean[] field_178500_b;
    private final boolean[] field_178501_c;
    private boolean field_178498_d;
    private final List field_178499_e;
    private SetVisibility field_178496_f;
    private WorldRenderer.State field_178497_g;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002456";
        field_178502_a = new CompiledChunk() {
            private static final String __OBFID;
            
            @Override
            protected void func_178486_a(final EnumWorldBlockLayer enumWorldBlockLayer) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public void func_178493_c(final EnumWorldBlockLayer enumWorldBlockLayer) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean func_178495_a(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
                return false;
            }
            
            static {
                __OBFID = "CL_00002455";
            }
        };
    }
    
    public CompiledChunk() {
        this.field_178500_b = new boolean[EnumWorldBlockLayer.values().length];
        this.field_178501_c = new boolean[EnumWorldBlockLayer.values().length];
        this.field_178498_d = true;
        this.field_178499_e = Lists.newArrayList();
        this.field_178496_f = new SetVisibility();
    }
    
    public boolean func_178489_a() {
        return this.field_178498_d;
    }
    
    protected void func_178486_a(final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.field_178498_d = false;
        this.field_178500_b[enumWorldBlockLayer.ordinal()] = true;
    }
    
    public boolean func_178491_b(final EnumWorldBlockLayer enumWorldBlockLayer) {
        return !this.field_178500_b[enumWorldBlockLayer.ordinal()];
    }
    
    public void func_178493_c(final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.field_178501_c[enumWorldBlockLayer.ordinal()] = true;
    }
    
    public boolean func_178492_d(final EnumWorldBlockLayer enumWorldBlockLayer) {
        return this.field_178501_c[enumWorldBlockLayer.ordinal()];
    }
    
    public List func_178485_b() {
        return this.field_178499_e;
    }
    
    public void func_178490_a(final TileEntity tileEntity) {
        this.field_178499_e.add(tileEntity);
    }
    
    public boolean func_178495_a(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
        return this.field_178496_f.func_178621_a(enumFacing, enumFacing2);
    }
    
    public void func_178488_a(final SetVisibility field_178496_f) {
        this.field_178496_f = field_178496_f;
    }
    
    public WorldRenderer.State func_178487_c() {
        return this.field_178497_g;
    }
    
    public void func_178494_a(final WorldRenderer.State field_178497_g) {
        this.field_178497_g = field_178497_g;
    }
}
