package net.java.games.input;

public class WinTabCursorComponent extends WinTabComponent
{
    private int index;
    
    protected WinTabCursorComponent(final WinTabContext winTabContext, final int n, final String s, final Component.Identifier identifier, final int index) {
        super(winTabContext, n, s, identifier);
        this.index = index;
    }
    
    public Event processPacket(final WinTabPacket winTabPacket) {
        Event event = null;
        if (winTabPacket.PK_CURSOR == this.index && this.lastKnownValue == 0.0f) {
            this.lastKnownValue = 1.0f;
            event = new Event();
            event.set(this, this.lastKnownValue, winTabPacket.PK_TIME * 1000L);
        }
        else if (winTabPacket.PK_CURSOR != this.index && this.lastKnownValue == 1.0f) {
            this.lastKnownValue = 0.0f;
            event = new Event();
            event.set(this, this.lastKnownValue, winTabPacket.PK_TIME * 1000L);
        }
        return event;
    }
}
