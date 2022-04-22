package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.*;
import org.lwjgl.input.*;
import net.minecraft.realms.*;
import com.mojang.realmsclient.gui.*;

public class RealmsSlotOptionsScreen extends RealmsScreen
{
    private static final int BUTTON_CANCEL_ID = 0;
    private static final int BUTTON_DONE_ID = 1;
    private static final int BUTTON_DIFFICULTY_ID = 2;
    private static final int BUTTON_GAMEMODE_ID = 3;
    private static final int BUTTON_PVP_ID = 4;
    private static final int BUTTON_SPAWN_ANIMALS_ID = 5;
    private static final int BUTTON_SPAWN_MONSTERS_ID = 6;
    private static final int BUTTON_SPAWN_NPCS_ID = 7;
    private static final int BUTTON_SPAWN_PROTECTION_ID = 8;
    private static final int BUTTON_COMMANDBLOCKS_ID = 9;
    private static final int BUTTON_FORCE_GAMEMODE_ID = 10;
    private static final int NAME_EDIT_BOX = 11;
    private RealmsEditBox nameEdit;
    protected final RealmsConfigureWorldScreen parent;
    private int column1_x;
    private int column_width;
    private int column2_x;
    private RealmsOptions options;
    private RealmsServer.WorldType worldType;
    private int activeSlot;
    private int difficultyIndex;
    private int gameModeIndex;
    private Boolean pvp;
    private Boolean spawnNPCs;
    private Boolean spawnAnimals;
    private Boolean spawnMonsters;
    private Integer spawnProtection;
    private Boolean commandBlocks;
    private Boolean forceGameMode;
    private RealmsButton pvpButton;
    private RealmsButton spawnAnimalsButton;
    private RealmsButton spawnMonstersButton;
    private RealmsButton spawnNPCsButton;
    private RealmsSliderButton spawnProtectionButton;
    private RealmsButton commandBlocksButton;
    private RealmsButton forceGameModeButton;
    private boolean notNormal;
    String[] difficulties;
    String[] gameModes;
    String[][] gameModeHints;
    
    public RealmsSlotOptionsScreen(final RealmsConfigureWorldScreen parent, final RealmsOptions options, final RealmsServer.WorldType worldType, final int activeSlot) {
        this.notNormal = false;
        this.parent = parent;
        this.options = options;
        this.worldType = worldType;
        this.activeSlot = activeSlot;
    }
    
