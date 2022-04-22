package wdl.gui;

import net.minecraft.client.resources.*;
import wdl.chan.*;
import java.io.*;
import net.minecraft.client.gui.*;
import wdl.*;
import java.util.*;

public class GuiWDLEntityRangePresets extends GuiScreen implements GuiYesNoCallback
{
    private final GuiScreen parent;
    private GuiButton vanillaButton;
    private GuiButton spigotButton;
    private GuiButton serverButton;
    private GuiButton cancelButton;
    
    public GuiWDLEntityRangePresets(final GuiScreen parent) {
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        int n = GuiWDLEntityRangePresets.height / 4;
        this.vanillaButton = new GuiButton(0, GuiWDLEntityRangePresets.width / 2 - 100, n, I18n.format("wdl.gui.rangePresets.vanilla", new Object[0]));
        n += 22;
        this.spigotButton = new GuiButton(1, GuiWDLEntityRangePresets.width / 2 - 100, n, I18n.format("wdl.gui.rangePresets.spigot", new Object[0]));
        n += 22;
        this.serverButton = new GuiButton(2, GuiWDLEntityRangePresets.width / 2 - 100, n, I18n.format("wdl.gui.rangePresets.server", new Object[0]));
        this.serverButton.enabled = WDLPluginChannels.hasServerEntityRange();
        this.buttonList.add(this.vanillaButton);
        this.buttonList.add(this.spigotButton);
        this.buttonList.add(this.serverButton);
        n += 28;
        this.cancelButton = new GuiButton(100, GuiWDLEntityRangePresets.width / 2 - 100, GuiWDLEntityRangePresets.height - 29, I18n.format("gui.cancel", new Object[0]));
        this.buttonList.add(this.cancelButton);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (!guiButton.enabled) {
            return;
        }
        if (guiButton.id < 3) {
            final String format = I18n.format("wdl.gui.rangePresets.upperWarning", new Object[0]);
            String s;
            if (guiButton.id == 0) {
                s = I18n.format("wdl.gui.rangePresets.vanilla.warning", new Object[0]);
            }
            else if (guiButton.id == 1) {
                s = I18n.format("wdl.gui.rangePresets.spigot.warning", new Object[0]);
            }
            else {
                if (guiButton.id != 2) {
                    throw new Error("Button.id should never be negative.");
                }
                s = I18n.format("wdl.gui.rangePresets.server.warning", new Object[0]);
            }
            GuiWDLEntityRangePresets.mc.displayGuiScreen(new GuiYesNo(this, format, s, guiButton.id));
        }
        if (guiButton.id == 100) {
            GuiWDLEntityRangePresets.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        Utils.drawListBackground(23, 32, 0, 0, GuiWDLEntityRangePresets.height, GuiWDLEntityRangePresets.width);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.rangePresets.title", new Object[0]), GuiWDLEntityRangePresets.width / 2, 8, 16777215);
        String s = null;
        if (this.vanillaButton.isMouseOver()) {
            s = I18n.format("wdl.gui.rangePresets.vanilla.description", new Object[0]);
        }
        else if (this.spigotButton.isMouseOver()) {
            s = I18n.format("wdl.gui.rangePresets.spigot.description", new Object[0]);
        }
        else if (this.serverButton.isMouseOver()) {
            final String string = String.valueOf(I18n.format("wdl.gui.rangePresets.server.description", new Object[0])) + "\n\n";
            if (this.serverButton.enabled) {
                s = String.valueOf(string) + I18n.format("wdl.gui.rangePresets.server.installed", new Object[0]);
            }
            else {
                s = String.valueOf(string) + I18n.format("wdl.gui.rangePresets.server.notInstalled", new Object[0]);
            }
        }
        else if (this.cancelButton.isMouseOver()) {
            s = I18n.format("wdl.gui.rangePresets.cancel.description", new Object[0]);
        }
        if (s != null) {
            Utils.drawGuiInfoBox(s, GuiWDLEntityRangePresets.width, GuiWDLEntityRangePresets.height, 48);
        }
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (b) {
            final Set entityTypes = EntityUtils.getEntityTypes();
            if (n == 0) {
                for (final String s : entityTypes) {
                    WDL.worldProps.setProperty("Entity." + s + ".TrackDistance", Integer.toString(EntityUtils.getDefaultEntityRange(s)));
                }
            }
            else if (n == 1) {
                for (final String s2 : entityTypes) {
                    final Class clazz = EntityUtils.stringToClassMapping.get(s2);
                    if (clazz == null) {
                        continue;
                    }
                    WDL.worldProps.setProperty("Entity." + s2 + ".TrackDistance", Integer.toString(EntityUtils.getDefaultSpigotEntityRange(clazz)));
                }
            }
            else if (n == 2) {
                for (final String s3 : entityTypes) {
                    WDL.worldProps.setProperty("Entity." + s3 + ".TrackDistance", Integer.toString(WDLPluginChannels.getEntityRange(s3)));
                }
            }
        }
        GuiWDLEntityRangePresets.mc.displayGuiScreen(this.parent);
    }
    
    @Override
    public void onGuiClosed() {
    }
}
