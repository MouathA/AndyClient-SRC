package org.apache.logging.log4j.core.net.ssl;

import java.security.*;
import java.io.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "keyStore", category = "Core", printObject = true)
public class KeyStoreConfiguration extends StoreConfiguration
{
    private KeyStore keyStore;
    private String keyStoreType;
    
    public KeyStoreConfiguration(final String s, final String s2) {
        super(s, s2);
        this.keyStoreType = "JKS";
        this.keyStore = null;
    }
    
    @Override
    protected void load() throws StoreConfigurationException {
        KeyStoreConfiguration.LOGGER.debug("Loading keystore from file with params(location={})", this.getLocation());
        if (this.getLocation() == null) {
            throw new IOException("The location is null");
        }
        final FileInputStream fileInputStream = new FileInputStream(this.getLocation());
        final KeyStore instance = KeyStore.getInstance(this.keyStoreType);
        instance.load(fileInputStream, this.getPasswordAsCharArray());
        this.keyStore = instance;
        if (fileInputStream != null) {
            fileInputStream.close();
        }
        KeyStoreConfiguration.LOGGER.debug("Keystore successfully loaded with params(location={})", this.getLocation());
    }
    
    public KeyStore getKeyStore() throws StoreConfigurationException {
        if (this.keyStore == null) {
            this.load();
        }
        return this.keyStore;
    }
    
    @PluginFactory
    public static KeyStoreConfiguration createKeyStoreConfiguration(@PluginAttribute("location") final String s, @PluginAttribute("password") final String s2) {
        return new KeyStoreConfiguration(s, s2);
    }
}
