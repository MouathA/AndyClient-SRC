package wdl.gui;

import net.minecraft.client.resources.*;
import wdl.*;
import java.io.*;
import net.minecraft.client.gui.*;

public class GuiWDLGenerator extends GuiScreen
{
    private String title;
    private GuiScreen parent;
    private GuiTextField seedField;
    private GuiButton fetchSeedBtn;
    private GuiButton generatorBtn;
    private GuiButton generateStructuresBtn;
    private GuiButton settingsPageBtn;
    private boolean hasSentSeedRequest;
    private String seedText;
    
    public GuiWDLGenerator(final GuiScreen parent) {
        this.hasSentSeedRequest = false;
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        this.seedText = I18n.format("wdl.gui.generator.seed", new Object[0]);
        final int stringWidth = this.fontRendererObj.getStringWidth(String.valueOf(this.seedText) + " ");
        this.buttonList.clear();
        this.title = I18n.format("wdl.gui.generator.title", WDL.baseFolderName.replace('@', ':'));
        int n = GuiWDLGenerator.height / 4 - 15;
        (this.seedField = new GuiTextField(40, this.fontRendererObj, GuiWDLGenerator.width / 2 - (100 - stringWidth), n, 200 - stringWidth, 18)).setText(WDL.worldProps.getProperty("RandomSeed"));
        n += 22;
        this.fetchSeedBtn = new GuiButton(0, GuiWDLGenerator.width / 2 - 100, n, I18n.format("wdl.gui.generator.fetchSeed", new Object[0]));
        if (WDL.thePlayer.canCommandSenderUseCommand(2, "seed")) {
            final String minecraftVersion = WDL.getMinecraftVersion();
            if (!minecraftVersion.startsWith("1.7") && !minecraftVersion.startsWith("1.8")) {
                this.fetchSeedBtn.enabled = false;
            }
        }
        if (this.hasSentSeedRequest) {
            this.fetchSeedBtn.enabled = false;
            this.fetchSeedBtn.displayString = I18n.format("wdl.gui.generator.fetchSeed.fetched", new Object[0]);
            this.seedField.setEnabled(false);
        }
        this.buttonList.add(this.fetchSeedBtn);
        n += 30;
        this.generatorBtn = new GuiButton(1, GuiWDLGenerator.width / 2 - 100, n, this.getGeneratorText());
        this.buttonList.add(this.generatorBtn);
        n += 22;
        this.generateStructuresBtn = new GuiButton(2, GuiWDLGenerator.width / 2 - 100, n, this.getGenerateStructuresText());
        this.buttonList.add(this.generateStructuresBtn);
        n += 22;
        this.settingsPageBtn = new GuiButton(3, GuiWDLGenerator.width / 2 - 100, n, "");
        this.updateSettingsButtonVisibility();
        this.buttonList.add(this.settingsPageBtn);
        this.buttonList.add(new GuiButton(100, GuiWDLGenerator.width / 2 - 100, GuiWDLGenerator.height - 29, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                if (this.hasSentSeedRequest) {
                    return;
                }
                WDL.thePlayer.sendChatMessage("/seed");
                this.hasSentSeedRequest = true;
                guiButton.displayString = I18n.format("wdl.gui.generator.fetchSeed.fetched", new Object[0]);
                this.fetchSeedBtn.enabled = false;
                this.seedField.setEnabled(false);
            }
            else if (guiButton.id == 1) {
                this.cycleGenerator();
            }
            else if (guiButton.id == 2) {
                this.cycleGenerateStructures();
            }
            else if (guiButton.id == 3) {
                if (WDL.worldProps.getProperty("MapGenerator", "").equals("flat")) {
                    GuiWDLGenerator.mc.displayGuiScreen(new GuiFlatPresets(new GuiCreateFlatWorldProxy()));
                }
                else if (WDL.worldProps.getProperty("MapGenerator", "").equals("custom")) {
                    GuiWDLGenerator.mc.displayGuiScreen(new GuiCustomizeWorldScreen(new GuiCreateWorldProxy(), WDL.worldProps.getProperty("GeneratorOptions", "")));
                }
            }
            else if (guiButton.id == 100) {
                GuiWDLGenerator.mc.displayGuiScreen(this.parent);
            }
        }
    }
    
    @Override
    public void onGuiClosed() {
        if (!this.hasSentSeedRequest) {
            WDL.worldProps.setProperty("RandomSeed", this.seedField.getText());
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.seedField.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        this.seedField.textboxKeyTyped(c, n);
    }
    
    @Override
    public void updateScreen() {
        this.seedField.updateCursorCounter();
        super.updateScreen();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        Utils.drawListBackground(23, 32, 0, 0, GuiWDLGenerator.height, GuiWDLGenerator.width);
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiWDLGenerator.width / 2, 8, 16777215);
        this.drawString(this.fontRendererObj, this.seedText, GuiWDLGenerator.width / 2 - 100, GuiWDLGenerator.height / 4 - 10, 16777215);
        this.seedField.drawTextBox();
        super.drawScreen(n, n2, n3);
        String s = null;
        if (Utils.isMouseOverTextBox(n, n2, this.seedField)) {
            s = I18n.format("wdl.gui.generator.seed.description", new Object[0]);
        }
        else if (this.fetchSeedBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.generator.fetchSeed.description", new Object[0]);
        }
        else if (this.generatorBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.generator.generator.description", new Object[0]);
        }
        else if (this.generateStructuresBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.generator.generateStructures.description", new Object[0]);
        }
        Utils.drawGuiInfoBox(s, GuiWDLGenerator.width, GuiWDLGenerator.height, 48);
    }
    
