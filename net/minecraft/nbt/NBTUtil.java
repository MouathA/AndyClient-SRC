package net.minecraft.nbt;

import com.mojang.authlib.*;
import net.minecraft.util.*;
import com.mojang.authlib.properties.*;
import java.util.*;

public final class NBTUtil
{
    private static final String __OBFID;
    
    public static GameProfile readGameProfileFromNBT(final NBTTagCompound nbtTagCompound) {
        String string = null;
        String string2 = null;
        if (nbtTagCompound.hasKey("Name", 8)) {
            string = nbtTagCompound.getString("Name");
        }
        if (nbtTagCompound.hasKey("Id", 8)) {
            string2 = nbtTagCompound.getString("Id");
        }
        if (StringUtils.isNullOrEmpty(string) && StringUtils.isNullOrEmpty(string2)) {
            return null;
        }
        final GameProfile gameProfile = new GameProfile(UUID.fromString(string2), string);
        if (nbtTagCompound.hasKey("Properties", 10)) {
            final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag("Properties");
            for (final String s : compoundTag.getKeySet()) {
                final NBTTagList tagList = compoundTag.getTagList(s, 10);
                while (0 < tagList.tagCount()) {
                    final NBTTagCompound compoundTag2 = tagList.getCompoundTagAt(0);
                    final String string3 = compoundTag2.getString("Value");
                    if (compoundTag2.hasKey("Signature", 8)) {
                        gameProfile.getProperties().put(s, new Property(s, string3, compoundTag2.getString("Signature")));
                    }
                    else {
                        gameProfile.getProperties().put(s, new Property(s, string3));
                    }
                    int n = 0;
                    ++n;
                }
            }
        }
        return gameProfile;
    }
    
    public static NBTTagCompound writeGameProfile(final NBTTagCompound nbtTagCompound, final GameProfile gameProfile) {
        if (!StringUtils.isNullOrEmpty(gameProfile.getName())) {
            nbtTagCompound.setString("Name", gameProfile.getName());
        }
        if (gameProfile.getId() != null) {
            nbtTagCompound.setString("Id", gameProfile.getId().toString());
        }
        if (!gameProfile.getProperties().isEmpty()) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            for (final String s : gameProfile.getProperties().keySet()) {
                final NBTTagList list = new NBTTagList();
                for (final Property property : gameProfile.getProperties().get(s)) {
                    final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
                    nbtTagCompound3.setString("Value", property.getValue());
                    if (property.hasSignature()) {
                        nbtTagCompound3.setString("Signature", property.getSignature());
                    }
                    list.appendTag(nbtTagCompound3);
                }
                nbtTagCompound2.setTag(s, list);
            }
            nbtTagCompound.setTag("Properties", nbtTagCompound2);
        }
        return nbtTagCompound;
    }
    
    static {
        __OBFID = "CL_00001901";
    }
}
