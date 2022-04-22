package com.google.common.io;

import com.google.common.annotations.*;
import java.nio.charset.*;
import com.google.common.hash.*;
import java.nio.*;
import java.nio.channels.*;
import java.io.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;

@Beta
public final class Files
{
    private static final int TEMP_DIR_ATTEMPTS = 10000;
    private static final TreeTraverser FILE_TREE_TRAVERSER;
    
    private Files() {
    }
    
    public static BufferedReader newReader(final File file, final Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }
    
    public static BufferedWriter newWriter(final File file, final Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    }
    
    public static ByteSource asByteSource(final File file) {
        return new FileByteSource(file, null);
    }
    
    static byte[] readFile(final InputStream inputStream, final long n) throws IOException {
        if (n > 2147483647L) {
            throw new OutOfMemoryError("file is too large to fit in a byte array: " + n + " bytes");
        }
        return (n == 0L) ? ByteStreams.toByteArray(inputStream) : ByteStreams.toByteArray(inputStream, (int)n);
    }
    
    public static ByteSink asByteSink(final File file, final FileWriteMode... array) {
        return new FileByteSink(file, array, null);
    }
    
    public static CharSource asCharSource(final File file, final Charset charset) {
        return asByteSource(file).asCharSource(charset);
    }
    
    public static CharSink asCharSink(final File file, final Charset charset, final FileWriteMode... array) {
        return asByteSink(file, array).asCharSink(charset);
    }
    
    @Deprecated
    public static InputSupplier newInputStreamSupplier(final File file) {
        return ByteStreams.asInputSupplier(asByteSource(file));
    }
    
    @Deprecated
    public static OutputSupplier newOutputStreamSupplier(final File file) {
        return newOutputStreamSupplier(file, false);
    }
    
    @Deprecated
    public static OutputSupplier newOutputStreamSupplier(final File file, final boolean b) {
        return ByteStreams.asOutputSupplier(asByteSink(file, modes(b)));
    }
    
    private static FileWriteMode[] modes(final boolean b) {
        return b ? new FileWriteMode[] { FileWriteMode.APPEND } : new FileWriteMode[0];
    }
    
    @Deprecated
    public static InputSupplier newReaderSupplier(final File file, final Charset charset) {
        return CharStreams.asInputSupplier(asCharSource(file, charset));
    }
    
    @Deprecated
    public static OutputSupplier newWriterSupplier(final File file, final Charset charset) {
        return newWriterSupplier(file, charset, false);
    }
    
    @Deprecated
    public static OutputSupplier newWriterSupplier(final File file, final Charset charset, final boolean b) {
        return CharStreams.asOutputSupplier(asCharSink(file, charset, modes(b)));
    }
    
    public static byte[] toByteArray(final File file) throws IOException {
        return asByteSource(file).read();
    }
    
    public static String toString(final File file, final Charset charset) throws IOException {
        return asCharSource(file, charset).read();
    }
    
    @Deprecated
    public static void copy(final InputSupplier inputSupplier, final File file) throws IOException {
        ByteStreams.asByteSource(inputSupplier).copyTo(asByteSink(file, new FileWriteMode[0]));
    }
    
    public static void write(final byte[] array, final File file) throws IOException {
        asByteSink(file, new FileWriteMode[0]).write(array);
    }
    
    @Deprecated
    public static void copy(final File file, final OutputSupplier outputSupplier) throws IOException {
        asByteSource(file).copyTo(ByteStreams.asByteSink(outputSupplier));
    }
    
    public static void copy(final File file, final OutputStream outputStream) throws IOException {
        asByteSource(file).copyTo(outputStream);
    }
    
    public static void copy(final File file, final File file2) throws IOException {
        Preconditions.checkArgument(!file.equals(file2), "Source %s and destination %s must be different", file, file2);
        asByteSource(file).copyTo(asByteSink(file2, new FileWriteMode[0]));
    }
    
    @Deprecated
    public static void copy(final InputSupplier inputSupplier, final File file, final Charset charset) throws IOException {
        CharStreams.asCharSource(inputSupplier).copyTo(asCharSink(file, charset, new FileWriteMode[0]));
    }
    
    public static void write(final CharSequence charSequence, final File file, final Charset charset) throws IOException {
        asCharSink(file, charset, new FileWriteMode[0]).write(charSequence);
    }
    
    public static void append(final CharSequence charSequence, final File file, final Charset charset) throws IOException {
        write(charSequence, file, charset, true);
    }
    
    private static void write(final CharSequence charSequence, final File file, final Charset charset, final boolean b) throws IOException {
        asCharSink(file, charset, modes(b)).write(charSequence);
    }
    
    @Deprecated
    public static void copy(final File file, final Charset charset, final OutputSupplier outputSupplier) throws IOException {
        asCharSource(file, charset).copyTo(CharStreams.asCharSink(outputSupplier));
    }
    