    @Override
    public void removed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void tick() {
        this.nameEdit.tick();
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (!realmsButton.active()) {
            return;
        }
        switch (realmsButton.id()) {
            case 1: {
                this.saveSettings();
                break;
            }
            case 0: {
                Realms.setScreen(this.parent);
                break;
            }
            case 2: {
                this.difficultyIndex = (this.difficultyIndex + 1) % this.difficulties.length;
                realmsButton.msg(this.difficultyTitle());
                if (this.worldType.equals(RealmsServer.WorldType.NORMAL)) {
                    this.spawnMonstersButton.active(this.difficultyIndex != 0);
                    this.spawnMonstersButton.msg(this.spawnMonstersTitle());
                    break;
                }
                break;
            }
            case 3: {
                this.gameModeIndex = (this.gameModeIndex + 1) % this.gameModes.length;
                realmsButton.msg(this.gameModeTitle());
                break;
            }
            case 4: {
                this.pvp = !this.pvp;
                realmsButton.msg(this.pvpTitle());
                break;
            }
            case 5: {
                this.spawnAnimals = !this.spawnAnimals;
                realmsButton.msg(this.spawnAnimalsTitle());
                break;
            }
            case 7: {
                this.spawnNPCs = !this.spawnNPCs;
                realmsButton.msg(this.spawnNPCsTitle());
                break;
            }
            case 6: {
                this.spawnMonsters = !this.spawnMonsters;
                realmsButton.msg(this.spawnMonstersTitle());
                break;
            }
            case 9: {
                this.commandBlocks = !this.commandBlocks;
                realmsButton.msg(this.commandBlocksTitle());
                break;
            }
            case 10: {
                this.forceGameMode = !this.forceGameMode;
                realmsButton.msg(this.forceGameModeTitle());
                break;
            }
            default: {}
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        this.nameEdit.keyPressed(c, n);
        switch (n) {
            case 15: {
                this.nameEdit.setFocus(!this.nameEdit.isFocused());
                break;
            }
            case 1: {
                Realms.setScreen(this.parent);
                break;
            }
            case 28:
            case 156: {
                this.saveSettings();
                break;
            }
            default: {}
        }
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        super.mouseClicked(n, n2, n3);
        this.nameEdit.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void init() {
        this.column1_x = this.width() / 2 - 122;
        this.column_width = 122;
        this.column2_x = this.width() / 2 + 10;
        this.createDifficultyAndGameMode();
        this.difficultyIndex = this.options.difficulty;
        this.gameModeIndex = this.options.gameMode;
        if (!this.worldType.equals(RealmsServer.WorldType.NORMAL)) {
            this.notNormal = true;
            this.pvp = true;
            this.spawnProtection = 0;
            this.forceGameMode = false;
            this.spawnAnimals = true;
            this.spawnMonsters = true;
            this.spawnNPCs = true;
            this.commandBlocks = true;
        }
        else {
            this.pvp = this.options.pvp;
            this.spawnProtection = this.options.spawnProtection;
            this.forceGameMode = this.options.forceGameMode;
            this.spawnAnimals = this.options.spawnAnimals;
            this.spawnMonsters = this.options.spawnMonsters;
            this.spawnNPCs = this.options.spawnNPCs;
            this.commandBlocks = this.options.commandBlocks;
        }
        (this.nameEdit = this.newEditBox(11, this.column1_x + 2, RealmsConstants.row(2), this.column_width - 4, 20)).setFocus(true);
        this.nameEdit.setMaxLength(10);
        this.nameEdit.setValue(this.options.getSlotName(this.activeSlot));
        this.buttonsAdd(RealmsScreen.newButton(3, this.column2_x, RealmsConstants.row(2), this.column_width, 20, this.gameModeTitle()));
        this.buttonsAdd(this.pvpButton = RealmsScreen.newButton(4, this.column1_x, RealmsConstants.row(4), this.column_width, 20, this.pvpTitle()));
        this.buttonsAdd(this.spawnAnimalsButton = RealmsScreen.newButton(5, this.column2_x, RealmsConstants.row(4), this.column_width, 20, this.spawnAnimalsTitle()));
        this.buttonsAdd(RealmsScreen.newButton(2, this.column1_x, RealmsConstants.row(6), this.column_width, 20, this.difficultyTitle()));
        this.buttonsAdd(this.spawnMonstersButton = RealmsScreen.newButton(6, this.column2_x, RealmsConstants.row(6), this.column_width, 20, this.spawnMonstersTitle()));
        this.buttonsAdd(this.spawnProtectionButton = new SettingsSlider(8, this.column1_x, RealmsConstants.row(8), this.column_width, 17, this.spawnProtection, 0.0f, 16.0f));
        this.buttonsAdd(this.spawnNPCsButton = RealmsScreen.newButton(7, this.column2_x, RealmsConstants.row(8), this.column_width, 20, this.spawnNPCsTitle()));
        this.buttonsAdd(this.forceGameModeButton = RealmsScreen.newButton(10, this.column1_x, RealmsConstants.row(10), this.column_width, 20, this.forceGameModeTitle()));
        this.buttonsAdd(this.commandBlocksButton = RealmsScreen.newButton(9, this.column2_x, RealmsConstants.row(10), this.column_width, 20, this.commandBlocksTitle()));
        if (!this.worldType.equals(RealmsServer.WorldType.NORMAL)) {
            this.pvpButton.active(false);
            this.spawnAnimalsButton.active(false);
            this.spawnNPCsButton.active(false);
            this.spawnMonstersButton.active(false);
            this.spawnProtectionButton.active(false);
            this.commandBlocksButton.active(false);
            this.spawnProtectionButton.active(false);
            this.forceGameModeButton.active(false);
        }
        if (this.difficultyIndex == 0) {
            this.spawnMonstersButton.active(false);
        }
        this.buttonsAdd(RealmsScreen.newButton(1, this.column1_x, RealmsConstants.row(13), this.column_width, 20, RealmsScreen.getLocalizedString("mco.configure.world.buttons.done")));
        this.buttonsAdd(RealmsScreen.newButton(0, this.column2_x, RealmsConstants.row(13), this.column_width, 20, RealmsScreen.getLocalizedString("gui.cancel")));
    }
    
    private void createDifficultyAndGameMode() {
        this.difficulties = new String[] { RealmsScreen.getLocalizedString("options.difficulty.peaceful"), RealmsScreen.getLocalizedString("options.difficulty.easy"), RealmsScreen.getLocalizedString("options.difficulty.normal"), RealmsScreen.getLocalizedString("options.difficulty.hard") };
        this.gameModes = new String[] { RealmsScreen.getLocalizedString("selectWorld.gameMode.survival"), RealmsScreen.getLocalizedString("selectWorld.gameMode.creative"), RealmsScreen.getLocalizedString("selectWorld.gameMode.adventure") };
        this.gameModeHints = new String[][] { { RealmsScreen.getLocalizedString("selectWorld.gameMode.survival.line1"), RealmsScreen.getLocalizedString("selectWorld.gameMode.survival.line2") }, { RealmsScreen.getLocalizedString("selectWorld.gameMode.creative.line1"), RealmsScreen.getLocalizedString("selectWorld.gameMode.creative.line2") }, { RealmsScreen.getLocalizedString("selectWorld.gameMode.adventure.line1"), RealmsScreen.getLocalizedString("selectWorld.gameMode.adventure.line2") } };
    }
    
    private String difficultyTitle() {
        return RealmsScreen.getLocalizedString("options.difficulty") + ": " + this.difficulties[this.difficultyIndex];
    }
    
    private String gameModeTitle() {
        return RealmsScreen.getLocalizedString("selectWorld.gameMode") + ": " + this.gameModes[this.gameModeIndex];
    }
    
    private String pvpTitle() {
        return RealmsScreen.getLocalizedString("mco.configure.world.pvp") + ": " + (this.pvp ? RealmsScreen.getLocalizedString("mco.configure.world.on") : RealmsScreen.getLocalizedString("mco.configure.world.off"));
    }
    
    private String spawnAnimalsTitle() {
        return RealmsScreen.getLocalizedString("mco.configure.world.spawnAnimals") + ": " + (this.spawnAnimals ? RealmsScreen.getLocalizedString("mco.configure.world.on") : RealmsScreen.getLocalizedString("mco.configure.world.off"));
    }
    
    private String spawnMonstersTitle() {
        if (this.difficultyIndex == 0) {
            return RealmsScreen.getLocalizedString("mco.configure.world.spawnMonsters") + ": " + RealmsScreen.getLocalizedString("mco.configure.world.off");
        }
        return RealmsScreen.getLocalizedString("mco.configure.world.spawnMonsters") + ": " + (this.spawnMonsters ? RealmsScreen.getLocalizedString("mco.configure.world.on") : RealmsScreen.getLocalizedString("mco.configure.world.off"));
    }
    
    private String spawnNPCsTitle() {
        return RealmsScreen.getLocalizedString("mco.configure.world.spawnNPCs") + ": " + (this.spawnNPCs ? RealmsScreen.getLocalizedString("mco.configure.world.on") : RealmsScreen.getLocalizedString("mco.configure.world.off"));
    }
    
    private String commandBlocksTitle() {
        return RealmsScreen.getLocalizedString("mco.configure.world.commandBlocks") + ": " + (this.commandBlocks ? RealmsScreen.getLocalizedString("mco.configure.world.on") : RealmsScreen.getLocalizedString("mco.configure.world.off"));
    }
    
    private String forceGameModeTitle() {
        return RealmsScreen.getLocalizedString("mco.configure.world.forceGameMode") + ": " + (this.forceGameMode ? RealmsScreen.getLocalizedString("mco.configure.world.on") : RealmsScreen.getLocalizedString("mco.configure.world.off"));
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        final String localizedString = RealmsScreen.getLocalizedString("mco.configure.world.edit.slot.name");
        this.drawString(localizedString, this.column1_x + this.fontWidth(localizedString) / 2, RealmsConstants.row(0) + 5, 16777215);
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.configure.world.buttons.options"), this.width() / 2, 17, 16777215);
        if (this.notNormal) {
            this.drawCenteredString(RealmsScreen.getLocalizedString("mco.configure.world.edit.subscreen.adventuremap"), this.width() / 2, 30, 16711680);
        }
        this.nameEdit.render();
        super.render(n, n2, n3);
    }
    
    public void renderHints() {
        this.drawString(this.gameModeHints[this.gameModeIndex][0], this.column2_x + 2, RealmsConstants.row(0), 10526880);
        this.drawString(this.gameModeHints[this.gameModeIndex][1], this.column2_x + 2, RealmsConstants.row(0) + this.fontLineHeight() + 2, 10526880);
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3) {
        if (!this.spawnProtectionButton.active()) {
            return;
        }
        this.spawnProtectionButton.released(n, n2);
    }
    
    @Override
    public void mouseDragged(final int n, final int n2, final int n3, final long n4) {
        if (!this.spawnProtectionButton.active()) {
            return;
        }
        if (n < this.column1_x + this.spawnProtectionButton.getWidth() && n > this.column1_x && n2 < this.spawnProtectionButton.y() + 20 && n2 > this.spawnProtectionButton.y()) {
            this.spawnProtectionButton.clicked(n, n2);
        }
    }
    
    private String getSlotName() {
        if (this.nameEdit.getValue().equals(this.options.getDefaultSlotName(this.activeSlot))) {
            return "";
        }
        return this.nameEdit.getValue();
    }
    
    private void saveSettings() {
        if (this.worldType.equals(RealmsServer.WorldType.ADVENTUREMAP)) {
            this.parent.saveSlotSettings(new RealmsOptions(this.options.pvp, this.options.spawnAnimals, this.options.spawnMonsters, this.options.spawnNPCs, this.options.spawnProtection, this.options.commandBlocks, this.difficultyIndex, this.gameModeIndex, this.options.forceGameMode, this.getSlotName()));
        }
        else {
            this.parent.saveSlotSettings(new RealmsOptions(this.pvp, this.spawnAnimals, this.spawnMonsters, this.spawnNPCs, this.spawnProtection, this.commandBlocks, this.difficultyIndex, this.gameModeIndex, this.forceGameMode, this.getSlotName()));
        }
    }
    
    static Integer access$000(final RealmsSlotOptionsScreen realmsSlotOptionsScreen) {
        return realmsSlotOptionsScreen.spawnProtection;
    }
    
    static RealmsSliderButton access$100(final RealmsSlotOptionsScreen realmsSlotOptionsScreen) {
        return realmsSlotOptionsScreen.spawnProtectionButton;
    }
    
    static Integer access$002(final RealmsSlotOptionsScreen realmsSlotOptionsScreen, final Integer spawnProtection) {
        return realmsSlotOptionsScreen.spawnProtection = spawnProtection;
    }
    
    private class SettingsSlider extends RealmsSliderButton
    {
        final RealmsSlotOptionsScreen this$0;
        
        public SettingsSlider(final RealmsSlotOptionsScreen this$0, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final float n7, final float n8) {
            this.this$0 = this$0;
            super(n, n2, n3, n4, n5, n6, n7, n8);
        }
        
        @Override
        public String getMessage() {
            return RealmsScreen.getLocalizedString("mco.configure.world.spawnProtection") + ": " + ((RealmsSlotOptionsScreen.access$000(this.this$0) == 0) ? RealmsScreen.getLocalizedString("mco.configure.world.off") : RealmsSlotOptionsScreen.access$000(this.this$0));
        }
        
        @Override
        public void clicked(final float n) {
            if (!RealmsSlotOptionsScreen.access$100(this.this$0).active()) {
                return;
            }
            RealmsSlotOptionsScreen.access$002(this.this$0, (int)n);
        }
    }
}
