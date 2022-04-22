package org.apache.commons.compress.archivers.zip;

public class UnicodePathExtraField extends AbstractUnicodeExtraField
{
    public static final ZipShort UPATH_ID;
    
    public UnicodePathExtraField() {
    }
    
    public UnicodePathExtraField(final String s, final byte[] array, final int n, final int n2) {
        super(s, array, n, n2);
    }
    
    public UnicodePathExtraField(final String s, final byte[] array) {
        super(s, array);
    }
    
    public ZipShort getHeaderId() {
        return UnicodePathExtraField.UPATH_ID;
    }
    
    static {
        UPATH_ID = new ZipShort(28789);
    }
}
