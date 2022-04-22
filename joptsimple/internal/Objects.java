package joptsimple.internal;

public final class Objects
{
    private Objects() {
        throw new UnsupportedOperationException();
    }
    
    public static void ensureNotNull(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }
}
