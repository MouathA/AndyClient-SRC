package com.viaversion.viaversion.libs.fastutil.ints;

public abstract class AbstractIntSpliterator implements IntSpliterator
{
    protected AbstractIntSpliterator() {
    }
    
    @Override
    public final boolean tryAdvance(final IntConsumer intConsumer) {
        return this.tryAdvance((java.util.function.IntConsumer)intConsumer);
    }
    
    @Override
    public final void forEachRemaining(final IntConsumer intConsumer) {
        this.forEachRemaining((java.util.function.IntConsumer)intConsumer);
    }
}
