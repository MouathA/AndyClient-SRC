package net.java.games.input;

import java.io.*;

class LinuxJoystickAxis extends AbstractComponent
{
    private float value;
    private boolean analog;
    
    public LinuxJoystickAxis(final Component.Identifier.Axis axis) {
        this(axis, true);
    }
    
    public LinuxJoystickAxis(final Component.Identifier.Axis axis, final boolean analog) {
        super(axis.getName(), axis);
        this.analog = analog;
    }
    
    public final boolean isRelative() {
        return false;
    }
    
    public final boolean isAnalog() {
        return this.analog;
    }
    
    final void setValue(final float value) {
        this.value = value;
        this.resetHasPolled();
    }
    
    protected final float poll() throws IOException {
        return this.value;
    }
}
