package org.yaml.snakeyaml;

import java.util.*;
import org.yaml.snakeyaml.serializer.*;
import org.yaml.snakeyaml.error.*;

public class DumperOptions
{
    private ScalarStyle defaultStyle;
    private FlowStyle defaultFlowStyle;
    private boolean canonical;
    private boolean allowUnicode;
    private boolean allowReadOnlyProperties;
    private int indent;
    private int indicatorIndent;
    private boolean indentWithIndicator;
    private int bestWidth;
    private boolean splitLines;
    private LineBreak lineBreak;
    private boolean explicitStart;
    private boolean explicitEnd;
    private TimeZone timeZone;
    private int maxSimpleKeyLength;
    private NonPrintableStyle nonPrintableStyle;
    private Version version;
    private Map tags;
    private Boolean prettyFlow;
    private AnchorGenerator anchorGenerator;
    
    public DumperOptions() {
        this.defaultStyle = ScalarStyle.PLAIN;
        this.defaultFlowStyle = FlowStyle.AUTO;
        this.canonical = false;
        this.allowUnicode = true;
        this.allowReadOnlyProperties = false;
        this.indent = 2;
        this.indicatorIndent = 0;
        this.indentWithIndicator = false;
        this.bestWidth = 80;
        this.splitLines = true;
        this.lineBreak = LineBreak.UNIX;
        this.explicitStart = false;
        this.explicitEnd = false;
        this.timeZone = null;
        this.maxSimpleKeyLength = 128;
        this.nonPrintableStyle = NonPrintableStyle.BINARY;
        this.version = null;
        this.tags = null;
        this.prettyFlow = false;
        this.anchorGenerator = new NumberAnchorGenerator(0);
    }
    
    public boolean isAllowUnicode() {
        return this.allowUnicode;
    }
    
    public void setAllowUnicode(final boolean allowUnicode) {
        this.allowUnicode = allowUnicode;
    }
    
    public ScalarStyle getDefaultScalarStyle() {
        return this.defaultStyle;
    }
    
    public void setDefaultScalarStyle(final ScalarStyle defaultStyle) {
        if (defaultStyle == null) {
            throw new NullPointerException("Use ScalarStyle enum.");
        }
        this.defaultStyle = defaultStyle;
    }
    
    public void setIndent(final int indent) {
        if (indent < 1) {
            throw new YAMLException("Indent must be at least 1");
        }
        if (indent > 10) {
            throw new YAMLException("Indent must be at most 10");
        }
        this.indent = indent;
    }
    
    public int getIndent() {
        return this.indent;
    }
    
    public void setIndicatorIndent(final int indicatorIndent) {
        if (indicatorIndent < 0) {
            throw new YAMLException("Indicator indent must be non-negative.");
        }
        if (indicatorIndent > 9) {
            throw new YAMLException("Indicator indent must be at most Emitter.MAX_INDENT-1: 9");
        }
        this.indicatorIndent = indicatorIndent;
    }
    
    public int getIndicatorIndent() {
        return this.indicatorIndent;
    }
    
    public boolean getIndentWithIndicator() {
        return this.indentWithIndicator;
    }
    
    public void setIndentWithIndicator(final boolean indentWithIndicator) {
        this.indentWithIndicator = indentWithIndicator;
    }
    
    public void setVersion(final Version version) {
        this.version = version;
    }
    
    public Version getVersion() {
        return this.version;
    }
    
    public void setCanonical(final boolean canonical) {
        this.canonical = canonical;
    }
    
    public boolean isCanonical() {
        return this.canonical;
    }
    
    public void setPrettyFlow(final boolean b) {
        this.prettyFlow = b;
    }
    
    public boolean isPrettyFlow() {
        return this.prettyFlow;
    }
    
    public void setWidth(final int bestWidth) {
        this.bestWidth = bestWidth;
    }
    
    public int getWidth() {
        return this.bestWidth;
    }
    
    public void setSplitLines(final boolean splitLines) {
        this.splitLines = splitLines;
    }
    
    public boolean getSplitLines() {
        return this.splitLines;
    }
    
    public LineBreak getLineBreak() {
        return this.lineBreak;
    }
    
    public void setDefaultFlowStyle(final FlowStyle defaultFlowStyle) {
        if (defaultFlowStyle == null) {
            throw new NullPointerException("Use FlowStyle enum.");
        }
        this.defaultFlowStyle = defaultFlowStyle;
    }
    
    public FlowStyle getDefaultFlowStyle() {
        return this.defaultFlowStyle;
    }
    
    public void setLineBreak(final LineBreak lineBreak) {
        if (lineBreak == null) {
            throw new NullPointerException("Specify line break.");
        }
        this.lineBreak = lineBreak;
    }
    
    public boolean isExplicitStart() {
        return this.explicitStart;
    }
    
    public void setExplicitStart(final boolean explicitStart) {
        this.explicitStart = explicitStart;
    }
    
    public boolean isExplicitEnd() {
        return this.explicitEnd;
    }
    
    public void setExplicitEnd(final boolean explicitEnd) {
        this.explicitEnd = explicitEnd;
    }
    
    public Map getTags() {
        return this.tags;
    }
    
    public void setTags(final Map tags) {
        this.tags = tags;
    }
    
    public boolean isAllowReadOnlyProperties() {
        return this.allowReadOnlyProperties;
    }
    
    public void setAllowReadOnlyProperties(final boolean allowReadOnlyProperties) {
        this.allowReadOnlyProperties = allowReadOnlyProperties;
    }
    
    public TimeZone getTimeZone() {
        return this.timeZone;
    }
    
