package com.ibm.icu.impl;

import java.lang.ref.*;
import java.text.*;
import java.util.concurrent.*;
import com.ibm.icu.util.*;
import com.ibm.icu.text.*;
import java.util.*;
import java.io.*;

public class TimeZoneGenericNames implements Serializable, Freezable
{
    private static final long serialVersionUID = 2729910342063468417L;
    private ULocale _locale;
    private TimeZoneNames _tznames;
    private transient boolean _frozen;
    private transient String _region;
    private transient WeakReference _localeDisplayNamesRef;
    private transient MessageFormat[] _patternFormatters;
    private transient ConcurrentHashMap _genericLocationNamesMap;
    private transient ConcurrentHashMap _genericPartialLocationNamesMap;
    private transient TextTrieMap _gnamesTrie;
    private transient boolean _gnamesTrieFullyLoaded;
    private static Cache GENERIC_NAMES_CACHE;
    private static final long DST_CHECK_RANGE = 15897600000L;
    private static final TimeZoneNames.NameType[] GENERIC_NON_LOCATION_TYPES;
    static final boolean $assertionsDisabled;
    
    public TimeZoneGenericNames(final ULocale locale, final TimeZoneNames tznames) {
        this._locale = locale;
        this._tznames = tznames;
        this.init();
    }
    
    private void init() {
        if (this._tznames == null) {
            this._tznames = TimeZoneNames.getInstance(this._locale);
        }
        this._genericLocationNamesMap = new ConcurrentHashMap();
        this._genericPartialLocationNamesMap = new ConcurrentHashMap();
        this._gnamesTrie = new TextTrieMap(true);
        this._gnamesTrieFullyLoaded = false;
        final String canonicalCLDRID = ZoneMeta.getCanonicalCLDRID(TimeZone.getDefault());
        if (canonicalCLDRID != null) {
            this.loadStrings(canonicalCLDRID);
        }
    }
    
    private TimeZoneGenericNames(final ULocale uLocale) {
        this(uLocale, (TimeZoneNames)null);
    }
    
    public static TimeZoneGenericNames getInstance(final ULocale uLocale) {
        return (TimeZoneGenericNames)TimeZoneGenericNames.GENERIC_NAMES_CACHE.getInstance(uLocale.getBaseName(), uLocale);
    }
    
    public String getDisplayName(final TimeZone timeZone, final GenericNameType genericNameType, final long n) {
        String s = null;
        switch (genericNameType) {
            case LOCATION: {
                final String canonicalCLDRID = ZoneMeta.getCanonicalCLDRID(timeZone);
                if (canonicalCLDRID != null) {
                    s = this.getGenericLocationName(canonicalCLDRID);
                    break;
                }
                break;
            }
            case LONG:
            case SHORT: {
                s = this.formatGenericNonLocationName(timeZone, genericNameType, n);
                if (s != null) {
                    break;
                }
                final String canonicalCLDRID2 = ZoneMeta.getCanonicalCLDRID(timeZone);
                if (canonicalCLDRID2 != null) {
                    s = this.getGenericLocationName(canonicalCLDRID2);
                    break;
                }
                break;
            }
        }
        return s;
    }
    
    public String getGenericLocationName(String intern) {
        if (intern == null || intern.length() == 0) {
            return null;
        }
        String s = this._genericLocationNamesMap.get(intern);
        if (s == null) {
            final Output output = new Output();
            final String canonicalCountry = ZoneMeta.getCanonicalCountry(intern, output);
            if (canonicalCountry != null) {
                if (output.value) {
                    s = this.formatPattern(Pattern.REGION_FORMAT, this.getLocaleDisplayNames().regionDisplayName(canonicalCountry));
                }
                else {
                    s = this.formatPattern(Pattern.REGION_FORMAT, this._tznames.getExemplarLocationName(intern));
                }
            }
            if (s == null) {
                this._genericLocationNamesMap.putIfAbsent(intern.intern(), "");
            }
            else {
                // monitorenter(this)
                intern = intern.intern();
                final String s2 = this._genericLocationNamesMap.putIfAbsent(intern, s.intern());
                if (s2 == null) {
                    final NameInfo nameInfo = new NameInfo(null);
                    nameInfo.tzID = intern;
                    nameInfo.type = GenericNameType.LOCATION;
                    this._gnamesTrie.put(s, nameInfo);
                }
                else {
                    s = s2;
                }
            }
            // monitorexit(this)
            return s;
        }
        if (s.length() == 0) {
            return null;
        }
        return s;
    }
    
