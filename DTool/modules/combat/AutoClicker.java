package DTool.modules.combat;

import DTool.modules.*;
import java.util.*;
import Mood.Host.Helper.*;
import DTool.events.*;
import DTool.events.listeners.*;

public class AutoClicker extends Module
{
    public Random random;
    public TimeHelper delay;
    
    public AutoClicker() {
        super("AutoClicker", 0, Category.Combat);
        this.random = new Random();
        this.delay = new TimeHelper();
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre() && this.delay.isDelayComplete((float)(51 + this.random.nextInt(50)))) {
            this.delay.setLastMS(0L);
            AutoClicker.mc.clickMouse();
        }
    }
}
