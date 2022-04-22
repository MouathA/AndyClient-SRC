package viamcp.gui;

import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import viamcp.*;
import viamcp.protocols.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class GuiProtocolSelector extends GuiScreen
{
    private GuiScreen parent;
    public SlotList list;
    
    public GuiProtocolSelector(final GuiScreen parent) {
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, GuiProtocolSelector.width / 2 - 100, GuiProtocolSelector.height - 25, 200, 20, "Back"));
        this.buttonList.add(new GuiButton(2, GuiProtocolSelector.width / 2 - 180, GuiProtocolSelector.height - 25, 75, 20, "Credits"));
        this.list = new SlotList(GuiProtocolSelector.mc, GuiProtocolSelector.width, GuiProtocolSelector.height, 32, GuiProtocolSelector.height - 32, 10);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        this.list.actionPerformed(guiButton);
        if (guiButton.id == 1) {
            GuiProtocolSelector.mc.displayGuiScreen(this.parent);
        }
        if (guiButton.id == 2) {
            GuiProtocolSelector.mc.displayGuiScreen(new GuiCredits(this));
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        this.list.func_178039_p();
        super.handleMouseInput();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.list.drawScreen(n, n2, n3);
        GlStateManager.scale(2.0, 2.0, 2.0);
        final String string = EnumChatFormatting.BOLD + "ViaMCP Reborn";
        this.drawString(this.fontRendererObj, string, (GuiProtocolSelector.width - this.fontRendererObj.getStringWidth(string) * 2) / 4, 5, -1);
        final String name = ProtocolCollection.getProtocolById(ViaMCP.getInstance().getVersion()).getName();
        final String name2 = ProtocolCollection.getProtocolInfoById(ViaMCP.getInstance().getVersion()).getName();
        final String releaseDate = ProtocolCollection.getProtocolInfoById(ViaMCP.getInstance().getVersion()).getReleaseDate();
        final String string2 = "Version: " + name + " - " + name2;
        final String string3 = "Released: " + releaseDate;
        final int n4 = (5 + this.fontRendererObj.FONT_HEIGHT) * 2 + 2;
        this.drawString(this.fontRendererObj, new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("Version Information").toString(), (GuiProtocolSelector.width - this.fontRendererObj.getStringWidth("Version Information")) / 2, n4, -1);
        this.drawString(this.fontRendererObj, string2, (GuiProtocolSelector.width - this.fontRendererObj.getStringWidth(string2)) / 2, n4 + this.fontRendererObj.FONT_HEIGHT, -1);
        this.drawString(this.fontRendererObj, string3, (GuiProtocolSelector.width - this.fontRendererObj.getStringWidth(string3)) / 2, n4 + this.fontRendererObj.FONT_HEIGHT * 2, -1);
        super.drawScreen(n, n2, n3);
    }
    
    class SlotList extends GuiSlot
    {
        final GuiProtocolSelector this$0;
        
        public SlotList(final GuiProtocolSelector this$0, final Minecraft minecraft, final int n, final int n2, final int n3, final int n4, final int n5) {
            this.this$0 = this$0;
            super(minecraft, n, n2, n3 + 30, n4, 18);
        }
        
        @Override
        protected int getSize() {
            return ProtocolCollection.values().length;
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
            final int version = ProtocolCollection.values()[n].getVersion().getVersion();
            ViaMCP.getInstance().setVersion(version);
            ViaMCP.getInstance().asyncSlider.setVersion(version);
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return false;
        }
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            Gui.drawCenteredString(Minecraft.fontRendererObj, String.valueOf((ViaMCP.getInstance().getVersion() == ProtocolCollection.values()[n].getVersion().getVersion()) ? new StringBuilder(String.valueOf(EnumChatFormatting.GREEN.toString())).append(EnumChatFormatting.BOLD).toString() : EnumChatFormatting.GRAY.toString()) + ProtocolCollection.getProtocolById(ProtocolCollection.values()[n].getVersion().getVersion()).getName(), this.width / 2, n3 + 2, -1);
            GlStateManager.scale(0.5, 0.5, 0.5);
            Gui.drawCenteredString(Minecraft.fontRendererObj, "PVN: " + ProtocolCollection.getProtocolById(ProtocolCollection.values()[n].getVersion().getVersion()).getVersion(), this.width, (n3 + 2) * 2 + 20, -1);
        }
    }
}
