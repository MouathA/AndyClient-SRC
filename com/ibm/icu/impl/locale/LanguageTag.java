package com.ibm.icu.impl.locale;

import java.util.*;

public class LanguageTag
{
    private static final boolean JDKIMPL = false;
    public static final String SEP = "-";
    public static final String PRIVATEUSE = "x";
    public static String UNDETERMINED;
    public static final String PRIVUSE_VARIANT_PREFIX = "lvariant";
    private String _language;
    private String _script;
    private String _region;
    private String _privateuse;
    private List _extlangs;
    private List _variants;
    private List _extensions;
    private static final Map GRANDFATHERED;
    
    private LanguageTag() {
        this._language = "";
        this._script = "";
        this._region = "";
        this._privateuse = "";
        this._extlangs = Collections.emptyList();
        this._variants = Collections.emptyList();
        this._extensions = Collections.emptyList();
    }
    
    public static LanguageTag parse(final String s, ParseStatus parseStatus) {
        if (parseStatus == null) {
            parseStatus = new ParseStatus();
        }
        else {
            parseStatus.reset();
        }
        final String[] array = LanguageTag.GRANDFATHERED.get(new AsciiUtil.CaseInsensitiveKey(s));
        StringTokenIterator stringTokenIterator;
        if (array != null) {
            stringTokenIterator = new StringTokenIterator(array[1], "-");
        }
        else {
            stringTokenIterator = new StringTokenIterator(s, "-");
        }
        final LanguageTag languageTag = new LanguageTag();
        if (parseStatus == 0) {
            languageTag.parseExtlangs(stringTokenIterator, parseStatus);
            languageTag.parseScript(stringTokenIterator, parseStatus);
            languageTag.parseRegion(stringTokenIterator, parseStatus);
            languageTag.parseVariants(stringTokenIterator, parseStatus);
            languageTag.parseExtensions(stringTokenIterator, parseStatus);
        }
        languageTag.parsePrivateuse(stringTokenIterator, parseStatus);
        if (!stringTokenIterator.isDone() && !parseStatus.isError()) {
            final String current = stringTokenIterator.current();
            parseStatus._errorIndex = stringTokenIterator.currentStart();
            if (current.length() == 0) {
                parseStatus._errorMsg = "Empty subtag";
            }
            else {
                parseStatus._errorMsg = "Invalid subtag: " + current;
            }
        }
        return languageTag;
    }
    
