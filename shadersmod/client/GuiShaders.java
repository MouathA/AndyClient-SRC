package shadersmod.client;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import optifine.*;
import java.net.*;
import net.minecraft.client.*;
import java.io.*;
import org.lwjgl.*;
import java.lang.reflect.*;
import net.minecraft.client.gui.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class GuiShaders extends GuiScreen
{
    protected GuiScreen parentGui;
    protected String screenTitle;
    private int updateTimer;
    private GuiSlotShaders shaderList;
    private static float[] QUALITY_MULTIPLIERS;
    private static String[] QUALITY_MULTIPLIER_NAMES;
    private static float[] HAND_DEPTH_VALUES;
    private static String[] HAND_DEPTH_NAMES;
    public static final int EnumOS_UNKNOWN;
    public static final int EnumOS_WINDOWS;
    public static final int EnumOS_OSX;
    public static final int EnumOS_SOLARIS;
    public static final int EnumOS_LINUX;
    private static final String[] lIIllIlllIlIlIIl;
    private static String[] lIIllIlllIllIIII;
    
    static {
        lllIIlIlIIIlllIl();
        lllIIlIlIIIllIll();
        EnumOS_SOLARIS = 3;
        EnumOS_LINUX = 4;
        EnumOS_WINDOWS = 1;
        EnumOS_UNKNOWN = 0;
        EnumOS_OSX = 2;
        GuiShaders.QUALITY_MULTIPLIERS = new float[] { 0.5f, 0.70710677f, 1.0f, 1.4142135f, 2.0f };
        GuiShaders.QUALITY_MULTIPLIER_NAMES = new String[] { GuiShaders.lIIllIlllIlIlIIl[0], GuiShaders.lIIllIlllIlIlIIl[1], GuiShaders.lIIllIlllIlIlIIl[2], GuiShaders.lIIllIlllIlIlIIl[3], GuiShaders.lIIllIlllIlIlIIl[4] };
        GuiShaders.HAND_DEPTH_VALUES = new float[] { 0.0625f, 0.125f, 0.25f };
        GuiShaders.HAND_DEPTH_NAMES = new String[] { GuiShaders.lIIllIlllIlIlIIl[5], GuiShaders.lIIllIlllIlIlIIl[6], GuiShaders.lIIllIlllIlIlIIl[7] };
    }
    
    public GuiShaders(final GuiScreen parentGui, final GameSettings gameSettings) {
        this.screenTitle = GuiShaders.lIIllIlllIlIlIIl[8];
        this.updateTimer = -1;
        this.parentGui = parentGui;
    }
    
    @Override
    public void initGui() {
        this.screenTitle = I18n.format(GuiShaders.lIIllIlllIlIlIIl[9], new Object[0]);
        if (Shaders.shadersConfig == null) {
            Shaders.loadConfig();
        }
        final int n = 120;
        final int n2 = 20;
        final int n3 = GuiShaders.width - n - 10;
        final int n4 = 30;
        final int n5 = 20;
        final int n6 = GuiShaders.width - n - 20;
        (this.shaderList = new GuiSlotShaders(this, n6, GuiShaders.height, n4, GuiShaders.height - 50, 16)).registerScrollButtons(7, 8);
        this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, n3, 0 * n5 + n4, n, n2));
        this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, n3, 1 * n5 + n4, n, n2));
        this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, n3, 2 * n5 + n4, n, n2));
        this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, n3, 3 * n5 + n4, n, n2));
        this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, n3, 4 * n5 + n4, n, n2));
        this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, n3, 5 * n5 + n4, n, n2));
        this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, n3, 6 * n5 + n4, n, n2));
        final int min = Math.min(150, n6 / 2 - 10);
        this.buttonList.add(new GuiButton(201, n6 / 4 - min / 2, GuiShaders.height - 25, min, n2, Lang.get(GuiShaders.lIIllIlllIlIlIIl[10])));
        this.buttonList.add(new GuiButton(202, n6 / 4 * 3 - min / 2, GuiShaders.height - 25, min, n2, I18n.format(GuiShaders.lIIllIlllIlIlIIl[11], new Object[0])));
        this.buttonList.add(new GuiButton(203, n3, GuiShaders.height - 25, n, n2, Lang.get(GuiShaders.lIIllIlllIlIlIIl[12])));
        this.updateButtons();
    }
    
    public void updateButtons() {
        final boolean shaders = Config.isShaders();
        for (final GuiButton guiButton : this.buttonList) {
            if (guiButton.id != 201 && guiButton.id != 202 && guiButton.id != EnumShaderOption.ANTIALIASING.ordinal()) {
                guiButton.enabled = shaders;
            }
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.shaderList.func_178039_p();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton instanceof GuiButtonEnumShaderOption) {
                final GuiButtonEnumShaderOption guiButtonEnumShaderOption = (GuiButtonEnumShaderOption)guiButton;
                switch (NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[guiButtonEnumShaderOption.getEnumShaderOption().ordinal()]) {
                    case 1: {
                        Shaders.nextAntialiasingLevel();
                        Shaders.uninit();
                        break;
                    }
                    case 2: {
                        Shaders.configNormalMap = !Shaders.configNormalMap;
                        GuiShaders.mc.func_175603_A();
                        break;
                    }
                    case 3: {
                        Shaders.configSpecularMap = !Shaders.configSpecularMap;
                        GuiShaders.mc.func_175603_A();
                        break;
                    }
                    case 4: {
                        final float configRenderResMul = Shaders.configRenderResMul;
                        final float[] quality_MULTIPLIERS = GuiShaders.QUALITY_MULTIPLIERS;
                        final String[] quality_MULTIPLIER_NAMES = GuiShaders.QUALITY_MULTIPLIER_NAMES;
                        int valueIndex = getValueIndex(configRenderResMul, quality_MULTIPLIERS);
                        if (isShiftKeyDown()) {
                            if (--valueIndex < 0) {
                                valueIndex = quality_MULTIPLIERS.length - 1;
                            }
                        }
                        else if (++valueIndex >= quality_MULTIPLIERS.length) {
                            valueIndex = 0;
                        }
                        Shaders.configRenderResMul = quality_MULTIPLIERS[valueIndex];
                        Shaders.scheduleResize();
                        break;
                    }
                    case 5: {
                        final float configShadowResMul = Shaders.configShadowResMul;
                        final float[] quality_MULTIPLIERS2 = GuiShaders.QUALITY_MULTIPLIERS;
                        final String[] quality_MULTIPLIER_NAMES2 = GuiShaders.QUALITY_MULTIPLIER_NAMES;
                        int valueIndex2 = getValueIndex(configShadowResMul, quality_MULTIPLIERS2);
                        if (isShiftKeyDown()) {
                            if (--valueIndex2 < 0) {
                                valueIndex2 = quality_MULTIPLIERS2.length - 1;
                            }
                        }
                        else if (++valueIndex2 >= quality_MULTIPLIERS2.length) {
                            valueIndex2 = 0;
                        }
                        Shaders.configShadowResMul = quality_MULTIPLIERS2[valueIndex2];
                        Shaders.scheduleResizeShadow();
                        break;
                    }
                    case 6: {
                        final float configHandDepthMul = Shaders.configHandDepthMul;
                        final float[] hand_DEPTH_VALUES = GuiShaders.HAND_DEPTH_VALUES;
                        final String[] hand_DEPTH_NAMES = GuiShaders.HAND_DEPTH_NAMES;
                        int valueIndex3 = getValueIndex(configHandDepthMul, hand_DEPTH_VALUES);
                        if (isShiftKeyDown()) {
                            if (--valueIndex3 < 0) {
                                valueIndex3 = hand_DEPTH_VALUES.length - 1;
                            }
                        }
                        else if (++valueIndex3 >= hand_DEPTH_VALUES.length) {
                            valueIndex3 = 0;
                        }
                        Shaders.configHandDepthMul = hand_DEPTH_VALUES[valueIndex3];
                        break;
                    }
                    case 7: {
                        Shaders.configCloudShadow = !Shaders.configCloudShadow;
                        break;
                    }
                    case 8: {
                        Shaders.configOldLighting.nextValue();
                        Shaders.updateBlockLightLevel();
                        GuiShaders.mc.func_175603_A();
                        break;
                    }
                    case 9: {
                        Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
                        break;
                    }
                    case 10: {
                        Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
                        Shaders.configTexMinFilN = (Shaders.configTexMinFilS = Shaders.configTexMinFilB);
                        guiButton.displayString = GuiShaders.lIIllIlllIlIlIIl[13] + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
                        ShadersTex.updateTextureMinMagFilter();
                        break;
                    }
                    case 11: {
                        Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
                        guiButton.displayString = GuiShaders.lIIllIlllIlIlIIl[14] + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
                        ShadersTex.updateTextureMinMagFilter();
                        break;
                    }
                    case 12: {
                        Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
                        guiButton.displayString = GuiShaders.lIIllIlllIlIlIIl[15] + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
                        ShadersTex.updateTextureMinMagFilter();
                        break;
                    }
                    case 13: {
                        Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
                        guiButton.displayString = GuiShaders.lIIllIlllIlIlIIl[16] + toStringOnOff(Shaders.configShadowClipFrustrum);
                        ShadersTex.updateTextureMinMagFilter();
                        break;
                    }
                }
                guiButtonEnumShaderOption.updateButtonText();
            }
            else {
                switch (guiButton.id) {
                    case 201: {
                        switch (getOSType()) {
                            case 1: {
                                final String format = String.format(GuiShaders.lIIllIlllIlIlIIl[17], Shaders.shaderpacksdir.getAbsolutePath());
                                try {
                                    Runtime.getRuntime().exec(format);
                                    return;
                                }
                                catch (IOException ex) {
                                    ex.printStackTrace();
                                    break;
                                }
                            }
                            case 2: {
                                try {
                                    Runtime.getRuntime().exec(new String[] { GuiShaders.lIIllIlllIlIlIIl[18], Shaders.shaderpacksdir.getAbsolutePath() });
                                    return;
                                }
                                catch (IOException ex2) {
                                    ex2.printStackTrace();
                                }
                                break;
                            }
                        }
                        boolean b = false;
                        try {
                            final Class<?> forName = Class.forName(GuiShaders.lIIllIlllIlIlIIl[19]);
                            final Object invoke = forName.getMethod(GuiShaders.lIIllIlllIlIlIIl[20], (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                            final Method method = forName.getMethod(GuiShaders.lIIllIlllIlIlIIl[21], URI.class);
                            final Object o = invoke;
                            final Object[] array = { null };
                            final int n = 0;
                            final Minecraft mc = GuiShaders.mc;
                            array[n] = new File(Minecraft.mcDataDir, Shaders.shaderpacksdirname).toURI();
                            method.invoke(o, array);
                        }
                        catch (Throwable t) {
                            t.printStackTrace();
                            b = true;
                        }
                        if (b) {
                            Config.dbg(GuiShaders.lIIllIlllIlIlIIl[22]);
                            Sys.openURL(GuiShaders.lIIllIlllIlIlIIl[23] + Shaders.shaderpacksdir.getAbsolutePath());
                            break;
                        }
                        break;
                    }
                    case 202: {
                        new File(Shaders.shadersdir, GuiShaders.lIIllIlllIlIlIIl[24]);
                        try {
                            Shaders.storeConfig();
                        }
                        catch (Exception ex3) {}
                        GuiShaders.mc.displayGuiScreen(this.parentGui);
                        break;
                    }
                    case 203: {
                        Config.getMinecraft().displayGuiScreen(new GuiShaderOptions(this, Config.getGameSettings()));
                        break;
                    }
                    default: {
                        this.shaderList.actionPerformed(guiButton);
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.shaderList.drawScreen(n, n2, n3);
        if (this.updateTimer <= 0) {
            this.shaderList.updateList();
            this.updateTimer += 20;
        }
        Gui.drawCenteredString(this.fontRendererObj, String.valueOf(this.screenTitle) + GuiShaders.lIIllIlllIlIlIIl[25], GuiShaders.width / 2, 15, 16777215);
        final String string = GuiShaders.lIIllIlllIlIlIIl[26] + Shaders.glVersionString + GuiShaders.lIIllIlllIlIlIIl[27] + Shaders.glVendorString + GuiShaders.lIIllIlllIlIlIIl[28] + Shaders.glRendererString;
        if (this.fontRendererObj.getStringWidth(string) < GuiShaders.width - 5) {
            Gui.drawCenteredString(this.fontRendererObj, string, GuiShaders.width / 2, GuiShaders.height - 40, 8421504);
        }
        else {
            this.drawString(this.fontRendererObj, string, 5, GuiShaders.height - 40, 8421504);
        }
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        --this.updateTimer;
    }
    
    public Minecraft getMc() {
        return GuiShaders.mc;
    }
    
    public void drawCenteredString(final String s, final int n, final int n2, final int n3) {
        Gui.drawCenteredString(this.fontRendererObj, s, n, n2, n3);
    }
    
    public static String toStringOnOff(final boolean b) {
        final String on = Lang.getOn();
        final String off = Lang.getOff();
        return b ? on : off;
    }
    
    public static String toStringAa(final int n) {
        return (n == 2) ? GuiShaders.lIIllIlllIlIlIIl[29] : ((n == 4) ? GuiShaders.lIIllIlllIlIlIIl[30] : Lang.getOff());
    }
    
    public static String toStringValue(final float n, final float[] array, final String[] array2) {
        return array2[getValueIndex(n, array)];
    }
    
    public static int getValueIndex(final float n, final float[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] >= n) {
                return i;
            }
        }
        return array.length - 1;
    }
    
    public static String toStringQuality(final float n) {
        return toStringValue(n, GuiShaders.QUALITY_MULTIPLIERS, GuiShaders.QUALITY_MULTIPLIER_NAMES);
    }
    
    public static String toStringHandDepth(final float n) {
        return toStringValue(n, GuiShaders.HAND_DEPTH_VALUES, GuiShaders.HAND_DEPTH_NAMES);
    }
    
    public static int getOSType() {
        final String lowerCase = System.getProperty(GuiShaders.lIIllIlllIlIlIIl[31]).toLowerCase();
        return lowerCase.contains(GuiShaders.lIIllIlllIlIlIIl[32]) ? 1 : (lowerCase.contains(GuiShaders.lIIllIlllIlIlIIl[33]) ? 2 : (lowerCase.contains(GuiShaders.lIIllIlllIlIlIIl[34]) ? 3 : (lowerCase.contains(GuiShaders.lIIllIlllIlIlIIl[35]) ? 3 : (lowerCase.contains(GuiShaders.lIIllIlllIlIlIIl[36]) ? 4 : (lowerCase.contains(GuiShaders.lIIllIlllIlIlIIl[37]) ? 4 : 0)))));
    }
    
    private static void lllIIlIlIIIllIll() {
        (lIIllIlllIlIlIIl = new String[38])[0] = lllIIlIlIIIIllll(GuiShaders.lIIllIlllIllIIII[0], GuiShaders.lIIllIlllIllIIII[1]);
        GuiShaders.lIIllIlllIlIlIIl[1] = lllIIlIlIIIlIIII(GuiShaders.lIIllIlllIllIIII[2], GuiShaders.lIIllIlllIllIIII[3]);
        GuiShaders.lIIllIlllIlIlIIl[2] = lllIIlIlIIIlIIIl(GuiShaders.lIIllIlllIllIIII[4], GuiShaders.lIIllIlllIllIIII[5]);
        GuiShaders.lIIllIlllIlIlIIl[3] = lllIIlIlIIIlIIlI(GuiShaders.lIIllIlllIllIIII[6], GuiShaders.lIIllIlllIllIIII[7]);
        GuiShaders.lIIllIlllIlIlIIl[4] = lllIIlIlIIIlIIII(GuiShaders.lIIllIlllIllIIII[8], GuiShaders.lIIllIlllIllIIII[9]);
        GuiShaders.lIIllIlllIlIlIIl[5] = lllIIlIlIIIlIIIl(GuiShaders.lIIllIlllIllIIII[10], GuiShaders.lIIllIlllIllIIII[11]);
        GuiShaders.lIIllIlllIlIlIIl[6] = lllIIlIlIIIIllll(GuiShaders.lIIllIlllIllIIII[12], GuiShaders.lIIllIlllIllIIII[13]);
        GuiShaders.lIIllIlllIlIlIIl[7] = lllIIlIlIIIIllll(GuiShaders.lIIllIlllIllIIII[14], GuiShaders.lIIllIlllIllIIII[15]);
        GuiShaders.lIIllIlllIlIlIIl[8] = lllIIlIlIIIlIIlI(GuiShaders.lIIllIlllIllIIII[16], GuiShaders.lIIllIlllIllIIII[17]);
        GuiShaders.lIIllIlllIlIlIIl[9] = lllIIlIlIIIlIIIl(GuiShaders.lIIllIlllIllIIII[18], GuiShaders.lIIllIlllIllIIII[19]);
        GuiShaders.lIIllIlllIlIlIIl[10] = lllIIlIlIIIIllll(GuiShaders.lIIllIlllIllIIII[20], GuiShaders.lIIllIlllIllIIII[21]);
        GuiShaders.lIIllIlllIlIlIIl[11] = lllIIlIlIIIlIIIl(GuiShaders.lIIllIlllIllIIII[22], GuiShaders.lIIllIlllIllIIII[23]);
        GuiShaders.lIIllIlllIlIlIIl[12] = lllIIlIlIIIlIIIl(GuiShaders.lIIllIlllIllIIII[24], GuiShaders.lIIllIlllIllIIII[25]);
        GuiShaders.lIIllIlllIlIlIIl[13] = lllIIlIlIIIlIIIl(GuiShaders.lIIllIlllIllIIII[26], GuiShaders.lIIllIlllIllIIII[27]);
        GuiShaders.lIIllIlllIlIlIIl[14] = lllIIlIlIIIlIIlI(GuiShaders.lIIllIlllIllIIII[28], GuiShaders.lIIllIlllIllIIII[29]);
        GuiShaders.lIIllIlllIlIlIIl[15] = lllIIlIlIIIlIIIl(GuiShaders.lIIllIlllIllIIII[30], GuiShaders.lIIllIlllIllIIII[31]);
        GuiShaders.lIIllIlllIlIlIIl[16] = lllIIlIlIIIlIIlI(GuiShaders.lIIllIlllIllIIII[32], GuiShaders.lIIllIlllIllIIII[33]);
        GuiShaders.lIIllIlllIlIlIIl[17] = lllIIlIlIIIlIIIl("EisLSQcJI09IIVE1GwYQBWZNKBIUKE8BCx0jTUdAVDVN", "qFogb");
        GuiShaders.lIIllIlllIlIlIIl[18] = lllIIlIlIIIlIIIl("awcGHWMmGxtAIzQXGw==", "DruoL");
        GuiShaders.lIIllIlllIlIlIIl[19] = lllIIlIlIIIlIIII("sw1WJv2QKGp6PR0GeCRXnYYHal3WfGJMfCtLm6IKJnk=", "fimnT");
        GuiShaders.lIIllIlllIlIlIIl[20] = lllIIlIlIIIlIIIl("LDMYFQ04PRg+GA==", "KVlQh");
        GuiShaders.lIIllIlllIlIlIIl[21] = lllIIlIlIIIlIIII("93nACdiQLIPlIz8MOsucQw==", "gfxwD");
        GuiShaders.lIIllIlllIlIlIIl[22] = lllIIlIlIIIIllll("1up2NXOOuXL4s0hB/p8B1SlLg1QkCg5iG29S1hwgaLU=", "pGttP");
        GuiShaders.lIIllIlllIlIlIIl[23] = lllIIlIlIIIIllll("HA0VA3FUQ1A=", "djZaC");
        GuiShaders.lIIllIlllIlIlIIl[24] = lllIIlIlIIIIllll("rg4iUx0StilZK1gBnVxDyA==", "xHwPy");
        GuiShaders.lIIllIlllIlIlIIl[25] = lllIIlIlIIIlIIII("TB6nCvfGWJRyENMvWmUtfw==", "bAOmb");
        GuiShaders.lIIllIlllIlIlIIl[26] = lllIIlIlIIIlIIlI("NPvnP+GL3wE7Z5QBY1XhWQ==", "dbbrc");
        GuiShaders.lIIllIlllIlIlIIl[27] = lllIIlIlIIIlIIlI("W0aiTJx6pWQ=", "ZArBj");
        GuiShaders.lIIllIlllIlIlIIl[28] = lllIIlIlIIIlIIlI("HjAD79cB8dU=", "jiLhW");
        GuiShaders.lIIllIlllIlIlIIl[29] = lllIIlIlIIIIllll("fSjf+C6e/VU=", "pnjEF");
        GuiShaders.lIIllIlllIlIlIIl[30] = lllIIlIlIIIlIIII("vcsLR+edigZxm8Gk70QInw==", "ruRmH");
        GuiShaders.lIIllIlllIlIlIIl[31] = lllIIlIlIIIIllll("xbtvABM9kHM=", "vqXEB");
        GuiShaders.lIIllIlllIlIlIIl[32] = lllIIlIlIIIIllll("95Fp9rf1whc=", "yrekb");
        GuiShaders.lIIllIlllIlIlIIl[33] = lllIIlIlIIIIllll("GjJHT3MIBG8=", "blNtg");
        GuiShaders.lIIllIlllIlIlIIl[34] = lllIIlIlIIIlIIlI("8rQrN3lLyKI=", "OfUyL");
        GuiShaders.lIIllIlllIlIlIIl[35] = lllIIlIlIIIlIIlI("Y40bW25ranQ=", "gqvtE");
        GuiShaders.lIIllIlllIlIlIIl[36] = lllIIlIlIIIlIIIl("LwA8Gjk=", "CiRoA");
        GuiShaders.lIIllIlllIlIlIIl[37] = lllIIlIlIIIlIIII("Z0uNPefZiNR3e2xcwEmE7g==", "bZXmJ");
        GuiShaders.lIIllIlllIllIIII = null;
    }
    
    private static void lllIIlIlIIIlllIl() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        GuiShaders.lIIllIlllIllIIII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lllIIlIlIIIlIIlI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lllIIlIlIIIIllll(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lllIIlIlIIIlIIIl(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
    
    private static String lllIIlIlIIIlIIII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    static class NamelessClass1647571870
    {
        static final int[] $SwitchMap$shadersmod$client$EnumShaderOption;
        
        static {
            $SwitchMap$shadersmod$client$EnumShaderOption = new int[EnumShaderOption.values().length];
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.ANTIALIASING.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.NORMAL_MAP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.SPECULAR_MAP.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.RENDER_RES_MUL.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.SHADOW_RES_MUL.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.HAND_DEPTH_MUL.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.CLOUD_SHADOW.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.OLD_LIGHTING.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TWEAK_BLOCK_DAMAGE.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MIN_FIL_B.ordinal()] = 10;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MAG_FIL_N.ordinal()] = 11;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MAG_FIL_S.ordinal()] = 12;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                NamelessClass1647571870.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.SHADOW_CLIP_FRUSTRUM.ordinal()] = 13;
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
        }
    }
}
