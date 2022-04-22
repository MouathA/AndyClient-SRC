package net.minecraft.util;

public class Tuple
{
    private Object a;
    private Object b;
    private static final String __OBFID;
    
    public Tuple(final Object a, final Object b) {
        this.a = a;
        this.b = b;
    }
    
    public Object getFirst() {
        return this.a;
    }
    
    public Object getSecond() {
        return this.b;
    }
    
    static {
        __OBFID = "CL_00001502";
    }
}
