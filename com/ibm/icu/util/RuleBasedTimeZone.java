package com.ibm.icu.util;

import com.ibm.icu.impl.*;
import java.util.*;

public class RuleBasedTimeZone extends BasicTimeZone
{
    private static final long serialVersionUID = 7580833058949327935L;
    private final InitialTimeZoneRule initialRule;
    private List historicRules;
    private AnnualTimeZoneRule[] finalRules;
    private transient List historicTransitions;
    private transient boolean upToDate;
    private transient boolean isFrozen;
    
    public RuleBasedTimeZone(final String s, final InitialTimeZoneRule initialRule) {
        super(s);
        this.isFrozen = false;
        this.initialRule = initialRule;
    }
    
    public void addTransitionRule(final TimeZoneRule timeZoneRule) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen RuleBasedTimeZone instance.");
        }
        if (!timeZoneRule.isTransitionRule()) {
            throw new IllegalArgumentException("Rule must be a transition rule");
        }
        if (timeZoneRule instanceof AnnualTimeZoneRule && ((AnnualTimeZoneRule)timeZoneRule).getEndYear() == Integer.MAX_VALUE) {
            if (this.finalRules == null) {
                (this.finalRules = new AnnualTimeZoneRule[2])[0] = (AnnualTimeZoneRule)timeZoneRule;
            }
            else {
                if (this.finalRules[1] != null) {
                    throw new IllegalStateException("Too many final rules");
                }
                this.finalRules[1] = (AnnualTimeZoneRule)timeZoneRule;
            }
        }
        else {
            if (this.historicRules == null) {
                this.historicRules = new ArrayList();
            }
            this.historicRules.add(timeZoneRule);
        }
        this.upToDate = false;
    }
    
    @Override
    public int getOffset(final int n, int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n == 0) {
            n2 = 1 - n2;
        }
        final long n7 = Grego.fieldsToDay(n2, n3, n4) * 86400000L + n6;
        final int[] array = new int[2];
        this.getOffset(n7, true, 3, 1, array);
        return array[0] + array[1];
    }
    
    @Override
    public void getOffset(final long n, final boolean b, final int[] array) {
        this.getOffset(n, b, 4, 12, array);
    }
    
    @Override
    @Deprecated
    public void getOffsetFromLocal(final long n, final int n2, final int n3, final int[] array) {
        this.getOffset(n, true, n2, n3, array);
    }
    
    @Override
    public int getRawOffset() {
        final long currentTimeMillis = System.currentTimeMillis();
        final int[] array = new int[2];
        this.getOffset(currentTimeMillis, false, array);
        return array[0];
    }
    
    @Override
    public boolean inDaylightTime(final Date date) {
        final int[] array = new int[2];
        this.getOffset(date.getTime(), false, array);
        return array[1] != 0;
    }
    
    @Override
    public void setRawOffset(final int n) {
        throw new UnsupportedOperationException("setRawOffset in RuleBasedTimeZone is not supported.");
    }
    
    @Override
    public boolean useDaylightTime() {
        final long currentTimeMillis = System.currentTimeMillis();
        final int[] array = new int[2];
        this.getOffset(currentTimeMillis, false, array);
        if (array[1] != 0) {
            return true;
        }
        final TimeZoneTransition nextTransition = this.getNextTransition(currentTimeMillis, false);
        return nextTransition != null && nextTransition.getTo().getDSTSavings() != 0;
    }
    
    @Override
    public boolean observesDaylightTime() {
        long n = System.currentTimeMillis();
        final int[] array = new int[2];
        this.getOffset(n, false, array);
        if (array[1] != 0) {
            return true;
        }
        final BitSet set = (this.finalRules == null) ? null : new BitSet(this.finalRules.length);
        while (true) {
            final TimeZoneTransition nextTransition = this.getNextTransition(n, false);
            if (nextTransition == null) {
                break;
            }
            final TimeZoneRule to = nextTransition.getTo();
            if (to.getDSTSavings() != 0) {
                return true;
            }
            if (set != null) {
                while (0 < this.finalRules.length) {
                    if (this.finalRules[0].equals(to)) {
                        set.set(0);
                    }
                    int n2 = 0;
                    ++n2;
                }
                if (set.cardinality() == this.finalRules.length) {
                    break;
                }
            }
            n = nextTransition.getTime();
        }
        return false;
    }
    
    @Override
    public boolean hasSameRules(final TimeZone timeZone) {
        if (this == timeZone) {
            return true;
        }
        if (!(timeZone instanceof RuleBasedTimeZone)) {
            return false;
        }
        final RuleBasedTimeZone ruleBasedTimeZone = (RuleBasedTimeZone)timeZone;
        if (!this.initialRule.isEquivalentTo(ruleBasedTimeZone.initialRule)) {
            return false;
        }
        if (this.finalRules != null && ruleBasedTimeZone.finalRules != null) {
            while (0 < this.finalRules.length) {
                if (this.finalRules[0] != null || ruleBasedTimeZone.finalRules[0] != null) {
                    if (this.finalRules[0] == null || ruleBasedTimeZone.finalRules[0] == null || !this.finalRules[0].isEquivalentTo(ruleBasedTimeZone.finalRules[0])) {
                        return false;
                    }
                }
                int n = 0;
                ++n;
            }
        }
        else if (this.finalRules != null || ruleBasedTimeZone.finalRules != null) {
            return false;
        }
        if (this.historicRules != null && ruleBasedTimeZone.historicRules != null) {
            if (this.historicRules.size() != ruleBasedTimeZone.historicRules.size()) {
                return false;
            }
            for (final TimeZoneRule timeZoneRule : this.historicRules) {
                final Iterator iterator2 = ruleBasedTimeZone.historicRules.iterator();
                while (iterator2.hasNext() && !timeZoneRule.isEquivalentTo(iterator2.next())) {}
                if (!true) {
                    return false;
                }
            }
        }
        else if (this.historicRules != null || ruleBasedTimeZone.historicRules != null) {
            return false;
        }
        return true;
    }
    
    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        int n = 0;
        if (this.historicRules != null) {
            n = 1 + this.historicRules.size();
        }
        if (this.finalRules != null) {
            if (this.finalRules[1] != null) {
                n += 2;
            }
            else {
                ++n;
            }
        }
        final TimeZoneRule[] array = { this.initialRule };
        int n2 = 0;
        if (this.historicRules != null) {
            while (1 < this.historicRules.size() + 1) {
                array[1] = this.historicRules.get(0);
                ++n2;
            }
        }
        if (this.finalRules != null) {
            final TimeZoneRule[] array2 = array;
            final int n3 = 1;
            ++n2;
            array2[n3] = this.finalRules[0];
            if (this.finalRules[1] != null) {
                array[1] = this.finalRules[1];
            }
        }
        return array;
    }
    
    @Override
    public TimeZoneTransition getNextTransition(final long n, final boolean b) {
        this.complete();
        if (this.historicTransitions == null) {
            return null;
        }
        final TimeZoneTransition timeZoneTransition = this.historicTransitions.get(0);
        final long time = timeZoneTransition.getTime();
        TimeZoneTransition nextTransition;
        if (time > n || (b && time == n)) {
            nextTransition = timeZoneTransition;
        }
        else {
            int i = this.historicTransitions.size() - 1;
            final TimeZoneTransition timeZoneTransition2 = this.historicTransitions.get(i);
            final long time2 = timeZoneTransition2.getTime();
            if (b && time2 == n) {
                nextTransition = timeZoneTransition2;
            }
            else if (time2 <= n) {
                if (this.finalRules == null) {
                    return null;
                }
                final Date nextStart = this.finalRules[0].getNextStart(n, this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), b);
                final Date nextStart2 = this.finalRules[1].getNextStart(n, this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), b);
                TimeZoneTransition timeZoneTransition3;
                if (nextStart2.after(nextStart)) {
                    timeZoneTransition3 = new TimeZoneTransition(nextStart.getTime(), this.finalRules[1], this.finalRules[0]);
                }
                else {
                    timeZoneTransition3 = new TimeZoneTransition(nextStart2.getTime(), this.finalRules[0], this.finalRules[1]);
                }
                nextTransition = timeZoneTransition3;
            }
            else {
                --i;
                TimeZoneTransition timeZoneTransition4 = timeZoneTransition2;
                while (i > 0) {
                    final TimeZoneTransition timeZoneTransition5 = this.historicTransitions.get(i);
                    final long time3 = timeZoneTransition5.getTime();
                    if (time3 < n) {
                        break;
                    }
                    if (!b && time3 == n) {
                        break;
                    }
                    --i;
                    timeZoneTransition4 = timeZoneTransition5;
                }
                nextTransition = timeZoneTransition4;
            }
        }
        if (nextTransition != null) {
            final TimeZoneRule from = nextTransition.getFrom();
            final TimeZoneRule to = nextTransition.getTo();
            if (from.getRawOffset() == to.getRawOffset() && from.getDSTSavings() == to.getDSTSavings()) {
                if (true) {
                    return null;
                }
                nextTransition = this.getNextTransition(nextTransition.getTime(), false);
            }
        }
        return nextTransition;
    }
    
    @Override
    public TimeZoneTransition getPreviousTransition(final long n, final boolean b) {
        this.complete();
        if (this.historicTransitions == null) {
            return null;
        }
        final TimeZoneTransition timeZoneTransition = this.historicTransitions.get(0);
        final long time = timeZoneTransition.getTime();
        TimeZoneTransition previousTransition;
        if (b && time == n) {
            previousTransition = timeZoneTransition;
        }
        else {
            if (time >= n) {
                return null;
            }
            int i = this.historicTransitions.size() - 1;
            TimeZoneTransition timeZoneTransition2 = this.historicTransitions.get(i);
            final long time2 = timeZoneTransition2.getTime();
            if (b && time2 == n) {
                previousTransition = timeZoneTransition2;
            }
            else if (time2 < n) {
                if (this.finalRules != null) {
                    final Date previousStart = this.finalRules[0].getPreviousStart(n, this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), b);
                    final Date previousStart2 = this.finalRules[1].getPreviousStart(n, this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), b);
                    if (previousStart2.before(previousStart)) {
                        timeZoneTransition2 = new TimeZoneTransition(previousStart.getTime(), this.finalRules[1], this.finalRules[0]);
                    }
                    else {
                        timeZoneTransition2 = new TimeZoneTransition(previousStart2.getTime(), this.finalRules[0], this.finalRules[1]);
                    }
                }
                previousTransition = timeZoneTransition2;
            }
            else {
                --i;
                while (i >= 0) {
                    timeZoneTransition2 = this.historicTransitions.get(i);
                    final long time3 = timeZoneTransition2.getTime();
                    if (time3 < n) {
                        break;
                    }
                    if (b && time3 == n) {
                        break;
                    }
                    --i;
                }
                previousTransition = timeZoneTransition2;
            }
        }
        if (previousTransition != null) {
            final TimeZoneRule from = previousTransition.getFrom();
            final TimeZoneRule to = previousTransition.getTo();
            if (from.getRawOffset() == to.getRawOffset() && from.getDSTSavings() == to.getDSTSavings()) {
                previousTransition = this.getPreviousTransition(previousTransition.getTime(), false);
            }
        }
        return previousTransition;
    }
    
    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }
    
    private void complete() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/ibm/icu/util/RuleBasedTimeZone.upToDate:Z
        //     4: ifeq            8
        //     7: return         
        //     8: aload_0        
        //     9: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //    12: ifnull          34
        //    15: aload_0        
        //    16: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //    19: iconst_1       
        //    20: aaload         
        //    21: ifnonnull       34
        //    24: new             Ljava/lang/IllegalStateException;
        //    27: dup            
        //    28: ldc             "Incomplete final rules"
        //    30: invokespecial   java/lang/IllegalStateException.<init>:(Ljava/lang/String;)V
        //    33: athrow         
        //    34: aload_0        
        //    35: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicRules:Ljava/util/List;
        //    38: ifnonnull       48
        //    41: aload_0        
        //    42: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //    45: ifnull          702
        //    48: aload_0        
        //    49: getfield        com/ibm/icu/util/RuleBasedTimeZone.initialRule:Lcom/ibm/icu/util/InitialTimeZoneRule;
        //    52: astore_1       
        //    53: ldc2_w          -184303902528000000
        //    56: lstore_2       
        //    57: aload_0        
        //    58: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicRules:Ljava/util/List;
        //    61: ifnull          426
        //    64: new             Ljava/util/BitSet;
        //    67: dup            
        //    68: aload_0        
        //    69: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicRules:Ljava/util/List;
        //    72: invokeinterface java/util/List.size:()I
        //    77: invokespecial   java/util/BitSet.<init>:(I)V
        //    80: astore          4
        //    82: aload_1        
        //    83: invokevirtual   com/ibm/icu/util/TimeZoneRule.getRawOffset:()I
        //    86: istore          5
        //    88: aload_1        
        //    89: invokevirtual   com/ibm/icu/util/TimeZoneRule.getDSTSavings:()I
        //    92: istore          6
        //    94: ldc2_w          183882168921600000
        //    97: lstore          7
        //    99: aconst_null    
        //   100: astore          9
        //   102: iconst_0       
        //   103: aload_0        
        //   104: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicRules:Ljava/util/List;
        //   107: invokeinterface java/util/List.size:()I
        //   112: if_icmpge       246
        //   115: aload           4
        //   117: iconst_0       
        //   118: invokevirtual   java/util/BitSet.get:(I)Z
        //   121: ifeq            127
        //   124: goto            240
        //   127: aload_0        
        //   128: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicRules:Ljava/util/List;
        //   131: iconst_0       
        //   132: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   137: checkcast       Lcom/ibm/icu/util/TimeZoneRule;
        //   140: astore          14
        //   142: aload           14
        //   144: lload_2        
        //   145: iload           5
        //   147: iload           6
        //   149: iconst_0       
        //   150: invokevirtual   com/ibm/icu/util/TimeZoneRule.getNextStart:(JIIZ)Ljava/util/Date;
        //   153: astore          10
        //   155: aload           10
        //   157: ifnonnull       169
        //   160: aload           4
        //   162: iconst_0       
        //   163: invokevirtual   java/util/BitSet.set:(I)V
        //   166: goto            240
        //   169: aload           14
        //   171: aload_1        
        //   172: if_acmpeq       240
        //   175: aload           14
        //   177: invokevirtual   com/ibm/icu/util/TimeZoneRule.getName:()Ljava/lang/String;
        //   180: aload_1        
        //   181: invokevirtual   com/ibm/icu/util/TimeZoneRule.getName:()Ljava/lang/String;
        //   184: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   187: ifeq            217
        //   190: aload           14
        //   192: invokevirtual   com/ibm/icu/util/TimeZoneRule.getRawOffset:()I
        //   195: aload_1        
        //   196: invokevirtual   com/ibm/icu/util/TimeZoneRule.getRawOffset:()I
        //   199: if_icmpne       217
        //   202: aload           14
        //   204: invokevirtual   com/ibm/icu/util/TimeZoneRule.getDSTSavings:()I
        //   207: aload_1        
        //   208: invokevirtual   com/ibm/icu/util/TimeZoneRule.getDSTSavings:()I
        //   211: if_icmpne       217
        //   214: goto            240
        //   217: aload           10
        //   219: invokevirtual   java/util/Date.getTime:()J
        //   222: lstore          11
        //   224: lload           11
        //   226: lload           7
        //   228: lcmp           
        //   229: ifge            240
        //   232: lload           11
        //   234: lstore          7
        //   236: aload           14
        //   238: astore          9
        //   240: iinc            13, 1
        //   243: goto            102
        //   246: aload           9
        //   248: ifnonnull       289
        //   251: iconst_0       
        //   252: aload_0        
        //   253: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicRules:Ljava/util/List;
        //   256: invokeinterface java/util/List.size:()I
        //   261: if_icmpge       282
        //   264: aload           4
        //   266: iconst_0       
        //   267: invokevirtual   java/util/BitSet.get:(I)Z
        //   270: ifne            276
        //   273: goto            282
        //   276: iinc            14, 1
        //   279: goto            251
        //   282: iconst_0       
        //   283: ifeq            289
        //   286: goto            426
        //   289: aload_0        
        //   290: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   293: ifnull          369
        //   296: iconst_0       
        //   297: iconst_2       
        //   298: if_icmpge       369
        //   301: aload_0        
        //   302: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   305: iconst_0       
        //   306: aaload         
        //   307: aload_1        
        //   308: if_acmpne       314
        //   311: goto            363
        //   314: aload_0        
        //   315: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   318: iconst_0       
        //   319: aaload         
        //   320: lload_2        
        //   321: iload           5
        //   323: iload           6
        //   325: iconst_0       
        //   326: invokevirtual   com/ibm/icu/util/AnnualTimeZoneRule.getNextStart:(JIIZ)Ljava/util/Date;
        //   329: astore          10
        //   331: aload           10
        //   333: ifnull          363
        //   336: aload           10
        //   338: invokevirtual   java/util/Date.getTime:()J
        //   341: lstore          11
        //   343: lload           11
        //   345: lload           7
        //   347: lcmp           
        //   348: ifge            363
        //   351: lload           11
        //   353: lstore          7
        //   355: aload_0        
        //   356: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   359: iconst_0       
        //   360: aaload         
        //   361: astore          9
        //   363: iinc            13, 1
        //   366: goto            296
        //   369: aload           9
        //   371: ifnonnull       377
        //   374: goto            426
        //   377: aload_0        
        //   378: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicTransitions:Ljava/util/List;
        //   381: ifnonnull       395
        //   384: aload_0        
        //   385: new             Ljava/util/ArrayList;
        //   388: dup            
        //   389: invokespecial   java/util/ArrayList.<init>:()V
        //   392: putfield        com/ibm/icu/util/RuleBasedTimeZone.historicTransitions:Ljava/util/List;
        //   395: aload_0        
        //   396: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicTransitions:Ljava/util/List;
        //   399: new             Lcom/ibm/icu/util/TimeZoneTransition;
        //   402: dup            
        //   403: lload           7
        //   405: aload_1        
        //   406: aload           9
        //   408: invokespecial   com/ibm/icu/util/TimeZoneTransition.<init>:(JLcom/ibm/icu/util/TimeZoneRule;Lcom/ibm/icu/util/TimeZoneRule;)V
        //   411: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   416: pop            
        //   417: lload           7
        //   419: lstore_2       
        //   420: aload           9
        //   422: astore_1       
        //   423: goto            82
        //   426: aload_0        
        //   427: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   430: ifnull          702
        //   433: aload_0        
        //   434: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicTransitions:Ljava/util/List;
        //   437: ifnonnull       451
        //   440: aload_0        
        //   441: new             Ljava/util/ArrayList;
        //   444: dup            
        //   445: invokespecial   java/util/ArrayList.<init>:()V
        //   448: putfield        com/ibm/icu/util/RuleBasedTimeZone.historicTransitions:Ljava/util/List;
        //   451: aload_0        
        //   452: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   455: iconst_0       
        //   456: aaload         
        //   457: lload_2        
        //   458: aload_1        
        //   459: invokevirtual   com/ibm/icu/util/TimeZoneRule.getRawOffset:()I
        //   462: aload_1        
        //   463: invokevirtual   com/ibm/icu/util/TimeZoneRule.getDSTSavings:()I
        //   466: iconst_0       
        //   467: invokevirtual   com/ibm/icu/util/AnnualTimeZoneRule.getNextStart:(JIIZ)Ljava/util/Date;
        //   470: astore          4
        //   472: aload_0        
        //   473: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   476: iconst_1       
        //   477: aaload         
        //   478: lload_2        
        //   479: aload_1        
        //   480: invokevirtual   com/ibm/icu/util/TimeZoneRule.getRawOffset:()I
        //   483: aload_1        
        //   484: invokevirtual   com/ibm/icu/util/TimeZoneRule.getDSTSavings:()I
        //   487: iconst_0       
        //   488: invokevirtual   com/ibm/icu/util/AnnualTimeZoneRule.getNextStart:(JIIZ)Ljava/util/Date;
        //   491: astore          5
        //   493: aload           5
        //   495: aload           4
        //   497: invokevirtual   java/util/Date.after:(Ljava/util/Date;)Z
        //   500: ifeq            604
        //   503: aload_0        
        //   504: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicTransitions:Ljava/util/List;
        //   507: new             Lcom/ibm/icu/util/TimeZoneTransition;
        //   510: dup            
        //   511: aload           4
        //   513: invokevirtual   java/util/Date.getTime:()J
        //   516: aload_1        
        //   517: aload_0        
        //   518: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   521: iconst_0       
        //   522: aaload         
        //   523: invokespecial   com/ibm/icu/util/TimeZoneTransition.<init>:(JLcom/ibm/icu/util/TimeZoneRule;Lcom/ibm/icu/util/TimeZoneRule;)V
        //   526: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   531: pop            
        //   532: aload_0        
        //   533: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   536: iconst_1       
        //   537: aaload         
        //   538: aload           4
        //   540: invokevirtual   java/util/Date.getTime:()J
        //   543: aload_0        
        //   544: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   547: iconst_0       
        //   548: aaload         
        //   549: invokevirtual   com/ibm/icu/util/AnnualTimeZoneRule.getRawOffset:()I
        //   552: aload_0        
        //   553: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   556: iconst_0       
        //   557: aaload         
        //   558: invokevirtual   com/ibm/icu/util/AnnualTimeZoneRule.getDSTSavings:()I
        //   561: iconst_0       
        //   562: invokevirtual   com/ibm/icu/util/AnnualTimeZoneRule.getNextStart:(JIIZ)Ljava/util/Date;
        //   565: astore          5
        //   567: aload_0        
        //   568: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicTransitions:Ljava/util/List;
        //   571: new             Lcom/ibm/icu/util/TimeZoneTransition;
        //   574: dup            
        //   575: aload           5
        //   577: invokevirtual   java/util/Date.getTime:()J
        //   580: aload_0        
        //   581: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   584: iconst_0       
        //   585: aaload         
        //   586: aload_0        
        //   587: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   590: iconst_1       
        //   591: aaload         
        //   592: invokespecial   com/ibm/icu/util/TimeZoneTransition.<init>:(JLcom/ibm/icu/util/TimeZoneRule;Lcom/ibm/icu/util/TimeZoneRule;)V
        //   595: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   600: pop            
        //   601: goto            702
        //   604: aload_0        
        //   605: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicTransitions:Ljava/util/List;
        //   608: new             Lcom/ibm/icu/util/TimeZoneTransition;
        //   611: dup            
        //   612: aload           5
        //   614: invokevirtual   java/util/Date.getTime:()J
        //   617: aload_1        
        //   618: aload_0        
        //   619: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   622: iconst_1       
        //   623: aaload         
        //   624: invokespecial   com/ibm/icu/util/TimeZoneTransition.<init>:(JLcom/ibm/icu/util/TimeZoneRule;Lcom/ibm/icu/util/TimeZoneRule;)V
        //   627: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   632: pop            
        //   633: aload_0        
        //   634: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   637: iconst_0       
        //   638: aaload         
        //   639: aload           5
        //   641: invokevirtual   java/util/Date.getTime:()J
        //   644: aload_0        
        //   645: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   648: iconst_1       
        //   649: aaload         
        //   650: invokevirtual   com/ibm/icu/util/AnnualTimeZoneRule.getRawOffset:()I
        //   653: aload_0        
        //   654: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   657: iconst_1       
        //   658: aaload         
        //   659: invokevirtual   com/ibm/icu/util/AnnualTimeZoneRule.getDSTSavings:()I
        //   662: iconst_0       
        //   663: invokevirtual   com/ibm/icu/util/AnnualTimeZoneRule.getNextStart:(JIIZ)Ljava/util/Date;
        //   666: astore          4
        //   668: aload_0        
        //   669: getfield        com/ibm/icu/util/RuleBasedTimeZone.historicTransitions:Ljava/util/List;
        //   672: new             Lcom/ibm/icu/util/TimeZoneTransition;
        //   675: dup            
        //   676: aload           4
        //   678: invokevirtual   java/util/Date.getTime:()J
        //   681: aload_0        
        //   682: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   685: iconst_1       
        //   686: aaload         
        //   687: aload_0        
        //   688: getfield        com/ibm/icu/util/RuleBasedTimeZone.finalRules:[Lcom/ibm/icu/util/AnnualTimeZoneRule;
        //   691: iconst_0       
        //   692: aaload         
        //   693: invokespecial   com/ibm/icu/util/TimeZoneTransition.<init>:(JLcom/ibm/icu/util/TimeZoneRule;Lcom/ibm/icu/util/TimeZoneRule;)V
        //   696: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   701: pop            
        //   702: aload_0        
        //   703: iconst_1       
        //   704: putfield        com/ibm/icu/util/RuleBasedTimeZone.upToDate:Z
        //   707: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void getOffset(final long n, final boolean b, final int n2, final int n3, final int[] array) {
        this.complete();
        TimeZoneRule timeZoneRule = null;
        if (this.historicTransitions == null) {
            timeZoneRule = this.initialRule;
        }
        else if (n < getTransitionTime(this.historicTransitions.get(0), b, n2, n3)) {
            timeZoneRule = this.initialRule;
        }
        else {
            int n4 = this.historicTransitions.size() - 1;
            if (n > getTransitionTime((TimeZoneTransition)this.historicTransitions.get(n4), b, n2, n3)) {
                if (this.finalRules != null) {
                    timeZoneRule = this.findRuleInFinal(n, b, n2, n3);
                }
                if (timeZoneRule == null) {
                    timeZoneRule = ((TimeZoneTransition)this.historicTransitions.get(n4)).getTo();
                }
            }
            else {
                while (n4 >= 0 && n < getTransitionTime((TimeZoneTransition)this.historicTransitions.get(n4), b, n2, n3)) {
                    --n4;
                }
                timeZoneRule = ((TimeZoneTransition)this.historicTransitions.get(n4)).getTo();
            }
        }
        array[0] = timeZoneRule.getRawOffset();
        array[1] = timeZoneRule.getDSTSavings();
    }
    
    private TimeZoneRule findRuleInFinal(final long n, final boolean b, final int n2, final int n3) {
        if (this.finalRules == null || this.finalRules.length != 2 || this.finalRules[0] == null || this.finalRules[1] == null) {
            return null;
        }
        long n4 = n;
        if (b) {
            n4 -= getLocalDelta(this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), n2, n3);
        }
        final Date previousStart = this.finalRules[0].getPreviousStart(n4, this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), true);
        long n5 = n;
        if (b) {
            n5 -= getLocalDelta(this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), n2, n3);
        }
        final Date previousStart2 = this.finalRules[1].getPreviousStart(n5, this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), true);
        if (previousStart != null && previousStart2 != null) {
            return previousStart.after(previousStart2) ? this.finalRules[0] : this.finalRules[1];
        }
        if (previousStart != null) {
            return this.finalRules[0];
        }
        if (previousStart2 != null) {
            return this.finalRules[1];
        }
        return null;
    }
    
    private static long getTransitionTime(final TimeZoneTransition timeZoneTransition, final boolean b, final int n, final int n2) {
        long time = timeZoneTransition.getTime();
        if (b) {
            time += getLocalDelta(timeZoneTransition.getFrom().getRawOffset(), timeZoneTransition.getFrom().getDSTSavings(), timeZoneTransition.getTo().getRawOffset(), timeZoneTransition.getTo().getDSTSavings(), n, n2);
        }
        return time;
    }
    
    private static int getLocalDelta(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final int n7 = n + n2;
        final int n8 = n3 + n4;
        final boolean b = n2 != 0 && n4 == 0;
        final boolean b2 = n2 == 0 && n4 != 0;
        if (n8 - n7 >= 0) {
            if (((n5 & 0x3) != 0x1 || !b) && ((n5 & 0x3) != 0x3 || !b2)) {
                if (((n5 & 0x3) != 0x1 || !b2) && ((n5 & 0x3) != 0x3 || !b)) {
                    if ((n5 & 0xC) == 0xC) {}
                }
            }
        }
        else if (((n6 & 0x3) != 0x1 || !b) && ((n6 & 0x3) != 0x3 || !b2)) {
            if (((n6 & 0x3) != 0x1 || !b2) && ((n6 & 0x3) != 0x3 || !b)) {
                if ((n6 & 0xC) == 0x4) {}
            }
        }
        return 0;
    }
    
    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }
    
    @Override
    public TimeZone freeze() {
        this.complete();
        this.isFrozen = true;
        return this;
    }
    
    @Override
    public TimeZone cloneAsThawed() {
        final RuleBasedTimeZone ruleBasedTimeZone = (RuleBasedTimeZone)super.cloneAsThawed();
        if (this.historicRules != null) {
            ruleBasedTimeZone.historicRules = new ArrayList(this.historicRules);
        }
        if (this.finalRules != null) {
            ruleBasedTimeZone.finalRules = this.finalRules.clone();
        }
        ruleBasedTimeZone.isFrozen = false;
        return ruleBasedTimeZone;
    }
    
    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    @Override
    public Object freeze() {
        return this.freeze();
    }
}
