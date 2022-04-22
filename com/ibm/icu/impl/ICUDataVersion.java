package com.ibm.icu.impl;

import com.ibm.icu.util.*;

public final class ICUDataVersion
{
    private static final String U_ICU_VERSION_BUNDLE = "icuver";
    private static final String U_ICU_DATA_KEY = "DataVersion";
    
    public static VersionInfo getDataVersion() {
        return VersionInfo.getInstance(UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "icuver", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("DataVersion").getString());
    }
}
