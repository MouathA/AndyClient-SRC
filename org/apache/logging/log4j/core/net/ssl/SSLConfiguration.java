package org.apache.logging.log4j.core.net.ssl;

import org.apache.logging.log4j.status.*;
import java.security.*;
import javax.net.ssl.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "ssl", category = "Core", printObject = true)
public class SSLConfiguration
{
    private static final StatusLogger LOGGER;
    private KeyStoreConfiguration keyStoreConfig;
    private TrustStoreConfiguration trustStoreConfig;
    private SSLContext sslContext;
    
    private SSLConfiguration(final KeyStoreConfiguration keyStoreConfig, final TrustStoreConfiguration trustStoreConfig) {
        this.keyStoreConfig = keyStoreConfig;
        this.trustStoreConfig = trustStoreConfig;
        this.sslContext = null;
    }
    
    public SSLSocketFactory getSSLSocketFactory() {
        if (this.sslContext == null) {
            this.sslContext = this.createSSLContext();
        }
        return this.sslContext.getSocketFactory();
    }
    
    public SSLServerSocketFactory getSSLServerSocketFactory() {
        if (this.sslContext == null) {
            this.sslContext = this.createSSLContext();
        }
        return this.sslContext.getServerSocketFactory();
    }
    
    private SSLContext createSSLContext() {
        final SSLContext sslContextBasedOnConfiguration = this.createSSLContextBasedOnConfiguration();
        SSLConfiguration.LOGGER.debug("Creating SSLContext with the given parameters");
        return sslContextBasedOnConfiguration;
    }
    
    private SSLContext createSSLContextWithTrustStoreFailure() {
        final SSLContext sslContextWithDefaultTrustManagerFactory = this.createSSLContextWithDefaultTrustManagerFactory();
        SSLConfiguration.LOGGER.debug("Creating SSLContext with default truststore");
        return sslContextWithDefaultTrustManagerFactory;
    }
    
    private SSLContext createSSLContextWithKeyStoreFailure() {
        final SSLContext sslContextWithDefaultKeyManagerFactory = this.createSSLContextWithDefaultKeyManagerFactory();
        SSLConfiguration.LOGGER.debug("Creating SSLContext with default keystore");
        return sslContextWithDefaultKeyManagerFactory;
    }
    
    private SSLContext createSSLContextBasedOnConfiguration() throws KeyStoreConfigurationException, TrustStoreConfigurationException {
        return this.createSSLContext(false, false);
    }
    
    private SSLContext createSSLContextWithDefaultKeyManagerFactory() throws TrustStoreConfigurationException {
        return this.createSSLContext(true, false);
    }
    
    private SSLContext createSSLContextWithDefaultTrustManagerFactory() throws KeyStoreConfigurationException {
        return this.createSSLContext(false, true);
    }
    
    private SSLContext createDefaultSSLContext() {
        return SSLContext.getDefault();
    }
    
    private SSLContext createSSLContext(final boolean b, final boolean b2) throws KeyStoreConfigurationException, TrustStoreConfigurationException {
        KeyManager[] keyManagers = null;
        TrustManager[] trustManagers = null;
        final SSLContext instance = SSLContext.getInstance("SSL");
        if (!b) {
            keyManagers = this.loadKeyManagerFactory().getKeyManagers();
        }
        if (!b2) {
            trustManagers = this.loadTrustManagerFactory().getTrustManagers();
        }
        instance.init(keyManagers, trustManagers, null);
        return instance;
    }
    
    private TrustManagerFactory loadTrustManagerFactory() throws TrustStoreConfigurationException {
        if (this.trustStoreConfig == null) {
            throw new TrustStoreConfigurationException(new Exception("The trustStoreConfiguration is null"));
        }
        final KeyStore trustStore = this.trustStoreConfig.getTrustStore();
        final TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        instance.init(trustStore);
        return instance;
    }
    
    private KeyManagerFactory loadKeyManagerFactory() throws KeyStoreConfigurationException {
        if (this.keyStoreConfig == null) {
            throw new KeyStoreConfigurationException(new Exception("The keyStoreConfiguration is null"));
        }
        final KeyStore keyStore = this.keyStoreConfig.getKeyStore();
        final KeyManagerFactory instance = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        instance.init(keyStore, this.keyStoreConfig.getPasswordAsCharArray());
        return instance;
    }
    
    public boolean equals(final SSLConfiguration sslConfiguration) {
        if (sslConfiguration == null) {
            return false;
        }
        if (this.keyStoreConfig != null) {
            this.keyStoreConfig.equals(sslConfiguration.keyStoreConfig);
        }
        else {
            final boolean b = this.keyStoreConfig == sslConfiguration.keyStoreConfig;
        }
        if (this.trustStoreConfig != null) {
            this.trustStoreConfig.equals(sslConfiguration.trustStoreConfig);
        }
        else {
            final boolean b2 = this.trustStoreConfig == sslConfiguration.trustStoreConfig;
        }
        return false && false;
    }
    
    @PluginFactory
    public static SSLConfiguration createSSLConfiguration(@PluginElement("keyStore") final KeyStoreConfiguration keyStoreConfiguration, @PluginElement("trustStore") final TrustStoreConfiguration trustStoreConfiguration) {
        return new SSLConfiguration(keyStoreConfiguration, trustStoreConfiguration);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
