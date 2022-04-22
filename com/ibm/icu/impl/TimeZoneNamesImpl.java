package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import java.util.concurrent.*;
import java.util.regex.*;
import com.ibm.icu.util.*;
import java.io.*;
import java.util.*;

public class TimeZoneNamesImpl extends TimeZoneNames
{
    private static final long serialVersionUID = -2179814848495897472L;
    private static final String ZONE_STRINGS_BUNDLE = "zoneStrings";
    private static final String MZ_PREFIX = "meta:";
    private static Set METAZONE_IDS;
    private static final TZ2MZsCache TZ_TO_MZS_CACHE;
    private static final MZ2TZsCache MZ_TO_TZS_CACHE;
    private transient ICUResourceBundle _zoneStrings;
    private transient ConcurrentHashMap _mzNamesMap;
    private transient ConcurrentHashMap _tzNamesMap;
    private transient TextTrieMap _namesTrie;
    private transient boolean _namesTrieFullyLoaded;
    private static final Pattern LOC_EXCLUSION_PATTERN;
    
    public TimeZoneNamesImpl(final ULocale uLocale) {
        this.initialize(uLocale);
    }
    
    @Override
    public synchronized Set getAvailableMetaZoneIDs() {
        if (TimeZoneNamesImpl.METAZONE_IDS == null) {
            TimeZoneNamesImpl.METAZONE_IDS = Collections.unmodifiableSet((Set<?>)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "metaZones").get("mapTimezones").keySet());
        }
        return TimeZoneNamesImpl.METAZONE_IDS;
    }
    
    @Override
    public Set getAvailableMetaZoneIDs(final String s) {
        if (s == null || s.length() == 0) {
            return Collections.emptySet();
        }
        final List list = (List)TimeZoneNamesImpl.TZ_TO_MZS_CACHE.getInstance(s, s);
        if (list.isEmpty()) {
            return Collections.emptySet();
        }
        final HashSet set = new HashSet<String>(list.size());
        final Iterator<MZMapEntry> iterator = list.iterator();
        while (iterator.hasNext()) {
            set.add(iterator.next().mzID());
        }
        return Collections.unmodifiableSet((Set<?>)set);
    }
    
    @Override
    public String getMetaZoneID(final String s, final long n) {
        if (s == null || s.length() == 0) {
            return null;
        }
        String mzID = null;
        for (final MZMapEntry mzMapEntry : (List)TimeZoneNamesImpl.TZ_TO_MZS_CACHE.getInstance(s, s)) {
            if (n >= mzMapEntry.from() && n < mzMapEntry.to()) {
                mzID = mzMapEntry.mzID();
                break;
            }
        }
        return mzID;
    }
    
    @Override
    public String getReferenceZoneID(final String s, final String s2) {
        if (s == null || s.length() == 0) {
            return null;
        }
        String s3 = null;
        final Map map = (Map)TimeZoneNamesImpl.MZ_TO_TZS_CACHE.getInstance(s, s);
        if (!map.isEmpty()) {
            s3 = map.get(s2);
            if (s3 == null) {
                s3 = map.get("001");
            }
        }
        return s3;
    }
    
    @Override
    public String getMetaZoneDisplayName(final String s, final NameType nameType) {
        if (s == null || s.length() == 0) {
            return null;
        }
        return this.loadMetaZoneNames(s).getName(nameType);
    }
    
    @Override
    public String getTimeZoneDisplayName(final String s, final NameType nameType) {
        if (s == null || s.length() == 0) {
            return null;
        }
        return this.loadTimeZoneNames(s).getName(nameType);
    }
    
    @Override
    public String getExemplarLocationName(final String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        return this.loadTimeZoneNames(s).getName(NameType.EXEMPLAR_LOCATION);
    }
    
    @Override
    public synchronized Collection find(final CharSequence charSequence, final int n, final EnumSet set) {
        if (charSequence == null || charSequence.length() == 0 || n < 0 || n >= charSequence.length()) {
            throw new IllegalArgumentException("bad input text or range");
        }
        final NameSearchHandler nameSearchHandler = new NameSearchHandler(set);
        this._namesTrie.find(charSequence, n, nameSearchHandler);
        if (nameSearchHandler.getMaxMatchLen() == charSequence.length() - n || this._namesTrieFullyLoaded) {
            return nameSearchHandler.getMatches();
        }
        final Iterator<String> iterator = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null).iterator();
        while (iterator.hasNext()) {
            this.loadTimeZoneNames(iterator.next());
        }
        final Iterator<String> iterator2 = this.getAvailableMetaZoneIDs().iterator();
        while (iterator2.hasNext()) {
            this.loadMetaZoneNames(iterator2.next());
        }
        this._namesTrieFullyLoaded = true;
        nameSearchHandler.resetResults();
        this._namesTrie.find(charSequence, n, nameSearchHandler);
        return nameSearchHandler.getMatches();
    }
    
    private void initialize(final ULocale uLocale) {
        this._zoneStrings = (ICUResourceBundle)((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b/zone", uLocale)).get("zoneStrings");
        this._tzNamesMap = new ConcurrentHashMap();
        this._mzNamesMap = new ConcurrentHashMap();
        this._namesTrie = new TextTrieMap(true);
        this._namesTrieFullyLoaded = false;
        final String canonicalCLDRID = ZoneMeta.getCanonicalCLDRID(TimeZone.getDefault());
        if (canonicalCLDRID != null) {
            this.loadStrings(canonicalCLDRID);
        }
    }
    
    private synchronized void loadStrings(final String s) {
        if (s == null || s.length() == 0) {
            return;
        }
        this.loadTimeZoneNames(s);
        final Iterator<String> iterator = this.getAvailableMetaZoneIDs(s).iterator();
        while (iterator.hasNext()) {
            this.loadMetaZoneNames(iterator.next());
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this._zoneStrings.getULocale());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.initialize((ULocale)objectInputStream.readObject());
    }
    
    private synchronized ZNames loadMetaZoneNames(String intern) {
        ZNames zNames = this._mzNamesMap.get(intern);
        if (zNames == null) {
            final ZNames instance = ZNames.getInstance(this._zoneStrings, "meta:" + intern);
            intern = intern.intern();
            final NameType[] values = NameType.values();
            while (0 < values.length) {
                final NameType type = values[0];
                final String name = instance.getName(type);
                if (name != null) {
                    final NameInfo nameInfo = new NameInfo(null);
                    nameInfo.mzID = intern;
                    nameInfo.type = type;
                    this._namesTrie.put(name, nameInfo);
                }
                int n = 0;
                ++n;
            }
            final ZNames zNames2 = this._mzNamesMap.putIfAbsent(intern, instance);
            zNames = ((zNames2 == null) ? instance : zNames2);
        }
        return zNames;
    }
    
    private synchronized TZNames loadTimeZoneNames(String intern) {
        TZNames tzNames = this._tzNamesMap.get(intern);
        if (tzNames == null) {
            final TZNames instance = TZNames.getInstance(this._zoneStrings, intern.replace('/', ':'), intern);
            intern = intern.intern();
            final NameType[] values = NameType.values();
            while (0 < values.length) {
                final NameType type = values[0];
                final String name = instance.getName(type);
                if (name != null) {
                    final NameInfo nameInfo = new NameInfo(null);
                    nameInfo.tzID = intern;
                    nameInfo.type = type;
                    this._namesTrie.put(name, nameInfo);
                }
                int n = 0;
                ++n;
            }
            final TZNames tzNames2 = this._tzNamesMap.putIfAbsent(intern, instance);
            tzNames = ((tzNames2 == null) ? instance : tzNames2);
        }
        return tzNames;
    }
    
    public static String getDefaultExemplarLocationName(final String s) {
        if (s == null || s.length() == 0 || TimeZoneNamesImpl.LOC_EXCLUSION_PATTERN.matcher(s).matches()) {
            return null;
        }
        String replace = null;
        final int lastIndex = s.lastIndexOf(47);
        if (lastIndex > 0 && lastIndex + 1 < s.length()) {
            replace = s.substring(lastIndex + 1).replace('_', ' ');
        }
        return replace;
    }
    
    static {
        TZ_TO_MZS_CACHE = new TZ2MZsCache(null);
        MZ_TO_TZS_CACHE = new MZ2TZsCache(null);
        LOC_EXCLUSION_PATTERN = Pattern.compile("Etc/.*|SystemV/.*|.*/Riyadh8[7-9]");
    }
    
    private static class MZ2TZsCache extends SoftCache
    {
        private MZ2TZsCache() {
        }
        
        protected Map createInstance(final String s, final String s2) {
            final UResourceBundle value = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "metaZones").get("mapTimezones").get(s);
            final Set keySet = value.keySet();
            final HashMap hashMap = new HashMap<String, String>(keySet.size());
            for (final String s3 : keySet) {
                hashMap.put(s3.intern(), value.getString(s3).intern());
            }
            return hashMap;
        }
        
        @Override
        protected Object createInstance(final Object o, final Object o2) {
            return this.createInstance((String)o, (String)o2);
        }
        
        MZ2TZsCache(final TimeZoneNamesImpl$1 object) {
            this();
        }
    }
    
    private static class TZ2MZsCache extends SoftCache
    {
        private TZ2MZsCache() {
        }
        
        protected List createInstance(final String s, final String s2) {
            final UResourceBundle value = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "metaZones").get("metazoneInfo").get(s2.replace('/', ':'));
            final ArrayList list = new ArrayList<MZMapEntry>(value.getSize());
            while (0 < value.getSize()) {
                final UResourceBundle value2 = value.get(0);
                final String string = value2.getString(0);
                String string2 = "1970-01-01 00:00";
                String string3 = "9999-12-31 23:59";
                if (value2.getSize() == 3) {
                    string2 = value2.getString(1);
                    string3 = value2.getString(2);
                }
                list.add(new MZMapEntry(string, parseDate(string2), parseDate(string3)));
                int n = 0;
                ++n;
            }
            return list;
        }
        
        private static long parseDate(final String s) {
            int n2 = 0;
            while (14 <= 3) {
                final int n = s.charAt(14) - '0';
                if (n < 0 || n >= 10) {
                    throw new IllegalArgumentException("Bad year");
                }
                ++n2;
            }
            while (14 <= 6) {
                final int n3 = s.charAt(14) - '0';
                if (n3 < 0 || n3 >= 10) {
                    throw new IllegalArgumentException("Bad month");
                }
                ++n2;
            }
            while (14 <= 9) {
                final int n4 = s.charAt(14) - '0';
                if (n4 < 0 || n4 >= 10) {
                    throw new IllegalArgumentException("Bad day");
                }
                ++n2;
            }
            while (14 <= 12) {
                final int n5 = s.charAt(14) - '0';
                if (n5 < 0 || n5 >= 10) {
                    throw new IllegalArgumentException("Bad hour");
                }
                ++n2;
            }
            while (14 <= 15) {
                final int n6 = s.charAt(14) - '0';
                if (n6 < 0 || n6 >= 10) {
                    throw new IllegalArgumentException("Bad minute");
                }
                ++n2;
            }
            return Grego.fieldsToDay(0, -1, 0) * 86400000L + 0 * 3600000L + 0 * 60000L;
        }
        
        @Override
        protected Object createInstance(final Object o, final Object o2) {
            return this.createInstance((String)o, (String)o2);
        }
        
        TZ2MZsCache(final TimeZoneNamesImpl$1 object) {
            this();
        }
    }
    
    private static class MZMapEntry
    {
        private String _mzID;
        private long _from;
        private long _to;
        
        MZMapEntry(final String mzID, final long from, final long to) {
            this._mzID = mzID;
            this._from = from;
            this._to = to;
        }
        
        String mzID() {
            return this._mzID;
        }
        
        long from() {
            return this._from;
        }
        
        long to() {
            return this._to;
        }
    }
    
    private static class TZNames extends ZNames
    {
        private String _locationName;
        private static final TZNames EMPTY_TZNAMES;
        
        public static TZNames getInstance(final ICUResourceBundle icuResourceBundle, final String s, final String s2) {
            if (icuResourceBundle == null || s == null || s.length() == 0) {
                return TZNames.EMPTY_TZNAMES;
            }
            final String[] loadData = ZNames.loadData(icuResourceBundle, s);
            String s3 = icuResourceBundle.getWithFallback(s).getStringWithFallback("ec");
            if (s3 == null) {
                s3 = TimeZoneNamesImpl.getDefaultExemplarLocationName(s2);
            }
            if (s3 == null && loadData == null) {
                return TZNames.EMPTY_TZNAMES;
            }
            return new TZNames(loadData, s3);
        }
        
        @Override
        public String getName(final NameType nameType) {
            if (nameType == NameType.EXEMPLAR_LOCATION) {
                return this._locationName;
            }
            return super.getName(nameType);
        }
        
        private TZNames(final String[] array, final String locationName) {
            super(array);
            this._locationName = locationName;
        }
        
        static {
            EMPTY_TZNAMES = new TZNames(null, null);
        }
    }
    
    private static class ZNames
    {
        private static final ZNames EMPTY_ZNAMES;
        private String[] _names;
        
        protected ZNames(final String[] names) {
            this._names = names;
        }
        
        public static ZNames getInstance(final ICUResourceBundle icuResourceBundle, final String s) {
            final String[] loadData = loadData(icuResourceBundle, s);
            if (loadData == null) {
                return ZNames.EMPTY_ZNAMES;
            }
            return new ZNames(loadData);
        }
        
        public String getName(final NameType nameType) {
            if (this._names == null) {
                return null;
            }
            String s = null;
            switch (nameType) {
                case LONG_GENERIC: {
                    s = this._names[0];
                    break;
                }
                case LONG_STANDARD: {
                    s = this._names[1];
                    break;
                }
                case LONG_DAYLIGHT: {
                    s = this._names[2];
                    break;
                }
                case SHORT_GENERIC: {
                    s = this._names[3];
                    break;
                }
                case SHORT_STANDARD: {
                    s = this._names[4];
                    break;
                }
                case SHORT_DAYLIGHT: {
                    s = this._names[5];
                    break;
                }
                case EXEMPLAR_LOCATION: {
                    s = null;
                    break;
                }
            }
            return s;
        }
        
        protected static String[] loadData(final ICUResourceBundle icuResourceBundle, final String s) {
            if (icuResourceBundle == null || s == null || s.length() == 0) {
                return null;
            }
            final ICUResourceBundle withFallback = icuResourceBundle.getWithFallback(s);
            final String[] array = new String[ZNames.KEYS.length];
            while (0 < array.length) {
                array[0] = withFallback.getStringWithFallback(ZNames.KEYS[0]);
                int n = 0;
                ++n;
            }
            if (false) {
                return null;
            }
            return array;
        }
        
        static {
            EMPTY_ZNAMES = new ZNames(null);
            ZNames.KEYS = new String[] { "lg", "ls", "ld", "sg", "ss", "sd" };
        }
    }
    
    private static class NameSearchHandler implements TextTrieMap.ResultHandler
    {
        private EnumSet _nameTypes;
        private Collection _matches;
        private int _maxMatchLen;
        static final boolean $assertionsDisabled;
        
        NameSearchHandler(final EnumSet nameTypes) {
            this._nameTypes = nameTypes;
        }
        
        public boolean handlePrefixMatch(final int maxMatchLen, final Iterator iterator) {
            while (iterator.hasNext()) {
                final NameInfo nameInfo = iterator.next();
                if (this._nameTypes != null && !this._nameTypes.contains(nameInfo.type)) {
                    continue;
                }
                MatchInfo matchInfo;
                if (nameInfo.tzID != null) {
                    matchInfo = new MatchInfo(nameInfo.type, nameInfo.tzID, null, maxMatchLen);
                }
                else {
                    assert nameInfo.mzID != null;
                    matchInfo = new MatchInfo(nameInfo.type, null, nameInfo.mzID, maxMatchLen);
                }
                if (this._matches == null) {
                    this._matches = new LinkedList();
                }
                this._matches.add(matchInfo);
                if (maxMatchLen <= this._maxMatchLen) {
                    continue;
                }
                this._maxMatchLen = maxMatchLen;
            }
            return true;
        }
        
        public Collection getMatches() {
            if (this._matches == null) {
                return Collections.emptyList();
            }
            return this._matches;
        }
        
        public int getMaxMatchLen() {
            return this._maxMatchLen;
        }
        
        public void resetResults() {
            this._matches = null;
            this._maxMatchLen = 0;
        }
        
        static {
            $assertionsDisabled = !TimeZoneNamesImpl.class.desiredAssertionStatus();
        }
    }
    
    private static class NameInfo
    {
        String tzID;
        String mzID;
        NameType type;
        
        private NameInfo() {
        }
        
        NameInfo(final TimeZoneNamesImpl$1 object) {
            this();
        }
    }
}
