package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.util.*;
import java.lang.reflect.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.status.*;

public final class PatternParser
{
    private static final char ESCAPE_CHAR = '%';
    private static final Logger LOGGER;
    private static final int BUF_SIZE = 32;
    private static final int DECIMAL = 10;
    private final Configuration config;
    private final Map converterRules;
    
    public PatternParser(final String s) {
        this(null, s, null, null);
    }
    
    public PatternParser(final Configuration configuration, final String s, final Class clazz) {
        this(configuration, s, clazz, null);
    }
    
    public PatternParser(final Configuration config, final String s, final Class clazz, final Class clazz2) {
        this.config = config;
        final PluginManager pluginManager = new PluginManager(s, clazz);
        pluginManager.collectPlugins();
        final Map plugins = pluginManager.getPlugins();
        final HashMap<String, Class> converterRules = new HashMap<String, Class>();
        final Iterator<PluginType> iterator = plugins.values().iterator();
        while (iterator.hasNext()) {
            final Class pluginClass = iterator.next().getPluginClass();
            if (clazz2 != null && !clazz2.isAssignableFrom(pluginClass)) {
                continue;
            }
            final ConverterKeys converterKeys = pluginClass.getAnnotation(ConverterKeys.class);
            if (converterKeys == null) {
                continue;
            }
            final String[] value = converterKeys.value();
            while (0 < value.length) {
                converterRules.put(value[0], pluginClass);
                int n = 0;
                ++n;
            }
        }
        this.converterRules = converterRules;
    }
    
    public List parse(final String s) {
        return this.parse(s, false);
    }
    
    public List parse(final String s, final boolean b) {
        final ArrayList<PatternFormatter> list = new ArrayList<PatternFormatter>();
        final ArrayList<PatternConverter> list2 = new ArrayList<PatternConverter>();
        final ArrayList<FormattingInfo> list3 = new ArrayList<FormattingInfo>();
        this.parse(s, list2, list3);
        final Iterator<Object> iterator = list3.iterator();
        for (final PatternConverter patternConverter : list2) {
            LogEventPatternConverter logEventPatternConverter;
            if (patternConverter instanceof LogEventPatternConverter) {
                logEventPatternConverter = (LogEventPatternConverter)patternConverter;
                final boolean b2 = false | logEventPatternConverter.handlesThrowable();
            }
            else {
                logEventPatternConverter = new LiteralPatternConverter(this.config, "");
            }
            FormattingInfo default1;
            if (iterator.hasNext()) {
                default1 = iterator.next();
            }
            else {
                default1 = FormattingInfo.getDefault();
            }
            list.add(new PatternFormatter(logEventPatternConverter, default1));
        }
        if (b && !false) {
            list.add(new PatternFormatter(ExtendedThrowablePatternConverter.newInstance(null), FormattingInfo.getDefault()));
        }
        return list;
    }
    
    private static int extractConverter(final char c, final String s, int n, final StringBuilder sb, final StringBuilder sb2) {
        sb.setLength(0);
        if (!Character.isUnicodeIdentifierStart(c)) {
            return n;
        }
        sb.append(c);
        while (n < s.length() && Character.isUnicodeIdentifierPart(s.charAt(n))) {
            sb.append(s.charAt(n));
            sb2.append(s.charAt(n));
            ++n;
        }
        return n;
    }
    
    private static int extractOptions(final String s, int n, final List list) {
        while (n < s.length() && s.charAt(n) == '{') {
            final int n2 = n++;
            int index;
            do {
                index = s.indexOf(125, n);
                if (index != -1) {
                    final int index2 = s.indexOf("{", n);
                    if (index2 != -1 && index2 < index) {
                        n = index + 1;
                        int n3 = 0;
                        ++n3;
                    }
                    else {
                        if (0 <= 0) {
                            continue;
                        }
                        int n3 = 0;
                        --n3;
                    }
                }
            } while (0 > 0);
            if (index == -1) {
                break;
            }
            list.add(s.substring(n2 + 1, index));
            n = index + 1;
        }
        return n;
    }
    
