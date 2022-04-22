package DTool.modules.movement;

import DTool.modules.*;
import DTool.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class Teleport extends Module
{
    public Teleport() {
        super("Teleport", 34, Category.Movement);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        super.onEnable();
        final MovingObjectPosition blockPlayerLooking = Utils.blockPlayerLookingAt(500.0);
        if (blockPlayerLooking == null) {
            return this.toggled;
        }
        final double n = blockPlayerLooking.func_178782_a().getX() + 0.5;
        final double n2 = blockPlayerLooking.func_178782_a().getY() + 1;
        final double n3 = blockPlayerLooking.func_178782_a().getZ() + 0.5;
        final Minecraft mc = Teleport.mc;
        for (double distance = Minecraft.thePlayer.getDistance(n, n2, n3), n4 = 0.0; n4 < distance; n4 += 2.0) {
            final Minecraft mc2 = Teleport.mc;
            final double posX = Minecraft.thePlayer.posX;
            final double n5 = n;
            final Minecraft mc3 = Teleport.mc;
            final double n6 = n5 - Minecraft.thePlayer.getHorizontalFacing().getFrontOffsetX();
            final Minecraft mc4 = Teleport.mc;
            final double n7 = posX + (n6 - Minecraft.thePlayer.posX) * n4 / distance;
            final Minecraft mc5 = Teleport.mc;
            final double posY = Minecraft.thePlayer.posY;
            final double n8 = n2;
            final Minecraft mc6 = Teleport.mc;
            final double n9 = posY + (n8 - Minecraft.thePlayer.posY) * n4 / distance;
            final Minecraft mc7 = Teleport.mc;
            final double posZ = Minecraft.thePlayer.posZ;
            final double n10 = n3;
            final Minecraft mc8 = Teleport.mc;
            final double n11 = n10 - Minecraft.thePlayer.getHorizontalFacing().getFrontOffsetZ();
            final Minecraft mc9 = Teleport.mc;
            Utils.teleportNormal(n7, n9, posZ + (n11 - Minecraft.thePlayer.posZ) * n4 / distance);
        }
        Utils.teleportNormal(n, n2, n3);
        Teleport.mc.renderGlobal.loadRenderers();
        this.onDisable();
        return super.onEnable();
    }
}
