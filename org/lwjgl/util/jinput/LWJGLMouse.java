package org.lwjgl.util.jinput;

import java.io.*;
import net.java.games.input.*;

final class LWJGLMouse extends Mouse
{
    private static final int EVENT_X = 1;
    private static final int EVENT_Y = 2;
    private static final int EVENT_WHEEL = 3;
    private static final int EVENT_BUTTON = 4;
    private static final int EVENT_DONE = 5;
    private int event_state;
    
    LWJGLMouse() {
        super("LWJGLMouse", createComponents(), new Controller[0], new Rumbler[0]);
        this.event_state = 5;
    }
    
    private static Component[] createComponents() {
        return new Component[] { new Axis(Component.Identifier.Axis.X), new Axis(Component.Identifier.Axis.Y), new Axis(Component.Identifier.Axis.Z), new Button(Component.Identifier.Button.LEFT), new Button(Component.Identifier.Button.MIDDLE), new Button(Component.Identifier.Button.RIGHT) };
    }
    
    public synchronized void pollDevice() throws IOException {
        if (!org.lwjgl.input.Mouse.isCreated()) {
            return;
        }
        while (true) {
            this.setButtonState(0);
            int n = 0;
            ++n;
        }
    }
    
    private Button map(final int n) {
        switch (n) {
            case 0: {
                return (Button)this.getLeft();
            }
            case 1: {
                return (Button)this.getRight();
            }
            case 2: {
                return (Button)this.getMiddle();
            }
            default: {
                return null;
            }
        }
    }
    
    private void setButtonState(final int n) {
        final Button map = this.map(n);
        if (map != null) {
            map.setValue(org.lwjgl.input.Mouse.isButtonDown(n) ? 1.0f : 0.0f);
        }
    }
    
    @Override
    protected synchronized boolean getNextDeviceEvent(final Event event) throws IOException {
        if (!org.lwjgl.input.Mouse.isCreated()) {
            return false;
        }
        while (true) {
            final long eventNanoseconds = org.lwjgl.input.Mouse.getEventNanoseconds();
            switch (this.event_state) {
                case 1: {
                    this.event_state = 2;
                    final int eventDX = org.lwjgl.input.Mouse.getEventDX();
                    if (eventDX != 0) {
                        event.set(this.getX(), (float)eventDX, eventNanoseconds);
                        return true;
                    }
                    continue;
                }
                case 2: {
                    this.event_state = 3;
                    final int n = -org.lwjgl.input.Mouse.getEventDY();
                    if (n != 0) {
                        event.set(this.getY(), (float)n, eventNanoseconds);
                        return true;
                    }
                    continue;
                }
                case 3: {
                    this.event_state = 4;
                    final int eventDWheel = org.lwjgl.input.Mouse.getEventDWheel();
                    if (eventDWheel != 0) {
                        event.set(this.getWheel(), (float)eventDWheel, eventNanoseconds);
                        return true;
                    }
                    continue;
                }
                case 4: {
                    this.event_state = 5;
                    final int eventButton = org.lwjgl.input.Mouse.getEventButton();
                    if (eventButton == -1) {
                        continue;
                    }
                    final Button map = this.map(eventButton);
                    if (map != null) {
                        event.set(map, org.lwjgl.input.Mouse.getEventButtonState() ? 1.0f : 0.0f, eventNanoseconds);
                        return true;
                    }
                    continue;
                }
                case 5: {
                    if (!org.lwjgl.input.Mouse.next()) {
                        return false;
                    }
                    this.event_state = 1;
                    continue;
                }
            }
        }
    }
    
    static final class Button extends AbstractComponent
    {
        private float value;
        
        Button(final Component.Identifier.Button button) {
            super(button.getName(), button);
        }
        
        void setValue(final float value) {
            this.value = value;
        }
        
        @Override
        protected float poll() throws IOException {
            return this.value;
        }
        
        public boolean isRelative() {
            return false;
        }
        
        @Override
        public boolean isAnalog() {
            return false;
        }
    }
    
    static final class Axis extends AbstractComponent
    {
        Axis(final Component.Identifier.Axis axis) {
            super(axis.getName(), axis);
        }
        
        public boolean isRelative() {
            return true;
        }
        
        @Override
        protected float poll() throws IOException {
            return 0.0f;
        }
        
        @Override
        public boolean isAnalog() {
            return true;
        }
    }
}
