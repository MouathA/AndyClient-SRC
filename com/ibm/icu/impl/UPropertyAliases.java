package com.ibm.icu.impl;

import java.io.*;
import com.ibm.icu.util.*;

public final class UPropertyAliases
{
    private static final int IX_VALUE_MAPS_OFFSET = 0;
    private static final int IX_BYTE_TRIES_OFFSET = 1;
    private static final int IX_NAME_GROUPS_OFFSET = 2;
    private static final int IX_RESERVED3_OFFSET = 3;
    private int[] valueMaps;
    private byte[] bytesTries;
    private String nameGroups;
    private static final IsAcceptable IS_ACCEPTABLE;
    private static final byte[] DATA_FORMAT;
    public static final UPropertyAliases INSTANCE;
    
    private void load(final InputStream inputStream) throws IOException {
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ICUBinary.readHeader(bufferedInputStream, UPropertyAliases.DATA_FORMAT, UPropertyAliases.IS_ACCEPTABLE);
        final DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
        final int n = dataInputStream.readInt() / 4;
        if (n < 8) {
            throw new IOException("pnames.icu: not enough indexes");
        }
        final int[] array = new int[n];
        array[0] = n * 4;
        while (1 < n) {
            array[1] = dataInputStream.readInt();
            int n2 = 0;
            ++n2;
        }
        int n2 = array[0];
        final int n3 = array[1];
        final int n4 = (n3 - 1) / 4;
        this.valueMaps = new int[n4];
        while (0 < n4) {
            this.valueMaps[0] = dataInputStream.readInt();
            int n5 = 0;
            ++n5;
        }
        n2 = n3;
        final int n6 = array[2];
        int n5 = n6 - 1;
        dataInputStream.readFully(this.bytesTries = new byte[0]);
        n2 = n6;
        n5 = array[3] - 1;
        final StringBuilder sb = new StringBuilder(0);
        while (0 < 0) {
            sb.append((char)dataInputStream.readByte());
            int n7 = 0;
            ++n7;
        }
        this.nameGroups = sb.toString();
        inputStream.close();
    }
    
    private UPropertyAliases() throws IOException {
        this.load(ICUData.getRequiredStream("data/icudt51b/pnames.icu"));
    }
    
    private int findProperty(final int n) {
        for (int i = this.valueMaps[0]; i > 0; --i) {
            final int n2 = this.valueMaps[1];
            final int n3 = this.valueMaps[2];
            int n4 = 0;
            n4 += 2;
            if (n < n2) {
                break;
            }
            if (n < n3) {
                return 1 + (n - n2) * 2;
            }
            n4 = 1 + (n3 - n2) * 2;
        }
        return 0;
    }
    
    private int findPropertyValueNameGroup(int n, final int n2) {
        if (n == 0) {
            return 0;
        }
        ++n;
        int i = this.valueMaps[n++];
        if (i < 16) {
            while (i > 0) {
                final int n3 = this.valueMaps[n];
                final int n4 = this.valueMaps[n + 1];
                n += 2;
                if (n2 < n3) {
                    break;
                }
                if (n2 < n4) {
                    return this.valueMaps[n + n2 - n3];
                }
                n += n4 - n3;
                --i;
            }
        }
        else {
            final int n5 = n;
            final int n6 = n + i - 16;
            do {
                final int n7 = this.valueMaps[n];
                if (n2 < n7) {
                    break;
                }
                if (n2 == n7) {
                    return this.valueMaps[n6 + n - n5];
                }
            } while (++n < n6);
        }
        return 0;
    }
    
    private String getName(int n, int i) {
        final char char1 = this.nameGroups.charAt(n++);
        if (i < 0 || char1 <= i) {
            throw new IllegalIcuArgumentException("Invalid property (value) name choice");
        }
        while (i > 0) {
            while ('\0' != this.nameGroups.charAt(n++)) {}
            --i;
        }
        final int n2 = n;
        while ('\0' != this.nameGroups.charAt(n)) {
            ++n;
        }
        if (n2 == n) {
            return null;
        }
        return this.nameGroups.substring(n2, n);
    }
    
