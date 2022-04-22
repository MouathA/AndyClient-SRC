package com.mojang.authlib.properties;

import java.security.*;
import org.apache.commons.codec.binary.*;

public class Property
{
    private final String name;
    private final String value;
    private final String signature;
    
    public Property(final String s, final String s2) {
        this(s, s2, null);
    }
    
    public Property(final String name, final String value, final String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getSignature() {
        return this.signature;
    }
    
    public boolean hasSignature() {
        return this.signature != null;
    }
    
    public boolean isSignatureValid(final PublicKey publicKey) {
        final Signature instance = Signature.getInstance("SHA1withRSA");
        instance.initVerify(publicKey);
        instance.update(this.value.getBytes());
        return instance.verify(Base64.decodeBase64(this.signature));
    }
}
