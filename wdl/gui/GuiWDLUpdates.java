package wdl.gui;

import net.minecraft.client.resources.*;
import java.io.*;
import wdl.*;
import wdl.update.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

public class GuiWDLUpdates extends GuiScreen
{
    private final GuiScreen parent;
    private UpdateList list;
    private GuiButton updateMinecraftVersionButton;
    private GuiButton updateAllowBetasButton;
    
    public GuiWDLUpdates(final GuiScreen parent) {
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        this.list = new UpdateList();
        this.updateMinecraftVersionButton = new GuiButton(0, GuiWDLUpdates.width / 2 - 155, 18, 150, 20, this.getUpdateMinecraftVersionText());
        this.buttonList.add(this.updateMinecraftVersionButton);
        this.updateAllowBetasButton = new GuiButton(1, GuiWDLUpdates.width / 2 + 5, 18, 150, 20, this.getAllowBetasText());
        this.buttonList.add(this.updateAllowBetasButton);
        this.buttonList.add(new GuiButton(100, GuiWDLUpdates.width / 2 - 100, GuiWDLUpdates.height - 29, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.cycleUpdateMinecraftVersion();
        }
        if (guiButton.id == 1) {
            this.cycleAllowBetas();
        }
        if (guiButton.id == 100) {
            GuiWDLUpdates.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    public void onGuiClosed() {
    }
    
    private void cycleUpdateMinecraftVersion() {
        final String property = WDL.globalProps.getProperty("UpdateMinecraftVersion");
        if (property.equals("client")) {
            WDL.globalProps.setProperty("UpdateMinecraftVersion", "server");
        }
        else if (property.equals("server")) {
            WDL.globalProps.setProperty("UpdateMinecraftVersion", "any");
        }
        else {
            WDL.globalProps.setProperty("UpdateMinecraftVersion", "client");
        }
        this.updateMinecraftVersionButton.displayString = this.getUpdateMinecraftVersionText();
    }
    
    private void cycleAllowBetas() {
        if (WDL.globalProps.getProperty("UpdateAllowBetas").equals("true")) {
            WDL.globalProps.setProperty("UpdateAllowBetas", "false");
        }
        else {
            WDL.globalProps.setProperty("UpdateAllowBetas", "true");
        }
        this.updateAllowBetasButton.displayString = this.getAllowBetasText();
    }
    
    private String getUpdateMinecraftVersionText() {
        return I18n.format("wdl.gui.updates.updateMinecraftVersion." + WDL.globalProps.getProperty("UpdateMinecraftVersion"), WDL.getMinecraftVersion());
    }
    
    private String getAllowBetasText() {
        return I18n.format("wdl.gui.updates.updateAllowBetas." + WDL.globalProps.getProperty("UpdateAllowBetas"), new Object[0]);
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
        UpdateList.access$4(this.list);
        this.list.drawScreen(n, n2, n3);
        if (!WDLUpdateChecker.hasFinishedUpdateCheck()) {
            Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.updates.pleaseWait", new Object[0]), GuiWDLUpdates.width / 2, GuiWDLUpdates.height / 2, 16777215);
        }
        else if (WDLUpdateChecker.hasUpdateCheckFailed()) {
            final String updateCheckFailReason = WDLUpdateChecker.getUpdateCheckFailReason();
            Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.updates.checkFailed", new Object[0]), GuiWDLUpdates.width / 2, GuiWDLUpdates.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2, 16733525);
            Gui.drawCenteredString(this.fontRendererObj, I18n.format(updateCheckFailReason, new Object[0]), GuiWDLUpdates.width / 2, GuiWDLUpdates.height / 2 + this.fontRendererObj.FONT_HEIGHT / 2, 16733525);
        }
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.updates.title", new Object[0]), GuiWDLUpdates.width / 2, 8, 16777215);
        super.drawScreen(n, n2, n3);
        if (this.updateMinecraftVersionButton.isMouseOver()) {
            Utils.drawGuiInfoBox(I18n.format("wdl.gui.updates.updateMinecraftVersion.description", WDL.getMinecraftVersion()), GuiWDLUpdates.width, GuiWDLUpdates.height, 32);
        }
        else if (this.updateAllowBetasButton.isMouseOver()) {
            Utils.drawGuiInfoBox(I18n.format("wdl.gui.updates.updateAllowBetas.description", new Object[0]), GuiWDLUpdates.width, GuiWDLUpdates.height, 32);
        }
    }
    
    private String buildVersionInfo(final Release release) {
        String loader = "?";
        String mainMinecraftVersion = "?";
        String s = "?";
        if (release.hiddenInfo != null) {
            loader = release.hiddenInfo.loader;
            mainMinecraftVersion = release.hiddenInfo.mainMinecraftVersion;
            final String[] supportedMinecraftVersions = release.hiddenInfo.supportedMinecraftVersions;
            if (supportedMinecraftVersions.length == 1) {
                s = I18n.format("wdl.gui.updates.update.version.listSingle", supportedMinecraftVersions[0]);
            }
            else if (supportedMinecraftVersions.length == 2) {
                s = I18n.format("wdl.gui.updates.update.version.listDouble", supportedMinecraftVersions[0], supportedMinecraftVersions[1]);
            }
            else {
                final StringBuilder sb = new StringBuilder();
                while (0 < supportedMinecraftVersions.length) {
                    sb.append(I18n.format("wdl.gui.updates.update.version.listStart", supportedMinecraftVersions[0]));
                    int n = 0;
                    ++n;
                }
                s = sb.toString();
            }
        }
        return I18n.format("wdl.gui.updates.update.version", loader, mainMinecraftVersion, s);
    }
    
    private String buildReleaseTitle(final Release release) {
        final String tag = release.tag;
        String mainMinecraftVersion = "?";
        if (release.hiddenInfo != null) {
            mainMinecraftVersion = release.hiddenInfo.mainMinecraftVersion;
        }
        if (release.prerelease) {
            return I18n.format("wdl.gui.updates.update.title.prerelease", tag, mainMinecraftVersion);
        }
        return I18n.format("wdl.gui.updates.update.title.release", tag, mainMinecraftVersion);
    }
    
    static FontRenderer access$0(final GuiWDLUpdates guiWDLUpdates) {
        return guiWDLUpdates.fontRendererObj;
    }
    
    static String access$1(final GuiWDLUpdates guiWDLUpdates, final Release release) {
        return guiWDLUpdates.buildReleaseTitle(release);
    }
    
    static String access$2(final GuiWDLUpdates guiWDLUpdates, final Release release) {
        return guiWDLUpdates.buildVersionInfo(release);
    }
    
    private class GuiWDLSingleUpdate extends GuiScreen
    {
        private final GuiWDLUpdates parent;
        private final Release release;
        private TextList list;
        final GuiWDLUpdates this$0;
        
        public GuiWDLSingleUpdate(final GuiWDLUpdates this$0, final GuiWDLUpdates parent, final Release release) {
            this.this$0 = this$0;
            this.parent = parent;
            this.release = release;
        }
        
        @Override
        public void initGui() {
            this.buttonList.add(new GuiButton(0, GuiWDLSingleUpdate.width / 2 - 155, 18, 150, 20, I18n.format("wdl.gui.updates.update.viewOnline", new Object[0])));
            if (this.release.hiddenInfo != null) {
                this.buttonList.add(new GuiButton(1, GuiWDLSingleUpdate.width / 2 + 5, 18, 150, 20, I18n.format("wdl.gui.updates.update.viewForumPost", new Object[0])));
            }
            this.buttonList.add(new GuiButton(100, GuiWDLSingleUpdate.width / 2 - 100, GuiWDLSingleUpdate.height - 29, I18n.format("gui.done", new Object[0])));
            (this.list = new TextList(GuiWDLSingleUpdate.mc, GuiWDLSingleUpdate.width, GuiWDLSingleUpdate.height, 39, 32)).addLine(GuiWDLUpdates.access$1(this.this$0, this.release));
            this.list.addLine(I18n.format("wdl.gui.updates.update.releaseDate", this.release.date));
            this.list.addLine(GuiWDLUpdates.access$2(this.this$0, this.release));
            this.list.addBlankLine();
            this.list.addLine(this.release.textOnlyBody);
        }
        
        @Override
        protected void actionPerformed(final GuiButton guiButton) throws IOException {
            if (guiButton.id == 0) {
                Utils.openLink(this.release.URL);
            }
            if (guiButton.id == 1) {
                Utils.openLink(this.release.hiddenInfo.post);
            }
            if (guiButton.id == 100) {
                GuiWDLSingleUpdate.mc.displayGuiScreen(this.parent);
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
            Gui.drawCenteredString(this.fontRendererObj, GuiWDLUpdates.access$1(this.this$0, this.release), GuiWDLSingleUpdate.width / 2, 8, 16777215);
            super.drawScreen(n, n2, n3);
        }
    }
    
    private class UpdateList extends GuiListExtended
    {
        private List displayedVersions;
        private Release recomendedRelease;
        final GuiWDLUpdates this$0;
        
        public UpdateList(final GuiWDLUpdates this$0) {
            this.this$0 = this$0;
            super(GuiWDLUpdates.mc, GuiWDLUpdates.width, GuiWDLUpdates.height, 39, GuiWDLUpdates.height - 32, (GuiWDLUpdates.access$0(this$0).FONT_HEIGHT + 1) * 6 + 2);
            this.showSelectionBox = true;
        }
        
        private void regenerateVersionList() {
            this.displayedVersions = new ArrayList();
            if (WDLUpdateChecker.hasNewVersion()) {
                this.recomendedRelease = WDLUpdateChecker.getRecomendedRelease();
            }
            else {
                this.recomendedRelease = null;
            }
            final List releases = WDLUpdateChecker.getReleases();
            if (releases == null) {
                return;
            }
            final Iterator<Release> iterator = releases.iterator();
            while (iterator.hasNext()) {
                this.displayedVersions.add(new VersionEntry(iterator.next()));
            }
        }
        
        @Override
        public VersionEntry getListEntry(final int n) {
            return this.displayedVersions.get(n);
        }
        
        @Override
        protected int getSize() {
            return this.displayedVersions.size();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return "1.8.9a-beta2".equals(VersionEntry.access$0(this.getListEntry(n)).tag);
        }
        
        @Override
        public int getListWidth() {
            return this.width - 30;
        }
        
        @Override
        protected int getScrollBarX() {
            return this.width - 10;
        }
        
        @Override
        public IGuiListEntry getListEntry(final int n) {
            return this.getListEntry(n);
        }
        
        static Release access$1(final UpdateList list) {
            return list.recomendedRelease;
        }
        
        static int access$2(final UpdateList list) {
            return list.slotHeight;
        }
        
        static Minecraft access$3(final UpdateList list) {
            return list.mc;
        }
        
        static void access$4(final UpdateList list) {
            list.regenerateVersionList();
        }
        
        static GuiWDLUpdates access$5(final UpdateList list) {
            return list.this$0;
        }
        
        private class VersionEntry implements IGuiListEntry
        {
            private final Release release;
            private String title;
            private String caption;
            private String body1;
            private String body2;
            private String body3;
            private String time;
            private final int fontHeight;
            final UpdateList this$1;
            
            public VersionEntry(final UpdateList this$1, final Release release) {
                this.this$1 = this$1;
                this.release = release;
                this.fontHeight = GuiWDLUpdates.access$0(UpdateList.access$5(this$1)).FONT_HEIGHT + 1;
                this.title = GuiWDLUpdates.access$1(UpdateList.access$5(this$1), release);
                this.caption = GuiWDLUpdates.access$2(UpdateList.access$5(this$1), release);
                final List wordWrap = Utils.wordWrap(release.textOnlyBody, this$1.getListWidth());
                this.body1 = ((wordWrap.size() >= 1) ? wordWrap.get(0) : "");
                this.body2 = ((wordWrap.size() >= 2) ? wordWrap.get(1) : "");
                this.body3 = ((wordWrap.size() >= 3) ? wordWrap.get(2) : "");
                this.time = I18n.format("wdl.gui.updates.update.releaseDate", release.date);
            }
            
            @Override
            public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
                String s;
                if (this.this$1.isSelected(n)) {
                    s = I18n.format("wdl.gui.updates.currentVersion", this.title);
                }
                else if (this.release == UpdateList.access$1(this.this$1)) {
                    s = I18n.format("wdl.gui.updates.recomendedVersion", this.title);
                }
                else {
                    s = this.title;
                }
                GuiWDLUpdates.access$0(UpdateList.access$5(this.this$1)).drawString(s, n2, n3 + this.fontHeight * 0, 16777215);
                GuiWDLUpdates.access$0(UpdateList.access$5(this.this$1)).drawString(this.caption, n2, n3 + this.fontHeight * 1, 8421504);
                GuiWDLUpdates.access$0(UpdateList.access$5(this.this$1)).drawString(this.body1, n2, n3 + this.fontHeight * 2, 16777215);
                GuiWDLUpdates.access$0(UpdateList.access$5(this.this$1)).drawString(this.body2, n2, n3 + this.fontHeight * 3, 16777215);
                GuiWDLUpdates.access$0(UpdateList.access$5(this.this$1)).drawString(this.body3, n2, n3 + this.fontHeight * 4, 16777215);
                GuiWDLUpdates.access$0(UpdateList.access$5(this.this$1)).drawString(this.time, n2, n3 + this.fontHeight * 5, 8421504);
                if (n6 > n2 && n6 < n2 + n4 && n7 > n3 && n7 < n3 + n5) {
                    Gui.drawRect(n2 - 2, n3 - 2, n2 + n4 - 3, n3 + n5 + 2, 536870911);
                }
            }
            
            @Override
            public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
                if (n6 > 0 && n6 < UpdateList.access$2(this.this$1)) {
                    UpdateList.access$3(this.this$1).displayGuiScreen(UpdateList.access$5(this.this$1).new GuiWDLSingleUpdate(UpdateList.access$5(this.this$1), this.release));
                    Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0f));
                    return true;
                }
                return false;
            }
            
            @Override
            public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            }
            
            @Override
            public void setSelected(final int n, final int n2, final int n3) {
            }
            
            static Release access$0(final VersionEntry versionEntry) {
                return versionEntry.release;
            }
        }
    }
}
