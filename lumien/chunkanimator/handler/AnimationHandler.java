package lumien.chunkanimator.handler;

import java.util.*;
import net.minecraft.client.renderer.chunk.*;
import lumien.chunkanimator.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import penner.easing.*;

public class AnimationHandler
{
    WeakHashMap timeStamps;
    
    public AnimationHandler() {
        this.timeStamps = new WeakHashMap();
    }
    
    public void preRender(final RenderChunk renderChunk) {
        if (this.timeStamps.containsKey(renderChunk)) {
            final AnimationData animationData = this.timeStamps.get(renderChunk);
            long timeStamp = animationData.timeStamp;
            final int mode = ChunkAnimator.INSTANCE.mode;
            if (timeStamp == -1L) {
                timeStamp = System.currentTimeMillis();
                animationData.timeStamp = timeStamp;
                if (3 == 4) {
                    Minecraft.getInstance();
                    final BlockPos position = Minecraft.thePlayer.getPosition();
                    final BlockPos subtract = position.add(0, -position.getY(), 0).subtract(renderChunk.func_178568_j().add(8, -renderChunk.func_178568_j().getY(), 8));
                    EnumFacing chunkFacing;
                    if (Math.abs(subtract.getX()) > Math.abs(subtract.getZ())) {
                        if (subtract.getX() > 0) {
                            chunkFacing = EnumFacing.EAST;
                        }
                        else {
                            chunkFacing = EnumFacing.WEST;
                        }
                    }
                    else if (subtract.getZ() > 0) {
                        chunkFacing = EnumFacing.SOUTH;
                    }
                    else {
                        chunkFacing = EnumFacing.NORTH;
                    }
                    animationData.chunkFacing = chunkFacing;
                }
            }
            final long n = System.currentTimeMillis() - timeStamp;
            final int animationDuration = ChunkAnimator.INSTANCE.animationDuration;
            if (n < animationDuration) {
                final int y = renderChunk.func_178568_j().getY();
                if (3 == 2) {
                    final double n2 = y;
                    Minecraft.getInstance();
                    if (n2 < Minecraft.theWorld.getHorizon()) {}
                }
                if (3 == 4) {}
                switch (3) {
                    case 0: {
                        GlStateManager.translate(0.0f, -y + this.getFunctionValue((float)n, 0.0f, (float)y, (float)animationDuration), 0.0f);
                        break;
                    }
                    case 1: {
                        GlStateManager.translate(0.0f, 256 - y - this.getFunctionValue((float)n, 0.0f, (float)(256 - y), (float)animationDuration), 0.0f);
                        break;
                    }
                    case 3: {
                        final EnumFacing chunkFacing2 = animationData.chunkFacing;
                        if (chunkFacing2 != null) {
                            final Vec3i directionVec = chunkFacing2.getDirectionVec();
                            final double n3 = -(200.0 - 200.0 / animationDuration * n);
                            final double n4 = -(200.0f - this.getFunctionValue((float)n, 0.0f, 200.0f, (float)animationDuration));
                            GlStateManager.translate(directionVec.getX() * n4, 0.0, directionVec.getZ() * n4);
                            break;
                        }
                        break;
                    }
                }
            }
            else {
                this.timeStamps.remove(renderChunk);
            }
        }
    }
    
    private float getFunctionValue(final float n, final float n2, final float n3, final float n4) {
        switch (ChunkAnimator.INSTANCE.easingFunction) {
            case 0: {
                return Linear.easeOut(n, n2, n3, n4);
            }
            case 1: {
                return Quad.easeOut(n, n2, n3, n4);
            }
            case 2: {
                return Cubic.easeOut(n, n2, n3, n4);
            }
            case 3: {
                return Quart.easeOut(n, n2, n3, n4);
            }
            case 4: {
                return Quint.easeOut(n, n2, n3, n4);
            }
            case 5: {
                return Expo.easeOut(n, n2, n3, n4);
            }
            case 6: {
                return Sine.easeOut(n, n2, n3, n4);
            }
            case 7: {
                return Circ.easeOut(n, n2, n3, n4);
            }
            case 8: {
                return Back.easeOut(n, n2, n3, n4);
            }
            case 9: {
                return Bounce.easeOut(n, n2, n3, n4);
            }
            case 10: {
                return Elastic.easeOut(n, n2, n3, n4);
            }
            default: {
                return Sine.easeOut(n, n2, n3, n4);
            }
        }
    }
    
    public void setOrigin(final RenderChunk renderChunk, final BlockPos blockPos) {
        Minecraft.getInstance();
        if (Minecraft.thePlayer != null) {
            Minecraft.getInstance();
            final BlockPos position = Minecraft.thePlayer.getPosition();
            final BlockPos add = position.add(0, -position.getY(), 0);
            final BlockPos add2 = blockPos.add(8, -blockPos.getY(), 8);
            if (ChunkAnimator.INSTANCE.disableAroundPlayer) {
                final boolean b = add.distanceSq(add2) > 4096.0;
            }
            if (true) {
                EnumFacing enumFacing = null;
                if (ChunkAnimator.INSTANCE.mode == 3) {
                    final BlockPos subtract = add.subtract(add2);
                    if (Math.abs(subtract.getX()) > Math.abs(subtract.getZ())) {
                        if (subtract.getX() > 0) {
                            enumFacing = EnumFacing.EAST;
                        }
                        else {
                            enumFacing = EnumFacing.WEST;
                        }
                    }
                    else if (subtract.getZ() > 0) {
                        enumFacing = EnumFacing.SOUTH;
                    }
                    else {
                        enumFacing = EnumFacing.NORTH;
                    }
                }
                this.timeStamps.put(renderChunk, new AnimationData(-1L, enumFacing));
            }
            else if (this.timeStamps.containsKey(renderChunk)) {
                this.timeStamps.remove(renderChunk);
            }
        }
    }
    
    private class AnimationData
    {
        public long timeStamp;
        public EnumFacing chunkFacing;
        final AnimationHandler this$0;
        
        public AnimationData(final AnimationHandler this$0, final long timeStamp, final EnumFacing chunkFacing) {
            this.this$0 = this$0;
            this.timeStamp = timeStamp;
            this.chunkFacing = chunkFacing;
        }
    }
}
