package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class MessageFormat extends UFormat
{
    static final long serialVersionUID = 7136212545847378652L;
    private transient ULocale ulocale;
    private transient MessagePattern msgPattern;
    private transient Map cachedFormatters;
    private transient Set customFormatArgStarts;
    private transient Format stockDateFormatter;
    private transient Format stockNumberFormatter;
    private transient PluralSelectorProvider pluralProvider;
    private transient PluralSelectorProvider ordinalProvider;
    private static final int TYPE_NUMBER = 0;
    private static final int TYPE_DATE = 1;
    private static final int TYPE_TIME = 2;
    private static final int TYPE_SPELLOUT = 3;
    private static final int TYPE_ORDINAL = 4;
    private static final int TYPE_DURATION = 5;
    private static final String[] modifierList;
    private static final int MODIFIER_EMPTY = 0;
    private static final int MODIFIER_CURRENCY = 1;
    private static final int MODIFIER_PERCENT = 2;
    private static final int MODIFIER_INTEGER = 3;
    private static final String[] dateModifierList;
    private static final int DATE_MODIFIER_EMPTY = 0;
    private static final int DATE_MODIFIER_SHORT = 1;
    private static final int DATE_MODIFIER_MEDIUM = 2;
    private static final int DATE_MODIFIER_LONG = 3;
    private static final int DATE_MODIFIER_FULL = 4;
    private static final Locale rootLocale;
    private static final char SINGLE_QUOTE = '\'';
    private static final char CURLY_BRACE_LEFT = '{';
    private static final char CURLY_BRACE_RIGHT = '}';
    private static final int STATE_INITIAL = 0;
    private static final int STATE_SINGLE_QUOTE = 1;
    private static final int STATE_IN_QUOTE = 2;
    private static final int STATE_MSG_ELEMENT = 3;
    static final boolean $assertionsDisabled;
    
    public MessageFormat(final String s) {
        this.ulocale = ULocale.getDefault(ULocale.Category.FORMAT);
        this.applyPattern(s);
    }
    
    public MessageFormat(final String s, final Locale locale) {
        this(s, ULocale.forLocale(locale));
    }
    
    public MessageFormat(final String s, final ULocale ulocale) {
        this.ulocale = ulocale;
        this.applyPattern(s);
    }
    
    public void setLocale(final Locale locale) {
        this.setLocale(ULocale.forLocale(locale));
    }
    
    public void setLocale(final ULocale ulocale) {
        final String pattern = this.toPattern();
        this.ulocale = ulocale;
        final Format format = null;
        this.stockDateFormatter = format;
        this.stockNumberFormatter = format;
        this.pluralProvider = null;
        this.ordinalProvider = null;
        this.applyPattern(pattern);
    }
    
    public Locale getLocale() {
        return this.ulocale.toLocale();
    }
    
    public ULocale getULocale() {
        return this.ulocale;
    }
    
    public void applyPattern(final String s) {
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern(s);
        }
        else {
            this.msgPattern.parse(s);
        }
        this.cacheExplicitFormats();
    }
    
    public void applyPattern(final String s, final MessagePattern.ApostropheMode apostropheMode) {
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern(apostropheMode);
        }
        else if (apostropheMode != this.msgPattern.getApostropheMode()) {
            this.msgPattern.clearPatternAndSetApostropheMode(apostropheMode);
        }
        this.applyPattern(s);
    }
    
    public MessagePattern.ApostropheMode getApostropheMode() {
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        return this.msgPattern.getApostropheMode();
    }
    
    public String toPattern() {
        if (this.customFormatArgStarts != null) {
            throw new IllegalStateException("toPattern() is not supported after custom Format objects have been set via setFormat() or similar APIs");
        }
        if (this.msgPattern == null) {
            return "";
        }
        final String patternString = this.msgPattern.getPatternString();
        return (patternString == null) ? "" : patternString;
    }
    
    private int nextTopLevelArgStart(int limitPartIndex) {
        if (limitPartIndex != 0) {
            limitPartIndex = this.msgPattern.getLimitPartIndex(limitPartIndex);
        }
        while (true) {
            final MessagePattern.Part.Type partType = this.msgPattern.getPartType(++limitPartIndex);
            if (partType == MessagePattern.Part.Type.ARG_START) {
                return limitPartIndex;
            }
            if (partType == MessagePattern.Part.Type.MSG_LIMIT) {
                return -1;
            }
        }
    }
    
    private boolean argNameMatches(final int n, final String s, final int n2) {
        final MessagePattern.Part part = this.msgPattern.getPart(n);
        return (part.getType() == MessagePattern.Part.Type.ARG_NAME) ? this.msgPattern.partSubstringMatches(part, s) : (part.getValue() == n2);
    }
    
    private String getArgName(final int n) {
        final MessagePattern.Part part = this.msgPattern.getPart(n);
        if (part.getType() == MessagePattern.Part.Type.ARG_NAME) {
            return this.msgPattern.getSubstring(part);
        }
        return Integer.toString(part.getValue());
    }
    
    public void setFormatsByArgumentIndex(final Format[] array) {
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        while (this.nextTopLevelArgStart(0) >= 0) {
            final int value = this.msgPattern.getPart(1).getValue();
            if (value < array.length) {
                this.setCustomArgStartFormat(0, array[value]);
            }
        }
    }
    
    public void setFormatsByArgumentName(final Map map) {
        while (this.nextTopLevelArgStart(0) >= 0) {
            final String argName = this.getArgName(1);
            if (map.containsKey(argName)) {
                this.setCustomArgStartFormat(0, map.get(argName));
            }
        }
    }
    
    public void setFormats(final Format[] array) {
        int nextTopLevelArgStart;
        while (0 < array.length && (nextTopLevelArgStart = this.nextTopLevelArgStart(0)) >= 0) {
            this.setCustomArgStartFormat(0, array[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void setFormatByArgumentIndex(final int n, final Format format) {
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        while (this.nextTopLevelArgStart(0) >= 0) {
            if (this.msgPattern.getPart(1).getValue() == n) {
                this.setCustomArgStartFormat(0, format);
            }
        }
    }
    
    public void setFormatByArgumentName(final String s, final Format format) {
        final int validateArgumentName = MessagePattern.validateArgumentName(s);
        if (validateArgumentName < -1) {
            return;
        }
        while (this.nextTopLevelArgStart(0) >= 0) {
            if (this.argNameMatches(1, s, validateArgumentName)) {
                this.setCustomArgStartFormat(0, format);
            }
        }
    }
    
    public void setFormat(final int n, final Format format) {
        while (this.nextTopLevelArgStart(0) >= 0) {
            if (0 == n) {
                this.setCustomArgStartFormat(0, format);
                return;
            }
            int n2 = 0;
            ++n2;
        }
        throw new ArrayIndexOutOfBoundsException(n);
    }
    
    public Format[] getFormatsByArgumentIndex() {
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        final ArrayList<Format> list = new ArrayList<Format>();
        while (this.nextTopLevelArgStart(0) >= 0) {
            final int i = this.msgPattern.getPart(1).getValue();
            while (i >= list.size()) {
                list.add(null);
            }
            list.set(i, (this.cachedFormatters == null) ? null : this.cachedFormatters.get(0));
        }
        return list.toArray(new Format[list.size()]);
    }
    
    public Format[] getFormats() {
        final ArrayList<Format> list = new ArrayList<Format>();
        while (this.nextTopLevelArgStart(0) >= 0) {
            list.add((this.cachedFormatters == null) ? null : this.cachedFormatters.get(0));
        }
        return list.toArray(new Format[list.size()]);
    }
    
    public Set getArgumentNames() {
        final HashSet<String> set = new HashSet<String>();
        while (this.nextTopLevelArgStart(0) >= 0) {
            set.add(this.getArgName(1));
        }
        return set;
    }
    
    public Format getFormatByArgumentName(final String s) {
        if (this.cachedFormatters == null) {
            return null;
        }
        final int validateArgumentName = MessagePattern.validateArgumentName(s);
        if (validateArgumentName < -1) {
            return null;
        }
        while (this.nextTopLevelArgStart(0) >= 0) {
            if (this.argNameMatches(1, s, validateArgumentName)) {
                return this.cachedFormatters.get(0);
            }
        }
        return null;
    }
    
    public final StringBuffer format(final Object[] array, final StringBuffer sb, final FieldPosition fieldPosition) {
        this.format(array, null, new AppendableWrapper(sb), fieldPosition);
        return sb;
    }
    
    public final StringBuffer format(final Map map, final StringBuffer sb, final FieldPosition fieldPosition) {
        this.format(null, map, new AppendableWrapper(sb), fieldPosition);
        return sb;
    }
    
    public static String format(final String s, final Object... array) {
        return new MessageFormat(s).format(array);
    }
    
    public static String format(final String s, final Map map) {
        return new MessageFormat(s).format(map);
    }
    
    public boolean usesNamedArguments() {
        return this.msgPattern.hasNamedArguments();
    }
    
    @Override
    public final StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        this.format(o, new AppendableWrapper(sb), fieldPosition);
        return sb;
    }
    
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(final Object o) {
        if (o == null) {
            throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
        }
        final StringBuilder sb = new StringBuilder();
        final AppendableWrapper appendableWrapper = new AppendableWrapper(sb);
        appendableWrapper.useAttributes();
        this.format(o, appendableWrapper, null);
        final AttributedString attributedString = new AttributedString(sb.toString());
        for (final AttributeAndPosition attributeAndPosition : AppendableWrapper.access$000(appendableWrapper)) {
            attributedString.addAttribute(AttributeAndPosition.access$100(attributeAndPosition), AttributeAndPosition.access$200(attributeAndPosition), AttributeAndPosition.access$300(attributeAndPosition), AttributeAndPosition.access$400(attributeAndPosition));
        }
        return attributedString.getIterator();
    }
    
    public Object[] parse(final String s, final ParsePosition parsePosition) {
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use named argument.");
        }
        while (this.nextTopLevelArgStart(0) >= 0) {
            if (this.msgPattern.getPart(1).getValue() > -1) {
                continue;
            }
        }
        final Object[] array = new Object[0];
        final int index = parsePosition.getIndex();
        this.parse(0, s, parsePosition, array, null);
        if (parsePosition.getIndex() == index) {
            return null;
        }
        return array;
    }
    
    public Map parseToMap(final String s, final ParsePosition parsePosition) {
        final HashMap hashMap = new HashMap();
        final int index = parsePosition.getIndex();
        this.parse(0, s, parsePosition, null, hashMap);
        if (parsePosition.getIndex() == index) {
            return null;
        }
        return hashMap;
    }
    
    public Object[] parse(final String s) throws ParseException {
        final ParsePosition parsePosition = new ParsePosition(0);
        final Object[] parse = this.parse(s, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw new ParseException("MessageFormat parse error!", parsePosition.getErrorIndex());
        }
        return parse;
    }
    
    private void parse(final int n, final String s, final ParsePosition parsePosition, final Object[] array, final Map map) {
        if (s == null) {
            return;
        }
        final String patternString = this.msgPattern.getPatternString();
        int n2 = this.msgPattern.getPart(n).getLimit();
        int errorIndex = parsePosition.getIndex();
        final ParsePosition parsePosition2 = new ParsePosition(0);
        int n3 = n + 1;
        while (true) {
            final MessagePattern.Part part = this.msgPattern.getPart(n3);
            final MessagePattern.Part.Type type = part.getType();
            final int n4 = part.getIndex() - n2;
            if (n4 != 0 && !patternString.regionMatches(n2, s, errorIndex, n4)) {
                parsePosition.setErrorIndex(errorIndex);
                return;
            }
            errorIndex += n4;
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                parsePosition.setIndex(errorIndex);
                return;
            }
            if (type == MessagePattern.Part.Type.SKIP_SYNTAX || type == MessagePattern.Part.Type.INSERT_CHAR) {
                n2 = part.getLimit();
            }
            else {
                assert type == MessagePattern.Part.Type.ARG_START : "Unexpected Part " + part + " in parsed message.";
                final int limitPartIndex = this.msgPattern.getLimitPartIndex(n3);
                final MessagePattern.ArgType argType = part.getArgType();
                final MessagePattern.Part part2 = this.msgPattern.getPart(++n3);
                String s2 = null;
                Serializable value;
                if (array != null) {
                    part2.getValue();
                    value = 0;
                }
                else {
                    if (part2.getType() == MessagePattern.Part.Type.ARG_NAME) {
                        s2 = this.msgPattern.getSubstring(part2);
                    }
                    else {
                        s2 = Integer.toString(part2.getValue());
                    }
                    value = s2;
                }
                ++n3;
                Object o = null;
                final Format format;
                if (this.cachedFormatters != null && (format = this.cachedFormatters.get(n3 - 2)) != null) {
                    parsePosition2.setIndex(errorIndex);
                    o = format.parseObject(s, parsePosition2);
                    if (parsePosition2.getIndex() == errorIndex) {
                        parsePosition.setErrorIndex(errorIndex);
                        return;
                    }
                    errorIndex = parsePosition2.getIndex();
                }
                else if (argType == MessagePattern.ArgType.NONE || (this.cachedFormatters != null && this.cachedFormatters.containsKey(n3 - 2))) {
                    final String literalStringUntilNextArgument = this.getLiteralStringUntilNextArgument(limitPartIndex);
                    int n5;
                    if (literalStringUntilNextArgument.length() != 0) {
                        n5 = s.indexOf(literalStringUntilNextArgument, errorIndex);
                    }
                    else {
                        n5 = s.length();
                    }
                    if (n5 < 0) {
                        parsePosition.setErrorIndex(errorIndex);
                        return;
                    }
                    final String substring = s.substring(errorIndex, n5);
                    if (!substring.equals("{" + value.toString() + "}")) {
                        o = substring;
                    }
                    errorIndex = n5;
                }
                else if (argType == MessagePattern.ArgType.CHOICE) {
                    parsePosition2.setIndex(errorIndex);
                    final double choiceArgument = parseChoiceArgument(this.msgPattern, n3, s, parsePosition2);
                    if (parsePosition2.getIndex() == errorIndex) {
                        parsePosition.setErrorIndex(errorIndex);
                        return;
                    }
                    o = choiceArgument;
                    errorIndex = parsePosition2.getIndex();
                }
                else {
                    if (argType.hasPluralStyle() || argType == MessagePattern.ArgType.SELECT) {
                        throw new UnsupportedOperationException("Parsing of plural/select/selectordinal argument is not supported.");
                    }
                    throw new IllegalStateException("unexpected argType " + argType);
                }
                if (true) {
                    if (array != null) {
                        array[0] = o;
                    }
                    else if (map != null) {
                        map.put(s2, o);
                    }
                }
                n2 = this.msgPattern.getPart(limitPartIndex).getLimit();
                n3 = limitPartIndex;
            }
            ++n3;
        }
    }
    
    public Map parseToMap(final String s) throws ParseException {
        final ParsePosition parsePosition = new ParsePosition(0);
        final HashMap hashMap = new HashMap();
        this.parse(0, s, parsePosition, null, hashMap);
        if (parsePosition.getIndex() == 0) {
            throw new ParseException("MessageFormat parse error!", parsePosition.getErrorIndex());
        }
        return hashMap;
    }
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        if (!this.msgPattern.hasNamedArguments()) {
            return this.parse(s, parsePosition);
        }
        return this.parseToMap(s, parsePosition);
    }
    
    @Override
    public Object clone() {
        final MessageFormat messageFormat = (MessageFormat)super.clone();
        if (this.customFormatArgStarts != null) {
            messageFormat.customFormatArgStarts = new HashSet();
            final Iterator<Integer> iterator = this.customFormatArgStarts.iterator();
            while (iterator.hasNext()) {
                messageFormat.customFormatArgStarts.add(iterator.next());
            }
        }
        else {
            messageFormat.customFormatArgStarts = null;
        }
        if (this.cachedFormatters != null) {
            messageFormat.cachedFormatters = new HashMap();
            for (final Map.Entry<Object, V> entry : this.cachedFormatters.entrySet()) {
                messageFormat.cachedFormatters.put(entry.getKey(), entry.getValue());
            }
        }
        else {
            messageFormat.cachedFormatters = null;
        }
        messageFormat.msgPattern = ((this.msgPattern == null) ? null : ((MessagePattern)this.msgPattern.clone()));
        messageFormat.stockDateFormatter = ((this.stockDateFormatter == null) ? null : ((Format)this.stockDateFormatter.clone()));
        messageFormat.stockNumberFormatter = ((this.stockNumberFormatter == null) ? null : ((Format)this.stockNumberFormatter.clone()));
        messageFormat.pluralProvider = null;
        messageFormat.ordinalProvider = null;
        return messageFormat;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final MessageFormat messageFormat = (MessageFormat)o;
        return Utility.objectEquals(this.ulocale, messageFormat.ulocale) && Utility.objectEquals(this.msgPattern, messageFormat.msgPattern) && Utility.objectEquals(this.cachedFormatters, messageFormat.cachedFormatters) && Utility.objectEquals(this.customFormatArgStarts, messageFormat.customFormatArgStarts);
    }
    
    @Override
    public int hashCode() {
        return this.msgPattern.getPatternString().hashCode();
    }
    
    private void format(final int n, final double n2, final Object[] array, final Map map, final AppendableWrapper appendableWrapper, FieldPosition updateMetaData) {
        final String patternString = this.msgPattern.getPatternString();
        int n3 = this.msgPattern.getPart(n).getLimit();
        int n4 = n + 1;
        while (true) {
            final MessagePattern.Part part = this.msgPattern.getPart(n4);
            final MessagePattern.Part.Type type = part.getType();
            appendableWrapper.append(patternString, n3, part.getIndex());
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                return;
            }
            n3 = part.getLimit();
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                if (this.stockNumberFormatter == null) {
                    this.stockNumberFormatter = NumberFormat.getInstance(this.ulocale);
                }
                appendableWrapper.formatAndAppend(this.stockNumberFormatter, n2);
            }
            else if (type == MessagePattern.Part.Type.ARG_START) {
                final int limitPartIndex = this.msgPattern.getLimitPartIndex(n4);
                final MessagePattern.ArgType argType = part.getArgType();
                final MessagePattern.Part part2 = this.msgPattern.getPart(++n4);
                CharSequence charSequence = null;
                Object value = null;
                Object value3;
                if (array != null) {
                    final int value2 = part2.getValue();
                    if (AppendableWrapper.access$000(appendableWrapper) != null) {
                        value = value2;
                    }
                    if (0 <= value2 && value2 < array.length) {
                        value3 = array[value2];
                    }
                    else {
                        value3 = null;
                        charSequence = "{" + value2 + "}";
                    }
                }
                else {
                    String s;
                    if (part2.getType() == MessagePattern.Part.Type.ARG_NAME) {
                        s = this.msgPattern.getSubstring(part2);
                    }
                    else {
                        s = Integer.toString(part2.getValue());
                    }
                    value = s;
                    if (map != null && map.containsKey(s)) {
                        value3 = map.get(s);
                    }
                    else {
                        value3 = null;
                        charSequence = "{" + s + "}";
                    }
                }
                ++n4;
                final int access$500 = AppendableWrapper.access$500(appendableWrapper);
                if (charSequence != null) {
                    appendableWrapper.append(charSequence);
                }
                else if (value3 == null) {
                    appendableWrapper.append("null");
                }
                else {
                    final Format format;
                    if (this.cachedFormatters != null && (format = this.cachedFormatters.get(n4 - 2)) != null) {
                        if (format instanceof ChoiceFormat || format instanceof PluralFormat || format instanceof SelectFormat) {
                            final String format2 = format.format(value3);
                            if (format2.indexOf(123) >= 0 || (format2.indexOf(39) >= 0 && !this.msgPattern.jdkAposMode())) {
                                new MessageFormat(format2, this.ulocale).format(0, 0.0, array, map, appendableWrapper, null);
                            }
                            else if (AppendableWrapper.access$000(appendableWrapper) == null) {
                                appendableWrapper.append(format2);
                            }
                            else {
                                appendableWrapper.formatAndAppend(format, value3);
                            }
                        }
                        else {
                            appendableWrapper.formatAndAppend(format, value3);
                        }
                    }
                    else if (argType == MessagePattern.ArgType.NONE || (this.cachedFormatters != null && this.cachedFormatters.containsKey(n4 - 2))) {
                        if (value3 instanceof Number) {
                            if (this.stockNumberFormatter == null) {
                                this.stockNumberFormatter = NumberFormat.getInstance(this.ulocale);
                            }
                            appendableWrapper.formatAndAppend(this.stockNumberFormatter, value3);
                        }
                        else if (value3 instanceof Date) {
                            if (this.stockDateFormatter == null) {
                                this.stockDateFormatter = DateFormat.getDateTimeInstance(3, 3, this.ulocale);
                            }
                            appendableWrapper.formatAndAppend(this.stockDateFormatter, value3);
                        }
                        else {
                            appendableWrapper.append(value3.toString());
                        }
                    }
                    else if (argType == MessagePattern.ArgType.CHOICE) {
                        if (!(value3 instanceof Number)) {
                            throw new IllegalArgumentException("'" + value3 + "' is not a Number");
                        }
                        this.formatComplexSubMessage(findChoiceSubMessage(this.msgPattern, n4, ((Number)value3).doubleValue()), 0.0, array, map, appendableWrapper);
                    }
                    else if (argType.hasPluralStyle()) {
                        if (!(value3 instanceof Number)) {
                            throw new IllegalArgumentException("'" + value3 + "' is not a Number");
                        }
                        final double doubleValue = ((Number)value3).doubleValue();
                        PluralSelectorProvider pluralSelectorProvider;
                        if (argType == MessagePattern.ArgType.PLURAL) {
                            if (this.pluralProvider == null) {
                                this.pluralProvider = new PluralSelectorProvider(this.ulocale, PluralRules.PluralType.CARDINAL);
                            }
                            pluralSelectorProvider = this.pluralProvider;
                        }
                        else {
                            if (this.ordinalProvider == null) {
                                this.ordinalProvider = new PluralSelectorProvider(this.ulocale, PluralRules.PluralType.ORDINAL);
                            }
                            pluralSelectorProvider = this.ordinalProvider;
                        }
                        this.formatComplexSubMessage(PluralFormat.findSubMessage(this.msgPattern, n4, pluralSelectorProvider, doubleValue), doubleValue - this.msgPattern.getPluralOffset(n4), array, map, appendableWrapper);
                    }
                    else {
                        if (argType != MessagePattern.ArgType.SELECT) {
                            throw new IllegalStateException("unexpected argType " + argType);
                        }
                        this.formatComplexSubMessage(SelectFormat.findSubMessage(this.msgPattern, n4, value3.toString()), 0.0, array, map, appendableWrapper);
                    }
                }
                updateMetaData = this.updateMetaData(appendableWrapper, access$500, updateMetaData, value);
                n3 = this.msgPattern.getPart(limitPartIndex).getLimit();
                n4 = limitPartIndex;
            }
            ++n4;
        }
    }
    
    private void formatComplexSubMessage(final int n, final double n2, final Object[] array, final Map map, final AppendableWrapper appendableWrapper) {
        if (!this.msgPattern.jdkAposMode()) {
            this.format(n, n2, array, map, appendableWrapper, null);
            return;
        }
        final String patternString = this.msgPattern.getPatternString();
        StringBuilder sb = null;
        int n3 = this.msgPattern.getPart(n).getLimit();
        int limitPartIndex = n;
        int index;
        while (true) {
            final MessagePattern.Part part = this.msgPattern.getPart(++limitPartIndex);
            final MessagePattern.Part.Type type = part.getType();
            index = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                break;
            }
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER || type == MessagePattern.Part.Type.SKIP_SYNTAX) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(patternString, n3, index);
                if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                    if (this.stockNumberFormatter == null) {
                        this.stockNumberFormatter = NumberFormat.getInstance(this.ulocale);
                    }
                    sb.append(this.stockNumberFormatter.format(n2));
                }
                n3 = part.getLimit();
            }
            else {
                if (type != MessagePattern.Part.Type.ARG_START) {
                    continue;
                }
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(patternString, n3, index);
                final int n4 = index;
                limitPartIndex = this.msgPattern.getLimitPartIndex(limitPartIndex);
                final int limit = this.msgPattern.getPart(limitPartIndex).getLimit();
                MessagePattern.appendReducedApostrophes(patternString, n4, limit, sb);
                n3 = limit;
            }
        }
        String s;
        if (sb == null) {
            s = patternString.substring(n3, index);
        }
        else {
            s = sb.append(patternString, n3, index).toString();
        }
        if (s.indexOf(123) >= 0) {
            final MessageFormat messageFormat = new MessageFormat("", this.ulocale);
            messageFormat.applyPattern(s, MessagePattern.ApostropheMode.DOUBLE_REQUIRED);
            messageFormat.format(0, 0.0, array, map, appendableWrapper, null);
        }
        else {
            appendableWrapper.append(s);
        }
    }
    
    private String getLiteralStringUntilNextArgument(final int n) {
        final StringBuilder sb = new StringBuilder();
        final String patternString = this.msgPattern.getPatternString();
        int n2 = this.msgPattern.getPart(n).getLimit();
        int n3 = n + 1;
        while (true) {
            final MessagePattern.Part part = this.msgPattern.getPart(n3);
            final MessagePattern.Part.Type type = part.getType();
            sb.append(patternString, n2, part.getIndex());
            if (type == MessagePattern.Part.Type.ARG_START || type == MessagePattern.Part.Type.MSG_LIMIT) {
                return sb.toString();
            }
            assert type == MessagePattern.Part.Type.INSERT_CHAR : "Unexpected Part " + part + " in parsed message.";
            n2 = part.getLimit();
            ++n3;
        }
    }
    
    private FieldPosition updateMetaData(final AppendableWrapper appendableWrapper, final int beginIndex, final FieldPosition fieldPosition, final Object o) {
        if (AppendableWrapper.access$000(appendableWrapper) != null && beginIndex < AppendableWrapper.access$500(appendableWrapper)) {
            AppendableWrapper.access$000(appendableWrapper).add(new AttributeAndPosition(o, beginIndex, AppendableWrapper.access$500(appendableWrapper)));
        }
        if (fieldPosition != null && Field.ARGUMENT.equals(fieldPosition.getFieldAttribute())) {
            fieldPosition.setBeginIndex(beginIndex);
            fieldPosition.setEndIndex(AppendableWrapper.access$500(appendableWrapper));
            return null;
        }
        return fieldPosition;
    }
    
    private static int findChoiceSubMessage(final MessagePattern messagePattern, int limitPartIndex, final double n) {
        final int countParts = messagePattern.countParts();
        limitPartIndex += 2;
        int n2;
        while (true) {
            n2 = limitPartIndex;
            limitPartIndex = messagePattern.getLimitPartIndex(limitPartIndex);
            if (++limitPartIndex >= countParts) {
                break;
            }
            final MessagePattern.Part part = messagePattern.getPart(limitPartIndex++);
            final MessagePattern.Part.Type type = part.getType();
            if (type == MessagePattern.Part.Type.ARG_LIMIT) {
                break;
            }
            assert type.hasNumericValue();
            final double numericValue = messagePattern.getNumericValue(part);
            if (messagePattern.getPatternString().charAt(messagePattern.getPatternIndex(limitPartIndex++)) == '<') {
                if (n <= numericValue) {
                    break;
                }
                continue;
            }
            else {
                if (n < numericValue) {
                    break;
                }
                continue;
            }
        }
        return n2;
    }
    
    private static double parseChoiceArgument(final MessagePattern messagePattern, int n, final String s, final ParsePosition parsePosition) {
        int index;
        final int errorIndex = index = parsePosition.getIndex();
        double n2 = Double.NaN;
        while (messagePattern.getPartType(n) != MessagePattern.Part.Type.ARG_LIMIT) {
            final double numericValue = messagePattern.getNumericValue(messagePattern.getPart(n));
            n += 2;
            final int limitPartIndex = messagePattern.getLimitPartIndex(n);
            final int matchStringUntilLimitPart = matchStringUntilLimitPart(messagePattern, n, limitPartIndex, s, errorIndex);
            if (matchStringUntilLimitPart >= 0) {
                final int n3 = errorIndex + matchStringUntilLimitPart;
                if (n3 > index) {
                    index = n3;
                    n2 = numericValue;
                    if (index == s.length()) {
                        break;
                    }
                }
            }
            n = limitPartIndex + 1;
        }
        if (index == errorIndex) {
            parsePosition.setErrorIndex(errorIndex);
        }
        else {
            parsePosition.setIndex(index);
        }
        return n2;
    }
    
    private static int matchStringUntilLimitPart(final MessagePattern messagePattern, int n, final int n2, final String s, final int n3) {
        final String patternString = messagePattern.getPatternString();
        int n4 = messagePattern.getPart(n).getLimit();
        while (true) {
            final MessagePattern.Part part = messagePattern.getPart(++n);
            if (n == n2 || part.getType() == MessagePattern.Part.Type.SKIP_SYNTAX) {
                final int n5 = part.getIndex() - n4;
                if (n5 != 0 && !s.regionMatches(n3, patternString, n4, n5)) {
                    return -1;
                }
                if (n == n2) {
                    return 0;
                }
                n4 = part.getLimit();
            }
        }
    }
    
    private void format(final Object o, final AppendableWrapper appendableWrapper, final FieldPosition fieldPosition) {
        if (o == null || o instanceof Map) {
            this.format(null, (Map)o, appendableWrapper, fieldPosition);
        }
        else {
            this.format((Object[])o, null, appendableWrapper, fieldPosition);
        }
    }
    
    private void format(final Object[] array, final Map map, final AppendableWrapper appendableWrapper, final FieldPosition fieldPosition) {
        if (array != null && this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        this.format(0, 0.0, array, map, appendableWrapper, fieldPosition);
    }
    
    private void resetPattern() {
        if (this.msgPattern != null) {
            this.msgPattern.clear();
        }
        if (this.cachedFormatters != null) {
            this.cachedFormatters.clear();
        }
        this.customFormatArgStarts = null;
    }
    
    private Format createAppropriateFormat(final String s, final String s2) {
        UFormat uFormat = null;
        Label_0560: {
            switch (findKeyword(s, MessageFormat.typeList)) {
                case 0: {
                    switch (findKeyword(s2, MessageFormat.modifierList)) {
                        case 0: {
                            uFormat = NumberFormat.getInstance(this.ulocale);
                            break Label_0560;
                        }
                        case 1: {
                            uFormat = NumberFormat.getCurrencyInstance(this.ulocale);
                            break Label_0560;
                        }
                        case 2: {
                            uFormat = NumberFormat.getPercentInstance(this.ulocale);
                            break Label_0560;
                        }
                        case 3: {
                            uFormat = NumberFormat.getIntegerInstance(this.ulocale);
                            break Label_0560;
                        }
                        default: {
                            uFormat = new DecimalFormat(s2, new DecimalFormatSymbols(this.ulocale));
                            break Label_0560;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (findKeyword(s2, MessageFormat.dateModifierList)) {
                        case 0: {
                            uFormat = DateFormat.getDateInstance(2, this.ulocale);
                            break Label_0560;
                        }
                        case 1: {
                            uFormat = DateFormat.getDateInstance(3, this.ulocale);
                            break Label_0560;
                        }
                        case 2: {
                            uFormat = DateFormat.getDateInstance(2, this.ulocale);
                            break Label_0560;
                        }
                        case 3: {
                            uFormat = DateFormat.getDateInstance(1, this.ulocale);
                            break Label_0560;
                        }
                        case 4: {
                            uFormat = DateFormat.getDateInstance(0, this.ulocale);
                            break Label_0560;
                        }
                        default: {
                            uFormat = new SimpleDateFormat(s2, this.ulocale);
                            break Label_0560;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (findKeyword(s2, MessageFormat.dateModifierList)) {
                        case 0: {
                            uFormat = DateFormat.getTimeInstance(2, this.ulocale);
                            break Label_0560;
                        }
                        case 1: {
                            uFormat = DateFormat.getTimeInstance(3, this.ulocale);
                            break Label_0560;
                        }
                        case 2: {
                            uFormat = DateFormat.getTimeInstance(2, this.ulocale);
                            break Label_0560;
                        }
                        case 3: {
                            uFormat = DateFormat.getTimeInstance(1, this.ulocale);
                            break Label_0560;
                        }
                        case 4: {
                            uFormat = DateFormat.getTimeInstance(0, this.ulocale);
                            break Label_0560;
                        }
                        default: {
                            uFormat = new SimpleDateFormat(s2, this.ulocale);
                            break Label_0560;
                        }
                    }
                    break;
                }
                case 3: {
                    final RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(this.ulocale, 1);
                    final String trim = s2.trim();
                    if (trim.length() != 0) {
                        ruleBasedNumberFormat.setDefaultRuleSet(trim);
                    }
                    uFormat = ruleBasedNumberFormat;
                    break;
                }
                case 4: {
                    final RuleBasedNumberFormat ruleBasedNumberFormat2 = new RuleBasedNumberFormat(this.ulocale, 2);
                    final String trim2 = s2.trim();
                    if (trim2.length() != 0) {
                        ruleBasedNumberFormat2.setDefaultRuleSet(trim2);
                    }
                    uFormat = ruleBasedNumberFormat2;
                    break;
                }
                case 5: {
                    final RuleBasedNumberFormat ruleBasedNumberFormat3 = new RuleBasedNumberFormat(this.ulocale, 3);
                    final String trim3 = s2.trim();
                    if (trim3.length() != 0) {
                        ruleBasedNumberFormat3.setDefaultRuleSet(trim3);
                    }
                    uFormat = ruleBasedNumberFormat3;
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown format type \"" + s + "\"");
                }
            }
        }
        return uFormat;
    }
    
    private static final int findKeyword(String lowerCase, final String[] array) {
        lowerCase = PatternProps.trimWhiteSpace(lowerCase).toLowerCase(MessageFormat.rootLocale);
        while (0 < array.length) {
            if (lowerCase.equals(array[0])) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.ulocale.toLanguageTag());
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        objectOutputStream.writeObject(this.msgPattern.getApostropheMode());
        objectOutputStream.writeObject(this.msgPattern.getPatternString());
        if (this.customFormatArgStarts == null || this.customFormatArgStarts.isEmpty()) {
            objectOutputStream.writeInt(0);
        }
        else {
            objectOutputStream.writeInt(this.customFormatArgStarts.size());
            while (this.nextTopLevelArgStart(0) >= 0) {
                if (this.customFormatArgStarts.contains(0)) {
                    objectOutputStream.writeInt(0);
                    objectOutputStream.writeObject(this.cachedFormatters.get(0));
                }
                int n = 0;
                ++n;
            }
        }
        objectOutputStream.writeInt(0);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.ulocale = ULocale.forLanguageTag((String)objectInputStream.readObject());
        final MessagePattern.ApostropheMode apostropheMode = (MessagePattern.ApostropheMode)objectInputStream.readObject();
        if (this.msgPattern == null || apostropheMode != this.msgPattern.getApostropheMode()) {
            this.msgPattern = new MessagePattern(apostropheMode);
        }
        final String s = (String)objectInputStream.readObject();
        if (s != null) {
            this.applyPattern(s);
        }
        for (int i = objectInputStream.readInt(); i > 0; --i) {
            this.setFormat(objectInputStream.readInt(), (Format)objectInputStream.readObject());
        }
        for (int j = objectInputStream.readInt(); j > 0; --j) {
            objectInputStream.readInt();
            objectInputStream.readObject();
        }
    }
    
    private void cacheExplicitFormats() {
        if (this.cachedFormatters != null) {
            this.cachedFormatters.clear();
        }
        this.customFormatArgStarts = null;
        while (1 < this.msgPattern.countParts() - 2) {
            final MessagePattern.Part part = this.msgPattern.getPart(1);
            int n = 0;
            if (part.getType() == MessagePattern.Part.Type.ARG_START) {
                if (part.getArgType() == MessagePattern.ArgType.SIMPLE) {
                    n += 2;
                    final MessagePattern msgPattern = this.msgPattern;
                    final MessagePattern msgPattern2 = this.msgPattern;
                    final int n2 = 1;
                    ++n;
                    final String substring = msgPattern.getSubstring(msgPattern2.getPart(n2));
                    String substring2 = "";
                    final MessagePattern.Part part2;
                    if ((part2 = this.msgPattern.getPart(1)).getType() == MessagePattern.Part.Type.ARG_STYLE) {
                        substring2 = this.msgPattern.getSubstring(part2);
                        ++n;
                    }
                    this.setArgStartFormat(1, this.createAppropriateFormat(substring, substring2));
                }
            }
            ++n;
        }
    }
    
    private void setArgStartFormat(final int n, final Format format) {
        if (this.cachedFormatters == null) {
            this.cachedFormatters = new HashMap();
        }
        this.cachedFormatters.put(n, format);
    }
    
    private void setCustomArgStartFormat(final int n, final Format format) {
        this.setArgStartFormat(n, format);
        if (this.customFormatArgStarts == null) {
            this.customFormatArgStarts = new HashSet();
        }
        this.customFormatArgStarts.add(n);
    }
    
    public static String autoQuoteApostrophe(final String s) {
        final StringBuilder sb = new StringBuilder(s.length() * 2);
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            Label_0217: {
                switch (false) {
                    case 0: {
                        switch (char1) {
                            case 123: {
                                int n = 0;
                                ++n;
                                break;
                            }
                        }
                        break;
                    }
                    case 1: {
                        switch (char1) {
                            case 39: {
                                break Label_0217;
                            }
                            case 123:
                            case 125: {
                                break Label_0217;
                            }
                            default: {
                                sb.append('\'');
                                break Label_0217;
                            }
                        }
                        break;
                    }
                    case 2: {
                        switch (char1) {
                            default: {
                                break Label_0217;
                            }
                        }
                        break;
                    }
                    case 3: {
                        switch (char1) {
                            case 123: {
                                int n = 0;
                                ++n;
                                break Label_0217;
                            }
                            case 125: {
                                int n = 0;
                                --n;
                                if (!false) {}
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            sb.append(char1);
            int n2 = 0;
            ++n2;
        }
        if (false == true || 0 == 2) {
            sb.append('\'');
        }
        return new String(sb);
    }
    
    static {
        $assertionsDisabled = !MessageFormat.class.desiredAssertionStatus();
        MessageFormat.typeList = new String[] { "number", "date", "time", "spellout", "ordinal", "duration" };
        modifierList = new String[] { "", "currency", "percent", "integer" };
        dateModifierList = new String[] { "", "short", "medium", "long", "full" };
        rootLocale = new Locale("");
    }
    
    private static final class AttributeAndPosition
    {
        private AttributedCharacterIterator.Attribute key;
        private Object value;
        private int start;
        private int limit;
        
        public AttributeAndPosition(final Object o, final int n, final int n2) {
            this.init(Field.ARGUMENT, o, n, n2);
        }
        
        public AttributeAndPosition(final AttributedCharacterIterator.Attribute attribute, final Object o, final int n, final int n2) {
            this.init(attribute, o, n, n2);
        }
        
        public void init(final AttributedCharacterIterator.Attribute key, final Object value, final int start, final int limit) {
            this.key = key;
            this.value = value;
            this.start = start;
            this.limit = limit;
        }
        
        static AttributedCharacterIterator.Attribute access$100(final AttributeAndPosition attributeAndPosition) {
            return attributeAndPosition.key;
        }
        
        static Object access$200(final AttributeAndPosition attributeAndPosition) {
            return attributeAndPosition.value;
        }
        
        static int access$300(final AttributeAndPosition attributeAndPosition) {
            return attributeAndPosition.start;
        }
        
        static int access$400(final AttributeAndPosition attributeAndPosition) {
            return attributeAndPosition.limit;
        }
    }
    
    public static class Field extends Format.Field
    {
        private static final long serialVersionUID = 7510380454602616157L;
        public static final Field ARGUMENT;
        
        protected Field(final String s) {
            super(s);
        }
        
        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != Field.class) {
                throw new InvalidObjectException("A subclass of MessageFormat.Field must implement readResolve.");
            }
            if (this.getName().equals(Field.ARGUMENT.getName())) {
                return Field.ARGUMENT;
            }
            throw new InvalidObjectException("Unknown attribute name.");
        }
        
        static {
            ARGUMENT = new Field("message argument field");
        }
    }
    
    private static final class AppendableWrapper
    {
        private Appendable app;
        private int length;
        private List attributes;
        
        public AppendableWrapper(final StringBuilder app) {
            this.app = app;
            this.length = app.length();
            this.attributes = null;
        }
        
        public AppendableWrapper(final StringBuffer app) {
            this.app = app;
            this.length = app.length();
            this.attributes = null;
        }
        
        public void useAttributes() {
            this.attributes = new ArrayList();
        }
        
        public void append(final CharSequence charSequence) {
            this.app.append(charSequence);
            this.length += charSequence.length();
        }
        
        public void append(final CharSequence charSequence, final int n, final int n2) {
            this.app.append(charSequence, n, n2);
            this.length += n2 - n;
        }
        
        public void append(final CharacterIterator characterIterator) {
            this.length += append(this.app, characterIterator);
        }
        
        public static int append(final Appendable appendable, final CharacterIterator characterIterator) {
            int beginIndex = characterIterator.getBeginIndex();
            final int endIndex = characterIterator.getEndIndex();
            final int n = endIndex - beginIndex;
            if (beginIndex < endIndex) {
                appendable.append(characterIterator.first());
                while (++beginIndex < endIndex) {
                    appendable.append(characterIterator.next());
                }
            }
            return n;
        }
        
        public void formatAndAppend(final Format format, final Object o) {
            if (this.attributes == null) {
                this.append(format.format(o));
            }
            else {
                final AttributedCharacterIterator formatToCharacterIterator = format.formatToCharacterIterator(o);
                final int length = this.length;
                this.append(formatToCharacterIterator);
                formatToCharacterIterator.first();
                int i = formatToCharacterIterator.getIndex();
                final int endIndex = formatToCharacterIterator.getEndIndex();
                final int n = length - i;
                while (i < endIndex) {
                    final Map<AttributedCharacterIterator.Attribute, Object> attributes = formatToCharacterIterator.getAttributes();
                    final int runLimit = formatToCharacterIterator.getRunLimit();
                    if (attributes.size() != 0) {
                        for (final Map.Entry<AttributedCharacterIterator.Attribute, V> entry : attributes.entrySet()) {
                            this.attributes.add(new AttributeAndPosition(entry.getKey(), entry.getValue(), n + i, n + runLimit));
                        }
                    }
                    i = runLimit;
                    formatToCharacterIterator.setIndex(i);
                }
            }
        }
        
        static List access$000(final AppendableWrapper appendableWrapper) {
            return appendableWrapper.attributes;
        }
        
        static int access$500(final AppendableWrapper appendableWrapper) {
            return appendableWrapper.length;
        }
    }
    
    private static final class PluralSelectorProvider implements PluralFormat.PluralSelector
    {
        private ULocale locale;
        private PluralRules rules;
        private PluralRules.PluralType type;
        
        public PluralSelectorProvider(final ULocale locale, final PluralRules.PluralType type) {
            this.locale = locale;
            this.type = type;
        }
        
        public String select(final double n) {
            if (this.rules == null) {
                this.rules = PluralRules.forLocale(this.locale, this.type);
            }
            return this.rules.select(n);
        }
    }
}
