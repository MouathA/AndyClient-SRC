package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import com.ibm.icu.util.*;

public class TimeZoneNamesFactoryImpl extends TimeZoneNames.Factory
{
    @Override
    public TimeZoneNames getTimeZoneNames(final ULocale uLocale) {
        return new TimeZoneNamesImpl(uLocale);
    }
}
