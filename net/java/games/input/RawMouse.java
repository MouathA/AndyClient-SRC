package net.java.games.input;

import java.io.*;

final class RawMouse extends Mouse
{
    private final RawDevice device;
    private final RawMouseEvent current_event;
    private int event_state;
    
    protected RawMouse(final String s, final RawDevice device, final Component[] array, final Controller[] array2, final Rumbler[] array3) throws IOException {
        super(s, array, array2, array3);
        this.current_event = new RawMouseEvent();
        this.event_state = 1;
        this.device = device;
    }
    
    public final void pollDevice() throws IOException {
        this.device.pollMouse();
    }
    
    private static final boolean makeButtonEvent(final RawMouseEvent rawMouseEvent, final Event event, final Component component, final int n, final int n2) {
        if ((rawMouseEvent.getButtonFlags() & n) != 0x0) {
            event.set(component, 1.0f, rawMouseEvent.getNanos());
            return true;
        }
        if ((rawMouseEvent.getButtonFlags() & n2) != 0x0) {
            event.set(component, 0.0f, rawMouseEvent.getNanos());
            return true;
        }
        return false;
    }
    
    protected final synchronized boolean getNextDeviceEvent(final Event event) throws IOException {
        while (true) {
            switch (this.event_state) {
                case 1: {
                    if (!this.device.getNextMouseEvent(this.current_event)) {
                        return false;
                    }
                    this.event_state = 2;
                    continue;
                }
                case 2: {
                    final int eventRelativeX = this.device.getEventRelativeX();
                    this.event_state = 3;
                    if (eventRelativeX != 0) {
                        event.set(this.getX(), (float)eventRelativeX, this.current_event.getNanos());
                        return true;
                    }
                    continue;
                }
                case 3: {
                    final int eventRelativeY = this.device.getEventRelativeY();
                    this.event_state = 4;
                    if (eventRelativeY != 0) {
                        event.set(this.getY(), (float)eventRelativeY, this.current_event.getNanos());
                        return true;
                    }
                    continue;
                }
                case 4: {
                    final int wheelDelta = this.current_event.getWheelDelta();
                    this.event_state = 5;
                    if (wheelDelta != 0) {
                        event.set(this.getWheel(), (float)wheelDelta, this.current_event.getNanos());
                        return true;
                    }
                    continue;
                }
                case 5: {
                    this.event_state = 6;
                    if (makeButtonEvent(this.current_event, event, this.getPrimaryButton(), 1, 2)) {
                        return true;
                    }
                    continue;
                }
                case 6: {
                    this.event_state = 7;
                    if (makeButtonEvent(this.current_event, event, this.getSecondaryButton(), 4, 8)) {
                        return true;
                    }
                    continue;
                }
                case 7: {
                    this.event_state = 8;
                    if (makeButtonEvent(this.current_event, event, this.getTertiaryButton(), 16, 32)) {
                        return true;
                    }
                    continue;
                }
                case 8: {
                    this.event_state = 9;
                    if (makeButtonEvent(this.current_event, event, this.getButton3(), 64, 128)) {
                        return true;
                    }
                    continue;
                }
                case 9: {
                    this.event_state = 1;
                    if (makeButtonEvent(this.current_event, event, this.getButton4(), 256, 512)) {
                        return true;
                    }
                    continue;
                }
                default: {
                    throw new RuntimeException("Unknown event state: " + this.event_state);
                }
            }
        }
    }
    
    protected final void setDeviceEventQueueSize(final int bufferSize) throws IOException {
        this.device.setBufferSize(bufferSize);
    }
    
    static final class Button extends AbstractComponent
    {
        private final RawDevice device;
        private final int button_id;
        
        public Button(final RawDevice device, final Component.Identifier.Button button, final int button_id) {
            super(button.getName(), button);
            this.device = device;
            this.button_id = button_id;
        }
        
        protected final float poll() throws IOException {
            return this.device.getButtonState(this.button_id) ? 1.0f : 0.0f;
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
        private final RawDevice device;
        
        public Axis(final RawDevice device, final Component.Identifier.Axis axis) {
            super(axis.getName(), axis);
            this.device = device;
        }
        
        public final boolean isRelative() {
            return true;
        }
        
        public final boolean isAnalog() {
            return true;
        }
        
        protected final float poll() throws IOException {
            if (this.getIdentifier() == Component.Identifier.Axis.X) {
                return (float)this.device.getRelativeX();
            }
            if (this.getIdentifier() == Component.Identifier.Axis.Y) {
                return (float)this.device.getRelativeY();
            }
            if (this.getIdentifier() == Component.Identifier.Axis.Z) {
                return (float)this.device.getWheel();
            }
            throw new RuntimeException("Unknown raw axis: " + this.getIdentifier());
        }
    }
}
