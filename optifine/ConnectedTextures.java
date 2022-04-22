package optifine;

import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.io.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class ConnectedTextures
{
    private static Map[] spriteQuadMaps;
    private static ConnectedProperties[][] blockProperties;
    private static ConnectedProperties[][] tileProperties;
    private static boolean multipass;
    private static final int Y_NEG_DOWN;
    private static final int Y_POS_UP;
    private static final int Z_NEG_NORTH;
    private static final int Z_POS_SOUTH;
    private static final int X_NEG_WEST;
    private static final int X_POS_EAST;
    private static final int Y_AXIS;
    private static final int Z_AXIS;
    private static final int X_AXIS;
    private static final String[] propSuffixes;
    private static final int[] ctmIndexes;
    public static final IBlockState AIR_DEFAULT_STATE;
    private static TextureAtlasSprite emptySprite;
    private static final String[] lIlIIIIllIIlIlll;
    private static String[] lIlIIIIllIlIlIIl;
    
    static {
        lllIllIlllllIlII();
        lllIllIlllllIIll();
        Z_AXIS = 1;
        Y_POS_UP = 1;
        Z_POS_SOUTH = 3;
        X_POS_EAST = 5;
        Y_AXIS = 0;
        X_NEG_WEST = 4;
        Y_NEG_DOWN = 0;
        Z_NEG_NORTH = 2;
        X_AXIS = 2;
        ConnectedTextures.spriteQuadMaps = null;
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        ConnectedTextures.multipass = false;
        propSuffixes = new String[] { ConnectedTextures.lIlIIIIllIIlIlll[0], ConnectedTextures.lIlIIIIllIIlIlll[1], ConnectedTextures.lIlIIIIllIIlIlll[2], ConnectedTextures.lIlIIIIllIIlIlll[3], ConnectedTextures.lIlIIIIllIIlIlll[4], ConnectedTextures.lIlIIIIllIIlIlll[5], ConnectedTextures.lIlIIIIllIIlIlll[6], ConnectedTextures.lIlIIIIllIIlIlll[7], ConnectedTextures.lIlIIIIllIIlIlll[8], ConnectedTextures.lIlIIIIllIIlIlll[9], ConnectedTextures.lIlIIIIllIIlIlll[10], ConnectedTextures.lIlIIIIllIIlIlll[11], ConnectedTextures.lIlIIIIllIIlIlll[12], ConnectedTextures.lIlIIIIllIIlIlll[13], ConnectedTextures.lIlIIIIllIIlIlll[14], ConnectedTextures.lIlIIIIllIIlIlll[15], ConnectedTextures.lIlIIIIllIIlIlll[16], ConnectedTextures.lIlIIIIllIIlIlll[17], ConnectedTextures.lIlIIIIllIIlIlll[18], ConnectedTextures.lIlIIIIllIIlIlll[19], ConnectedTextures.lIlIIIIllIIlIlll[20], ConnectedTextures.lIlIIIIllIIlIlll[21], ConnectedTextures.lIlIIIIllIIlIlll[22], ConnectedTextures.lIlIIIIllIIlIlll[23], ConnectedTextures.lIlIIIIllIIlIlll[24], ConnectedTextures.lIlIIIIllIIlIlll[25], ConnectedTextures.lIlIIIIllIIlIlll[26] };
        ctmIndexes = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 0, 0, 0, 0, 0 };
        AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
        ConnectedTextures.emptySprite = null;
    }
    
    public static synchronized BakedQuad getConnectedTexture(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final BakedQuad bakedQuad, final RenderEnv renderEnv) {
        final TextureAtlasSprite sprite = bakedQuad.getSprite();
        if (sprite == null) {
            return bakedQuad;
        }
        final Block block = blockState.getBlock();
        final EnumFacing face = bakedQuad.getFace();
        if (block instanceof BlockPane && sprite.getIconName().startsWith(ConnectedTextures.lIlIIIIllIIlIlll[27]) && blockAccess.getBlockState(blockPos.offset(bakedQuad.getFace())) == blockState) {
            return getQuad(ConnectedTextures.emptySprite, block, blockState, bakedQuad);
        }
        final TextureAtlasSprite connectedTextureMultiPass = getConnectedTextureMultiPass(blockAccess, blockState, blockPos, face, sprite, renderEnv);
        return (connectedTextureMultiPass == sprite) ? bakedQuad : getQuad(connectedTextureMultiPass, block, blockState, bakedQuad);
    }
    
    private static BakedQuad getQuad(final TextureAtlasSprite textureAtlasSprite, final Block block, final IBlockState blockState, final BakedQuad bakedQuad) {
        if (ConnectedTextures.spriteQuadMaps == null) {
            return bakedQuad;
        }
        final int indexInMap = textureAtlasSprite.getIndexInMap();
        if (indexInMap >= 0 && indexInMap < ConnectedTextures.spriteQuadMaps.length) {
            Map<Object, BakedQuad> map = (Map<Object, BakedQuad>)ConnectedTextures.spriteQuadMaps[indexInMap];
            if (map == null) {
                map = new IdentityHashMap<Object, BakedQuad>(1);
                ConnectedTextures.spriteQuadMaps[indexInMap] = map;
            }
            BakedQuad spriteQuad = map.get(bakedQuad);
            if (spriteQuad == null) {
                spriteQuad = makeSpriteQuad(bakedQuad, textureAtlasSprite);
                map.put(bakedQuad, spriteQuad);
            }
            return spriteQuad;
        }
        return bakedQuad;
    }
    
    private static BakedQuad makeSpriteQuad(final BakedQuad bakedQuad, final TextureAtlasSprite textureAtlasSprite) {
        final int[] array = bakedQuad.func_178209_a().clone();
        final TextureAtlasSprite sprite = bakedQuad.getSprite();
        for (int i = 0; i < 4; ++i) {
            fixVertex(array, i, sprite, textureAtlasSprite);
        }
        return new BakedQuad(array, bakedQuad.func_178211_c(), bakedQuad.getFace(), textureAtlasSprite);
    }
    
    private static void fixVertex(final int[] array, final int n, final TextureAtlasSprite textureAtlasSprite, final TextureAtlasSprite textureAtlasSprite2) {
        final int n2 = array.length / 4 * n;
        final float intBitsToFloat = Float.intBitsToFloat(array[n2 + 4]);
        final float intBitsToFloat2 = Float.intBitsToFloat(array[n2 + 4 + 1]);
        final double spriteU16 = textureAtlasSprite.getSpriteU16(intBitsToFloat);
        final double spriteV16 = textureAtlasSprite.getSpriteV16(intBitsToFloat2);
        array[n2 + 4] = Float.floatToRawIntBits(textureAtlasSprite2.getInterpolatedU(spriteU16));
        array[n2 + 4 + 1] = Float.floatToRawIntBits(textureAtlasSprite2.getInterpolatedV(spriteV16));
    }
    
    private static TextureAtlasSprite getConnectedTextureMultiPass(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final EnumFacing enumFacing, final TextureAtlasSprite textureAtlasSprite, final RenderEnv renderEnv) {
        final TextureAtlasSprite connectedTextureSingle = getConnectedTextureSingle(blockAccess, blockState, blockPos, enumFacing, textureAtlasSprite, true, renderEnv);
        if (!ConnectedTextures.multipass) {
            return connectedTextureSingle;
        }
        if (connectedTextureSingle == textureAtlasSprite) {
            return connectedTextureSingle;
        }
        TextureAtlasSprite textureAtlasSprite2 = connectedTextureSingle;
        for (int i = 0; i < 3; ++i) {
            final TextureAtlasSprite connectedTextureSingle2 = getConnectedTextureSingle(blockAccess, blockState, blockPos, enumFacing, textureAtlasSprite2, false, renderEnv);
            if (connectedTextureSingle2 == textureAtlasSprite2) {
                break;
            }
            textureAtlasSprite2 = connectedTextureSingle2;
        }
        return textureAtlasSprite2;
    }
    
    public static TextureAtlasSprite getConnectedTextureSingle(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final EnumFacing enumFacing, final TextureAtlasSprite textureAtlasSprite, final boolean b, final RenderEnv renderEnv) {
        blockState.getBlock();
        if (!(blockState instanceof BlockStateBase)) {
            return textureAtlasSprite;
        }
        final BlockStateBase blockStateBase = (BlockStateBase)blockState;
        if (ConnectedTextures.tileProperties != null) {
            final int indexInMap = textureAtlasSprite.getIndexInMap();
            if (indexInMap >= 0 && indexInMap < ConnectedTextures.tileProperties.length) {
                final ConnectedProperties[] array = ConnectedTextures.tileProperties[indexInMap];
                if (array != null) {
                    final int side = getSide(enumFacing);
                    for (int i = 0; i < array.length; ++i) {
                        final ConnectedProperties connectedProperties = array[i];
                        if (connectedProperties != null && connectedProperties.matchesBlockId(blockStateBase.getBlockId())) {
                            final TextureAtlasSprite connectedTexture = getConnectedTexture(connectedProperties, blockAccess, blockStateBase, blockPos, side, textureAtlasSprite, renderEnv);
                            if (connectedTexture != null) {
                                return connectedTexture;
                            }
                        }
                    }
                }
            }
        }
        if (ConnectedTextures.blockProperties != null && b) {
            final int blockId = renderEnv.getBlockId();
            if (blockId >= 0 && blockId < ConnectedTextures.blockProperties.length) {
                final ConnectedProperties[] array2 = ConnectedTextures.blockProperties[blockId];
                if (array2 != null) {
                    final int side2 = getSide(enumFacing);
                    for (int j = 0; j < array2.length; ++j) {
                        final ConnectedProperties connectedProperties2 = array2[j];
                        if (connectedProperties2 != null && connectedProperties2.matchesIcon(textureAtlasSprite)) {
                            final TextureAtlasSprite connectedTexture2 = getConnectedTexture(connectedProperties2, blockAccess, blockStateBase, blockPos, side2, textureAtlasSprite, renderEnv);
                            if (connectedTexture2 != null) {
                                return connectedTexture2;
                            }
                        }
                    }
                }
            }
        }
        return textureAtlasSprite;
    }
    
    public static int getSide(final EnumFacing enumFacing) {
        if (enumFacing == null) {
            return -1;
        }
        switch (NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 5;
            }
            case 4: {
                return 4;
            }
            case 5: {
                return 2;
            }
            case 6: {
                return 3;
            }
            default: {
                return -1;
            }
        }
    }
    
    private static EnumFacing getFacing(final int n) {
        switch (n) {
            case 0: {
                return EnumFacing.DOWN;
            }
            case 1: {
                return EnumFacing.UP;
            }
            case 2: {
                return EnumFacing.NORTH;
            }
            case 3: {
                return EnumFacing.SOUTH;
            }
            case 4: {
                return EnumFacing.WEST;
            }
            case 5: {
                return EnumFacing.EAST;
            }
            default: {
                return EnumFacing.UP;
            }
        }
    }
    
    private static TextureAtlasSprite getConnectedTexture(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final BlockStateBase blockStateBase, final BlockPos blockPos, final int n, final TextureAtlasSprite textureAtlasSprite, final RenderEnv renderEnv) {
        int n2 = 0;
        int metadata;
        final int n3 = metadata = blockStateBase.getMetadata();
        final Block block = blockStateBase.getBlock();
        if (block instanceof BlockRotatedPillar) {
            n2 = getWoodAxis(n, n3);
            if (connectedProperties.getMetadataMax() <= 3) {
                metadata = (n3 & 0x3);
            }
        }
        if (block instanceof BlockQuartz) {
            n2 = getQuartzAxis(n, n3);
            if (connectedProperties.getMetadataMax() <= 2 && metadata > 2) {
                metadata = 2;
            }
        }
        if (!connectedProperties.matchesBlock(blockStateBase.getBlockId(), metadata)) {
            return null;
        }
        if (n >= 0 && connectedProperties.faces != 63) {
            int fixSideByAxis = n;
            if (n2 != 0) {
                fixSideByAxis = fixSideByAxis(n, n2);
            }
            if ((1 << fixSideByAxis & connectedProperties.faces) == 0x0) {
                return null;
            }
        }
        final int y = blockPos.getY();
        if (y < connectedProperties.minHeight || y > connectedProperties.maxHeight) {
            return null;
        }
        if (connectedProperties.biomes != null && !connectedProperties.matchesBiome(blockAccess.getBiomeGenForCoords(blockPos))) {
            return null;
        }
        switch (connectedProperties.method) {
            case 1: {
                return getConnectedTextureCtm(connectedProperties, blockAccess, blockStateBase, blockPos, n2, n, textureAtlasSprite, n3, renderEnv);
            }
            case 2: {
                return getConnectedTextureHorizontal(connectedProperties, blockAccess, blockStateBase, blockPos, n2, n, textureAtlasSprite, n3);
            }
            case 3: {
                return getConnectedTextureTop(connectedProperties, blockAccess, blockStateBase, blockPos, n2, n, textureAtlasSprite, n3);
            }
            case 4: {
                return getConnectedTextureRandom(connectedProperties, blockPos, n);
            }
            case 5: {
                return getConnectedTextureRepeat(connectedProperties, blockPos, n);
            }
            case 6: {
                return getConnectedTextureVertical(connectedProperties, blockAccess, blockStateBase, blockPos, n2, n, textureAtlasSprite, n3);
            }
            case 7: {
                return getConnectedTextureFixed(connectedProperties);
            }
            case 8: {
                return getConnectedTextureHorizontalVertical(connectedProperties, blockAccess, blockStateBase, blockPos, n2, n, textureAtlasSprite, n3);
            }
            case 9: {
                return getConnectedTextureVerticalHorizontal(connectedProperties, blockAccess, blockStateBase, blockPos, n2, n, textureAtlasSprite, n3);
            }
            default: {
                return null;
            }
        }
    }
    
    private static int fixSideByAxis(final int n, final int n2) {
        switch (n2) {
            case 0: {
                return n;
            }
            case 1: {
                switch (n) {
                    case 0: {
                        return 2;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 1;
                    }
                    case 3: {
                        return 0;
                    }
                    default: {
                        return n;
                    }
                }
                break;
            }
            case 2: {
                switch (n) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 5;
                    }
                    default: {
                        return n;
                    }
                    case 4: {
                        return 1;
                    }
                    case 5: {
                        return 0;
                    }
                }
                break;
            }
            default: {
                return n;
            }
        }
    }
    
    private static int getWoodAxis(final int n, final int n2) {
        switch ((n2 & 0xC) >> 2) {
            case 1: {
                return 2;
            }
            case 2: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }
    
    private static int getQuartzAxis(final int n, final int n2) {
        switch (n2) {
            case 3: {
                return 2;
            }
            case 4: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }
    
    private static TextureAtlasSprite getConnectedTextureRandom(final ConnectedProperties connectedProperties, final BlockPos blockPos, final int n) {
        if (connectedProperties.tileIcons.length == 1) {
            return connectedProperties.tileIcons[0];
        }
        final int n2 = Config.getRandom(blockPos, n / connectedProperties.symmetry * connectedProperties.symmetry) & Integer.MAX_VALUE;
        int n3 = 0;
        if (connectedProperties.weights == null) {
            n3 = n2 % connectedProperties.tileIcons.length;
        }
        else {
            final int n4 = n2 % connectedProperties.sumAllWeights;
            final int[] sumWeights = connectedProperties.sumWeights;
            for (int i = 0; i < sumWeights.length; ++i) {
                if (n4 < sumWeights[i]) {
                    n3 = i;
                    break;
                }
            }
        }
        return connectedProperties.tileIcons[n3];
    }
    
    private static TextureAtlasSprite getConnectedTextureFixed(final ConnectedProperties connectedProperties) {
        return connectedProperties.tileIcons[0];
    }
    
    private static TextureAtlasSprite getConnectedTextureRepeat(final ConnectedProperties connectedProperties, final BlockPos blockPos, final int n) {
        if (connectedProperties.tileIcons.length == 1) {
            return connectedProperties.tileIcons[0];
        }
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        int n2 = 0;
        int n3 = 0;
        switch (n) {
            case 0: {
                n2 = x;
                n3 = z;
                break;
            }
            case 1: {
                n2 = x;
                n3 = z;
                break;
            }
            case 2: {
                n2 = -x - 1;
                n3 = -y;
                break;
            }
            case 3: {
                n2 = x;
                n3 = -y;
                break;
            }
            case 4: {
                n2 = z;
                n3 = -y;
                break;
            }
            case 5: {
                n2 = -z - 1;
                n3 = -y;
                break;
            }
        }
        int n4 = n2 % connectedProperties.width;
        int n5 = n3 % connectedProperties.height;
        if (n4 < 0) {
            n4 += connectedProperties.width;
        }
        if (n5 < 0) {
            n5 += connectedProperties.height;
        }
        return connectedProperties.tileIcons[n5 * connectedProperties.width + n4];
    }
    
    private static TextureAtlasSprite getConnectedTextureCtm(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3, final RenderEnv renderEnv) {
        final boolean[] borderFlags = renderEnv.getBorderFlags();
        switch (n2) {
            case 0: {
                borderFlags[0] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest(), n2, textureAtlasSprite, n3);
                borderFlags[1] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast(), n2, textureAtlasSprite, n3);
                borderFlags[2] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetNorth(), n2, textureAtlasSprite, n3);
                borderFlags[3] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetSouth(), n2, textureAtlasSprite, n3);
                break;
            }
            case 1: {
                borderFlags[0] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest(), n2, textureAtlasSprite, n3);
                borderFlags[1] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast(), n2, textureAtlasSprite, n3);
                borderFlags[2] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetSouth(), n2, textureAtlasSprite, n3);
                borderFlags[3] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetNorth(), n2, textureAtlasSprite, n3);
                break;
            }
            case 2: {
                borderFlags[0] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast(), n2, textureAtlasSprite, n3);
                borderFlags[1] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest(), n2, textureAtlasSprite, n3);
                borderFlags[2] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown(), n2, textureAtlasSprite, n3);
                borderFlags[3] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp(), n2, textureAtlasSprite, n3);
                break;
            }
            case 3: {
                borderFlags[0] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest(), n2, textureAtlasSprite, n3);
                borderFlags[1] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast(), n2, textureAtlasSprite, n3);
                borderFlags[2] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown(), n2, textureAtlasSprite, n3);
                borderFlags[3] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp(), n2, textureAtlasSprite, n3);
                break;
            }
            case 4: {
                borderFlags[0] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetNorth(), n2, textureAtlasSprite, n3);
                borderFlags[1] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetSouth(), n2, textureAtlasSprite, n3);
                borderFlags[2] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown(), n2, textureAtlasSprite, n3);
                borderFlags[3] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp(), n2, textureAtlasSprite, n3);
                break;
            }
            case 5: {
                borderFlags[0] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetSouth(), n2, textureAtlasSprite, n3);
                borderFlags[1] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetNorth(), n2, textureAtlasSprite, n3);
                borderFlags[2] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown(), n2, textureAtlasSprite, n3);
                borderFlags[3] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp(), n2, textureAtlasSprite, n3);
                break;
            }
        }
        int n4 = 0;
        if (borderFlags[0] & !borderFlags[1] & !borderFlags[2] & !borderFlags[3]) {
            n4 = 3;
        }
        else if (!borderFlags[0] & borderFlags[1] & !borderFlags[2] & !borderFlags[3]) {
            n4 = 1;
        }
        else if (!borderFlags[0] & !borderFlags[1] & borderFlags[2] & !borderFlags[3]) {
            n4 = 12;
        }
        else if (!borderFlags[0] & !borderFlags[1] & !borderFlags[2] & borderFlags[3]) {
            n4 = 36;
        }
        else if (borderFlags[0] & borderFlags[1] & !borderFlags[2] & !borderFlags[3]) {
            n4 = 2;
        }
        else if (!borderFlags[0] & !borderFlags[1] & borderFlags[2] & borderFlags[3]) {
            n4 = 24;
        }
        else if (borderFlags[0] & !borderFlags[1] & borderFlags[2] & !borderFlags[3]) {
            n4 = 15;
        }
        else if (borderFlags[0] & !borderFlags[1] & !borderFlags[2] & borderFlags[3]) {
            n4 = 39;
        }
        else if (!borderFlags[0] & borderFlags[1] & borderFlags[2] & !borderFlags[3]) {
            n4 = 13;
        }
        else if (!borderFlags[0] & borderFlags[1] & !borderFlags[2] & borderFlags[3]) {
            n4 = 37;
        }
        else if (!borderFlags[0] & borderFlags[1] & borderFlags[2] & borderFlags[3]) {
            n4 = 25;
        }
        else if (borderFlags[0] & !borderFlags[1] & borderFlags[2] & borderFlags[3]) {
            n4 = 27;
        }
        else if (borderFlags[0] & borderFlags[1] & !borderFlags[2] & borderFlags[3]) {
            n4 = 38;
        }
        else if (borderFlags[0] & borderFlags[1] & borderFlags[2] & !borderFlags[3]) {
            n4 = 14;
        }
        else if (borderFlags[0] & borderFlags[1] & borderFlags[2] & borderFlags[3]) {
            n4 = 26;
        }
        if (n4 == 0) {
            return connectedProperties.tileIcons[n4];
        }
        if (!Config.isConnectedTexturesFancy()) {
            return connectedProperties.tileIcons[n4];
        }
        switch (n2) {
            case 0: {
                borderFlags[0] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast().offsetNorth(), n2, textureAtlasSprite, n3);
                borderFlags[1] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest().offsetNorth(), n2, textureAtlasSprite, n3);
                borderFlags[2] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast().offsetSouth(), n2, textureAtlasSprite, n3);
                borderFlags[3] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest().offsetSouth(), n2, textureAtlasSprite, n3);
                break;
            }
            case 1: {
                borderFlags[0] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast().offsetSouth(), n2, textureAtlasSprite, n3);
                borderFlags[1] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest().offsetSouth(), n2, textureAtlasSprite, n3);
                borderFlags[2] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast().offsetNorth(), n2, textureAtlasSprite, n3);
                borderFlags[3] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest().offsetNorth(), n2, textureAtlasSprite, n3);
                break;
            }
            case 2: {
                borderFlags[0] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest().offsetDown(), n2, textureAtlasSprite, n3);
                borderFlags[1] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast().offsetDown(), n2, textureAtlasSprite, n3);
                borderFlags[2] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest().offsetUp(), n2, textureAtlasSprite, n3);
                borderFlags[3] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast().offsetUp(), n2, textureAtlasSprite, n3);
                break;
            }
            case 3: {
                borderFlags[0] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast().offsetDown(), n2, textureAtlasSprite, n3);
                borderFlags[1] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest().offsetDown(), n2, textureAtlasSprite, n3);
                borderFlags[2] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast().offsetUp(), n2, textureAtlasSprite, n3);
                borderFlags[3] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest().offsetUp(), n2, textureAtlasSprite, n3);
                break;
            }
            case 4: {
                borderFlags[0] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown().offsetSouth(), n2, textureAtlasSprite, n3);
                borderFlags[1] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown().offsetNorth(), n2, textureAtlasSprite, n3);
                borderFlags[2] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp().offsetSouth(), n2, textureAtlasSprite, n3);
                borderFlags[3] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp().offsetNorth(), n2, textureAtlasSprite, n3);
                break;
            }
            case 5: {
                borderFlags[0] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown().offsetNorth(), n2, textureAtlasSprite, n3);
                borderFlags[1] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown().offsetSouth(), n2, textureAtlasSprite, n3);
                borderFlags[2] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp().offsetNorth(), n2, textureAtlasSprite, n3);
                borderFlags[3] = !isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp().offsetSouth(), n2, textureAtlasSprite, n3);
                break;
            }
        }
        if (n4 == 13 && borderFlags[0]) {
            n4 = 4;
        }
        else if (n4 == 15 && borderFlags[1]) {
            n4 = 5;
        }
        else if (n4 == 37 && borderFlags[2]) {
            n4 = 16;
        }
        else if (n4 == 39 && borderFlags[3]) {
            n4 = 17;
        }
        else if (n4 == 14 && borderFlags[0] && borderFlags[1]) {
            n4 = 7;
        }
        else if (n4 == 25 && borderFlags[0] && borderFlags[2]) {
            n4 = 6;
        }
        else if (n4 == 27 && borderFlags[3] && borderFlags[1]) {
            n4 = 19;
        }
        else if (n4 == 38 && borderFlags[3] && borderFlags[2]) {
            n4 = 18;
        }
        else if (n4 == 14 && !borderFlags[0] && borderFlags[1]) {
            n4 = 31;
        }
        else if (n4 == 25 && borderFlags[0] && !borderFlags[2]) {
            n4 = 30;
        }
        else if (n4 == 27 && !borderFlags[3] && borderFlags[1]) {
            n4 = 41;
        }
        else if (n4 == 38 && borderFlags[3] && !borderFlags[2]) {
            n4 = 40;
        }
        else if (n4 == 14 && borderFlags[0] && !borderFlags[1]) {
            n4 = 29;
        }
        else if (n4 == 25 && !borderFlags[0] && borderFlags[2]) {
            n4 = 28;
        }
        else if (n4 == 27 && borderFlags[3] && !borderFlags[1]) {
            n4 = 43;
        }
        else if (n4 == 38 && !borderFlags[3] && borderFlags[2]) {
            n4 = 42;
        }
        else if (n4 == 26 && borderFlags[0] && borderFlags[1] && borderFlags[2] && borderFlags[3]) {
            n4 = 46;
        }
        else if (n4 == 26 && !borderFlags[0] && borderFlags[1] && borderFlags[2] && borderFlags[3]) {
            n4 = 9;
        }
        else if (n4 == 26 && borderFlags[0] && !borderFlags[1] && borderFlags[2] && borderFlags[3]) {
            n4 = 21;
        }
        else if (n4 == 26 && borderFlags[0] && borderFlags[1] && !borderFlags[2] && borderFlags[3]) {
            n4 = 8;
        }
        else if (n4 == 26 && borderFlags[0] && borderFlags[1] && borderFlags[2] && !borderFlags[3]) {
            n4 = 20;
        }
        else if (n4 == 26 && borderFlags[0] && borderFlags[1] && !borderFlags[2] && !borderFlags[3]) {
            n4 = 11;
        }
        else if (n4 == 26 && !borderFlags[0] && !borderFlags[1] && borderFlags[2] && borderFlags[3]) {
            n4 = 22;
        }
        else if (n4 == 26 && !borderFlags[0] && borderFlags[1] && !borderFlags[2] && borderFlags[3]) {
            n4 = 23;
        }
        else if (n4 == 26 && borderFlags[0] && !borderFlags[1] && borderFlags[2] && !borderFlags[3]) {
            n4 = 10;
        }
        else if (n4 == 26 && borderFlags[0] && !borderFlags[1] && !borderFlags[2] && borderFlags[3]) {
            n4 = 34;
        }
        else if (n4 == 26 && !borderFlags[0] && borderFlags[1] && borderFlags[2] && !borderFlags[3]) {
            n4 = 35;
        }
        else if (n4 == 26 && borderFlags[0] && !borderFlags[1] && !borderFlags[2] && !borderFlags[3]) {
            n4 = 32;
        }
        else if (n4 == 26 && !borderFlags[0] && borderFlags[1] && !borderFlags[2] && !borderFlags[3]) {
            n4 = 33;
        }
        else if (n4 == 26 && !borderFlags[0] && !borderFlags[1] && borderFlags[2] && !borderFlags[3]) {
            n4 = 44;
        }
        else if (n4 == 26 && !borderFlags[0] && !borderFlags[1] && !borderFlags[2] && borderFlags[3]) {
            n4 = 45;
        }
        return connectedProperties.tileIcons[n4];
    }
    
    private static boolean isNeighbour(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final TextureAtlasSprite textureAtlasSprite, final int n2) {
        final IBlockState blockState2 = blockAccess.getBlockState(blockPos);
        if (blockState == blockState2) {
            return true;
        }
        if (connectedProperties.connect == 2) {
            return blockState2 != null && blockState2 != ConnectedTextures.AIR_DEFAULT_STATE && getNeighbourIcon(blockAccess, blockPos, blockState2, n) == textureAtlasSprite;
        }
        return connectedProperties.connect == 3 && blockState2 != null && blockState2 != ConnectedTextures.AIR_DEFAULT_STATE && blockState2.getBlock().getMaterial() == blockState.getBlock().getMaterial();
    }
    
    private static TextureAtlasSprite getNeighbourIcon(final IBlockAccess blockAccess, final BlockPos blockPos, IBlockState actualState, final int n) {
        actualState = actualState.getBlock().getActualState(actualState, blockAccess, blockPos);
        final IBakedModel func_178125_b = Minecraft.getMinecraft().getBlockRendererDispatcher().func_175023_a().func_178125_b(actualState);
        if (func_178125_b == null) {
            return null;
        }
        final EnumFacing facing = getFacing(n);
        final List func_177551_a = func_178125_b.func_177551_a(facing);
        if (func_177551_a.size() > 0) {
            return func_177551_a.get(0).getSprite();
        }
        final List func_177550_a = func_178125_b.func_177550_a();
        for (int i = 0; i < func_177550_a.size(); ++i) {
            final BakedQuad bakedQuad = func_177550_a.get(i);
            if (bakedQuad.getFace() == facing) {
                return bakedQuad.getSprite();
            }
        }
        return null;
    }
    
    private static TextureAtlasSprite getConnectedTextureHorizontal(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        boolean b = false;
        boolean b2 = false;
        Label_0634: {
            switch (n) {
                case 0: {
                    switch (n2) {
                        case 0:
                        case 1: {
                            return null;
                        }
                        case 2: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 3: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 4: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetNorth(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetSouth(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 5: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetSouth(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetNorth(), n2, textureAtlasSprite, n3);
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (n2) {
                        case 0: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 1: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 2:
                        case 3: {
                            return null;
                        }
                        case 4: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 5: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown(), n2, textureAtlasSprite, n3);
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (n2) {
                        case 0: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetNorth(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetSouth(), n2, textureAtlasSprite, n3);
                            break Label_0634;
                        }
                        case 1: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetNorth(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetSouth(), n2, textureAtlasSprite, n3);
                            break Label_0634;
                        }
                        case 2: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp(), n2, textureAtlasSprite, n3);
                            break Label_0634;
                        }
                        case 3: {
                            b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp(), n2, textureAtlasSprite, n3);
                            b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown(), n2, textureAtlasSprite, n3);
                            break Label_0634;
                        }
                        case 4:
                        case 5: {
                            return null;
                        }
                    }
                    break;
                }
            }
        }
        int n4;
        if (b) {
            if (b2) {
                n4 = 1;
            }
            else {
                n4 = 2;
            }
        }
        else if (b2) {
            n4 = 0;
        }
        else {
            n4 = 3;
        }
        return connectedProperties.tileIcons[n4];
    }
    
    private static TextureAtlasSprite getConnectedTextureVertical(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        boolean b = false;
        boolean b2 = false;
        switch (n) {
            case 0: {
                if (n2 == 1 || n2 == 0) {
                    return null;
                }
                b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetDown(), n2, textureAtlasSprite, n3);
                b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp(), n2, textureAtlasSprite, n3);
                break;
            }
            case 1: {
                if (n2 == 3 || n2 == 2) {
                    return null;
                }
                b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetSouth(), n2, textureAtlasSprite, n3);
                b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetNorth(), n2, textureAtlasSprite, n3);
                break;
            }
            case 2: {
                if (n2 == 5 || n2 == 4) {
                    return null;
                }
                b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetWest(), n2, textureAtlasSprite, n3);
                b2 = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast(), n2, textureAtlasSprite, n3);
                break;
            }
        }
        int n4;
        if (b) {
            if (b2) {
                n4 = 1;
            }
            else {
                n4 = 2;
            }
        }
        else if (b2) {
            n4 = 0;
        }
        else {
            n4 = 3;
        }
        return connectedProperties.tileIcons[n4];
    }
    
    private static TextureAtlasSprite getConnectedTextureHorizontalVertical(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        final TextureAtlasSprite[] tileIcons = connectedProperties.tileIcons;
        final TextureAtlasSprite connectedTextureHorizontal = getConnectedTextureHorizontal(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        if (connectedTextureHorizontal != null && connectedTextureHorizontal != textureAtlasSprite && connectedTextureHorizontal != tileIcons[3]) {
            return connectedTextureHorizontal;
        }
        final TextureAtlasSprite connectedTextureVertical = getConnectedTextureVertical(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        return (connectedTextureVertical == tileIcons[0]) ? tileIcons[4] : ((connectedTextureVertical == tileIcons[1]) ? tileIcons[5] : ((connectedTextureVertical == tileIcons[2]) ? tileIcons[6] : connectedTextureVertical));
    }
    
    private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        final TextureAtlasSprite[] tileIcons = connectedProperties.tileIcons;
        final TextureAtlasSprite connectedTextureVertical = getConnectedTextureVertical(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        if (connectedTextureVertical != null && connectedTextureVertical != textureAtlasSprite && connectedTextureVertical != tileIcons[3]) {
            return connectedTextureVertical;
        }
        final TextureAtlasSprite connectedTextureHorizontal = getConnectedTextureHorizontal(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        return (connectedTextureHorizontal == tileIcons[0]) ? tileIcons[4] : ((connectedTextureHorizontal == tileIcons[1]) ? tileIcons[5] : ((connectedTextureHorizontal == tileIcons[2]) ? tileIcons[6] : connectedTextureHorizontal));
    }
    
    private static TextureAtlasSprite getConnectedTextureTop(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        boolean b = false;
        switch (n) {
            case 0: {
                if (n2 == 1 || n2 == 0) {
                    return null;
                }
                b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetUp(), n2, textureAtlasSprite, n3);
                break;
            }
            case 1: {
                if (n2 == 3 || n2 == 2) {
                    return null;
                }
                b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetSouth(), n2, textureAtlasSprite, n3);
                break;
            }
            case 2: {
                if (n2 == 5 || n2 == 4) {
                    return null;
                }
                b = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.offsetEast(), n2, textureAtlasSprite, n3);
                break;
            }
        }
        return b ? connectedProperties.tileIcons[0] : null;
    }
    
    public static void updateIcons(final TextureMap textureMap) {
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        ConnectedTextures.spriteQuadMaps = null;
        if (Config.isConnectedTextures()) {
            final IResourcePack[] resourcePacks = Config.getResourcePacks();
            for (int i = resourcePacks.length - 1; i >= 0; --i) {
                updateIcons(textureMap, resourcePacks[i]);
            }
            updateIcons(textureMap, Config.getDefaultResourcePack());
            ConnectedTextures.emptySprite = textureMap.func_174942_a(new ResourceLocation(ConnectedTextures.lIlIIIIllIIlIlll[28]));
            ConnectedTextures.spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
            if (ConnectedTextures.blockProperties.length <= 0) {
                ConnectedTextures.blockProperties = null;
            }
            if (ConnectedTextures.tileProperties.length <= 0) {
                ConnectedTextures.tileProperties = null;
            }
        }
    }
    
    private static void updateIconEmpty(final TextureMap textureMap) {
    }
    
    public static void updateIcons(final TextureMap textureMap, final IResourcePack resourcePack) {
        final String[] collectFiles = ResUtils.collectFiles(resourcePack, ConnectedTextures.lIlIIIIllIIlIlll[29], ConnectedTextures.lIlIIIIllIIlIlll[30], getDefaultCtmPaths());
        Arrays.sort(collectFiles);
        final List propertyList = makePropertyList(ConnectedTextures.tileProperties);
        final List propertyList2 = makePropertyList(ConnectedTextures.blockProperties);
        for (int i = 0; i < collectFiles.length; ++i) {
            final String s = collectFiles[i];
            Config.dbg(ConnectedTextures.lIlIIIIllIIlIlll[31] + s);
            try {
                final InputStream inputStream = resourcePack.getInputStream(new ResourceLocation(s));
                if (inputStream == null) {
                    Config.warn(ConnectedTextures.lIlIIIIllIIlIlll[32] + s);
                }
                else {
                    final Properties properties = new Properties();
                    properties.load(inputStream);
                    final ConnectedProperties connectedProperties = new ConnectedProperties(properties, s);
                    if (connectedProperties.isValid(s)) {
                        connectedProperties.updateIcons(textureMap);
                        addToTileList(connectedProperties, propertyList);
                        addToBlockList(connectedProperties, propertyList2);
                    }
                }
            }
            catch (FileNotFoundException ex2) {
                Config.warn(ConnectedTextures.lIlIIIIllIIlIlll[33] + s);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        ConnectedTextures.blockProperties = propertyListToArray(propertyList2);
        ConnectedTextures.tileProperties = propertyListToArray(propertyList);
        ConnectedTextures.multipass = detectMultipass();
        Config.dbg(ConnectedTextures.lIlIIIIllIIlIlll[34] + ConnectedTextures.multipass);
    }
    
    private static List makePropertyList(final ConnectedProperties[][] array) {
        final ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                final ConnectedProperties[] array2 = array[i];
                ArrayList list2 = null;
                if (array2 != null) {
                    list2 = new ArrayList(Arrays.asList(array2));
                }
                list.add(list2);
            }
        }
        return list;
    }
    
    private static boolean detectMultipass() {
        final ArrayList list = new ArrayList();
        for (int i = 0; i < ConnectedTextures.tileProperties.length; ++i) {
            final ConnectedProperties[] array = ConnectedTextures.tileProperties[i];
            if (array != null) {
                list.addAll(Arrays.asList(array));
            }
        }
        for (int j = 0; j < ConnectedTextures.blockProperties.length; ++j) {
            final ConnectedProperties[] array2 = ConnectedTextures.blockProperties[j];
            if (array2 != null) {
                list.addAll(Arrays.asList(array2));
            }
        }
        final ConnectedProperties[] array3 = list.toArray(new ConnectedProperties[list.size()]);
        final HashSet set = new HashSet();
        final HashSet set2 = new HashSet();
        for (int k = 0; k < array3.length; ++k) {
            final ConnectedProperties connectedProperties = array3[k];
            if (connectedProperties.matchTileIcons != null) {
                set.addAll(Arrays.asList(connectedProperties.matchTileIcons));
            }
            if (connectedProperties.tileIcons != null) {
                set2.addAll(Arrays.asList(connectedProperties.tileIcons));
            }
        }
        set.retainAll(set2);
        return !set.isEmpty();
    }
    
    private static ConnectedProperties[][] propertyListToArray(final List list) {
        final ConnectedProperties[][] array = new ConnectedProperties[list.size()][];
        for (int i = 0; i < list.size(); ++i) {
            final List list2 = list.get(i);
            if (list2 != null) {
                array[i] = (ConnectedProperties[])list2.toArray(new ConnectedProperties[list2.size()]);
            }
        }
        return array;
    }
    
    private static void addToTileList(final ConnectedProperties connectedProperties, final List list) {
        if (connectedProperties.matchTileIcons != null) {
            for (int i = 0; i < connectedProperties.matchTileIcons.length; ++i) {
                final TextureAtlasSprite textureAtlasSprite = connectedProperties.matchTileIcons[i];
                if (!(textureAtlasSprite instanceof TextureAtlasSprite)) {
                    Config.warn(ConnectedTextures.lIlIIIIllIIlIlll[35] + textureAtlasSprite + ConnectedTextures.lIlIIIIllIIlIlll[36] + textureAtlasSprite.getIconName());
                }
                else {
                    final int indexInMap = textureAtlasSprite.getIndexInMap();
                    if (indexInMap < 0) {
                        Config.warn(ConnectedTextures.lIlIIIIllIIlIlll[37] + indexInMap + ConnectedTextures.lIlIIIIllIIlIlll[38] + textureAtlasSprite.getIconName());
                    }
                    else {
                        addToList(connectedProperties, list, indexInMap);
                    }
                }
            }
        }
    }
    
    private static void addToBlockList(final ConnectedProperties connectedProperties, final List list) {
        if (connectedProperties.matchBlocks != null) {
            for (int i = 0; i < connectedProperties.matchBlocks.length; ++i) {
                final int blockId = connectedProperties.matchBlocks[i].getBlockId();
                if (blockId < 0) {
                    Config.warn(ConnectedTextures.lIlIIIIllIIlIlll[39] + blockId);
                }
                else {
                    addToList(connectedProperties, list, blockId);
                }
            }
        }
    }
    
    private static void addToList(final ConnectedProperties connectedProperties, final List list, final int i) {
        while (i >= list.size()) {
            list.add(null);
        }
        List<ConnectedProperties> list2 = list.get(i);
        if (list2 == null) {
            list2 = new ArrayList<ConnectedProperties>();
            list.set(i, list2);
        }
        list2.add(connectedProperties);
    }
    
    private static String[] getDefaultCtmPaths() {
        final ArrayList<String> list = new ArrayList<String>();
        final String s = ConnectedTextures.lIlIIIIllIIlIlll[40];
        if (Config.isFromDefaultResourcePack(new ResourceLocation(ConnectedTextures.lIlIIIIllIIlIlll[41]))) {
            list.add(String.valueOf(s) + ConnectedTextures.lIlIIIIllIIlIlll[42]);
            list.add(String.valueOf(s) + ConnectedTextures.lIlIIIIllIIlIlll[43]);
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation(ConnectedTextures.lIlIIIIllIIlIlll[44]))) {
            list.add(String.valueOf(s) + ConnectedTextures.lIlIIIIllIIlIlll[45]);
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation(ConnectedTextures.lIlIIIIllIIlIlll[46]))) {
            list.add(String.valueOf(s) + ConnectedTextures.lIlIIIIllIIlIlll[47]);
        }
        final String[] array = { ConnectedTextures.lIlIIIIllIIlIlll[48], ConnectedTextures.lIlIIIIllIIlIlll[49], ConnectedTextures.lIlIIIIllIIlIlll[50], ConnectedTextures.lIlIIIIllIIlIlll[51], ConnectedTextures.lIlIIIIllIIlIlll[52], ConnectedTextures.lIlIIIIllIIlIlll[53], ConnectedTextures.lIlIIIIllIIlIlll[54], ConnectedTextures.lIlIIIIllIIlIlll[55], ConnectedTextures.lIlIIIIllIIlIlll[56], ConnectedTextures.lIlIIIIllIIlIlll[57], ConnectedTextures.lIlIIIIllIIlIlll[58], ConnectedTextures.lIlIIIIllIIlIlll[59], ConnectedTextures.lIlIIIIllIIlIlll[60], ConnectedTextures.lIlIIIIllIIlIlll[61], ConnectedTextures.lIlIIIIllIIlIlll[62], ConnectedTextures.lIlIIIIllIIlIlll[63] };
        for (int i = 0; i < array.length; ++i) {
            final String s2 = array[i];
            if (Config.isFromDefaultResourcePack(new ResourceLocation(ConnectedTextures.lIlIIIIllIIlIlll[64] + s2 + ConnectedTextures.lIlIIIIllIIlIlll[65]))) {
                list.add(String.valueOf(s) + i + ConnectedTextures.lIlIIIIllIIlIlll[66] + s2 + ConnectedTextures.lIlIIIIllIIlIlll[67] + s2 + ConnectedTextures.lIlIIIIllIIlIlll[68]);
                list.add(String.valueOf(s) + i + ConnectedTextures.lIlIIIIllIIlIlll[69] + s2 + ConnectedTextures.lIlIIIIllIIlIlll[70] + s2 + ConnectedTextures.lIlIIIIllIIlIlll[71]);
            }
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static int getPaneTextureIndex(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        return (b2 && b) ? (b3 ? (b4 ? 34 : 50) : (b4 ? 18 : 2)) : ((b2 && !b) ? (b3 ? (b4 ? 35 : 51) : (b4 ? 19 : 3)) : ((!b2 && b) ? (b3 ? (b4 ? 33 : 49) : (b4 ? 17 : 1)) : (b3 ? (b4 ? 32 : 48) : (b4 ? 16 : 0))));
    }
    
    public static int getReversePaneTextureIndex(final int n) {
        final int n2 = n % 16;
        return (n2 == 1) ? (n + 2) : ((n2 == 3) ? (n - 2) : n);
    }
    
    public static TextureAtlasSprite getCtmTexture(final ConnectedProperties connectedProperties, final int n, final TextureAtlasSprite textureAtlasSprite) {
        if (connectedProperties.method != 1) {
            return textureAtlasSprite;
        }
        if (n >= 0 && n < ConnectedTextures.ctmIndexes.length) {
            final int n2 = ConnectedTextures.ctmIndexes[n];
            final TextureAtlasSprite[] tileIcons = connectedProperties.tileIcons;
            return (n2 >= 0 && n2 < tileIcons.length) ? tileIcons[n2] : textureAtlasSprite;
        }
        return textureAtlasSprite;
    }
    
    private static void lllIllIlllllIIll() {
        (lIlIIIIllIIlIlll = new String[72])[0] = lllIllIlllIllIll(ConnectedTextures.lIlIIIIllIlIlIIl[0], ConnectedTextures.lIlIIIIllIlIlIIl[1]);
        ConnectedTextures.lIlIIIIllIIlIlll[1] = lllIllIlllIllIll(ConnectedTextures.lIlIIIIllIlIlIIl[2], ConnectedTextures.lIlIIIIllIlIlIIl[3]);
        ConnectedTextures.lIlIIIIllIIlIlll[2] = lllIllIlllIllllI(ConnectedTextures.lIlIIIIllIlIlIIl[4], ConnectedTextures.lIlIIIIllIlIlIIl[5]);
        ConnectedTextures.lIlIIIIllIIlIlll[3] = lllIllIlllIllllI(ConnectedTextures.lIlIIIIllIlIlIIl[6], ConnectedTextures.lIlIIIIllIlIlIIl[7]);
        ConnectedTextures.lIlIIIIllIIlIlll[4] = lllIllIlllIllIll(ConnectedTextures.lIlIIIIllIlIlIIl[8], ConnectedTextures.lIlIIIIllIlIlIIl[9]);
        ConnectedTextures.lIlIIIIllIIlIlll[5] = lllIllIlllIlllll(ConnectedTextures.lIlIIIIllIlIlIIl[10], ConnectedTextures.lIlIIIIllIlIlIIl[11]);
        ConnectedTextures.lIlIIIIllIIlIlll[6] = lllIllIllllIIIII(ConnectedTextures.lIlIIIIllIlIlIIl[12], ConnectedTextures.lIlIIIIllIlIlIIl[13]);
        ConnectedTextures.lIlIIIIllIIlIlll[7] = lllIllIlllIllIll(ConnectedTextures.lIlIIIIllIlIlIIl[14], ConnectedTextures.lIlIIIIllIlIlIIl[15]);
        ConnectedTextures.lIlIIIIllIIlIlll[8] = lllIllIlllIlllll(ConnectedTextures.lIlIIIIllIlIlIIl[16], ConnectedTextures.lIlIIIIllIlIlIIl[17]);
        ConnectedTextures.lIlIIIIllIIlIlll[9] = lllIllIlllIlllll(ConnectedTextures.lIlIIIIllIlIlIIl[18], ConnectedTextures.lIlIIIIllIlIlIIl[19]);
        ConnectedTextures.lIlIIIIllIIlIlll[10] = lllIllIlllIllllI(ConnectedTextures.lIlIIIIllIlIlIIl[20], ConnectedTextures.lIlIIIIllIlIlIIl[21]);
        ConnectedTextures.lIlIIIIllIIlIlll[11] = lllIllIlllIllllI(ConnectedTextures.lIlIIIIllIlIlIIl[22], ConnectedTextures.lIlIIIIllIlIlIIl[23]);
        ConnectedTextures.lIlIIIIllIIlIlll[12] = lllIllIlllIllllI(ConnectedTextures.lIlIIIIllIlIlIIl[24], ConnectedTextures.lIlIIIIllIlIlIIl[25]);
        ConnectedTextures.lIlIIIIllIIlIlll[13] = lllIllIllllIIIII(ConnectedTextures.lIlIIIIllIlIlIIl[26], ConnectedTextures.lIlIIIIllIlIlIIl[27]);
        ConnectedTextures.lIlIIIIllIIlIlll[14] = lllIllIlllIllllI(ConnectedTextures.lIlIIIIllIlIlIIl[28], ConnectedTextures.lIlIIIIllIlIlIIl[29]);
        ConnectedTextures.lIlIIIIllIIlIlll[15] = lllIllIlllIllIll(ConnectedTextures.lIlIIIIllIlIlIIl[30], ConnectedTextures.lIlIIIIllIlIlIIl[31]);
        ConnectedTextures.lIlIIIIllIIlIlll[16] = lllIllIlllIllllI(ConnectedTextures.lIlIIIIllIlIlIIl[32], ConnectedTextures.lIlIIIIllIlIlIIl[33]);
        ConnectedTextures.lIlIIIIllIIlIlll[17] = lllIllIlllIllIll(ConnectedTextures.lIlIIIIllIlIlIIl[34], ConnectedTextures.lIlIIIIllIlIlIIl[35]);
        ConnectedTextures.lIlIIIIllIIlIlll[18] = lllIllIllllIIIII(ConnectedTextures.lIlIIIIllIlIlIIl[36], ConnectedTextures.lIlIIIIllIlIlIIl[37]);
        ConnectedTextures.lIlIIIIllIIlIlll[19] = lllIllIlllIlllll(ConnectedTextures.lIlIIIIllIlIlIIl[38], ConnectedTextures.lIlIIIIllIlIlIIl[39]);
        ConnectedTextures.lIlIIIIllIIlIlll[20] = lllIllIlllIlllll(ConnectedTextures.lIlIIIIllIlIlIIl[40], ConnectedTextures.lIlIIIIllIlIlIIl[41]);
        ConnectedTextures.lIlIIIIllIIlIlll[21] = lllIllIlllIlllll(ConnectedTextures.lIlIIIIllIlIlIIl[42], ConnectedTextures.lIlIIIIllIlIlIIl[43]);
        ConnectedTextures.lIlIIIIllIIlIlll[22] = lllIllIlllIllllI(ConnectedTextures.lIlIIIIllIlIlIIl[44], ConnectedTextures.lIlIIIIllIlIlIIl[45]);
        ConnectedTextures.lIlIIIIllIIlIlll[23] = lllIllIlllIlllll(ConnectedTextures.lIlIIIIllIlIlIIl[46], ConnectedTextures.lIlIIIIllIlIlIIl[47]);
        ConnectedTextures.lIlIIIIllIIlIlll[24] = lllIllIlllIllllI("2opvVo627pwRJTiOuL7TKw==", "ZEeHB");
        ConnectedTextures.lIlIIIIllIIlIlll[25] = lllIllIlllIlllll("FA==", "mAKAC");
        ConnectedTextures.lIlIIIIllIIlIlll[26] = lllIllIlllIlllll("Hg==", "dnKVn");
        ConnectedTextures.lIlIIIIllIIlIlll[27] = lllIllIlllIlllll("IAobISc/AhMwfi8PGicvPkwSKCU+ECo0JSMGKjArPQ==", "McuDD");
        ConnectedTextures.lIlIIIIllIIlIlll[28] = lllIllIlllIllllI("UWliea2P7w2XGCOBggFLTwy/VlNnaFbuSmfxNt1gOqk=", "prJgt");
        ConnectedTextures.lIlIIIIllIIlIlll[29] = lllIllIlllIllllI("p2ZXtfr4RguQPAmWd1mPFw==", "HAipB");
        ConnectedTextures.lIlIIIIllIIlIlll[30] = lllIllIllllIIIII("m22Mr2ZKJEaX5M5u77dcKg==", "yFesF");
        ConnectedTextures.lIlIIIIllIIlIlll[31] = lllIllIlllIllIll("zdsTA6AA56z2AJR+NKNA62YhykEb4/Us", "VKQyU");
        ConnectedTextures.lIlIIIIllIIlIlll[32] = lllIllIlllIllIll("OvzssAHwIip6N6q0St3u8eIhVqiOSVmlMsxzXZPHDMR3F76rIJF4SA==", "IwrEN");
        ConnectedTextures.lIlIIIIllIIlIlll[33] = lllIllIlllIllIll("HWiWj3JwD2Qsa0HEhBNSQAPjL0InNNfj6Y84IpIMF8ZyQb9TYtpnAQ==", "zvTLm");
        ConnectedTextures.lIlIIIIllIIlIlll[34] = lllIllIlllIllIll("+EuFSkD40zdv3lGxCISZJsUu62hgDkek9F27NrukKr4=", "hFbju");
        ConnectedTextures.lIlIIIIllIIlIlll[35] = lllIllIlllIllIll("Fwwi9TKiEOSWJM3iRSO5U+J77jgJIJgIEOD/xBR731zrK4KeyOFXibJQlJ2pfa8A", "jLjew");
        ConnectedTextures.lIlIIIIllIIlIlll[36] = lllIllIlllIllIll("c0PpKUTONnwmHayAmtPkOA==", "InCYB");
        ConnectedTextures.lIlIIIIllIIlIlll[37] = lllIllIlllIlllll("PC0eJCEcJ0gxJBkmSAwJT2M=", "uChEM");
        ConnectedTextures.lIlIIIIllIIlIlll[38] = lllIllIlllIllIll("WGp9TrCQk9RLLC70iuIgZg==", "yBPLD");
        ConnectedTextures.lIlIIIIllIIlIlll[39] = lllIllIlllIllIll("0hvntW5FXoxicFeR6Q9oYeCyzKENEN4S", "sEPoZ");
        ConnectedTextures.lIlIIIIllIIlIlll[40] = lllIllIlllIllIll("51hiYbY1UWBvpOBO8PnzV+37jOsgp25H", "nysVf");
        ConnectedTextures.lIlIIIIllIIlIlll[41] = lllIllIlllIllllI("ecV8yh8K4cXmTrkizYWKl5gXDj4QYabByty6Eh0KBMI=", "JkEjd");
        ConnectedTextures.lIlIIIIllIIlIlll[42] = lllIllIlllIllllI("a+vM6DmBwAlriTEwLkhKS5rZAukbvgI/bfYZ4nQFtRI=", "nebNn");
        ConnectedTextures.lIlIIIIllIIlIlll[43] = lllIllIlllIllllI("yoanMRWOFYKkLk2kRJfI//fJCt8cpBa30CTZL2ZwS8c=", "rTGXt");
        ConnectedTextures.lIlIIIIllIIlIlll[44] = lllIllIlllIllllI("2kIcFikUFBQ/fGtOZJSGLIhH7RNdpuQu2dTjubXDuBI=", "JKymk");
        ConnectedTextures.lIlIIIIllIIlIlll[45] = lllIllIlllIllIll("ocwPVZvYgp6wQZ03NYi4t5PGoctUz9Do", "dHqDE");
        ConnectedTextures.lIlIIIIllIIlIlll[46] = lllIllIlllIllllI("rqntadIk7FD1cr70VaQwfqTWbbmeuXOgSNqukh5kJyZqqSzs4BEyCwTpOEr36Nlx", "wcvQU");
        ConnectedTextures.lIlIIIIllIIlIlll[47] = lllIllIlllIlllll("FwkmFCsQByYVdhQaJwA9FhwhFSs=", "dhHpX");
        ConnectedTextures.lIlIIIIllIIlIlll[48] = lllIllIlllIlllll("EC86DAc=", "gGSxb");
        ConnectedTextures.lIlIIIIllIIlIlll[49] = lllIllIlllIllIll("mymewrAc/n0=", "OSEcB");
        ConnectedTextures.lIlIIIIllIIlIlll[50] = lllIllIlllIlllll("IBksNzc5GQ==", "MxKRY");
        ConnectedTextures.lIlIIIIllIIlIlll[51] = lllIllIlllIlllll("DQ0XGQ4+BhwEHw==", "adpqz");
        ConnectedTextures.lIlIIIIllIIlIlll[52] = lllIllIlllIllIll("gLkFAJcqMBY=", "ogVXC");
        ConnectedTextures.lIlIIIIllIIlIlll[53] = lllIllIllllIIIII("o1QYmWqoLTg=", "BOZVV");
        ConnectedTextures.lIlIIIIllIIlIlll[54] = lllIllIlllIllllI("Wc4+BnSyjd+ppUoiQ7tsPw==", "gNhxm");
        ConnectedTextures.lIlIIIIllIIlIlll[55] = lllIllIllllIIIII("+qc3ttXs/sM=", "behQO");
        ConnectedTextures.lIlIIIIllIIlIlll[56] = lllIllIlllIlllll("Hi4VBzYf", "mGyqS");
        ConnectedTextures.lIlIIIIllIIlIlll[57] = lllIllIlllIllllI("i+FVGgRwpWGQnoXzlz/IfQ==", "jVwDn");
        ConnectedTextures.lIlIIIIllIIlIlll[58] = lllIllIlllIlllll("NSAwBgsg", "EUBvg");
        ConnectedTextures.lIlIIIIllIIlIlll[59] = lllIllIllllIIIII("AM/HEXaDh7c=", "BgvBJ");
        ConnectedTextures.lIlIIIIllIIlIlll[60] = lllIllIllllIIIII("DzvTb9o0MvM=", "Euafe");
        ConnectedTextures.lIlIIIIllIIlIlll[61] = lllIllIllllIIIII("YcfzeNewHRo=", "Nshlh");
        ConnectedTextures.lIlIIIIllIIlIlll[62] = lllIllIlllIllIll("yFmV+AxSVX4=", "sNAGP");
        ConnectedTextures.lIlIIIIllIIlIlll[63] = lllIllIlllIllllI("kTLxJlXQJIJ3yp3IOKKIqg==", "SrTws");
        ConnectedTextures.lIlIIIIllIIlIlll[64] = lllIllIlllIllllI("4dEC5n0cZG7Bf12HF1/RXbWbHkw+ByZXKqwfeQf9urE=", "PiISf");
        ConnectedTextures.lIlIIIIllIIlIlll[65] = lllIllIlllIllIll("dK9Li+8C1ac=", "wkzlq");
        ConnectedTextures.lIlIIIIllIIlIlll[66] = lllIllIllllIIIII("RpyaRbkOz8U=", "Lewkw");
        ConnectedTextures.lIlIIIIllIIlIlll[67] = lllIllIllllIIIII("nzLBNC+zDj0=", "yLjRM");
        ConnectedTextures.lIlIIIIllIIlIlll[68] = lllIllIllllIIIII("epdJ67VEY/Emhau6MjH+Gw==", "jBaNK");
        ConnectedTextures.lIlIIIIllIIlIlll[69] = lllIllIlllIlllll("BiYBLRUqHg==", "YAmLf");
        ConnectedTextures.lIlIIIIllIIlIlll[70] = lllIllIllllIIIII("EwEJDbmNywW8w67pkMW0DQ==", "ErKkO");
        ConnectedTextures.lIlIIIIllIIlIlll[71] = lllIllIlllIllIll("XBycMjF5tjFHOqy9OWQIEA==", "KJOnU");
        ConnectedTextures.lIlIIIIllIlIlIIl = null;
    }
    
    private static void lllIllIlllllIlII() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        ConnectedTextures.lIlIIIIllIlIlIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lllIllIlllIllIll(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lllIllIlllIlllll(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
    
    private static String lllIllIlllIllllI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lllIllIllllIIIII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    static class NamelessClass379831726
    {
        static final int[] $SwitchMap$net$minecraft$util$EnumFacing;
        
        static {
            $SwitchMap$net$minecraft$util$EnumFacing = new int[EnumFacing.values().length];
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.EAST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.NORTH.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.SOUTH.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
