package com.viaversion.viaversion.api.minecraft.nbt;

import java.io.*;

class StringTagParseException extends IOException
{
    private static final long serialVersionUID = -3001637554903912905L;
    private final CharSequence buffer;
    private final int position;
    
    public StringTagParseException(final String s, final CharSequence buffer, final int position) {
        super(s);
        this.buffer = buffer;
        this.position = position;
    }
    
    @Override
    public String getMessage() {
        return super.getMessage() + "(at position " + this.position + ")";
    }
}
