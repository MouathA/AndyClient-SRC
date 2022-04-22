package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.io.*;
import java.math.*;
import com.ibm.icu.math.*;
import java.text.*;
import java.util.*;
import com.ibm.icu.impl.*;

public class RuleBasedNumberFormat extends NumberFormat
{
    static final long serialVersionUID = -7664252765575395068L;
    public static final int SPELLOUT = 1;
    public static final int ORDINAL = 2;
    public static final int DURATION = 3;
    public static final int NUMBERING_SYSTEM = 4;
    private transient NFRuleSet[] ruleSets;
    private transient String[] ruleSetDescriptions;
    private transient NFRuleSet defaultRuleSet;
    private ULocale locale;
    private transient RbnfLenientScannerProvider scannerProvider;
    private transient boolean lookedForScanner;
    private transient DecimalFormatSymbols decimalFormatSymbols;
    private transient DecimalFormat decimalFormat;
    private boolean lenientParse;
    private transient String lenientParseRules;
    private transient String postProcessRules;
    private transient RBNFPostProcessor postProcessor;
    private Map ruleSetDisplayNames;
    private String[] publicRuleSetNames;
    private static final boolean DEBUG;
    private static final String[] locnames;
    
    public RuleBasedNumberFormat(final String s) {
        this.ruleSets = null;
        this.ruleSetDescriptions = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.lenientParse = false;
        this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        this.init(s, null);
    }
    
    public RuleBasedNumberFormat(final String s, final String[][] array) {
        this.ruleSets = null;
        this.ruleSetDescriptions = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.lenientParse = false;
        this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        this.init(s, array);
    }
    
    public RuleBasedNumberFormat(final String s, final Locale locale) {
        this(s, ULocale.forLocale(locale));
    }
    
    public RuleBasedNumberFormat(final String s, final ULocale locale) {
        this.ruleSets = null;
        this.ruleSetDescriptions = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.lenientParse = false;
        this.locale = locale;
        this.init(s, null);
    }
    
    public RuleBasedNumberFormat(final String s, final String[][] array, final ULocale locale) {
        this.ruleSets = null;
        this.ruleSetDescriptions = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.lenientParse = false;
        this.locale = locale;
        this.init(s, array);
    }
    
    public RuleBasedNumberFormat(final Locale locale, final int n) {
        this(ULocale.forLocale(locale), n);
    }
    
    public RuleBasedNumberFormat(final ULocale locale, final int n) {
        this.ruleSets = null;
        this.ruleSetDescriptions = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.lenientParse = false;
        this.locale = locale;
        final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b/rbnf", locale);
        final ULocale uLocale = icuResourceBundle.getULocale();
        this.setLocale(uLocale, uLocale);
        final String[][] array = null;
        final String string = icuResourceBundle.getString(RuleBasedNumberFormat.rulenames[n - 1]);
        final UResourceBundle value = icuResourceBundle.get(RuleBasedNumberFormat.locnames[n - 1]);
        final String[][] array2 = new String[value.getSize()][];
        while (0 < array2.length) {
            array2[0] = value.get(0).getStringArray();
            int n2 = 0;
            ++n2;
        }
        this.init(string, array2);
    }
    
