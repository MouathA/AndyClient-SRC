package DTool.modules.combat;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import net.minecraft.item.*;

public class InfiniteAura extends Module
{
    private int ticks;
    
    public InfiniteAura() {
        super("InfiniteAura", 0, Category.Combat);
        this.ticks = 0;
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
            ++this.ticks;
            if (this.ticks >= 20 - this.speed()) {
                this.ticks = 0;
                final Minecraft mc = InfiniteAura.mc;
                for (final EntityLivingBase next : Minecraft.theWorld.loadedEntityList) {
                    final EntityLivingBase entityLivingBase;
                    if (next instanceof EntityLivingBase && !((entityLivingBase = next) instanceof EntityPlayerSP)) {
                        final Minecraft mc2 = InfiniteAura.mc;
                        if (Minecraft.thePlayer.getDistanceToEntity(entityLivingBase) > 200.0f) {
                            continue;
                        }
                        if (entityLivingBase.isInvisible()) {
                            break;
                        }
                        if (!entityLivingBase.isEntityAlive()) {
                            continue;
                        }
                        final Minecraft mc3 = InfiniteAura.mc;
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, true));
                        final Minecraft mc4 = InfiniteAura.mc;
                        if (Minecraft.thePlayer.getHeldItem() != null) {
                            final Minecraft mc5 = InfiniteAura.mc;
                            final EntityPlayerSP thePlayer = Minecraft.thePlayer;
                            final Minecraft mc6 = InfiniteAura.mc;
                            final ItemStack heldItem = Minecraft.thePlayer.getHeldItem();
                            final Minecraft mc7 = InfiniteAura.mc;
                            thePlayer.setItemInUse(heldItem, Minecraft.thePlayer.getHeldItem().getMaxItemUseDuration());
                        }
                        final Minecraft mc8 = InfiniteAura.mc;
                        if (Minecraft.thePlayer.isBlocking()) {
                            final Minecraft mc9 = InfiniteAura.mc;
                            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
                        }
                        final Minecraft mc10 = InfiniteAura.mc;
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entityLivingBase, C02PacketUseEntity.Action.ATTACK));
                        final Minecraft mc11 = InfiniteAura.mc;
                        Minecraft.thePlayer.swingItem();
                        break;
                    }
                }
            }
        }
    }
    
    private int speed() {
        return 18;
    }
}
