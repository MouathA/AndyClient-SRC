package Mood.Gui;

import Mood.*;
import net.minecraft.util.*;
import Mood.UIButtons.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class GuiCrashScreen extends GuiScreen
{
    private Throwable ex;
    public String[] messages;
    
    public GuiCrashScreen(final Throwable ex) {
        this.ex = ex;
    }
    
    @Override
    public void initGui() {
        Client.getInstance();
        Client.getDiscordRP().update("Game Crashed.", "V\u00e1laszra v\u00e1rva..");
        String[] messages;
        if (this.ex instanceof ReportedException) {
            messages = ((ReportedException)this.ex).getCrashReport().getCompleteReport().split("\n");
        }
        else {
            messages = this.stacktraceToStringArray(this.ex.getStackTrace());
        }
        this.messages = messages;
        this.buttonList.add(new UIButtons(0, GuiCrashScreen.width / 2 - 75 - 104, GuiCrashScreen.height - 30, 100, 20, "§3§lF\u0151men\u00fc"));
        this.buttonList.add(new UIButtons(1, GuiCrashScreen.width / 2 - 75, GuiCrashScreen.height - 30, 150, 20, "§3§lCrash Report M\u00e1sol\u00e1sa"));
        this.buttonList.add(new UIButtons(2, GuiCrashScreen.width / 2 + 79, GuiCrashScreen.height - 30, 100, 20, "§3§lKijelentkez\u00e9s"));
        super.initGui();
    }
    
    private String[] stacktraceToStringArray(final StackTraceElement[] array) {
        final String[] array2 = new String[array.length];
        while (0 < array.length) {
            array2[0] = array[0].toString();
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            final Minecraft mc = GuiCrashScreen.mc;
            Minecraft.theWorld.sendQuittingDisconnectingPacket();
            GuiCrashScreen.mc.loadWorld(null);
            GuiCrashScreen.mc.displayGuiScreen(new GuiMainMenu());
        }
        if (guiButton.id == 1) {
            if (this.ex instanceof ReportedException) {
                GuiScreen.setClipboardString(((ReportedException)this.ex).getCrashReport().getCompleteReport());
            }
            else {
                String string = "";
                final String[] stacktraceToStringArray = this.stacktraceToStringArray(this.ex.getStackTrace());
                while (0 < stacktraceToStringArray.length) {
                    string = String.valueOf(String.valueOf(String.valueOf(string))) + stacktraceToStringArray[0] + System.lineSeparator();
                    int n = 0;
                    ++n;
                }
                GuiScreen.setClipboardString(string);
            }
        }
        if (guiButton.id == 2) {
            System.exit(0);
        }
        super.actionPerformed(guiButton);
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        while (0 < this.messages.length) {
            this.drawString(this.fontRendererObj, this.messages[0], 3, 3, -1);
            final int n4;
            n4 += 8;
            int n5 = 0;
            ++n5;
        }
        super.drawScreen(n, n2, n3);
    }
}
