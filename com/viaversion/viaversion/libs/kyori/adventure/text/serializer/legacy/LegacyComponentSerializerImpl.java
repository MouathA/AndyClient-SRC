package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import java.util.regex.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import java.util.*;

final class LegacyComponentSerializerImpl implements LegacyComponentSerializer
{
    static final Pattern DEFAULT_URL_PATTERN;
    static final Pattern URL_SCHEME_PATTERN;
    private static final TextDecoration[] DECORATIONS;
    private static final char LEGACY_BUNGEE_HEX_CHAR = 'x';
    private static final List FORMATS;
    private static final String LEGACY_CHARS;
    private static final Optional SERVICE;
    static final Consumer BUILDER;
    private final char character;
    private final char hexCharacter;
    @Nullable
    private final TextReplacementConfig urlReplacementConfig;
    private final boolean hexColours;
    private final boolean useTerriblyStupidHexFormat;
    private final ComponentFlattener flattener;
    
    LegacyComponentSerializerImpl(final char character, final char hexCharacter, @Nullable final TextReplacementConfig urlReplacementConfig, final boolean hexColours, final boolean useTerriblyStupidHexFormat, final ComponentFlattener flattener) {
        this.character = character;
        this.hexCharacter = hexCharacter;
        this.urlReplacementConfig = urlReplacementConfig;
        this.hexColours = hexColours;
        this.useTerriblyStupidHexFormat = useTerriblyStupidHexFormat;
        this.flattener = flattener;
    }
    
    @Nullable
    private FormatCodeType determineFormatType(final char legacy, final String input, final int pos) {
        if (pos >= 14) {
            final int n = pos - 14;
            final int n2 = pos - 13;
            if (input.charAt(n) == this.character && input.charAt(n2) == 'x') {
                return FormatCodeType.BUNGEECORD_UNUSUAL_HEX;
            }
        }
        if (legacy == this.hexCharacter && input.length() - pos >= 6) {
            return FormatCodeType.KYORI_HEX;
        }
        if (LegacyComponentSerializerImpl.LEGACY_CHARS.indexOf(legacy) != -1) {
            return FormatCodeType.MOJANG_LEGACY;
        }
        return null;
    }
    
    @Nullable
    static LegacyFormat legacyFormat(final char character) {
        final int index = LegacyComponentSerializerImpl.LEGACY_CHARS.indexOf(character);
        if (index != -1) {
            final TextFormat textFormat = LegacyComponentSerializerImpl.FORMATS.get(index);
            if (textFormat instanceof NamedTextColor) {
                return new LegacyFormat((NamedTextColor)textFormat);
            }
            if (textFormat instanceof TextDecoration) {
                return new LegacyFormat((TextDecoration)textFormat);
            }
            if (textFormat instanceof Reset) {
                return LegacyFormat.RESET;
            }
        }
        return null;
    }
    
    @Nullable
    private DecodedFormat decodeTextFormat(final char legacy, final String input, final int pos) {
        final FormatCodeType determineFormatType = this.determineFormatType(legacy, input, pos);
        if (determineFormatType == null) {
            return null;
        }
        if (determineFormatType == FormatCodeType.KYORI_HEX) {
            final TextColor tryParseHexColor = tryParseHexColor(input.substring(pos, pos + 6));
            if (tryParseHexColor != null) {
                return new DecodedFormat(determineFormatType, tryParseHexColor, null);
            }
        }
        else {
            if (determineFormatType == FormatCodeType.MOJANG_LEGACY) {
                return new DecodedFormat(determineFormatType, LegacyComponentSerializerImpl.FORMATS.get(LegacyComponentSerializerImpl.LEGACY_CHARS.indexOf(legacy)), null);
            }
            if (determineFormatType == FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
                final StringBuilder sb = new StringBuilder(6);
                for (int i = pos - 1; i >= pos - 11; i -= 2) {
                    sb.append(input.charAt(i));
                }
                final TextColor tryParseHexColor2 = tryParseHexColor(sb.reverse().toString());
                if (tryParseHexColor2 != null) {
                    return new DecodedFormat(determineFormatType, tryParseHexColor2, null);
                }
            }
        }
        return null;
    }
    
    @Nullable
    private static TextColor tryParseHexColor(final String hexDigits) {
        return TextColor.color(Integer.parseInt(hexDigits, 16));
    }
    
    private static boolean isHexTextColor(final TextFormat format) {
        return format instanceof TextColor && !(format instanceof NamedTextColor);
    }
    
