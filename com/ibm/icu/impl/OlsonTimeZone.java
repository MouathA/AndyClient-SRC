package com.ibm.icu.impl;

import java.util.*;
import com.ibm.icu.util.*;
import java.io.*;

public class OlsonTimeZone extends BasicTimeZone
{
    static final long serialVersionUID = -6281977362477515376L;
    private int transitionCount;
    private int typeCount;
    private long[] transitionTimes64;
    private int[] typeOffsets;
    private byte[] typeMapData;
    private int finalStartYear;
    private double finalStartMillis;
    private SimpleTimeZone finalZone;
    private String canonicalID;
    private static final String ZONEINFORES = "zoneinfo64";
    private static final boolean DEBUG;
    private static final int SECONDS_PER_DAY = 86400;
    private transient InitialTimeZoneRule initialRule;
    private transient TimeZoneTransition firstTZTransition;
    private transient int firstTZTransitionIdx;
    private transient TimeZoneTransition firstFinalTZTransition;
    private transient TimeArrayTimeZoneRule[] historicRules;
    private transient SimpleTimeZone finalZoneWithStartYear;
    private transient boolean transitionRulesInitialized;
    private static final int currentSerialVersion = 1;
    private int serialVersionOnStream;
    private transient boolean isFrozen;
    static final boolean $assertionsDisabled;
    
