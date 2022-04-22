package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.security.*;
import java.nio.charset.*;
import java.util.*;
import java.io.*;
import java.net.*;

public final class UTF8ResourceBundleControl extends ResourceBundle.Control
{
    private static final UTF8ResourceBundleControl INSTANCE;
    
    public static ResourceBundle.Control get() {
        return UTF8ResourceBundleControl.INSTANCE;
    }
    
    @Override
    public ResourceBundle newBundle(final String baseName, final Locale locale, final String format, final ClassLoader loader, final boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        if (!format.equals("java.properties")) {
            return super.newBundle(baseName, locale, format, loader, reload);
        }
        final InputStream inputStream = AccessController.doPrivileged(UTF8ResourceBundleControl::lambda$newBundle$0);
        if (inputStream != null) {
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            final PropertyResourceBundle propertyResourceBundle = new PropertyResourceBundle(inputStreamReader);
            inputStreamReader.close();
            return propertyResourceBundle;
        }
        return null;
    }
    
    private static InputStream lambda$newBundle$0(final boolean b, final ClassLoader classLoader, final String s) throws Exception {
        if (b) {
            final URL resource = classLoader.getResource(s);
            if (resource != null) {
                final URLConnection openConnection = resource.openConnection();
                if (openConnection != null) {
                    openConnection.setUseCaches(false);
                    return openConnection.getInputStream();
                }
            }
            return null;
        }
        return classLoader.getResourceAsStream(s);
    }
    
    static {
        INSTANCE = new UTF8ResourceBundleControl();
    }
}
