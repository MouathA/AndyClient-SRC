package net.java.games.input;

public class ControllerEvent
{
    private Controller controller;
    
    public ControllerEvent(final Controller controller) {
        this.controller = controller;
    }
    
    public Controller getController() {
        return this.controller;
    }
}
