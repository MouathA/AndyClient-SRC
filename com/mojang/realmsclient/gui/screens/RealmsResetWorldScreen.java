package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.*;
import com.mojang.realmsclient.client.*;
import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.realms.*;
import org.lwjgl.opengl.*;
import com.mojang.realmsclient.util.*;
import com.mojang.realmsclient.gui.*;
import org.apache.logging.log4j.*;

public class RealmsResetWorldScreen extends RealmsScreenWithCallback
{
    private static final Logger LOGGER;
    private RealmsScreen lastScreen;
    private RealmsServer serverData;
    private RealmsScreen returnScreen;
    private String title;
    private String subtitle;
    private String buttonTitle;
    private int subtitleColor;
    private static final String SLOT_FRAME_LOCATION = "realms:textures/gui/realms/slot_frame.png";
    private static final String UPLOAD_LOCATION = "realms:textures/gui/realms/upload.png";
    private final int BUTTON_CANCEL_ID = 0;
    private boolean loaded;
    private List templates;
    private List adventuremaps;
    private final Random random;
    private ResetType selectedType;
    private int templateId;
    private int adventureMapId;
    public int slot;
    private ResetType typeToReset;
    private ResetWorldInfo worldInfoToReset;
    private WorldTemplate worldTemplateToReset;
    private String resetTitle;
    
    public RealmsResetWorldScreen(final RealmsScreen lastScreen, final RealmsServer serverData, final RealmsScreen returnScreen) {
        this.title = RealmsScreen.getLocalizedString("mco.reset.world.title");
        this.subtitle = RealmsScreen.getLocalizedString("mco.reset.world.warning");
        this.buttonTitle = RealmsScreen.getLocalizedString("gui.cancel");
        this.subtitleColor = 16711680;
        this.loaded = false;
        this.templates = new ArrayList();
        this.adventuremaps = new ArrayList();
        this.random = new Random();
        this.selectedType = ResetType.NONE;
        this.slot = -1;
        this.typeToReset = ResetType.NONE;
        this.worldInfoToReset = null;
        this.worldTemplateToReset = null;
        this.resetTitle = null;
        this.lastScreen = lastScreen;
        this.serverData = serverData;
        this.returnScreen = returnScreen;
    }
    
    public RealmsResetWorldScreen(final RealmsScreen realmsScreen, final RealmsServer realmsServer, final RealmsScreen realmsScreen2, final String title, final String subtitle, final int subtitleColor, final String buttonTitle) {
        this(realmsScreen, realmsServer, realmsScreen2);
        this.title = title;
        this.subtitle = subtitle;
        this.subtitleColor = subtitleColor;
        this.buttonTitle = buttonTitle;
    }
    
    public void setSlot(final int slot) {
        this.slot = slot;
    }
    
    public void setResetTitle(final String resetTitle) {
        this.resetTitle = resetTitle;
    }
    
