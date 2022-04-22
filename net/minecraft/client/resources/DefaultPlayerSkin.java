package net.minecraft.client.resources;

import net.minecraft.util.*;
import java.util.*;

public class DefaultPlayerSkin
{
    private static final ResourceLocation field_177337_a;
    private static final ResourceLocation field_177336_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002396";
        field_177337_a = new ResourceLocation("textures/entity/steve.png");
        field_177336_b = new ResourceLocation("textures/entity/alex.png");
    }
    
    public static ResourceLocation func_177335_a() {
        return DefaultPlayerSkin.field_177337_a;
    }
    
    public static ResourceLocation func_177334_a(final UUID uuid) {
        return func_177333_c(uuid) ? DefaultPlayerSkin.field_177336_b : DefaultPlayerSkin.field_177337_a;
    }
    
    public static String func_177332_b(final UUID uuid) {
        return func_177333_c(uuid) ? "slim" : "default";
    }
    
    private static boolean func_177333_c(final UUID uuid) {
        return (uuid.hashCode() & 0x1) == 0x1;
    }
    
    public static ResourceLocation getDefaultSkinLegacy() {
        return DefaultPlayerSkin.field_177337_a;
    }
}
