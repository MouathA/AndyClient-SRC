package org.lwjgl;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;

abstract class J2SESysImplementation extends DefaultSysImplementation
{
    @Override
    public long getTime() {
        return System.currentTimeMillis();
    }
    
    @Override
    public void alert(final String s, final String s2) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JOptionPane.showMessageDialog(null, s2, s, 2);
    }
    
    @Override
    public String getClipboard() {
        final Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String)contents.getTransferData(DataFlavor.stringFlavor);
        }
        return null;
    }
}
