package net.java.games.input;

import java.util.*;
import java.io.*;

public abstract class AbstractController implements Controller
{
    private static final Event event;
    private final String name;
    private final Component[] components;
    private final Controller[] children;
    private final Rumbler[] rumblers;
    private final Map id_to_components;
    private EventQueue event_queue;
    
    protected AbstractController(final String name, final Component[] components, final Controller[] children, final Rumbler[] rumblers) {
        this.id_to_components = new HashMap();
        this.event_queue = new EventQueue(32);
        this.name = name;
        this.components = components;
        this.children = children;
        this.rumblers = rumblers;
        for (int i = components.length - 1; i >= 0; --i) {
            this.id_to_components.put(components[i].getIdentifier(), components[i]);
        }
    }
    
    public final Controller[] getControllers() {
        return this.children;
    }
    
    public final Component[] getComponents() {
        return this.components;
    }
    
    public final Component getComponent(final Component.Identifier identifier) {
        return this.id_to_components.get(identifier);
    }
    
    public final Rumbler[] getRumblers() {
        return this.rumblers;
    }
    
    public PortType getPortType() {
        return PortType.UNKNOWN;
    }
    
    public int getPortNumber() {
        return 0;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public String toString() {
        return this.name;
    }
    
    public Type getType() {
        return Type.UNKNOWN;
    }
    
    public final void setEventQueueSize(final int deviceEventQueueSize) {
        this.setDeviceEventQueueSize(deviceEventQueueSize);
        this.event_queue = new EventQueue(deviceEventQueueSize);
    }
    
    protected void setDeviceEventQueueSize(final int n) throws IOException {
    }
    
    public final EventQueue getEventQueue() {
        return this.event_queue;
    }
    
    protected abstract boolean getNextDeviceEvent(final Event p0) throws IOException;
    
    protected void pollDevice() throws IOException {
    }
    
    public synchronized boolean poll() {
        final Component[] components = this.getComponents();
        this.pollDevice();
        while (0 < components.length) {
            final AbstractComponent abstractComponent = (AbstractComponent)components[0];
            if (abstractComponent.isRelative()) {
                abstractComponent.setPollData(0.0f);
            }
            else {
                abstractComponent.resetHasPolled();
            }
            int n = 0;
            ++n;
        }
        while (this.getNextDeviceEvent(AbstractController.event)) {
            final AbstractComponent abstractComponent2 = (AbstractComponent)AbstractController.event.getComponent();
            final float value = AbstractController.event.getValue();
            if (abstractComponent2.isRelative()) {
                if (value == 0.0f) {
                    continue;
                }
                abstractComponent2.setPollData(abstractComponent2.getPollData() + value);
            }
            else {
                if (value == abstractComponent2.getEventValue()) {
                    continue;
                }
                abstractComponent2.setEventValue(value);
            }
            if (!this.event_queue.isFull()) {
                this.event_queue.add(AbstractController.event);
            }
        }
        return true;
    }
    
    static {
        event = new Event();
    }
}
