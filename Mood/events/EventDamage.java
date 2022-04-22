package Mood.events;

import com.darkmagician6.eventapi.events.callables.*;

public class EventDamage extends EventCancellable
{
    private boolean cancel;
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