    public static void copy(final File file, final Charset charset, final Appendable appendable) throws IOException {
        asCharSource(file, charset).copyTo(appendable);
    }
    
    public static boolean equal(final File file, final File file2) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        if (file == file2 || file.equals(file2)) {
            return true;
        }
        final long length = file.length();
        final long length2 = file2.length();
        return (length == 0L || length2 == 0L || length == length2) && asByteSource(file).contentEquals(asByteSource(file2));
    }
    
    public static File createTempDir() {
        final File file = new File(System.getProperty("java.io.tmpdir"));
        final String string = System.currentTimeMillis() + "-";
        while (0 < 10000) {
            final File file2 = new File(file, string + 0);
            if (file2.mkdir()) {
                return file2;
            }
            int n = 0;
            ++n;
        }
        throw new IllegalStateException("Failed to create directory within 10000 attempts (tried " + string + "0 to " + string + 9999 + ')');
    }
    
    public static void touch(final File file) throws IOException {
        Preconditions.checkNotNull(file);
        if (!file.createNewFile() && !file.setLastModified(System.currentTimeMillis())) {
            throw new IOException("Unable to update modification time of " + file);
        }
    }
    
    public static void createParentDirs(final File file) throws IOException {
        Preconditions.checkNotNull(file);
        final File parentFile = file.getCanonicalFile().getParentFile();
        if (parentFile == null) {
            return;
        }
        parentFile.mkdirs();
        if (!parentFile.isDirectory()) {
            throw new IOException("Unable to create parent directories of " + file);
        }
    }
    
    public static void move(final File file, final File file2) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        Preconditions.checkArgument(!file.equals(file2), "Source %s and destination %s must be different", file, file2);
        if (!file.renameTo(file2)) {
            copy(file, file2);
            if (!file.delete()) {
                if (!file2.delete()) {
                    throw new IOException("Unable to delete " + file2);
                }
                throw new IOException("Unable to delete " + file);
            }
        }
    }
    
    public static String readFirstLine(final File file, final Charset charset) throws IOException {
        return asCharSource(file, charset).readFirstLine();
    }
    
    public static List readLines(final File file, final Charset charset) throws IOException {
        return (List)readLines(file, charset, new LineProcessor() {
            final List result = Lists.newArrayList();
            
            @Override
            public boolean processLine(final String s) {
                this.result.add(s);
                return true;
            }
            
            @Override
            public List getResult() {
                return this.result;
            }
            
            @Override
            public Object getResult() {
                return this.getResult();
            }
        });
    }
    
    public static Object readLines(final File file, final Charset charset, final LineProcessor lineProcessor) throws IOException {
        return CharStreams.readLines(newReaderSupplier(file, charset), lineProcessor);
    }
    
    public static Object readBytes(final File file, final ByteProcessor byteProcessor) throws IOException {
        return ByteStreams.readBytes(newInputStreamSupplier(file), byteProcessor);
    }
    
    public static HashCode hash(final File file, final HashFunction hashFunction) throws IOException {
        return asByteSource(file).hash(hashFunction);
    }
    
    public static MappedByteBuffer map(final File file) throws IOException {
        Preconditions.checkNotNull(file);
        return map(file, FileChannel.MapMode.READ_ONLY);
    }
    
    public static MappedByteBuffer map(final File file, final FileChannel.MapMode mapMode) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(mapMode);
        if (!file.exists()) {
            throw new FileNotFoundException(file.toString());
        }
        return map(file, mapMode, file.length());
    }
    
    public static MappedByteBuffer map(final File file, final FileChannel.MapMode mapMode, final long n) throws FileNotFoundException, IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(mapMode);
        final Closer create = Closer.create();
        final MappedByteBuffer map = map((RandomAccessFile)create.register(new RandomAccessFile(file, (mapMode == FileChannel.MapMode.READ_ONLY) ? "r" : "rw")), mapMode, n);
        create.close();
        return map;
    }
    
    private static MappedByteBuffer map(final RandomAccessFile randomAccessFile, final FileChannel.MapMode mapMode, final long n) throws IOException {
        final Closer create = Closer.create();
        final MappedByteBuffer map = ((FileChannel)create.register(randomAccessFile.getChannel())).map(mapMode, 0L, n);
        create.close();
        return map;
    }
    
    public static String simplifyPath(final String s) {
        Preconditions.checkNotNull(s);
        if (s.length() == 0) {
            return ".";
        }
        final Iterable split = Splitter.on('/').omitEmptyStrings().split(s);
        final ArrayList<String> list = new ArrayList<String>();
        for (final String s2 : split) {
            if (s2.equals(".")) {
                continue;
            }
            if (s2.equals("..")) {
                if (list.size() > 0 && !((String)list.get(list.size() - 1)).equals("..")) {
                    list.remove(list.size() - 1);
                }
                else {
                    list.add("..");
                }
            }
            else {
                list.add(s2);
            }
        }
        String s3 = Joiner.on('/').join(list);
        if (s.charAt(0) == '/') {
            s3 = "/" + s3;
        }
        while (s3.startsWith("/../")) {
            s3 = s3.substring(3);
        }
        if (s3.equals("/..")) {
            s3 = "/";
        }
        else if ("".equals(s3)) {
            s3 = ".";
        }
        return s3;
    }
    
    public static String getFileExtension(final String s) {
        Preconditions.checkNotNull(s);
        final String name = new File(s).getName();
        final int lastIndex = name.lastIndexOf(46);
        return (lastIndex == -1) ? "" : name.substring(lastIndex + 1);
    }
    
    public static String getNameWithoutExtension(final String s) {
        Preconditions.checkNotNull(s);
        final String name = new File(s).getName();
        final int lastIndex = name.lastIndexOf(46);
        return (lastIndex == -1) ? name : name.substring(0, lastIndex);
    }
    
    public static TreeTraverser fileTreeTraverser() {
        return Files.FILE_TREE_TRAVERSER;
    }
    
    public static Predicate isDirectory() {
        return FilePredicate.IS_DIRECTORY;
    }
    
    public static Predicate isFile() {
        return FilePredicate.IS_FILE;
    }
    
    static {
        FILE_TREE_TRAVERSER = new TreeTraverser() {
            public Iterable children(final File file) {
                if (file.isDirectory()) {
                    final File[] listFiles = file.listFiles();
                    if (listFiles != null) {
                        return Collections.unmodifiableList((List<?>)Arrays.asList((T[])listFiles));
                    }
                }
                return Collections.emptyList();
            }
            
            @Override
            public String toString() {
                return "Files.fileTreeTraverser()";
            }
            
            @Override
            public Iterable children(final Object o) {
                return this.children((File)o);
            }
        };
    }
    
    private enum FilePredicate implements Predicate
    {
        IS_DIRECTORY {
            public boolean apply(final File file) {
                return file.isDirectory();
            }
            
            @Override
            public String toString() {
                return "Files.isDirectory()";
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.apply((File)o);
            }
        }, 
        IS_FILE {
            public boolean apply(final File file) {
                return file.isFile();
            }
            
            @Override
            public String toString() {
                return "Files.isFile()";
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.apply((File)o);
            }
        };
        
        private static final FilePredicate[] $VALUES;
        
        private FilePredicate(final String s, final int n) {
        }
        
        FilePredicate(final String s, final int n, final Files$1 lineProcessor) {
            this(s, n);
        }
        
        static {
            $VALUES = new FilePredicate[] { FilePredicate.IS_DIRECTORY, FilePredicate.IS_FILE };
        }
    }
    
    private static final class FileByteSink extends ByteSink
    {
        private final File file;
        private final ImmutableSet modes;
        
        private FileByteSink(final File file, final FileWriteMode... array) {
            this.file = (File)Preconditions.checkNotNull(file);
            this.modes = ImmutableSet.copyOf(array);
        }
        
        @Override
        public FileOutputStream openStream() throws IOException {
            return new FileOutputStream(this.file, this.modes.contains(FileWriteMode.APPEND));
        }
        
        @Override
        public String toString() {
            return "Files.asByteSink(" + this.file + ", " + this.modes + ")";
        }
        
        @Override
        public OutputStream openStream() throws IOException {
            return this.openStream();
        }
        
        FileByteSink(final File file, final FileWriteMode[] array, final Files$1 lineProcessor) {
            this(file, array);
        }
    }
    
    private static final class FileByteSource extends ByteSource
    {
        private final File file;
        
        private FileByteSource(final File file) {
            this.file = (File)Preconditions.checkNotNull(file);
        }
        
        @Override
        public FileInputStream openStream() throws IOException {
            return new FileInputStream(this.file);
        }
        
        @Override
        public long size() throws IOException {
            if (!this.file.isFile()) {
                throw new FileNotFoundException(this.file.toString());
            }
            return this.file.length();
        }
        
        @Override
        public byte[] read() throws IOException {
            final Closer create = Closer.create();
            final FileInputStream fileInputStream = (FileInputStream)create.register(this.openStream());
            final byte[] file = Files.readFile(fileInputStream, fileInputStream.getChannel().size());
            create.close();
            return file;
        }
        
        @Override
        public String toString() {
            return "Files.asByteSource(" + this.file + ")";
        }
        
        @Override
        public InputStream openStream() throws IOException {
            return this.openStream();
        }
        
        FileByteSource(final File file, final Files$1 lineProcessor) {
            this(file);
        }
    }
}
