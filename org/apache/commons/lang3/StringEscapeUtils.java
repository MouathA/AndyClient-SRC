package org.apache.commons.lang3;

import org.apache.commons.lang3.text.translate.*;
import java.io.*;

public class StringEscapeUtils
{
    public static final CharSequenceTranslator ESCAPE_JAVA;
    public static final CharSequenceTranslator ESCAPE_ECMASCRIPT;
    public static final CharSequenceTranslator ESCAPE_JSON;
    @Deprecated
    public static final CharSequenceTranslator ESCAPE_XML;
    public static final CharSequenceTranslator ESCAPE_XML10;
    public static final CharSequenceTranslator ESCAPE_XML11;
    public static final CharSequenceTranslator ESCAPE_HTML3;
    public static final CharSequenceTranslator ESCAPE_HTML4;
    public static final CharSequenceTranslator ESCAPE_CSV;
    public static final CharSequenceTranslator UNESCAPE_JAVA;
    public static final CharSequenceTranslator UNESCAPE_ECMASCRIPT;
    public static final CharSequenceTranslator UNESCAPE_JSON;
    public static final CharSequenceTranslator UNESCAPE_HTML3;
    public static final CharSequenceTranslator UNESCAPE_HTML4;
    public static final CharSequenceTranslator UNESCAPE_XML;
    public static final CharSequenceTranslator UNESCAPE_CSV;
    
    public static final String escapeJava(final String s) {
        return StringEscapeUtils.ESCAPE_JAVA.translate(s);
    }
    
    public static final String escapeEcmaScript(final String s) {
        return StringEscapeUtils.ESCAPE_ECMASCRIPT.translate(s);
    }
    
    public static final String escapeJson(final String s) {
        return StringEscapeUtils.ESCAPE_JSON.translate(s);
    }
    
    public static final String unescapeJava(final String s) {
        return StringEscapeUtils.UNESCAPE_JAVA.translate(s);
    }
    
    public static final String unescapeEcmaScript(final String s) {
        return StringEscapeUtils.UNESCAPE_ECMASCRIPT.translate(s);
    }
    
    public static final String unescapeJson(final String s) {
        return StringEscapeUtils.UNESCAPE_JSON.translate(s);
    }
    
    public static final String escapeHtml4(final String s) {
        return StringEscapeUtils.ESCAPE_HTML4.translate(s);
    }
    
    public static final String escapeHtml3(final String s) {
        return StringEscapeUtils.ESCAPE_HTML3.translate(s);
    }
    
    public static final String unescapeHtml4(final String s) {
        return StringEscapeUtils.UNESCAPE_HTML4.translate(s);
    }
    
    public static final String unescapeHtml3(final String s) {
        return StringEscapeUtils.UNESCAPE_HTML3.translate(s);
    }
    
    @Deprecated
    public static final String escapeXml(final String s) {
        return StringEscapeUtils.ESCAPE_XML.translate(s);
    }
    
    public static String escapeXml10(final String s) {
        return StringEscapeUtils.ESCAPE_XML10.translate(s);
    }
    
    public static String escapeXml11(final String s) {
        return StringEscapeUtils.ESCAPE_XML11.translate(s);
    }
    
    public static final String unescapeXml(final String s) {
        return StringEscapeUtils.UNESCAPE_XML.translate(s);
    }
    
    public static final String escapeCsv(final String s) {
        return StringEscapeUtils.ESCAPE_CSV.translate(s);
    }
    
    public static final String unescapeCsv(final String s) {
        return StringEscapeUtils.UNESCAPE_CSV.translate(s);
    }
    
