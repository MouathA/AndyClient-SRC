package optifine;

import net.minecraft.block.state.*;

public class MatchBlock
{
    private int blockId;
    private int[] metadatas;
    
    public MatchBlock(final int blockId) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = blockId;
    }
    
    public MatchBlock(final int blockId, final int n) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = blockId;
        if (n >= 0 && n <= 15) {
            this.metadatas = new int[] { n };
        }
    }
    
    public MatchBlock(final int blockId, final int[] metadatas) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = blockId;
        this.metadatas = metadatas;
    }
    
    public int getBlockId() {
        return this.blockId;
    }
    
    public int[] getMetadatas() {
        return this.metadatas;
    }
    
    public boolean matches(final BlockStateBase blockStateBase) {
        return blockStateBase.getBlockId() == this.blockId && Matches.metadata(blockStateBase.getMetadata(), this.metadatas);
    }
    
    public boolean matches(final int n, final int n2) {
        return n == this.blockId && Matches.metadata(n2, this.metadatas);
    }
    
    public void addMetadata(final int n) {
        if (this.metadatas != null && n >= 0 && n <= 15) {
            while (0 < this.metadatas.length) {
                if (this.metadatas[0] == n) {
                    return;
                }
                int n2 = 0;
                ++n2;
            }
            this.metadatas = Config.addIntToArray(this.metadatas, n);
        }
    }
    
    @Override
    public String toString() {
        return this.blockId + ":" + Config.arrayToString(this.metadatas);
    }
}
