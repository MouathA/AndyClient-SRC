package optifine;

import net.minecraft.util.*;
import java.util.*;
import java.io.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.item.*;

public class ReflectorForge
{
    public static void FMLClientHandler_trackBrokenTexture(final ResourceLocation resourceLocation, final String s) {
        if (!Reflector.FMLClientHandler_trackBrokenTexture.exists()) {
            Reflector.call(Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]), Reflector.FMLClientHandler_trackBrokenTexture, resourceLocation, s);
        }
    }
    
    public static void FMLClientHandler_trackMissingTexture(final ResourceLocation resourceLocation) {
        if (!Reflector.FMLClientHandler_trackMissingTexture.exists()) {
            Reflector.call(Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]), Reflector.FMLClientHandler_trackMissingTexture, resourceLocation);
        }
    }
    
    public static void putLaunchBlackboard(final String s, final Object o) {
        final Map map = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);
        if (map != null) {
            map.put(s, o);
        }
    }
    
    public static InputStream getOptiFineResourceStream(String substring) {
        if (!Reflector.OptiFineClassTransformer_instance.exists()) {
            return null;
        }
        final Object fieldValue = Reflector.getFieldValue(Reflector.OptiFineClassTransformer_instance);
        if (fieldValue == null) {
            return null;
        }
        if (substring.startsWith("/")) {
            substring = substring.substring(1);
        }
        final byte[] array = (byte[])Reflector.call(fieldValue, Reflector.OptiFineClassTransformer_getOptiFineResource, substring);
        if (array == null) {
            return null;
        }
        return new ByteArrayInputStream(array);
    }
    
    public static boolean blockHasTileEntity(final IBlockState blockState) {
        final Block block = blockState.getBlock();
        return Reflector.ForgeBlock_hasTileEntity.exists() ? Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, blockState) : block.hasTileEntity();
    }
    
    public static boolean isItemDamaged(final ItemStack itemStack) {
        return Reflector.ForgeItem_showDurabilityBar.exists() ? Reflector.callBoolean(itemStack.getItem(), Reflector.ForgeItem_showDurabilityBar, itemStack) : itemStack.isItemDamaged();
    }
}
