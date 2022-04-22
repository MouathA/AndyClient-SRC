package org.apache.commons.compress.archivers.sevenz;

import java.io.*;
import org.tukaani.xz.*;

class LZMA2Decoder extends CoderBase
{
    LZMA2Decoder() {
        super(new Class[] { LZMA2Options.class, Number.class });
    }
    
    @Override
    InputStream decode(final InputStream inputStream, final Coder coder, final byte[] array) throws IOException {
        return (InputStream)new LZMA2InputStream(inputStream, this.getDictionarySize(coder));
    }
    
    @Override
    OutputStream encode(final OutputStream outputStream, final Object o) throws IOException {
        return (OutputStream)this.getOptions(o).getOutputStream((FinishableOutputStream)new FinishableWrapperOutputStream(outputStream));
    }
    
    @Override
    byte[] getOptionsAsProperties(final Object o) {
        final int dictSize = this.getDictSize(o);
        final int numberOfLeadingZeros = Integer.numberOfLeadingZeros(dictSize);
        return new byte[] { (byte)((19 - numberOfLeadingZeros) * 2 + ((dictSize >>> 30 - numberOfLeadingZeros) - 2)) };
    }
    
    @Override
    Object getOptionsFromCoder(final Coder coder, final InputStream inputStream) {
        return this.getDictionarySize(coder);
    }
    
    private int getDictSize(final Object o) {
        if (o instanceof LZMA2Options) {
            return ((LZMA2Options)o).getDictSize();
        }
        return this.numberOptionOrDefault(o);
    }
    
    private int getDictionarySize(final Coder coder) throws IllegalArgumentException {
        final int n = 0xFF & coder.properties[0];
        if ((n & 0xFFFFFFC0) != 0x0) {
            throw new IllegalArgumentException("Unsupported LZMA2 property bits");
        }
        if (n > 40) {
            throw new IllegalArgumentException("Dictionary larger than 4GiB maximum size");
        }
        if (n == 40) {
            return -1;
        }
        return (0x2 | (n & 0x1)) << n / 2 + 11;
    }
    
    private LZMA2Options getOptions(final Object o) throws IOException {
        if (o instanceof LZMA2Options) {
            return (LZMA2Options)o;
        }
        final LZMA2Options lzma2Options = new LZMA2Options();
        lzma2Options.setDictSize(this.numberOptionOrDefault(o));
        return lzma2Options;
    }
    
    private int numberOptionOrDefault(final Object o) {
        return CoderBase.numberOptionOrDefault(o, 8388608);
    }
}
