package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import java.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

final class StyleImpl implements Style
{
    static final StyleImpl EMPTY;
    private static final TextDecoration[] DECORATIONS;
    @Nullable
    final Key font;
    @Nullable
    final TextColor color;
    final TextDecoration.State obfuscated;
    final TextDecoration.State bold;
    final TextDecoration.State strikethrough;
    final TextDecoration.State underlined;
    final TextDecoration.State italic;
    @Nullable
    final ClickEvent clickEvent;
    @Nullable
    final HoverEvent hoverEvent;
    @Nullable
    final String insertion;
    
    static void decorate(final Builder builder, final TextDecoration[] decorations) {
        while (0 < decorations.length) {
            builder.decoration(decorations[0], true);
            int n = 0;
            ++n;
        }
    }
    
    StyleImpl(@Nullable final Key font, @Nullable final TextColor color, final TextDecoration.State obfuscated, final TextDecoration.State bold, final TextDecoration.State strikethrough, final TextDecoration.State underlined, final TextDecoration.State italic, @Nullable final ClickEvent clickEvent, @Nullable final HoverEvent hoverEvent, @Nullable final String insertion) {
        this.font = font;
        this.color = color;
        this.obfuscated = obfuscated;
        this.bold = bold;
        this.strikethrough = strikethrough;
        this.underlined = underlined;
        this.italic = italic;
        this.clickEvent = clickEvent;
        this.hoverEvent = hoverEvent;
        this.insertion = insertion;
    }
    
    @Nullable
    @Override
    public Key font() {
        return this.font;
    }
    
    @NotNull
    @Override
    public Style font(@Nullable final Key font) {
        if (Objects.equals(this.font, font)) {
            return this;
        }
        return new StyleImpl(font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
    }
    
    @Nullable
    @Override
    public TextColor color() {
        return this.color;
    }
    
    @NotNull
    @Override
    public Style color(@Nullable final TextColor color) {
        if (Objects.equals(this.color, color)) {
            return this;
        }
        return new StyleImpl(this.font, color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
    }
    
    @NotNull
    @Override
    public Style colorIfAbsent(@Nullable final TextColor color) {
        if (this.color == null) {
            return this.color(color);
        }
        return this;
    }
    
    @Override
    public TextDecoration.State decoration(@NotNull final TextDecoration decoration) {
        if (decoration == TextDecoration.BOLD) {
            return this.bold;
        }
        if (decoration == TextDecoration.ITALIC) {
            return this.italic;
        }
        if (decoration == TextDecoration.UNDERLINED) {
            return this.underlined;
        }
        if (decoration == TextDecoration.STRIKETHROUGH) {
            return this.strikethrough;
        }
        if (decoration == TextDecoration.OBFUSCATED) {
            return this.obfuscated;
        }
        throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
    }
    
    @NotNull
    @Override
    public Style decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
        Objects.requireNonNull(state, "state");
        if (decoration == TextDecoration.BOLD) {
            return new StyleImpl(this.font, this.color, this.obfuscated, state, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
        }
        if (decoration == TextDecoration.ITALIC) {
            return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, state, this.clickEvent, this.hoverEvent, this.insertion);
        }
        if (decoration == TextDecoration.UNDERLINED) {
            return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, state, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
        }
        if (decoration == TextDecoration.STRIKETHROUGH) {
            return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, state, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
        }
        if (decoration == TextDecoration.OBFUSCATED) {
            return new StyleImpl(this.font, this.color, state, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
        }
        throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
    }
    
    @NotNull
    @Override
    public Map decorations() {
        final EnumMap<TextDecoration, TextDecoration.State> enumMap = new EnumMap<TextDecoration, TextDecoration.State>(TextDecoration.class);
        while (0 < StyleImpl.DECORATIONS.length) {
            final TextDecoration decoration = StyleImpl.DECORATIONS[0];
            enumMap.put(decoration, this.decoration(decoration));
            int n = 0;
            ++n;
        }
        return enumMap;
    }
    
    @NotNull
    @Override
    public Style decorations(@NotNull final Map decorations) {
        return new StyleImpl(this.font, this.color, decorations.getOrDefault(TextDecoration.OBFUSCATED, this.obfuscated), decorations.getOrDefault(TextDecoration.BOLD, this.bold), decorations.getOrDefault(TextDecoration.STRIKETHROUGH, this.strikethrough), decorations.getOrDefault(TextDecoration.UNDERLINED, this.underlined), decorations.getOrDefault(TextDecoration.ITALIC, this.italic), this.clickEvent, this.hoverEvent, this.insertion);
    }
    
    @Nullable
    @Override
    public ClickEvent clickEvent() {
        return this.clickEvent;
    }
    
    @NotNull
    @Override
    public Style clickEvent(@Nullable final ClickEvent event) {
        return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, event, this.hoverEvent, this.insertion);
    }
    
    @Nullable
    @Override
    public HoverEvent hoverEvent() {
        return this.hoverEvent;
    }
    
    @NotNull
    @Override
    public Style hoverEvent(@Nullable final HoverEventSource source) {
        return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, HoverEventSource.unbox(source), this.insertion);
    }
    
