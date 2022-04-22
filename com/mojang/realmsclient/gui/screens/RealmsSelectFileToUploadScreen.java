package com.mojang.realmsclient.gui.screens;

import java.text.*;
import org.lwjgl.input.*;
import org.apache.logging.log4j.*;
import net.minecraft.realms.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;

public class RealmsSelectFileToUploadScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private static final int CANCEL_BUTTON = 1;
    private static final int UPLOAD_BUTTON = 2;
    private final RealmsResetWorldScreen lastScreen;
    private final long worldId;
    private int slotId;
    private RealmsButton uploadButton;
    private final DateFormat DATE_FORMAT;
    private List levelList;
    private int selectedWorld;
    private WorldSelectionList worldSelectionList;
    private String worldLang;
    private String conversionLang;
    private String[] gameModesLang;
    
    public RealmsSelectFileToUploadScreen(final long worldId, final int slotId, final RealmsResetWorldScreen lastScreen) {
        this.DATE_FORMAT = new SimpleDateFormat();
        this.levelList = new ArrayList();
        this.selectedWorld = -1;
        this.gameModesLang = new String[4];
        this.lastScreen = lastScreen;
        this.worldId = worldId;
        this.slotId = slotId;
    }
    
    private void loadLevelList() throws Exception {
        Collections.sort((List<Comparable>)(this.levelList = this.getLevelStorageSource().getLevelList()));
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.loadLevelList();
        this.worldLang = RealmsScreen.getLocalizedString("selectWorld.world");
        this.conversionLang = RealmsScreen.getLocalizedString("selectWorld.conversion");
        this.gameModesLang[Realms.survivalId()] = RealmsScreen.getLocalizedString("gameMode.survival");
        this.gameModesLang[Realms.creativeId()] = RealmsScreen.getLocalizedString("gameMode.creative");
        this.gameModesLang[Realms.adventureId()] = RealmsScreen.getLocalizedString("gameMode.adventure");
        this.gameModesLang[Realms.spectatorId()] = RealmsScreen.getLocalizedString("gameMode.spectator");
        this.buttonsAdd(RealmsScreen.newButton(1, this.width() / 2 + 6, this.height() - 32, 153, 20, RealmsScreen.getLocalizedString("gui.back")));
        this.buttonsAdd(this.uploadButton = RealmsScreen.newButton(2, this.width() / 2 - 154, this.height() - 32, 153, 20, RealmsScreen.getLocalizedString("mco.upload.button.name")));
        this.uploadButton.active(this.selectedWorld >= 0 && this.selectedWorld < this.levelList.size());
        this.worldSelectionList = new WorldSelectionList();
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
        if (realmsButton.id() == 1) {
            Realms.setScreen(this.lastScreen);
        }
        else if (realmsButton.id() == 2) {
            this.upload();
        }
    }
    
    private void upload() {
        if (this.selectedWorld != -1 && !this.levelList.get(this.selectedWorld).isHardcore()) {
            Realms.setScreen(new RealmsUploadScreen(this.worldId, this.slotId, this.lastScreen, this.levelList.get(this.selectedWorld)));
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.worldSelectionList.render(n, n2, n3);
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.upload.select.world.title"), this.width() / 2, 13, 16777215);
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.upload.select.world.subtitle"), this.width() / 2, RealmsConstants.row(-1), 10526880);
        if (this.levelList.size() == 0) {
            this.drawCenteredString(RealmsScreen.getLocalizedString("mco.upload.select.world.none"), this.width() / 2, this.height() / 2 - 20, 16777215);
        }
        super.render(n, n2, n3);
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void mouseEvent() {
        super.mouseEvent();
        this.worldSelectionList.mouseEvent();
    }
    
    @Override
    public void tick() {
        super.tick();
    }
    
    static List access$000(final RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
        return realmsSelectFileToUploadScreen.levelList;
    }
    
    static int access$102(final RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen, final int selectedWorld) {
        return realmsSelectFileToUploadScreen.selectedWorld = selectedWorld;
    }
    
    static int access$100(final RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
        return realmsSelectFileToUploadScreen.selectedWorld;
    }
    
    static RealmsButton access$200(final RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
        return realmsSelectFileToUploadScreen.uploadButton;
    }
    
    static void access$300(final RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
        realmsSelectFileToUploadScreen.upload();
    }
    
    static String access$400(final RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
        return realmsSelectFileToUploadScreen.worldLang;
    }
    
    static DateFormat access$500(final RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
        return realmsSelectFileToUploadScreen.DATE_FORMAT;
    }
    
    static String access$600(final RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
        return realmsSelectFileToUploadScreen.conversionLang;
    }
    
    static String[] access$700(final RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
        return realmsSelectFileToUploadScreen.gameModesLang;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    private class WorldSelectionList extends RealmsScrolledSelectionList
    {
        final RealmsSelectFileToUploadScreen this$0;
        
        public WorldSelectionList(final RealmsSelectFileToUploadScreen this$0) {
            this.this$0 = this$0;
            super(this$0.width(), this$0.height(), RealmsConstants.row(0), this$0.height() - 40, 36);
        }
        
        @Override
        public int getItemCount() {
            return RealmsSelectFileToUploadScreen.access$000(this.this$0).size();
        }
        
        @Override
        public void selectItem(final int n, final boolean b, final int n2, final int n3) {
            RealmsSelectFileToUploadScreen.access$102(this.this$0, n);
            RealmsSelectFileToUploadScreen.access$200(this.this$0).active(RealmsSelectFileToUploadScreen.access$100(this.this$0) >= 0 && RealmsSelectFileToUploadScreen.access$100(this.this$0) < this.getItemCount() && !RealmsSelectFileToUploadScreen.access$000(this.this$0).get(RealmsSelectFileToUploadScreen.access$100(this.this$0)).isHardcore());
            if (b) {
                RealmsSelectFileToUploadScreen.access$300(this.this$0);
            }
        }
        
        @Override
        public boolean isSelectedItem(final int n) {
            return n == RealmsSelectFileToUploadScreen.access$100(this.this$0);
        }
        
        @Override
        public int getMaxPosition() {
            return RealmsSelectFileToUploadScreen.access$000(this.this$0).size() * 36;
        }
        
        @Override
        public void renderBackground() {
            this.this$0.renderBackground();
        }
        
        @Override
        protected void renderItem(final int n, final int n2, final int n3, final int n4, final Tezzelator tezzelator, final int n5, final int n6) {
            final RealmsLevelSummary realmsLevelSummary = RealmsSelectFileToUploadScreen.access$000(this.this$0).get(n);
            String s = realmsLevelSummary.getLevelName();
            if (s == null || s.isEmpty()) {
                s = RealmsSelectFileToUploadScreen.access$400(this.this$0) + " " + (n + 1);
            }
            final String string = realmsLevelSummary.getLevelId() + " (" + RealmsSelectFileToUploadScreen.access$500(this.this$0).format(new Date(realmsLevelSummary.getLastPlayed())) + ")";
            final String s2 = "";
            String s3;
            if (realmsLevelSummary.isRequiresConversion()) {
                s3 = RealmsSelectFileToUploadScreen.access$600(this.this$0) + " " + s2;
            }
            else {
                s3 = RealmsSelectFileToUploadScreen.access$700(this.this$0)[realmsLevelSummary.getGameMode()];
                if (realmsLevelSummary.isHardcore()) {
                    s3 = ChatFormatting.DARK_RED + RealmsScreen.getLocalizedString("mco.upload.hardcore") + ChatFormatting.RESET;
                }
                if (realmsLevelSummary.hasCheats()) {
                    s3 = s3 + ", " + RealmsScreen.getLocalizedString("selectWorld.cheats");
                }
            }
            this.this$0.drawString(s, n2 + 2, n3 + 1, 16777215);
            this.this$0.drawString(string, n2 + 2, n3 + 12, 8421504);
            this.this$0.drawString(s3, n2 + 2, n3 + 12 + 10, 8421504);
        }
    }
}
