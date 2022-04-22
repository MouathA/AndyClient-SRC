package wdl.gui;

import net.minecraft.client.resources.*;
import wdl.*;
import java.io.*;
import net.minecraft.client.gui.*;

public class GuiWDLAbout extends GuiScreen
{
    private final GuiScreen parent;
    private static final String FORUMS_THREAD;
    private static final String COREMOD_GITHUB;
    private static final String LITEMOD_GITHUB;
    private TextList list;
    
    public GuiWDLAbout(final GuiScreen parent) {
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, GuiWDLAbout.width / 2 - 155, 18, 150, 20, I18n.format("wdl.gui.about.extensions", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiWDLAbout.width / 2 + 5, 18, 150, 20, I18n.format("wdl.gui.about.debugInfo", new Object[0])));
        this.buttonList.add(new GuiButton(2, GuiWDLAbout.width / 2 - 100, GuiWDLAbout.height - 29, I18n.format("gui.done", new Object[0])));
        final String s = "1.8.9a-beta2";
        final String minecraftVersionInfo = WDL.getMinecraftVersionInfo();
        (this.list = new TextList(GuiWDLAbout.mc, GuiWDLAbout.width, GuiWDLAbout.height, 39, 32)).addLine(I18n.format("wdl.gui.about.blurb", new Object[0]));
        this.list.addBlankLine();
        this.list.addLine(I18n.format("wdl.gui.about.version", s, minecraftVersionInfo));
        this.list.addBlankLine();
        final String format = I18n.format("wdl.translatorCredit", WDL.minecraft.getLanguageManager().getCurrentLanguage().toString());
        if (format != null && !format.isEmpty()) {
            this.list.addLine(format);
            this.list.addBlankLine();
        }
        this.list.addLinkLine(I18n.format("wdl.gui.about.forumThread", new Object[0]), "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2520465");
        this.list.addBlankLine();
        this.list.addLinkLine(I18n.format("wdl.gui.about.coremodSrc", new Object[0]), "https://github.com/Pokechu22/WorldDownloader");
        this.list.addBlankLine();
        this.list.addLinkLine(I18n.format("wdl.gui.about.litemodSrc", new Object[0]), "https://github.com/uyjulian/LiteModWDL/");
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            GuiWDLAbout.mc.displayGuiScreen(new GuiWDLExtensions(this));
        }
        else if (guiButton.id == 1) {
            GuiScreen.setClipboardString(WDL.getDebugInfo());
            guiButton.displayString = I18n.format("wdl.gui.about.debugInfo.copied", new Object[0]);
        }
        else if (guiButton.id == 2) {
            GuiWDLAbout.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.list.func_148179_a(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.func_178039_p();
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (this.list.func_148181_b(n, n2, n3)) {
            return;
        }
        super.mouseReleased(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.list == null) {
            return;
        }
        this.list.drawScreen(n, n2, n3);
        super.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.about.title", new Object[0]), GuiWDLAbout.width / 2, 2, 16777215);
    }
    
    static {
        LITEMOD_GITHUB = "https://github.com/uyjulian/LiteModWDL/";
        COREMOD_GITHUB = "https://github.com/Pokechu22/WorldDownloader";
        FORUMS_THREAD = "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2520465";
    }
}
