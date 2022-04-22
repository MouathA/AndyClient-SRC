package org.apache.commons.io;

import java.math.*;
import java.nio.charset.*;
import org.apache.commons.io.filefilter.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import org.apache.commons.io.output.*;

public class FileUtils
{
    public static final long ONE_KB = 1024L;
    public static final BigInteger ONE_KB_BI;
    public static final long ONE_MB = 1048576L;
    public static final BigInteger ONE_MB_BI;
    private static final long FILE_COPY_BUFFER_SIZE = 31457280L;
    public static final long ONE_GB = 1073741824L;
    public static final BigInteger ONE_GB_BI;
    public static final long ONE_TB = 1099511627776L;
    public static final BigInteger ONE_TB_BI;
    public static final long ONE_PB = 1125899906842624L;
    public static final BigInteger ONE_PB_BI;
    public static final long ONE_EB = 1152921504606846976L;
    public static final BigInteger ONE_EB_BI;
    public static final BigInteger ONE_ZB;
    public static final BigInteger ONE_YB;
    public static final File[] EMPTY_FILE_ARRAY;
    private static final Charset UTF8;
    
    public static File getFile(final File file, final String... array) {
        if (file == null) {
            throw new NullPointerException("directorydirectory must not be null");
        }
        if (array == null) {
            throw new NullPointerException("names must not be null");
        }
        File file2 = file;
        while (0 < array.length) {
            file2 = new File(file2, array[0]);
            int n = 0;
            ++n;
        }
        return file2;
    }
    
