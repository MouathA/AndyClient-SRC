package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.gen.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class GuiCreateFlatWorld extends GuiScreen
{
    private final GuiCreateWorld createWorldGui;
    private FlatGeneratorInfo theFlatGeneratorInfo;
    private String field_146393_h;
    private String field_146394_i;
    private String field_146391_r;
    private Details createFlatWorldListSlotGui;
    private GuiButton field_146389_t;
    private GuiButton field_146388_u;
    private GuiButton field_146386_v;
    private static final String __OBFID;
    
    public GuiCreateFlatWorld(final GuiCreateWorld createWorldGui, final String s) {
        this.theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
        this.createWorldGui = createWorldGui;
        this.func_146383_a(s);
    }
    
    public String func_146384_e() {
        return this.theFlatGeneratorInfo.toString();
    }
    
    public void func_146383_a(final String s) {
        this.theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(s);
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.field_146393_h = I18n.format("createWorld.customize.flat.title", new Object[0]);
        this.field_146394_i = I18n.format("createWorld.customize.flat.tile", new Object[0]);
        this.field_146391_r = I18n.format("createWorld.customize.flat.height", new Object[0]);
        this.createFlatWorldListSlotGui = new Details();
        this.buttonList.add(this.field_146389_t = new GuiButton(2, GuiCreateFlatWorld.width / 2 - 154, GuiCreateFlatWorld.height - 52, 100, 20, String.valueOf(I18n.format("createWorld.customize.flat.addLayer", new Object[0])) + " (NYI)"));
        this.buttonList.add(this.field_146388_u = new GuiButton(3, GuiCreateFlatWorld.width / 2 - 50, GuiCreateFlatWorld.height - 52, 100, 20, String.valueOf(I18n.format("createWorld.customize.flat.editLayer", new Object[0])) + " (NYI)"));
        this.buttonList.add(this.field_146386_v = new GuiButton(4, GuiCreateFlatWorld.width / 2 - 155, GuiCreateFlatWorld.height - 52, 150, 20, I18n.format("createWorld.customize.flat.removeLayer", new Object[0])));
        this.buttonList.add(new GuiButton(0, GuiCreateFlatWorld.width / 2 - 155, GuiCreateFlatWorld.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(new GuiButton(5, GuiCreateFlatWorld.width / 2 + 5, GuiCreateFlatWorld.height - 52, 150, 20, I18n.format("createWorld.customize.presets", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiCreateFlatWorld.width / 2 + 5, GuiCreateFlatWorld.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        final GuiButton field_146389_t = this.field_146389_t;
        final GuiButton field_146388_u = this.field_146388_u;
        final boolean b = false;
        field_146388_u.visible = b;
        field_146389_t.visible = b;
        this.theFlatGeneratorInfo.func_82645_d();
        this.func_146375_g();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.createFlatWorldListSlotGui.func_178039_p();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        final int n = this.theFlatGeneratorInfo.getFlatLayers().size() - this.createFlatWorldListSlotGui.field_148228_k - 1;
        if (guiButton.id == 1) {
            GuiCreateFlatWorld.mc.displayGuiScreen(this.createWorldGui);
        }
        else if (guiButton.id == 0) {
            this.createWorldGui.field_146334_a = this.func_146384_e();
            GuiCreateFlatWorld.mc.displayGuiScreen(this.createWorldGui);
        }
        else if (guiButton.id == 5) {
            GuiCreateFlatWorld.mc.displayGuiScreen(new GuiFlatPresets(this));
        }
        else if (guiButton.id == 4 && this.func_146382_i()) {
            this.theFlatGeneratorInfo.getFlatLayers().remove(n);
            this.createFlatWorldListSlotGui.field_148228_k = Math.min(this.createFlatWorldListSlotGui.field_148228_k, this.theFlatGeneratorInfo.getFlatLayers().size() - 1);
        }
        this.theFlatGeneratorInfo.func_82645_d();
        this.func_146375_g();
    }
    
    public void func_146375_g() {
        final boolean func_146382_i = this.func_146382_i();
        this.field_146386_v.enabled = func_146382_i;
        this.field_146388_u.enabled = func_146382_i;
        this.field_146388_u.enabled = false;
        this.field_146389_t.enabled = false;
    }
    
    private boolean func_146382_i() {
        return this.createFlatWorldListSlotGui.field_148228_k > -1 && this.createFlatWorldListSlotGui.field_148228_k < this.theFlatGeneratorInfo.getFlatLayers().size();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.createFlatWorldListSlotGui.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, this.field_146393_h, GuiCreateFlatWorld.width / 2, 8, 16777215);
        final int n4 = GuiCreateFlatWorld.width / 2 - 92 - 16;
        this.drawString(this.fontRendererObj, this.field_146394_i, n4, 32, 16777215);
        this.drawString(this.fontRendererObj, this.field_146391_r, n4 + 2 + 213 - this.fontRendererObj.getStringWidth(this.field_146391_r), 32, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    static FlatGeneratorInfo access$0(final GuiCreateFlatWorld guiCreateFlatWorld) {
        return guiCreateFlatWorld.theFlatGeneratorInfo;
    }
    
    static {
        __OBFID = "CL_00000687";
    }
    
    class Details extends GuiSlot
    {
        public int field_148228_k;
        private static final String __OBFID;
        final GuiCreateFlatWorld this$0;
        
        public Details(final GuiCreateFlatWorld this$0) {
            this.this$0 = this$0;
            super(GuiCreateFlatWorld.mc, GuiCreateFlatWorld.width, GuiCreateFlatWorld.height, 43, GuiCreateFlatWorld.height - 60, 24);
            this.field_148228_k = -1;
        }
        
        private void func_148225_a(final int n, final int n2, final ItemStack itemStack) {
            this.func_148226_e(n + 1, n2 + 1);
            if (itemStack != null && itemStack.getItem() != null) {
                this.this$0.itemRender.func_175042_a(itemStack, n + 2, n2 + 2);
            }
        }
        
        private void func_148226_e(final int n, final int n2) {
            this.func_148224_c(n, n2, 0, 0);
        }
        
        private void func_148224_c(final int n, final int n2, final int n3, final int n4) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(Gui.statIcons);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertexWithUV(n + 0, n2 + 18, this.this$0.zLevel, (n3 + 0) * 0.0078125f, (n4 + 18) * 0.0078125f);
            worldRenderer.addVertexWithUV(n + 18, n2 + 18, this.this$0.zLevel, (n3 + 18) * 0.0078125f, (n4 + 18) * 0.0078125f);
            worldRenderer.addVertexWithUV(n + 18, n2 + 0, this.this$0.zLevel, (n3 + 18) * 0.0078125f, (n4 + 0) * 0.0078125f);
            worldRenderer.addVertexWithUV(n + 0, n2 + 0, this.this$0.zLevel, (n3 + 0) * 0.0078125f, (n4 + 0) * 0.0078125f);
            instance.draw();
        }
        
        @Override
        protected int getSize() {
            return GuiCreateFlatWorld.access$0(this.this$0).getFlatLayers().size();
        }
        
        @Override
        protected void elementClicked(final int field_148228_k, final boolean b, final int n, final int n2) {
            this.field_148228_k = field_148228_k;
            this.this$0.func_146375_g();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return n == this.field_148228_k;
        }
        
        @Override
        protected void drawBackground() {
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final FlatLayerInfo flatLayerInfo = GuiCreateFlatWorld.access$0(this.this$0).getFlatLayers().get(GuiCreateFlatWorld.access$0(this.this$0).getFlatLayers().size() - n - 1);
            final IBlockState func_175900_c = flatLayerInfo.func_175900_c();
            final Block block = func_175900_c.getBlock();
            Item item = Item.getItemFromBlock(block);
            ItemStack itemStack = (block != Blocks.air && item != null) ? new ItemStack(item, 1, block.getMetaFromState(func_175900_c)) : null;
            String localizedName = (itemStack == null) ? "Air" : item.getItemStackDisplayName(itemStack);
            if (item == null) {
                if (block != Blocks.water && block != Blocks.flowing_water) {
                    if (block == Blocks.lava || block == Blocks.flowing_lava) {
                        item = Items.lava_bucket;
                    }
                }
                else {
                    item = Items.water_bucket;
                }
                if (item != null) {
                    itemStack = new ItemStack(item, 1, block.getMetaFromState(func_175900_c));
                    localizedName = block.getLocalizedName();
                }
            }
            this.func_148225_a(n2, n3, itemStack);
            this.this$0.fontRendererObj.drawString(localizedName, n2 + 18 + 5, n3 + 3, 16777215);
            String s;
            if (n == 0) {
                s = I18n.format("createWorld.customize.flat.layer.top", flatLayerInfo.getLayerCount());
            }
            else if (n == GuiCreateFlatWorld.access$0(this.this$0).getFlatLayers().size() - 1) {
                s = I18n.format("createWorld.customize.flat.layer.bottom", flatLayerInfo.getLayerCount());
            }
            else {
                s = I18n.format("createWorld.customize.flat.layer", flatLayerInfo.getLayerCount());
            }
            this.this$0.fontRendererObj.drawString(s, n2 + 2 + 213 - this.this$0.fontRendererObj.getStringWidth(s), n3 + 3, 16777215);
        }
        
        @Override
        protected int getScrollBarX() {
            return this.width - 70;
        }
        
        static {
            __OBFID = "CL_00000688";
        }
    }
}
