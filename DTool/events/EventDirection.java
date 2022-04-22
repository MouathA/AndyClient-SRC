package DTool.events;

public enum EventDirection
{
    INCOMING("INCOMING", 0), 
    OUTGOING("OUTGOING", 1);
    
    private static final EventDirection[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new EventDirection[] { EventDirection.INCOMING, EventDirection.OUTGOING };
    }
    
    private EventDirection(final String s, final int n) {
    }
}