    private boolean parseExtlangs(final StringTokenIterator p0, final ParseStatus p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //     4: ifne            14
        //     7: aload_2        
        //     8: invokevirtual   com/ibm/icu/impl/locale/ParseStatus.isError:()Z
        //    11: ifeq            16
        //    14: iconst_0       
        //    15: ireturn        
        //    16: aload_1        
        //    17: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //    20: ifne            105
        //    23: aload_1        
        //    24: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.current:()Ljava/lang/String;
        //    27: astore          4
        //    29: aload           4
        //    31: if_icmpne       37
        //    34: goto            105
        //    37: aload_0        
        //    38: getfield        com/ibm/icu/impl/locale/LanguageTag._extlangs:Ljava/util/List;
        //    41: invokeinterface java/util/List.isEmpty:()Z
        //    46: ifeq            61
        //    49: aload_0        
        //    50: new             Ljava/util/ArrayList;
        //    53: dup            
        //    54: iconst_3       
        //    55: invokespecial   java/util/ArrayList.<init>:(I)V
        //    58: putfield        com/ibm/icu/impl/locale/LanguageTag._extlangs:Ljava/util/List;
        //    61: aload_0        
        //    62: getfield        com/ibm/icu/impl/locale/LanguageTag._extlangs:Ljava/util/List;
        //    65: aload           4
        //    67: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    72: pop            
        //    73: aload_2        
        //    74: aload_1        
        //    75: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.currentEnd:()I
        //    78: putfield        com/ibm/icu/impl/locale/ParseStatus._parseLength:I
        //    81: aload_1        
        //    82: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.next:()Ljava/lang/String;
        //    85: pop            
        //    86: aload_0        
        //    87: getfield        com/ibm/icu/impl/locale/LanguageTag._extlangs:Ljava/util/List;
        //    90: invokeinterface java/util/List.size:()I
        //    95: iconst_3       
        //    96: if_icmpne       102
        //    99: goto            105
        //   102: goto            16
        //   105: iconst_1       
        //   106: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private boolean parseScript(final StringTokenIterator p0, final ParseStatus p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //     4: ifne            14
        //     7: aload_2        
        //     8: invokevirtual   com/ibm/icu/impl/locale/ParseStatus.isError:()Z
        //    11: ifeq            16
        //    14: iconst_0       
        //    15: ireturn        
        //    16: aload_1        
        //    17: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.current:()Ljava/lang/String;
        //    20: astore          4
        //    22: aload           4
        //    24: if_icmpne       46
        //    27: aload_0        
        //    28: aload           4
        //    30: putfield        com/ibm/icu/impl/locale/LanguageTag._script:Ljava/lang/String;
        //    33: aload_2        
        //    34: aload_1        
        //    35: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.currentEnd:()I
        //    38: putfield        com/ibm/icu/impl/locale/ParseStatus._parseLength:I
        //    41: aload_1        
        //    42: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.next:()Ljava/lang/String;
        //    45: pop            
        //    46: iconst_1       
        //    47: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private boolean parseRegion(final StringTokenIterator p0, final ParseStatus p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //     4: ifne            14
        //     7: aload_2        
        //     8: invokevirtual   com/ibm/icu/impl/locale/ParseStatus.isError:()Z
        //    11: ifeq            16
        //    14: iconst_0       
        //    15: ireturn        
        //    16: aload_1        
        //    17: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.current:()Ljava/lang/String;
        //    20: astore          4
        //    22: aload           4
        //    24: if_icmpne       46
        //    27: aload_0        
        //    28: aload           4
        //    30: putfield        com/ibm/icu/impl/locale/LanguageTag._region:Ljava/lang/String;
        //    33: aload_2        
        //    34: aload_1        
        //    35: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.currentEnd:()I
        //    38: putfield        com/ibm/icu/impl/locale/ParseStatus._parseLength:I
        //    41: aload_1        
        //    42: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.next:()Ljava/lang/String;
        //    45: pop            
        //    46: iconst_1       
        //    47: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private boolean parseVariants(final StringTokenIterator p0, final ParseStatus p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //     4: ifne            14
        //     7: aload_2        
        //     8: invokevirtual   com/ibm/icu/impl/locale/ParseStatus.isError:()Z
        //    11: ifeq            16
        //    14: iconst_0       
        //    15: ireturn        
        //    16: aload_1        
        //    17: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //    20: ifne            89
        //    23: aload_1        
        //    24: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.current:()Ljava/lang/String;
        //    27: astore          4
        //    29: aload           4
        //    31: if_icmplt       37
        //    34: goto            89
        //    37: aload_0        
        //    38: getfield        com/ibm/icu/impl/locale/LanguageTag._variants:Ljava/util/List;
        //    41: invokeinterface java/util/List.isEmpty:()Z
        //    46: ifeq            61
        //    49: aload_0        
        //    50: new             Ljava/util/ArrayList;
        //    53: dup            
        //    54: iconst_3       
        //    55: invokespecial   java/util/ArrayList.<init>:(I)V
        //    58: putfield        com/ibm/icu/impl/locale/LanguageTag._variants:Ljava/util/List;
        //    61: aload_0        
        //    62: getfield        com/ibm/icu/impl/locale/LanguageTag._variants:Ljava/util/List;
        //    65: aload           4
        //    67: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    72: pop            
        //    73: aload_2        
        //    74: aload_1        
        //    75: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.currentEnd:()I
        //    78: putfield        com/ibm/icu/impl/locale/ParseStatus._parseLength:I
        //    81: aload_1        
        //    82: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.next:()Ljava/lang/String;
        //    85: pop            
        //    86: goto            16
        //    89: iconst_1       
        //    90: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private boolean parseExtensions(final StringTokenIterator p0, final ParseStatus p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //     4: ifne            14
        //     7: aload_2        
        //     8: invokevirtual   com/ibm/icu/impl/locale/ParseStatus.isError:()Z
        //    11: ifeq            16
        //    14: iconst_0       
        //    15: ireturn        
        //    16: aload_1        
        //    17: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //    20: ifne            196
        //    23: aload_1        
        //    24: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.current:()Ljava/lang/String;
        //    27: astore          4
        //    29: aload           4
        //    31: if_icmpne       196
        //    34: aload_1        
        //    35: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.currentStart:()I
        //    38: istore          5
        //    40: aload           4
        //    42: astore          6
        //    44: new             Ljava/lang/StringBuilder;
        //    47: dup            
        //    48: aload           6
        //    50: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    53: astore          7
        //    55: aload_1        
        //    56: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.next:()Ljava/lang/String;
        //    59: pop            
        //    60: aload_1        
        //    61: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //    64: ifne            107
        //    67: aload_1        
        //    68: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.current:()Ljava/lang/String;
        //    71: astore          4
        //    73: aload           4
        //    75: if_icmplt       107
        //    78: aload           7
        //    80: ldc             "-"
        //    82: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    85: aload           4
        //    87: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    90: pop            
        //    91: aload_2        
        //    92: aload_1        
        //    93: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.currentEnd:()I
        //    96: putfield        com/ibm/icu/impl/locale/ParseStatus._parseLength:I
        //    99: aload_1        
        //   100: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.next:()Ljava/lang/String;
        //   103: pop            
        //   104: goto            60
        //   107: aload_2        
        //   108: getfield        com/ibm/icu/impl/locale/ParseStatus._parseLength:I
        //   111: iload           5
        //   113: if_icmpgt       154
        //   116: aload_2        
        //   117: iload           5
        //   119: putfield        com/ibm/icu/impl/locale/ParseStatus._errorIndex:I
        //   122: aload_2        
        //   123: new             Ljava/lang/StringBuilder;
        //   126: dup            
        //   127: invokespecial   java/lang/StringBuilder.<init>:()V
        //   130: ldc             "Incomplete extension '"
        //   132: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   135: aload           6
        //   137: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   140: ldc             "'"
        //   142: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   145: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   148: putfield        com/ibm/icu/impl/locale/ParseStatus._errorMsg:Ljava/lang/String;
        //   151: goto            196
        //   154: aload_0        
        //   155: getfield        com/ibm/icu/impl/locale/LanguageTag._extensions:Ljava/util/List;
        //   158: invokeinterface java/util/List.size:()I
        //   163: ifne            178
        //   166: aload_0        
        //   167: new             Ljava/util/ArrayList;
        //   170: dup            
        //   171: iconst_4       
        //   172: invokespecial   java/util/ArrayList.<init>:(I)V
        //   175: putfield        com/ibm/icu/impl/locale/LanguageTag._extensions:Ljava/util/List;
        //   178: aload_0        
        //   179: getfield        com/ibm/icu/impl/locale/LanguageTag._extensions:Ljava/util/List;
        //   182: aload           7
        //   184: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   187: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   192: pop            
        //   193: goto            16
        //   196: iconst_1       
        //   197: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private boolean parsePrivateuse(final StringTokenIterator p0, final ParseStatus p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //     4: ifne            14
        //     7: aload_2        
        //     8: invokevirtual   com/ibm/icu/impl/locale/ParseStatus.isError:()Z
        //    11: ifeq            16
        //    14: iconst_0       
        //    15: ireturn        
        //    16: aload_1        
        //    17: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.current:()Ljava/lang/String;
        //    20: astore          4
        //    22: aload           4
        //    24: if_icmpne       132
        //    27: aload_1        
        //    28: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.currentStart:()I
        //    31: istore          5
        //    33: new             Ljava/lang/StringBuilder;
        //    36: dup            
        //    37: aload           4
        //    39: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    42: astore          6
        //    44: aload_1        
        //    45: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.next:()Ljava/lang/String;
        //    48: pop            
        //    49: aload_1        
        //    50: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //    53: ifne            99
        //    56: aload_1        
        //    57: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.current:()Ljava/lang/String;
        //    60: astore          4
        //    62: aload           4
        //    64: if_icmplt       70
        //    67: goto            99
        //    70: aload           6
        //    72: ldc             "-"
        //    74: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    77: aload           4
        //    79: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    82: pop            
        //    83: aload_2        
        //    84: aload_1        
        //    85: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.currentEnd:()I
        //    88: putfield        com/ibm/icu/impl/locale/ParseStatus._parseLength:I
        //    91: aload_1        
        //    92: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.next:()Ljava/lang/String;
        //    95: pop            
        //    96: goto            49
        //    99: aload_2        
        //   100: getfield        com/ibm/icu/impl/locale/ParseStatus._parseLength:I
        //   103: iload           5
        //   105: if_icmpgt       123
        //   108: aload_2        
        //   109: iload           5
        //   111: putfield        com/ibm/icu/impl/locale/ParseStatus._errorIndex:I
        //   114: aload_2        
        //   115: ldc             "Incomplete privateuse"
        //   117: putfield        com/ibm/icu/impl/locale/ParseStatus._errorMsg:Ljava/lang/String;
        //   120: goto            132
        //   123: aload_0        
        //   124: aload           6
        //   126: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   129: putfield        com/ibm/icu/impl/locale/LanguageTag._privateuse:Ljava/lang/String;
        //   132: iconst_1       
        //   133: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static LanguageTag parseLocale(final BaseLocale p0, final LocaleExtensions p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   com/ibm/icu/impl/locale/LanguageTag.<init>:()V
        //     7: astore_2       
        //     8: aload_0        
        //     9: invokevirtual   com/ibm/icu/impl/locale/BaseLocale.getLanguage:()Ljava/lang/String;
        //    12: astore_3       
        //    13: aload_0        
        //    14: invokevirtual   com/ibm/icu/impl/locale/BaseLocale.getScript:()Ljava/lang/String;
        //    17: astore          4
        //    19: aload_0        
        //    20: invokevirtual   com/ibm/icu/impl/locale/BaseLocale.getRegion:()Ljava/lang/String;
        //    23: astore          5
        //    25: aload_0        
        //    26: invokevirtual   com/ibm/icu/impl/locale/BaseLocale.getVariant:()Ljava/lang/String;
        //    29: astore          6
        //    31: aconst_null    
        //    32: astore          8
        //    34: aload_3        
        //    35: invokevirtual   java/lang/String.length:()I
        //    38: ifle            92
        //    41: aload_3        
        //    42: if_icmplt       92
        //    45: aload_3        
        //    46: ldc             "iw"
        //    48: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    51: ifeq            60
        //    54: ldc             "he"
        //    56: astore_3       
        //    57: goto            87
        //    60: aload_3        
        //    61: ldc             "ji"
        //    63: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    66: ifeq            75
        //    69: ldc             "yi"
        //    71: astore_3       
        //    72: goto            87
        //    75: aload_3        
        //    76: ldc             "in"
        //    78: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    81: ifeq            87
        //    84: ldc             "id"
        //    86: astore_3       
        //    87: aload_2        
        //    88: aload_3        
        //    89: putfield        com/ibm/icu/impl/locale/LanguageTag._language:Ljava/lang/String;
        //    92: aload           4
        //    94: invokevirtual   java/lang/String.length:()I
        //    97: ifle            114
        //   100: aload           4
        //   102: if_icmpne       114
        //   105: aload_2        
        //   106: aload           4
        //   108: invokestatic    com/ibm/icu/impl/locale/LanguageTag.canonicalizeScript:(Ljava/lang/String;)Ljava/lang/String;
        //   111: putfield        com/ibm/icu/impl/locale/LanguageTag._script:Ljava/lang/String;
        //   114: aload           5
        //   116: invokevirtual   java/lang/String.length:()I
        //   119: ifle            136
        //   122: aload           5
        //   124: if_icmpne       136
        //   127: aload_2        
        //   128: aload           5
        //   130: invokestatic    com/ibm/icu/impl/locale/LanguageTag.canonicalizeRegion:(Ljava/lang/String;)Ljava/lang/String;
        //   133: putfield        com/ibm/icu/impl/locale/LanguageTag._region:Ljava/lang/String;
        //   136: aload           6
        //   138: invokevirtual   java/lang/String.length:()I
        //   141: ifle            325
        //   144: aconst_null    
        //   145: astore          9
        //   147: new             Lcom/ibm/icu/impl/locale/StringTokenIterator;
        //   150: dup            
        //   151: aload           6
        //   153: ldc             "_"
        //   155: invokespecial   com/ibm/icu/impl/locale/StringTokenIterator.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //   158: astore          10
        //   160: aload           10
        //   162: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //   165: ifne            219
        //   168: aload           10
        //   170: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.current:()Ljava/lang/String;
        //   173: astore          11
        //   175: aload           11
        //   177: if_icmplt       183
        //   180: goto            219
        //   183: aload           9
        //   185: ifnonnull       197
        //   188: new             Ljava/util/ArrayList;
        //   191: dup            
        //   192: invokespecial   java/util/ArrayList.<init>:()V
        //   195: astore          9
        //   197: aload           9
        //   199: aload           11
        //   201: invokestatic    com/ibm/icu/impl/locale/LanguageTag.canonicalizeVariant:(Ljava/lang/String;)Ljava/lang/String;
        //   204: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   209: pop            
        //   210: aload           10
        //   212: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.next:()Ljava/lang/String;
        //   215: pop            
        //   216: goto            160
        //   219: aload           9
        //   221: ifnull          230
        //   224: aload_2        
        //   225: aload           9
        //   227: putfield        com/ibm/icu/impl/locale/LanguageTag._variants:Ljava/util/List;
        //   230: aload           10
        //   232: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //   235: ifne            325
        //   238: new             Ljava/lang/StringBuilder;
        //   241: dup            
        //   242: invokespecial   java/lang/StringBuilder.<init>:()V
        //   245: astore          11
        //   247: aload           10
        //   249: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.isDone:()Z
        //   252: ifne            310
        //   255: aload           10
        //   257: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.current:()Ljava/lang/String;
        //   260: astore          12
        //   262: aload           12
        //   264: if_icmplt       270
        //   267: goto            310
        //   270: aload           11
        //   272: invokevirtual   java/lang/StringBuilder.length:()I
        //   275: ifle            286
        //   278: aload           11
        //   280: ldc             "-"
        //   282: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   285: pop            
        //   286: aload           12
        //   288: invokestatic    com/ibm/icu/impl/locale/AsciiUtil.toLowerString:(Ljava/lang/String;)Ljava/lang/String;
        //   291: astore          12
        //   293: aload           11
        //   295: aload           12
        //   297: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   300: pop            
        //   301: aload           10
        //   303: invokevirtual   com/ibm/icu/impl/locale/StringTokenIterator.next:()Ljava/lang/String;
        //   306: pop            
        //   307: goto            247
        //   310: aload           11
        //   312: invokevirtual   java/lang/StringBuilder.length:()I
        //   315: ifle            325
        //   318: aload           11
        //   320: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   323: astore          8
        //   325: aconst_null    
        //   326: astore          9
        //   328: aconst_null    
        //   329: astore          10
        //   331: aload_1        
        //   332: invokevirtual   com/ibm/icu/impl/locale/LocaleExtensions.getKeys:()Ljava/util/Set;
        //   335: astore          11
        //   337: aload           11
        //   339: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   344: astore          12
        //   346: aload           12
        //   348: invokeinterface java/util/Iterator.hasNext:()Z
        //   353: ifeq            453
        //   356: aload           12
        //   358: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   363: checkcast       Ljava/lang/Character;
        //   366: astore          13
        //   368: aload_1        
        //   369: aload           13
        //   371: invokevirtual   com/ibm/icu/impl/locale/LocaleExtensions.getExtension:(Ljava/lang/Character;)Lcom/ibm/icu/impl/locale/Extension;
        //   374: astore          14
        //   376: aload           13
        //   378: invokevirtual   java/lang/Character.charValue:()C
        //   381: invokestatic    com/ibm/icu/impl/locale/LanguageTag.isPrivateusePrefixChar:(C)Z
        //   384: ifeq            397
        //   387: aload           14
        //   389: invokevirtual   com/ibm/icu/impl/locale/Extension.getValue:()Ljava/lang/String;
        //   392: astore          10
        //   394: goto            450
        //   397: aload           9
        //   399: ifnonnull       411
        //   402: new             Ljava/util/ArrayList;
        //   405: dup            
        //   406: invokespecial   java/util/ArrayList.<init>:()V
        //   409: astore          9
        //   411: aload           9
        //   413: new             Ljava/lang/StringBuilder;
        //   416: dup            
        //   417: invokespecial   java/lang/StringBuilder.<init>:()V
        //   420: aload           13
        //   422: invokevirtual   java/lang/Character.toString:()Ljava/lang/String;
        //   425: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   428: ldc             "-"
        //   430: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   433: aload           14
        //   435: invokevirtual   com/ibm/icu/impl/locale/Extension.getValue:()Ljava/lang/String;
        //   438: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   441: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   444: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   449: pop            
        //   450: goto            346
        //   453: aload           9
        //   455: ifnull          464
        //   458: aload_2        
        //   459: aload           9
        //   461: putfield        com/ibm/icu/impl/locale/LanguageTag._extensions:Ljava/util/List;
        //   464: aload           8
        //   466: ifnull          544
        //   469: aload           10
        //   471: ifnonnull       500
        //   474: new             Ljava/lang/StringBuilder;
        //   477: dup            
        //   478: invokespecial   java/lang/StringBuilder.<init>:()V
        //   481: ldc_w           "lvariant-"
        //   484: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   487: aload           8
        //   489: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   492: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   495: astore          10
        //   497: goto            544
        //   500: new             Ljava/lang/StringBuilder;
        //   503: dup            
        //   504: invokespecial   java/lang/StringBuilder.<init>:()V
        //   507: aload           10
        //   509: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   512: ldc             "-"
        //   514: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   517: ldc             "lvariant"
        //   519: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   522: ldc             "-"
        //   524: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   527: aload           8
        //   529: ldc             "_"
        //   531: ldc             "-"
        //   533: invokevirtual   java/lang/String.replace:(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
        //   536: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   539: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   542: astore          10
        //   544: aload           10
        //   546: ifnull          555
        //   549: aload_2        
        //   550: aload           10
        //   552: putfield        com/ibm/icu/impl/locale/LanguageTag._privateuse:Ljava/lang/String;
        //   555: aload_2        
        //   556: getfield        com/ibm/icu/impl/locale/LanguageTag._language:Ljava/lang/String;
        //   559: invokevirtual   java/lang/String.length:()I
        //   562: ifne            580
        //   565: goto            573
        //   568: aload           10
        //   570: ifnonnull       580
        //   573: aload_2        
        //   574: getstatic       com/ibm/icu/impl/locale/LanguageTag.UNDETERMINED:Ljava/lang/String;
        //   577: putfield        com/ibm/icu/impl/locale/LanguageTag._language:Ljava/lang/String;
        //   580: aload_2        
        //   581: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public String getLanguage() {
        return this._language;
    }
    