    public TimeZoneGenericNames setFormatPattern(final Pattern pattern, final String s) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (!this._genericLocationNamesMap.isEmpty()) {
            this._genericLocationNamesMap = new ConcurrentHashMap();
        }
        if (!this._genericPartialLocationNamesMap.isEmpty()) {
            this._genericPartialLocationNamesMap = new ConcurrentHashMap();
        }
        this._gnamesTrie = null;
        this._gnamesTrieFullyLoaded = false;
        if (this._patternFormatters == null) {
            this._patternFormatters = new MessageFormat[Pattern.values().length];
        }
        this._patternFormatters[pattern.ordinal()] = new MessageFormat(s);
        return this;
    }
    
    private String formatGenericNonLocationName(final TimeZone timeZone, final GenericNameType genericNameType, final long n) {
        assert genericNameType == GenericNameType.SHORT;
        final String canonicalCLDRID = ZoneMeta.getCanonicalCLDRID(timeZone);
        if (canonicalCLDRID == null) {
            return null;
        }
        final TimeZoneNames.NameType nameType = (genericNameType == GenericNameType.LONG) ? TimeZoneNames.NameType.LONG_GENERIC : TimeZoneNames.NameType.SHORT_GENERIC;
        String s = this._tznames.getTimeZoneDisplayName(canonicalCLDRID, nameType);
        if (s != null) {
            return s;
        }
        final String metaZoneID = this._tznames.getMetaZoneID(canonicalCLDRID, n);
        if (metaZoneID != null) {
            final int[] array = { 0, 0 };
            timeZone.getOffset(n, false, array);
            if (array[1] == 0) {
                if (timeZone instanceof BasicTimeZone) {
                    final BasicTimeZone basicTimeZone = (BasicTimeZone)timeZone;
                    final TimeZoneTransition previousTransition = basicTimeZone.getPreviousTransition(n, true);
                    if (previousTransition == null || n - previousTransition.getTime() >= 15897600000L || previousTransition.getFrom().getDSTSavings() == 0) {
                        final TimeZoneTransition nextTransition = basicTimeZone.getNextTransition(n, false);
                        if (nextTransition == null || nextTransition.getTime() - n >= 15897600000L || nextTransition.getTo().getDSTSavings() != 0) {}
                    }
                }
                else {
                    final int[] array2 = new int[2];
                    timeZone.getOffset(n - 15897600000L, false, array2);
                    if (array2[1] == 0) {
                        timeZone.getOffset(n + 15897600000L, false, array2);
                        if (array2[1] != 0) {}
                    }
                }
            }
            if (false) {
                final String displayName = this._tznames.getDisplayName(canonicalCLDRID, (nameType == TimeZoneNames.NameType.LONG_GENERIC) ? TimeZoneNames.NameType.LONG_STANDARD : TimeZoneNames.NameType.SHORT_STANDARD, n);
                if (displayName != null) {
                    s = displayName;
                    if (displayName.equalsIgnoreCase(this._tznames.getMetaZoneDisplayName(metaZoneID, nameType))) {
                        s = null;
                    }
                }
            }
            if (s == null) {
                final String metaZoneDisplayName = this._tznames.getMetaZoneDisplayName(metaZoneID, nameType);
                if (metaZoneDisplayName != null) {
                    final String referenceZoneID = this._tznames.getReferenceZoneID(metaZoneID, this.getTargetRegion());
                    if (referenceZoneID != null && !referenceZoneID.equals(canonicalCLDRID)) {
                        final TimeZone frozenTimeZone = TimeZone.getFrozenTimeZone(referenceZoneID);
                        final int[] array3 = { 0, 0 };
                        frozenTimeZone.getOffset(n + array[0] + array[1], true, array3);
                        if (array[0] != array3[0] || array[1] != array3[1]) {
                            s = this.getPartialLocationName(canonicalCLDRID, metaZoneID, nameType == TimeZoneNames.NameType.LONG_GENERIC, metaZoneDisplayName);
                        }
                        else {
                            s = metaZoneDisplayName;
                        }
                    }
                    else {
                        s = metaZoneDisplayName;
                    }
                }
            }
        }
        return s;
    }
    
    private synchronized String formatPattern(final Pattern pattern, final String... array) {
        if (this._patternFormatters == null) {
            this._patternFormatters = new MessageFormat[Pattern.values().length];
        }
        final int ordinal = pattern.ordinal();
        if (this._patternFormatters[ordinal] == null) {
            this._patternFormatters[ordinal] = new MessageFormat(((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b/zone", this._locale)).getStringWithFallback("zoneStrings/" + pattern.key()));
        }
        return this._patternFormatters[ordinal].format(array);
    }
    
    private synchronized LocaleDisplayNames getLocaleDisplayNames() {
        LocaleDisplayNames instance = null;
        if (this._localeDisplayNamesRef != null) {
            instance = (LocaleDisplayNames)this._localeDisplayNamesRef.get();
        }
        if (instance == null) {
            instance = LocaleDisplayNames.getInstance(this._locale);
            this._localeDisplayNamesRef = new WeakReference(instance);
        }
        return instance;
    }
    
    private synchronized void loadStrings(final String s) {
        if (s == null || s.length() == 0) {
            return;
        }
        this.getGenericLocationName(s);
        for (final String s2 : this._tznames.getAvailableMetaZoneIDs(s)) {
            if (!s.equals(this._tznames.getReferenceZoneID(s2, this.getTargetRegion()))) {
                final TimeZoneNames.NameType[] generic_NON_LOCATION_TYPES = TimeZoneGenericNames.GENERIC_NON_LOCATION_TYPES;
                while (0 < generic_NON_LOCATION_TYPES.length) {
                    final TimeZoneNames.NameType nameType = generic_NON_LOCATION_TYPES[0];
                    final String metaZoneDisplayName = this._tznames.getMetaZoneDisplayName(s2, nameType);
                    if (metaZoneDisplayName != null) {
                        this.getPartialLocationName(s, s2, nameType == TimeZoneNames.NameType.LONG_GENERIC, metaZoneDisplayName);
                    }
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    private synchronized String getTargetRegion() {
        if (this._region == null) {
            this._region = this._locale.getCountry();
            if (this._region.length() == 0) {
                this._region = ULocale.addLikelySubtags(this._locale).getCountry();
                if (this._region.length() == 0) {
                    this._region = "001";
                }
            }
        }
        return this._region;
    }
    
    private String getPartialLocationName(final String s, final String s2, final boolean b, final String s3) {
        final String string = s + "&" + s2 + "#" + (b ? "L" : "S");
        final String s4 = this._genericPartialLocationNamesMap.get(string);
        if (s4 != null) {
            return s4;
        }
        final String canonicalCountry = ZoneMeta.getCanonicalCountry(s);
        String s5;
        if (canonicalCountry != null) {
            if (s.equals(this._tznames.getReferenceZoneID(s2, canonicalCountry))) {
                s5 = this.getLocaleDisplayNames().regionDisplayName(canonicalCountry);
            }
            else {
                s5 = this._tznames.getExemplarLocationName(s);
            }
        }
        else {
            s5 = this._tznames.getExemplarLocationName(s);
            if (s5 == null) {
                s5 = s;
            }
        }
        String formatPattern = this.formatPattern(Pattern.FALLBACK_FORMAT, s5, s3);
        // monitorenter(this)
        final String s6 = this._genericPartialLocationNamesMap.putIfAbsent(string.intern(), formatPattern.intern());
        if (s6 == null) {
            final NameInfo nameInfo = new NameInfo(null);
            nameInfo.tzID = s.intern();
            nameInfo.type = (b ? GenericNameType.LONG : GenericNameType.SHORT);
            this._gnamesTrie.put(formatPattern, nameInfo);
        }
        else {
            formatPattern = s6;
        }
        // monitorexit(this)
        return formatPattern;
    }
    
    public GenericMatchInfo findBestMatch(final String s, final int n, final EnumSet set) {
        if (s == null || s.length() == 0 || n < 0 || n >= s.length()) {
            throw new IllegalArgumentException("bad input text or range");
        }
        GenericMatchInfo genericMatchInfo = null;
        final Collection timeZoneNames = this.findTimeZoneNames(s, n, set);
        if (timeZoneNames != null) {
            TimeZoneNames.MatchInfo matchInfo = null;
            for (final TimeZoneNames.MatchInfo matchInfo2 : timeZoneNames) {
                if (matchInfo == null || matchInfo2.matchLength() > matchInfo.matchLength()) {
                    matchInfo = matchInfo2;
                }
            }
            if (matchInfo != null) {
                genericMatchInfo = this.createGenericMatchInfo(matchInfo);
                if (genericMatchInfo.matchLength() == s.length() - n && genericMatchInfo.timeType != TimeZoneFormat.TimeType.STANDARD) {
                    return genericMatchInfo;
                }
            }
        }
        final Collection local = this.findLocal(s, n, set);
        if (local != null) {
            for (final GenericMatchInfo genericMatchInfo2 : local) {
                if (genericMatchInfo == null || genericMatchInfo2.matchLength() >= genericMatchInfo.matchLength()) {
                    genericMatchInfo = genericMatchInfo2;
                }
            }
        }
        return genericMatchInfo;
    }
    
    public Collection find(final String s, final int n, final EnumSet set) {
        if (s == null || s.length() == 0 || n < 0 || n >= s.length()) {
            throw new IllegalArgumentException("bad input text or range");
        }
        Collection<GenericMatchInfo> local = (Collection<GenericMatchInfo>)this.findLocal(s, n, set);
        final Collection timeZoneNames = this.findTimeZoneNames(s, n, set);
        if (timeZoneNames != null) {
            for (final TimeZoneNames.MatchInfo matchInfo : timeZoneNames) {
                if (local == null) {
                    local = new LinkedList<GenericMatchInfo>();
                }
                local.add(this.createGenericMatchInfo(matchInfo));
            }
        }
        return local;
    }
    
    private GenericMatchInfo createGenericMatchInfo(final TimeZoneNames.MatchInfo matchInfo) {
        GenericNameType nameType = null;
        TimeZoneFormat.TimeType timeType = TimeZoneFormat.TimeType.UNKNOWN;
        switch (matchInfo.nameType()) {
            case LONG_STANDARD: {
                nameType = GenericNameType.LONG;
                timeType = TimeZoneFormat.TimeType.STANDARD;
                break;
            }
            case LONG_GENERIC: {
                nameType = GenericNameType.LONG;
                break;
            }
            case SHORT_STANDARD: {
                nameType = GenericNameType.SHORT;
                timeType = TimeZoneFormat.TimeType.STANDARD;
                break;
            }
            case SHORT_GENERIC: {
                nameType = GenericNameType.SHORT;
                break;
            }
        }
        assert nameType != null;
        String tzID = matchInfo.tzID();
        if (tzID == null) {
            final String mzID = matchInfo.mzID();
            assert mzID != null;
            tzID = this._tznames.getReferenceZoneID(mzID, this.getTargetRegion());
        }
        assert tzID != null;
        final GenericMatchInfo genericMatchInfo = new GenericMatchInfo();
        genericMatchInfo.nameType = nameType;
        genericMatchInfo.tzID = tzID;
        genericMatchInfo.matchLength = matchInfo.matchLength();
        genericMatchInfo.timeType = timeType;
        return genericMatchInfo;
    }
    
    private Collection findTimeZoneNames(final String s, final int n, final EnumSet set) {
        Collection find = null;
        final EnumSet<TimeZoneNames.NameType> none = EnumSet.noneOf(TimeZoneNames.NameType.class);
        if (set.contains(GenericNameType.LONG)) {
            none.add(TimeZoneNames.NameType.LONG_GENERIC);
            none.add(TimeZoneNames.NameType.LONG_STANDARD);
        }
        if (set.contains(GenericNameType.SHORT)) {
            none.add(TimeZoneNames.NameType.SHORT_GENERIC);
            none.add(TimeZoneNames.NameType.SHORT_STANDARD);
        }
        if (!none.isEmpty()) {
            find = this._tznames.find(s, n, none);
        }
        return find;
    }
    
    private synchronized Collection findLocal(final String s, final int n, final EnumSet set) {
        final GenericNameSearchHandler genericNameSearchHandler = new GenericNameSearchHandler(set);
        this._gnamesTrie.find(s, n, genericNameSearchHandler);
        if (genericNameSearchHandler.getMaxMatchLen() == s.length() - n || this._gnamesTrieFullyLoaded) {
            return genericNameSearchHandler.getMatches();
        }
        final Iterator<String> iterator = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null).iterator();
        while (iterator.hasNext()) {
            this.loadStrings(iterator.next());
        }
        this._gnamesTrieFullyLoaded = true;
        genericNameSearchHandler.resetResults();
        this._gnamesTrie.find(s, n, genericNameSearchHandler);
        return genericNameSearchHandler.getMatches();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.init();
    }
    
    public boolean isFrozen() {
        return this._frozen;
    }
    
    public TimeZoneGenericNames freeze() {
        this._frozen = true;
        return this;
    }
    
    public TimeZoneGenericNames cloneAsThawed() {
        final TimeZoneGenericNames timeZoneGenericNames = (TimeZoneGenericNames)super.clone();
        timeZoneGenericNames._frozen = false;
        return timeZoneGenericNames;
    }
    
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    public Object freeze() {
        return this.freeze();
    }
    
    TimeZoneGenericNames(final ULocale uLocale, final TimeZoneGenericNames$1 object) {
        this(uLocale);
    }
    
    static {
        $assertionsDisabled = !TimeZoneGenericNames.class.desiredAssertionStatus();
        TimeZoneGenericNames.GENERIC_NAMES_CACHE = new Cache(null);
        GENERIC_NON_LOCATION_TYPES = new TimeZoneNames.NameType[] { TimeZoneNames.NameType.LONG_GENERIC, TimeZoneNames.NameType.SHORT_GENERIC };
    }
    
    public enum GenericNameType
    {
        LOCATION("LOCATION", 0, new String[] { "LONG", "SHORT" }), 
        LONG("LONG", 1, new String[0]), 
        SHORT("SHORT", 2, new String[0]);
        
        String[] _fallbackTypeOf;
        private static final GenericNameType[] $VALUES;
        
        private GenericNameType(final String s, final int n, final String... fallbackTypeOf) {
            this._fallbackTypeOf = fallbackTypeOf;
        }
        
        public boolean isFallbackTypeOf(final GenericNameType genericNameType) {
            final String string = genericNameType.toString();
            final String[] fallbackTypeOf = this._fallbackTypeOf;
            while (0 < fallbackTypeOf.length) {
                if (fallbackTypeOf[0].equals(string)) {
                    return true;
                }
                int n = 0;
                ++n;
            }
            return false;
        }
        
        static {
            $VALUES = new GenericNameType[] { GenericNameType.LOCATION, GenericNameType.LONG, GenericNameType.SHORT };
        }
    }
    
    private static class Cache extends SoftCache
    {
        private Cache() {
        }
        
        protected TimeZoneGenericNames createInstance(final String s, final ULocale uLocale) {
            return new TimeZoneGenericNames(uLocale, (TimeZoneGenericNames$1)null).freeze();
        }
        
        @Override
        protected Object createInstance(final Object o, final Object o2) {
            return this.createInstance((String)o, (ULocale)o2);
        }
        
        Cache(final TimeZoneGenericNames$1 object) {
            this();
        }
    }
    
    private static class GenericNameSearchHandler implements TextTrieMap.ResultHandler
    {
        private EnumSet _types;
        private Collection _matches;
        private int _maxMatchLen;
        
        GenericNameSearchHandler(final EnumSet types) {
            this._types = types;
        }
        
        public boolean handlePrefixMatch(final int n, final Iterator iterator) {
            while (iterator.hasNext()) {
                final NameInfo nameInfo = iterator.next();
                if (this._types != null && !this._types.contains(nameInfo.type)) {
                    continue;
                }
                final GenericMatchInfo genericMatchInfo = new GenericMatchInfo();
                genericMatchInfo.tzID = nameInfo.tzID;
                genericMatchInfo.nameType = nameInfo.type;
                genericMatchInfo.matchLength = n;
                if (this._matches == null) {
                    this._matches = new LinkedList();
                }
                this._matches.add(genericMatchInfo);
                if (n <= this._maxMatchLen) {
                    continue;
                }
                this._maxMatchLen = n;
            }
            return true;
        }
        
        public Collection getMatches() {
            return this._matches;
        }
        
        public int getMaxMatchLen() {
            return this._maxMatchLen;
        }
        
        public void resetResults() {
            this._matches = null;
            this._maxMatchLen = 0;
        }
    }
    
    public static class GenericMatchInfo
    {
        GenericNameType nameType;
        String tzID;
        int matchLength;
        TimeZoneFormat.TimeType timeType;
        
        public GenericMatchInfo() {
            this.timeType = TimeZoneFormat.TimeType.UNKNOWN;
        }
        
        public GenericNameType nameType() {
            return this.nameType;
        }
        
        public String tzID() {
            return this.tzID;
        }
        
        public TimeZoneFormat.TimeType timeType() {
            return this.timeType;
        }
        
        public int matchLength() {
            return this.matchLength;
        }
    }
    
    private static class NameInfo
    {
        String tzID;
        GenericNameType type;
        
        private NameInfo() {
        }
        
        NameInfo(final TimeZoneGenericNames$1 object) {
            this();
        }
    }
    
    public enum Pattern
    {
        REGION_FORMAT("REGION_FORMAT", 0, "regionFormat", "({0})"), 
        FALLBACK_FORMAT("FALLBACK_FORMAT", 1, "fallbackFormat", "{1} ({0})");
        
        String _key;
        String _defaultVal;
        private static final Pattern[] $VALUES;
        
        private Pattern(final String s, final int n, final String key, final String defaultVal) {
            this._key = key;
            this._defaultVal = defaultVal;
        }
        
        String key() {
            return this._key;
        }
        
        String defaultValue() {
            return this._defaultVal;
        }
        
        static {
            $VALUES = new Pattern[] { Pattern.REGION_FORMAT, Pattern.FALLBACK_FORMAT };
        }
    }
}
