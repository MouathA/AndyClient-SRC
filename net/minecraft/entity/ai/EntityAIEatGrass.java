package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.block.*;
import com.google.common.base.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityAIEatGrass extends EntityAIBase
{
    private static final Predicate field_179505_b;
    private EntityLiving grassEaterEntity;
    private World entityWorld;
    int eatingGrassTimer;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001582";
        field_179505_b = BlockStateHelper.forBlock(Blocks.tallgrass).func_177637_a(BlockTallGrass.field_176497_a, Predicates.equalTo(BlockTallGrass.EnumType.GRASS));
    }
    
    public EntityAIEatGrass(final EntityLiving grassEaterEntity) {
        this.grassEaterEntity = grassEaterEntity;
        this.entityWorld = grassEaterEntity.worldObj;
        this.setMutexBits(7);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.grassEaterEntity.getRNG().nextInt(this.grassEaterEntity.isChild() ? 50 : 1000) != 0) {
            return false;
        }
        final BlockPos blockPos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
        return EntityAIEatGrass.field_179505_b.apply(this.entityWorld.getBlockState(blockPos)) || this.entityWorld.getBlockState(blockPos.offsetDown()).getBlock() == Blocks.grass;
    }
    
    @Override
    public void startExecuting() {
        this.eatingGrassTimer = 40;
        this.entityWorld.setEntityState(this.grassEaterEntity, (byte)10);
        this.grassEaterEntity.getNavigator().clearPathEntity();
    }
    
    @Override
    public void resetTask() {
        this.eatingGrassTimer = 0;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.eatingGrassTimer > 0;
    }
    
    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }
    
    @Override
    public void updateTask() {
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
        if (this.eatingGrassTimer == 4) {
            final BlockPos blockPos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
            if (EntityAIEatGrass.field_179505_b.apply(this.entityWorld.getBlockState(blockPos))) {
                if (this.entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    this.entityWorld.destroyBlock(blockPos, false);
                }
                this.grassEaterEntity.eatGrassBonus();
            }
            else {
                final BlockPos offsetDown = blockPos.offsetDown();
                if (this.entityWorld.getBlockState(offsetDown).getBlock() == Blocks.grass) {
                    if (this.entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                        this.entityWorld.playAuxSFX(2001, offsetDown, Block.getIdFromBlock(Blocks.grass));
                        this.entityWorld.setBlockState(offsetDown, Blocks.dirt.getDefaultState(), 2);
                    }
                    this.grassEaterEntity.eatGrassBonus();
                }
            }
        }
    }
}
