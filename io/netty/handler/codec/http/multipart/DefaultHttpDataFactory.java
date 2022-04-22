package io.netty.handler.codec.http.multipart;

import io.netty.util.internal.*;
import io.netty.handler.codec.http.*;
import java.nio.charset.*;
import java.util.*;

public class DefaultHttpDataFactory implements HttpDataFactory
{
    public static final long MINSIZE = 16384L;
    private final boolean useDisk;
    private final boolean checkSize;
    private long minSize;
    private final Map requestFileDeleteMap;
    
    public DefaultHttpDataFactory() {
        this.requestFileDeleteMap = PlatformDependent.newConcurrentHashMap();
        this.useDisk = false;
        this.checkSize = true;
        this.minSize = 16384L;
    }
    
    public DefaultHttpDataFactory(final boolean useDisk) {
        this.requestFileDeleteMap = PlatformDependent.newConcurrentHashMap();
        this.useDisk = useDisk;
        this.checkSize = false;
    }
    
    public DefaultHttpDataFactory(final long minSize) {
        this.requestFileDeleteMap = PlatformDependent.newConcurrentHashMap();
        this.useDisk = false;
        this.checkSize = true;
        this.minSize = minSize;
    }
    
    private List getList(final HttpRequest httpRequest) {
        List list = this.requestFileDeleteMap.get(httpRequest);
        if (list == null) {
            list = new ArrayList();
            this.requestFileDeleteMap.put(httpRequest, list);
        }
        return list;
    }
    
    @Override
    public Attribute createAttribute(final HttpRequest httpRequest, final String s) {
        if (this.useDisk) {
            final DiskAttribute diskAttribute = new DiskAttribute(s);
            this.getList(httpRequest).add(diskAttribute);
            return diskAttribute;
        }
        if (this.checkSize) {
            final MixedAttribute mixedAttribute = new MixedAttribute(s, this.minSize);
            this.getList(httpRequest).add(mixedAttribute);
            return mixedAttribute;
        }
        return new MemoryAttribute(s);
    }
    
    @Override
    public Attribute createAttribute(final HttpRequest httpRequest, final String s, final String s2) {
        if (this.useDisk) {
            final DiskAttribute diskAttribute = new DiskAttribute(s, s2);
            this.getList(httpRequest).add(diskAttribute);
            return diskAttribute;
        }
        if (this.checkSize) {
            final MixedAttribute mixedAttribute = new MixedAttribute(s, s2, this.minSize);
            this.getList(httpRequest).add(mixedAttribute);
            return mixedAttribute;
        }
        return new MemoryAttribute(s, s2);
    }
    
    @Override
    public FileUpload createFileUpload(final HttpRequest httpRequest, final String s, final String s2, final String s3, final String s4, final Charset charset, final long n) {
        if (this.useDisk) {
            final DiskFileUpload diskFileUpload = new DiskFileUpload(s, s2, s3, s4, charset, n);
            this.getList(httpRequest).add(diskFileUpload);
            return diskFileUpload;
        }
        if (this.checkSize) {
            final MixedFileUpload mixedFileUpload = new MixedFileUpload(s, s2, s3, s4, charset, n, this.minSize);
            this.getList(httpRequest).add(mixedFileUpload);
            return mixedFileUpload;
        }
        return new MemoryFileUpload(s, s2, s3, s4, charset, n);
    }
    
    @Override
    public void removeHttpDataFromClean(final HttpRequest httpRequest, final InterfaceHttpData interfaceHttpData) {
        if (interfaceHttpData instanceof HttpData) {
            this.getList(httpRequest).remove(interfaceHttpData);
        }
    }
    
    @Override
    public void cleanRequestHttpDatas(final HttpRequest httpRequest) {
        final List<HttpData> list = this.requestFileDeleteMap.remove(httpRequest);
        if (list != null) {
            final Iterator<HttpData> iterator = list.iterator();
            while (iterator.hasNext()) {
                iterator.next().delete();
            }
            list.clear();
        }
    }
    
    @Override
    public void cleanAllHttpDatas() {
        final Iterator<Map.Entry<K, List>> iterator = this.requestFileDeleteMap.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<K, List> entry = iterator.next();
            iterator.remove();
            final List<HttpData> list = entry.getValue();
            if (list != null) {
                final Iterator<HttpData> iterator2 = list.iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().delete();
                }
                list.clear();
            }
        }
    }
}
