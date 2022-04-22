package shadersmod.client;

import optifine.*;
import java.util.regex.*;

public class ShaderOptionSwitchConst extends ShaderOptionSwitch
{
    private static final Pattern PATTERN_CONST;
    
    static {
        PATTERN_CONST = Pattern.compile("^\\s*const\\s*bool\\s*([A-Za-z0-9_]+)\\s*=\\s*(true|false)\\s*;\\s*(//.*)?$");
    }
    
    public ShaderOptionSwitchConst(final String s, final String s2, final String s3, final String s4) {
        super(s, s2, s3, s4);
    }
    
    @Override
    public String getSourceLine() {
        return "const bool " + this.getName() + " = " + this.getValue() + "; // Shader option " + this.getValue();
    }
    
    public static ShaderOption parseOption(final String s, String removePrefix) {
        final Matcher matcher = ShaderOptionSwitchConst.PATTERN_CONST.matcher(s);
        if (!matcher.matches()) {
            return null;
        }
        final String group = matcher.group(1);
        final String group2 = matcher.group(2);
        final String group3 = matcher.group(3);
        if (group != null && group.length() > 0) {
            removePrefix = StrUtils.removePrefix(removePrefix, "/shaders/");
            final ShaderOptionSwitchConst shaderOptionSwitchConst = new ShaderOptionSwitchConst(group, group3, group2, removePrefix);
            shaderOptionSwitchConst.setVisible(false);
            return shaderOptionSwitchConst;
        }
        return null;
    }
    
    @Override
    public boolean matchesLine(final String s) {
        final Matcher matcher = ShaderOptionSwitchConst.PATTERN_CONST.matcher(s);
        return matcher.matches() && matcher.group(1).matches(this.getName());
    }
    
    @Override
    public boolean checkUsed() {
        return false;
    }
}
