package org.apache.commons.compress.archivers.zip;

public class UnicodeCommentExtraField extends AbstractUnicodeExtraField
{
    public static final ZipShort UCOM_ID;
    
    public UnicodeCommentExtraField() {
    }
    
    public UnicodeCommentExtraField(final String s, final byte[] array, final int n, final int n2) {
        super(s, array, n, n2);
    }
    
    public UnicodeCommentExtraField(final String s, final byte[] array) {
        super(s, array);
    }
    
    public ZipShort getHeaderId() {
        return UnicodeCommentExtraField.UCOM_ID;
    }
    
    static {
        UCOM_ID = new ZipShort(25461);
    }
}
