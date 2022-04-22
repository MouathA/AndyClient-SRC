package io.netty.channel.group;

import io.netty.channel.*;

public final class ChannelMatchers
{
    private static final ChannelMatcher ALL_MATCHER;
    private static final ChannelMatcher SERVER_CHANNEL_MATCHER;
    private static final ChannelMatcher NON_SERVER_CHANNEL_MATCHER;
    
    private ChannelMatchers() {
    }
    
    public static ChannelMatcher all() {
        return ChannelMatchers.ALL_MATCHER;
    }
    
    public static ChannelMatcher isNot(final Channel channel) {
        return invert(is(channel));
    }
    
    public static ChannelMatcher is(final Channel channel) {
        return new InstanceMatcher(channel);
    }
    
    public static ChannelMatcher isInstanceOf(final Class clazz) {
        return new ClassMatcher(clazz);
    }
    
    public static ChannelMatcher isNotInstanceOf(final Class clazz) {
        return invert(isInstanceOf(clazz));
    }
    
    public static ChannelMatcher isServerChannel() {
        return ChannelMatchers.SERVER_CHANNEL_MATCHER;
    }
    
    public static ChannelMatcher isNonServerChannel() {
        return ChannelMatchers.NON_SERVER_CHANNEL_MATCHER;
    }
    
    public static ChannelMatcher invert(final ChannelMatcher channelMatcher) {
        return new InvertMatcher(channelMatcher);
    }
    
    public static ChannelMatcher compose(final ChannelMatcher... array) {
        if (array.length < 1) {
            throw new IllegalArgumentException("matchers must at least contain one element");
        }
        if (array.length == 1) {
            return array[0];
        }
        return new CompositeMatcher(array);
    }
    
    static {
        ALL_MATCHER = new ChannelMatcher() {
            @Override
            public boolean matches(final Channel channel) {
                return true;
            }
        };
        SERVER_CHANNEL_MATCHER = isInstanceOf(ServerChannel.class);
        NON_SERVER_CHANNEL_MATCHER = isNotInstanceOf(ServerChannel.class);
    }
    
    private static final class ClassMatcher implements ChannelMatcher
    {
        private final Class clazz;
        
        ClassMatcher(final Class clazz) {
            this.clazz = clazz;
        }
        
        @Override
        public boolean matches(final Channel channel) {
            return this.clazz.isInstance(channel);
        }
    }
    
    private static final class InstanceMatcher implements ChannelMatcher
    {
        private final Channel channel;
        
        InstanceMatcher(final Channel channel) {
            this.channel = channel;
        }
        
        @Override
        public boolean matches(final Channel channel) {
            return this.channel == channel;
        }
    }
    
    private static final class InvertMatcher implements ChannelMatcher
    {
        private final ChannelMatcher matcher;
        
        InvertMatcher(final ChannelMatcher matcher) {
            this.matcher = matcher;
        }
        
        @Override
        public boolean matches(final Channel channel) {
            return !this.matcher.matches(channel);
        }
    }
    
    private static final class CompositeMatcher implements ChannelMatcher
    {
        private final ChannelMatcher[] matchers;
        
        CompositeMatcher(final ChannelMatcher... matchers) {
            this.matchers = matchers;
        }
        
        @Override
        public boolean matches(final Channel channel) {
            final ChannelMatcher[] matchers = this.matchers;
            while (0 < matchers.length) {
                if (!matchers[0].matches(channel)) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
    }
}
