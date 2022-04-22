package com.ibm.icu.impl;

import java.util.*;

public class CalendarAstronomer
{
    public static final double SIDEREAL_DAY = 23.93446960027;
    public static final double SOLAR_DAY = 24.065709816;
    public static final double SYNODIC_MONTH = 29.530588853;
    public static final double SIDEREAL_MONTH = 27.32166;
    public static final double TROPICAL_YEAR = 365.242191;
    public static final double SIDEREAL_YEAR = 365.25636;
    public static final int SECOND_MS = 1000;
    public static final int MINUTE_MS = 60000;
    public static final int HOUR_MS = 3600000;
    public static final long DAY_MS = 86400000L;
    public static final long JULIAN_EPOCH_MS = -210866760000000L;
    static final long EPOCH_2000_MS = 946598400000L;
    private static final double PI = 3.141592653589793;
    private static final double PI2 = 6.283185307179586;
    private static final double RAD_HOUR = 3.819718634205488;
    private static final double DEG_RAD = 0.017453292519943295;
    private static final double RAD_DEG = 57.29577951308232;
    static final double JD_EPOCH = 2447891.5;
    static final double SUN_ETA_G = 4.87650757829735;
    static final double SUN_OMEGA_G = 4.935239984568769;
    static final double SUN_E = 0.016713;
    public static final SolarLongitude VERNAL_EQUINOX;
    public static final SolarLongitude SUMMER_SOLSTICE;
    public static final SolarLongitude AUTUMN_EQUINOX;
    public static final SolarLongitude WINTER_SOLSTICE;
    static final double moonL0 = 5.556284436750021;
    static final double moonP0 = 0.6342598060246725;
    static final double moonN0 = 5.559050068029439;
    static final double moonI = 0.08980357792017056;
    static final double moonE = 0.0549;
    static final double moonA = 384401.0;
    static final double moonT0 = 0.009042550854582622;
    static final double moonPi = 0.016592845198710092;
    public static final MoonAge NEW_MOON;
    public static final MoonAge FIRST_QUARTER;
    public static final MoonAge FULL_MOON;
    public static final MoonAge LAST_QUARTER;
    private long time;
    private double fLongitude;
    private double fLatitude;
    private long fGmtOffset;
    private static final double INVALID = Double.MIN_VALUE;
    private transient double julianDay;
    private transient double julianCentury;
    private transient double sunLongitude;
    private transient double meanAnomalySun;
    private transient double moonLongitude;
    private transient double moonEclipLong;
    private transient double eclipObliquity;
    private transient double siderealT0;
    private transient double siderealTime;
    private transient Equatorial moonPosition;
    
    public CalendarAstronomer() {
        this(System.currentTimeMillis());
    }
    
    public CalendarAstronomer(final Date date) {
        this(date.getTime());
    }
    
    public CalendarAstronomer(final long time) {
        this.fLongitude = 0.0;
        this.fLatitude = 0.0;
        this.fGmtOffset = 0L;
        this.julianDay = Double.MIN_VALUE;
        this.julianCentury = Double.MIN_VALUE;
        this.sunLongitude = Double.MIN_VALUE;
        this.meanAnomalySun = Double.MIN_VALUE;
        this.moonLongitude = Double.MIN_VALUE;
        this.moonEclipLong = Double.MIN_VALUE;
        this.eclipObliquity = Double.MIN_VALUE;
        this.siderealT0 = Double.MIN_VALUE;
        this.siderealTime = Double.MIN_VALUE;
        this.moonPosition = null;
        this.time = time;
    }
    
    public CalendarAstronomer(final double n, final double n2) {
        this();
        this.fLongitude = normPI(n * 0.017453292519943295);
        this.fLatitude = normPI(n2 * 0.017453292519943295);
        this.fGmtOffset = (long)(this.fLongitude * 24.0 * 3600000.0 / 6.283185307179586);
    }
    
    public void setTime(final long time) {
        this.time = time;
        this.clearCache();
    }
    
    public void setDate(final Date date) {
        this.setTime(date.getTime());
    }
    
    public void setJulianDay(final double julianDay) {
        this.time = (long)(julianDay * 8.64E7) - 210866760000000L;
        this.clearCache();
        this.julianDay = julianDay;
    }
    
    public long getTime() {
        return this.time;
    }
    
    public Date getDate() {
        return new Date(this.time);
    }
    
    public double getJulianDay() {
        if (this.julianDay == Double.MIN_VALUE) {
            this.julianDay = (this.time + 210866760000000L) / 8.64E7;
        }
        return this.julianDay;
    }
    
    public double getJulianCentury() {
        if (this.julianCentury == Double.MIN_VALUE) {
            this.julianCentury = (this.getJulianDay() - 2415020.0) / 36525.0;
        }
        return this.julianCentury;
    }
    
