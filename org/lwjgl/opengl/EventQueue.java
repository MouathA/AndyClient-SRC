package org.lwjgl.opengl;

import java.nio.*;

class EventQueue
{
    private static final int QUEUE_SIZE = 200;
    private final int event_size;
    private final ByteBuffer queue;
    
    protected EventQueue(final int event_size) {
        this.event_size = event_size;
        this.queue = ByteBuffer.allocate(200 * event_size);
    }
    
    protected synchronized void clearEvents() {
        this.queue.clear();
    }
    
    public synchronized void copyEvents(final ByteBuffer byteBuffer) {
        this.queue.flip();
        final int limit = this.queue.limit();
        if (byteBuffer.remaining() < this.queue.remaining()) {
            this.queue.limit(byteBuffer.remaining() + this.queue.position());
        }
        byteBuffer.put(this.queue);
        this.queue.limit(limit);
        this.queue.compact();
    }
    
    public synchronized boolean putEvent(final ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() != this.event_size) {
            throw new IllegalArgumentException("Internal error: event size " + this.event_size + " does not equal the given event size " + byteBuffer.remaining());
        }
        if (this.queue.remaining() >= byteBuffer.remaining()) {
            this.queue.put(byteBuffer);
            return true;
        }
        return false;
    }
}
