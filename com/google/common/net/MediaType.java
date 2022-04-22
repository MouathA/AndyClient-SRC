package com.google.common.net;

import com.google.common.annotations.*;
import javax.annotation.concurrent.*;
import java.nio.charset.*;
import java.util.*;
import javax.annotation.*;
import com.google.common.collect.*;
import com.google.common.base.*;

@Beta
@GwtCompatible
@Immutable
public final class MediaType
{
    private static final String CHARSET_ATTRIBUTE = "charset";
    private static final ImmutableListMultimap UTF_8_CONSTANT_PARAMETERS;
    private static final CharMatcher TOKEN_MATCHER;
    private static final CharMatcher QUOTED_TEXT_MATCHER;
    private static final CharMatcher LINEAR_WHITE_SPACE;
    private static final String APPLICATION_TYPE = "application";
    private static final String AUDIO_TYPE = "audio";
    private static final String IMAGE_TYPE = "image";
    private static final String TEXT_TYPE = "text";
    private static final String VIDEO_TYPE = "video";
    private static final String WILDCARD = "*";
    private static final Map KNOWN_TYPES;
    public static final MediaType ANY_TYPE;
    public static final MediaType ANY_TEXT_TYPE;
    public static final MediaType ANY_IMAGE_TYPE;
    public static final MediaType ANY_AUDIO_TYPE;
    public static final MediaType ANY_VIDEO_TYPE;
    public static final MediaType ANY_APPLICATION_TYPE;
    public static final MediaType CACHE_MANIFEST_UTF_8;
    public static final MediaType CSS_UTF_8;
    public static final MediaType CSV_UTF_8;
    public static final MediaType HTML_UTF_8;
    public static final MediaType I_CALENDAR_UTF_8;
    public static final MediaType PLAIN_TEXT_UTF_8;
    public static final MediaType TEXT_JAVASCRIPT_UTF_8;
    public static final MediaType TSV_UTF_8;
    public static final MediaType VCARD_UTF_8;
    public static final MediaType WML_UTF_8;
    public static final MediaType XML_UTF_8;
    public static final MediaType BMP;
    public static final MediaType CRW;
    public static final MediaType GIF;
    public static final MediaType ICO;
    public static final MediaType JPEG;
    public static final MediaType PNG;
    public static final MediaType PSD;
    public static final MediaType SVG_UTF_8;
    public static final MediaType TIFF;
    public static final MediaType WEBP;
    public static final MediaType MP4_AUDIO;
    public static final MediaType MPEG_AUDIO;
    public static final MediaType OGG_AUDIO;
    public static final MediaType WEBM_AUDIO;
    public static final MediaType MP4_VIDEO;
    public static final MediaType MPEG_VIDEO;
    public static final MediaType OGG_VIDEO;
    public static final MediaType QUICKTIME;
    public static final MediaType WEBM_VIDEO;
    public static final MediaType WMV;
    public static final MediaType APPLICATION_XML_UTF_8;
    public static final MediaType ATOM_UTF_8;
    public static final MediaType BZIP2;
    public static final MediaType EOT;
    public static final MediaType EPUB;
    public static final MediaType FORM_DATA;
    public static final MediaType KEY_ARCHIVE;
    public static final MediaType APPLICATION_BINARY;
    public static final MediaType GZIP;
    public static final MediaType JAVASCRIPT_UTF_8;
    public static final MediaType JSON_UTF_8;
    public static final MediaType KML;
    public static final MediaType KMZ;
    public static final MediaType MBOX;
    public static final MediaType MICROSOFT_EXCEL;
    public static final MediaType MICROSOFT_POWERPOINT;
    public static final MediaType MICROSOFT_WORD;
    public static final MediaType OCTET_STREAM;
    public static final MediaType OGG_CONTAINER;
    public static final MediaType OOXML_DOCUMENT;
    public static final MediaType OOXML_PRESENTATION;
    public static final MediaType OOXML_SHEET;
    public static final MediaType OPENDOCUMENT_GRAPHICS;
    public static final MediaType OPENDOCUMENT_PRESENTATION;
    public static final MediaType OPENDOCUMENT_SPREADSHEET;
    public static final MediaType OPENDOCUMENT_TEXT;
    public static final MediaType PDF;
    public static final MediaType POSTSCRIPT;
    public static final MediaType PROTOBUF;
    public static final MediaType RDF_XML_UTF_8;
    public static final MediaType RTF_UTF_8;
    public static final MediaType SFNT;
    public static final MediaType SHOCKWAVE_FLASH;
    public static final MediaType SKETCHUP;
    public static final MediaType TAR;
    public static final MediaType WOFF;
    public static final MediaType XHTML_UTF_8;
    public static final MediaType XRD_UTF_8;
    public static final MediaType ZIP;
    private final String type;
    private final String subtype;
    private final ImmutableListMultimap parameters;
    private static final Joiner.MapJoiner PARAMETER_JOINER;
    
