package com.mojang.realmsclient.gui.screens;

import java.util.*;
import com.google.common.collect.*;
import com.mojang.realmsclient.client.*;
import com.mojang.realmsclient.*;
import com.mojang.realmsclient.dto.*;
import org.apache.logging.log4j.*;
import org.lwjgl.opengl.*;
import net.minecraft.realms.*;
import com.mojang.realmsclient.util.*;
import org.lwjgl.input.*;

public class RealmsPendingInvitesScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private static final int BUTTON_BACK_ID = 0;
    private static final String ACCEPT_ICON_LOCATION = "realms:textures/gui/realms/accept_icon.png";
    private static final String REJECT_ICON_LOCATION = "realms:textures/gui/realms/reject_icon.png";
    private final RealmsScreen lastScreen;
    private String toolTip;
    private boolean loaded;
    private PendingInvitationList pendingList;
    private List pendingInvites;
    
    public RealmsPendingInvitesScreen(final RealmsScreen lastScreen) {
        this.toolTip = null;
        this.loaded = false;
        this.pendingInvites = Lists.newArrayList();
        this.lastScreen = lastScreen;
    }
    
    @Override
    public void mouseEvent() {
        super.mouseEvent();
        this.pendingList.mouseEvent();
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.pendingList = new PendingInvitationList();
        new Thread("Realms-pending-invitations-fetcher") {
            final RealmsPendingInvitesScreen this$0;
            
            @Override
            public void run() {
                RealmsPendingInvitesScreen.access$002(this.this$0, RealmsClient.createRealmsClient().pendingInvites().pendingInvites);
                RealmsPendingInvitesScreen.access$202(this.this$0, true);
            }
        }.start();
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 75, this.height() - 32, 153, 20, RealmsScreen.getLocalizedString("gui.done")));
    }
    
    @Override
    public void tick() {
        super.tick();
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (!realmsButton.active()) {
            return;
        }
        switch (realmsButton.id()) {
            case 0: {
                Realms.setScreen(new RealmsMainScreen(this.lastScreen));
                break;
            }
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            Realms.setScreen(new RealmsMainScreen(this.lastScreen));
        }
    }
    
    private void updateList(final int n) {
        this.pendingInvites.remove(n);
    }
    
    private void reject(final int n) {
        if (n < this.pendingInvites.size()) {
            new Thread("Realms-reject-invitation", n) {
                final int val$slot;
                final RealmsPendingInvitesScreen this$0;
                
                @Override
                public void run() {
                    RealmsClient.createRealmsClient().rejectInvitation(RealmsPendingInvitesScreen.access$000(this.this$0).get(this.val$slot).invitationId);
                    RealmsPendingInvitesScreen.access$300(this.this$0, this.val$slot);
                }
            }.start();
        }
    }
    
    private void accept(final int n) {
        if (n < this.pendingInvites.size()) {
            new Thread("Realms-accept-invitation", n) {
                final int val$slot;
                final RealmsPendingInvitesScreen this$0;
                
                @Override
                public void run() {
                    RealmsClient.createRealmsClient().acceptInvitation(RealmsPendingInvitesScreen.access$000(this.this$0).get(this.val$slot).invitationId);
                    RealmsPendingInvitesScreen.access$300(this.this$0, this.val$slot);
                }
            }.start();
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.toolTip = null;
        this.renderBackground();
        this.pendingList.render(n, n2, n3);
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.invites.title"), this.width() / 2, 12, 16777215);
        if (this.toolTip != null) {
            this.renderMousehoverTooltip(this.toolTip, n, n2);
        }
        if (this.pendingInvites.size() == 0 && this.loaded) {
            this.drawCenteredString(RealmsScreen.getLocalizedString("mco.invites.nopending"), this.width() / 2, this.height() / 2 - 20, 16777215);
        }
        super.render(n, n2, n3);
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
    
    static List access$002(final RealmsPendingInvitesScreen realmsPendingInvitesScreen, final List pendingInvites) {
        return realmsPendingInvitesScreen.pendingInvites = pendingInvites;
    }
    
    static Logger access$100() {
        return RealmsPendingInvitesScreen.LOGGER;
    }
    
    static boolean access$202(final RealmsPendingInvitesScreen realmsPendingInvitesScreen, final boolean loaded) {
        return realmsPendingInvitesScreen.loaded = loaded;
    }
    
    static List access$000(final RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.pendingInvites;
    }
    
    static void access$300(final RealmsPendingInvitesScreen realmsPendingInvitesScreen, final int n) {
        realmsPendingInvitesScreen.updateList(n);
    }
    
    static String access$402(final RealmsPendingInvitesScreen realmsPendingInvitesScreen, final String toolTip) {
        return realmsPendingInvitesScreen.toolTip = toolTip;
    }
    
    static void access$500(final RealmsPendingInvitesScreen realmsPendingInvitesScreen, final int n) {
        realmsPendingInvitesScreen.accept(n);
    }
    
    static void access$600(final RealmsPendingInvitesScreen realmsPendingInvitesScreen, final int n) {
        realmsPendingInvitesScreen.reject(n);
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    private class PendingInvitationList extends RealmsClickableScrolledSelectionList
    {
        final RealmsPendingInvitesScreen this$0;
        
        public PendingInvitationList(final RealmsPendingInvitesScreen this$0) {
            this.this$0 = this$0;
            super(this$0.width() + 50, this$0.height(), 32, this$0.height() - 40, 36);
        }
        
        @Override
        public int getItemCount() {
            return RealmsPendingInvitesScreen.access$000(this.this$0).size();
        }
        
        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 36;
        }
        
        @Override
        public void renderBackground() {
            this.this$0.renderBackground();
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
        
        @Override
        public void renderItem(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            if (n < RealmsPendingInvitesScreen.access$000(this.this$0).size()) {
                this.renderPendingInvitationItem(n, n2, n3, n4);
            }
        }
        
        private void renderPendingInvitationItem(final int n, final int n2, final int n3, final int n4) {
            final PendingInvite pendingInvite = RealmsPendingInvitesScreen.access$000(this.this$0).get(n);
            this.this$0.drawString(pendingInvite.worldName, n2 + 2, n3 + 1, 16777215);
            this.this$0.drawString(pendingInvite.worldOwnerName, n2 + 2, n3 + 12, 7105644);
            this.this$0.drawString(RealmsUtil.convertToAgePresentation(System.currentTimeMillis() - pendingInvite.date.getTime()), n2 + 2, n3 + 24, 7105644);
            final int n5 = this.getScrollbarPosition() - 50;
            this.drawAccept(n5, n3, this.xm(), this.ym());
            this.drawReject(n5 + 20, n3, this.xm(), this.ym());
            RealmsScreen.bindFace(pendingInvite.worldOwnerUuid, pendingInvite.worldOwnerName);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsScreen.blit(n2 - 36, n3, 8.0f, 8.0f, 8, 8, 32, 32, 64.0f, 64.0f);
            RealmsScreen.blit(n2 - 36, n3, 40.0f, 8.0f, 8, 8, 32, 32, 64.0f, 64.0f);
        }
        
        private void drawAccept(final int n, final int n2, final int n3, final int n4) {
            if (n3 < n || n3 > n + 15 || n4 < n2 || n4 > n2 + 15 || n4 >= this.this$0.height() - 40 || n4 > 32) {}
            RealmsScreen.bind("realms:textures/gui/realms/accept_icon.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsScreen.blit(n, n2, true ? 19.0f : 0.0f, 0.0f, 18, 18, 37.0f, 18.0f);
            if (true) {
                RealmsPendingInvitesScreen.access$402(this.this$0, RealmsScreen.getLocalizedString("mco.invites.button.accept"));
            }
        }
        
        private void drawReject(final int n, final int n2, final int n3, final int n4) {
            if (n3 < n || n3 > n + 15 || n4 < n2 || n4 > n2 + 15 || n4 >= this.this$0.height() - 40 || n4 > 32) {}
            RealmsScreen.bind("realms:textures/gui/realms/reject_icon.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsScreen.blit(n, n2, true ? 19.0f : 0.0f, 0.0f, 18, 18, 37.0f, 18.0f);
            if (true) {
                RealmsPendingInvitesScreen.access$402(this.this$0, RealmsScreen.getLocalizedString("mco.invites.button.reject"));
            }
        }
        
        @Override
        public void itemClicked(final int n, final int n2, final int n3, final int n4, final int n5) {
            final int n6 = this.getScrollbarPosition() - 50;
            final int n7 = n + 30 - this.getScroll();
            if (n3 >= n6 && n3 <= n6 + 15 && n4 >= n7 && n4 <= n7 + 15) {
                RealmsPendingInvitesScreen.access$500(this.this$0, n2);
            }
            else if (n3 >= n6 + 20 && n3 <= n6 + 20 + 15 && n4 >= n7 && n4 <= n7 + 15) {
                RealmsPendingInvitesScreen.access$600(this.this$0, n2);
            }
        }
        
        @Override
        public void customMouseEvent(final int n, final int n2, final int n3, final float n4, final int n5) {
            if (Mouse.isButtonDown(0) && this.ym() >= n && this.ym() <= n2) {
                final int n6 = this.width() / 2 - 92;
                final int width = this.width();
                final int n7 = this.ym() - n - n3 + (int)n4 - 4;
                final int n8 = n7 / n5;
                if (this.xm() >= n6 && this.xm() <= width && n8 >= 0 && n7 >= 0 && n8 < this.getItemCount()) {
                    this.itemClicked(n7, n8, this.xm(), this.ym(), this.width());
                }
            }
        }
    }
}
