package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import java.util.regex.*;
import java.util.function.*;

final class TextReplacementRenderer implements ComponentRenderer
{
    static final TextReplacementRenderer INSTANCE;
    
    private TextReplacementRenderer() {
    }
    
    @NotNull
    public Component render(@NotNull final Component component, @NotNull final State state) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.running:Z
        //     4: ifne            9
        //     7: aload_1        
        //     8: areturn        
        //     9: aload_2        
        //    10: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.firstMatch:Z
        //    13: istore_3       
        //    14: aload_2        
        //    15: iconst_1       
        //    16: putfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.firstMatch:Z
        //    19: aload_1        
        //    20: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.children:()Ljava/util/List;
        //    25: astore          4
        //    27: aload           4
        //    29: invokeinterface java/util/List.size:()I
        //    34: istore          5
        //    36: aconst_null    
        //    37: astore          6
        //    39: aload_1        
        //    40: astore          7
        //    42: aload_1        
        //    43: instanceof      Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //    46: ifeq            582
        //    49: aload_1        
        //    50: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //    53: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/TextComponent.content:()Ljava/lang/String;
        //    58: astore          8
        //    60: aload_2        
        //    61: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.pattern:Ljava/util/regex/Pattern;
        //    64: aload           8
        //    66: invokevirtual   java/util/regex/Pattern.matcher:(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
        //    69: astore          9
        //    71: aload           9
        //    73: invokevirtual   java/util/regex/Matcher.find:()Z
        //    76: ifeq            533
        //    79: aload_2        
        //    80: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.continuer:Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementConfig$Condition;
        //    83: aload           9
        //    85: aload_2        
        //    86: dup            
        //    87: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.matchCount:I
        //    90: iconst_1       
        //    91: iadd           
        //    92: dup_x1         
        //    93: putfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.matchCount:I
        //    96: aload_2        
        //    97: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.replaceCount:I
        //   100: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementConfig$Condition.shouldReplace:(Ljava/util/regex/MatchResult;II)Lcom/viaversion/viaversion/libs/kyori/adventure/text/PatternReplacementResult;
        //   105: astore          11
        //   107: aload           11
        //   109: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/PatternReplacementResult.CONTINUE:Lcom/viaversion/viaversion/libs/kyori/adventure/text/PatternReplacementResult;
        //   112: if_acmpne       118
        //   115: goto            71
        //   118: aload           11
        //   120: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/PatternReplacementResult.STOP:Lcom/viaversion/viaversion/libs/kyori/adventure/text/PatternReplacementResult;
        //   123: if_acmpne       134
        //   126: aload_2        
        //   127: iconst_0       
        //   128: putfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.running:Z
        //   131: goto            533
        //   134: aload           9
        //   136: invokevirtual   java/util/regex/Matcher.start:()I
        //   139: ifne            378
        //   142: aload           9
        //   144: invokevirtual   java/util/regex/Matcher.end:()I
        //   147: aload           8
        //   149: invokevirtual   java/lang/String.length:()I
        //   152: if_icmpne       295
        //   155: aload_2        
        //   156: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.replacement:Ljava/util/function/BiFunction;
        //   159: aload           9
        //   161: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/Component.text:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder;
        //   164: aload           9
        //   166: invokevirtual   java/util/regex/Matcher.group:()Ljava/lang/String;
        //   169: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder.content:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder;
        //   174: aload_1        
        //   175: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   180: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder.style:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/ComponentBuilder;
        //   185: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder;
        //   188: invokeinterface java/util/function/BiFunction.apply:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   193: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/ComponentLike;
        //   196: astore          12
        //   198: aload           12
        //   200: ifnonnull       209
        //   203: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/Component.empty:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   206: goto            216
        //   209: aload           12
        //   211: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/ComponentLike.asComponent:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   216: astore          7
        //   218: aload           7
        //   220: aload           7
        //   222: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   227: aload_1        
        //   228: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   233: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy.IF_ABSENT_ON_TARGET:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;
        //   236: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.merge:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   241: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   246: astore          7
        //   248: aload           6
        //   250: ifnonnull       292
        //   253: new             Ljava/util/ArrayList;
        //   256: dup            
        //   257: iload           5
        //   259: aload           7
        //   261: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.children:()Ljava/util/List;
        //   266: invokeinterface java/util/List.size:()I
        //   271: iadd           
        //   272: invokespecial   java/util/ArrayList.<init>:(I)V
        //   275: astore          6
        //   277: aload           6
        //   279: aload           7
        //   281: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.children:()Ljava/util/List;
        //   286: invokeinterface java/util/List.addAll:(Ljava/util/Collection;)Z
        //   291: pop            
        //   292: goto            508
        //   295: ldc             ""
        //   297: aload_1        
        //   298: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   303: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/Component.text:(Ljava/lang/String;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   306: astore          7
        //   308: aload_2        
        //   309: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.replacement:Ljava/util/function/BiFunction;
        //   312: aload           9
        //   314: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/Component.text:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder;
        //   317: aload           9
        //   319: invokevirtual   java/util/regex/Matcher.group:()Ljava/lang/String;
        //   322: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder.content:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder;
        //   327: invokeinterface java/util/function/BiFunction.apply:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   332: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/ComponentLike;
        //   335: astore          12
        //   337: aload           12
        //   339: ifnull          375
        //   342: aload           6
        //   344: ifnonnull       360
        //   347: new             Ljava/util/ArrayList;
        //   350: dup            
        //   351: iload           5
        //   353: iconst_1       
        //   354: iadd           
        //   355: invokespecial   java/util/ArrayList.<init>:(I)V
        //   358: astore          6
        //   360: aload           6
        //   362: aload           12
        //   364: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/ComponentLike.asComponent:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   369: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   374: pop            
        //   375: goto            508
        //   378: aload           6
        //   380: ifnonnull       396
        //   383: new             Ljava/util/ArrayList;
        //   386: dup            
        //   387: iload           5
        //   389: iconst_2       
        //   390: iadd           
        //   391: invokespecial   java/util/ArrayList.<init>:(I)V
        //   394: astore          6
        //   396: aload_2        
        //   397: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.firstMatch:Z
        //   400: ifeq            428
        //   403: aload_1        
        //   404: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   407: aload           8
        //   409: iconst_0       
        //   410: aload           9
        //   412: invokevirtual   java/util/regex/Matcher.start:()I
        //   415: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   418: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/TextComponent.content:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   423: astore          7
        //   425: goto            459
        //   428: iconst_0       
        //   429: aload           9
        //   431: invokevirtual   java/util/regex/Matcher.start:()I
        //   434: if_icmpge       459
        //   437: aload           6
        //   439: aload           8
        //   441: iconst_0       
        //   442: aload           9
        //   444: invokevirtual   java/util/regex/Matcher.start:()I
        //   447: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   450: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/Component.text:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   453: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   458: pop            
        //   459: aload_2        
        //   460: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.replacement:Ljava/util/function/BiFunction;
        //   463: aload           9
        //   465: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/Component.text:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder;
        //   468: aload           9
        //   470: invokevirtual   java/util/regex/Matcher.group:()Ljava/lang/String;
        //   473: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder.content:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent$Builder;
        //   478: invokeinterface java/util/function/BiFunction.apply:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   483: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/ComponentLike;
        //   486: astore          12
        //   488: aload           12
        //   490: ifnull          508
        //   493: aload           6
        //   495: aload           12
        //   497: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/ComponentLike.asComponent:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   502: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   507: pop            
        //   508: aload_2        
        //   509: dup            
        //   510: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.replaceCount:I
        //   513: iconst_1       
        //   514: iadd           
        //   515: putfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.replaceCount:I
        //   518: aload_2        
        //   519: iconst_0       
        //   520: putfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.firstMatch:Z
        //   523: aload           9
        //   525: invokevirtual   java/util/regex/Matcher.end:()I
        //   528: istore          10
        //   530: goto            71
        //   533: iconst_0       
        //   534: aload           8
        //   536: invokevirtual   java/lang/String.length:()I
        //   539: if_icmpge       579
        //   542: iconst_0       
        //   543: ifle            579
        //   546: aload           6
        //   548: ifnonnull       562
        //   551: new             Ljava/util/ArrayList;
        //   554: dup            
        //   555: iload           5
        //   557: invokespecial   java/util/ArrayList.<init>:(I)V
        //   560: astore          6
        //   562: aload           6
        //   564: aload           8
        //   566: iconst_0       
        //   567: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
        //   570: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/Component.text:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   573: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   578: pop            
        //   579: goto            725
        //   582: aload           7
        //   584: instanceof      Lcom/viaversion/viaversion/libs/kyori/adventure/text/TranslatableComponent;
        //   587: ifeq            725
        //   590: aload           7
        //   592: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/TranslatableComponent;
        //   595: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/TranslatableComponent.args:()Ljava/util/List;
        //   600: astore          8
        //   602: aconst_null    
        //   603: astore          9
        //   605: aload           8
        //   607: invokeinterface java/util/List.size:()I
        //   612: istore          11
        //   614: iconst_0       
        //   615: iload           11
        //   617: if_icmpge       706
        //   620: aload           8
        //   622: iconst_0       
        //   623: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   628: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   631: astore          12
        //   633: aload_0        
        //   634: aload           12
        //   636: aload_2        
        //   637: invokevirtual   com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer.render:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   640: astore          13
        //   642: aload           13
        //   644: aload_1        
        //   645: if_acmpeq       685
        //   648: aload           9
        //   650: ifnonnull       685
        //   653: new             Ljava/util/ArrayList;
        //   656: dup            
        //   657: iload           11
        //   659: invokespecial   java/util/ArrayList.<init>:(I)V
        //   662: astore          9
        //   664: iconst_0       
        //   665: ifle            685
        //   668: aload           9
        //   670: aload           8
        //   672: iconst_0       
        //   673: iconst_0       
        //   674: invokeinterface java/util/List.subList:(II)Ljava/util/List;
        //   679: invokeinterface java/util/List.addAll:(Ljava/util/Collection;)Z
        //   684: pop            
        //   685: aload           9
        //   687: ifnull          700
        //   690: aload           9
        //   692: aload           13
        //   694: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   699: pop            
        //   700: iinc            10, 1
        //   703: goto            614
        //   706: aload           9
        //   708: ifnull          725
        //   711: aload           7
        //   713: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/TranslatableComponent;
        //   716: aload           9
        //   718: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/TranslatableComponent.args:(Ljava/util/List;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/TranslatableComponent;
        //   723: astore          7
        //   725: aload_2        
        //   726: getfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.running:Z
        //   729: ifeq            879
        //   732: aload           7
        //   734: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   739: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.hoverEvent:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEvent;
        //   744: astore          8
        //   746: aload           8
        //   748: ifnull          783
        //   751: aload           8
        //   753: aload_0        
        //   754: aload_2        
        //   755: invokevirtual   com/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEvent.withRenderedValue:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/renderer/ComponentRenderer;Ljava/lang/Object;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEvent;
        //   758: astore          9
        //   760: aload           8
        //   762: aload           9
        //   764: if_acmpeq       783
        //   767: aload           7
        //   769: aload           9
        //   771: invokedynamic   BootstrapMethod #0, accept:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEvent;)Ljava/util/function/Consumer;
        //   776: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:(Ljava/util/function/Consumer;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   781: astore          7
        //   783: iconst_0       
        //   784: iload           5
        //   786: if_icmpge       876
        //   789: aload           4
        //   791: iconst_0       
        //   792: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   797: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   800: astore          11
        //   802: aload_0        
        //   803: aload           11
        //   805: aload_2        
        //   806: invokevirtual   com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer.render:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   809: astore          12
        //   811: aload           12
        //   813: aload           11
        //   815: if_acmpeq       855
        //   818: aload           6
        //   820: ifnonnull       834
        //   823: new             Ljava/util/ArrayList;
        //   826: dup            
        //   827: iload           5
        //   829: invokespecial   java/util/ArrayList.<init>:(I)V
        //   832: astore          6
        //   834: iconst_0       
        //   835: ifeq            855
        //   838: aload           6
        //   840: aload           4
        //   842: iconst_0       
        //   843: iconst_0       
        //   844: invokeinterface java/util/List.subList:(II)Ljava/util/List;
        //   849: invokeinterface java/util/List.addAll:(Ljava/util/Collection;)Z
        //   854: pop            
        //   855: aload           6
        //   857: ifnull          870
        //   860: aload           6
        //   862: aload           12
        //   864: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   869: pop            
        //   870: iinc            10, 1
        //   873: goto            783
        //   876: goto            894
        //   879: aload           6
        //   881: ifnull          894
        //   884: aload           6
        //   886: aload           4
        //   888: invokeinterface java/util/List.addAll:(Ljava/util/Collection;)Z
        //   893: pop            
        //   894: aload_2        
        //   895: iload_3        
        //   896: putfield        com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.firstMatch:Z
        //   899: aload           6
        //   901: ifnull          914
        //   904: aload           7
        //   906: aload           6
        //   908: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.children:(Ljava/util/List;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   913: areturn        
        //   914: aload           7
        //   916: areturn        
        //    RuntimeInvisibleTypeAnnotations: 00 03 14 00 00 2D 00 00 16 00 00 00 2D 00 00 16 01 00 00 2D 00 00
        //    MethodParameters:
        //  Name       Flags  
        //  ---------  -----
        //  component  FINAL
        //  state      FINAL
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
    
    @NotNull
    @Override
    public Component render(@NotNull final Component component, @NotNull final Object state) {
        return this.render(component, (State)state);
    }
    
    private static void lambda$render$0(final HoverEvent source, final Style.Builder builder) {
        builder.hoverEvent(source);
    }
    
    static {
        INSTANCE = new TextReplacementRenderer();
    }
    
    static final class State
    {
        final Pattern pattern;
        final BiFunction replacement;
        final TextReplacementConfig.Condition continuer;
        boolean running;
        int matchCount;
        int replaceCount;
        boolean firstMatch;
        
        State(@NotNull final Pattern pattern, @NotNull final BiFunction replacement, final TextReplacementConfig.Condition continuer) {
            this.running = true;
            this.matchCount = 0;
            this.replaceCount = 0;
            this.firstMatch = true;
            this.pattern = pattern;
            this.replacement = replacement;
            this.continuer = continuer;
        }
    }
}
