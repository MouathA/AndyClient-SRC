package net.minecraft.realms;

import net.minecraft.util.*;

public class RealmsSharedConstants
{
    public static String VERSION_STRING;
    public static char[] ILLEGAL_FILE_CHARACTERS;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001866";
        RealmsSharedConstants.VERSION_STRING = "1.8";
        RealmsSharedConstants.ILLEGAL_FILE_CHARACTERS = ChatAllowedCharacters.allowedCharactersArray;
    }
}
