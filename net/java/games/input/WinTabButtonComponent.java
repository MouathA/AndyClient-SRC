package net.java.games.input;

public class WinTabButtonComponent extends WinTabComponent
{
    private int index;
    
    protected WinTabButtonComponent(final WinTabContext winTabContext, final int n, final String s, final Component.Identifier identifier, final int index) {
        super(winTabContext, n, s, identifier);
        this.index = index;
    }
    
    public Event processPacket(final WinTabPacket winTabPacket) {
        final Event event = null;
        final float lastKnownValue = ((winTabPacket.PK_BUTTONS & (int)Math.pow(2.0, this.index)) > 0) ? 1.0f : 0.0f;
        if (lastKnownValue != this.getPollData()) {
            this.lastKnownValue = lastKnownValue;
            final Event event2 = new Event();
            event2.set(this, lastKnownValue, winTabPacket.PK_TIME * 1000L);
            return event2;
        }
        return event;
    }
}
