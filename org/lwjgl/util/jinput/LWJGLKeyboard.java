package org.lwjgl.util.jinput;

import java.util.*;
import java.lang.reflect.*;
import java.io.*;
import net.java.games.input.*;

final class LWJGLKeyboard extends Keyboard
{
    LWJGLKeyboard() {
        super("LWJGLKeyboard", createComponents(), new Controller[0], new Rumbler[0]);
    }
    
    private static Component[] createComponents() {
        final ArrayList<Key> list = new ArrayList<Key>();
        final Field[] fields = org.lwjgl.input.Keyboard.class.getFields();
        while (0 < fields.length) {
            final Field field = fields[0];
            if (Modifier.isStatic(field.getModifiers()) && field.getType() == Integer.TYPE && field.getName().startsWith("KEY_")) {
                final int int1 = field.getInt(null);
                final Component.Identifier.Key map = KeyMap.map(int1);
                if (map != Component.Identifier.Key.UNKNOWN) {
                    list.add(new Key(map, int1));
                }
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new Component[list.size()]);
    }
    
    public synchronized void pollDevice() throws IOException {
        if (!org.lwjgl.input.Keyboard.isCreated()) {
            return;
        }
        final Component[] components = this.getComponents();
        while (0 < components.length) {
            ((Key)components[0]).update();
            int n = 0;
            ++n;
        }
    }
    
    @Override
    protected synchronized boolean getNextDeviceEvent(final Event event) throws IOException {
        if (!org.lwjgl.input.Keyboard.isCreated()) {
            return false;
        }
        if (!org.lwjgl.input.Keyboard.next()) {
            return false;
        }
        final int eventKey = org.lwjgl.input.Keyboard.getEventKey();
        if (eventKey == 0) {
            return false;
        }
        final Component.Identifier.Key map = KeyMap.map(eventKey);
        if (map == null) {
            return false;
        }
        final Component component = this.getComponent(map);
        if (component == null) {
            return false;
        }
        event.set(component, org.lwjgl.input.Keyboard.getEventKeyState() ? 1.0f : 0.0f, org.lwjgl.input.Keyboard.getEventNanoseconds());
        return true;
    }
    
    private static final class Key extends AbstractComponent
    {
        private final int lwjgl_key;
        private float value;
        
        Key(final Component.Identifier.Key key, final int lwjgl_key) {
            super(key.getName(), key);
            this.lwjgl_key = lwjgl_key;
        }
        
        public void update() {
            this.value = (org.lwjgl.input.Keyboard.isKeyDown(this.lwjgl_key) ? 1.0f : 0.0f);
        }
        
        @Override
        protected float poll() {
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
}
