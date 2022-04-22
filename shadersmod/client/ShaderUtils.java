package shadersmod.client;

import optifine.*;

public class ShaderUtils
{
    public static ShaderOption getShaderOption(final String s, final ShaderOption[] array) {
        if (array == null) {
            return null;
        }
        while (0 < array.length) {
            final ShaderOption shaderOption = array[0];
            if (shaderOption.getName().equals(s)) {
                return shaderOption;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public static ShaderProfile detectProfile(final ShaderProfile[] array, final ShaderOption[] array2, final boolean b) {
        if (array == null) {
            return null;
        }
        while (0 < array.length) {
            final ShaderProfile shaderProfile = array[0];
            if (matchProfile(shaderProfile, array2, b)) {
                return shaderProfile;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public static boolean matchProfile(final ShaderProfile shaderProfile, final ShaderOption[] array, final boolean b) {
        if (shaderProfile == null) {
            return false;
        }
        if (array == null) {
            return false;
        }
        final String[] options = shaderProfile.getOptions();
        while (0 < options.length) {
            final String s = options[0];
            final ShaderOption shaderOption = getShaderOption(s, array);
            if (shaderOption != null && !Config.equals(b ? shaderOption.getValueDefault() : shaderOption.getValue(), shaderProfile.getValue(s))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
}
