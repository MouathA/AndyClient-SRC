package org.lwjgl.opencl;

import org.lwjgl.*;

abstract class CLObjectChild extends CLObjectRetainable
{
    private final CLObject parent;
    
    protected CLObjectChild(final long n, final CLObject parent) {
        super(n);
        if (LWJGLUtil.DEBUG && parent != null && !parent.isValid()) {
            throw new IllegalStateException("The parent specified is not a valid CL object.");
        }
        this.parent = parent;
    }
    
    public CLObject getParent() {
        return this.parent;
    }
}
