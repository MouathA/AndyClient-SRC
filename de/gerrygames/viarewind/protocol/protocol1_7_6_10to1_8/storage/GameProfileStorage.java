package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viaversion.api.connection.*;
import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import de.gerrygames.viarewind.utils.*;
import com.viaversion.viaversion.util.*;

public class GameProfileStorage extends StoredObject
{
    private Map properties;
    
    public GameProfileStorage(final UserConnection userConnection) {
        super(userConnection);
        this.properties = new HashMap();
    }
    
    public GameProfile put(final UUID uuid, final String s) {
        final GameProfile gameProfile = new GameProfile(uuid, s);
        this.properties.put(uuid, gameProfile);
        return gameProfile;
    }
    
    public void putProperty(final UUID uuid, final Property property) {
        this.properties.computeIfAbsent(uuid, GameProfileStorage::lambda$putProperty$0).properties.add(property);
    }
    
    public void putProperty(final UUID uuid, final String s, final String s2, final String s3) {
        this.putProperty(uuid, new Property(s, s2, s3));
    }
    
    public GameProfile get(final UUID uuid) {
        return this.properties.get(uuid);
    }
    
    public GameProfile get(String lowerCase, final boolean b) {
        if (b) {
            lowerCase = lowerCase.toLowerCase();
        }
        for (final GameProfile gameProfile : this.properties.values()) {
            if (gameProfile.name == null) {
                continue;
            }
            if ((b ? gameProfile.name.toLowerCase() : gameProfile.name).equals(lowerCase)) {
                return gameProfile;
            }
        }
        return null;
    }
    
    public List getAllWithPrefix(String lowerCase, final boolean b) {
        if (b) {
            lowerCase = lowerCase.toLowerCase();
        }
        final ArrayList<GameProfile> list = new ArrayList<GameProfile>();
        for (final GameProfile gameProfile : this.properties.values()) {
            if (gameProfile.name == null) {
                continue;
            }
            if (!(b ? gameProfile.name.toLowerCase() : gameProfile.name).startsWith(lowerCase)) {
                continue;
            }
            list.add(gameProfile);
        }
        return list;
    }
    
    public GameProfile remove(final UUID uuid) {
        return this.properties.remove(uuid);
    }
    
    private static GameProfile lambda$putProperty$0(final UUID uuid, final UUID uuid2) {
        return new GameProfile(uuid, null);
    }
    
    public static class Property
    {
        public String name;
        public String value;
        public String signature;
        
        public Property(final String name, final String value, final String signature) {
            this.name = name;
            this.value = value;
            this.signature = signature;
        }
    }
    
    public static class GameProfile
    {
        public String name;
        public String displayName;
        public int ping;
        public UUID uuid;
        public List properties;
        public int gamemode;
        
        public GameProfile(final UUID uuid, final String name) {
            this.properties = new ArrayList();
            this.gamemode = 0;
            this.name = name;
            this.uuid = uuid;
        }
        
        public Item getSkull() {
            final CompoundTag compoundTag = new CompoundTag();
            final CompoundTag compoundTag2 = new CompoundTag();
            compoundTag.put("SkullOwner", compoundTag2);
            compoundTag2.put("Id", new StringTag(this.uuid.toString()));
            final CompoundTag compoundTag3 = new CompoundTag();
            compoundTag2.put("Properties", compoundTag3);
            final ListTag listTag = new ListTag(CompoundTag.class);
            compoundTag3.put("textures", listTag);
            for (final Property property : this.properties) {
                if (property.name.equals("textures")) {
                    final CompoundTag compoundTag4 = new CompoundTag();
                    compoundTag4.put("Value", new StringTag(property.value));
                    if (property.signature != null) {
                        compoundTag4.put("Signature", new StringTag(property.signature));
                    }
                    listTag.add(compoundTag4);
                }
            }
            return new DataItem(397, (byte)1, (short)3, compoundTag);
        }
        
        public String getDisplayName() {
            String s = (this.displayName == null) ? this.name : this.displayName;
            if (s.length() > 16) {
                s = ChatUtil.removeUnusedColor(s, 'f');
            }
            if (s.length() > 16) {
                s = ChatColorUtil.stripColor(s);
            }
            if (s.length() > 16) {
                s = s.substring(0, 16);
            }
            return s;
        }
        
        public void setDisplayName(final String displayName) {
            this.displayName = displayName;
        }
    }
}
