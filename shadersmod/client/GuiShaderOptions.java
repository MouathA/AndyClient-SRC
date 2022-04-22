package shadersmod.client;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import java.io.*;
import java.util.*;
import optifine.*;
import net.minecraft.client.gui.*;

public class GuiShaderOptions extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    private String screenName;
    private String screenText;
    private boolean changed;
    public static final String OPTION_PROFILE;
    public static final String OPTION_EMPTY;
    public static final String OPTION_REST;
    
    public GuiShaderOptions(final GuiScreen prevScreen, final GameSettings settings) {
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.screenName = null;
        this.screenText = null;
        this.changed = false;
        this.title = "Shader Options";
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    public GuiShaderOptions(final GuiScreen guiScreen, final GameSettings gameSettings, final String screenName) {
        this(guiScreen, gameSettings);
        this.screenName = screenName;
        if (screenName != null) {
            this.screenText = Shaders.translate("screen." + screenName, screenName);
        }
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.shaderOptionsTitle", new Object[0]);
        final int n = GuiShaderOptions.width - 130;
        final ShaderOption[] shaderPackOptions = Shaders.getShaderPackOptions(this.screenName);
        if (shaderPackOptions != null) {
            if (shaderPackOptions.length > 18) {
                final int n2 = shaderPackOptions.length / 9 + 1;
            }
            while (0 < shaderPackOptions.length) {
                final ShaderOption shaderOption = shaderPackOptions[0];
                if (shaderOption != null && shaderOption.isVisible()) {
                    final int min = Math.min(GuiShaderOptions.width / 2, 200);
                    final int n3 = 0 * min + 5 + (GuiShaderOptions.width - min * 2) / 2;
                    final int n4 = min - 10;
                    final GuiButtonShaderOption guiButtonShaderOption = new GuiButtonShaderOption(100, n3, 30, n4, 20, shaderOption, this.getButtonText(shaderOption, n4));
                    guiButtonShaderOption.enabled = shaderOption.isEnabled();
                    this.buttonList.add(guiButtonShaderOption);
                }
                int n5 = 0;
                ++n5;
            }
        }
        this.buttonList.add(new GuiButton(201, GuiShaderOptions.width / 2 - 120 - 20, GuiShaderOptions.height / 6 + 168 + 11, 120, 20, I18n.format("controls.reset", new Object[0])));
        this.buttonList.add(new GuiButton(200, GuiShaderOptions.width / 2 + 20, GuiShaderOptions.height / 6 + 168 + 11, 120, 20, I18n.format("gui.done", new Object[0])));
    }
    
    private String getButtonText(final ShaderOption shaderOption, final int n) {
        String s = shaderOption.getNameText();
        if (shaderOption instanceof ShaderOptionScreen) {
            final ShaderOptionScreen shaderOptionScreen = (ShaderOptionScreen)shaderOption;
            return String.valueOf(s) + "...";
        }
        Config.getMinecraft();
        for (FontRenderer fontRendererObj = Minecraft.fontRendererObj; fontRendererObj.getStringWidth(s) + (fontRendererObj.getStringWidth(": " + Lang.getOff()) + 5) >= n && s.length() > 0; s = s.substring(0, s.length() - 1)) {}
        return String.valueOf(s) + ": " + (shaderOption.isChanged() ? shaderOption.getValueColor(shaderOption.getValue()) : "") + shaderOption.getValueText(shaderOption.getValue());
    }
    
    @Override
    protected void actionPerformed(final GuiButton p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/gui/GuiButton.enabled:Z
        //     4: ifeq            157
        //     7: aload_1        
        //     8: getfield        net/minecraft/client/gui/GuiButton.id:I
        //    11: sipush          200
        //    14: if_icmpge       85
        //    17: aload_1        
        //    18: instanceof      Lshadersmod/client/GuiButtonShaderOption;
        //    21: ifeq            85
        //    24: aload_1        
        //    25: checkcast       Lshadersmod/client/GuiButtonShaderOption;
        //    28: astore_2       
        //    29: aload_2        
        //    30: invokevirtual   shadersmod/client/GuiButtonShaderOption.getShaderOption:()Lshadersmod/client/ShaderOption;
        //    33: astore_3       
        //    34: aload_3        
        //    35: instanceof      Lshadersmod/client/ShaderOptionScreen;
        //    38: ifeq            72
        //    41: aload_3        
        //    42: invokevirtual   shadersmod/client/ShaderOption.getName:()Ljava/lang/String;
        //    45: astore          4
        //    47: new             Lshadersmod/client/GuiShaderOptions;
        //    50: dup            
        //    51: aload_0        
        //    52: aload_0        
        //    53: getfield        shadersmod/client/GuiShaderOptions.settings:Lnet/minecraft/client/settings/GameSettings;
        //    56: aload           4
        //    58: invokespecial   shadersmod/client/GuiShaderOptions.<init>:(Lnet/minecraft/client/gui/GuiScreen;Lnet/minecraft/client/settings/GameSettings;Ljava/lang/String;)V
        //    61: astore          5
        //    63: getstatic       shadersmod/client/GuiShaderOptions.mc:Lnet/minecraft/client/Minecraft;
        //    66: aload           5
        //    68: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //    71: return         
        //    72: aload_3        
        //    73: invokevirtual   shadersmod/client/ShaderOption.nextValue:()V
        //    76: aload_0        
        //    77: invokespecial   shadersmod/client/GuiShaderOptions.updateAllButtons:()V
        //    80: aload_0        
        //    81: iconst_1       
        //    82: putfield        shadersmod/client/GuiShaderOptions.changed:Z
        //    85: aload_1        
        //    86: getfield        net/minecraft/client/gui/GuiButton.id:I
        //    89: sipush          201
        //    92: if_icmpne       133
        //    95: invokestatic    shadersmod/client/Shaders.getShaderPackOptions:()[Lshadersmod/client/ShaderOption;
        //    98: invokestatic    shadersmod/client/Shaders.getChangedOptions:([Lshadersmod/client/ShaderOption;)[Lshadersmod/client/ShaderOption;
        //   101: astore_2       
        //   102: goto            123
        //   105: aload_2        
        //   106: iconst_0       
        //   107: aaload         
        //   108: astore          4
        //   110: aload           4
        //   112: invokevirtual   shadersmod/client/ShaderOption.resetValue:()V
        //   115: aload_0        
        //   116: iconst_1       
        //   117: putfield        shadersmod/client/GuiShaderOptions.changed:Z
        //   120: iinc            3, 1
        //   123: iconst_0       
        //   124: aload_2        
        //   125: arraylength    
        //   126: if_icmplt       105
        //   129: aload_0        
        //   130: invokespecial   shadersmod/client/GuiShaderOptions.updateAllButtons:()V
        //   133: aload_1        
        //   134: getfield        net/minecraft/client/gui/GuiButton.id:I
        //   137: sipush          200
        //   140: if_icmpne       157
        //   143: aload_0        
        //   144: getfield        shadersmod/client/GuiShaderOptions.changed:Z
        //   147: getstatic       shadersmod/client/GuiShaderOptions.mc:Lnet/minecraft/client/Minecraft;
        //   150: aload_0        
        //   151: getfield        shadersmod/client/GuiShaderOptions.prevScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   154: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //   157: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0157 (coming from #0154).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (n3 == 1) {
            final GuiButton selectedButton = this.getSelectedButton(n, n2);
            if (selectedButton instanceof GuiButtonShaderOption) {
                final GuiButtonShaderOption guiButtonShaderOption = (GuiButtonShaderOption)selectedButton;
                final ShaderOption shaderOption = guiButtonShaderOption.getShaderOption();
                if (shaderOption.isChanged()) {
                    final GuiButtonShaderOption guiButtonShaderOption2 = guiButtonShaderOption;
                    final Minecraft mc = GuiShaderOptions.mc;
                    guiButtonShaderOption2.playPressSound(Minecraft.getSoundHandler());
                    shaderOption.resetValue();
                    this.changed = true;
                    this.updateAllButtons();
                }
            }
        }
    }
    
    private void updateAllButtons() {
        for (final GuiButton guiButton : this.buttonList) {
            if (guiButton instanceof GuiButtonShaderOption) {
                final GuiButtonShaderOption guiButtonShaderOption = (GuiButtonShaderOption)guiButton;
                final ShaderOption shaderOption = guiButtonShaderOption.getShaderOption();
                if (shaderOption instanceof ShaderOptionProfile) {
                    ((ShaderOptionProfile)shaderOption).updateProfile();
                }
                guiButtonShaderOption.displayString = this.getButtonText(shaderOption, guiButtonShaderOption.getButtonWidth());
            }
        }
    }
    
    @Override
    public void drawScreen(final int lastMouseX, final int lastMouseY, final float n) {
        this.drawDefaultBackground();
        if (this.screenText != null) {
            Gui.drawCenteredString(this.fontRendererObj, this.screenText, GuiShaderOptions.width / 2, 15, 16777215);
        }
        else {
            Gui.drawCenteredString(this.fontRendererObj, this.title, GuiShaderOptions.width / 2, 15, 16777215);
        }
        super.drawScreen(lastMouseX, lastMouseY, n);
        if (Math.abs(lastMouseX - this.lastMouseX) <= 5 && Math.abs(lastMouseY - this.lastMouseY) <= 5) {
            this.drawTooltips(lastMouseX, lastMouseY, this.buttonList);
        }
        else {
            this.lastMouseX = lastMouseX;
            this.lastMouseY = lastMouseY;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }
    
    private void drawTooltips(final int n, final int n2, final List list) {
        if (System.currentTimeMillis() >= this.mouseStillTime + 700) {
            final int n3 = GuiShaderOptions.width / 2 - 150;
            int n4 = GuiShaderOptions.height / 6 - 7;
            if (n2 <= n4 + 98) {
                n4 += 105;
            }
            final int n5 = n3 + 150 + 150;
            final int n6 = n4 + 84 + 10;
            final GuiButton selectedButton = this.getSelectedButton(n, n2);
            if (selectedButton instanceof GuiButtonShaderOption) {
                final String[] tooltipLines = this.makeTooltipLines(((GuiButtonShaderOption)selectedButton).getShaderOption(), n5 - n3);
                if (tooltipLines == null) {
                    return;
                }
                this.drawGradientRect(n3, n4, n5, n6, -536870912, -536870912);
                while (0 < tooltipLines.length) {
                    final String s = tooltipLines[0];
                    if (s.endsWith("!")) {}
                    this.fontRendererObj.func_175063_a(s, (float)(n3 + 5), (float)(n4 + 5 + 0), 16719904);
                    int n7 = 0;
                    ++n7;
                }
            }
        }
    }
    
    private String[] makeTooltipLines(final ShaderOption shaderOption, final int n) {
        if (shaderOption instanceof ShaderOptionProfile) {
            return null;
        }
        final String nameText = shaderOption.getNameText();
        final String[] splitDescription = this.splitDescription(Config.normalize(shaderOption.getDescriptionText()).trim());
        String string = null;
        if (!nameText.equals(shaderOption.getName())) {
            string = String.valueOf(Lang.get("of.general.id")) + ": " + shaderOption.getName();
        }
        String string2 = null;
        if (shaderOption.getPaths() != null) {
            string2 = String.valueOf(Lang.get("of.general.from")) + ": " + Config.arrayToString(shaderOption.getPaths());
        }
        String string3 = null;
        if (shaderOption.getValueDefault() != null) {
            string3 = String.valueOf(Lang.getDefault()) + ": " + (shaderOption.isEnabled() ? shaderOption.getValueText(shaderOption.getValueDefault()) : Lang.get("of.general.ambiguous"));
        }
        final ArrayList<String> list = new ArrayList<String>();
        list.add(nameText);
        list.addAll(Arrays.asList(splitDescription));
        if (string != null) {
            list.add(string);
        }
        if (string2 != null) {
            list.add(string2);
        }
        if (string3 != null) {
            list.add(string3);
        }
        return this.makeTooltipLines(n, list);
    }
    
    private String[] splitDescription(String removePrefix) {
        if (removePrefix.length() <= 0) {
            return new String[0];
        }
        removePrefix = StrUtils.removePrefix(removePrefix, "//");
        final String[] split = removePrefix.split("\\. ");
        while (0 < split.length) {
            split[0] = "- " + split[0].trim();
            split[0] = StrUtils.removeSuffix(split[0], ".");
            int n = 0;
            ++n;
        }
        return split;
    }
    
    private String[] makeTooltipLines(final int n, final List list) {
        Config.getMinecraft();
        final FontRenderer fontRendererObj = Minecraft.fontRendererObj;
        final ArrayList<String> list2 = new ArrayList<String>();
        while (0 < list.size()) {
            final String s = list.get(0);
            if (s != null && s.length() > 0) {
                final Iterator iterator = fontRendererObj.listFormattedStringToWidth(s, n).iterator();
                while (iterator.hasNext()) {
                    list2.add(iterator.next());
                }
            }
            int n2 = 0;
            ++n2;
        }
        return list2.toArray(new String[list2.size()]);
    }
    
    private GuiButton getSelectedButton(final int n, final int n2) {
        while (0 < this.buttonList.size()) {
            final GuiButton guiButton = this.buttonList.get(0);
            final int buttonWidth = GuiVideoSettings.getButtonWidth(guiButton);
            final int buttonHeight = GuiVideoSettings.getButtonHeight(guiButton);
            if (n >= guiButton.xPosition && n2 >= guiButton.yPosition && n < guiButton.xPosition + buttonWidth && n2 < guiButton.yPosition + buttonHeight) {
                return guiButton;
            }
            int n3 = 0;
            ++n3;
        }
        return null;
    }
    
    static {
        OPTION_EMPTY = "<empty>";
        OPTION_PROFILE = "<profile>";
        OPTION_REST = "*";
    }
}
