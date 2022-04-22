package DTool.modules.combat;

import DTool.modules.*;
import DTool.util.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.client.network.*;

public class AutoSoup extends Module
{
    private final Timer time;
    
    public AutoSoup() {
        super("AutoSoup", 0, Category.Combat);
        this.time = new Timer();
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = AutoSoup.mc;
            final float health = Minecraft.thePlayer.getHealth();
            final Minecraft mc2 = AutoSoup.mc;
            if (health < Minecraft.thePlayer.getMaxHealth() - 10.0f && this.time.delay(50.0f)) {
                if (this.hotbarHasSoups()) {
                    this.useSoup();
                }
                else {
                    this.getSoupFromInventory();
                    this.time.reset();
                }
            }
        }
    }
    
    private boolean hotbarHasSoups() {
        while (36 < 45) {
            final Minecraft mc = AutoSoup.mc;
            final ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(36).getStack();
            if (stack != null && stack.getItem() instanceof ItemSoup) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    private int countSoups() {
        while (9 < 45) {
            final Minecraft mc = AutoSoup.mc;
            final ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(9).getStack();
            if (stack != null) {
                if (stack.getItem() instanceof ItemSoup) {
                    final int n = 0 + stack.stackSize;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    private void getSoupFromInventory() {
        int n = 0;
        while (0 >= 9) {
            final Minecraft mc = AutoSoup.mc;
            final ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(0).getStack();
            if (stack != null && stack.getItem() instanceof ItemSoup) {
                break;
            }
            --n;
        }
        if (true) {
            while (0 < 45) {
                final Minecraft mc2 = AutoSoup.mc;
                final ItemStack stack2 = Minecraft.thePlayer.inventoryContainer.getSlot(0).getStack();
                if (stack2 == null) {
                    ++n;
                }
                else {
                    if (stack2.getItem() != Items.bowl || 0 < 36) {
                        break;
                    }
                    if (0 > 44) {
                        break;
                    }
                    final Minecraft mc3 = AutoSoup.mc;
                    final PlayerControllerMP playerController = Minecraft.playerController;
                    final int n2 = 0;
                    final int n3 = 0;
                    final int n4 = 0;
                    final int n5 = 0;
                    final Minecraft mc4 = AutoSoup.mc;
                    playerController.windowClick(n2, n3, n4, n5, Minecraft.thePlayer);
                    final Minecraft mc5 = AutoSoup.mc;
                    final PlayerControllerMP playerController2 = Minecraft.playerController;
                    final int n6 = 0;
                    final int n7 = -999;
                    final int n8 = 0;
                    final int n9 = 0;
                    final Minecraft mc6 = AutoSoup.mc;
                    playerController2.windowClick(n6, n7, n8, n9, Minecraft.thePlayer);
                    break;
                }
            }
            final Minecraft mc7 = AutoSoup.mc;
            final PlayerControllerMP playerController3 = Minecraft.playerController;
            final int n10 = 0;
            final int n11 = -1;
            final int n12 = 0;
            final int n13 = 1;
            final Minecraft mc8 = AutoSoup.mc;
            playerController3.windowClick(n10, n11, n12, n13, Minecraft.thePlayer);
        }
    }
    
    private void useSoup() {
        int n = 0;
        while (0 < 45) {
            final Minecraft mc = AutoSoup.mc;
            final ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(0).getStack();
            if (stack != null && stack.getItem() instanceof ItemSoup) {
                break;
            }
            ++n;
        }
        if (true) {
            while (0 < 45) {
                final Minecraft mc2 = AutoSoup.mc;
                final ItemStack stack2 = Minecraft.thePlayer.inventoryContainer.getSlot(0).getStack();
                if (stack2 != null && stack2.getItem() == Items.bowl && 0 >= 36) {
                    if (0 <= 44) {
                        final Minecraft mc3 = AutoSoup.mc;
                        final PlayerControllerMP playerController = Minecraft.playerController;
                        final int n2 = 0;
                        final int n3 = 0;
                        final int n4 = 0;
                        final int n5 = 0;
                        final Minecraft mc4 = AutoSoup.mc;
                        playerController.windowClick(n2, n3, n4, n5, Minecraft.thePlayer);
                        final Minecraft mc5 = AutoSoup.mc;
                        final PlayerControllerMP playerController2 = Minecraft.playerController;
                        final int n6 = 0;
                        final int n7 = -999;
                        final int n8 = 0;
                        final int n9 = 0;
                        final Minecraft mc6 = AutoSoup.mc;
                        playerController2.windowClick(n6, n7, n8, n9, Minecraft.thePlayer);
                    }
                }
                ++n;
            }
            final Minecraft mc7 = AutoSoup.mc;
            final int currentItem = Minecraft.thePlayer.inventory.currentItem;
            final Minecraft mc8 = AutoSoup.mc;
            Minecraft.thePlayer.inventory.currentItem = -37;
            final Minecraft mc9 = AutoSoup.mc;
            Minecraft.playerController.updateController();
            final Minecraft mc10 = AutoSoup.mc;
            final NetHandlerPlayClient sendQueue = Minecraft.thePlayer.sendQueue;
            final Minecraft mc11 = AutoSoup.mc;
            sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem()));
            final Minecraft mc12 = AutoSoup.mc;
            final NetHandlerPlayClient sendQueue2 = Minecraft.thePlayer.sendQueue;
            final Minecraft mc13 = AutoSoup.mc;
            sendQueue2.addToSendQueue(new C09PacketHeldItemChange(Minecraft.thePlayer.inventory.currentItem));
            final Minecraft mc14 = AutoSoup.mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            final Minecraft mc15 = AutoSoup.mc;
            Minecraft.thePlayer.stopUsingItem();
            final Minecraft mc16 = AutoSoup.mc;
            Minecraft.thePlayer.inventory.currentItem = currentItem;
        }
    }
}