    public double getGreenwichSidereal() {
        if (this.siderealTime == Double.MIN_VALUE) {
            this.siderealTime = normalize(this.getSiderealOffset() + normalize(this.time / 3600000.0, 24.0) * 1.002737909, 24.0);
        }
        return this.siderealTime;
    }
    
    private double getSiderealOffset() {
        if (this.siderealT0 == Double.MIN_VALUE) {
            final double n = (Math.floor(this.getJulianDay() - 0.5) + 0.5 - 2451545.0) / 36525.0;
            this.siderealT0 = normalize(6.697374558 + 2400.051336 * n + 2.5862E-5 * n * n, 24.0);
        }
        return this.siderealT0;
    }
    
    public double getLocalSidereal() {
        return normalize(this.getGreenwichSidereal() + this.fGmtOffset / 3600000.0, 24.0);
    }
    
    private long lstToUT(final double n) {
        return 86400000L * ((this.time + this.fGmtOffset) / 86400000L) - this.fGmtOffset + (long)(normalize((n - this.getSiderealOffset()) * 0.9972695663, 24.0) * 3600000.0);
    }
    
    public final Equatorial eclipticToEquatorial(final Ecliptic ecliptic) {
        return this.eclipticToEquatorial(ecliptic.longitude, ecliptic.latitude);
    }
    
    public final Equatorial eclipticToEquatorial(final double n, final double n2) {
        final double eclipticObliquity = this.eclipticObliquity();
        final double sin = Math.sin(eclipticObliquity);
        final double cos = Math.cos(eclipticObliquity);
        final double sin2 = Math.sin(n);
        return new Equatorial(Math.atan2(sin2 * cos - Math.tan(n2) * sin, Math.cos(n)), Math.asin(Math.sin(n2) * cos + Math.cos(n2) * sin * sin2));
    }
    
    public final Equatorial eclipticToEquatorial(final double n) {
        return this.eclipticToEquatorial(n, 0.0);
    }
    
    public Horizon eclipticToHorizon(final double n) {
        final Equatorial eclipticToEquatorial = this.eclipticToEquatorial(n);
        final double n2 = this.getLocalSidereal() * 3.141592653589793 / 12.0 - eclipticToEquatorial.ascension;
        final double sin = Math.sin(n2);
        final double cos = Math.cos(n2);
        final double sin2 = Math.sin(eclipticToEquatorial.declination);
        final double cos2 = Math.cos(eclipticToEquatorial.declination);
        final double sin3 = Math.sin(this.fLatitude);
        final double cos3 = Math.cos(this.fLatitude);
        final double asin = Math.asin(sin2 * sin3 + cos2 * cos3 * cos);
        return new Horizon(Math.atan2(-cos2 * cos3 * sin, sin2 - sin3 * Math.sin(asin)), asin);
    }
    
    public double getSunLongitude() {
        if (this.sunLongitude == Double.MIN_VALUE) {
            final double[] sunLongitude = this.getSunLongitude(this.getJulianDay());
            this.sunLongitude = sunLongitude[0];
            this.meanAnomalySun = sunLongitude[1];
        }
        return this.sunLongitude;
    }
    
    double[] getSunLongitude(final double n) {
        final double norm2PI = norm2PI(norm2PI(0.017202791632524146 * (n - 2447891.5)) + 4.87650757829735 - 4.935239984568769);
        return new double[] { norm2PI(this.trueAnomaly(norm2PI, 0.016713) + 4.935239984568769), norm2PI };
    }
    
    public Equatorial getSunPosition() {
        return this.eclipticToEquatorial(this.getSunLongitude(), 0.0);
    }
    
    public long getSunTime(final double n, final boolean b) {
        return this.timeOfAngle(new AngleFunc() {
            final CalendarAstronomer this$0;
            
            public double eval() {
                return this.this$0.getSunLongitude();
            }
        }, n, 365.242191, 60000L, b);
    }
    
    public long getSunTime(final SolarLongitude solarLongitude, final boolean b) {
        return this.getSunTime(solarLongitude.value, b);
    }
    
    public long getSunRiseSet(final boolean b) {
        final long time = this.time;
        this.setTime((this.time + this.fGmtOffset) / 86400000L * 86400000L - this.fGmtOffset + 43200000L + (b ? -6L : 6L) * 3600000L);
        final long riseOrSet = this.riseOrSet(new CoordFunc() {
            final CalendarAstronomer this$0;
            
            public Equatorial eval() {
                return this.this$0.getSunPosition();
            }
        }, b, 0.009302604913129777, 0.009890199094634533, 5000L);
        this.setTime(time);
        return riseOrSet;
    }
    
