package com.ibm.icu.text;

import java.io.*;
import java.util.*;

public class CharsetDetector
{
    private static final int kBufSize = 8000;
    byte[] fInputBytes;
    int fInputLen;
    short[] fByteStats;
    boolean fC1Bytes;
    String fDeclaredEncoding;
    byte[] fRawInput;
    int fRawLength;
    InputStream fInputStream;
    boolean fStripTags;
    private static ArrayList fCSRecognizers;
    private static String[] fCharsetNames;
    
    public CharsetDetector() {
        this.fInputBytes = new byte[8000];
        this.fByteStats = new short[256];
        this.fC1Bytes = false;
        this.fStripTags = false;
    }
    
    public CharsetDetector setDeclaredEncoding(final String fDeclaredEncoding) {
        this.fDeclaredEncoding = fDeclaredEncoding;
        return this;
    }
    
    public CharsetDetector setText(final byte[] fRawInput) {
        this.fRawInput = fRawInput;
        this.fRawLength = fRawInput.length;
        return this;
    }
    
    public CharsetDetector setText(final InputStream fInputStream) throws IOException {
        (this.fInputStream = fInputStream).mark(8000);
        this.fRawInput = new byte[8000];
        this.fRawLength = 0;
        while (8000 > 0) {
            final int read = this.fInputStream.read(this.fRawInput, this.fRawLength, 8000);
            if (read <= 0) {
                break;
            }
            this.fRawLength += read;
        }
        this.fInputStream.reset();
        return this;
    }
    
    public CharsetMatch detect() {
        final CharsetMatch[] detectAll = this.detectAll();
        if (detectAll == null || detectAll.length == 0) {
            return null;
        }
        return detectAll[0];
    }
    
    public CharsetMatch[] detectAll() {
        final ArrayList<CharsetMatch> list = (ArrayList<CharsetMatch>)new ArrayList<Comparable>();
        this.MungeInput();
        final Iterator<CharsetRecognizer> iterator = CharsetDetector.fCSRecognizers.iterator();
        while (iterator.hasNext()) {
            final CharsetMatch match = iterator.next().match(this);
            if (match != null) {
                list.add(match);
            }
        }
        Collections.sort((List<Comparable>)list);
        Collections.reverse(list);
        return list.toArray(new CharsetMatch[list.size()]);
    }
    
    public Reader getReader(final InputStream text, final String fDeclaredEncoding) {
        this.fDeclaredEncoding = fDeclaredEncoding;
        this.setText(text);
        final CharsetMatch detect = this.detect();
        if (detect == null) {
            return null;
        }
        return detect.getReader();
    }
    
    public String getString(final byte[] text, final String fDeclaredEncoding) {
        this.fDeclaredEncoding = fDeclaredEncoding;
        this.setText(text);
        final CharsetMatch detect = this.detect();
        if (detect == null) {
            return null;
        }
        return detect.getString(-1);
    }
    
    public static String[] getAllDetectableCharsets() {
        return CharsetDetector.fCharsetNames;
    }
    
    public boolean inputFilterEnabled() {
        return this.fStripTags;
    }
    
    public boolean enableInputFilter(final boolean fStripTags) {
        final boolean fStripTags2 = this.fStripTags;
        this.fStripTags = fStripTags;
        return fStripTags2;
    }
    
    private void MungeInput() {
        int n5 = 0;
        if (this.fStripTags) {
            while (0 < this.fRawLength && 0 < this.fInputBytes.length) {
                final byte b = this.fRawInput[0];
                if (b == 60) {
                    if (false) {
                        int n = 0;
                        ++n;
                    }
                    int n2 = 0;
                    ++n2;
                }
                if (!false) {
                    final byte[] fInputBytes = this.fInputBytes;
                    final int n3 = 0;
                    int n4 = 0;
                    ++n4;
                    fInputBytes[n3] = b;
                }
                if (b == 62) {}
                ++n5;
            }
            this.fInputLen = 0;
        }
        int fRawLength = 0;
        if (0 < 5 || 0 < 0 || (this.fInputLen < 100 && this.fRawLength > 600)) {
            fRawLength = this.fRawLength;
            if (128 > 8000) {}
            while (0 < 128) {
                this.fInputBytes[0] = this.fRawInput[0];
                ++n5;
            }
            this.fInputLen = 0;
        }
        Arrays.fill(this.fByteStats, (short)0);
        while (0 < this.fInputLen) {
            fRawLength = (this.fInputBytes[0] & 0xFF);
            final short[] fByteStats = this.fByteStats;
            final int n6 = 128;
            ++fByteStats[n6];
            ++n5;
        }
        this.fC1Bytes = false;
        while (128 <= 159) {
            if (this.fByteStats[128] != 0) {
                this.fC1Bytes = true;
                break;
            }
            ++fRawLength;
        }
    }
    
    private static ArrayList createRecognizers() {
        final ArrayList<CharsetRecog_UTF8> list = new ArrayList<CharsetRecog_UTF8>();
        list.add(new CharsetRecog_UTF8());
        list.add((CharsetRecog_UTF8)new CharsetRecog_Unicode.CharsetRecog_UTF_16_BE());
        list.add((CharsetRecog_UTF8)new CharsetRecog_Unicode.CharsetRecog_UTF_16_LE());
        list.add((CharsetRecog_UTF8)new CharsetRecog_Unicode.CharsetRecog_UTF_32_BE());
        list.add((CharsetRecog_UTF8)new CharsetRecog_Unicode.CharsetRecog_UTF_32_LE());
        list.add((CharsetRecog_UTF8)new CharsetRecog_mbcs.CharsetRecog_sjis());
        list.add((CharsetRecog_UTF8)new CharsetRecog_2022.CharsetRecog_2022JP());
        list.add((CharsetRecog_UTF8)new CharsetRecog_2022.CharsetRecog_2022CN());
        list.add((CharsetRecog_UTF8)new CharsetRecog_2022.CharsetRecog_2022KR());
        list.add((CharsetRecog_UTF8)new CharsetRecog_mbcs.CharsetRecog_gb_18030());
        list.add((CharsetRecog_UTF8)new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_jp());
        list.add((CharsetRecog_UTF8)new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_kr());
        list.add((CharsetRecog_UTF8)new CharsetRecog_mbcs.CharsetRecog_big5());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_8859_1());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_8859_2());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_8859_5_ru());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_8859_6_ar());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_8859_7_el());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_8859_8_I_he());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_8859_8_he());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_windows_1251());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_windows_1256());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_KOI8_R());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_8859_9_tr());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_IBM424_he_rtl());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_IBM424_he_ltr());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_rtl());
        list.add((CharsetRecog_UTF8)new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_ltr());
        final String[] array = new String[list.size()];
        while (0 < list.size()) {
            final String name = list.get(0).getName();
            if (!false || !name.equals(array[-1])) {
                final String[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = name;
            }
            int n3 = 0;
            ++n3;
        }
        System.arraycopy(array, 0, CharsetDetector.fCharsetNames = new String[0], 0, 0);
        return list;
    }
    
    static {
        CharsetDetector.fCSRecognizers = createRecognizers();
    }
}
