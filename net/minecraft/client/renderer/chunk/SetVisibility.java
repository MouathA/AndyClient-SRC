package net.minecraft.client.renderer.chunk;

import net.minecraft.util.*;
import java.util.*;

public class SetVisibility
{
    private static final int field_178623_a;
    private final BitSet field_178622_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002448";
        field_178623_a = EnumFacing.values().length;
    }
    
    public SetVisibility() {
        this.field_178622_b = new BitSet(SetVisibility.field_178623_a * SetVisibility.field_178623_a);
    }
    
    public void func_178620_a(final Set set) {
        for (final EnumFacing enumFacing : set) {
            final Iterator<EnumFacing> iterator2 = set.iterator();
            while (iterator2.hasNext()) {
                this.func_178619_a(enumFacing, iterator2.next(), true);
            }
        }
    }
    
    public void func_178619_a(final EnumFacing enumFacing, final EnumFacing enumFacing2, final boolean b) {
        this.field_178622_b.set(enumFacing.ordinal() + enumFacing2.ordinal() * SetVisibility.field_178623_a, b);
        this.field_178622_b.set(enumFacing2.ordinal() + enumFacing.ordinal() * SetVisibility.field_178623_a, b);
    }
    
    public void func_178618_a(final boolean b) {
        this.field_178622_b.set(0, this.field_178622_b.size(), b);
    }
    
    public boolean func_178621_a(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
        return this.field_178622_b.get(enumFacing.ordinal() + enumFacing2.ordinal() * SetVisibility.field_178623_a);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(' ');
        final EnumFacing[] values = EnumFacing.values();
        int n = 0;
        while (0 < values.length) {
            sb.append(' ').append(values[0].toString().toUpperCase().charAt(0));
            ++n;
        }
        sb.append('\n');
        final EnumFacing[] values2 = EnumFacing.values();
        while (0 < values2.length) {
            final EnumFacing enumFacing = values2[0];
            sb.append(enumFacing.toString().toUpperCase().charAt(0));
            final EnumFacing[] values3 = EnumFacing.values();
            while (0 < values3.length) {
                final EnumFacing enumFacing2 = values3[0];
                if (enumFacing == enumFacing2) {
                    sb.append("  ");
                }
                else {
                    sb.append(' ').append((char)(this.func_178621_a(enumFacing, enumFacing2) ? 89 : 110));
                }
                int n2 = 0;
                ++n2;
            }
            sb.append('\n');
            ++n;
        }
        return sb.toString();
    }
}
