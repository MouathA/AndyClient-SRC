package Mood.Helpers;

public interface Bus
{
    void subscribe(final Object p0);
    
    void unsubscribe(final Object p0);
    
    void post(final Object p0);
}
