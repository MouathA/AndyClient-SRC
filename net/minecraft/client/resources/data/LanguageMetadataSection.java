package net.minecraft.client.resources.data;

import java.util.*;

public class LanguageMetadataSection implements IMetadataSection
{
    private final Collection languages;
    private static final String __OBFID;
    
    public LanguageMetadataSection(final Collection languages) {
        this.languages = languages;
    }
    
    public Collection getLanguages() {
        return this.languages;
    }
    
    static {
        __OBFID = "CL_00001110";
    }
}
