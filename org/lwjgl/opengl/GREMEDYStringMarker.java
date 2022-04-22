package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class GREMEDYStringMarker
{
    private GREMEDYStringMarker() {
    }
    
    public static void glStringMarkerGREMEDY(final ByteBuffer byteBuffer) {
        final long glStringMarkerGREMEDY = GLContext.getCapabilities().glStringMarkerGREMEDY;
        BufferChecks.checkFunctionAddress(glStringMarkerGREMEDY);
        BufferChecks.checkDirect(byteBuffer);
        nglStringMarkerGREMEDY(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glStringMarkerGREMEDY);
    }
    
    static native void nglStringMarkerGREMEDY(final int p0, final long p1, final long p2);
    
    public static void glStringMarkerGREMEDY(final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glStringMarkerGREMEDY = capabilities.glStringMarkerGREMEDY;
        BufferChecks.checkFunctionAddress(glStringMarkerGREMEDY);
        nglStringMarkerGREMEDY(charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glStringMarkerGREMEDY);
    }
}
