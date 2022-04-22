package com.viaversion.viaversion.libs.javassist;

import java.net.*;
import java.io.*;

public class ByteArrayClassPath implements ClassPath
{
    protected String classname;
    protected byte[] classfile;
    
    public ByteArrayClassPath(final String classname, final byte[] classfile) {
        this.classname = classname;
        this.classfile = classfile;
    }
    
    @Override
    public String toString() {
        return "byte[]:" + this.classname;
    }
    
    @Override
    public InputStream openClassfile(final String s) {
        if (this.classname.equals(s)) {
            return new ByteArrayInputStream(this.classfile);
        }
        return null;
    }
    
    @Override
    public URL find(final String s) {
        if (this.classname.equals(s)) {
            return new URL(null, "file:/ByteArrayClassPath/" + (s.replace('.', '/') + ".class"), new BytecodeURLStreamHandler(null));
        }
        return null;
    }
    
    private class BytecodeURLConnection extends URLConnection
    {
        final ByteArrayClassPath this$0;
        
        protected BytecodeURLConnection(final ByteArrayClassPath this$0, final URL url) {
            this.this$0 = this$0;
            super(url);
        }
        
        @Override
        public void connect() throws IOException {
        }
        
        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(this.this$0.classfile);
        }
        
        @Override
        public int getContentLength() {
            return this.this$0.classfile.length;
        }
    }
    
    private class BytecodeURLStreamHandler extends URLStreamHandler
    {
        final ByteArrayClassPath this$0;
        
        private BytecodeURLStreamHandler(final ByteArrayClassPath this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        protected URLConnection openConnection(final URL url) {
            return this.this$0.new BytecodeURLConnection(url);
        }
        
        BytecodeURLStreamHandler(final ByteArrayClassPath byteArrayClassPath, final ByteArrayClassPath$1 object) {
            this(byteArrayClassPath);
        }
    }
}
