package net.java.games.input;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

final class AWTMouse extends Mouse implements AWTEventListener
{
    private final List awt_events;
    private final List processed_awt_events;
    private int event_state;
    
    protected AWTMouse() {
        super("AWTMouse", createComponents(), new Controller[0], new Rumbler[0]);
        this.awt_events = new ArrayList();
        this.processed_awt_events = new ArrayList();
        this.event_state = 1;
        Toolkit.getDefaultToolkit().addAWTEventListener(this, 131120L);
    }
    
    private static final Component[] createComponents() {
        return new Component[] { new Axis(Component.Identifier.Axis.X), new Axis(Component.Identifier.Axis.Y), new Axis(Component.Identifier.Axis.Z), new Button(Component.Identifier.Button.LEFT), new Button(Component.Identifier.Button.MIDDLE), new Button(Component.Identifier.Button.RIGHT) };
    }
    
    private final void processButtons(final int n, final float value) {
        final Button button = this.getButton(n);
        if (button != null) {
            button.setValue(value);
        }
    }
    
    private final Button getButton(final int n) {
        switch (n) {
            case 1: {
                return (Button)this.getLeft();
            }
            case 2: {
                return (Button)this.getMiddle();
            }
            case 3: {
                return (Button)this.getRight();
            }
            default: {
                return null;
            }
        }
    }
    
    private final void processEvent(final AWTEvent awtEvent) throws IOException {
        if (awtEvent instanceof MouseWheelEvent) {
            final MouseWheelEvent mouseWheelEvent = (MouseWheelEvent)awtEvent;
            final Axis axis = (Axis)this.getWheel();
            axis.setValue(axis.poll() + mouseWheelEvent.getWheelRotation());
        }
        else if (awtEvent instanceof MouseEvent) {
            final MouseEvent mouseEvent = (MouseEvent)awtEvent;
            final Axis axis2 = (Axis)this.getX();
            final Axis axis3 = (Axis)this.getY();
            axis2.setValue((float)mouseEvent.getX());
            axis3.setValue((float)mouseEvent.getY());
            switch (mouseEvent.getID()) {
                case 501: {
                    this.processButtons(mouseEvent.getButton(), 1.0f);
                    break;
                }
                case 502: {
                    this.processButtons(mouseEvent.getButton(), 0.0f);
                    break;
                }
            }
        }
    }
    
    public final synchronized void pollDevice() throws IOException {
        ((Axis)this.getWheel()).setValue(0.0f);
        while (0 < this.awt_events.size()) {
            final AWTEvent awtEvent = this.awt_events.get(0);
            this.processEvent(awtEvent);
            this.processed_awt_events.add(awtEvent);
            int n = 0;
            ++n;
        }
        this.awt_events.clear();
    }
    
    protected final synchronized boolean getNextDeviceEvent(final Event event) throws IOException {
        while (!this.processed_awt_events.isEmpty()) {
            final AWTEvent awtEvent = this.processed_awt_events.get(0);
            if (awtEvent instanceof MouseWheelEvent) {
                final MouseWheelEvent mouseWheelEvent = (MouseWheelEvent)awtEvent;
                event.set(this.getWheel(), (float)mouseWheelEvent.getWheelRotation(), mouseWheelEvent.getWhen() * 1000000L);
                this.processed_awt_events.remove(0);
            }
            else {
                if (!(awtEvent instanceof MouseEvent)) {
                    continue;
                }
                final MouseWheelEvent mouseWheelEvent2 = (MouseWheelEvent)awtEvent;
                final long n = mouseWheelEvent2.getWhen() * 1000000L;
                switch (this.event_state) {
                    case 1: {
                        this.event_state = 2;
                        event.set(this.getX(), (float)mouseWheelEvent2.getX(), n);
                        return true;
                    }
                    case 2: {
                        this.event_state = 4;
                        event.set(this.getY(), (float)mouseWheelEvent2.getY(), n);
                        return true;
                    }
                    case 4: {
                        this.processed_awt_events.remove(0);
                        this.event_state = 1;
                        final Button button = this.getButton(mouseWheelEvent2.getButton());
                        if (button == null) {
                            continue;
                        }
                        switch (mouseWheelEvent2.getID()) {
                            case 501: {
                                event.set(button, 1.0f, n);
                                return true;
                            }
                            case 502: {
                                event.set(button, 0.0f, n);
                                return true;
                            }
                            default: {
                                continue;
                            }
                        }
                        break;
                    }
                    default: {
                        throw new RuntimeException("Unknown event state: " + this.event_state);
                    }
                }
            }
        }
        return false;
    }
    
    public final synchronized void eventDispatched(final AWTEvent awtEvent) {
        this.awt_events.add(awtEvent);
    }
    
    static final class Button extends AbstractComponent
    {
        private float value;
        
        public Button(final Component.Identifier.Button button) {
            super(button.getName(), button);
        }
        
        protected final void setValue(final float value) {
            this.value = value;
        }
        
        protected final float poll() throws IOException {
            return this.value;
        }
        
        public final boolean isAnalog() {
            return false;
        }
        
        public final boolean isRelative() {
            return false;
        }
    }
    
    static final class Axis extends AbstractComponent
    {
        private float value;
        
        public Axis(final Component.Identifier.Axis axis) {
            super(axis.getName(), axis);
        }
        
        public final boolean isRelative() {
            return false;
        }
        
        public final boolean isAnalog() {
            return true;
        }
        
        protected final void setValue(final float value) {
            this.value = value;
        }
        
        protected final float poll() throws IOException {
            return this.value;
        }
    }
}