    static {
        ESCAPE_JAVA = new LookupTranslator((CharSequence[][])new String[][] { { "\"", "\\\"" }, { "\\", "\\\\" } }).with(new LookupTranslator((CharSequence[][])EntityArrays.JAVA_CTRL_CHARS_ESCAPE())).with(JavaUnicodeEscaper.outsideOf(32, 127));
        ESCAPE_ECMASCRIPT = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator((CharSequence[][])new String[][] { { "'", "\\'" }, { "\"", "\\\"" }, { "\\", "\\\\" }, { "/", "\\/" } }), new LookupTranslator((CharSequence[][])EntityArrays.JAVA_CTRL_CHARS_ESCAPE()), JavaUnicodeEscaper.outsideOf(32, 127) });
        ESCAPE_JSON = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator((CharSequence[][])new String[][] { { "\"", "\\\"" }, { "\\", "\\\\" }, { "/", "\\/" } }), new LookupTranslator((CharSequence[][])EntityArrays.JAVA_CTRL_CHARS_ESCAPE()), JavaUnicodeEscaper.outsideOf(32, 127) });
        ESCAPE_XML = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator((CharSequence[][])EntityArrays.BASIC_ESCAPE()), new LookupTranslator((CharSequence[][])EntityArrays.APOS_ESCAPE()) });
        ESCAPE_XML10 = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator((CharSequence[][])EntityArrays.BASIC_ESCAPE()), new LookupTranslator((CharSequence[][])EntityArrays.APOS_ESCAPE()), new LookupTranslator((CharSequence[][])new String[][] { { "\u0000", "" }, { "\u0001", "" }, { "\u0002", "" }, { "\u0003", "" }, { "\u0004", "" }, { "\u0005", "" }, { "\u0006", "" }, { "\u0007", "" }, { "\b", "" }, { "\u000b", "" }, { "\f", "" }, { "\u000e", "" }, { "\u000f", "" }, { "\u0010", "" }, { "\u0011", "" }, { "\u0012", "" }, { "\u0013", "" }, { "\u0014", "" }, { "\u0015", "" }, { "\u0016", "" }, { "\u0017", "" }, { "\u0018", "" }, { "\u0019", "" }, { "\u001a", "" }, { "\u001b", "" }, { "\u001c", "" }, { "\u001d", "" }, { "\u001e", "" }, { "\u001f", "" }, { "\ufffe", "" }, { "\uffff", "" } }), NumericEntityEscaper.between(127, 132), NumericEntityEscaper.between(134, 159), new UnicodeUnpairedSurrogateRemover() });
        ESCAPE_XML11 = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator((CharSequence[][])EntityArrays.BASIC_ESCAPE()), new LookupTranslator((CharSequence[][])EntityArrays.APOS_ESCAPE()), new LookupTranslator((CharSequence[][])new String[][] { { "\u0000", "" }, { "\u000b", "&#11;" }, { "\f", "&#12;" }, { "\ufffe", "" }, { "\uffff", "" } }), NumericEntityEscaper.between(1, 8), NumericEntityEscaper.between(14, 31), NumericEntityEscaper.between(127, 132), NumericEntityEscaper.between(134, 159), new UnicodeUnpairedSurrogateRemover() });
        ESCAPE_HTML3 = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator((CharSequence[][])EntityArrays.BASIC_ESCAPE()), new LookupTranslator((CharSequence[][])EntityArrays.ISO8859_1_ESCAPE()) });
        ESCAPE_HTML4 = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator((CharSequence[][])EntityArrays.BASIC_ESCAPE()), new LookupTranslator((CharSequence[][])EntityArrays.ISO8859_1_ESCAPE()), new LookupTranslator((CharSequence[][])EntityArrays.HTML40_EXTENDED_ESCAPE()) });
        ESCAPE_CSV = new CsvEscaper();
        UNESCAPE_JAVA = new AggregateTranslator(new CharSequenceTranslator[] { new OctalUnescaper(), new UnicodeUnescaper(), new LookupTranslator((CharSequence[][])EntityArrays.JAVA_CTRL_CHARS_UNESCAPE()), new LookupTranslator((CharSequence[][])new String[][] { { "\\\\", "\\" }, { "\\\"", "\"" }, { "\\'", "'" }, { "\\", "" } }) });
        UNESCAPE_ECMASCRIPT = StringEscapeUtils.UNESCAPE_JAVA;
        UNESCAPE_JSON = StringEscapeUtils.UNESCAPE_JAVA;
        UNESCAPE_HTML3 = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator((CharSequence[][])EntityArrays.BASIC_UNESCAPE()), new LookupTranslator((CharSequence[][])EntityArrays.ISO8859_1_UNESCAPE()), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]) });
        UNESCAPE_HTML4 = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator((CharSequence[][])EntityArrays.BASIC_UNESCAPE()), new LookupTranslator((CharSequence[][])EntityArrays.ISO8859_1_UNESCAPE()), new LookupTranslator((CharSequence[][])EntityArrays.HTML40_EXTENDED_UNESCAPE()), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]) });
        UNESCAPE_XML = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator((CharSequence[][])EntityArrays.BASIC_UNESCAPE()), new LookupTranslator((CharSequence[][])EntityArrays.APOS_UNESCAPE()), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]) });
        UNESCAPE_CSV = new CsvUnescaper();
    }
    
    static class CsvUnescaper extends CharSequenceTranslator
    {
        private static final char CSV_DELIMITER = ',';
        private static final char CSV_QUOTE = '\"';
        private static final String CSV_QUOTE_STR;
        private static final char[] CSV_SEARCH_CHARS;
        
        @Override
        public int translate(final CharSequence charSequence, final int n, final Writer writer) throws IOException {
            if (n != 0) {
                throw new IllegalStateException("CsvUnescaper should never reach the [1] index");
            }
            if (charSequence.charAt(0) != '\"' || charSequence.charAt(charSequence.length() - 1) != '\"') {
                writer.write(charSequence.toString());
                return Character.codePointCount(charSequence, 0, charSequence.length());
            }
            final String string = charSequence.subSequence(1, charSequence.length() - 1).toString();
            if (StringUtils.containsAny(string, CsvUnescaper.CSV_SEARCH_CHARS)) {
                writer.write(StringUtils.replace(string, CsvUnescaper.CSV_QUOTE_STR + CsvUnescaper.CSV_QUOTE_STR, CsvUnescaper.CSV_QUOTE_STR));
            }
            else {
                writer.write(charSequence.toString());
            }
            return Character.codePointCount(charSequence, 0, charSequence.length());
        }
        
        static {
            CSV_QUOTE_STR = String.valueOf('\"');
            CSV_SEARCH_CHARS = new char[] { ',', '\"', '\r', '\n' };
        }
    }
    
    static class CsvEscaper extends CharSequenceTranslator
    {
        private static final char CSV_DELIMITER = ',';
        private static final char CSV_QUOTE = '\"';
        private static final String CSV_QUOTE_STR;
        private static final char[] CSV_SEARCH_CHARS;
        
        @Override
        public int translate(final CharSequence charSequence, final int n, final Writer writer) throws IOException {
            if (n != 0) {
                throw new IllegalStateException("CsvEscaper should never reach the [1] index");
            }
            if (StringUtils.containsNone(charSequence.toString(), CsvEscaper.CSV_SEARCH_CHARS)) {
                writer.write(charSequence.toString());
            }
            else {
                writer.write(34);
                writer.write(StringUtils.replace(charSequence.toString(), CsvEscaper.CSV_QUOTE_STR, CsvEscaper.CSV_QUOTE_STR + CsvEscaper.CSV_QUOTE_STR));
                writer.write(34);
            }
            return Character.codePointCount(charSequence, 0, charSequence.length());
        }
        
        static {
            CSV_QUOTE_STR = String.valueOf('\"');
            CSV_SEARCH_CHARS = new char[] { ',', '\"', '\r', '\n' };
        }
    }
}
