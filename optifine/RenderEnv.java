package optifine;

import net.minecraft.world.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;

public class RenderEnv
{
    private IBlockAccess blockAccess;
    private IBlockState blockState;
    private BlockPos blockPos;
    private GameSettings gameSettings;
    private int blockId;
    private int metadata;
    private int breakingAnimation;
    private float[] quadBounds;
    private BitSet boundsFlags;
    private BlockModelRenderer.AmbientOcclusionFace aoFace;
    private BlockPosM colorizerBlockPosM;
    private boolean[] borderFlags;
    private static ThreadLocal threadLocalInstance;
    
    static {
        RenderEnv.threadLocalInstance = new ThreadLocal();
    }
    
    private RenderEnv(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
        this.blockId = -1;
        this.metadata = -1;
        this.breakingAnimation = -1;
        this.quadBounds = new float[EnumFacing.VALUES.length * 2];
        this.boundsFlags = new BitSet(3);
        this.aoFace = new BlockModelRenderer.AmbientOcclusionFace();
        this.colorizerBlockPosM = null;
        this.borderFlags = null;
        this.blockAccess = blockAccess;
        this.blockState = blockState;
        this.blockPos = blockPos;
        this.gameSettings = Config.getGameSettings();
    }
    
    public static RenderEnv getInstance(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
        final RenderEnv renderEnv = RenderEnv.threadLocalInstance.get();
        if (renderEnv == null) {
            final RenderEnv renderEnv2 = new RenderEnv(blockAccess, blockState, blockPos);
            RenderEnv.threadLocalInstance.set(renderEnv2);
            return renderEnv2;
        }
        renderEnv.reset(blockAccess, blockState, blockPos);
        return renderEnv;
    }
    
    private void reset(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
        this.blockAccess = blockAccess;
        this.blockState = blockState;
        this.blockPos = blockPos;
        this.blockId = -1;
        this.metadata = -1;
        this.breakingAnimation = -1;
        this.boundsFlags.clear();
    }
    
    public int getBlockId() {
        if (this.blockId < 0) {
            if (this.blockState instanceof BlockStateBase) {
                this.blockId = ((BlockStateBase)this.blockState).getBlockId();
            }
            else {
                this.blockId = Block.getIdFromBlock(this.blockState.getBlock());
            }
        }
        return this.blockId;
    }
    
    public int getMetadata() {
        if (this.metadata < 0) {
            if (this.blockState instanceof BlockStateBase) {
                this.metadata = ((BlockStateBase)this.blockState).getMetadata();
            }
            else {
                this.metadata = this.blockState.getBlock().getMetaFromState(this.blockState);
            }
        }
        return this.metadata;
    }
    
    public float[] getQuadBounds() {
        return this.quadBounds;
    }
    
    public BitSet getBoundsFlags() {
        return this.boundsFlags;
    }
    
    public BlockModelRenderer.AmbientOcclusionFace getAoFace() {
        return this.aoFace;
    }
    
    public boolean isBreakingAnimation(final List list) {
        if (this.breakingAnimation < 0 && list.size() > 0) {
            if (list.get(0) instanceof BreakingFour) {
                this.breakingAnimation = 1;
            }
            else {
                this.breakingAnimation = 0;
            }
        }
        return this.breakingAnimation == 1;
    }
    
    public boolean isBreakingAnimation(final BakedQuad bakedQuad) {
        if (this.breakingAnimation < 0) {
            if (bakedQuad instanceof BreakingFour) {
                this.breakingAnimation = 1;
            }
            else {
                this.breakingAnimation = 0;
            }
        }
        return this.breakingAnimation == 1;
    }
    
    public boolean isBreakingAnimation() {
        return this.breakingAnimation == 1;
    }
    
    public IBlockState getBlockState() {
        return this.blockState;
    }
    
    public BlockPosM getColorizerBlockPosM() {
        if (this.colorizerBlockPosM == null) {
            this.colorizerBlockPosM = new BlockPosM(0, 0, 0);
        }
        return this.colorizerBlockPosM;
    }
    
    public boolean[] getBorderFlags() {
        if (this.borderFlags == null) {
            this.borderFlags = new boolean[4];
        }
        return this.borderFlags;
    }
}
