package org.apache.http.conn.ssl;

import org.apache.http.annotation.*;
import javax.net.ssl.*;
import java.net.*;
import java.util.*;
import java.security.*;
import java.security.cert.*;

@NotThreadSafe
public class SSLContextBuilder
{
    static final String TLS = "TLS";
    static final String SSL = "SSL";
    private String protocol;
    private Set keymanagers;
    private Set trustmanagers;
    private SecureRandom secureRandom;
    
    public SSLContextBuilder() {
        this.keymanagers = new HashSet();
        this.trustmanagers = new HashSet();
    }
    
    public SSLContextBuilder useTLS() {
        this.protocol = "TLS";
        return this;
    }
    
    public SSLContextBuilder useSSL() {
        this.protocol = "SSL";
        return this;
    }
    
    public SSLContextBuilder useProtocol(final String protocol) {
        this.protocol = protocol;
        return this;
    }
    
    public SSLContextBuilder setSecureRandom(final SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
        return this;
    }
    
    public SSLContextBuilder loadTrustMaterial(final KeyStore keyStore, final TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException {
        final TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        instance.init(keyStore);
        final TrustManager[] trustManagers = instance.getTrustManagers();
        if (trustManagers != null) {
            if (trustStrategy != null) {
                while (0 < trustManagers.length) {
                    final TrustManager trustManager = trustManagers[0];
                    if (trustManager instanceof X509TrustManager) {
                        trustManagers[0] = new TrustManagerDelegate((X509TrustManager)trustManager, trustStrategy);
                    }
                    int n = 0;
                    ++n;
                }
            }
            final TrustManager[] array = trustManagers;
            while (0 < array.length) {
                this.trustmanagers.add(array[0]);
                int n2 = 0;
                ++n2;
            }
        }
        return this;
    }
    
    public SSLContextBuilder loadTrustMaterial(final KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException {
        return this.loadTrustMaterial(keyStore, null);
    }
    
    public SSLContextBuilder loadKeyMaterial(final KeyStore keyStore, final char[] array) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        this.loadKeyMaterial(keyStore, array, null);
        return this;
    }
    
    public SSLContextBuilder loadKeyMaterial(final KeyStore keyStore, final char[] array, final PrivateKeyStrategy privateKeyStrategy) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        final KeyManagerFactory instance = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        instance.init(keyStore, array);
        final KeyManager[] keyManagers = instance.getKeyManagers();
        if (keyManagers != null) {
            if (privateKeyStrategy != null) {
                while (0 < keyManagers.length) {
                    final KeyManager keyManager = keyManagers[0];
                    if (keyManager instanceof X509KeyManager) {
                        keyManagers[0] = new KeyManagerDelegate((X509KeyManager)keyManager, privateKeyStrategy);
                    }
                    int n = 0;
                    ++n;
                }
            }
            final KeyManager[] array2 = keyManagers;
            while (0 < array2.length) {
                this.keymanagers.add(array2[0]);
                int n2 = 0;
                ++n2;
            }
        }
        return this;
    }
    
    public SSLContext build() throws NoSuchAlgorithmException, KeyManagementException {
        final SSLContext instance = SSLContext.getInstance((this.protocol != null) ? this.protocol : "TLS");
        instance.init((KeyManager[])(this.keymanagers.isEmpty() ? null : ((KeyManager[])this.keymanagers.toArray(new KeyManager[this.keymanagers.size()]))), (TrustManager[])(this.trustmanagers.isEmpty() ? null : ((TrustManager[])this.trustmanagers.toArray(new TrustManager[this.trustmanagers.size()]))), this.secureRandom);
        return instance;
    }
    
    static class KeyManagerDelegate implements X509KeyManager
    {
        private final X509KeyManager keyManager;
        private final PrivateKeyStrategy aliasStrategy;
        
        KeyManagerDelegate(final X509KeyManager keyManager, final PrivateKeyStrategy aliasStrategy) {
            this.keyManager = keyManager;
            this.aliasStrategy = aliasStrategy;
        }
        
        public String[] getClientAliases(final String s, final Principal[] array) {
            return this.keyManager.getClientAliases(s, array);
        }
        
        public String chooseClientAlias(final String[] array, final Principal[] array2, final Socket socket) {
            final HashMap<String, PrivateKeyDetails> hashMap = new HashMap<String, PrivateKeyDetails>();
            while (0 < array.length) {
                final String s = array[0];
                final String[] clientAliases = this.keyManager.getClientAliases(s, array2);
                if (clientAliases != null) {
                    final String[] array3 = clientAliases;
                    while (0 < array3.length) {
                        final String s2 = array3[0];
                        hashMap.put(s2, new PrivateKeyDetails(s, this.keyManager.getCertificateChain(s2)));
                        int n = 0;
                        ++n;
                    }
                }
                int n2 = 0;
                ++n2;
            }
            return this.aliasStrategy.chooseAlias(hashMap, socket);
        }
        
        public String[] getServerAliases(final String s, final Principal[] array) {
            return this.keyManager.getServerAliases(s, array);
        }
        
        public String chooseServerAlias(final String s, final Principal[] array, final Socket socket) {
            final HashMap<String, PrivateKeyDetails> hashMap = new HashMap<String, PrivateKeyDetails>();
            final String[] serverAliases = this.keyManager.getServerAliases(s, array);
            if (serverAliases != null) {
                final String[] array2 = serverAliases;
                while (0 < array2.length) {
                    final String s2 = array2[0];
                    hashMap.put(s2, new PrivateKeyDetails(s, this.keyManager.getCertificateChain(s2)));
                    int n = 0;
                    ++n;
                }
            }
            return this.aliasStrategy.chooseAlias(hashMap, socket);
        }
        
        public X509Certificate[] getCertificateChain(final String s) {
            return this.keyManager.getCertificateChain(s);
        }
        
        public PrivateKey getPrivateKey(final String s) {
            return this.keyManager.getPrivateKey(s);
        }
    }
    
    static class TrustManagerDelegate implements X509TrustManager
    {
        private final X509TrustManager trustManager;
        private final TrustStrategy trustStrategy;
        
        TrustManagerDelegate(final X509TrustManager trustManager, final TrustStrategy trustStrategy) {
            this.trustManager = trustManager;
            this.trustStrategy = trustStrategy;
        }
        
        public void checkClientTrusted(final X509Certificate[] array, final String s) throws CertificateException {
            this.trustManager.checkClientTrusted(array, s);
        }
        
        public void checkServerTrusted(final X509Certificate[] array, final String s) throws CertificateException {
            if (!this.trustStrategy.isTrusted(array, s)) {
                this.trustManager.checkServerTrusted(array, s);
            }
        }
        
        public X509Certificate[] getAcceptedIssuers() {
            return this.trustManager.getAcceptedIssuers();
        }
    }
}
