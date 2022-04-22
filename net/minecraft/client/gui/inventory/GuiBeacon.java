package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.potion.*;

public class GuiBeacon extends GuiContainer
{
    private static final Logger logger;
    private static final ResourceLocation beaconGuiTextures;
    private IInventory tileBeacon;
    private ConfirmButton beaconConfirmButton;
    private boolean buttonsNotDrawn;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000739";
        logger = LogManager.getLogger();
        beaconGuiTextures = new ResourceLocation("textures/gui/container/beacon.png");
    }
    
    public GuiBeacon(final InventoryPlayer inventoryPlayer, final IInventory tileBeacon) {
        super(new ContainerBeacon(inventoryPlayer, tileBeacon));
        this.tileBeacon = tileBeacon;
        this.xSize = 230;
        this.ySize = 219;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(this.beaconConfirmButton = new ConfirmButton(-1, this.guiLeft + 164, this.guiTop + 107));
        this.buttonList.add(new CancelButton(-2, this.guiLeft + 190, this.guiTop + 107));
        this.buttonsNotDrawn = true;
        this.beaconConfirmButton.enabled = false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final int field = this.tileBeacon.getField(0);
        final int field2 = this.tileBeacon.getField(1);
        final int field3 = this.tileBeacon.getField(2);
        if (this.buttonsNotDrawn && field >= 0) {
            this.buttonsNotDrawn = false;
            int n2 = 0;
            while (3 <= 2) {
                final int length = TileEntityBeacon.effectsList[3].length;
                final int n = length * 22 + (length - 1) * 2;
                while (0 < length) {
                    final int id = TileEntityBeacon.effectsList[3][0].id;
                    final PowerButton powerButton = new PowerButton(0x300 | id, this.guiLeft + 76 + 0 - n / 2, this.guiTop + 22 + 75, id, 3);
                    this.buttonList.add(powerButton);
                    if (3 >= field) {
                        powerButton.enabled = false;
                    }
                    else if (id == field2) {
                        powerButton.func_146140_b(true);
                    }
                    ++n2;
                }
                int n3 = 0;
                ++n3;
            }
            final int n4 = TileEntityBeacon.effectsList[3].length + 1;
            final int n5 = n4 * 22 + (n4 - 1) * 2;
            while (0 < n4 - 1) {
                final int id2 = TileEntityBeacon.effectsList[3][0].id;
                final PowerButton powerButton2 = new PowerButton(0x300 | id2, this.guiLeft + 167 + 0 - n5 / 2, this.guiTop + 47, id2, 3);
                this.buttonList.add(powerButton2);
                if (3 >= field) {
                    powerButton2.enabled = false;
                }
                else if (id2 == field3) {
                    powerButton2.func_146140_b(true);
                }
                ++n2;
            }
            if (field2 > 0) {
                final PowerButton powerButton3 = new PowerButton(0x300 | field2, this.guiLeft + 167 + (n4 - 1) * 24 - n5 / 2, this.guiTop + 47, field2, 3);
                this.buttonList.add(powerButton3);
                if (3 >= field) {
                    powerButton3.enabled = false;
                }
                else if (field2 == field3) {
                    powerButton3.func_146140_b(true);
                }
            }
        }
        this.beaconConfirmButton.enabled = (this.tileBeacon.getStackInSlot(0) != null && field2 > 0);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == -2) {
            GuiBeacon.mc.displayGuiScreen(null);
        }
        else if (guiButton.id == -1) {
            final String s = "MC|Beacon";
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeInt(this.tileBeacon.getField(1));
            packetBuffer.writeInt(this.tileBeacon.getField(2));
            GuiBeacon.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(s, packetBuffer));
            GuiBeacon.mc.displayGuiScreen(null);
        }
        else if (guiButton instanceof PowerButton) {
            if (((PowerButton)guiButton).func_146141_c()) {
                return;
            }
            final int id = guiButton.id;
            final int n = id & 0xFF;
            if (id >> 8 < 3) {
                this.tileBeacon.setField(1, n);
            }
            else {
                this.tileBeacon.setField(2, n);
            }
            this.buttonList.clear();
            this.initGui();
            this.updateScreen();
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.primary", new Object[0]), 62, 10, 14737632);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.secondary", new Object[0]), 169, 10, 14737632);
        for (final GuiButton guiButton : this.buttonList) {
            if (guiButton.isMouseOver()) {
                guiButton.drawButtonForegroundLayer(n - this.guiLeft, n2 - this.guiTop);
                break;
            }
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiBeacon.mc.getTextureManager().bindTexture(GuiBeacon.beaconGuiTextures);
        final int n4 = (GuiBeacon.width - this.xSize) / 2;
        final int n5 = (GuiBeacon.height - this.ySize) / 2;
        this.drawTexturedModalRect(n4, n5, 0, 0, this.xSize, this.ySize);
        this.itemRender.zLevel = 100.0f;
        this.itemRender.func_180450_b(new ItemStack(Items.emerald), n4 + 42, n5 + 109);
        this.itemRender.func_180450_b(new ItemStack(Items.diamond), n4 + 42 + 22, n5 + 109);
        this.itemRender.func_180450_b(new ItemStack(Items.gold_ingot), n4 + 42 + 44, n5 + 109);
        this.itemRender.func_180450_b(new ItemStack(Items.iron_ingot), n4 + 42 + 66, n5 + 109);
        this.itemRender.zLevel = 0.0f;
    }
    
    static ResourceLocation access$0() {
        return GuiBeacon.beaconGuiTextures;
    }
    
    static void access$1(final GuiBeacon guiBeacon, final String s, final int n, final int n2) {
        guiBeacon.drawCreativeTabHoveringText(s, n, n2);
    }
    
    static class Button extends GuiButton
    {
        private final ResourceLocation field_146145_o;
        private final int field_146144_p;
        private final int field_146143_q;
        private boolean field_146142_r;
        private static final String __OBFID;
        
        protected Button(final int n, final int n2, final int n3, final ResourceLocation field_146145_o, final int field_146144_p, final int field_146143_q) {
            super(n, n2, n3, 22, 22, "");
            this.field_146145_o = field_146145_o;
            this.field_146144_p = field_146144_p;
            this.field_146143_q = field_146143_q;
        }
        
        @Override
        public void drawButton(final Minecraft minecraft, final int n, final int n2) {
            if (this.visible) {
                minecraft.getTextureManager().bindTexture(GuiBeacon.access$0());
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.hovered = (n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height);
                if (!this.enabled) {
                    final int n3 = 0 + this.width * 2;
                }
                else if (this.field_146142_r) {
                    final int n4 = 0 + this.width * 1;
                }
                else if (this.hovered) {
                    final int n5 = 0 + this.width * 3;
                }
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 219, this.width, this.height);
                if (!GuiBeacon.access$0().equals(this.field_146145_o)) {
                    minecraft.getTextureManager().bindTexture(this.field_146145_o);
                }
                this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.field_146144_p, this.field_146143_q, 18, 18);
            }
        }
        
        public boolean func_146141_c() {
            return this.field_146142_r;
        }
        
        public void func_146140_b(final boolean field_146142_r) {
            this.field_146142_r = field_146142_r;
        }
        
        static {
            __OBFID = "CL_00000743";
        }
    }
    
    class CancelButton extends Button
    {
        private static final String __OBFID;
        final GuiBeacon this$0;
        
        public CancelButton(final GuiBeacon this$0, final int n, final int n2, final int n3) {
            this.this$0 = this$0;
            super(n, n2, n3, GuiBeacon.access$0(), 112, 220);
        }
        
        @Override
        public void drawButtonForegroundLayer(final int n, final int n2) {
            GuiBeacon.access$1(this.this$0, I18n.format("gui.cancel", new Object[0]), n, n2);
        }
        
        static {
            __OBFID = "CL_00000740";
        }
    }
    
    class ConfirmButton extends Button
    {
        private static final String __OBFID;
        final GuiBeacon this$0;
        
        public ConfirmButton(final GuiBeacon this$0, final int n, final int n2, final int n3) {
            this.this$0 = this$0;
            super(n, n2, n3, GuiBeacon.access$0(), 90, 220);
        }
        
        @Override
        public void drawButtonForegroundLayer(final int n, final int n2) {
            GuiBeacon.access$1(this.this$0, I18n.format("gui.done", new Object[0]), n, n2);
        }
        
        static {
            __OBFID = "CL_00000741";
        }
    }
    
    class PowerButton extends Button
    {
        private final int field_146149_p;
        private final int field_146148_q;
        private static final String __OBFID;
        final GuiBeacon this$0;
        
        public PowerButton(final GuiBeacon this$0, final int n, final int n2, final int n3, final int field_146149_p, final int field_146148_q) {
            this.this$0 = this$0;
            super(n, n2, n3, GuiContainer.inventoryBackground, 0 + Potion.potionTypes[field_146149_p].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[field_146149_p].getStatusIconIndex() / 8 * 18);
            this.field_146149_p = field_146149_p;
            this.field_146148_q = field_146148_q;
        }
        
        @Override
        public void drawButtonForegroundLayer(final int n, final int n2) {
            String s = I18n.format(Potion.potionTypes[this.field_146149_p].getName(), new Object[0]);
            if (this.field_146148_q >= 3 && this.field_146149_p != Potion.regeneration.id) {
                s = String.valueOf(s) + " II";
            }
            GuiBeacon.access$1(this.this$0, s, n, n2);
        }
        
        static {
            __OBFID = "CL_00000742";
        }
    }
}
