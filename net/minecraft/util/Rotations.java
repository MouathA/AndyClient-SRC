package net.minecraft.util;

import net.minecraft.nbt.*;

public class Rotations
{
    protected final float field_179419_a;
    protected final float field_179417_b;
    protected final float field_179418_c;
    private static final String __OBFID;
    
    public Rotations(final float field_179419_a, final float field_179417_b, final float field_179418_c) {
        this.field_179419_a = field_179419_a;
        this.field_179417_b = field_179417_b;
        this.field_179418_c = field_179418_c;
    }
    
    public Rotations(final NBTTagList list) {
        this.field_179419_a = list.getFloat(0);
        this.field_179417_b = list.getFloat(1);
        this.field_179418_c = list.getFloat(2);
    }
    
    public NBTTagList func_179414_a() {
        final NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagFloat(this.field_179419_a));
        list.appendTag(new NBTTagFloat(this.field_179417_b));
        list.appendTag(new NBTTagFloat(this.field_179418_c));
        return list;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Rotations)) {
            return false;
        }
        final Rotations rotations = (Rotations)o;
        return this.field_179419_a == rotations.field_179419_a && this.field_179417_b == rotations.field_179417_b && this.field_179418_c == rotations.field_179418_c;
    }
    
    public float func_179415_b() {
        return this.field_179419_a;
    }
    
    public float func_179416_c() {
        return this.field_179417_b;
    }
    
    public float func_179413_d() {
        return this.field_179418_c;
    }
    
    static {
        __OBFID = "CL_00002316";
    }
}