    @Nullable
    @Override
    public String insertion() {
        return this.insertion;
    }
    
    @NotNull
    @Override
    public Style insertion(@Nullable final String insertion) {
        if (Objects.equals(this.insertion, insertion)) {
            return this;
        }
        return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, insertion);
    }
    
    @NotNull
    @Override
    public Style merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Set merges) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.isEmpty:()Z
        //     6: ifne            25
        //     9: aload_2        
        //    10: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy.NEVER:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;
        //    13: if_acmpeq       25
        //    16: aload_3        
        //    17: invokeinterface java/util/Set.isEmpty:()Z
        //    22: ifeq            27
        //    25: aload_0        
        //    26: areturn        
        //    27: aload_0        
        //    28: if_acmpne       40
        //    31: aload_3        
        //    32: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge.hasAll:(Ljava/util/Set;)Z
        //    35: ifeq            40
        //    38: aload_1        
        //    39: areturn        
        //    40: aload_0        
        //    41: invokevirtual   com/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl.toBuilder:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Builder;
        //    44: astore          4
        //    46: aload           4
        //    48: aload_1        
        //    49: aload_2        
        //    50: aload_3        
        //    51: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Builder.merge:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;Ljava/util/Set;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Builder;
        //    56: pop            
        //    57: aload           4
        //    59: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Builder.build:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style;
        //    64: areturn        
        //    RuntimeInvisibleTypeAnnotations: 00 04 14 00 00 54 00 00 16 00 00 00 54 00 00 16 02 00 00 54 00 00 16 01 00 00 54 00 00
        //    MethodParameters:
        //  Name      Flags  
        //  --------  -----
        //  that      FINAL
        //  strategy  FINAL
        //  merges    FINAL
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("color", this.color), ExaminableProperty.of("obfuscated", this.obfuscated), ExaminableProperty.of("bold", this.bold), ExaminableProperty.of("strikethrough", this.strikethrough), ExaminableProperty.of("underlined", this.underlined), ExaminableProperty.of("italic", this.italic), ExaminableProperty.of("clickEvent", this.clickEvent), ExaminableProperty.of("hoverEvent", this.hoverEvent), ExaminableProperty.of("insertion", this.insertion), ExaminableProperty.of("font", this.font) });
    }
    
    @NotNull
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StyleImpl)) {
            return false;
        }
        final StyleImpl styleImpl = (StyleImpl)other;
        return Objects.equals(this.color, styleImpl.color) && this.obfuscated == styleImpl.obfuscated && this.bold == styleImpl.bold && this.strikethrough == styleImpl.strikethrough && this.underlined == styleImpl.underlined && this.italic == styleImpl.italic && Objects.equals(this.clickEvent, styleImpl.clickEvent) && Objects.equals(this.hoverEvent, styleImpl.hoverEvent) && Objects.equals(this.insertion, styleImpl.insertion) && Objects.equals(this.font, styleImpl.font);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * Objects.hashCode(this.color) + this.obfuscated.hashCode()) + this.bold.hashCode()) + this.strikethrough.hashCode()) + this.underlined.hashCode()) + this.italic.hashCode()) + Objects.hashCode(this.clickEvent)) + Objects.hashCode(this.hoverEvent)) + Objects.hashCode(this.insertion)) + Objects.hashCode(this.font);
    }
    
    @NotNull
    @Override
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    static TextDecoration[] access$000() {
        return StyleImpl.DECORATIONS;
    }
    
    static {
        EMPTY = new StyleImpl(null, null, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, null, null, null);
        DECORATIONS = TextDecoration.values();
    }
    
    static final class BuilderImpl implements Builder
    {
        @Nullable
        Key font;
        @Nullable
        TextColor color;
        TextDecoration.State obfuscated;
        TextDecoration.State bold;
        TextDecoration.State strikethrough;
        TextDecoration.State underlined;
        TextDecoration.State italic;
        @Nullable
        ClickEvent clickEvent;
        @Nullable
        HoverEvent hoverEvent;
        @Nullable
        String insertion;
        
        BuilderImpl() {
            this.obfuscated = TextDecoration.State.NOT_SET;
            this.bold = TextDecoration.State.NOT_SET;
            this.strikethrough = TextDecoration.State.NOT_SET;
            this.underlined = TextDecoration.State.NOT_SET;
            this.italic = TextDecoration.State.NOT_SET;
        }
        
        BuilderImpl(@NotNull final StyleImpl style) {
            this.obfuscated = TextDecoration.State.NOT_SET;
            this.bold = TextDecoration.State.NOT_SET;
            this.strikethrough = TextDecoration.State.NOT_SET;
            this.underlined = TextDecoration.State.NOT_SET;
            this.italic = TextDecoration.State.NOT_SET;
            this.color = style.color;
            this.obfuscated = style.obfuscated;
            this.bold = style.bold;
            this.strikethrough = style.strikethrough;
            this.underlined = style.underlined;
            this.italic = style.italic;
            this.clickEvent = style.clickEvent;
            this.hoverEvent = style.hoverEvent;
            this.insertion = style.insertion;
            this.font = style.font;
        }
        
        @NotNull
        @Override
        public Builder font(@Nullable final Key font) {
            this.font = font;
            return this;
        }
        
        @NotNull
        @Override
        public Builder color(@Nullable final TextColor color) {
            this.color = color;
            return this;
        }
        
        @NotNull
        @Override
        public Builder colorIfAbsent(@Nullable final TextColor color) {
            if (this.color == null) {
                this.color = color;
            }
            return this;
        }
        
        @NotNull
        @Override
        public Builder decorate(@NotNull final TextDecoration decoration) {
            return this.decoration(decoration, TextDecoration.State.TRUE);
        }
        
        @NotNull
        @Override
        public Builder decorate(@NotNull final TextDecoration... decorations) {
            while (0 < decorations.length) {
                this.decorate(decorations[0]);
                int n = 0;
                ++n;
            }
            return this;
        }
        
        @NotNull
        @Override
        public Builder decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
            Objects.requireNonNull(state, "state");
            if (decoration == TextDecoration.BOLD) {
                this.bold = state;
                return this;
            }
            if (decoration == TextDecoration.ITALIC) {
                this.italic = state;
                return this;
            }
            if (decoration == TextDecoration.UNDERLINED) {
                this.underlined = state;
                return this;
            }
            if (decoration == TextDecoration.STRIKETHROUGH) {
                this.strikethrough = state;
                return this;
            }
            if (decoration == TextDecoration.OBFUSCATED) {
                this.obfuscated = state;
                return this;
            }
            throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
        }
        
        @NotNull
        Builder decorationIfAbsent(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
            Objects.requireNonNull(state, "state");
            if (decoration == TextDecoration.BOLD) {
                if (this.bold == TextDecoration.State.NOT_SET) {
                    this.bold = state;
                }
                return this;
            }
            if (decoration == TextDecoration.ITALIC) {
                if (this.italic == TextDecoration.State.NOT_SET) {
                    this.italic = state;
                }
                return this;
            }
            if (decoration == TextDecoration.UNDERLINED) {
                if (this.underlined == TextDecoration.State.NOT_SET) {
                    this.underlined = state;
                }
                return this;
            }
            if (decoration == TextDecoration.STRIKETHROUGH) {
                if (this.strikethrough == TextDecoration.State.NOT_SET) {
                    this.strikethrough = state;
                }
                return this;
            }
            if (decoration == TextDecoration.OBFUSCATED) {
                if (this.obfuscated == TextDecoration.State.NOT_SET) {
                    this.obfuscated = state;
                }
                return this;
            }
            throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
        }
        
        @NotNull
        @Override
        public Builder clickEvent(@Nullable final ClickEvent event) {
            this.clickEvent = event;
            return this;
        }
        
        @NotNull
        @Override
        public Builder hoverEvent(@Nullable final HoverEventSource source) {
            this.hoverEvent = HoverEventSource.unbox(source);
            return this;
        }
        
        @NotNull
        @Override
        public Builder insertion(@Nullable final String insertion) {
            this.insertion = insertion;
            return this;
        }
        
        @NotNull
        @Override
        public Builder merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Set merges) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy.NEVER:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;
            //     4: if_acmpeq       25
            //     7: aload_1        
            //     8: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.isEmpty:()Z
            //    13: ifne            25
            //    16: aload_3        
            //    17: invokeinterface java/util/Set.isEmpty:()Z
            //    22: ifeq            27
            //    25: aload_0        
            //    26: areturn        
            //    27: aload_2        
            //    28: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl$BuilderImpl.merger:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Merger;
            //    31: astore          4
            //    33: aload_3        
            //    34: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge.COLOR:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge;
            //    37: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
            //    42: ifeq            68
            //    45: aload_1        
            //    46: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.color:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/TextColor;
            //    51: astore          5
            //    53: aload           5
            //    55: ifnull          68
            //    58: aload           4
            //    60: aload_0        
            //    61: aload           5
            //    63: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Merger.mergeColor:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl$BuilderImpl;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/TextColor;)V
            //    68: aload_3        
            //    69: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge.DECORATIONS:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge;
            //    72: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
            //    77: ifeq            135
            //    80: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl.access$000:()[Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecoration;
            //    83: arraylength    
            //    84: istore          6
            //    86: iconst_0       
            //    87: iload           6
            //    89: if_icmpge       135
            //    92: invokestatic    com/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl.access$000:()[Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecoration;
            //    95: iconst_0       
            //    96: aaload         
            //    97: astore          7
            //    99: aload_1        
            //   100: aload           7
            //   102: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.decoration:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecoration;)Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecoration$State;
            //   107: astore          8
            //   109: aload           8
            //   111: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecoration$State.NOT_SET:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecoration$State;
            //   114: if_acmpeq       129
            //   117: aload           4
            //   119: aload_0        
            //   120: aload           7
            //   122: aload           8
            //   124: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Merger.mergeDecoration:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl$BuilderImpl;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecoration;Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecoration$State;)V
            //   129: iinc            5, 1
            //   132: goto            86
            //   135: aload_3        
            //   136: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge.EVENTS:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge;
            //   139: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
            //   144: ifeq            193
            //   147: aload_1        
            //   148: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.clickEvent:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/event/ClickEvent;
            //   153: astore          5
            //   155: aload           5
            //   157: ifnull          170
            //   160: aload           4
            //   162: aload_0        
            //   163: aload           5
            //   165: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Merger.mergeClickEvent:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl$BuilderImpl;Lcom/viaversion/viaversion/libs/kyori/adventure/text/event/ClickEvent;)V
            //   170: aload_1        
            //   171: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.hoverEvent:()Lcom/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEvent;
            //   176: astore          6
            //   178: aload           6
            //   180: ifnull          193
            //   183: aload           4
            //   185: aload_0        
            //   186: aload           6
            //   188: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Merger.mergeHoverEvent:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl$BuilderImpl;Lcom/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEvent;)V
            //   193: aload_3        
            //   194: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge.INSERTION:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge;
            //   197: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
            //   202: ifeq            228
            //   205: aload_1        
            //   206: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.insertion:()Ljava/lang/String;
            //   211: astore          5
            //   213: aload           5
            //   215: ifnull          228
            //   218: aload           4
            //   220: aload_0        
            //   221: aload           5
            //   223: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Merger.mergeInsertion:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl$BuilderImpl;Ljava/lang/String;)V
            //   228: aload_3        
            //   229: getstatic       com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge.FONT:Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge;
            //   232: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
            //   237: ifeq            263
            //   240: aload_1        
            //   241: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.font:()Lcom/viaversion/viaversion/libs/kyori/adventure/key/Key;
            //   246: astore          5
            //   248: aload           5
            //   250: ifnull          263
            //   253: aload           4
            //   255: aload_0        
            //   256: aload           5
            //   258: invokeinterface com/viaversion/viaversion/libs/kyori/adventure/text/format/Merger.mergeFont:(Lcom/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl$BuilderImpl;Lcom/viaversion/viaversion/libs/kyori/adventure/key/Key;)V
            //   263: aload_0        
            //   264: areturn        
            //    RuntimeInvisibleTypeAnnotations: 00 04 14 00 00 3C 00 00 16 00 00 00 3C 00 00 16 02 00 00 3C 00 00 16 01 00 00 3C 00 00
            //    MethodParameters:
            //  Name      Flags  
            //  --------  -----
            //  that      FINAL
            //  strategy  FINAL
            //  merges    FINAL
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        private static Merger merger(final Merge.Strategy strategy) {
            if (strategy == Merge.Strategy.ALWAYS) {
                return AlwaysMerger.INSTANCE;
            }
            if (strategy == Merge.Strategy.NEVER) {
                throw new UnsupportedOperationException();
            }
            if (strategy == Merge.Strategy.IF_ABSENT_ON_TARGET) {
                return IfAbsentOnTargetMerger.INSTANCE;
            }
            throw new IllegalArgumentException(strategy.name());
        }
        
        @NotNull
        @Override
        public StyleImpl build() {
            if (this.isEmpty()) {
                return StyleImpl.EMPTY;
            }
            return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
        }
        
        private boolean isEmpty() {
            return this.color == null && this.obfuscated == TextDecoration.State.NOT_SET && this.bold == TextDecoration.State.NOT_SET && this.strikethrough == TextDecoration.State.NOT_SET && this.underlined == TextDecoration.State.NOT_SET && this.italic == TextDecoration.State.NOT_SET && this.clickEvent == null && this.hoverEvent == null && this.insertion == null && this.font == null;
        }
        
        @NotNull
        @Override
        public Style build() {
            return this.build();
        }
        
        @NotNull
        @Override
        public Object build() {
            return this.build();
        }
    }
}
