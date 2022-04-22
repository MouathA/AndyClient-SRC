package net.minecraft.client.resources;

public class Language implements Comparable
{
    private final String languageCode;
    private final String region;
    private final String name;
    private final boolean bidirectional;
    private static final String __OBFID;
    
    public Language(final String languageCode, final String region, final String name, final boolean bidirectional) {
        this.languageCode = languageCode;
        this.region = region;
        this.name = name;
        this.bidirectional = bidirectional;
    }
    
    public String getLanguageCode() {
        return this.languageCode;
    }
    
    public boolean isBidirectional() {
        return this.bidirectional;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s)", this.name, this.region);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof Language && this.languageCode.equals(((Language)o).languageCode));
    }
    
    @Override
    public int hashCode() {
        return this.languageCode.hashCode();
    }
    
    public int compareTo(final Language language) {
        return this.languageCode.compareTo(language.languageCode);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Language)o);
    }
    
    static {
        __OBFID = "CL_00001095";
    }
}
