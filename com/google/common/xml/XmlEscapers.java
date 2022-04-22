package com.google.common.xml;

import com.google.common.annotations.*;
import com.google.common.escape.*;

@Beta
@GwtCompatible
public class XmlEscapers
{
    private static final char MIN_ASCII_CONTROL_CHAR = '\0';
    private static final char MAX_ASCII_CONTROL_CHAR = '\u001f';
    private static final Escaper XML_ESCAPER;
    private static final Escaper XML_CONTENT_ESCAPER;
    private static final Escaper XML_ATTRIBUTE_ESCAPER;
    
    private XmlEscapers() {
    }
    
    public static Escaper xmlContentEscaper() {
        return XmlEscapers.XML_CONTENT_ESCAPER;
    }
    
    public static Escaper xmlAttributeEscaper() {
        return XmlEscapers.XML_ATTRIBUTE_ESCAPER;
    }
    
    static {
        final Escapers.Builder builder = Escapers.builder();
        builder.setSafeRange('\0', '\uffff');
        builder.setUnsafeReplacement("");
        while (true) {
            builder.addEscape('\0', "");
            final char c = 1;
        }
    }
}
