package com.mojang.realmsclient.gui.screens;

import org.lwjgl.input.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.realms.*;

public class RealmsResetNormalWorldScreen extends RealmsScreen
{
    private RealmsResetWorldScreen lastScreen;
    private RealmsEditBox seedEdit;
    private Boolean generateStructures;
    private Integer levelTypeIndex;
    String[] levelTypes;
    private final int BUTTON_CANCEL_ID = 0;
    private final int BUTTON_RESET_ID = 1;
    private static final int BUTTON_LEVEL_TYPE_ID = 2;
    private static final int BUTTON_GENERATE_STRUCTURES_ID = 3;
    private final int SEED_EDIT_BOX = 4;
    private RealmsButton resetButton;
    private RealmsButton levelTypeButton;
    private RealmsButton generateStructuresButton;
    
    public RealmsResetNormalWorldScreen(final RealmsResetWorldScreen lastScreen) {
        this.generateStructures = true;
        this.levelTypeIndex = 0;
        this.lastScreen = lastScreen;
    }
    
    @Override
    public void tick() {
        this.seedEdit.tick();
        super.tick();
    }
    
    @Override
    public void init() {
        this.levelTypes = new String[] { RealmsScreen.getLocalizedString("generator.default"), RealmsScreen.getLocalizedString("generator.flat"), RealmsScreen.getLocalizedString("generator.largeBiomes"), RealmsScreen.getLocalizedString("generator.amplified") };
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 + 8, RealmsConstants.row(12), 97, 20, RealmsScreen.getLocalizedString("gui.back")));
        this.buttonsAdd(this.resetButton = RealmsScreen.newButton(1, this.width() / 2 - 102, RealmsConstants.row(12), 97, 20, RealmsScreen.getLocalizedString("mco.backup.button.reset")));
        (this.seedEdit = this.newEditBox(4, this.width() / 2 - 100, RealmsConstants.row(2), 200, 20)).setFocus(true);
        this.seedEdit.setMaxLength(32);
        this.seedEdit.setValue("");
        this.buttonsAdd(this.levelTypeButton = RealmsScreen.newButton(2, this.width() / 2 - 102, RealmsConstants.row(4), 205, 20, this.levelTypeTitle()));
        this.buttonsAdd(this.generateStructuresButton = RealmsScreen.newButton(3, this.width() / 2 - 102, RealmsConstants.row(6) - 2, 205, 20, this.generateStructuresTitle()));
    }
    
    @Override
    public void removed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        this.seedEdit.keyPressed(c, n);
        if (n == 28 || n == 156) {
            this.buttonClicked(this.resetButton);
        }
        if (n == 1) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (!realmsButton.active()) {
            return;
        }
        switch (realmsButton.id()) {
            case 0: {
                Realms.setScreen(this.lastScreen);
                break;
            }
            case 1: {
                this.lastScreen.resetWorld(new RealmsResetWorldScreen.ResetWorldInfo(this.seedEdit.getValue(), this.levelTypeIndex, this.generateStructures));
                break;
            }
            case 2: {
                this.levelTypeIndex = (this.levelTypeIndex + 1) % this.levelTypes.length;
                realmsButton.msg(this.levelTypeTitle());
                break;
            }
            case 3: {
                this.generateStructures = !this.generateStructures;
                realmsButton.msg(this.generateStructuresTitle());
                break;
            }
            default: {}
        }
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        super.mouseClicked(n, n2, n3);
        this.seedEdit.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.reset.world.generate"), this.width() / 2, 17, 16777215);
        this.drawString(RealmsScreen.getLocalizedString("mco.reset.world.seed"), this.width() / 2 - 100, RealmsConstants.row(1), 10526880);
        this.seedEdit.render();
        super.render(n, n2, n3);
    }
    
    private String levelTypeTitle() {
        return RealmsScreen.getLocalizedString("selectWorld.mapType") + " " + this.levelTypes[this.levelTypeIndex];
    }
    
    private String generateStructuresTitle() {
        return RealmsScreen.getLocalizedString("selectWorld.mapFeatures") + " " + (this.generateStructures ? RealmsScreen.getLocalizedString("mco.configure.world.on") : RealmsScreen.getLocalizedString("mco.configure.world.off"));
    }
}
