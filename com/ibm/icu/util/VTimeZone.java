package com.ibm.icu.util;

import java.io.*;
import com.ibm.icu.impl.*;
import java.util.*;

public class VTimeZone extends BasicTimeZone
{
    private static final long serialVersionUID = -6851467294127795902L;
    private BasicTimeZone tz;
    private List vtzlines;
    private String olsonzid;
    private String tzurl;
    private Date lastmod;
    private static String ICU_TZVERSION;
    private static final String ICU_TZINFO_PROP = "X-TZINFO";
    private static final int DEF_DSTSAVINGS = 3600000;
    private static final long DEF_TZSTARTTIME = 0L;
    private static final long MIN_TIME = Long.MIN_VALUE;
    private static final long MAX_TIME = Long.MAX_VALUE;
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";
    private static final String EQUALS_SIGN = "=";
    private static final String COMMA = ",";
    private static final String NEWLINE = "\r\n";
    private static final String ICAL_BEGIN_VTIMEZONE = "BEGIN:VTIMEZONE";
    private static final String ICAL_END_VTIMEZONE = "END:VTIMEZONE";
    private static final String ICAL_BEGIN = "BEGIN";
    private static final String ICAL_END = "END";
    private static final String ICAL_VTIMEZONE = "VTIMEZONE";
    private static final String ICAL_TZID = "TZID";
    private static final String ICAL_STANDARD = "STANDARD";
    private static final String ICAL_DAYLIGHT = "DAYLIGHT";
    private static final String ICAL_DTSTART = "DTSTART";
    private static final String ICAL_TZOFFSETFROM = "TZOFFSETFROM";
    private static final String ICAL_TZOFFSETTO = "TZOFFSETTO";
    private static final String ICAL_RDATE = "RDATE";
    private static final String ICAL_RRULE = "RRULE";
    private static final String ICAL_TZNAME = "TZNAME";
    private static final String ICAL_TZURL = "TZURL";
    private static final String ICAL_LASTMOD = "LAST-MODIFIED";
    private static final String ICAL_FREQ = "FREQ";
    private static final String ICAL_UNTIL = "UNTIL";
    private static final String ICAL_YEARLY = "YEARLY";
    private static final String ICAL_BYMONTH = "BYMONTH";
    private static final String ICAL_BYDAY = "BYDAY";
    private static final String ICAL_BYMONTHDAY = "BYMONTHDAY";
    private static final String[] ICAL_DOW_NAMES;
    private static final int[] MONTHLENGTH;
    private static final int INI = 0;
    private static final int VTZ = 1;
    private static final int TZI = 2;
    private static final int ERR = 3;
    private transient boolean isFrozen;
    
    public static VTimeZone create(final String s) {
        final VTimeZone vTimeZone = new VTimeZone(s);
        vTimeZone.tz = (BasicTimeZone)TimeZone.getTimeZone(s, 0);
        vTimeZone.olsonzid = vTimeZone.tz.getID();
        return vTimeZone;
    }
    
    public static VTimeZone create(final Reader reader) {
        final VTimeZone vTimeZone = new VTimeZone();
        if (vTimeZone.load(reader)) {
            return vTimeZone;
        }
        return null;
    }
    
    @Override
    public int getOffset(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return this.tz.getOffset(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void getOffset(final long n, final boolean b, final int[] array) {
        this.tz.getOffset(n, b, array);
    }
    
    @Override
    @Deprecated
    public void getOffsetFromLocal(final long n, final int n2, final int n3, final int[] array) {
        this.tz.getOffsetFromLocal(n, n2, n3, array);
    }
    
    @Override
    public int getRawOffset() {
        return this.tz.getRawOffset();
    }
    
    @Override
    public boolean inDaylightTime(final Date date) {
        return this.tz.inDaylightTime(date);
    }
    
    @Override
    public void setRawOffset(final int rawOffset) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
        }
        this.tz.setRawOffset(rawOffset);
    }
    
    @Override
    public boolean useDaylightTime() {
        return this.tz.useDaylightTime();
    }
    
    @Override
    public boolean observesDaylightTime() {
        return this.tz.observesDaylightTime();
    }
    
    @Override
    public boolean hasSameRules(final TimeZone timeZone) {
        if (this == timeZone) {
            return true;
        }
        if (timeZone instanceof VTimeZone) {
            return this.tz.hasSameRules(((VTimeZone)timeZone).tz);
        }
        return this.tz.hasSameRules(timeZone);
    }
    
    public String getTZURL() {
        return this.tzurl;
    }
    
