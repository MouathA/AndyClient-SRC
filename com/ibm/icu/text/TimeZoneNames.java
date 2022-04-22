package com.ibm.icu.text;

import java.io.*;
import com.ibm.icu.util.*;
import java.util.*;
import com.ibm.icu.impl.*;

public abstract class TimeZoneNames implements Serializable
{
    private static final long serialVersionUID = -9180227029248969153L;
    private static Cache TZNAMES_CACHE;
    private static final Factory TZNAMES_FACTORY;
    private static final String FACTORY_NAME_PROP = "com.ibm.icu.text.TimeZoneNames.Factory.impl";
    private static final String DEFAULT_FACTORY_CLASS = "com.ibm.icu.impl.TimeZoneNamesFactoryImpl";
    
    public static TimeZoneNames getInstance(final ULocale uLocale) {
        return (TimeZoneNames)TimeZoneNames.TZNAMES_CACHE.getInstance(uLocale.getBaseName(), uLocale);
    }
    
    public abstract Set getAvailableMetaZoneIDs();
    
    public abstract Set getAvailableMetaZoneIDs(final String p0);
    
    public abstract String getMetaZoneID(final String p0, final long p1);
    
    public abstract String getReferenceZoneID(final String p0, final String p1);
    
    public abstract String getMetaZoneDisplayName(final String p0, final NameType p1);
    
    public final String getDisplayName(final String s, final NameType nameType, final long n) {
        String s2 = this.getTimeZoneDisplayName(s, nameType);
        if (s2 == null) {
            s2 = this.getMetaZoneDisplayName(this.getMetaZoneID(s, n), nameType);
        }
        return s2;
    }
    
    public abstract String getTimeZoneDisplayName(final String p0, final NameType p1);
    
    public String getExemplarLocationName(final String s) {
        return TimeZoneNamesImpl.getDefaultExemplarLocationName(s);
    }
    
    public Collection find(final CharSequence charSequence, final int n, final EnumSet set) {
        throw new UnsupportedOperationException("The method is not implemented in TimeZoneNames base class.");
    }
    
    protected TimeZoneNames() {
    }
    
    static Factory access$100() {
        return TimeZoneNames.TZNAMES_FACTORY;
    }
    
    static {
        TimeZoneNames.TZNAMES_CACHE = new Cache(null);
        Factory tznames_FACTORY = (Factory)Class.forName(ICUConfig.get("com.ibm.icu.text.TimeZoneNames.Factory.impl", "com.ibm.icu.impl.TimeZoneNamesFactoryImpl")).newInstance();
        if (tznames_FACTORY == null) {
            tznames_FACTORY = new DefaultTimeZoneNames.FactoryImpl();
        }
        TZNAMES_FACTORY = tznames_FACTORY;
    }
    
    private static class DefaultTimeZoneNames extends TimeZoneNames
    {
        private static final long serialVersionUID = -995672072494349071L;
        public static final DefaultTimeZoneNames INSTANCE;
        
        @Override
        public Set getAvailableMetaZoneIDs() {
            return Collections.emptySet();
        }
        
        @Override
        public Set getAvailableMetaZoneIDs(final String s) {
            return Collections.emptySet();
        }
        
        @Override
        public String getMetaZoneID(final String s, final long n) {
            return null;
        }
        
        @Override
        public String getReferenceZoneID(final String s, final String s2) {
            return null;
        }
        
        @Override
        public String getMetaZoneDisplayName(final String s, final NameType nameType) {
            return null;
        }
        
        @Override
        public String getTimeZoneDisplayName(final String s, final NameType nameType) {
            return null;
        }
        
        @Override
        public Collection find(final CharSequence charSequence, final int n, final EnumSet set) {
            return Collections.emptyList();
        }
        
        static {
            INSTANCE = new DefaultTimeZoneNames();
        }
        
        public static class FactoryImpl extends Factory
        {
            @Override
            public TimeZoneNames getTimeZoneNames(final ULocale uLocale) {
                return DefaultTimeZoneNames.INSTANCE;
            }
        }
    }
    
    public abstract static class Factory
    {
        public abstract TimeZoneNames getTimeZoneNames(final ULocale p0);
    }
    
    public enum NameType
    {
        LONG_GENERIC("LONG_GENERIC", 0), 
        LONG_STANDARD("LONG_STANDARD", 1), 
        LONG_DAYLIGHT("LONG_DAYLIGHT", 2), 
        SHORT_GENERIC("SHORT_GENERIC", 3), 
        SHORT_STANDARD("SHORT_STANDARD", 4), 
        SHORT_DAYLIGHT("SHORT_DAYLIGHT", 5), 
        EXEMPLAR_LOCATION("EXEMPLAR_LOCATION", 6);
        
        private static final NameType[] $VALUES;
        
        private NameType(final String s, final int n) {
        }
        
        static {
            $VALUES = new NameType[] { NameType.LONG_GENERIC, NameType.LONG_STANDARD, NameType.LONG_DAYLIGHT, NameType.SHORT_GENERIC, NameType.SHORT_STANDARD, NameType.SHORT_DAYLIGHT, NameType.EXEMPLAR_LOCATION };
        }
    }
    
    public static class MatchInfo
    {
        private NameType _nameType;
        private String _tzID;
        private String _mzID;
        private int _matchLength;
        
        public MatchInfo(final NameType nameType, final String tzID, final String mzID, final int matchLength) {
            if (nameType == null) {
                throw new IllegalArgumentException("nameType is null");
            }
            if (tzID == null && mzID == null) {
                throw new IllegalArgumentException("Either tzID or mzID must be available");
            }
            if (matchLength <= 0) {
                throw new IllegalArgumentException("matchLength must be positive value");
            }
            this._nameType = nameType;
            this._tzID = tzID;
            this._mzID = mzID;
            this._matchLength = matchLength;
        }
        
        public String tzID() {
            return this._tzID;
        }
        
        public String mzID() {
            return this._mzID;
        }
        
        public NameType nameType() {
            return this._nameType;
        }
        
        public int matchLength() {
            return this._matchLength;
        }
    }
    
    private static class Cache extends SoftCache
    {
        private Cache() {
        }
        
        protected TimeZoneNames createInstance(final String s, final ULocale uLocale) {
            return TimeZoneNames.access$100().getTimeZoneNames(uLocale);
        }
        
        @Override
        protected Object createInstance(final Object o, final Object o2) {
            return this.createInstance((String)o, (ULocale)o2);
        }
        
        Cache(final TimeZoneNames$1 object) {
            this();
        }
    }
}
