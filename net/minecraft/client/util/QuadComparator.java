package net.minecraft.client.util;

import java.util.*;
import java.nio.*;

public class QuadComparator implements Comparator
{
    private float field_147630_a;
    private float field_147628_b;
    private float field_147629_c;
    private FloatBuffer field_147627_d;
    private int field_178079_e;
    private static final String __OBFID;
    
    public QuadComparator(final FloatBuffer field_147627_d, final float field_147630_a, final float field_147628_b, final float field_147629_c, final int field_178079_e) {
        this.field_147627_d = field_147627_d;
        this.field_147630_a = field_147630_a;
        this.field_147628_b = field_147628_b;
        this.field_147629_c = field_147629_c;
        this.field_178079_e = field_178079_e;
    }
    
    public int compare(final Integer n, final Integer n2) {
        final float n3 = this.field_147627_d.get(n) - this.field_147630_a;
        final float n4 = this.field_147627_d.get(n + 1) - this.field_147628_b;
        final float n5 = this.field_147627_d.get(n + 2) - this.field_147629_c;
        final float n6 = this.field_147627_d.get(n + this.field_178079_e + 0) - this.field_147630_a;
        final float n7 = this.field_147627_d.get(n + this.field_178079_e + 1) - this.field_147628_b;
        final float n8 = this.field_147627_d.get(n + this.field_178079_e + 2) - this.field_147629_c;
        final float n9 = this.field_147627_d.get(n + this.field_178079_e * 2 + 0) - this.field_147630_a;
        final float n10 = this.field_147627_d.get(n + this.field_178079_e * 2 + 1) - this.field_147628_b;
        final float n11 = this.field_147627_d.get(n + this.field_178079_e * 2 + 2) - this.field_147629_c;
        final float n12 = this.field_147627_d.get(n + this.field_178079_e * 3 + 0) - this.field_147630_a;
        final float n13 = this.field_147627_d.get(n + this.field_178079_e * 3 + 1) - this.field_147628_b;
        final float n14 = this.field_147627_d.get(n + this.field_178079_e * 3 + 2) - this.field_147629_c;
        final float n15 = this.field_147627_d.get(n2) - this.field_147630_a;
        final float n16 = this.field_147627_d.get(n2 + 1) - this.field_147628_b;
        final float n17 = this.field_147627_d.get(n2 + 2) - this.field_147629_c;
        final float n18 = this.field_147627_d.get(n2 + this.field_178079_e + 0) - this.field_147630_a;
        final float n19 = this.field_147627_d.get(n2 + this.field_178079_e + 1) - this.field_147628_b;
        final float n20 = this.field_147627_d.get(n2 + this.field_178079_e + 2) - this.field_147629_c;
        final float n21 = this.field_147627_d.get(n2 + this.field_178079_e * 2 + 0) - this.field_147630_a;
        final float n22 = this.field_147627_d.get(n2 + this.field_178079_e * 2 + 1) - this.field_147628_b;
        final float n23 = this.field_147627_d.get(n2 + this.field_178079_e * 2 + 2) - this.field_147629_c;
        final float n24 = this.field_147627_d.get(n2 + this.field_178079_e * 3 + 0) - this.field_147630_a;
        final float n25 = this.field_147627_d.get(n2 + this.field_178079_e * 3 + 1) - this.field_147628_b;
        final float n26 = this.field_147627_d.get(n2 + this.field_178079_e * 3 + 2) - this.field_147629_c;
        final float n27 = (n3 + n6 + n9 + n12) * 0.25f;
        final float n28 = (n4 + n7 + n10 + n13) * 0.25f;
        final float n29 = (n5 + n8 + n11 + n14) * 0.25f;
        final float n30 = (n15 + n18 + n21 + n24) * 0.25f;
        final float n31 = (n16 + n19 + n22 + n25) * 0.25f;
        final float n32 = (n17 + n20 + n23 + n26) * 0.25f;
        return Float.compare(n30 * n30 + n31 * n31 + n32 * n32, n27 * n27 + n28 * n28 + n29 * n29);
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.compare((Integer)o, (Integer)o2);
    }
    
    static {
        __OBFID = "CL_00000958";
    }
}
