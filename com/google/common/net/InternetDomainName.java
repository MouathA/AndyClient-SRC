package com.google.common.net;

import com.google.common.annotations.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;
import javax.annotation.*;

@Beta
@GwtCompatible
public final class InternetDomainName
{
    private static final CharMatcher DOTS_MATCHER;
    private static final Splitter DOT_SPLITTER;
    private static final Joiner DOT_JOINER;
    private static final int NO_PUBLIC_SUFFIX_FOUND = -1;
    private static final String DOT_REGEX = "\\.";
    private static final int MAX_PARTS = 127;
    private static final int MAX_LENGTH = 253;
    private static final int MAX_DOMAIN_PART_LENGTH = 63;
    private final String name;
    private final ImmutableList parts;
    private final int publicSuffixIndex;
    private static final CharMatcher DASH_MATCHER;
    private static final CharMatcher PART_CHAR_MATCHER;
    
    InternetDomainName(String name) {
        name = Ascii.toLowerCase(InternetDomainName.DOTS_MATCHER.replaceFrom(name, '.'));
        if (name.endsWith(".")) {
            name = name.substring(0, name.length() - 1);
        }
        Preconditions.checkArgument(name.length() <= 253, "Domain name too long: '%s':", name);
        this.name = name;
        this.parts = ImmutableList.copyOf(InternetDomainName.DOT_SPLITTER.split(name));
        Preconditions.checkArgument(this.parts.size() <= 127, "Domain has too many parts: '%s'", name);
        Preconditions.checkArgument(validateSyntax(this.parts), "Not a valid domain name: '%s'", name);
        this.publicSuffixIndex = this.findPublicSuffix();
    }
    
    private int findPublicSuffix() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/google/common/net/InternetDomainName.parts:Lcom/google/common/collect/ImmutableList;
        //     4: invokevirtual   com/google/common/collect/ImmutableList.size:()I
        //     7: istore_1       
        //     8: iconst_0       
        //     9: iload_1        
        //    10: if_icmpge       65
        //    13: getstatic       com/google/common/net/InternetDomainName.DOT_JOINER:Lcom/google/common/base/Joiner;
        //    16: aload_0        
        //    17: getfield        com/google/common/net/InternetDomainName.parts:Lcom/google/common/collect/ImmutableList;
        //    20: iconst_0       
        //    21: iload_1        
        //    22: invokevirtual   com/google/common/collect/ImmutableList.subList:(II)Lcom/google/common/collect/ImmutableList;
        //    25: invokevirtual   com/google/common/base/Joiner.join:(Ljava/lang/Iterable;)Ljava/lang/String;
        //    28: astore_3       
        //    29: getstatic       com/google/thirdparty/publicsuffix/PublicSuffixPatterns.EXACT:Lcom/google/common/collect/ImmutableMap;
        //    32: aload_3        
        //    33: invokevirtual   com/google/common/collect/ImmutableMap.containsKey:(Ljava/lang/Object;)Z
        //    36: ifeq            41
        //    39: iconst_0       
        //    40: ireturn        
        //    41: getstatic       com/google/thirdparty/publicsuffix/PublicSuffixPatterns.EXCLUDED:Lcom/google/common/collect/ImmutableMap;
        //    44: aload_3        
        //    45: invokevirtual   com/google/common/collect/ImmutableMap.containsKey:(Ljava/lang/Object;)Z
        //    48: ifeq            53
        //    51: iconst_1       
        //    52: ireturn        
        //    53: aload_3        
        //    54: if_icmpne       59
        //    57: iconst_0       
        //    58: ireturn        
        //    59: iinc            2, 1
        //    62: goto            8
        //    65: iconst_m1      
        //    66: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static InternetDomainName from(final String s) {
        return new InternetDomainName((String)Preconditions.checkNotNull(s));
    }
    
    private static boolean validateSyntax(final List list) {
        final int n = list.size() - 1;
        if ((String)list.get(n) >= 1) {
            return false;
        }
        while (0 < n) {
            if ((String)list.get(0) >= 0) {
                return false;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    public ImmutableList parts() {
        return this.parts;
    }
    
    public boolean isPublicSuffix() {
        return this.publicSuffixIndex == 0;
    }
    
    public InternetDomainName publicSuffix() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpeq       15
        //     4: aload_0        
        //     5: aload_0        
        //     6: getfield        com/google/common/net/InternetDomainName.publicSuffixIndex:I
        //     9: invokespecial   com/google/common/net/InternetDomainName.ancestor:(I)Lcom/google/common/net/InternetDomainName;
        //    12: goto            16
        //    15: aconst_null    
        //    16: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean isUnderPublicSuffix() {
        return this.publicSuffixIndex > 0;
    }
    
    public InternetDomainName topPrivateDomain() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpne       6
        //     4: aload_0        
        //     5: areturn        
        //     6: aload_0        
        //     7: invokevirtual   com/google/common/net/InternetDomainName.isUnderPublicSuffix:()Z
        //    10: ldc             "Not under a public suffix: %s"
        //    12: iconst_1       
        //    13: anewarray       Ljava/lang/Object;
        //    16: dup            
        //    17: iconst_0       
        //    18: aload_0        
        //    19: getfield        com/google/common/net/InternetDomainName.name:Ljava/lang/String;
        //    22: aastore        
        //    23: invokestatic    com/google/common/base/Preconditions.checkState:(ZLjava/lang/String;[Ljava/lang/Object;)V
        //    26: aload_0        
        //    27: aload_0        
        //    28: getfield        com/google/common/net/InternetDomainName.publicSuffixIndex:I
        //    31: iconst_1       
        //    32: isub           
        //    33: invokespecial   com/google/common/net/InternetDomainName.ancestor:(I)Lcom/google/common/net/InternetDomainName;
        //    36: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean hasParent() {
        return this.parts.size() > 1;
    }
    
    public InternetDomainName parent() {
        Preconditions.checkState(this.hasParent(), "Domain '%s' has no parent", this.name);
        return this.ancestor(1);
    }
    
    private InternetDomainName ancestor(final int n) {
        return from(InternetDomainName.DOT_JOINER.join(this.parts.subList(n, this.parts.size())));
    }
    
    public InternetDomainName child(final String s) {
        return from((String)Preconditions.checkNotNull(s) + "." + this.name);
    }
    
    public static boolean isValid(final String s) {
        from(s);
        return true;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || (o instanceof InternetDomainName && this.name.equals(((InternetDomainName)o).name));
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    static {
        DOTS_MATCHER = CharMatcher.anyOf(".\u3002\uff0e\uff61");
        DOT_SPLITTER = Splitter.on('.');
        DOT_JOINER = Joiner.on('.');
        DASH_MATCHER = CharMatcher.anyOf("-_");
        PART_CHAR_MATCHER = CharMatcher.JAVA_LETTER_OR_DIGIT.or(InternetDomainName.DASH_MATCHER);
    }
}