    public void setTimeZone(final TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    
    public AnchorGenerator getAnchorGenerator() {
        return this.anchorGenerator;
    }
    
    public void setAnchorGenerator(final AnchorGenerator anchorGenerator) {
        this.anchorGenerator = anchorGenerator;
    }
    
    public int getMaxSimpleKeyLength() {
        return this.maxSimpleKeyLength;
    }
    
    public void setMaxSimpleKeyLength(final int maxSimpleKeyLength) {
        if (maxSimpleKeyLength > 1024) {
            throw new YAMLException("The simple key must not span more than 1024 stream characters. See https://yaml.org/spec/1.1/#id934537");
        }
        this.maxSimpleKeyLength = maxSimpleKeyLength;
    }
    
    public NonPrintableStyle getNonPrintableStyle() {
        return this.nonPrintableStyle;
    }
    
    public void setNonPrintableStyle(final NonPrintableStyle nonPrintableStyle) {
        this.nonPrintableStyle = nonPrintableStyle;
    }
    
    public enum NonPrintableStyle
    {
        BINARY("BINARY", 0), 
        ESCAPE("ESCAPE", 1);
        
        private static final NonPrintableStyle[] $VALUES;
        
        private NonPrintableStyle(final String s, final int n) {
        }
        
        static {
            $VALUES = new NonPrintableStyle[] { NonPrintableStyle.BINARY, NonPrintableStyle.ESCAPE };
        }
    }
    
    public enum Version
    {
        V1_0("V1_0", 0, new Integer[] { 1, 0 }), 
        V1_1("V1_1", 1, new Integer[] { 1, 1 });
        
        private Integer[] version;
        private static final Version[] $VALUES;
        
        private Version(final String s, final int n, final Integer[] version) {
            this.version = version;
        }
        
        public int major() {
            return this.version[0];
        }
        
        public int minor() {
            return this.version[1];
        }
        
        public String getRepresentation() {
            return this.version[0] + "." + this.version[1];
        }
        
        @Override
        public String toString() {
            return "Version: " + this.getRepresentation();
        }
        
        static {
            $VALUES = new Version[] { Version.V1_0, Version.V1_1 };
        }
    }
    
    public enum LineBreak
    {
        WIN("WIN", 0, "\r\n"), 
        MAC("MAC", 1, "\r"), 
        UNIX("UNIX", 2, "\n");
        
        private String lineBreak;
        private static final LineBreak[] $VALUES;
        
        private LineBreak(final String s, final int n, final String lineBreak) {
            this.lineBreak = lineBreak;
        }
        
        public String getString() {
            return this.lineBreak;
        }
        
        @Override
        public String toString() {
            return "Line break: " + this.name();
        }
        
        public static LineBreak getPlatformLineBreak() {
            final String property = System.getProperty("line.separator");
            final LineBreak[] values = values();
            while (0 < values.length) {
                final LineBreak lineBreak = values[0];
                if (lineBreak.lineBreak.equals(property)) {
                    return lineBreak;
                }
                int n = 0;
                ++n;
            }
            return LineBreak.UNIX;
        }
        
        static {
            $VALUES = new LineBreak[] { LineBreak.WIN, LineBreak.MAC, LineBreak.UNIX };
        }
    }
    
    public enum FlowStyle
    {
        FLOW("FLOW", 0, Boolean.TRUE), 
        BLOCK("BLOCK", 1, Boolean.FALSE), 
        AUTO("AUTO", 2, (Boolean)null);
        
        private Boolean styleBoolean;
        private static final FlowStyle[] $VALUES;
        
        private FlowStyle(final String s, final int n, final Boolean styleBoolean) {
            this.styleBoolean = styleBoolean;
        }
        
        @Deprecated
        public static FlowStyle fromBoolean(final Boolean b) {
            return (b == null) ? FlowStyle.AUTO : (b ? FlowStyle.FLOW : FlowStyle.BLOCK);
        }
        
        public Boolean getStyleBoolean() {
            return this.styleBoolean;
        }
        
        @Override
        public String toString() {
            return "Flow style: '" + this.styleBoolean + "'";
        }
        
        static {
            $VALUES = new FlowStyle[] { FlowStyle.FLOW, FlowStyle.BLOCK, FlowStyle.AUTO };
        }
    }
    
    public enum ScalarStyle
    {
        DOUBLE_QUOTED("DOUBLE_QUOTED", 0, Character.valueOf('\"')), 
        SINGLE_QUOTED("SINGLE_QUOTED", 1, Character.valueOf('\'')), 
        LITERAL("LITERAL", 2, Character.valueOf('|')), 
        FOLDED("FOLDED", 3, Character.valueOf('>')), 
        PLAIN("PLAIN", 4, (Character)null);
        
        private Character styleChar;
        private static final ScalarStyle[] $VALUES;
        
        private ScalarStyle(final String s, final int n, final Character styleChar) {
            this.styleChar = styleChar;
        }
        
        public Character getChar() {
            return this.styleChar;
        }
        
        @Override
        public String toString() {
            return "Scalar style: '" + this.styleChar + "'";
        }
        
        public static ScalarStyle createStyle(final Character c) {
            if (c == null) {
                return ScalarStyle.PLAIN;
            }
            switch ((char)c) {
                case '\"': {
                    return ScalarStyle.DOUBLE_QUOTED;
                }
                case '\'': {
                    return ScalarStyle.SINGLE_QUOTED;
                }
                case '|': {
                    return ScalarStyle.LITERAL;
                }
                case '>': {
                    return ScalarStyle.FOLDED;
                }
                default: {
                    throw new YAMLException("Unknown scalar style character: " + c);
                }
            }
        }
        
        static {
            $VALUES = new ScalarStyle[] { ScalarStyle.DOUBLE_QUOTED, ScalarStyle.SINGLE_QUOTED, ScalarStyle.LITERAL, ScalarStyle.FOLDED, ScalarStyle.PLAIN };
        }
    }
}
