package DTool.events;

public interface Cancellable
{
    boolean isCancelled();
    
    void setCancelled(final boolean p0);
}
