package net.java.games.input;

import java.io.*;

final class LinuxJoystickButton extends AbstractComponent
{
    private float value;
    
    public LinuxJoystickButton(final Component.Identifier identifier) {
        super(identifier.getName(), identifier);
    }
    
    public final boolean isRelative() {
        return false;
    }
    
    final void setValue(final float value) {
        this.value = value;
    }
    
    protected final float poll() throws IOException {
        return this.value;
    }
}
