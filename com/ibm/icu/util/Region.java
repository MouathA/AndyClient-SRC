package com.ibm.icu.util;

import java.util.*;

public class Region implements Comparable
{
    public static final int UNDEFINED_NUMERIC_CODE = -1;
    private String id;
    private int code;
    private RegionType type;
    private Region containingRegion;
    private Set containedRegions;
    private List preferredValues;
    private static boolean regionDataIsLoaded;
    private static Map regionIDMap;
    private static Map numericCodeMap;
    private static Map regionAliases;
    private static ArrayList regions;
    private static ArrayList availableRegions;
    private static final String UNKNOWN_REGION_ID = "ZZ";
    private static final String OUTLYING_OCEANIA_REGION_ID = "QO";
    private static final String WORLD_ID = "001";
    
    private Region() {
        this.containingRegion = null;
        this.containedRegions = new TreeSet();
        this.preferredValues = null;
    }
    
    private static synchronized void loadRegionData() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            7
        //     6: return         
        //     7: new             Ljava/util/HashMap;
        //    10: dup            
        //    11: invokespecial   java/util/HashMap.<init>:()V
        //    14: putstatic       com/ibm/icu/util/Region.regionAliases:Ljava/util/Map;
        //    17: new             Ljava/util/HashMap;
        //    20: dup            
        //    21: invokespecial   java/util/HashMap.<init>:()V
        //    24: putstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //    27: new             Ljava/util/HashMap;
        //    30: dup            
        //    31: invokespecial   java/util/HashMap.<init>:()V
        //    34: putstatic       com/ibm/icu/util/Region.numericCodeMap:Ljava/util/Map;
        //    37: new             Ljava/util/ArrayList;
        //    40: dup            
        //    41: invokestatic    com/ibm/icu/util/Region$RegionType.values:()[Lcom/ibm/icu/util/Region$RegionType;
        //    44: arraylength    
        //    45: invokespecial   java/util/ArrayList.<init>:(I)V
        //    48: putstatic       com/ibm/icu/util/Region.availableRegions:Ljava/util/ArrayList;
        //    51: aconst_null    
        //    52: astore_0       
        //    53: aconst_null    
        //    54: astore_1       
        //    55: aconst_null    
        //    56: astore_2       
        //    57: aconst_null    
        //    58: astore_3       
        //    59: aconst_null    
        //    60: astore          4
        //    62: aconst_null    
        //    63: astore          5
        //    65: ldc             "com/ibm/icu/impl/data/icudt51b"
        //    67: ldc             "metadata"
        //    69: getstatic       com/ibm/icu/impl/ICUResourceBundle.ICU_DATA_CLASS_LOADER:Ljava/lang/ClassLoader;
        //    72: invokestatic    com/ibm/icu/util/UResourceBundle.getBundleInstance:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)Lcom/ibm/icu/util/UResourceBundle;
        //    75: astore          6
        //    77: aload           6
        //    79: ldc             "regionCodes"
        //    81: invokevirtual   com/ibm/icu/util/UResourceBundle.get:(Ljava/lang/String;)Lcom/ibm/icu/util/UResourceBundle;
        //    84: astore_0       
        //    85: aload           6
        //    87: ldc             "territoryAlias"
        //    89: invokevirtual   com/ibm/icu/util/UResourceBundle.get:(Ljava/lang/String;)Lcom/ibm/icu/util/UResourceBundle;
        //    92: astore_1       
        //    93: ldc             "com/ibm/icu/impl/data/icudt51b"
        //    95: ldc             "supplementalData"
        //    97: getstatic       com/ibm/icu/impl/ICUResourceBundle.ICU_DATA_CLASS_LOADER:Ljava/lang/ClassLoader;
        //   100: invokestatic    com/ibm/icu/util/UResourceBundle.getBundleInstance:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)Lcom/ibm/icu/util/UResourceBundle;
        //   103: astore          7
        //   105: aload           7
        //   107: ldc             "codeMappings"
        //   109: invokevirtual   com/ibm/icu/util/UResourceBundle.get:(Ljava/lang/String;)Lcom/ibm/icu/util/UResourceBundle;
        //   112: astore_2       
        //   113: aload           7
        //   115: ldc             "territoryContainment"
        //   117: invokevirtual   com/ibm/icu/util/UResourceBundle.get:(Ljava/lang/String;)Lcom/ibm/icu/util/UResourceBundle;
        //   120: astore          4
        //   122: aload           4
        //   124: ldc             "001"
        //   126: invokevirtual   com/ibm/icu/util/UResourceBundle.get:(Ljava/lang/String;)Lcom/ibm/icu/util/UResourceBundle;
        //   129: astore_3       
        //   130: aload           4
        //   132: ldc             "grouping"
        //   134: invokevirtual   com/ibm/icu/util/UResourceBundle.get:(Ljava/lang/String;)Lcom/ibm/icu/util/UResourceBundle;
        //   137: astore          5
        //   139: aload_3        
        //   140: invokevirtual   com/ibm/icu/util/UResourceBundle.getStringArray:()[Ljava/lang/String;
        //   143: astore          8
        //   145: aload           8
        //   147: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   150: astore          9
        //   152: aload           5
        //   154: invokevirtual   com/ibm/icu/util/UResourceBundle.getStringArray:()[Ljava/lang/String;
        //   157: astore          10
        //   159: aload           10
        //   161: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   164: astore          11
        //   166: aload_0        
        //   167: invokevirtual   com/ibm/icu/util/UResourceBundle.getSize:()I
        //   170: istore          12
        //   172: new             Ljava/util/ArrayList;
        //   175: dup            
        //   176: iload           12
        //   178: invokespecial   java/util/ArrayList.<init>:(I)V
        //   181: putstatic       com/ibm/icu/util/Region.regions:Ljava/util/ArrayList;
        //   184: iconst_0       
        //   185: iload           12
        //   187: if_icmpge       308
        //   190: new             Lcom/ibm/icu/util/Region;
        //   193: dup            
        //   194: invokespecial   com/ibm/icu/util/Region.<init>:()V
        //   197: astore          14
        //   199: aload_0        
        //   200: iconst_0       
        //   201: invokevirtual   com/ibm/icu/util/UResourceBundle.getString:(I)Ljava/lang/String;
        //   204: astore          15
        //   206: aload           14
        //   208: aload           15
        //   210: putfield        com/ibm/icu/util/Region.id:Ljava/lang/String;
        //   213: aload           14
        //   215: getstatic       com/ibm/icu/util/Region$RegionType.TERRITORY:Lcom/ibm/icu/util/Region$RegionType;
        //   218: putfield        com/ibm/icu/util/Region.type:Lcom/ibm/icu/util/Region$RegionType;
        //   221: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   224: aload           15
        //   226: aload           14
        //   228: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   233: pop            
        //   234: aload           15
        //   236: ldc             "[0-9]{3}"
        //   238: invokevirtual   java/lang/String.matches:(Ljava/lang/String;)Z
        //   241: ifeq            287
        //   244: aload           14
        //   246: aload           15
        //   248: invokestatic    java/lang/Integer.valueOf:(Ljava/lang/String;)Ljava/lang/Integer;
        //   251: invokevirtual   java/lang/Integer.intValue:()I
        //   254: putfield        com/ibm/icu/util/Region.code:I
        //   257: getstatic       com/ibm/icu/util/Region.numericCodeMap:Ljava/util/Map;
        //   260: aload           14
        //   262: getfield        com/ibm/icu/util/Region.code:I
        //   265: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   268: aload           14
        //   270: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   275: pop            
        //   276: aload           14
        //   278: getstatic       com/ibm/icu/util/Region$RegionType.SUBCONTINENT:Lcom/ibm/icu/util/Region$RegionType;
        //   281: putfield        com/ibm/icu/util/Region.type:Lcom/ibm/icu/util/Region$RegionType;
        //   284: goto            293
        //   287: aload           14
        //   289: iconst_m1      
        //   290: putfield        com/ibm/icu/util/Region.code:I
        //   293: getstatic       com/ibm/icu/util/Region.regions:Ljava/util/ArrayList;
        //   296: aload           14
        //   298: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   301: pop            
        //   302: iinc            13, 1
        //   305: goto            184
        //   308: iconst_0       
        //   309: aload_1        
        //   310: invokevirtual   com/ibm/icu/util/UResourceBundle.getSize:()I
        //   313: if_icmpge       613
        //   316: aload_1        
        //   317: iconst_0       
        //   318: invokevirtual   com/ibm/icu/util/UResourceBundle.get:(I)Lcom/ibm/icu/util/UResourceBundle;
        //   321: astore          14
        //   323: aload           14
        //   325: invokevirtual   com/ibm/icu/util/UResourceBundle.getKey:()Ljava/lang/String;
        //   328: astore          15
        //   330: aload           14
        //   332: invokevirtual   com/ibm/icu/util/UResourceBundle.getString:()Ljava/lang/String;
        //   335: astore          16
        //   337: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   340: aload           16
        //   342: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   347: ifeq            387
        //   350: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   353: aload           15
        //   355: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   360: ifne            387
        //   363: getstatic       com/ibm/icu/util/Region.regionAliases:Ljava/util/Map;
        //   366: aload           15
        //   368: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   371: aload           16
        //   373: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   378: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   383: pop            
        //   384: goto            607
        //   387: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   390: aload           15
        //   392: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   397: ifeq            418
        //   400: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   403: aload           15
        //   405: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   410: checkcast       Lcom/ibm/icu/util/Region;
        //   413: astore          17
        //   415: goto            507
        //   418: new             Lcom/ibm/icu/util/Region;
        //   421: dup            
        //   422: invokespecial   com/ibm/icu/util/Region.<init>:()V
        //   425: astore          17
        //   427: aload           17
        //   429: aload           15
        //   431: putfield        com/ibm/icu/util/Region.id:Ljava/lang/String;
        //   434: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   437: aload           15
        //   439: aload           17
        //   441: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   446: pop            
        //   447: aload           15
        //   449: ldc             "[0-9]{3}"
        //   451: invokevirtual   java/lang/String.matches:(Ljava/lang/String;)Z
        //   454: ifeq            492
        //   457: aload           17
        //   459: aload           15
        //   461: invokestatic    java/lang/Integer.valueOf:(Ljava/lang/String;)Ljava/lang/Integer;
        //   464: invokevirtual   java/lang/Integer.intValue:()I
        //   467: putfield        com/ibm/icu/util/Region.code:I
        //   470: getstatic       com/ibm/icu/util/Region.numericCodeMap:Ljava/util/Map;
        //   473: aload           17
        //   475: getfield        com/ibm/icu/util/Region.code:I
        //   478: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   481: aload           17
        //   483: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   488: pop            
        //   489: goto            498
        //   492: aload           17
        //   494: iconst_m1      
        //   495: putfield        com/ibm/icu/util/Region.code:I
        //   498: getstatic       com/ibm/icu/util/Region.regions:Ljava/util/ArrayList;
        //   501: aload           17
        //   503: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   506: pop            
        //   507: aload           17
        //   509: getstatic       com/ibm/icu/util/Region$RegionType.DEPRECATED:Lcom/ibm/icu/util/Region$RegionType;
        //   512: putfield        com/ibm/icu/util/Region.type:Lcom/ibm/icu/util/Region$RegionType;
        //   515: aload           16
        //   517: ldc             " "
        //   519: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   522: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   525: astore          18
        //   527: aload           17
        //   529: new             Ljava/util/ArrayList;
        //   532: dup            
        //   533: invokespecial   java/util/ArrayList.<init>:()V
        //   536: putfield        com/ibm/icu/util/Region.preferredValues:Ljava/util/List;
        //   539: aload           18
        //   541: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   546: astore          19
        //   548: aload           19
        //   550: invokeinterface java/util/Iterator.hasNext:()Z
        //   555: ifeq            607
        //   558: aload           19
        //   560: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   565: checkcast       Ljava/lang/String;
        //   568: astore          20
        //   570: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   573: aload           20
        //   575: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   580: ifeq            604
        //   583: aload           17
        //   585: getfield        com/ibm/icu/util/Region.preferredValues:Ljava/util/List;
        //   588: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   591: aload           20
        //   593: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   598: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   603: pop            
        //   604: goto            548
        //   607: iinc            13, 1
        //   610: goto            308
        //   613: iconst_0       
        //   614: aload_2        
        //   615: invokevirtual   com/ibm/icu/util/UResourceBundle.getSize:()I
        //   618: if_icmpge       742
        //   621: aload_2        
        //   622: iconst_0       
        //   623: invokevirtual   com/ibm/icu/util/UResourceBundle.get:(I)Lcom/ibm/icu/util/UResourceBundle;
        //   626: astore          14
        //   628: aload           14
        //   630: invokevirtual   com/ibm/icu/util/UResourceBundle.getType:()I
        //   633: bipush          8
        //   635: if_icmpne       736
        //   638: aload           14
        //   640: invokevirtual   com/ibm/icu/util/UResourceBundle.getStringArray:()[Ljava/lang/String;
        //   643: astore          15
        //   645: aload           15
        //   647: iconst_0       
        //   648: aaload         
        //   649: astore          16
        //   651: aload           15
        //   653: iconst_1       
        //   654: aaload         
        //   655: invokestatic    java/lang/Integer.valueOf:(Ljava/lang/String;)Ljava/lang/Integer;
        //   658: astore          17
        //   660: aload           15
        //   662: iconst_2       
        //   663: aaload         
        //   664: astore          18
        //   666: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   669: aload           16
        //   671: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   676: ifeq            736
        //   679: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   682: aload           16
        //   684: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   689: checkcast       Lcom/ibm/icu/util/Region;
        //   692: astore          19
        //   694: aload           19
        //   696: aload           17
        //   698: invokevirtual   java/lang/Integer.intValue:()I
        //   701: putfield        com/ibm/icu/util/Region.code:I
        //   704: getstatic       com/ibm/icu/util/Region.numericCodeMap:Ljava/util/Map;
        //   707: aload           19
        //   709: getfield        com/ibm/icu/util/Region.code:I
        //   712: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   715: aload           19
        //   717: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   722: pop            
        //   723: getstatic       com/ibm/icu/util/Region.regionAliases:Ljava/util/Map;
        //   726: aload           18
        //   728: aload           19
        //   730: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   735: pop            
        //   736: iinc            13, 1
        //   739: goto            613
        //   742: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   745: ldc             "001"
        //   747: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   752: ifeq            778
        //   755: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   758: ldc             "001"
        //   760: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   765: checkcast       Lcom/ibm/icu/util/Region;
        //   768: astore          13
        //   770: aload           13
        //   772: getstatic       com/ibm/icu/util/Region$RegionType.WORLD:Lcom/ibm/icu/util/Region$RegionType;
        //   775: putfield        com/ibm/icu/util/Region.type:Lcom/ibm/icu/util/Region$RegionType;
        //   778: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   781: ldc             "ZZ"
        //   783: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   788: ifeq            814
        //   791: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   794: ldc             "ZZ"
        //   796: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   801: checkcast       Lcom/ibm/icu/util/Region;
        //   804: astore          13
        //   806: aload           13
        //   808: getstatic       com/ibm/icu/util/Region$RegionType.UNKNOWN:Lcom/ibm/icu/util/Region$RegionType;
        //   811: putfield        com/ibm/icu/util/Region.type:Lcom/ibm/icu/util/Region$RegionType;
        //   814: aload           9
        //   816: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   821: astore          14
        //   823: aload           14
        //   825: invokeinterface java/util/Iterator.hasNext:()Z
        //   830: ifeq            884
        //   833: aload           14
        //   835: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   840: checkcast       Ljava/lang/String;
        //   843: astore          15
        //   845: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   848: aload           15
        //   850: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   855: ifeq            881
        //   858: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   861: aload           15
        //   863: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   868: checkcast       Lcom/ibm/icu/util/Region;
        //   871: astore          13
        //   873: aload           13
        //   875: getstatic       com/ibm/icu/util/Region$RegionType.CONTINENT:Lcom/ibm/icu/util/Region$RegionType;
        //   878: putfield        com/ibm/icu/util/Region.type:Lcom/ibm/icu/util/Region$RegionType;
        //   881: goto            823
        //   884: aload           11
        //   886: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   891: astore          14
        //   893: aload           14
        //   895: invokeinterface java/util/Iterator.hasNext:()Z
        //   900: ifeq            954
        //   903: aload           14
        //   905: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   910: checkcast       Ljava/lang/String;
        //   913: astore          15
        //   915: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   918: aload           15
        //   920: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   925: ifeq            951
        //   928: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   931: aload           15
        //   933: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   938: checkcast       Lcom/ibm/icu/util/Region;
        //   941: astore          13
        //   943: aload           13
        //   945: getstatic       com/ibm/icu/util/Region$RegionType.GROUPING:Lcom/ibm/icu/util/Region$RegionType;
        //   948: putfield        com/ibm/icu/util/Region.type:Lcom/ibm/icu/util/Region$RegionType;
        //   951: goto            893
        //   954: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   957: ldc             "QO"
        //   959: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   964: ifeq            990
        //   967: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //   970: ldc             "QO"
        //   972: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   977: checkcast       Lcom/ibm/icu/util/Region;
        //   980: astore          13
        //   982: aload           13
        //   984: getstatic       com/ibm/icu/util/Region$RegionType.SUBCONTINENT:Lcom/ibm/icu/util/Region$RegionType;
        //   987: putfield        com/ibm/icu/util/Region.type:Lcom/ibm/icu/util/Region$RegionType;
        //   990: iconst_0       
        //   991: aload           4
        //   993: invokevirtual   com/ibm/icu/util/UResourceBundle.getSize:()I
        //   996: if_icmpge       1114
        //   999: aload           4
        //  1001: iconst_0       
        //  1002: invokevirtual   com/ibm/icu/util/UResourceBundle.get:(I)Lcom/ibm/icu/util/UResourceBundle;
        //  1005: astore          15
        //  1007: aload           15
        //  1009: invokevirtual   com/ibm/icu/util/UResourceBundle.getKey:()Ljava/lang/String;
        //  1012: astore          16
        //  1014: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //  1017: aload           16
        //  1019: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //  1024: checkcast       Lcom/ibm/icu/util/Region;
        //  1027: astore          17
        //  1029: iconst_0       
        //  1030: aload           15
        //  1032: invokevirtual   com/ibm/icu/util/UResourceBundle.getSize:()I
        //  1035: if_icmpge       1108
        //  1038: aload           15
        //  1040: iconst_0       
        //  1041: invokevirtual   com/ibm/icu/util/UResourceBundle.getString:(I)Ljava/lang/String;
        //  1044: astore          19
        //  1046: getstatic       com/ibm/icu/util/Region.regionIDMap:Ljava/util/Map;
        //  1049: aload           19
        //  1051: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //  1056: checkcast       Lcom/ibm/icu/util/Region;
        //  1059: astore          20
        //  1061: aload           17
        //  1063: ifnull          1102
        //  1066: aload           20
        //  1068: ifnull          1102
        //  1071: aload           17
        //  1073: getfield        com/ibm/icu/util/Region.containedRegions:Ljava/util/Set;
        //  1076: aload           20
        //  1078: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //  1083: pop            
        //  1084: aload           17
        //  1086: invokevirtual   com/ibm/icu/util/Region.getType:()Lcom/ibm/icu/util/Region$RegionType;
        //  1089: getstatic       com/ibm/icu/util/Region$RegionType.GROUPING:Lcom/ibm/icu/util/Region$RegionType;
        //  1092: if_acmpeq       1102
        //  1095: aload           20
        //  1097: aload           17
        //  1099: putfield        com/ibm/icu/util/Region.containingRegion:Lcom/ibm/icu/util/Region;
        //  1102: iinc            18, 1
        //  1105: goto            1029
        //  1108: iinc            14, 1
        //  1111: goto            990
        //  1114: iconst_0       
        //  1115: invokestatic    com/ibm/icu/util/Region$RegionType.values:()[Lcom/ibm/icu/util/Region$RegionType;
        //  1118: arraylength    
        //  1119: if_icmpge       1142
        //  1122: getstatic       com/ibm/icu/util/Region.availableRegions:Ljava/util/ArrayList;
        //  1125: new             Ljava/util/TreeSet;
        //  1128: dup            
        //  1129: invokespecial   java/util/TreeSet.<init>:()V
        //  1132: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //  1135: pop            
        //  1136: iinc            14, 1
        //  1139: goto            1114
        //  1142: getstatic       com/ibm/icu/util/Region.regions:Ljava/util/ArrayList;
        //  1145: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //  1148: astore          14
        //  1150: aload           14
        //  1152: invokeinterface java/util/Iterator.hasNext:()Z
        //  1157: ifeq            1221
        //  1160: aload           14
        //  1162: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1167: checkcast       Lcom/ibm/icu/util/Region;
        //  1170: astore          15
        //  1172: getstatic       com/ibm/icu/util/Region.availableRegions:Ljava/util/ArrayList;
        //  1175: aload           15
        //  1177: getfield        com/ibm/icu/util/Region.type:Lcom/ibm/icu/util/Region$RegionType;
        //  1180: invokevirtual   com/ibm/icu/util/Region$RegionType.ordinal:()I
        //  1183: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1186: checkcast       Ljava/util/Set;
        //  1189: astore          16
        //  1191: aload           16
        //  1193: aload           15
        //  1195: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //  1200: pop            
        //  1201: getstatic       com/ibm/icu/util/Region.availableRegions:Ljava/util/ArrayList;
        //  1204: aload           15
        //  1206: getfield        com/ibm/icu/util/Region.type:Lcom/ibm/icu/util/Region$RegionType;
        //  1209: invokevirtual   com/ibm/icu/util/Region$RegionType.ordinal:()I
        //  1212: aload           16
        //  1214: invokevirtual   java/util/ArrayList.set:(ILjava/lang/Object;)Ljava/lang/Object;
        //  1217: pop            
        //  1218: goto            1150
        //  1221: iconst_1       
        //  1222: putstatic       com/ibm/icu/util/Region.regionDataIsLoaded:Z
        //  1225: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static Region getInstance(final String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        Region region = Region.regionIDMap.get(s);
        if (region == null) {
            region = Region.regionAliases.get(s);
        }
        if (region == null) {
            throw new IllegalArgumentException("Unknown region id: " + s);
        }
        if (region.type == RegionType.DEPRECATED && region.preferredValues.size() == 1) {
            region = (Region)region.preferredValues.get(0);
        }
        return region;
    }
    
    public static Region getInstance(final int n) {
        Region region = Region.numericCodeMap.get(n);
        if (region == null) {
            String s = "";
            if (n < 10) {
                s = "00";
            }
            else if (n < 100) {
                s = "0";
            }
            region = (Region)Region.regionAliases.get(s + Integer.toString(n));
        }
        if (region == null) {
            throw new IllegalArgumentException("Unknown region code: " + n);
        }
        if (region.type == RegionType.DEPRECATED && region.preferredValues.size() == 1) {
            region = (Region)region.preferredValues.get(0);
        }
        return region;
    }
    
    public static Set getAvailable(final RegionType regionType) {
        return Collections.unmodifiableSet((Set<?>)Region.availableRegions.get(regionType.ordinal()));
    }
    
    public Region getContainingRegion() {
        return this.containingRegion;
    }
    
    public Region getContainingRegion(final RegionType regionType) {
        if (this.containingRegion == null) {
            return null;
        }
        if (this.containingRegion.type.equals(regionType)) {
            return this.containingRegion;
        }
        return this.containingRegion.getContainingRegion(regionType);
    }
    
    public Set getContainedRegions() {
        return Collections.unmodifiableSet((Set<?>)this.containedRegions);
    }
    
    public Set getContainedRegions(final RegionType regionType) {
        final TreeSet<Object> set = new TreeSet<Object>();
        for (final Region region : this.getContainedRegions()) {
            if (region.getType() == regionType) {
                set.add(region);
            }
            else {
                set.addAll(region.getContainedRegions(regionType));
            }
        }
        return Collections.unmodifiableSet((Set<?>)set);
    }
    
    public List getPreferredValues() {
        if (this.type == RegionType.DEPRECATED) {
            return Collections.unmodifiableList((List<?>)this.preferredValues);
        }
        return null;
    }
    
    public boolean contains(final Region region) {
        if (this.containedRegions.contains(region)) {
            return true;
        }
        final Iterator<Region> iterator = this.containedRegions.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().contains(region)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return this.id;
    }
    
    public int getNumericCode() {
        return this.code;
    }
    
    public RegionType getType() {
        return this.type;
    }
    
    public int compareTo(final Region region) {
        return this.id.compareTo(region.id);
    }
    
    public int compareTo(final Object o) {
        return this.compareTo((Region)o);
    }
    
    static {
        Region.regionDataIsLoaded = false;
        Region.regionIDMap = null;
        Region.numericCodeMap = null;
        Region.regionAliases = null;
        Region.regions = null;
        Region.availableRegions = null;
    }
    
    public enum RegionType
    {
        UNKNOWN("UNKNOWN", 0), 
        TERRITORY("TERRITORY", 1), 
        WORLD("WORLD", 2), 
        CONTINENT("CONTINENT", 3), 
        SUBCONTINENT("SUBCONTINENT", 4), 
        GROUPING("GROUPING", 5), 
        DEPRECATED("DEPRECATED", 6);
        
        private static final RegionType[] $VALUES;
        
        private RegionType(final String s, final int n) {
        }
        
        static {
            $VALUES = new RegionType[] { RegionType.UNKNOWN, RegionType.TERRITORY, RegionType.WORLD, RegionType.CONTINENT, RegionType.SUBCONTINENT, RegionType.GROUPING, RegionType.DEPRECATED };
        }
    }
}
