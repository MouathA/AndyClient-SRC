package net.minecraft.client.entity;

import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import Mood.*;
import DTool.events.*;
import net.minecraft.entity.item.*;
import DTool.events.listeners.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;
import net.minecraft.entity.passive.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.command.server.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.audio.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;

public class EntityPlayerSP extends AbstractClientPlayer
{
    public final NetHandlerPlayClient sendQueue;
    private final StatFileWriter field_146108_bO;
    private double field_175172_bI;
    private double field_175166_bJ;
    private double field_175167_bK;
    private float field_175164_bL;
    private float field_175165_bM;
    private boolean field_175170_bN;
    private boolean field_175171_bO;
    private int field_175168_bP;
    private boolean field_175169_bQ;
    private String clientBrand;
    public MovementInput movementInput;
    protected Minecraft mc;
    protected int sprintToggleTimer;
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    private int horseJumpPowerCounter;
    private float horseJumpPower;
    public float timeInPortal;
    public float prevTimeInPortal;
    private static final String __OBFID;
    
    public EntityPlayerSP(final Minecraft mc, final World world, final NetHandlerPlayClient sendQueue, final StatFileWriter field_146108_bO) {
        super(world, sendQueue.func_175105_e());
        this.sendQueue = sendQueue;
        this.field_146108_bO = field_146108_bO;
        this.mc = mc;
        this.dimension = 0;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return false;
    }
    