    public void setTZURL(final String tzurl) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
        }
        this.tzurl = tzurl;
    }
    
    public Date getLastModified() {
        return this.lastmod;
    }
    
    public void setLastModified(final Date lastmod) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
        }
        this.lastmod = lastmod;
    }
    
    public void write(final Writer writer) throws IOException {
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);
        if (this.vtzlines != null) {
            for (final String s : this.vtzlines) {
                if (s.startsWith("TZURL:")) {
                    if (this.tzurl == null) {
                        continue;
                    }
                    bufferedWriter.write("TZURL");
                    bufferedWriter.write(":");
                    bufferedWriter.write(this.tzurl);
                    bufferedWriter.write("\r\n");
                }
                else if (s.startsWith("LAST-MODIFIED:")) {
                    if (this.lastmod == null) {
                        continue;
                    }
                    bufferedWriter.write("LAST-MODIFIED");
                    bufferedWriter.write(":");
                    bufferedWriter.write(getUTCDateTimeString(this.lastmod.getTime()));
                    bufferedWriter.write("\r\n");
                }
                else {
                    bufferedWriter.write(s);
                    bufferedWriter.write("\r\n");
                }
            }
            bufferedWriter.flush();
        }
        else {
            String[] array = null;
            if (this.olsonzid != null && VTimeZone.ICU_TZVERSION != null) {
                array = new String[] { "X-TZINFO:" + this.olsonzid + "[" + VTimeZone.ICU_TZVERSION + "]" };
            }
            this.writeZone(writer, this.tz, array);
        }
    }
    
    public void write(final Writer writer, final long n) throws IOException {
        final TimeZoneRule[] timeZoneRules = this.tz.getTimeZoneRules(n);
        final RuleBasedTimeZone ruleBasedTimeZone = new RuleBasedTimeZone(this.tz.getID(), (InitialTimeZoneRule)timeZoneRules[0]);
        for (int i = 1; i < timeZoneRules.length; ++i) {
            ruleBasedTimeZone.addTransitionRule(timeZoneRules[i]);
        }
        String[] array = null;
        if (this.olsonzid != null && VTimeZone.ICU_TZVERSION != null) {
            array = new String[] { "X-TZINFO:" + this.olsonzid + "[" + VTimeZone.ICU_TZVERSION + "/Partial@" + n + "]" };
        }
        this.writeZone(writer, ruleBasedTimeZone, array);
    }
    
    public void writeSimple(final Writer writer, final long n) throws IOException {
        final TimeZoneRule[] simpleTimeZoneRulesNear = this.tz.getSimpleTimeZoneRulesNear(n);
        final RuleBasedTimeZone ruleBasedTimeZone = new RuleBasedTimeZone(this.tz.getID(), (InitialTimeZoneRule)simpleTimeZoneRulesNear[0]);
        for (int i = 1; i < simpleTimeZoneRulesNear.length; ++i) {
            ruleBasedTimeZone.addTransitionRule(simpleTimeZoneRulesNear[i]);
        }
        String[] array = null;
        if (this.olsonzid != null && VTimeZone.ICU_TZVERSION != null) {
            array = new String[] { "X-TZINFO:" + this.olsonzid + "[" + VTimeZone.ICU_TZVERSION + "/Simple@" + n + "]" };
        }
        this.writeZone(writer, ruleBasedTimeZone, array);
    }
    
    @Override
    public TimeZoneTransition getNextTransition(final long n, final boolean b) {
        return this.tz.getNextTransition(n, b);
    }
    
    @Override
    public TimeZoneTransition getPreviousTransition(final long n, final boolean b) {
        return this.tz.getPreviousTransition(n, b);
    }
    
    @Override
    public boolean hasEquivalentTransitions(final TimeZone timeZone, final long n, final long n2) {
        return this == timeZone || this.tz.hasEquivalentTransitions(timeZone, n, n2);
    }
    
    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        return this.tz.getTimeZoneRules();
    }
    
    @Override
    public TimeZoneRule[] getTimeZoneRules(final long n) {
        return this.tz.getTimeZoneRules(n);
    }
    
    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }
    
    private VTimeZone() {
        this.olsonzid = null;
        this.tzurl = null;
        this.lastmod = null;
        this.isFrozen = false;
    }
    
    private VTimeZone(final String s) {
        super(s);
        this.olsonzid = null;
        this.tzurl = null;
        this.lastmod = null;
        this.isFrozen = false;
    }
    
    private boolean load(final Reader reader) {
        try {
            this.vtzlines = new LinkedList();
            int n = 0;
            int n2 = 0;
            boolean b = false;
            final StringBuilder sb = new StringBuilder();
            while (true) {
                final int read = reader.read();
                if (read == -1) {
                    if (n2 != 0 && sb.toString().startsWith("END:VTIMEZONE")) {
                        this.vtzlines.add(sb.toString());
                        b = true;
                        break;
                    }
                    break;
                }
                else {
                    if (read == 13) {
                        continue;
                    }
                    if (n != 0) {
                        if (read != 9 && read != 32) {
                            if (n2 != 0 && sb.length() > 0) {
                                this.vtzlines.add(sb.toString());
                            }
                            sb.setLength(0);
                            if (read != 10) {
                                sb.append((char)read);
                            }
                        }
                        n = 0;
                    }
                    else if (read == 10) {
                        n = 1;
                        if (n2 != 0) {
                            if (sb.toString().startsWith("END:VTIMEZONE")) {
                                this.vtzlines.add(sb.toString());
                                b = true;
                                break;
                            }
                            continue;
                        }
                        else {
                            if (!sb.toString().startsWith("BEGIN:VTIMEZONE")) {
                                continue;
                            }
                            this.vtzlines.add(sb.toString());
                            sb.setLength(0);
                            n2 = 1;
                            n = 0;
                        }
                    }
                    else {
                        sb.append((char)read);
                    }
                }
            }
            if (!b) {
                return false;
            }
        }
        catch (IOException ex) {
            return false;
        }
        return this.parse();
    }
    
    private boolean parse() {
        if (this.vtzlines == null || this.vtzlines.size() == 0) {
            return false;
        }
        String id = null;
        int n = 0;
        boolean b = false;
        String s = null;
        String s2 = null;
        String defaultTZName = null;
        String s3 = null;
        int n2 = 0;
        List<String> list = null;
        final ArrayList<TimeZoneRule> list2 = new ArrayList<TimeZoneRule>();
        int n3 = 0;
        int n4 = 0;
        long time = Long.MAX_VALUE;
        for (final String s4 : this.vtzlines) {
            final int index = s4.indexOf(":");
            if (index < 0) {
                continue;
            }
            final String substring = s4.substring(0, index);
            final String substring2 = s4.substring(index + 1);
            switch (n) {
                case 0: {
                    if (substring.equals("BEGIN") && substring2.equals("VTIMEZONE")) {
                        n = 1;
                        break;
                    }
                    break;
                }
                case 1: {
                    if (substring.equals("TZID")) {
                        id = substring2;
                        break;
                    }
                    if (substring.equals("TZURL")) {
                        this.tzurl = substring2;
                        break;
                    }
                    if (substring.equals("LAST-MODIFIED")) {
                        this.lastmod = new Date(parseDateTimeString(substring2, 0));
                        break;
                    }
                    if (substring.equals("BEGIN")) {
                        final boolean equals = substring2.equals("DAYLIGHT");
                        if (!substring2.equals("STANDARD") && !equals) {
                            n = 3;
                            break;
                        }
                        if (id == null) {
                            n = 3;
                            break;
                        }
                        list = null;
                        n2 = 0;
                        s = null;
                        s2 = null;
                        defaultTZName = null;
                        b = equals;
                        n = 2;
                        break;
                    }
                    else {
                        if (substring.equals("END")) {
                            break;
                        }
                        break;
                    }
                    break;
                }
                case 2: {
                    if (substring.equals("DTSTART")) {
                        s3 = substring2;
                        break;
                    }
                    if (substring.equals("TZNAME")) {
                        defaultTZName = substring2;
                        break;
                    }
                    if (substring.equals("TZOFFSETFROM")) {
                        s = substring2;
                        break;
                    }
                    if (substring.equals("TZOFFSETTO")) {
                        s2 = substring2;
                        break;
                    }
                    if (substring.equals("RDATE")) {
                        if (n2 != 0) {
                            n = 3;
                            break;
                        }
                        if (list == null) {
                            list = new LinkedList<String>();
                        }
                        final StringTokenizer stringTokenizer = new StringTokenizer(substring2, ",");
                        while (stringTokenizer.hasMoreTokens()) {
                            list.add(stringTokenizer.nextToken());
                        }
                        break;
                    }
                    else if (substring.equals("RRULE")) {
                        if (n2 == 0 && list != null) {
                            n = 3;
                            break;
                        }
                        if (list == null) {
                            list = new LinkedList<String>();
                        }
                        n2 = 1;
                        list.add(substring2);
                        break;
                    }
                    else {
                        if (!substring.equals("END")) {
                            break;
                        }
                        if (s3 == null || s == null || s2 == null) {
                            n = 3;
                            break;
                        }
                        if (defaultTZName == null) {
                            defaultTZName = getDefaultTZName(id, b);
                        }
                        TimeZoneRule timeZoneRule = null;
                        try {
                            final int offsetStrToMillis = offsetStrToMillis(s);
                            final int offsetStrToMillis2 = offsetStrToMillis(s2);
                            int n5;
                            int n6;
                            if (b) {
                                if (offsetStrToMillis2 - offsetStrToMillis > 0) {
                                    n5 = offsetStrToMillis;
                                    n6 = offsetStrToMillis2 - offsetStrToMillis;
                                }
                                else {
                                    n5 = offsetStrToMillis2 - 3600000;
                                    n6 = 3600000;
                                }
                            }
                            else {
                                n5 = offsetStrToMillis2;
                                n6 = 0;
                            }
                            final long dateTimeString = parseDateTimeString(s3, offsetStrToMillis);
                            if (n2 != 0) {
                                timeZoneRule = createRuleByRRULE(defaultTZName, n5, n6, dateTimeString, list, offsetStrToMillis);
                            }
                            else {
                                timeZoneRule = createRuleByRDATE(defaultTZName, n5, n6, dateTimeString, list, offsetStrToMillis);
                            }
                            if (timeZoneRule != null) {
                                final Date firstStart = timeZoneRule.getFirstStart(offsetStrToMillis, 0);
                                if (firstStart.getTime() < time) {
                                    time = firstStart.getTime();
                                    if (n6 > 0) {
                                        n3 = offsetStrToMillis;
                                        n4 = 0;
                                    }
                                    else if (offsetStrToMillis - offsetStrToMillis2 == 3600000) {
                                        n3 = offsetStrToMillis - 3600000;
                                        n4 = 3600000;
                                    }
                                    else {
                                        n3 = offsetStrToMillis;
                                        n4 = 0;
                                    }
                                }
                            }
                        }
                        catch (IllegalArgumentException ex) {}
                        if (timeZoneRule == null) {
                            n = 3;
                            break;
                        }
                        list2.add(timeZoneRule);
                        n = 1;
                        break;
                    }
                    break;
                }
            }
            if (n == 3) {
                this.vtzlines = null;
                return false;
            }
        }
        if (list2.size() == 0) {
            return false;
        }
        final RuleBasedTimeZone tz = new RuleBasedTimeZone(id, new InitialTimeZoneRule(getDefaultTZName(id, false), n3, n4));
        int n7 = -1;
        int n8 = 0;
        for (int i = 0; i < list2.size(); ++i) {
            final AnnualTimeZoneRule annualTimeZoneRule = list2.get(i);
            if (annualTimeZoneRule instanceof AnnualTimeZoneRule && annualTimeZoneRule.getEndYear() == Integer.MAX_VALUE) {
                ++n8;
                n7 = i;
            }
        }
        if (n8 > 2) {
            return false;
        }
        if (n8 == 1) {
            if (list2.size() == 1) {
                list2.clear();
            }
            else {
                final AnnualTimeZoneRule annualTimeZoneRule2 = list2.get(n7);
                final int rawOffset = annualTimeZoneRule2.getRawOffset();
                final int dstSavings = annualTimeZoneRule2.getDSTSavings();
                Date date2;
                final Date date = date2 = annualTimeZoneRule2.getFirstStart(n3, n4);
                for (int j = 0; j < list2.size(); ++j) {
                    if (n7 != j) {
                        final AnnualTimeZoneRule annualTimeZoneRule3 = list2.get(j);
                        final Date finalStart = annualTimeZoneRule3.getFinalStart(rawOffset, dstSavings);
                        if (finalStart.after(date2)) {
                            date2 = annualTimeZoneRule2.getNextStart(finalStart.getTime(), annualTimeZoneRule3.getRawOffset(), annualTimeZoneRule3.getDSTSavings(), false);
                        }
                    }
                }
                TimeZoneRule timeZoneRule2;
                if (date2 == date) {
                    timeZoneRule2 = new TimeArrayTimeZoneRule(annualTimeZoneRule2.getName(), annualTimeZoneRule2.getRawOffset(), annualTimeZoneRule2.getDSTSavings(), new long[] { date.getTime() }, 2);
                }
                else {
                    timeZoneRule2 = new AnnualTimeZoneRule(annualTimeZoneRule2.getName(), annualTimeZoneRule2.getRawOffset(), annualTimeZoneRule2.getDSTSavings(), annualTimeZoneRule2.getRule(), annualTimeZoneRule2.getStartYear(), Grego.timeToFields(date2.getTime(), null)[0]);
                }
                list2.set(n7, timeZoneRule2);
            }
        }
        final Iterator<Object> iterator2 = list2.iterator();
        while (iterator2.hasNext()) {
            tz.addTransitionRule(iterator2.next());
        }
        this.tz = tz;
        this.setID(id);
        return true;
    }
    
    private static String getDefaultTZName(final String s, final boolean b) {
        if (b) {
            return s + "(DST)";
        }
        return s + "(STD)";
    }
    
    private static TimeZoneRule createRuleByRRULE(final String s, final int n, final int n2, final long n3, final List list, final int n4) {
        if (list == null || list.size() == 0) {
            return null;
        }
        final String s2 = list.get(0);
        long[] array = { 0L };
        final int[] rrule = parseRRULE(s2, array);
        if (rrule == null) {
            return null;
        }
        int n5 = rrule[0];
        final int n6 = rrule[1];
        final int n7 = rrule[2];
        int n8 = rrule[3];
        if (list.size() == 1) {
            if (rrule.length > 4) {
                if (rrule.length != 10 || n5 == -1 || n6 == 0) {
                    return null;
                }
                int n9 = 31;
                final int[] array2 = new int[7];
                for (int i = 0; i < 7; ++i) {
                    array2[i] = rrule[3 + i];
                    array2[i] = ((array2[i] > 0) ? array2[i] : (VTimeZone.MONTHLENGTH[n5] + array2[i] + 1));
                    n9 = ((array2[i] < n9) ? array2[i] : n9);
                }
                for (int j = 1; j < 7; ++j) {
                    boolean b = false;
                    for (int k = 0; k < 7; ++k) {
                        if (array2[k] == n9 + j) {
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        return null;
                    }
                }
                n8 = n9;
            }
        }
        else {
            if (n5 == -1 || n6 == 0 || n8 == 0) {
                return null;
            }
            if (list.size() > 7) {
                return null;
            }
            int n10 = n5;
            int n11 = rrule.length - 3;
            int n12 = 31;
            for (int l = 0; l < n11; ++l) {
                final int n13 = rrule[3 + l];
                final int n14 = (n13 > 0) ? n13 : (VTimeZone.MONTHLENGTH[n5] + n13 + 1);
                n12 = ((n14 < n12) ? n14 : n12);
            }
            int n15 = -1;
            for (int n16 = 1; n16 < list.size(); ++n16) {
                final String s3 = list.get(n16);
                final long[] array3 = { 0L };
                final int[] rrule2 = parseRRULE(s3, array3);
                if (array3[0] > array[0]) {
                    array = array3;
                }
                if (rrule2[0] == -1 || rrule2[1] == 0 || rrule2[3] == 0) {
                    return null;
                }
                final int n17 = rrule2.length - 3;
                if (n11 + n17 > 7) {
                    return null;
                }
                if (rrule2[1] != n6) {
                    return null;
                }
                if (rrule2[0] != n5) {
                    if (n15 == -1) {
                        final int n18 = rrule2[0] - n5;
                        if (n18 == -11 || n18 == -1) {
                            n15 = (n10 = rrule2[0]);
                            n12 = 31;
                        }
                        else {
                            if (n18 != 11 && n18 != 1) {
                                return null;
                            }
                            n15 = rrule2[0];
                        }
                    }
                    else if (rrule2[0] != n5 && rrule2[0] != n15) {
                        return null;
                    }
                }
                if (rrule2[0] == n10) {
                    for (int n19 = 0; n19 < n17; ++n19) {
                        final int n20 = rrule2[3 + n19];
                        final int n21 = (n20 > 0) ? n20 : (VTimeZone.MONTHLENGTH[rrule2[0]] + n20 + 1);
                        n12 = ((n21 < n12) ? n21 : n12);
                    }
                }
                n11 += n17;
            }
            if (n11 != 7) {
                return null;
            }
            n5 = n10;
            n8 = n12;
        }
        final int[] timeToFields = Grego.timeToFields(n3 + n4, null);
        final int n22 = timeToFields[0];
        if (n5 == -1) {
            n5 = timeToFields[1];
        }
        if (n6 == 0 && n7 == 0 && n8 == 0) {
            n8 = timeToFields[2];
        }
        final int n23 = timeToFields[5];
        int n24 = Integer.MAX_VALUE;
        if (array[0] != Long.MIN_VALUE) {
            Grego.timeToFields(array[0], timeToFields);
            n24 = timeToFields[0];
        }
        DateTimeRule dateTimeRule;
        if (n6 == 0 && n7 == 0 && n8 != 0) {
            dateTimeRule = new DateTimeRule(n5, n8, n23, 0);
        }
        else if (n6 != 0 && n7 != 0 && n8 == 0) {
            dateTimeRule = new DateTimeRule(n5, n7, n6, n23, 0);
        }
        else {
            if (n6 == 0 || n7 != 0 || n8 == 0) {
                return null;
            }
            dateTimeRule = new DateTimeRule(n5, n8, n6, true, n23, 0);
        }
        return new AnnualTimeZoneRule(s, n, n2, dateTimeRule, n22, n24);
    }
    
    private static int[] parseRRULE(final String s, final long[] array) {
        int n = -1;
        int n2 = 0;
        int n3 = 0;
        int[] array2 = null;
        long dateTimeString = Long.MIN_VALUE;
        boolean b = false;
        boolean b2 = false;
        final StringTokenizer stringTokenizer = new StringTokenizer(s, ";");
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            final int index = nextToken.indexOf("=");
            if (index == -1) {
                b2 = true;
                break;
            }
            final String substring = nextToken.substring(0, index);
            String s2 = nextToken.substring(index + 1);
            if (substring.equals("FREQ")) {
                if (!s2.equals("YEARLY")) {
                    b2 = true;
                    break;
                }
                b = true;
            }
            else {
                if (substring.equals("UNTIL")) {
                    try {
                        dateTimeString = parseDateTimeString(s2, 0);
                        continue;
                    }
                    catch (IllegalArgumentException ex) {
                        b2 = true;
                        break;
                    }
                }
                if (substring.equals("BYMONTH")) {
                    if (s2.length() > 2) {
                        b2 = true;
                        break;
                    }
                    try {
                        n = Integer.parseInt(s2) - 1;
                        if (n < 0 || n >= 12) {
                            b2 = true;
                            break;
                        }
                        continue;
                    }
                    catch (NumberFormatException ex2) {
                        b2 = true;
                        break;
                    }
                }
                if (substring.equals("BYDAY")) {
                    final int length = s2.length();
                    if (length < 2 || length > 4) {
                        b2 = true;
                        break;
                    }
                    if (length > 2) {
                        int n4 = 1;
                        if (s2.charAt(0) == '+') {
                            n4 = 1;
                        }
                        else if (s2.charAt(0) == '-') {
                            n4 = -1;
                        }
                        else if (length == 4) {
                            b2 = true;
                            break;
                        }
                        try {
                            final int int1 = Integer.parseInt(s2.substring(length - 3, length - 2));
                            if (int1 == 0 || int1 > 4) {
                                b2 = true;
                                break;
                            }
                            n3 = int1 * n4;
                        }
                        catch (NumberFormatException ex3) {
                            b2 = true;
                            break;
                        }
                        s2 = s2.substring(length - 2);
                    }
                    int n5;
                    for (n5 = 0; n5 < VTimeZone.ICAL_DOW_NAMES.length && !s2.equals(VTimeZone.ICAL_DOW_NAMES[n5]); ++n5) {}
                    if (n5 >= VTimeZone.ICAL_DOW_NAMES.length) {
                        b2 = true;
                        break;
                    }
                    n2 = n5 + 1;
                }
                else {
                    if (!substring.equals("BYMONTHDAY")) {
                        continue;
                    }
                    final StringTokenizer stringTokenizer2 = new StringTokenizer(s2, ",");
                    array2 = new int[stringTokenizer2.countTokens()];
                    int n6 = 0;
                    while (stringTokenizer2.hasMoreTokens()) {
                        try {
                            array2[n6++] = Integer.parseInt(stringTokenizer2.nextToken());
                            continue;
                        }
                        catch (NumberFormatException ex4) {
                            b2 = true;
                        }
                        break;
                    }
                }
            }
        }
        if (b2) {
            return null;
        }
        if (!b) {
            return null;
        }
        array[0] = dateTimeString;
        int[] array3;
        if (array2 == null) {
            array3 = new int[] { 0, 0, 0, 0 };
        }
        else {
            array3 = new int[3 + array2.length];
            for (int i = 0; i < array2.length; ++i) {
                array3[3 + i] = array2[i];
            }
        }
        array3[0] = n;
        array3[1] = n2;
        array3[2] = n3;
        return array3;
    }
    
    private static TimeZoneRule createRuleByRDATE(final String s, final int n, final int n2, final long n3, final List list, final int n4) {
        long[] array;
        if (list == null || list.size() == 0) {
            array = new long[] { n3 };
        }
        else {
            array = new long[list.size()];
            int n5 = 0;
            try {
                final Iterator<String> iterator = list.iterator();
                while (iterator.hasNext()) {
                    array[n5++] = parseDateTimeString(iterator.next(), n4);
                }
            }
            catch (IllegalArgumentException ex) {
                return null;
            }
        }
        return new TimeArrayTimeZoneRule(s, n, n2, array, 2);
    }
    
    private void writeZone(final Writer writer, final BasicTimeZone basicTimeZone, final String[] array) throws IOException {
        this.writeHeader(writer);
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i] != null) {
                    writer.write(array[i]);
                    writer.write("\r\n");
                }
            }
        }
        long time = Long.MIN_VALUE;
        String s = null;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        long n9 = 0L;
        long n10 = 0L;
        int n11 = 0;
        AnnualTimeZoneRule annualTimeZoneRule = null;
        String s2 = null;
        int n12 = 0;
        int n13 = 0;
        int n14 = 0;
        int n15 = 0;
        int n16 = 0;
        int n17 = 0;
        int n18 = 0;
        int n19 = 0;
        long n20 = 0L;
        long n21 = 0L;
        int n22 = 0;
        AnnualTimeZoneRule annualTimeZoneRule2 = null;
        final int[] array2 = new int[6];
        boolean b = false;
        while (true) {
            final TimeZoneTransition nextTransition = basicTimeZone.getNextTransition(time, false);
            if (nextTransition == null) {
                break;
            }
            b = true;
            time = nextTransition.getTime();
            final String name = nextTransition.getTo().getName();
            final boolean b2 = nextTransition.getTo().getDSTSavings() != 0;
            final int n23 = nextTransition.getFrom().getRawOffset() + nextTransition.getFrom().getDSTSavings();
            final int dstSavings = nextTransition.getFrom().getDSTSavings();
            final int n24 = nextTransition.getTo().getRawOffset() + nextTransition.getTo().getDSTSavings();
            Grego.timeToFields(nextTransition.getTime() + n23, array2);
            final int dayOfWeekInMonth = Grego.getDayOfWeekInMonth(array2[0], array2[1], array2[2]);
            final int n25 = array2[0];
            int n26 = 0;
            if (b2) {
                if (annualTimeZoneRule == null && nextTransition.getTo() instanceof AnnualTimeZoneRule && ((AnnualTimeZoneRule)nextTransition.getTo()).getEndYear() == Integer.MAX_VALUE) {
                    annualTimeZoneRule = (AnnualTimeZoneRule)nextTransition.getTo();
                }
                if (n11 > 0) {
                    if (n25 == n4 + n11 && name.equals(s) && n == n23 && n3 == n24 && n5 == array2[1] && n6 == array2[3] && n7 == dayOfWeekInMonth && n8 == array2[5]) {
                        n10 = time;
                        ++n11;
                        n26 = 1;
                    }
                    if (n26 == 0) {
                        if (n11 == 1) {
                            writeZonePropsByTime(writer, true, s, n, n3, n9, true);
                        }
                        else {
                            writeZonePropsByDOW(writer, true, s, n, n3, n5, n7, n6, n9, n10);
                        }
                    }
                }
                if (n26 == 0) {
                    s = name;
                    n = n23;
                    n2 = dstSavings;
                    n3 = n24;
                    n4 = n25;
                    n5 = array2[1];
                    n6 = array2[3];
                    n7 = dayOfWeekInMonth;
                    n8 = array2[5];
                    n10 = (n9 = time);
                    n11 = 1;
                }
                if (annualTimeZoneRule2 != null && annualTimeZoneRule != null) {
                    break;
                }
                continue;
            }
            else {
                if (annualTimeZoneRule2 == null && nextTransition.getTo() instanceof AnnualTimeZoneRule && ((AnnualTimeZoneRule)nextTransition.getTo()).getEndYear() == Integer.MAX_VALUE) {
                    annualTimeZoneRule2 = (AnnualTimeZoneRule)nextTransition.getTo();
                }
                if (n22 > 0) {
                    if (n25 == n15 + n22 && name.equals(s2) && n12 == n23 && n14 == n24 && n16 == array2[1] && n17 == array2[3] && n18 == dayOfWeekInMonth && n19 == array2[5]) {
                        n21 = time;
                        ++n22;
                        n26 = 1;
                    }
                    if (n26 == 0) {
                        if (n22 == 1) {
                            writeZonePropsByTime(writer, false, s2, n12, n14, n20, true);
                        }
                        else {
                            writeZonePropsByDOW(writer, false, s2, n12, n14, n16, n18, n17, n20, n21);
                        }
                    }
                }
                if (n26 == 0) {
                    s2 = name;
                    n12 = n23;
                    n13 = dstSavings;
                    n14 = n24;
                    n15 = n25;
                    n16 = array2[1];
                    n17 = array2[3];
                    n18 = dayOfWeekInMonth;
                    n19 = array2[5];
                    n21 = (n20 = time);
                    n22 = 1;
                }
                if (annualTimeZoneRule2 != null && annualTimeZoneRule != null) {
                    break;
                }
                continue;
            }
        }
        if (!b) {
            final int offset = basicTimeZone.getOffset(0L);
            final boolean b3 = offset != basicTimeZone.getRawOffset();
            writeZonePropsByTime(writer, b3, getDefaultTZName(basicTimeZone.getID(), b3), offset, offset, 0L - offset, false);
        }
        else {
            if (n11 > 0) {
                if (annualTimeZoneRule == null) {
                    if (n11 == 1) {
                        writeZonePropsByTime(writer, true, s, n, n3, n9, true);
                    }
                    else {
                        writeZonePropsByDOW(writer, true, s, n, n3, n5, n7, n6, n9, n10);
                    }
                }
                else if (n11 == 1) {
                    writeFinalRule(writer, true, annualTimeZoneRule, n - n2, n2, n9);
                }
                else if (isEquivalentDateRule(n5, n7, n6, annualTimeZoneRule.getRule())) {
                    writeZonePropsByDOW(writer, true, s, n, n3, n5, n7, n6, n9, Long.MAX_VALUE);
                }
                else {
                    writeZonePropsByDOW(writer, true, s, n, n3, n5, n7, n6, n9, n10);
                    writeFinalRule(writer, true, annualTimeZoneRule, n - n2, n2, n9);
                }
            }
            if (n22 > 0) {
                if (annualTimeZoneRule2 == null) {
                    if (n22 == 1) {
                        writeZonePropsByTime(writer, false, s2, n12, n14, n20, true);
                    }
                    else {
                        writeZonePropsByDOW(writer, false, s2, n12, n14, n16, n18, n17, n20, n21);
                    }
                }
                else if (n22 == 1) {
                    writeFinalRule(writer, false, annualTimeZoneRule2, n12 - n13, n13, n20);
                }
                else if (isEquivalentDateRule(n16, n18, n17, annualTimeZoneRule2.getRule())) {
                    writeZonePropsByDOW(writer, false, s2, n12, n14, n16, n18, n17, n20, Long.MAX_VALUE);
                }
                else {
                    writeZonePropsByDOW(writer, false, s2, n12, n14, n16, n18, n17, n20, n21);
                    writeFinalRule(writer, false, annualTimeZoneRule2, n12 - n13, n13, n20);
                }
            }
        }
        writeFooter(writer);
    }
    
    private static boolean isEquivalentDateRule(final int n, final int n2, final int n3, final DateTimeRule dateTimeRule) {
        if (n != dateTimeRule.getRuleMonth() || n3 != dateTimeRule.getRuleDayOfWeek()) {
            return false;
        }
        if (dateTimeRule.getTimeRuleType() != 0) {
            return false;
        }
        if (dateTimeRule.getDateRuleType() == 1 && dateTimeRule.getRuleWeekInMonth() == n2) {
            return true;
        }
        final int ruleDayOfMonth = dateTimeRule.getRuleDayOfMonth();
        if (dateTimeRule.getDateRuleType() == 2) {
            if (ruleDayOfMonth % 7 == 1 && (ruleDayOfMonth + 6) / 7 == n2) {
                return true;
            }
            if (n != 1 && (VTimeZone.MONTHLENGTH[n] - ruleDayOfMonth) % 7 == 6 && n2 == -1 * ((VTimeZone.MONTHLENGTH[n] - ruleDayOfMonth + 1) / 7)) {
                return true;
            }
        }
        if (dateTimeRule.getDateRuleType() == 3) {
            if (ruleDayOfMonth % 7 == 0 && ruleDayOfMonth / 7 == n2) {
                return true;
            }
            if (n != 1 && (VTimeZone.MONTHLENGTH[n] - ruleDayOfMonth) % 7 == 0 && n2 == -1 * ((VTimeZone.MONTHLENGTH[n] - ruleDayOfMonth) / 7 + 1)) {
                return true;
            }
        }
        return false;
    }
    
    private static void writeZonePropsByTime(final Writer writer, final boolean b, final String s, final int n, final int n2, final long n3, final boolean b2) throws IOException {
        beginZoneProps(writer, b, s, n, n2, n3);
        if (b2) {
            writer.write("RDATE");
            writer.write(":");
            writer.write(getDateTimeString(n3 + n));
            writer.write("\r\n");
        }
        endZoneProps(writer, b);
    }
    
    private static void writeZonePropsByDOM(final Writer writer, final boolean b, final String s, final int n, final int n2, final int n3, final int n4, final long n5, final long n6) throws IOException {
        beginZoneProps(writer, b, s, n, n2, n5);
        beginRRULE(writer, n3);
        writer.write("BYMONTHDAY");
        writer.write("=");
        writer.write(Integer.toString(n4));
        if (n6 != Long.MAX_VALUE) {
            appendUNTIL(writer, getDateTimeString(n6 + n));
        }
        writer.write("\r\n");
        endZoneProps(writer, b);
    }
    
    private static void writeZonePropsByDOW(final Writer writer, final boolean b, final String s, final int n, final int n2, final int n3, final int n4, final int n5, final long n6, final long n7) throws IOException {
        beginZoneProps(writer, b, s, n, n2, n6);
        beginRRULE(writer, n3);
        writer.write("BYDAY");
        writer.write("=");
        writer.write(Integer.toString(n4));
        writer.write(VTimeZone.ICAL_DOW_NAMES[n5 - 1]);
        if (n7 != Long.MAX_VALUE) {
            appendUNTIL(writer, getDateTimeString(n7 + n));
        }
        writer.write("\r\n");
        endZoneProps(writer, b);
    }
    
    private static void writeZonePropsByDOW_GEQ_DOM(final Writer writer, final boolean b, final String s, final int n, final int n2, final int n3, final int n4, final int n5, final long n6, final long n7) throws IOException {
        if (n4 % 7 == 1) {
            writeZonePropsByDOW(writer, b, s, n, n2, n3, (n4 + 6) / 7, n5, n6, n7);
        }
        else if (n3 != 1 && (VTimeZone.MONTHLENGTH[n3] - n4) % 7 == 6) {
            writeZonePropsByDOW(writer, b, s, n, n2, n3, -1 * ((VTimeZone.MONTHLENGTH[n3] - n4 + 1) / 7), n5, n6, n7);
        }
        else {
            beginZoneProps(writer, b, s, n, n2, n6);
            int n8 = n4;
            int n9 = 7;
            if (n4 <= 0) {
                final int n10 = 1 - n4;
                n9 -= n10;
                writeZonePropsByDOW_GEQ_DOM_sub(writer, (n3 - 1 < 0) ? 11 : (n3 - 1), -n10, n5, n10, Long.MAX_VALUE, n);
                n8 = 1;
            }
            else if (n4 + 6 > VTimeZone.MONTHLENGTH[n3]) {
                final int n11 = n4 + 6 - VTimeZone.MONTHLENGTH[n3];
                n9 -= n11;
                writeZonePropsByDOW_GEQ_DOM_sub(writer, (n3 + 1 > 11) ? 0 : (n3 + 1), 1, n5, n11, Long.MAX_VALUE, n);
            }
            writeZonePropsByDOW_GEQ_DOM_sub(writer, n3, n8, n5, n9, n7, n);
            endZoneProps(writer, b);
        }
    }
    
    private static void writeZonePropsByDOW_GEQ_DOM_sub(final Writer writer, final int n, final int n2, final int n3, final int n4, final long n5, final int n6) throws IOException {
        int n7 = n2;
        final boolean b = n == 1;
        if (n2 < 0 && !b) {
            n7 = VTimeZone.MONTHLENGTH[n] + n2 + 1;
        }
        beginRRULE(writer, n);
        writer.write("BYDAY");
        writer.write("=");
        writer.write(VTimeZone.ICAL_DOW_NAMES[n3 - 1]);
        writer.write(";");
        writer.write("BYMONTHDAY");
        writer.write("=");
        writer.write(Integer.toString(n7));
        for (int i = 1; i < n4; ++i) {
            writer.write(",");
            writer.write(Integer.toString(n7 + i));
        }
        if (n5 != Long.MAX_VALUE) {
            appendUNTIL(writer, getDateTimeString(n5 + n6));
        }
        writer.write("\r\n");
    }
    
    private static void writeZonePropsByDOW_LEQ_DOM(final Writer writer, final boolean b, final String s, final int n, final int n2, final int n3, final int n4, final int n5, final long n6, final long n7) throws IOException {
        if (n4 % 7 == 0) {
            writeZonePropsByDOW(writer, b, s, n, n2, n3, n4 / 7, n5, n6, n7);
        }
        else if (n3 != 1 && (VTimeZone.MONTHLENGTH[n3] - n4) % 7 == 0) {
            writeZonePropsByDOW(writer, b, s, n, n2, n3, -1 * ((VTimeZone.MONTHLENGTH[n3] - n4) / 7 + 1), n5, n6, n7);
        }
        else if (n3 == 1 && n4 == 29) {
            writeZonePropsByDOW(writer, b, s, n, n2, 1, -1, n5, n6, n7);
        }
        else {
            writeZonePropsByDOW_GEQ_DOM(writer, b, s, n, n2, n3, n4 - 6, n5, n6, n7);
        }
    }
    
    private static void writeFinalRule(final Writer writer, final boolean b, final AnnualTimeZoneRule annualTimeZoneRule, final int n, final int n2, long n3) throws IOException {
        final DateTimeRule wallTimeRule = toWallTimeRule(annualTimeZoneRule.getRule(), n, n2);
        final int ruleMillisInDay = wallTimeRule.getRuleMillisInDay();
        if (ruleMillisInDay < 0) {
            n3 += 0 - ruleMillisInDay;
        }
        else if (ruleMillisInDay >= 86400000) {
            n3 -= ruleMillisInDay - 86399999;
        }
        final int n4 = annualTimeZoneRule.getRawOffset() + annualTimeZoneRule.getDSTSavings();
        switch (wallTimeRule.getDateRuleType()) {
            case 0: {
                writeZonePropsByDOM(writer, b, annualTimeZoneRule.getName(), n + n2, n4, wallTimeRule.getRuleMonth(), wallTimeRule.getRuleDayOfMonth(), n3, Long.MAX_VALUE);
                break;
            }
            case 1: {
                writeZonePropsByDOW(writer, b, annualTimeZoneRule.getName(), n + n2, n4, wallTimeRule.getRuleMonth(), wallTimeRule.getRuleWeekInMonth(), wallTimeRule.getRuleDayOfWeek(), n3, Long.MAX_VALUE);
                break;
            }
            case 2: {
                writeZonePropsByDOW_GEQ_DOM(writer, b, annualTimeZoneRule.getName(), n + n2, n4, wallTimeRule.getRuleMonth(), wallTimeRule.getRuleDayOfMonth(), wallTimeRule.getRuleDayOfWeek(), n3, Long.MAX_VALUE);
                break;
            }
            case 3: {
                writeZonePropsByDOW_LEQ_DOM(writer, b, annualTimeZoneRule.getName(), n + n2, n4, wallTimeRule.getRuleMonth(), wallTimeRule.getRuleDayOfMonth(), wallTimeRule.getRuleDayOfWeek(), n3, Long.MAX_VALUE);
                break;
            }
        }
    }
    
    private static DateTimeRule toWallTimeRule(final DateTimeRule dateTimeRule, final int n, final int n2) {
        if (dateTimeRule.getTimeRuleType() == 0) {
            return dateTimeRule;
        }
        int ruleMillisInDay = dateTimeRule.getRuleMillisInDay();
        if (dateTimeRule.getTimeRuleType() == 2) {
            ruleMillisInDay += n + n2;
        }
        else if (dateTimeRule.getTimeRuleType() == 1) {
            ruleMillisInDay += n2;
        }
        int n3 = 0;
        if (ruleMillisInDay < 0) {
            n3 = -1;
            ruleMillisInDay += 86400000;
        }
        else if (ruleMillisInDay >= 86400000) {
            n3 = 1;
            ruleMillisInDay -= 86400000;
        }
        int ruleMonth = dateTimeRule.getRuleMonth();
        int ruleDayOfMonth = dateTimeRule.getRuleDayOfMonth();
        int ruleDayOfWeek = dateTimeRule.getRuleDayOfWeek();
        int dateRuleType = dateTimeRule.getDateRuleType();
        if (n3 != 0) {
            if (dateRuleType == 1) {
                final int ruleWeekInMonth = dateTimeRule.getRuleWeekInMonth();
                if (ruleWeekInMonth > 0) {
                    dateRuleType = 2;
                    ruleDayOfMonth = 7 * (ruleWeekInMonth - 1) + 1;
                }
                else {
                    dateRuleType = 3;
                    ruleDayOfMonth = VTimeZone.MONTHLENGTH[ruleMonth] + 7 * (ruleWeekInMonth + 1);
                }
            }
            ruleDayOfMonth += n3;
            if (ruleDayOfMonth == 0) {
                ruleMonth = ((--ruleMonth < 0) ? 11 : ruleMonth);
                ruleDayOfMonth = VTimeZone.MONTHLENGTH[ruleMonth];
            }
            else if (ruleDayOfMonth > VTimeZone.MONTHLENGTH[ruleMonth]) {
                ruleMonth = ((++ruleMonth > 11) ? 0 : ruleMonth);
                ruleDayOfMonth = 1;
            }
            if (dateRuleType != 0) {
                ruleDayOfWeek += n3;
                if (ruleDayOfWeek < 1) {
                    ruleDayOfWeek = 7;
                }
                else if (ruleDayOfWeek > 7) {
                    ruleDayOfWeek = 1;
                }
            }
        }
        DateTimeRule dateTimeRule2;
        if (dateRuleType == 0) {
            dateTimeRule2 = new DateTimeRule(ruleMonth, ruleDayOfMonth, ruleMillisInDay, 0);
        }
        else {
            dateTimeRule2 = new DateTimeRule(ruleMonth, ruleDayOfMonth, ruleDayOfWeek, dateRuleType == 2, ruleMillisInDay, 0);
        }
        return dateTimeRule2;
    }
    
    private static void beginZoneProps(final Writer writer, final boolean b, final String s, final int n, final int n2, final long n3) throws IOException {
        writer.write("BEGIN");
        writer.write(":");
        if (b) {
            writer.write("DAYLIGHT");
        }
        else {
            writer.write("STANDARD");
        }
        writer.write("\r\n");
        writer.write("TZOFFSETTO");
        writer.write(":");
        writer.write(millisToOffset(n2));
        writer.write("\r\n");
        writer.write("TZOFFSETFROM");
        writer.write(":");
        writer.write(millisToOffset(n));
        writer.write("\r\n");
        writer.write("TZNAME");
        writer.write(":");
        writer.write(s);
        writer.write("\r\n");
        writer.write("DTSTART");
        writer.write(":");
        writer.write(getDateTimeString(n3 + n));
        writer.write("\r\n");
    }
    
    private static void endZoneProps(final Writer writer, final boolean b) throws IOException {
        writer.write("END");
        writer.write(":");
        if (b) {
            writer.write("DAYLIGHT");
        }
        else {
            writer.write("STANDARD");
        }
        writer.write("\r\n");
    }
    
    private static void beginRRULE(final Writer writer, final int n) throws IOException {
        writer.write("RRULE");
        writer.write(":");
        writer.write("FREQ");
        writer.write("=");
        writer.write("YEARLY");
        writer.write(";");
        writer.write("BYMONTH");
        writer.write("=");
        writer.write(Integer.toString(n + 1));
        writer.write(";");
    }
    
    private static void appendUNTIL(final Writer writer, final String s) throws IOException {
        if (s != null) {
            writer.write(";");
            writer.write("UNTIL");
            writer.write("=");
            writer.write(s);
        }
    }
    
    private void writeHeader(final Writer writer) throws IOException {
        writer.write("BEGIN");
        writer.write(":");
        writer.write("VTIMEZONE");
        writer.write("\r\n");
        writer.write("TZID");
        writer.write(":");
        writer.write(this.tz.getID());
        writer.write("\r\n");
        if (this.tzurl != null) {
            writer.write("TZURL");
            writer.write(":");
            writer.write(this.tzurl);
            writer.write("\r\n");
        }
        if (this.lastmod != null) {
            writer.write("LAST-MODIFIED");
            writer.write(":");
            writer.write(getUTCDateTimeString(this.lastmod.getTime()));
            writer.write("\r\n");
        }
    }
    
    private static void writeFooter(final Writer writer) throws IOException {
        writer.write("END");
        writer.write(":");
        writer.write("VTIMEZONE");
        writer.write("\r\n");
    }
    
    private static String getDateTimeString(final long n) {
        final int[] timeToFields = Grego.timeToFields(n, null);
        final StringBuilder sb = new StringBuilder(15);
        sb.append(numToString(timeToFields[0], 4));
        sb.append(numToString(timeToFields[1] + 1, 2));
        sb.append(numToString(timeToFields[2], 2));
        sb.append('T');
        final int n2 = timeToFields[5];
        final int n3 = n2 / 3600000;
        final int n4 = n2 % 3600000;
        final int n5 = n4 / 60000;
        final int n6 = n4 % 60000 / 1000;
        sb.append(numToString(n3, 2));
        sb.append(numToString(n5, 2));
        sb.append(numToString(n6, 2));
        return sb.toString();
    }
    
    private static String getUTCDateTimeString(final long n) {
        return getDateTimeString(n) + "Z";
    }
    
    private static long parseDateTimeString(final String s, final int n) {
        int int1 = 0;
        int n2 = 0;
        int int2 = 0;
        int int3 = 0;
        int int4 = 0;
        int int5 = 0;
        boolean b = false;
        boolean b2 = false;
        Label_0249: {
            if (s != null) {
                final int length = s.length();
                if (length == 15 || length == 16) {
                    if (s.charAt(8) == 'T') {
                        if (length == 16) {
                            if (s.charAt(15) != 'Z') {
                                break Label_0249;
                            }
                            b = true;
                        }
                        try {
                            int1 = Integer.parseInt(s.substring(0, 4));
                            n2 = Integer.parseInt(s.substring(4, 6)) - 1;
                            int2 = Integer.parseInt(s.substring(6, 8));
                            int3 = Integer.parseInt(s.substring(9, 11));
                            int4 = Integer.parseInt(s.substring(11, 13));
                            int5 = Integer.parseInt(s.substring(13, 15));
                        }
                        catch (NumberFormatException ex) {
                            break Label_0249;
                        }
                        final int monthLength = Grego.monthLength(int1, n2);
                        if (int1 >= 0 && n2 >= 0 && n2 <= 11 && int2 >= 1 && int2 <= monthLength && int3 >= 0 && int3 < 24 && int4 >= 0 && int4 < 60 && int5 >= 0) {
                            if (int5 < 60) {
                                b2 = true;
                            }
                        }
                    }
                }
            }
        }
        if (!b2) {
            throw new IllegalArgumentException("Invalid date time string format");
        }
        long n3 = Grego.fieldsToDay(int1, n2, int2) * 86400000L + (int3 * 3600000 + int4 * 60000 + int5 * 1000);
        if (!b) {
            n3 -= n;
        }
        return n3;
    }
    
    private static int offsetStrToMillis(final String s) {
        boolean b = false;
        int n = 0;
        int int1 = 0;
        int int2 = 0;
        int int3 = 0;
        Label_0119: {
            if (s != null) {
                final int length = s.length();
                if (length == 5 || length == 7) {
                    final char char1 = s.charAt(0);
                    if (char1 == '+') {
                        n = 1;
                    }
                    else {
                        if (char1 != '-') {
                            break Label_0119;
                        }
                        n = -1;
                    }
                    try {
                        int1 = Integer.parseInt(s.substring(1, 3));
                        int2 = Integer.parseInt(s.substring(3, 5));
                        if (length == 7) {
                            int3 = Integer.parseInt(s.substring(5, 7));
                        }
                    }
                    catch (NumberFormatException ex) {
                        break Label_0119;
                    }
                    b = true;
                }
            }
        }
        if (!b) {
            throw new IllegalArgumentException("Bad offset string");
        }
        return n * ((int1 * 60 + int2) * 60 + int3) * 1000;
    }
    
    private static String millisToOffset(int n) {
        final StringBuilder sb = new StringBuilder(7);
        if (n >= 0) {
            sb.append('+');
        }
        else {
            sb.append('-');
            n = -n;
        }
        final int n2 = n / 1000;
        final int n3 = n2 % 60;
        final int n4 = (n2 - n3) / 60;
        final int n5 = n4 % 60;
        sb.append(numToString(n4 / 60, 2));
        sb.append(numToString(n5, 2));
        sb.append(numToString(n3, 2));
        return sb.toString();
    }
    
    private static String numToString(final int n, final int n2) {
        final String string = Integer.toString(n);
        final int length = string.length();
        if (length >= n2) {
            return string.substring(length - n2, length);
        }
        final StringBuilder sb = new StringBuilder(n2);
        for (int i = length; i < n2; ++i) {
            sb.append('0');
        }
        sb.append(string);
        return sb.toString();
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
        final VTimeZone vTimeZone = (VTimeZone)super.cloneAsThawed();
        vTimeZone.tz = (BasicTimeZone)this.tz.cloneAsThawed();
        vTimeZone.isFrozen = false;
        return vTimeZone;
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
        ICAL_DOW_NAMES = new String[] { "SU", "MO", "TU", "WE", "TH", "FR", "SA" };
        MONTHLENGTH = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        try {
            VTimeZone.ICU_TZVERSION = TimeZone.getTZDataVersion();
        }
        catch (MissingResourceException ex) {
            VTimeZone.ICU_TZVERSION = null;
        }
    }
}
