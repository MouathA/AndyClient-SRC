package Mood.events;

import com.darkmagician6.eventapi.events.*;

public final class KeyPressedEvent implements Event
{
    private boolean canceled;
    private final int eventKey;
    
    public KeyPressedEvent(final int eventKey) {
        this.eventKey = eventKey;
    }
    
    public int getEventKey() {
        return this.eventKey;
    }
    
    public boolean isCancelled() {
        return this.canceled;
    }
    
    public void setCancelled(final boolean canceled) {
        this.canceled = canceled;
    }
}
