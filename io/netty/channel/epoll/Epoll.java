package io.netty.channel.epoll;

public final class Epoll
{
    private static final Throwable UNAVAILABILITY_CAUSE;
    
    public static boolean isAvailable() {
        return Epoll.UNAVAILABILITY_CAUSE == null;
    }
    
    public static void ensureAvailability() {
        if (Epoll.UNAVAILABILITY_CAUSE != null) {
            throw (Error)new UnsatisfiedLinkError("failed to load the required native library").initCause(Epoll.UNAVAILABILITY_CAUSE);
        }
    }
    
    public static Throwable unavailabilityCause() {
        return Epoll.UNAVAILABILITY_CAUSE;
    }
    
    private Epoll() {
    }
    
    static {
        final Throwable unavailability_CAUSE = null;
        Native.epollCreate();
        Native.eventFd();
        if (-1 != -1) {
            Native.close(-1);
        }
        if (-1 != -1) {
            Native.close(-1);
        }
        if (unavailability_CAUSE != null) {
            UNAVAILABILITY_CAUSE = unavailability_CAUSE;
        }
        else {
            UNAVAILABILITY_CAUSE = null;
        }
    }
}
