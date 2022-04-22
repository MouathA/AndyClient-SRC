package net.minecraft.client.gui;

import com.google.common.base.*;
import net.minecraft.world.gen.*;
import java.util.*;
import com.google.common.primitives.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;
import net.minecraft.client.renderer.*;

public class GuiCustomizeWorldScreen extends GuiScreen implements GuiSlider.FormatHelper, GuiPageButtonList.GuiResponder
{
    private GuiCreateWorld field_175343_i;
    protected String field_175341_a;
    protected String field_175333_f;
    protected String field_175335_g;
    protected String[] field_175342_h;
    private GuiPageButtonList field_175349_r;
    private GuiButton field_175348_s;
    private GuiButton field_175347_t;
    private GuiButton field_175346_u;
    private GuiButton field_175345_v;
    private GuiButton field_175344_w;
    private GuiButton field_175352_x;
    private GuiButton field_175351_y;
    private GuiButton field_175350_z;
    private boolean field_175338_A;
    private int field_175339_B;
    private boolean field_175340_C;
    private Predicate field_175332_D;
    private ChunkProviderSettings.Factory field_175334_E;
    private ChunkProviderSettings.Factory field_175336_F;
    private Random field_175337_G;
    private static final String __OBFID;
    
    public GuiCustomizeWorldScreen(final GuiScreen guiScreen, final String s) {
        this.field_175341_a = "Customize World Settings";
        this.field_175333_f = "Page 1 of 3";
        this.field_175335_g = "Basic Settings";
        this.field_175342_h = new String[4];
        this.field_175338_A = false;
        this.field_175339_B = 0;
        this.field_175340_C = false;
        this.field_175332_D = new Predicate() {
            private static final String __OBFID;
            final GuiCustomizeWorldScreen this$0;
            
            public boolean func_178956_a(final String s) {
                final Float tryParse = Floats.tryParse(s);
                return s.length() == 0 || (tryParse != null && Floats.isFinite(tryParse) && tryParse >= 0.0f);
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_178956_a((String)o);
            }
            
            static {
                __OBFID = "CL_00001933";
            }
        };
        this.field_175334_E = new ChunkProviderSettings.Factory();
        this.field_175337_G = new Random();
        this.field_175343_i = (GuiCreateWorld)guiScreen;
        this.func_175324_a(s);
    }
    
    @Override
    public void initGui() {
        this.field_175341_a = I18n.format("options.customizeTitle", new Object[0]);
        this.buttonList.clear();
        this.buttonList.add(this.field_175345_v = new GuiButton(302, 20, 5, 80, 20, I18n.format("createWorld.customize.custom.prev", new Object[0])));
        this.buttonList.add(this.field_175344_w = new GuiButton(303, GuiCustomizeWorldScreen.width - 100, 5, 80, 20, I18n.format("createWorld.customize.custom.next", new Object[0])));
        this.buttonList.add(this.field_175346_u = new GuiButton(304, GuiCustomizeWorldScreen.width / 2 - 187, GuiCustomizeWorldScreen.height - 27, 90, 20, I18n.format("createWorld.customize.custom.defaults", new Object[0])));
        this.buttonList.add(this.field_175347_t = new GuiButton(301, GuiCustomizeWorldScreen.width / 2 - 92, GuiCustomizeWorldScreen.height - 27, 90, 20, I18n.format("createWorld.customize.custom.randomize", new Object[0])));
        this.buttonList.add(this.field_175350_z = new GuiButton(305, GuiCustomizeWorldScreen.width / 2 + 3, GuiCustomizeWorldScreen.height - 27, 90, 20, I18n.format("createWorld.customize.custom.presets", new Object[0])));
        this.buttonList.add(this.field_175348_s = new GuiButton(300, GuiCustomizeWorldScreen.width / 2 + 98, GuiCustomizeWorldScreen.height - 27, 90, 20, I18n.format("gui.done", new Object[0])));
        this.field_175352_x = new GuiButton(306, GuiCustomizeWorldScreen.width / 2 - 55, 160, 50, 20, I18n.format("gui.yes", new Object[0]));
        this.field_175352_x.visible = false;
        this.buttonList.add(this.field_175352_x);
        this.field_175351_y = new GuiButton(307, GuiCustomizeWorldScreen.width / 2 + 5, 160, 50, 20, I18n.format("gui.no", new Object[0]));
        this.field_175351_y.visible = false;
        this.buttonList.add(this.field_175351_y);
        this.func_175325_f();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_175349_r.func_178039_p();
    }
    
