package org.apache.logging.log4j.core.net.ssl;

import java.security.*;
import java.io.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "trustStore", category = "Core", printObject = true)
public class TrustStoreConfiguration extends StoreConfiguration
{
    private KeyStore trustStore;
    private String trustStoreType;
    
    public TrustStoreConfiguration(final String s, final String s2) {
        super(s, s2);
        this.trustStoreType = "JKS";
        this.trustStore = null;
    }
    
    @Override
    protected void load() throws StoreConfigurationException {
        TrustStoreConfiguration.LOGGER.debug("Loading truststore from file with params(location={})", this.getLocation());
        if (this.getLocation() == null) {
            throw new IOException("The location is null");
        }
        final KeyStore instance = KeyStore.getInstance(this.trustStoreType);
        final FileInputStream fileInputStream = new FileInputStream(this.getLocation());
        instance.load(fileInputStream, this.getPasswordAsCharArray());
        if (fileInputStream != null) {
            fileInputStream.close();
        }
        this.trustStore = instance;
        TrustStoreConfiguration.LOGGER.debug("Truststore successfully loaded with params(location={})", this.getLocation());
    }
    
    public KeyStore getTrustStore() throws StoreConfigurationException {
        if (this.trustStore == null) {
            this.load();
        }
        return this.trustStore;
    }
    
    @PluginFactory
    public static TrustStoreConfiguration createTrustStoreConfiguration(@PluginAttribute("location") final String s, @PluginAttribute("password") final String s2) {
        return new TrustStoreConfiguration(s, s2);
    }
}
