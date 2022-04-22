package org.apache.commons.compress.archivers.sevenz;

import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.security.spec.*;

class AES256SHA256Decoder extends CoderBase
{
    AES256SHA256Decoder() {
        super(new Class[0]);
    }
    
    @Override
    InputStream decode(final InputStream inputStream, final Coder coder, final byte[] array) throws IOException {
        return new InputStream(coder, array, inputStream) {
            private boolean isInitialized = false;
            private CipherInputStream cipherInputStream = null;
            final Coder val$coder;
            final byte[] val$passwordBytes;
            final InputStream val$in;
            final AES256SHA256Decoder this$0;
            
            private CipherInputStream init() throws IOException {
                if (this.isInitialized) {
                    return this.cipherInputStream;
                }
                final int n = 0xFF & this.val$coder.properties[0];
                final int n2 = n & 0x3F;
                final int n3 = 0xFF & this.val$coder.properties[1];
                final int n4 = (n >> 6 & 0x1) + (n3 & 0xF);
                final int n5 = (n >> 7 & 0x1) + (n3 >> 4);
                if (2 + n5 + n4 > this.val$coder.properties.length) {
                    throw new IOException("Salt size + IV size too long");
                }
                final byte[] array = new byte[n5];
                System.arraycopy(this.val$coder.properties, 2, array, 0, n5);
                final byte[] array2 = new byte[16];
                System.arraycopy(this.val$coder.properties, 2 + n5, array2, 0, n4);
                if (this.val$passwordBytes == null) {
                    throw new IOException("Cannot read encrypted files without a password");
                }
                byte[] digest;
                if (n2 == 63) {
                    digest = new byte[32];
                    System.arraycopy(array, 0, digest, 0, n5);
                    System.arraycopy(this.val$passwordBytes, 0, digest, n5, Math.min(this.val$passwordBytes.length, digest.length - n5));
                }
                else {
                    final MessageDigest instance = MessageDigest.getInstance("SHA-256");
                    final byte[] array3 = new byte[8];
                    for (long n6 = 0L; n6 < 1L << n2; ++n6) {
                        instance.update(array);
                        instance.update(this.val$passwordBytes);
                        instance.update(array3);
                        while (0 < array3.length) {
                            final byte[] array4 = array3;
                            final int n7 = 0;
                            ++array4[n7];
                            if (array3[0] != 0) {
                                break;
                            }
                            int n8 = 0;
                            ++n8;
                        }
                    }
                    digest = instance.digest();
                }
                final SecretKeySpec secretKeySpec = new SecretKeySpec(digest, "AES");
                final Cipher instance2 = Cipher.getInstance("AES/CBC/NoPadding");
                instance2.init(2, secretKeySpec, new IvParameterSpec(array2));
                this.cipherInputStream = new CipherInputStream(this.val$in, instance2);
                this.isInitialized = true;
                return this.cipherInputStream;
            }
            
            @Override
            public int read() throws IOException {
                return this.init().read();
            }
            
            @Override
            public int read(final byte[] array, final int n, final int n2) throws IOException {
                return this.init().read(array, n, n2);
            }
            
            @Override
            public void close() {
            }
        };
    }
}
