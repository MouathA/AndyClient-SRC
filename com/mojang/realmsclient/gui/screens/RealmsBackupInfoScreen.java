package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.*;
import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.realms.*;

public class RealmsBackupInfoScreen extends RealmsScreen
{
    private final RealmsScreen lastScreen;
    private final int BUTTON_BACK_ID = 0;
    private final Backup backup;
    private List keys;
    private BackupInfoList backupInfoList;
    String[] difficulties;
    String[] gameModes;
    
    public RealmsBackupInfoScreen(final RealmsScreen lastScreen, final Backup backup) {
        this.keys = new ArrayList();
        this.difficulties = new String[] { RealmsScreen.getLocalizedString("options.difficulty.peaceful"), RealmsScreen.getLocalizedString("options.difficulty.easy"), RealmsScreen.getLocalizedString("options.difficulty.normal"), RealmsScreen.getLocalizedString("options.difficulty.hard") };
        this.gameModes = new String[] { RealmsScreen.getLocalizedString("selectWorld.gameMode.survival"), RealmsScreen.getLocalizedString("selectWorld.gameMode.creative"), RealmsScreen.getLocalizedString("selectWorld.gameMode.adventure") };
        this.lastScreen = lastScreen;
        this.backup = backup;
        if (backup.changeList != null) {
            final Iterator<Map.Entry<Object, V>> iterator = backup.changeList.entrySet().iterator();
            while (iterator.hasNext()) {
                this.keys.add(iterator.next().getKey());
            }
        }
    }
    
    @Override
    public void mouseEvent() {
        super.mouseEvent();
        this.backupInfoList.mouseEvent();
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 100, this.height() / 4 + 120 + 24, RealmsScreen.getLocalizedString("gui.back")));
        this.backupInfoList = new BackupInfoList();
    }
    
    @Override
    public void removed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (!realmsButton.active()) {
            return;
        }
        if (realmsButton.id() == 0) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString("Changes from last backup", this.width() / 2, 10, 16777215);
        this.backupInfoList.render(n, n2, n3);
        super.render(n, n2, n3);
    }
    
    private String checkForSpecificMetadata(final String s, final String s2) {
        final String lowerCase = s.toLowerCase();
        if (lowerCase.contains("game") && lowerCase.contains("mode")) {
            return this.gameModeMetadata(s2);
        }
        if (lowerCase.contains("game") && lowerCase.contains("difficulty")) {
            return this.gameDifficultyMetadata(s2);
        }
        return s2;
    }
    
    private String gameDifficultyMetadata(final String s) {
        return this.difficulties[Integer.parseInt(s)];
    }
    
    private String gameModeMetadata(final String s) {
        return this.gameModes[Integer.parseInt(s)];
    }
    
    static Backup access$000(final RealmsBackupInfoScreen realmsBackupInfoScreen) {
        return realmsBackupInfoScreen.backup;
    }
    
    static List access$100(final RealmsBackupInfoScreen realmsBackupInfoScreen) {
        return realmsBackupInfoScreen.keys;
    }
    
    static String access$200(final RealmsBackupInfoScreen realmsBackupInfoScreen, final String s, final String s2) {
        return realmsBackupInfoScreen.checkForSpecificMetadata(s, s2);
    }
    
    private class BackupInfoList extends RealmsSimpleScrolledSelectionList
    {
        final RealmsBackupInfoScreen this$0;
        
        public BackupInfoList(final RealmsBackupInfoScreen this$0) {
            this.this$0 = this$0;
            super(this$0.width(), this$0.height(), 32, this$0.height() - 64, 36);
        }
        
        @Override
        public int getItemCount() {
            return RealmsBackupInfoScreen.access$000(this.this$0).changeList.size();
        }
        
        @Override
        public void selectItem(final int n, final boolean b, final int n2, final int n3) {
        }
        
        @Override
        public boolean isSelectedItem(final int n) {
            return false;
        }
        
        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 36;
        }
        
        @Override
        public void renderBackground() {
        }
        
        @Override
        protected void renderItem(final int n, final int n2, final int n3, final int n4, final Tezzelator tezzelator, final int n5, final int n6) {
            final String s = RealmsBackupInfoScreen.access$100(this.this$0).get(n);
            this.this$0.drawString(s, this.width() / 2 - 40, n3, 10526880);
            this.this$0.drawString(RealmsBackupInfoScreen.access$200(this.this$0, s, (String)RealmsBackupInfoScreen.access$000(this.this$0).changeList.get(s)), this.width() / 2 - 40, n3 + 12, 16777215);
        }
    }
}
