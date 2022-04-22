package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import java.io.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.client.network.*;

public class GuiSleepMP extends GuiChat
{
    private static final String __OBFID;
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, GuiSleepMP.width / 2 - 100, GuiSleepMP.height - 40, I18n.format("multiplayer.stopSleeping", new Object[0])));
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            this.wakeFromSleep();
        }
        else if (n != 28 && n != 156) {
            super.keyTyped(c, n);
        }
        else {
            final String trim = this.inputField.getText().trim();
            if (!trim.isEmpty()) {
                final Minecraft mc = GuiSleepMP.mc;
                Minecraft.thePlayer.sendChatMessage(trim);
            }
            this.inputField.setText("");
            final Minecraft mc2 = GuiSleepMP.mc;
            Minecraft.ingameGUI.getChatGUI().resetScroll();
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            this.wakeFromSleep();
        }
        else {
            super.actionPerformed(guiButton);
        }
    }
    
    private void wakeFromSleep() {
        final Minecraft mc = GuiSleepMP.mc;
        final NetHandlerPlayClient sendQueue = Minecraft.thePlayer.sendQueue;
        final Minecraft mc2 = GuiSleepMP.mc;
        sendQueue.addToSendQueue(new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
    }
    
    static {
        __OBFID = "CL_00000697";
    }
}
