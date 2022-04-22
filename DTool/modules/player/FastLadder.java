package DTool.modules.player;

import DTool.modules.*;
import DTool.events.*;
import net.minecraft.client.*;

public class FastLadder extends Module
{
    public FastLadder() {
        super("FastLadder", 0, Category.Player);
    }
    
    @Override
    public void onEvent(final Event event) {
        final Minecraft mc = FastLadder.mc;
        if (Minecraft.thePlayer.isOnLadder()) {
            final Minecraft mc2 = FastLadder.mc;
            if (Minecraft.thePlayer.isCollidedHorizontally) {
                final Minecraft mc3 = FastLadder.mc;
                if (Minecraft.thePlayer.movementInput.moveForward == 0.0f) {
                    final Minecraft mc4 = FastLadder.mc;
                    if (Minecraft.thePlayer.movementInput.moveStrafe == 0.0f) {
                        return;
                    }
                }
                final Minecraft mc5 = FastLadder.mc;
                Minecraft.thePlayer.motionY = 0.2872;
            }
        }
    }
}
