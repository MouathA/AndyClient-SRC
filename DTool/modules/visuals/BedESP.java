package DTool.modules.visuals;

import DTool.modules.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import DTool.util.*;
import java.util.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class BedESP extends Module
{
    private long currentMS;
    protected long lastMS;
    private int range;
    private int maxChests;
    public boolean shouldInform;
    private ArrayList matchingBlocks;
    
    public BedESP() {
        super("BedESP", 0, Category.Visuals);
        this.currentMS = 0L;
        this.lastMS = -1L;
        this.range = 50;
        this.maxChests = 1000;
        this.shouldInform = true;
        this.matchingBlocks = new ArrayList();
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onRender() {
        if (!this.isEnable()) {
            return;
        }
        Minecraft.getMinecraft();
        final Iterator<Object> iterator = (Iterator<Object>)Minecraft.theWorld.loadedTileEntityList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            if (0 >= this.maxChests) {
                break;
            }
        }
        Minecraft.getMinecraft();
        final Iterator<Object> iterator2 = (Iterator<Object>)Minecraft.theWorld.loadedEntityList.iterator();
        while (iterator2.hasNext()) {
            iterator2.next();
            if (0 >= this.maxChests) {
                break;
            }
        }
        for (final BlockPos blockPos : this.matchingBlocks) {
            if (0 >= this.maxChests) {
                break;
            }
            int n = 0;
            ++n;
            Utils.blockESPBox(blockPos);
        }
        if (0 >= this.maxChests && this.shouldInform) {
            this.shouldInform = false;
        }
        else if (0 < this.maxChests) {
            this.shouldInform = true;
        }
    }
    
    public final void updateMS() {
        this.currentMS = System.currentTimeMillis();
    }
    
    public final void updateLastMS() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public final boolean hasTimePassedM(final long n) {
        return this.currentMS >= this.lastMS + n;
    }
    
    public final boolean hasTimePassedS(final float n) {
        return this.currentMS >= this.lastMS + 1000.0f / n;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            this.updateMS();
            if (this.hasTimePassedM(3000L)) {
                this.matchingBlocks.clear();
                for (int i = this.range; i >= -this.range; --i) {
                    for (int j = this.range; j >= -this.range; --j) {
                        for (int k = this.range; k >= -this.range; --k) {
                            Minecraft.getMinecraft();
                            final int n = (int)(Minecraft.thePlayer.posX + j);
                            Minecraft.getMinecraft();
                            final int n2 = (int)(Minecraft.thePlayer.posY + i);
                            Minecraft.getMinecraft();
                            final BlockPos blockPos = new BlockPos(n, n2, (int)(Minecraft.thePlayer.posZ + k));
                            Minecraft.getMinecraft();
                            final IBlockState blockState = Minecraft.theWorld.getBlockState(blockPos);
                            final Block block = blockState.getBlock();
                            block.getMetaFromState(blockState);
                            if (block.getBlockState().getBlock() == Block.getBlockById(26)) {
                                this.matchingBlocks.add(blockPos);
                            }
                        }
                    }
                }
                this.updateLastMS();
            }
        }
    }
}
