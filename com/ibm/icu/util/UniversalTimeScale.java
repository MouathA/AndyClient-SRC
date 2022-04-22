package com.ibm.icu.util;

import com.ibm.icu.math.*;

public final class UniversalTimeScale
{
    public static final int JAVA_TIME = 0;
    public static final int UNIX_TIME = 1;
    public static final int ICU4C_TIME = 2;
    public static final int WINDOWS_FILE_TIME = 3;
    public static final int DOTNET_DATE_TIME = 4;
    public static final int MAC_OLD_TIME = 5;
    public static final int MAC_TIME = 6;
    public static final int EXCEL_TIME = 7;
    public static final int DB2_TIME = 8;
    public static final int UNIX_MICROSECONDS_TIME = 9;
    public static final int MAX_SCALE = 10;
    public static final int UNITS_VALUE = 0;
    public static final int EPOCH_OFFSET_VALUE = 1;
    public static final int FROM_MIN_VALUE = 2;
    public static final int FROM_MAX_VALUE = 3;
    public static final int TO_MIN_VALUE = 4;
    public static final int TO_MAX_VALUE = 5;
    public static final int EPOCH_OFFSET_PLUS_1_VALUE = 6;
    @Deprecated
    public static final int EPOCH_OFFSET_MINUS_1_VALUE = 7;
    @Deprecated
    public static final int UNITS_ROUND_VALUE = 8;
    @Deprecated
    public static final int MIN_ROUND_VALUE = 9;
    @Deprecated
    public static final int MAX_ROUND_VALUE = 10;
    @Deprecated
    public static final int MAX_SCALE_VALUE = 11;
    private static final long ticks = 1L;
    private static final long microseconds = 10L;
    private static final long milliseconds = 10000L;
    private static final long seconds = 10000000L;
    private static final long minutes = 600000000L;
    private static final long hours = 36000000000L;
    private static final long days = 864000000000L;
    private static final TimeScaleData[] timeScaleTable;
    
    private UniversalTimeScale() {
    }
    
    public static long from(final long n, final int n2) {
        final TimeScaleData fromRangeCheck = fromRangeCheck(n, n2);
        return (n + fromRangeCheck.epochOffset) * fromRangeCheck.units;
    }
    
    public static BigDecimal bigDecimalFrom(final double n, final int n2) {
        final TimeScaleData timeScaleData = getTimeScaleData(n2);
        return new BigDecimal(String.valueOf(n)).add(new BigDecimal(timeScaleData.epochOffset)).multiply(new BigDecimal(timeScaleData.units));
    }
    
    public static BigDecimal bigDecimalFrom(final long n, final int n2) {
        final TimeScaleData timeScaleData = getTimeScaleData(n2);
        return new BigDecimal(n).add(new BigDecimal(timeScaleData.epochOffset)).multiply(new BigDecimal(timeScaleData.units));
    }
    
    public static BigDecimal bigDecimalFrom(final BigDecimal bigDecimal, final int n) {
        final TimeScaleData timeScaleData = getTimeScaleData(n);
        return bigDecimal.add(new BigDecimal(timeScaleData.epochOffset)).multiply(new BigDecimal(timeScaleData.units));
    }
    
    public static long toLong(final long n, final int n2) {
        final TimeScaleData rangeCheck = toRangeCheck(n, n2);
        if (n < 0L) {
            if (n < rangeCheck.minRound) {
                return (n + rangeCheck.unitsRound) / rangeCheck.units - rangeCheck.epochOffsetP1;
            }
            return (n - rangeCheck.unitsRound) / rangeCheck.units - rangeCheck.epochOffset;
        }
        else {
            if (n > rangeCheck.maxRound) {
                return (n - rangeCheck.unitsRound) / rangeCheck.units - rangeCheck.epochOffsetM1;
            }
            return (n + rangeCheck.unitsRound) / rangeCheck.units - rangeCheck.epochOffset;
        }
    }
    
    public static BigDecimal toBigDecimal(final long n, final int n2) {
        final TimeScaleData timeScaleData = getTimeScaleData(n2);
        return new BigDecimal(n).divide(new BigDecimal(timeScaleData.units), 4).subtract(new BigDecimal(timeScaleData.epochOffset));
    }
    
    public static BigDecimal toBigDecimal(final BigDecimal bigDecimal, final int n) {
        final TimeScaleData timeScaleData = getTimeScaleData(n);
        return bigDecimal.divide(new BigDecimal(timeScaleData.units), 4).subtract(new BigDecimal(timeScaleData.epochOffset));
    }
    
    private static TimeScaleData getTimeScaleData(final int n) {
        if (n < 0 || n >= 10) {
            throw new IllegalArgumentException("scale out of range: " + n);
        }
        return UniversalTimeScale.timeScaleTable[n];
    }
    
