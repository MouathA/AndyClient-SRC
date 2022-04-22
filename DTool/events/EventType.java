package DTool.events;

public enum EventType
{
    PRE("PRE", 0), 
    POST("POST", 1);
    
    private static final EventType[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new EventType[] { EventType.PRE, EventType.POST };
    }
    
    private EventType(final String s, final int n) {
    }
}