    public void parse(final String s, final List list, final List list2) {
        if (s == null) {
            throw new NullPointerException("pattern");
        }
        final StringBuilder sb = new StringBuilder(32);
        final int length = s.length();
        ParserState parserState = ParserState.LITERAL_STATE;
        FormattingInfo formattingInfo = FormattingInfo.getDefault();
        while (0 < length) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            final char char1 = s.charAt(n);
            switch (parserState) {
                case LITERAL_STATE: {
                    if (length == 0) {
                        sb.append(char1);
                        continue;
                    }
                    if (char1 != '%') {
                        sb.append(char1);
                        continue;
                    }
                    switch (s.charAt(0)) {
                        case '%': {
                            sb.append(char1);
                            ++n2;
                            continue;
                        }
                        default: {
                            if (sb.length() != 0) {
                                list.add(new LiteralPatternConverter(this.config, sb.toString()));
                                list2.add(FormattingInfo.getDefault());
                            }
                            sb.setLength(0);
                            sb.append(char1);
                            parserState = ParserState.CONVERTER_STATE;
                            formattingInfo = FormattingInfo.getDefault();
                            continue;
                        }
                    }
                    break;
                }
                case CONVERTER_STATE: {
                    sb.append(char1);
                    switch (char1) {
                        case '-': {
                            formattingInfo = new FormattingInfo(true, formattingInfo.getMinLength(), formattingInfo.getMaxLength());
                            continue;
                        }
                        case '.': {
                            parserState = ParserState.DOT_STATE;
                            continue;
                        }
                        default: {
                            if (char1 >= '0' && char1 <= '9') {
                                formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), char1 - '0', formattingInfo.getMaxLength());
                                parserState = ParserState.MIN_STATE;
                                continue;
                            }
                            n2 = this.finalizeConverter(char1, s, 0, sb, formattingInfo, this.converterRules, list, list2);
                            parserState = ParserState.LITERAL_STATE;
                            formattingInfo = FormattingInfo.getDefault();
                            sb.setLength(0);
                            continue;
                        }
                    }
                    break;
                }
                case MIN_STATE: {
                    sb.append(char1);
                    if (char1 >= '0' && char1 <= '9') {
                        formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength() * 10 + char1 - 48, formattingInfo.getMaxLength());
                        continue;
                    }
                    if (char1 == '.') {
                        parserState = ParserState.DOT_STATE;
                        continue;
                    }
                    n2 = this.finalizeConverter(char1, s, 0, sb, formattingInfo, this.converterRules, list, list2);
                    parserState = ParserState.LITERAL_STATE;
                    formattingInfo = FormattingInfo.getDefault();
                    sb.setLength(0);
                    continue;
                }
                case DOT_STATE: {
                    sb.append(char1);
                    if (char1 >= '0' && char1 <= '9') {
                        formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), char1 - '0');
                        parserState = ParserState.MAX_STATE;
                        continue;
                    }
                    PatternParser.LOGGER.error("Error occurred in position " + 0 + ".\n Was expecting digit, instead got char \"" + char1 + "\".");
                    parserState = ParserState.LITERAL_STATE;
                    continue;
                }
                case MAX_STATE: {
                    sb.append(char1);
                    if (char1 >= '0' && char1 <= '9') {
                        formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), formattingInfo.getMaxLength() * 10 + char1 - 48);
                        continue;
                    }
                    n2 = this.finalizeConverter(char1, s, 0, sb, formattingInfo, this.converterRules, list, list2);
                    parserState = ParserState.LITERAL_STATE;
                    formattingInfo = FormattingInfo.getDefault();
                    sb.setLength(0);
                    continue;
                }
            }
        }
        if (sb.length() != 0) {
            list.add(new LiteralPatternConverter(this.config, sb.toString()));
            list2.add(FormattingInfo.getDefault());
        }
    }
    
    private PatternConverter createConverter(final String s, final StringBuilder sb, final Map map, final List list) {
        String substring = s;
        Class clazz = null;
        for (int length = s.length(); length > 0 && clazz == null; --length) {
            substring = substring.substring(0, length);
            if (clazz == null && map != null) {
                clazz = map.get(substring);
            }
        }
        if (clazz == null) {
            PatternParser.LOGGER.error("Unrecognized format specifier [" + s + "]");
            return null;
        }
        final Method[] declaredMethods = clazz.getDeclaredMethods();
        Method method = null;
        final Method[] array = declaredMethods;
        int n = 0;
        while (0 < array.length) {
            final Method method2 = array[0];
            if (Modifier.isStatic(method2.getModifiers()) && method2.getDeclaringClass().equals(clazz) && method2.getName().equals("newInstance")) {
                if (method == null) {
                    method = method2;
                }
                else if (method2.getReturnType().equals(method.getReturnType())) {
                    PatternParser.LOGGER.error("Class " + clazz + " cannot contain multiple static newInstance methods");
                    return null;
                }
            }
            ++n;
        }
        if (method == null) {
            PatternParser.LOGGER.error("Class " + clazz + " does not contain a static newInstance method");
            return null;
        }
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Object[] array2 = (Object[])((parameterTypes.length > 0) ? new Object[parameterTypes.length] : null);
        if (array2 != null) {
            final Class<?>[] array3 = parameterTypes;
            while (0 < array3.length) {
                final Class<?> clazz2 = array3[0];
                if (clazz2.isArray() && clazz2.getName().equals("[Ljava.lang.String;")) {
                    array2[0] = list.toArray(new String[list.size()]);
                }
                else if (clazz2.isAssignableFrom(Configuration.class)) {
                    array2[0] = this.config;
                }
                else {
                    PatternParser.LOGGER.error("Unknown parameter type " + clazz2.getName() + " for static newInstance method of " + clazz.getName());
                }
                ++n;
                int n2 = 0;
                ++n2;
            }
            if (true) {
                return null;
            }
        }
        final Object invoke = method.invoke(null, array2);
        if (invoke instanceof PatternConverter) {
            sb.delete(0, sb.length() - (s.length() - substring.length()));
            return (PatternConverter)invoke;
        }
        PatternParser.LOGGER.warn("Class " + clazz.getName() + " does not extend PatternConverter.");
        return null;
    }
    
    private int finalizeConverter(final char c, final String s, int n, final StringBuilder sb, final FormattingInfo formattingInfo, final Map map, final List list, final List list2) {
        final StringBuilder sb2 = new StringBuilder();
        n = extractConverter(c, s, n, sb2, sb);
        final String string = sb2.toString();
        final ArrayList list3 = new ArrayList();
        n = extractOptions(s, n, list3);
        final PatternConverter converter = this.createConverter(string, sb, map, list3);
        if (converter == null) {
            StringBuilder sb3;
            if (Strings.isEmpty(string)) {
                sb3 = new StringBuilder("Empty conversion specifier starting at position ");
            }
            else {
                sb3 = new StringBuilder("Unrecognized conversion specifier [");
                sb3.append(string);
                sb3.append("] starting at position ");
            }
            sb3.append(Integer.toString(n));
            sb3.append(" in conversion pattern.");
            PatternParser.LOGGER.error(sb3.toString());
            list.add(new LiteralPatternConverter(this.config, sb.toString()));
            list2.add(FormattingInfo.getDefault());
        }
        else {
            list.add(converter);
            list2.add(formattingInfo);
            if (sb.length() > 0) {
                list.add(new LiteralPatternConverter(this.config, sb.toString()));
                list2.add(FormattingInfo.getDefault());
            }
        }
        sb.setLength(0);
        return n;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
    
    private enum ParserState
    {
        LITERAL_STATE("LITERAL_STATE", 0), 
        CONVERTER_STATE("CONVERTER_STATE", 1), 
        DOT_STATE("DOT_STATE", 2), 
        MIN_STATE("MIN_STATE", 3), 
        MAX_STATE("MAX_STATE", 4);
        
        private static final ParserState[] $VALUES;
        
        private ParserState(final String s, final int n) {
        }
        
        static {
            $VALUES = new ParserState[] { ParserState.LITERAL_STATE, ParserState.CONVERTER_STATE, ParserState.DOT_STATE, ParserState.MIN_STATE, ParserState.MAX_STATE };
        }
    }
}
