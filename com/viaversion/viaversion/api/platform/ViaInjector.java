package com.viaversion.viaversion.api.platform;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.libs.gson.*;

public interface ViaInjector
{
    void inject() throws Exception;
    
    void uninject() throws Exception;
    
    default boolean lateProtocolVersionSetting() {
        return false;
    }
    
    int getServerProtocolVersion() throws Exception;
    
    default IntSortedSet getServerProtocolVersions() throws Exception {
        return IntSortedSets.singleton(this.getServerProtocolVersion());
    }
    
    default String getEncoderName() {
        return "via-encoder";
    }
    
    default String getDecoderName() {
        return "via-decoder";
    }
    
    JsonObject getDump();
}
