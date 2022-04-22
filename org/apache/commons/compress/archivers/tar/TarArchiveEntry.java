package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.archivers.zip.*;
import java.io.*;
import java.util.*;
import org.apache.commons.compress.utils.*;

public class TarArchiveEntry implements TarConstants, ArchiveEntry
{
    private String name;
    private int mode;
    private int userId;
    private int groupId;
    private long size;
    private long modTime;
    private boolean checkSumOK;
    private byte linkFlag;
    private String linkName;
    private String magic;
    private String version;
    private String userName;
    private String groupName;
    private int devMajor;
    private int devMinor;
    private boolean isExtended;
    private long realSize;
    private final File file;
    public static final int MAX_NAMELEN = 31;
    public static final int DEFAULT_DIR_MODE = 16877;
    public static final int DEFAULT_FILE_MODE = 33188;
    public static final int MILLIS_PER_SECOND = 1000;
    
    private TarArchiveEntry() {
        this.name = "";
        this.userId = 0;
        this.groupId = 0;
        this.size = 0L;
        this.linkName = "";
        this.magic = "ustar\u0000";
        this.version = "00";
        this.groupName = "";
        this.devMajor = 0;
        this.devMinor = 0;
        String userName = System.getProperty("user.name", "");
        if (userName.length() > 31) {
            userName = userName.substring(0, 31);
        }
        this.userName = userName;
        this.file = null;
    }
    
    public TarArchiveEntry(final String s) {
        this(s, false);
    }
    
    public TarArchiveEntry(String normalizeFileName, final boolean b) {
        this();
        normalizeFileName = normalizeFileName(normalizeFileName, b);
        final boolean endsWith = normalizeFileName.endsWith("/");
        this.name = normalizeFileName;
        this.mode = (endsWith ? 16877 : 33188);
        this.linkFlag = (byte)(endsWith ? 53 : 48);
        this.modTime = new Date().getTime() / 1000L;
        this.userName = "";
    }
    
    public TarArchiveEntry(final String s, final byte b) {
        this(s, b, false);
    }
    
    public TarArchiveEntry(final String s, final byte linkFlag, final boolean b) {
        this(s, b);
        this.linkFlag = linkFlag;
        if (linkFlag == 76) {
            this.magic = "ustar ";
            this.version = " \u0000";
        }
    }
    
    public TarArchiveEntry(final File file) {
        this(file, normalizeFileName(file.getPath(), false));
    }
    
    public TarArchiveEntry(final File file, final String s) {
        this.name = "";
        this.userId = 0;
        this.groupId = 0;
        this.size = 0L;
        this.linkName = "";
        this.magic = "ustar\u0000";
        this.version = "00";
        this.groupName = "";
        this.devMajor = 0;
        this.devMinor = 0;
        this.file = file;
        if (file.isDirectory()) {
            this.mode = 16877;
            this.linkFlag = 53;
            final int length = s.length();
            if (length == 0 || s.charAt(length - 1) != '/') {
                this.name = s + "/";
            }
            else {
                this.name = s;
            }
        }
        else {
            this.mode = 33188;
            this.linkFlag = 48;
            this.size = file.length();
            this.name = s;
        }
        this.modTime = file.lastModified() / 1000L;
        this.userName = "";
    }
    
    public TarArchiveEntry(final byte[] array) {
        this();
        this.parseTarHeader(array);
    }
    
    public TarArchiveEntry(final byte[] array, final ZipEncoding zipEncoding) throws IOException {
        this();
        this.parseTarHeader(array, zipEncoding);
    }
    
    public boolean equals(final TarArchiveEntry tarArchiveEntry) {
        return this.getName().equals(tarArchiveEntry.getName());
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && this.getClass() == o.getClass() && this.equals((TarArchiveEntry)o);
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
    
    public boolean isDescendent(final TarArchiveEntry tarArchiveEntry) {
        return tarArchiveEntry.getName().startsWith(this.getName());
    }
    
    public String getName() {
        return this.name.toString();
    }
    
    public void setName(final String s) {
        this.name = normalizeFileName(s, false);
    }
    
    public void setMode(final int mode) {
        this.mode = mode;
    }
    
    public String getLinkName() {
        return this.linkName.toString();
    }
    
    public void setLinkName(final String linkName) {
        this.linkName = linkName;
    }
    
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    public int getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(final int groupId) {
        this.groupId = groupId;
    }
    
    public String getUserName() {
        return this.userName.toString();
    }
    
    public void setUserName(final String userName) {
        this.userName = userName;
    }
    
    public String getGroupName() {
        return this.groupName.toString();
    }
    
    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }
    
    public void setIds(final int userId, final int groupId) {
        this.setUserId(userId);
        this.setGroupId(groupId);
    }
    
