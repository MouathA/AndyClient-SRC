package optifine;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import java.util.*;
import javax.vecmath.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.renderer.block.model.*;

public class BlockModelUtils
{
    public static IBakedModel makeModelCube(final String s, final int n) {
        return makeModelCube(Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(s), n);
    }
    
    public static IBakedModel makeModelCube(final TextureAtlasSprite textureAtlasSprite, final int n) {
        final ArrayList list = new ArrayList();
        final EnumFacing[] values = EnumFacing.VALUES;
        final ArrayList list2 = new ArrayList<ArrayList<BakedQuad>>(values.length);
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            final ArrayList<BakedQuad> list3 = new ArrayList<BakedQuad>();
            list3.add(makeBakedQuad(enumFacing, textureAtlasSprite, n));
            list2.add(list3);
            int n2 = 0;
            ++n2;
        }
        return new SimpleBakedModel(list, list2, true, true, textureAtlasSprite, ItemCameraTransforms.field_178357_a);
    }
    
    private static BakedQuad makeBakedQuad(final EnumFacing enumFacing, final TextureAtlasSprite textureAtlasSprite, final int n) {
        return new FaceBakery().func_178414_a(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(16.0f, 16.0f, 16.0f), new BlockPartFace(enumFacing, n, "#" + enumFacing.getName(), new BlockFaceUV(new float[] { 0.0f, 0.0f, 16.0f, 16.0f }, 0)), textureAtlasSprite, enumFacing, ModelRotation.X0_Y0, null, false, true);
    }
}
