package net.minecraft.server.integrated;

import net.minecraft.server.management.*;
import net.minecraft.nbt.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import java.net.*;
import com.mojang.authlib.*;

public class IntegratedPlayerList extends ServerConfigurationManager
{
    private NBTTagCompound hostPlayerData;
    private static final String __OBFID;
    
    public IntegratedPlayerList(final IntegratedServer integratedServer) {
        super(integratedServer);
        this.setViewDistance(10);
    }
    
    @Override
    protected void writePlayerData(final EntityPlayerMP entityPlayerMP) {
        if (entityPlayerMP.getName().equals(this.func_180603_b().getServerOwner())) {
            entityPlayerMP.writeToNBT(this.hostPlayerData = new NBTTagCompound());
        }
        super.writePlayerData(entityPlayerMP);
    }
    
    @Override
    public String allowUserToConnect(final SocketAddress socketAddress, final GameProfile gameProfile) {
        return (gameProfile.getName().equalsIgnoreCase(this.func_180603_b().getServerOwner()) && this.getPlayerByUsername(gameProfile.getName()) != null) ? "That name is already taken." : super.allowUserToConnect(socketAddress, gameProfile);
    }
    
    public IntegratedServer func_180603_b() {
        return (IntegratedServer)super.getServerInstance();
    }
    
    @Override
    public NBTTagCompound getHostPlayerData() {
        return this.hostPlayerData;
    }
    
    @Override
    public MinecraftServer getServerInstance() {
        return this.func_180603_b();
    }
    
    static {
        __OBFID = "CL_00001128";
    }
}
