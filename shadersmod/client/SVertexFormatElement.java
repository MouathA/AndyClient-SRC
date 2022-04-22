package shadersmod.client;

import net.minecraft.client.renderer.vertex.*;

public class SVertexFormatElement extends VertexFormatElement
{
    int sUsage;
    
    public SVertexFormatElement(final int sUsage, final EnumType enumType, final int n) {
        super(0, enumType, EnumUseage.PADDING, n);
        this.sUsage = sUsage;
    }
}
