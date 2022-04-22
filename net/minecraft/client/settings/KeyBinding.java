package net.minecraft.client.settings;

import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.resources.*;

public class KeyBinding implements Comparable
{
    private static final List keybindArray;
    private static final IntHashMap hash;
    private static final Set keybindSet;
    private final String keyDescription;
    private final int keyCodeDefault;
    private final String keyCategory;
    private int keyCode;
    public boolean pressed;
    private int pressTime;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000628";
        keybindArray = Lists.newArrayList();
        hash = new IntHashMap();
        keybindSet = Sets.newHashSet();
    }
    
    public static void onTick(final int n) {
        if (n != 0) {
            final KeyBinding keyBinding = (KeyBinding)KeyBinding.hash.lookup(n);
            if (keyBinding != null) {
                final KeyBinding keyBinding2 = keyBinding;
                ++keyBinding2.pressTime;
            }
        }
    }
    
    public static void setKeyBindState(final int n, final boolean pressed) {
        if (n != 0) {
            final KeyBinding keyBinding = (KeyBinding)KeyBinding.hash.lookup(n);
            if (keyBinding != null) {
                keyBinding.pressed = pressed;
            }
        }
    }
    
    public static void unPressAllKeys() {
        final Iterator<KeyBinding> iterator = KeyBinding.keybindArray.iterator();
        while (iterator.hasNext()) {
            iterator.next().unpressKey();
        }
    }
    
    public static void resetKeyBindingArrayAndHash() {
        KeyBinding.hash.clearMap();
        for (final KeyBinding keyBinding : KeyBinding.keybindArray) {
            KeyBinding.hash.addKey(keyBinding.keyCode, keyBinding);
        }
    }
    
    public static Set getKeybinds() {
        return KeyBinding.keybindSet;
    }
    
    public KeyBinding(final String keyDescription, final int n, final String keyCategory) {
        this.keyDescription = keyDescription;
        this.keyCode = n;
        this.keyCodeDefault = n;
        this.keyCategory = keyCategory;
        KeyBinding.keybindArray.add(this);
        KeyBinding.hash.addKey(n, this);
        KeyBinding.keybindSet.add(keyCategory);
    }
    
    public boolean getIsKeyPressed() {
        return this.pressed;
    }
    
    public String getKeyCategory() {
        return this.keyCategory;
    }
    
    public boolean isPressed() {
        if (this.pressTime == 0) {
            return false;
        }
        --this.pressTime;
        return true;
    }
    
    private void unpressKey() {
        this.pressTime = 0;
        this.pressed = false;
    }
    
    public String getKeyDescription() {
        return this.keyDescription;
    }
    
    public int getKeyCodeDefault() {
        return this.keyCodeDefault;
    }
    
    public int getKeyCode() {
        return this.keyCode;
    }
    
    public void setKeyCode(final int keyCode) {
        this.keyCode = keyCode;
    }
    
    public int compareTo(final KeyBinding keyBinding) {
        int n = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(keyBinding.keyCategory, new Object[0]));
        if (n == 0) {
            n = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(keyBinding.keyDescription, new Object[0]));
        }
        return n;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((KeyBinding)o);
    }
    
    public boolean isKeyDown() {
        return this.pressed;
    }
}
