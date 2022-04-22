package org.lwjgl.input;

import net.java.games.input.*;
import java.util.*;
import org.lwjgl.*;

public class Controllers
{
    private static ArrayList controllers;
    private static int controllerCount;
    private static ArrayList events;
    private static ControllerEvent event;
    private static boolean created;
    
    public static void create() throws LWJGLException {
        if (Controllers.created) {
            return;
        }
        final Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        final ArrayList<Controller> list = new ArrayList<Controller>();
        final Controller[] array = controllers;
        while (0 < array.length) {
            final Controller controller = array[0];
            if (!controller.getType().equals(Controller.Type.KEYBOARD) && !controller.getType().equals(Controller.Type.MOUSE)) {
                list.add(controller);
            }
            int n = 0;
            ++n;
        }
        final Iterator<Controller> iterator = list.iterator();
        while (iterator.hasNext()) {
            createController(iterator.next());
        }
        Controllers.created = true;
    }
    
    private static void createController(final Controller controller) {
        final Controller[] controllers = controller.getControllers();
        if (controllers.length == 0) {
            Controllers.controllers.add(new JInputController(Controllers.controllerCount, controller));
            ++Controllers.controllerCount;
        }
        else {
            final Controller[] array = controllers;
            while (0 < array.length) {
                createController(array[0]);
                int n = 0;
                ++n;
            }
        }
    }
    
    public static org.lwjgl.input.Controller getController(final int n) {
        return Controllers.controllers.get(n);
    }
    
    public static int getControllerCount() {
        return Controllers.controllers.size();
    }
    
    public static void poll() {
        while (0 < Controllers.controllers.size()) {
            getController(0).poll();
            int n = 0;
            ++n;
        }
    }
    
    public static void clearEvents() {
        Controllers.events.clear();
    }
    
    public static boolean next() {
        if (Controllers.events.size() == 0) {
            Controllers.event = null;
            return false;
        }
        Controllers.event = Controllers.events.remove(0);
        return Controllers.event != null;
    }
    
    public static boolean isCreated() {
        return Controllers.created;
    }
    
    public static void destroy() {
    }
    
    public static org.lwjgl.input.Controller getEventSource() {
        return Controllers.event.getSource();
    }
    
    public static int getEventControlIndex() {
        return Controllers.event.getControlIndex();
    }
    
    public static boolean isEventButton() {
        return Controllers.event.isButton();
    }
    
    public static boolean isEventAxis() {
        return Controllers.event.isAxis();
    }
    
    public static boolean isEventXAxis() {
        return Controllers.event.isXAxis();
    }
    
    public static boolean isEventYAxis() {
        return Controllers.event.isYAxis();
    }
    
    public static boolean isEventPovX() {
        return Controllers.event.isPovX();
    }
    
    public static boolean isEventPovY() {
        return Controllers.event.isPovY();
    }
    
    public static long getEventNanoseconds() {
        return Controllers.event.getTimeStamp();
    }
    
    public static boolean getEventButtonState() {
        return Controllers.event.getButtonState();
    }
    
    public static float getEventXAxisValue() {
        return Controllers.event.getXAxisValue();
    }
    
    public static float getEventYAxisValue() {
        return Controllers.event.getYAxisValue();
    }
    
    static void addEvent(final ControllerEvent controllerEvent) {
        if (controllerEvent != null) {
            Controllers.events.add(controllerEvent);
        }
    }
    
    static {
        Controllers.controllers = new ArrayList();
        Controllers.events = new ArrayList();
    }
}
