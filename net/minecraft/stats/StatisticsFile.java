package net.minecraft.stats;

import net.minecraft.server.*;
import java.io.*;
import org.apache.logging.log4j.*;
import org.apache.commons.io.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import com.google.gson.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;

public class StatisticsFile extends StatFileWriter
{
    private static final Logger logger;
    private final MinecraftServer field_150890_c;
    private final File field_150887_d;
    private final Set field_150888_e;
    private int field_150885_f;
    private boolean field_150886_g;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001471";
        logger = LogManager.getLogger();
    }
    
    public StatisticsFile(final MinecraftServer field_150890_c, final File field_150887_d) {
        this.field_150888_e = Sets.newHashSet();
        this.field_150885_f = -300;
        this.field_150886_g = false;
        this.field_150890_c = field_150890_c;
        this.field_150887_d = field_150887_d;
    }
    
    public void func_150882_a() {
        if (this.field_150887_d.isFile()) {
            this.field_150875_a.clear();
            this.field_150875_a.putAll(this.func_150881_a(FileUtils.readFileToString(this.field_150887_d)));
        }
    }
    
    public void func_150883_b() {
        FileUtils.writeStringToFile(this.field_150887_d, func_150880_a(this.field_150875_a));
    }
    
    @Override
    public void func_150873_a(final EntityPlayer entityPlayer, final StatBase statBase, final int n) {
        final int n2 = statBase.isAchievement() ? this.writeStat(statBase) : 0;
        super.func_150873_a(entityPlayer, statBase, n);
        this.field_150888_e.add(statBase);
        if (statBase.isAchievement() && n2 == 0 && n > 0) {
            this.field_150886_g = true;
            if (this.field_150890_c.isAnnouncingPlayerAchievements()) {
                this.field_150890_c.getConfigurationManager().sendChatMsg(new ChatComponentTranslation("chat.type.achievement", new Object[] { entityPlayer.getDisplayName(), statBase.func_150955_j() }));
            }
        }
        if (statBase.isAchievement() && n2 > 0 && n == 0) {
            this.field_150886_g = true;
            if (this.field_150890_c.isAnnouncingPlayerAchievements()) {
                this.field_150890_c.getConfigurationManager().sendChatMsg(new ChatComponentTranslation("chat.type.achievement.taken", new Object[] { entityPlayer.getDisplayName(), statBase.func_150955_j() }));
            }
        }
    }
    
    public Set func_150878_c() {
        final HashSet hashSet = Sets.newHashSet(this.field_150888_e);
        this.field_150888_e.clear();
        this.field_150886_g = false;
        return hashSet;
    }
    
    public Map func_150881_a(final String s) {
        final JsonElement parse = new JsonParser().parse(s);
        if (!parse.isJsonObject()) {
            return Maps.newHashMap();
        }
        final JsonObject asJsonObject = parse.getAsJsonObject();
        final HashMap hashMap = Maps.newHashMap();
        for (final Map.Entry<String, V> entry : asJsonObject.entrySet()) {
            final StatBase oneShotStat = StatList.getOneShotStat(entry.getKey());
            if (oneShotStat != null) {
                final TupleIntJsonSerializable tupleIntJsonSerializable = new TupleIntJsonSerializable();
                if (((JsonElement)entry.getValue()).isJsonPrimitive() && ((JsonElement)entry.getValue()).getAsJsonPrimitive().isNumber()) {
                    tupleIntJsonSerializable.setIntegerValue(((JsonElement)entry.getValue()).getAsInt());
                }
                else if (((JsonElement)entry.getValue()).isJsonObject()) {
                    final JsonObject asJsonObject2 = ((JsonElement)entry.getValue()).getAsJsonObject();
                    if (asJsonObject2.has("value") && asJsonObject2.get("value").isJsonPrimitive() && asJsonObject2.get("value").getAsJsonPrimitive().isNumber()) {
                        tupleIntJsonSerializable.setIntegerValue(asJsonObject2.getAsJsonPrimitive("value").getAsInt());
                    }
                    if (asJsonObject2.has("progress") && oneShotStat.func_150954_l() != null) {
                        final IJsonSerializable jsonSerializableValue = oneShotStat.func_150954_l().getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                        jsonSerializableValue.func_152753_a(asJsonObject2.get("progress"));
                        tupleIntJsonSerializable.setJsonSerializableValue(jsonSerializableValue);
                    }
                }
                hashMap.put(oneShotStat, tupleIntJsonSerializable);
            }
            else {
                StatisticsFile.logger.warn("Invalid statistic in " + this.field_150887_d + ": Don't know what " + entry.getKey() + " is");
            }
        }
        return hashMap;
    }
    
    public static String func_150880_a(final Map map) {
        final JsonObject jsonObject = new JsonObject();
        for (final Map.Entry<K, TupleIntJsonSerializable> entry : map.entrySet()) {
            if (entry.getValue().getJsonSerializableValue() != null) {
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("value", entry.getValue().getIntegerValue());
                jsonObject2.add("progress", entry.getValue().getJsonSerializableValue().getSerializableElement());
                jsonObject.add(((StatBase)entry.getKey()).statId, jsonObject2);
            }
            else {
                jsonObject.addProperty(((StatBase)entry.getKey()).statId, entry.getValue().getIntegerValue());
            }
        }
        return jsonObject.toString();
    }
    
    public void func_150877_d() {
        final Iterator<StatBase> iterator = this.field_150875_a.keySet().iterator();
        while (iterator.hasNext()) {
            this.field_150888_e.add(iterator.next());
        }
    }
    
    public void func_150876_a(final EntityPlayerMP entityPlayerMP) {
        final int tickCounter = this.field_150890_c.getTickCounter();
        final HashMap hashMap = Maps.newHashMap();
        if (this.field_150886_g || tickCounter - this.field_150885_f > 300) {
            this.field_150885_f = tickCounter;
            for (final StatBase statBase : this.func_150878_c()) {
                hashMap.put(statBase, this.writeStat(statBase));
            }
        }
        entityPlayerMP.playerNetServerHandler.sendPacket(new S37PacketStatistics(hashMap));
    }
    
    public void func_150884_b(final EntityPlayerMP entityPlayerMP) {
        final HashMap hashMap = Maps.newHashMap();
        for (final Achievement achievement : AchievementList.achievementList) {
            if (this.hasAchievementUnlocked(achievement)) {
                hashMap.put(achievement, this.writeStat(achievement));
                this.field_150888_e.remove(achievement);
            }
        }
        entityPlayerMP.playerNetServerHandler.sendPacket(new S37PacketStatistics(hashMap));
    }
    
    public boolean func_150879_e() {
        return this.field_150886_g;
    }
}
