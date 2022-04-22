package io.netty.handler.codec.spdy;

import io.netty.util.internal.*;
import java.util.*;

public class DefaultSpdySettingsFrame implements SpdySettingsFrame
{
    private boolean clear;
    private final Map settingsMap;
    
    public DefaultSpdySettingsFrame() {
        this.settingsMap = new TreeMap();
    }
    
    @Override
    public Set ids() {
        return this.settingsMap.keySet();
    }
    
    @Override
    public boolean isSet(final int n) {
        return this.settingsMap.containsKey(n);
    }
    
    @Override
    public int getValue(final int n) {
        final Integer value = n;
        if (this.settingsMap.containsKey(value)) {
            return ((Setting)this.settingsMap.get(value)).getValue();
        }
        return -1;
    }
    
    @Override
    public SpdySettingsFrame setValue(final int n, final int n2) {
        return this.setValue(n, n2, false, false);
    }
    
    @Override
    public SpdySettingsFrame setValue(final int n, final int value, final boolean persist, final boolean persisted) {
        if (n < 0 || n > 16777215) {
            throw new IllegalArgumentException("Setting ID is not valid: " + n);
        }
        final Integer value2 = n;
        if (this.settingsMap.containsKey(value2)) {
            final Setting setting = this.settingsMap.get(value2);
            setting.setValue(value);
            setting.setPersist(persist);
            setting.setPersisted(persisted);
        }
        else {
            this.settingsMap.put(value2, new Setting(value, persist, persisted));
        }
        return this;
    }
    
    @Override
    public SpdySettingsFrame removeValue(final int n) {
        final Integer value = n;
        if (this.settingsMap.containsKey(value)) {
            this.settingsMap.remove(value);
        }
        return this;
    }
    
    @Override
    public boolean isPersistValue(final int n) {
        final Integer value = n;
        return this.settingsMap.containsKey(value) && ((Setting)this.settingsMap.get(value)).isPersist();
    }
    
    @Override
    public SpdySettingsFrame setPersistValue(final int n, final boolean persist) {
        final Integer value = n;
        if (this.settingsMap.containsKey(value)) {
            ((Setting)this.settingsMap.get(value)).setPersist(persist);
        }
        return this;
    }
    
    @Override
    public boolean isPersisted(final int n) {
        final Integer value = n;
        return this.settingsMap.containsKey(value) && ((Setting)this.settingsMap.get(value)).isPersisted();
    }
    
    @Override
    public SpdySettingsFrame setPersisted(final int n, final boolean persisted) {
        final Integer value = n;
        if (this.settingsMap.containsKey(value)) {
            ((Setting)this.settingsMap.get(value)).setPersisted(persisted);
        }
        return this;
    }
    
    @Override
    public boolean clearPreviouslyPersistedSettings() {
        return this.clear;
    }
    
    @Override
    public SpdySettingsFrame setClearPreviouslyPersistedSettings(final boolean clear) {
        this.clear = clear;
        return this;
    }
    
    private Set getSettings() {
        return this.settingsMap.entrySet();
    }
    
    private void appendSettings(final StringBuilder sb) {
        for (final Map.Entry<K, Setting> entry : this.getSettings()) {
            final Setting setting = entry.getValue();
            sb.append("--> ");
            sb.append(entry.getKey());
            sb.append(':');
            sb.append(setting.getValue());
            sb.append(" (persist value: ");
            sb.append(setting.isPersist());
            sb.append("; persisted: ");
            sb.append(setting.isPersisted());
            sb.append(')');
            sb.append(StringUtil.NEWLINE);
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append(StringUtil.NEWLINE);
        this.appendSettings(sb);
        sb.setLength(sb.length() - StringUtil.NEWLINE.length());
        return sb.toString();
    }
    
    private static final class Setting
    {
        private int value;
        private boolean persist;
        private boolean persisted;
        
        Setting(final int value, final boolean persist, final boolean persisted) {
            this.value = value;
            this.persist = persist;
            this.persisted = persisted;
        }
        
        int getValue() {
            return this.value;
        }
        
        void setValue(final int value) {
            this.value = value;
        }
        
        boolean isPersist() {
            return this.persist;
        }
        
        void setPersist(final boolean persist) {
            this.persist = persist;
        }
        
        boolean isPersisted() {
            return this.persisted;
        }
        
        void setPersisted(final boolean persisted) {
            this.persisted = persisted;
        }
    }
}
