package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import optifine.*;
import shadersmod.client.*;
import java.io.*;

public class GuiVideoSettings extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    private GameSettings guiGameSettings;
    private static GameSettings.Options[] videoOptions;
    private static final String __OBFID;
    private TooltipManager tooltipManager;
    
    static {
        __OBFID = "CL_00000718";
        GuiVideoSettings.videoOptions = new GameSettings.Options[] { GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.USE_VBO, GameSettings.Options.GAMMA, GameSettings.Options.BLOCK_ALTERNATIVES, GameSettings.Options.FOG_FANCY, GameSettings.Options.FOG_START };
    }
    
    public GuiVideoSettings(final GuiScreen parentGuiScreen, final GameSettings guiGameSettings) {
        this.screenTitle = "Video Settings";
        this.tooltipManager = new TooltipManager(this);
        this.parentGuiScreen = parentGuiScreen;
        this.guiGameSettings = guiGameSettings;
    }
    
    @Override
    public void initGui() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "options.videoTitle"
        //     3: iconst_0       
        //     4: anewarray       Ljava/lang/Object;
        //     7: invokestatic    net/minecraft/client/resources/I18n.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //    10: putfield        net/minecraft/client/gui/GuiVideoSettings.screenTitle:Ljava/lang/String;
        //    13: aload_0        
        //    14: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //    17: invokeinterface java/util/List.clear:()V
        //    22: goto            131
        //    25: getstatic       net/minecraft/client/gui/GuiVideoSettings.videoOptions:[Lnet/minecraft/client/settings/GameSettings$Options;
        //    28: iconst_0       
        //    29: aaload         
        //    30: astore_2       
        //    31: aload_2        
        //    32: ifnull          128
        //    35: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //    38: iconst_2       
        //    39: idiv           
        //    40: sipush          155
        //    43: isub           
        //    44: iconst_0       
        //    45: iadd           
        //    46: istore_3       
        //    47: getstatic       net/minecraft/client/gui/GuiVideoSettings.height:I
        //    50: bipush          6
        //    52: idiv           
        //    53: iconst_0       
        //    54: iadd           
        //    55: bipush          12
        //    57: isub           
        //    58: istore          4
        //    60: aload_2        
        //    61: invokevirtual   net/minecraft/client/settings/GameSettings$Options.getEnumFloat:()Z
        //    64: ifeq            95
        //    67: aload_0        
        //    68: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //    71: new             Loptifine/GuiOptionSliderOF;
        //    74: dup            
        //    75: aload_2        
        //    76: invokevirtual   net/minecraft/client/settings/GameSettings$Options.returnEnumOrdinal:()I
        //    79: iload_3        
        //    80: iload           4
        //    82: aload_2        
        //    83: invokespecial   optifine/GuiOptionSliderOF.<init>:(IIILnet/minecraft/client/settings/GameSettings$Options;)V
        //    86: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    91: pop            
        //    92: goto            128
        //    95: aload_0        
        //    96: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //    99: new             Loptifine/GuiOptionButtonOF;
        //   102: dup            
        //   103: aload_2        
        //   104: invokevirtual   net/minecraft/client/settings/GameSettings$Options.returnEnumOrdinal:()I
        //   107: iload_3        
        //   108: iload           4
        //   110: aload_2        
        //   111: aload_0        
        //   112: getfield        net/minecraft/client/gui/GuiVideoSettings.guiGameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   115: aload_2        
        //   116: invokevirtual   net/minecraft/client/settings/GameSettings.getKeyBinding:(Lnet/minecraft/client/settings/GameSettings$Options;)Ljava/lang/String;
        //   119: invokespecial   optifine/GuiOptionButtonOF.<init>:(IIILnet/minecraft/client/settings/GameSettings$Options;Ljava/lang/String;)V
        //   122: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   127: pop            
        //   128: iinc            1, 1
        //   131: iconst_0       
        //   132: getstatic       net/minecraft/client/gui/GuiVideoSettings.videoOptions:[Lnet/minecraft/client/settings/GameSettings$Options;
        //   135: arraylength    
        //   136: if_icmplt       25
        //   139: getstatic       net/minecraft/client/gui/GuiVideoSettings.height:I
        //   142: bipush          6
        //   144: idiv           
        //   145: bipush          21
        //   147: getstatic       net/minecraft/client/gui/GuiVideoSettings.videoOptions:[Lnet/minecraft/client/settings/GameSettings$Options;
        //   150: arraylength    
        //   151: iconst_2       
        //   152: idiv           
        //   153: imul           
        //   154: iadd           
        //   155: bipush          12
        //   157: isub           
        //   158: istore_1       
        //   159: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   162: iconst_2       
        //   163: idiv           
        //   164: sipush          155
        //   167: isub           
        //   168: iconst_0       
        //   169: iadd           
        //   170: istore_3       
        //   171: aload_0        
        //   172: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   175: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   178: dup            
        //   179: sipush          231
        //   182: iload_3        
        //   183: iconst_0       
        //   184: ldc             "of.options.shaders"
        //   186: invokestatic    optifine/Lang.get:(Ljava/lang/String;)Ljava/lang/String;
        //   189: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILjava/lang/String;)V
        //   192: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   197: pop            
        //   198: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   201: iconst_2       
        //   202: idiv           
        //   203: sipush          155
        //   206: isub           
        //   207: sipush          160
        //   210: iadd           
        //   211: istore_3       
        //   212: aload_0        
        //   213: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   216: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   219: dup            
        //   220: sipush          202
        //   223: iload_3        
        //   224: iconst_0       
        //   225: ldc             "of.options.quality"
        //   227: invokestatic    optifine/Lang.get:(Ljava/lang/String;)Ljava/lang/String;
        //   230: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILjava/lang/String;)V
        //   233: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   238: pop            
        //   239: iinc            1, 21
        //   242: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   245: iconst_2       
        //   246: idiv           
        //   247: sipush          155
        //   250: isub           
        //   251: iconst_0       
        //   252: iadd           
        //   253: istore_3       
        //   254: aload_0        
        //   255: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   258: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   261: dup            
        //   262: sipush          201
        //   265: iload_3        
        //   266: iconst_0       
        //   267: ldc             "of.options.details"
        //   269: invokestatic    optifine/Lang.get:(Ljava/lang/String;)Ljava/lang/String;
        //   272: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILjava/lang/String;)V
        //   275: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   280: pop            
        //   281: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   284: iconst_2       
        //   285: idiv           
        //   286: sipush          155
        //   289: isub           
        //   290: sipush          160
        //   293: iadd           
        //   294: istore_3       
        //   295: aload_0        
        //   296: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   299: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   302: dup            
        //   303: sipush          212
        //   306: iload_3        
        //   307: iconst_0       
        //   308: ldc             "of.options.performance"
        //   310: invokestatic    optifine/Lang.get:(Ljava/lang/String;)Ljava/lang/String;
        //   313: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILjava/lang/String;)V
        //   316: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   321: pop            
        //   322: iinc            1, 21
        //   325: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   328: iconst_2       
        //   329: idiv           
        //   330: sipush          155
        //   333: isub           
        //   334: iconst_0       
        //   335: iadd           
        //   336: istore_3       
        //   337: aload_0        
        //   338: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   341: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   344: dup            
        //   345: sipush          211
        //   348: iload_3        
        //   349: iconst_0       
        //   350: ldc             "of.options.animations"
        //   352: invokestatic    optifine/Lang.get:(Ljava/lang/String;)Ljava/lang/String;
        //   355: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILjava/lang/String;)V
        //   358: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   363: pop            
        //   364: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   367: iconst_2       
        //   368: idiv           
        //   369: sipush          155
        //   372: isub           
        //   373: sipush          160
        //   376: iadd           
        //   377: istore_3       
        //   378: aload_0        
        //   379: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   382: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   385: dup            
        //   386: sipush          222
        //   389: iload_3        
        //   390: iconst_0       
        //   391: ldc             "of.options.other"
        //   393: invokestatic    optifine/Lang.get:(Ljava/lang/String;)Ljava/lang/String;
        //   396: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILjava/lang/String;)V
        //   399: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   404: pop            
        //   405: iinc            1, 21
        //   408: aload_0        
        //   409: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   412: new             Lnet/minecraft/client/gui/GuiButton;
        //   415: dup            
        //   416: sipush          200
        //   419: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   422: iconst_2       
        //   423: idiv           
        //   424: bipush          100
        //   426: isub           
        //   427: getstatic       net/minecraft/client/gui/GuiVideoSettings.height:I
        //   430: bipush          6
        //   432: idiv           
        //   433: sipush          168
        //   436: iadd           
        //   437: bipush          11
        //   439: iadd           
        //   440: ldc             "gui.done"
        //   442: iconst_0       
        //   443: anewarray       Ljava/lang/Object;
        //   446: invokestatic    net/minecraft/client/resources/I18n.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   449: invokespecial   net/minecraft/client/gui/GuiButton.<init>:(IIILjava/lang/String;)V
        //   452: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   457: pop            
        //   458: return         
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            final int guiScale = this.guiGameSettings.guiScale;
            if (guiButton.id < 200 && guiButton instanceof GuiOptionButton) {
                this.guiGameSettings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                guiButton.displayString = this.guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 200) {
                GuiVideoSettings.mc.gameSettings.saveOptions();
                GuiVideoSettings.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (this.guiGameSettings.guiScale != guiScale) {
                final ScaledResolution scaledResolution = new ScaledResolution(GuiVideoSettings.mc, GuiVideoSettings.mc.displayWidth, GuiVideoSettings.mc.displayHeight);
                this.setWorldAndResolution(GuiVideoSettings.mc, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
            }
            if (guiButton.id == 201) {
                GuiVideoSettings.mc.gameSettings.saveOptions();
                GuiVideoSettings.mc.displayGuiScreen(new GuiDetailSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 202) {
                GuiVideoSettings.mc.gameSettings.saveOptions();
                GuiVideoSettings.mc.displayGuiScreen(new GuiQualitySettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 211) {
                GuiVideoSettings.mc.gameSettings.saveOptions();
                GuiVideoSettings.mc.displayGuiScreen(new GuiAnimationSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 212) {
                GuiVideoSettings.mc.gameSettings.saveOptions();
                GuiVideoSettings.mc.displayGuiScreen(new GuiPerformanceSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 222) {
                GuiVideoSettings.mc.gameSettings.saveOptions();
                GuiVideoSettings.mc.displayGuiScreen(new GuiOtherSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 231) {
                if (Config.isAntialiasing() || Config.isAntialiasingConfigured()) {
                    Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
                    return;
                }
                if (Config.isAnisotropicFiltering()) {
                    Config.showGuiMessage(Lang.get("of.message.shaders.af1"), Lang.get("of.message.shaders.af2"));
                    return;
                }
                if (Config.isFastRender()) {
                    Config.showGuiMessage(Lang.get("of.message.shaders.fr1"), Lang.get("of.message.shaders.fr2"));
                    return;
                }
                GuiVideoSettings.mc.gameSettings.saveOptions();
                GuiVideoSettings.mc.displayGuiScreen(new GuiShaders(this, this.guiGameSettings));
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.screenTitle, GuiVideoSettings.width / 2, 15, 16777215);
        String version = Config.getVersion();
        final String s = "HD_U";
        if (s.equals("HD")) {
            version = "OptiFine HD H6";
        }
        if (s.equals("HD_U")) {
            version = "OptiFine HD H6 Ultra";
        }
        if (s.equals("L")) {
            version = "OptiFine H6 Light";
        }
        this.drawString(this.fontRendererObj, version, 2, GuiVideoSettings.height - 10, 8421504);
        final String s2 = "Minecraft 1.8";
        this.drawString(this.fontRendererObj, s2, GuiVideoSettings.width - this.fontRendererObj.getStringWidth(s2) - 2, GuiVideoSettings.height - 10, 8421504);
        super.drawScreen(n, n2, n3);
        this.tooltipManager.drawTooltips(n, n2, this.buttonList);
    }
    
    public static int getButtonWidth(final GuiButton guiButton) {
        return guiButton.width;
    }
    
    public static int getButtonHeight(final GuiButton guiButton) {
        return guiButton.height;
    }
    
    public static void drawGradientRect(final GuiScreen guiScreen, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        guiScreen.drawGradientRect(n, n2, n3, n4, n5, n6);
    }
}
