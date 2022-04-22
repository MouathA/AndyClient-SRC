package shadersmod.client;

import java.util.regex.*;
import optifine.*;

public class ShaderOptionVariableConst extends ShaderOptionVariable
{
    private String type;
    private static final Pattern PATTERN_CONST;
    
    static {
        PATTERN_CONST = Pattern.compile("^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9\\.]+f?F?)\\s*;\\s*(//.*)?$");
    }
    
    public ShaderOptionVariableConst(final String s, final String type, final String s2, final String s3, final String[] array, final String s4) {
        super(s, s2, s3, array, s4);
        this.type = null;
        this.type = type;
    }
    
    @Override
    public String getSourceLine() {
        return "const " + this.type + " " + this.getName() + " = " + this.getValue() + "; // Shader option " + this.getValue();
    }
    
    @Override
    public boolean matchesLine(final String s) {
        final Matcher matcher = ShaderOptionVariableConst.PATTERN_CONST.matcher(s);
        return matcher.matches() && matcher.group(2).matches(this.getName());
    }
    
    public static ShaderOption parseOption(final String s, String removePrefix) {
        final Matcher matcher = ShaderOptionVariableConst.PATTERN_CONST.matcher(s);
        if (!matcher.matches()) {
            return null;
        }
        final String group = matcher.group(1);
        final String group2 = matcher.group(2);
        final String group3 = matcher.group(3);
        String s2 = matcher.group(4);
        final String segment = StrUtils.getSegment(s2, "[", "]");
        if (segment != null && segment.length() > 0) {
            s2 = s2.replace(segment, "").trim();
        }
        final String[] values = ShaderOptionVariable.parseValues(group3, segment);
        if (group2 != null && group2.length() > 0) {
            removePrefix = StrUtils.removePrefix(removePrefix, "/shaders/");
            return new ShaderOptionVariableConst(group2, group, s2, group3, values, removePrefix);
        }
        return null;
    }
}
