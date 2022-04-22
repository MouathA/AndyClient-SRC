package com.ibm.icu.util;

import java.util.*;

public class OverlayBundle extends ResourceBundle
{
    private String[] baseNames;
    private Locale locale;
    private ResourceBundle[] bundles;
    
    @Deprecated
    public OverlayBundle(final String[] baseNames, final Locale locale) {
        this.baseNames = baseNames;
        this.locale = locale;
        this.bundles = new ResourceBundle[baseNames.length];
    }
    
    @Override
    @Deprecated
    protected Object handleGetObject(final String s) throws MissingResourceException {
        Object object = null;
        while (0 < this.bundles.length) {
            this.load(0);
            object = this.bundles[0].getObject(s);
            if (object != null) {
                break;
            }
            int n = 0;
            ++n;
        }
        return object;
    }
    
    @Override
    @Deprecated
    public Enumeration getKeys() {
        final int n = this.bundles.length - 1;
        this.load(n);
        return this.bundles[n].getKeys();
    }
    
    private void load(final int n) throws MissingResourceException {
        if (this.bundles[n] == null) {
            this.bundles[n] = ResourceBundle.getBundle(this.baseNames[n], this.locale);
            if (this.bundles[n].getLocale().equals(this.locale)) {
                return;
            }
            if (this.locale.getCountry().length() == 0 || n != this.bundles.length - 1) {}
            if (true) {
                this.bundles[n] = ResourceBundle.getBundle(this.baseNames[n], new Locale("xx", this.locale.getCountry(), this.locale.getVariant()));
            }
        }
    }
}
