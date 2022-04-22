package net.java.games.input;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

final class RawKeyboard extends Keyboard
{
    private final RawKeyboardEvent raw_event;
    private final RawDevice device;
    static Class class$net$java$games$input$RawIdentifierMap;
    
    protected RawKeyboard(final String s, final RawDevice device, final Controller[] array, final Rumbler[] array2) throws IOException {
        super(s, createKeyboardComponents(device), array, array2);
        this.raw_event = new RawKeyboardEvent();
        this.device = device;
    }
    
    private static final Component[] createKeyboardComponents(final RawDevice rawDevice) {
        final ArrayList list = new ArrayList<Key>();
        final Field[] fields = ((RawKeyboard.class$net$java$games$input$RawIdentifierMap == null) ? (RawKeyboard.class$net$java$games$input$RawIdentifierMap = class$("net.java.games.input.RawIdentifierMap")) : RawKeyboard.class$net$java$games$input$RawIdentifierMap).getFields();
        while (0 < fields.length) {
            final Field field = fields[0];
            if (Modifier.isStatic(field.getModifiers()) && field.getType() == Integer.TYPE) {
                final int int1 = field.getInt(null);
                final Component.Identifier.Key mapVKey = RawIdentifierMap.mapVKey(int1);
                if (mapVKey != Component.Identifier.Key.UNKNOWN) {
                    list.add(new Key(rawDevice, int1, mapVKey));
                }
            }
            int n = 0;
            ++n;
        }
        return (Component[])list.toArray(new Component[0]);
    }
    
    protected final synchronized boolean getNextDeviceEvent(final Event event) throws IOException {
        while (this.device.getNextKeyboardEvent(this.raw_event)) {
            final Component component = this.getComponent(RawIdentifierMap.mapVKey(this.raw_event.getVKey()));
            if (component == null) {
                continue;
            }
            final int message = this.raw_event.getMessage();
            if (message == 256 || message == 260) {
                event.set(component, 1.0f, this.raw_event.getNanos());
                return true;
            }
            if (message == 257 || message == 261) {
                event.set(component, 0.0f, this.raw_event.getNanos());
                return true;
            }
        }
        return false;
    }
    
    public final void pollDevice() throws IOException {
        this.device.pollKeyboard();
    }
    
    protected final void setDeviceEventQueueSize(final int bufferSize) throws IOException {
        this.device.setBufferSize(bufferSize);
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static final class Key extends AbstractComponent
    {
        private final RawDevice device;
        private final int vkey_code;
        
        public Key(final RawDevice device, final int vkey_code, final Component.Identifier.Key key) {
            super(key.getName(), key);
            this.device = device;
            this.vkey_code = vkey_code;
        }
        
        protected final float poll() throws IOException {
            return this.device.isKeyDown(this.vkey_code) ? 1.0f : 0.0f;
        }
        
        public final boolean isAnalog() {
            return false;
        }
        
        public final boolean isRelative() {
            return false;
        }
    }
}
