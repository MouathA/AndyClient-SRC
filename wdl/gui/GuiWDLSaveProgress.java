package wdl.gui;

import wdl.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class GuiWDLSaveProgress extends GuiTurningCameraBase implements WorldBackup.IBackupProgressMonitor
{
    private final String title;
    private String majorTaskMessage;
    private String minorTaskMessage;
    private int majorTaskNumber;
    private final int majorTaskCount;
    private int minorTaskProgress;
    private int minorTaskMaximum;
    private boolean doneWorking;
    
    public GuiWDLSaveProgress(final String title, final int majorTaskCount) {
        this.majorTaskMessage = "";
        this.minorTaskMessage = "";
        this.doneWorking = false;
        this.title = title;
        this.majorTaskCount = majorTaskCount;
        this.majorTaskNumber = 0;
    }
    
    public void startMajorTask(final String majorTaskMessage, final int minorTaskMaximum) {
        this.majorTaskMessage = majorTaskMessage;
        ++this.majorTaskNumber;
        this.minorTaskMessage = "";
        this.minorTaskProgress = 0;
        this.minorTaskMaximum = minorTaskMaximum;
    }
    
    public void setMinorTaskProgress(final String minorTaskMessage, final int minorTaskProgress) {
        this.minorTaskMessage = minorTaskMessage;
        this.minorTaskProgress = minorTaskProgress;
    }
    
    public void setMinorTaskProgress(final int minorTaskProgress) {
        this.minorTaskProgress = minorTaskProgress;
    }
    
    public void setDoneWorking() {
        this.doneWorking = true;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.doneWorking) {
            GuiWDLSaveProgress.mc.displayGuiScreen(null);
        }
        else {
            Utils.drawBorder(32, 32, 0, 0, GuiWDLSaveProgress.height, GuiWDLSaveProgress.width);
            String s = this.majorTaskMessage;
            if (this.majorTaskCount > 1) {
                s = I18n.format("wdl.gui.saveProgress.progressInfo", this.majorTaskMessage, this.majorTaskNumber, this.majorTaskCount);
            }
            final String minorTaskMessage = this.minorTaskMessage;
            if (this.minorTaskMaximum > 1) {
                s = I18n.format("wdl.gui.saveProgress.progressInfo", this.minorTaskMessage, this.minorTaskProgress, this.minorTaskMaximum);
            }
            Gui.drawCenteredString(this.fontRendererObj, this.title, GuiWDLSaveProgress.width / 2, 8, 16777215);
            Gui.drawCenteredString(this.fontRendererObj, s, GuiWDLSaveProgress.width / 2, 100, 16777215);
            if (this.minorTaskMaximum > 0) {
                this.drawProgressBar(110, 84, 89, this.majorTaskNumber * this.minorTaskMaximum + this.minorTaskProgress, (this.majorTaskCount + 1) * this.minorTaskMaximum);
            }
            else {
                this.drawProgressBar(110, 84, 89, this.majorTaskNumber, this.majorTaskCount);
            }
            Gui.drawCenteredString(this.fontRendererObj, minorTaskMessage, GuiWDLSaveProgress.width / 2, 130, 16777215);
            this.drawProgressBar(140, 64, 69, this.minorTaskProgress, this.minorTaskMaximum);
            super.drawScreen(n, n2, n3);
        }
    }
    
    private void drawProgressBar(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (n5 == 0) {
            return;
        }
        GuiWDLSaveProgress.mc.getTextureManager().bindTexture(Gui.icons);
        final int n6 = n4 * 182 / n5;
        final int n7 = GuiWDLSaveProgress.width / 2 - 91;
        this.drawTexturedModalRect(n7, n, 0, n2, 182, 5);
        this.drawTexturedModalRect(n7, n, 0, n3, n6, 5);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    @Override
    public void setNumberOfFiles(final int minorTaskMaximum) {
        this.minorTaskMaximum = minorTaskMaximum;
    }
    
    @Override
    public void onNextFile(final String s) {
        ++this.minorTaskProgress;
        this.minorTaskMessage = I18n.format("wdl.saveProgress.backingUp.file", s);
    }
}
