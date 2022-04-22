package net.minecraft.network.play.server;

import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class S38PacketPlayerListItem implements Packet
{
    private Action field_179770_a;
    private final List field_179769_b;
    private static final String __OBFID;
    
    public S38PacketPlayerListItem() {
        this.field_179769_b = Lists.newArrayList();
    }
    
    public S38PacketPlayerListItem(final Action field_179770_a, final EntityPlayerMP... array) {
        this.field_179769_b = Lists.newArrayList();
        this.field_179770_a = field_179770_a;
        while (0 < array.length) {
            final EntityPlayerMP entityPlayerMP = array[0];
            this.field_179769_b.add(new AddPlayerData(entityPlayerMP.getGameProfile(), entityPlayerMP.ping, entityPlayerMP.theItemInWorldManager.getGameType(), entityPlayerMP.func_175396_E()));
            int n = 0;
            ++n;
        }
    }
    
    public S38PacketPlayerListItem(final Action field_179770_a, final Iterable iterable) {
        this.field_179769_b = Lists.newArrayList();
        this.field_179770_a = field_179770_a;
        for (final EntityPlayerMP entityPlayerMP : iterable) {
            this.field_179769_b.add(new AddPlayerData(entityPlayerMP.getGameProfile(), entityPlayerMP.ping, entityPlayerMP.theItemInWorldManager.getGameType(), entityPlayerMP.func_175396_E()));
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179770_a = (Action)packetBuffer.readEnumValue(Action.class);
        while (0 < packetBuffer.readVarIntFromBuffer()) {
            GameProfile gameProfile = null;
            WorldSettings.GameType gameType = null;
            IChatComponent chatComponent = null;
            switch (SwitchAction.field_179938_a[this.field_179770_a.ordinal()]) {
                case 1: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), packetBuffer.readStringFromBuffer(16));
                    while (0 < packetBuffer.readVarIntFromBuffer()) {
                        final String stringFromBuffer = packetBuffer.readStringFromBuffer(32767);
                        final String stringFromBuffer2 = packetBuffer.readStringFromBuffer(32767);
                        if (packetBuffer.readBoolean()) {
                            gameProfile.getProperties().put(stringFromBuffer, new Property(stringFromBuffer, stringFromBuffer2, packetBuffer.readStringFromBuffer(32767)));
                        }
                        else {
                            gameProfile.getProperties().put(stringFromBuffer, new Property(stringFromBuffer, stringFromBuffer2));
                        }
                        int n = 0;
                        ++n;
                    }
                    gameType = WorldSettings.GameType.getByID(packetBuffer.readVarIntFromBuffer());
                    packetBuffer.readVarIntFromBuffer();
                    if (packetBuffer.readBoolean()) {
                        chatComponent = packetBuffer.readChatComponent();
                        break;
                    }
                    break;
                }
                case 2: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), null);
                    gameType = WorldSettings.GameType.getByID(packetBuffer.readVarIntFromBuffer());
                    break;
                }
                case 3: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), null);
                    packetBuffer.readVarIntFromBuffer();
                    break;
                }
                case 4: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), null);
                    if (packetBuffer.readBoolean()) {
                        chatComponent = packetBuffer.readChatComponent();
                        break;
                    }
                    break;
                }
                case 5: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), null);
                    break;
                }
            }
            this.field_179769_b.add(new AddPlayerData(gameProfile, 0, gameType, chatComponent));
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.field_179770_a);
        packetBuffer.writeVarIntToBuffer(this.field_179769_b.size());
        for (final AddPlayerData addPlayerData : this.field_179769_b) {
            switch (SwitchAction.field_179938_a[this.field_179770_a.ordinal()]) {
                case 1: {
                    packetBuffer.writeUuid(addPlayerData.func_179962_a().getId());
                    packetBuffer.writeString(addPlayerData.func_179962_a().getName());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.func_179962_a().getProperties().size());
                    for (final Property property : addPlayerData.func_179962_a().getProperties().values()) {
                        packetBuffer.writeString(property.getName());
                        packetBuffer.writeString(property.getValue());
                        if (property.hasSignature()) {
                            packetBuffer.writeBoolean(true);
                            packetBuffer.writeString(property.getSignature());
                        }
                        else {
                            packetBuffer.writeBoolean(false);
                        }
                    }
                    packetBuffer.writeVarIntToBuffer(addPlayerData.func_179960_c().getID());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.func_179963_b());
                    if (addPlayerData.func_179961_d() == null) {
                        packetBuffer.writeBoolean(false);
                        continue;
                    }
                    packetBuffer.writeBoolean(true);
                    packetBuffer.writeChatComponent(addPlayerData.func_179961_d());
                    continue;
                }
                case 4: {
                    packetBuffer.writeUuid(addPlayerData.func_179962_a().getId());
                    if (addPlayerData.func_179961_d() == null) {
                        packetBuffer.writeBoolean(false);
                        continue;
                    }
                    packetBuffer.writeBoolean(true);
                    packetBuffer.writeChatComponent(addPlayerData.func_179961_d());
                    continue;
                }
                default: {
                    continue;
                }
                case 2: {
                    packetBuffer.writeUuid(addPlayerData.func_179962_a().getId());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.func_179960_c().getID());
                    continue;
                }
                case 3: {
                    packetBuffer.writeUuid(addPlayerData.func_179962_a().getId());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.func_179963_b());
                    continue;
                }
                case 5: {
                    packetBuffer.writeUuid(addPlayerData.func_179962_a().getId());
                    continue;
                }
            }
        }
    }
    
    public void func_180743_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handlePlayerListItem(this);
    }
    
    public List func_179767_a() {
        return this.field_179769_b;
    }
    
    public Action func_179768_b() {
        return this.field_179770_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180743_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001318";
    }
    
    public enum Action
    {
        ADD_PLAYER("ADD_PLAYER", 0, "ADD_PLAYER", 0), 
        UPDATE_GAME_MODE("UPDATE_GAME_MODE", 1, "UPDATE_GAME_MODE", 1), 
        UPDATE_LATENCY("UPDATE_LATENCY", 2, "UPDATE_LATENCY", 2), 
        UPDATE_DISPLAY_NAME("UPDATE_DISPLAY_NAME", 3, "UPDATE_DISPLAY_NAME", 3), 
        REMOVE_PLAYER("REMOVE_PLAYER", 4, "REMOVE_PLAYER", 4);
        
        private static final Action[] $VALUES;
        private static final String __OBFID;
        private static final Action[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002295";
            ENUM$VALUES = new Action[] { Action.ADD_PLAYER, Action.UPDATE_GAME_MODE, Action.UPDATE_LATENCY, Action.UPDATE_DISPLAY_NAME, Action.REMOVE_PLAYER };
            $VALUES = new Action[] { Action.ADD_PLAYER, Action.UPDATE_GAME_MODE, Action.UPDATE_LATENCY, Action.UPDATE_DISPLAY_NAME, Action.REMOVE_PLAYER };
        }
        
        private Action(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    public class AddPlayerData
    {
        private final int field_179966_b;
        private final WorldSettings.GameType field_179967_c;
        private final GameProfile field_179964_d;
        private final IChatComponent field_179965_e;
        private static final String __OBFID;
        final S38PacketPlayerListItem this$0;
        
        public AddPlayerData(final S38PacketPlayerListItem this$0, final GameProfile field_179964_d, final int field_179966_b, final WorldSettings.GameType field_179967_c, final IChatComponent field_179965_e) {
            this.this$0 = this$0;
            this.field_179964_d = field_179964_d;
            this.field_179966_b = field_179966_b;
            this.field_179967_c = field_179967_c;
            this.field_179965_e = field_179965_e;
        }
        
        public GameProfile func_179962_a() {
            return this.field_179964_d;
        }
        
        public int func_179963_b() {
            return this.field_179966_b;
        }
        
        public WorldSettings.GameType func_179960_c() {
            return this.field_179967_c;
        }
        
        public IChatComponent func_179961_d() {
            return this.field_179965_e;
        }
        
        static {
            __OBFID = "CL_00002294";
        }
    }
    
    static final class SwitchAction
    {
        static final int[] field_179938_a;
        private static final String __OBFID;
        private static final String[] lllllIIIIlIIlll;
        private static String[] lllllIIIIlIlIII;
        
        static {
            lIllIlllIIIIlllI();
            lIllIlllIIIIllIl();
            __OBFID = SwitchAction.lllllIIIIlIIlll[0];
            field_179938_a = new int[Action.values().length];
            try {
                SwitchAction.field_179938_a[Action.ADD_PLAYER.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchAction.field_179938_a[Action.UPDATE_GAME_MODE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchAction.field_179938_a[Action.UPDATE_LATENCY.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchAction.field_179938_a[Action.UPDATE_DISPLAY_NAME.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchAction.field_179938_a[Action.REMOVE_PLAYER.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
        
        private static void lIllIlllIIIIllIl() {
            (lllllIIIIlIIlll = new String[1])[0] = lIllIlllIIIIllII(SwitchAction.lllllIIIIlIlIII[0], SwitchAction.lllllIIIIlIlIII[1]);
            SwitchAction.lllllIIIIlIlIII = null;
        }
        
        private static void lIllIlllIIIIlllI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchAction.lllllIIIIlIlIII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIllIlllIIIIllII(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
                final Cipher instance = Cipher.getInstance("DES");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
