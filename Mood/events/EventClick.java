package Mood.events;

import com.darkmagician6.eventapi.events.callables.*;

public class EventClick extends EventCancellable
{
    private boolean canceled;
    
    @Override
    public boolean isCancelled() {
        return this.canceled;
    }
    
    @Override
    public void setCancelled(final boolean canceled) {
        this.canceled = canceled;
    }
}