    public Equatorial getMoonPosition() {
        if (this.moonPosition == null) {
            final double sunLongitude = this.getSunLongitude();
            final double n = this.getJulianDay() - 2447891.5;
            final double norm2PI = norm2PI(0.22997150421858628 * n + 5.556284436750021);
            final double norm2PI2 = norm2PI(norm2PI - 0.001944368345221015 * n - 0.6342598060246725);
            final double n2 = 0.022233749341155764 * Math.sin(2.0 * (norm2PI - sunLongitude) - norm2PI2);
            final double n3 = 0.003242821750205464 * Math.sin(this.meanAnomalySun);
            final double n4 = norm2PI2 + (n2 - n3 - 0.00645771823237902 * Math.sin(this.meanAnomalySun));
            this.moonLongitude = norm2PI + n2 + 0.10975677534091541 * Math.sin(n4) - n3 + 0.0037350045992678655 * Math.sin(2.0 * n4);
            this.moonLongitude += 0.011489502465878671 * Math.sin(2.0 * (this.moonLongitude - sunLongitude));
            final double n5 = norm2PI(5.559050068029439 - 9.242199067718253E-4 * n) - 0.0027925268031909274 * Math.sin(this.meanAnomalySun);
            final double sin = Math.sin(this.moonLongitude - n5);
            this.moonEclipLong = Math.atan2(sin * Math.cos(0.08980357792017056), Math.cos(this.moonLongitude - n5)) + n5;
            this.moonPosition = this.eclipticToEquatorial(this.moonEclipLong, Math.asin(sin * Math.sin(0.08980357792017056)));
        }
        return this.moonPosition;
    }
    
    public double getMoonAge() {
        this.getMoonPosition();
        return norm2PI(this.moonEclipLong - this.sunLongitude);
    }
    
    public double getMoonPhase() {
        return 0.5 * (1.0 - Math.cos(this.getMoonAge()));
    }
    
    public long getMoonTime(final double n, final boolean b) {
        return this.timeOfAngle(new AngleFunc() {
            final CalendarAstronomer this$0;
            
            public double eval() {
                return this.this$0.getMoonAge();
            }
        }, n, 29.530588853, 60000L, b);
    }
    
    public long getMoonTime(final MoonAge moonAge, final boolean b) {
        return this.getMoonTime(moonAge.value, b);
    }
    
    public long getMoonRiseSet(final boolean b) {
        return this.riseOrSet(new CoordFunc() {
            final CalendarAstronomer this$0;
            
            public Equatorial eval() {
                return this.this$0.getMoonPosition();
            }
        }, b, 0.009302604913129777, 0.009890199094634533, 60000L);
    }
    
    private long timeOfAngle(final AngleFunc angleFunc, final double n, final double n2, final long n3, final boolean b) {
        double eval = angleFunc.eval();
        double n5;
        double n4 = n5 = (norm2PI(n - eval) + (b ? 0.0 : -6.283185307179586)) * (n2 * 8.64E7) / 6.283185307179586;
        final long time = this.time;
        this.setTime(this.time + (long)n4);
        do {
            final double eval2 = angleFunc.eval();
            n4 = normPI(n - eval2) * Math.abs(n4 / normPI(eval2 - eval));
            if (Math.abs(n4) > Math.abs(n5)) {
                final long n6 = (long)(n2 * 8.64E7 / 8.0);
                this.setTime(time + (b ? n6 : (-n6)));
                return this.timeOfAngle(angleFunc, n, n2, n3, b);
            }
            n5 = n4;
            eval = eval2;
            this.setTime(this.time + (long)n4);
        } while (Math.abs(n4) > n3);
        return this.time;
    }
    
    private long riseOrSet(final CoordFunc coordFunc, final boolean b, final double n, final double n2, final long n3) {
        final double tan = Math.tan(this.fLatitude);
        long n4;
        Equatorial eval;
        do {
            eval = coordFunc.eval();
            final double acos = Math.acos(-tan * Math.tan(eval.declination));
            final long lstToUT = this.lstToUT(((b ? (6.283185307179586 - acos) : acos) + eval.ascension) * 24.0 / 6.283185307179586);
            n4 = lstToUT - this.time;
            this.setTime(lstToUT);
            int n5 = 0;
            ++n5;
        } while (Math.abs(n4) > n3);
        final double cos = Math.cos(eval.declination);
        final long n6 = (long)(240.0 * Math.asin(Math.sin(n / 2.0 + n2) / Math.sin(Math.acos(Math.sin(this.fLatitude) / cos))) * 57.29577951308232 / cos * 1000.0);
        return this.time + (b ? (-n6) : n6);
    }
    
    private static final double normalize(final double n, final double n2) {
        return n - n2 * Math.floor(n / n2);
    }
    
    private static final double norm2PI(final double n) {
        return normalize(n, 6.283185307179586);
    }
    
