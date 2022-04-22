package com.viaversion.viaversion.libs.fastutil.ints;

public abstract class AbstractIntIterator implements IntIterator
{
    protected AbstractIntIterator() {
    }
    
    @Override
    public final void forEachRemaining(final IntConsumer intConsumer) {
        this.forEachRemaining((java.util.function.IntConsumer)intConsumer);
    }
}
