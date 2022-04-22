package net.minecraft.client.renderer;

import net.minecraft.util.*;

public enum EnumFaceing
{
    DOWN("DOWN", 0, "DOWN", 0, new VertexInformation[] { new VertexInformation(Constants.field_179176_f, Constants.field_179178_e, Constants.field_179181_a, null), new VertexInformation(Constants.field_179176_f, Constants.field_179178_e, Constants.field_179177_d, null), new VertexInformation(Constants.field_179180_c, Constants.field_179178_e, Constants.field_179177_d, null), new VertexInformation(Constants.field_179180_c, Constants.field_179178_e, Constants.field_179181_a, null) }), 
    UP("UP", 1, "UP", 1, new VertexInformation[] { new VertexInformation(Constants.field_179176_f, Constants.field_179179_b, Constants.field_179177_d, null), new VertexInformation(Constants.field_179176_f, Constants.field_179179_b, Constants.field_179181_a, null), new VertexInformation(Constants.field_179180_c, Constants.field_179179_b, Constants.field_179181_a, null), new VertexInformation(Constants.field_179180_c, Constants.field_179179_b, Constants.field_179177_d, null) }), 
    NORTH("NORTH", 2, "NORTH", 2, new VertexInformation[] { new VertexInformation(Constants.field_179180_c, Constants.field_179179_b, Constants.field_179177_d, null), new VertexInformation(Constants.field_179180_c, Constants.field_179178_e, Constants.field_179177_d, null), new VertexInformation(Constants.field_179176_f, Constants.field_179178_e, Constants.field_179177_d, null), new VertexInformation(Constants.field_179176_f, Constants.field_179179_b, Constants.field_179177_d, null) }), 
    SOUTH("SOUTH", 3, "SOUTH", 3, new VertexInformation[] { new VertexInformation(Constants.field_179176_f, Constants.field_179179_b, Constants.field_179181_a, null), new VertexInformation(Constants.field_179176_f, Constants.field_179178_e, Constants.field_179181_a, null), new VertexInformation(Constants.field_179180_c, Constants.field_179178_e, Constants.field_179181_a, null), new VertexInformation(Constants.field_179180_c, Constants.field_179179_b, Constants.field_179181_a, null) }), 
    WEST("WEST", 4, "WEST", 4, new VertexInformation[] { new VertexInformation(Constants.field_179176_f, Constants.field_179179_b, Constants.field_179177_d, null), new VertexInformation(Constants.field_179176_f, Constants.field_179178_e, Constants.field_179177_d, null), new VertexInformation(Constants.field_179176_f, Constants.field_179178_e, Constants.field_179181_a, null), new VertexInformation(Constants.field_179176_f, Constants.field_179179_b, Constants.field_179181_a, null) }), 
    EAST("EAST", 5, "EAST", 5, new VertexInformation[] { new VertexInformation(Constants.field_179180_c, Constants.field_179179_b, Constants.field_179181_a, null), new VertexInformation(Constants.field_179180_c, Constants.field_179178_e, Constants.field_179181_a, null), new VertexInformation(Constants.field_179180_c, Constants.field_179178_e, Constants.field_179177_d, null), new VertexInformation(Constants.field_179180_c, Constants.field_179179_b, Constants.field_179177_d, null) });
    
    private static final EnumFaceing[] field_179029_g;
    private final VertexInformation[] field_179035_h;
    private static final EnumFaceing[] $VALUES;
    private static final String __OBFID;
    private static final EnumFaceing[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00002562";
        ENUM$VALUES = new EnumFaceing[] { EnumFaceing.DOWN, EnumFaceing.UP, EnumFaceing.NORTH, EnumFaceing.SOUTH, EnumFaceing.WEST, EnumFaceing.EAST };
        field_179029_g = new EnumFaceing[6];
        $VALUES = new EnumFaceing[] { EnumFaceing.DOWN, EnumFaceing.UP, EnumFaceing.NORTH, EnumFaceing.SOUTH, EnumFaceing.WEST, EnumFaceing.EAST };
        EnumFaceing.field_179029_g[Constants.field_179178_e] = EnumFaceing.DOWN;
        EnumFaceing.field_179029_g[Constants.field_179179_b] = EnumFaceing.UP;
        EnumFaceing.field_179029_g[Constants.field_179177_d] = EnumFaceing.NORTH;
        EnumFaceing.field_179029_g[Constants.field_179181_a] = EnumFaceing.SOUTH;
        EnumFaceing.field_179029_g[Constants.field_179176_f] = EnumFaceing.WEST;
        EnumFaceing.field_179029_g[Constants.field_179180_c] = EnumFaceing.EAST;
    }
    
    public static EnumFaceing func_179027_a(final EnumFacing enumFacing) {
        return EnumFaceing.field_179029_g[enumFacing.getIndex()];
    }
    
    private EnumFaceing(final String s, final int n, final String s2, final int n2, final VertexInformation... field_179035_h) {
        this.field_179035_h = field_179035_h;
    }
    
    public VertexInformation func_179025_a(final int n) {
        return this.field_179035_h[n];
    }
    
    public static final class Constants
    {
        public static final int field_179181_a;
        public static final int field_179179_b;
        public static final int field_179180_c;
        public static final int field_179177_d;
        public static final int field_179178_e;
        public static final int field_179176_f;
        private static final String __OBFID;
        
        static {
            __OBFID = "CL_00002560";
            field_179181_a = EnumFacing.SOUTH.getIndex();
            field_179179_b = EnumFacing.UP.getIndex();
            field_179180_c = EnumFacing.EAST.getIndex();
            field_179177_d = EnumFacing.NORTH.getIndex();
            field_179178_e = EnumFacing.DOWN.getIndex();
            field_179176_f = EnumFacing.WEST.getIndex();
        }
    }
    
    public static class VertexInformation
    {
        public final int field_179184_a;
        public final int field_179182_b;
        public final int field_179183_c;
        private static final String __OBFID;
        
        private VertexInformation(final int field_179184_a, final int field_179182_b, final int field_179183_c) {
            this.field_179184_a = field_179184_a;
            this.field_179182_b = field_179182_b;
            this.field_179183_c = field_179183_c;
        }
        
        VertexInformation(final int n, final int n2, final int n3, final Object o) {
            this(n, n2, n3);
        }
        
        static {
            __OBFID = "CL_00002559";
        }
    }
}
