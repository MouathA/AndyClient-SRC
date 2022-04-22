package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.protocol.packet.*;

public class PacketUtil
{
    public static void sendToServer(final PacketWrapper packetWrapper, final Class clazz) {
        sendToServer(packetWrapper, clazz, true);
    }
    
    public static void sendToServer(final PacketWrapper packetWrapper, final Class clazz, final boolean b) {
        sendToServer(packetWrapper, clazz, b, false);
    }
    
    public static void sendToServer(final PacketWrapper packetWrapper, final Class clazz, final boolean b, final boolean b2) {
        if (b2) {
            packetWrapper.sendToServer(clazz, b);
        }
        else {
            packetWrapper.scheduleSendToServer(clazz, b);
        }
    }
    
    public static boolean sendPacket(final PacketWrapper packetWrapper, final Class clazz) {
        return sendPacket(packetWrapper, clazz, true);
    }
    
    public static boolean sendPacket(final PacketWrapper packetWrapper, final Class clazz, final boolean b) {
        return sendPacket(packetWrapper, clazz, true, false);
    }
    
    public static boolean sendPacket(final PacketWrapper packetWrapper, final Class clazz, final boolean b, final boolean b2) {
        if (b2) {
            packetWrapper.send(clazz, b);
        }
        else {
            packetWrapper.scheduleSend(clazz, b);
        }
        return true;
    }
}
