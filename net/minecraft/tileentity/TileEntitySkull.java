package net.minecraft.tileentity;

import com.mojang.authlib.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.server.*;
import com.mojang.authlib.properties.*;
import com.google.common.collect.*;

public class TileEntitySkull extends TileEntity
{
    private int skullType;
    private int skullRotation;
    private GameProfile playerProfile;
    private static final String __OBFID;
    
    public TileEntitySkull() {
        this.playerProfile = null;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte("SkullType", (byte)(this.skullType & 0xFF));
        nbtTagCompound.setByte("Rot", (byte)(this.skullRotation & 0xFF));
        if (this.playerProfile != null) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            NBTUtil.writeGameProfile(nbtTagCompound2, this.playerProfile);
            nbtTagCompound.setTag("Owner", nbtTagCompound2);
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.skullType = nbtTagCompound.getByte("SkullType");
        this.skullRotation = nbtTagCompound.getByte("Rot");
        if (this.skullType == 3) {
            if (nbtTagCompound.hasKey("Owner", 10)) {
                this.playerProfile = NBTUtil.readGameProfileFromNBT(nbtTagCompound.getCompoundTag("Owner"));
            }
            else if (nbtTagCompound.hasKey("ExtraType", 8)) {
                final String string = nbtTagCompound.getString("ExtraType");
                if (!StringUtils.isNullOrEmpty(string)) {
                    this.playerProfile = new GameProfile(null, string);
                    this.func_152109_d();
                }
            }
        }
    }
    
    public GameProfile getPlayerProfile() {
        return this.playerProfile;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, 4, nbtTagCompound);
    }
    
    public void setType(final int skullType) {
        this.skullType = skullType;
        this.playerProfile = null;
    }
    
    public void setPlayerProfile(final GameProfile playerProfile) {
        this.skullType = 3;
        this.playerProfile = playerProfile;
        this.func_152109_d();
    }
    
    private void func_152109_d() {
        this.playerProfile = updateGameprofile(this.playerProfile);
        this.markDirty();
    }
    
    public static GameProfile updateGameprofile(final GameProfile gameProfile) {
        if (gameProfile == null || StringUtils.isNullOrEmpty(gameProfile.getName())) {
            return gameProfile;
        }
        if (gameProfile.isComplete() && gameProfile.getProperties().containsKey("textures")) {
            return gameProfile;
        }
        if (MinecraftServer.getServer() == null) {
            return gameProfile;
        }
        GameProfile gameProfile2 = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(gameProfile.getName());
        if (gameProfile2 == null) {
            return gameProfile;
        }
        if (Iterables.getFirst(gameProfile2.getProperties().get("textures"), null) == null) {
            gameProfile2 = MinecraftServer.getServer().getMinecraftSessionService().fillProfileProperties(gameProfile2, true);
        }
        return gameProfile2;
    }
    
    public int getSkullType() {
        return this.skullType;
    }
    
    public int getSkullRotation() {
        return this.skullRotation;
    }
    
    public void setSkullRotation(final int skullRotation) {
        this.skullRotation = skullRotation;
    }
    
    static {
        __OBFID = "CL_00000364";
    }
}
