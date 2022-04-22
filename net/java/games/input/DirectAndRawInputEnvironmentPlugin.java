package net.java.games.input;

import java.util.*;

public class DirectAndRawInputEnvironmentPlugin extends ControllerEnvironment
{
    private RawInputEnvironmentPlugin rawPlugin;
    private DirectInputEnvironmentPlugin dinputPlugin;
    private Controller[] controllers;
    
    public DirectAndRawInputEnvironmentPlugin() {
        this.controllers = null;
        this.dinputPlugin = new DirectInputEnvironmentPlugin();
        this.rawPlugin = new RawInputEnvironmentPlugin();
    }
    
    public Controller[] getControllers() {
        if (this.controllers == null) {
            final ArrayList list = new ArrayList<Controller>();
            final Controller[] controllers = this.dinputPlugin.getControllers();
            final Controller[] controllers2 = this.rawPlugin.getControllers();
            int n = 0;
            while (0 < controllers2.length) {
                list.add(controllers2[0]);
                if (controllers2[0].getType() != Controller.Type.KEYBOARD) {
                    if (controllers2[0].getType() == Controller.Type.MOUSE) {}
                }
                ++n;
            }
            while (0 < controllers.length) {
                if (controllers[0].getType() != Controller.Type.KEYBOARD) {
                    if (controllers[0].getType() != Controller.Type.MOUSE) {
                        list.add(controllers[0]);
                    }
                }
                ++n;
            }
            this.controllers = (Controller[])list.toArray(new Controller[0]);
        }
        return this.controllers;
    }
    
    public boolean isSupported() {
        return this.rawPlugin.isSupported() || this.dinputPlugin.isSupported();
    }
}
