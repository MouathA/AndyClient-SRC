package Mood.Gui.clickgui.setting;

import DTool.modules.*;
import java.util.*;

public class SettingsManager
{
    private ArrayList settings;
    
    public SettingsManager() {
        this.settings = new ArrayList();
    }
    
    public void rSetting(final Setting setting) {
        this.settings.add(setting);
    }
    
    public ArrayList getSettings() {
        return this.settings;
    }
    
    public ArrayList getSettingsByMod(final Module module) {
        final ArrayList<Setting> list = new ArrayList<Setting>();
        for (final Setting setting : this.getSettings()) {
            if (setting.getParentMod().equals(module)) {
                list.add(setting);
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }
    
    public Setting getSettingByName(final String s) {
        for (final Setting setting : this.getSettings()) {
            if (setting.getName().equalsIgnoreCase(s)) {
                return setting;
            }
        }
        System.err.println("Error Setting NOT found: '" + s + "'!");
        return null;
    }
}
