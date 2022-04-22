package net.minecraft.client.network;

import net.minecraft.network.play.*;
import com.mojang.authlib.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.realms.*;
import DTool.command.impl.*;
import net.minecraft.client.particle.*;
import wdl.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.audio.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.client.player.inventory.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.creativetab.*;
import net.minecraft.inventory.*;
import Mood.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.stats.*;
import net.minecraft.potion.*;
import net.minecraft.client.stream.*;
import java.io.*;
import net.minecraft.network.play.client.*;
import com.google.common.util.concurrent.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.village.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.ai.attributes.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class NetHandlerPlayClient implements INetHandlerPlayClient
{
    private static final Logger logger;
    private final NetworkManager netManager;
    private final GameProfile field_175107_d;
    private final GuiScreen guiScreenServer;
    private Minecraft gameController;
    private WorldClient clientWorldController;
    private boolean doneLoadingTerrain;
    private final Map playerInfoMap;
    public int currentServerMaxPlayers;
    private boolean field_147308_k;
    private final Random avRandomizer;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000878";
        logger = LogManager.getLogger();
    }
    
    public NetHandlerPlayClient(final Minecraft gameController, final GuiScreen guiScreenServer, final NetworkManager netManager, final GameProfile field_175107_d) {
        this.playerInfoMap = Maps.newHashMap();
        this.currentServerMaxPlayers = 20;
        this.field_147308_k = false;
        this.avRandomizer = new Random();
        this.gameController = gameController;
        this.guiScreenServer = guiScreenServer;
        this.netManager = netManager;
        this.field_175107_d = field_175107_d;
    }
    
    public void cleanup() {
        this.clientWorldController = null;
    }
    
    @Override
    public void handleJoinGame(final S01PacketJoinGame s01PacketJoinGame) {
        PacketThreadUtil.func_180031_a(s01PacketJoinGame, this, this.gameController);
        Minecraft.playerController = new PlayerControllerMP(this.gameController, this);
        this.clientWorldController = new WorldClient(this, new WorldSettings(0L, s01PacketJoinGame.func_149198_e(), false, s01PacketJoinGame.func_149195_d(), s01PacketJoinGame.func_149196_i()), s01PacketJoinGame.func_149194_f(), s01PacketJoinGame.func_149192_g(), this.gameController.mcProfiler);
        this.gameController.gameSettings.difficulty = s01PacketJoinGame.func_149192_g();
        this.gameController.loadWorld(this.clientWorldController);
        Minecraft.thePlayer.dimension = s01PacketJoinGame.func_149194_f();
        this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
        Minecraft.thePlayer.setEntityId(s01PacketJoinGame.func_149197_c());
        this.currentServerMaxPlayers = s01PacketJoinGame.func_149193_h();
        Minecraft.thePlayer.func_175150_k(s01PacketJoinGame.func_179744_h());
        Minecraft.playerController.setGameType(s01PacketJoinGame.func_149198_e());
        final GameSettings gameSettings = this.gameController.gameSettings;
        this.netManager.sendPacket(new C17PacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).writeString(ClientBrandRetriever.getClientModName())));
    }
    
    @Override
    public void handleSpawnObject(final S0EPacketSpawnObject s0EPacketSpawnObject) {
        PacketThreadUtil.func_180031_a(s0EPacketSpawnObject, this, this.gameController);
        final double n = s0EPacketSpawnObject.func_148997_d() / 32.0;
        final double n2 = s0EPacketSpawnObject.func_148998_e() / 32.0;
        final double n3 = s0EPacketSpawnObject.func_148994_f() / 32.0;
        Entity func_180458_a = null;
        if (s0EPacketSpawnObject.func_148993_l() == 10) {
            func_180458_a = EntityMinecart.func_180458_a(this.clientWorldController, n, n2, n3, EntityMinecart.EnumMinecartType.func_180038_a(s0EPacketSpawnObject.func_149009_m()));
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 90) {
            final Entity entityByID = this.clientWorldController.getEntityByID(s0EPacketSpawnObject.func_149009_m());
            if (entityByID instanceof EntityPlayer) {
                func_180458_a = new EntityFishHook(this.clientWorldController, n, n2, n3, (EntityPlayer)entityByID);
            }
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 60) {
            func_180458_a = new EntityArrow(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 61) {
            func_180458_a = new EntitySnowball(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 71) {
            func_180458_a = new EntityItemFrame(this.clientWorldController, new BlockPos(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3)), EnumFacing.getHorizontal(s0EPacketSpawnObject.func_149009_m()));
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 77) {
            func_180458_a = new EntityLeashKnot(this.clientWorldController, new BlockPos(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3)));
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 65) {
            func_180458_a = new EntityEnderPearl(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 72) {
            func_180458_a = new EntityEnderEye(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 76) {
            func_180458_a = new EntityFireworkRocket(this.clientWorldController, n, n2, n3, null);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 63) {
            func_180458_a = new EntityLargeFireball(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.func_149010_g() / 8000.0, s0EPacketSpawnObject.func_149004_h() / 8000.0, s0EPacketSpawnObject.func_148999_i() / 8000.0);
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 64) {
            func_180458_a = new EntitySmallFireball(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.func_149010_g() / 8000.0, s0EPacketSpawnObject.func_149004_h() / 8000.0, s0EPacketSpawnObject.func_148999_i() / 8000.0);
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 66) {
            func_180458_a = new EntityWitherSkull(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.func_149010_g() / 8000.0, s0EPacketSpawnObject.func_149004_h() / 8000.0, s0EPacketSpawnObject.func_148999_i() / 8000.0);
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 62) {
            func_180458_a = new EntityEgg(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 73) {
            func_180458_a = new EntityPotion(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.func_149009_m());
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 75) {
            func_180458_a = new EntityExpBottle(this.clientWorldController, n, n2, n3);
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 1) {
            func_180458_a = new EntityBoat(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 50) {
            func_180458_a = new EntityTNTPrimed(this.clientWorldController, n, n2, n3, null);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 78) {
            func_180458_a = new EntityArmorStand(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 51) {
            func_180458_a = new EntityEnderCrystal(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 2) {
            func_180458_a = new EntityItem(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.func_148993_l() == 70) {
            func_180458_a = new EntityFallingBlock(this.clientWorldController, n, n2, n3, Block.getStateById(s0EPacketSpawnObject.func_149009_m() & 0xFFFF));
            s0EPacketSpawnObject.func_149002_g(0);
        }
        if (func_180458_a != null) {
            ((EntityArrow)func_180458_a).serverPosX = s0EPacketSpawnObject.func_148997_d();
            ((EntityArrow)func_180458_a).serverPosY = s0EPacketSpawnObject.func_148998_e();
            ((EntityArrow)func_180458_a).serverPosZ = s0EPacketSpawnObject.func_148994_f();
            ((EntityArrow)func_180458_a).rotationPitch = s0EPacketSpawnObject.func_149008_j() * 360 / 256.0f;
            ((EntityArrow)func_180458_a).rotationYaw = s0EPacketSpawnObject.func_149006_k() * 360 / 256.0f;
            final Entity[] parts = ((EntityArrow)func_180458_a).getParts();
            if (parts != null) {
                final int n4 = s0EPacketSpawnObject.func_149001_c() - ((EntityArrow)func_180458_a).getEntityId();
                while (0 < parts.length) {
                    parts[0].setEntityId(parts[0].getEntityId() + n4);
                    int n5 = 0;
                    ++n5;
                }
            }
            ((EntityArrow)func_180458_a).setEntityId(s0EPacketSpawnObject.func_149001_c());
            this.clientWorldController.addEntityToWorld(s0EPacketSpawnObject.func_149001_c(), func_180458_a);
            if (s0EPacketSpawnObject.func_149009_m() > 0) {
                if (s0EPacketSpawnObject.func_148993_l() == 60) {
                    final Entity entityByID2 = this.clientWorldController.getEntityByID(s0EPacketSpawnObject.func_149009_m());
                    if (entityByID2 instanceof EntityLivingBase && func_180458_a instanceof EntityArrow) {
                        ((EntityArrow)func_180458_a).shootingEntity = entityByID2;
                    }
                }
                ((EntityArrow)func_180458_a).setVelocity(s0EPacketSpawnObject.func_149010_g() / 8000.0, s0EPacketSpawnObject.func_149004_h() / 8000.0, s0EPacketSpawnObject.func_148999_i() / 8000.0);
            }
        }
    }
    
    @Override
    public void handleSpawnExperienceOrb(final S11PacketSpawnExperienceOrb s11PacketSpawnExperienceOrb) {
        PacketThreadUtil.func_180031_a(s11PacketSpawnExperienceOrb, this, this.gameController);
        final EntityXPOrb entityXPOrb = new EntityXPOrb(this.clientWorldController, s11PacketSpawnExperienceOrb.func_148984_d(), s11PacketSpawnExperienceOrb.func_148983_e(), s11PacketSpawnExperienceOrb.func_148982_f(), s11PacketSpawnExperienceOrb.func_148986_g());
        entityXPOrb.serverPosX = s11PacketSpawnExperienceOrb.func_148984_d();
        entityXPOrb.serverPosY = s11PacketSpawnExperienceOrb.func_148983_e();
        entityXPOrb.serverPosZ = s11PacketSpawnExperienceOrb.func_148982_f();
        entityXPOrb.rotationYaw = 0.0f;
        entityXPOrb.rotationPitch = 0.0f;
        entityXPOrb.setEntityId(s11PacketSpawnExperienceOrb.func_148985_c());
        this.clientWorldController.addEntityToWorld(s11PacketSpawnExperienceOrb.func_148985_c(), entityXPOrb);
    }
    
    @Override
    public void handleSpawnGlobalEntity(final S2CPacketSpawnGlobalEntity s2CPacketSpawnGlobalEntity) {
        PacketThreadUtil.func_180031_a(s2CPacketSpawnGlobalEntity, this, this.gameController);
        final double n = s2CPacketSpawnGlobalEntity.func_149051_d() / 32.0;
        final double n2 = s2CPacketSpawnGlobalEntity.func_149050_e() / 32.0;
        final double n3 = s2CPacketSpawnGlobalEntity.func_149049_f() / 32.0;
        EntityLightningBolt entityLightningBolt = null;
        if (s2CPacketSpawnGlobalEntity.func_149053_g() == 1) {
            entityLightningBolt = new EntityLightningBolt(this.clientWorldController, n, n2, n3);
        }
        if (entityLightningBolt != null) {
            entityLightningBolt.serverPosX = s2CPacketSpawnGlobalEntity.func_149051_d();
            entityLightningBolt.serverPosY = s2CPacketSpawnGlobalEntity.func_149050_e();
            entityLightningBolt.serverPosZ = s2CPacketSpawnGlobalEntity.func_149049_f();
            entityLightningBolt.rotationYaw = 0.0f;
            entityLightningBolt.rotationPitch = 0.0f;
            entityLightningBolt.setEntityId(s2CPacketSpawnGlobalEntity.func_149052_c());
            this.clientWorldController.addWeatherEffect(entityLightningBolt);
        }
    }
    
    @Override
    public void handleSpawnPainting(final S10PacketSpawnPainting s10PacketSpawnPainting) {
        PacketThreadUtil.func_180031_a(s10PacketSpawnPainting, this, this.gameController);
        this.clientWorldController.addEntityToWorld(s10PacketSpawnPainting.func_148965_c(), new EntityPainting(this.clientWorldController, s10PacketSpawnPainting.func_179837_b(), s10PacketSpawnPainting.func_179836_c(), s10PacketSpawnPainting.func_148961_h()));
    }
    
    @Override
    public void handleEntityVelocity(final S12PacketEntityVelocity s12PacketEntityVelocity) {
        PacketThreadUtil.func_180031_a(s12PacketEntityVelocity, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s12PacketEntityVelocity.func_149412_c());
        if (entityByID != null) {
            entityByID.setVelocity(s12PacketEntityVelocity.func_149411_d() / 8000.0, s12PacketEntityVelocity.func_149410_e() / 8000.0, s12PacketEntityVelocity.func_149409_f() / 8000.0);
        }
    }
    
    @Override
    public void handleEntityMetadata(final S1CPacketEntityMetadata s1CPacketEntityMetadata) {
        PacketThreadUtil.func_180031_a(s1CPacketEntityMetadata, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s1CPacketEntityMetadata.func_149375_d());
        if (entityByID != null && s1CPacketEntityMetadata.func_149376_c() != null) {
            entityByID.getDataWatcher().updateWatchedObjectsFromList(s1CPacketEntityMetadata.func_149376_c());
        }
    }
    
    @Override
    public void handleSpawnPlayer(final S0CPacketSpawnPlayer s0CPacketSpawnPlayer) {
        PacketThreadUtil.func_180031_a(s0CPacketSpawnPlayer, this, this.gameController);
        final double n = s0CPacketSpawnPlayer.func_148942_f() / 32.0;
        final double n2 = s0CPacketSpawnPlayer.func_148949_g() / 32.0;
        final double n3 = s0CPacketSpawnPlayer.func_148946_h() / 32.0;
        final float n4 = s0CPacketSpawnPlayer.func_148941_i() * 360 / 256.0f;
        final float n5 = s0CPacketSpawnPlayer.func_148945_j() * 360 / 256.0f;
        final EntityOtherPlayerMP entityOtherPlayerMP4;
        final EntityOtherPlayerMP entityOtherPlayerMP3;
        final EntityOtherPlayerMP entityOtherPlayerMP2;
        final EntityOtherPlayerMP entityOtherPlayerMP = entityOtherPlayerMP2 = (entityOtherPlayerMP3 = (entityOtherPlayerMP4 = new EntityOtherPlayerMP(Minecraft.theWorld, this.func_175102_a(s0CPacketSpawnPlayer.func_179819_c()).func_178845_a())));
        final int func_148942_f = s0CPacketSpawnPlayer.func_148942_f();
        entityOtherPlayerMP2.serverPosX = func_148942_f;
        final double n6 = func_148942_f;
        entityOtherPlayerMP3.lastTickPosX = n6;
        entityOtherPlayerMP4.prevPosX = n6;
        final EntityOtherPlayerMP entityOtherPlayerMP5 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP6 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP7 = entityOtherPlayerMP;
        final int func_148949_g = s0CPacketSpawnPlayer.func_148949_g();
        entityOtherPlayerMP7.serverPosY = func_148949_g;
        final double n7 = func_148949_g;
        entityOtherPlayerMP6.lastTickPosY = n7;
        entityOtherPlayerMP5.prevPosY = n7;
        final EntityOtherPlayerMP entityOtherPlayerMP8 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP9 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP10 = entityOtherPlayerMP;
        final int func_148946_h = s0CPacketSpawnPlayer.func_148946_h();
        entityOtherPlayerMP10.serverPosZ = func_148946_h;
        final double n8 = func_148946_h;
        entityOtherPlayerMP9.lastTickPosZ = n8;
        entityOtherPlayerMP8.prevPosZ = n8;
        final int func_148947_k = s0CPacketSpawnPlayer.func_148947_k();
        if (func_148947_k == 0) {
            entityOtherPlayerMP.inventory.mainInventory[entityOtherPlayerMP.inventory.currentItem] = null;
        }
        else {
            entityOtherPlayerMP.inventory.mainInventory[entityOtherPlayerMP.inventory.currentItem] = new ItemStack(Item.getItemById(func_148947_k), 1, 0);
        }
        entityOtherPlayerMP.setPositionAndRotation(n, n2, n3, n4, n5);
        this.clientWorldController.addEntityToWorld(s0CPacketSpawnPlayer.func_148943_d(), entityOtherPlayerMP);
        final List func_148944_c = s0CPacketSpawnPlayer.func_148944_c();
        if (func_148944_c != null) {
            entityOtherPlayerMP.getDataWatcher().updateWatchedObjectsFromList(func_148944_c);
        }
    }
    
    @Override
    public void handleEntityTeleport(final S18PacketEntityTeleport s18PacketEntityTeleport) {
        PacketThreadUtil.func_180031_a(s18PacketEntityTeleport, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s18PacketEntityTeleport.func_149451_c());
        if (entityByID != null) {
            entityByID.serverPosX = s18PacketEntityTeleport.func_149449_d();
            entityByID.serverPosY = s18PacketEntityTeleport.func_149448_e();
            entityByID.serverPosZ = s18PacketEntityTeleport.func_149446_f();
            final double n = entityByID.serverPosX / 32.0;
            final double n2 = entityByID.serverPosY / 32.0 + 0.015625;
            final double n3 = entityByID.serverPosZ / 32.0;
            final float n4 = s18PacketEntityTeleport.func_149450_g() * 360 / 256.0f;
            final float n5 = s18PacketEntityTeleport.func_149447_h() * 360 / 256.0f;
            if (Math.abs(entityByID.posX - n) < 0.03125 && Math.abs(entityByID.posY - n2) < 0.015625 && Math.abs(entityByID.posZ - n3) < 0.03125) {
                entityByID.func_180426_a(entityByID.posX, entityByID.posY, entityByID.posZ, n4, n5, 3, true);
            }
            else {
                entityByID.func_180426_a(n, n2, n3, n4, n5, 3, true);
            }
            entityByID.onGround = s18PacketEntityTeleport.func_179697_g();
        }
    }
    
    @Override
    public void handleHeldItemChange(final S09PacketHeldItemChange s09PacketHeldItemChange) {
        PacketThreadUtil.func_180031_a(s09PacketHeldItemChange, this, this.gameController);
        if (s09PacketHeldItemChange.func_149385_c() >= 0 && s09PacketHeldItemChange.func_149385_c() < 9) {
            Minecraft.thePlayer.inventory.currentItem = s09PacketHeldItemChange.func_149385_c();
        }
    }
    
    @Override
    public void handleEntityMovement(final S14PacketEntity s14PacketEntity) {
        PacketThreadUtil.func_180031_a(s14PacketEntity, this, this.gameController);
        final Entity func_149065_a = s14PacketEntity.func_149065_a(this.clientWorldController);
        if (func_149065_a != null) {
            final Entity entity = func_149065_a;
            entity.serverPosX += s14PacketEntity.func_149062_c();
            final Entity entity2 = func_149065_a;
            entity2.serverPosY += s14PacketEntity.func_149061_d();
            final Entity entity3 = func_149065_a;
            entity3.serverPosZ += s14PacketEntity.func_149064_e();
            func_149065_a.func_180426_a(func_149065_a.serverPosX / 32.0, func_149065_a.serverPosY / 32.0, func_149065_a.serverPosZ / 32.0, s14PacketEntity.func_149060_h() ? (s14PacketEntity.func_149066_f() * 360 / 256.0f) : func_149065_a.rotationYaw, s14PacketEntity.func_149060_h() ? (s14PacketEntity.func_149063_g() * 360 / 256.0f) : func_149065_a.rotationPitch, 3, false);
            func_149065_a.onGround = s14PacketEntity.func_179742_g();
        }
    }
    
    @Override
    public void handleEntityHeadLook(final S19PacketEntityHeadLook s19PacketEntityHeadLook) {
        PacketThreadUtil.func_180031_a(s19PacketEntityHeadLook, this, this.gameController);
        final Entity func_149381_a = s19PacketEntityHeadLook.func_149381_a(this.clientWorldController);
        if (func_149381_a != null) {
            func_149381_a.setRotationYawHead(s19PacketEntityHeadLook.func_149380_c() * 360 / 256.0f);
        }
    }
    
    @Override
    public void handleDestroyEntities(final S13PacketDestroyEntities s13PacketDestroyEntities) {
        PacketThreadUtil.func_180031_a(s13PacketDestroyEntities, this, this.gameController);
        while (0 < s13PacketDestroyEntities.func_149098_c().length) {
            this.clientWorldController.removeEntityFromWorld(s13PacketDestroyEntities.func_149098_c()[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void handlePlayerPosLook(final S08PacketPlayerPosLook s08PacketPlayerPosLook) {
        PacketThreadUtil.func_180031_a(s08PacketPlayerPosLook, this, this.gameController);
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        double func_148932_c = s08PacketPlayerPosLook.func_148932_c();
        double func_148928_d = s08PacketPlayerPosLook.func_148928_d();
        double func_148933_e = s08PacketPlayerPosLook.func_148933_e();
        float func_148931_f = s08PacketPlayerPosLook.func_148931_f();
        float func_148930_g = s08PacketPlayerPosLook.func_148930_g();
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
            func_148932_c += thePlayer.posX;
        }
        else {
            thePlayer.motionX = 0.0;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
            func_148928_d += thePlayer.posY;
        }
        else {
            thePlayer.motionY = 0.0;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
            func_148933_e += thePlayer.posZ;
        }
        else {
            thePlayer.motionZ = 0.0;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
            func_148930_g += thePlayer.rotationPitch;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
            func_148931_f += thePlayer.rotationYaw;
        }
        thePlayer.setPositionAndRotation(func_148932_c, func_148928_d, func_148933_e, func_148931_f, func_148930_g);
        this.netManager.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(thePlayer.posX, thePlayer.getEntityBoundingBox().minY, thePlayer.posZ, thePlayer.rotationYaw, thePlayer.rotationPitch, false));
        if (!this.doneLoadingTerrain) {
            Minecraft.thePlayer.prevPosX = Minecraft.thePlayer.posX;
            Minecraft.thePlayer.prevPosY = Minecraft.thePlayer.posY;
            Minecraft.thePlayer.prevPosZ = Minecraft.thePlayer.posZ;
            this.doneLoadingTerrain = true;
            this.gameController.displayGuiScreen(null);
        }
    }
    
    @Override
    public void handleMultiBlockChange(final S22PacketMultiBlockChange s22PacketMultiBlockChange) {
        PacketThreadUtil.func_180031_a(s22PacketMultiBlockChange, this, this.gameController);
        final S22PacketMultiBlockChange.BlockUpdateData[] func_179844_a = s22PacketMultiBlockChange.func_179844_a();
        while (0 < func_179844_a.length) {
            final S22PacketMultiBlockChange.BlockUpdateData blockUpdateData = func_179844_a[0];
            this.clientWorldController.func_180503_b(blockUpdateData.func_180090_a(), blockUpdateData.func_180088_c());
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void handleChunkData(final S21PacketChunkData s21PacketChunkData) {
        PacketThreadUtil.func_180031_a(s21PacketChunkData, this, this.gameController);
        if (s21PacketChunkData.func_149274_i()) {
            if (s21PacketChunkData.func_149276_g() == 0) {
                this.clientWorldController.doPreChunk(s21PacketChunkData.func_149273_e(), s21PacketChunkData.func_149271_f(), false);
                return;
            }
            this.clientWorldController.doPreChunk(s21PacketChunkData.func_149273_e(), s21PacketChunkData.func_149271_f(), true);
        }
        this.clientWorldController.invalidateBlockReceiveRegion(s21PacketChunkData.func_149273_e() << 4, 0, s21PacketChunkData.func_149271_f() << 4, (s21PacketChunkData.func_149273_e() << 4) + 15, 256, (s21PacketChunkData.func_149271_f() << 4) + 15);
        final Chunk chunkFromChunkCoords = this.clientWorldController.getChunkFromChunkCoords(s21PacketChunkData.func_149273_e(), s21PacketChunkData.func_149271_f());
        chunkFromChunkCoords.func_177439_a(s21PacketChunkData.func_149272_d(), s21PacketChunkData.func_149276_g(), s21PacketChunkData.func_149274_i());
        this.clientWorldController.markBlockRangeForRenderUpdate(s21PacketChunkData.func_149273_e() << 4, 0, s21PacketChunkData.func_149271_f() << 4, (s21PacketChunkData.func_149273_e() << 4) + 15, 256, (s21PacketChunkData.func_149271_f() << 4) + 15);
        if (!s21PacketChunkData.func_149274_i() || !(this.clientWorldController.provider instanceof WorldProviderSurface)) {
            chunkFromChunkCoords.resetRelightChecks();
        }
    }
    
    @Override
    public void handleBlockChange(final S23PacketBlockChange s23PacketBlockChange) {
        PacketThreadUtil.func_180031_a(s23PacketBlockChange, this, this.gameController);
        this.clientWorldController.func_180503_b(s23PacketBlockChange.func_179827_b(), s23PacketBlockChange.func_180728_a());
    }
    
    @Override
    public void handleDisconnect(final S40PacketDisconnect s40PacketDisconnect) {
        if (WDL.downloading) {
            Thread.sleep(2000L);
        }
        this.netManager.closeChannel(s40PacketDisconnect.func_149165_c());
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        if (WDL.downloading) {
            Thread.sleep(2000L);
        }
        this.gameController.loadWorld(null);
        if (this.guiScreenServer != null) {
            if (this.guiScreenServer instanceof GuiScreenRealmsProxy) {
                this.gameController.displayGuiScreen(new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.guiScreenServer).func_154321_a(), "disconnect.lost", chatComponent).getProxy());
            }
            else {
                this.gameController.displayGuiScreen(new GuiDisconnected(this.guiScreenServer, "disconnect.lost", chatComponent));
            }
        }
        else {
            this.gameController.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", chatComponent));
        }
    }
    
    public void addToSendQueue(final Packet packet) {
        this.netManager.sendPacket(packet);
        if (packet instanceof C08PacketPlayerBlockPlacement) {
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.getHeldItem() != null) {
                Minecraft.getMinecraft();
                ObjectLoader.onPlace(Minecraft.thePlayer.getHeldItem());
            }
        }
    }
    
    @Override
    public void handleCollectItem(final S0DPacketCollectItem s0DPacketCollectItem) {
        PacketThreadUtil.func_180031_a(s0DPacketCollectItem, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s0DPacketCollectItem.func_149354_c());
        EntityLivingBase thePlayer = (EntityLivingBase)this.clientWorldController.getEntityByID(s0DPacketCollectItem.func_149353_d());
        if (thePlayer == null) {
            thePlayer = Minecraft.thePlayer;
        }
        if (entityByID != null) {
            if (entityByID instanceof EntityXPOrb) {
                this.clientWorldController.playSoundAtEntity(entityByID, "random.orb", 0.2f, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
            else {
                this.clientWorldController.playSoundAtEntity(entityByID, "random.pop", 0.2f, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
            this.gameController.effectRenderer.addEffect(new EntityPickupFX(this.clientWorldController, entityByID, thePlayer, 0.5f));
            this.clientWorldController.removeEntityFromWorld(s0DPacketCollectItem.func_149354_c());
        }
    }
    
    @Override
    public void handleChat(final S02PacketChat s02PacketChat) {
        WDLHooks.onNHPCHandleChat(this, s02PacketChat);
        PacketThreadUtil.func_180031_a(s02PacketChat, this, this.gameController);
        if (s02PacketChat.func_179841_c() == 2) {
            Minecraft.ingameGUI.func_175188_a(s02PacketChat.func_148915_c(), false);
        }
        else {
            Minecraft.ingameGUI.getChatGUI().printChatMessage(s02PacketChat.func_148915_c());
        }
    }
    
    @Override
    public void handleAnimation(final S0BPacketAnimation s0BPacketAnimation) {
        PacketThreadUtil.func_180031_a(s0BPacketAnimation, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s0BPacketAnimation.func_148978_c());
        if (entityByID != null) {
            if (s0BPacketAnimation.func_148977_d() == 0) {
                ((EntityPlayer)entityByID).swingItem();
            }
            else if (s0BPacketAnimation.func_148977_d() == 1) {
                entityByID.performHurtAnimation();
            }
            else if (s0BPacketAnimation.func_148977_d() == 2) {
                ((EntityPlayer)entityByID).wakeUpPlayer(false, false, false);
            }
            else if (s0BPacketAnimation.func_148977_d() == 4) {
                this.gameController.effectRenderer.func_178926_a(entityByID, EnumParticleTypes.CRIT);
            }
            else if (s0BPacketAnimation.func_148977_d() == 5) {
                this.gameController.effectRenderer.func_178926_a(entityByID, EnumParticleTypes.CRIT_MAGIC);
            }
        }
    }
    
    @Override
    public void handleUseBed(final S0APacketUseBed s0APacketUseBed) {
        PacketThreadUtil.func_180031_a(s0APacketUseBed, this, this.gameController);
        s0APacketUseBed.getPlayer(this.clientWorldController).func_180469_a(s0APacketUseBed.func_179798_a());
    }
    
    @Override
    public void handleSpawnMob(final S0FPacketSpawnMob s0FPacketSpawnMob) {
        PacketThreadUtil.func_180031_a(s0FPacketSpawnMob, this, this.gameController);
        final double n = s0FPacketSpawnMob.func_149023_f() / 32.0;
        final double n2 = s0FPacketSpawnMob.func_149034_g() / 32.0;
        final double n3 = s0FPacketSpawnMob.func_149029_h() / 32.0;
        final float n4 = s0FPacketSpawnMob.func_149028_l() * 360 / 256.0f;
        final float n5 = s0FPacketSpawnMob.func_149030_m() * 360 / 256.0f;
        final EntityLivingBase entityLivingBase = (EntityLivingBase)EntityList.createEntityByID(s0FPacketSpawnMob.func_149025_e(), Minecraft.theWorld);
        entityLivingBase.serverPosX = s0FPacketSpawnMob.func_149023_f();
        entityLivingBase.serverPosY = s0FPacketSpawnMob.func_149034_g();
        entityLivingBase.serverPosZ = s0FPacketSpawnMob.func_149029_h();
        entityLivingBase.rotationYawHead = s0FPacketSpawnMob.func_149032_n() * 360 / 256.0f;
        final Entity[] parts = entityLivingBase.getParts();
        if (parts != null) {
            final int n6 = s0FPacketSpawnMob.func_149024_d() - entityLivingBase.getEntityId();
            while (0 < parts.length) {
                parts[0].setEntityId(parts[0].getEntityId() + n6);
                int n7 = 0;
                ++n7;
            }
        }
        entityLivingBase.setEntityId(s0FPacketSpawnMob.func_149024_d());
        entityLivingBase.setPositionAndRotation(n, n2, n3, n4, n5);
        entityLivingBase.motionX = s0FPacketSpawnMob.func_149026_i() / 8000.0f;
        entityLivingBase.motionY = s0FPacketSpawnMob.func_149033_j() / 8000.0f;
        entityLivingBase.motionZ = s0FPacketSpawnMob.func_149031_k() / 8000.0f;
        this.clientWorldController.addEntityToWorld(s0FPacketSpawnMob.func_149024_d(), entityLivingBase);
        final List func_149027_c = s0FPacketSpawnMob.func_149027_c();
        if (func_149027_c != null) {
            entityLivingBase.getDataWatcher().updateWatchedObjectsFromList(func_149027_c);
        }
    }
    
    @Override
    public void handleTimeUpdate(final S03PacketTimeUpdate s03PacketTimeUpdate) {
        PacketThreadUtil.func_180031_a(s03PacketTimeUpdate, this, this.gameController);
        Minecraft.theWorld.func_82738_a(s03PacketTimeUpdate.func_149366_c());
        Minecraft.theWorld.setWorldTime(s03PacketTimeUpdate.func_149365_d());
    }
    
    @Override
    public void handleSpawnPosition(final S05PacketSpawnPosition s05PacketSpawnPosition) {
        PacketThreadUtil.func_180031_a(s05PacketSpawnPosition, this, this.gameController);
        Minecraft.thePlayer.func_180473_a(s05PacketSpawnPosition.func_179800_a(), true);
        Minecraft.theWorld.getWorldInfo().setSpawn(s05PacketSpawnPosition.func_179800_a());
    }
    
    @Override
    public void handleEntityAttach(final S1BPacketEntityAttach s1BPacketEntityAttach) {
        PacketThreadUtil.func_180031_a(s1BPacketEntityAttach, this, this.gameController);
        Entity entity = this.clientWorldController.getEntityByID(s1BPacketEntityAttach.func_149403_d());
        final Entity entityByID = this.clientWorldController.getEntityByID(s1BPacketEntityAttach.func_149402_e());
        if (s1BPacketEntityAttach.func_149404_c() == 0) {
            if (s1BPacketEntityAttach.func_149403_d() == Minecraft.thePlayer.getEntityId()) {
                entity = Minecraft.thePlayer;
                if (entityByID instanceof EntityBoat) {
                    ((EntityBoat)entityByID).setIsBoatEmpty(false);
                }
                final boolean b = ((EntityLiving)entity).ridingEntity == null && entityByID != null;
            }
            else if (entityByID instanceof EntityBoat) {
                ((EntityBoat)entityByID).setIsBoatEmpty(true);
            }
            if (entity == null) {
                return;
            }
            ((EntityLiving)entity).mountEntity(entityByID);
            if (false) {
                Minecraft.ingameGUI.setRecordPlaying(I18n.format("mount.onboard", GameSettings.getKeyDisplayString(this.gameController.gameSettings.keyBindSneak.getKeyCode())), false);
            }
        }
        else if (s1BPacketEntityAttach.func_149404_c() == 1 && entity instanceof EntityLiving) {
            if (entityByID != null) {
                ((EntityLiving)entity).setLeashedToEntity(entityByID, false);
            }
            else {
                ((EntityLiving)entity).clearLeashed(false, false);
            }
        }
    }
    
    @Override
    public void handleEntityStatus(final S19PacketEntityStatus s19PacketEntityStatus) {
        PacketThreadUtil.func_180031_a(s19PacketEntityStatus, this, this.gameController);
        final Entity func_149161_a = s19PacketEntityStatus.func_149161_a(this.clientWorldController);
        if (func_149161_a != null) {
            if (s19PacketEntityStatus.func_149160_c() == 21) {
                Minecraft.getSoundHandler().playSound(new GuardianSound((EntityGuardian)func_149161_a));
            }
            else {
                func_149161_a.handleHealthUpdate(s19PacketEntityStatus.func_149160_c());
            }
        }
    }
    
    @Override
    public void handleUpdateHealth(final S06PacketUpdateHealth s06PacketUpdateHealth) {
        PacketThreadUtil.func_180031_a(s06PacketUpdateHealth, this, this.gameController);
        Minecraft.thePlayer.setPlayerSPHealth(s06PacketUpdateHealth.getHealth());
        Minecraft.thePlayer.getFoodStats().setFoodLevel(s06PacketUpdateHealth.getFoodLevel());
        Minecraft.thePlayer.getFoodStats().setFoodSaturationLevel(s06PacketUpdateHealth.getSaturationLevel());
    }
    
    @Override
    public void handleSetExperience(final S1FPacketSetExperience s1FPacketSetExperience) {
        PacketThreadUtil.func_180031_a(s1FPacketSetExperience, this, this.gameController);
        Minecraft.thePlayer.setXPStats(s1FPacketSetExperience.func_149397_c(), s1FPacketSetExperience.func_149396_d(), s1FPacketSetExperience.func_149395_e());
    }
    
    @Override
    public void handleRespawn(final S07PacketRespawn s07PacketRespawn) {
        PacketThreadUtil.func_180031_a(s07PacketRespawn, this, this.gameController);
        if (s07PacketRespawn.func_149082_c() != Minecraft.thePlayer.dimension) {
            this.doneLoadingTerrain = false;
            (this.clientWorldController = new WorldClient(this, new WorldSettings(0L, s07PacketRespawn.func_149083_e(), false, Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled(), s07PacketRespawn.func_149080_f()), s07PacketRespawn.func_149082_c(), s07PacketRespawn.func_149081_d(), this.gameController.mcProfiler)).setWorldScoreboard(this.clientWorldController.getScoreboard());
            this.gameController.loadWorld(this.clientWorldController);
            Minecraft.thePlayer.dimension = s07PacketRespawn.func_149082_c();
            this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
        }
        this.gameController.setDimensionAndSpawnPlayer(s07PacketRespawn.func_149082_c());
        Minecraft.playerController.setGameType(s07PacketRespawn.func_149083_e());
    }
    
    @Override
    public void handleExplosion(final S27PacketExplosion s27PacketExplosion) {
        PacketThreadUtil.func_180031_a(s27PacketExplosion, this, this.gameController);
        new Explosion(Minecraft.theWorld, null, s27PacketExplosion.func_149148_f(), s27PacketExplosion.func_149143_g(), s27PacketExplosion.func_149145_h(), s27PacketExplosion.func_149146_i(), s27PacketExplosion.func_149150_j()).doExplosionB(true);
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        thePlayer.motionX += s27PacketExplosion.func_149149_c();
        final EntityPlayerSP thePlayer2 = Minecraft.thePlayer;
        thePlayer2.motionY += s27PacketExplosion.func_149144_d();
        final EntityPlayerSP thePlayer3 = Minecraft.thePlayer;
        thePlayer3.motionZ += s27PacketExplosion.func_149147_e();
    }
    
    @Override
    public void handleOpenWindow(final S2DPacketOpenWindow s2DPacketOpenWindow) {
        PacketThreadUtil.func_180031_a(s2DPacketOpenWindow, this, this.gameController);
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        if ("minecraft:container".equals(s2DPacketOpenWindow.func_148902_e())) {
            thePlayer.displayGUIChest(new InventoryBasic(s2DPacketOpenWindow.func_179840_c(), s2DPacketOpenWindow.func_148898_f()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.func_148901_c();
        }
        else if ("minecraft:villager".equals(s2DPacketOpenWindow.func_148902_e())) {
            thePlayer.displayVillagerTradeGui(new NpcMerchant(thePlayer, s2DPacketOpenWindow.func_179840_c()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.func_148901_c();
        }
        else if ("EntityHorse".equals(s2DPacketOpenWindow.func_148902_e())) {
            final Entity entityByID = this.clientWorldController.getEntityByID(s2DPacketOpenWindow.func_148897_h());
            if (entityByID instanceof EntityHorse) {
                thePlayer.displayGUIHorse((EntityHorse)entityByID, new AnimalChest(s2DPacketOpenWindow.func_179840_c(), s2DPacketOpenWindow.func_148898_f()));
                thePlayer.openContainer.windowId = s2DPacketOpenWindow.func_148901_c();
            }
        }
        else if (!s2DPacketOpenWindow.func_148900_g()) {
            thePlayer.displayGui(new LocalBlockIntercommunication(s2DPacketOpenWindow.func_148902_e(), s2DPacketOpenWindow.func_179840_c()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.func_148901_c();
        }
        else {
            thePlayer.displayGUIChest(new ContainerLocalMenu(s2DPacketOpenWindow.func_148902_e(), s2DPacketOpenWindow.func_179840_c(), s2DPacketOpenWindow.func_148898_f()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.func_148901_c();
        }
    }
    
    @Override
    public void handleSetSlot(final S2FPacketSetSlot s2FPacketSetSlot) {
        PacketThreadUtil.func_180031_a(s2FPacketSetSlot, this, this.gameController);
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        if (s2FPacketSetSlot.func_149175_c() == -1) {
            thePlayer.inventory.setItemStack(s2FPacketSetSlot.func_149174_e());
        }
        else {
            if (this.gameController.currentScreen instanceof GuiContainerCreative) {
                final boolean b = ((GuiContainerCreative)this.gameController.currentScreen).func_147056_g() != CreativeTabs.tabInventory.getTabIndex();
            }
            if (s2FPacketSetSlot.func_149175_c() == 0 && s2FPacketSetSlot.func_149173_d() >= 36 && s2FPacketSetSlot.func_149173_d() < 45) {
                final ItemStack stack = thePlayer.inventoryContainer.getSlot(s2FPacketSetSlot.func_149173_d()).getStack();
                if (s2FPacketSetSlot.func_149174_e() != null && (stack == null || stack.stackSize < s2FPacketSetSlot.func_149174_e().stackSize)) {
                    s2FPacketSetSlot.func_149174_e().animationsToGo = 5;
                }
                thePlayer.inventoryContainer.putStackInSlot(s2FPacketSetSlot.func_149173_d(), s2FPacketSetSlot.func_149174_e());
            }
            else if (s2FPacketSetSlot.func_149175_c() == thePlayer.openContainer.windowId && (s2FPacketSetSlot.func_149175_c() != 0 || !false)) {
                thePlayer.openContainer.putStackInSlot(s2FPacketSetSlot.func_149173_d(), s2FPacketSetSlot.func_149174_e());
            }
        }
    }
    
    @Override
    public void handleConfirmTransaction(final S32PacketConfirmTransaction s32PacketConfirmTransaction) {
        PacketThreadUtil.func_180031_a(s32PacketConfirmTransaction, this, this.gameController);
        Container container = null;
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        if (s32PacketConfirmTransaction.func_148889_c() == 0) {
            container = thePlayer.inventoryContainer;
        }
        else if (s32PacketConfirmTransaction.func_148889_c() == thePlayer.openContainer.windowId) {
            container = thePlayer.openContainer;
        }
        if (container != null && !s32PacketConfirmTransaction.func_148888_e()) {
            this.addToSendQueue(new C0FPacketConfirmTransaction(s32PacketConfirmTransaction.func_148889_c(), s32PacketConfirmTransaction.func_148890_d(), true));
        }
    }
    
    @Override
    public void handleWindowItems(final S30PacketWindowItems s30PacketWindowItems) {
        PacketThreadUtil.func_180031_a(s30PacketWindowItems, this, this.gameController);
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        if (s30PacketWindowItems.func_148911_c() == 0) {
            thePlayer.inventoryContainer.putStacksInSlots(s30PacketWindowItems.func_148910_d());
        }
        else if (s30PacketWindowItems.func_148911_c() == thePlayer.openContainer.windowId) {
            thePlayer.openContainer.putStacksInSlots(s30PacketWindowItems.func_148910_d());
        }
    }
    
    @Override
    public void handleSignEditorOpen(final S36PacketSignEditorOpen s36PacketSignEditorOpen) {
        PacketThreadUtil.func_180031_a(s36PacketSignEditorOpen, this, this.gameController);
        TileEntity tileEntity = this.clientWorldController.getTileEntity(s36PacketSignEditorOpen.func_179777_a());
        if (!(tileEntity instanceof TileEntitySign)) {
            tileEntity = new TileEntitySign();
            ((TileEntitySign)tileEntity).setWorldObj(this.clientWorldController);
            ((TileEntitySign)tileEntity).setPos(s36PacketSignEditorOpen.func_179777_a());
        }
        Minecraft.thePlayer.func_175141_a((TileEntitySign)tileEntity);
    }
    
    @Override
    public void handleUpdateSign(final S33PacketUpdateSign s33PacketUpdateSign) {
        PacketThreadUtil.func_180031_a(s33PacketUpdateSign, this, this.gameController);
        if (Minecraft.theWorld.isBlockLoaded(s33PacketUpdateSign.func_179704_a())) {
            final TileEntity tileEntity = Minecraft.theWorld.getTileEntity(s33PacketUpdateSign.func_179704_a());
            if (tileEntity instanceof TileEntitySign) {
                final TileEntitySign tileEntitySign = (TileEntitySign)tileEntity;
                if (tileEntitySign.getIsEditable()) {
                    while (0 != 4) {
                        if (s33PacketUpdateSign.getLines()[0] != null) {
                            if (s33PacketUpdateSign.getLines()[0].getUnformattedText().length() > 80) {}
                            final int n = 0 + s33PacketUpdateSign.getLines()[0].getUnformattedText().length();
                        }
                        int n2 = 0;
                        ++n2;
                    }
                    if (true) {
                        final String[] array = { "", "§6Karakterek:", "§7" + 0, "" };
                        final Client instance = Client.INSTANCE;
                        if (Client.getModuleByName("ExploitFixer").toggled) {
                            final String[] array2 = { "§8§l[§6§lExploitFixer§8§l]", "§6Karakterek:", "§7" + 0, "" };
                            ((TileEntitySign)tileEntity).signText = new IChatComponent[] { new ChatComponentText(array2[0]), new ChatComponentText(array2[1]), new ChatComponentText(array2[2]), new ChatComponentText(array2[3]) };
                        }
                        else {
                            System.arraycopy(s33PacketUpdateSign.getLines(), 0, tileEntitySign.signText, 0, 4);
                        }
                        tileEntitySign.markDirty();
                    }
                }
            }
        }
    }
    
    @Override
    public void handleUpdateTileEntity(final S35PacketUpdateTileEntity s35PacketUpdateTileEntity) {
        PacketThreadUtil.func_180031_a(s35PacketUpdateTileEntity, this, this.gameController);
        if (Minecraft.theWorld.isBlockLoaded(s35PacketUpdateTileEntity.func_179823_a())) {
            final TileEntity tileEntity = Minecraft.theWorld.getTileEntity(s35PacketUpdateTileEntity.func_179823_a());
            final int tileEntityType = s35PacketUpdateTileEntity.getTileEntityType();
            if ((tileEntityType == 1 && tileEntity instanceof TileEntityMobSpawner) || (tileEntityType == 2 && tileEntity instanceof TileEntityCommandBlock) || (tileEntityType == 3 && tileEntity instanceof TileEntityBeacon) || (tileEntityType == 4 && tileEntity instanceof TileEntitySkull) || (tileEntityType == 5 && tileEntity instanceof TileEntityFlowerPot) || (tileEntityType == 6 && tileEntity instanceof TileEntityBanner)) {
                tileEntity.readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
            }
        }
    }
    
    @Override
    public void handleWindowProperty(final S31PacketWindowProperty s31PacketWindowProperty) {
        PacketThreadUtil.func_180031_a(s31PacketWindowProperty, this, this.gameController);
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        if (thePlayer.openContainer != null && thePlayer.openContainer.windowId == s31PacketWindowProperty.func_149182_c()) {
            thePlayer.openContainer.updateProgressBar(s31PacketWindowProperty.func_149181_d(), s31PacketWindowProperty.func_149180_e());
        }
    }
    
    @Override
    public void handleEntityEquipment(final S04PacketEntityEquipment s04PacketEntityEquipment) {
        PacketThreadUtil.func_180031_a(s04PacketEntityEquipment, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s04PacketEntityEquipment.func_149389_d());
        if (entityByID != null) {
            entityByID.setCurrentItemOrArmor(s04PacketEntityEquipment.func_149388_e(), s04PacketEntityEquipment.func_149390_c());
        }
    }
    
    @Override
    public void handleCloseWindow(final S2EPacketCloseWindow s2EPacketCloseWindow) {
        PacketThreadUtil.func_180031_a(s2EPacketCloseWindow, this, this.gameController);
        Minecraft.thePlayer.func_175159_q();
    }
    
    @Override
    public void handleBlockAction(final S24PacketBlockAction s24PacketBlockAction) {
        WDLHooks.onNHPCHandleBlockAction(this, s24PacketBlockAction);
        PacketThreadUtil.func_180031_a(s24PacketBlockAction, this, this.gameController);
        Minecraft.theWorld.addBlockEvent(s24PacketBlockAction.func_179825_a(), s24PacketBlockAction.getBlockType(), s24PacketBlockAction.getData1(), s24PacketBlockAction.getData2());
    }
    
    @Override
    public void handleBlockBreakAnim(final S25PacketBlockBreakAnim s25PacketBlockBreakAnim) {
        PacketThreadUtil.func_180031_a(s25PacketBlockBreakAnim, this, this.gameController);
        Minecraft.theWorld.sendBlockBreakProgress(s25PacketBlockBreakAnim.func_148845_c(), s25PacketBlockBreakAnim.func_179821_b(), s25PacketBlockBreakAnim.func_148846_g());
    }
    
    @Override
    public void handleMapChunkBulk(final S26PacketMapChunkBulk s26PacketMapChunkBulk) {
        PacketThreadUtil.func_180031_a(s26PacketMapChunkBulk, this, this.gameController);
        while (0 < s26PacketMapChunkBulk.func_149254_d()) {
            final int func_149255_a = s26PacketMapChunkBulk.func_149255_a(0);
            final int func_149253_b = s26PacketMapChunkBulk.func_149253_b(0);
            this.clientWorldController.doPreChunk(func_149255_a, func_149253_b, true);
            this.clientWorldController.invalidateBlockReceiveRegion(func_149255_a << 4, 0, func_149253_b << 4, (func_149255_a << 4) + 15, 256, (func_149253_b << 4) + 15);
            final Chunk chunkFromChunkCoords = this.clientWorldController.getChunkFromChunkCoords(func_149255_a, func_149253_b);
            chunkFromChunkCoords.func_177439_a(s26PacketMapChunkBulk.func_149256_c(0), s26PacketMapChunkBulk.func_179754_d(0), true);
            this.clientWorldController.markBlockRangeForRenderUpdate(func_149255_a << 4, 0, func_149253_b << 4, (func_149255_a << 4) + 15, 256, (func_149253_b << 4) + 15);
            if (!(this.clientWorldController.provider instanceof WorldProviderSurface)) {
                chunkFromChunkCoords.resetRelightChecks();
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void handleChangeGameState(final S2BPacketChangeGameState s2BPacketChangeGameState) {
        PacketThreadUtil.func_180031_a(s2BPacketChangeGameState, this, this.gameController);
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        final int func_149138_c = s2BPacketChangeGameState.func_149138_c();
        final float func_149137_d = s2BPacketChangeGameState.func_149137_d();
        final int floor_float = MathHelper.floor_float(func_149137_d + 0.5f);
        if (func_149138_c >= 0 && func_149138_c < S2BPacketChangeGameState.MESSAGE_NAMES.length && S2BPacketChangeGameState.MESSAGE_NAMES[func_149138_c] != null) {
            thePlayer.addChatComponentMessage(new ChatComponentTranslation(S2BPacketChangeGameState.MESSAGE_NAMES[func_149138_c], new Object[0]));
        }
        if (func_149138_c == 1) {
            this.clientWorldController.getWorldInfo().setRaining(true);
            this.clientWorldController.setRainStrength(0.0f);
        }
        else if (func_149138_c == 2) {
            this.clientWorldController.getWorldInfo().setRaining(false);
            this.clientWorldController.setRainStrength(1.0f);
        }
        else if (func_149138_c == 3) {
            Minecraft.playerController.setGameType(WorldSettings.GameType.getByID(floor_float));
        }
        else if (func_149138_c == 4) {
            this.gameController.displayGuiScreen(new GuiWinGame());
        }
        else if (func_149138_c == 5) {
            final GameSettings gameSettings = this.gameController.gameSettings;
            if (func_149137_d == 0.0f) {
                this.gameController.displayGuiScreen(new GuiScreenDemo());
            }
            else if (func_149137_d == 101.0f) {
                Minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation("demo.help.movement", new Object[] { GameSettings.getKeyDisplayString(gameSettings.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(gameSettings.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(gameSettings.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(gameSettings.keyBindRight.getKeyCode()) }));
            }
            else if (func_149137_d == 102.0f) {
                Minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(gameSettings.keyBindJump.getKeyCode()) }));
            }
            else if (func_149137_d == 103.0f) {
                Minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(gameSettings.keyBindInventory.getKeyCode()) }));
            }
        }
        else if (func_149138_c == 6) {
            this.clientWorldController.playSound(thePlayer.posX, thePlayer.posY + thePlayer.getEyeHeight(), thePlayer.posZ, "random.successful_hit", 0.18f, 0.45f, false);
        }
        else if (func_149138_c == 7) {
            this.clientWorldController.setRainStrength(func_149137_d);
        }
        else if (func_149138_c == 8) {
            this.clientWorldController.setThunderStrength(func_149137_d);
        }
        else if (func_149138_c == 10) {
            this.clientWorldController.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, thePlayer.posX, thePlayer.posY, thePlayer.posZ, 0.0, 0.0, 0.0, new int[0]);
            this.clientWorldController.playSound(thePlayer.posX, thePlayer.posY, thePlayer.posZ, "mob.guardian.curse", 1.0f, 1.0f, false);
        }
    }
    
    @Override
    public void handleMaps(final S34PacketMaps s34PacketMaps) {
        WDLHooks.onNHPCHandleMaps(this, s34PacketMaps);
        PacketThreadUtil.func_180031_a(s34PacketMaps, this, this.gameController);
        final MapData loadMapData = ItemMap.loadMapData(s34PacketMaps.getMapId(), Minecraft.theWorld);
        s34PacketMaps.func_179734_a(loadMapData);
        this.gameController.entityRenderer.getMapItemRenderer().func_148246_a(loadMapData);
    }
    
    @Override
    public void handleEffect(final S28PacketEffect s28PacketEffect) {
        PacketThreadUtil.func_180031_a(s28PacketEffect, this, this.gameController);
        if (s28PacketEffect.isSoundServerwide()) {
            Minecraft.theWorld.func_175669_a(s28PacketEffect.getSoundType(), s28PacketEffect.func_179746_d(), s28PacketEffect.getSoundData());
        }
        else {
            Minecraft.theWorld.playAuxSFX(s28PacketEffect.getSoundType(), s28PacketEffect.func_179746_d(), s28PacketEffect.getSoundData());
        }
    }
    
    @Override
    public void handleStatistics(final S37PacketStatistics s37PacketStatistics) {
        PacketThreadUtil.func_180031_a(s37PacketStatistics, this, this.gameController);
        for (final Map.Entry<StatBase, V> entry : s37PacketStatistics.func_148974_c().entrySet()) {
            final StatBase statBase = entry.getKey();
            final int intValue = (int)entry.getValue();
            if (statBase.isAchievement() && intValue > 0 && this.field_147308_k && Minecraft.thePlayer.getStatFileWriter().writeStat(statBase) == 0) {
                final Achievement achievement = (Achievement)statBase;
                this.gameController.guiAchievement.displayAchievement(achievement);
                this.gameController.getTwitchStream().func_152911_a(new MetadataAchievement(achievement), 0L);
                if (statBase == AchievementList.openInventory) {
                    this.gameController.gameSettings.showInventoryAchievementHint = false;
                    this.gameController.gameSettings.saveOptions();
                }
            }
            Minecraft.thePlayer.getStatFileWriter().func_150873_a(Minecraft.thePlayer, statBase, intValue);
        }
        if (!this.field_147308_k && !true && this.gameController.gameSettings.showInventoryAchievementHint) {
            this.gameController.guiAchievement.displayUnformattedAchievement(AchievementList.openInventory);
        }
        this.field_147308_k = true;
        if (this.gameController.currentScreen instanceof IProgressMeter) {
            ((IProgressMeter)this.gameController.currentScreen).doneLoading();
        }
    }
    
    @Override
    public void handleEntityEffect(final S1DPacketEntityEffect s1DPacketEntityEffect) {
        PacketThreadUtil.func_180031_a(s1DPacketEntityEffect, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s1DPacketEntityEffect.func_149426_d());
        if (entityByID instanceof EntityLivingBase) {
            final PotionEffect potionEffect = new PotionEffect(s1DPacketEntityEffect.func_149427_e(), s1DPacketEntityEffect.func_180755_e(), s1DPacketEntityEffect.func_149428_f(), false, s1DPacketEntityEffect.func_179707_f());
            potionEffect.setPotionDurationMax(s1DPacketEntityEffect.func_149429_c());
            ((EntityLivingBase)entityByID).addPotionEffect(potionEffect);
        }
    }
    
    @Override
    public void func_175098_a(final S42PacketCombatEvent s42PacketCombatEvent) {
        PacketThreadUtil.func_180031_a(s42PacketCombatEvent, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s42PacketCombatEvent.field_179775_c);
        final EntityLivingBase entityLivingBase = (entityByID instanceof EntityLivingBase) ? ((EntityLivingBase)entityByID) : null;
        if (s42PacketCombatEvent.field_179776_a == S42PacketCombatEvent.Event.END_COMBAT) {
            this.gameController.getTwitchStream().func_176026_a(new MetadataCombat(Minecraft.thePlayer, entityLivingBase), 0L - 1000 * s42PacketCombatEvent.field_179772_d / 20, 0L);
        }
        else if (s42PacketCombatEvent.field_179776_a == S42PacketCombatEvent.Event.ENTITY_DIED) {
            final Entity entityByID2 = this.clientWorldController.getEntityByID(s42PacketCombatEvent.field_179774_b);
            if (entityByID2 instanceof EntityPlayer) {
                final MetadataPlayerDeath metadataPlayerDeath = new MetadataPlayerDeath((EntityLivingBase)entityByID2, entityLivingBase);
                metadataPlayerDeath.func_152807_a(s42PacketCombatEvent.field_179773_e);
                this.gameController.getTwitchStream().func_152911_a(metadataPlayerDeath, 0L);
            }
        }
    }
    
    @Override
    public void func_175101_a(final S41PacketServerDifficulty s41PacketServerDifficulty) {
        PacketThreadUtil.func_180031_a(s41PacketServerDifficulty, this, this.gameController);
        Minecraft.theWorld.getWorldInfo().setDifficulty(s41PacketServerDifficulty.func_179831_b());
        Minecraft.theWorld.getWorldInfo().setDifficultyLocked(s41PacketServerDifficulty.func_179830_a());
    }
    
    @Override
    public void func_175094_a(final S43PacketCamera s43PacketCamera) {
        PacketThreadUtil.func_180031_a(s43PacketCamera, this, this.gameController);
        final Entity func_179780_a = s43PacketCamera.func_179780_a(this.clientWorldController);
        if (func_179780_a != null) {
            this.gameController.func_175607_a(func_179780_a);
        }
    }
    
    @Override
    public void func_175093_a(final S44PacketWorldBorder s44PacketWorldBorder) {
        PacketThreadUtil.func_180031_a(s44PacketWorldBorder, this, this.gameController);
        s44PacketWorldBorder.func_179788_a(this.clientWorldController.getWorldBorder());
    }
    
    @Override
    public void func_175099_a(final S45PacketTitle s45PacketTitle) {
        PacketThreadUtil.func_180031_a(s45PacketTitle, this, this.gameController);
        final S45PacketTitle.Type func_179807_a = s45PacketTitle.func_179807_a();
        String s = null;
        String s2 = null;
        final String s3 = (s45PacketTitle.func_179805_b() != null) ? s45PacketTitle.func_179805_b().getFormattedText() : "";
        switch (SwitchAction.field_178885_a[func_179807_a.ordinal()]) {
            case 1: {
                s = s3;
                break;
            }
            case 2: {
                s2 = s3;
                break;
            }
            case 3: {
                Minecraft.ingameGUI.func_175178_a("", "", -1, -1, -1);
                Minecraft.ingameGUI.func_175177_a();
                return;
            }
        }
        Minecraft.ingameGUI.func_175178_a(s, s2, s45PacketTitle.func_179806_c(), s45PacketTitle.func_179804_d(), s45PacketTitle.func_179803_e());
    }
    
    @Override
    public void func_175100_a(final S46PacketSetCompressionLevel s46PacketSetCompressionLevel) {
        if (!this.netManager.isLocalChannel()) {
            this.netManager.setCompressionTreshold(s46PacketSetCompressionLevel.func_179760_a());
        }
    }
    
    @Override
    public void func_175096_a(final S47PacketPlayerListHeaderFooter s47PacketPlayerListHeaderFooter) {
        Minecraft.ingameGUI.getTabList().setHeader((s47PacketPlayerListHeaderFooter.func_179700_a().getFormattedText().length() == 0) ? null : s47PacketPlayerListHeaderFooter.func_179700_a());
        Minecraft.ingameGUI.getTabList().setFooter((s47PacketPlayerListHeaderFooter.func_179701_b().getFormattedText().length() == 0) ? null : s47PacketPlayerListHeaderFooter.func_179701_b());
    }
    
    @Override
    public void handleRemoveEntityEffect(final S1EPacketRemoveEntityEffect s1EPacketRemoveEntityEffect) {
        PacketThreadUtil.func_180031_a(s1EPacketRemoveEntityEffect, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s1EPacketRemoveEntityEffect.func_149076_c());
        if (entityByID instanceof EntityLivingBase) {
            ((EntityLivingBase)entityByID).removePotionEffectClient(s1EPacketRemoveEntityEffect.func_149075_d());
        }
    }
    
    @Override
    public void handlePlayerListItem(final S38PacketPlayerListItem s38PacketPlayerListItem) {
        PacketThreadUtil.func_180031_a(s38PacketPlayerListItem, this, this.gameController);
        for (final S38PacketPlayerListItem.AddPlayerData addPlayerData : s38PacketPlayerListItem.func_179767_a()) {
            if (s38PacketPlayerListItem.func_179768_b() == S38PacketPlayerListItem.Action.REMOVE_PLAYER) {
                this.playerInfoMap.remove(addPlayerData.func_179962_a().getId());
            }
            else {
                NetworkPlayerInfo networkPlayerInfo = this.playerInfoMap.get(addPlayerData.func_179962_a().getId());
                if (s38PacketPlayerListItem.func_179768_b() == S38PacketPlayerListItem.Action.ADD_PLAYER) {
                    networkPlayerInfo = new NetworkPlayerInfo(addPlayerData);
                    this.playerInfoMap.put(networkPlayerInfo.func_178845_a().getId(), networkPlayerInfo);
                }
                if (networkPlayerInfo == null) {
                    continue;
                }
                switch (SwitchAction.field_178884_b[s38PacketPlayerListItem.func_179768_b().ordinal()]) {
                    default: {
                        continue;
                    }
                    case 1: {
                        networkPlayerInfo.func_178839_a(addPlayerData.func_179960_c());
                        networkPlayerInfo.func_178838_a(addPlayerData.func_179963_b());
                        continue;
                    }
                    case 2: {
                        networkPlayerInfo.func_178839_a(addPlayerData.func_179960_c());
                        continue;
                    }
                    case 3: {
                        networkPlayerInfo.func_178838_a(addPlayerData.func_179963_b());
                        continue;
                    }
                    case 4: {
                        networkPlayerInfo.func_178859_a(addPlayerData.func_179961_d());
                        continue;
                    }
                }
            }
        }
    }
    
    @Override
    public void handleKeepAlive(final S00PacketKeepAlive s00PacketKeepAlive) {
        this.addToSendQueue(new C00PacketKeepAlive(s00PacketKeepAlive.func_149134_c()));
    }
    
    @Override
    public void handlePlayerAbilities(final S39PacketPlayerAbilities s39PacketPlayerAbilities) {
        PacketThreadUtil.func_180031_a(s39PacketPlayerAbilities, this, this.gameController);
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        thePlayer.capabilities.isFlying = s39PacketPlayerAbilities.isFlying();
        thePlayer.capabilities.isCreativeMode = s39PacketPlayerAbilities.isCreativeMode();
        thePlayer.capabilities.disableDamage = s39PacketPlayerAbilities.isInvulnerable();
        thePlayer.capabilities.allowFlying = s39PacketPlayerAbilities.isAllowFlying();
        thePlayer.capabilities.setFlySpeed(s39PacketPlayerAbilities.getFlySpeed());
        thePlayer.capabilities.setPlayerWalkSpeed(s39PacketPlayerAbilities.getWalkSpeed());
    }
    
    @Override
    public void handleTabComplete(final S3APacketTabComplete s3APacketTabComplete) {
        PacketThreadUtil.func_180031_a(s3APacketTabComplete, this, this.gameController);
        final String[] func_149630_c = s3APacketTabComplete.func_149630_c();
        if (this.gameController.currentScreen instanceof GuiChat) {
            ((GuiChat)this.gameController.currentScreen).onAutocompleteResponse(func_149630_c);
        }
    }
    
    @Override
    public void handleSoundEffect(final S29PacketSoundEffect s29PacketSoundEffect) {
        PacketThreadUtil.func_180031_a(s29PacketSoundEffect, this, this.gameController);
        Minecraft.theWorld.playSound(s29PacketSoundEffect.func_149207_d(), s29PacketSoundEffect.func_149211_e(), s29PacketSoundEffect.func_149210_f(), s29PacketSoundEffect.func_149212_c(), s29PacketSoundEffect.func_149208_g(), s29PacketSoundEffect.func_149209_h(), false);
    }
    
    @Override
    public void func_175095_a(final S48PacketResourcePackSend s48PacketResourcePackSend) {
        final String func_179783_a = s48PacketResourcePackSend.func_179783_a();
        final String func_179784_b = s48PacketResourcePackSend.func_179784_b();
        if (func_179783_a.startsWith("level://")) {
            final File file = new File(new File(Minecraft.mcDataDir, "saves"), func_179783_a.substring(8));
            if (file.isFile()) {
                this.netManager.sendPacket(new C19PacketResourcePackStatus(func_179784_b, C19PacketResourcePackStatus.Action.ACCEPTED));
                Futures.addCallback(this.gameController.getResourcePackRepository().func_177319_a(file), new FutureCallback(func_179784_b) {
                    private static final String __OBFID;
                    final NetHandlerPlayClient this$0;
                    private final String val$var3;
                    
                    @Override
                    public void onSuccess(final Object o) {
                        NetHandlerPlayClient.access$0(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                    }
                    
                    @Override
                    public void onFailure(final Throwable t) {
                        NetHandlerPlayClient.access$0(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                    }
                    
                    static {
                        __OBFID = "CL_00000879";
                    }
                });
            }
            else {
                this.netManager.sendPacket(new C19PacketResourcePackStatus(func_179784_b, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
            }
        }
        else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(func_179784_b, C19PacketResourcePackStatus.Action.ACCEPTED));
            Futures.addCallback(this.gameController.getResourcePackRepository().func_180601_a(func_179783_a, func_179784_b), new FutureCallback(func_179784_b) {
                private static final String __OBFID;
                final NetHandlerPlayClient this$0;
                private final String val$var3;
                
                @Override
                public void onSuccess(final Object o) {
                    NetHandlerPlayClient.access$0(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                }
                
                @Override
                public void onFailure(final Throwable t) {
                    NetHandlerPlayClient.access$0(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                }
                
                static {
                    __OBFID = "CL_00002624";
                }
            });
        }
        else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(func_179784_b, C19PacketResourcePackStatus.Action.DECLINED));
        }
        else {
            this.gameController.addScheduledTask(new Runnable(func_179784_b, func_179783_a) {
                private static final String __OBFID;
                final NetHandlerPlayClient this$0;
                private final String val$var3;
                private final String val$var2;
                
                @Override
                public void run() {
                    NetHandlerPlayClient.access$1(this.this$0).displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(this.val$var3, this.val$var2) {
                        private static final String __OBFID;
                        final NetHandlerPlayClient$3 this$1;
                        private final String val$var3;
                        private final String val$var2;
                        
                        @Override
                        public void confirmClicked(final boolean b, final int n) {
                            NetHandlerPlayClient.access$2(NetHandlerPlayClient$3.access$0(this.this$1), Minecraft.getMinecraft());
                            if (b) {
                                if (NetHandlerPlayClient.access$1(NetHandlerPlayClient$3.access$0(this.this$1)).getCurrentServerData() != null) {
                                    NetHandlerPlayClient.access$1(NetHandlerPlayClient$3.access$0(this.this$1)).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.ENABLED);
                                }
                                NetHandlerPlayClient.access$0(NetHandlerPlayClient$3.access$0(this.this$1)).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.ACCEPTED));
                                Futures.addCallback(NetHandlerPlayClient.access$1(NetHandlerPlayClient$3.access$0(this.this$1)).getResourcePackRepository().func_180601_a(this.val$var2, this.val$var3), new FutureCallback(this.val$var3) {
                                    private static final String __OBFID;
                                    final NetHandlerPlayClient$3$1 this$2;
                                    private final String val$var3;
                                    
                                    @Override
                                    public void onSuccess(final Object o) {
                                        NetHandlerPlayClient.access$0(NetHandlerPlayClient$3.access$0(NetHandlerPlayClient$3$1.access$0(this.this$2))).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                                    }
                                    
                                    @Override
                                    public void onFailure(final Throwable t) {
                                        NetHandlerPlayClient.access$0(NetHandlerPlayClient$3.access$0(NetHandlerPlayClient$3$1.access$0(this.this$2))).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                                    }
                                    
                                    static {
                                        __OBFID = "CL_00002621";
                                    }
                                });
                            }
                            else {
                                if (NetHandlerPlayClient.access$1(NetHandlerPlayClient$3.access$0(this.this$1)).getCurrentServerData() != null) {
                                    NetHandlerPlayClient.access$1(NetHandlerPlayClient$3.access$0(this.this$1)).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.DISABLED);
                                }
                                NetHandlerPlayClient.access$0(NetHandlerPlayClient$3.access$0(this.this$1)).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.DECLINED));
                            }
                            ServerList.func_147414_b(NetHandlerPlayClient.access$1(NetHandlerPlayClient$3.access$0(this.this$1)).getCurrentServerData());
                            NetHandlerPlayClient.access$1(NetHandlerPlayClient$3.access$0(this.this$1)).displayGuiScreen(null);
                        }
                        
                        static NetHandlerPlayClient$3 access$0(final NetHandlerPlayClient$3$1 guiYesNoCallback) {
                            return guiYesNoCallback.this$1;
                        }
                        
                        static {
                            __OBFID = "CL_00002622";
                        }
                    }, I18n.format("multiplayer.texturePrompt.line1", new Object[0]), I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0));
                }
                
                static NetHandlerPlayClient access$0(final NetHandlerPlayClient$3 runnable) {
                    return runnable.this$0;
                }
                
                static {
                    __OBFID = "CL_00002623";
                }
            });
        }
    }
    
    @Override
    public void func_175097_a(final S49PacketUpdateEntityNBT s49PacketUpdateEntityNBT) {
        PacketThreadUtil.func_180031_a(s49PacketUpdateEntityNBT, this, this.gameController);
        final Entity func_179764_a = s49PacketUpdateEntityNBT.func_179764_a(this.clientWorldController);
        if (func_179764_a != null) {
            func_179764_a.func_174834_g(s49PacketUpdateEntityNBT.func_179763_a());
        }
    }
    
    @Override
    public void handleCustomPayload(final S3FPacketCustomPayload s3FPacketCustomPayload) {
        WDLHooks.onNHPCHandleCustomPayload(this, s3FPacketCustomPayload);
        PacketThreadUtil.func_180031_a(s3FPacketCustomPayload, this, this.gameController);
        if ("MC|TrList".equals(s3FPacketCustomPayload.getChannelName())) {
            final PacketBuffer bufferData = s3FPacketCustomPayload.getBufferData();
            final int int1 = bufferData.readInt();
            final GuiScreen currentScreen = this.gameController.currentScreen;
            if (currentScreen != null && currentScreen instanceof GuiMerchant && int1 == Minecraft.thePlayer.openContainer.windowId) {
                ((GuiMerchant)currentScreen).getMerchant().setRecipes(MerchantRecipeList.func_151390_b(bufferData));
            }
            bufferData.release();
        }
        else if ("MC|Brand".equals(s3FPacketCustomPayload.getChannelName())) {
            Minecraft.thePlayer.func_175158_f(s3FPacketCustomPayload.getBufferData().readStringFromBuffer(32767));
        }
        else if ("MC|BOpen".equals(s3FPacketCustomPayload.getChannelName())) {
            final ItemStack currentEquippedItem = Minecraft.thePlayer.getCurrentEquippedItem();
            if (currentEquippedItem != null && currentEquippedItem.getItem() == Items.written_book) {
                this.gameController.displayGuiScreen(new GuiScreenBook(Minecraft.thePlayer, currentEquippedItem, false));
            }
        }
    }
    
    @Override
    public void handleScoreboardObjective(final S3BPacketScoreboardObjective s3BPacketScoreboardObjective) {
        PacketThreadUtil.func_180031_a(s3BPacketScoreboardObjective, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        if (s3BPacketScoreboardObjective.func_149338_e() == 0) {
            final ScoreObjective addScoreObjective = scoreboard.addScoreObjective(s3BPacketScoreboardObjective.func_149339_c(), IScoreObjectiveCriteria.DUMMY);
            addScoreObjective.setDisplayName(s3BPacketScoreboardObjective.func_149337_d());
            addScoreObjective.func_178767_a(s3BPacketScoreboardObjective.func_179817_d());
        }
        else {
            final ScoreObjective objective = scoreboard.getObjective(s3BPacketScoreboardObjective.func_149339_c());
            if (s3BPacketScoreboardObjective.func_149338_e() == 1) {
                scoreboard.func_96519_k(objective);
            }
            else if (s3BPacketScoreboardObjective.func_149338_e() == 2) {
                objective.setDisplayName(s3BPacketScoreboardObjective.func_149337_d());
                objective.func_178767_a(s3BPacketScoreboardObjective.func_179817_d());
            }
        }
    }
    
    @Override
    public void handleUpdateScore(final S3CPacketUpdateScore s3CPacketUpdateScore) {
        PacketThreadUtil.func_180031_a(s3CPacketUpdateScore, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        final ScoreObjective objective = scoreboard.getObjective(s3CPacketUpdateScore.func_149321_d());
        if (s3CPacketUpdateScore.func_180751_d() == S3CPacketUpdateScore.Action.CHANGE) {
            scoreboard.getValueFromObjective(s3CPacketUpdateScore.func_149324_c(), objective).setScorePoints(s3CPacketUpdateScore.func_149323_e());
        }
        else if (s3CPacketUpdateScore.func_180751_d() == S3CPacketUpdateScore.Action.REMOVE) {
            if (StringUtils.isNullOrEmpty(s3CPacketUpdateScore.func_149321_d())) {
                scoreboard.func_178822_d(s3CPacketUpdateScore.func_149324_c(), null);
            }
            else if (objective != null) {
                scoreboard.func_178822_d(s3CPacketUpdateScore.func_149324_c(), objective);
            }
        }
    }
    
    @Override
    public void handleDisplayScoreboard(final S3DPacketDisplayScoreboard s3DPacketDisplayScoreboard) {
        PacketThreadUtil.func_180031_a(s3DPacketDisplayScoreboard, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        if (s3DPacketDisplayScoreboard.func_149370_d().length() == 0) {
            scoreboard.setObjectiveInDisplaySlot(s3DPacketDisplayScoreboard.func_149371_c(), null);
        }
        else {
            scoreboard.setObjectiveInDisplaySlot(s3DPacketDisplayScoreboard.func_149371_c(), scoreboard.getObjective(s3DPacketDisplayScoreboard.func_149370_d()));
        }
    }
    
    @Override
    public void handleTeams(final S3EPacketTeams s3EPacketTeams) {
        PacketThreadUtil.func_180031_a(s3EPacketTeams, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        ScorePlayerTeam scorePlayerTeam;
        if (s3EPacketTeams.func_149307_h() == 0) {
            scorePlayerTeam = scoreboard.createTeam(s3EPacketTeams.func_149312_c());
        }
        else {
            scorePlayerTeam = scoreboard.getTeam(s3EPacketTeams.func_149312_c());
        }
        if (s3EPacketTeams.func_149307_h() == 0 || s3EPacketTeams.func_149307_h() == 2) {
            scorePlayerTeam.setTeamName(s3EPacketTeams.func_149306_d());
            scorePlayerTeam.setNamePrefix(s3EPacketTeams.func_149311_e());
            scorePlayerTeam.setNameSuffix(s3EPacketTeams.func_149309_f());
            scorePlayerTeam.func_178774_a(EnumChatFormatting.func_175744_a(s3EPacketTeams.func_179813_h()));
            scorePlayerTeam.func_98298_a(s3EPacketTeams.func_149308_i());
            final Team.EnumVisible func_178824_a = Team.EnumVisible.func_178824_a(s3EPacketTeams.func_179814_i());
            if (func_178824_a != null) {
                scorePlayerTeam.func_178772_a(func_178824_a);
            }
        }
        if (s3EPacketTeams.func_149307_h() == 0 || s3EPacketTeams.func_149307_h() == 3) {
            final Iterator<String> iterator = s3EPacketTeams.func_149310_g().iterator();
            while (iterator.hasNext()) {
                scoreboard.func_151392_a(iterator.next(), s3EPacketTeams.func_149312_c());
            }
        }
        if (s3EPacketTeams.func_149307_h() == 4) {
            final Iterator<String> iterator2 = s3EPacketTeams.func_149310_g().iterator();
            while (iterator2.hasNext()) {
                scoreboard.removePlayerFromTeam(iterator2.next(), scorePlayerTeam);
            }
        }
        if (s3EPacketTeams.func_149307_h() == 1) {
            scoreboard.removeTeam(scorePlayerTeam);
        }
    }
    
    @Override
    public void handleParticles(final S2APacketParticles s2APacketParticles) {
        PacketThreadUtil.func_180031_a(s2APacketParticles, this, this.gameController);
        if (s2APacketParticles.func_149222_k() == 0) {
            this.clientWorldController.spawnParticle(s2APacketParticles.func_179749_a(), s2APacketParticles.func_179750_b(), s2APacketParticles.func_149220_d(), s2APacketParticles.func_149226_e(), s2APacketParticles.func_149225_f(), s2APacketParticles.func_149227_j() * s2APacketParticles.func_149221_g(), s2APacketParticles.func_149227_j() * s2APacketParticles.func_149224_h(), s2APacketParticles.func_149227_j() * s2APacketParticles.func_149223_i(), s2APacketParticles.func_179748_k());
        }
        else {
            while (0 < s2APacketParticles.func_149222_k()) {
                this.clientWorldController.spawnParticle(s2APacketParticles.func_179749_a(), s2APacketParticles.func_179750_b(), s2APacketParticles.func_149220_d() + this.avRandomizer.nextGaussian() * s2APacketParticles.func_149221_g(), s2APacketParticles.func_149226_e() + this.avRandomizer.nextGaussian() * s2APacketParticles.func_149224_h(), s2APacketParticles.func_149225_f() + this.avRandomizer.nextGaussian() * s2APacketParticles.func_149223_i(), this.avRandomizer.nextGaussian() * s2APacketParticles.func_149227_j(), this.avRandomizer.nextGaussian() * s2APacketParticles.func_149227_j(), this.avRandomizer.nextGaussian() * s2APacketParticles.func_149227_j(), s2APacketParticles.func_179748_k());
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public void handleEntityProperties(final S20PacketEntityProperties s20PacketEntityProperties) {
        PacketThreadUtil.func_180031_a(s20PacketEntityProperties, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s20PacketEntityProperties.func_149442_c());
        if (entityByID != null) {
            if (!(entityByID instanceof EntityLivingBase)) {
                throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entityByID + ")");
            }
            final BaseAttributeMap attributeMap = ((EntityLivingBase)entityByID).getAttributeMap();
            for (final S20PacketEntityProperties.Snapshot snapshot : s20PacketEntityProperties.func_149441_d()) {
                IAttributeInstance attributeInstance = attributeMap.getAttributeInstanceByName(snapshot.func_151409_a());
                if (attributeInstance == null) {
                    attributeInstance = attributeMap.registerAttribute(new RangedAttribute(null, snapshot.func_151409_a(), 0.0, Double.MIN_NORMAL, Double.MAX_VALUE));
                }
                attributeInstance.setBaseValue(snapshot.func_151410_b());
                attributeInstance.removeAllModifiers();
                final Iterator iterator2 = snapshot.func_151408_c().iterator();
                while (iterator2.hasNext()) {
                    attributeInstance.applyModifier(iterator2.next());
                }
            }
        }
    }
    
    public NetworkManager getNetworkManager() {
        return this.netManager;
    }
    
    public Collection func_175106_d() {
        return this.playerInfoMap.values();
    }
    
    public NetworkPlayerInfo func_175102_a(final UUID uuid) {
        return this.playerInfoMap.get(uuid);
    }
    
    public NetworkPlayerInfo func_175104_a(final String s) {
        for (final NetworkPlayerInfo networkPlayerInfo : this.playerInfoMap.values()) {
            if (networkPlayerInfo.func_178845_a().getName().equals(s)) {
                return networkPlayerInfo;
            }
        }
        return null;
    }
    
    public GameProfile func_175105_e() {
        return this.field_175107_d;
    }
    
    public Collection getPlayerInfoMap() {
        return this.playerInfoMap.values();
    }
    
    public NetworkPlayerInfo getPlayerInfo(final UUID uuid) {
        return this.playerInfoMap.get(uuid);
    }
    
    static NetworkManager access$0(final NetHandlerPlayClient netHandlerPlayClient) {
        return netHandlerPlayClient.netManager;
    }
    
    static Minecraft access$1(final NetHandlerPlayClient netHandlerPlayClient) {
        return netHandlerPlayClient.gameController;
    }
    
    static void access$2(final NetHandlerPlayClient netHandlerPlayClient, final Minecraft gameController) {
        netHandlerPlayClient.gameController = gameController;
    }
    
    static final class SwitchAction
    {
        static final int[] field_178885_a;
        static final int[] field_178884_b;
        private static final String __OBFID;
        private static final String[] lIIIIIIllIIIIlII;
        private static String[] lIIIIIIllIIIIlIl;
        
        static {
            lIllllllIIIlllll();
            lIllllllIIIllllI();
            __OBFID = SwitchAction.lIIIIIIllIIIIlII[0];
            field_178884_b = new int[S38PacketPlayerListItem.Action.values().length];
            try {
                SwitchAction.field_178884_b[S38PacketPlayerListItem.Action.ADD_PLAYER.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchAction.field_178884_b[S38PacketPlayerListItem.Action.UPDATE_GAME_MODE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchAction.field_178884_b[S38PacketPlayerListItem.Action.UPDATE_LATENCY.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchAction.field_178884_b[S38PacketPlayerListItem.Action.UPDATE_DISPLAY_NAME.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            field_178885_a = new int[S45PacketTitle.Type.values().length];
            try {
                SwitchAction.field_178885_a[S45PacketTitle.Type.TITLE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchAction.field_178885_a[S45PacketTitle.Type.SUBTITLE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchAction.field_178885_a[S45PacketTitle.Type.RESET.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
        }
        
        private static void lIllllllIIIllllI() {
            (lIIIIIIllIIIIlII = new String[1])[0] = lIllllllIIIlllIl(SwitchAction.lIIIIIIllIIIIlIl[0], SwitchAction.lIIIIIIllIIIIlIl[1]);
            SwitchAction.lIIIIIIllIIIIlIl = null;
        }
        
        private static void lIllllllIIIlllll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchAction.lIIIIIIllIIIIlIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIllllllIIIlllIl(final String s, final String s2) {
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
    }
}
