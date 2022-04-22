package org.yaml.snakeyaml.reader;

import org.yaml.snakeyaml.error.*;

public class ReaderException extends YAMLException
{
    private static final long serialVersionUID = 8710781187529689083L;
    private final String name;
    private final int codePoint;
    private final int position;
    
    public ReaderException(final String name, final int position, final int codePoint, final String s) {
        super(s);
        this.name = name;
        this.codePoint = codePoint;
        this.position = position;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getCodePoint() {
        return this.codePoint;
    }
    
    public int getPosition() {
        return this.position;
    }
    
    @Override
    public String toString() {
        return "unacceptable code point '" + new String(Character.toChars(this.codePoint)) + "' (0x" + Integer.toHexString(this.codePoint).toUpperCase() + ") " + this.getMessage() + "\nin \"" + this.name + "\", position " + this.position;
    }
}
