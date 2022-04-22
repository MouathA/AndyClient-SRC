package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.commons.codec.binary.*;

@NotThreadSafe
final class NTLMEngineImpl implements NTLMEngine
{
    protected static final int FLAG_REQUEST_UNICODE_ENCODING = 1;
    protected static final int FLAG_REQUEST_TARGET = 4;
    protected static final int FLAG_REQUEST_SIGN = 16;
    protected static final int FLAG_REQUEST_SEAL = 32;
    protected static final int FLAG_REQUEST_LAN_MANAGER_KEY = 128;
    protected static final int FLAG_REQUEST_NTLMv1 = 512;
    protected static final int FLAG_DOMAIN_PRESENT = 4096;
    protected static final int FLAG_WORKSTATION_PRESENT = 8192;
    protected static final int FLAG_REQUEST_ALWAYS_SIGN = 32768;
    protected static final int FLAG_REQUEST_NTLM2_SESSION = 524288;
    protected static final int FLAG_REQUEST_VERSION = 33554432;
    protected static final int FLAG_TARGETINFO_PRESENT = 8388608;
    protected static final int FLAG_REQUEST_128BIT_KEY_EXCH = 536870912;
    protected static final int FLAG_REQUEST_EXPLICIT_KEY_EXCH = 1073741824;
    protected static final int FLAG_REQUEST_56BIT_ENCRYPTION = Integer.MIN_VALUE;
    private static final SecureRandom RND_GEN;
    static final String DEFAULT_CHARSET = "ASCII";
    private String credentialCharset;
    private static final byte[] SIGNATURE;
    
    NTLMEngineImpl() {
        this.credentialCharset = "ASCII";
    }
    
    final String getResponseFor(final String s, final String s2, final String s3, final String s4, final String s5) throws NTLMEngineException {
        String s6;
        if (s == null || s.trim().equals("")) {
            s6 = this.getType1Message(s4, s5);
        }
        else {
            final Type2Message type2Message = new Type2Message(s);
            s6 = this.getType3Message(s2, s3, s4, s5, type2Message.getChallenge(), type2Message.getFlags(), type2Message.getTarget(), type2Message.getTargetInfo());
        }
        return s6;
    }
    
    String getType1Message(final String s, final String s2) throws NTLMEngineException {
        return new Type1Message(s2, s).getResponse();
    }
    
    String getType3Message(final String s, final String s2, final String s3, final String s4, final byte[] array, final int n, final String s5, final byte[] array2) throws NTLMEngineException {
        return new Type3Message(s4, s3, s, s2, array, n, s5, array2).getResponse();
    }
    
    String getCredentialCharset() {
        return this.credentialCharset;
    }
    
    void setCredentialCharset(final String credentialCharset) {
        this.credentialCharset = credentialCharset;
    }
    
    private static String stripDotSuffix(final String s) {
        if (s == null) {
            return null;
        }
        final int index = s.indexOf(".");
        if (index != -1) {
            return s.substring(0, index);
        }
        return s;
    }
    
    private static String convertHost(final String s) {
        return stripDotSuffix(s);
    }
    
    private static String convertDomain(final String s) {
        return stripDotSuffix(s);
    }
    
    private static int readULong(final byte[] array, final int n) throws NTLMEngineException {
        if (array.length < n + 4) {
            throw new NTLMEngineException("NTLM authentication - buffer too small for DWORD");
        }
        return (array[n] & 0xFF) | (array[n + 1] & 0xFF) << 8 | (array[n + 2] & 0xFF) << 16 | (array[n + 3] & 0xFF) << 24;
    }
    
    private static int readUShort(final byte[] array, final int n) throws NTLMEngineException {
        if (array.length < n + 2) {
            throw new NTLMEngineException("NTLM authentication - buffer too small for WORD");
        }
        return (array[n] & 0xFF) | (array[n + 1] & 0xFF) << 8;
    }
    
    private static byte[] readSecurityBuffer(final byte[] array, final int n) throws NTLMEngineException {
        final int uShort = readUShort(array, n);
        final int uLong = readULong(array, n + 4);
        if (array.length < uLong + uShort) {
            throw new NTLMEngineException("NTLM authentication - buffer too small for data item");
        }
        final byte[] array2 = new byte[uShort];
        System.arraycopy(array, uLong, array2, 0, uShort);
        return array2;
    }
    
    private static byte[] makeRandomChallenge() throws NTLMEngineException {
        if (NTLMEngineImpl.RND_GEN == null) {
            throw new NTLMEngineException("Random generator not available");
        }
        final byte[] array = new byte[8];
        // monitorenter(rnd_GEN = NTLMEngineImpl.RND_GEN)
        NTLMEngineImpl.RND_GEN.nextBytes(array);
        // monitorexit(rnd_GEN)
        return array;
    }
    
    private static byte[] makeSecondaryKey() throws NTLMEngineException {
        if (NTLMEngineImpl.RND_GEN == null) {
            throw new NTLMEngineException("Random generator not available");
        }
        final byte[] array = new byte[16];
        // monitorenter(rnd_GEN = NTLMEngineImpl.RND_GEN)
        NTLMEngineImpl.RND_GEN.nextBytes(array);
        // monitorexit(rnd_GEN)
        return array;
    }
    
