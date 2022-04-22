package net.minecraft.network;

import net.minecraft.util.*;

public class PacketThreadUtil
{
    private static final String __OBFID;
    
    public static void func_180031_a(final Packet packet, final INetHandler netHandler, final IThreadListener threadListener) {
        if (!threadListener.isCallingFromMinecraftThread()) {
            threadListener.addScheduledTask(new Runnable(netHandler) {
                private static final String __OBFID;
                private final Packet val$p_180031_0_;
                private final INetHandler val$p_180031_1_;
                
                @Override
                public void run() {
                    this.val$p_180031_0_.processPacket(this.val$p_180031_1_);
                }
                
                static {
                    __OBFID = "CL_00002305";
                }
            });
            throw ThreadQuickExitException.field_179886_a;
        }
    }
    
    static {
        __OBFID = "CL_00002306";
    }
}
