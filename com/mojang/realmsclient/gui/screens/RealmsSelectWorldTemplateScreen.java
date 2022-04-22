package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.*;
import java.util.*;
import com.mojang.realmsclient.client.*;
import com.mojang.realmsclient.gui.*;
import org.apache.logging.log4j.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import net.minecraft.realms.*;
import com.mojang.realmsclient.util.*;

public class RealmsSelectWorldTemplateScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private static final String LINK_ICON = "realms:textures/gui/realms/link_icons.png";
    private static final String TRAILER_ICON = "realms:textures/gui/realms/trailer_icons.png";
    private static final String SLOT_FRAME_LOCATION = "realms:textures/gui/realms/slot_frame.png";
    private final RealmsScreenWithCallback lastScreen;
    private WorldTemplate selectedWorldTemplate;
    private List templates;
    private WorldTemplateSelectionList worldTemplateSelectionList;
    private int selectedTemplate;
    private String title;
    private static final int BUTTON_BACK_ID = 0;
    private static final int BUTTON_SELECT_ID = 1;
    private RealmsButton selectButton;
    private String toolTip;
    private String currentLink;
    private boolean isMiniGame;
    private boolean displayWarning;
    private int clicks;
    
    public RealmsSelectWorldTemplateScreen(final RealmsScreenWithCallback lastScreen, final WorldTemplate selectedWorldTemplate, final boolean isMiniGame) {
        this.templates = Collections.emptyList();
        this.selectedTemplate = -1;
        this.toolTip = null;
        this.currentLink = null;
        this.displayWarning = false;
        this.clicks = 0;
        this.lastScreen = lastScreen;
        this.selectedWorldTemplate = selectedWorldTemplate;
        this.isMiniGame = isMiniGame;
        this.title = (isMiniGame ? RealmsScreen.getLocalizedString("mco.template.title.minigame") : RealmsScreen.getLocalizedString("mco.template.title"));
    }
    
    public RealmsSelectWorldTemplateScreen(final RealmsScreenWithCallback realmsScreenWithCallback, final WorldTemplate worldTemplate, final boolean b, final boolean displayWarning) {
        this(realmsScreenWithCallback, worldTemplate, b);
        this.displayWarning = displayWarning;
    }
    
    public RealmsSelectWorldTemplateScreen(final RealmsScreenWithCallback realmsScreenWithCallback, final WorldTemplate worldTemplate, final boolean b, final boolean b2, final List templates) {
        this(realmsScreenWithCallback, worldTemplate, b, b2);
        this.templates = templates;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    @Override
    public void mouseEvent() {
        super.mouseEvent();
        this.worldTemplateSelectionList.mouseEvent();
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.worldTemplateSelectionList = new WorldTemplateSelectionList();
        if (this.templates.size() == 0) {
            new Thread("Realms-minigame-fetcher", this.isMiniGame) {
                final boolean val$isMiniGame;
                final RealmsSelectWorldTemplateScreen this$0;
                
                @Override
                public void run() {
                    final RealmsClient realmsClient = RealmsClient.createRealmsClient();
                    if (this.val$isMiniGame) {
                        RealmsSelectWorldTemplateScreen.access$002(this.this$0, realmsClient.fetchMinigames().templates);
                    }
                    else {
                        RealmsSelectWorldTemplateScreen.access$002(this.this$0, realmsClient.fetchWorldTemplates().templates);
                    }
                }
            }.start();
        }
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 + 6, this.height() - 32, 153, 20, this.isMiniGame ? RealmsScreen.getLocalizedString("gui.cancel") : RealmsScreen.getLocalizedString("gui.back")));
        this.buttonsAdd(this.selectButton = RealmsScreen.newButton(1, this.width() / 2 - 154, this.height() - 32, 153, 20, RealmsScreen.getLocalizedString("mco.template.button.select")));
        this.selectButton.active(false);
    }
    
    @Override
    public void tick() {
        super.tick();
        --this.clicks;
        if (this.clicks < 0) {
            this.clicks = 0;
        }
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (!realmsButton.active()) {
            return;
        }
        switch (realmsButton.id()) {
            case 0: {
                this.backButtonClicked();
                break;
            }
            case 1: {
                this.selectTemplate();
                break;
            }
            default: {}
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            this.backButtonClicked();
        }
    }
    
    private void backButtonClicked() {
        this.lastScreen.callback(null);
        Realms.setScreen(this.lastScreen);
    }
    
    private void selectTemplate() {
        if (this.selectedTemplate >= 0 && this.selectedTemplate < this.templates.size()) {
            final WorldTemplate worldTemplate = this.templates.get(this.selectedTemplate);
            worldTemplate.setMinigame(this.isMiniGame);
            this.lastScreen.callback(worldTemplate);
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.toolTip = null;
        this.currentLink = null;
        this.renderBackground();
        this.worldTemplateSelectionList.render(n, n2, n3);
        this.drawCenteredString(this.title, this.width() / 2, 13, 16777215);
        if (this.displayWarning) {
            this.drawCenteredString(RealmsScreen.getLocalizedString("mco.minigame.world.info1"), this.width() / 2, RealmsConstants.row(-1), 10526880);
            this.drawCenteredString(RealmsScreen.getLocalizedString("mco.minigame.world.info2"), this.width() / 2, RealmsConstants.row(0), 10526880);
        }
        super.render(n, n2, n3);
        if (this.toolTip != null) {
            this.renderMousehoverTooltip(this.toolTip, n, n2);
        }
    }
    
    protected void renderMousehoverTooltip(final String s, final int n, final int n2) {
        if (s == null) {
            return;
        }
        final int n3 = n + 12;
        final int n4 = n2 - 12;
        this.fillGradient(n3 - 3, n4 - 3, n3 + this.fontWidth(s) + 3, n4 + 8 + 3, -1073741824, -1073741824);
        this.fontDrawShadow(s, n3, n4, 16777215);
    }
    
    static List access$002(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen, final List templates) {
        return realmsSelectWorldTemplateScreen.templates = templates;
    }
    
    static Logger access$100() {
        return RealmsSelectWorldTemplateScreen.LOGGER;
    }
    
    static boolean access$200(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.displayWarning;
    }
    
    static List access$000(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.templates;
    }
    
    static RealmsButton access$300(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.selectButton;
    }
    
    static int access$402(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen, final int selectedTemplate) {
        return realmsSelectWorldTemplateScreen.selectedTemplate = selectedTemplate;
    }
    
    static WorldTemplate access$502(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen, final WorldTemplate selectedWorldTemplate) {
        return realmsSelectWorldTemplateScreen.selectedWorldTemplate = selectedWorldTemplate;
    }
    
    static int access$612(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen, final int n) {
        return realmsSelectWorldTemplateScreen.clicks += n;
    }
    
    static int access$600(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.clicks;
    }
    
    static void access$700(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        realmsSelectWorldTemplateScreen.selectTemplate();
    }
    
    static WorldTemplate access$500(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.selectedWorldTemplate;
    }
    
    static int access$400(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.selectedTemplate;
    }
    
    static String access$800(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.currentLink;
    }
    
    static String access$902(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen, final String toolTip) {
        return realmsSelectWorldTemplateScreen.toolTip = toolTip;
    }
    
    static String access$802(final RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen, final String currentLink) {
        return realmsSelectWorldTemplateScreen.currentLink = currentLink;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    private class WorldTemplateSelectionList extends RealmsClickableScrolledSelectionList
    {
        final RealmsSelectWorldTemplateScreen this$0;
        
        public WorldTemplateSelectionList(final RealmsSelectWorldTemplateScreen this$0) {
            this.this$0 = this$0;
            super(this$0.width(), this$0.height(), RealmsSelectWorldTemplateScreen.access$200(this$0) ? RealmsConstants.row(1) : 32, this$0.height() - 40, 46);
        }
        
        @Override
        public int getItemCount() {
            return RealmsSelectWorldTemplateScreen.access$000(this.this$0).size();
        }
        
        @Override
        public void customMouseEvent(final int n, final int n2, final int n3, final float n4, final int n5) {
            if (Mouse.isButtonDown(0) && this.ym() >= n && this.ym() <= n2) {
                final int n6 = this.width() / 2 - 150;
                final int width = this.width();
                final int n7 = this.ym() - n - n3 + (int)n4 - 4;
                final int n8 = n7 / n5;
                if (this.xm() >= n6 && this.xm() <= width && n8 >= 0 && n7 >= 0 && n8 < this.getItemCount()) {
                    this.itemClicked(n7, n8, this.xm(), this.ym(), this.width());
                    if (n8 >= RealmsSelectWorldTemplateScreen.access$000(this.this$0).size()) {
                        return;
                    }
                    RealmsSelectWorldTemplateScreen.access$300(this.this$0).active(true);
                    RealmsSelectWorldTemplateScreen.access$402(this.this$0, n8);
                    RealmsSelectWorldTemplateScreen.access$502(this.this$0, null);
                    RealmsSelectWorldTemplateScreen.access$612(this.this$0, RealmsSharedConstants.TICKS_PER_SECOND / 3 + 1);
                    if (RealmsSelectWorldTemplateScreen.access$600(this.this$0) >= RealmsSharedConstants.TICKS_PER_SECOND / 2) {
                        RealmsSelectWorldTemplateScreen.access$700(this.this$0);
                    }
                }
            }
        }
        
        @Override
        public boolean isSelectedItem(final int n) {
            if (RealmsSelectWorldTemplateScreen.access$000(this.this$0).size() == 0) {
                return false;
            }
            if (n >= RealmsSelectWorldTemplateScreen.access$000(this.this$0).size()) {
                return false;
            }
            if (RealmsSelectWorldTemplateScreen.access$500(this.this$0) != null) {
                final boolean equals = RealmsSelectWorldTemplateScreen.access$500(this.this$0).name.equals(RealmsSelectWorldTemplateScreen.access$000(this.this$0).get(n).name);
                if (equals) {
                    RealmsSelectWorldTemplateScreen.access$402(this.this$0, n);
                }
                return equals;
            }
            return n == RealmsSelectWorldTemplateScreen.access$400(this.this$0);
        }
        
        @Override
        public void itemClicked(final int n, final int n2, final int n3, final int n4, final int n5) {
            if (n2 >= RealmsSelectWorldTemplateScreen.access$000(this.this$0).size()) {
                return;
            }
            if (RealmsSelectWorldTemplateScreen.access$800(this.this$0) != null) {
                RealmsUtil.browseTo(RealmsSelectWorldTemplateScreen.access$800(this.this$0));
            }
        }
        
        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 46;
        }
        
        @Override
        public void renderBackground() {
            this.this$0.renderBackground();
        }
        
        @Override
        public void renderItem(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            if (n < RealmsSelectWorldTemplateScreen.access$000(this.this$0).size()) {
                this.renderWorldTemplateItem(n, n2, n3, n4);
            }
        }
        
        @Override
        public int getScrollbarPosition() {
            return super.getScrollbarPosition() + 30;
        }
        
        @Override
        public void renderSelected(final int n, final int n2, final int n3, final Tezzelator tezzelator) {
            final int n4 = this.getScrollbarPosition() - 290;
            final int n5 = this.getScrollbarPosition() - 10;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3553);
            tezzelator.begin(7, RealmsDefaultVertexFormat.POSITION_TEX_COLOR);
            tezzelator.vertex((double)n4, (double)(n2 + n3 + 2), 0.0).tex(0.0, 1.0).color(128, 128, 128, 255).endVertex();
            tezzelator.vertex((double)n5, (double)(n2 + n3 + 2), 0.0).tex(1.0, 1.0).color(128, 128, 128, 255).endVertex();
            tezzelator.vertex((double)n5, (double)(n2 - 2), 0.0).tex(1.0, 0.0).color(128, 128, 128, 255).endVertex();
            tezzelator.vertex((double)n4, (double)(n2 - 2), 0.0).tex(0.0, 0.0).color(128, 128, 128, 255).endVertex();
            tezzelator.vertex((double)(n4 + 1), (double)(n2 + n3 + 1), 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
            tezzelator.vertex((double)(n5 - 1), (double)(n2 + n3 + 1), 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
            tezzelator.vertex((double)(n5 - 1), (double)(n2 - 1), 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
            tezzelator.vertex((double)(n4 + 1), (double)(n2 - 1), 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
            tezzelator.end();
            GL11.glEnable(3553);
        }
        
        private void renderWorldTemplateItem(final int n, final int n2, final int n3, final int n4) {
            final WorldTemplate worldTemplate = RealmsSelectWorldTemplateScreen.access$000(this.this$0).get(n);
            final int n5 = n2 + 20;
            this.this$0.drawString(worldTemplate.name, n5, n3 + 2, 16777215);
            this.this$0.drawString(worldTemplate.author, n5, n3 + 15, 7105644);
            this.this$0.drawString(worldTemplate.version, n5 + 227 - this.this$0.fontWidth(worldTemplate.version), n3 + 1, 7105644);
            if (!worldTemplate.link.equals("") || !worldTemplate.trailer.equals("") || !worldTemplate.recommendedPlayers.equals("")) {
                this.drawIcons(n5 - 1, n3 + 25, this.xm(), this.ym(), worldTemplate.link, worldTemplate.trailer, worldTemplate.recommendedPlayers);
            }
            this.drawImage(n2 - 25, n3 + 1, this.xm(), this.ym(), worldTemplate);
        }
        
        private void drawImage(final int n, final int n2, final int n3, final int n4, final WorldTemplate worldTemplate) {
            RealmsTextureManager.bindWorldTemplate(worldTemplate.id, worldTemplate.image);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsScreen.blit(n + 1, n2 + 1, 0.0f, 0.0f, 38, 38, 38.0f, 38.0f);
            RealmsScreen.bind("realms:textures/gui/realms/slot_frame.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsScreen.blit(n, n2, 0.0f, 0.0f, 40, 40, 40.0f, 40.0f);
        }
        
        private void drawIcons(final int n, final int n2, final int n3, final int n4, final String s, final String s2, final String s3) {
            if (!s3.equals("")) {
                this.this$0.drawString(s3, n, n2 + 4, 5000268);
            }
            final int n5 = s3.equals("") ? 0 : (this.this$0.fontWidth(s3) + 2);
            if (n3 >= n + n5 && n3 <= n + n5 + 32 && n4 >= n2 && n4 <= n2 + 15 && n4 < this.this$0.height() - 15 && n4 > 32) {
                if (n3 <= n + 15 + n5 && n3 > n5) {
                    if (!s.equals("")) {}
                }
                else if (!s.equals("")) {}
            }
            if (!s.equals("")) {
                RealmsScreen.bind("realms:textures/gui/realms/link_icons.png");
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                RealmsScreen.blit(n + n5, n2, true ? 15.0f : 0.0f, 0.0f, 15, 15, 30.0f, 15.0f);
            }
            if (!s2.equals("")) {
                RealmsScreen.bind("realms:textures/gui/realms/trailer_icons.png");
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                RealmsScreen.blit(n + n5 + (s.equals("") ? 0 : 17), n2, true ? 15.0f : 0.0f, 0.0f, 15, 15, 30.0f, 15.0f);
            }
            if (true && !s.equals("")) {
                RealmsSelectWorldTemplateScreen.access$902(this.this$0, RealmsScreen.getLocalizedString("mco.template.info.tooltip"));
                RealmsSelectWorldTemplateScreen.access$802(this.this$0, s);
            }
            else if (true && !s2.equals("")) {
                RealmsSelectWorldTemplateScreen.access$902(this.this$0, RealmsScreen.getLocalizedString("mco.template.trailer.tooltip"));
                RealmsSelectWorldTemplateScreen.access$802(this.this$0, s2);
            }
        }
    }
}
