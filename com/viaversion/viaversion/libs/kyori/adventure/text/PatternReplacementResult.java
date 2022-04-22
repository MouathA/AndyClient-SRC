package com.viaversion.viaversion.libs.kyori.adventure.text;

public enum PatternReplacementResult
{
    REPLACE, 
    CONTINUE, 
    STOP;
    
    private static final PatternReplacementResult[] $VALUES;
    
    static {
        $VALUES = new PatternReplacementResult[] { PatternReplacementResult.REPLACE, PatternReplacementResult.CONTINUE, PatternReplacementResult.STOP };
    }
}
