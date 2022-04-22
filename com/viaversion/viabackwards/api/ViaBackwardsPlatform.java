package com.viaversion.viabackwards.api;

import java.io.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.*;
import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.*;
import com.viaversion.viabackwards.protocol.protocol1_12to1_12_1.*;
import com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.*;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.*;
import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.*;
import com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.*;
import com.viaversion.viabackwards.protocol.protocol1_14_1to1_14_2.*;
import com.viaversion.viabackwards.protocol.protocol1_14_2to1_14_3.*;
import com.viaversion.viabackwards.protocol.protocol1_14_3to1_14_4.*;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.*;
import com.viaversion.viabackwards.protocol.protocol1_15to1_15_1.*;
import com.viaversion.viabackwards.protocol.protocol1_15_1to1_15_2.*;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.*;
import com.viaversion.viabackwards.protocol.protocol1_16to1_16_1.*;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.*;
import com.viaversion.viabackwards.protocol.protocol1_16_2to1_16_3.*;
import com.viaversion.viabackwards.protocol.protocol1_16_3to1_16_4.*;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.*;
import com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.*;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.*;
import com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.*;
import com.viaversion.viaversion.api.protocol.*;
import java.util.logging.*;
import com.viaversion.viaversion.update.*;

public interface ViaBackwardsPlatform
{
    public static final String MINIMUM_VV_VERSION = "4.2.0";
    public static final String IMPL_VERSION = "git-ViaBackwards-4.2.0-22w06a-SNAPSHOT:121c735";
    
    default void init(final File file) {
        final ViaBackwardsConfig viaBackwardsConfig = new ViaBackwardsConfig(new File(file, "config.yml"));
        viaBackwardsConfig.reloadConfig();
        ViaBackwards.init(this, viaBackwardsConfig);
        if (this.isOutdated()) {
            return;
        }
        Via.getManager().getSubPlatforms().add("git-ViaBackwards-4.2.0-22w06a-SNAPSHOT:121c735");
        this.getLogger().info("Loading translations...");
        final ProtocolManager protocolManager = Via.getManager().getProtocolManager();
        protocolManager.registerProtocol(new Protocol1_9_4To1_10(), ProtocolVersion.v1_9_3, ProtocolVersion.v1_10);
        protocolManager.registerProtocol(new Protocol1_10To1_11(), ProtocolVersion.v1_10, ProtocolVersion.v1_11);
        protocolManager.registerProtocol(new Protocol1_11To1_11_1(), ProtocolVersion.v1_11, ProtocolVersion.v1_11_1);
        protocolManager.registerProtocol(new Protocol1_11_1To1_12(), ProtocolVersion.v1_11_1, ProtocolVersion.v1_12);
        protocolManager.registerProtocol(new Protocol1_12To1_12_1(), ProtocolVersion.v1_12, ProtocolVersion.v1_12_1);
        protocolManager.registerProtocol(new Protocol1_12_1To1_12_2(), ProtocolVersion.v1_12_1, ProtocolVersion.v1_12_2);
        protocolManager.registerProtocol(new Protocol1_12_2To1_13(), ProtocolVersion.v1_12_2, ProtocolVersion.v1_13);
        protocolManager.registerProtocol(new Protocol1_13To1_13_1(), ProtocolVersion.v1_13, ProtocolVersion.v1_13_1);
        protocolManager.registerProtocol(new Protocol1_13_1To1_13_2(), ProtocolVersion.v1_13_1, ProtocolVersion.v1_13_2);
        protocolManager.registerProtocol(new Protocol1_13_2To1_14(), ProtocolVersion.v1_13_2, ProtocolVersion.v1_14);
        protocolManager.registerProtocol(new Protocol1_14To1_14_1(), ProtocolVersion.v1_14, ProtocolVersion.v1_14_1);
        protocolManager.registerProtocol(new Protocol1_14_1To1_14_2(), ProtocolVersion.v1_14_1, ProtocolVersion.v1_14_2);
        protocolManager.registerProtocol(new Protocol1_14_2To1_14_3(), ProtocolVersion.v1_14_2, ProtocolVersion.v1_14_3);
        protocolManager.registerProtocol(new Protocol1_14_3To1_14_4(), ProtocolVersion.v1_14_3, ProtocolVersion.v1_14_4);
        protocolManager.registerProtocol(new Protocol1_14_4To1_15(), ProtocolVersion.v1_14_4, ProtocolVersion.v1_15);
        protocolManager.registerProtocol(new Protocol1_15To1_15_1(), ProtocolVersion.v1_15, ProtocolVersion.v1_15_1);
        protocolManager.registerProtocol(new Protocol1_15_1To1_15_2(), ProtocolVersion.v1_15_1, ProtocolVersion.v1_15_2);
        protocolManager.registerProtocol(new Protocol1_15_2To1_16(), ProtocolVersion.v1_15_2, ProtocolVersion.v1_16);
        protocolManager.registerProtocol(new Protocol1_16To1_16_1(), ProtocolVersion.v1_16, ProtocolVersion.v1_16_1);
        protocolManager.registerProtocol(new Protocol1_16_1To1_16_2(), ProtocolVersion.v1_16_1, ProtocolVersion.v1_16_2);
        protocolManager.registerProtocol(new Protocol1_16_2To1_16_3(), ProtocolVersion.v1_16_2, ProtocolVersion.v1_16_3);
        protocolManager.registerProtocol(new Protocol1_16_3To1_16_4(), ProtocolVersion.v1_16_3, ProtocolVersion.v1_16_4);
        protocolManager.registerProtocol(new Protocol1_16_4To1_17(), ProtocolVersion.v1_16_4, ProtocolVersion.v1_17);
        protocolManager.registerProtocol(new Protocol1_17To1_17_1(), ProtocolVersion.v1_17, ProtocolVersion.v1_17_1);
        protocolManager.registerProtocol(new Protocol1_17_1To1_18(), ProtocolVersion.v1_17_1, ProtocolVersion.v1_18);
        protocolManager.registerProtocol(new Protocol1_18To1_18_2(), ProtocolVersion.v1_18, ProtocolVersion.v1_18_2);
    }
    
    Logger getLogger();
    
    default boolean isOutdated() {
        final String pluginVersion = Via.getPlatform().getPluginVersion();
        if (pluginVersion != null && new Version(pluginVersion).compareTo(new Version("4.2.0--")) < 0) {
            this.getLogger().severe("================================");
            this.getLogger().severe("YOUR VIAVERSION IS OUTDATED");
            this.getLogger().severe("PLEASE USE VIAVERSION 4.2.0 OR NEWER");
            this.getLogger().severe("LINK: https://ci.viaversion.com/");
            this.getLogger().severe("VIABACKWARDS WILL NOW DISABLE");
            this.getLogger().severe("================================");
            this.disable();
            return true;
        }
        return false;
    }
    
    void disable();
    
    File getDataFolder();
}
