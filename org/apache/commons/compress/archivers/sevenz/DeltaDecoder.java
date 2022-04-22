package org.apache.commons.compress.archivers.sevenz;

import java.io.*;
import org.tukaani.xz.*;

class DeltaDecoder extends CoderBase
{
    DeltaDecoder() {
        super(new Class[] { Number.class });
    }
    
    @Override
    InputStream decode(final InputStream inputStream, final Coder coder, final byte[] array) throws IOException {
        return new DeltaOptions(this.getOptionsFromCoder(coder)).getInputStream(inputStream);
    }
    
    @Override
    OutputStream encode(final OutputStream outputStream, final Object o) throws IOException {
        return (OutputStream)new DeltaOptions(CoderBase.numberOptionOrDefault(o, 1)).getOutputStream((FinishableOutputStream)new FinishableWrapperOutputStream(outputStream));
    }
    
    @Override
    byte[] getOptionsAsProperties(final Object o) {
        return new byte[] { (byte)(CoderBase.numberOptionOrDefault(o, 1) - 1) };
    }
    
    @Override
    Object getOptionsFromCoder(final Coder coder, final InputStream inputStream) {
        return this.getOptionsFromCoder(coder);
    }
    
    private int getOptionsFromCoder(final Coder coder) {
        if (coder.properties == null || coder.properties.length == 0) {
            return 1;
        }
        return (0xFF & coder.properties[0]) + 1;
    }
}
