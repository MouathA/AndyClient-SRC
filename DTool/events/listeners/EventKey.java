package DTool.events.listeners;

import DTool.events.*;

public class EventKey extends Event
{
    public int code;
    
    public EventKey(final int code) {
        this.code = code;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public void setCode(final int code) {
        this.code = code;
    }
}
