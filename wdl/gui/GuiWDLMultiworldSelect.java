package wdl.gui;

import wdl.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.client.*;
import java.io.*;

public class GuiWDLMultiworldSelect extends GuiTurningCameraBase
{
    private final WorldSelectionCallback callback;
    private final String title;
    private GuiButton cancelBtn;
    private GuiButton acceptBtn;
    private GuiTextField newNameField;
    private GuiTextField searchField;
    private GuiButton newWorldButton;
    private boolean showNewWorldTextBox;
    private List linkedWorlds;
    private List linkedWorldsFiltered;
    private MultiworldInfo selectedMultiWorld;
    private int index;
    private GuiButton nextButton;
    private GuiButton prevButton;
    private int numWorldButtons;
    private String searchText;
    
    public GuiWDLMultiworldSelect(final String title, final WorldSelectionCallback callback) {
        this.index = 0;
        this.searchText = "";
        this.title = title;
        this.callback = callback;
        final String[] split = WDL.baseProps.getProperty("LinkedWorlds").split("\\|");
        this.linkedWorlds = new ArrayList();
        String[] array;
        while (0 < (array = split).length) {
            final String s = array[0];
            if (s != null) {
                if (!s.isEmpty()) {
                    final Properties loadWorldProps = WDL.loadWorldProps(s);
                    if (loadWorldProps.containsKey("WorldName")) {
                        this.linkedWorlds.add(new MultiworldInfo(s, loadWorldProps.getProperty("WorldName", s)));
                    }
                }
            }
            int n = 0;
            ++n;
        }
        (this.linkedWorldsFiltered = new ArrayList()).addAll(this.linkedWorlds);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.numWorldButtons = (GuiWDLMultiworldSelect.width - 50) / 155;
        if (this.numWorldButtons < 1) {
            this.numWorldButtons = 1;
        }
        final int n = (this.numWorldButtons * 155 + 45) / 2;
        final int n2 = GuiWDLMultiworldSelect.height - 49;
        this.cancelBtn = new GuiButton(-1, GuiWDLMultiworldSelect.width / 2 - 155, GuiWDLMultiworldSelect.height - 25, 150, 20, I18n.format("gui.cancel", new Object[0]));
        this.buttonList.add(this.cancelBtn);
        this.acceptBtn = new GuiButton(-2, GuiWDLMultiworldSelect.width / 2 + 5, GuiWDLMultiworldSelect.height - 25, 150, 20, I18n.format("wdl.gui.multiworldSelect.done", new Object[0]));
        this.acceptBtn.enabled = (this.selectedMultiWorld != null);
        this.buttonList.add(this.acceptBtn);
        this.prevButton = new GuiButton(-4, GuiWDLMultiworldSelect.width / 2 - n, n2, 20, 20, "<");
        this.buttonList.add(this.prevButton);
        while (0 < this.numWorldButtons) {
            this.buttonList.add(new WorldGuiButton(0, GuiWDLMultiworldSelect.width / 2 - n + 0 + 25, n2, 150, 20));
            int n3 = 0;
            ++n3;
        }
        this.nextButton = new GuiButton(-5, GuiWDLMultiworldSelect.width / 2 - n + 25 + this.numWorldButtons * 155, n2, 20, 20, ">");
        this.buttonList.add(this.nextButton);
        this.newWorldButton = new GuiButton(-3, GuiWDLMultiworldSelect.width / 2 - 155, 29, 150, 20, I18n.format("wdl.gui.multiworldSelect.newName", new Object[0]));
        this.buttonList.add(this.newWorldButton);
        this.newNameField = new GuiTextField(40, this.fontRendererObj, GuiWDLMultiworldSelect.width / 2 - 155, 29, 150, 20);
        (this.searchField = new GuiTextField(41, this.fontRendererObj, GuiWDLMultiworldSelect.width / 2 + 5, 29, 150, 20)).setText(this.searchText);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton instanceof WorldGuiButton) {
                this.selectedMultiWorld = ((WorldGuiButton)guiButton).getWorldInfo();
                if (this.selectedMultiWorld != null) {
                    this.acceptBtn.enabled = true;
                }
                else {
                    this.acceptBtn.enabled = false;
                }
            }
            else if (guiButton.id == -1) {
                this.callback.onCancel();
            }
            else if (guiButton.id == -2) {
                this.callback.onWorldSelected(this.selectedMultiWorld.folderName);
            }
            else if (guiButton.id == -3) {
                this.showNewWorldTextBox = true;
            }
            else if (guiButton.id == -4) {
                --this.index;
            }
            else if (guiButton.id == -5) {
                ++this.index;
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (this.showNewWorldTextBox) {
            this.newNameField.mouseClicked(n, n2, n3);
        }
        this.searchField.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            this.callback.onCancel();
        }
        super.keyTyped(c, n);
        if (this.showNewWorldTextBox) {
            this.newNameField.textboxKeyTyped(c, n);
            if (n == 28) {
                final String text = this.newNameField.getText();
                if (text != null && !text.isEmpty()) {
                    this.addMultiworld(text);
                    this.newNameField.setText("");
                    this.showNewWorldTextBox = false;
                }
            }
        }
        if (this.searchField.textboxKeyTyped(c, n)) {
            this.searchText = this.searchField.getText();
            this.rebuildFilteredWorlds();
        }
    }
    
    @Override
    public void updateScreen() {
        this.newNameField.updateCursorCounter();
        this.searchField.updateCursorCounter();
        super.updateScreen();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.index >= this.linkedWorlds.size() - this.numWorldButtons) {
            this.index = this.linkedWorlds.size() - this.numWorldButtons;
            this.nextButton.enabled = false;
        }
        else {
            this.nextButton.enabled = true;
        }
        if (this.index <= 0) {
            this.index = 0;
            this.prevButton.enabled = false;
        }
        else {
            this.prevButton.enabled = true;
        }
        Utils.drawBorder(53, 53, 0, 0, GuiWDLMultiworldSelect.height, GuiWDLMultiworldSelect.width);
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiWDLMultiworldSelect.width / 2, 8, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.multiworldSelect.subtitle", new Object[0]), GuiWDLMultiworldSelect.width / 2, 18, 16711680);
        if (this.showNewWorldTextBox) {
            this.newNameField.drawTextBox();
        }
        this.searchField.drawTextBox();
        if (this.searchField.getText().isEmpty() && !this.searchField.isFocused()) {
            this.drawString(this.fontRendererObj, I18n.format("wdl.gui.multiworldSelect.search", new Object[0]), this.searchField.xPosition + 4, this.searchField.yPosition + 6, 9474192);
        }
        this.newWorldButton.visible = !this.showNewWorldTextBox;
        super.drawScreen(n, n2, n3);
        this.drawMultiworldDescription();
    }
    
    private void addMultiworld(final String s) {
        String replace = s;
        char[] charArray;
        while (0 < (charArray = "\\/:*?\"<>|.".toCharArray()).length) {
            replace = replace.replace(charArray[0], '_');
            int n = 0;
            ++n;
        }
        final Properties properties = new Properties(WDL.baseProps);
        properties.setProperty("WorldName", s);
        WDL.baseProps.setProperty("LinkedWorlds", String.valueOf(WDL.baseProps.getProperty("LinkedWorlds")) + "|" + replace);
        WDL.saveProps(replace, properties);
        this.linkedWorlds.add(new MultiworldInfo(replace, s));
        this.rebuildFilteredWorlds();
    }
    
    private void rebuildFilteredWorlds() {
        final String lowerCase = this.searchText.toLowerCase();
        this.linkedWorldsFiltered.clear();
        for (final MultiworldInfo multiworldInfo : this.linkedWorlds) {
            if (multiworldInfo.displayName.toLowerCase().contains(lowerCase)) {
                this.linkedWorldsFiltered.add(multiworldInfo);
            }
        }
    }
    
    private void drawMultiworldDescription() {
        if (this.selectedMultiWorld == null) {
            return;
        }
        final String string = "Info about " + this.selectedMultiWorld.displayName;
        final List description = this.selectedMultiWorld.getDescription();
        int stringWidth = this.fontRendererObj.getStringWidth(string);
        final Iterator<String> iterator = description.iterator();
        while (iterator.hasNext()) {
            final int stringWidth2 = this.fontRendererObj.getStringWidth(iterator.next());
            if (stringWidth2 > stringWidth) {
                stringWidth = stringWidth2;
            }
        }
        Gui.drawRect(2, 61, 5 + stringWidth + 3, GuiWDLMultiworldSelect.height - 61, Integer.MIN_VALUE);
        this.drawString(this.fontRendererObj, string, 5, 64, 16777215);
        int n = 64 + this.fontRendererObj.FONT_HEIGHT;
        final Iterator<String> iterator2 = description.iterator();
        while (iterator2.hasNext()) {
            this.drawString(this.fontRendererObj, iterator2.next(), 5, n, 16777215);
            n += this.fontRendererObj.FONT_HEIGHT;
        }
    }
    
    static MultiworldInfo access$0(final GuiWDLMultiworldSelect guiWDLMultiworldSelect) {
        return guiWDLMultiworldSelect.selectedMultiWorld;
    }
    
    static int access$1(final GuiWDLMultiworldSelect guiWDLMultiworldSelect) {
        return guiWDLMultiworldSelect.index;
    }
    
    static List access$2(final GuiWDLMultiworldSelect guiWDLMultiworldSelect) {
        return guiWDLMultiworldSelect.linkedWorldsFiltered;
    }
    
    private static class MultiworldInfo
    {
        public final String folderName;
        public final String displayName;
        private List description;
        
        public MultiworldInfo(final String folderName, final String displayName) {
            this.folderName = folderName;
            this.displayName = displayName;
        }
        
        public List getDescription() {
            if (this.description == null) {
                (this.description = new ArrayList()).add("Defined dimensions:");
                final File[] listFiles = new File(new File(Minecraft.mcDataDir, "saves"), WDL.getWorldFolderName(this.folderName)).listFiles();
                if (listFiles != null) {
                    File[] array;
                    while (0 < (array = listFiles).length) {
                        final File file = array[0];
                        if (file.listFiles() != null) {
                            if (file.listFiles().length != 0) {
                                if (file.getName().equals("region")) {
                                    this.description.add(" * Overworld (#0)");
                                }
                                else if (file.getName().startsWith("DIM")) {
                                    final String substring = file.getName().substring(3);
                                    if (substring.equals("-1")) {
                                        this.description.add(" * Nether (#-1)");
                                    }
                                    else if (substring.equals("1")) {
                                        this.description.add(" * The End (#1)");
                                    }
                                    else {
                                        this.description.add(" * #" + substring);
                                    }
                                }
                            }
                        }
                        int n = 0;
                        ++n;
                    }
                }
            }
            return this.description;
        }
    }
    
    private class WorldGuiButton extends GuiButton
    {
        final GuiWDLMultiworldSelect this$0;
        
        public WorldGuiButton(final GuiWDLMultiworldSelect this$0, final int n, final int n2, final int n3, final int n4, final int n5) {
            this.this$0 = this$0;
            super(n, n2, n3, n4, n5, "");
        }
        
        public WorldGuiButton(final GuiWDLMultiworldSelect this$0, final int n, final int n2, final int n3, final String s, final String s2) {
            this.this$0 = this$0;
            super(n, n2, n3, "");
        }
        
        @Override
        public void drawButton(final Minecraft minecraft, final int n, final int n2) {
            final MultiworldInfo worldInfo = this.getWorldInfo();
            if (worldInfo == null) {
                this.displayString = "";
                this.enabled = false;
            }
            else {
                this.displayString = worldInfo.displayName;
                this.enabled = true;
            }
            if (worldInfo != null && worldInfo == GuiWDLMultiworldSelect.access$0(this.this$0)) {
                Gui.drawRect(this.xPosition - 2, this.yPosition - 2, this.xPosition + this.width + 2, this.yPosition + this.height + 2, -16744704);
            }
            super.drawButton(minecraft, n, n2);
        }
        
        public MultiworldInfo getWorldInfo() {
            final int n = GuiWDLMultiworldSelect.access$1(this.this$0) + this.id;
            if (n < 0) {
                return null;
            }
            if (n >= GuiWDLMultiworldSelect.access$2(this.this$0).size()) {
                return null;
            }
            return (MultiworldInfo)GuiWDLMultiworldSelect.access$2(this.this$0).get(n);
        }
    }
    
    public interface WorldSelectionCallback
    {
        void onCancel();
        
        void onWorldSelected(final String p0);
    }
}