    public List getExtlangs() {
        return Collections.unmodifiableList((List<?>)this._extlangs);
    }
    
    public String getScript() {
        return this._script;
    }
    
    public String getRegion() {
        return this._region;
    }
    
    public List getVariants() {
        return Collections.unmodifiableList((List<?>)this._variants);
    }
    
    public List getExtensions() {
        return Collections.unmodifiableList((List<?>)this._extensions);
    }
    
    public String getPrivateuse() {
        return this._privateuse;
    }
    
    public static boolean isExtensionSingletonChar(final char c) {
        return isExtensionSingleton(String.valueOf(c));
    }
    
    public static boolean isPrivateusePrefixChar(final char c) {
        return AsciiUtil.caseIgnoreMatch("x", String.valueOf(c));
    }
    
    public static String canonicalizeLanguage(final String s) {
        return AsciiUtil.toLowerString(s);
    }
    
    public static String canonicalizeExtlang(final String s) {
        return AsciiUtil.toLowerString(s);
    }
    
    public static String canonicalizeScript(final String s) {
        return AsciiUtil.toTitleString(s);
    }
    
    public static String canonicalizeRegion(final String s) {
        return AsciiUtil.toUpperString(s);
    }
    
    public static String canonicalizeVariant(final String s) {
        return AsciiUtil.toLowerString(s);
    }
    
