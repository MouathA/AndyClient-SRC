package net.minecraft.server.management;

import java.io.*;
import java.text.*;
import net.minecraft.server.*;
import org.apache.logging.log4j.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraft.potion.*;
import com.mojang.authlib.*;
import net.minecraft.nbt.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import net.minecraft.world.border.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import java.net.*;
import net.minecraft.world.demo.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;
import net.minecraft.network.play.server.*;

public abstract class ServerConfigurationManager
{
    public static final File FILE_PLAYERBANS;
    public static final File FILE_IPBANS;
    public static final File FILE_OPS;
    public static final File FILE_WHITELIST;
    private static final Logger logger;
    private static final SimpleDateFormat dateFormat;
    private final MinecraftServer mcServer;
    public final List playerEntityList;
    public final Map field_177454_f;
    private final UserListBans bannedPlayers;
    private final BanList bannedIPs;
    private final UserListOps ops;
    private final UserListWhitelist whiteListedPlayers;
    private final Map playerStatFiles;
    private IPlayerFileData playerNBTManagerObj;
    private boolean whiteListEnforced;
    protected int maxPlayers;
    private int viewDistance;
    private WorldSettings.GameType gameType;
    private boolean commandsAllowedForAll;
    private int playerPingIndex;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001423";
        FILE_PLAYERBANS = new File("banned-players.json");
        FILE_IPBANS = new File("banned-ips.json");
        FILE_OPS = new File("ops.json");
        FILE_WHITELIST = new File("whitelist.json");
        logger = LogManager.getLogger();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    }
    
    public ServerConfigurationManager(final MinecraftServer mcServer) {
        this.playerEntityList = Lists.newArrayList();
        this.field_177454_f = Maps.newHashMap();
        this.bannedPlayers = new UserListBans(ServerConfigurationManager.FILE_PLAYERBANS);
        this.bannedIPs = new BanList(ServerConfigurationManager.FILE_IPBANS);
        this.ops = new UserListOps(ServerConfigurationManager.FILE_OPS);
        this.whiteListedPlayers = new UserListWhitelist(ServerConfigurationManager.FILE_WHITELIST);
        this.playerStatFiles = Maps.newHashMap();
        this.mcServer = mcServer;
        this.bannedPlayers.setLanServer(false);
        this.bannedIPs.setLanServer(false);
        this.maxPlayers = 8;
    }
    
    public void initializeConnectionToPlayer(final NetworkManager networkManager, final EntityPlayerMP entityPlayerMP) {
        final GameProfile gameProfile = entityPlayerMP.getGameProfile();
        final PlayerProfileCache playerProfileCache = this.mcServer.getPlayerProfileCache();
        final GameProfile func_152652_a = playerProfileCache.func_152652_a(gameProfile.getId());
        final String s = (func_152652_a == null) ? gameProfile.getName() : func_152652_a.getName();
        playerProfileCache.func_152649_a(gameProfile);
        final NBTTagCompound playerDataFromFile = this.readPlayerDataFromFile(entityPlayerMP);
        entityPlayerMP.setWorld(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
        entityPlayerMP.theItemInWorldManager.setWorld((WorldServer)entityPlayerMP.worldObj);
        String string = "local";
        if (networkManager.getRemoteAddress() != null) {
            string = networkManager.getRemoteAddress().toString();
        }
        ServerConfigurationManager.logger.info(String.valueOf(entityPlayerMP.getName()) + "[" + string + "] logged in with entity id " + entityPlayerMP.getEntityId() + " at (" + entityPlayerMP.posX + ", " + entityPlayerMP.posY + ", " + entityPlayerMP.posZ + ")");
        final WorldServer worldServerForDimension = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        final WorldInfo worldInfo = worldServerForDimension.getWorldInfo();
        final BlockPos spawnPoint = worldServerForDimension.getSpawnPoint();
        this.func_72381_a(entityPlayerMP, null, worldServerForDimension);
        final NetHandlerPlayServer netHandlerPlayServer = new NetHandlerPlayServer(this.mcServer, networkManager, entityPlayerMP);
        netHandlerPlayServer.sendPacket(new S01PacketJoinGame(entityPlayerMP.getEntityId(), entityPlayerMP.theItemInWorldManager.getGameType(), worldInfo.isHardcoreModeEnabled(), worldServerForDimension.provider.getDimensionId(), worldServerForDimension.getDifficulty(), this.getMaxPlayers(), worldInfo.getTerrainType(), worldServerForDimension.getGameRules().getGameRuleBooleanValue("reducedDebugInfo")));
        netHandlerPlayServer.sendPacket(new S3FPacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).writeString(this.getServerInstance().getServerModName())));
        netHandlerPlayServer.sendPacket(new S41PacketServerDifficulty(worldInfo.getDifficulty(), worldInfo.isDifficultyLocked()));
        netHandlerPlayServer.sendPacket(new S05PacketSpawnPosition(spawnPoint));
        netHandlerPlayServer.sendPacket(new S39PacketPlayerAbilities(entityPlayerMP.capabilities));
        netHandlerPlayServer.sendPacket(new S09PacketHeldItemChange(entityPlayerMP.inventory.currentItem));
        entityPlayerMP.getStatFile().func_150877_d();
        entityPlayerMP.getStatFile().func_150884_b(entityPlayerMP);
        this.func_96456_a((ServerScoreboard)worldServerForDimension.getScoreboard(), entityPlayerMP);
        this.mcServer.refreshStatusNextTick();
        ChatComponentTranslation chatComponentTranslation;
        if (!entityPlayerMP.getName().equalsIgnoreCase(s)) {
            chatComponentTranslation = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[] { entityPlayerMP.getDisplayName(), s });
        }
        else {
            chatComponentTranslation = new ChatComponentTranslation("multiplayer.player.joined", new Object[] { entityPlayerMP.getDisplayName() });
        }
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.sendChatMsg(chatComponentTranslation);
        this.playerLoggedIn(entityPlayerMP);
        netHandlerPlayServer.setPlayerLocation(entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.rotationYaw, entityPlayerMP.rotationPitch);
        this.updateTimeAndWeatherForPlayer(entityPlayerMP, worldServerForDimension);
        if (this.mcServer.getResourcePackUrl().length() > 0) {
            entityPlayerMP.func_175397_a(this.mcServer.getResourcePackUrl(), this.mcServer.getResourcePackHash());
        }
        final Iterator<PotionEffect> iterator = (Iterator<PotionEffect>)entityPlayerMP.getActivePotionEffects().iterator();
        while (iterator.hasNext()) {
            netHandlerPlayServer.sendPacket(new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), iterator.next()));
        }
        entityPlayerMP.addSelfToInternalCraftingInventory();
        if (playerDataFromFile != null && playerDataFromFile.hasKey("Riding", 10)) {
            final Entity entityFromNBT = EntityList.createEntityFromNBT(playerDataFromFile.getCompoundTag("Riding"), worldServerForDimension);
            if (entityFromNBT != null) {
                entityFromNBT.forceSpawn = true;
                worldServerForDimension.spawnEntityInWorld(entityFromNBT);
                entityPlayerMP.mountEntity(entityFromNBT);
                entityFromNBT.forceSpawn = false;
            }
        }
    }
    
    protected void func_96456_a(final ServerScoreboard serverScoreboard, final EntityPlayerMP entityPlayerMP) {
        Sets.newHashSet();
        final Iterator<ScorePlayerTeam> iterator = serverScoreboard.getTeams().iterator();
        while (iterator.hasNext()) {
            entityPlayerMP.playerNetServerHandler.sendPacket(new S3EPacketTeams(iterator.next(), 0));
        }
    }
    
    public void setPlayerManager(final WorldServer[] array) {
        this.playerNBTManagerObj = array[0].getSaveHandler().getPlayerNBTManager();
        array[0].getWorldBorder().addListener(new IBorderListener() {
            private static final String __OBFID;
            final ServerConfigurationManager this$0;
            
            @Override
            public void onSizeChanged(final WorldBorder worldBorder, final double n) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_SIZE));
            }
            
            @Override
            public void func_177692_a(final WorldBorder worldBorder, final double n, final double n2, final long n3) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.LERP_SIZE));
            }
            
            @Override
            public void onCenterChanged(final WorldBorder worldBorder, final double n, final double n2) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_CENTER));
            }
            
            @Override
            public void onWarningTimeChanged(final WorldBorder worldBorder, final int n) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_WARNING_TIME));
            }
            
            @Override
            public void onWarningDistanceChanged(final WorldBorder worldBorder, final int n) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS));
            }
            
            @Override
            public void func_177696_b(final WorldBorder worldBorder, final double n) {
            }
            
            @Override
            public void func_177695_c(final WorldBorder worldBorder, final double n) {
            }
            
            static {
                __OBFID = "CL_00002267";
            }
        });
    }
    
    public void func_72375_a(final EntityPlayerMP entityPlayerMP, final WorldServer worldServer) {
        final WorldServer serverForPlayer = entityPlayerMP.getServerForPlayer();
        if (worldServer != null) {
            worldServer.getPlayerManager().removePlayer(entityPlayerMP);
        }
        serverForPlayer.getPlayerManager().addPlayer(entityPlayerMP);
        serverForPlayer.theChunkProviderServer.loadChunk((int)entityPlayerMP.posX >> 4, (int)entityPlayerMP.posZ >> 4);
    }
    
    public int getEntityViewDistance() {
        return PlayerManager.getFurthestViewableBlock(this.getViewDistance());
    }
    
    public NBTTagCompound readPlayerDataFromFile(final EntityPlayerMP entityPlayerMP) {
        final NBTTagCompound playerNBTTagCompound = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
        NBTTagCompound playerData;
        if (entityPlayerMP.getName().equals(this.mcServer.getServerOwner()) && playerNBTTagCompound != null) {
            entityPlayerMP.readFromNBT(playerNBTTagCompound);
            playerData = playerNBTTagCompound;
            ServerConfigurationManager.logger.debug("loading single player");
        }
        else {
            playerData = this.playerNBTManagerObj.readPlayerData(entityPlayerMP);
        }
        return playerData;
    }
    
    protected void writePlayerData(final EntityPlayerMP entityPlayerMP) {
        this.playerNBTManagerObj.writePlayerData(entityPlayerMP);
        final StatisticsFile statisticsFile = this.playerStatFiles.get(entityPlayerMP.getUniqueID());
        if (statisticsFile != null) {
            statisticsFile.func_150883_b();
        }
    }
    
    public void playerLoggedIn(final EntityPlayerMP entityPlayerMP) {
        this.playerEntityList.add(entityPlayerMP);
        this.field_177454_f.put(entityPlayerMP.getUniqueID(), entityPlayerMP);
        this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { entityPlayerMP }));
        this.mcServer.worldServerForDimension(entityPlayerMP.dimension).spawnEntityInWorld(entityPlayerMP);
        this.func_72375_a(entityPlayerMP, null);
        while (0 < this.playerEntityList.size()) {
            entityPlayerMP.playerNetServerHandler.sendPacket(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { this.playerEntityList.get(0) }));
            int n = 0;
            ++n;
        }
    }
    
    public void serverUpdateMountedMovingPlayer(final EntityPlayerMP entityPlayerMP) {
        entityPlayerMP.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(entityPlayerMP);
    }
    
    public void playerLoggedOut(final EntityPlayerMP entityPlayerMP) {
        entityPlayerMP.triggerAchievement(StatList.leaveGameStat);
        this.writePlayerData(entityPlayerMP);
        final WorldServer serverForPlayer = entityPlayerMP.getServerForPlayer();
        if (entityPlayerMP.ridingEntity != null) {
            serverForPlayer.removePlayerEntityDangerously(entityPlayerMP.ridingEntity);
            ServerConfigurationManager.logger.debug("removing player mount");
        }
        serverForPlayer.removeEntity(entityPlayerMP);
        serverForPlayer.getPlayerManager().removePlayer(entityPlayerMP);
        this.playerEntityList.remove(entityPlayerMP);
        this.field_177454_f.remove(entityPlayerMP.getUniqueID());
        this.playerStatFiles.remove(entityPlayerMP.getUniqueID());
        this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.REMOVE_PLAYER, new EntityPlayerMP[] { entityPlayerMP }));
    }
    
    public String allowUserToConnect(final SocketAddress socketAddress, final GameProfile gameProfile) {
        if (this.bannedPlayers.isBanned(gameProfile)) {
            final UserListBansEntry userListBansEntry = (UserListBansEntry)this.bannedPlayers.getEntry(gameProfile);
            String s = "You are banned from this server!\nReason: " + userListBansEntry.getBanReason();
            if (userListBansEntry.getBanEndDate() != null) {
                s = String.valueOf(s) + "\nYour ban will be removed on " + ServerConfigurationManager.dateFormat.format(userListBansEntry.getBanEndDate());
            }
            return s;
        }
        if (gameProfile != 0) {
            return "You are not white-listed on this server!";
        }
        if (this.bannedIPs.isBanned(socketAddress)) {
            final IPBanEntry banEntry = this.bannedIPs.getBanEntry(socketAddress);
            String s2 = "Your IP address is banned from this server!\nReason: " + banEntry.getBanReason();
            if (banEntry.getBanEndDate() != null) {
                s2 = String.valueOf(s2) + "\nYour ban will be removed on " + ServerConfigurationManager.dateFormat.format(banEntry.getBanEndDate());
            }
            return s2;
        }
        return (this.playerEntityList.size() >= this.maxPlayers) ? "The server is full!" : null;
    }
    
    public EntityPlayerMP createPlayerForUser(final GameProfile gameProfile) {
        final UUID uuid = EntityPlayer.getUUID(gameProfile);
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < this.playerEntityList.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(0);
            if (entityPlayerMP.getUniqueID().equals(uuid)) {
                arrayList.add(entityPlayerMP);
            }
            int n = 0;
            ++n;
        }
        final Iterator<EntityPlayerMP> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            iterator.next().playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
        }
        ItemInWorldManager itemInWorldManager;
        if (this.mcServer.isDemo()) {
            itemInWorldManager = new DemoWorldManager(this.mcServer.worldServerForDimension(0));
        }
        else {
            itemInWorldManager = new ItemInWorldManager(this.mcServer.worldServerForDimension(0));
        }
        return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), gameProfile, itemInWorldManager);
    }
    
    public EntityPlayerMP recreatePlayerEntity(final EntityPlayerMP entityPlayerMP, final int dimension, final boolean b) {
        entityPlayerMP.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(entityPlayerMP);
        entityPlayerMP.getServerForPlayer().getEntityTracker().untrackEntity(entityPlayerMP);
        entityPlayerMP.getServerForPlayer().getPlayerManager().removePlayer(entityPlayerMP);
        this.playerEntityList.remove(entityPlayerMP);
        this.mcServer.worldServerForDimension(entityPlayerMP.dimension).removePlayerEntityDangerously(entityPlayerMP);
        final BlockPos func_180470_cg = entityPlayerMP.func_180470_cg();
        final boolean spawnForced = entityPlayerMP.isSpawnForced();
        entityPlayerMP.dimension = dimension;
        ItemInWorldManager itemInWorldManager;
        if (this.mcServer.isDemo()) {
            itemInWorldManager = new DemoWorldManager(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
        }
        else {
            itemInWorldManager = new ItemInWorldManager(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
        }
        final EntityPlayerMP entityPlayerMP2 = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(entityPlayerMP.dimension), entityPlayerMP.getGameProfile(), itemInWorldManager);
        entityPlayerMP2.playerNetServerHandler = entityPlayerMP.playerNetServerHandler;
        entityPlayerMP2.clonePlayer(entityPlayerMP, b);
        entityPlayerMP2.setEntityId(entityPlayerMP.getEntityId());
        entityPlayerMP2.func_174817_o(entityPlayerMP);
        final WorldServer worldServerForDimension = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        this.func_72381_a(entityPlayerMP2, entityPlayerMP, worldServerForDimension);
        if (func_180470_cg != null) {
            final BlockPos func_180467_a = EntityPlayer.func_180467_a(this.mcServer.worldServerForDimension(entityPlayerMP.dimension), func_180470_cg, spawnForced);
            if (func_180467_a != null) {
                entityPlayerMP2.setLocationAndAngles(func_180467_a.getX() + 0.5f, func_180467_a.getY() + 0.1f, func_180467_a.getZ() + 0.5f, 0.0f, 0.0f);
                entityPlayerMP2.func_180473_a(func_180470_cg, spawnForced);
            }
            else {
                entityPlayerMP2.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0, 0.0f));
            }
        }
        worldServerForDimension.theChunkProviderServer.loadChunk((int)entityPlayerMP2.posX >> 4, (int)entityPlayerMP2.posZ >> 4);
        while (!worldServerForDimension.getCollidingBoundingBoxes(entityPlayerMP2, entityPlayerMP2.getEntityBoundingBox()).isEmpty() && entityPlayerMP2.posY < 256.0) {
            entityPlayerMP2.setPosition(entityPlayerMP2.posX, entityPlayerMP2.posY + 1.0, entityPlayerMP2.posZ);
        }
        entityPlayerMP2.playerNetServerHandler.sendPacket(new S07PacketRespawn(entityPlayerMP2.dimension, entityPlayerMP2.worldObj.getDifficulty(), entityPlayerMP2.worldObj.getWorldInfo().getTerrainType(), entityPlayerMP2.theItemInWorldManager.getGameType()));
        final BlockPos spawnPoint = worldServerForDimension.getSpawnPoint();
        entityPlayerMP2.playerNetServerHandler.setPlayerLocation(entityPlayerMP2.posX, entityPlayerMP2.posY, entityPlayerMP2.posZ, entityPlayerMP2.rotationYaw, entityPlayerMP2.rotationPitch);
        entityPlayerMP2.playerNetServerHandler.sendPacket(new S05PacketSpawnPosition(spawnPoint));
        entityPlayerMP2.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(entityPlayerMP2.experience, entityPlayerMP2.experienceTotal, entityPlayerMP2.experienceLevel));
        this.updateTimeAndWeatherForPlayer(entityPlayerMP2, worldServerForDimension);
        worldServerForDimension.getPlayerManager().addPlayer(entityPlayerMP2);
        worldServerForDimension.spawnEntityInWorld(entityPlayerMP2);
        this.playerEntityList.add(entityPlayerMP2);
        this.field_177454_f.put(entityPlayerMP2.getUniqueID(), entityPlayerMP2);
        entityPlayerMP2.addSelfToInternalCraftingInventory();
        entityPlayerMP2.setHealth(entityPlayerMP2.getHealth());
        return entityPlayerMP2;
    }
    
    public void transferPlayerToDimension(final EntityPlayerMP entityPlayerMP, final int dimension) {
        final int dimension2 = entityPlayerMP.dimension;
        final WorldServer worldServerForDimension = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        entityPlayerMP.dimension = dimension;
        final WorldServer worldServerForDimension2 = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        entityPlayerMP.playerNetServerHandler.sendPacket(new S07PacketRespawn(entityPlayerMP.dimension, entityPlayerMP.worldObj.getDifficulty(), entityPlayerMP.worldObj.getWorldInfo().getTerrainType(), entityPlayerMP.theItemInWorldManager.getGameType()));
        worldServerForDimension.removePlayerEntityDangerously(entityPlayerMP);
        entityPlayerMP.isDead = false;
        this.transferEntityToWorld(entityPlayerMP, dimension2, worldServerForDimension, worldServerForDimension2);
        this.func_72375_a(entityPlayerMP, worldServerForDimension);
        entityPlayerMP.playerNetServerHandler.setPlayerLocation(entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.rotationYaw, entityPlayerMP.rotationPitch);
        entityPlayerMP.theItemInWorldManager.setWorld(worldServerForDimension2);
        this.updateTimeAndWeatherForPlayer(entityPlayerMP, worldServerForDimension2);
        this.syncPlayerInventory(entityPlayerMP);
        final Iterator<PotionEffect> iterator = (Iterator<PotionEffect>)entityPlayerMP.getActivePotionEffects().iterator();
        while (iterator.hasNext()) {
            entityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), iterator.next()));
        }
    }
    
    public void transferEntityToWorld(final Entity entity, final int n, final WorldServer worldServer, final WorldServer world) {
        final double posX = entity.posX;
        final double posZ = entity.posZ;
        final double n2 = 8.0;
        final float rotationYaw = entity.rotationYaw;
        worldServer.theProfiler.startSection("moving");
        double n3;
        double n4;
        if (entity.dimension == -1) {
            n3 = MathHelper.clamp_double(posX / n2, world.getWorldBorder().minX() + 16.0, world.getWorldBorder().maxX() - 16.0);
            n4 = MathHelper.clamp_double(posZ / n2, world.getWorldBorder().minZ() + 16.0, world.getWorldBorder().maxZ() - 16.0);
            entity.setLocationAndAngles(n3, entity.posY, n4, entity.rotationYaw, entity.rotationPitch);
            if (entity.isEntityAlive()) {
                worldServer.updateEntityWithOptionalForce(entity, false);
            }
        }
        else if (entity.dimension == 0) {
            n3 = MathHelper.clamp_double(posX * n2, world.getWorldBorder().minX() + 16.0, world.getWorldBorder().maxX() - 16.0);
            n4 = MathHelper.clamp_double(posZ * n2, world.getWorldBorder().minZ() + 16.0, world.getWorldBorder().maxZ() - 16.0);
            entity.setLocationAndAngles(n3, entity.posY, n4, entity.rotationYaw, entity.rotationPitch);
            if (entity.isEntityAlive()) {
                worldServer.updateEntityWithOptionalForce(entity, false);
            }
        }
        else {
            BlockPos blockPos;
            if (n == 1) {
                blockPos = world.getSpawnPoint();
            }
            else {
                blockPos = world.func_180504_m();
            }
            n3 = blockPos.getX();
            entity.posY = blockPos.getY();
            n4 = blockPos.getZ();
            entity.setLocationAndAngles(n3, entity.posY, n4, 90.0f, 0.0f);
            if (entity.isEntityAlive()) {
                worldServer.updateEntityWithOptionalForce(entity, false);
            }
        }
        worldServer.theProfiler.endSection();
        if (n != 1) {
            worldServer.theProfiler.startSection("placing");
            final double n5 = MathHelper.clamp_int((int)n3, -29999872, 29999872);
            final double n6 = MathHelper.clamp_int((int)n4, -29999872, 29999872);
            if (entity.isEntityAlive()) {
                entity.setLocationAndAngles(n5, entity.posY, n6, entity.rotationYaw, entity.rotationPitch);
                world.getDefaultTeleporter().func_180266_a(entity, rotationYaw);
                world.spawnEntityInWorld(entity);
                world.updateEntityWithOptionalForce(entity, false);
            }
            worldServer.theProfiler.endSection();
        }
        entity.setWorld(world);
    }
    
    public void onTick() {
        if (++this.playerPingIndex > 600) {
            this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_LATENCY, this.playerEntityList));
            this.playerPingIndex = 0;
        }
    }
    
    public void sendPacketToAllPlayers(final Packet packet) {
        while (0 < this.playerEntityList.size()) {
            this.playerEntityList.get(0).playerNetServerHandler.sendPacket(packet);
            int n = 0;
            ++n;
        }
    }
    
    public void sendPacketToAllPlayersInDimension(final Packet packet, final int n) {
        while (0 < this.playerEntityList.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(0);
            if (entityPlayerMP.dimension == n) {
                entityPlayerMP.playerNetServerHandler.sendPacket(packet);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public void func_177453_a(final EntityPlayer entityPlayer, final IChatComponent chatComponent) {
        final Team team = entityPlayer.getTeam();
        if (team != null) {
            final Iterator iterator = team.getMembershipCollection().iterator();
            while (iterator.hasNext()) {
                final EntityPlayerMP playerByUsername = this.getPlayerByUsername(iterator.next());
                if (playerByUsername != null && playerByUsername != entityPlayer) {
                    playerByUsername.addChatMessage(chatComponent);
                }
            }
        }
    }
    
    public void func_177452_b(final EntityPlayer entityPlayer, final IChatComponent chatComponent) {
        final Team team = entityPlayer.getTeam();
        if (team == null) {
            this.sendChatMsg(chatComponent);
        }
        else {
            while (0 < this.playerEntityList.size()) {
                final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(0);
                if (entityPlayerMP.getTeam() != team) {
                    entityPlayerMP.addChatMessage(chatComponent);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public String func_180602_f() {
        String string = "";
        while (0 < this.playerEntityList.size()) {
            string = String.valueOf(string) + this.playerEntityList.get(0).getName();
            int n = 0;
            ++n;
        }
        return string;
    }
    
    public String[] getAllUsernames() {
        final String[] array = new String[this.playerEntityList.size()];
        while (0 < this.playerEntityList.size()) {
            array[0] = this.playerEntityList.get(0).getName();
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public GameProfile[] getAllProfiles() {
        final GameProfile[] array = new GameProfile[this.playerEntityList.size()];
        while (0 < this.playerEntityList.size()) {
            array[0] = this.playerEntityList.get(0).getGameProfile();
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public UserListBans getBannedPlayers() {
        return this.bannedPlayers;
    }
    
    public BanList getBannedIPs() {
        return this.bannedIPs;
    }
    
    public void addOp(final GameProfile gameProfile) {
        this.ops.addEntry(new UserListOpsEntry(gameProfile, this.mcServer.getOpPermissionLevel()));
    }
    
    public void removeOp(final GameProfile gameProfile) {
        this.ops.removeEntry(gameProfile);
    }
    
    public boolean canSendCommands(final GameProfile gameProfile) {
        return this.ops.hasEntry(gameProfile) || (this.mcServer.isSinglePlayer() && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && this.mcServer.getServerOwner().equalsIgnoreCase(gameProfile.getName())) || this.commandsAllowedForAll;
    }
    
    public EntityPlayerMP getPlayerByUsername(final String s) {
        for (final EntityPlayerMP entityPlayerMP : this.playerEntityList) {
            if (entityPlayerMP.getName().equalsIgnoreCase(s)) {
                return entityPlayerMP;
            }
        }
        return null;
    }
    
    public void sendToAllNear(final double n, final double n2, final double n3, final double n4, final int n5, final Packet packet) {
        this.sendToAllNearExcept(null, n, n2, n3, n4, n5, packet);
    }
    
    public void sendToAllNearExcept(final EntityPlayer entityPlayer, final double n, final double n2, final double n3, final double n4, final int n5, final Packet packet) {
        while (0 < this.playerEntityList.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(0);
            if (entityPlayerMP != entityPlayer && entityPlayerMP.dimension == n5) {
                final double n6 = n - entityPlayerMP.posX;
                final double n7 = n2 - entityPlayerMP.posY;
                final double n8 = n3 - entityPlayerMP.posZ;
                if (n6 * n6 + n7 * n7 + n8 * n8 < n4 * n4) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                }
            }
            int n9 = 0;
            ++n9;
        }
    }
    
    public void saveAllPlayerData() {
        while (0 < this.playerEntityList.size()) {
            this.writePlayerData(this.playerEntityList.get(0));
            int n = 0;
            ++n;
        }
    }
    
    public void addWhitelistedPlayer(final GameProfile gameProfile) {
        this.whiteListedPlayers.addEntry(new UserListWhitelistEntry(gameProfile));
    }
    
    public void removePlayerFromWhitelist(final GameProfile gameProfile) {
        this.whiteListedPlayers.removeEntry(gameProfile);
    }
    
    public UserListWhitelist getWhitelistedPlayers() {
        return this.whiteListedPlayers;
    }
    
    public String[] getWhitelistedPlayerNames() {
        return this.whiteListedPlayers.getKeys();
    }
    
    public UserListOps getOppedPlayers() {
        return this.ops;
    }
    
    public String[] getOppedPlayerNames() {
        return this.ops.getKeys();
    }
    
    public void loadWhiteList() {
    }
    
    public void updateTimeAndWeatherForPlayer(final EntityPlayerMP entityPlayerMP, final WorldServer worldServer) {
        entityPlayerMP.playerNetServerHandler.sendPacket(new S44PacketWorldBorder(this.mcServer.worldServers[0].getWorldBorder(), S44PacketWorldBorder.Action.INITIALIZE));
        entityPlayerMP.playerNetServerHandler.sendPacket(new S03PacketTimeUpdate(worldServer.getTotalWorldTime(), worldServer.getWorldTime(), worldServer.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));
        if (worldServer.isRaining()) {
            entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(1, 0.0f));
            entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(7, worldServer.getRainStrength(1.0f)));
            entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(8, worldServer.getWeightedThunderStrength(1.0f)));
        }
    }
    
    public void syncPlayerInventory(final EntityPlayerMP entityPlayerMP) {
        entityPlayerMP.sendContainerToPlayer(entityPlayerMP.inventoryContainer);
        entityPlayerMP.setPlayerHealthUpdated();
        entityPlayerMP.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(entityPlayerMP.inventory.currentItem));
    }
    
    public int getCurrentPlayerCount() {
        return this.playerEntityList.size();
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public String[] getAvailablePlayerDat() {
        return this.mcServer.worldServers[0].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
    }
    
    public void setWhiteListEnabled(final boolean whiteListEnforced) {
        this.whiteListEnforced = whiteListEnforced;
    }
    
    public List getPlayersMatchingAddress(final String s) {
        final ArrayList arrayList = Lists.newArrayList();
        for (final EntityPlayerMP entityPlayerMP : this.playerEntityList) {
            if (entityPlayerMP.getPlayerIP().equals(s)) {
                arrayList.add(entityPlayerMP);
            }
        }
        return arrayList;
    }
    
    public int getViewDistance() {
        return this.viewDistance;
    }
    
    public MinecraftServer getServerInstance() {
        return this.mcServer;
    }
    
    public NBTTagCompound getHostPlayerData() {
        return null;
    }
    
    public void func_152604_a(final WorldSettings.GameType gameType) {
        this.gameType = gameType;
    }
    
    private void func_72381_a(final EntityPlayerMP entityPlayerMP, final EntityPlayerMP entityPlayerMP2, final World world) {
        if (entityPlayerMP2 != null) {
            entityPlayerMP.theItemInWorldManager.setGameType(entityPlayerMP2.theItemInWorldManager.getGameType());
        }
        else if (this.gameType != null) {
            entityPlayerMP.theItemInWorldManager.setGameType(this.gameType);
        }
        entityPlayerMP.theItemInWorldManager.initializeGameType(world.getWorldInfo().getGameType());
    }
    
    public void setCommandsAllowedForAll(final boolean commandsAllowedForAll) {
        this.commandsAllowedForAll = commandsAllowedForAll;
    }
    
    public void removeAllPlayers() {
        while (0 < this.playerEntityList.size()) {
            this.playerEntityList.get(0).playerNetServerHandler.kickPlayerFromServer("Server closed");
            int n = 0;
            ++n;
        }
    }
    
    public void sendChatMsgImpl(final IChatComponent chatComponent, final boolean b) {
        this.mcServer.addChatMessage(chatComponent);
        this.sendPacketToAllPlayers(new S02PacketChat(chatComponent, (byte)(b ? 1 : 0)));
    }
    
    public void sendChatMsg(final IChatComponent chatComponent) {
        this.sendChatMsgImpl(chatComponent, true);
    }
    
    public StatisticsFile getPlayerStatsFile(final EntityPlayer entityPlayer) {
        final UUID uniqueID = entityPlayer.getUniqueID();
        StatisticsFile statisticsFile = (uniqueID == null) ? null : this.playerStatFiles.get(uniqueID);
        if (statisticsFile == null) {
            final File file = new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats");
            final File file2 = new File(file, String.valueOf(uniqueID.toString()) + ".json");
            if (!file2.exists()) {
                final File file3 = new File(file, String.valueOf(entityPlayer.getName()) + ".json");
                if (file3.exists() && file3.isFile()) {
                    file3.renameTo(file2);
                }
            }
            statisticsFile = new StatisticsFile(this.mcServer, file2);
            statisticsFile.func_150882_a();
            this.playerStatFiles.put(uniqueID, statisticsFile);
        }
        return statisticsFile;
    }
    
    public void setViewDistance(final int viewDistance) {
        this.viewDistance = viewDistance;
        if (this.mcServer.worldServers != null) {
            final WorldServer[] worldServers = this.mcServer.worldServers;
            while (0 < worldServers.length) {
                final WorldServer worldServer = worldServers[0];
                if (worldServer != null) {
                    worldServer.getPlayerManager().func_152622_a(viewDistance);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public EntityPlayerMP func_177451_a(final UUID uuid) {
        return this.field_177454_f.get(uuid);
    }
}
