package net.java.games.input;

import java.io.*;

class OSXComponent extends AbstractComponent
{
    private final OSXHIDElement element;
    
    public OSXComponent(final Component.Identifier identifier, final OSXHIDElement element) {
        super(identifier.getName(), identifier);
        this.element = element;
    }
    
    public final boolean isRelative() {
        return this.element.isRelative();
    }
    
    public boolean isAnalog() {
        return this.element.isAnalog();
    }
    
    public final OSXHIDElement getElement() {
        return this.element;
    }
    
    protected float poll() throws IOException {
        return OSXControllers.poll(this.element);
    }
}
