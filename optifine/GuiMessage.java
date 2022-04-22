package optifine;

import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiMessage extends GuiScreen
{
    private GuiScreen parentScreen;
    private String messageLine1;
    private String messageLine2;
    private final List listLines2;
    protected String confirmButtonText;
    private int ticksUntilEnable;
    
    public GuiMessage(final GuiScreen parentScreen, final String messageLine1, final String messageLine2) {
        this.listLines2 = Lists.newArrayList();
        this.parentScreen = parentScreen;
        this.messageLine1 = messageLine1;
        this.messageLine2 = messageLine2;
        this.confirmButtonText = I18n.format("gui.done", new Object[0]);
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiOptionButton(0, GuiMessage.width / 2 - 74, GuiMessage.height / 6 + 96, this.confirmButtonText));
        this.listLines2.clear();
        this.listLines2.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, GuiMessage.width - 50));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        Config.getMinecraft().displayGuiScreen(this.parentScreen);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.messageLine1, GuiMessage.width / 2, 70, 16777215);
        final Iterator<String> iterator = this.listLines2.iterator();
        while (iterator.hasNext()) {
            Gui.drawCenteredString(this.fontRendererObj, iterator.next(), GuiMessage.width / 2, 90, 16777215);
            final int n4 = 90 + this.fontRendererObj.FONT_HEIGHT;
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void setButtonDelay(final int ticksUntilEnable) {
        this.ticksUntilEnable = ticksUntilEnable;
        final Iterator<GuiButton> iterator = this.buttonList.iterator();
        while (iterator.hasNext()) {
            iterator.next().enabled = false;
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final int ticksUntilEnable = this.ticksUntilEnable - 1;
        this.ticksUntilEnable = ticksUntilEnable;
        if (ticksUntilEnable == 0) {
            final Iterator<GuiButton> iterator = (Iterator<GuiButton>)this.buttonList.iterator();
            while (iterator.hasNext()) {
                iterator.next().enabled = true;
            }
        }
    }
}