    static byte[] hmacMD5(final byte[] array, final byte[] array2) throws NTLMEngineException {
        final HMACMD5 hmacmd5 = new HMACMD5(array2);
        hmacmd5.update(array);
        return hmacmd5.getOutput();
    }
    
    static byte[] RC4(final byte[] array, final byte[] array2) throws NTLMEngineException {
        final Cipher instance = Cipher.getInstance("RC4");
        instance.init(1, new SecretKeySpec(array2, "RC4"));
        return instance.doFinal(array);
    }
    
    static byte[] ntlm2SessionResponse(final byte[] array, final byte[] array2, final byte[] array3) throws NTLMEngineException {
        final MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(array2);
        instance.update(array3);
        final byte[] digest = instance.digest();
        final byte[] array4 = new byte[8];
        System.arraycopy(digest, 0, array4, 0, 8);
        return lmResponse(array, array4);
    }
    
    private static byte[] lmHash(final String s) throws NTLMEngineException {
        final byte[] bytes = s.toUpperCase(Locale.US).getBytes("US-ASCII");
        final int min = Math.min(bytes.length, 14);
        final byte[] array = new byte[14];
        System.arraycopy(bytes, 0, array, 0, min);
        final Key desKey = createDESKey(array, 0);
        final Key desKey2 = createDESKey(array, 7);
        final byte[] bytes2 = "KGS!@#$%".getBytes("US-ASCII");
        final Cipher instance = Cipher.getInstance("DES/ECB/NoPadding");
        instance.init(1, desKey);
        final byte[] doFinal = instance.doFinal(bytes2);
        instance.init(1, desKey2);
        final byte[] doFinal2 = instance.doFinal(bytes2);
        final byte[] array2 = new byte[16];
        System.arraycopy(doFinal, 0, array2, 0, 8);
        System.arraycopy(doFinal2, 0, array2, 8, 8);
        return array2;
    }
    
    private static byte[] ntlmHash(final String s) throws NTLMEngineException {
        final byte[] bytes = s.getBytes("UnicodeLittleUnmarked");
        final MD4 md4 = new MD4();
        md4.update(bytes);
        return md4.getOutput();
    }
    
    private static byte[] lmv2Hash(final String s, final String s2, final byte[] array) throws NTLMEngineException {
        final HMACMD5 hmacmd5 = new HMACMD5(array);
        hmacmd5.update(s2.toUpperCase(Locale.US).getBytes("UnicodeLittleUnmarked"));
        if (s != null) {
            hmacmd5.update(s.toUpperCase(Locale.US).getBytes("UnicodeLittleUnmarked"));
        }
        return hmacmd5.getOutput();
    }
    
    private static byte[] ntlmv2Hash(final String s, final String s2, final byte[] array) throws NTLMEngineException {
        final HMACMD5 hmacmd5 = new HMACMD5(array);
        hmacmd5.update(s2.toUpperCase(Locale.US).getBytes("UnicodeLittleUnmarked"));
        if (s != null) {
            hmacmd5.update(s.getBytes("UnicodeLittleUnmarked"));
        }
        return hmacmd5.getOutput();
    }
    
    private static byte[] lmResponse(final byte[] array, final byte[] array2) throws NTLMEngineException {
        final byte[] array3 = new byte[21];
        System.arraycopy(array, 0, array3, 0, 16);
        final Key desKey = createDESKey(array3, 0);
        final Key desKey2 = createDESKey(array3, 7);
        final Key desKey3 = createDESKey(array3, 14);
        final Cipher instance = Cipher.getInstance("DES/ECB/NoPadding");
        instance.init(1, desKey);
        final byte[] doFinal = instance.doFinal(array2);
        instance.init(1, desKey2);
        final byte[] doFinal2 = instance.doFinal(array2);
        instance.init(1, desKey3);
        final byte[] doFinal3 = instance.doFinal(array2);
        final byte[] array4 = new byte[24];
        System.arraycopy(doFinal, 0, array4, 0, 8);
        System.arraycopy(doFinal2, 0, array4, 8, 8);
        System.arraycopy(doFinal3, 0, array4, 16, 8);
        return array4;
    }
    
    private static byte[] lmv2Response(final byte[] array, final byte[] array2, final byte[] array3) throws NTLMEngineException {
        final HMACMD5 hmacmd5 = new HMACMD5(array);
        hmacmd5.update(array2);
        hmacmd5.update(array3);
        final byte[] output = hmacmd5.getOutput();
        final byte[] array4 = new byte[output.length + array3.length];
        System.arraycopy(output, 0, array4, 0, output.length);
        System.arraycopy(array3, 0, array4, output.length, array3.length);
        return array4;
    }
    
    private static byte[] createBlob(final byte[] array, final byte[] array2, final byte[] array3) {
        final byte[] array4 = { 1, 1, 0, 0 };
        final byte[] array5 = { 0, 0, 0, 0 };
        final byte[] array6 = { 0, 0, 0, 0 };
        final byte[] array7 = { 0, 0, 0, 0 };
        final byte[] array8 = new byte[array4.length + array5.length + array3.length + 8 + array6.length + array2.length + array7.length];
        System.arraycopy(array4, 0, array8, 0, array4.length);
        final int n = 0 + array4.length;
        System.arraycopy(array5, 0, array8, 0, array5.length);
        final int n2 = 0 + array5.length;
        System.arraycopy(array3, 0, array8, 0, array3.length);
        int n3 = 0 + array3.length;
        System.arraycopy(array, 0, array8, 0, 8);
        n3 += 8;
        System.arraycopy(array6, 0, array8, 0, array6.length);
        final int n4 = 0 + array6.length;
        System.arraycopy(array2, 0, array8, 0, array2.length);
        final int n5 = 0 + array2.length;
        System.arraycopy(array7, 0, array8, 0, array7.length);
        final int n6 = 0 + array7.length;
        return array8;
    }
    
