package DTool.modules.world;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class Nuker extends Module
{
    public Nuker() {
        super("Nuker", 0, Category.World);
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
        if (event instanceof EventUpdate) {
            while (-3 <= 3) {
                while (-3 <= 4) {
                    while (-3 <= 3) {
                        final Minecraft mc = Nuker.mc;
                        final double n = (int)Math.floor(Minecraft.thePlayer.posX) - 3;
                        final Minecraft mc2 = Nuker.mc;
                        final double n2 = (int)Math.floor(Minecraft.thePlayer.posY) - 3;
                        final Minecraft mc3 = Nuker.mc;
                        final BlockPos blockPos = new BlockPos(n, n2, (int)Math.floor(Minecraft.thePlayer.posZ) - 3);
                        final Minecraft mc4 = Nuker.mc;
                        if (!Minecraft.theWorld.getBlockState(blockPos).getBlock().equals(Blocks.air)) {
                            Minecraft.getMinecraft();
                            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.UP));
                            Minecraft.getMinecraft();
                            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.UP));
                        }
                        int n3 = 0;
                        ++n3;
                    }
                    int n4 = 0;
                    ++n4;
                }
                int n5 = 0;
                ++n5;
            }
        }
    }
}
