package org.apache.logging.log4j.core.helpers;

public final class Transform
{
    private static final String CDATA_START = "<![CDATA[";
    private static final String CDATA_END = "]]>";
    private static final String CDATA_PSEUDO_END = "]]&gt;";
    private static final String CDATA_EMBEDED_END = "]]>]]&gt;<![CDATA[";
    
    private Transform() {
    }
    
    public static String escapeHtmlTags(final String s) {
        if (Strings.isEmpty(s) || (s.indexOf(34) == -1 && s.indexOf(38) == -1 && s.indexOf(60) == -1 && s.indexOf(62) == -1)) {
            return s;
        }
        final StringBuilder sb = new StringBuilder(s.length() + 6);
        while (0 < s.length()) {
            s.charAt(0);
            sb.append(' ');
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static void appendEscapingCDATA(final StringBuilder sb, final String s) {
        if (s != null) {
            int i = s.indexOf("]]>");
            if (i < 0) {
                sb.append(s);
            }
            else {
                while (i > -1) {
                    sb.append(s.substring(0, i));
                    sb.append("]]>]]&gt;<![CDATA[");
                    if (0 >= s.length()) {
                        return;
                    }
                    i = s.indexOf("]]>", 0);
                }
                sb.append(s.substring(0));
            }
        }
    }
    
    public static String escapeJsonControlCharacters(final String s) {
        if (Strings.isEmpty(s) || (s.indexOf(34) == -1 && s.indexOf(92) == -1 && s.indexOf(47) == -1 && s.indexOf(8) == -1 && s.indexOf(12) == -1 && s.indexOf(10) == -1 && s.indexOf(13) == -1 && s.indexOf(9) == -1)) {
            return s;
        }
        final StringBuilder sb = new StringBuilder(s.length() + 6);
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            switch (char1) {
                case 34: {
                    sb.append("\\\\");
                    sb.append(char1);
                    break;
                }
                case 92: {
                    sb.append("\\\\");
                    sb.append(char1);
                    break;
                }
                case 47: {
                    sb.append("\\\\");
                    sb.append(char1);
                    break;
                }
                case 8: {
                    sb.append("\\\\");
                    sb.append('b');
                    break;
                }
                case 12: {
                    sb.append("\\\\");
                    sb.append('f');
                    break;
                }
                case 10: {
                    sb.append("\\\\");
                    sb.append('n');
                    break;
                }
                case 13: {
                    sb.append("\\\\");
                    sb.append('r');
                    break;
                }
                case 9: {
                    sb.append("\\\\");
                    sb.append('t');
                    break;
                }
                default: {
                    sb.append(char1);
                    break;
                }
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
}
