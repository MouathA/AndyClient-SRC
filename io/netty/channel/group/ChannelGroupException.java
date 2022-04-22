package io.netty.channel.group;

import io.netty.channel.*;
import java.util.*;

public class ChannelGroupException extends ChannelException implements Iterable
{
    private static final long serialVersionUID = -4093064295562629453L;
    private final Collection failed;
    
    public ChannelGroupException(final Collection collection) {
        if (collection == null) {
            throw new NullPointerException("causes");
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("causes must be non empty");
        }
        this.failed = Collections.unmodifiableCollection((Collection<?>)collection);
    }
    
    @Override
    public Iterator iterator() {
        return this.failed.iterator();
    }
}