    private String toLegacyCode(TextFormat var_1_8A) {
        if (isHexTextColor(var_1_8A)) {
            final TextColor any = (TextColor)var_1_8A;
            if (this.hexColours) {
                final String format = String.format("%06x", any.value());
                if (this.useTerriblyStupidHexFormat) {
                    final StringBuilder sb = new StringBuilder(String.valueOf('x'));
                    final char[] charArray = format.toCharArray();
                    while (0 < charArray.length) {
                        sb.append(this.character).append(charArray[0]);
                        int n = 0;
                        ++n;
                    }
                    return sb.toString();
                }
                return this.hexCharacter + format;
            }
            else {
                var_1_8A = NamedTextColor.nearestTo(any);
            }
        }
        return Character.toString(LegacyComponentSerializerImpl.LEGACY_CHARS.charAt(LegacyComponentSerializerImpl.FORMATS.indexOf(var_1_8A)));
    }
    
    private TextComponent extractUrl(final TextComponent component) {
        if (this.urlReplacementConfig == null) {
            return component;
        }
        final Component replaceText = component.replaceText(this.urlReplacementConfig);
        if (replaceText instanceof TextComponent) {
            return (TextComponent)replaceText;
        }
        return (TextComponent)((TextComponent.Builder)Component.text().append(replaceText)).build();
    }
    
    @NotNull
    @Override
    public TextComponent deserialize(@NotNull final String input) {
        int i = input.lastIndexOf(this.character, input.length() - 2);
        if (i == -1) {
            return this.extractUrl(Component.text(input));
        }
        final ArrayList<TextComponent> components = new ArrayList<TextComponent>();
        TextComponent.Builder builder = null;
        int length = input.length();
        do {
            final DecodedFormat decodeTextFormat = this.decodeTextFormat(input.charAt(i + 1), input, i + 2);
            if (decodeTextFormat != null) {
                final int n = i + ((decodeTextFormat.encodedFormat == FormatCodeType.KYORI_HEX) ? 8 : 2);
                if (n != length) {
                    if (builder != null) {
                        if (false) {
                            components.add((TextComponent)builder.build());
                            builder = Component.text();
                        }
                        else {
                            builder = (TextComponent.Builder)Component.text().append(builder.build());
                        }
                    }
                    else {
                        builder = Component.text();
                    }
                    builder.content(input.substring(n, length));
                }
                else if (builder == null) {
                    builder = Component.text();
                }
                if (!false) {
                    applyFormat(builder, decodeTextFormat.format);
                }
                if (decodeTextFormat.encodedFormat == FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
                    i -= 12;
                }
                length = i;
            }
            i = input.lastIndexOf(this.character, i - 1);
        } while (i != -1);
        if (builder != null) {
            components.add((TextComponent)builder.build());
        }
        final String content = (length > 0) ? input.substring(0, length) : "";
        if (components.size() == 1 && content.isEmpty()) {
            return this.extractUrl((TextComponent)components.get(0));
        }
        Collections.reverse(components);
        return this.extractUrl((TextComponent)((TextComponent.Builder)Component.text().content(content).append(components)).build());
    }
    
    @NotNull
    @Override
    public String serialize(@NotNull final Component component) {
        final Cereal listener = new Cereal(null);
        this.flattener.flatten(component, listener);
        return listener.toString();
    }
    
