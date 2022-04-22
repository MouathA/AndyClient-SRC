package DTool.events.listeners;

import DTool.events.*;

public class EventChat extends Event
{
    public String message;
    
    public EventChat(final String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
}
