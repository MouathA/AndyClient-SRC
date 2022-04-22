package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.*;

public abstract class Token
{
    private final Mark startMark;
    private final Mark endMark;
    
    public Token(final Mark startMark, final Mark endMark) {
        if (startMark == null || endMark == null) {
            throw new YAMLException("Token requires marks.");
        }
        this.startMark = startMark;
        this.endMark = endMark;
    }
    
    public Mark getStartMark() {
        return this.startMark;
    }
    
    public Mark getEndMark() {
        return this.endMark;
    }
    
    public abstract ID getTokenId();
    
    public enum ID
    {
        Alias("Alias", 0, "<alias>"), 
        Anchor("Anchor", 1, "<anchor>"), 
        BlockEnd("BlockEnd", 2, "<block end>"), 
        BlockEntry("BlockEntry", 3, "-"), 
        BlockMappingStart("BlockMappingStart", 4, "<block mapping start>"), 
        BlockSequenceStart("BlockSequenceStart", 5, "<block sequence start>"), 
        Directive("Directive", 6, "<directive>"), 
        DocumentEnd("DocumentEnd", 7, "<document end>"), 
        DocumentStart("DocumentStart", 8, "<document start>"), 
        FlowEntry("FlowEntry", 9, ","), 
        FlowMappingEnd("FlowMappingEnd", 10, "}"), 
        FlowMappingStart("FlowMappingStart", 11, "{"), 
        FlowSequenceEnd("FlowSequenceEnd", 12, "]"), 
        FlowSequenceStart("FlowSequenceStart", 13, "["), 
        Key("Key", 14, "?"), 
        Scalar("Scalar", 15, "<scalar>"), 
        StreamEnd("StreamEnd", 16, "<stream end>"), 
        StreamStart("StreamStart", 17, "<stream start>"), 
        Tag("Tag", 18, "<tag>"), 
        Value("Value", 19, ":"), 
        Whitespace("Whitespace", 20, "<whitespace>"), 
        Comment("Comment", 21, "#"), 
        Error("Error", 22, "<error>");
        
        private final String description;
        private static final ID[] $VALUES;
        
        private ID(final String s, final int n, final String description) {
            this.description = description;
        }
        
        @Override
        public String toString() {
            return this.description;
        }
        
        static {
            $VALUES = new ID[] { ID.Alias, ID.Anchor, ID.BlockEnd, ID.BlockEntry, ID.BlockMappingStart, ID.BlockSequenceStart, ID.Directive, ID.DocumentEnd, ID.DocumentStart, ID.FlowEntry, ID.FlowMappingEnd, ID.FlowMappingStart, ID.FlowSequenceEnd, ID.FlowSequenceStart, ID.Key, ID.Scalar, ID.StreamEnd, ID.StreamStart, ID.Tag, ID.Value, ID.Whitespace, ID.Comment, ID.Error };
        }
    }
}
