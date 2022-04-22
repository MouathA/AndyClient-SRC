package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S45PacketTitle implements Packet
{
    private Type field_179812_a;
    private IChatComponent field_179810_b;
    private int field_179811_c;
    private int field_179808_d;
    private int field_179809_e;
    private static final String __OBFID;
    
    public S45PacketTitle() {
    }
    
    public S45PacketTitle(final Type type, final IChatComponent chatComponent) {
        this(type, chatComponent, -1, -1, -1);
    }
    
    public S45PacketTitle(final int n, final int n2, final int n3) {
        this(Type.TIMES, null, n, n2, n3);
    }
    
    public S45PacketTitle(final Type field_179812_a, final IChatComponent field_179810_b, final int field_179811_c, final int field_179808_d, final int field_179809_e) {
        this.field_179812_a = field_179812_a;
        this.field_179810_b = field_179810_b;
        this.field_179811_c = field_179811_c;
        this.field_179808_d = field_179808_d;
        this.field_179809_e = field_179809_e;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179812_a = (Type)packetBuffer.readEnumValue(Type.class);
        if (this.field_179812_a == Type.TITLE || this.field_179812_a == Type.SUBTITLE) {
            this.field_179810_b = packetBuffer.readChatComponent();
        }
        if (this.field_179812_a == Type.TIMES) {
            this.field_179811_c = packetBuffer.readInt();
            this.field_179808_d = packetBuffer.readInt();
            this.field_179809_e = packetBuffer.readInt();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.field_179812_a);
        if (this.field_179812_a == Type.TITLE || this.field_179812_a == Type.SUBTITLE) {
            packetBuffer.writeChatComponent(this.field_179810_b);
        }
        if (this.field_179812_a == Type.TIMES) {
            packetBuffer.writeInt(this.field_179811_c);
            packetBuffer.writeInt(this.field_179808_d);
            packetBuffer.writeInt(this.field_179809_e);
        }
    }
    
    public void func_179802_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.func_175099_a(this);
    }
    
    public Type func_179807_a() {
        return this.field_179812_a;
    }
    
    public IChatComponent func_179805_b() {
        return this.field_179810_b;
    }
    
    public int func_179806_c() {
        return this.field_179811_c;
    }
    
    public int func_179804_d() {
        return this.field_179808_d;
    }
    
    public int func_179803_e() {
        return this.field_179809_e;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179802_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00002287";
    }
    
    public enum Type
    {
        TITLE("TITLE", 0, "TITLE", 0), 
        SUBTITLE("SUBTITLE", 1, "SUBTITLE", 1), 
        TIMES("TIMES", 2, "TIMES", 2), 
        CLEAR("CLEAR", 3, "CLEAR", 3), 
        RESET("RESET", 4, "RESET", 4);
        
        private static final Type[] $VALUES;
        private static final String __OBFID;
        private static final Type[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002286";
            ENUM$VALUES = new Type[] { Type.TITLE, Type.SUBTITLE, Type.TIMES, Type.CLEAR, Type.RESET };
            $VALUES = new Type[] { Type.TITLE, Type.SUBTITLE, Type.TIMES, Type.CLEAR, Type.RESET };
        }
        
        private Type(final String s, final int n, final String s2, final int n2) {
        }
        
        public static Type func_179969_a(final String s) {
            final Type[] values = values();
            while (0 < values.length) {
                final Type type = values[0];
                if (type.name().equalsIgnoreCase(s)) {
                    return type;
                }
                int n = 0;
                ++n;
            }
            return Type.TITLE;
        }
        
        public static String[] func_179971_a() {
            final String[] array = new String[values().length];
            final Type[] values = values();
            while (0 < values.length) {
                final Type type = values[0];
                final String[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = type.name().toLowerCase();
                int n3 = 0;
                ++n3;
            }
            return array;
        }
    }
}
