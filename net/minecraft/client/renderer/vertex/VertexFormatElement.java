package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.*;

public class VertexFormatElement
{
    private static final Logger field_177381_a;
    private final EnumType field_177379_b;
    private final EnumUseage field_177380_c;
    private int field_177377_d;
    private int field_177378_e;
    private int field_177376_f;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002399";
        field_177381_a = LogManager.getLogger();
    }
    
    public VertexFormatElement(final int field_177377_d, final EnumType field_177379_b, final EnumUseage field_177380_c, final int field_177378_e) {
        if (!this.func_177372_a(field_177377_d, field_177380_c)) {
            VertexFormatElement.field_177381_a.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
            this.field_177380_c = EnumUseage.UV;
        }
        else {
            this.field_177380_c = field_177380_c;
        }
        this.field_177379_b = field_177379_b;
        this.field_177377_d = field_177377_d;
        this.field_177378_e = field_177378_e;
        this.field_177376_f = 0;
    }
    
    public void func_177371_a(final int field_177376_f) {
        this.field_177376_f = field_177376_f;
    }
    
    public int func_177373_a() {
        return this.field_177376_f;
    }
    
    private final boolean func_177372_a(final int n, final EnumUseage enumUseage) {
        return n == 0 || enumUseage == EnumUseage.UV;
    }
    
    public final EnumType func_177367_b() {
        return this.field_177379_b;
    }
    
    public final EnumUseage func_177375_c() {
        return this.field_177380_c;
    }
    
    public final int func_177370_d() {
        return this.field_177378_e;
    }
    
    public final int func_177369_e() {
        return this.field_177377_d;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.field_177378_e) + "," + this.field_177380_c.func_177384_a() + "," + this.field_177379_b.func_177396_b();
    }
    
    public final int func_177368_f() {
        return this.field_177379_b.func_177395_a() * this.field_177378_e;
    }
    
    public final boolean func_177374_g() {
        return this.field_177380_c == EnumUseage.POSITION;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final VertexFormatElement vertexFormatElement = (VertexFormatElement)o;
            return this.field_177378_e == vertexFormatElement.field_177378_e && this.field_177377_d == vertexFormatElement.field_177377_d && this.field_177376_f == vertexFormatElement.field_177376_f && this.field_177379_b == vertexFormatElement.field_177379_b && this.field_177380_c == vertexFormatElement.field_177380_c;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * this.field_177379_b.hashCode() + this.field_177380_c.hashCode()) + this.field_177377_d) + this.field_177378_e) + this.field_177376_f;
    }
    
    public final EnumType getType() {
        return this.field_177379_b;
    }
    
    public enum EnumType
    {
        FLOAT("FLOAT", 0, "FLOAT", 0, 4, "Float", 5126), 
        UBYTE("UBYTE", 1, "UBYTE", 1, 1, "Unsigned Byte", 5121), 
        BYTE("BYTE", 2, "BYTE", 2, 1, "Byte", 5120), 
        USHORT("USHORT", 3, "USHORT", 3, 2, "Unsigned Short", 5123), 
        SHORT("SHORT", 4, "SHORT", 4, 2, "Short", 5122), 
        UINT("UINT", 5, "UINT", 5, 4, "Unsigned Int", 5125), 
        INT("INT", 6, "INT", 6, 4, "Int", 5124);
        
        private final int field_177407_h;
        private final String field_177408_i;
        private final int field_177405_j;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002398";
            ENUM$VALUES = new EnumType[] { EnumType.FLOAT, EnumType.UBYTE, EnumType.BYTE, EnumType.USHORT, EnumType.SHORT, EnumType.UINT, EnumType.INT };
            $VALUES = new EnumType[] { EnumType.FLOAT, EnumType.UBYTE, EnumType.BYTE, EnumType.USHORT, EnumType.SHORT, EnumType.UINT, EnumType.INT };
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_177407_h, final String field_177408_i, final int field_177405_j) {
            this.field_177407_h = field_177407_h;
            this.field_177408_i = field_177408_i;
            this.field_177405_j = field_177405_j;
        }
        
        public int func_177395_a() {
            return this.field_177407_h;
        }
        
        public String func_177396_b() {
            return this.field_177408_i;
        }
        
        public int func_177397_c() {
            return this.field_177405_j;
        }
    }
    
    public enum EnumUseage
    {
        POSITION("POSITION", 0, "POSITION", 0, "Position"), 
        NORMAL("NORMAL", 1, "NORMAL", 1, "Normal"), 
        COLOR("COLOR", 2, "COLOR", 2, "Vertex Color"), 
        UV("UV", 3, "UV", 3, "UV"), 
        MATRIX("MATRIX", 4, "MATRIX", 4, "Bone Matrix"), 
        BLEND_WEIGHT("BLEND_WEIGHT", 5, "BLEND_WEIGHT", 5, "Blend Weight"), 
        PADDING("PADDING", 6, "PADDING", 6, "Padding");
        
        private final String field_177392_h;
        private static final EnumUseage[] $VALUES;
        private static final String __OBFID;
        private static final EnumUseage[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002397";
            ENUM$VALUES = new EnumUseage[] { EnumUseage.POSITION, EnumUseage.NORMAL, EnumUseage.COLOR, EnumUseage.UV, EnumUseage.MATRIX, EnumUseage.BLEND_WEIGHT, EnumUseage.PADDING };
            $VALUES = new EnumUseage[] { EnumUseage.POSITION, EnumUseage.NORMAL, EnumUseage.COLOR, EnumUseage.UV, EnumUseage.MATRIX, EnumUseage.BLEND_WEIGHT, EnumUseage.PADDING };
        }
        
        private EnumUseage(final String s, final int n, final String s2, final int n2, final String field_177392_h) {
            this.field_177392_h = field_177392_h;
        }
        
        public String func_177384_a() {
            return this.field_177392_h;
        }
    }
}
