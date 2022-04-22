package optifine;

import net.minecraft.block.state.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.biome.*;

public class Matches
{
    public static boolean block(final BlockStateBase blockStateBase, final MatchBlock[] array) {
        if (array == null) {
            return true;
        }
        while (0 < array.length) {
            if (array[0].matches(blockStateBase)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean block(final int n, final int n2, final MatchBlock[] array) {
        if (array == null) {
            return true;
        }
        while (0 < array.length) {
            if (array[0].matches(n, n2)) {
                return true;
            }
            int n3 = 0;
            ++n3;
        }
        return false;
    }
    
    public static boolean blockId(final int n, final MatchBlock[] array) {
        if (array == null) {
            return true;
        }
        while (0 < array.length) {
            if (array[0].getBlockId() == n) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    public static boolean metadata(final int n, final int[] array) {
        if (array == null) {
            return true;
        }
        while (0 < array.length) {
            if (array[0] == n) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    public static boolean sprite(final TextureAtlasSprite textureAtlasSprite, final TextureAtlasSprite[] array) {
        if (array == null) {
            return true;
        }
        while (0 < array.length) {
            if (array[0] == textureAtlasSprite) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean biome(final BiomeGenBase biomeGenBase, final BiomeGenBase[] array) {
        if (array == null) {
            return true;
        }
        while (0 < array.length) {
            if (array[0] == biomeGenBase) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
}
