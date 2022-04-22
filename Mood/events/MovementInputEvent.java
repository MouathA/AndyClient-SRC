package Mood.events;

import com.darkmagician6.eventapi.events.*;
import net.minecraft.util.*;

public class MovementInputEvent implements Event
{
    private MovementInput movementInput;
    
    public MovementInputEvent(final MovementInput movementInput) {
        this.movementInput = movementInput;
    }
    
    public MovementInput getMovementInput() {
        return this.movementInput;
    }
}
