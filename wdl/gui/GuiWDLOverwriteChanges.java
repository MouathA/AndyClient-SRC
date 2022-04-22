package wdl.gui;

import net.minecraft.client.resources.*;
import wdl.*;
import net.minecraft.client.gui.*;
import java.text.*;
import java.util.*;
import java.io.*;

public class GuiWDLOverwriteChanges extends GuiTurningCameraBase implements WorldBackup.IBackupProgressMonitor
{
    private boolean backingUp;
    private String backupData;
    private int backupCount;
    private int backupCurrent;
    private String backupFile;
    private int infoBoxX;
    private int infoBoxY;
    private int infoBoxWidth;
    private int infoBoxHeight;
    private GuiButton backupAsZipButton;
    private GuiButton backupAsFolderButton;
    private GuiButton downloadNowButton;
    private GuiButton cancelButton;
    private final long lastSaved;
    private final long lastPlayed;
    private String title;
    private String footer;
    private String captionTitle;
    private String captionSubtitle;
    private String overwriteWarning1;
    private String overwriteWarning2;
    private String backingUpTitle;
    
    public GuiWDLOverwriteChanges(final long lastSaved, final long lastPlayed) {
        this.backingUp = false;
        this.backupData = "";
        this.backupFile = "";
        this.lastSaved = lastSaved;
        this.lastPlayed = lastPlayed;
    }
    