    private static Key createDESKey(final byte[] array, final int n) {
        final byte[] array2 = new byte[7];
        System.arraycopy(array, n, array2, 0, 7);
        final byte[] array3 = { array2[0], (byte)(array2[0] << 7 | (array2[1] & 0xFF) >>> 1), (byte)(array2[1] << 6 | (array2[2] & 0xFF) >>> 2), (byte)(array2[2] << 5 | (array2[3] & 0xFF) >>> 3), (byte)(array2[3] << 4 | (array2[4] & 0xFF) >>> 4), (byte)(array2[4] << 3 | (array2[5] & 0xFF) >>> 5), (byte)(array2[5] << 2 | (array2[6] & 0xFF) >>> 6), (byte)(array2[6] << 1) };
        oddParity(array3);
        return new SecretKeySpec(array3, "DES");
    }
    
    private static void oddParity(final byte[] array) {
        while (0 < array.length) {
            final byte b = array[0];
            if (((b >>> 7 ^ b >>> 6 ^ b >>> 5 ^ b >>> 4 ^ b >>> 3 ^ b >>> 2 ^ b >>> 1) & 0x1) == 0x0) {
                final int n = 0;
                array[n] |= 0x1;
            }
            else {
                final int n2 = 0;
                array[n2] &= 0xFFFFFFFE;
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    static void writeULong(final byte[] array, final int n, final int n2) {
        array[n2] = (byte)(n & 0xFF);
        array[n2 + 1] = (byte)(n >> 8 & 0xFF);
        array[n2 + 2] = (byte)(n >> 16 & 0xFF);
        array[n2 + 3] = (byte)(n >> 24 & 0xFF);
    }
    
    static int F(final int n, final int n2, final int n3) {
        return (n & n2) | (~n & n3);
    }
    
    static int G(final int n, final int n2, final int n3) {
        return (n & n2) | (n & n3) | (n2 & n3);
    }
    
    static int H(final int n, final int n2, final int n3) {
        return n ^ n2 ^ n3;
    }
    
    static int rotintlft(final int n, final int n2) {
        return n << n2 | n >>> 32 - n2;
    }
    
    public String generateType1Msg(final String s, final String s2) throws NTLMEngineException {
        return this.getType1Message(s2, s);
    }
    
    public String generateType3Msg(final String s, final String s2, final String s3, final String s4, final String s5) throws NTLMEngineException {
        final Type2Message type2Message = new Type2Message(s5);
        return this.getType3Message(s, s2, s4, s3, type2Message.getChallenge(), type2Message.getFlags(), type2Message.getTarget(), type2Message.getTargetInfo());
    }
    
    static byte[] access$000() throws NTLMEngineException {
        return makeRandomChallenge();
    }
    
    static byte[] access$100() throws NTLMEngineException {
        return makeSecondaryKey();
    }
    
    static byte[] access$200(final String s) throws NTLMEngineException {
        return lmHash(s);
    }
    
    static byte[] access$300(final byte[] array, final byte[] array2) throws NTLMEngineException {
        return lmResponse(array, array2);
    }
    
    static byte[] access$400(final String s) throws NTLMEngineException {
        return ntlmHash(s);
    }
    
    static byte[] access$500(final String s, final String s2, final byte[] array) throws NTLMEngineException {
        return lmv2Hash(s, s2, array);
    }
    
    static byte[] access$600(final String s, final String s2, final byte[] array) throws NTLMEngineException {
        return ntlmv2Hash(s, s2, array);
    }
    
    static byte[] access$700(final byte[] array, final byte[] array2, final byte[] array3) {
        return createBlob(array, array2, array3);
    }
    
    static byte[] access$800(final byte[] array, final byte[] array2, final byte[] array3) throws NTLMEngineException {
        return lmv2Response(array, array2, array3);
    }
    
    static Key access$900(final byte[] array, final int n) {
        return createDESKey(array, n);
    }
    
    static byte[] access$1000() {
        return NTLMEngineImpl.SIGNATURE;
    }
    
    static int access$1100(final byte[] array, final int n) throws NTLMEngineException {
        return readUShort(array, n);
    }
    
    static int access$1200(final byte[] array, final int n) throws NTLMEngineException {
        return readULong(array, n);
    }
    
    static byte[] access$1300(final byte[] array, final int n) throws NTLMEngineException {
        return readSecurityBuffer(array, n);
    }
    
    static String access$1400(final String s) {
        return convertHost(s);
    }
    
    static String access$1500(final String s) {
        return convertDomain(s);
    }
    
    static {
        RND_GEN = SecureRandom.getInstance("SHA1PRNG");
        final byte[] bytes = EncodingUtils.getBytes("NTLMSSP", "ASCII");
        System.arraycopy(bytes, 0, SIGNATURE = new byte[bytes.length + 1], 0, bytes.length);
        NTLMEngineImpl.SIGNATURE[bytes.length] = 0;
    }
    
    static class HMACMD5
    {
        protected byte[] ipad;
        protected byte[] opad;
        protected MessageDigest md5;
        
        HMACMD5(final byte[] array) throws NTLMEngineException {
            byte[] digest = array;
            this.md5 = MessageDigest.getInstance("MD5");
            this.ipad = new byte[64];
            this.opad = new byte[64];
            int n = digest.length;
            if (n > 64) {
                this.md5.update(digest);
                digest = this.md5.digest();
                n = digest.length;
            }
            int n2 = 0;
            while (0 < n) {
                this.ipad[0] = (byte)(digest[0] ^ 0x36);
                this.opad[0] = (byte)(digest[0] ^ 0x5C);
                ++n2;
            }
            while (true) {
                this.ipad[0] = 54;
                this.opad[0] = 92;
                ++n2;
            }
        }
        
        byte[] getOutput() {
            final byte[] digest = this.md5.digest();
            this.md5.update(this.opad);
            return this.md5.digest(digest);
        }
        
        void update(final byte[] array) {
            this.md5.update(array);
        }
        
        void update(final byte[] array, final int n, final int n2) {
            this.md5.update(array, n, n2);
        }
    }
    
    static class MD4
    {
        protected int A;
        protected int B;
        protected int C;
        protected int D;
        protected long count;
        protected byte[] dataBuffer;
        
        MD4() {
            this.A = 1732584193;
            this.B = -271733879;
            this.C = -1732584194;
            this.D = 271733878;
            this.count = 0L;
            this.dataBuffer = new byte[64];
        }
        
        void update(final byte[] array) {
            final int n = (int)(this.count & 0x3FL);
            while (array.length - 0 + 0 >= this.dataBuffer.length) {
                final int n2 = this.dataBuffer.length - 0;
                System.arraycopy(array, 0, this.dataBuffer, 0, n2);
                this.count += n2;
                this.processBuffer();
            }
            if (0 < array.length) {
                final int n3 = array.length - 0;
                System.arraycopy(array, 0, this.dataBuffer, 0, n3);
                this.count += n3;
            }
        }
        
        byte[] getOutput() {
            final int n = (int)(this.count & 0x3FL);
            final int n2 = (n < 56) ? (56 - n) : (120 - n);
            final byte[] array = new byte[n2 + 8];
            array[0] = -128;
            while (0 < 8) {
                array[n2 + 0] = (byte)(this.count * 8L >>> 0);
                int n3 = 0;
                ++n3;
            }
            this.update(array);
            final byte[] array2 = new byte[16];
            NTLMEngineImpl.writeULong(array2, this.A, 0);
            NTLMEngineImpl.writeULong(array2, this.B, 4);
            NTLMEngineImpl.writeULong(array2, this.C, 8);
            NTLMEngineImpl.writeULong(array2, this.D, 12);
            return array2;
        }
        
        protected void processBuffer() {
            final int[] array = new int[16];
            while (0 < 16) {
                array[0] = (this.dataBuffer[0] & 0xFF) + ((this.dataBuffer[1] & 0xFF) << 8) + ((this.dataBuffer[2] & 0xFF) << 16) + ((this.dataBuffer[3] & 0xFF) << 24);
                int a = 0;
                ++a;
            }
            int a = this.A;
            final int b = this.B;
            final int c = this.C;
            final int d = this.D;
            this.round1(array);
            this.round2(array);
            this.round3(array);
            this.A += 0;
            this.B += b;
            this.C += c;
            this.D += d;
        }
        
        protected void round1(final int[] array) {
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + array[0], 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + array[1], 7);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + array[2], 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + array[3], 19);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + array[4], 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + array[5], 7);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + array[6], 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + array[7], 19);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + array[8], 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + array[9], 7);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + array[10], 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + array[11], 19);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + array[12], 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + array[13], 7);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + array[14], 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + array[15], 19);
        }
        
        protected void round2(final int[] array) {
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + array[0] + 1518500249, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + array[4] + 1518500249, 5);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + array[8] + 1518500249, 9);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + array[12] + 1518500249, 13);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + array[1] + 1518500249, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + array[5] + 1518500249, 5);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + array[9] + 1518500249, 9);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + array[13] + 1518500249, 13);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + array[2] + 1518500249, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + array[6] + 1518500249, 5);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + array[10] + 1518500249, 9);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + array[14] + 1518500249, 13);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + array[3] + 1518500249, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + array[7] + 1518500249, 5);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + array[11] + 1518500249, 9);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + array[15] + 1518500249, 13);
        }
        
        protected void round3(final int[] array) {
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + array[0] + 1859775393, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + array[8] + 1859775393, 9);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + array[4] + 1859775393, 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + array[12] + 1859775393, 15);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + array[2] + 1859775393, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + array[10] + 1859775393, 9);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + array[6] + 1859775393, 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + array[14] + 1859775393, 15);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + array[1] + 1859775393, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + array[9] + 1859775393, 9);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + array[5] + 1859775393, 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + array[13] + 1859775393, 15);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + array[3] + 1859775393, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + array[11] + 1859775393, 9);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + array[7] + 1859775393, 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + array[15] + 1859775393, 15);
        }
    }
    
    static class Type3Message extends NTLMMessage
    {
        protected int type2Flags;
        protected byte[] domainBytes;
        protected byte[] hostBytes;
        protected byte[] userBytes;
        protected byte[] lmResp;
        protected byte[] ntResp;
        protected byte[] sessionKey;
        
        Type3Message(final String s, final String s2, final String s3, final String s4, final byte[] array, final int type2Flags, final String s5, final byte[] array2) throws NTLMEngineException {
            this.type2Flags = type2Flags;
            final String access$1400 = NTLMEngineImpl.access$1400(s2);
            final String access$1401 = NTLMEngineImpl.access$1500(s);
            final CipherGen cipherGen = new CipherGen(access$1401, s3, s4, array, s5, array2);
            byte[] sessionKey;
            if ((type2Flags & 0x800000) != 0x0 && array2 != null && s5 != null) {
                this.ntResp = cipherGen.getNTLMv2Response();
                this.lmResp = cipherGen.getLMv2Response();
                if ((type2Flags & 0x80) != 0x0) {
                    sessionKey = cipherGen.getLanManagerSessionKey();
                }
                else {
                    sessionKey = cipherGen.getNTLMv2UserSessionKey();
                }
            }
            else if ((type2Flags & 0x80000) != 0x0) {
                this.ntResp = cipherGen.getNTLM2SessionResponse();
                this.lmResp = cipherGen.getLM2SessionResponse();
                if ((type2Flags & 0x80) != 0x0) {
                    sessionKey = cipherGen.getLanManagerSessionKey();
                }
                else {
                    sessionKey = cipherGen.getNTLM2SessionResponseUserSessionKey();
                }
            }
            else {
                this.ntResp = cipherGen.getNTLMResponse();
                this.lmResp = cipherGen.getLMResponse();
                if ((type2Flags & 0x80) != 0x0) {
                    sessionKey = cipherGen.getLanManagerSessionKey();
                }
                else {
                    sessionKey = cipherGen.getNTLMUserSessionKey();
                }
            }
            if ((type2Flags & 0x10) != 0x0) {
                if ((type2Flags & 0x40000000) != 0x0) {
                    this.sessionKey = NTLMEngineImpl.RC4(cipherGen.getSecondaryKey(), sessionKey);
                }
                else {
                    this.sessionKey = sessionKey;
                }
            }
            else {
                this.sessionKey = null;
            }
            this.hostBytes = (byte[])((access$1400 != null) ? access$1400.getBytes("UnicodeLittleUnmarked") : null);
            this.domainBytes = (byte[])((access$1401 != null) ? access$1401.toUpperCase(Locale.US).getBytes("UnicodeLittleUnmarked") : null);
            this.userBytes = s3.getBytes("UnicodeLittleUnmarked");
        }
        
        @Override
        String getResponse() {
            final int length = this.ntResp.length;
            final int length2 = this.lmResp.length;
            final int n = (this.domainBytes != null) ? this.domainBytes.length : 0;
            final int n2 = (this.hostBytes != null) ? this.hostBytes.length : 0;
            final int length3 = this.userBytes.length;
            if (this.sessionKey != null) {
                final int length4 = this.sessionKey.length;
            }
            final int n3 = 72 + length2;
            final int n4 = n3 + length;
            final int n5 = n4 + n;
            final int n6 = n5 + length3;
            final int n7 = n6 + n2;
            this.prepareResponse(n7 + 0, 3);
            this.addUShort(length2);
            this.addUShort(length2);
            this.addULong(72);
            this.addUShort(length);
            this.addUShort(length);
            this.addULong(n3);
            this.addUShort(n);
            this.addUShort(n);
            this.addULong(n4);
            this.addUShort(length3);
            this.addUShort(length3);
            this.addULong(n5);
            this.addUShort(n2);
            this.addUShort(n2);
            this.addULong(n6);
            this.addUShort(0);
            this.addUShort(0);
            this.addULong(n7);
            this.addULong((this.type2Flags & 0x80) | (this.type2Flags & 0x200) | (this.type2Flags & 0x80000) | 0x2000000 | (this.type2Flags & 0x8000) | (this.type2Flags & 0x20) | (this.type2Flags & 0x10) | (this.type2Flags & 0x20000000) | (this.type2Flags & Integer.MIN_VALUE) | (this.type2Flags & 0x40000000) | (this.type2Flags & 0x800000) | (this.type2Flags & 0x1) | (this.type2Flags & 0x4));
            this.addUShort(261);
            this.addULong(2600);
            this.addUShort(3840);
            this.addBytes(this.lmResp);
            this.addBytes(this.ntResp);
            this.addBytes(this.domainBytes);
            this.addBytes(this.userBytes);
            this.addBytes(this.hostBytes);
            if (this.sessionKey != null) {
                this.addBytes(this.sessionKey);
            }
            return super.getResponse();
        }
    }
    
    protected static class CipherGen
    {
        protected final String domain;
        protected final String user;
        protected final String password;
        protected final byte[] challenge;
        protected final String target;
        protected final byte[] targetInformation;
        protected byte[] clientChallenge;
        protected byte[] clientChallenge2;
        protected byte[] secondaryKey;
        protected byte[] timestamp;
        protected byte[] lmHash;
        protected byte[] lmResponse;
        protected byte[] ntlmHash;
        protected byte[] ntlmResponse;
        protected byte[] ntlmv2Hash;
        protected byte[] lmv2Hash;
        protected byte[] lmv2Response;
        protected byte[] ntlmv2Blob;
        protected byte[] ntlmv2Response;
        protected byte[] ntlm2SessionResponse;
        protected byte[] lm2SessionResponse;
        protected byte[] lmUserSessionKey;
        protected byte[] ntlmUserSessionKey;
        protected byte[] ntlmv2UserSessionKey;
        protected byte[] ntlm2SessionResponseUserSessionKey;
        protected byte[] lanManagerSessionKey;
        
        public CipherGen(final String domain, final String user, final String password, final byte[] challenge, final String target, final byte[] targetInformation, final byte[] clientChallenge, final byte[] clientChallenge2, final byte[] secondaryKey, final byte[] timestamp) {
            this.lmHash = null;
            this.lmResponse = null;
            this.ntlmHash = null;
            this.ntlmResponse = null;
            this.ntlmv2Hash = null;
            this.lmv2Hash = null;
            this.lmv2Response = null;
            this.ntlmv2Blob = null;
            this.ntlmv2Response = null;
            this.ntlm2SessionResponse = null;
            this.lm2SessionResponse = null;
            this.lmUserSessionKey = null;
            this.ntlmUserSessionKey = null;
            this.ntlmv2UserSessionKey = null;
            this.ntlm2SessionResponseUserSessionKey = null;
            this.lanManagerSessionKey = null;
            this.domain = domain;
            this.target = target;
            this.user = user;
            this.password = password;
            this.challenge = challenge;
            this.targetInformation = targetInformation;
            this.clientChallenge = clientChallenge;
            this.clientChallenge2 = clientChallenge2;
            this.secondaryKey = secondaryKey;
            this.timestamp = timestamp;
        }
        
        public CipherGen(final String s, final String s2, final String s3, final byte[] array, final String s4, final byte[] array2) {
            this(s, s2, s3, array, s4, array2, null, null, null, null);
        }
        
        public byte[] getClientChallenge() throws NTLMEngineException {
            if (this.clientChallenge == null) {
                this.clientChallenge = NTLMEngineImpl.access$000();
            }
            return this.clientChallenge;
        }
        
        public byte[] getClientChallenge2() throws NTLMEngineException {
            if (this.clientChallenge2 == null) {
                this.clientChallenge2 = NTLMEngineImpl.access$000();
            }
            return this.clientChallenge2;
        }
        
        public byte[] getSecondaryKey() throws NTLMEngineException {
            if (this.secondaryKey == null) {
                this.secondaryKey = NTLMEngineImpl.access$100();
            }
            return this.secondaryKey;
        }
        
        public byte[] getLMHash() throws NTLMEngineException {
            if (this.lmHash == null) {
                this.lmHash = NTLMEngineImpl.access$200(this.password);
            }
            return this.lmHash;
        }
        
        public byte[] getLMResponse() throws NTLMEngineException {
            if (this.lmResponse == null) {
                this.lmResponse = NTLMEngineImpl.access$300(this.getLMHash(), this.challenge);
            }
            return this.lmResponse;
        }
        
        public byte[] getNTLMHash() throws NTLMEngineException {
            if (this.ntlmHash == null) {
                this.ntlmHash = NTLMEngineImpl.access$400(this.password);
            }
            return this.ntlmHash;
        }
        
        public byte[] getNTLMResponse() throws NTLMEngineException {
            if (this.ntlmResponse == null) {
                this.ntlmResponse = NTLMEngineImpl.access$300(this.getNTLMHash(), this.challenge);
            }
            return this.ntlmResponse;
        }
        
        public byte[] getLMv2Hash() throws NTLMEngineException {
            if (this.lmv2Hash == null) {
                this.lmv2Hash = NTLMEngineImpl.access$500(this.domain, this.user, this.getNTLMHash());
            }
            return this.lmv2Hash;
        }
        
        public byte[] getNTLMv2Hash() throws NTLMEngineException {
            if (this.ntlmv2Hash == null) {
                this.ntlmv2Hash = NTLMEngineImpl.access$600(this.domain, this.user, this.getNTLMHash());
            }
            return this.ntlmv2Hash;
        }
        
        public byte[] getTimestamp() {
            if (this.timestamp == null) {
                long n = (System.currentTimeMillis() + 11644473600000L) * 10000L;
                this.timestamp = new byte[8];
                while (0 < 8) {
                    this.timestamp[0] = (byte)n;
                    n >>>= 8;
                    int n2 = 0;
                    ++n2;
                }
            }
            return this.timestamp;
        }
        
        public byte[] getNTLMv2Blob() throws NTLMEngineException {
            if (this.ntlmv2Blob == null) {
                this.ntlmv2Blob = NTLMEngineImpl.access$700(this.getClientChallenge2(), this.targetInformation, this.getTimestamp());
            }
            return this.ntlmv2Blob;
        }
        
        public byte[] getNTLMv2Response() throws NTLMEngineException {
            if (this.ntlmv2Response == null) {
                this.ntlmv2Response = NTLMEngineImpl.access$800(this.getNTLMv2Hash(), this.challenge, this.getNTLMv2Blob());
            }
            return this.ntlmv2Response;
        }
        
        public byte[] getLMv2Response() throws NTLMEngineException {
            if (this.lmv2Response == null) {
                this.lmv2Response = NTLMEngineImpl.access$800(this.getLMv2Hash(), this.challenge, this.getClientChallenge());
            }
            return this.lmv2Response;
        }
        
        public byte[] getNTLM2SessionResponse() throws NTLMEngineException {
            if (this.ntlm2SessionResponse == null) {
                this.ntlm2SessionResponse = NTLMEngineImpl.ntlm2SessionResponse(this.getNTLMHash(), this.challenge, this.getClientChallenge());
            }
            return this.ntlm2SessionResponse;
        }
        
        public byte[] getLM2SessionResponse() throws NTLMEngineException {
            if (this.lm2SessionResponse == null) {
                final byte[] clientChallenge = this.getClientChallenge();
                System.arraycopy(clientChallenge, 0, this.lm2SessionResponse = new byte[24], 0, clientChallenge.length);
                Arrays.fill(this.lm2SessionResponse, clientChallenge.length, this.lm2SessionResponse.length, (byte)0);
            }
            return this.lm2SessionResponse;
        }
        
        public byte[] getLMUserSessionKey() throws NTLMEngineException {
            if (this.lmUserSessionKey == null) {
                System.arraycopy(this.getLMHash(), 0, this.lmUserSessionKey = new byte[16], 0, 8);
                Arrays.fill(this.lmUserSessionKey, 8, 16, (byte)0);
            }
            return this.lmUserSessionKey;
        }
        
        public byte[] getNTLMUserSessionKey() throws NTLMEngineException {
            if (this.ntlmUserSessionKey == null) {
                final byte[] ntlmHash = this.getNTLMHash();
                final MD4 md4 = new MD4();
                md4.update(ntlmHash);
                this.ntlmUserSessionKey = md4.getOutput();
            }
            return this.ntlmUserSessionKey;
        }
        
        public byte[] getNTLMv2UserSessionKey() throws NTLMEngineException {
            if (this.ntlmv2UserSessionKey == null) {
                final byte[] ntlMv2Hash = this.getNTLMv2Hash();
                final byte[] array = new byte[16];
                System.arraycopy(this.getNTLMv2Response(), 0, array, 0, 16);
                this.ntlmv2UserSessionKey = NTLMEngineImpl.hmacMD5(array, ntlMv2Hash);
            }
            return this.ntlmv2UserSessionKey;
        }
        
        public byte[] getNTLM2SessionResponseUserSessionKey() throws NTLMEngineException {
            if (this.ntlm2SessionResponseUserSessionKey == null) {
                final byte[] ntlmUserSessionKey = this.getNTLMUserSessionKey();
                final byte[] lm2SessionResponse = this.getLM2SessionResponse();
                final byte[] array = new byte[this.challenge.length + lm2SessionResponse.length];
                System.arraycopy(this.challenge, 0, array, 0, this.challenge.length);
                System.arraycopy(lm2SessionResponse, 0, array, this.challenge.length, lm2SessionResponse.length);
                this.ntlm2SessionResponseUserSessionKey = NTLMEngineImpl.hmacMD5(array, ntlmUserSessionKey);
            }
            return this.ntlm2SessionResponseUserSessionKey;
        }
        
        public byte[] getLanManagerSessionKey() throws NTLMEngineException {
            if (this.lanManagerSessionKey == null) {
                final byte[] lmHash = this.getLMHash();
                final byte[] lmResponse = this.getLMResponse();
                final byte[] array = new byte[14];
                System.arraycopy(lmHash, 0, array, 0, 8);
                Arrays.fill(array, 8, array.length, (byte)(-67));
                final Key access$900 = NTLMEngineImpl.access$900(array, 0);
                final Key access$901 = NTLMEngineImpl.access$900(array, 7);
                final byte[] array2 = new byte[8];
                System.arraycopy(lmResponse, 0, array2, 0, array2.length);
                final Cipher instance = Cipher.getInstance("DES/ECB/NoPadding");
                instance.init(1, access$900);
                final byte[] doFinal = instance.doFinal(array2);
                final Cipher instance2 = Cipher.getInstance("DES/ECB/NoPadding");
                instance2.init(1, access$901);
                final byte[] doFinal2 = instance2.doFinal(array2);
                System.arraycopy(doFinal, 0, this.lanManagerSessionKey = new byte[16], 0, doFinal.length);
                System.arraycopy(doFinal2, 0, this.lanManagerSessionKey, doFinal.length, doFinal2.length);
            }
            return this.lanManagerSessionKey;
        }
    }
    
    static class NTLMMessage
    {
        private byte[] messageContents;
        private int currentOutputPosition;
        
        NTLMMessage() {
            this.messageContents = null;
            this.currentOutputPosition = 0;
        }
        
        NTLMMessage(final String s, final int n) throws NTLMEngineException {
            this.messageContents = null;
            this.currentOutputPosition = 0;
            this.messageContents = Base64.decodeBase64(EncodingUtils.getBytes(s, "ASCII"));
            if (this.messageContents.length < NTLMEngineImpl.access$1000().length) {
                throw new NTLMEngineException("NTLM message decoding error - packet too short");
            }
            while (0 < NTLMEngineImpl.access$1000().length) {
                if (this.messageContents[0] != NTLMEngineImpl.access$1000()[0]) {
                    throw new NTLMEngineException("NTLM message expected - instead got unrecognized bytes");
                }
                int n2 = 0;
                ++n2;
            }
            final int uLong = this.readULong(NTLMEngineImpl.access$1000().length);
            if (uLong != n) {
                throw new NTLMEngineException("NTLM type " + Integer.toString(n) + " message expected - instead got type " + Integer.toString(uLong));
            }
            this.currentOutputPosition = this.messageContents.length;
        }
        
        protected int getPreambleLength() {
            return NTLMEngineImpl.access$1000().length + 4;
        }
        
        protected int getMessageLength() {
            return this.currentOutputPosition;
        }
        
        protected byte readByte(final int n) throws NTLMEngineException {
            if (this.messageContents.length < n + 1) {
                throw new NTLMEngineException("NTLM: Message too short");
            }
            return this.messageContents[n];
        }
        
        protected void readBytes(final byte[] array, final int n) throws NTLMEngineException {
            if (this.messageContents.length < n + array.length) {
                throw new NTLMEngineException("NTLM: Message too short");
            }
            System.arraycopy(this.messageContents, n, array, 0, array.length);
        }
        
        protected int readUShort(final int n) throws NTLMEngineException {
            return NTLMEngineImpl.access$1100(this.messageContents, n);
        }
        
        protected int readULong(final int n) throws NTLMEngineException {
            return NTLMEngineImpl.access$1200(this.messageContents, n);
        }
        
        protected byte[] readSecurityBuffer(final int n) throws NTLMEngineException {
            return NTLMEngineImpl.access$1300(this.messageContents, n);
        }
        
        protected void prepareResponse(final int n, final int n2) {
            this.messageContents = new byte[n];
            this.currentOutputPosition = 0;
            this.addBytes(NTLMEngineImpl.access$1000());
            this.addULong(n2);
        }
        
        protected void addByte(final byte b) {
            this.messageContents[this.currentOutputPosition] = b;
            ++this.currentOutputPosition;
        }
        
        protected void addBytes(final byte[] array) {
            if (array == null) {
                return;
            }
            while (0 < array.length) {
                this.messageContents[this.currentOutputPosition] = array[0];
                ++this.currentOutputPosition;
                int n = 0;
                ++n;
            }
        }
        
        protected void addUShort(final int n) {
            this.addByte((byte)(n & 0xFF));
            this.addByte((byte)(n >> 8 & 0xFF));
        }
        
        protected void addULong(final int n) {
            this.addByte((byte)(n & 0xFF));
            this.addByte((byte)(n >> 8 & 0xFF));
            this.addByte((byte)(n >> 16 & 0xFF));
            this.addByte((byte)(n >> 24 & 0xFF));
        }
        
        String getResponse() {
            byte[] messageContents;
            if (this.messageContents.length > this.currentOutputPosition) {
                final byte[] array = new byte[this.currentOutputPosition];
                System.arraycopy(this.messageContents, 0, array, 0, this.currentOutputPosition);
                messageContents = array;
            }
            else {
                messageContents = this.messageContents;
            }
            return EncodingUtils.getAsciiString(Base64.encodeBase64(messageContents));
        }
    }
    
    static class Type2Message extends NTLMMessage
    {
        protected byte[] challenge;
        protected String target;
        protected byte[] targetInfo;
        protected int flags;
        
        Type2Message(final String s) throws NTLMEngineException {
            super(s, 2);
            this.readBytes(this.challenge = new byte[8], 24);
            this.flags = this.readULong(20);
            if ((this.flags & 0x1) == 0x0) {
                throw new NTLMEngineException("NTLM type 2 message has flags that make no sense: " + Integer.toString(this.flags));
            }
            this.target = null;
            if (this.getMessageLength() >= 20) {
                final byte[] securityBuffer = this.readSecurityBuffer(12);
                if (securityBuffer.length != 0) {
                    this.target = new String(securityBuffer, "UnicodeLittleUnmarked");
                }
            }
            this.targetInfo = null;
            if (this.getMessageLength() >= 48) {
                final byte[] securityBuffer2 = this.readSecurityBuffer(40);
                if (securityBuffer2.length != 0) {
                    this.targetInfo = securityBuffer2;
                }
            }
        }
        
        byte[] getChallenge() {
            return this.challenge;
        }
        
        String getTarget() {
            return this.target;
        }
        
        byte[] getTargetInfo() {
            return this.targetInfo;
        }
        
        int getFlags() {
            return this.flags;
        }
    }
    
    static class Type1Message extends NTLMMessage
    {
        protected byte[] hostBytes;
        protected byte[] domainBytes;
        
        Type1Message(final String s, final String s2) throws NTLMEngineException {
            final String access$1400 = NTLMEngineImpl.access$1400(s2);
            final String access$1401 = NTLMEngineImpl.access$1500(s);
            this.hostBytes = (byte[])((access$1400 != null) ? access$1400.getBytes("ASCII") : null);
            this.domainBytes = (byte[])((access$1401 != null) ? access$1401.toUpperCase(Locale.US).getBytes("ASCII") : null);
        }
        
        @Override
        String getResponse() {
            this.prepareResponse(40, 1);
            this.addULong(-1576500735);
            this.addUShort(0);
            this.addUShort(0);
            this.addULong(40);
            this.addUShort(0);
            this.addUShort(0);
            this.addULong(40);
            this.addUShort(261);
            this.addULong(2600);
            this.addUShort(3840);
            return super.getResponse();
        }
    }
}
