package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;

public class NumberingSystem
{
    private String desc;
    private int radix;
    private boolean algorithmic;
    private String name;
    private static ICUCache cachedLocaleData;
    private static ICUCache cachedStringData;
    
    public NumberingSystem() {
        this.radix = 10;
        this.algorithmic = false;
        this.desc = "0123456789";
        this.name = "latn";
    }
    
    public static NumberingSystem getInstance(final int n, final boolean b, final String s) {
        return getInstance(null, n, b, s);
    }
    
    private static NumberingSystem getInstance(final String p0, final int p1, final boolean p2, final String p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_2       
        //     2: if_icmpge       15
        //     5: new             Ljava/lang/IllegalArgumentException;
        //     8: dup            
        //     9: ldc             "Invalid radix for numbering system"
        //    11: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //    14: athrow         
        //    15: iload_2        
        //    16: ifne            41
        //    19: aload_3        
        //    20: invokevirtual   java/lang/String.length:()I
        //    23: iload_1        
        //    24: if_icmpne       31
        //    27: aload_3        
        //    28: if_icmpeq       41
        //    31: new             Ljava/lang/IllegalArgumentException;
        //    34: dup            
        //    35: ldc             "Invalid digit string for numbering system"
        //    37: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //    40: athrow         
        //    41: new             Lcom/ibm/icu/text/NumberingSystem;
        //    44: dup            
        //    45: invokespecial   com/ibm/icu/text/NumberingSystem.<init>:()V
        //    48: astore          4
        //    50: aload           4
        //    52: iload_1        
        //    53: putfield        com/ibm/icu/text/NumberingSystem.radix:I
        //    56: aload           4
        //    58: iload_2        
        //    59: putfield        com/ibm/icu/text/NumberingSystem.algorithmic:Z
        //    62: aload           4
        //    64: aload_3        
        //    65: putfield        com/ibm/icu/text/NumberingSystem.desc:Ljava/lang/String;
        //    68: aload           4
        //    70: aload_0        
        //    71: putfield        com/ibm/icu/text/NumberingSystem.name:Ljava/lang/String;
        //    74: aload           4
        //    76: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static NumberingSystem getInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }
    
    public static NumberingSystem getInstance(final ULocale uLocale) {
        final String[] array = { "native", "traditional", "finance" };
        Boolean b = true;
        String keywordValue = uLocale.getKeywordValue("numbers");
        if (keywordValue != null) {
            final String[] array2 = array;
            while (0 < array2.length) {
                if (keywordValue.equals(array2[0])) {
                    b = false;
                    break;
                }
                int n = 0;
                ++n;
            }
        }
        else {
            keywordValue = "default";
            b = false;
        }
        if (b) {
            final NumberingSystem instanceByName = getInstanceByName(keywordValue);
            if (instanceByName != null) {
                return instanceByName;
            }
            keywordValue = "default";
            b = false;
        }
        final String baseName = uLocale.getBaseName();
        NumberingSystem instanceByName2 = (NumberingSystem)NumberingSystem.cachedLocaleData.get(baseName + "@numbers=" + keywordValue);
        if (instanceByName2 != null) {
            return instanceByName2;
        }
        final String s = keywordValue;
        String stringWithFallback = null;
        while (!b) {
            stringWithFallback = ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale)).getWithFallback("NumberElements").getStringWithFallback(keywordValue);
            b = true;
        }
        if (stringWithFallback != null) {
            instanceByName2 = getInstanceByName(stringWithFallback);
        }
        if (instanceByName2 == null) {
            instanceByName2 = new NumberingSystem();
        }
        NumberingSystem.cachedLocaleData.put(baseName + "@numbers=" + s, instanceByName2);
        return instanceByName2;
    }
    
    public static NumberingSystem getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static NumberingSystem getInstanceByName(final String s) {
        final NumberingSystem numberingSystem = (NumberingSystem)NumberingSystem.cachedStringData.get(s);
        if (numberingSystem != null) {
            return numberingSystem;
        }
        final UResourceBundle value = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "numberingSystems").get("numberingSystems").get(s);
        final NumberingSystem instance = getInstance(s, value.get("radix").getInt(), value.get("algorithmic").getInt() == 1, value.getString("desc"));
        NumberingSystem.cachedStringData.put(s, instance);
        return instance;
    }
    
    public static String[] getAvailableNames() {
        final UResourceBundle value = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "numberingSystems").get("numberingSystems");
        final ArrayList<String> list = new ArrayList<String>();
        final UResourceBundleIterator iterator = value.getIterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getKey());
        }
        return list.toArray(new String[list.size()]);
    }
    
    public int getRadix() {
        return this.radix;
    }
    
    public String getDescription() {
        return this.desc;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isAlgorithmic() {
        return this.algorithmic;
    }
    
    static {
        NumberingSystem.cachedLocaleData = new SimpleCache();
        NumberingSystem.cachedStringData = new SimpleCache();
    }
}