    @Override
    public int getOffset(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n3 < 0 || n3 > 11) {
            throw new IllegalArgumentException("Month is not in the legal range: " + n3);
        }
        return this.getOffset(n, n2, n3, n4, n5, n6, Grego.monthLength(n2, n3));
    }
    
    public int getOffset(final int n, int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        if ((n != 1 && n != 0) || n3 < 0 || n3 > 11 || n4 < 1 || n4 > n7 || n5 < 1 || n5 > 7 || n6 < 0 || n6 >= 86400000 || n7 < 28 || n7 > 31) {
            throw new IllegalArgumentException();
        }
        if (n == 0) {
            n2 = -n2;
        }
        if (this.finalZone != null && n2 >= this.finalStartYear) {
            return this.finalZone.getOffset(n, n2, n3, n4, n5, n6);
        }
        final long n8 = Grego.fieldsToDay(n2, n3, n4) * 86400000L + n6;
        final int[] array = new int[2];
        this.getHistoricalOffset(n8, true, 3, 1, array);
        return array[0] + array[1];
    }
    
    @Override
    public void setRawOffset(final int rawOffset) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen OlsonTimeZone instance.");
        }
        if (this.getRawOffset() == rawOffset) {
            return;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis < this.finalStartMillis) {
            final SimpleTimeZone finalZone = new SimpleTimeZone(rawOffset, this.getID());
            final boolean useDaylightTime = this.useDaylightTime();
            if (useDaylightTime) {
                TimeZoneRule[] array = this.getSimpleTimeZoneRulesNear(currentTimeMillis);
                if (array.length != 3) {
                    final TimeZoneTransition previousTransition = this.getPreviousTransition(currentTimeMillis, false);
                    if (previousTransition != null) {
                        array = this.getSimpleTimeZoneRulesNear(previousTransition.getTime() - 1L);
                    }
                }
                if (array.length == 3 && array[1] instanceof AnnualTimeZoneRule && array[2] instanceof AnnualTimeZoneRule) {
                    final AnnualTimeZoneRule annualTimeZoneRule = (AnnualTimeZoneRule)array[1];
                    final AnnualTimeZoneRule annualTimeZoneRule2 = (AnnualTimeZoneRule)array[2];
                    final int n = annualTimeZoneRule.getRawOffset() + annualTimeZoneRule.getDSTSavings();
                    final int n2 = annualTimeZoneRule2.getRawOffset() + annualTimeZoneRule2.getDSTSavings();
                    DateTimeRule dateTimeRule;
                    DateTimeRule dateTimeRule2;
                    int dstSavings;
                    if (n > n2) {
                        dateTimeRule = annualTimeZoneRule.getRule();
                        dateTimeRule2 = annualTimeZoneRule2.getRule();
                        dstSavings = n - n2;
                    }
                    else {
                        dateTimeRule = annualTimeZoneRule2.getRule();
                        dateTimeRule2 = annualTimeZoneRule.getRule();
                        dstSavings = n2 - n;
                    }
                    finalZone.setStartRule(dateTimeRule.getRuleMonth(), dateTimeRule.getRuleWeekInMonth(), dateTimeRule.getRuleDayOfWeek(), dateTimeRule.getRuleMillisInDay());
                    finalZone.setEndRule(dateTimeRule2.getRuleMonth(), dateTimeRule2.getRuleWeekInMonth(), dateTimeRule2.getRuleDayOfWeek(), dateTimeRule2.getRuleMillisInDay());
                    finalZone.setDSTSavings(dstSavings);
                }
                else {
                    finalZone.setStartRule(0, 1, 0);
                    finalZone.setEndRule(11, 31, 86399999);
                }
            }
            final int[] timeToFields = Grego.timeToFields(currentTimeMillis, null);
            this.finalStartYear = timeToFields[0];
            this.finalStartMillis = (double)Grego.fieldsToDay(timeToFields[0], 0, 1);
            if (useDaylightTime) {
                finalZone.setStartYear(this.finalStartYear);
            }
            this.finalZone = finalZone;
        }
        else {
            this.finalZone.setRawOffset(rawOffset);
        }
        this.transitionRulesInitialized = false;
    }
    
    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }
    
    @Override
    public void getOffset(final long n, final boolean b, final int[] array) {
        if (this.finalZone != null && n >= this.finalStartMillis) {
            this.finalZone.getOffset(n, b, array);
        }
        else {
            this.getHistoricalOffset(n, b, 4, 12, array);
        }
    }
    
    @Override
    @Deprecated
    public void getOffsetFromLocal(final long n, final int n2, final int n3, final int[] array) {
        if (this.finalZone != null && n >= this.finalStartMillis) {
            this.finalZone.getOffsetFromLocal(n, n2, n3, array);
        }
        else {
            this.getHistoricalOffset(n, true, n2, n3, array);
        }
    }
    
    @Override
    public int getRawOffset() {
        final int[] array = new int[2];
        this.getOffset(System.currentTimeMillis(), false, array);
        return array[0];
    }
    
    @Override
    public boolean useDaylightTime() {
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.finalZone != null && currentTimeMillis >= this.finalStartMillis) {
            return this.finalZone != null && this.finalZone.useDaylightTime();
        }
        final int[] timeToFields = Grego.timeToFields(currentTimeMillis, null);
        final long n = Grego.fieldsToDay(timeToFields[0], 0, 1) * 86400L;
        final long n2 = Grego.fieldsToDay(timeToFields[0] + 1, 0, 1) * 86400L;
        while (0 < this.transitionCount && this.transitionTimes64[0] < n2) {
            if ((this.transitionTimes64[0] >= n && this.dstOffsetAt(0) != 0) || (this.transitionTimes64[0] > n && 0 > 0 && this.dstOffsetAt(-1) != 0)) {
                return true;
            }
            int n3 = 0;
            ++n3;
        }
        return false;
    }
    
    @Override
    public boolean observesDaylightTime() {
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.finalZone != null) {
            if (this.finalZone.useDaylightTime()) {
                return true;
            }
            if (currentTimeMillis >= this.finalStartMillis) {
                return false;
            }
        }
        final long floorDivide = Grego.floorDivide(currentTimeMillis, 1000L);
        final int n = this.transitionCount - 1;
        if (this.dstOffsetAt(n) != 0) {
            return true;
        }
        while (n >= 0 && this.transitionTimes64[n] > floorDivide) {
            if (this.dstOffsetAt(n - 1) != 0) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int getDSTSavings() {
        if (this.finalZone != null) {
            return this.finalZone.getDSTSavings();
        }
        return super.getDSTSavings();
    }
    
    @Override
    public boolean inDaylightTime(final Date date) {
        final int[] array = new int[2];
        this.getOffset(date.getTime(), false, array);
        return array[1] != 0;
    }
    
    @Override
    public boolean hasSameRules(final TimeZone timeZone) {
        if (this == timeZone) {
            return true;
        }
        if (!super.hasSameRules(timeZone)) {
            return false;
        }
        if (!(timeZone instanceof OlsonTimeZone)) {
            return false;
        }
        final OlsonTimeZone olsonTimeZone = (OlsonTimeZone)timeZone;
        if (this.finalZone == null) {
            if (olsonTimeZone.finalZone != null) {
                return false;
            }
        }
        else if (olsonTimeZone.finalZone == null || this.finalStartYear != olsonTimeZone.finalStartYear || !this.finalZone.hasSameRules(olsonTimeZone.finalZone)) {
            return false;
        }
        return this.transitionCount == olsonTimeZone.transitionCount && Arrays.equals(this.transitionTimes64, olsonTimeZone.transitionTimes64) && this.typeCount == olsonTimeZone.typeCount && Arrays.equals(this.typeMapData, olsonTimeZone.typeMapData) && Arrays.equals(this.typeOffsets, olsonTimeZone.typeOffsets);
    }
    
    public String getCanonicalID() {
        if (this.canonicalID == null) {
            // monitorenter(this)
            if (this.canonicalID == null) {
                this.canonicalID = TimeZone.getCanonicalID(this.getID());
                assert this.canonicalID != null;
                if (this.canonicalID == null) {
                    this.canonicalID = this.getID();
                }
            }
        }
        // monitorexit(this)
        return this.canonicalID;
    }
    
    private void constructEmpty() {
        this.transitionCount = 0;
        this.transitionTimes64 = null;
        this.typeMapData = null;
        this.typeCount = 1;
        this.typeOffsets = new int[] { 0, 0 };
        this.finalZone = null;
        this.finalStartYear = Integer.MAX_VALUE;
        this.finalStartMillis = Double.MAX_VALUE;
        this.transitionRulesInitialized = false;
    }
    
    public OlsonTimeZone(final UResourceBundle uResourceBundle, final UResourceBundle uResourceBundle2, final String s) {
        super(s);
        this.finalStartYear = Integer.MAX_VALUE;
        this.finalStartMillis = Double.MAX_VALUE;
        this.finalZone = null;
        this.canonicalID = null;
        this.serialVersionOnStream = 1;
        this.isFrozen = false;
        this.construct(uResourceBundle, uResourceBundle2);
    }
    
    private void construct(final UResourceBundle uResourceBundle, final UResourceBundle uResourceBundle2) {
        if (uResourceBundle == null || uResourceBundle2 == null) {
            throw new IllegalArgumentException();
        }
        if (OlsonTimeZone.DEBUG) {
            System.out.println("OlsonTimeZone(" + uResourceBundle2.getKey() + ")");
        }
        this.transitionCount = 0;
        final int[] intVector = uResourceBundle2.get("transPre32").getIntVector();
        if (intVector.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid Format");
        }
        this.transitionCount += intVector.length / 2;
        final int[] intVector2 = uResourceBundle2.get("trans").getIntVector();
        this.transitionCount += intVector2.length;
        final int[] intVector3 = uResourceBundle2.get("transPost32").getIntVector();
        if (intVector3.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid Format");
        }
        this.transitionCount += intVector3.length / 2;
        if (this.transitionCount > 0) {
            this.transitionTimes64 = new long[this.transitionCount];
            int n = 0;
            int n2 = 0;
            if (intVector != null) {
                while (0 < intVector.length / 2) {
                    this.transitionTimes64[0] = (((long)intVector[0] & 0xFFFFFFFFL) << 32 | ((long)intVector[1] & 0xFFFFFFFFL));
                    ++n;
                    ++n2;
                }
            }
            if (intVector2 != null) {
                while (0 < intVector2.length) {
                    this.transitionTimes64[0] = intVector2[0];
                    ++n;
                    ++n2;
                }
            }
            if (intVector3 != null) {
                while (0 < intVector3.length / 2) {
                    this.transitionTimes64[0] = (((long)intVector3[0] & 0xFFFFFFFFL) << 32 | ((long)intVector3[1] & 0xFFFFFFFFL));
                    ++n;
                    ++n2;
                }
            }
        }
        else {
            this.transitionTimes64 = null;
        }
        this.typeOffsets = uResourceBundle2.get("typeOffsets").getIntVector();
        if (this.typeOffsets.length < 2 || this.typeOffsets.length > 32766 || this.typeOffsets.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid Format");
        }
        this.typeCount = this.typeOffsets.length / 2;
        if (this.transitionCount > 0) {
            this.typeMapData = uResourceBundle2.get("typeMap").getBinary(null);
            if (this.typeMapData.length != this.transitionCount) {
                throw new IllegalArgumentException("Invalid Format");
            }
        }
        else {
            this.typeMapData = null;
        }
        this.finalZone = null;
        this.finalStartYear = Integer.MAX_VALUE;
        this.finalStartMillis = Double.MAX_VALUE;
        final String string = uResourceBundle2.getString("finalRule");
        int n = uResourceBundle2.get("finalRaw").getInt() * 1000;
        final int[] intVector4 = loadRule(uResourceBundle, string).getIntVector();
        if (intVector4 == null || intVector4.length != 11) {
            throw new IllegalArgumentException("Invalid Format");
        }
        this.finalZone = new SimpleTimeZone(0, "", intVector4[0], intVector4[1], intVector4[2], intVector4[3] * 1000, intVector4[4], intVector4[5], intVector4[6], intVector4[7], intVector4[8] * 1000, intVector4[9], intVector4[10] * 1000);
        this.finalStartYear = uResourceBundle2.get("finalYear").getInt();
        this.finalStartMillis = (double)(Grego.fieldsToDay(this.finalStartYear, 0, 1) * 86400000L);
    }
    
    public OlsonTimeZone(final String id) {
        super(id);
        this.finalStartYear = Integer.MAX_VALUE;
        this.finalStartMillis = Double.MAX_VALUE;
        this.finalZone = null;
        this.canonicalID = null;
        this.serialVersionOnStream = 1;
        this.isFrozen = false;
        final UResourceBundle bundleInstance = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "zoneinfo64", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        this.construct(bundleInstance, ZoneMeta.openOlsonResource(bundleInstance, id));
        if (this.finalZone != null) {
            this.finalZone.setID(id);
        }
    }
    
    @Override
    public void setID(final String s) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen OlsonTimeZone instance.");
        }
        if (this.canonicalID == null) {
            this.canonicalID = TimeZone.getCanonicalID(this.getID());
            assert this.canonicalID != null;
            if (this.canonicalID == null) {
                this.canonicalID = this.getID();
            }
        }
        if (this.finalZone != null) {
            this.finalZone.setID(s);
        }
        super.setID(s);
        this.transitionRulesInitialized = false;
    }
    
    private void getHistoricalOffset(final long n, final boolean b, final int n2, final int n3, final int[] array) {
        if (this.transitionCount != 0) {
            final long floorDivide = Grego.floorDivide(n, 1000L);
            if (!b && floorDivide < this.transitionTimes64[0]) {
                array[0] = this.initialRawOffset() * 1000;
                array[1] = this.initialDstOffset() * 1000;
            }
            else {
                int i;
                for (i = this.transitionCount - 1; i >= 0; --i) {
                    long n4 = this.transitionTimes64[i];
                    if (b) {
                        final int zoneOffset = this.zoneOffsetAt(i - 1);
                        final boolean b2 = this.dstOffsetAt(i - 1) != 0;
                        final int zoneOffset2 = this.zoneOffsetAt(i);
                        final boolean b3 = this.dstOffsetAt(i) != 0;
                        final boolean b4 = b2 && !b3;
                        final boolean b5 = !b2 && b3;
                        if (zoneOffset2 - zoneOffset >= 0) {
                            if (((n2 & 0x3) == 0x1 && b4) || ((n2 & 0x3) == 0x3 && b5)) {
                                n4 += zoneOffset;
                            }
                            else if (((n2 & 0x3) == 0x1 && b5) || ((n2 & 0x3) == 0x3 && b4)) {
                                n4 += zoneOffset2;
                            }
                            else if ((n2 & 0xC) == 0xC) {
                                n4 += zoneOffset;
                            }
                            else {
                                n4 += zoneOffset2;
                            }
                        }
                        else if (((n3 & 0x3) == 0x1 && b4) || ((n3 & 0x3) == 0x3 && b5)) {
                            n4 += zoneOffset2;
                        }
                        else if (((n3 & 0x3) == 0x1 && b5) || ((n3 & 0x3) == 0x3 && b4)) {
                            n4 += zoneOffset;
                        }
                        else if ((n3 & 0xC) == 0x4) {
                            n4 += zoneOffset;
                        }
                        else {
                            n4 += zoneOffset2;
                        }
                    }
                    if (floorDivide >= n4) {
                        break;
                    }
                }
                array[0] = this.rawOffsetAt(i) * 1000;
                array[1] = this.dstOffsetAt(i) * 1000;
            }
        }
        else {
            array[0] = this.initialRawOffset() * 1000;
            array[1] = this.initialDstOffset() * 1000;
        }
    }
    
    private int getInt(final byte b) {
        return b & 0xFF;
    }
    
    private int zoneOffsetAt(final int n) {
        final int n2 = (n >= 0) ? (this.getInt(this.typeMapData[n]) * 2) : 0;
        return this.typeOffsets[n2] + this.typeOffsets[n2 + 1];
    }
    
    private int rawOffsetAt(final int n) {
        return this.typeOffsets[(n >= 0) ? (this.getInt(this.typeMapData[n]) * 2) : 0];
    }
    
    private int dstOffsetAt(final int n) {
        return this.typeOffsets[((n >= 0) ? (this.getInt(this.typeMapData[n]) * 2) : 0) + 1];
    }
    
    private int initialRawOffset() {
        return this.typeOffsets[0];
    }
    
    private int initialDstOffset() {
        return this.typeOffsets[1];
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append('[');
        sb.append("transitionCount=" + this.transitionCount);
        sb.append(",typeCount=" + this.typeCount);
        sb.append(",transitionTimes=");
        int n = 0;
        if (this.transitionTimes64 != null) {
            sb.append('[');
            while (0 < this.transitionTimes64.length) {
                if (0 > 0) {
                    sb.append(',');
                }
                sb.append(Long.toString(this.transitionTimes64[0]));
                ++n;
            }
            sb.append(']');
        }
        else {
            sb.append("null");
        }
        sb.append(",typeOffsets=");
        if (this.typeOffsets != null) {
            sb.append('[');
            while (0 < this.typeOffsets.length) {
                if (0 > 0) {
                    sb.append(',');
                }
                sb.append(Integer.toString(this.typeOffsets[0]));
                ++n;
            }
            sb.append(']');
        }
        else {
            sb.append("null");
        }
        sb.append(",typeMapData=");
        if (this.typeMapData != null) {
            sb.append('[');
            while (0 < this.typeMapData.length) {
                if (0 > 0) {
                    sb.append(',');
                }
                sb.append(Byte.toString(this.typeMapData[0]));
                ++n;
            }
        }
        else {
            sb.append("null");
        }
        sb.append(",finalStartYear=" + this.finalStartYear);
        sb.append(",finalStartMillis=" + this.finalStartMillis);
        sb.append(",finalZone=" + this.finalZone);
        sb.append(']');
        return sb.toString();
    }
    
    private static UResourceBundle loadRule(final UResourceBundle uResourceBundle, final String s) {
        return uResourceBundle.get("Rules").get(s);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return false;
        }
        final OlsonTimeZone olsonTimeZone = (OlsonTimeZone)o;
        return Utility.arrayEquals(this.typeMapData, olsonTimeZone.typeMapData) || (this.finalStartYear == olsonTimeZone.finalStartYear && ((this.finalZone == null && olsonTimeZone.finalZone == null) || (this.finalZone != null && olsonTimeZone.finalZone != null && this.finalZone.equals(olsonTimeZone.finalZone) && this.transitionCount == olsonTimeZone.transitionCount && this.typeCount == olsonTimeZone.typeCount && Utility.arrayEquals((Object)this.transitionTimes64, (Object)olsonTimeZone.transitionTimes64) && Utility.arrayEquals(this.typeOffsets, olsonTimeZone.typeOffsets) && Utility.arrayEquals(this.typeMapData, olsonTimeZone.typeMapData))));
    }
    
    @Override
    public int hashCode() {
        int n = (int)((long)(this.finalStartYear ^ (this.finalStartYear >>> 4) + this.transitionCount ^ (this.transitionCount >>> 6) + this.typeCount) ^ (this.typeCount >>> 8) + Double.doubleToLongBits(this.finalStartMillis) + ((this.finalZone == null) ? 0 : this.finalZone.hashCode()) + super.hashCode());
        int n2 = 0;
        if (this.transitionTimes64 != null) {
            while (0 < this.transitionTimes64.length) {
                n += (int)(this.transitionTimes64[0] ^ this.transitionTimes64[0] >>> 8);
                ++n2;
            }
        }
        while (0 < this.typeOffsets.length) {
            n += (this.typeOffsets[0] ^ this.typeOffsets[0] >>> 8);
            ++n2;
        }
        if (this.typeMapData != null) {
            while (0 < this.typeMapData.length) {
                n += (this.typeMapData[0] & 0xFF);
                ++n2;
            }
        }
        return n;
    }
    
    @Override
    public TimeZoneTransition getNextTransition(final long n, final boolean b) {
        this.initTransitionRules();
        if (this.finalZone != null) {
            if (b && n == this.firstFinalTZTransition.getTime()) {
                return this.firstFinalTZTransition;
            }
            if (n >= this.firstFinalTZTransition.getTime()) {
                if (this.finalZone.useDaylightTime()) {
                    return this.finalZoneWithStartYear.getNextTransition(n, b);
                }
                return null;
            }
        }
        if (this.historicRules == null) {
            return null;
        }
        int i;
        for (i = this.transitionCount - 1; i >= this.firstTZTransitionIdx; --i) {
            final long n2 = this.transitionTimes64[i] * 1000L;
            if (n > n2) {
                break;
            }
            if (!b && n == n2) {
                break;
            }
        }
        if (i == this.transitionCount - 1) {
            return this.firstFinalTZTransition;
        }
        if (i < this.firstTZTransitionIdx) {
            return this.firstTZTransition;
        }
        final TimeArrayTimeZoneRule timeArrayTimeZoneRule = this.historicRules[this.getInt(this.typeMapData[i + 1])];
        final TimeArrayTimeZoneRule timeArrayTimeZoneRule2 = this.historicRules[this.getInt(this.typeMapData[i])];
        final long n3 = this.transitionTimes64[i + 1] * 1000L;
        if (timeArrayTimeZoneRule2.getName().equals(timeArrayTimeZoneRule.getName()) && timeArrayTimeZoneRule2.getRawOffset() == timeArrayTimeZoneRule.getRawOffset() && timeArrayTimeZoneRule2.getDSTSavings() == timeArrayTimeZoneRule.getDSTSavings()) {
            return this.getNextTransition(n3, false);
        }
        return new TimeZoneTransition(n3, timeArrayTimeZoneRule2, timeArrayTimeZoneRule);
    }
    
    @Override
    public TimeZoneTransition getPreviousTransition(final long n, final boolean b) {
        this.initTransitionRules();
        if (this.finalZone != null) {
            if (b && n == this.firstFinalTZTransition.getTime()) {
                return this.firstFinalTZTransition;
            }
            if (n > this.firstFinalTZTransition.getTime()) {
                if (this.finalZone.useDaylightTime()) {
                    return this.finalZoneWithStartYear.getPreviousTransition(n, b);
                }
                return this.firstFinalTZTransition;
            }
        }
        if (this.historicRules == null) {
            return null;
        }
        int i;
        for (i = this.transitionCount - 1; i >= this.firstTZTransitionIdx; --i) {
            final long n2 = this.transitionTimes64[i] * 1000L;
            if (n > n2) {
                break;
            }
            if (b && n == n2) {
                break;
            }
        }
        if (i < this.firstTZTransitionIdx) {
            return null;
        }
        if (i == this.firstTZTransitionIdx) {
            return this.firstTZTransition;
        }
        final TimeArrayTimeZoneRule timeArrayTimeZoneRule = this.historicRules[this.getInt(this.typeMapData[i])];
        final TimeArrayTimeZoneRule timeArrayTimeZoneRule2 = this.historicRules[this.getInt(this.typeMapData[i - 1])];
        final long n3 = this.transitionTimes64[i] * 1000L;
        if (timeArrayTimeZoneRule2.getName().equals(timeArrayTimeZoneRule.getName()) && timeArrayTimeZoneRule2.getRawOffset() == timeArrayTimeZoneRule.getRawOffset() && timeArrayTimeZoneRule2.getDSTSavings() == timeArrayTimeZoneRule.getDSTSavings()) {
            return this.getPreviousTransition(n3, false);
        }
        return new TimeZoneTransition(n3, timeArrayTimeZoneRule2, timeArrayTimeZoneRule);
    }
    
    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        this.initTransitionRules();
        int n = 0;
        if (this.historicRules != null) {
            while (0 < this.historicRules.length) {
                if (this.historicRules[0] != null) {
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
        }
        if (this.finalZone != null) {
            if (this.finalZone.useDaylightTime()) {
                n += 2;
            }
            else {
                ++n;
            }
        }
        final TimeZoneRule[] array2;
        final TimeZoneRule[] array = array2 = new TimeZoneRule[] { null };
        final int n3 = 0;
        int n4 = 0;
        ++n4;
        array2[n3] = this.initialRule;
        if (this.historicRules != null) {
            while (0 < this.historicRules.length) {
                if (this.historicRules[0] != null) {
                    final TimeZoneRule[] array3 = array;
                    final int n5 = 0;
                    ++n4;
                    array3[n5] = this.historicRules[0];
                }
                int n6 = 0;
                ++n6;
            }
        }
        if (this.finalZone != null) {
            if (this.finalZone.useDaylightTime()) {
                final TimeZoneRule[] timeZoneRules = this.finalZoneWithStartYear.getTimeZoneRules();
                final TimeZoneRule[] array4 = array;
                final int n7 = 0;
                ++n4;
                array4[n7] = timeZoneRules[1];
                final TimeZoneRule[] array5 = array;
                final int n8 = 0;
                ++n4;
                array5[n8] = timeZoneRules[2];
            }
            else {
                final TimeZoneRule[] array6 = array;
                final int n9 = 0;
                ++n4;
                array6[n9] = new TimeArrayTimeZoneRule(this.getID() + "(STD)", this.finalZone.getRawOffset(), 0, new long[] { (long)this.finalStartMillis }, 2);
            }
        }
        return array;
    }
    
    private synchronized void initTransitionRules() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/ibm/icu/impl/OlsonTimeZone.transitionRulesInitialized:Z
        //     4: ifeq            8
        //     7: return         
        //     8: aload_0        
        //     9: aconst_null    
        //    10: putfield        com/ibm/icu/impl/OlsonTimeZone.initialRule:Lcom/ibm/icu/util/InitialTimeZoneRule;
        //    13: aload_0        
        //    14: aconst_null    
        //    15: putfield        com/ibm/icu/impl/OlsonTimeZone.firstTZTransition:Lcom/ibm/icu/util/TimeZoneTransition;
        //    18: aload_0        
        //    19: aconst_null    
        //    20: putfield        com/ibm/icu/impl/OlsonTimeZone.firstFinalTZTransition:Lcom/ibm/icu/util/TimeZoneTransition;
        //    23: aload_0        
        //    24: aconst_null    
        //    25: putfield        com/ibm/icu/impl/OlsonTimeZone.historicRules:[Lcom/ibm/icu/util/TimeArrayTimeZoneRule;
        //    28: aload_0        
        //    29: iconst_0       
        //    30: putfield        com/ibm/icu/impl/OlsonTimeZone.firstTZTransitionIdx:I
        //    33: aload_0        
        //    34: aconst_null    
        //    35: putfield        com/ibm/icu/impl/OlsonTimeZone.finalZoneWithStartYear:Lcom/ibm/icu/util/SimpleTimeZone;
        //    38: new             Ljava/lang/StringBuilder;
        //    41: dup            
        //    42: invokespecial   java/lang/StringBuilder.<init>:()V
        //    45: aload_0        
        //    46: invokevirtual   com/ibm/icu/impl/OlsonTimeZone.getID:()Ljava/lang/String;
        //    49: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    52: ldc_w           "(STD)"
        //    55: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    58: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    61: astore_1       
        //    62: new             Ljava/lang/StringBuilder;
        //    65: dup            
        //    66: invokespecial   java/lang/StringBuilder.<init>:()V
        //    69: aload_0        
        //    70: invokevirtual   com/ibm/icu/impl/OlsonTimeZone.getID:()Ljava/lang/String;
        //    73: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    76: ldc_w           "(DST)"
        //    79: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    82: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    85: astore_2       
        //    86: aload_0        
        //    87: invokespecial   com/ibm/icu/impl/OlsonTimeZone.initialRawOffset:()I
        //    90: sipush          1000
        //    93: imul           
        //    94: istore_3       
        //    95: aload_0        
        //    96: invokespecial   com/ibm/icu/impl/OlsonTimeZone.initialDstOffset:()I
        //    99: sipush          1000
        //   102: imul           
        //   103: istore          4
        //   105: aload_0        
        //   106: new             Lcom/ibm/icu/util/InitialTimeZoneRule;
        //   109: dup            
        //   110: iload           4
        //   112: ifne            119
        //   115: aload_1        
        //   116: goto            120
        //   119: aload_2        
        //   120: iload_3        
        //   121: iload           4
        //   123: invokespecial   com/ibm/icu/util/InitialTimeZoneRule.<init>:(Ljava/lang/String;II)V
        //   126: putfield        com/ibm/icu/impl/OlsonTimeZone.initialRule:Lcom/ibm/icu/util/InitialTimeZoneRule;
        //   129: aload_0        
        //   130: getfield        com/ibm/icu/impl/OlsonTimeZone.transitionCount:I
        //   133: ifle            413
        //   136: iconst_0       
        //   137: aload_0        
        //   138: getfield        com/ibm/icu/impl/OlsonTimeZone.transitionCount:I
        //   141: if_icmpge       176
        //   144: aload_0        
        //   145: aload_0        
        //   146: getfield        com/ibm/icu/impl/OlsonTimeZone.typeMapData:[B
        //   149: iconst_0       
        //   150: baload         
        //   151: invokespecial   com/ibm/icu/impl/OlsonTimeZone.getInt:(B)I
        //   154: ifeq            160
        //   157: goto            176
        //   160: aload_0        
        //   161: dup            
        //   162: getfield        com/ibm/icu/impl/OlsonTimeZone.firstTZTransitionIdx:I
        //   165: iconst_1       
        //   166: iadd           
        //   167: putfield        com/ibm/icu/impl/OlsonTimeZone.firstTZTransitionIdx:I
        //   170: iinc            5, 1
        //   173: goto            136
        //   176: iconst_0       
        //   177: aload_0        
        //   178: getfield        com/ibm/icu/impl/OlsonTimeZone.transitionCount:I
        //   181: if_icmpne       187
        //   184: goto            413
        //   187: aload_0        
        //   188: getfield        com/ibm/icu/impl/OlsonTimeZone.transitionCount:I
        //   191: newarray        J
        //   193: astore          7
        //   195: iconst_0       
        //   196: aload_0        
        //   197: getfield        com/ibm/icu/impl/OlsonTimeZone.typeCount:I
        //   200: if_icmpge       364
        //   203: aload_0        
        //   204: getfield        com/ibm/icu/impl/OlsonTimeZone.firstTZTransitionIdx:I
        //   207: istore          5
        //   209: iconst_0       
        //   210: aload_0        
        //   211: getfield        com/ibm/icu/impl/OlsonTimeZone.transitionCount:I
        //   214: if_icmpge       269
        //   217: iconst_0       
        //   218: aload_0        
        //   219: aload_0        
        //   220: getfield        com/ibm/icu/impl/OlsonTimeZone.typeMapData:[B
        //   223: iconst_0       
        //   224: baload         
        //   225: invokespecial   com/ibm/icu/impl/OlsonTimeZone.getInt:(B)I
        //   228: if_icmpne       263
        //   231: aload_0        
        //   232: getfield        com/ibm/icu/impl/OlsonTimeZone.transitionTimes64:[J
        //   235: iconst_0       
        //   236: laload         
        //   237: ldc2_w          1000
        //   240: lmul           
        //   241: lstore          9
        //   243: lload           9
        //   245: l2d            
        //   246: aload_0        
        //   247: getfield        com/ibm/icu/impl/OlsonTimeZone.finalStartMillis:D
        //   250: dcmpg          
        //   251: ifge            263
        //   254: aload           7
        //   256: iconst_0       
        //   257: iinc            8, 1
        //   260: lload           9
        //   262: lastore        
        //   263: iinc            5, 1
        //   266: goto            209
        //   269: iconst_0       
        //   270: ifle            358
        //   273: iconst_0       
        //   274: newarray        J
        //   276: astore          9
        //   278: aload           7
        //   280: iconst_0       
        //   281: aload           9
        //   283: iconst_0       
        //   284: iconst_0       
        //   285: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   288: aload_0        
        //   289: getfield        com/ibm/icu/impl/OlsonTimeZone.typeOffsets:[I
        //   292: iconst_0       
        //   293: iaload         
        //   294: sipush          1000
        //   297: imul           
        //   298: istore_3       
        //   299: aload_0        
        //   300: getfield        com/ibm/icu/impl/OlsonTimeZone.typeOffsets:[I
        //   303: iconst_1       
        //   304: iaload         
        //   305: sipush          1000
        //   308: imul           
        //   309: istore          4
        //   311: aload_0        
        //   312: getfield        com/ibm/icu/impl/OlsonTimeZone.historicRules:[Lcom/ibm/icu/util/TimeArrayTimeZoneRule;
        //   315: ifnonnull       329
        //   318: aload_0        
        //   319: aload_0        
        //   320: getfield        com/ibm/icu/impl/OlsonTimeZone.typeCount:I
        //   323: anewarray       Lcom/ibm/icu/util/TimeArrayTimeZoneRule;
        //   326: putfield        com/ibm/icu/impl/OlsonTimeZone.historicRules:[Lcom/ibm/icu/util/TimeArrayTimeZoneRule;
        //   329: aload_0        
        //   330: getfield        com/ibm/icu/impl/OlsonTimeZone.historicRules:[Lcom/ibm/icu/util/TimeArrayTimeZoneRule;
        //   333: iconst_0       
        //   334: new             Lcom/ibm/icu/util/TimeArrayTimeZoneRule;
        //   337: dup            
        //   338: iload           4
        //   340: ifne            347
        //   343: aload_1        
        //   344: goto            348
        //   347: aload_2        
        //   348: iload_3        
        //   349: iload           4
        //   351: aload           9
        //   353: iconst_2       
        //   354: invokespecial   com/ibm/icu/util/TimeArrayTimeZoneRule.<init>:(Ljava/lang/String;II[JI)V
        //   357: aastore        
        //   358: iinc            6, 1
        //   361: goto            195
        //   364: aload_0        
        //   365: aload_0        
        //   366: getfield        com/ibm/icu/impl/OlsonTimeZone.typeMapData:[B
        //   369: aload_0        
        //   370: getfield        com/ibm/icu/impl/OlsonTimeZone.firstTZTransitionIdx:I
        //   373: baload         
        //   374: invokespecial   com/ibm/icu/impl/OlsonTimeZone.getInt:(B)I
        //   377: istore          6
        //   379: aload_0        
        //   380: new             Lcom/ibm/icu/util/TimeZoneTransition;
        //   383: dup            
        //   384: aload_0        
        //   385: getfield        com/ibm/icu/impl/OlsonTimeZone.transitionTimes64:[J
        //   388: aload_0        
        //   389: getfield        com/ibm/icu/impl/OlsonTimeZone.firstTZTransitionIdx:I
        //   392: laload         
        //   393: ldc2_w          1000
        //   396: lmul           
        //   397: aload_0        
        //   398: getfield        com/ibm/icu/impl/OlsonTimeZone.initialRule:Lcom/ibm/icu/util/InitialTimeZoneRule;
        //   401: aload_0        
        //   402: getfield        com/ibm/icu/impl/OlsonTimeZone.historicRules:[Lcom/ibm/icu/util/TimeArrayTimeZoneRule;
        //   405: iconst_0       
        //   406: aaload         
        //   407: invokespecial   com/ibm/icu/util/TimeZoneTransition.<init>:(JLcom/ibm/icu/util/TimeZoneRule;Lcom/ibm/icu/util/TimeZoneRule;)V
        //   410: putfield        com/ibm/icu/impl/OlsonTimeZone.firstTZTransition:Lcom/ibm/icu/util/TimeZoneTransition;
        //   413: aload_0        
        //   414: getfield        com/ibm/icu/impl/OlsonTimeZone.finalZone:Lcom/ibm/icu/util/SimpleTimeZone;
        //   417: ifnull          592
        //   420: aload_0        
        //   421: getfield        com/ibm/icu/impl/OlsonTimeZone.finalStartMillis:D
        //   424: d2l            
        //   425: lstore          5
        //   427: aload_0        
        //   428: getfield        com/ibm/icu/impl/OlsonTimeZone.finalZone:Lcom/ibm/icu/util/SimpleTimeZone;
        //   431: invokevirtual   com/ibm/icu/util/SimpleTimeZone.useDaylightTime:()Z
        //   434: ifeq            491
        //   437: aload_0        
        //   438: aload_0        
        //   439: getfield        com/ibm/icu/impl/OlsonTimeZone.finalZone:Lcom/ibm/icu/util/SimpleTimeZone;
        //   442: invokevirtual   com/ibm/icu/util/SimpleTimeZone.clone:()Ljava/lang/Object;
        //   445: checkcast       Lcom/ibm/icu/util/SimpleTimeZone;
        //   448: putfield        com/ibm/icu/impl/OlsonTimeZone.finalZoneWithStartYear:Lcom/ibm/icu/util/SimpleTimeZone;
        //   451: aload_0        
        //   452: getfield        com/ibm/icu/impl/OlsonTimeZone.finalZoneWithStartYear:Lcom/ibm/icu/util/SimpleTimeZone;
        //   455: aload_0        
        //   456: getfield        com/ibm/icu/impl/OlsonTimeZone.finalStartYear:I
        //   459: invokevirtual   com/ibm/icu/util/SimpleTimeZone.setStartYear:(I)V
        //   462: aload_0        
        //   463: getfield        com/ibm/icu/impl/OlsonTimeZone.finalZoneWithStartYear:Lcom/ibm/icu/util/SimpleTimeZone;
        //   466: lload           5
        //   468: iconst_0       
        //   469: invokevirtual   com/ibm/icu/util/SimpleTimeZone.getNextTransition:(JZ)Lcom/ibm/icu/util/TimeZoneTransition;
        //   472: astore          8
        //   474: aload           8
        //   476: invokevirtual   com/ibm/icu/util/TimeZoneTransition.getTo:()Lcom/ibm/icu/util/TimeZoneRule;
        //   479: astore          7
        //   481: aload           8
        //   483: invokevirtual   com/ibm/icu/util/TimeZoneTransition.getTime:()J
        //   486: lstore          5
        //   488: goto            532
        //   491: aload_0        
        //   492: aload_0        
        //   493: getfield        com/ibm/icu/impl/OlsonTimeZone.finalZone:Lcom/ibm/icu/util/SimpleTimeZone;
        //   496: putfield        com/ibm/icu/impl/OlsonTimeZone.finalZoneWithStartYear:Lcom/ibm/icu/util/SimpleTimeZone;
        //   499: new             Lcom/ibm/icu/util/TimeArrayTimeZoneRule;
        //   502: dup            
        //   503: aload_0        
        //   504: getfield        com/ibm/icu/impl/OlsonTimeZone.finalZone:Lcom/ibm/icu/util/SimpleTimeZone;
        //   507: invokevirtual   com/ibm/icu/util/SimpleTimeZone.getID:()Ljava/lang/String;
        //   510: aload_0        
        //   511: getfield        com/ibm/icu/impl/OlsonTimeZone.finalZone:Lcom/ibm/icu/util/SimpleTimeZone;
        //   514: invokevirtual   com/ibm/icu/util/SimpleTimeZone.getRawOffset:()I
        //   517: iconst_0       
        //   518: iconst_1       
        //   519: newarray        J
        //   521: dup            
        //   522: iconst_0       
        //   523: lload           5
        //   525: lastore        
        //   526: iconst_2       
        //   527: invokespecial   com/ibm/icu/util/TimeArrayTimeZoneRule.<init>:(Ljava/lang/String;II[JI)V
        //   530: astore          7
        //   532: aconst_null    
        //   533: astore          8
        //   535: aload_0        
        //   536: getfield        com/ibm/icu/impl/OlsonTimeZone.transitionCount:I
        //   539: ifle            564
        //   542: aload_0        
        //   543: getfield        com/ibm/icu/impl/OlsonTimeZone.historicRules:[Lcom/ibm/icu/util/TimeArrayTimeZoneRule;
        //   546: aload_0        
        //   547: aload_0        
        //   548: getfield        com/ibm/icu/impl/OlsonTimeZone.typeMapData:[B
        //   551: aload_0        
        //   552: getfield        com/ibm/icu/impl/OlsonTimeZone.transitionCount:I
        //   555: iconst_1       
        //   556: isub           
        //   557: baload         
        //   558: invokespecial   com/ibm/icu/impl/OlsonTimeZone.getInt:(B)I
        //   561: aaload         
        //   562: astore          8
        //   564: aload           8
        //   566: ifnonnull       575
        //   569: aload_0        
        //   570: getfield        com/ibm/icu/impl/OlsonTimeZone.initialRule:Lcom/ibm/icu/util/InitialTimeZoneRule;
        //   573: astore          8
        //   575: aload_0        
        //   576: new             Lcom/ibm/icu/util/TimeZoneTransition;
        //   579: dup            
        //   580: lload           5
        //   582: aload           8
        //   584: aload           7
        //   586: invokespecial   com/ibm/icu/util/TimeZoneTransition.<init>:(JLcom/ibm/icu/util/TimeZoneRule;Lcom/ibm/icu/util/TimeZoneRule;)V
        //   589: putfield        com/ibm/icu/impl/OlsonTimeZone.firstFinalTZTransition:Lcom/ibm/icu/util/TimeZoneTransition;
        //   592: aload_0        
        //   593: iconst_1       
        //   594: putfield        com/ibm/icu/impl/OlsonTimeZone.transitionRulesInitialized:Z
        //   597: return         
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            final String id = this.getID();
            if (id != null) {
                final UResourceBundle bundleInstance = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "zoneinfo64", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
                this.construct(bundleInstance, ZoneMeta.openOlsonResource(bundleInstance, id));
                if (this.finalZone != null) {
                    this.finalZone.setID(id);
                }
            }
            if (!true) {
                this.constructEmpty();
            }
        }
        this.transitionRulesInitialized = false;
    }
    
    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }
    
    @Override
    public TimeZone freeze() {
        this.isFrozen = true;
        return this;
    }
    
    @Override
    public TimeZone cloneAsThawed() {
        final OlsonTimeZone olsonTimeZone = (OlsonTimeZone)super.cloneAsThawed();
        if (this.finalZone != null) {
            this.finalZone.setID(this.getID());
            olsonTimeZone.finalZone = (SimpleTimeZone)this.finalZone.clone();
        }
        olsonTimeZone.isFrozen = false;
        return olsonTimeZone;
    }
    
    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    @Override
    public Object freeze() {
        return this.freeze();
    }
    
    static {
        $assertionsDisabled = !OlsonTimeZone.class.desiredAssertionStatus();
        DEBUG = ICUDebug.enabled("olson");
    }
}
