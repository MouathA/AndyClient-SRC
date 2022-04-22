package net.minecraft.util;

public class ChatAllowedCharacters
{
    public static final char[] allowedCharactersArray;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001606";
        allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
    }
    
    public static boolean isAllowedCharacter(final char c) {
        return c != '§' && c >= ' ' && c != '\u007f';
    }
    
    public static String filterAllowedCharacters(final String s) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            final char c = charArray[0];
            if (isAllowedCharacter(c)) {
                sb.append(c);
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
}
