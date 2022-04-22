package net.minecraft.client.resources.data;

public class FontMetadataSection implements IMetadataSection
{
    private final float[] charWidths;
    private final float[] charLefts;
    private final float[] charSpacings;
    private static final String __OBFID;
    
    public FontMetadataSection(final float[] charWidths, final float[] charLefts, final float[] charSpacings) {
        this.charWidths = charWidths;
        this.charLefts = charLefts;
        this.charSpacings = charSpacings;
    }
    
    static {
        __OBFID = "CL_00001108";
    }
}