    private static MediaType createConstant(final String s, final String s2) {
        return addKnownType(new MediaType(s, s2, ImmutableListMultimap.of()));
    }
    
    private static MediaType createConstantUtf8(final String s, final String s2) {
        return addKnownType(new MediaType(s, s2, MediaType.UTF_8_CONSTANT_PARAMETERS));
    }
    
    private static MediaType addKnownType(final MediaType mediaType) {
        MediaType.KNOWN_TYPES.put(mediaType, mediaType);
        return mediaType;
    }
    
    private MediaType(final String type, final String subtype, final ImmutableListMultimap parameters) {
        this.type = type;
        this.subtype = subtype;
        this.parameters = parameters;
    }
    
    public String type() {
        return this.type;
    }
    
    public String subtype() {
        return this.subtype;
    }
    
    public ImmutableListMultimap parameters() {
        return this.parameters;
    }
    
    private Map parametersAsMap() {
        return Maps.transformValues(this.parameters.asMap(), new Function() {
            final MediaType this$0;
            
            public ImmutableMultiset apply(final Collection collection) {
                return ImmutableMultiset.copyOf(collection);
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Collection)o);
            }
        });
    }
    
    public Optional charset() {
        final ImmutableSet copy = ImmutableSet.copyOf(this.parameters.get((Object)"charset"));
        switch (copy.size()) {
            case 0: {
                return Optional.absent();
            }
            case 1: {
                return Optional.of(Charset.forName((String)Iterables.getOnlyElement(copy)));
            }
            default: {
                throw new IllegalStateException("Multiple charset values defined: " + copy);
            }
        }
    }
    
    public MediaType withoutParameters() {
        return this.parameters.isEmpty() ? this : create(this.type, this.subtype);
    }
    
    public MediaType withParameters(final Multimap multimap) {
        return create(this.type, this.subtype, multimap);
    }
    
    public MediaType withParameter(final String s, final String s2) {
        Preconditions.checkNotNull(s);
        Preconditions.checkNotNull(s2);
        final String normalizeToken = normalizeToken(s);
        final ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        for (final Map.Entry<String, V> entry : this.parameters.entries()) {
            final String s3 = entry.getKey();
            if (!normalizeToken.equals(s3)) {
                builder.put((Object)s3, (Object)entry.getValue());
            }
        }
        builder.put((Object)normalizeToken, (Object)normalizeParameterValue(normalizeToken, s2));
        final MediaType mediaType = new MediaType(this.type, this.subtype, builder.build());
        return (MediaType)Objects.firstNonNull(MediaType.KNOWN_TYPES.get(mediaType), mediaType);
    }
    
    public MediaType withCharset(final Charset charset) {
        Preconditions.checkNotNull(charset);
        return this.withParameter("charset", charset.name());
    }
    
    public boolean hasWildcard() {
        return "*".equals(this.type) || "*".equals(this.subtype);
    }
    
    public boolean is(final MediaType mediaType) {
        return (mediaType.type.equals("*") || mediaType.type.equals(this.type)) && (mediaType.subtype.equals("*") || mediaType.subtype.equals(this.subtype)) && this.parameters.entries().containsAll(mediaType.parameters.entries());
    }
    
    public static MediaType create(final String s, final String s2) {
        return create(s, s2, ImmutableListMultimap.of());
    }
    
    static MediaType createApplicationType(final String s) {
        return create("application", s);
    }
    
    static MediaType createAudioType(final String s) {
        return create("audio", s);
    }
    
    static MediaType createImageType(final String s) {
        return create("image", s);
    }
    
    static MediaType createTextType(final String s) {
        return create("text", s);
    }
    
    static MediaType createVideoType(final String s) {
        return create("video", s);
    }
    
    private static MediaType create(final String s, final String s2, final Multimap multimap) {
        Preconditions.checkNotNull(s);
        Preconditions.checkNotNull(s2);
        Preconditions.checkNotNull(multimap);
        final String normalizeToken = normalizeToken(s);
        final String normalizeToken2 = normalizeToken(s2);
        Preconditions.checkArgument(!"*".equals(normalizeToken) || "*".equals(normalizeToken2), (Object)"A wildcard type cannot be used with a non-wildcard subtype");
        final ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        for (final Map.Entry<String, V> entry : multimap.entries()) {
            final String normalizeToken3 = normalizeToken(entry.getKey());
            builder.put((Object)normalizeToken3, (Object)normalizeParameterValue(normalizeToken3, (String)entry.getValue()));
        }
        final MediaType mediaType = new MediaType(normalizeToken, normalizeToken2, builder.build());
        return (MediaType)Objects.firstNonNull(MediaType.KNOWN_TYPES.get(mediaType), mediaType);
    }
    
    private static String normalizeToken(final String s) {
        Preconditions.checkArgument(MediaType.TOKEN_MATCHER.matchesAllOf(s));
        return Ascii.toLowerCase(s);
    }
    
    private static String normalizeParameterValue(final String s, final String s2) {
        return "charset".equals(s) ? Ascii.toLowerCase(s2) : s2;
    }
    
    public static MediaType parse(final String s) {
        Preconditions.checkNotNull(s);
        final Tokenizer tokenizer = new Tokenizer(s);
        final String consumeToken = tokenizer.consumeToken(MediaType.TOKEN_MATCHER);
        tokenizer.consumeCharacter('/');
        final String consumeToken2 = tokenizer.consumeToken(MediaType.TOKEN_MATCHER);
        final ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        while (tokenizer.hasMore()) {
            tokenizer.consumeCharacter(';');
            tokenizer.consumeTokenIfPresent(MediaType.LINEAR_WHITE_SPACE);
            final String consumeToken3 = tokenizer.consumeToken(MediaType.TOKEN_MATCHER);
            tokenizer.consumeCharacter('=');
            String s2;
            if ('\"' == tokenizer.previewChar()) {
                tokenizer.consumeCharacter('\"');
                final StringBuilder sb = new StringBuilder();
                while ('\"' != tokenizer.previewChar()) {
                    if ('\\' == tokenizer.previewChar()) {
                        tokenizer.consumeCharacter('\\');
                        sb.append(tokenizer.consumeCharacter(CharMatcher.ASCII));
                    }
                    else {
                        sb.append(tokenizer.consumeToken(MediaType.QUOTED_TEXT_MATCHER));
                    }
                }
                s2 = sb.toString();
                tokenizer.consumeCharacter('\"');
            }
            else {
                s2 = tokenizer.consumeToken(MediaType.TOKEN_MATCHER);
            }
            builder.put((Object)consumeToken3, (Object)s2);
        }
        return create(consumeToken, consumeToken2, builder.build());
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof MediaType) {
            final MediaType mediaType = (MediaType)o;
            return this.type.equals(mediaType.type) && this.subtype.equals(mediaType.subtype) && this.parametersAsMap().equals(mediaType.parametersAsMap());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.type, this.subtype, this.parametersAsMap());
    }
    
    @Override
    public String toString() {
        final StringBuilder append = new StringBuilder().append(this.type).append('/').append(this.subtype);
        if (!this.parameters.isEmpty()) {
            append.append("; ");
            MediaType.PARAMETER_JOINER.appendTo(append, Multimaps.transformValues(this.parameters, new Function() {
                final MediaType this$0;
                
                public String apply(final String s) {
                    return MediaType.access$000().matchesAllOf(s) ? s : MediaType.access$100(s);
                }
                
                @Override
                public Object apply(final Object o) {
                    return this.apply((String)o);
                }
            }).entries());
        }
        return append.toString();
    }
    
    private static String escapeAndQuote(final String s) {
        final StringBuilder append = new StringBuilder(s.length() + 16).append('\"');
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            final char c = charArray[0];
            if (c == '\r' || c == '\\' || c == '\"') {
                append.append('\\');
            }
            append.append(c);
            int n = 0;
            ++n;
        }
        return append.append('\"').toString();
    }
    
    static CharMatcher access$000() {
        return MediaType.TOKEN_MATCHER;
    }
    
    static String access$100(final String s) {
        return escapeAndQuote(s);
    }
    
    static {
        UTF_8_CONSTANT_PARAMETERS = ImmutableListMultimap.of("charset", Ascii.toLowerCase(Charsets.UTF_8.name()));
        TOKEN_MATCHER = CharMatcher.ASCII.and(CharMatcher.JAVA_ISO_CONTROL.negate()).and(CharMatcher.isNot(' ')).and(CharMatcher.noneOf("()<>@,;:\\\"/[]?="));
        QUOTED_TEXT_MATCHER = CharMatcher.ASCII.and(CharMatcher.noneOf("\"\\\r"));
        LINEAR_WHITE_SPACE = CharMatcher.anyOf(" \t\r\n");
        KNOWN_TYPES = Maps.newHashMap();
        ANY_TYPE = createConstant("*", "*");
        ANY_TEXT_TYPE = createConstant("text", "*");
        ANY_IMAGE_TYPE = createConstant("image", "*");
        ANY_AUDIO_TYPE = createConstant("audio", "*");
        ANY_VIDEO_TYPE = createConstant("video", "*");
        ANY_APPLICATION_TYPE = createConstant("application", "*");
        CACHE_MANIFEST_UTF_8 = createConstantUtf8("text", "cache-manifest");
        CSS_UTF_8 = createConstantUtf8("text", "css");
        CSV_UTF_8 = createConstantUtf8("text", "csv");
        HTML_UTF_8 = createConstantUtf8("text", "html");
        I_CALENDAR_UTF_8 = createConstantUtf8("text", "calendar");
        PLAIN_TEXT_UTF_8 = createConstantUtf8("text", "plain");
        TEXT_JAVASCRIPT_UTF_8 = createConstantUtf8("text", "javascript");
        TSV_UTF_8 = createConstantUtf8("text", "tab-separated-values");
        VCARD_UTF_8 = createConstantUtf8("text", "vcard");
        WML_UTF_8 = createConstantUtf8("text", "vnd.wap.wml");
        XML_UTF_8 = createConstantUtf8("text", "xml");
        BMP = createConstant("image", "bmp");
        CRW = createConstant("image", "x-canon-crw");
        GIF = createConstant("image", "gif");
        ICO = createConstant("image", "vnd.microsoft.icon");
        JPEG = createConstant("image", "jpeg");
        PNG = createConstant("image", "png");
        PSD = createConstant("image", "vnd.adobe.photoshop");
        SVG_UTF_8 = createConstantUtf8("image", "svg+xml");
        TIFF = createConstant("image", "tiff");
        WEBP = createConstant("image", "webp");
        MP4_AUDIO = createConstant("audio", "mp4");
        MPEG_AUDIO = createConstant("audio", "mpeg");
        OGG_AUDIO = createConstant("audio", "ogg");
        WEBM_AUDIO = createConstant("audio", "webm");
        MP4_VIDEO = createConstant("video", "mp4");
        MPEG_VIDEO = createConstant("video", "mpeg");
        OGG_VIDEO = createConstant("video", "ogg");
        QUICKTIME = createConstant("video", "quicktime");
        WEBM_VIDEO = createConstant("video", "webm");
        WMV = createConstant("video", "x-ms-wmv");
        APPLICATION_XML_UTF_8 = createConstantUtf8("application", "xml");
        ATOM_UTF_8 = createConstantUtf8("application", "atom+xml");
        BZIP2 = createConstant("application", "x-bzip2");
        EOT = createConstant("application", "vnd.ms-fontobject");
        EPUB = createConstant("application", "epub+zip");
        FORM_DATA = createConstant("application", "x-www-form-urlencoded");
        KEY_ARCHIVE = createConstant("application", "pkcs12");
        APPLICATION_BINARY = createConstant("application", "binary");
        GZIP = createConstant("application", "x-gzip");
        JAVASCRIPT_UTF_8 = createConstantUtf8("application", "javascript");
        JSON_UTF_8 = createConstantUtf8("application", "json");
        KML = createConstant("application", "vnd.google-earth.kml+xml");
        KMZ = createConstant("application", "vnd.google-earth.kmz");
        MBOX = createConstant("application", "mbox");
        MICROSOFT_EXCEL = createConstant("application", "vnd.ms-excel");
        MICROSOFT_POWERPOINT = createConstant("application", "vnd.ms-powerpoint");
        MICROSOFT_WORD = createConstant("application", "msword");
        OCTET_STREAM = createConstant("application", "octet-stream");
        OGG_CONTAINER = createConstant("application", "ogg");
        OOXML_DOCUMENT = createConstant("application", "vnd.openxmlformats-officedocument.wordprocessingml.document");
        OOXML_PRESENTATION = createConstant("application", "vnd.openxmlformats-officedocument.presentationml.presentation");
        OOXML_SHEET = createConstant("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        OPENDOCUMENT_GRAPHICS = createConstant("application", "vnd.oasis.opendocument.graphics");
        OPENDOCUMENT_PRESENTATION = createConstant("application", "vnd.oasis.opendocument.presentation");
        OPENDOCUMENT_SPREADSHEET = createConstant("application", "vnd.oasis.opendocument.spreadsheet");
        OPENDOCUMENT_TEXT = createConstant("application", "vnd.oasis.opendocument.text");
        PDF = createConstant("application", "pdf");
        POSTSCRIPT = createConstant("application", "postscript");
        PROTOBUF = createConstant("application", "protobuf");
        RDF_XML_UTF_8 = createConstantUtf8("application", "rdf+xml");
        RTF_UTF_8 = createConstantUtf8("application", "rtf");
        SFNT = createConstant("application", "font-sfnt");
        SHOCKWAVE_FLASH = createConstant("application", "x-shockwave-flash");
        SKETCHUP = createConstant("application", "vnd.sketchup.skp");
        TAR = createConstant("application", "x-tar");
        WOFF = createConstant("application", "font-woff");
        XHTML_UTF_8 = createConstantUtf8("application", "xhtml+xml");
        XRD_UTF_8 = createConstantUtf8("application", "xrd+xml");
        ZIP = createConstant("application", "zip");
        PARAMETER_JOINER = Joiner.on("; ").withKeyValueSeparator("=");
    }
    
    private static final class Tokenizer
    {
        final String input;
        int position;
        
        Tokenizer(final String input) {
            this.position = 0;
            this.input = input;
        }
        
        String consumeTokenIfPresent(final CharMatcher charMatcher) {
            Preconditions.checkState(this.hasMore());
            final int position = this.position;
            this.position = charMatcher.negate().indexIn(this.input, position);
            return this.hasMore() ? this.input.substring(position, this.position) : this.input.substring(position);
        }
        
        String consumeToken(final CharMatcher charMatcher) {
            final int position = this.position;
            final String consumeTokenIfPresent = this.consumeTokenIfPresent(charMatcher);
            Preconditions.checkState(this.position != position);
            return consumeTokenIfPresent;
        }
        
        char consumeCharacter(final CharMatcher charMatcher) {
            Preconditions.checkState(this.hasMore());
            final char previewChar = this.previewChar();
            Preconditions.checkState(charMatcher.matches(previewChar));
            ++this.position;
            return previewChar;
        }
        
        char consumeCharacter(final char c) {
            Preconditions.checkState(this.hasMore());
            Preconditions.checkState(this.previewChar() == c);
            ++this.position;
            return c;
        }
        
        char previewChar() {
            Preconditions.checkState(this.hasMore());
            return this.input.charAt(this.position);
        }
        
        boolean hasMore() {
            return this.position >= 0 && this.position < this.input.length();
        }
    }
}
