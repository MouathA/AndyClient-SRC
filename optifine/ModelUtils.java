package optifine;

import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import java.util.*;
import net.minecraft.client.resources.model.*;

public class ModelUtils
{
    public static void dbgModel(final IBakedModel bakedModel) {
        if (bakedModel != null) {
            Config.dbg("Model: " + bakedModel + ", ao: " + bakedModel.isGui3d() + ", gui3d: " + bakedModel.isAmbientOcclusionEnabled() + ", builtIn: " + bakedModel.isBuiltInRenderer() + ", particle: " + bakedModel.getTexture());
            final EnumFacing[] values = EnumFacing.VALUES;
            while (0 < values.length) {
                final EnumFacing enumFacing = values[0];
                dbgQuads(enumFacing.getName(), bakedModel.func_177551_a(enumFacing), "  ");
                int n = 0;
                ++n;
            }
            dbgQuads("General", bakedModel.func_177550_a(), "  ");
        }
    }
    
    private static void dbgQuads(final String s, final List list, final String s2) {
        final Iterator<BakedQuad> iterator = list.iterator();
        while (iterator.hasNext()) {
            dbgQuad(s, iterator.next(), s2);
        }
    }
    
    public static void dbgQuad(final String s, final BakedQuad bakedQuad, final String s2) {
        Config.dbg(String.valueOf(s2) + "Quad: " + bakedQuad.getClass().getName() + ", type: " + s + ", face: " + bakedQuad.getFace() + ", tint: " + bakedQuad.func_178211_c() + ", sprite: " + bakedQuad.getSprite());
        dbgVertexData(bakedQuad.func_178209_a(), "  " + s2);
    }
    
    public static void dbgVertexData(final int[] array, final String s) {
        Config.dbg(String.valueOf(s) + "Length: " + array.length + ", step: " + array.length / 4);
    }
    
    public static IBakedModel duplicateModel(final IBakedModel bakedModel) {
        final List duplicateQuadList = duplicateQuadList(bakedModel.func_177550_a());
        final EnumFacing[] values = EnumFacing.VALUES;
        final ArrayList<List> list = new ArrayList<List>();
        while (0 < values.length) {
            list.add(duplicateQuadList(bakedModel.func_177551_a(values[0])));
            int n = 0;
            ++n;
        }
        return new SimpleBakedModel(duplicateQuadList, list, bakedModel.isGui3d(), bakedModel.isAmbientOcclusionEnabled(), bakedModel.getTexture(), bakedModel.getItemCameraTransforms());
    }
    
    public static List duplicateQuadList(final List list) {
        final ArrayList<BakedQuad> list2 = new ArrayList<BakedQuad>();
        final Iterator<BakedQuad> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.add(duplicateQuad(iterator.next()));
        }
        return list2;
    }
    
    public static BakedQuad duplicateQuad(final BakedQuad bakedQuad) {
        return new BakedQuad(bakedQuad.func_178209_a().clone(), bakedQuad.func_178211_c(), bakedQuad.getFace(), bakedQuad.getSprite());
    }
}
