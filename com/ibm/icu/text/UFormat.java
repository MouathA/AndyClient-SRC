package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.util.*;

public abstract class UFormat extends Format
{
    private static final long serialVersionUID = -4964390515840164416L;
    private ULocale validLocale;
    private ULocale actualLocale;
    
    public final ULocale getLocale(final ULocale.Type type) {
        return (type == ULocale.ACTUAL_LOCALE) ? this.actualLocale : this.validLocale;
    }
    
    final void setLocale(final ULocale validLocale, final ULocale actualLocale) {
        if (validLocale == null != (actualLocale == null)) {
            throw new IllegalArgumentException();
        }
        this.validLocale = validLocale;
        this.actualLocale = actualLocale;
    }
}
