package DTool.modules.movement;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import java.util.*;

public class InventoryMove extends Module
{
    public InventoryMove() {
        super("InventoryMove", 7, Category.Movement);
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
        if (event instanceof EventUpdate && event.isPre()) {
            final KeyBinding[] array = { InventoryMove.mc.gameSettings.keyBindRight, InventoryMove.mc.gameSettings.keyBindLeft, InventoryMove.mc.gameSettings.keyBindForward, InventoryMove.mc.gameSettings.keyBindBack, InventoryMove.mc.gameSettings.keyBindJump, InventoryMove.mc.gameSettings.keyBindSprint };
            if (InventoryMove.mc.currentScreen instanceof Gui && !(InventoryMove.mc.currentScreen instanceof GuiChat)) {
                KeyBinding[] array2;
                while (0 < (array2 = array).length) {
                    final KeyBinding keyBinding = array2[0];
                    keyBinding.pressed = Keyboard.isKeyDown(keyBinding.getKeyCode());
                    int n = 0;
                    ++n;
                }
            }
            else if (Objects.isNull(InventoryMove.mc.currentScreen)) {
                KeyBinding[] array3;
                while (0 < (array3 = array).length) {
                    final KeyBinding keyBinding2 = array3[0];
                    if (!Keyboard.isKeyDown(keyBinding2.getKeyCode())) {
                        KeyBinding.setKeyBindState(keyBinding2.getKeyCode(), false);
                    }
                    int n = 0;
                    ++n;
                }
            }
        }
    }
}
