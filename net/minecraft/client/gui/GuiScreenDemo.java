package net.minecraft.client.gui;

import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.resources.*;
import java.net.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.settings.*;

public class GuiScreenDemo extends GuiScreen
{
    private static final Logger logger;
    private static final ResourceLocation field_146348_f;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000691";
        logger = LogManager.getLogger();
        field_146348_f = new ResourceLocation("textures/gui/demo_background.png");
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, GuiScreenDemo.width / 2 - 116, GuiScreenDemo.height / 2 + 62 - 16, 114, 20, I18n.format("demo.help.buy", new Object[0])));
        this.buttonList.add(new GuiButton(2, GuiScreenDemo.width / 2 + 2, GuiScreenDemo.height / 2 + 62 - 16, 114, 20, I18n.format("demo.help.later", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 1: {
                guiButton.enabled = false;
                final Class<?> forName = Class.forName("java.awt.Desktop");
                forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), new URI("http://www.minecraft.net/store?source=demo"));
                break;
            }
            case 2: {
                GuiScreenDemo.mc.displayGuiScreen(null);
                GuiScreenDemo.mc.setIngameFocus();
                break;
            }
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
    }
    
    @Override
    public void drawDefaultBackground() {
        super.drawDefaultBackground();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiScreenDemo.mc.getTextureManager().bindTexture(GuiScreenDemo.field_146348_f);
        this.drawTexturedModalRect((GuiScreenDemo.width - 248) / 2, (GuiScreenDemo.height - 166) / 2, 0, 0, 248, 166);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        final int n4 = (GuiScreenDemo.width - 248) / 2 + 10;
        int n5 = (GuiScreenDemo.height - 166) / 2 + 8;
        this.fontRendererObj.drawString(I18n.format("demo.help.title", new Object[0]), n4, n5, 2039583);
        n5 += 12;
        final GameSettings gameSettings = GuiScreenDemo.mc.gameSettings;
        this.fontRendererObj.drawString(I18n.format("demo.help.movementShort", GameSettings.getKeyDisplayString(gameSettings.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(gameSettings.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(gameSettings.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(gameSettings.keyBindRight.getKeyCode())), n4, n5, 5197647);
        this.fontRendererObj.drawString(I18n.format("demo.help.movementMouse", new Object[0]), n4, n5 + 12, 5197647);
        this.fontRendererObj.drawString(I18n.format("demo.help.jump", GameSettings.getKeyDisplayString(gameSettings.keyBindJump.getKeyCode())), n4, n5 + 24, 5197647);
        this.fontRendererObj.drawString(I18n.format("demo.help.inventory", GameSettings.getKeyDisplayString(gameSettings.keyBindInventory.getKeyCode())), n4, n5 + 36, 5197647);
        this.fontRendererObj.drawSplitString(I18n.format("demo.help.fullWrapped", new Object[0]), n4, n5 + 68, 218, 2039583);
        super.drawScreen(n, n2, n3);
    }
}
