package com.viaversion.viaversion.api.minecraft.nbt;

final class Tokens
{
    static final char COMPOUND_BEGIN = '{';
    static final char COMPOUND_END = '}';
    static final char COMPOUND_KEY_TERMINATOR = ':';
    static final char ARRAY_BEGIN = '[';
    static final char ARRAY_END = ']';
    static final char ARRAY_SIGNATURE_SEPARATOR = ';';
    static final char VALUE_SEPARATOR = ',';
    static final char SINGLE_QUOTE = '\'';
    static final char DOUBLE_QUOTE = '\"';
    static final char ESCAPE_MARKER = '\\';
    static final char TYPE_BYTE = 'b';
    static final char TYPE_SHORT = 's';
    static final char TYPE_INT = 'i';
    static final char TYPE_LONG = 'l';
    static final char TYPE_FLOAT = 'f';
    static final char TYPE_DOUBLE = 'd';
    static final String LITERAL_TRUE = "true";
    static final String LITERAL_FALSE = "false";
    static final String NEWLINE;
    static final char EOF = '\0';
    
    private Tokens() {
    }
    
    static boolean id(final char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '-' || c == '_' || c == '.' || c == '+';
    }
    
    static boolean numeric(final char c) {
        return (c >= '0' && c <= '9') || c == '+' || c == '-' || c == 'e' || c == 'E' || c == '.';
    }
    
    static {
        NEWLINE = System.getProperty("line.separator", "\n");
    }
}