    public static File getFile(final String... array) {
        if (array == null) {
            throw new NullPointerException("names must not be null");
        }
        File file = null;
        while (0 < array.length) {
            final String s = array[0];
            if (file == null) {
                file = new File(s);
            }
            else {
                file = new File(file, s);
            }
            int n = 0;
            ++n;
        }
        return file;
    }
    
    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }
    
    public static File getTempDirectory() {
        return new File(getTempDirectoryPath());
    }
    
    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }
    
    public static File getUserDirectory() {
        return new File(getUserDirectoryPath());
    }
    
    public static FileInputStream openInputStream(final File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        }
        if (!file.canRead()) {
            throw new IOException("File '" + file + "' cannot be read");
        }
        return new FileInputStream(file);
    }
    
    public static FileOutputStream openOutputStream(final File file) throws IOException {
        return openOutputStream(file, false);
    }
    
    public static FileOutputStream openOutputStream(final File file, final boolean b) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        }
        else {
            final File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Directory '" + parentFile + "' could not be created");
            }
        }
        return new FileOutputStream(file, b);
    }
    
    public static String byteCountToDisplaySize(final BigInteger bigInteger) {
        String s;
        if (bigInteger.divide(FileUtils.ONE_EB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(bigInteger.divide(FileUtils.ONE_EB_BI)) + " EB";
        }
        else if (bigInteger.divide(FileUtils.ONE_PB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(bigInteger.divide(FileUtils.ONE_PB_BI)) + " PB";
        }
        else if (bigInteger.divide(FileUtils.ONE_TB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(bigInteger.divide(FileUtils.ONE_TB_BI)) + " TB";
        }
        else if (bigInteger.divide(FileUtils.ONE_GB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(bigInteger.divide(FileUtils.ONE_GB_BI)) + " GB";
        }
        else if (bigInteger.divide(FileUtils.ONE_MB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(bigInteger.divide(FileUtils.ONE_MB_BI)) + " MB";
        }
        else if (bigInteger.divide(FileUtils.ONE_KB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(bigInteger.divide(FileUtils.ONE_KB_BI)) + " KB";
        }
        else {
            s = String.valueOf(bigInteger) + " bytes";
        }
        return s;
    }
    
    public static String byteCountToDisplaySize(final long n) {
        return byteCountToDisplaySize(BigInteger.valueOf(n));
    }
    
    public static void touch(final File file) throws IOException {
        if (!file.exists()) {
            IOUtils.closeQuietly(openOutputStream(file));
        }
        if (!file.setLastModified(System.currentTimeMillis())) {
            throw new IOException("Unable to set the last modification time for " + file);
        }
    }
    
    public static File[] convertFileCollectionToFileArray(final Collection collection) {
        return collection.toArray(new File[collection.size()]);
    }
    
    private static void innerListFiles(final Collection collection, final File file, final IOFileFilter ioFileFilter, final boolean b) {
        final File[] listFiles = file.listFiles((FileFilter)ioFileFilter);
        if (listFiles != null) {
            final File[] array = listFiles;
            while (0 < array.length) {
                final File file2 = array[0];
                if (file2.isDirectory()) {
                    if (b) {
                        collection.add(file2);
                    }
                    innerListFiles(collection, file2, ioFileFilter, b);
                }
                else {
                    collection.add(file2);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public static Collection listFiles(final File file, final IOFileFilter upEffectiveFileFilter, final IOFileFilter upEffectiveDirFilter) {
        validateListFilesParameters(file, upEffectiveFileFilter);
        final IOFileFilter setUpEffectiveFileFilter = setUpEffectiveFileFilter(upEffectiveFileFilter);
        final IOFileFilter setUpEffectiveDirFilter = setUpEffectiveDirFilter(upEffectiveDirFilter);
        final LinkedList list = new LinkedList();
        innerListFiles(list, file, FileFilterUtils.or(setUpEffectiveFileFilter, setUpEffectiveDirFilter), false);
        return list;
    }
    
    private static void validateListFilesParameters(final File file, final IOFileFilter ioFileFilter) {
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Parameter 'directory' is not a directory");
        }
        if (ioFileFilter == null) {
            throw new NullPointerException("Parameter 'fileFilter' is null");
        }
    }
    
    private static IOFileFilter setUpEffectiveFileFilter(final IOFileFilter ioFileFilter) {
        return FileFilterUtils.and(ioFileFilter, FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE));
    }
    
    private static IOFileFilter setUpEffectiveDirFilter(final IOFileFilter ioFileFilter) {
        return (ioFileFilter == null) ? FalseFileFilter.INSTANCE : FileFilterUtils.and(ioFileFilter, DirectoryFileFilter.INSTANCE);
    }
    
    public static Collection listFilesAndDirs(final File file, final IOFileFilter upEffectiveFileFilter, final IOFileFilter upEffectiveDirFilter) {
        validateListFilesParameters(file, upEffectiveFileFilter);
        final IOFileFilter setUpEffectiveFileFilter = setUpEffectiveFileFilter(upEffectiveFileFilter);
        final IOFileFilter setUpEffectiveDirFilter = setUpEffectiveDirFilter(upEffectiveDirFilter);
        final LinkedList<File> list = new LinkedList<File>();
        if (file.isDirectory()) {
            list.add(file);
        }
        innerListFiles(list, file, FileFilterUtils.or(setUpEffectiveFileFilter, setUpEffectiveDirFilter), true);
        return list;
    }
    
    public static Iterator iterateFiles(final File file, final IOFileFilter ioFileFilter, final IOFileFilter ioFileFilter2) {
        return listFiles(file, ioFileFilter, ioFileFilter2).iterator();
    }
    
    public static Iterator iterateFilesAndDirs(final File file, final IOFileFilter ioFileFilter, final IOFileFilter ioFileFilter2) {
        return listFilesAndDirs(file, ioFileFilter, ioFileFilter2).iterator();
    }
    
    private static String[] toSuffixes(final String[] array) {
        final String[] array2 = new String[array.length];
        while (0 < array.length) {
            array2[0] = "." + array[0];
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static Collection listFiles(final File file, final String[] array, final boolean b) {
        IOFileFilter instance;
        if (array == null) {
            instance = TrueFileFilter.INSTANCE;
        }
        else {
            instance = new SuffixFileFilter(toSuffixes(array));
        }
        return listFiles(file, instance, b ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
    }
    
    public static Iterator iterateFiles(final File file, final String[] array, final boolean b) {
        return listFiles(file, array, b).iterator();
    }
    
    public static boolean contentEquals(final File file, final File file2) throws IOException {
        final boolean exists = file.exists();
        if (exists != file2.exists()) {
            return false;
        }
        if (!exists) {
            return true;
        }
        if (file.isDirectory() || file2.isDirectory()) {
            throw new IOException("Can't compare directories, only files");
        }
        if (file.length() != file2.length()) {
            return false;
        }
        if (file.getCanonicalFile().equals(file2.getCanonicalFile())) {
            return true;
        }
        final FileInputStream fileInputStream = new FileInputStream(file);
        final FileInputStream fileInputStream2 = new FileInputStream(file2);
        final boolean contentEquals = IOUtils.contentEquals(fileInputStream, fileInputStream2);
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(fileInputStream2);
        return contentEquals;
    }
    
    public static boolean contentEqualsIgnoreEOL(final File file, final File file2, final String s) throws IOException {
        final boolean exists = file.exists();
        if (exists != file2.exists()) {
            return false;
        }
        if (!exists) {
            return true;
        }
        if (file.isDirectory() || file2.isDirectory()) {
            throw new IOException("Can't compare directories, only files");
        }
        if (file.getCanonicalFile().equals(file2.getCanonicalFile())) {
            return true;
        }
        InputStreamReader inputStreamReader;
        InputStreamReader inputStreamReader2;
        if (s == null) {
            inputStreamReader = new InputStreamReader(new FileInputStream(file));
            inputStreamReader2 = new InputStreamReader(new FileInputStream(file2));
        }
        else {
            inputStreamReader = new InputStreamReader(new FileInputStream(file), s);
            inputStreamReader2 = new InputStreamReader(new FileInputStream(file2), s);
        }
        final boolean contentEqualsIgnoreEOL = IOUtils.contentEqualsIgnoreEOL(inputStreamReader, inputStreamReader2);
        IOUtils.closeQuietly(inputStreamReader);
        IOUtils.closeQuietly(inputStreamReader2);
        return contentEqualsIgnoreEOL;
    }
    
    public static File toFile(final URL url) {
        if (url == null || !"file".equalsIgnoreCase(url.getProtocol())) {
            return null;
        }
        return new File(decodeUrl(url.getFile().replace('/', File.separatorChar)));
    }
    
    static String decodeUrl(final String s) {
        String string = s;
        if (s != null && s.indexOf(37) >= 0) {
            final int length = s.length();
            final StringBuffer sb = new StringBuffer();
            final ByteBuffer allocate = ByteBuffer.allocate(length);
            while (0 < length) {
                if (s.charAt(0) == '%') {
                    do {
                        allocate.put((byte)Integer.parseInt(s.substring(1, 3), 16));
                        final int n;
                        n += 3;
                    } while (0 < length && s.charAt(0) == '%');
                    if (allocate.position() <= 0) {
                        continue;
                    }
                    allocate.flip();
                    sb.append(FileUtils.UTF8.decode(allocate).toString());
                    allocate.clear();
                }
                else {
                    final StringBuffer sb2 = sb;
                    final int n2 = 0;
                    int n = 0;
                    ++n;
                    sb2.append(s.charAt(n2));
                }
            }
            string = sb.toString();
        }
        return string;
    }
    
    public static File[] toFiles(final URL[] array) {
        if (array == null || array.length == 0) {
            return FileUtils.EMPTY_FILE_ARRAY;
        }
        final File[] array2 = new File[array.length];
        while (0 < array.length) {
            final URL url = array[0];
            if (url != null) {
                if (!url.getProtocol().equals("file")) {
                    throw new IllegalArgumentException("URL could not be converted to a File: " + url);
                }
                array2[0] = toFile(url);
            }
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static URL[] toURLs(final File[] array) throws IOException {
        final URL[] array2 = new URL[array.length];
        while (0 < array2.length) {
            array2[0] = array[0].toURI().toURL();
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static void copyFileToDirectory(final File file, final File file2) throws IOException {
        copyFileToDirectory(file, file2, true);
    }
    
    public static void copyFileToDirectory(final File file, final File file2, final boolean b) throws IOException {
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (file2.exists() && !file2.isDirectory()) {
            throw new IllegalArgumentException("Destination '" + file2 + "' is not a directory");
        }
        copyFile(file, new File(file2, file.getName()), b);
    }
    
    public static void copyFile(final File file, final File file2) throws IOException {
        copyFile(file, file2, true);
    }
    
    public static void copyFile(final File file, final File file2, final boolean b) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            throw new IOException("Source '" + file + "' exists but is a directory");
        }
        if (file.getCanonicalPath().equals(file2.getCanonicalPath())) {
            throw new IOException("Source '" + file + "' and destination '" + file2 + "' are the same");
        }
        final File parentFile = file2.getParentFile();
        if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
            throw new IOException("Destination '" + parentFile + "' directory cannot be created");
        }
        if (file2.exists() && !file2.canWrite()) {
            throw new IOException("Destination '" + file2 + "' exists but is read-only");
        }
        doCopyFile(file, file2, b);
    }
    
    public static long copyFile(final File file, final OutputStream outputStream) throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(file);
        final long copyLarge = IOUtils.copyLarge(fileInputStream, outputStream);
        fileInputStream.close();
        return copyLarge;
    }
    
    private static void doCopyFile(final File file, final File file2, final boolean b) throws IOException {
        if (file2.exists() && file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' exists but is a directory");
        }
        final FileInputStream fileInputStream = new FileInputStream(file);
        final FileOutputStream fileOutputStream = new FileOutputStream(file2);
        final FileChannel channel = fileInputStream.getChannel();
        final FileChannel channel2 = fileOutputStream.getChannel();
        for (long size = channel.size(), n = 0L; n < size; n += channel2.transferFrom(channel, n, (size - n > 31457280L) ? 31457280L : (size - n))) {}
        IOUtils.closeQuietly(channel2);
        IOUtils.closeQuietly(fileOutputStream);
        IOUtils.closeQuietly(channel);
        IOUtils.closeQuietly(fileInputStream);
        if (file.length() != file2.length()) {
            throw new IOException("Failed to copy full contents from '" + file + "' to '" + file2 + "'");
        }
        if (b) {
            file2.setLastModified(file.lastModified());
        }
    }
    
    public static void copyDirectoryToDirectory(final File file, final File file2) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file.exists() && !file.isDirectory()) {
            throw new IllegalArgumentException("Source '" + file2 + "' is not a directory");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (file2.exists() && !file2.isDirectory()) {
            throw new IllegalArgumentException("Destination '" + file2 + "' is not a directory");
        }
        copyDirectory(file, new File(file2, file.getName()), true);
    }
    
    public static void copyDirectory(final File file, final File file2) throws IOException {
        copyDirectory(file, file2, true);
    }
    
    public static void copyDirectory(final File file, final File file2, final boolean b) throws IOException {
        copyDirectory(file, file2, null, b);
    }
    
    public static void copyDirectory(final File file, final File file2, final FileFilter fileFilter) throws IOException {
        copyDirectory(file, file2, fileFilter, true);
    }
    
    public static void copyDirectory(final File file, final File file2, final FileFilter fileFilter, final boolean b) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (!file.isDirectory()) {
            throw new IOException("Source '" + file + "' exists but is not a directory");
        }
        if (file.getCanonicalPath().equals(file2.getCanonicalPath())) {
            throw new IOException("Source '" + file + "' and destination '" + file2 + "' are the same");
        }
        List<String> list = null;
        if (file2.getCanonicalPath().startsWith(file.getCanonicalPath())) {
            final File[] array = (fileFilter == null) ? file.listFiles() : file.listFiles(fileFilter);
            if (array != null && array.length > 0) {
                list = new ArrayList<String>(array.length);
                final File[] array2 = array;
                while (0 < array2.length) {
                    list.add(new File(file2, array2[0].getName()).getCanonicalPath());
                    int n = 0;
                    ++n;
                }
            }
        }
        doCopyDirectory(file, file2, fileFilter, b, list);
    }
    
    private static void doCopyDirectory(final File file, final File file2, final FileFilter fileFilter, final boolean b, final List list) throws IOException {
        final File[] array = (fileFilter == null) ? file.listFiles() : file.listFiles(fileFilter);
        if (array == null) {
            throw new IOException("Failed to list contents of " + file);
        }
        if (file2.exists()) {
            if (!file2.isDirectory()) {
                throw new IOException("Destination '" + file2 + "' exists but is not a directory");
            }
        }
        else if (!file2.mkdirs() && !file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' directory cannot be created");
        }
        if (!file2.canWrite()) {
            throw new IOException("Destination '" + file2 + "' cannot be written to");
        }
        final File[] array2 = array;
        while (0 < array2.length) {
            final File file3 = array2[0];
            final File file4 = new File(file2, file3.getName());
            if (list == null || !list.contains(file3.getCanonicalPath())) {
                if (file3.isDirectory()) {
                    doCopyDirectory(file3, file4, fileFilter, b, list);
                }
                else {
                    doCopyFile(file3, file4, b);
                }
            }
            int n = 0;
            ++n;
        }
        if (b) {
            file2.setLastModified(file.lastModified());
        }
    }
    
    public static void copyURLToFile(final URL url, final File file) throws IOException {
        copyInputStreamToFile(url.openStream(), file);
    }
    
    public static void copyURLToFile(final URL url, final File file, final int connectTimeout, final int readTimeout) throws IOException {
        final URLConnection openConnection = url.openConnection();
        openConnection.setConnectTimeout(connectTimeout);
        openConnection.setReadTimeout(readTimeout);
        copyInputStreamToFile(openConnection.getInputStream(), file);
    }
    
    public static void copyInputStreamToFile(final InputStream inputStream, final File file) throws IOException {
        final FileOutputStream openOutputStream = openOutputStream(file);
        IOUtils.copy(inputStream, openOutputStream);
        openOutputStream.close();
        IOUtils.closeQuietly(openOutputStream);
        IOUtils.closeQuietly(inputStream);
    }
    
    public static void deleteDirectory(final File file) throws IOException {
        if (!file.exists()) {
            return;
        }
        if (!isSymlink(file)) {
            cleanDirectory(file);
        }
        if (!file.delete()) {
            throw new IOException("Unable to delete directory " + file + ".");
        }
    }
    
    public static boolean deleteQuietly(final File file) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            cleanDirectory(file);
        }
        return file.delete();
    }
    
    public static boolean directoryContains(final File file, final File file2) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("Directory must not be null");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + file);
        }
        return file2 != null && file.exists() && file2.exists() && FilenameUtils.directoryContains(file.getCanonicalPath(), file2.getCanonicalPath());
    }
    
    public static void cleanDirectory(final File file) throws IOException {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(file + " is not a directory");
        }
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            throw new IOException("Failed to list contents of " + file);
        }
        final Object o = null;
        final File[] array = listFiles;
        while (0 < array.length) {
            forceDelete(array[0]);
            int n = 0;
            ++n;
        }
        if (null != o) {
            throw o;
        }
    }
    
    public static boolean waitFor(final File file, final int n) {
        while (!file.exists()) {
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            if (n2 >= 10) {
                final int n4 = 0;
                int n5 = 0;
                ++n5;
                if (n4 > n) {
                    return false;
                }
            }
            Thread.sleep(100L);
        }
        return true;
    }
    
    public static String readFileToString(final File file, final Charset charset) throws IOException {
        final FileInputStream openInputStream = openInputStream(file);
        final String string = IOUtils.toString(openInputStream, Charsets.toCharset(charset));
        IOUtils.closeQuietly(openInputStream);
        return string;
    }
    
    public static String readFileToString(final File file, final String s) throws IOException {
        return readFileToString(file, Charsets.toCharset(s));
    }
    
    public static String readFileToString(final File file) throws IOException {
        return readFileToString(file, Charset.defaultCharset());
    }
    
    public static byte[] readFileToByteArray(final File file) throws IOException {
        final FileInputStream openInputStream = openInputStream(file);
        final byte[] byteArray = IOUtils.toByteArray(openInputStream, file.length());
        IOUtils.closeQuietly(openInputStream);
        return byteArray;
    }
    
    public static List readLines(final File file, final Charset charset) throws IOException {
        final FileInputStream openInputStream = openInputStream(file);
        final List lines = IOUtils.readLines(openInputStream, Charsets.toCharset(charset));
        IOUtils.closeQuietly(openInputStream);
        return lines;
    }
    
    public static List readLines(final File file, final String s) throws IOException {
        return readLines(file, Charsets.toCharset(s));
    }
    
    public static List readLines(final File file) throws IOException {
        return readLines(file, Charset.defaultCharset());
    }
    
    public static LineIterator lineIterator(final File file, final String s) throws IOException {
        return IOUtils.lineIterator(openInputStream(file), s);
    }
    
    public static LineIterator lineIterator(final File file) throws IOException {
        return lineIterator(file, null);
    }
    
    public static void writeStringToFile(final File file, final String s, final Charset charset) throws IOException {
        writeStringToFile(file, s, charset, false);
    }
    
    public static void writeStringToFile(final File file, final String s, final String s2) throws IOException {
        writeStringToFile(file, s, s2, false);
    }
    
    public static void writeStringToFile(final File file, final String s, final Charset charset, final boolean b) throws IOException {
        final FileOutputStream openOutputStream = openOutputStream(file, b);
        IOUtils.write(s, openOutputStream, charset);
        openOutputStream.close();
        IOUtils.closeQuietly(openOutputStream);
    }
    
    public static void writeStringToFile(final File file, final String s, final String s2, final boolean b) throws IOException {
        writeStringToFile(file, s, Charsets.toCharset(s2), b);
    }
    
    public static void writeStringToFile(final File file, final String s) throws IOException {
        writeStringToFile(file, s, Charset.defaultCharset(), false);
    }
    
    public static void writeStringToFile(final File file, final String s, final boolean b) throws IOException {
        writeStringToFile(file, s, Charset.defaultCharset(), b);
    }
    
    public static void write(final File file, final CharSequence charSequence) throws IOException {
        write(file, charSequence, Charset.defaultCharset(), false);
    }
    
    public static void write(final File file, final CharSequence charSequence, final boolean b) throws IOException {
        write(file, charSequence, Charset.defaultCharset(), b);
    }
    
    public static void write(final File file, final CharSequence charSequence, final Charset charset) throws IOException {
        write(file, charSequence, charset, false);
    }
    
    public static void write(final File file, final CharSequence charSequence, final String s) throws IOException {
        write(file, charSequence, s, false);
    }
    
    public static void write(final File file, final CharSequence charSequence, final Charset charset, final boolean b) throws IOException {
        writeStringToFile(file, (charSequence == null) ? null : charSequence.toString(), charset, b);
    }
    
    public static void write(final File file, final CharSequence charSequence, final String s, final boolean b) throws IOException {
        write(file, charSequence, Charsets.toCharset(s), b);
    }
    
    public static void writeByteArrayToFile(final File file, final byte[] array) throws IOException {
        writeByteArrayToFile(file, array, false);
    }
    
    public static void writeByteArrayToFile(final File file, final byte[] array, final boolean b) throws IOException {
        final FileOutputStream openOutputStream = openOutputStream(file, b);
        openOutputStream.write(array);
        openOutputStream.close();
        IOUtils.closeQuietly(openOutputStream);
    }
    
    public static void writeLines(final File file, final String s, final Collection collection) throws IOException {
        writeLines(file, s, collection, null, false);
    }
    
    public static void writeLines(final File file, final String s, final Collection collection, final boolean b) throws IOException {
        writeLines(file, s, collection, null, b);
    }
    
    public static void writeLines(final File file, final Collection collection) throws IOException {
        writeLines(file, null, collection, null, false);
    }
    
    public static void writeLines(final File file, final Collection collection, final boolean b) throws IOException {
        writeLines(file, null, collection, null, b);
    }
    
    public static void writeLines(final File file, final String s, final Collection collection, final String s2) throws IOException {
        writeLines(file, s, collection, s2, false);
    }
    
    public static void writeLines(final File file, final String s, final Collection collection, final String s2, final boolean b) throws IOException {
        final FileOutputStream openOutputStream = openOutputStream(file, b);
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(openOutputStream);
        IOUtils.writeLines(collection, s2, bufferedOutputStream, s);
        bufferedOutputStream.flush();
        openOutputStream.close();
        IOUtils.closeQuietly(openOutputStream);
    }
    
    public static void writeLines(final File file, final Collection collection, final String s) throws IOException {
        writeLines(file, null, collection, s, false);
    }
    
    public static void writeLines(final File file, final Collection collection, final String s, final boolean b) throws IOException {
        writeLines(file, null, collection, s, b);
    }
    
    public static void forceDelete(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        }
        else {
            final boolean exists = file.exists();
            if (!file.delete()) {
                if (!exists) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                throw new IOException("Unable to delete file: " + file);
            }
        }
    }
    
    public static void forceDeleteOnExit(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectoryOnExit(file);
        }
        else {
            file.deleteOnExit();
        }
    }
    
    private static void deleteDirectoryOnExit(final File file) throws IOException {
        if (!file.exists()) {
            return;
        }
        file.deleteOnExit();
        if (!isSymlink(file)) {
            cleanDirectoryOnExit(file);
        }
    }
    
    private static void cleanDirectoryOnExit(final File file) throws IOException {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(file + " is not a directory");
        }
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            throw new IOException("Failed to list contents of " + file);
        }
        final Object o = null;
        final File[] array = listFiles;
        while (0 < array.length) {
            forceDeleteOnExit(array[0]);
            int n = 0;
            ++n;
        }
        if (null != o) {
            throw o;
        }
    }
    
    public static void forceMkdir(final File file) throws IOException {
        if (file.exists()) {
            if (!file.isDirectory()) {
                throw new IOException("File " + file + " exists and is " + "not a directory. Unable to create directory.");
            }
        }
        else if (!file.mkdirs() && !file.isDirectory()) {
            throw new IOException("Unable to create directory " + file);
        }
    }
    
    public static long sizeOf(final File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (file.isDirectory()) {
            return sizeOfDirectory(file);
        }
        return file.length();
    }
    
    public static BigInteger sizeOfAsBigInteger(final File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (file.isDirectory()) {
            return sizeOfDirectoryAsBigInteger(file);
        }
        return BigInteger.valueOf(file.length());
    }
    
    public static long sizeOfDirectory(final File file) {
        checkDirectory(file);
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return 0L;
        }
        long n = 0L;
        final File[] array = listFiles;
        while (0 < array.length) {
            final File file2 = array[0];
            if (!isSymlink(file2)) {
                n += sizeOf(file2);
                if (n < 0L) {
                    break;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static BigInteger sizeOfDirectoryAsBigInteger(final File file) {
        checkDirectory(file);
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return BigInteger.ZERO;
        }
        BigInteger bigInteger = BigInteger.ZERO;
        final File[] array = listFiles;
        while (0 < array.length) {
            final File file2 = array[0];
            if (!isSymlink(file2)) {
                bigInteger = bigInteger.add(BigInteger.valueOf(sizeOf(file2)));
            }
            int n = 0;
            ++n;
        }
        return bigInteger;
    }
    
    private static void checkDirectory(final File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(file + " is not a directory");
        }
    }
    
    public static boolean isFileNewer(final File file, final File file2) {
        if (file2 == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (!file2.exists()) {
            throw new IllegalArgumentException("The reference file '" + file2 + "' doesn't exist");
        }
        return isFileNewer(file, file2.lastModified());
    }
    
    public static boolean isFileNewer(final File file, final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("No specified date");
        }
        return isFileNewer(file, date.getTime());
    }
    
    public static boolean isFileNewer(final File file, final long n) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        return file.exists() && file.lastModified() > n;
    }
    
    public static boolean isFileOlder(final File file, final File file2) {
        if (file2 == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (!file2.exists()) {
            throw new IllegalArgumentException("The reference file '" + file2 + "' doesn't exist");
        }
        return isFileOlder(file, file2.lastModified());
    }
    
    public static boolean isFileOlder(final File file, final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("No specified date");
        }
        return isFileOlder(file, date.getTime());
    }
    
    public static boolean isFileOlder(final File file, final long n) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        return file.exists() && file.lastModified() < n;
    }
    
    public static long checksumCRC32(final File file) throws IOException {
        final CRC32 crc32 = new CRC32();
        checksum(file, crc32);
        return crc32.getValue();
    }
    
    public static Checksum checksum(final File file, final Checksum checksum) throws IOException {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("Checksums can't be computed on directories");
        }
        final CheckedInputStream checkedInputStream = new CheckedInputStream(new FileInputStream(file), checksum);
        IOUtils.copy(checkedInputStream, new NullOutputStream());
        IOUtils.closeQuietly(checkedInputStream);
        return checksum;
    }
    
    public static void moveDirectory(final File file, final File file2) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (!file.isDirectory()) {
            throw new IOException("Source '" + file + "' is not a directory");
        }
        if (file2.exists()) {
            throw new FileExistsException("Destination '" + file2 + "' already exists");
        }
        if (!file.renameTo(file2)) {
            if (file2.getCanonicalPath().startsWith(file.getCanonicalPath())) {
                throw new IOException("Cannot move directory: " + file + " to a subdirectory of itself: " + file2);
            }
            copyDirectory(file, file2);
            deleteDirectory(file);
            if (file.exists()) {
                throw new IOException("Failed to delete original directory '" + file + "' after copy to '" + file2 + "'");
            }
        }
    }
    
    public static void moveDirectoryToDirectory(final File file, final File file2, final boolean b) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination directory must not be null");
        }
        if (!file2.exists() && b) {
            file2.mkdirs();
        }
        if (!file2.exists()) {
            throw new FileNotFoundException("Destination directory '" + file2 + "' does not exist [createDestDir=" + b + "]");
        }
        if (!file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' is not a directory");
        }
        moveDirectory(file, new File(file2, file.getName()));
    }
    
    public static void moveFile(final File file, final File file2) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            throw new IOException("Source '" + file + "' is a directory");
        }
        if (file2.exists()) {
            throw new FileExistsException("Destination '" + file2 + "' already exists");
        }
        if (file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' is a directory");
        }
        if (!file.renameTo(file2)) {
            copyFile(file, file2);
            if (!file.delete()) {
                deleteQuietly(file2);
                throw new IOException("Failed to delete original file '" + file + "' after copy to '" + file2 + "'");
            }
        }
    }
    
    public static void moveFileToDirectory(final File file, final File file2, final boolean b) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination directory must not be null");
        }
        if (!file2.exists() && b) {
            file2.mkdirs();
        }
        if (!file2.exists()) {
            throw new FileNotFoundException("Destination directory '" + file2 + "' does not exist [createDestDir=" + b + "]");
        }
        if (!file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' is not a directory");
        }
        moveFile(file, new File(file2, file.getName()));
    }
    
    public static void moveToDirectory(final File file, final File file2, final boolean b) throws IOException {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            moveDirectoryToDirectory(file, file2, b);
        }
        else {
            moveFileToDirectory(file, file2, b);
        }
    }
    
    public static boolean isSymlink(final File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        if (FilenameUtils.isSystemWindows()) {
            return false;
        }
        File file2;
        if (file.getParent() == null) {
            file2 = file;
        }
        else {
            file2 = new File(file.getParentFile().getCanonicalFile(), file.getName());
        }
        return !file2.getCanonicalFile().equals(file2.getAbsoluteFile());
    }
    
    static {
        ONE_KB_BI = BigInteger.valueOf(1024L);
        ONE_MB_BI = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_KB_BI);
        ONE_GB_BI = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_MB_BI);
        ONE_TB_BI = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_GB_BI);
        ONE_PB_BI = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_TB_BI);
        ONE_EB_BI = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_PB_BI);
        ONE_ZB = BigInteger.valueOf(1024L).multiply(BigInteger.valueOf(1152921504606846976L));
        ONE_YB = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_ZB);
        EMPTY_FILE_ARRAY = new File[0];
        UTF8 = Charset.forName("UTF-8");
    }
}
