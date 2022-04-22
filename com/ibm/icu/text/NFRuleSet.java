package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.text.*;

final class NFRuleSet
{
    private String name;
    private NFRule[] rules;
    private NFRule negativeNumberRule;
    private NFRule[] fractionRules;
    private boolean isFractionRuleSet;
    private boolean isParseable;
    private int recursionCount;
    private static final int RECURSION_LIMIT = 50;
    static final boolean $assertionsDisabled;
    
    public NFRuleSet(final String[] array, final int n) throws IllegalArgumentException {
        this.negativeNumberRule = null;
        this.fractionRules = new NFRule[3];
        this.isFractionRuleSet = false;
        this.isParseable = true;
        this.recursionCount = 0;
        String substring = array[n];
        if (substring.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
        if (substring.charAt(0) == '%') {
            int index = substring.indexOf(58);
            if (index == -1) {
                throw new IllegalArgumentException("Rule set name doesn't end in colon");
            }
            this.name = substring.substring(0, index);
            while (index < substring.length() && PatternProps.isWhiteSpace(substring.charAt(++index))) {}
            substring = substring.substring(index);
            array[n] = substring;
        }
        else {
            this.name = "%default";
        }
        if (substring.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
        if (this.name.endsWith("@noparse")) {
            this.name = this.name.substring(0, this.name.length() - 8);
            this.isParseable = false;
        }
    }
    
    public void parseRules(final String p0, final RuleBasedNumberFormat p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/ArrayList.<init>:()V
        //     7: astore_3       
        //     8: aload_1        
        //     9: bipush          59
        //    11: invokevirtual   java/lang/String.indexOf:(I)I
        //    14: istore          5
        //    16: iconst_0       
        //    17: iconst_m1      
        //    18: if_icmpeq       89
        //    21: iload           5
        //    23: iconst_m1      
        //    24: if_icmpeq       50
        //    27: aload_3        
        //    28: aload_1        
        //    29: iconst_0       
        //    30: iload           5
        //    32: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    35: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    40: pop            
        //    41: iload           5
        //    43: iconst_1       
        //    44: iadd           
        //    45: istore          4
        //    47: goto            74
        //    50: iconst_0       
        //    51: aload_1        
        //    52: invokevirtual   java/lang/String.length:()I
        //    55: if_icmpge       70
        //    58: aload_3        
        //    59: aload_1        
        //    60: iconst_0       
        //    61: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
        //    64: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    69: pop            
        //    70: iload           5
        //    72: istore          4
        //    74: aload_1        
        //    75: bipush          59
        //    77: iload           5
        //    79: iconst_1       
        //    80: iadd           
        //    81: invokevirtual   java/lang/String.indexOf:(II)I
        //    84: istore          5
        //    86: goto            16
        //    89: new             Ljava/util/ArrayList;
        //    92: dup            
        //    93: invokespecial   java/util/ArrayList.<init>:()V
        //    96: astore          6
        //    98: aconst_null    
        //    99: astore          7
        //   101: iconst_0       
        //   102: aload_3        
        //   103: invokeinterface java/util/List.size:()I
        //   108: if_icmpge       216
        //   111: aload_3        
        //   112: iconst_0       
        //   113: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   118: checkcast       Ljava/lang/String;
        //   121: aload_0        
        //   122: aload           7
        //   124: aload_2        
        //   125: invokestatic    com/ibm/icu/text/NFRule.makeRules:(Ljava/lang/String;Lcom/ibm/icu/text/NFRuleSet;Lcom/ibm/icu/text/NFRule;Lcom/ibm/icu/text/RuleBasedNumberFormat;)Ljava/lang/Object;
        //   128: astore          9
        //   130: aload           9
        //   132: instanceof      Lcom/ibm/icu/text/NFRule;
        //   135: ifeq            161
        //   138: aload           6
        //   140: aload           9
        //   142: checkcast       Lcom/ibm/icu/text/NFRule;
        //   145: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   150: pop            
        //   151: aload           9
        //   153: checkcast       Lcom/ibm/icu/text/NFRule;
        //   156: astore          7
        //   158: goto            210
        //   161: aload           9
        //   163: instanceof      [Lcom/ibm/icu/text/NFRule;
        //   166: ifeq            210
        //   169: aload           9
        //   171: checkcast       [Lcom/ibm/icu/text/NFRule;
        //   174: checkcast       [Lcom/ibm/icu/text/NFRule;
        //   177: astore          10
        //   179: iconst_0       
        //   180: aload           10
        //   182: arraylength    
        //   183: if_icmpge       210
        //   186: aload           6
        //   188: aload           10
        //   190: iconst_0       
        //   191: aaload         
        //   192: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   197: pop            
        //   198: aload           10
        //   200: iconst_0       
        //   201: aaload         
        //   202: astore          7
        //   204: iinc            11, 1
        //   207: goto            179
        //   210: iinc            8, 1
        //   213: goto            101
        //   216: aconst_null    
        //   217: astore_3       
        //   218: lconst_0       
        //   219: lstore          8
        //   221: iconst_0       
        //   222: aload           6
        //   224: invokeinterface java/util/List.size:()I
        //   229: if_icmpge       466
        //   232: aload           6
        //   234: iconst_0       
        //   235: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   240: checkcast       Lcom/ibm/icu/text/NFRule;
        //   243: astore          11
        //   245: aload           11
        //   247: invokevirtual   com/ibm/icu/text/NFRule.getBaseValue:()J
        //   250: l2i            
        //   251: tableswitch {
        //               -8: 368
        //               -7: 348
        //               -6: 328
        //               -5: 310
        //               -4: 284
        //          default: 388
        //        }
        //   284: aload           11
        //   286: lload           8
        //   288: invokevirtual   com/ibm/icu/text/NFRule.setBaseValue:(J)V
        //   291: aload_0        
        //   292: getfield        com/ibm/icu/text/NFRuleSet.isFractionRuleSet:Z
        //   295: ifne            304
        //   298: lload           8
        //   300: lconst_1       
        //   301: ladd           
        //   302: lstore          8
        //   304: iinc            10, 1
        //   307: goto            463
        //   310: aload_0        
        //   311: aload           11
        //   313: putfield        com/ibm/icu/text/NFRuleSet.negativeNumberRule:Lcom/ibm/icu/text/NFRule;
        //   316: aload           6
        //   318: iconst_0       
        //   319: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   324: pop            
        //   325: goto            463
        //   328: aload_0        
        //   329: getfield        com/ibm/icu/text/NFRuleSet.fractionRules:[Lcom/ibm/icu/text/NFRule;
        //   332: iconst_0       
        //   333: aload           11
        //   335: aastore        
        //   336: aload           6
        //   338: iconst_0       
        //   339: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   344: pop            
        //   345: goto            463
        //   348: aload_0        
        //   349: getfield        com/ibm/icu/text/NFRuleSet.fractionRules:[Lcom/ibm/icu/text/NFRule;
        //   352: iconst_1       
        //   353: aload           11
        //   355: aastore        
        //   356: aload           6
        //   358: iconst_0       
        //   359: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   364: pop            
        //   365: goto            463
        //   368: aload_0        
        //   369: getfield        com/ibm/icu/text/NFRuleSet.fractionRules:[Lcom/ibm/icu/text/NFRule;
        //   372: iconst_2       
        //   373: aload           11
        //   375: aastore        
        //   376: aload           6
        //   378: iconst_0       
        //   379: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   384: pop            
        //   385: goto            463
        //   388: aload           11
        //   390: invokevirtual   com/ibm/icu/text/NFRule.getBaseValue:()J
        //   393: lload           8
        //   395: lcmp           
        //   396: ifge            440
        //   399: new             Ljava/lang/IllegalArgumentException;
        //   402: dup            
        //   403: new             Ljava/lang/StringBuilder;
        //   406: dup            
        //   407: invokespecial   java/lang/StringBuilder.<init>:()V
        //   410: ldc             "Rules are not in order, base: "
        //   412: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   415: aload           11
        //   417: invokevirtual   com/ibm/icu/text/NFRule.getBaseValue:()J
        //   420: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //   423: ldc             " < "
        //   425: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   428: lload           8
        //   430: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //   433: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   436: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //   439: athrow         
        //   440: aload           11
        //   442: invokevirtual   com/ibm/icu/text/NFRule.getBaseValue:()J
        //   445: lstore          8
        //   447: aload_0        
        //   448: getfield        com/ibm/icu/text/NFRuleSet.isFractionRuleSet:Z
        //   451: ifne            460
        //   454: lload           8
        //   456: lconst_1       
        //   457: ladd           
        //   458: lstore          8
        //   460: iinc            10, 1
        //   463: goto            221
        //   466: aload_0        
        //   467: aload           6
        //   469: invokeinterface java/util/List.size:()I
        //   474: anewarray       Lcom/ibm/icu/text/NFRule;
        //   477: putfield        com/ibm/icu/text/NFRuleSet.rules:[Lcom/ibm/icu/text/NFRule;
        //   480: aload           6
        //   482: aload_0        
        //   483: getfield        com/ibm/icu/text/NFRuleSet.rules:[Lcom/ibm/icu/text/NFRule;
        //   486: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   491: pop            
        //   492: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void makeIntoFractionRuleSet() {
        this.isFractionRuleSet = true;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NFRuleSet)) {
            return false;
        }
        final NFRuleSet set = (NFRuleSet)o;
        if (!this.name.equals(set.name) || !Utility.objectEquals(this.negativeNumberRule, set.negativeNumberRule) || !Utility.objectEquals(this.fractionRules[0], set.fractionRules[0]) || !Utility.objectEquals(this.fractionRules[1], set.fractionRules[1]) || !Utility.objectEquals(this.fractionRules[2], set.fractionRules[2]) || this.rules.length != set.rules.length || this.isFractionRuleSet != set.isFractionRuleSet) {
            return false;
        }
        while (0 < this.rules.length) {
            if (!this.rules[0].equals(set.rules[0])) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.name + ":\n");
        while (0 < this.rules.length) {
            sb.append("    " + this.rules[0].toString() + "\n");
            int n = 0;
            ++n;
        }
        if (this.negativeNumberRule != null) {
            sb.append("    " + this.negativeNumberRule.toString() + "\n");
        }
        if (this.fractionRules[0] != null) {
            sb.append("    " + this.fractionRules[0].toString() + "\n");
        }
        if (this.fractionRules[1] != null) {
            sb.append("    " + this.fractionRules[1].toString() + "\n");
        }
        if (this.fractionRules[2] != null) {
            sb.append("    " + this.fractionRules[2].toString() + "\n");
        }
        return sb.toString();
    }
    