    @Override
    public void heal(final float n) {
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        super.mountEntity(entity);
        if (entity instanceof EntityMinecart) {
            Minecraft.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entity));
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0, this.posZ))) {
            super.onUpdate();
            if (this.isRiding()) {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
            }
            else {
                this.func_175161_p();
            }
        }
    }
    
    public void func_175161_p() {
        final EventUpdate eventUpdate = new EventUpdate();
        eventUpdate.setType(EventType.PRE);
        Client.onEvent(eventUpdate);
        final EventMotion eventMotion = new EventMotion(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround);
        eventMotion.setType(EventType.PRE);
        Client.onEvent(eventMotion);
        final boolean sprinting = this.isSprinting();
        if (sprinting != this.field_175171_bO) {
            if (sprinting) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
            }
            this.field_175171_bO = sprinting;
        }
        final boolean sneaking = this.isSneaking();
        if (sneaking != this.field_175170_bN) {
            if (sneaking) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.field_175170_bN = sneaking;
        }
        if (this.func_175160_A()) {
            final double n = eventMotion.getX() - this.field_175172_bI;
            final double n2 = eventMotion.getY() - this.field_175166_bJ;
            final double n3 = eventMotion.getZ() - this.field_175167_bK;
            final double n4 = eventMotion.getYaw() - this.field_175164_bL;
            final double n5 = eventMotion.getPitch() - this.field_175165_bM;
            final boolean b = n * n + n2 * n2 + n3 * n3 > 9.0E-4 || this.field_175168_bP >= 20;
            final boolean b2 = n4 != 0.0 || n5 != 0.0;
            if (this.ridingEntity == null) {
                if (false && b2) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(eventMotion.getX(), eventMotion.getY(), eventMotion.getZ(), this.rotationYaw, eventMotion.getPitch(), this.onGround));
                }
                else if (false) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(eventMotion.getX(), eventMotion.getY(), eventMotion.getZ(), this.onGround));
                }
                else if (b2) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, eventMotion.getPitch(), this.onGround));
                }
                else {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
                }
            }
            else {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0, this.motionZ, this.rotationYaw, eventMotion.getPitch(), this.onGround));
            }
            ++this.field_175168_bP;
            if (false) {
                this.field_175172_bI = eventMotion.getX();
                this.field_175166_bJ = eventMotion.getY();
                this.field_175167_bK = eventMotion.getZ();
                this.field_175168_bP = 0;
            }
            if (b2) {
                this.field_175164_bL = this.rotationYaw;
                this.field_175165_bM = eventMotion.getPitch();
            }
        }
        eventMotion.setType(EventType.POST);
        Client.onEvent(eventMotion);
    }
    
    @Override
    public EntityItem dropOneItem(final boolean b) {
        this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(b ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        return null;
    }
    
    @Override
    protected void joinEntityItemWithWorld(final EntityItem entityItem) {
    }
    
    public void sendChatMessage(final String s) {
        final EventChat eventChat = new EventChat(s);
        Client.onEvent(eventChat);
        if (eventChat.isCancelled()) {
            return;
        }
        this.sendQueue.addToSendQueue(new C01PacketChatMessage(eventChat.getMessage()));
    }
    
    @Override
    public void swingItem() {
        super.swingItem();
        this.sendQueue.addToSendQueue(new C0APacketAnimation());
    }
    
    @Override
    public void respawnPlayer() {
        this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
    }
    
    @Override
    protected void damageEntity(final DamageSource damageSource, final float n) {
        if (!this.func_180431_b(damageSource)) {
            this.setHealth(this.getHealth() - n);
        }
    }
    
    public void closeScreen() {
        this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
        this.func_175159_q();
    }
    
    public void func_175159_q() {
        this.inventory.setItemStack(null);
        super.closeScreen();
        this.mc.displayGuiScreen(null);
    }
    
    public void setPlayerSPHealth(final float n) {
        if (this.field_175169_bQ) {
            final float lastDamage = this.getHealth() - n;
            if (lastDamage <= 0.0f) {
                this.setHealth(n);
                if (lastDamage < 0.0f) {
                    this.hurtResistantTime = this.maxHurtResistantTime / 2;
                }
            }
            else {
                this.lastDamage = lastDamage;
                this.setHealth(this.getHealth());
                this.hurtResistantTime = this.maxHurtResistantTime;
                this.damageEntity(DamageSource.generic, lastDamage);
                final int n2 = 10;
                this.maxHurtTime = n2;
                this.hurtTime = n2;
            }
        }
        else {
            this.setHealth(n);
            this.field_175169_bQ = true;
        }
    }
    
    @Override
    public void addStat(final StatBase statBase, final int n) {
        if (statBase != null && statBase.isIndependent) {
            super.addStat(statBase, n);
        }
    }
    
    @Override
    public void sendPlayerAbilities() {
        this.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
    }
    
    @Override
    public boolean func_175144_cb() {
        return true;
    }
    
    protected void sendHorseJump() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(this.getHorseJumpPower() * 100.0f)));
    }
    
    public void func_175163_u() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
    }
    
    public void func_175158_f(final String clientBrand) {
        this.clientBrand = clientBrand;
    }
    
    public String getClientBrand() {
        return this.clientBrand;
    }
    
    public StatFileWriter getStatFileWriter() {
        return this.field_146108_bO;
    }
    
    @Override
    public void addChatComponentMessage(final IChatComponent chatComponent) {
        Minecraft.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }
    
    @Override
    protected boolean pushOutOfBlocks(final double n, final double n2, final double n3) {
        if (this.noClip) {
            return false;
        }
        final BlockPos blockPos = new BlockPos(n, n2, n3);
        final double n4 = n - blockPos.getX();
        final double n5 = n3 - blockPos.getZ();
        if (!this.func_175162_d(blockPos)) {
            double n6 = 9999.0;
            if (this.func_175162_d(blockPos.offsetWest()) && n4 < n6) {
                n6 = n4;
            }
            if (this.func_175162_d(blockPos.offsetEast()) && 1.0 - n4 < n6) {
                n6 = 1.0 - n4;
            }
            if (this.func_175162_d(blockPos.offsetNorth()) && n5 < n6) {
                n6 = n5;
            }
            if (this.func_175162_d(blockPos.offsetSouth()) && 1.0 - n5 < n6) {}
            final float n7 = 0.1f;
            if (5 == 0) {
                this.motionX = -n7;
            }
            if (5 == 1) {
                this.motionX = n7;
            }
            if (5 == 4) {
                this.motionZ = -n7;
            }
            if (5 == 5) {
                this.motionZ = n7;
            }
        }
        return false;
    }
    
    private boolean func_175162_d(final BlockPos blockPos) {
        return !this.worldObj.getBlockState(blockPos).getBlock().isNormalCube() && !this.worldObj.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube();
    }
    
    @Override
    public void setSprinting(final boolean sprinting) {
        super.setSprinting(sprinting);
        this.sprintingTicksLeft = (sprinting ? 600 : 0);
    }
    
    public void setXPStats(final float experience, final int experienceTotal, final int experienceLevel) {
        this.experience = experience;
        this.experienceTotal = experienceTotal;
        this.experienceLevel = experienceLevel;
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        Minecraft.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return n <= 0;
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5);
    }
    
    @Override
    public void playSound(final String s, final float n, final float n2) {
        this.worldObj.playSound(this.posX, this.posY, this.posZ, s, n, n2, false);
    }
    
    @Override
    public boolean isServerWorld() {
        return true;
    }
    
    public boolean isRidingHorse() {
        return this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled();
    }
    
    public float getHorseJumpPower() {
        return this.horseJumpPower;
    }
    
    @Override
    public void func_175141_a(final TileEntitySign tileEntitySign) {
        if (GuiEditSign.laggsign) {
            final C12PacketUpdateSign c12PacketUpdateSign = new C12PacketUpdateSign(tileEntitySign.getPos(), new IChatComponent[] { new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("") });
            final ChatComponentText chatComponentText = new ChatComponentText("");
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("options.snooper.desc", new Object[0]);
            final ChatComponentText chatComponentText2 = new ChatComponentText("                           ");
            int n = 0;
            while (0 < 8) {
                chatComponentText2.appendSibling(chatComponentTranslation);
                ++n;
            }
            while (0 < 4) {
                c12PacketUpdateSign.getLines()[0].appendSibling(chatComponentText2);
                ++n;
            }
            this.mc.getNetHandler().getNetworkManager().channel.writeAndFlush(c12PacketUpdateSign);
        }
        else {
            this.mc.displayGuiScreen(new GuiEditSign(tileEntitySign));
        }
    }
    
    @Override
    public void func_146095_a(final CommandBlockLogic commandBlockLogic) {
        this.mc.displayGuiScreen(new GuiCommandBlock(commandBlockLogic));
    }
    
    @Override
    public void displayGUIBook(final ItemStack itemStack) {
        if (itemStack.getItem() == Items.writable_book) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, itemStack, true));
        }
    }
    
    @Override
    public void displayGUIChest(final IInventory inventory) {
        final String s = (inventory instanceof IInteractionObject) ? ((IInteractionObject)inventory).getGuiID() : "minecraft:container";
        if ("minecraft:chest".equals(s)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, inventory));
        }
        else if ("minecraft:hopper".equals(s)) {
            this.mc.displayGuiScreen(new GuiHopper(this.inventory, inventory));
        }
        else if ("minecraft:furnace".equals(s)) {
            this.mc.displayGuiScreen(new GuiFurnace(this.inventory, inventory));
        }
        else if ("minecraft:brewing_stand".equals(s)) {
            this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, inventory));
        }
        else if ("minecraft:beacon".equals(s)) {
            this.mc.displayGuiScreen(new GuiBeacon(this.inventory, inventory));
        }
        else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, inventory));
        }
        else {
            this.mc.displayGuiScreen(new GuiDispenser(this.inventory, inventory));
        }
    }
    
    @Override
    public void displayGUIHorse(final EntityHorse entityHorse, final IInventory inventory) {
        this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, inventory, entityHorse));
    }
    
    @Override
    public void displayGui(final IInteractionObject interactionObject) {
        final String guiID = interactionObject.getGuiID();
        if ("minecraft:crafting_table".equals(guiID)) {
            this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj));
        }
        else if ("minecraft:enchanting_table".equals(guiID)) {
            this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, interactionObject));
        }
        else if ("minecraft:anvil".equals(guiID)) {
            this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
        }
    }
    
    @Override
    public void displayVillagerTradeGui(final IMerchant merchant) {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, merchant, this.worldObj));
    }
    
    @Override
    public void onCriticalHit(final Entity entity) {
        this.mc.effectRenderer.func_178926_a(entity, EnumParticleTypes.CRIT);
    }
    
    @Override
    public void onEnchantmentCritical(final Entity entity) {
        this.mc.effectRenderer.func_178926_a(entity, EnumParticleTypes.CRIT_MAGIC);
    }
    
    @Override
    public boolean isSneaking() {
        return this.movementInput != null && this.movementInput.sneak && !this.sleeping;
    }
    
    public void updateEntityActionState() {
        super.updateEntityActionState();
        if (this.func_175160_A()) {
            this.moveStrafing = this.movementInput.moveStrafe;
            this.moveForward = this.movementInput.moveForward;
            this.isJumping = this.movementInput.jump;
            this.prevRenderArmYaw = this.renderArmYaw;
            this.prevRenderArmPitch = this.renderArmPitch;
            this.renderArmPitch += (float)((this.rotationPitch - this.renderArmPitch) * 0.5);
            this.renderArmYaw += (float)((this.rotationYaw - this.renderArmYaw) * 0.5);
        }
    }
    
    protected boolean func_175160_A() {
        return this.mc.func_175606_aa() == this;
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.sprintingTicksLeft > 0) {
            --this.sprintingTicksLeft;
            if (this.sprintingTicksLeft == 0) {
                this.setSprinting(false);
            }
        }
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }
        this.prevTimeInPortal = this.timeInPortal;
        if (this.inPortal) {
            if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
                this.mc.displayGuiScreen(null);
            }
            if (this.timeInPortal == 0.0f) {
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4f + 0.8f));
            }
            this.timeInPortal += 0.0125f;
            if (this.timeInPortal >= 1.0f) {
                this.timeInPortal = 1.0f;
            }
            this.inPortal = false;
        }
        else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
            this.timeInPortal += 0.006666667f;
            if (this.timeInPortal > 1.0f) {
                this.timeInPortal = 1.0f;
            }
        }
        else {
            if (this.timeInPortal > 0.0f) {
                this.timeInPortal -= 0.05f;
            }
            if (this.timeInPortal < 0.0f) {
                this.timeInPortal = 0.0f;
            }
        }
        if (this.timeUntilPortal > 0) {
            --this.timeUntilPortal;
        }
        final boolean jump = this.movementInput.jump;
        final boolean sneak = this.movementInput.sneak;
        final float n = 0.8f;
        final boolean b = this.movementInput.moveForward >= n;
        this.movementInput.updatePlayerMoveState();
        if (this.isUsingItem() && !this.isRiding()) {
            final Client instance = Client.INSTANCE;
            if (!Client.getModuleByName("NoSlowDown").toggled) {
                final MovementInput movementInput = this.movementInput;
                movementInput.moveStrafe *= 0.2f;
                final MovementInput movementInput2 = this.movementInput;
                movementInput2.moveForward *= 0.2f;
                this.sprintToggleTimer = 0;
            }
        }
        this.pushOutOfBlocks(this.posX - this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ + this.width * 0.35);
        this.pushOutOfBlocks(this.posX - this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ - this.width * 0.35);
        this.pushOutOfBlocks(this.posX + this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ - this.width * 0.35);
        this.pushOutOfBlocks(this.posX + this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ + this.width * 0.35);
        final boolean b2 = this.getFoodStats().getFoodLevel() > 6.0f || this.capabilities.allowFlying;
        if (this.onGround && !sneak && !b && this.movementInput.moveForward >= n && !this.isSprinting() && b2 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
            if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
                this.sprintToggleTimer = 7;
            }
            else {
                this.setSprinting(true);
            }
        }
        if (!this.isSprinting() && this.movementInput.moveForward >= n && b2 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
            this.setSprinting(true);
        }
        if (this.isSprinting() && (this.movementInput.moveForward < n || this.isCollidedHorizontally || !b2)) {
            this.setSprinting(false);
        }
        if (this.capabilities.allowFlying) {
            if (Minecraft.playerController.isSpectatorMode()) {
                if (!this.capabilities.isFlying) {
                    this.capabilities.isFlying = true;
                    this.sendPlayerAbilities();
                }
            }
            else if (!jump && this.movementInput.jump) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = 7;
                }
                else {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }
        if (this.capabilities.isFlying && this.func_175160_A()) {
            if (this.movementInput.sneak) {
                this.motionY -= this.capabilities.getFlySpeed() * 3.0f;
            }
            if (this.movementInput.jump) {
                this.motionY += this.capabilities.getFlySpeed() * 3.0f;
            }
        }
        if (this.isRidingHorse()) {
            if (this.horseJumpPowerCounter < 0) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter == 0) {
                    this.horseJumpPower = 0.0f;
                }
            }
            if (jump && !this.movementInput.jump) {
                this.horseJumpPowerCounter = -10;
                this.sendHorseJump();
            }
            else if (!jump && this.movementInput.jump) {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0f;
            }
            else if (jump) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter < 10) {
                    this.horseJumpPower = this.horseJumpPowerCounter * 0.1f;
                }
                else {
                    this.horseJumpPower = 0.8f + 2.0f / (this.horseJumpPowerCounter - 9) * 0.1f;
                }
            }
            final Client instance2 = Client.INSTANCE;
            if (Client.getModuleByName("PerfectHorseJump").toggled) {
                this.horseJumpPower = 1.0f;
            }
        }
        else {
            this.horseJumpPower = 0.0f;
        }
        super.onLivingUpdate();
        if (this.onGround && this.capabilities.isFlying && !Minecraft.playerController.isSpectatorMode()) {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
        }
    }
    
    public Entity getRidingEntity() {
        return this.ridingEntity;
    }
    
    public Vec3 getPositionEyes(final float n) {
        if (n == 1.0f) {
            return new Vec3(this.posX, this.posY + this.getEyeHeight(), this.posZ);
        }
        return new Vec3(this.prevPosX + (this.posX - this.prevPosX) * n, this.prevPosY + (this.posY - this.prevPosY) * n + this.getEyeHeight(), this.prevPosZ + (this.posZ - this.prevPosZ) * n);
    }
    
    public EnumFacing getHorizontalFacing() {
        return EnumFacing.getHorizontal(MathHelper.floor_double(this.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
    }
    
    static {
        __OBFID = "CL_00000938";
    }
}
