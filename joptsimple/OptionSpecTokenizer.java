package joptsimple;

class OptionSpecTokenizer
{
    private static final char POSIXLY_CORRECT_MARKER = '+';
    private static final char HELP_MARKER = '*';
    private String specification;
    private int index;
    
    OptionSpecTokenizer(final String specification) {
        if (specification == null) {
            throw new NullPointerException("null option specification");
        }
        this.specification = specification;
    }
    
    AbstractOptionSpec next() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: new             Ljava/util/NoSuchElementException;
        //     7: dup            
        //     8: invokespecial   java/util/NoSuchElementException.<init>:()V
        //    11: athrow         
        //    12: aload_0        
        //    13: getfield        joptsimple/OptionSpecTokenizer.specification:Ljava/lang/String;
        //    16: aload_0        
        //    17: getfield        joptsimple/OptionSpecTokenizer.index:I
        //    20: invokevirtual   java/lang/String.charAt:(I)C
        //    23: invokestatic    java/lang/String.valueOf:(C)Ljava/lang/String;
        //    26: astore_1       
        //    27: aload_0        
        //    28: dup            
        //    29: getfield        joptsimple/OptionSpecTokenizer.index:I
        //    32: iconst_1       
        //    33: iadd           
        //    34: putfield        joptsimple/OptionSpecTokenizer.index:I
        //    37: ldc             "W"
        //    39: aload_1        
        //    40: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    43: ifeq            57
        //    46: aload_0        
        //    47: invokespecial   joptsimple/OptionSpecTokenizer.handleReservedForExtensionsToken:()Ljoptsimple/AbstractOptionSpec;
        //    50: astore_2       
        //    51: aload_2        
        //    52: ifnull          57
        //    55: aload_2        
        //    56: areturn        
        //    57: aload_1        
        //    58: invokestatic    joptsimple/ParserRules.ensureLegalOption:(Ljava/lang/String;)V
        //    61: aload_0        
        //    62: if_icmpge       136
        //    65: aload_0        
        //    66: getfield        joptsimple/OptionSpecTokenizer.specification:Ljava/lang/String;
        //    69: aload_0        
        //    70: getfield        joptsimple/OptionSpecTokenizer.index:I
        //    73: invokevirtual   java/lang/String.charAt:(I)C
        //    76: bipush          42
        //    78: if_icmpne       91
        //    81: aload_0        
        //    82: dup            
        //    83: getfield        joptsimple/OptionSpecTokenizer.index:I
        //    86: iconst_1       
        //    87: iadd           
        //    88: putfield        joptsimple/OptionSpecTokenizer.index:I
        //    91: aload_0        
        //    92: if_icmpge       119
        //    95: aload_0        
        //    96: getfield        joptsimple/OptionSpecTokenizer.specification:Ljava/lang/String;
        //    99: aload_0        
        //   100: getfield        joptsimple/OptionSpecTokenizer.index:I
        //   103: invokevirtual   java/lang/String.charAt:(I)C
        //   106: bipush          58
        //   108: if_icmpne       119
        //   111: aload_0        
        //   112: aload_1        
        //   113: invokespecial   joptsimple/OptionSpecTokenizer.handleArgumentAcceptingOption:(Ljava/lang/String;)Ljoptsimple/AbstractOptionSpec;
        //   116: goto            127
        //   119: new             Ljoptsimple/NoArgumentOptionSpec;
        //   122: dup            
        //   123: aload_1        
        //   124: invokespecial   joptsimple/NoArgumentOptionSpec.<init>:(Ljava/lang/String;)V
        //   127: astore_2       
        //   128: aload_2        
        //   129: invokevirtual   joptsimple/AbstractOptionSpec.forHelp:()Ljoptsimple/AbstractOptionSpec;
        //   132: pop            
        //   133: goto            145
        //   136: new             Ljoptsimple/NoArgumentOptionSpec;
        //   139: dup            
        //   140: aload_1        
        //   141: invokespecial   joptsimple/NoArgumentOptionSpec.<init>:(Ljava/lang/String;)V
        //   144: astore_2       
        //   145: aload_2        
        //   146: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    void configure(final OptionParser p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: invokespecial   joptsimple/OptionSpecTokenizer.adjustForPosixlyCorrect:(Ljoptsimple/OptionParser;)V
        //     5: aload_0        
        //     6: if_icmpge       20
        //     9: aload_1        
        //    10: aload_0        
        //    11: invokevirtual   joptsimple/OptionSpecTokenizer.next:()Ljoptsimple/AbstractOptionSpec;
        //    14: invokevirtual   joptsimple/OptionParser.recognize:(Ljoptsimple/AbstractOptionSpec;)V
        //    17: goto            5
        //    20: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void adjustForPosixlyCorrect(final OptionParser optionParser) {
        if ('+' == this.specification.charAt(0)) {
            optionParser.posixlyCorrect(true);
            this.specification = this.specification.substring(1);
        }
    }
    
    private AbstractOptionSpec handleReservedForExtensionsToken() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpge       14
        //     4: new             Ljoptsimple/NoArgumentOptionSpec;
        //     7: dup            
        //     8: ldc             "W"
        //    10: invokespecial   joptsimple/NoArgumentOptionSpec.<init>:(Ljava/lang/String;)V
        //    13: areturn        
        //    14: aload_0        
        //    15: getfield        joptsimple/OptionSpecTokenizer.specification:Ljava/lang/String;
        //    18: aload_0        
        //    19: getfield        joptsimple/OptionSpecTokenizer.index:I
        //    22: invokevirtual   java/lang/String.charAt:(I)C
        //    25: bipush          59
        //    27: if_icmpne       48
        //    30: aload_0        
        //    31: dup            
        //    32: getfield        joptsimple/OptionSpecTokenizer.index:I
        //    35: iconst_1       
        //    36: iadd           
        //    37: putfield        joptsimple/OptionSpecTokenizer.index:I
        //    40: new             Ljoptsimple/AlternativeLongOptionSpec;
        //    43: dup            
        //    44: invokespecial   joptsimple/AlternativeLongOptionSpec.<init>:()V
        //    47: areturn        
        //    48: aconst_null    
        //    49: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private AbstractOptionSpec handleArgumentAcceptingOption(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: dup            
        //     2: getfield        joptsimple/OptionSpecTokenizer.index:I
        //     5: iconst_1       
        //     6: iadd           
        //     7: putfield        joptsimple/OptionSpecTokenizer.index:I
        //    10: aload_0        
        //    11: if_icmpge       49
        //    14: aload_0        
        //    15: getfield        joptsimple/OptionSpecTokenizer.specification:Ljava/lang/String;
        //    18: aload_0        
        //    19: getfield        joptsimple/OptionSpecTokenizer.index:I
        //    22: invokevirtual   java/lang/String.charAt:(I)C
        //    25: bipush          58
        //    27: if_icmpne       49
        //    30: aload_0        
        //    31: dup            
        //    32: getfield        joptsimple/OptionSpecTokenizer.index:I
        //    35: iconst_1       
        //    36: iadd           
        //    37: putfield        joptsimple/OptionSpecTokenizer.index:I
        //    40: new             Ljoptsimple/OptionalArgumentOptionSpec;
        //    43: dup            
        //    44: aload_1        
        //    45: invokespecial   joptsimple/OptionalArgumentOptionSpec.<init>:(Ljava/lang/String;)V
        //    48: areturn        
        //    49: new             Ljoptsimple/RequiredArgumentOptionSpec;
        //    52: dup            
        //    53: aload_1        
        //    54: invokespecial   joptsimple/RequiredArgumentOptionSpec.<init>:(Ljava/lang/String;)V
        //    57: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
