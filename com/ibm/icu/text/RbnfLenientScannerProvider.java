package com.ibm.icu.text;

import com.ibm.icu.util.*;

public interface RbnfLenientScannerProvider
{
    RbnfLenientScanner get(final ULocale p0, final String p1);
}