    private static boolean applyFormat(final TextComponent.Builder builder, @NotNull final TextFormat format) {
        if (format instanceof TextColor) {
            builder.colorIfAbsent((TextColor)format);
            return true;
        }
        if (format instanceof TextDecoration) {
            builder.decoration((TextDecoration)format, TextDecoration.State.TRUE);
            return false;
        }
        if (format instanceof Reset) {
            return true;
        }
        throw new IllegalArgumentException(String.format("unknown format '%s'", format.getClass()));
    }
    
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }
    
    @NotNull
    @Override
    public Object serialize(@NotNull final Component component) {
        return this.serialize(component);
    }
    
    @NotNull
    @Override
    public Component deserialize(@NotNull final Object input) {
        return this.deserialize((String)input);
    }
    
    @NotNull
    @Override
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    private static Consumer lambda$static$1() {
        return LegacyComponentSerializerImpl::lambda$static$0;
    }
    
    private static void lambda$static$0(final Builder builder) {
    }
    
    static Optional access$000() {
        return LegacyComponentSerializerImpl.SERVICE;
    }
    
    static String access$300(final LegacyComponentSerializerImpl legacyComponentSerializerImpl, final TextFormat format) {
        return legacyComponentSerializerImpl.toLegacyCode(format);
    }
    
    static char access$400(final LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.character;
    }
    
    static TextDecoration[] access$500() {
        return LegacyComponentSerializerImpl.DECORATIONS;
    }
    
    static char access$800(final LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.hexCharacter;
    }
    
    static TextReplacementConfig access$900(final LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.urlReplacementConfig;
    }
    
    static boolean access$1000(final LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.hexColours;
    }
    
    static boolean access$1100(final LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.useTerriblyStupidHexFormat;
    }
    
    static ComponentFlattener access$1200(final LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.flattener;
    }
    
    static {
        DEFAULT_URL_PATTERN = Pattern.compile("(?:(https?)://)?([-\\w_.]+\\.\\w{2,})(/\\S*)?");
        URL_SCHEME_PATTERN = Pattern.compile("^[a-z][a-z0-9+\\-.]*:");
        DECORATIONS = TextDecoration.values();
        final LinkedHashMap<Reset, String> linkedHashMap = new LinkedHashMap<Reset, String>(22);
        linkedHashMap.put(NamedTextColor.BLACK, "0");
        linkedHashMap.put((Reset)NamedTextColor.DARK_BLUE, "1");
        linkedHashMap.put((Reset)NamedTextColor.DARK_GREEN, "2");
        linkedHashMap.put((Reset)NamedTextColor.DARK_AQUA, "3");
        linkedHashMap.put((Reset)NamedTextColor.DARK_RED, "4");
        linkedHashMap.put((Reset)NamedTextColor.DARK_PURPLE, "5");
        linkedHashMap.put((Reset)NamedTextColor.GOLD, "6");
        linkedHashMap.put((Reset)NamedTextColor.GRAY, "7");
        linkedHashMap.put((Reset)NamedTextColor.DARK_GRAY, "8");
        linkedHashMap.put((Reset)NamedTextColor.BLUE, "9");
        linkedHashMap.put((Reset)NamedTextColor.GREEN, "a");
        linkedHashMap.put((Reset)NamedTextColor.AQUA, "b");
        linkedHashMap.put((Reset)NamedTextColor.RED, "c");
        linkedHashMap.put((Reset)NamedTextColor.LIGHT_PURPLE, "d");
        linkedHashMap.put((Reset)NamedTextColor.YELLOW, "e");
        linkedHashMap.put((Reset)NamedTextColor.WHITE, "f");
        linkedHashMap.put((Reset)TextDecoration.OBFUSCATED, "k");
        linkedHashMap.put((Reset)TextDecoration.BOLD, "l");
        linkedHashMap.put((Reset)TextDecoration.STRIKETHROUGH, "m");
        linkedHashMap.put((Reset)TextDecoration.UNDERLINED, "n");
        linkedHashMap.put((Reset)TextDecoration.ITALIC, "o");
        linkedHashMap.put(Reset.INSTANCE, "r");
        FORMATS = Collections.unmodifiableList((List<?>)new ArrayList<Object>(linkedHashMap.keySet()));
        LEGACY_CHARS = String.join("", (Iterable<? extends CharSequence>)linkedHashMap.values());
        if (LegacyComponentSerializerImpl.FORMATS.size() != LegacyComponentSerializerImpl.LEGACY_CHARS.length()) {
            throw new IllegalStateException("FORMATS length differs from LEGACY_CHARS length");
        }
        SERVICE = Services.service(Provider.class);
        BUILDER = LegacyComponentSerializerImpl.SERVICE.map(Provider::legacy).orElseGet(LegacyComponentSerializerImpl::lambda$static$1);
    }
    
    static final class DecodedFormat
    {
        final FormatCodeType encodedFormat;
        final TextFormat format;
        
        private DecodedFormat(final FormatCodeType encodedFormat, final TextFormat format) {
            if (format == null) {
                throw new IllegalStateException("No format found");
            }
            this.encodedFormat = encodedFormat;
            this.format = format;
        }
        
        DecodedFormat(final FormatCodeType encodedFormat, final TextFormat format, final LegacyComponentSerializerImpl$1 object) {
            this(encodedFormat, format);
        }
    }
    
    enum FormatCodeType
    {
        MOJANG_LEGACY, 
        KYORI_HEX, 
        BUNGEECORD_UNUSUAL_HEX;
        
        private static final FormatCodeType[] $VALUES;
        
        static {
            $VALUES = new FormatCodeType[] { FormatCodeType.MOJANG_LEGACY, FormatCodeType.KYORI_HEX, FormatCodeType.BUNGEECORD_UNUSUAL_HEX };
        }
    }
    
    static final class BuilderImpl implements Builder
    {
        private char character;
        private char hexCharacter;
        private TextReplacementConfig urlReplacementConfig;
        private boolean hexColours;
        private boolean useTerriblyStupidHexFormat;
        private ComponentFlattener flattener;
        
        BuilderImpl() {
            this.character = '§';
            this.hexCharacter = '#';
            this.urlReplacementConfig = null;
            this.hexColours = false;
            this.useTerriblyStupidHexFormat = false;
            this.flattener = ComponentFlattener.basic();
            LegacyComponentSerializerImpl.BUILDER.accept(this);
        }
        
        BuilderImpl(@NotNull final LegacyComponentSerializerImpl serializer) {
            this();
            this.character = LegacyComponentSerializerImpl.access$400(serializer);
            this.hexCharacter = LegacyComponentSerializerImpl.access$800(serializer);
            this.urlReplacementConfig = LegacyComponentSerializerImpl.access$900(serializer);
            this.hexColours = LegacyComponentSerializerImpl.access$1000(serializer);
            this.useTerriblyStupidHexFormat = LegacyComponentSerializerImpl.access$1100(serializer);
            this.flattener = LegacyComponentSerializerImpl.access$1200(serializer);
        }
        
        @NotNull
        @Override
        public Builder character(final char legacyCharacter) {
            this.character = legacyCharacter;
            return this;
        }
        
        @NotNull
        @Override
        public Builder hexCharacter(final char legacyHexCharacter) {
            this.hexCharacter = legacyHexCharacter;
            return this;
        }
        
        @NotNull
        @Override
        public Builder extractUrls() {
            return this.extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, null);
        }
        
        @NotNull
        @Override
        public Builder extractUrls(@NotNull final Pattern pattern) {
            return this.extractUrls(pattern, null);
        }
        
        @NotNull
        @Override
        public Builder extractUrls(@Nullable final Style style) {
            return this.extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, style);
        }
        
        @NotNull
        @Override
        public Builder extractUrls(@NotNull final Pattern pattern, @Nullable final Style style) {
            Objects.requireNonNull(pattern, "pattern");
            this.urlReplacementConfig = (TextReplacementConfig)TextReplacementConfig.builder().match(pattern).replacement(BuilderImpl::lambda$extractUrls$0).build();
            return this;
        }
        
        @NotNull
        @Override
        public Builder hexColors() {
            this.hexColours = true;
            return this;
        }
        
        @NotNull
        @Override
        public Builder useUnusualXRepeatedCharacterHexFormat() {
            this.useTerriblyStupidHexFormat = true;
            return this;
        }
        
        @NotNull
        @Override
        public Builder flattener(@NotNull final ComponentFlattener flattener) {
            this.flattener = Objects.requireNonNull(flattener, "flattener");
            return this;
        }
        
        @NotNull
        @Override
        public LegacyComponentSerializer build() {
            return new LegacyComponentSerializerImpl(this.character, this.hexCharacter, this.urlReplacementConfig, this.hexColours, this.useTerriblyStupidHexFormat, this.flattener);
        }
        
        @NotNull
        @Override
        public Object build() {
            return this.build();
        }
        
        private static ComponentLike lambda$extractUrls$0(final Style style, final TextComponent.Builder builder) {
            String url = builder.content();
            if (!LegacyComponentSerializerImpl.URL_SCHEME_PATTERN.matcher(url).find()) {
                url = "http://" + url;
            }
            return ((style == null) ? builder : builder.style(style)).clickEvent(ClickEvent.openUrl(url));
        }
    }
    
    private final class Cereal implements FlattenerListener
    {
        private final StringBuilder sb;
        private final StyleState style;
        @Nullable
        private TextFormat lastWritten;
        private StyleState[] styles;
        private int head;
        final LegacyComponentSerializerImpl this$0;
        
        private Cereal(final LegacyComponentSerializerImpl this$0) {
            this.this$0 = this$0;
            this.sb = new StringBuilder();
            this.style = new StyleState();
            this.styles = new StyleState[8];
            this.head = -1;
        }
        
        @Override
        public void pushStyle(@NotNull final Style pushed) {
            final int n = ++this.head;
            if (n >= this.styles.length) {
                this.styles = Arrays.copyOf(this.styles, this.styles.length * 2);
            }
            StyleState styleState = this.styles[n];
            if (styleState == null) {
                styleState = (this.styles[n] = new StyleState());
            }
            if (n > 0) {
                styleState.set(this.styles[n - 1]);
            }
            else {
                styleState.clear();
            }
            styleState.apply(pushed);
        }
        
        @Override
        public void component(@NotNull final String text) {
            if (!text.isEmpty()) {
                if (this.head < 0) {
                    throw new IllegalStateException("No style has been pushed!");
                }
                this.styles[this.head].applyFormat();
                this.sb.append(text);
            }
        }
        
        @Override
        public void popStyle(@NotNull final Style style) {
            if (this.head-- < 0) {
                throw new IllegalStateException("Tried to pop beyond what was pushed!");
            }
        }
        
        void append(@NotNull final TextFormat format) {
            if (this.lastWritten != format) {
                this.sb.append(LegacyComponentSerializerImpl.access$400(this.this$0)).append(LegacyComponentSerializerImpl.access$300(this.this$0, format));
            }
            this.lastWritten = format;
        }
        
        @Override
        public String toString() {
            return this.sb.toString();
        }
        
        Cereal(final LegacyComponentSerializerImpl this$0, final LegacyComponentSerializerImpl$1 object) {
            this();
        }
        
        static StyleState access$600(final Cereal cereal) {
            return cereal.style;
        }
        
        static TextFormat access$700(final Cereal cereal) {
            return cereal.lastWritten;
        }
        
        private final class StyleState
        {
            @Nullable
            private TextColor color;
            private final Set decorations;
            private boolean needsReset;
            final Cereal this$1;
            
            StyleState(final Cereal this$1) {
                this.this$1 = this$1;
                this.decorations = EnumSet.noneOf(TextDecoration.class);
            }
            
            void set(@NotNull final StyleState that) {
                this.color = that.color;
                this.decorations.clear();
                this.decorations.addAll(that.decorations);
            }
            
            public void clear() {
                this.color = null;
                this.decorations.clear();
            }
            
            void apply(@NotNull final Style component) {
                final TextColor color = component.color();
                if (color != null) {
                    this.color = color;
                }
                while (0 < LegacyComponentSerializerImpl.access$500().length) {
                    final TextDecoration decoration = LegacyComponentSerializerImpl.access$500()[0];
                    switch (LegacyComponentSerializerImpl$1.$SwitchMap$net$kyori$adventure$text$format$TextDecoration$State[component.decoration(decoration).ordinal()]) {
                        case 1: {
                            this.decorations.add(decoration);
                            break;
                        }
                        case 2: {
                            if (this.decorations.remove(decoration)) {
                                this.needsReset = true;
                                break;
                            }
                            break;
                        }
                    }
                    int n = 0;
                    ++n;
                }
            }
            
            void applyFormat() {
                final boolean b = this.color != Cereal.access$600(this.this$1).color;
                if (this.needsReset) {
                    if (!b) {
                        this.this$1.append(Reset.INSTANCE);
                    }
                    this.needsReset = false;
                }
                if (b || Cereal.access$700(this.this$1) == Reset.INSTANCE) {
                    this.applyFullFormat();
                    return;
                }
                if (!this.decorations.containsAll(Cereal.access$600(this.this$1).decorations)) {
                    this.applyFullFormat();
                    return;
                }
                for (final TextDecoration format : this.decorations) {
                    if (Cereal.access$600(this.this$1).decorations.add(format)) {
                        this.this$1.append(format);
                    }
                }
            }
            
            private void applyFullFormat() {
                if (this.color != null) {
                    this.this$1.append(this.color);
                }
                else {
                    this.this$1.append(Reset.INSTANCE);
                }
                Cereal.access$600(this.this$1).color = this.color;
                final Iterator<TextDecoration> iterator = this.decorations.iterator();
                while (iterator.hasNext()) {
                    this.this$1.append(iterator.next());
                }
                Cereal.access$600(this.this$1).decorations.clear();
                Cereal.access$600(this.this$1).decorations.addAll(this.decorations);
            }
        }
    }
    
    private enum Reset implements TextFormat
    {
        INSTANCE;
        
        private static final Reset[] $VALUES;
        
        static {
            $VALUES = new Reset[] { Reset.INSTANCE };
        }
    }
    
    static final class Instances
    {
        static final LegacyComponentSerializer SECTION;
        static final LegacyComponentSerializer AMPERSAND;
        
        private static LegacyComponentSerializer lambda$static$1() {
            return new LegacyComponentSerializerImpl('&', '#', null, false, false, ComponentFlattener.basic());
        }
        
        private static LegacyComponentSerializer lambda$static$0() {
            return new LegacyComponentSerializerImpl('§', '#', null, false, false, ComponentFlattener.basic());
        }
        
        static {
            SECTION = LegacyComponentSerializerImpl.access$000().map(Provider::legacySection).orElseGet(Instances::lambda$static$0);
            AMPERSAND = LegacyComponentSerializerImpl.access$000().map(Provider::legacyAmpersand).orElseGet(Instances::lambda$static$1);
        }
    }
}
