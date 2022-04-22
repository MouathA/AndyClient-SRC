package net.minecraft.client.gui;

import net.minecraft.world.biome.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.world.gen.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;

public class GuiFlatPresets extends GuiScreen
{
    private static final List field_146431_f;
    private final GuiCreateFlatWorld field_146432_g;
    private String field_146438_h;
    private String field_146439_i;
    private String field_146436_r;
    private ListSlot field_146435_s;
    private GuiButton field_146434_t;
    private GuiTextField field_146433_u;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000704";
        field_146431_f = Lists.newArrayList();
        func_146421_a("Classic Flat", Item.getItemFromBlock(Blocks.grass), BiomeGenBase.plains, Arrays.asList("village"), new FlatLayerInfo(1, (Block)Blocks.grass), new FlatLayerInfo(2, Blocks.dirt), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Tunnelers' Dream", Item.getItemFromBlock(Blocks.stone), BiomeGenBase.extremeHills, Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"), new FlatLayerInfo(1, (Block)Blocks.grass), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(230, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Water World", Items.water_bucket, BiomeGenBase.deepOcean, Arrays.asList("biome_1", "oceanmonument"), new FlatLayerInfo(90, (Block)Blocks.water), new FlatLayerInfo(5, (Block)Blocks.sand), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(5, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_175354_a("Overworld", Item.getItemFromBlock(Blocks.tallgrass), BlockTallGrass.EnumType.GRASS.func_177044_a(), BiomeGenBase.plains, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"), new FlatLayerInfo(1, (Block)Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Snowy Kingdom", Item.getItemFromBlock(Blocks.snow_layer), BiomeGenBase.icePlains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.snow_layer), new FlatLayerInfo(1, (Block)Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Bottomless Pit", Items.feather, BiomeGenBase.plains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, (Block)Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(2, Blocks.cobblestone));
        func_146421_a("Desert", Item.getItemFromBlock(Blocks.sand), BiomeGenBase.desert, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"), new FlatLayerInfo(8, (Block)Blocks.sand), new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146425_a("Redstone Ready", Items.redstone, BiomeGenBase.desert, new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
    }
    
    public GuiFlatPresets(final GuiCreateFlatWorld field_146432_g) {
        this.field_146432_g = field_146432_g;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.field_146438_h = I18n.format("createWorld.customize.presets.title", new Object[0]);
        this.field_146439_i = I18n.format("createWorld.customize.presets.share", new Object[0]);
        this.field_146436_r = I18n.format("createWorld.customize.presets.list", new Object[0]);
        this.field_146433_u = new GuiTextField(2, this.fontRendererObj, 50, 40, GuiFlatPresets.width - 100, 20);
        this.field_146435_s = new ListSlot();
        this.field_146433_u.setMaxStringLength(1230);
        this.field_146433_u.setText(this.field_146432_g.func_146384_e());
        this.buttonList.add(this.field_146434_t = new GuiButton(0, GuiFlatPresets.width / 2 - 155, GuiFlatPresets.height - 28, 150, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiFlatPresets.width / 2 + 5, GuiFlatPresets.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.func_146426_g();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_146435_s.func_178039_p();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.field_146433_u.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (!this.field_146433_u.textboxKeyTyped(c, n)) {
            super.keyTyped(c, n);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0 && this.func_146430_p()) {
            this.field_146432_g.func_146383_a(this.field_146433_u.getText());
            GuiFlatPresets.mc.displayGuiScreen(this.field_146432_g);
        }
        else if (guiButton.id == 1) {
            GuiFlatPresets.mc.displayGuiScreen(this.field_146432_g);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_146435_s.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, this.field_146438_h, GuiFlatPresets.width / 2, 8, 16777215);
        this.drawString(this.fontRendererObj, this.field_146439_i, 50, 30, 10526880);
        this.drawString(this.fontRendererObj, this.field_146436_r, 50, 70, 10526880);
        this.field_146433_u.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void updateScreen() {
        this.field_146433_u.updateCursorCounter();
        super.updateScreen();
    }
    
    public void func_146426_g() {
        this.field_146434_t.enabled = this.func_146430_p();
    }
    
    private boolean func_146430_p() {
        return (this.field_146435_s.field_148175_k > -1 && this.field_146435_s.field_148175_k < GuiFlatPresets.field_146431_f.size()) || this.field_146433_u.getText().length() > 1;
    }
    
    private static void func_146425_a(final String s, final Item item, final BiomeGenBase biomeGenBase, final FlatLayerInfo... array) {
        func_175354_a(s, item, 0, biomeGenBase, null, array);
    }
    
    private static void func_146421_a(final String s, final Item item, final BiomeGenBase biomeGenBase, final List list, final FlatLayerInfo... array) {
        func_175354_a(s, item, 0, biomeGenBase, list, array);
    }
    
    private static void func_175354_a(final String s, final Item item, final int n, final BiomeGenBase biomeGenBase, final List list, final FlatLayerInfo... array) {
        final FlatGeneratorInfo flatGeneratorInfo = new FlatGeneratorInfo();
        for (int i = array.length - 1; i >= 0; --i) {
            flatGeneratorInfo.getFlatLayers().add(array[i]);
        }
        flatGeneratorInfo.setBiome(biomeGenBase.biomeID);
        flatGeneratorInfo.func_82645_d();
        if (list != null) {
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                flatGeneratorInfo.getWorldFeatures().put(iterator.next(), Maps.newHashMap());
            }
        }
        GuiFlatPresets.field_146431_f.add(new LayerItem(item, n, s, flatGeneratorInfo.toString()));
    }
    
    static List access$0() {
        return GuiFlatPresets.field_146431_f;
    }
    
    static GuiTextField access$1(final GuiFlatPresets guiFlatPresets) {
        return guiFlatPresets.field_146433_u;
    }
    
    static ListSlot access$2(final GuiFlatPresets guiFlatPresets) {
        return guiFlatPresets.field_146435_s;
    }
    
    static class LayerItem
    {
        public Item field_148234_a;
        public int field_179037_b;
        public String field_148232_b;
        public String field_148233_c;
        private static final String __OBFID;
        
        public LayerItem(final Item field_148234_a, final int field_179037_b, final String field_148232_b, final String field_148233_c) {
            this.field_148234_a = field_148234_a;
            this.field_179037_b = field_179037_b;
            this.field_148232_b = field_148232_b;
            this.field_148233_c = field_148233_c;
        }
        
        static {
            __OBFID = "CL_00000705";
        }
    }
    
    class ListSlot extends GuiSlot
    {
        public int field_148175_k;
        private static final String __OBFID;
        final GuiFlatPresets this$0;
        
        public ListSlot(final GuiFlatPresets this$0) {
            this.this$0 = this$0;
            super(GuiFlatPresets.mc, GuiFlatPresets.width, GuiFlatPresets.height, 80, GuiFlatPresets.height - 37, 24);
            this.field_148175_k = -1;
        }
        
        private void func_178054_a(final int n, final int n2, final Item item, final int n3) {
            this.func_148173_e(n + 1, n2 + 1);
            this.this$0.itemRender.func_175042_a(new ItemStack(item, 1, n3), n + 2, n2 + 2);
        }
        
        private void func_148173_e(final int n, final int n2) {
            this.func_148171_c(n, n2, 0, 0);
        }
        
        private void func_148171_c(final int n, final int n2, final int n3, final int n4) {
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
            return GuiFlatPresets.access$0().size();
        }
        
        @Override
        protected void elementClicked(final int field_148175_k, final boolean b, final int n, final int n2) {
            this.field_148175_k = field_148175_k;
            this.this$0.func_146426_g();
            GuiFlatPresets.access$1(this.this$0).setText(GuiFlatPresets.access$0().get(GuiFlatPresets.access$2(this.this$0).field_148175_k).field_148233_c);
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return n == this.field_148175_k;
        }
        
        @Override
        protected void drawBackground() {
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final LayerItem layerItem = GuiFlatPresets.access$0().get(n);
            this.func_178054_a(n2, n3, layerItem.field_148234_a, layerItem.field_179037_b);
            this.this$0.fontRendererObj.drawString(layerItem.field_148232_b, n2 + 18 + 5, n3 + 6, 16777215);
        }
        
        static {
            __OBFID = "CL_00000706";
        }
    }
}
