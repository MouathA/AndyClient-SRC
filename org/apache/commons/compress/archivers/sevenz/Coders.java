package org.apache.commons.compress.archivers.sevenz;

import java.util.*;
import org.apache.commons.compress.compressors.bzip2.*;
import java.util.zip.*;
import java.io.*;
import org.tukaani.xz.*;

class Coders
{
    private static final Map CODER_MAP;
    
    static CoderBase findByMethod(final SevenZMethod sevenZMethod) {
        return Coders.CODER_MAP.get(sevenZMethod);
    }
    
    static InputStream addDecoder(final InputStream inputStream, final Coder coder, final byte[] array) throws IOException {
        final CoderBase byMethod = findByMethod(SevenZMethod.byId(coder.decompressionMethodId));
        if (byMethod == null) {
            throw new IOException("Unsupported compression method " + Arrays.toString(coder.decompressionMethodId));
        }
        return byMethod.decode(inputStream, coder, array);
    }
    
    static OutputStream addEncoder(final OutputStream outputStream, final SevenZMethod sevenZMethod, final Object o) throws IOException {
        final CoderBase byMethod = findByMethod(sevenZMethod);
        if (byMethod == null) {
            throw new IOException("Unsupported compression method " + sevenZMethod);
        }
        return byMethod.encode(outputStream, o);
    }
    
    static {
        CODER_MAP = new HashMap() {
            private static final long serialVersionUID = 1664829131806520867L;
            
            {
                this.put(SevenZMethod.COPY, new CopyDecoder());
                this.put(SevenZMethod.LZMA, new LZMADecoder());
                this.put(SevenZMethod.LZMA2, new LZMA2Decoder());
                this.put(SevenZMethod.DEFLATE, new DeflateDecoder());
                this.put(SevenZMethod.BZIP2, new BZIP2Decoder());
                this.put(SevenZMethod.AES256SHA256, new AES256SHA256Decoder());
                this.put(SevenZMethod.BCJ_X86_FILTER, new BCJDecoder((FilterOptions)new X86Options()));
                this.put(SevenZMethod.BCJ_PPC_FILTER, new BCJDecoder((FilterOptions)new PowerPCOptions()));
                this.put(SevenZMethod.BCJ_IA64_FILTER, new BCJDecoder((FilterOptions)new IA64Options()));
                this.put(SevenZMethod.BCJ_ARM_FILTER, new BCJDecoder((FilterOptions)new ARMOptions()));
                this.put(SevenZMethod.BCJ_ARM_THUMB_FILTER, new BCJDecoder((FilterOptions)new ARMThumbOptions()));
                this.put(SevenZMethod.BCJ_SPARC_FILTER, new BCJDecoder((FilterOptions)new SPARCOptions()));
                this.put(SevenZMethod.DELTA_FILTER, new DeltaDecoder());
            }
        };
    }
    
    private static class DummyByteAddingInputStream extends FilterInputStream
    {
        private boolean addDummyByte;
        
        private DummyByteAddingInputStream(final InputStream inputStream) {
            super(inputStream);
            this.addDummyByte = true;
        }
        
        @Override
        public int read() throws IOException {
            super.read();
            return 0;
        }
        
        @Override
        public int read(final byte[] array, final int n, final int n2) throws IOException {
            final int read = super.read(array, n, n2);
            if (read == -1 && this.addDummyByte) {
                this.addDummyByte = false;
                array[n] = 0;
                return 1;
            }
            return read;
        }
        
        DummyByteAddingInputStream(final InputStream inputStream, final Coders$1 hashMap) {
            this(inputStream);
        }
    }
    
    static class BZIP2Decoder extends CoderBase
    {
        BZIP2Decoder() {
            super(new Class[] { Number.class });
        }
        
        @Override
        InputStream decode(final InputStream inputStream, final Coder coder, final byte[] array) throws IOException {
            return new BZip2CompressorInputStream(inputStream);
        }
        
        @Override
        OutputStream encode(final OutputStream outputStream, final Object o) throws IOException {
            return new BZip2CompressorOutputStream(outputStream, CoderBase.numberOptionOrDefault(o, 9));
        }
    }
    
    static class DeflateDecoder extends CoderBase
    {
        DeflateDecoder() {
            super(new Class[] { Number.class });
        }
        
        @Override
        InputStream decode(final InputStream inputStream, final Coder coder, final byte[] array) throws IOException {
            return new InflaterInputStream(new DummyByteAddingInputStream(inputStream, null), new Inflater(true));
        }
        
        @Override
        OutputStream encode(final OutputStream outputStream, final Object o) {
            return new DeflaterOutputStream(outputStream, new Deflater(CoderBase.numberOptionOrDefault(o, 9), true));
        }
    }
    
    static class BCJDecoder extends CoderBase
    {
        private final FilterOptions opts;
        
        BCJDecoder(final FilterOptions opts) {
            super(new Class[0]);
            this.opts = opts;
        }
        
        @Override
        InputStream decode(final InputStream inputStream, final Coder coder, final byte[] array) throws IOException {
            return this.opts.getInputStream(inputStream);
        }
        
        @Override
        OutputStream encode(final OutputStream outputStream, final Object o) {
            return new FilterOutputStream((OutputStream)this.opts.getOutputStream((FinishableOutputStream)new FinishableWrapperOutputStream(outputStream))) {
                final BCJDecoder this$0;
                
                @Override
                public void flush() {
                }
            };
        }
    }
    
    static class LZMADecoder extends CoderBase
    {
        LZMADecoder() {
            super(new Class[0]);
        }
        
        @Override
        InputStream decode(final InputStream inputStream, final Coder coder, final byte[] array) throws IOException {
            final byte b = coder.properties[0];
            long n = coder.properties[1];
            while (1 < 4) {
                n |= ((long)coder.properties[2] & 0xFFL) << 8;
                int n2 = 0;
                ++n2;
            }
            if (n > 2147483632L) {
                throw new IOException("Dictionary larger than 4GiB maximum size");
            }
            return (InputStream)new LZMAInputStream(inputStream, -1L, b, (int)n);
        }
    }
    
    static class CopyDecoder extends CoderBase
    {
        CopyDecoder() {
            super(new Class[0]);
        }
        
        @Override
        InputStream decode(final InputStream inputStream, final Coder coder, final byte[] array) throws IOException {
            return inputStream;
        }
        
        @Override
        OutputStream encode(final OutputStream outputStream, final Object o) {
            return outputStream;
        }
    }
}