    private static final double normPI(final double n) {
        return normalize(n + 3.141592653589793, 6.283185307179586) - 3.141592653589793;
    }
    
    private double trueAnomaly(final double n, final double n2) {
        double n3 = n;
        double n4;
        do {
            n4 = n3 - n2 * Math.sin(n3) - n;
            n3 -= n4 / (1.0 - n2 * Math.cos(n3));
        } while (Math.abs(n4) > 1.0E-5);
        return 2.0 * Math.atan(Math.tan(n3 / 2.0) * Math.sqrt((1.0 + n2) / (1.0 - n2)));
    }
    
    private double eclipticObliquity() {
        if (this.eclipObliquity == Double.MIN_VALUE) {
            final double n = (this.getJulianDay() - 2451545.0) / 36525.0;
            this.eclipObliquity = 23.439292 - 0.013004166666666666 * n - 1.6666666666666665E-7 * n * n + 5.027777777777778E-7 * n * n * n;
            this.eclipObliquity *= 0.017453292519943295;
        }
        return this.eclipObliquity;
    }
    
    private void clearCache() {
        this.julianDay = Double.MIN_VALUE;
        this.julianCentury = Double.MIN_VALUE;
        this.sunLongitude = Double.MIN_VALUE;
        this.meanAnomalySun = Double.MIN_VALUE;
        this.moonLongitude = Double.MIN_VALUE;
        this.moonEclipLong = Double.MIN_VALUE;
        this.eclipObliquity = Double.MIN_VALUE;
        this.siderealTime = Double.MIN_VALUE;
        this.siderealT0 = Double.MIN_VALUE;
        this.moonPosition = null;
    }
    
    public String local(final long n) {
        return new Date(n - TimeZone.getDefault().getRawOffset()).toString();
    }
    
    private static String radToHms(final double n) {
        final int n2 = (int)(n * 3.819718634205488);
        final int n3 = (int)((n * 3.819718634205488 - n2) * 60.0);
        return Integer.toString(n2) + "h" + n3 + "m" + (int)((n * 3.819718634205488 - n2 - n3 / 60.0) * 3600.0) + "s";
    }
    
    private static String radToDms(final double n) {
        final int n2 = (int)(n * 57.29577951308232);
        final int n3 = (int)((n * 57.29577951308232 - n2) * 60.0);
        return Integer.toString(n2) + "°" + n3 + "'" + (int)((n * 57.29577951308232 - n2 - n3 / 60.0) * 3600.0) + "\"";
    }
    
    static String access$000(final double n) {
        return radToHms(n);
    }
    
    static String access$100(final double n) {
        return radToDms(n);
    }
    
    static {
        VERNAL_EQUINOX = new SolarLongitude(0.0);
        SUMMER_SOLSTICE = new SolarLongitude(1.5707963267948966);
        AUTUMN_EQUINOX = new SolarLongitude(3.141592653589793);
        WINTER_SOLSTICE = new SolarLongitude(4.71238898038469);
        NEW_MOON = new MoonAge(0.0);
        FIRST_QUARTER = new MoonAge(1.5707963267948966);
        FULL_MOON = new MoonAge(3.141592653589793);
        LAST_QUARTER = new MoonAge(4.71238898038469);
    }
    
    public static final class Horizon
    {
        public final double altitude;
        public final double azimuth;
        
        public Horizon(final double altitude, final double azimuth) {
            this.altitude = altitude;
            this.azimuth = azimuth;
        }
        
        @Override
        public String toString() {
            return Double.toString(this.altitude * 57.29577951308232) + "," + this.azimuth * 57.29577951308232;
        }
    }
    
    public static final class Equatorial
    {
        public final double ascension;
        public final double declination;
        
        public Equatorial(final double ascension, final double declination) {
            this.ascension = ascension;
            this.declination = declination;
        }
        
        @Override
        public String toString() {
            return Double.toString(this.ascension * 57.29577951308232) + "," + this.declination * 57.29577951308232;
        }
        
        public String toHmsString() {
            return CalendarAstronomer.access$000(this.ascension) + "," + CalendarAstronomer.access$100(this.declination);
        }
    }
    
    public static final class Ecliptic
    {
        public final double latitude;
        public final double longitude;
        
        public Ecliptic(final double latitude, final double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
        
        @Override
        public String toString() {
            return Double.toString(this.longitude * 57.29577951308232) + "," + this.latitude * 57.29577951308232;
        }
    }
    
    private interface CoordFunc
    {
        Equatorial eval();
    }
    
    private interface AngleFunc
    {
        double eval();
    }
    
    private static class MoonAge
    {
        double value;
        
        MoonAge(final double value) {
            this.value = value;
        }
    }
    
    private static class SolarLongitude
    {
        double value;
        
        SolarLongitude(final double value) {
            this.value = value;
        }
    }
}
