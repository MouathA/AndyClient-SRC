package shadersmod.client;

import optifine.*;
import java.util.*;

public abstract class ShaderOption
{
    private String name;
    private String description;
    private String value;
    private String[] values;
    private String valueDefault;
    private String[] paths;
    private boolean enabled;
    private boolean visible;
    public static final String COLOR_GREEN;
    public static final String COLOR_RED;
    public static final String COLOR_BLUE;
    
    public ShaderOption(final String name, final String description, final String value, final String[] values, final String valueDefault, final String s) {
        this.name = null;
        this.description = null;
        this.value = null;
        this.values = null;
        this.valueDefault = null;
        this.paths = null;
        this.enabled = true;
        this.visible = true;
        this.name = name;
        this.description = description;
        this.value = value;
        this.values = values;
        this.valueDefault = valueDefault;
        if (s != null) {
            this.paths = new String[] { s };
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getDescriptionText() {
        return Shaders.translate("option." + this.getName() + ".comment", StrUtils.removePrefix(Config.normalize(this.description), "//"));
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public boolean setValue(final String value) {
        if (getIndex(value, this.values) < 0) {
            return false;
        }
        this.value = value;
        return true;
    }
    
    public String getValueDefault() {
        return this.valueDefault;
    }
    
    public void resetValue() {
        this.value = this.valueDefault;
    }
    
    public void nextValue() {
        final int index = getIndex(this.value, this.values);
        if (index >= 0) {
            this.value = this.values[(index + 1) % this.values.length];
        }
    }
    
    private static int getIndex(final String s, final String[] array) {
        while (0 < array.length) {
            if (array[0].equals(s)) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public String[] getPaths() {
        return this.paths;
    }
    
    public void addPaths(final String[] array) {
        final List<String> list = Arrays.asList(this.paths);
        while (0 < array.length) {
            final String s = array[0];
            if (!list.contains(s)) {
                this.paths = (String[])Config.addObjectToArray(this.paths, s);
            }
            int n = 0;
            ++n;
        }
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isChanged() {
        return !Config.equals(this.value, this.valueDefault);
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public boolean isValidValue(final String s) {
        return getIndex(s, this.values) >= 0;
    }
    
    public String getNameText() {
        return Shaders.translate("option." + this.name, this.name);
    }
    
    public String getValueText(final String s) {
        return s;
    }
    
    public String getValueColor(final String s) {
        return "";
    }
    
    public boolean matchesLine(final String s) {
        return false;
    }
    
    public boolean checkUsed() {
        return false;
    }
    
    public boolean isUsedInLine(final String s) {
        return false;
    }
    
    public String getSourceLine() {
        return null;
    }
    
    public String[] getValues() {
        return this.values.clone();
    }
    
    @Override
    public String toString() {
        return this.name + ", value: " + this.value + ", valueDefault: " + this.valueDefault + ", paths: " + Config.arrayToString(this.paths);
    }
    
    static {
        COLOR_RED = "§c";
        COLOR_BLUE = "§9";
        COLOR_GREEN = "§a";
    }
}
