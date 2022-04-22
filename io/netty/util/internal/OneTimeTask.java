package io.netty.util.internal;

public abstract class OneTimeTask extends MpscLinkedQueueNode implements Runnable
{
    @Override
    public Runnable value() {
        return this;
    }
    
    @Override
    public Object value() {
        return this.value();
    }
}
