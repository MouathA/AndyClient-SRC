package Mood.Host;

import net.minecraft.client.gui.*;
import java.io.*;

public class CheckHostScreen extends GuiScreen
{
    private GuiScreen parent;
    private String pingedServer;
    private ServerInfos.Hosting hosting;
    private GuiTextField ipField;
    private Result pingResult;
    private Result tcpResult;
    private String i;
    private String type;
    private int reloadtime;
    
    public CheckHostScreen(final String i, final String type, final GuiScreen parent) {
        this.pingedServer = "";
        this.i = "";
        this.type = "";
        this.reloadtime = 0;
        this.setParent(parent);
        this.type = type;
        this.i = i;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, CheckHostScreen.width - 144 + 74, 54, 60, 20, "Vissza"));
        this.buttonList.add(new GuiButton(2, CheckHostScreen.width - 144 + 10, 54, 60, 20, "Ping"));
        (this.ipField = new GuiTextField(3, this.fontRendererObj, CheckHostScreen.width - 134, 30, 124, 20)).setText(this.i);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.ipField.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        this.ipField.textboxKeyTyped(c, n);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 1: {
                CheckHostScreen.mc.displayGuiScreen(this.getParent());
                break;
            }
            case 2: {
                if (this.type.equals("Ping")) {
                    new Thread(new Runnable() {
                        final CheckHostScreen this$0;
                        
                        @Override
                        public void run() {
                            CheckHostScreen.access$1(this.this$0, CheckHostScreen.access$0(this.this$0).getText());
                            CheckHostScreen.access$3(this.this$0, new ServerInfos.Hosting(CheckHostScreen.access$2(this.this$0)));
                            if (!CheckHostScreen.access$2(this.this$0).isEmpty()) {
                                CheckHostScreen.access$4(this.this$0, CheckHostAPI.createPingRequest(CheckHostScreen.access$2(this.this$0), 100));
                            }
                            CheckHostScreen.access$5(this.this$0, null);
                            CheckHostScreen.access$6(this.this$0).update();
                        }
                    }).start();
                    break;
                }
                new Thread(new Runnable() {
                    final CheckHostScreen this$0;
                    
                    @Override
                    public void run() {
                        CheckHostScreen.access$1(this.this$0, CheckHostScreen.access$0(this.this$0).getText());
                        CheckHostScreen.access$3(this.this$0, new ServerInfos.Hosting(CheckHostScreen.access$2(this.this$0)));
                        if (!CheckHostScreen.access$2(this.this$0).isEmpty()) {
                            CheckHostScreen.access$5(this.this$0, CheckHostAPI.createTcpRequest(CheckHostScreen.access$2(this.this$0), 100));
                        }
                        CheckHostScreen.access$4(this.this$0, null);
                        CheckHostScreen.access$7(this.this$0).update();
                    }
                }).start();
                break;
            }
        }
        super.actionPerformed(guiButton);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.ipField.updateCursorCounter();
    }
    
    @Override
    public void drawScreen(final int p0, final int p1, final float p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   Mood/Host/CheckHostScreen.drawDefaultBackground:()V
        //     4: aload_0        
        //     5: getfield        Mood/Host/CheckHostScreen.ipField:Lnet/minecraft/client/gui/GuiTextField;
        //     8: invokevirtual   net/minecraft/client/gui/GuiTextField.drawTextBox:()V
        //    11: aload_0        
        //    12: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //    15: new             Ljava/lang/StringBuilder;
        //    18: dup            
        //    19: ldc             "§6§lCheckHost §8§l(§e§l"
        //    21: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    24: aload_0        
        //    25: getfield        Mood/Host/CheckHostScreen.type:Ljava/lang/String;
        //    28: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    31: ldc             "§8§l)"
        //    33: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    36: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    39: getstatic       Mood/Host/CheckHostScreen.width:I
        //    42: iconst_2       
        //    43: idiv           
        //    44: bipush          10
        //    46: iconst_m1      
        //    47: invokestatic    Mood/Host/CheckHostScreen.drawCenteredString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //    50: aload_0        
        //    51: getfield        Mood/Host/CheckHostScreen.pingedServer:Ljava/lang/String;
        //    54: invokevirtual   java/lang/String.isEmpty:()Z
        //    57: ifne            1216
        //    60: bipush          8
        //    62: bipush          29
        //    64: sipush          400
        //    67: bipush          30
        //    69: getstatic       java/awt/Color.ORANGE:Ljava/awt/Color;
        //    72: invokevirtual   java/awt/Color.getRGB:()I
        //    75: invokestatic    net/minecraft/client/gui/Gui.drawRect:(IIIII)V
        //    78: bipush          8
        //    80: sipush          291
        //    83: sipush          400
        //    86: sipush          290
        //    89: getstatic       java/awt/Color.ORANGE:Ljava/awt/Color;
        //    92: invokevirtual   java/awt/Color.getRGB:()I
        //    95: invokestatic    net/minecraft/client/gui/Gui.drawRect:(IIIII)V
        //    98: goto            202
        //   101: bipush          10
        //   103: sipush          330
        //   106: sipush          400
        //   109: sipush          340
        //   112: ldc             -2147483648
        //   114: invokestatic    net/minecraft/client/gui/Gui.drawRect:(IIIII)V
        //   117: bipush          8
        //   119: sipush          330
        //   122: bipush          9
        //   124: sipush          340
        //   127: getstatic       java/awt/Color.ORANGE:Ljava/awt/Color;
        //   130: invokevirtual   java/awt/Color.getRGB:()I
        //   133: invokestatic    net/minecraft/client/gui/Gui.drawRect:(IIIII)V
        //   136: sipush          399
        //   139: sipush          330
        //   142: sipush          400
        //   145: sipush          340
        //   148: getstatic       java/awt/Color.ORANGE:Ljava/awt/Color;
        //   151: invokevirtual   java/awt/Color.getRGB:()I
        //   154: invokestatic    net/minecraft/client/gui/Gui.drawRect:(IIIII)V
        //   157: sipush          135
        //   160: sipush          330
        //   163: sipush          136
        //   166: sipush          340
        //   169: getstatic       java/awt/Color.ORANGE:Ljava/awt/Color;
        //   172: invokevirtual   java/awt/Color.getRGB:()I
        //   175: invokestatic    net/minecraft/client/gui/Gui.drawRect:(IIIII)V
        //   178: sipush          240
        //   181: sipush          330
        //   184: sipush          241
        //   187: sipush          340
        //   190: getstatic       java/awt/Color.ORANGE:Ljava/awt/Color;
        //   193: invokevirtual   java/awt/Color.getRGB:()I
        //   196: invokestatic    net/minecraft/client/gui/Gui.drawRect:(IIIII)V
        //   199: iinc            4, 1
        //   202: bipush          30
        //   204: bipush          26
        //   206: if_icmplt       101
        //   209: aload_0        
        //   210: getfield        Mood/Host/CheckHostScreen.pingResult:LMood/Host/Result;
        //   213: ifnull          817
        //   216: aload_0        
        //   217: getfield        Mood/Host/CheckHostScreen.pingResult:LMood/Host/Result;
        //   220: invokevirtual   Mood/Host/Result.getServers:()Ljava/util/List;
        //   223: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   228: astore          5
        //   230: goto            804
        //   233: aload           5
        //   235: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   240: checkcast       LMood/Host/CheckHostServer;
        //   243: astore          6
        //   245: aload_0        
        //   246: getfield        Mood/Host/CheckHostScreen.reloadtime:I
        //   249: sipush          600
        //   252: if_icmpge       268
        //   255: aload_0        
        //   256: dup            
        //   257: getfield        Mood/Host/CheckHostScreen.reloadtime:I
        //   260: iconst_1       
        //   261: iadd           
        //   262: putfield        Mood/Host/CheckHostScreen.reloadtime:I
        //   265: goto            280
        //   268: aload_0        
        //   269: getfield        Mood/Host/CheckHostScreen.pingResult:LMood/Host/Result;
        //   272: invokevirtual   Mood/Host/Result.update:()V
        //   275: aload_0        
        //   276: iconst_0       
        //   277: putfield        Mood/Host/CheckHostScreen.reloadtime:I
        //   280: aload_0        
        //   281: getfield        Mood/Host/CheckHostScreen.pingResult:LMood/Host/Result;
        //   284: invokevirtual   Mood/Host/Result.getResult:()Ljava/lang/Object;
        //   287: checkcast       Ljava/util/Map;
        //   290: aload           6
        //   292: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   297: checkcast       LMood/Host/PingResult;
        //   300: astore          9
        //   302: aload_0        
        //   303: aload_0        
        //   304: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   307: ldc             "§6Elhelyezked\u00e9s"
        //   309: bipush          10
        //   311: bipush          20
        //   313: iconst_m1      
        //   314: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //   317: aload_0        
        //   318: aload_0        
        //   319: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   322: new             Ljava/lang/StringBuilder;
        //   325: dup            
        //   326: ldc             "§e"
        //   328: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   331: aload           6
        //   333: invokevirtual   Mood/Host/CheckHostServer.getCountry:()Ljava/lang/String;
        //   336: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   339: ldc             ", "
        //   341: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   344: aload           6
        //   346: invokevirtual   Mood/Host/CheckHostServer.getCity:()Ljava/lang/String;
        //   349: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   352: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   355: bipush          10
        //   357: bipush          31
        //   359: iconst_m1      
        //   360: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //   363: ldc2_w          1337.0
        //   366: dstore          7
        //   368: dconst_0       
        //   369: dstore          10
        //   371: dconst_0       
        //   372: dstore          12
        //   374: goto            499
        //   377: aload           9
        //   379: invokevirtual   Mood/Host/PingResult.getPingEntries:()Ljava/util/List;
        //   382: iconst_0       
        //   383: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   388: checkcast       LMood/Host/PingResult$PingEntry;
        //   391: invokevirtual   Mood/Host/PingResult$PingEntry.getStatus:()Ljava/lang/String;
        //   394: ldc_w           "OK"
        //   397: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   400: ifeq            406
        //   403: iinc            14, 1
        //   406: aload           9
        //   408: invokevirtual   Mood/Host/PingResult.getPingEntries:()Ljava/util/List;
        //   411: iconst_1       
        //   412: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   417: checkcast       LMood/Host/PingResult$PingEntry;
        //   420: invokevirtual   Mood/Host/PingResult$PingEntry.getPing:()D
        //   423: dstore          17
        //   425: dload           17
        //   427: ldc2_w          10000.0
        //   430: dmul           
        //   431: dstore          17
        //   433: dload           17
        //   435: d2i            
        //   436: i2d            
        //   437: ldc2_w          10.0
        //   440: ddiv           
        //   441: dstore          17
        //   443: dload           17
        //   445: dload           7
        //   447: dcmpg          
        //   448: ifge            457
        //   451: dload           17
        //   453: d2i            
        //   454: i2d            
        //   455: dstore          7
        //   457: dload           17
        //   459: dload           12
        //   461: dcmpl          
        //   462: ifle            471
        //   465: dload           17
        //   467: d2i            
        //   468: i2d            
        //   469: dstore          12
        //   471: dload           10
        //   473: dload           17
        //   475: dadd           
        //   476: dstore          10
        //   478: iinc            15, 1
        //   481: dload           10
        //   483: iconst_0       
        //   484: i2d            
        //   485: ddiv           
        //   486: dstore          10
        //   488: dload           10
        //   490: dconst_1       
        //   491: invokestatic    java/lang/Double.max:(DD)D
        //   494: dstore          10
        //   496: iinc            16, 1
        //   499: iconst_0       
        //   500: aload           9
        //   502: invokevirtual   Mood/Host/PingResult.getPingEntries:()Ljava/util/List;
        //   505: invokeinterface java/util/List.size:()I
        //   510: if_icmplt       377
        //   513: aload_0        
        //   514: aload_0        
        //   515: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   518: ldc_w           "§6Tal\u00e1latok"
        //   521: sipush          137
        //   524: bipush          20
        //   526: iconst_m1      
        //   527: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //   530: aload_0        
        //   531: aload_0        
        //   532: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   535: new             Ljava/lang/StringBuilder;
        //   538: dup            
        //   539: iconst_0       
        //   540: iconst_0       
        //   541: if_icmpne       550
        //   544: ldc_w           "§6"
        //   547: goto            553
        //   550: ldc_w           "§4"
        //   553: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   556: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   559: iconst_0       
        //   560: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   563: ldc_w           "/"
        //   566: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   569: iconst_0       
        //   570: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   573: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   576: sipush          137
        //   579: bipush          31
        //   581: iconst_m1      
        //   582: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //   585: aload_0        
        //   586: aload_0        
        //   587: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   590: ldc_w           "§6rtt min/avg/max"
        //   593: sipush          242
        //   596: bipush          20
        //   598: iconst_m1      
        //   599: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //   602: iconst_0       
        //   603: ifeq            762
        //   606: new             Ljava/lang/StringBuilder;
        //   609: dup            
        //   610: dload           10
        //   612: ldc2_w          25.0
        //   615: dcmpg          
        //   616: ifgt            625
        //   619: ldc_w           "§a"
        //   622: goto            657
        //   625: dload           10
        //   627: ldc2_w          50.0
        //   630: dcmpg          
        //   631: ifgt            639
        //   634: ldc             "§e"
        //   636: goto            657
        //   639: dload           10
        //   641: ldc2_w          100.0
        //   644: dcmpg          
        //   645: ifgt            654
        //   648: ldc_w           "§c"
        //   651: goto            657
        //   654: ldc_w           "§4"
        //   657: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   660: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   663: ldc_w           "%.2f"
        //   666: iconst_1       
        //   667: anewarray       Ljava/lang/Object;
        //   670: dup            
        //   671: iconst_0       
        //   672: dload           7
        //   674: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   677: aastore        
        //   678: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   681: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   684: ldc_w           "/"
        //   687: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   690: ldc_w           "%.2f"
        //   693: iconst_1       
        //   694: anewarray       Ljava/lang/Object;
        //   697: dup            
        //   698: iconst_0       
        //   699: dload           10
        //   701: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   704: aastore        
        //   705: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   708: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   711: ldc_w           "/"
        //   714: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   717: ldc_w           "%.2f"
        //   720: iconst_1       
        //   721: anewarray       Ljava/lang/Object;
        //   724: dup            
        //   725: iconst_0       
        //   726: dload           12
        //   728: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   731: aastore        
        //   732: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   735: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   738: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   741: astore          16
        //   743: aload_0        
        //   744: aload_0        
        //   745: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   748: aload           16
        //   750: sipush          242
        //   753: bipush          31
        //   755: iconst_m1      
        //   756: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //   759: goto            801
        //   762: aload_0        
        //   763: aload_0        
        //   764: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   767: ldc_w           "§8Id\u0151t\u00fall\u00e9p\u00e9s"
        //   770: sipush          242
        //   773: bipush          31
        //   775: iconst_m1      
        //   776: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //   779: goto            801
        //   782: astore          10
        //   784: aload_0        
        //   785: aload_0        
        //   786: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   789: ldc_w           "§7Keres\u00e9s..."
        //   792: sipush          137
        //   795: bipush          31
        //   797: iconst_m1      
        //   798: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //   801: iinc            4, 10
        //   804: aload           5
        //   806: invokeinterface java/util/Iterator.hasNext:()Z
        //   811: ifne            233
        //   814: goto            1216
        //   817: aload_0        
        //   818: getfield        Mood/Host/CheckHostScreen.tcpResult:LMood/Host/Result;
        //   821: invokevirtual   Mood/Host/Result.getServers:()Ljava/util/List;
        //   824: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   829: astore          5
        //   831: goto            1196
        //   834: aload           5
        //   836: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   841: checkcast       LMood/Host/CheckHostServer;
        //   844: astore          6
        //   846: aload_0        
        //   847: getfield        Mood/Host/CheckHostScreen.reloadtime:I
        //   850: sipush          600
        //   853: if_icmpge       869
        //   856: aload_0        
        //   857: dup            
        //   858: getfield        Mood/Host/CheckHostScreen.reloadtime:I
        //   861: iconst_1       
        //   862: iadd           
        //   863: putfield        Mood/Host/CheckHostScreen.reloadtime:I
        //   866: goto            881
        //   869: aload_0        
        //   870: getfield        Mood/Host/CheckHostScreen.tcpResult:LMood/Host/Result;
        //   873: invokevirtual   Mood/Host/Result.update:()V
        //   876: aload_0        
        //   877: iconst_0       
        //   878: putfield        Mood/Host/CheckHostScreen.reloadtime:I
        //   881: aload_0        
        //   882: getfield        Mood/Host/CheckHostScreen.tcpResult:LMood/Host/Result;
        //   885: invokevirtual   Mood/Host/Result.getResult:()Ljava/lang/Object;
        //   888: checkcast       Ljava/util/Map;
        //   891: aload           6
        //   893: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   898: checkcast       LMood/Host/TCPResult;
        //   901: astore          9
        //   903: aload_0        
        //   904: aload_0        
        //   905: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   908: ldc             "§6Elhelyezked\u00e9s"
        //   910: bipush          10
        //   912: bipush          20
        //   914: iconst_m1      
        //   915: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //   918: aload_0        
        //   919: aload_0        
        //   920: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   923: new             Ljava/lang/StringBuilder;
        //   926: dup            
        //   927: ldc             "§e"
        //   929: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   932: aload           6
        //   934: invokevirtual   Mood/Host/CheckHostServer.getCountry:()Ljava/lang/String;
        //   937: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   940: ldc             ", "
        //   942: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   945: aload           6
        //   947: invokevirtual   Mood/Host/CheckHostServer.getCity:()Ljava/lang/String;
        //   950: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   953: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   956: bipush          10
        //   958: bipush          31
        //   960: iconst_m1      
        //   961: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //   964: aload_0        
        //   965: aload_0        
        //   966: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   969: ldc_w           "§6Tal\u00e1latok"
        //   972: sipush          137
        //   975: bipush          20
        //   977: iconst_m1      
        //   978: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //   981: aload           9
        //   983: invokevirtual   Mood/Host/TCPResult.isSuccessful:()Z
        //   986: ifeq            1009
        //   989: aload_0        
        //   990: aload_0        
        //   991: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   994: ldc_w           "§6Csatlakoztatva"
        //   997: sipush          137
        //  1000: bipush          31
        //  1002: iconst_m1      
        //  1003: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //  1006: goto            1044
        //  1009: aload_0        
        //  1010: aload_0        
        //  1011: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //  1014: new             Ljava/lang/StringBuilder;
        //  1017: dup            
        //  1018: ldc_w           "§7"
        //  1021: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1024: aload           9
        //  1026: invokevirtual   Mood/Host/TCPResult.getError:()Ljava/lang/String;
        //  1029: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1032: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1035: sipush          137
        //  1038: bipush          31
        //  1040: iconst_m1      
        //  1041: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //  1044: aload           9
        //  1046: invokevirtual   Mood/Host/TCPResult.getPing:()D
        //  1049: dstore          7
        //  1051: dload           7
        //  1053: ldc2_w          10000.0
        //  1056: dmul           
        //  1057: dstore          7
        //  1059: dload           7
        //  1061: d2i            
        //  1062: i2d            
        //  1063: ldc2_w          10.0
        //  1066: ddiv           
        //  1067: dstore          7
        //  1069: aload_0        
        //  1070: aload_0        
        //  1071: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //  1074: ldc_w           "§6§lId\u0151"
        //  1077: sipush          242
        //  1080: bipush          20
        //  1082: iconst_m1      
        //  1083: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //  1086: aload_0        
        //  1087: aload_0        
        //  1088: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //  1091: new             Ljava/lang/StringBuilder;
        //  1094: dup            
        //  1095: dload           7
        //  1097: ldc2_w          25.0
        //  1100: dcmpg          
        //  1101: ifgt            1110
        //  1104: ldc_w           "§a"
        //  1107: goto            1142
        //  1110: dload           7
        //  1112: ldc2_w          50.0
        //  1115: dcmpg          
        //  1116: ifgt            1124
        //  1119: ldc             "§e"
        //  1121: goto            1142
        //  1124: dload           7
        //  1126: ldc2_w          100.0
        //  1129: dcmpg          
        //  1130: ifgt            1139
        //  1133: ldc_w           "§c"
        //  1136: goto            1142
        //  1139: ldc_w           "§4"
        //  1142: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //  1145: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1148: dload           7
        //  1150: invokevirtual   java/lang/StringBuilder.append:(D)Ljava/lang/StringBuilder;
        //  1153: ldc_w           "ms"
        //  1156: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1159: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1162: sipush          242
        //  1165: bipush          31
        //  1167: iconst_m1      
        //  1168: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //  1171: goto            1193
        //  1174: astore          10
        //  1176: aload_0        
        //  1177: aload_0        
        //  1178: getfield        Mood/Host/CheckHostScreen.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //  1181: ldc_w           "§7Keres\u00e9s..."
        //  1184: sipush          137
        //  1187: bipush          31
        //  1189: iconst_m1      
        //  1190: invokevirtual   Mood/Host/CheckHostScreen.drawString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //  1193: iinc            4, 10
        //  1196: aload           5
        //  1198: invokeinterface java/util/Iterator.hasNext:()Z
        //  1203: ifne            834
        //  1206: goto            1216
        //  1209: astore          4
        //  1211: aload           4
        //  1213: invokevirtual   java/lang/Exception.printStackTrace:()V
        //  1216: aload_0        
        //  1217: iload_1        
        //  1218: iload_2        
        //  1219: fload_3        
        //  1220: invokespecial   net/minecraft/client/gui/GuiScreen.drawScreen:(IIF)V
        //  1223: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public GuiScreen getParent() {
        return this.parent;
    }
    
    public void setParent(final GuiScreen parent) {
        this.parent = parent;
    }
    
    static GuiTextField access$0(final CheckHostScreen checkHostScreen) {
        return checkHostScreen.ipField;
    }
    
    static void access$1(final CheckHostScreen checkHostScreen, final String pingedServer) {
        checkHostScreen.pingedServer = pingedServer;
    }
    
    static String access$2(final CheckHostScreen checkHostScreen) {
        return checkHostScreen.pingedServer;
    }
    
    static void access$3(final CheckHostScreen checkHostScreen, final ServerInfos.Hosting hosting) {
        checkHostScreen.hosting = hosting;
    }
    
    static void access$4(final CheckHostScreen checkHostScreen, final Result pingResult) {
        checkHostScreen.pingResult = pingResult;
    }
    
    static void access$5(final CheckHostScreen checkHostScreen, final Result tcpResult) {
        checkHostScreen.tcpResult = tcpResult;
    }
    
    static Result access$6(final CheckHostScreen checkHostScreen) {
        return checkHostScreen.pingResult;
    }
    
    static Result access$7(final CheckHostScreen checkHostScreen) {
        return checkHostScreen.tcpResult;
    }
}