    public RuleBasedNumberFormat(final int n) {
        this(ULocale.getDefault(ULocale.Category.FORMAT), n);
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof RuleBasedNumberFormat)) {
            return false;
        }
        final RuleBasedNumberFormat ruleBasedNumberFormat = (RuleBasedNumberFormat)o;
        if (!this.locale.equals(ruleBasedNumberFormat.locale) || this.lenientParse != ruleBasedNumberFormat.lenientParse) {
            return false;
        }
        if (this.ruleSets.length != ruleBasedNumberFormat.ruleSets.length) {
            return false;
        }
        while (0 < this.ruleSets.length) {
            if (!this.ruleSets[0].equals(ruleBasedNumberFormat.ruleSets[0])) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    @Deprecated
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        while (0 < this.ruleSets.length) {
            sb.append(this.ruleSets[0].toString());
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(this.toString());
        objectOutputStream.writeObject(this.locale);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        final RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(objectInputStream.readUTF(), (ULocale)objectInputStream.readObject());
        this.ruleSets = ruleBasedNumberFormat.ruleSets;
        this.defaultRuleSet = ruleBasedNumberFormat.defaultRuleSet;
        this.publicRuleSetNames = ruleBasedNumberFormat.publicRuleSetNames;
        this.decimalFormatSymbols = ruleBasedNumberFormat.decimalFormatSymbols;
        this.decimalFormat = ruleBasedNumberFormat.decimalFormat;
        this.locale = ruleBasedNumberFormat.locale;
    }
    
    public String[] getRuleSetNames() {
        return this.publicRuleSetNames.clone();
    }
    
    public ULocale[] getRuleSetDisplayNameLocales() {
        if (this.ruleSetDisplayNames != null) {
            final Set keySet = this.ruleSetDisplayNames.keySet();
            final String[] array = (String[])keySet.toArray(new String[keySet.size()]);
            Arrays.sort(array, String.CASE_INSENSITIVE_ORDER);
            final ULocale[] array2 = new ULocale[array.length];
            while (0 < array.length) {
                array2[0] = new ULocale(array[0]);
                int n = 0;
                ++n;
            }
            return array2;
        }
        return null;
    }
    
    private String[] getNameListForLocale(final ULocale uLocale) {
        if (uLocale != null && this.ruleSetDisplayNames != null) {
            final String[] array = { uLocale.getBaseName(), ULocale.getDefault(ULocale.Category.DISPLAY).getBaseName() };
            while (0 < array.length) {
                for (String fallback = array[0]; fallback.length() > 0; fallback = ULocale.getFallback(fallback)) {
                    final String[] array2 = this.ruleSetDisplayNames.get(fallback);
                    if (array2 != null) {
                        return array2;
                    }
                }
                int n = 0;
                ++n;
            }
        }
        return null;
    }
    
    public String[] getRuleSetDisplayNames(final ULocale uLocale) {
        final String[] nameListForLocale = this.getNameListForLocale(uLocale);
        if (nameListForLocale != null) {
            return nameListForLocale.clone();
        }
        final String[] ruleSetNames = this.getRuleSetNames();
        while (0 < ruleSetNames.length) {
            ruleSetNames[0] = ruleSetNames[0].substring(1);
            int n = 0;
            ++n;
        }
        return ruleSetNames;
    }
    
    public String[] getRuleSetDisplayNames() {
        return this.getRuleSetDisplayNames(ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public String getRuleSetDisplayName(final String s, final ULocale uLocale) {
        final String[] publicRuleSetNames = this.publicRuleSetNames;
        while (0 < publicRuleSetNames.length) {
            if (publicRuleSetNames[0].equals(s)) {
                final String[] nameListForLocale = this.getNameListForLocale(uLocale);
                if (nameListForLocale != null) {
                    return nameListForLocale[0];
                }
                return publicRuleSetNames[0].substring(1);
            }
            else {
                int n = 0;
                ++n;
            }
        }
        throw new IllegalArgumentException("unrecognized rule set name: " + s);
    }
    
    public String getRuleSetDisplayName(final String s) {
        return this.getRuleSetDisplayName(s, ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public String format(final double n, final String s) throws IllegalArgumentException {
        if (s.startsWith("%%")) {
            throw new IllegalArgumentException("Can't use internal rule set");
        }
        return this.format(n, this.findRuleSet(s));
    }
    
    public String format(final long n, final String s) throws IllegalArgumentException {
        if (s.startsWith("%%")) {
            throw new IllegalArgumentException("Can't use internal rule set");
        }
        return this.format(n, this.findRuleSet(s));
    }
    
    @Override
    public StringBuffer format(final double n, final StringBuffer sb, final FieldPosition fieldPosition) {
        sb.append(this.format(n, this.defaultRuleSet));
        return sb;
    }
    
    @Override
    public StringBuffer format(final long n, final StringBuffer sb, final FieldPosition fieldPosition) {
        sb.append(this.format(n, this.defaultRuleSet));
        return sb;
    }
    
    @Override
    public StringBuffer format(final BigInteger bigInteger, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format(new BigDecimal(bigInteger), sb, fieldPosition);
    }
    
    @Override
    public StringBuffer format(final java.math.BigDecimal bigDecimal, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format(new BigDecimal(bigDecimal), sb, fieldPosition);
    }
    
    @Override
    public StringBuffer format(final BigDecimal bigDecimal, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format(bigDecimal.doubleValue(), sb, fieldPosition);
    }
    
    @Override
    public Number parse(final String s, final ParsePosition parsePosition) {
        final String substring = s.substring(parsePosition.getIndex());
        final ParsePosition parsePosition2 = new ParsePosition(0);
        Long value = 0L;
        final ParsePosition parsePosition3 = new ParsePosition(parsePosition2.getIndex());
        for (int i = this.ruleSets.length - 1; i >= 0; --i) {
            if (this.ruleSets[i].isPublic()) {
                if (this.ruleSets[i].isParseable()) {
                    final Number parse = this.ruleSets[i].parse(substring, parsePosition2, Double.MAX_VALUE);
                    if (parsePosition2.getIndex() > parsePosition3.getIndex()) {
                        value = (Long)parse;
                        parsePosition3.setIndex(parsePosition2.getIndex());
                    }
                    if (parsePosition3.getIndex() == substring.length()) {
                        break;
                    }
                    parsePosition2.setIndex(0);
                }
            }
        }
        parsePosition.setIndex(parsePosition.getIndex() + parsePosition3.getIndex());
        return value;
    }
    
    public void setLenientParseMode(final boolean lenientParse) {
        this.lenientParse = lenientParse;
    }
    
    public boolean lenientParseEnabled() {
        return this.lenientParse;
    }
    
    public void setLenientScannerProvider(final RbnfLenientScannerProvider scannerProvider) {
        this.scannerProvider = scannerProvider;
    }
    
    public RbnfLenientScannerProvider getLenientScannerProvider() {
        if (this.scannerProvider == null && this.lenientParse && !this.lookedForScanner) {
            this.lookedForScanner = true;
            this.setLenientScannerProvider((RbnfLenientScannerProvider)Class.forName("com.ibm.icu.text.RbnfScannerProviderImpl").newInstance());
        }
        return this.scannerProvider;
    }
    
    public void setDefaultRuleSet(final String s) {
        if (s == null) {
            if (this.publicRuleSetNames.length > 0) {
                this.defaultRuleSet = this.findRuleSet(this.publicRuleSetNames[0]);
            }
            else {
                this.defaultRuleSet = null;
                int length = this.ruleSets.length;
                while (--length >= 0) {
                    final String name = this.ruleSets[length].getName();
                    if (name.equals("%spellout-numbering") || name.equals("%digits-ordinal") || name.equals("%duration")) {
                        this.defaultRuleSet = this.ruleSets[length];
                        return;
                    }
                }
                int length2 = this.ruleSets.length;
                while (--length2 >= 0) {
                    if (this.ruleSets[length2].isPublic()) {
                        this.defaultRuleSet = this.ruleSets[length2];
                        break;
                    }
                }
            }
        }
        else {
            if (s.startsWith("%%")) {
                throw new IllegalArgumentException("cannot use private rule set: " + s);
            }
            this.defaultRuleSet = this.findRuleSet(s);
        }
    }
    
    public String getDefaultRuleSetName() {
        if (this.defaultRuleSet != null && this.defaultRuleSet.isPublic()) {
            return this.defaultRuleSet.getName();
        }
        return "";
    }
    
    public void setDecimalFormatSymbols(final DecimalFormatSymbols decimalFormatSymbols) {
        if (decimalFormatSymbols != null) {
            this.decimalFormatSymbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
            if (this.decimalFormat != null) {
                this.decimalFormat.setDecimalFormatSymbols(this.decimalFormatSymbols);
            }
            while (0 < this.ruleSets.length) {
                this.ruleSets[0].parseRules(this.ruleSetDescriptions[0], this);
                int n = 0;
                ++n;
            }
        }
    }
    
    NFRuleSet getDefaultRuleSet() {
        return this.defaultRuleSet;
    }
    
    RbnfLenientScanner getLenientScanner() {
        if (this.lenientParse) {
            final RbnfLenientScannerProvider lenientScannerProvider = this.getLenientScannerProvider();
            if (lenientScannerProvider != null) {
                return lenientScannerProvider.get(this.locale, this.lenientParseRules);
            }
        }
        return null;
    }
    
    DecimalFormatSymbols getDecimalFormatSymbols() {
        if (this.decimalFormatSymbols == null) {
            this.decimalFormatSymbols = new DecimalFormatSymbols(this.locale);
        }
        return this.decimalFormatSymbols;
    }
    
    DecimalFormat getDecimalFormat() {
        if (this.decimalFormat == null) {
            this.decimalFormat = (DecimalFormat)NumberFormat.getInstance(this.locale);
            if (this.decimalFormatSymbols != null) {
                this.decimalFormat.setDecimalFormatSymbols(this.decimalFormatSymbols);
            }
        }
        return this.decimalFormat;
    }
    
    private String extractSpecial(final StringBuilder sb, final String s) {
        String substring = null;
        final int index = sb.indexOf(s);
        if (index != -1 && (index == 0 || sb.charAt(index - 1) == ';')) {
            int index2 = sb.indexOf(";%", index);
            if (index2 == -1) {
                index2 = sb.length() - 1;
            }
            int n;
            for (n = index + s.length(); n < index2 && PatternProps.isWhiteSpace(sb.charAt(n)); ++n) {}
            substring = sb.substring(n, index2);
            sb.delete(index, index2 + 1);
        }
        return substring;
    }
    
    private void init(final String s, final String[][] array) {
        this.initLocalizations(array);
        final StringBuilder stripWhitespace = this.stripWhitespace(s);
        this.lenientParseRules = this.extractSpecial(stripWhitespace, "%%lenient-parse:");
        this.postProcessRules = this.extractSpecial(stripWhitespace, "%%post-process:");
        int n = stripWhitespace.indexOf(";%");
        while (true) {
            int n2 = 0;
            ++n2;
            ++n;
            n = stripWhitespace.indexOf(";%", 0);
        }
    }
    
    private void initLocalizations(final String[][] array) {
        if (array != null) {
            this.publicRuleSetNames = array[0].clone();
            final HashMap<String, String[]> ruleSetDisplayNames = new HashMap<String, String[]>();
            while (1 < array.length) {
                final String[] array2 = array[1];
                final String s = array2[0];
                final String[] array3 = new String[array2.length - 1];
                if (array3.length != this.publicRuleSetNames.length) {
                    throw new IllegalArgumentException("public name length: " + this.publicRuleSetNames.length + " != localized names[" + 1 + "] length: " + array3.length);
                }
                System.arraycopy(array2, 1, array3, 0, array3.length);
                ruleSetDisplayNames.put(s, array3);
                int n = 0;
                ++n;
            }
            if (!ruleSetDisplayNames.isEmpty()) {
                this.ruleSetDisplayNames = ruleSetDisplayNames;
            }
        }
    }
    
    private StringBuilder stripWhitespace(final String s) {
        return new StringBuilder();
    }
    
    private String format(final double n, final NFRuleSet set) {
        final StringBuffer sb = new StringBuffer();
        set.format(n, sb, 0);
        this.postProcess(sb, set);
        return sb.toString();
    }
    
    private String format(final long n, final NFRuleSet set) {
        final StringBuffer sb = new StringBuffer();
        set.format(n, sb, 0);
        this.postProcess(sb, set);
        return sb.toString();
    }
    
    private void postProcess(final StringBuffer sb, final NFRuleSet set) {
        if (this.postProcessRules != null) {
            if (this.postProcessor == null) {
                int n = this.postProcessRules.indexOf(";");
                if (n == -1) {
                    n = this.postProcessRules.length();
                }
                (this.postProcessor = (RBNFPostProcessor)Class.forName(this.postProcessRules.substring(0, n).trim()).newInstance()).init(this, this.postProcessRules);
            }
            this.postProcessor.process(sb, set);
        }
    }
    
    NFRuleSet findRuleSet(final String s) throws IllegalArgumentException {
        while (0 < this.ruleSets.length) {
            if (this.ruleSets[0].getName().equals(s)) {
                return this.ruleSets[0];
            }
            int n = 0;
            ++n;
        }
        throw new IllegalArgumentException("No rule set named " + s);
    }
    
    static {
        DEBUG = ICUDebug.enabled("rbnf");
        RuleBasedNumberFormat.rulenames = new String[] { "SpelloutRules", "OrdinalRules", "DurationRules", "NumberingSystemRules" };
        locnames = new String[] { "SpelloutLocalizations", "OrdinalLocalizations", "DurationLocalizations", "NumberingSystemLocalizations" };
    }
}
