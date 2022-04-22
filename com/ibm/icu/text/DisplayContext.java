package com.ibm.icu.text;

public enum DisplayContext
{
    STANDARD_NAMES("STANDARD_NAMES", 0, Type.DIALECT_HANDLING, 0), 
    DIALECT_NAMES("DIALECT_NAMES", 1, Type.DIALECT_HANDLING, 1), 
    CAPITALIZATION_NONE("CAPITALIZATION_NONE", 2, Type.CAPITALIZATION, 0), 
    CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE("CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE", 3, Type.CAPITALIZATION, 1), 
    CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE("CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE", 4, Type.CAPITALIZATION, 2), 
    CAPITALIZATION_FOR_UI_LIST_OR_MENU("CAPITALIZATION_FOR_UI_LIST_OR_MENU", 5, Type.CAPITALIZATION, 3), 
    CAPITALIZATION_FOR_STANDALONE("CAPITALIZATION_FOR_STANDALONE", 6, Type.CAPITALIZATION, 4);
    
    private final Type type;
    private final int value;
    private static final DisplayContext[] $VALUES;
    
    private DisplayContext(final String s, final int n, final Type type, final int value) {
        this.type = type;
        this.value = value;
    }
    
    public Type type() {
        return this.type;
    }
    
    public int value() {
        return this.value;
    }
    
    static {
        $VALUES = new DisplayContext[] { DisplayContext.STANDARD_NAMES, DisplayContext.DIALECT_NAMES, DisplayContext.CAPITALIZATION_NONE, DisplayContext.CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE, DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE, DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU, DisplayContext.CAPITALIZATION_FOR_STANDALONE };
    }
    
    public enum Type
    {
        DIALECT_HANDLING("DIALECT_HANDLING", 0), 
        CAPITALIZATION("CAPITALIZATION", 1);
        
        private static final Type[] $VALUES;
        
        private Type(final String s, final int n) {
        }
        
        static {
            $VALUES = new Type[] { Type.DIALECT_HANDLING, Type.CAPITALIZATION };
        }
    }
}
