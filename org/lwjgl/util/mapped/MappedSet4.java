package org.lwjgl.util.mapped;

public class MappedSet4
{
    private final MappedObject a;
    private final MappedObject b;
    private final MappedObject c;
    private final MappedObject d;
    public int view;
    
    MappedSet4(final MappedObject a, final MappedObject b, final MappedObject c, final MappedObject d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    void view(final int n) {
        this.a.setViewAddress(this.a.getViewAddress(n));
        this.b.setViewAddress(this.b.getViewAddress(n));
        this.c.setViewAddress(this.c.getViewAddress(n));
        this.d.setViewAddress(this.d.getViewAddress(n));
    }
    
    public void next() {
        this.a.next();
        this.b.next();
        this.c.next();
        this.d.next();
    }
}
