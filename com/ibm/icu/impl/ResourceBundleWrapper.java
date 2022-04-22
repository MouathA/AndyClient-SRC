package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import java.security.*;
import java.io.*;
import java.util.*;

public class ResourceBundleWrapper extends UResourceBundle
{
    private ResourceBundle bundle;
    private String localeID;
    private String baseName;
    private List keys;
    private static final boolean DEBUG;
    
    private ResourceBundleWrapper(final ResourceBundle bundle) {
        this.bundle = null;
        this.localeID = null;
        this.baseName = null;
        this.keys = null;
        this.bundle = bundle;
    }
    
    @Override
    protected void setLoadingStatus(final int n) {
    }
    
    @Override
    protected Object handleGetObject(final String s) {
        Object object = null;
        if (this != null) {
            object = this.bundle.getObject(s);
        }
        if (object == null) {
            throw new MissingResourceException("Can't find resource for bundle " + this.baseName + ", key " + s, this.getClass().getName(), s);
        }
        return object;
    }
    
    @Override
    public Enumeration getKeys() {
        return Collections.enumeration((Collection<Object>)this.keys);
    }
    
    private void initKeysVector() {
        ResourceBundleWrapper resourceBundleWrapper = this;
        this.keys = new ArrayList();
        while (resourceBundleWrapper != null) {
            final Enumeration<String> keys = resourceBundleWrapper.bundle.getKeys();
            while (keys.hasMoreElements()) {
                final String s = keys.nextElement();
                if (!this.keys.contains(s)) {
                    this.keys.add(s);
                }
            }
            resourceBundleWrapper = (ResourceBundleWrapper)resourceBundleWrapper.getParent();
        }
    }
    
    @Override
    protected String getLocaleID() {
        return this.localeID;
    }
    
    @Override
    protected String getBaseName() {
        return this.bundle.getClass().getName().replace('.', '/');
    }
    
    @Override
    public ULocale getULocale() {
        return new ULocale(this.localeID);
    }
    
    public UResourceBundle getParent() {
        return (UResourceBundle)this.parent;
    }
    
    public static UResourceBundle getBundleInstance(final String s, final String s2, final ClassLoader classLoader, final boolean b) {
        final UResourceBundle instantiateBundle = instantiateBundle(s, s2, classLoader, b);
        if (instantiateBundle == null) {
            String s3 = "_";
            if (s.indexOf(47) >= 0) {
                s3 = "/";
            }
            throw new MissingResourceException("Could not find the bundle " + s + s3 + s2, "", "");
        }
        return instantiateBundle;
    }
    
    protected static synchronized UResourceBundle instantiateBundle(final String s, final String s2, ClassLoader fallbackClassLoader, final boolean b) {
        if (fallbackClassLoader == null) {
            fallbackClassLoader = Utility.getFallbackClassLoader();
        }
        final ClassLoader classLoader = fallbackClassLoader;
        String string = s;
        final ULocale default1 = ULocale.getDefault();
        if (s2.length() != 0) {
            string = string + "_" + s2;
        }
        ResourceBundleWrapper resourceBundleWrapper = (ResourceBundleWrapper)UResourceBundle.loadFromCache(classLoader, string, default1);
        if (resourceBundleWrapper == null) {
            ResourceBundle resourceBundle = null;
            final int lastIndex = s2.lastIndexOf(95);
            if (lastIndex != -1) {
                final String substring = s2.substring(0, lastIndex);
                resourceBundle = UResourceBundle.loadFromCache(classLoader, s + "_" + substring, default1);
                if (resourceBundle == null) {
                    resourceBundle = instantiateBundle(s, substring, classLoader, b);
                }
            }
            else if (s2.length() > 0) {
                resourceBundle = UResourceBundle.loadFromCache(classLoader, s, default1);
                if (resourceBundle == null) {
                    resourceBundle = instantiateBundle(s, "", classLoader, b);
                }
            }
            ResourceBundleWrapper resourceBundleWrapper2 = new ResourceBundleWrapper((ResourceBundle)classLoader.loadClass(string).asSubclass(ResourceBundle.class).newInstance());
            if (resourceBundle != null) {
                resourceBundleWrapper2.setParent(resourceBundle);
            }
            resourceBundleWrapper2.baseName = s;
            resourceBundleWrapper2.localeID = s2;
            if (true) {
                final InputStream inputStream = AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction(classLoader, string.replace('.', '/') + ".properties") {
                    final ClassLoader val$cl;
                    final String val$resName;
                    
                    public InputStream run() {
                        if (this.val$cl != null) {
                            return this.val$cl.getResourceAsStream(this.val$resName);
                        }
                        return ClassLoader.getSystemResourceAsStream(this.val$resName);
                    }
                    
                    public Object run() {
                        return this.run();
                    }
                });
                if (inputStream != null) {
                    final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    resourceBundleWrapper2 = new ResourceBundleWrapper(new PropertyResourceBundle(bufferedInputStream));
                    if (resourceBundle != null) {
                        resourceBundleWrapper2.setParent(resourceBundle);
                    }
                    resourceBundleWrapper2.baseName = s;
                    resourceBundleWrapper2.localeID = s2;
                    bufferedInputStream.close();
                }
                if (resourceBundleWrapper2 == null) {
                    final String string2 = default1.toString();
                    if (s2.length() > 0 && s2.indexOf(95) < 0 && string2.indexOf(s2) == -1) {
                        resourceBundleWrapper2 = (ResourceBundleWrapper)UResourceBundle.loadFromCache(classLoader, s + "_" + string2, default1);
                        if (resourceBundleWrapper2 == null) {
                            resourceBundleWrapper2 = (ResourceBundleWrapper)instantiateBundle(s, string2, classLoader, b);
                        }
                    }
                }
                if (resourceBundleWrapper2 == null) {
                    resourceBundleWrapper2 = (ResourceBundleWrapper)resourceBundle;
                }
            }
            resourceBundleWrapper = (ResourceBundleWrapper)UResourceBundle.addToCache(classLoader, string, default1, resourceBundleWrapper2);
        }
        if (resourceBundleWrapper != null) {
            resourceBundleWrapper.initKeysVector();
        }
        else if (ResourceBundleWrapper.DEBUG) {
            System.out.println("Returning null for " + s + "_" + s2);
        }
        return resourceBundleWrapper;
    }
    
    static {
        DEBUG = ICUDebug.enabled("resourceBundleWrapper");
    }
}
