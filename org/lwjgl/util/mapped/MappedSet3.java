package org.lwjgl.util.mapped;

public class MappedSet3
{
    private final MappedObject a;
    private final MappedObject b;
    private final MappedObject c;
    public int view;
    
    MappedSet3(final MappedObject a, final MappedObject b, final MappedObject c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    void view(final int n) {
        this.a.setViewAddress(this.a.getViewAddress(n));
        this.b.setViewAddress(this.b.getViewAddress(n));
        this.c.setViewAddress(this.c.getViewAddress(n));
    }
    
    public void next() {
        this.a.next();
        this.b.next();
        this.c.next();
    }
}
