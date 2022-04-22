package net.minecraft.util;

public class Vec4b
{
    private byte field_176117_a;
    private byte field_176115_b;
    private byte field_176116_c;
    private byte field_176114_d;
    private static final String __OBFID;
    
    public Vec4b(final byte field_176117_a, final byte field_176115_b, final byte field_176116_c, final byte field_176114_d) {
        this.field_176117_a = field_176117_a;
        this.field_176115_b = field_176115_b;
        this.field_176116_c = field_176116_c;
        this.field_176114_d = field_176114_d;
    }
    
    public Vec4b(final Vec4b vec4b) {
        this.field_176117_a = vec4b.field_176117_a;
        this.field_176115_b = vec4b.field_176115_b;
        this.field_176116_c = vec4b.field_176116_c;
        this.field_176114_d = vec4b.field_176114_d;
    }
    
    public byte func_176110_a() {
        return this.field_176117_a;
    }
    
    public byte func_176112_b() {
        return this.field_176115_b;
    }
    
    public byte func_176113_c() {
        return this.field_176116_c;
    }
    
    public byte func_176111_d() {
        return this.field_176114_d;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec4b)) {
            return false;
        }
        final Vec4b vec4b = (Vec4b)o;
        return this.field_176117_a == vec4b.field_176117_a && this.field_176114_d == vec4b.field_176114_d && this.field_176115_b == vec4b.field_176115_b && this.field_176116_c == vec4b.field_176116_c;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * this.field_176117_a + this.field_176115_b) + this.field_176116_c) + this.field_176114_d;
    }
    
    static {
        __OBFID = "CL_00001964";
    }
}
