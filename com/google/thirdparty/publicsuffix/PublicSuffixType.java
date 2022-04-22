package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.*;

@GwtCompatible
enum PublicSuffixType
{
    PRIVATE("PRIVATE", 0, ':', ','), 
    ICANN("ICANN", 1, '!', '?');
    
    private final char innerNodeCode;
    private final char leafNodeCode;
    private static final PublicSuffixType[] $VALUES;
    
    private PublicSuffixType(final String s, final int n, final char innerNodeCode, final char leafNodeCode) {
        this.innerNodeCode = innerNodeCode;
        this.leafNodeCode = leafNodeCode;
    }
    
    char getLeafNodeCode() {
        return this.leafNodeCode;
    }
    
    char getInnerNodeCode() {
        return this.innerNodeCode;
    }
    
    static PublicSuffixType fromCode(final char c) {
        final PublicSuffixType[] values = values();
        while (0 < values.length) {
            final PublicSuffixType publicSuffixType = values[0];
            if (publicSuffixType.getInnerNodeCode() == c || publicSuffixType.getLeafNodeCode() == c) {
                return publicSuffixType;
            }
            int n = 0;
            ++n;
        }
        throw new IllegalArgumentException("No enum corresponding to given code: " + c);
    }
    
    static PublicSuffixType fromIsPrivate(final boolean b) {
        return b ? PublicSuffixType.PRIVATE : PublicSuffixType.ICANN;
    }
    
    static {
        $VALUES = new PublicSuffixType[] { PublicSuffixType.PRIVATE, PublicSuffixType.ICANN };
    }
}
