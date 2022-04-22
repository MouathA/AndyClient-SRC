package org.lwjgl.util.mapped;

public class MappedSet2
{
    private final MappedObject a;
    private final MappedObject b;
    public int view;
    
    MappedSet2(final MappedObject a, final MappedObject b) {
        this.a = a;
        this.b = b;
    }
    
    void view(final int n) {
        this.a.setViewAddress(this.a.getViewAddress(n));
        this.b.setViewAddress(this.b.getViewAddress(n));
    }
    
    public void next() {
        this.a.next();
        this.b.next();
    }
}
