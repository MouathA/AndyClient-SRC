package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.gui.*;
import com.mojang.realmsclient.client.*;
import com.mojang.realmsclient.dto.*;
import java.util.*;
import org.lwjgl.opengl.*;
import org.apache.logging.log4j.*;
import net.minecraft.realms.*;
import org.lwjgl.input.*;

public class RealmsPlayerScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private static final String OP_ICON_LOCATION = "realms:textures/gui/realms/op_icon.png";
    private static final String USER_ICON_LOCATION = "realms:textures/gui/realms/user_icon.png";
    private static final String CROSS_ICON_LOCATION = "realms:textures/gui/realms/cross_icon.png";
    private String toolTip;
    private final RealmsConfigureWorldScreen lastScreen;
    private RealmsServer serverData;
    private InvitedSelectionList invitedSelectionList;
    private int column1_x;
    private int column_width;
    private int column2_x;
    private static final int BUTTON_BACK_ID = 0;
    private static final int BUTTON_INVITE_ID = 1;
    private static final int BUTTON_UNINVITE_ID = 2;
    private static final int BUTTON_ACTIVITY_ID = 3;
    private RealmsButton inviteButton;
    private RealmsButton activityButton;
    private int selectedInvitedIndex;
    private String selectedInvited;
    private boolean stateChanged;
    
    public RealmsPlayerScreen(final RealmsConfigureWorldScreen lastScreen, final RealmsServer serverData) {
        this.selectedInvitedIndex = -1;
        this.lastScreen = lastScreen;
        this.serverData = serverData;
    }
    
    @Override
    public void mouseEvent() {
        super.mouseEvent();
        if (this.invitedSelectionList != null) {
            this.invitedSelectionList.mouseEvent();
        }
    }
    
    @Override
    public void tick() {
        super.tick();
    }
    
    @Override
    public void init() {
        this.column1_x = this.width() / 2 - 160;
        this.column_width = 150;
        this.column2_x = this.width() / 2 + 12;
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.buttonsAdd(this.inviteButton = RealmsScreen.newButton(1, this.column2_x, RealmsConstants.row(1), this.column_width + 10, 20, RealmsScreen.getLocalizedString("mco.configure.world.buttons.invite")));
        this.buttonsAdd(this.activityButton = RealmsScreen.newButton(3, this.column2_x, RealmsConstants.row(3), this.column_width + 10, 20, RealmsScreen.getLocalizedString("mco.configure.world.buttons.activity")));
        this.buttonsAdd(RealmsScreen.newButton(0, this.column2_x + this.column_width / 2 + 2, RealmsConstants.row(12), this.column_width / 2 + 10 - 2, 20, RealmsScreen.getLocalizedString("gui.back")));
        (this.invitedSelectionList = new InvitedSelectionList()).setLeftPos(this.column1_x);
        this.inviteButton.active(false);
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
        switch (realmsButton.id()) {
            case 0: {
                this.backButtonClicked();
                break;
            }
            case 1: {
                Realms.setScreen(new RealmsInviteScreen(this.lastScreen, this, this.serverData));
                break;
            }
            case 3: {
                Realms.setScreen(new RealmsActivityScreen(this, this.serverData));
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
        if (this.stateChanged) {
            Realms.setScreen(this.lastScreen.getNewScreen());
        }
        else {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    private void op(final int n) {
        this.updateOps(RealmsClient.createRealmsClient().op(this.serverData.id, this.serverData.players.get(n).getName()));
    }
    
    private void deop(final int n) {
        this.updateOps(RealmsClient.createRealmsClient().deop(this.serverData.id, this.serverData.players.get(n).getName()));
    }
    
    private void updateOps(final Ops ops) {
        for (final PlayerInfo playerInfo : this.serverData.players) {
            playerInfo.setOperator(ops.ops.contains(playerInfo.getName()));
        }
    }
    
    private void uninvite(final int selectedInvitedIndex) {
        if (selectedInvitedIndex >= 0 && selectedInvitedIndex < this.serverData.players.size()) {
            final PlayerInfo playerInfo = this.serverData.players.get(selectedInvitedIndex);
            this.selectedInvited = playerInfo.getUuid();
            this.selectedInvitedIndex = selectedInvitedIndex;
            Realms.setScreen(new RealmsConfirmScreen(this, "Question", RealmsScreen.getLocalizedString("mco.configure.world.uninvite.question") + " '" + playerInfo.getName() + "' ?", 2));
        }
    }
    
    @Override
    public void confirmResult(final boolean b, final int n) {
        if (n == 2) {
            if (b) {
                RealmsClient.createRealmsClient().uninvite(this.serverData.id, this.selectedInvited);
                this.deleteFromInvitedList(this.selectedInvitedIndex);
            }
            this.stateChanged = true;
            Realms.setScreen(this);
        }
    }
    
    private void deleteFromInvitedList(final int n) {
        this.serverData.players.remove(n);
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.toolTip = null;
        this.renderBackground();
        if (this.invitedSelectionList != null) {
            this.invitedSelectionList.render(n, n2, n3);
        }
        final int n4 = RealmsConstants.row(12) + 20;
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        final Tezzelator instance = Tezzelator.instance;
        RealmsScreen.bind("textures/gui/options_background.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        instance.begin(7, RealmsDefaultVertexFormat.POSITION_TEX_COLOR);
        instance.vertex(0.0, (double)this.height(), 0.0).tex(0.0, (double)((this.height() - n4) / 32.0f + 0.0f)).color(64, 64, 64, 255).endVertex();
        instance.vertex((double)this.width(), (double)this.height(), 0.0).tex((double)(this.width() / 32.0f), (double)((this.height() - n4) / 32.0f + 0.0f)).color(64, 64, 64, 255).endVertex();
        instance.vertex((double)this.width(), (double)n4, 0.0).tex((double)(this.width() / 32.0f), 0.0).color(64, 64, 64, 255).endVertex();
        instance.vertex(0.0, (double)n4, 0.0).tex(0.0, 0.0).color(64, 64, 64, 255).endVertex();
        instance.end();
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.configure.world.players.title"), this.width() / 2, 17, 16777215);
        if (this.serverData != null && this.serverData.players != null) {
            this.drawString(RealmsScreen.getLocalizedString("mco.configure.world.invited") + " (" + this.serverData.players.size() + ")", this.column1_x, RealmsConstants.row(0), 10526880);
            this.inviteButton.active(this.serverData.players.size() < 200);
        }
        else {
            this.drawString(RealmsScreen.getLocalizedString("mco.configure.world.invited"), this.column1_x, RealmsConstants.row(0), 10526880);
            this.inviteButton.active(false);
        }
        super.render(n, n2, n3);
        if (this.serverData == null) {
            return;
        }
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
    
    private void drawRemoveIcon(final int n, final int n2, final int n3, final int n4) {
        final boolean b = n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9 && n4 < this.height() - 25 && n4 > RealmsConstants.row(1);
        RealmsScreen.bind("realms:textures/gui/realms/cross_icon.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RealmsScreen.blit(n, n2, 0.0f, b ? 7.0f : 0.0f, 8, 7, 8.0f, 14.0f);
        if (b) {
            this.toolTip = RealmsScreen.getLocalizedString("mco.configure.world.invites.remove.tooltip");
        }
    }
    
    private void drawOpped(final int n, final int n2, final int n3, final int n4) {
        final boolean b = n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9 && n4 < this.height() - 25 && n4 > RealmsConstants.row(1);
        RealmsScreen.bind("realms:textures/gui/realms/op_icon.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RealmsScreen.blit(n, n2, 0.0f, b ? 8.0f : 0.0f, 8, 8, 8.0f, 16.0f);
        if (b) {
            this.toolTip = RealmsScreen.getLocalizedString("mco.configure.world.invites.ops.tooltip");
        }
    }
    
    private void drawNormal(final int n, final int n2, final int n3, final int n4) {
        final boolean b = n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9;
        RealmsScreen.bind("realms:textures/gui/realms/user_icon.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RealmsScreen.blit(n, n2, 0.0f, b ? 8.0f : 0.0f, 8, 8, 8.0f, 16.0f);
        if (b) {
            this.toolTip = RealmsScreen.getLocalizedString("mco.configure.world.invites.normal.tooltip");
        }
    }
    
    static int access$000(final RealmsPlayerScreen realmsPlayerScreen) {
        return realmsPlayerScreen.column_width;
    }
    
    static int access$100(final RealmsPlayerScreen realmsPlayerScreen) {
        return realmsPlayerScreen.column1_x;
    }
    
    static RealmsServer access$200(final RealmsPlayerScreen realmsPlayerScreen) {
        return realmsPlayerScreen.serverData;
    }
    
    static String access$300(final RealmsPlayerScreen realmsPlayerScreen) {
        return realmsPlayerScreen.toolTip;
    }
    
    static void access$400(final RealmsPlayerScreen realmsPlayerScreen, final int n) {
        realmsPlayerScreen.deop(n);
    }
    
    static void access$500(final RealmsPlayerScreen realmsPlayerScreen, final int n) {
        realmsPlayerScreen.op(n);
    }
    
    static void access$600(final RealmsPlayerScreen realmsPlayerScreen, final int n) {
        realmsPlayerScreen.uninvite(n);
    }
    
    static void access$700(final RealmsPlayerScreen realmsPlayerScreen, final int n, final int n2, final int n3, final int n4) {
        realmsPlayerScreen.drawOpped(n, n2, n3, n4);
    }
    
    static void access$800(final RealmsPlayerScreen realmsPlayerScreen, final int n, final int n2, final int n3, final int n4) {
        realmsPlayerScreen.drawNormal(n, n2, n3, n4);
    }
    
    static void access$900(final RealmsPlayerScreen realmsPlayerScreen, final int n, final int n2, final int n3, final int n4) {
        realmsPlayerScreen.drawRemoveIcon(n, n2, n3, n4);
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    private class InvitedSelectionList extends RealmsClickableScrolledSelectionList
    {
        final RealmsPlayerScreen this$0;
        
        public InvitedSelectionList(final RealmsPlayerScreen this$0) {
            this.this$0 = this$0;
            super(RealmsPlayerScreen.access$000(this$0) + 10, RealmsConstants.row(12) + 20, RealmsConstants.row(1), RealmsConstants.row(12) + 20, 13);
        }
        
        @Override
        public void customMouseEvent(final int n, final int n2, final int n3, final float n4, final int n5) {
            if (Mouse.isButtonDown(0) && this.ym() >= n && this.ym() <= n2) {
                final int access$100 = RealmsPlayerScreen.access$100(this.this$0);
                final int n6 = RealmsPlayerScreen.access$100(this.this$0) + RealmsPlayerScreen.access$000(this.this$0);
                final int n7 = this.ym() - n - n3 + (int)n4 - 4;
                final int n8 = n7 / n5;
                if (this.xm() >= access$100 && this.xm() <= n6 && n8 >= 0 && n7 >= 0 && n8 < this.getItemCount()) {
                    this.itemClicked(n7, n8, this.xm(), this.ym(), this.width());
                }
            }
        }
        
        @Override
        public void itemClicked(final int n, final int n2, final int n3, final int n4, final int n5) {
            if (n2 < 0 || n2 > RealmsPlayerScreen.access$200(this.this$0).players.size() || RealmsPlayerScreen.access$300(this.this$0) == null) {
                return;
            }
            if (RealmsPlayerScreen.access$300(this.this$0).equals(RealmsScreen.getLocalizedString("mco.configure.world.invites.ops.tooltip")) || RealmsPlayerScreen.access$300(this.this$0).equals(RealmsScreen.getLocalizedString("mco.configure.world.invites.normal.tooltip"))) {
                if (RealmsPlayerScreen.access$200(this.this$0).players.get(n2).isOperator()) {
                    RealmsPlayerScreen.access$400(this.this$0, n2);
                }
                else {
                    RealmsPlayerScreen.access$500(this.this$0, n2);
                }
            }
            else if (RealmsPlayerScreen.access$300(this.this$0).equals(RealmsScreen.getLocalizedString("mco.configure.world.invites.remove.tooltip"))) {
                RealmsPlayerScreen.access$600(this.this$0, n2);
            }
        }
        
        @Override
        public void renderBackground() {
            this.this$0.renderBackground();
        }
        
        @Override
        public int getScrollbarPosition() {
            return RealmsPlayerScreen.access$100(this.this$0) + this.width() - 5;
        }
        
        @Override
        public int getItemCount() {
            return (RealmsPlayerScreen.access$200(this.this$0) == null) ? 1 : RealmsPlayerScreen.access$200(this.this$0).players.size();
        }
        
        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 13;
        }
        
        @Override
        protected void renderItem(final int n, final int n2, final int n3, final int n4, final Tezzelator tezzelator, final int n5, final int n6) {
            if (RealmsPlayerScreen.access$200(this.this$0) == null) {
                return;
            }
            if (n < RealmsPlayerScreen.access$200(this.this$0).players.size()) {
                this.renderInvitedItem(n, n2, n3, n4);
            }
        }
        
        private void renderInvitedItem(final int n, final int n2, final int n3, final int n4) {
            final PlayerInfo playerInfo = RealmsPlayerScreen.access$200(this.this$0).players.get(n);
            this.this$0.drawString(playerInfo.getName(), RealmsPlayerScreen.access$100(this.this$0) + 3 + 12, n3 + 1, playerInfo.getAccepted() ? 16777215 : 10526880);
            if (playerInfo.isOperator()) {
                RealmsPlayerScreen.access$700(this.this$0, RealmsPlayerScreen.access$100(this.this$0) + RealmsPlayerScreen.access$000(this.this$0) - 10, n3 + 1, this.xm(), this.ym());
            }
            else {
                RealmsPlayerScreen.access$800(this.this$0, RealmsPlayerScreen.access$100(this.this$0) + RealmsPlayerScreen.access$000(this.this$0) - 10, n3 + 1, this.xm(), this.ym());
            }
            RealmsPlayerScreen.access$900(this.this$0, RealmsPlayerScreen.access$100(this.this$0) + RealmsPlayerScreen.access$000(this.this$0) - 22, n3 + 2, this.xm(), this.ym());
            RealmsScreen.bindFace(playerInfo.getUuid(), playerInfo.getName());
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsScreen.blit(RealmsPlayerScreen.access$100(this.this$0) + 2 + 2, n3 + 1, 8.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
            RealmsScreen.blit(RealmsPlayerScreen.access$100(this.this$0) + 2 + 2, n3 + 1, 40.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
        }
    }
}
