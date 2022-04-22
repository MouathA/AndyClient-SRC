package org.apache.commons.io;

import java.util.*;
import java.io.*;

public class FileSystemUtils
{
    private static final FileSystemUtils INSTANCE;
    private static final int INIT_PROBLEM = -1;
    private static final int OTHER = 0;
    private static final int WINDOWS = 1;
    private static final int UNIX = 2;
    private static final int POSIX_UNIX = 3;
    private static final int OS;
    private static final String DF;
    
    @Deprecated
    public static long freeSpace(final String s) throws IOException {
        return FileSystemUtils.INSTANCE.freeSpaceOS(s, FileSystemUtils.OS, false, -1L);
    }
    
    public static long freeSpaceKb(final String s) throws IOException {
        return freeSpaceKb(s, -1L);
    }
    
    public static long freeSpaceKb(final String s, final long n) throws IOException {
        return FileSystemUtils.INSTANCE.freeSpaceOS(s, FileSystemUtils.OS, true, n);
    }
    
    public static long freeSpaceKb() throws IOException {
        return freeSpaceKb(-1L);
    }
    
    public static long freeSpaceKb(final long n) throws IOException {
        return freeSpaceKb(new File(".").getAbsolutePath(), n);
    }
    
    long freeSpaceOS(final String s, final int n, final boolean b, final long n2) throws IOException {
        if (s == null) {
            throw new IllegalArgumentException("Path must not be empty");
        }
        switch (n) {
            case 1: {
                return b ? (this.freeSpaceWindows(s, n2) / 1024L) : this.freeSpaceWindows(s, n2);
            }
            case 2: {
                return this.freeSpaceUnix(s, b, false, n2);
            }
            case 3: {
                return this.freeSpaceUnix(s, b, true, n2);
            }
            case 0: {
                throw new IllegalStateException("Unsupported operating system");
            }
            default: {
                throw new IllegalStateException("Exception caught when determining operating system");
            }
        }
    }
    
    long freeSpaceWindows(String s, final long n) throws IOException {
        s = FilenameUtils.normalize(s, false);
        if (s.length() > 0 && s.charAt(0) != '\"') {
            s = "\"" + s + "\"";
        }
        final List performCommand = this.performCommand(new String[] { "cmd.exe", "/C", "dir /a /-c " + s }, Integer.MAX_VALUE, n);
        for (int i = performCommand.size() - 1; i >= 0; --i) {
            final String s2 = performCommand.get(i);
            if (s2.length() > 0) {
                return this.parseDir(s2, s);
            }
        }
        throw new IOException("Command line 'dir /-c' did not return any info for path '" + s + "'");
    }
    
    long parseDir(final String s, final String s2) throws IOException {
        int i;
        for (i = s.length() - 1; i >= 0; --i) {
            if (Character.isDigit(s.charAt(i))) {
                break;
            }
        }
        while (i >= 0) {
            final char char1 = s.charAt(i);
            if (!Character.isDigit(char1) && char1 != ',' && char1 != '.') {
                break;
            }
            --i;
        }
        if (i < 0) {
            throw new IOException("Command line 'dir /-c' did not return valid info for path '" + s2 + "'");
        }
        final StringBuilder sb = new StringBuilder(s.substring(0, 0));
        while (0 < sb.length()) {
            int n2 = 0;
            if (sb.charAt(0) == ',' || sb.charAt(0) == '.') {
                final StringBuilder sb2 = sb;
                final int n = 0;
                --n2;
                sb2.deleteCharAt(n);
            }
            ++n2;
        }
        return this.parseBytes(sb.toString(), s2);
    }
    
    long freeSpaceUnix(final String s, final boolean b, final boolean b2, final long n) throws IOException {
        if (s.length() == 0) {
            throw new IllegalArgumentException("Path must not be empty");
        }
        String s2 = "-";
        if (b) {
            s2 += "k";
        }
        if (b2) {
            s2 += "P";
        }
        final List performCommand = this.performCommand((s2.length() > 1) ? new String[] { FileSystemUtils.DF, s2, s } : new String[] { FileSystemUtils.DF, s }, 3, n);
        if (performCommand.size() < 2) {
            throw new IOException("Command line '" + FileSystemUtils.DF + "' did not return info as expected " + "for path '" + s + "'- response was " + performCommand);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(performCommand.get(1), " ");
        if (stringTokenizer.countTokens() < 4) {
            if (stringTokenizer.countTokens() != 1 || performCommand.size() < 3) {
                throw new IOException("Command line '" + FileSystemUtils.DF + "' did not return data as expected " + "for path '" + s + "'- check path is valid");
            }
            stringTokenizer = new StringTokenizer(performCommand.get(2), " ");
        }
        else {
            stringTokenizer.nextToken();
        }
        stringTokenizer.nextToken();
        stringTokenizer.nextToken();
        return this.parseBytes(stringTokenizer.nextToken(), s);
    }
    
    long parseBytes(final String s, final String s2) throws IOException {
        final long long1 = Long.parseLong(s);
        if (long1 < 0L) {
            throw new IOException("Command line '" + FileSystemUtils.DF + "' did not find free space in response " + "for path '" + s2 + "'- check path is valid");
        }
        return long1;
    }
    
    List performCommand(final String[] array, final int n, final long n2) throws IOException {
        final ArrayList<String> list = new ArrayList<String>(20);
        final Thread start = ThreadMonitor.start(n2);
        final Process openProcess = this.openProcess(array);
        final InputStream inputStream = openProcess.getInputStream();
        final OutputStream outputStream = openProcess.getOutputStream();
        final InputStream errorStream = openProcess.getErrorStream();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        for (String s = bufferedReader.readLine(); s != null && list.size() < n; s = bufferedReader.readLine()) {
            list.add(s.toLowerCase(Locale.ENGLISH).trim());
        }
        openProcess.waitFor();
        ThreadMonitor.stop(start);
        if (openProcess.exitValue() != 0) {
            throw new IOException("Command line returned OS error code '" + openProcess.exitValue() + "' for command " + Arrays.asList(array));
        }
        if (list.isEmpty()) {
            throw new IOException("Command line did not return any info for command " + Arrays.asList(array));
        }
        final ArrayList<String> list2 = list;
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
        IOUtils.closeQuietly(errorStream);
        IOUtils.closeQuietly(bufferedReader);
        if (openProcess != null) {
            openProcess.destroy();
        }
        return list2;
    }
    
    Process openProcess(final String[] array) throws IOException {
        return Runtime.getRuntime().exec(array);
    }
    
    static {
        INSTANCE = new FileSystemUtils();
        String df = "df";
        final String property = System.getProperty("os.name");
        if (property == null) {
            throw new IOException("os.name not found");
        }
        final String lowerCase = property.toLowerCase(Locale.ENGLISH);
        if (lowerCase.indexOf("windows") == -1) {
            if (lowerCase.indexOf("linux") == -1 && lowerCase.indexOf("mpe/ix") == -1 && lowerCase.indexOf("freebsd") == -1 && lowerCase.indexOf("irix") == -1 && lowerCase.indexOf("digital unix") == -1 && lowerCase.indexOf("unix") == -1 && lowerCase.indexOf("mac os x") == -1) {
                if (lowerCase.indexOf("sun os") != -1 || lowerCase.indexOf("sunos") != -1 || lowerCase.indexOf("solaris") != -1) {
                    df = "/usr/xpg4/bin/df";
                }
                else if (lowerCase.indexOf("hp-ux") != -1 || lowerCase.indexOf("aix") != -1) {}
            }
        }
        OS = -1;
        DF = df;
    }
}
