package net.minecraft.client.entity;

import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class EntityOtherPlayerMP extends AbstractClientPlayer
{
    private boolean isItemInUse;
    private int otherPlayerMPPosRotationIncrements;
    private double otherPlayerMPX;
    private double otherPlayerMPY;
    private double otherPlayerMPZ;
    private double otherPlayerMPYaw;
    private double otherPlayerMPPitch;
    private static final String __OBFID;
    
    public EntityOtherPlayerMP(final World world, final GameProfile gameProfile) {
        super(world, gameProfile);
        this.stepHeight = 0.0f;
        this.noClip = true;
        this.field_71082_cx = 0.25f;
        this.renderDistanceWeight = 10.0;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return true;
    }
    
    @Override
    public void func_180426_a(final double otherPlayerMPX, final double otherPlayerMPY, final double otherPlayerMPZ, final float n, final float n2, final int otherPlayerMPPosRotationIncrements, final boolean b) {
        this.otherPlayerMPX = otherPlayerMPX;
        this.otherPlayerMPY = otherPlayerMPY;
        this.otherPlayerMPZ = otherPlayerMPZ;
        this.otherPlayerMPYaw = n;
        this.otherPlayerMPPitch = n2;
        this.otherPlayerMPPosRotationIncrements = otherPlayerMPPosRotationIncrements;
    }
    
    @Override
    public void onUpdate() {
        this.field_71082_cx = 0.0f;
        super.onUpdate();
        this.prevLimbSwingAmount = this.limbSwingAmount;
        final double n = this.posX - this.prevPosX;
        final double n2 = this.posZ - this.prevPosZ;
        float n3 = MathHelper.sqrt_double(n * n + n2 * n2) * 4.0f;
        if (n3 > 1.0f) {
            n3 = 1.0f;
        }
        this.limbSwingAmount += (n3 - this.limbSwingAmount) * 0.4f;
        this.limbSwing += this.limbSwingAmount;
        if (!this.isItemInUse && this.isEating() && this.inventory.mainInventory[this.inventory.currentItem] != null) {
            final ItemStack itemStack = this.inventory.mainInventory[this.inventory.currentItem];
            this.setItemInUse(this.inventory.mainInventory[this.inventory.currentItem], itemStack.getItem().getMaxItemUseDuration(itemStack));
            this.isItemInUse = true;
        }
        else if (this.isItemInUse && !this.isEating()) {
            this.clearItemInUse();
            this.isItemInUse = false;
        }
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.otherPlayerMPPosRotationIncrements > 0) {
            final double n = this.posX + (this.otherPlayerMPX - this.posX) / this.otherPlayerMPPosRotationIncrements;
            final double n2 = this.posY + (this.otherPlayerMPY - this.posY) / this.otherPlayerMPPosRotationIncrements;
            final double n3 = this.posZ + (this.otherPlayerMPZ - this.posZ) / this.otherPlayerMPPosRotationIncrements;
            double n4;
            for (n4 = this.otherPlayerMPYaw - this.rotationYaw; n4 < -180.0; n4 += 360.0) {}
            while (n4 >= 180.0) {
                n4 -= 360.0;
            }
            this.rotationYaw += (float)(n4 / this.otherPlayerMPPosRotationIncrements);
            this.rotationPitch += (float)((this.otherPlayerMPPitch - this.rotationPitch) / this.otherPlayerMPPosRotationIncrements);
            --this.otherPlayerMPPosRotationIncrements;
            this.setPosition(n, n2, n3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        this.prevCameraYaw = this.cameraYaw;
        this.updateArmSwingProgress();
        float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float n5 = (float)Math.atan(-this.motionY * 0.20000000298023224) * 15.0f;
        if (sqrt_double > 0.1f) {
            sqrt_double = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0.0f) {
            sqrt_double = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0.0f) {
            n5 = 0.0f;
        }
        this.cameraYaw += (sqrt_double - this.cameraYaw) * 0.4f;
        this.cameraPitch += (n5 - this.cameraPitch) * 0.8f;
    }
    
    @Override
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
        if (n == 0) {
            this.inventory.mainInventory[this.inventory.currentItem] = itemStack;
        }
        else {
            this.inventory.armorInventory[n - 1] = itemStack;
        }
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        Minecraft.getMinecraft();
        Minecraft.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return false;
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5);
    }
    
    static {
        __OBFID = "CL_00000939";
    }
}
