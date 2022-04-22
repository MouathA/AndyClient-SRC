package com.mojang.realmsclient.dto;

import com.google.gson.*;
import com.mojang.realmsclient.util.*;
import net.minecraft.realms.*;

public class RealmsOptions
{
    public Boolean pvp;
    public Boolean spawnAnimals;
    public Boolean spawnMonsters;
    public Boolean spawnNPCs;
    public Integer spawnProtection;
    public Boolean commandBlocks;
    public Boolean forceGameMode;
    public Integer difficulty;
    public Integer gameMode;
    public String slotName;
    public long templateId;
    public String templateImage;
    public boolean empty;
    private static boolean forceGameModeDefault;
    private static boolean pvpDefault;
    private static boolean spawnAnimalsDefault;
    private static boolean spawnMonstersDefault;
    private static boolean spawnNPCsDefault;
    private static boolean commandBlocksDefault;
    private static String slotNameDefault;
    private static String templateImageDefault;
    
    public RealmsOptions(final Boolean pvp, final Boolean spawnAnimals, final Boolean spawnMonsters, final Boolean spawnNPCs, final Integer spawnProtection, final Boolean commandBlocks, final Integer difficulty, final Integer gameMode, final Boolean forceGameMode, final String slotName) {
        this.empty = false;
        this.pvp = pvp;
        this.spawnAnimals = spawnAnimals;
        this.spawnMonsters = spawnMonsters;
        this.spawnNPCs = spawnNPCs;
        this.spawnProtection = spawnProtection;
        this.commandBlocks = commandBlocks;
        this.difficulty = difficulty;
        this.gameMode = gameMode;
        this.forceGameMode = forceGameMode;
        this.slotName = slotName;
    }
    
    public static RealmsOptions getDefaults() {
        return new RealmsOptions(RealmsOptions.pvpDefault, RealmsOptions.spawnAnimalsDefault, RealmsOptions.spawnMonstersDefault, RealmsOptions.spawnNPCsDefault, 0, RealmsOptions.commandBlocksDefault, 2, 0, RealmsOptions.forceGameModeDefault, RealmsOptions.slotNameDefault);
    }
    
    public static RealmsOptions getEmptyDefaults() {
        final RealmsOptions realmsOptions = new RealmsOptions(RealmsOptions.pvpDefault, RealmsOptions.spawnAnimalsDefault, RealmsOptions.spawnMonstersDefault, RealmsOptions.spawnNPCsDefault, 0, RealmsOptions.commandBlocksDefault, 2, 0, RealmsOptions.forceGameModeDefault, RealmsOptions.slotNameDefault);
        realmsOptions.setEmpty(true);
        return realmsOptions;
    }
    
    public void setEmpty(final boolean empty) {
        this.empty = empty;
    }
    
    public static RealmsOptions parse(final JsonObject jsonObject) {
        final RealmsOptions realmsOptions = new RealmsOptions(JsonUtils.getBooleanOr("pvp", jsonObject, RealmsOptions.pvpDefault), JsonUtils.getBooleanOr("spawnAnimals", jsonObject, RealmsOptions.spawnAnimalsDefault), JsonUtils.getBooleanOr("spawnMonsters", jsonObject, RealmsOptions.spawnMonstersDefault), JsonUtils.getBooleanOr("spawnNPCs", jsonObject, RealmsOptions.spawnNPCsDefault), JsonUtils.getIntOr("spawnProtection", jsonObject, 0), JsonUtils.getBooleanOr("commandBlocks", jsonObject, RealmsOptions.commandBlocksDefault), JsonUtils.getIntOr("difficulty", jsonObject, 2), JsonUtils.getIntOr("gameMode", jsonObject, 0), JsonUtils.getBooleanOr("forceGameMode", jsonObject, RealmsOptions.forceGameModeDefault), JsonUtils.getStringOr("slotName", jsonObject, RealmsOptions.slotNameDefault));
        realmsOptions.templateId = JsonUtils.getLongOr("worldTemplateId", jsonObject, -1L);
        realmsOptions.templateImage = JsonUtils.getStringOr("worldTemplateImage", jsonObject, RealmsOptions.templateImageDefault);
        return realmsOptions;
    }
    
    public String getSlotName(final int n) {
        if (this.slotName != null && !this.slotName.equals("")) {
            return this.slotName;
        }
        if (this.empty) {
            return RealmsScreen.getLocalizedString("mco.configure.world.slot.empty");
        }
        return RealmsScreen.getLocalizedString("mco.configure.world.slot", n);
    }
    
    public String getDefaultSlotName(final int n) {
        return RealmsScreen.getLocalizedString("mco.configure.world.slot", n);
    }
    
    public String toJson() {
        final JsonObject jsonObject = new JsonObject();
        if (this.pvp != RealmsOptions.pvpDefault) {
            jsonObject.addProperty("pvp", this.pvp);
        }
        if (this.spawnAnimals != RealmsOptions.spawnAnimalsDefault) {
            jsonObject.addProperty("spawnAnimals", this.spawnAnimals);
        }
        if (this.spawnMonsters != RealmsOptions.spawnMonstersDefault) {
            jsonObject.addProperty("spawnMonsters", this.spawnMonsters);
        }
        if (this.spawnNPCs != RealmsOptions.spawnNPCsDefault) {
            jsonObject.addProperty("spawnNPCs", this.spawnNPCs);
        }
        if (this.spawnProtection != 0) {
            jsonObject.addProperty("spawnProtection", this.spawnProtection);
        }
        if (this.commandBlocks != RealmsOptions.commandBlocksDefault) {
            jsonObject.addProperty("commandBlocks", this.commandBlocks);
        }
        if (this.difficulty != 2) {
            jsonObject.addProperty("difficulty", this.difficulty);
        }
        if (this.gameMode != 0) {
            jsonObject.addProperty("gameMode", this.gameMode);
        }
        if (this.forceGameMode != RealmsOptions.forceGameModeDefault) {
            jsonObject.addProperty("forceGameMode", this.forceGameMode);
        }
        if (!this.slotName.equals(RealmsOptions.slotNameDefault) && !this.slotName.equals("")) {
            jsonObject.addProperty("slotName", this.slotName);
        }
        return jsonObject.toString();
    }
    
    public RealmsOptions clone() {
        return new RealmsOptions(this.pvp, this.spawnAnimals, this.spawnMonsters, this.spawnNPCs, this.spawnProtection, this.commandBlocks, this.difficulty, this.gameMode, this.forceGameMode, this.slotName);
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static {
        RealmsOptions.forceGameModeDefault = false;
        RealmsOptions.pvpDefault = true;
        RealmsOptions.spawnAnimalsDefault = true;
        RealmsOptions.spawnMonstersDefault = true;
        RealmsOptions.spawnNPCsDefault = true;
        RealmsOptions.commandBlocksDefault = false;
        RealmsOptions.slotNameDefault = null;
        RealmsOptions.templateImageDefault = null;
    }
}
