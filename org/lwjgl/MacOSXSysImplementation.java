package org.lwjgl;

import com.apple.eio.*;
import java.awt.*;

final class MacOSXSysImplementation extends J2SESysImplementation
{
    private static final int JNI_VERSION = 25;
    
    public int getRequiredJNIVersion() {
        return 25;
    }
    
    public boolean openURL(final String s) {
        FileManager.openURL(s);
        return true;
    }
    
    static {
        Toolkit.getDefaultToolkit();
    }
}
