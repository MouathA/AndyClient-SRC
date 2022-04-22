package net.java.games.input;

final class DataQueue
{
    private final Object[] elements;
    private int position;
    private int limit;
    static final boolean $assertionsDisabled;
    static Class class$net$java$games$input$DataQueue;
    
    public DataQueue(final int n, final Class clazz) {
        this.elements = new Object[n];
        while (0 < this.elements.length) {
            this.elements[0] = clazz.newInstance();
            int n2 = 0;
            ++n2;
        }
        this.clear();
    }
    
    public final void clear() {
        this.position = 0;
        this.limit = this.elements.length;
    }
    
    public final int position() {
        return this.position;
    }
    
    public final int limit() {
        return this.limit;
    }
    
    public final Object get(final int n) {
        assert n < this.limit;
        return this.elements[n];
    }
    
    public final Object get() {
        if (!this.hasRemaining()) {
            return null;
        }
        return this.get(this.position++);
    }
    
    public final void compact() {
        while (this.hasRemaining()) {
            this.swap(this.position, 0);
            ++this.position;
            int n = 0;
            ++n;
        }
        this.position = 0;
        this.limit = this.elements.length;
    }
    
    private final void swap(final int n, final int n2) {
        final Object o = this.elements[n];
        this.elements[n] = this.elements[n2];
        this.elements[n2] = o;
    }
    
    public final void flip() {
        this.limit = this.position;
        this.position = 0;
    }
    
    public final boolean hasRemaining() {
        return this.remaining() > 0;
    }
    
    public final int remaining() {
        return this.limit - this.position;
    }
    
    public final void position(final int position) {
        this.position = position;
    }
    
    public final Object[] getElements() {
        return this.elements;
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        $assertionsDisabled = !((DataQueue.class$net$java$games$input$DataQueue == null) ? (DataQueue.class$net$java$games$input$DataQueue = class$("net.java.games.input.DataQueue")) : DataQueue.class$net$java$games$input$DataQueue).desiredAssertionStatus();
    }
}