    @Override
    public void initGui() {
        this.backingUp = false;
        this.title = I18n.format("wdl.gui.overwriteChanges.title", new Object[0]);
        if (this.lastSaved != -1L) {
            this.footer = I18n.format("wdl.gui.overwriteChanges.footer", this.lastSaved, this.lastPlayed);
        }
        else {
            this.footer = I18n.format("wdl.gui.overwriteChanges.footerNeverSaved", this.lastPlayed);
        }
        this.captionTitle = I18n.format("wdl.gui.overwriteChanges.captionTitle", new Object[0]);
        this.captionSubtitle = I18n.format("wdl.gui.overwriteChanges.captionSubtitle", new Object[0]);
        this.overwriteWarning1 = I18n.format("wdl.gui.overwriteChanges.overwriteWarning1", new Object[0]);
        this.overwriteWarning2 = I18n.format("wdl.gui.overwriteChanges.overwriteWarning2", new Object[0]);
        this.backingUpTitle = I18n.format("wdl.gui.overwriteChanges.backingUp.title", new Object[0]);
        this.infoBoxWidth = this.fontRendererObj.getStringWidth(this.overwriteWarning1);
        this.infoBoxHeight = 132;
        if (this.infoBoxWidth < 200) {
            this.infoBoxWidth = 200;
        }
        this.infoBoxY = 48;
        this.infoBoxX = GuiWDLOverwriteChanges.width / 2 - this.infoBoxWidth / 2;
        final int n = GuiWDLOverwriteChanges.width / 2 - 100;
        int n2 = this.infoBoxY + 22;
        this.backupAsZipButton = new GuiButton(0, n, n2, I18n.format("wdl.gui.overwriteChanges.asZip.name", new Object[0]));
        this.buttonList.add(this.backupAsZipButton);
        n2 += 22;
        this.backupAsFolderButton = new GuiButton(1, n, n2, I18n.format("wdl.gui.overwriteChanges.asFolder.name", new Object[0]));
        this.buttonList.add(this.backupAsFolderButton);
        n2 += 22;
        this.downloadNowButton = new GuiButton(2, n, n2, I18n.format("wdl.gui.overwriteChanges.startNow.name", new Object[0]));
        this.buttonList.add(this.downloadNowButton);
        n2 += 22;
        this.cancelButton = new GuiButton(3, n, n2, I18n.format("wdl.gui.overwriteChanges.cancel.name", new Object[0]));
        this.buttonList.add(this.cancelButton);
        super.initGui();
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            return;
        }
        super.keyTyped(c, n);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (this.backingUp) {
            return;
        }
        if (guiButton.id == 0) {
            this.backingUp = true;
            new BackupThread(true).start();
        }
        if (guiButton.id == 1) {
            this.backingUp = true;
            new BackupThread(false).start();
        }
        if (guiButton.id == 2) {
            WDL.overrideLastModifiedCheck = true;
            GuiWDLOverwriteChanges.mc.displayGuiScreen(null);
        }
        if (guiButton.id == 3) {
            GuiWDLOverwriteChanges.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.backingUp) {
            this.drawBackground(0);
            Gui.drawCenteredString(this.fontRendererObj, this.backingUpTitle, GuiWDLOverwriteChanges.width / 2, GuiWDLOverwriteChanges.height / 4 - 40, 16777215);
            Gui.drawCenteredString(this.fontRendererObj, this.backupData, GuiWDLOverwriteChanges.width / 2, GuiWDLOverwriteChanges.height / 4 - 10, 16777215);
            if (this.backupFile != null) {
                Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.overwriteChanges.backingUp.progress", this.backupCurrent, this.backupCount, this.backupFile), GuiWDLOverwriteChanges.width / 2, GuiWDLOverwriteChanges.height / 4 + 10, 16777215);
            }
        }
        else {
            this.drawDefaultBackground();
            Utils.drawBorder(32, 22, 0, 0, GuiWDLOverwriteChanges.height, GuiWDLOverwriteChanges.width);
            Gui.drawCenteredString(this.fontRendererObj, this.title, GuiWDLOverwriteChanges.width / 2, 8, 16777215);
            Gui.drawCenteredString(this.fontRendererObj, this.footer, GuiWDLOverwriteChanges.width / 2, GuiWDLOverwriteChanges.height - 8 - this.fontRendererObj.FONT_HEIGHT, 16777215);
            Gui.drawRect(this.infoBoxX - 5, this.infoBoxY - 5, this.infoBoxX + this.infoBoxWidth + 5, this.infoBoxY + this.infoBoxHeight + 5, -1342177280);
            Gui.drawCenteredString(this.fontRendererObj, this.captionTitle, GuiWDLOverwriteChanges.width / 2, this.infoBoxY, 16777215);
            Gui.drawCenteredString(this.fontRendererObj, this.captionSubtitle, GuiWDLOverwriteChanges.width / 2, this.infoBoxY + this.fontRendererObj.FONT_HEIGHT, 16777215);
            Gui.drawCenteredString(this.fontRendererObj, this.overwriteWarning1, GuiWDLOverwriteChanges.width / 2, this.infoBoxY + 115, 16777215);
            Gui.drawCenteredString(this.fontRendererObj, this.overwriteWarning2, GuiWDLOverwriteChanges.width / 2, this.infoBoxY + 115 + this.fontRendererObj.FONT_HEIGHT, 16777215);
            super.drawScreen(n, n2, n3);
            String s = null;
            if (this.backupAsZipButton.isMouseOver()) {
                s = I18n.format("wdl.gui.overwriteChanges.asZip.description", new Object[0]);
            }
            else if (this.backupAsFolderButton.isMouseOver()) {
                s = I18n.format("wdl.gui.overwriteChanges.asFolder.description", new Object[0]);
            }
            else if (this.downloadNowButton.isMouseOver()) {
                s = I18n.format("wdl.gui.overwriteChanges.startNow.description", new Object[0]);
            }
            else if (this.cancelButton.isMouseOver()) {
                s = I18n.format("wdl.gui.overwriteChanges.cancel.description", new Object[0]);
            }
            Utils.drawGuiInfoBox(s, GuiWDLOverwriteChanges.width, GuiWDLOverwriteChanges.height, 48);
        }
    }
    
    @Override
    public void setNumberOfFiles(final int backupCount) {
        this.backupCount = backupCount;
        this.backupCurrent = 0;
    }
    
    @Override
    public void onNextFile(final String backupFile) {
        ++this.backupCurrent;
        this.backupFile = backupFile;
    }
    
    static void access$0(final GuiWDLOverwriteChanges guiWDLOverwriteChanges, final boolean backingUp) {
        guiWDLOverwriteChanges.backingUp = backingUp;
    }
    
    static void access$1(final GuiWDLOverwriteChanges guiWDLOverwriteChanges, final String backupData) {
        guiWDLOverwriteChanges.backupData = backupData;
    }
    
    private class BackupThread extends Thread
    {
        private final DateFormat folderDateFormat;
        private final boolean zip;
        final GuiWDLOverwriteChanges this$0;
        
        public BackupThread(final GuiWDLOverwriteChanges this$0, final boolean zip) {
            this.this$0 = this$0;
            this.folderDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            this.zip = zip;
        }
        
        @Override
        public void run() {
            final String string = String.valueOf(WDL.getWorldFolderName(WDL.worldName)) + "_" + this.folderDateFormat.format(new Date()) + "_user" + (this.zip ? ".zip" : "");
            if (this.zip) {
                GuiWDLOverwriteChanges.access$1(this.this$0, I18n.format("wdl.gui.overwriteChanges.backingUp.zip", string));
            }
            else {
                GuiWDLOverwriteChanges.access$1(this.this$0, I18n.format("wdl.gui.overwriteChanges.backingUp.folder", string));
            }
            final File worldDirectory = WDL.saveHandler.getWorldDirectory();
            final File file = new File(worldDirectory.getParentFile(), string);
            if (file.exists()) {
                throw new IOException("Backup target (" + file + ") already exists!");
            }
            if (this.zip) {
                WorldBackup.zipDirectory(worldDirectory, file, this.this$0);
            }
            else {
                WorldBackup.copyDirectory(worldDirectory, file, this.this$0);
            }
            GuiWDLOverwriteChanges.access$0(this.this$0, false);
            WDL.overrideLastModifiedCheck = true;
            GuiWDLOverwriteChanges.mc.displayGuiScreen(null);
        }
    }
}
