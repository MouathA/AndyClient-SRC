package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;

final class ComponentCompaction
{
    private static final TextDecoration[] DECORATIONS;
    
    private ComponentCompaction() {
    }
    
    static Component compact(@NotNull final Component self, @Nullable final Style parentStyle) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.children:()Ljava/util/List;
        //     6: astore_2       
        //     7: aload_0        
        //     8: invokestatic    java/util/Collections.emptyList:()Ljava/util/List;
        //    11: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.children:(Ljava/util/List;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //    16: astore_3       
        //    17: aload_1        
        //    18: ifnull          38
        //    21: aload_3        
        //    22: aload_0        
        //    23: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //    28: aload_1        
        //    29: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/ComponentCompaction.simplifyStyle:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //    32: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //    37: astore_3       
        //    38: aload_2        
        //    39: invokeinterface java/util/List.size:()I
        //    44: istore          4
        //    46: iload           4
        //    48: ifne            53
        //    51: aload_3        
        //    52: areturn        
        //    53: iload           4
        //    55: iconst_1       
        //    56: if_icmpne       131
        //    59: aload_0        
        //    60: instanceof      Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //    63: ifeq            131
        //    66: aload_0        
        //    67: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //    70: astore          5
        //    72: aload           5
        //    74: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/TextComponent.content:()Ljava/lang/String;
        //    79: invokevirtual   java/lang/String.isEmpty:()Z
        //    82: ifeq            131
        //    85: aload_2        
        //    86: iconst_0       
        //    87: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    92: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //    95: astore          6
        //    97: aload           6
        //    99: aload           6
        //   101: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   106: aload_3        
        //   107: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   112: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy.IF_ABSENT_ON_TARGET:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;
        //   115: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.merge:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   120: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   125: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.compact:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   130: areturn        
        //   131: aload_3        
        //   132: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   137: astore          5
        //   139: aload_1        
        //   140: ifnull          156
        //   143: aload           5
        //   145: aload_1        
        //   146: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy.IF_ABSENT_ON_TARGET:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;
        //   149: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.merge:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   154: astore          5
        //   156: new             Ljava/util/ArrayList;
        //   159: dup            
        //   160: aload_2        
        //   161: invokeinterface java/util/List.size:()I
        //   166: invokespecial   java/util/ArrayList.<init>:(I)V
        //   169: astore          6
        //   171: iconst_0       
        //   172: aload_2        
        //   173: invokeinterface java/util/List.size:()I
        //   178: if_icmpge       210
        //   181: aload           6
        //   183: aload_2        
        //   184: iconst_0       
        //   185: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   190: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   193: aload           5
        //   195: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/ComponentCompaction.compact:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   198: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   203: pop            
        //   204: iinc            7, 1
        //   207: goto            171
        //   210: aload           6
        //   212: invokeinterface java/util/List.isEmpty:()Z
        //   217: ifne            318
        //   220: aload           6
        //   222: iconst_0       
        //   223: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   228: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   231: astore          7
        //   233: aload           7
        //   235: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   240: aload           5
        //   242: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy.IF_ABSENT_ON_TARGET:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;
        //   245: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.merge:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   250: astore          8
        //   252: aload_3        
        //   253: instanceof      Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   256: ifeq            318
        //   259: aload           7
        //   261: instanceof      Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   264: ifeq            318
        //   267: aload           8
        //   269: aload           5
        //   271: invokestatic    java/util/Objects.equals:(Ljava/lang/Object;Ljava/lang/Object;)Z
        //   274: ifeq            318
        //   277: aload_3        
        //   278: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   281: aload           7
        //   283: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   286: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/ComponentCompaction.joinText:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   289: astore_3       
        //   290: aload           6
        //   292: iconst_0       
        //   293: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   298: pop            
        //   299: aload           6
        //   301: iconst_0       
        //   302: aload           7
        //   304: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.children:()Ljava/util/List;
        //   309: invokeinterface java/util/List.addAll:(ILjava/util/Collection;)Z
        //   314: pop            
        //   315: goto            210
        //   318: iconst_1       
        //   319: aload           6
        //   321: invokeinterface java/util/List.size:()I
        //   326: if_icmpge       478
        //   329: aload           6
        //   331: iconst_0       
        //   332: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   337: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   340: astore          8
        //   342: aload           6
        //   344: iconst_1       
        //   345: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   350: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   353: astore          9
        //   355: aload           8
        //   357: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   362: aload           5
        //   364: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy.IF_ABSENT_ON_TARGET:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;
        //   367: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.merge:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   372: astore          10
        //   374: aload           9
        //   376: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.style:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   381: aload           5
        //   383: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy.IF_ABSENT_ON_TARGET:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;
        //   386: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.merge:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //   391: astore          11
        //   393: aload           8
        //   395: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.children:()Ljava/util/List;
        //   400: invokeinterface java/util/List.isEmpty:()Z
        //   405: ifeq            472
        //   408: aload           8
        //   410: instanceof      Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   413: ifeq            472
        //   416: aload           9
        //   418: instanceof      Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   421: ifeq            472
        //   424: aload           10
        //   426: aload           11
        //   428: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   431: ifeq            472
        //   434: aload           8
        //   436: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   439: aload           9
        //   441: checkcast       Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   444: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/ComponentCompaction.joinText:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/TextComponent;
        //   447: astore          12
        //   449: aload           6
        //   451: iconst_0       
        //   452: aload           12
        //   454: invokeinterface java/util/List.set:(ILjava/lang/Object;)Ljava/lang/Object;
        //   459: pop            
        //   460: aload           6
        //   462: iconst_1       
        //   463: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   468: pop            
        //   469: goto            475
        //   472: iinc            7, 1
        //   475: goto            318
        //   478: aload_3        
        //   479: aload           6
        //   481: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/Component.children:(Ljava/util/List;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/Component;
        //   486: areturn        
        //    RuntimeInvisibleTypeAnnotations: 00 02 16 00 00 00 1F 00 00 16 01 00 00 20 00 00
        //    MethodParameters:
        //  Name         Flags  
        //  -----------  -----
        //  self         FINAL
        //  parentStyle  FINAL
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @NotNull
    private static Style simplifyStyle(@NotNull final Style style, @NotNull final Style parentStyle) {
        if (style.isEmpty()) {
            return style;
        }
        final Style.Builder builder = style.toBuilder();
        if (Objects.equals(style.font(), parentStyle.font())) {
            builder.font(null);
        }
        if (Objects.equals(style.color(), parentStyle.color())) {
            builder.color(null);
        }
        final TextDecoration[] decorations = ComponentCompaction.DECORATIONS;
        while (0 < decorations.length) {
            final TextDecoration decoration = decorations[0];
            if (style.decoration(decoration) == parentStyle.decoration(decoration)) {
                builder.decoration(decoration, TextDecoration.State.NOT_SET);
            }
            int n = 0;
            ++n;
        }
        if (Objects.equals(style.clickEvent(), parentStyle.clickEvent())) {
            builder.clickEvent(null);
        }
        if (Objects.equals(style.hoverEvent(), parentStyle.hoverEvent())) {
            builder.hoverEvent(null);
        }
        if (Objects.equals(style.insertion(), parentStyle.insertion())) {
            builder.insertion(null);
        }
        return builder.build();
    }
    
    private static TextComponent joinText(final TextComponent one, final TextComponent two) {
        return new TextComponentImpl(two.children(), one.style(), one.content() + two.content());
    }
    
    static {
        DECORATIONS = TextDecoration.values();
    }
}
