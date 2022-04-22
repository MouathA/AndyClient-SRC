package org.apache.commons.io.filefilter;

import org.apache.commons.io.*;
import java.io.*;
import java.util.*;

public class FileFilterUtils
{
    private static final IOFileFilter cvsFilter;
    private static final IOFileFilter svnFilter;
    
    public static File[] filter(final IOFileFilter ioFileFilter, final File... array) {
        if (ioFileFilter == null) {
            throw new IllegalArgumentException("file filter is null");
        }
        if (array == null) {
            return new File[0];
        }
        final ArrayList<File> list = new ArrayList<File>();
        while (0 < array.length) {
            final File file = array[0];
            if (file == null) {
                throw new IllegalArgumentException("file array contains null");
            }
            if (ioFileFilter.accept(file)) {
                list.add(file);
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new File[list.size()]);
    }
    
    public static File[] filter(final IOFileFilter ioFileFilter, final Iterable iterable) {
        final List filterList = filterList(ioFileFilter, iterable);
        return filterList.toArray(new File[filterList.size()]);
    }
    
    public static List filterList(final IOFileFilter ioFileFilter, final Iterable iterable) {
        return (List)filter(ioFileFilter, iterable, new ArrayList());
    }
    
    public static List filterList(final IOFileFilter ioFileFilter, final File... array) {
        return Arrays.asList(filter(ioFileFilter, array));
    }
    
    public static Set filterSet(final IOFileFilter ioFileFilter, final File... array) {
        return new HashSet(Arrays.asList(filter(ioFileFilter, array)));
    }
    
    public static Set filterSet(final IOFileFilter ioFileFilter, final Iterable iterable) {
        return (Set)filter(ioFileFilter, iterable, new HashSet());
    }
    
    private static Collection filter(final IOFileFilter ioFileFilter, final Iterable iterable, final Collection collection) {
        if (ioFileFilter == null) {
            throw new IllegalArgumentException("file filter is null");
        }
        if (iterable != null) {
            for (final File file : iterable) {
                if (file == null) {
                    throw new IllegalArgumentException("file collection contains null");
                }
                if (!ioFileFilter.accept(file)) {
                    continue;
                }
                collection.add(file);
            }
        }
        return collection;
    }
    
    public static IOFileFilter prefixFileFilter(final String s) {
        return new PrefixFileFilter(s);
    }
    
    public static IOFileFilter prefixFileFilter(final String s, final IOCase ioCase) {
        return new PrefixFileFilter(s, ioCase);
    }
    
    public static IOFileFilter suffixFileFilter(final String s) {
        return new SuffixFileFilter(s);
    }
    
    public static IOFileFilter suffixFileFilter(final String s, final IOCase ioCase) {
        return new SuffixFileFilter(s, ioCase);
    }
    
    public static IOFileFilter nameFileFilter(final String s) {
        return new NameFileFilter(s);
    }
    
    public static IOFileFilter nameFileFilter(final String s, final IOCase ioCase) {
        return new NameFileFilter(s, ioCase);
    }
    
    public static IOFileFilter directoryFileFilter() {
        return DirectoryFileFilter.DIRECTORY;
    }
    
    public static IOFileFilter fileFileFilter() {
        return FileFileFilter.FILE;
    }
    
    @Deprecated
    public static IOFileFilter andFileFilter(final IOFileFilter ioFileFilter, final IOFileFilter ioFileFilter2) {
        return new AndFileFilter(ioFileFilter, ioFileFilter2);
    }
    
    @Deprecated
    public static IOFileFilter orFileFilter(final IOFileFilter ioFileFilter, final IOFileFilter ioFileFilter2) {
        return new OrFileFilter(ioFileFilter, ioFileFilter2);
    }
    
    public static IOFileFilter and(final IOFileFilter... array) {
        return new AndFileFilter(toList(array));
    }
    
    public static IOFileFilter or(final IOFileFilter... array) {
        return new OrFileFilter(toList(array));
    }
    
    public static List toList(final IOFileFilter... array) {
        if (array == null) {
            throw new IllegalArgumentException("The filters must not be null");
        }
        final ArrayList<IOFileFilter> list = new ArrayList<IOFileFilter>(array.length);
        while (0 < array.length) {
            if (array[0] == null) {
                throw new IllegalArgumentException("The filter[" + 0 + "] is null");
            }
            list.add(array[0]);
            int n = 0;
            ++n;
        }
        return list;
    }
    
    public static IOFileFilter notFileFilter(final IOFileFilter ioFileFilter) {
        return new NotFileFilter(ioFileFilter);
    }
    
    public static IOFileFilter trueFileFilter() {
        return TrueFileFilter.TRUE;
    }
    
    public static IOFileFilter falseFileFilter() {
        return FalseFileFilter.FALSE;
    }
    
    public static IOFileFilter asFileFilter(final FileFilter fileFilter) {
        return new DelegateFileFilter(fileFilter);
    }
    
    public static IOFileFilter asFileFilter(final FilenameFilter filenameFilter) {
        return new DelegateFileFilter(filenameFilter);
    }
    
    public static IOFileFilter ageFileFilter(final long n) {
        return new AgeFileFilter(n);
    }
    
    public static IOFileFilter ageFileFilter(final long n, final boolean b) {
        return new AgeFileFilter(n, b);
    }
    
    public static IOFileFilter ageFileFilter(final Date date) {
        return new AgeFileFilter(date);
    }
    
    public static IOFileFilter ageFileFilter(final Date date, final boolean b) {
        return new AgeFileFilter(date, b);
    }
    
    public static IOFileFilter ageFileFilter(final File file) {
        return new AgeFileFilter(file);
    }
    
    public static IOFileFilter ageFileFilter(final File file, final boolean b) {
        return new AgeFileFilter(file, b);
    }
    
    public static IOFileFilter sizeFileFilter(final long n) {
        return new SizeFileFilter(n);
    }
    
    public static IOFileFilter sizeFileFilter(final long n, final boolean b) {
        return new SizeFileFilter(n, b);
    }
    
    public static IOFileFilter sizeRangeFileFilter(final long n, final long n2) {
        return new AndFileFilter(new SizeFileFilter(n, true), new SizeFileFilter(n2 + 1L, false));
    }
    
    public static IOFileFilter magicNumberFileFilter(final String s) {
        return new MagicNumberFileFilter(s);
    }
    
    public static IOFileFilter magicNumberFileFilter(final String s, final long n) {
        return new MagicNumberFileFilter(s, n);
    }
    
    public static IOFileFilter magicNumberFileFilter(final byte[] array) {
        return new MagicNumberFileFilter(array);
    }
    
    public static IOFileFilter magicNumberFileFilter(final byte[] array, final long n) {
        return new MagicNumberFileFilter(array, n);
    }
    
    public static IOFileFilter makeCVSAware(final IOFileFilter ioFileFilter) {
        if (ioFileFilter == null) {
            return FileFilterUtils.cvsFilter;
        }
        return and(ioFileFilter, FileFilterUtils.cvsFilter);
    }
    
    public static IOFileFilter makeSVNAware(final IOFileFilter ioFileFilter) {
        if (ioFileFilter == null) {
            return FileFilterUtils.svnFilter;
        }
        return and(ioFileFilter, FileFilterUtils.svnFilter);
    }
    
    public static IOFileFilter makeDirectoryOnly(final IOFileFilter ioFileFilter) {
        if (ioFileFilter == null) {
            return DirectoryFileFilter.DIRECTORY;
        }
        return new AndFileFilter(DirectoryFileFilter.DIRECTORY, ioFileFilter);
    }
    
    public static IOFileFilter makeFileOnly(final IOFileFilter ioFileFilter) {
        if (ioFileFilter == null) {
            return FileFileFilter.FILE;
        }
        return new AndFileFilter(FileFileFilter.FILE, ioFileFilter);
    }
    
    static {
        cvsFilter = notFileFilter(and(directoryFileFilter(), nameFileFilter("CVS")));
        svnFilter = notFileFilter(and(directoryFileFilter(), nameFileFilter(".svn")));
    }
}
