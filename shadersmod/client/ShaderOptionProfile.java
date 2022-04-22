package shadersmod.client;

import optifine.*;
import java.util.*;

public class ShaderOptionProfile extends ShaderOption
{
    private ShaderProfile[] profiles;
    private ShaderOption[] options;
    private static final String NAME_PROFILE;
    private static final String VALUE_CUSTOM;
    
    public ShaderOptionProfile(final ShaderProfile[] profiles, final ShaderOption[] options) {
        super("<profile>", "", detectProfileName(profiles, options), getProfileNames(profiles), detectProfileName(profiles, options, true), null);
        this.profiles = null;
        this.options = null;
        this.profiles = profiles;
        this.options = options;
    }
    
    @Override
    public void nextValue() {
        super.nextValue();
        if (this.getValue().equals("<custom>")) {
            super.nextValue();
        }
        this.applyProfileOptions();
    }
    
    public void updateProfile() {
        final ShaderProfile profile = this.getProfile(this.getValue());
        if (profile == null || !ShaderUtils.matchProfile(profile, this.options, false)) {
            this.setValue(detectProfileName(this.profiles, this.options));
        }
    }
    
    private void applyProfileOptions() {
        final ShaderProfile profile = this.getProfile(this.getValue());
        if (profile != null) {
            final String[] options = profile.getOptions();
            while (0 < options.length) {
                final String s = options[0];
                final ShaderOption option = this.getOption(s);
                if (option != null) {
                    option.setValue(profile.getValue(s));
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    private ShaderOption getOption(final String s) {
        while (0 < this.options.length) {
            final ShaderOption shaderOption = this.options[0];
            if (shaderOption.getName().equals(s)) {
                return shaderOption;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    private ShaderProfile getProfile(final String s) {
        while (0 < this.profiles.length) {
            final ShaderProfile shaderProfile = this.profiles[0];
            if (shaderProfile.getName().equals(s)) {
                return shaderProfile;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    @Override
    public String getNameText() {
        return "\u160c\u1605\u164d\u1610\u160b\u1602\u1607\u1606\u1611\u1610\u164d\u1613\u1611\u160c\u1605\u160a\u160f\u1606";
    }
    
    @Override
    public String getValueText(final String s) {
        return s.equals("<custom>") ? Lang.get("of.general.custom", "<custom>") : Shaders.translate("profile." + s, s);
    }
    
    @Override
    public String getValueColor(final String s) {
        return s.equals("<custom>") ? "§c" : "§a";
    }
    
    private static String detectProfileName(final ShaderProfile[] array, final ShaderOption[] array2) {
        return detectProfileName(array, array2, false);
    }
    
    private static String detectProfileName(final ShaderProfile[] array, final ShaderOption[] array2, final boolean b) {
        final ShaderProfile detectProfile = ShaderUtils.detectProfile(array, array2, b);
        return (detectProfile == null) ? "<custom>" : detectProfile.getName();
    }
    
    private static String[] getProfileNames(final ShaderProfile[] array) {
        final ArrayList<String> list = new ArrayList<String>();
        while (0 < array.length) {
            list.add(array[0].getName());
            int n = 0;
            ++n;
        }
        list.add("<custom>");
        return list.toArray(new String[list.size()]);
    }
    
    static {
        VALUE_CUSTOM = "<custom>";
        NAME_PROFILE = "<profile>";
    }
}