    @Override
    public void init() {
        this.buttonsClear();
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 40, RealmsConstants.row(14) - 10, 80, 20, this.buttonTitle));
        if (!this.loaded) {
            new Thread("Realms-reset-world-fetcher") {
                final RealmsResetWorldScreen this$0;
                
                @Override
                public void run() {
                    for (final WorldTemplate worldTemplate : RealmsClient.createRealmsClient().fetchWorldTemplates().templates) {
                        if (!worldTemplate.recommendedPlayers.equals("")) {
                            RealmsResetWorldScreen.access$000(this.this$0).add(worldTemplate);
                        }
                        else {
                            RealmsResetWorldScreen.access$100(this.this$0).add(worldTemplate);
                        }
                    }
                    RealmsResetWorldScreen.access$202(this.this$0, RealmsResetWorldScreen.access$300(this.this$0).nextInt(RealmsResetWorldScreen.access$100(this.this$0).size()));
                    RealmsResetWorldScreen.access$402(this.this$0, RealmsResetWorldScreen.access$300(this.this$0).nextInt(RealmsResetWorldScreen.access$000(this.this$0).size()));
                    RealmsResetWorldScreen.access$502(this.this$0, true);
                }
            }.start();
        }
    }
    
    @Override
    public void removed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            Realms.setScreen(this.lastScreen);
        }
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
    public void mouseClicked(final int n, final int n2, final int n3) {
        switch (this.selectedType) {
            case NONE: {
                break;
            }
            case GENERATE: {
                Realms.setScreen(new RealmsResetNormalWorldScreen(this));
                break;
            }
            case UPLOAD: {
                Realms.setScreen(new RealmsSelectFileToUploadScreen(this.serverData.id, (this.slot != -1) ? this.slot : this.serverData.activeSlot, this));
                break;
            }
            case ADVENTURE: {
                final RealmsSelectWorldTemplateScreen screen = new RealmsSelectWorldTemplateScreen(this, null, false, false, this.adventuremaps);
                screen.setTitle(RealmsScreen.getLocalizedString("mco.reset.world.adventure"));
                Realms.setScreen(screen);
                break;
            }
            case SURVIVAL_SPAWN: {
                final RealmsSelectWorldTemplateScreen screen2 = new RealmsSelectWorldTemplateScreen(this, null, false, false, this.templates);
                screen2.setTitle(RealmsScreen.getLocalizedString("mco.reset.world.template"));
                Realms.setScreen(screen2);
                break;
            }
            default: {}
        }
    }
    
    private int frame(final int n) {
        return this.width() / 2 - 80 + (n - 1) * 100;
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.selectedType = ResetType.NONE;
        this.renderBackground();
        this.drawCenteredString(this.title, this.width() / 2, 7, 16777215);
        this.drawCenteredString(this.subtitle, this.width() / 2, 22, this.subtitleColor);
        if (this.loaded) {
            this.drawFrame(this.frame(1), RealmsConstants.row(0) + 10, n, n2, RealmsScreen.getLocalizedString("mco.reset.world.generate"), -1L, "textures/gui/title/background/panorama_3.png", ResetType.GENERATE);
            this.drawFrame(this.frame(2), RealmsConstants.row(0) + 10, n, n2, RealmsScreen.getLocalizedString("mco.reset.world.upload"), -1L, "realms:textures/gui/realms/upload.png", ResetType.UPLOAD);
            this.drawFrame(this.frame(1), RealmsConstants.row(6) + 20, n, n2, RealmsScreen.getLocalizedString("mco.reset.world.adventure"), Long.valueOf(this.adventuremaps.get(this.adventureMapId).id), this.adventuremaps.get(this.adventureMapId).image, ResetType.ADVENTURE);
            this.drawFrame(this.frame(2), RealmsConstants.row(6) + 20, n, n2, RealmsScreen.getLocalizedString("mco.reset.world.template"), Long.valueOf(this.templates.get(this.templateId).id), this.templates.get(this.templateId).image, ResetType.SURVIVAL_SPAWN);
        }
        super.render(n, n2, n3);
    }
    
    private void drawFrame(final int n, final int n2, final int n3, final int n4, final String s, final long n5, final String s2, final ResetType selectedType) {
        if (n3 >= n && n3 <= n + 60 && n4 >= n2 - 12 && n4 <= n2 + 60) {
            this.selectedType = selectedType;
        }
        if (n5 != -1L) {
            RealmsTextureManager.bindWorldTemplate(String.valueOf(n5), s2);
        }
        else {
            RealmsScreen.bind(s2);
        }
        if (true) {
            GL11.glColor4f(0.56f, 0.56f, 0.56f, 1.0f);
        }
        else {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        RealmsScreen.blit(n + 2, n2 + 2, 0.0f, 0.0f, 56, 56, 56.0f, 56.0f);
        RealmsScreen.bind("realms:textures/gui/realms/slot_frame.png");
        if (true) {
            GL11.glColor4f(0.56f, 0.56f, 0.56f, 1.0f);
        }
        else {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        RealmsScreen.blit(n, n2, 0.0f, 0.0f, 60, 60, 60.0f, 60.0f);
        this.drawCenteredString(s, n + 30, n2 - 12, true ? 10526880 : 16777215);
    }
    
    void callback(final WorldTemplate worldTemplateToReset) {
        if (worldTemplateToReset != null) {
            if (this.slot != -1) {
                this.typeToReset = (worldTemplateToReset.recommendedPlayers.equals("") ? ResetType.SURVIVAL_SPAWN : ResetType.ADVENTURE);
                this.worldTemplateToReset = worldTemplateToReset;
                this.switchSlot();
            }
            else {
                this.resetWorldWithTemplate(worldTemplateToReset);
            }
        }
    }
    
    private void switchSlot() {
        this.switchSlot(this);
    }
    
    public void switchSlot(final RealmsScreen realmsScreen) {
        final RealmsLongRunningMcoTaskScreen screen = new RealmsLongRunningMcoTaskScreen(this.lastScreen, new RealmsTasks.SwitchSlotTask(this.serverData.id, this.slot, realmsScreen, 100));
        screen.start();
        Realms.setScreen(screen);
    }
    
    @Override
    public void confirmResult(final boolean b, final int n) {
        if (n == 100 && b) {
            switch (this.typeToReset) {
                case ADVENTURE:
                case SURVIVAL_SPAWN: {
                    if (this.worldTemplateToReset != null) {
                        this.resetWorldWithTemplate(this.worldTemplateToReset);
                        break;
                    }
                    break;
                }
                case GENERATE: {
                    if (this.worldInfoToReset != null) {
                        this.triggerResetWorld(this.worldInfoToReset);
                        break;
                    }
                    break;
                }
                default: {}
            }
            return;
        }
        if (b) {
            Realms.setScreen(this.returnScreen);
        }
    }
    
    public void resetWorldWithTemplate(final WorldTemplate worldTemplate) {
        final RealmsTasks.ResettingWorldTask resettingWorldTask = new RealmsTasks.ResettingWorldTask(this.serverData.id, this.returnScreen, worldTemplate);
        if (this.resetTitle != null) {
            resettingWorldTask.setResetTitle(this.resetTitle);
        }
        final RealmsLongRunningMcoTaskScreen screen = new RealmsLongRunningMcoTaskScreen(this.lastScreen, resettingWorldTask);
        screen.start();
        Realms.setScreen(screen);
    }
    
    public void resetWorld(final ResetWorldInfo worldInfoToReset) {
        if (this.slot != -1) {
            this.typeToReset = ResetType.GENERATE;
            this.worldInfoToReset = worldInfoToReset;
            this.switchSlot();
        }
        else {
            this.triggerResetWorld(worldInfoToReset);
        }
    }
    
    private void triggerResetWorld(final ResetWorldInfo resetWorldInfo) {
        final RealmsTasks.ResettingWorldTask resettingWorldTask = new RealmsTasks.ResettingWorldTask(this.serverData.id, this.returnScreen, resetWorldInfo.seed, resetWorldInfo.levelType, resetWorldInfo.generateStructures);
        if (this.resetTitle != null) {
            resettingWorldTask.setResetTitle(this.resetTitle);
        }
        final RealmsLongRunningMcoTaskScreen screen = new RealmsLongRunningMcoTaskScreen(this.lastScreen, resettingWorldTask);
        screen.start();
        Realms.setScreen(screen);
    }
    
    @Override
    void callback(final Object o) {
        this.callback((WorldTemplate)o);
    }
    
    static List access$000(final RealmsResetWorldScreen realmsResetWorldScreen) {
        return realmsResetWorldScreen.adventuremaps;
    }
    
    static List access$100(final RealmsResetWorldScreen realmsResetWorldScreen) {
        return realmsResetWorldScreen.templates;
    }
    
    static int access$202(final RealmsResetWorldScreen realmsResetWorldScreen, final int templateId) {
        return realmsResetWorldScreen.templateId = templateId;
    }
    
    static Random access$300(final RealmsResetWorldScreen realmsResetWorldScreen) {
        return realmsResetWorldScreen.random;
    }
    
    static int access$402(final RealmsResetWorldScreen realmsResetWorldScreen, final int adventureMapId) {
        return realmsResetWorldScreen.adventureMapId = adventureMapId;
    }
    
    static boolean access$502(final RealmsResetWorldScreen realmsResetWorldScreen, final boolean loaded) {
        return realmsResetWorldScreen.loaded = loaded;
    }
    
    static Logger access$600() {
        return RealmsResetWorldScreen.LOGGER;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    enum ResetType
    {
        NONE("NONE", 0), 
        GENERATE("GENERATE", 1), 
        UPLOAD("UPLOAD", 2), 
        ADVENTURE("ADVENTURE", 3), 
        SURVIVAL_SPAWN("SURVIVAL_SPAWN", 4);
        
        private static final ResetType[] $VALUES;
        
        private ResetType(final String s, final int n) {
        }
        
        static {
            $VALUES = new ResetType[] { ResetType.NONE, ResetType.GENERATE, ResetType.UPLOAD, ResetType.ADVENTURE, ResetType.SURVIVAL_SPAWN };
        }
    }
    
    public static class ResetWorldInfo
    {
        String seed;
        int levelType;
        boolean generateStructures;
        
        public ResetWorldInfo(final String seed, final int levelType, final boolean generateStructures) {
            this.seed = seed;
            this.levelType = levelType;
            this.generateStructures = generateStructures;
        }
    }
}
