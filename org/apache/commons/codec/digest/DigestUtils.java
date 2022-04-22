package org.apache.commons.codec.digest;

import java.security.*;
import java.io.*;
import org.apache.commons.codec.binary.*;

public class DigestUtils
{
    private static final int STREAM_BUFFER_LENGTH = 1024;
    
    private static byte[] digest(final MessageDigest messageDigest, final InputStream inputStream) throws IOException {
        return updateDigest(messageDigest, inputStream).digest();
    }
    
    public static MessageDigest getDigest(final String s) {
        return MessageDigest.getInstance(s);
    }
    
    public static MessageDigest getMd2Digest() {
        return getDigest("MD2");
    }
    
    public static MessageDigest getMd5Digest() {
        return getDigest("MD5");
    }
    
    public static MessageDigest getSha1Digest() {
        return getDigest("SHA-1");
    }
    
    public static MessageDigest getSha256Digest() {
        return getDigest("SHA-256");
    }
    
    public static MessageDigest getSha384Digest() {
        return getDigest("SHA-384");
    }
    
    public static MessageDigest getSha512Digest() {
        return getDigest("SHA-512");
    }
    
    @Deprecated
    public static MessageDigest getShaDigest() {
        return getSha1Digest();
    }
    
    public static byte[] md2(final byte[] array) {
        return getMd2Digest().digest(array);
    }
    
    public static byte[] md2(final InputStream inputStream) throws IOException {
        return digest(getMd2Digest(), inputStream);
    }
    
    public static byte[] md2(final String s) {
        return md2(StringUtils.getBytesUtf8(s));
    }
    
    public static String md2Hex(final byte[] array) {
        return Hex.encodeHexString(md2(array));
    }
    
    public static String md2Hex(final InputStream inputStream) throws IOException {
        return Hex.encodeHexString(md2(inputStream));
    }
    
    public static String md2Hex(final String s) {
        return Hex.encodeHexString(md2(s));
    }
    
    public static byte[] md5(final byte[] array) {
        return getMd5Digest().digest(array);
    }
    
    public static byte[] md5(final InputStream inputStream) throws IOException {
        return digest(getMd5Digest(), inputStream);
    }
    
    public static byte[] md5(final String s) {
        return md5(StringUtils.getBytesUtf8(s));
    }
    
    public static String md5Hex(final byte[] array) {
        return Hex.encodeHexString(md5(array));
    }
    
    public static String md5Hex(final InputStream inputStream) throws IOException {
        return Hex.encodeHexString(md5(inputStream));
    }
    
    public static String md5Hex(final String s) {
        return Hex.encodeHexString(md5(s));
    }
    
    @Deprecated
    public static byte[] sha(final byte[] array) {
        return sha1(array);
    }
    
    @Deprecated
    public static byte[] sha(final InputStream inputStream) throws IOException {
        return sha1(inputStream);
    }
    
    @Deprecated
    public static byte[] sha(final String s) {
        return sha1(s);
    }
    
    public static byte[] sha1(final byte[] array) {
        return getSha1Digest().digest(array);
    }
    
    public static byte[] sha1(final InputStream inputStream) throws IOException {
        return digest(getSha1Digest(), inputStream);
    }
    
    public static byte[] sha1(final String s) {
        return sha1(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha1Hex(final byte[] array) {
        return Hex.encodeHexString(sha1(array));
    }
    
    public static String sha1Hex(final InputStream inputStream) throws IOException {
        return Hex.encodeHexString(sha1(inputStream));
    }
    
    public static String sha1Hex(final String s) {
        return Hex.encodeHexString(sha1(s));
    }
    
    public static byte[] sha256(final byte[] array) {
        return getSha256Digest().digest(array);
    }
    
    public static byte[] sha256(final InputStream inputStream) throws IOException {
        return digest(getSha256Digest(), inputStream);
    }
    
    public static byte[] sha256(final String s) {
        return sha256(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha256Hex(final byte[] array) {
        return Hex.encodeHexString(sha256(array));
    }
    
    public static String sha256Hex(final InputStream inputStream) throws IOException {
        return Hex.encodeHexString(sha256(inputStream));
    }
    
    public static String sha256Hex(final String s) {
        return Hex.encodeHexString(sha256(s));
    }
    
    public static byte[] sha384(final byte[] array) {
        return getSha384Digest().digest(array);
    }
    
    public static byte[] sha384(final InputStream inputStream) throws IOException {
        return digest(getSha384Digest(), inputStream);
    }
    
    public static byte[] sha384(final String s) {
        return sha384(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha384Hex(final byte[] array) {
        return Hex.encodeHexString(sha384(array));
    }
    
    public static String sha384Hex(final InputStream inputStream) throws IOException {
        return Hex.encodeHexString(sha384(inputStream));
    }
    
    public static String sha384Hex(final String s) {
        return Hex.encodeHexString(sha384(s));
    }
    
    public static byte[] sha512(final byte[] array) {
        return getSha512Digest().digest(array);
    }
    
    public static byte[] sha512(final InputStream inputStream) throws IOException {
        return digest(getSha512Digest(), inputStream);
    }
    
    public static byte[] sha512(final String s) {
        return sha512(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha512Hex(final byte[] array) {
        return Hex.encodeHexString(sha512(array));
    }
    
    public static String sha512Hex(final InputStream inputStream) throws IOException {
        return Hex.encodeHexString(sha512(inputStream));
    }
    
    public static String sha512Hex(final String s) {
        return Hex.encodeHexString(sha512(s));
    }
    
    @Deprecated
    public static String shaHex(final byte[] array) {
        return sha1Hex(array);
    }
    
    @Deprecated
    public static String shaHex(final InputStream inputStream) throws IOException {
        return sha1Hex(inputStream);
    }
    
    @Deprecated
    public static String shaHex(final String s) {
        return sha1Hex(s);
    }
    
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final byte[] array) {
        messageDigest.update(array);
        return messageDigest;
    }
    
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final InputStream inputStream) throws IOException {
        final byte[] array = new byte[1024];
        for (int i = inputStream.read(array, 0, 1024); i > -1; i = inputStream.read(array, 0, 1024)) {
            messageDigest.update(array, 0, i);
        }
        return messageDigest;
    }
    
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final String s) {
        messageDigest.update(StringUtils.getBytesUtf8(s));
        return messageDigest;
    }
}