    private static int asciiToLowercase(final int n) {
        return (65 <= n && n <= 90) ? (n + 32) : n;
    }
    
    private boolean containsName(final BytesTrie bytesTrie, final CharSequence charSequence) {
        BytesTrie.Result result = BytesTrie.Result.NO_VALUE;
        while (0 < charSequence.length()) {
            final char char1 = charSequence.charAt(0);
            if (char1 != '-' && char1 != '_' && char1 != ' ') {
                if ('\t' > char1 || char1 > '\r') {
                    if (!result.hasNext()) {
                        return false;
                    }
                    result = bytesTrie.next(asciiToLowercase(char1));
                }
            }
            int n = 0;
            ++n;
        }
        return result.hasValue();
    }
    
    public String getPropertyName(final int n, final int n2) {
        final int property = this.findProperty(n);
        if (property == 0) {
            throw new IllegalArgumentException("Invalid property enum " + n + " (0x" + Integer.toHexString(n) + ")");
        }
        return this.getName(this.valueMaps[property], n2);
    }
    
    public String getPropertyValueName(final int n, final int n2, final int n3) {
        final int property = this.findProperty(n);
        if (property == 0) {
            throw new IllegalArgumentException("Invalid property enum " + n + " (0x" + Integer.toHexString(n) + ")");
        }
        final int propertyValueNameGroup = this.findPropertyValueNameGroup(this.valueMaps[property + 1], n2);
        if (propertyValueNameGroup == 0) {
            throw new IllegalArgumentException("Property " + n + " (0x" + Integer.toHexString(n) + ") does not have named values");
        }
        return this.getName(propertyValueNameGroup, n3);
    }
    
    private int getPropertyOrValueEnum(final int n, final CharSequence charSequence) {
        final BytesTrie bytesTrie = new BytesTrie(this.bytesTries, n);
        if (this.containsName(bytesTrie, charSequence)) {
            return bytesTrie.getValue();
        }
        return -1;
    }
    
    public int getPropertyEnum(final CharSequence charSequence) {
        return this.getPropertyOrValueEnum(0, charSequence);
    }
    
    public int getPropertyValueEnum(final int n, final CharSequence charSequence) {
        final int property = this.findProperty(n);
        if (property == 0) {
            throw new IllegalArgumentException("Invalid property enum " + n + " (0x" + Integer.toHexString(n) + ")");
        }
        final int n2 = this.valueMaps[property + 1];
        if (n2 == 0) {
            throw new IllegalArgumentException("Property " + n + " (0x" + Integer.toHexString(n) + ") does not have named values");
        }
        return this.getPropertyOrValueEnum(this.valueMaps[n2], charSequence);
    }
    
    public static int compare(final String s, final String s2) {
        while (true) {
            int n = 0;
            if (0 < s.length()) {
                s.charAt(0);
                switch (false) {
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 32:
                    case 45:
                    case 95: {
                        ++n;
                        continue;
                    }
                }
            }
            int n2 = 0;
        Label_0194:
            while (0 < s2.length()) {
                s2.charAt(0);
                switch (false) {
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 32:
                    case 45:
                    case 95: {
                        ++n2;
                        continue;
                    }
                    default: {
                        break Label_0194;
                    }
                }
            }
            final boolean b = 0 == s.length();
            final boolean b2 = 0 == s2.length();
            if (b) {
                if (b2) {
                    return 0;
                }
            }
            else if (b2) {}
            final int n3 = asciiToLowercase(0) - asciiToLowercase(0);
            if (n3 != 0) {
                return n3;
            }
            ++n;
            ++n2;
        }
    }
    
    static {
        IS_ACCEPTABLE = new IsAcceptable(null);
        DATA_FORMAT = new byte[] { 112, 110, 97, 109 };
        INSTANCE = new UPropertyAliases();
    }
    
    private static final class IsAcceptable implements ICUBinary.Authenticate
    {
        private IsAcceptable() {
        }
        
        public boolean isDataVersionAcceptable(final byte[] array) {
            return array[0] == 2;
        }
        
        IsAcceptable(final UPropertyAliases$1 object) {
            this();
        }
    }
}
