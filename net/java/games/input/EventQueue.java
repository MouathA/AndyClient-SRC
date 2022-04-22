package net.java.games.input;

public final class EventQueue
{
    private final Event[] queue;
    private int head;
    private int tail;
    
    public EventQueue(final int n) {
        this.queue = new Event[n + 1];
        while (0 < this.queue.length) {
            this.queue[0] = new Event();
            int n2 = 0;
            ++n2;
        }
    }
    
    final synchronized void add(final Event event) {
        this.queue[this.tail].set(event);
        this.tail = this.increase(this.tail);
    }
    
    final synchronized boolean isFull() {
        return this.increase(this.tail) == this.head;
    }
    
    private final int increase(final int n) {
        return (n + 1) % this.queue.length;
    }
    
    public final synchronized boolean getNextEvent(final Event event) {
        if (this.head == this.tail) {
            return false;
        }
        event.set(this.queue[this.head]);
        this.head = this.increase(this.head);
        return true;
    }
}
