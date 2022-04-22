package optifine;

import java.util.*;
import java.io.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;

public class NaturalTextures
{
    private static NaturalProperties[] propertiesByIndex;
    
    static {
        NaturalTextures.propertiesByIndex = new NaturalProperties[0];
    }
    
    public static void update() {
        NaturalTextures.propertiesByIndex = new NaturalProperties[0];
        if (Config.isNaturalTextures()) {
            final String s = "optifine/natural.properties";
            final ResourceLocation resourceLocation = new ResourceLocation(s);
            if (!Config.hasResource(resourceLocation)) {
                Config.dbg("NaturalTextures: configuration \"" + s + "\" not found");
                return;
            }
            final boolean fromDefaultResourcePack = Config.isFromDefaultResourcePack(resourceLocation);
            final InputStream resourceStream = Config.getResourceStream(resourceLocation);
            final ArrayList<NaturalProperties> list = new ArrayList<NaturalProperties>(256);
            final String inputStream = Config.readInputStream(resourceStream);
            resourceStream.close();
            final String[] tokenize = Config.tokenize(inputStream, "\n\r");
            if (fromDefaultResourcePack) {
                Config.dbg("Natural Textures: Parsing default configuration \"" + s + "\"");
                Config.dbg("Natural Textures: Valid only for textures from default resource pack");
            }
            else {
                Config.dbg("Natural Textures: Parsing configuration \"" + s + "\"");
            }
            final TextureMap textureMapBlocks = TextureUtils.getTextureMapBlocks();
            while (0 < tokenize.length) {
                final String trim = tokenize[0].trim();
                if (!trim.startsWith("#")) {
                    final String[] tokenize2 = Config.tokenize(trim, "=");
                    if (tokenize2.length != 2) {
                        Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + trim);
                    }
                    else {
                        final String trim2 = tokenize2[0].trim();
                        final String trim3 = tokenize2[1].trim();
                        final TextureAtlasSprite spriteSafe = textureMapBlocks.getSpriteSafe("minecraft:blocks/" + trim2);
                        if (spriteSafe == null) {
                            Config.warn("Natural Textures: Texture not found: \"" + s + "\" line: " + trim);
                        }
                        else {
                            final int indexInMap = spriteSafe.getIndexInMap();
                            if (indexInMap < 0) {
                                Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + trim);
                            }
                            else {
                                if (fromDefaultResourcePack && !Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + trim2 + ".png"))) {
                                    return;
                                }
                                final NaturalProperties naturalProperties = new NaturalProperties(trim3);
                                if (naturalProperties.isValid()) {
                                    while (list.size() <= indexInMap) {
                                        list.add(null);
                                    }
                                    list.set(indexInMap, naturalProperties);
                                    Config.dbg("NaturalTextures: " + trim2 + " = " + trim3);
                                }
                            }
                        }
                    }
                }
                int n = 0;
                ++n;
            }
            NaturalTextures.propertiesByIndex = list.toArray(new NaturalProperties[list.size()]);
        }
    }
    
    public static BakedQuad getNaturalTexture(final BlockPos blockPos, final BakedQuad bakedQuad) {
        final TextureAtlasSprite sprite = bakedQuad.getSprite();
        if (sprite == null) {
            return bakedQuad;
        }
        final NaturalProperties naturalProperties = getNaturalProperties(sprite);
        if (naturalProperties == null) {
            return bakedQuad;
        }
        final int random = Config.getRandom(blockPos, ConnectedTextures.getSide(bakedQuad.getFace()));
        if (naturalProperties.rotation > 1) {}
        if (naturalProperties.rotation == 2) {}
        if (naturalProperties.flip) {
            final boolean b = (random & 0x4) != 0x0;
        }
        return naturalProperties.getQuad(bakedQuad, 0, false);
    }
    
    public static NaturalProperties getNaturalProperties(final TextureAtlasSprite textureAtlasSprite) {
        if (!(textureAtlasSprite instanceof TextureAtlasSprite)) {
            return null;
        }
        final int indexInMap = textureAtlasSprite.getIndexInMap();
        if (indexInMap >= 0 && indexInMap < NaturalTextures.propertiesByIndex.length) {
            return NaturalTextures.propertiesByIndex[indexInMap];
        }
        return null;
    }
}
