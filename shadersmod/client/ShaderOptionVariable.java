package shadersmod.client;

import java.util.regex.*;
import optifine.*;
import java.util.*;

public class ShaderOptionVariable extends ShaderOption
{
    private static final Pattern PATTERN_VARIABLE;
    
    static {
        PATTERN_VARIABLE = Pattern.compile("^\\s*#define\\s+([A-Za-z0-9_]+)\\s+(-?[0-9\\.]*)F?f?\\s*(//.*)?$");
    }
    
    public ShaderOptionVariable(final String s, final String s2, final String s3, final String[] array, final String s4) {
        super(s, s2, s3, array, s3, s4);
        this.setVisible(this.getValues().length > 1);
    }
    
    @Override
    public String getSourceLine() {
        return "#define " + this.getName() + " " + this.getValue() + " // Shader option " + this.getValue();
    }
    
    @Override
    public String getValueColor(final String s) {
        return "§a";
    }
    
    @Override
    public boolean matchesLine(final String s) {
        final Matcher matcher = ShaderOptionVariable.PATTERN_VARIABLE.matcher(s);
        return matcher.matches() && matcher.group(1).matches(this.getName());
    }
    
    public static ShaderOption parseOption(final String s, String removePrefix) {
        final Matcher matcher = ShaderOptionVariable.PATTERN_VARIABLE.matcher(s);
        if (!matcher.matches()) {
            return null;
        }
        final String group = matcher.group(1);
        final String group2 = matcher.group(2);
        String s2 = matcher.group(3);
        final String segment = StrUtils.getSegment(s2, "[", "]");
        if (segment != null && segment.length() > 0) {
            s2 = s2.replace(segment, "").trim();
        }
        final String[] values = parseValues(group2, segment);
        if (group != null && group.length() > 0) {
            removePrefix = StrUtils.removePrefix(removePrefix, "/shaders/");
            return new ShaderOptionVariable(group, s2, group2, values, removePrefix);
        }
        return null;
    }
    
    public static String[] parseValues(final String s, String s2) {
        final String[] array = { s };
        if (s2 == null) {
            return array;
        }
        s2 = s2.trim();
        s2 = StrUtils.removePrefix(s2, "[");
        s2 = StrUtils.removeSuffix(s2, "]");
        s2 = s2.trim();
        if (s2.length() <= 0) {
            return array;
        }
        String[] tokenize = Config.tokenize(s2, " ");
        if (tokenize.length <= 0) {
            return array;
        }
        if (!Arrays.asList(tokenize).contains(s)) {
            tokenize = (String[])Config.addObjectToArray(tokenize, s, 0);
        }
        return tokenize;
    }
}
