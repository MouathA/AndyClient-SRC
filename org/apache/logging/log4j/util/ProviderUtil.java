package org.apache.logging.log4j.util;

import org.apache.logging.log4j.*;
import java.security.*;
import org.apache.logging.log4j.status.*;
import java.net.*;
import org.apache.logging.log4j.spi.*;
import java.util.*;

public final class ProviderUtil
{
    private static final String PROVIDER_RESOURCE = "META-INF/log4j-provider.properties";
    private static final String API_VERSION = "Log4jAPIVersion";
    private static final Logger LOGGER;
    private static final List PROVIDERS;
    
    private ProviderUtil() {
    }
    
    public static Iterator getProviders() {
        return ProviderUtil.PROVIDERS.iterator();
    }
    
    public static boolean hasProviders() {
        return ProviderUtil.PROVIDERS.size() > 0;
    }
    
    public static ClassLoader findClassLoader() {
        ClassLoader classLoader;
        if (System.getSecurityManager() == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        else {
            classLoader = AccessController.doPrivileged((PrivilegedAction<ClassLoader>)new PrivilegedAction() {
                @Override
                public ClassLoader run() {
                    return Thread.currentThread().getContextClassLoader();
                }
                
                @Override
                public Object run() {
                    return this.run();
                }
            });
        }
        if (classLoader == null) {
            classLoader = ProviderUtil.class.getClassLoader();
        }
        return classLoader;
    }
    
    private static boolean validVersion(final String s) {
        final String[] compatible_API_VERSIONS = ProviderUtil.COMPATIBLE_API_VERSIONS;
        while (0 < compatible_API_VERSIONS.length) {
            if (s.startsWith(compatible_API_VERSIONS[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    static {
        ProviderUtil.COMPATIBLE_API_VERSIONS = new String[] { "2.0.0" };
        LOGGER = StatusLogger.getLogger();
        PROVIDERS = new ArrayList();
        final Enumeration<URL> resources = findClassLoader().getResources("META-INF/log4j-provider.properties");
        if (resources != null) {
            while (resources.hasMoreElements()) {
                final URL url = resources.nextElement();
                final Properties loadClose = PropertiesUtil.loadClose(url.openStream(), url);
                if (!validVersion(loadClose.getProperty("Log4jAPIVersion"))) {
                    continue;
                }
                ProviderUtil.PROVIDERS.add(new Provider(loadClose, url));
            }
        }
    }
}
