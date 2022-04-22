package net.minecraft.client.gui;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.net.*;
import org.lwjgl.*;
import java.util.*;
import java.io.*;

public class GuiScreenResourcePacks extends GuiScreen
{
    private static final Logger logger;
    private GuiScreen field_146965_f;
    private List field_146966_g;
    private List field_146969_h;
    private GuiResourcePackAvailable field_146970_i;
    private GuiResourcePackSelected field_146967_r;
    private boolean field_175289_s;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000820";
        logger = LogManager.getLogger();
    }
    
    public GuiScreenResourcePacks(final GuiScreen field_146965_f) {
        this.field_175289_s = false;
        this.field_146965_f = field_146965_f;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiOptionButton(2, GuiScreenResourcePacks.width / 2 - 154, GuiScreenResourcePacks.height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
        this.buttonList.add(new GuiOptionButton(1, GuiScreenResourcePacks.width / 2 + 4, GuiScreenResourcePacks.height - 48, I18n.format("gui.done", new Object[0])));
        this.field_146966_g = Lists.newArrayList();
        this.field_146969_h = Lists.newArrayList();
        final ResourcePackRepository resourcePackRepository = GuiScreenResourcePacks.mc.getResourcePackRepository();
        resourcePackRepository.updateRepositoryEntriesAll();
        final ArrayList arrayList = Lists.newArrayList(resourcePackRepository.getRepositoryEntriesAll());
        arrayList.removeAll(resourcePackRepository.getRepositoryEntries());
        final Iterator<ResourcePackRepository.Entry> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            this.field_146966_g.add(new ResourcePackListEntryFound(this, iterator.next()));
        }
        final Iterator iterator2 = Lists.reverse(resourcePackRepository.getRepositoryEntries()).iterator();
        while (iterator2.hasNext()) {
            this.field_146969_h.add(new ResourcePackListEntryFound(this, iterator2.next()));
        }
        this.field_146969_h.add(new ResourcePackListEntryDefault(this));
        (this.field_146970_i = new GuiResourcePackAvailable(GuiScreenResourcePacks.mc, 200, GuiScreenResourcePacks.height, this.field_146966_g)).setSlotXBoundsFromLeft(GuiScreenResourcePacks.width / 2 - 4 - 200);
        this.field_146970_i.registerScrollButtons(7, 8);
        (this.field_146967_r = new GuiResourcePackSelected(GuiScreenResourcePacks.mc, 200, GuiScreenResourcePacks.height, this.field_146969_h)).setSlotXBoundsFromLeft(GuiScreenResourcePacks.width / 2 + 4);
        this.field_146967_r.registerScrollButtons(7, 8);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_146967_r.func_178039_p();
        this.field_146970_i.func_178039_p();
    }
    
    public boolean hasResourcePackEntry(final ResourcePackListEntry resourcePackListEntry) {
        return this.field_146969_h.contains(resourcePackListEntry);
    }
    
    public List func_146962_b(final ResourcePackListEntry resourcePackListEntry) {
        return this.hasResourcePackEntry(resourcePackListEntry) ? this.field_146969_h : this.field_146966_g;
    }
    
    public List func_146964_g() {
        return this.field_146966_g;
    }
    
    public List func_146963_h() {
        return this.field_146969_h;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 2) {
                final File dirResourcepacks = GuiScreenResourcePacks.mc.getResourcePackRepository().getDirResourcepacks();
                final String absolutePath = dirResourcepacks.getAbsolutePath();
                if (Util.getOSType() == Util.EnumOS.OSX) {
                    GuiScreenResourcePacks.logger.info(absolutePath);
                    Runtime.getRuntime().exec(new String[] { "/usr/bin/open", absolutePath });
                    return;
                }
                if (Util.getOSType() == Util.EnumOS.WINDOWS) {
                    Runtime.getRuntime().exec(String.format("cmd.exe /C start \"Open file\" \"%s\"", absolutePath));
                    return;
                }
                final Class<?> forName = Class.forName("java.awt.Desktop");
                forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), dirResourcepacks.toURI());
                if (true) {
                    GuiScreenResourcePacks.logger.info("Opening via system class!");
                    Sys.openURL("file://" + absolutePath);
                }
            }
            else if (guiButton.id == 1) {
                if (this.field_175289_s) {
                    final ArrayList arrayList = Lists.newArrayList();
                    for (final ResourcePackListEntry resourcePackListEntry : this.field_146969_h) {
                        if (resourcePackListEntry instanceof ResourcePackListEntryFound) {
                            arrayList.add(((ResourcePackListEntryFound)resourcePackListEntry).func_148318_i());
                        }
                    }
                    Collections.reverse(arrayList);
                    GuiScreenResourcePacks.mc.getResourcePackRepository().func_148527_a(arrayList);
                    GuiScreenResourcePacks.mc.gameSettings.resourcePacks.clear();
                    final Iterator<ResourcePackRepository.Entry> iterator2 = arrayList.iterator();
                    while (iterator2.hasNext()) {
                        GuiScreenResourcePacks.mc.gameSettings.resourcePacks.add(iterator2.next().getResourcePackName());
                    }
                    GuiScreenResourcePacks.mc.gameSettings.saveOptions();
                    GuiScreenResourcePacks.mc.refreshResources();
                }
                GuiScreenResourcePacks.mc.displayGuiScreen(this.field_146965_f);
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.field_146970_i.func_148179_a(n, n2, n3);
        this.field_146967_r.func_148179_a(n, n2, n3);
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawBackground(0);
        this.field_146970_i.drawScreen(n, n2, n3);
        this.field_146967_r.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), GuiScreenResourcePacks.width / 2, 16, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), GuiScreenResourcePacks.width / 2 - 77, GuiScreenResourcePacks.height - 26, 8421504);
        super.drawScreen(n, n2, n3);
    }
    
    public void func_175288_g() {
        this.field_175289_s = true;
    }
}
