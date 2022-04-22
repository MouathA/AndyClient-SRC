package net.java.games.input;

import java.util.*;
import java.lang.reflect.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

final class AWTKeyboard extends Keyboard implements AWTEventListener
{
    private final List awt_events;
    private Event[] processed_events;
    private int processed_events_index;
    static Class class$java$awt$event$KeyEvent;
    
    protected AWTKeyboard() {
        super("AWTKeyboard", createComponents(), new Controller[0], new Rumbler[0]);
        this.awt_events = new ArrayList();
        Toolkit.getDefaultToolkit().addAWTEventListener(this, 8L);
        this.resizeEventQueue(32);
    }
    
    private static final Component[] createComponents() {
        final ArrayList list = new ArrayList<Key>();
        final Field[] fields = ((AWTKeyboard.class$java$awt$event$KeyEvent == null) ? (AWTKeyboard.class$java$awt$event$KeyEvent = class$("java.awt.event.KeyEvent")) : AWTKeyboard.class$java$awt$event$KeyEvent).getFields();
        while (0 < fields.length) {
            final Field field = fields[0];
            if (Modifier.isStatic(field.getModifiers()) && field.getType() == Integer.TYPE && field.getName().startsWith("VK_")) {
                final Component.Identifier.Key mapKeyCode = AWTKeyMap.mapKeyCode(field.getInt(null));
                if (mapKeyCode != Component.Identifier.Key.UNKNOWN) {
                    list.add(new Key(mapKeyCode));
                }
            }
            int n = 0;
            ++n;
        }
        list.add(new Key(Component.Identifier.Key.RCONTROL));
        list.add(new Key(Component.Identifier.Key.LCONTROL));
        list.add(new Key(Component.Identifier.Key.RSHIFT));
        list.add(new Key(Component.Identifier.Key.LSHIFT));
        list.add(new Key(Component.Identifier.Key.RALT));
        list.add(new Key(Component.Identifier.Key.LALT));
        list.add(new Key(Component.Identifier.Key.NUMPADENTER));
        list.add(new Key(Component.Identifier.Key.RETURN));
        list.add(new Key(Component.Identifier.Key.NUMPADCOMMA));
        list.add(new Key(Component.Identifier.Key.COMMA));
        return (Component[])list.toArray(new Component[0]);
    }
    
    private final void resizeEventQueue(final int n) {
        this.processed_events = new Event[n];
        while (0 < this.processed_events.length) {
            this.processed_events[0] = new Event();
            int n2 = 0;
            ++n2;
        }
        this.processed_events_index = 0;
    }
    
    protected final void setDeviceEventQueueSize(final int n) throws IOException {
        this.resizeEventQueue(n);
    }
    
    public final synchronized void eventDispatched(final AWTEvent awtEvent) {
        if (awtEvent instanceof KeyEvent) {
            this.awt_events.add(awtEvent);
        }
    }
    
    public final synchronized void pollDevice() throws IOException {
        while (0 < this.awt_events.size()) {
            this.processEvent(this.awt_events.get(0));
            int n = 0;
            ++n;
        }
        this.awt_events.clear();
    }
    
    private final void processEvent(final KeyEvent keyEvent) {
        final Component.Identifier.Key map = AWTKeyMap.map(keyEvent);
        if (map == null) {
            return;
        }
        final Key key = (Key)this.getComponent(map);
        if (key == null) {
            return;
        }
        final long n = keyEvent.getWhen() * 1000000L;
        if (keyEvent.getID() == 401) {
            this.addEvent(key, 1.0f, n);
        }
        else if (keyEvent.getID() == 402) {
            final KeyEvent keyEvent2 = (KeyEvent)Toolkit.getDefaultToolkit().getSystemEventQueue().peekEvent(401);
            if (keyEvent2 == null || keyEvent2.getWhen() != keyEvent.getWhen()) {
                this.addEvent(key, 0.0f, n);
            }
        }
    }
    
    private final void addEvent(final Key key, final float value, final long n) {
        key.setValue(value);
        if (this.processed_events_index < this.processed_events.length) {
            this.processed_events[this.processed_events_index++].set(key, value, n);
        }
    }
    
    protected final synchronized boolean getNextDeviceEvent(final Event event) throws IOException {
        if (this.processed_events_index == 0) {
            return false;
        }
        --this.processed_events_index;
        event.set(this.processed_events[0]);
        final Event event2 = this.processed_events[0];
        this.processed_events[0] = this.processed_events[this.processed_events_index];
        this.processed_events[this.processed_events_index] = event2;
        return true;
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    private static final class Key extends AbstractComponent
    {
        private float value;
        
        public Key(final Component.Identifier.Key key) {
            super(key.getName(), key);
        }
        
        public final void setValue(final float value) {
            this.value = value;
        }
        
        protected final float poll() {
            return this.value;
        }
        
        public final boolean isAnalog() {
            return false;
        }
        
        public final boolean isRelative() {
            return false;
        }
    }
}
