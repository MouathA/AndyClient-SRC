package wdl.gui;

import net.minecraft.client.resources.*;
import wdl.*;
import java.io.*;
import net.minecraft.client.gui.*;

public class GuiWDLBackup extends GuiScreen
{
    private GuiScreen parent;
    private String description;
    private WorldBackup.WorldBackupType backupType;
    private static int[] $SWITCH_TABLE$wdl$WorldBackup$WorldBackupType;
    
    public GuiWDLBackup(final GuiScreen parent) {
        this.parent = parent;
        this.description = String.valueOf(I18n.format("wdl.gui.backup.description1", new Object[0])) + "\n\n" + I18n.format("wdl.gui.backup.description2", new Object[0]) + "\n\n" + I18n.format("wdl.gui.backup.description3", new Object[0]);
    }
    
    @Override
    public void initGui() {
        this.backupType = WorldBackup.WorldBackupType.match(WDL.baseProps.getProperty("Backup", "ZIP"));
        this.buttonList.add(new GuiButton(0, GuiWDLBackup.width / 2 - 100, 32, this.getBackupButtonText()));
        this.buttonList.add(new GuiButton(100, GuiWDLBackup.width / 2 - 100, GuiWDLBackup.height - 29, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (!guiButton.enabled) {
            return;
        }
        if (guiButton.id == 0) {
            switch ($SWITCH_TABLE$wdl$WorldBackup$WorldBackupType()[this.backupType.ordinal()]) {
                case 1: {
                    this.backupType = WorldBackup.WorldBackupType.FOLDER;
                    break;
                }
                case 2: {
                    this.backupType = WorldBackup.WorldBackupType.ZIP;
                    break;
                }
                case 3: {
                    this.backupType = WorldBackup.WorldBackupType.NONE;
                    break;
                }
            }
            guiButton.displayString = this.getBackupButtonText();
        }
        else if (guiButton.id == 100) {
            GuiWDLBackup.mc.displayGuiScreen(this.parent);
        }
    }
    
    private String getBackupButtonText() {
        return I18n.format("wdl.gui.backup.backupMode", this.backupType.getDescription());
    }
    
    @Override
    public void onGuiClosed() {
        WDL.baseProps.setProperty("Backup", this.backupType.name());
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        Utils.drawListBackground(23, 32, 0, 0, GuiWDLBackup.height, GuiWDLBackup.width);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.backup.title", new Object[0]), GuiWDLBackup.width / 2, 8, 16777215);
        super.drawScreen(n, n2, n3);
        Utils.drawGuiInfoBox(this.description, GuiWDLBackup.width - 50, 3 * GuiWDLBackup.height / 5, GuiWDLBackup.width, GuiWDLBackup.height, 48);
    }
    
    static int[] $SWITCH_TABLE$wdl$WorldBackup$WorldBackupType() {
        final int[] $switch_TABLE$wdl$WorldBackup$WorldBackupType = GuiWDLBackup.$SWITCH_TABLE$wdl$WorldBackup$WorldBackupType;
        if ($switch_TABLE$wdl$WorldBackup$WorldBackupType != null) {
            return $switch_TABLE$wdl$WorldBackup$WorldBackupType;
        }
        final int[] $switch_TABLE$wdl$WorldBackup$WorldBackupType2 = new int[WorldBackup.WorldBackupType.values().length];
        $switch_TABLE$wdl$WorldBackup$WorldBackupType2[WorldBackup.WorldBackupType.FOLDER.ordinal()] = 2;
        $switch_TABLE$wdl$WorldBackup$WorldBackupType2[WorldBackup.WorldBackupType.NONE.ordinal()] = 1;
        $switch_TABLE$wdl$WorldBackup$WorldBackupType2[WorldBackup.WorldBackupType.ZIP.ordinal()] = 3;
        return GuiWDLBackup.$SWITCH_TABLE$wdl$WorldBackup$WorldBackupType = $switch_TABLE$wdl$WorldBackup$WorldBackupType2;
    }
}