    public static String canonicalizeExtension(final String s) {
        return AsciiUtil.toLowerString(s);
    }
    
    public static String canonicalizeExtensionSingleton(final String s) {
        return AsciiUtil.toLowerString(s);
    }
    
    public static String canonicalizeExtensionSubtag(final String s) {
        return AsciiUtil.toLowerString(s);
    }
    
    public static String canonicalizePrivateuse(final String s) {
        return AsciiUtil.toLowerString(s);
    }
    
    public static String canonicalizePrivateuseSubtag(final String s) {
        return AsciiUtil.toLowerString(s);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this._language.length() > 0) {
            sb.append(this._language);
            final Iterator<String> iterator = this._extlangs.iterator();
            while (iterator.hasNext()) {
                sb.append("-").append(iterator.next());
            }
            if (this._script.length() > 0) {
                sb.append("-").append(this._script);
            }
            if (this._region.length() > 0) {
                sb.append("-").append(this._region);
            }
            final Iterator<String> iterator2 = this._extlangs.iterator();
            while (iterator2.hasNext()) {
                sb.append("-").append(iterator2.next());
            }
            final Iterator<String> iterator3 = this._extensions.iterator();
            while (iterator3.hasNext()) {
                sb.append("-").append(iterator3.next());
            }
        }
        if (this._privateuse.length() > 0) {
            if (sb.length() > 0) {
                sb.append("-");
            }
            sb.append(this._privateuse);
        }
        return sb.toString();
    }
    
    static {
        LanguageTag.UNDETERMINED = "und";
        GRANDFATHERED = new HashMap();
        final String[][] array = { { "art-lojban", "jbo" }, { "cel-gaulish", "xtg-x-cel-gaulish" }, { "en-GB-oed", "en-GB-x-oed" }, { "i-ami", "ami" }, { "i-bnn", "bnn" }, { "i-default", "en-x-i-default" }, { "i-enochian", "und-x-i-enochian" }, { "i-hak", "hak" }, { "i-klingon", "tlh" }, { "i-lux", "lb" }, { "i-mingo", "see-x-i-mingo" }, { "i-navajo", "nv" }, { "i-pwn", "pwn" }, { "i-tao", "tao" }, { "i-tay", "tay" }, { "i-tsu", "tsu" }, { "no-bok", "nb" }, { "no-nyn", "nn" }, { "sgn-BE-FR", "sfb" }, { "sgn-BE-NL", "vgt" }, { "sgn-CH-DE", "sgg" }, { "zh-guoyu", "cmn" }, { "zh-hakka", "hak" }, { "zh-min", "nan-x-zh-min" }, { "zh-min-nan", "nan" }, { "zh-xiang", "hsn" } };
        while (0 < array.length) {
            final String[] array2 = array[0];
            LanguageTag.GRANDFATHERED.put(new AsciiUtil.CaseInsensitiveKey(array2[0]), array2);
            int n = 0;
            ++n;
        }
    }
}
