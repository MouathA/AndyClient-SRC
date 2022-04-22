package shadersmod.client;

import optifine.*;
import java.util.regex.*;

public class ShaderOptionSwitch extends ShaderOption
{
    private static final Pattern PATTERN_DEFINE;
    private static final Pattern PATTERN_IFDEF;
    
    static {
        PATTERN_DEFINE = Pattern.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
        PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");
    }
    
    public ShaderOptionSwitch(final String s, final String s2, final String s3, final String s4) {
        super(s, s2, s3, new String[] { "true", "false" }, s3, s4);
    }
    
    @Override
    public String getSourceLine() {
        return isTrue(this.getValue()) ? ("#define " + this.getName() + " // Shader option ON") : ("//#define " + this.getName() + " // Shader option OFF");
    }
    
    @Override
    public String getValueText(final String s) {
        return isTrue(s) ? Lang.getOn() : Lang.getOff();
    }
    
    @Override
    public String getValueColor(final String s) {
        return isTrue(s) ? "§a" : "§c";
    }
    
    public static ShaderOption parseOption(final String s, String removePrefix) {
        final Matcher matcher = ShaderOptionSwitch.PATTERN_DEFINE.matcher(s);
        if (!matcher.matches()) {
            return null;
        }
        final String group = matcher.group(1);
        final String group2 = matcher.group(2);
        final String group3 = matcher.group(3);
        if (group2 != null && group2.length() > 0) {
            final boolean b = !Config.equals(group, "//");
            removePrefix = StrUtils.removePrefix(removePrefix, "/shaders/");
            return new ShaderOptionSwitch(group2, group3, String.valueOf(b), removePrefix);
        }
        return null;
    }
    
    @Override
    public boolean matchesLine(final String s) {
        final Matcher matcher = ShaderOptionSwitch.PATTERN_DEFINE.matcher(s);
        return matcher.matches() && matcher.group(2).matches(this.getName());
    }
    
    @Override
    public boolean checkUsed() {
        return true;
    }
    
    @Override
    public boolean isUsedInLine(final String s) {
        final Matcher matcher = ShaderOptionSwitch.PATTERN_IFDEF.matcher(s);
        return matcher.matches() && matcher.group(2).equals(this.getName());
    }
    
    public static boolean isTrue(final String s) {
        return Boolean.valueOf(s);
    }
}
