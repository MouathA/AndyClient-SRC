package io.netty.util.internal;

public final class NoOpTypeParameterMatcher extends TypeParameterMatcher
{
    @Override
    public boolean match(final Object o) {
        return true;
    }
}
