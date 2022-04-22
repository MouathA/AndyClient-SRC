package net.java.games.input;

import java.util.logging.*;
import java.util.*;

public abstract class ControllerEnvironment
{
    private static ControllerEnvironment defaultEnvironment;
    protected final ArrayList controllerListeners;
    static Class class$net$java$games$input$ControllerEnvironment;
    static final boolean $assertionsDisabled;
    
    static void logln(final String s) {
        log(s + "\n");
    }
    
    static void log(final String s) {
        Logger.getLogger(((ControllerEnvironment.class$net$java$games$input$ControllerEnvironment == null) ? (ControllerEnvironment.class$net$java$games$input$ControllerEnvironment = class$("net.java.games.input.ControllerEnvironment")) : ControllerEnvironment.class$net$java$games$input$ControllerEnvironment).getName()).info(s);
    }
    
    protected ControllerEnvironment() {
        this.controllerListeners = new ArrayList();
    }
    
    public abstract Controller[] getControllers();
    
    public void addControllerListener(final ControllerListener controllerListener) {
        assert controllerListener != null;
        this.controllerListeners.add(controllerListener);
    }
    
    public abstract boolean isSupported();
    
    public void removeControllerListener(final ControllerListener controllerListener) {
        assert controllerListener != null;
        this.controllerListeners.remove(controllerListener);
    }
    
    protected void fireControllerAdded(final Controller controller) {
        final ControllerEvent controllerEvent = new ControllerEvent(controller);
        final Iterator<ControllerListener> iterator = (Iterator<ControllerListener>)this.controllerListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().controllerAdded(controllerEvent);
        }
    }
    
    protected void fireControllerRemoved(final Controller controller) {
        final ControllerEvent controllerEvent = new ControllerEvent(controller);
        final Iterator<ControllerListener> iterator = (Iterator<ControllerListener>)this.controllerListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().controllerRemoved(controllerEvent);
        }
    }
    
    public static ControllerEnvironment getDefaultEnvironment() {
        return ControllerEnvironment.defaultEnvironment;
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        $assertionsDisabled = !((ControllerEnvironment.class$net$java$games$input$ControllerEnvironment == null) ? (ControllerEnvironment.class$net$java$games$input$ControllerEnvironment = class$("net.java.games.input.ControllerEnvironment")) : ControllerEnvironment.class$net$java$games$input$ControllerEnvironment).desiredAssertionStatus();
        ControllerEnvironment.defaultEnvironment = new DefaultControllerEnvironment();
    }
}