    public boolean isFractionSet() {
        return this.isFractionRuleSet;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isPublic() {
        return !this.name.startsWith("%%");
    }
    
    public boolean isParseable() {
        return this.isParseable;
    }
    
    public void format(final long n, final StringBuffer sb, final int n2) {
        final NFRule normalRule = this.findNormalRule(n);
        if (++this.recursionCount >= 50) {
            this.recursionCount = 0;
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        normalRule.doFormat(n, sb, n2);
        --this.recursionCount;
    }
    
    public void format(final double n, final StringBuffer sb, final int n2) {
        final NFRule rule = this.findRule(n);
        if (++this.recursionCount >= 50) {
            this.recursionCount = 0;
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        rule.doFormat(n, sb, n2);
        --this.recursionCount;
    }
    
    private NFRule findRule(double n) {
        if (this.isFractionRuleSet) {
            return this.findFractionRuleSetRule(n);
        }
        if (n < 0.0) {
            if (this.negativeNumberRule != null) {
                return this.negativeNumberRule;
            }
            n = -n;
        }
        if (n != Math.floor(n)) {
            if (n < 1.0 && this.fractionRules[1] != null) {
                return this.fractionRules[1];
            }
            if (this.fractionRules[0] != null) {
                return this.fractionRules[0];
            }
        }
        if (this.fractionRules[2] != null) {
            return this.fractionRules[2];
        }
        return this.findNormalRule(Math.round(n));
    }
    
    private NFRule findNormalRule(long n) {
        if (this.isFractionRuleSet) {
            return this.findFractionRuleSetRule((double)n);
        }
        if (n < 0L) {
            if (this.negativeNumberRule != null) {
                return this.negativeNumberRule;
            }
            n = -n;
        }
        int length = this.rules.length;
        if (length <= 0) {
            return this.fractionRules[2];
        }
        while (0 < length) {
            final int n2 = 0 + length >>> 1;
            if (this.rules[n2].getBaseValue() == n) {
                return this.rules[n2];
            }
            if (this.rules[n2].getBaseValue() <= n) {
                continue;
            }
            length = n2;
        }
        if (length == 0) {
            throw new IllegalStateException("The rule set " + this.name + " cannot format the value " + n);
        }
        NFRule nfRule = this.rules[length - 1];
        if (nfRule.shouldRollBack((double)n)) {
            if (length == 1) {
                throw new IllegalStateException("The rule set " + this.name + " cannot roll back from the rule '" + nfRule + "'");
            }
            nfRule = this.rules[length - 2];
        }
        return nfRule;
    }
    
    private NFRule findFractionRuleSetRule(final double n) {
        long n2 = this.rules[0].getBaseValue();
        while (1 < this.rules.length) {
            n2 = lcm(n2, this.rules[1].getBaseValue());
            int n3 = 0;
            ++n3;
        }
        final long round = Math.round(n * n2);
        long n4 = Long.MAX_VALUE;
        while (0 < this.rules.length) {
            long n5 = round * this.rules[0].getBaseValue() % n2;
            if (n2 - n5 < n5) {
                n5 = n2 - n5;
            }
            if (n5 < n4) {
                n4 = n5;
                if (n4 == 0L) {
                    break;
                }
            }
            int n6 = 0;
            ++n6;
        }
        if (1 < this.rules.length && this.rules[1].getBaseValue() == this.rules[0].getBaseValue() && (Math.round(n * this.rules[0].getBaseValue()) < 1L || Math.round(n * this.rules[0].getBaseValue()) >= 2L)) {
            int n7 = 0;
            ++n7;
        }
        return this.rules[0];
    }
    
    private static long lcm(final long n, final long n2) {
        long n3;
        long n4;
        for (n3 = n, n4 = n2; (n3 & 0x1L) == 0x0L && (n4 & 0x1L) == 0x0L; n3 >>= 1, n4 >>= 1) {
            int n5 = 0;
            ++n5;
        }
        long n6;
        if ((n3 & 0x1L) == 0x1L) {
            n6 = -n4;
        }
        else {
            n6 = n3;
        }
        while (n6 != 0L) {
            while ((n6 & 0x1L) == 0x0L) {
                n6 >>= 1;
            }
            if (n6 > 0L) {
                n3 = n6;
            }
            else {
                n4 = -n6;
            }
            n6 = n3 - n4;
        }
        return n / (n3 << 0) * n2;
    }
    
    public Number parse(final String p0, final ParsePosition p1, final double p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: iconst_0       
        //     5: invokespecial   java/text/ParsePosition.<init>:(I)V
        //     8: astore          5
        //    10: lconst_0       
        //    11: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //    14: astore          6
        //    16: aconst_null    
        //    17: astore          7
        //    19: aload_1        
        //    20: invokevirtual   java/lang/String.length:()I
        //    23: ifne            29
        //    26: aload           6
        //    28: areturn        
        //    29: aload_0        
        //    30: getfield        com/ibm/icu/text/NFRuleSet.negativeNumberRule:Lcom/ibm/icu/text/NFRule;
        //    33: ifnull          79
        //    36: aload_0        
        //    37: getfield        com/ibm/icu/text/NFRuleSet.negativeNumberRule:Lcom/ibm/icu/text/NFRule;
        //    40: aload_1        
        //    41: aload_2        
        //    42: iconst_0       
        //    43: dload_3        
        //    44: invokevirtual   com/ibm/icu/text/NFRule.doParse:(Ljava/lang/String;Ljava/text/ParsePosition;ZD)Ljava/lang/Number;
        //    47: astore          7
        //    49: aload_2        
        //    50: invokevirtual   java/text/ParsePosition.getIndex:()I
        //    53: aload           5
        //    55: invokevirtual   java/text/ParsePosition.getIndex:()I
        //    58: if_icmple       74
        //    61: aload           7
        //    63: astore          6
        //    65: aload           5
        //    67: aload_2        
        //    68: invokevirtual   java/text/ParsePosition.getIndex:()I
        //    71: invokevirtual   java/text/ParsePosition.setIndex:(I)V
        //    74: aload_2        
        //    75: iconst_0       
        //    76: invokevirtual   java/text/ParsePosition.setIndex:(I)V
        //    79: iconst_0       
        //    80: iconst_3       
        //    81: if_icmpge       144
        //    84: aload_0        
        //    85: getfield        com/ibm/icu/text/NFRuleSet.fractionRules:[Lcom/ibm/icu/text/NFRule;
        //    88: iconst_0       
        //    89: aaload         
        //    90: ifnull          138
        //    93: aload_0        
        //    94: getfield        com/ibm/icu/text/NFRuleSet.fractionRules:[Lcom/ibm/icu/text/NFRule;
        //    97: iconst_0       
        //    98: aaload         
        //    99: aload_1        
        //   100: aload_2        
        //   101: iconst_0       
        //   102: dload_3        
        //   103: invokevirtual   com/ibm/icu/text/NFRule.doParse:(Ljava/lang/String;Ljava/text/ParsePosition;ZD)Ljava/lang/Number;
        //   106: astore          7
        //   108: aload_2        
        //   109: invokevirtual   java/text/ParsePosition.getIndex:()I
        //   112: aload           5
        //   114: invokevirtual   java/text/ParsePosition.getIndex:()I
        //   117: if_icmple       133
        //   120: aload           7
        //   122: astore          6
        //   124: aload           5
        //   126: aload_2        
        //   127: invokevirtual   java/text/ParsePosition.getIndex:()I
        //   130: invokevirtual   java/text/ParsePosition.setIndex:(I)V
        //   133: aload_2        
        //   134: iconst_0       
        //   135: invokevirtual   java/text/ParsePosition.setIndex:(I)V
        //   138: iinc            8, 1
        //   141: goto            79
        //   144: aload_0        
        //   145: getfield        com/ibm/icu/text/NFRuleSet.rules:[Lcom/ibm/icu/text/NFRule;
        //   148: arraylength    
        //   149: iconst_1       
        //   150: isub           
        //   151: istore          8
        //   153: iconst_0       
        //   154: iflt            248
        //   157: aload           5
        //   159: invokevirtual   java/text/ParsePosition.getIndex:()I
        //   162: aload_1        
        //   163: invokevirtual   java/lang/String.length:()I
        //   166: if_icmpge       248
        //   169: aload_0        
        //   170: getfield        com/ibm/icu/text/NFRuleSet.isFractionRuleSet:Z
        //   173: ifne            194
        //   176: aload_0        
        //   177: getfield        com/ibm/icu/text/NFRuleSet.rules:[Lcom/ibm/icu/text/NFRule;
        //   180: iconst_0       
        //   181: aaload         
        //   182: invokevirtual   com/ibm/icu/text/NFRule.getBaseValue:()J
        //   185: l2d            
        //   186: dload_3        
        //   187: dcmpl          
        //   188: iflt            194
        //   191: goto            242
        //   194: aload_0        
        //   195: getfield        com/ibm/icu/text/NFRuleSet.rules:[Lcom/ibm/icu/text/NFRule;
        //   198: iconst_0       
        //   199: aaload         
        //   200: aload_1        
        //   201: aload_2        
        //   202: aload_0        
        //   203: getfield        com/ibm/icu/text/NFRuleSet.isFractionRuleSet:Z
        //   206: dload_3        
        //   207: invokevirtual   com/ibm/icu/text/NFRule.doParse:(Ljava/lang/String;Ljava/text/ParsePosition;ZD)Ljava/lang/Number;
        //   210: astore          7
        //   212: aload_2        
        //   213: invokevirtual   java/text/ParsePosition.getIndex:()I
        //   216: aload           5
        //   218: invokevirtual   java/text/ParsePosition.getIndex:()I
        //   221: if_icmple       237
        //   224: aload           7
        //   226: astore          6
        //   228: aload           5
        //   230: aload_2        
        //   231: invokevirtual   java/text/ParsePosition.getIndex:()I
        //   234: invokevirtual   java/text/ParsePosition.setIndex:(I)V
        //   237: aload_2        
        //   238: iconst_0       
        //   239: invokevirtual   java/text/ParsePosition.setIndex:(I)V
        //   242: iinc            8, -1
        //   245: goto            153
        //   248: aload_2        
        //   249: aload           5
        //   251: invokevirtual   java/text/ParsePosition.getIndex:()I
        //   254: invokevirtual   java/text/ParsePosition.setIndex:(I)V
        //   257: aload           6
        //   259: areturn        
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
    
    static {
        $assertionsDisabled = !NFRuleSet.class.desiredAssertionStatus();
    }
}
