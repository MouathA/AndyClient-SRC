package Mood.events;

import com.darkmagician6.eventapi.events.callables.*;

public class EventChatSend extends EventCancellable
{
    private static String message;
    private boolean canceled;
    
    public static String getMessage() {
        return EventChatSend.message;
    }
    
    public void setMessage(final String message) {
        EventChatSend.message = message;
    }
    
    @Override
    public boolean isCancelled() {
        return this.canceled;
    }
    
    @Override
    public void setCancelled(final boolean canceled) {
        this.canceled = canceled;
    }
}
