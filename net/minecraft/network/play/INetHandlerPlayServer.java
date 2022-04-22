package net.minecraft.network.play;

import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

public interface INetHandlerPlayServer extends INetHandler
{
    void func_175087_a(final C0APacketAnimation p0);
    
    void processChatMessage(final C01PacketChatMessage p0);
    
    void processTabComplete(final C14PacketTabComplete p0);
    
    void processClientStatus(final C16PacketClientStatus p0);
    
    void processClientSettings(final C15PacketClientSettings p0);
    
    void processConfirmTransaction(final C0FPacketConfirmTransaction p0);
    
    void processEnchantItem(final C11PacketEnchantItem p0);
    
    void processClickWindow(final C0EPacketClickWindow p0);
    
    void processCloseWindow(final C0DPacketCloseWindow p0);
    
    void processVanilla250Packet(final C17PacketCustomPayload p0);
    
    void processUseEntity(final C02PacketUseEntity p0);
    
    void processKeepAlive(final C00PacketKeepAlive p0);
    
    void processPlayer(final C03PacketPlayer p0);
    
    void processPlayerAbilities(final C13PacketPlayerAbilities p0);
    
    void processPlayerDigging(final C07PacketPlayerDigging p0);
    
    void processEntityAction(final C0BPacketEntityAction p0);
    
    void processInput(final C0CPacketInput p0);
    
    void processHeldItemChange(final C09PacketHeldItemChange p0);
    
    void processCreativeInventoryAction(final C10PacketCreativeInventoryAction p0);
    
    void processUpdateSign(final C12PacketUpdateSign p0);
    
    void processPlayerBlockPlacement(final C08PacketPlayerBlockPlacement p0);
    
    void func_175088_a(final C18PacketSpectate p0);
    
    void func_175086_a(final C19PacketResourcePackStatus p0);
}