    public static long getTimeScaleValue(final int n, final int n2) {
        final TimeScaleData timeScaleData = getTimeScaleData(n);
        switch (n2) {
            case 0: {
                return timeScaleData.units;
            }
            case 1: {
                return timeScaleData.epochOffset;
            }
            case 2: {
                return timeScaleData.fromMin;
            }
            case 3: {
                return timeScaleData.fromMax;
            }
            case 4: {
                return timeScaleData.toMin;
            }
            case 5: {
                return timeScaleData.toMax;
            }
            case 6: {
                return timeScaleData.epochOffsetP1;
            }
            case 7: {
                return timeScaleData.epochOffsetM1;
            }
            case 8: {
                return timeScaleData.unitsRound;
            }
            case 9: {
                return timeScaleData.minRound;
            }
            case 10: {
                return timeScaleData.maxRound;
            }
            default: {
                throw new IllegalArgumentException("value out of range: " + n2);
            }
        }
    }
    
    private static TimeScaleData toRangeCheck(final long n, final int n2) {
        final TimeScaleData timeScaleData = getTimeScaleData(n2);
        if (n >= timeScaleData.toMin && n <= timeScaleData.toMax) {
            return timeScaleData;
        }
        throw new IllegalArgumentException("universalTime out of range:" + n);
    }
    
    private static TimeScaleData fromRangeCheck(final long n, final int n2) {
        final TimeScaleData timeScaleData = getTimeScaleData(n2);
        if (n >= timeScaleData.fromMin && n <= timeScaleData.fromMax) {
            return timeScaleData;
        }
        throw new IllegalArgumentException("otherTime out of range:" + n);
    }
    
    @Deprecated
    public static BigDecimal toBigDecimalTrunc(final BigDecimal bigDecimal, final int n) {
        final TimeScaleData timeScaleData = getTimeScaleData(n);
        return bigDecimal.divide(new BigDecimal(timeScaleData.units), 1).subtract(new BigDecimal(timeScaleData.epochOffset));
    }
    
    static {
        timeScaleTable = new TimeScaleData[] { new TimeScaleData(10000L, 621355968000000000L, -9223372036854774999L, 9223372036854774999L, -984472800485477L, 860201606885477L), new TimeScaleData(10000000L, 621355968000000000L, Long.MIN_VALUE, Long.MAX_VALUE, -984472800485L, 860201606885L), new TimeScaleData(10000L, 621355968000000000L, -9223372036854774999L, 9223372036854774999L, -984472800485477L, 860201606885477L), new TimeScaleData(1L, 504911232000000000L, -8718460804854775808L, Long.MAX_VALUE, Long.MIN_VALUE, 8718460804854775807L), new TimeScaleData(1L, 0L, Long.MIN_VALUE, Long.MAX_VALUE, Long.MIN_VALUE, Long.MAX_VALUE), new TimeScaleData(10000000L, 600527520000000000L, Long.MIN_VALUE, Long.MAX_VALUE, -982389955685L, 862284451685L), new TimeScaleData(10000000L, 631139040000000000L, Long.MIN_VALUE, Long.MAX_VALUE, -985451107685L, 859223299685L), new TimeScaleData(864000000000L, 599265216000000000L, Long.MIN_VALUE, Long.MAX_VALUE, -11368793L, 9981605L), new TimeScaleData(864000000000L, 599265216000000000L, Long.MIN_VALUE, Long.MAX_VALUE, -11368793L, 9981605L), new TimeScaleData(10L, 621355968000000000L, -9223372036854775804L, 9223372036854775804L, -984472800485477580L, 860201606885477580L) };
    }
    
    private static final class TimeScaleData
    {
        long units;
        long epochOffset;
        long fromMin;
        long fromMax;
        long toMin;
        long toMax;
        long epochOffsetP1;
        long epochOffsetM1;
        long unitsRound;
        long minRound;
        long maxRound;
        
        TimeScaleData(final long units, final long n, final long toMin, final long toMax, final long fromMin, final long fromMax) {
            this.units = units;
            this.unitsRound = units / 2L;
            this.minRound = Long.MIN_VALUE + this.unitsRound;
            this.maxRound = Long.MAX_VALUE - this.unitsRound;
            this.epochOffset = n / units;
            if (units == 1L) {
                final long epochOffset = this.epochOffset;
                this.epochOffsetM1 = epochOffset;
                this.epochOffsetP1 = epochOffset;
            }
            else {
                this.epochOffsetP1 = this.epochOffset + 1L;
                this.epochOffsetM1 = this.epochOffset - 1L;
            }
            this.toMin = toMin;
            this.toMax = toMax;
            this.fromMin = fromMin;
            this.fromMax = fromMax;
        }
    }
}