    private void func_175325_f() {
        this.field_175349_r = new GuiPageButtonList(GuiCustomizeWorldScreen.mc, GuiCustomizeWorldScreen.width, GuiCustomizeWorldScreen.height, 32, GuiCustomizeWorldScreen.height - 32, 25, this, new GuiPageButtonList.GuiListEntry[][] { { new GuiPageButtonList.GuiSlideEntry(160, I18n.format("createWorld.customize.custom.seaLevel", new Object[0]), true, this, 1.0f, 255.0f, (float)this.field_175336_F.field_177929_r), new GuiPageButtonList.GuiButtonEntry(148, I18n.format("createWorld.customize.custom.useCaves", new Object[0]), true, this.field_175336_F.field_177927_s), new GuiPageButtonList.GuiButtonEntry(150, I18n.format("createWorld.customize.custom.useStrongholds", new Object[0]), true, this.field_175336_F.field_177921_v), new GuiPageButtonList.GuiButtonEntry(151, I18n.format("createWorld.customize.custom.useVillages", new Object[0]), true, this.field_175336_F.field_177919_w), new GuiPageButtonList.GuiButtonEntry(152, I18n.format("createWorld.customize.custom.useMineShafts", new Object[0]), true, this.field_175336_F.field_177944_x), new GuiPageButtonList.GuiButtonEntry(153, I18n.format("createWorld.customize.custom.useTemples", new Object[0]), true, this.field_175336_F.field_177942_y), new GuiPageButtonList.GuiButtonEntry(210, I18n.format("createWorld.customize.custom.useMonuments", new Object[0]), true, this.field_175336_F.field_177940_z), new GuiPageButtonList.GuiButtonEntry(154, I18n.format("createWorld.customize.custom.useRavines", new Object[0]), true, this.field_175336_F.field_177870_A), new GuiPageButtonList.GuiButtonEntry(149, I18n.format("createWorld.customize.custom.useDungeons", new Object[0]), true, this.field_175336_F.field_177925_t), new GuiPageButtonList.GuiSlideEntry(157, I18n.format("createWorld.customize.custom.dungeonChance", new Object[0]), true, this, 1.0f, 100.0f, (float)this.field_175336_F.field_177923_u), new GuiPageButtonList.GuiButtonEntry(155, I18n.format("createWorld.customize.custom.useWaterLakes", new Object[0]), true, this.field_175336_F.field_177871_B), new GuiPageButtonList.GuiSlideEntry(158, I18n.format("createWorld.customize.custom.waterLakeChance", new Object[0]), true, this, 1.0f, 100.0f, (float)this.field_175336_F.field_177872_C), new GuiPageButtonList.GuiButtonEntry(156, I18n.format("createWorld.customize.custom.useLavaLakes", new Object[0]), true, this.field_175336_F.field_177866_D), new GuiPageButtonList.GuiSlideEntry(159, I18n.format("createWorld.customize.custom.lavaLakeChance", new Object[0]), true, this, 10.0f, 100.0f, (float)this.field_175336_F.field_177867_E), new GuiPageButtonList.GuiButtonEntry(161, I18n.format("createWorld.customize.custom.useLavaOceans", new Object[0]), true, this.field_175336_F.field_177868_F), new GuiPageButtonList.GuiSlideEntry(162, I18n.format("createWorld.customize.custom.fixedBiome", new Object[0]), true, this, -1.0f, 37.0f, (float)this.field_175336_F.field_177869_G), new GuiPageButtonList.GuiSlideEntry(163, I18n.format("createWorld.customize.custom.biomeSize", new Object[0]), true, this, 1.0f, 8.0f, (float)this.field_175336_F.field_177877_H), new GuiPageButtonList.GuiSlideEntry(164, I18n.format("createWorld.customize.custom.riverSize", new Object[0]), true, this, 1.0f, 5.0f, (float)this.field_175336_F.field_177878_I) }, { new GuiPageButtonList.GuiLabelEntry(416, I18n.format("tile.dirt.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(165, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.field_177879_J), new GuiPageButtonList.GuiSlideEntry(166, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.field_177880_K), new GuiPageButtonList.GuiSlideEntry(167, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177873_L), new GuiPageButtonList.GuiSlideEntry(168, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177874_M), new GuiPageButtonList.GuiLabelEntry(417, I18n.format("tile.gravel.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(169, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.field_177875_N), new GuiPageButtonList.GuiSlideEntry(170, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.field_177876_O), new GuiPageButtonList.GuiSlideEntry(171, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177886_P), new GuiPageButtonList.GuiSlideEntry(172, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177885_Q), new GuiPageButtonList.GuiLabelEntry(418, I18n.format("tile.stone.granite.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(173, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.field_177888_R), new GuiPageButtonList.GuiSlideEntry(174, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.field_177887_S), new GuiPageButtonList.GuiSlideEntry(175, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177882_T), new GuiPageButtonList.GuiSlideEntry(176, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177881_U), new GuiPageButtonList.GuiLabelEntry(419, I18n.format("tile.stone.diorite.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(177, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.field_177884_V), new GuiPageButtonList.GuiSlideEntry(178, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.field_177883_W), new GuiPageButtonList.GuiSlideEntry(179, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177891_X), new GuiPageButtonList.GuiSlideEntry(180, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177890_Y), new GuiPageButtonList.GuiLabelEntry(420, I18n.format("tile.stone.andesite.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(181, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.field_177892_Z), new GuiPageButtonList.GuiSlideEntry(182, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.field_177936_aa), new GuiPageButtonList.GuiSlideEntry(183, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177937_ab), new GuiPageButtonList.GuiSlideEntry(184, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177934_ac), new GuiPageButtonList.GuiLabelEntry(421, I18n.format("tile.oreCoal.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(185, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.field_177935_ad), new GuiPageButtonList.GuiSlideEntry(186, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.field_177941_ae), new GuiPageButtonList.GuiSlideEntry(187, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177943_af), new GuiPageButtonList.GuiSlideEntry(189, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177938_ag), new GuiPageButtonList.GuiLabelEntry(422, I18n.format("tile.oreIron.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(190, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.field_177939_ah), new GuiPageButtonList.GuiSlideEntry(191, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.field_177922_ai), new GuiPageButtonList.GuiSlideEntry(192, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177924_aj), new GuiPageButtonList.GuiSlideEntry(193, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177918_ak), new GuiPageButtonList.GuiLabelEntry(423, I18n.format("tile.oreGold.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(194, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.field_177920_al), new GuiPageButtonList.GuiSlideEntry(195, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.field_177930_am), new GuiPageButtonList.GuiSlideEntry(196, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177932_an), new GuiPageButtonList.GuiSlideEntry(197, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177926_ao), new GuiPageButtonList.GuiLabelEntry(424, I18n.format("tile.oreRedstone.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(198, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.field_177928_ap), new GuiPageButtonList.GuiSlideEntry(199, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.field_177908_aq), new GuiPageButtonList.GuiSlideEntry(200, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177906_ar), new GuiPageButtonList.GuiSlideEntry(201, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177904_as), new GuiPageButtonList.GuiLabelEntry(425, I18n.format("tile.oreDiamond.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(202, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.field_177902_at), new GuiPageButtonList.GuiSlideEntry(203, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.field_177916_au), new GuiPageButtonList.GuiSlideEntry(204, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177914_av), new GuiPageButtonList.GuiSlideEntry(205, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177912_aw), new GuiPageButtonList.GuiLabelEntry(426, I18n.format("tile.oreLapis.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(206, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.field_177910_ax), new GuiPageButtonList.GuiSlideEntry(207, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.field_177897_ay), new GuiPageButtonList.GuiSlideEntry(208, I18n.format("createWorld.customize.custom.center", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177895_az), new GuiPageButtonList.GuiSlideEntry(209, I18n.format("createWorld.customize.custom.spread", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.field_177889_aA) }, { new GuiPageButtonList.GuiSlideEntry(100, I18n.format("createWorld.customize.custom.mainNoiseScaleX", new Object[0]), false, this, 1.0f, 5000.0f, this.field_175336_F.field_177917_i), new GuiPageButtonList.GuiSlideEntry(101, I18n.format("createWorld.customize.custom.mainNoiseScaleY", new Object[0]), false, this, 1.0f, 5000.0f, this.field_175336_F.field_177911_j), new GuiPageButtonList.GuiSlideEntry(102, I18n.format("createWorld.customize.custom.mainNoiseScaleZ", new Object[0]), false, this, 1.0f, 5000.0f, this.field_175336_F.field_177913_k), new GuiPageButtonList.GuiSlideEntry(103, I18n.format("createWorld.customize.custom.depthNoiseScaleX", new Object[0]), false, this, 1.0f, 2000.0f, this.field_175336_F.field_177893_f), new GuiPageButtonList.GuiSlideEntry(104, I18n.format("createWorld.customize.custom.depthNoiseScaleZ", new Object[0]), false, this, 1.0f, 2000.0f, this.field_175336_F.field_177894_g), new GuiPageButtonList.GuiSlideEntry(105, I18n.format("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0]), false, this, 0.01f, 20.0f, this.field_175336_F.field_177915_h), new GuiPageButtonList.GuiSlideEntry(106, I18n.format("createWorld.customize.custom.baseSize", new Object[0]), false, this, 1.0f, 25.0f, this.field_175336_F.field_177907_l), new GuiPageButtonList.GuiSlideEntry(107, I18n.format("createWorld.customize.custom.coordinateScale", new Object[0]), false, this, 1.0f, 6000.0f, this.field_175336_F.field_177899_b), new GuiPageButtonList.GuiSlideEntry(108, I18n.format("createWorld.customize.custom.heightScale", new Object[0]), false, this, 1.0f, 6000.0f, this.field_175336_F.field_177900_c), new GuiPageButtonList.GuiSlideEntry(109, I18n.format("createWorld.customize.custom.stretchY", new Object[0]), false, this, 0.01f, 50.0f, this.field_175336_F.field_177909_m), new GuiPageButtonList.GuiSlideEntry(110, I18n.format("createWorld.customize.custom.upperLimitScale", new Object[0]), false, this, 1.0f, 5000.0f, this.field_175336_F.field_177896_d), new GuiPageButtonList.GuiSlideEntry(111, I18n.format("createWorld.customize.custom.lowerLimitScale", new Object[0]), false, this, 1.0f, 5000.0f, this.field_175336_F.field_177898_e), new GuiPageButtonList.GuiSlideEntry(112, I18n.format("createWorld.customize.custom.biomeDepthWeight", new Object[0]), false, this, 1.0f, 20.0f, this.field_175336_F.field_177903_n), new GuiPageButtonList.GuiSlideEntry(113, I18n.format("createWorld.customize.custom.biomeDepthOffset", new Object[0]), false, this, 0.0f, 20.0f, this.field_175336_F.field_177905_o), new GuiPageButtonList.GuiSlideEntry(114, I18n.format("createWorld.customize.custom.biomeScaleWeight", new Object[0]), false, this, 1.0f, 20.0f, this.field_175336_F.field_177933_p), new GuiPageButtonList.GuiSlideEntry(115, I18n.format("createWorld.customize.custom.biomeScaleOffset", new Object[0]), false, this, 0.0f, 20.0f, this.field_175336_F.field_177931_q) }, { new GuiPageButtonList.GuiLabelEntry(400, String.valueOf(I18n.format("createWorld.customize.custom.mainNoiseScaleX", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(132, String.format("%5.3f", this.field_175336_F.field_177917_i), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(401, String.valueOf(I18n.format("createWorld.customize.custom.mainNoiseScaleY", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(133, String.format("%5.3f", this.field_175336_F.field_177911_j), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(402, String.valueOf(I18n.format("createWorld.customize.custom.mainNoiseScaleZ", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(134, String.format("%5.3f", this.field_175336_F.field_177913_k), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(403, String.valueOf(I18n.format("createWorld.customize.custom.depthNoiseScaleX", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(135, String.format("%5.3f", this.field_175336_F.field_177893_f), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(404, String.valueOf(I18n.format("createWorld.customize.custom.depthNoiseScaleZ", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(136, String.format("%5.3f", this.field_175336_F.field_177894_g), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(405, String.valueOf(I18n.format("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(137, String.format("%2.3f", this.field_175336_F.field_177915_h), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(406, String.valueOf(I18n.format("createWorld.customize.custom.baseSize", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(138, String.format("%2.3f", this.field_175336_F.field_177907_l), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(407, String.valueOf(I18n.format("createWorld.customize.custom.coordinateScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(139, String.format("%5.3f", this.field_175336_F.field_177899_b), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(408, String.valueOf(I18n.format("createWorld.customize.custom.heightScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(140, String.format("%5.3f", this.field_175336_F.field_177900_c), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(409, String.valueOf(I18n.format("createWorld.customize.custom.stretchY", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(141, String.format("%2.3f", this.field_175336_F.field_177909_m), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(410, String.valueOf(I18n.format("createWorld.customize.custom.upperLimitScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(142, String.format("%5.3f", this.field_175336_F.field_177896_d), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(411, String.valueOf(I18n.format("createWorld.customize.custom.lowerLimitScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(143, String.format("%5.3f", this.field_175336_F.field_177898_e), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(412, String.valueOf(I18n.format("createWorld.customize.custom.biomeDepthWeight", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(144, String.format("%2.3f", this.field_175336_F.field_177903_n), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(413, String.valueOf(I18n.format("createWorld.customize.custom.biomeDepthOffset", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(145, String.format("%2.3f", this.field_175336_F.field_177905_o), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(414, String.valueOf(I18n.format("createWorld.customize.custom.biomeScaleWeight", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(146, String.format("%2.3f", this.field_175336_F.field_177933_p), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(415, String.valueOf(I18n.format("createWorld.customize.custom.biomeScaleOffset", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(147, String.format("%2.3f", this.field_175336_F.field_177931_q), false, this.field_175332_D) } });
        while (0 < 4) {
            this.field_175342_h[0] = I18n.format("createWorld.customize.custom.page" + 0, new Object[0]);
            int n = 0;
            ++n;
        }
        this.func_175328_i();
    }
    
    public String func_175323_a() {
        return this.field_175336_F.toString().replace("\n", "");
    }
    
    public void func_175324_a(final String s) {
        if (s != null && s.length() != 0) {
            this.field_175336_F = ChunkProviderSettings.Factory.func_177865_a(s);
        }
        else {
            this.field_175336_F = new ChunkProviderSettings.Factory();
        }
    }
    
    @Override
    public void func_175319_a(final int n, final String s) {
        final float float1 = Float.parseFloat(s);
        float n2 = 0.0f;
        switch (n) {
            case 132: {
                final ChunkProviderSettings.Factory field_175336_F = this.field_175336_F;
                final float clamp_float = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F.field_177917_i = clamp_float;
                n2 = clamp_float;
                break;
            }
            case 133: {
                final ChunkProviderSettings.Factory field_175336_F2 = this.field_175336_F;
                final float clamp_float2 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F2.field_177911_j = clamp_float2;
                n2 = clamp_float2;
                break;
            }
            case 134: {
                final ChunkProviderSettings.Factory field_175336_F3 = this.field_175336_F;
                final float clamp_float3 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F3.field_177913_k = clamp_float3;
                n2 = clamp_float3;
                break;
            }
            case 135: {
                final ChunkProviderSettings.Factory field_175336_F4 = this.field_175336_F;
                final float clamp_float4 = MathHelper.clamp_float(float1, 1.0f, 2000.0f);
                field_175336_F4.field_177893_f = clamp_float4;
                n2 = clamp_float4;
                break;
            }
            case 136: {
                final ChunkProviderSettings.Factory field_175336_F5 = this.field_175336_F;
                final float clamp_float5 = MathHelper.clamp_float(float1, 1.0f, 2000.0f);
                field_175336_F5.field_177894_g = clamp_float5;
                n2 = clamp_float5;
                break;
            }
            case 137: {
                final ChunkProviderSettings.Factory field_175336_F6 = this.field_175336_F;
                final float clamp_float6 = MathHelper.clamp_float(float1, 0.01f, 20.0f);
                field_175336_F6.field_177915_h = clamp_float6;
                n2 = clamp_float6;
                break;
            }
            case 138: {
                final ChunkProviderSettings.Factory field_175336_F7 = this.field_175336_F;
                final float clamp_float7 = MathHelper.clamp_float(float1, 1.0f, 25.0f);
                field_175336_F7.field_177907_l = clamp_float7;
                n2 = clamp_float7;
                break;
            }
            case 139: {
                final ChunkProviderSettings.Factory field_175336_F8 = this.field_175336_F;
                final float clamp_float8 = MathHelper.clamp_float(float1, 1.0f, 6000.0f);
                field_175336_F8.field_177899_b = clamp_float8;
                n2 = clamp_float8;
                break;
            }
            case 140: {
                final ChunkProviderSettings.Factory field_175336_F9 = this.field_175336_F;
                final float clamp_float9 = MathHelper.clamp_float(float1, 1.0f, 6000.0f);
                field_175336_F9.field_177900_c = clamp_float9;
                n2 = clamp_float9;
                break;
            }
            case 141: {
                final ChunkProviderSettings.Factory field_175336_F10 = this.field_175336_F;
                final float clamp_float10 = MathHelper.clamp_float(float1, 0.01f, 50.0f);
                field_175336_F10.field_177909_m = clamp_float10;
                n2 = clamp_float10;
                break;
            }
            case 142: {
                final ChunkProviderSettings.Factory field_175336_F11 = this.field_175336_F;
                final float clamp_float11 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F11.field_177896_d = clamp_float11;
                n2 = clamp_float11;
                break;
            }
            case 143: {
                final ChunkProviderSettings.Factory field_175336_F12 = this.field_175336_F;
                final float clamp_float12 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F12.field_177898_e = clamp_float12;
                n2 = clamp_float12;
                break;
            }
            case 144: {
                final ChunkProviderSettings.Factory field_175336_F13 = this.field_175336_F;
                final float clamp_float13 = MathHelper.clamp_float(float1, 1.0f, 20.0f);
                field_175336_F13.field_177903_n = clamp_float13;
                n2 = clamp_float13;
                break;
            }
            case 145: {
                final ChunkProviderSettings.Factory field_175336_F14 = this.field_175336_F;
                final float clamp_float14 = MathHelper.clamp_float(float1, 0.0f, 20.0f);
                field_175336_F14.field_177905_o = clamp_float14;
                n2 = clamp_float14;
                break;
            }
            case 146: {
                final ChunkProviderSettings.Factory field_175336_F15 = this.field_175336_F;
                final float clamp_float15 = MathHelper.clamp_float(float1, 1.0f, 20.0f);
                field_175336_F15.field_177933_p = clamp_float15;
                n2 = clamp_float15;
                break;
            }
            case 147: {
                final ChunkProviderSettings.Factory field_175336_F16 = this.field_175336_F;
                final float clamp_float16 = MathHelper.clamp_float(float1, 0.0f, 20.0f);
                field_175336_F16.field_177931_q = clamp_float16;
                n2 = clamp_float16;
                break;
            }
        }
        if (n2 != float1 && float1 != 0.0f) {
            ((GuiTextField)this.field_175349_r.func_178061_c(n)).setText(this.func_175330_b(n, n2));
        }
        ((GuiSlider)this.field_175349_r.func_178061_c(n - 132 + 100)).func_175218_a(n2, false);
        if (!this.field_175336_F.equals((Object)this.field_175334_E)) {
            this.field_175338_A = true;
        }
    }
    
    @Override
    public String func_175318_a(final int n, final String s, final float n2) {
        return String.valueOf(s) + ": " + this.func_175330_b(n, n2);
    }
    
    private String func_175330_b(final int n, final float n2) {
        switch (n) {
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 107:
            case 108:
            case 110:
            case 111:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 139:
            case 140:
            case 142:
            case 143: {
                return String.format("%5.3f", n2);
            }
            case 105:
            case 106:
            case 109:
            case 112:
            case 113:
            case 114:
            case 115:
            case 137:
            case 138:
            case 141:
            case 144:
            case 145:
            case 146:
            case 147: {
                return String.format("%2.3f", n2);
            }
            default: {
                return String.format("%d", (int)n2);
            }
            case 162: {
                if (n2 < 0.0f) {
                    return I18n.format("gui.all", new Object[0]);
                }
                if ((int)n2 >= BiomeGenBase.hell.biomeID) {
                    final BiomeGenBase biomeGenBase = BiomeGenBase.getBiomeGenArray()[(int)n2 + 2];
                    return (biomeGenBase != null) ? biomeGenBase.biomeName : "?";
                }
                final BiomeGenBase biomeGenBase2 = BiomeGenBase.getBiomeGenArray()[(int)n2];
                return (biomeGenBase2 != null) ? biomeGenBase2.biomeName : "?";
            }
        }
    }
    
    @Override
    public void func_175321_a(final int n, final boolean field_177940_z) {
        switch (n) {
            case 148: {
                this.field_175336_F.field_177927_s = field_177940_z;
                break;
            }
            case 149: {
                this.field_175336_F.field_177925_t = field_177940_z;
                break;
            }
            case 150: {
                this.field_175336_F.field_177921_v = field_177940_z;
                break;
            }
            case 151: {
                this.field_175336_F.field_177919_w = field_177940_z;
                break;
            }
            case 152: {
                this.field_175336_F.field_177944_x = field_177940_z;
                break;
            }
            case 153: {
                this.field_175336_F.field_177942_y = field_177940_z;
                break;
            }
            case 154: {
                this.field_175336_F.field_177870_A = field_177940_z;
                break;
            }
            case 155: {
                this.field_175336_F.field_177871_B = field_177940_z;
                break;
            }
            case 156: {
                this.field_175336_F.field_177866_D = field_177940_z;
                break;
            }
            case 161: {
                this.field_175336_F.field_177868_F = field_177940_z;
                break;
            }
            case 210: {
                this.field_175336_F.field_177940_z = field_177940_z;
                break;
            }
        }
        if (!this.field_175336_F.equals((Object)this.field_175334_E)) {
            this.field_175338_A = true;
        }
    }
    
    @Override
    public void func_175320_a(final int n, final float n2) {
        switch (n) {
            case 100: {
                this.field_175336_F.field_177917_i = n2;
                break;
            }
            case 101: {
                this.field_175336_F.field_177911_j = n2;
                break;
            }
            case 102: {
                this.field_175336_F.field_177913_k = n2;
                break;
            }
            case 103: {
                this.field_175336_F.field_177893_f = n2;
                break;
            }
            case 104: {
                this.field_175336_F.field_177894_g = n2;
                break;
            }
            case 105: {
                this.field_175336_F.field_177915_h = n2;
                break;
            }
            case 106: {
                this.field_175336_F.field_177907_l = n2;
                break;
            }
            case 107: {
                this.field_175336_F.field_177899_b = n2;
                break;
            }
            case 108: {
                this.field_175336_F.field_177900_c = n2;
                break;
            }
            case 109: {
                this.field_175336_F.field_177909_m = n2;
                break;
            }
            case 110: {
                this.field_175336_F.field_177896_d = n2;
                break;
            }
            case 111: {
                this.field_175336_F.field_177898_e = n2;
                break;
            }
            case 112: {
                this.field_175336_F.field_177903_n = n2;
                break;
            }
            case 113: {
                this.field_175336_F.field_177905_o = n2;
                break;
            }
            case 114: {
                this.field_175336_F.field_177933_p = n2;
                break;
            }
            case 115: {
                this.field_175336_F.field_177931_q = n2;
                break;
            }
            case 157: {
                this.field_175336_F.field_177923_u = (int)n2;
                break;
            }
            case 158: {
                this.field_175336_F.field_177872_C = (int)n2;
                break;
            }
            case 159: {
                this.field_175336_F.field_177867_E = (int)n2;
                break;
            }
            case 160: {
                this.field_175336_F.field_177929_r = (int)n2;
                break;
            }
            case 162: {
                this.field_175336_F.field_177869_G = (int)n2;
                break;
            }
            case 163: {
                this.field_175336_F.field_177877_H = (int)n2;
                break;
            }
            case 164: {
                this.field_175336_F.field_177878_I = (int)n2;
                break;
            }
            case 165: {
                this.field_175336_F.field_177879_J = (int)n2;
                break;
            }
            case 166: {
                this.field_175336_F.field_177880_K = (int)n2;
                break;
            }
            case 167: {
                this.field_175336_F.field_177873_L = (int)n2;
                break;
            }
            case 168: {
                this.field_175336_F.field_177874_M = (int)n2;
                break;
            }
            case 169: {
                this.field_175336_F.field_177875_N = (int)n2;
                break;
            }
            case 170: {
                this.field_175336_F.field_177876_O = (int)n2;
                break;
            }
            case 171: {
                this.field_175336_F.field_177886_P = (int)n2;
                break;
            }
            case 172: {
                this.field_175336_F.field_177885_Q = (int)n2;
                break;
            }
            case 173: {
                this.field_175336_F.field_177888_R = (int)n2;
                break;
            }
            case 174: {
                this.field_175336_F.field_177887_S = (int)n2;
                break;
            }
            case 175: {
                this.field_175336_F.field_177882_T = (int)n2;
                break;
            }
            case 176: {
                this.field_175336_F.field_177881_U = (int)n2;
                break;
            }
            case 177: {
                this.field_175336_F.field_177884_V = (int)n2;
                break;
            }
            case 178: {
                this.field_175336_F.field_177883_W = (int)n2;
                break;
            }
            case 179: {
                this.field_175336_F.field_177891_X = (int)n2;
                break;
            }
            case 180: {
                this.field_175336_F.field_177890_Y = (int)n2;
                break;
            }
            case 181: {
                this.field_175336_F.field_177892_Z = (int)n2;
                break;
            }
            case 182: {
                this.field_175336_F.field_177936_aa = (int)n2;
                break;
            }
            case 183: {
                this.field_175336_F.field_177937_ab = (int)n2;
                break;
            }
            case 184: {
                this.field_175336_F.field_177934_ac = (int)n2;
                break;
            }
            case 185: {
                this.field_175336_F.field_177935_ad = (int)n2;
                break;
            }
            case 186: {
                this.field_175336_F.field_177941_ae = (int)n2;
                break;
            }
            case 187: {
                this.field_175336_F.field_177943_af = (int)n2;
                break;
            }
            case 189: {
                this.field_175336_F.field_177938_ag = (int)n2;
                break;
            }
            case 190: {
                this.field_175336_F.field_177939_ah = (int)n2;
                break;
            }
            case 191: {
                this.field_175336_F.field_177922_ai = (int)n2;
                break;
            }
            case 192: {
                this.field_175336_F.field_177924_aj = (int)n2;
                break;
            }
            case 193: {
                this.field_175336_F.field_177918_ak = (int)n2;
                break;
            }
            case 194: {
                this.field_175336_F.field_177920_al = (int)n2;
                break;
            }
            case 195: {
                this.field_175336_F.field_177930_am = (int)n2;
                break;
            }
            case 196: {
                this.field_175336_F.field_177932_an = (int)n2;
                break;
            }
            case 197: {
                this.field_175336_F.field_177926_ao = (int)n2;
                break;
            }
            case 198: {
                this.field_175336_F.field_177928_ap = (int)n2;
                break;
            }
            case 199: {
                this.field_175336_F.field_177908_aq = (int)n2;
                break;
            }
            case 200: {
                this.field_175336_F.field_177906_ar = (int)n2;
                break;
            }
            case 201: {
                this.field_175336_F.field_177904_as = (int)n2;
                break;
            }
            case 202: {
                this.field_175336_F.field_177902_at = (int)n2;
                break;
            }
            case 203: {
                this.field_175336_F.field_177916_au = (int)n2;
                break;
            }
            case 204: {
                this.field_175336_F.field_177914_av = (int)n2;
                break;
            }
            case 205: {
                this.field_175336_F.field_177912_aw = (int)n2;
                break;
            }
            case 206: {
                this.field_175336_F.field_177910_ax = (int)n2;
                break;
            }
            case 207: {
                this.field_175336_F.field_177897_ay = (int)n2;
                break;
            }
            case 208: {
                this.field_175336_F.field_177895_az = (int)n2;
                break;
            }
            case 209: {
                this.field_175336_F.field_177889_aA = (int)n2;
                break;
            }
        }
        if (n >= 100 && n < 116) {
            final Gui func_178061_c = this.field_175349_r.func_178061_c(n - 100 + 132);
            if (func_178061_c != null) {
                ((GuiTextField)func_178061_c).setText(this.func_175330_b(n, n2));
            }
        }
        if (!this.field_175336_F.equals((Object)this.field_175334_E)) {
            this.field_175338_A = true;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            switch (guiButton.id) {
                case 300: {
                    this.field_175343_i.field_146334_a = this.field_175336_F.toString();
                    GuiCustomizeWorldScreen.mc.displayGuiScreen(this.field_175343_i);
                    break;
                }
                case 301: {
                    while (0 < this.field_175349_r.getSize()) {
                        final GuiPageButtonList.GuiEntry func_178070_d = this.field_175349_r.func_178070_d(0);
                        final Gui func_178022_a = func_178070_d.func_178022_a();
                        if (func_178022_a instanceof GuiButton) {
                            final GuiButton guiButton2 = (GuiButton)func_178022_a;
                            if (guiButton2 instanceof GuiSlider) {
                                ((GuiSlider)guiButton2).func_175219_a(MathHelper.clamp_float(((GuiSlider)guiButton2).func_175217_d() * (0.75f + this.field_175337_G.nextFloat() * 0.5f) + (this.field_175337_G.nextFloat() * 0.1f - 0.05f), 0.0f, 1.0f));
                            }
                            else if (guiButton2 instanceof GuiListButton) {
                                ((GuiListButton)guiButton2).func_175212_b(this.field_175337_G.nextBoolean());
                            }
                        }
                        final Gui func_178021_b = func_178070_d.func_178021_b();
                        if (func_178021_b instanceof GuiButton) {
                            final GuiButton guiButton3 = (GuiButton)func_178021_b;
                            if (guiButton3 instanceof GuiSlider) {
                                ((GuiSlider)guiButton3).func_175219_a(MathHelper.clamp_float(((GuiSlider)guiButton3).func_175217_d() * (0.75f + this.field_175337_G.nextFloat() * 0.5f) + (this.field_175337_G.nextFloat() * 0.1f - 0.05f), 0.0f, 1.0f));
                            }
                            else if (guiButton3 instanceof GuiListButton) {
                                ((GuiListButton)guiButton3).func_175212_b(this.field_175337_G.nextBoolean());
                            }
                        }
                        int n = 0;
                        ++n;
                    }
                }
                case 302: {
                    this.field_175349_r.func_178071_h();
                    this.func_175328_i();
                    break;
                }
                case 303: {
                    this.field_175349_r.func_178064_i();
                    this.func_175328_i();
                    break;
                }
                case 304: {
                    if (this.field_175338_A) {
                        this.func_175322_b(304);
                        break;
                    }
                    break;
                }
                case 305: {
                    GuiCustomizeWorldScreen.mc.displayGuiScreen(new GuiScreenCustomizePresets(this));
                    break;
                }
                case 306: {
                    this.func_175331_h();
                    break;
                }
                case 307: {
                    this.field_175339_B = 0;
                    this.func_175331_h();
                    break;
                }
            }
        }
    }
    
    private void func_175326_g() {
        this.field_175336_F.func_177863_a();
        this.func_175325_f();
    }
    
    private void func_175322_b(final int field_175339_B) {
        this.field_175339_B = field_175339_B;
        this.func_175329_a(true);
    }
    
    private void func_175331_h() throws IOException {
        switch (this.field_175339_B) {
            case 300: {
                this.actionPerformed((GuiButton)this.field_175349_r.func_178061_c(300));
                break;
            }
            case 304: {
                this.func_175326_g();
                break;
            }
        }
        this.field_175339_B = 0;
        this.field_175340_C = true;
        this.func_175329_a(false);
    }
    
    private void func_175329_a(final boolean b) {
        this.field_175352_x.visible = b;
        this.field_175351_y.visible = b;
        this.field_175347_t.enabled = !b;
        this.field_175348_s.enabled = !b;
        this.field_175345_v.enabled = !b;
        this.field_175344_w.enabled = !b;
        this.field_175346_u.enabled = !b;
        this.field_175350_z.enabled = !b;
    }
    
    private void func_175328_i() {
        this.field_175345_v.enabled = (this.field_175349_r.func_178059_e() != 0);
        this.field_175344_w.enabled = (this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1);
        this.field_175333_f = I18n.format("book.pageIndicator", this.field_175349_r.func_178059_e() + 1, this.field_175349_r.func_178057_f());
        this.field_175335_g = this.field_175342_h[this.field_175349_r.func_178059_e()];
        this.field_175347_t.enabled = (this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        if (this.field_175339_B == 0) {
            switch (n) {
                case 200: {
                    this.func_175327_a(1.0f);
                    break;
                }
                case 208: {
                    this.func_175327_a(-1.0f);
                    break;
                }
                default: {
                    this.field_175349_r.func_178062_a(c, n);
                    break;
                }
            }
        }
    }
    
    private void func_175327_a(final float n) {
        final Gui func_178056_g = this.field_175349_r.func_178056_g();
        if (func_178056_g instanceof GuiTextField) {
            float n2 = n;
            if (GuiScreen.isShiftKeyDown()) {
                n2 = n * 0.1f;
                if (GuiScreen.isCtrlKeyDown()) {
                    n2 *= 0.1f;
                }
            }
            else if (GuiScreen.isCtrlKeyDown()) {
                n2 = n * 10.0f;
                if (GuiScreen.func_175283_s()) {
                    n2 *= 10.0f;
                }
            }
            final GuiTextField guiTextField = (GuiTextField)func_178056_g;
            final Float tryParse = Floats.tryParse(guiTextField.getText());
            if (tryParse != null) {
                final Float value = tryParse + n2;
                final int func_175206_d = guiTextField.func_175206_d();
                final String func_175330_b = this.func_175330_b(guiTextField.func_175206_d(), value);
                guiTextField.setText(func_175330_b);
                this.func_175319_a(func_175206_d, func_175330_b);
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (this.field_175339_B == 0 && !this.field_175340_C) {
            this.field_175349_r.func_148179_a(n, n2, n3);
        }
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
        if (this.field_175340_C) {
            this.field_175340_C = false;
        }
        else if (this.field_175339_B == 0) {
            this.field_175349_r.func_148181_b(n, n2, n3);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_175349_r.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, this.field_175341_a, GuiCustomizeWorldScreen.width / 2, 2, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, this.field_175333_f, GuiCustomizeWorldScreen.width / 2, 12, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, this.field_175335_g, GuiCustomizeWorldScreen.width / 2, 22, 16777215);
        super.drawScreen(n, n2, n3);
        if (this.field_175339_B != 0) {
            Gui.drawRect(0, 0, GuiCustomizeWorldScreen.width, GuiCustomizeWorldScreen.height, Integer.MIN_VALUE);
            this.drawHorizontalLine(GuiCustomizeWorldScreen.width / 2 - 91, GuiCustomizeWorldScreen.width / 2 + 90, 99, -2039584);
            this.drawHorizontalLine(GuiCustomizeWorldScreen.width / 2 - 91, GuiCustomizeWorldScreen.width / 2 + 90, 185, -6250336);
            this.drawVerticalLine(GuiCustomizeWorldScreen.width / 2 - 91, 99, 185, -2039584);
            this.drawVerticalLine(GuiCustomizeWorldScreen.width / 2 + 90, 99, 185, -6250336);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            GuiCustomizeWorldScreen.mc.getTextureManager().bindTexture(GuiCustomizeWorldScreen.optionsBackground);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            worldRenderer.startDrawingQuads();
            worldRenderer.func_178991_c(4210752);
            worldRenderer.addVertexWithUV(GuiCustomizeWorldScreen.width / 2 - 90, 185.0, 0.0, 0.0, 2.65625);
            worldRenderer.addVertexWithUV(GuiCustomizeWorldScreen.width / 2 + 90, 185.0, 0.0, 5.625, 2.65625);
            worldRenderer.addVertexWithUV(GuiCustomizeWorldScreen.width / 2 + 90, 100.0, 0.0, 5.625, 0.0);
            worldRenderer.addVertexWithUV(GuiCustomizeWorldScreen.width / 2 - 90, 100.0, 0.0, 0.0, 0.0);
            instance.draw();
            Gui.drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirmTitle", new Object[0]), GuiCustomizeWorldScreen.width / 2, 105, 16777215);
            Gui.drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm1", new Object[0]), GuiCustomizeWorldScreen.width / 2, 125, 16777215);
            Gui.drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm2", new Object[0]), GuiCustomizeWorldScreen.width / 2, 135, 16777215);
            this.field_175352_x.drawButton(GuiCustomizeWorldScreen.mc, n, n2);
            this.field_175351_y.drawButton(GuiCustomizeWorldScreen.mc, n, n2);
        }
    }
    
    static {
        __OBFID = "CL_00001934";
    }
}