    private void cycleGenerator() {
        final String property = WDL.worldProps.getProperty("MapGenerator");
        if (property.equals("void")) {
            WDL.worldProps.setProperty("MapGenerator", "default");
            WDL.worldProps.setProperty("GeneratorName", "default");
            WDL.worldProps.setProperty("GeneratorVersion", "1");
            WDL.worldProps.setProperty("GeneratorOptions", "");
        }
        else if (property.equals("default")) {
            WDL.worldProps.setProperty("MapGenerator", "flat");
            WDL.worldProps.setProperty("GeneratorName", "flat");
            WDL.worldProps.setProperty("GeneratorVersion", "0");
            WDL.worldProps.setProperty("GeneratorOptions", "");
        }
        else if (property.equals("flat")) {
            WDL.worldProps.setProperty("MapGenerator", "largeBiomes");
            WDL.worldProps.setProperty("GeneratorName", "largeBiomes");
            WDL.worldProps.setProperty("GeneratorVersion", "0");
            WDL.worldProps.setProperty("GeneratorOptions", "");
        }
        else if (property.equals("largeBiomes")) {
            WDL.worldProps.setProperty("MapGenerator", "amplified");
            WDL.worldProps.setProperty("GeneratorName", "amplified");
            WDL.worldProps.setProperty("GeneratorVersion", "0");
            WDL.worldProps.setProperty("GeneratorOptions", "");
        }
        else if (property.equals("amplified")) {
            WDL.worldProps.setProperty("MapGenerator", "custom");
            WDL.worldProps.setProperty("GeneratorName", "custom");
            WDL.worldProps.setProperty("GeneratorVersion", "0");
            WDL.worldProps.setProperty("GeneratorOptions", "");
        }
        else if (property.equals("custom")) {
            WDL.worldProps.setProperty("MapGenerator", "legacy");
            WDL.worldProps.setProperty("GeneratorName", "default_1_1");
            WDL.worldProps.setProperty("GeneratorVersion", "0");
            WDL.worldProps.setProperty("GeneratorOptions", "");
        }
        else {
            WDL.worldProps.setProperty("MapGenerator", "void");
            WDL.worldProps.setProperty("GeneratorName", "flat");
            WDL.worldProps.setProperty("GeneratorVersion", "0");
            WDL.worldProps.setProperty("GeneratorOptions", ";0");
        }
        this.generatorBtn.displayString = this.getGeneratorText();
        this.updateSettingsButtonVisibility();
    }
    
    private void cycleGenerateStructures() {
        if (WDL.worldProps.getProperty("MapFeatures").equals("true")) {
            WDL.worldProps.setProperty("MapFeatures", "false");
        }
        else {
            WDL.worldProps.setProperty("MapFeatures", "true");
        }
        this.generateStructuresBtn.displayString = this.getGenerateStructuresText();
    }
    
    private void updateSettingsButtonVisibility() {
        if (WDL.worldProps.getProperty("MapGenerator", "").equals("flat")) {
            this.settingsPageBtn.visible = true;
            this.settingsPageBtn.displayString = I18n.format("wdl.gui.generator.flatSettings", new Object[0]);
        }
        else if (WDL.worldProps.getProperty("MapGenerator", "").equals("custom")) {
            this.settingsPageBtn.visible = true;
            this.settingsPageBtn.displayString = I18n.format("wdl.gui.generator.customSettings", new Object[0]);
        }
        else {
            this.settingsPageBtn.visible = false;
        }
    }
    
    private String getGeneratorText() {
        return I18n.format("wdl.gui.generator.generator." + WDL.worldProps.getProperty("MapGenerator"), new Object[0]);
    }
    
    private String getGenerateStructuresText() {
        return I18n.format("wdl.gui.generator.generateStructures." + WDL.worldProps.getProperty("MapFeatures"), new Object[0]);
    }
    
    private class GuiCreateFlatWorldProxy extends GuiCreateFlatWorld
    {
        final GuiWDLGenerator this$0;
        
        public GuiCreateFlatWorldProxy(final GuiWDLGenerator this$0) {
            this.this$0 = this$0;
            super(null, WDL.worldProps.getProperty("GeneratorOptions", ""));
        }
        
        @Override
        public void initGui() {
            GuiCreateFlatWorldProxy.mc.displayGuiScreen(this.this$0);
        }
        
        @Override
        protected void actionPerformed(final GuiButton guiButton) throws IOException {
        }
        
        @Override
        public void drawScreen(final int n, final int n2, final float n3) {
        }
        
        @Override
        public String func_146384_e() {
            return WDL.worldProps.getProperty("GeneratorOptions", "");
        }
        
        @Override
        public void func_146383_a(String s) {
            if (s == null) {
                s = "";
            }
            WDL.worldProps.setProperty("GeneratorOptions", s);
        }
    }
    
    private class GuiCreateWorldProxy extends GuiCreateWorld
    {
        final GuiWDLGenerator this$0;
        
        public GuiCreateWorldProxy(final GuiWDLGenerator this$0) {
            super(this.this$0 = this$0);
            this.field_146334_a = WDL.worldProps.getProperty("GeneratorOptions", "");
        }
        
        @Override
        public void initGui() {
            GuiCreateWorldProxy.mc.displayGuiScreen(this.this$0);
            WDL.worldProps.setProperty("GeneratorOptions", this.field_146334_a);
        }
        
        @Override
        protected void actionPerformed(final GuiButton guiButton) throws IOException {
        }
        
        @Override
        public void drawScreen(final int n, final int n2, final float n3) {
        }
    }
}