    public void setNames(final String userName, final String groupName) {
        this.setUserName(userName);
        this.setGroupName(groupName);
    }
    
    public void setModTime(final long n) {
        this.modTime = n / 1000L;
    }
    
    public void setModTime(final Date date) {
        this.modTime = date.getTime() / 1000L;
    }
    
    public Date getModTime() {
        return new Date(this.modTime * 1000L);
    }
    
    public Date getLastModifiedDate() {
        return this.getModTime();
    }
    
    public boolean isCheckSumOK() {
        return this.checkSumOK;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public int getMode() {
        return this.mode;
    }
    
    public long getSize() {
        return this.size;
    }
    
    public void setSize(final long size) {
        if (size < 0L) {
            throw new IllegalArgumentException("Size is out of range: " + size);
        }
        this.size = size;
    }
    
    public int getDevMajor() {
        return this.devMajor;
    }
    
    public void setDevMajor(final int devMajor) {
        if (devMajor < 0) {
            throw new IllegalArgumentException("Major device number is out of range: " + devMajor);
        }
        this.devMajor = devMajor;
    }
    
    public int getDevMinor() {
        return this.devMinor;
    }
    
    public void setDevMinor(final int devMinor) {
        if (devMinor < 0) {
            throw new IllegalArgumentException("Minor device number is out of range: " + devMinor);
        }
        this.devMinor = devMinor;
    }
    
    public boolean isExtended() {
        return this.isExtended;
    }
    
    public long getRealSize() {
        return this.realSize;
    }
    
    public boolean isGNUSparse() {
        return this.linkFlag == 83;
    }
    
    public boolean isGNULongLinkEntry() {
        return this.linkFlag == 75 && this.name.equals("././@LongLink");
    }
    
    public boolean isGNULongNameEntry() {
        return this.linkFlag == 76 && this.name.equals("././@LongLink");
    }
    
    public boolean isPaxHeader() {
        return this.linkFlag == 120 || this.linkFlag == 88;
    }
    
    public boolean isGlobalPaxHeader() {
        return this.linkFlag == 103;
    }
    
    public boolean isDirectory() {
        if (this.file != null) {
            return this.file.isDirectory();
        }
        return this.linkFlag == 53 || this.getName().endsWith("/");
    }
    
    public boolean isFile() {
        if (this.file != null) {
            return this.file.isFile();
        }
        return this.linkFlag == 0 || this.linkFlag == 48 || !this.getName().endsWith("/");
    }
    
    public boolean isSymbolicLink() {
        return this.linkFlag == 50;
    }
    
    public boolean isLink() {
        return this.linkFlag == 49;
    }
    
    public boolean isCharacterDevice() {
        return this.linkFlag == 51;
    }
    
    public boolean isBlockDevice() {
        return this.linkFlag == 52;
    }
    
    public boolean isFIFO() {
        return this.linkFlag == 54;
    }
    
    public TarArchiveEntry[] getDirectoryEntries() {
        if (this.file == null || !this.file.isDirectory()) {
            return new TarArchiveEntry[0];
        }
        final String[] list = this.file.list();
        final TarArchiveEntry[] array = new TarArchiveEntry[list.length];
        while (0 < list.length) {
            array[0] = new TarArchiveEntry(new File(this.file, list[0]));
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public void writeEntryHeader(final byte[] array) {
        this.writeEntryHeader(array, TarUtils.DEFAULT_ENCODING, false);
    }
    
    public void writeEntryHeader(final byte[] array, final ZipEncoding zipEncoding, final boolean b) throws IOException {
        TarUtils.formatNameBytes(this.name, array, 0, 100, zipEncoding);
        this.writeEntryHeaderField(this.mode, array, 0, 8, b);
        this.writeEntryHeaderField(this.userId, array, 0, 8, b);
        this.writeEntryHeaderField(this.groupId, array, 0, 8, b);
        this.writeEntryHeaderField(this.size, array, 0, 12, b);
        int writeEntryHeaderField = this.writeEntryHeaderField(this.modTime, array, 0, 12, b);
        while (0 < 8) {
            final int n = 0;
            ++writeEntryHeaderField;
            array[n] = 32;
            int n2 = 0;
            ++n2;
        }
        final int n3 = 0;
        ++writeEntryHeaderField;
        array[n3] = this.linkFlag;
        TarUtils.formatNameBytes(this.linkName, array, 0, 100, zipEncoding);
        TarUtils.formatNameBytes(this.magic, array, 0, 6);
        TarUtils.formatNameBytes(this.version, array, 0, 2);
        TarUtils.formatNameBytes(this.userName, array, 0, 32, zipEncoding);
        TarUtils.formatNameBytes(this.groupName, array, 0, 32, zipEncoding);
        this.writeEntryHeaderField(this.devMajor, array, 0, 8, b);
        int writeEntryHeaderField2 = this.writeEntryHeaderField(this.devMinor, array, 0, 8, b);
        while (0 < array.length) {
            final int n4 = 0;
            ++writeEntryHeaderField2;
            array[n4] = 0;
        }
        TarUtils.formatCheckSumOctalBytes(TarUtils.computeCheckSum(array), array, 0, 8);
    }
    
    private int writeEntryHeaderField(final long n, final byte[] array, final int n2, final int n3, final boolean b) {
        if (!b && (n < 0L || n >= 1L << 3 * (n3 - 1))) {
            return TarUtils.formatLongOctalBytes(0L, array, n2, n3);
        }
        return TarUtils.formatLongOctalOrBinaryBytes(n, array, n2, n3);
    }
    
    public void parseTarHeader(final byte[] array) {
        this.parseTarHeader(array, TarUtils.DEFAULT_ENCODING);
    }
    
    public void parseTarHeader(final byte[] array, final ZipEncoding zipEncoding) throws IOException {
        this.parseTarHeader(array, zipEncoding, false);
    }
    
    private void parseTarHeader(final byte[] array, final ZipEncoding zipEncoding, final boolean b) throws IOException {
        this.name = (b ? TarUtils.parseName(array, 0, 100) : TarUtils.parseName(array, 0, 100, zipEncoding));
        int n = 0;
        n += 100;
        this.mode = (int)TarUtils.parseOctalOrBinary(array, 0, 8);
        n += 8;
        this.userId = (int)TarUtils.parseOctalOrBinary(array, 0, 8);
        n += 8;
        this.groupId = (int)TarUtils.parseOctalOrBinary(array, 0, 8);
        n += 8;
        this.size = TarUtils.parseOctalOrBinary(array, 0, 12);
        n += 12;
        this.modTime = TarUtils.parseOctalOrBinary(array, 0, 12);
        n += 12;
        this.checkSumOK = TarUtils.verifyCheckSum(array);
        n += 8;
        final int n2 = 0;
        ++n;
        this.linkFlag = array[n2];
        this.linkName = (b ? TarUtils.parseName(array, 0, 100) : TarUtils.parseName(array, 0, 100, zipEncoding));
        n += 100;
        this.magic = TarUtils.parseName(array, 0, 6);
        n += 6;
        this.version = TarUtils.parseName(array, 0, 2);
        n += 2;
        this.userName = (b ? TarUtils.parseName(array, 0, 32) : TarUtils.parseName(array, 0, 32, zipEncoding));
        n += 32;
        this.groupName = (b ? TarUtils.parseName(array, 0, 32) : TarUtils.parseName(array, 0, 32, zipEncoding));
        n += 32;
        this.devMajor = (int)TarUtils.parseOctalOrBinary(array, 0, 8);
        n += 8;
        this.devMinor = (int)TarUtils.parseOctalOrBinary(array, 0, 8);
        n += 8;
        switch (this.evaluateType(array)) {
            case 2: {
                n += 12;
                n += 12;
                n += 12;
                n += 4;
                ++n;
                n += 96;
                this.isExtended = TarUtils.parseBoolean(array, 0);
                ++n;
                this.realSize = TarUtils.parseOctal(array, 0, 12);
                n += 12;
                break;
            }
            default: {
                final String s = b ? TarUtils.parseName(array, 0, 155) : TarUtils.parseName(array, 0, 155, zipEncoding);
                if (this.isDirectory() && !this.name.endsWith("/")) {
                    this.name += "/";
                }
                if (s.length() > 0) {
                    this.name = s + "/" + this.name;
                    break;
                }
                break;
            }
        }
    }
    
    private static String normalizeFileName(String s, final boolean b) {
        final String lowerCase = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (lowerCase != null) {
            if (lowerCase.startsWith("windows")) {
                if (s.length() > 2) {
                    final char char1 = s.charAt(0);
                    if (s.charAt(1) == ':' && ((char1 >= 'a' && char1 <= 'z') || (char1 >= 'A' && char1 <= 'Z'))) {
                        s = s.substring(2);
                    }
                }
            }
            else if (lowerCase.indexOf("netware") > -1) {
                final int index = s.indexOf(58);
                if (index != -1) {
                    s = s.substring(index + 1);
                }
            }
        }
        for (s = s.replace(File.separatorChar, '/'); !b && s.startsWith("/"); s = s.substring(1)) {}
        return s;
    }
    
    private int evaluateType(final byte[] array) {
        if (ArchiveUtils.matchAsciiBuffer("ustar ", array, 257, 6)) {
            return 2;
        }
        if (ArchiveUtils.matchAsciiBuffer("ustar\u0000", array, 257, 6)) {
            return 3;
        }
        return 0;
    }
}
