package com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds;

import java.util.*;

public enum SoundEffect
{
    MOB_HORSE_ZOMBIE_IDLE("MOB_HORSE_ZOMBIE_IDLE", 0, "mob.horse.zombie.idle", "entity.zombie_horse.ambient", SoundCategory.NEUTRAL), 
    NOTE_SNARE("NOTE_SNARE", 1, "note.snare", "block.note.snare", SoundCategory.RECORD), 
    RANDOM_WOOD_CLICK("RANDOM_WOOD_CLICK", 2, "random.wood_click", "block.wood_button.click_on", SoundCategory.BLOCK), 
    DIG_GRAVEL("DIG_GRAVEL", 3, "dig.gravel", "block.gravel.place", SoundCategory.BLOCK), 
    RANDOM_BOWHIT("RANDOM_BOWHIT", 4, "random.bowhit", "block.tripwire.detach", SoundCategory.NEUTRAL), 
    DIG_GLASS("DIG_GLASS", 5, "dig.glass", "block.glass.break", SoundCategory.BLOCK), 
    MOB_ZOMBIE_SAY("MOB_ZOMBIE_SAY", 6, "mob.zombie.say", "entity.zombie.ambient", SoundCategory.HOSTILE), 
    MOB_PIG_DEATH("MOB_PIG_DEATH", 7, "mob.pig.death", "entity.pig.death", SoundCategory.NEUTRAL), 
    MOB_HORSE_DONKEY_HIT("MOB_HORSE_DONKEY_HIT", 8, "mob.horse.donkey.hit", "entity.donkey.hurt", SoundCategory.NEUTRAL), 
    GAME_NEUTRAL_SWIM("GAME_NEUTRAL_SWIM", 9, "game.neutral.swim", "entity.player.swim", SoundCategory.NEUTRAL), 
    GAME_PLAYER_SWIM("GAME_PLAYER_SWIM", 10, "game.player.swim", "entity.player.swim", SoundCategory.PLAYER), 
    MOB_ENDERMEN_IDLE("MOB_ENDERMEN_IDLE", 11, "mob.endermen.idle", "entity.endermen.ambient", SoundCategory.HOSTILE), 
    PORTAL_PORTAL("PORTAL_PORTAL", 12, "portal.portal", "block.portal.ambient", SoundCategory.BLOCK), 
    RANDOM_FIZZ("RANDOM_FIZZ", 13, "random.fizz", "entity.generic.extinguish_fire", SoundCategory.BLOCK), 
    NOTE_HARP("NOTE_HARP", 14, "note.harp", "block.note.harp", SoundCategory.RECORD), 
    STEP_SNOW("STEP_SNOW", 15, "step.snow", "block.snow.step", SoundCategory.NEUTRAL), 
    RANDOM_SUCCESSFUL_HIT("RANDOM_SUCCESSFUL_HIT", 16, "random.successful_hit", "entity.arrow.hit_player", SoundCategory.PLAYER), 
    MOB_ZOMBIEPIG_ZPIGHURT("MOB_ZOMBIEPIG_ZPIGHURT", 17, "mob.zombiepig.zpighurt", "entity.zombie_pig.hurt", SoundCategory.HOSTILE), 
    MOB_WOLF_HOWL("MOB_WOLF_HOWL", 18, "mob.wolf.howl", "entity.wolf.howl", SoundCategory.NEUTRAL), 
    FIREWORKS_LAUNCH("FIREWORKS_LAUNCH", 19, "fireworks.launch", "entity.firework.launch", SoundCategory.AMBIENT), 
    MOB_COW_HURT("MOB_COW_HURT", 20, "mob.cow.hurt", "entity.cow.death", SoundCategory.NEUTRAL), 
    FIREWORKS_LARGEBLAST("FIREWORKS_LARGEBLAST", 21, "fireworks.largeBlast", "entity.firework.large_blast", SoundCategory.AMBIENT), 
    MOB_BLAZE_HIT("MOB_BLAZE_HIT", 22, "mob.blaze.hit", "entity.blaze.hurt", SoundCategory.HOSTILE), 
    MOB_VILLAGER_DEATH("MOB_VILLAGER_DEATH", 23, "mob.villager.death", "entity.villager.death", SoundCategory.NEUTRAL), 
    MOB_BLAZE_DEATH("MOB_BLAZE_DEATH", 24, "mob.blaze.death", "entity.blaze.death", SoundCategory.HOSTILE), 
    MOB_HORSE_ZOMBIE_DEATH("MOB_HORSE_ZOMBIE_DEATH", 25, "mob.horse.zombie.death", "entity.zombie_horse.death", SoundCategory.NEUTRAL), 
    MOB_SILVERFISH_KILL("MOB_SILVERFISH_KILL", 26, "mob.silverfish.kill", "entity.endermite.death", SoundCategory.HOSTILE), 
    MOB_WOLF_PANTING("MOB_WOLF_PANTING", 27, "mob.wolf.panting", "entity.wolf.pant", SoundCategory.NEUTRAL), 
    NOTE_BASS("NOTE_BASS", 28, "note.bass", "block.note.bass", SoundCategory.RECORD), 
    DIG_STONE("DIG_STONE", 29, "dig.stone", "block.glass.place", SoundCategory.BLOCK), 
    MOB_ENDERMEN_STARE("MOB_ENDERMEN_STARE", 30, "mob.endermen.stare", "entity.endermen.stare", SoundCategory.HOSTILE), 
    GAME_PLAYER_SWIM_SPLASH("GAME_PLAYER_SWIM_SPLASH", 31, "game.player.swim.splash", "entity.generic.splash", SoundCategory.BLOCK), 
    MOB_SLIME_SMALL("MOB_SLIME_SMALL", 32, "mob.slime.small", "block.slime.hit", SoundCategory.HOSTILE), 
    MOB_GHAST_DEATH("MOB_GHAST_DEATH", 33, "mob.ghast.death", "entity.ghast.death", SoundCategory.HOSTILE), 
    MOB_GUARDIAN_ATTACK("MOB_GUARDIAN_ATTACK", 34, "mob.guardian.attack", "entity.guardian.attack", SoundCategory.HOSTILE), 
    RANDOM_CLICK("RANDOM_CLICK", 35, "random.click", "block.wood_pressureplate.click_on", SoundCategory.BLOCK), 
    MOB_ZOMBIEPIG_ZPIG("MOB_ZOMBIEPIG_ZPIG", 36, "mob.zombiepig.zpig", "entity.zombie_pig.ambient", SoundCategory.HOSTILE), 
    GAME_PLAYER_DIE("GAME_PLAYER_DIE", 37, "game.player.die", "entity.player.death", SoundCategory.PLAYER), 
    FIREWORKS_TWINKLE_FAR("FIREWORKS_TWINKLE_FAR", 38, "fireworks.twinkle_far", "entity.firework.twinkle_far", SoundCategory.AMBIENT), 
    MOB_GUARDIAN_LAND_IDLE("MOB_GUARDIAN_LAND_IDLE", 39, "mob.guardian.land.idle", "entity.guardian.ambient_land", SoundCategory.HOSTILE), 
    DIG_GRASS("DIG_GRASS", 40, "dig.grass", "block.grass.place", SoundCategory.BLOCK), 
    MOB_SKELETON_STEP("MOB_SKELETON_STEP", 41, "mob.skeleton.step", "entity.skeleton.step", SoundCategory.HOSTILE), 
    MOB_WITHER_DEATH("MOB_WITHER_DEATH", 42, "mob.wither.death", "entity.wither.death", SoundCategory.HOSTILE), 
    MOB_WOLF_HURT("MOB_WOLF_HURT", 43, "mob.wolf.hurt", "entity.wolf.hurt", SoundCategory.NEUTRAL), 
    MOB_HORSE_LEATHER("MOB_HORSE_LEATHER", 44, "mob.horse.leather", "entity.horse.saddle", SoundCategory.NEUTRAL), 
    MOB_BAT_LOOP("MOB_BAT_LOOP", 45, "mob.bat.loop", "entity.bat.loop", SoundCategory.NEUTRAL), 
    MOB_GHAST_SCREAM("MOB_GHAST_SCREAM", 46, "mob.ghast.scream", "entity.ghast.hurt", SoundCategory.HOSTILE), 
    GAME_PLAYER_HURT("GAME_PLAYER_HURT", 47, "game.player.hurt", "entity.player.death", SoundCategory.PLAYER), 
    GAME_NEUTRAL_DIE("GAME_NEUTRAL_DIE", 48, "game.neutral.die", "entity.player.death", SoundCategory.NEUTRAL), 
    MOB_CREEPER_DEATH("MOB_CREEPER_DEATH", 49, "mob.creeper.death", "entity.creeper.death", SoundCategory.HOSTILE), 
    MOB_HORSE_GALLOP("MOB_HORSE_GALLOP", 50, "mob.horse.gallop", "entity.horse.gallop", SoundCategory.NEUTRAL), 
    MOB_WITHER_SPAWN("MOB_WITHER_SPAWN", 51, "mob.wither.spawn", "entity.wither.spawn", SoundCategory.HOSTILE), 
    MOB_ENDERMEN_HIT("MOB_ENDERMEN_HIT", 52, "mob.endermen.hit", "entity.endermen.hurt", SoundCategory.HOSTILE), 
    MOB_CREEPER_SAY("MOB_CREEPER_SAY", 53, "mob.creeper.say", "entity.creeper.hurt", SoundCategory.HOSTILE), 
    MOB_HORSE_WOOD("MOB_HORSE_WOOD", 54, "mob.horse.wood", "entity.horse.step_wood", SoundCategory.NEUTRAL), 
    MOB_ZOMBIE_UNFECT("MOB_ZOMBIE_UNFECT", 55, "mob.zombie.unfect", "entity.zombie_villager.converted", SoundCategory.HOSTILE), 
    RANDOM_ANVIL_USE("RANDOM_ANVIL_USE", 56, "random.anvil_use", "block.anvil.use", SoundCategory.BLOCK), 
    RANDOM_CHESTCLOSED("RANDOM_CHESTCLOSED", 57, "random.chestclosed", "block.chest.close", SoundCategory.BLOCK), 
    MOB_SHEEP_SHEAR("MOB_SHEEP_SHEAR", 58, "mob.sheep.shear", "entity.sheep.shear", SoundCategory.NEUTRAL), 
    RANDOM_POP("RANDOM_POP", 59, "random.pop", "entity.item.pickup", SoundCategory.PLAYER), 
    MOB_BAT_DEATH("MOB_BAT_DEATH", 60, "mob.bat.death", "entity.bat.death", SoundCategory.NEUTRAL), 
    DIG_WOOD("DIG_WOOD", 61, "dig.wood", "block.ladder.break", SoundCategory.BLOCK), 
    MOB_HORSE_DONKEY_DEATH("MOB_HORSE_DONKEY_DEATH", 62, "mob.horse.donkey.death", "entity.donkey.death", SoundCategory.NEUTRAL), 
    FIREWORKS_BLAST("FIREWORKS_BLAST", 63, "fireworks.blast", "entity.firework.blast", SoundCategory.AMBIENT), 
    MOB_ZOMBIEPIG_ZPIGANGRY("MOB_ZOMBIEPIG_ZPIGANGRY", 64, "mob.zombiepig.zpigangry", "entity.zombie_pig.angry", SoundCategory.HOSTILE), 
    GAME_HOSTILE_SWIM("GAME_HOSTILE_SWIM", 65, "game.hostile.swim", "entity.player.swim", SoundCategory.HOSTILE), 
    MOB_GUARDIAN_FLOP("MOB_GUARDIAN_FLOP", 66, "mob.guardian.flop", "entity.guardian.flop", SoundCategory.HOSTILE), 
    MOB_VILLAGER_YES("MOB_VILLAGER_YES", 67, "mob.villager.yes", "entity.villager.yes", SoundCategory.NEUTRAL), 
    MOB_GHAST_CHARGE("MOB_GHAST_CHARGE", 68, "mob.ghast.charge", "entity.ghast.warn", SoundCategory.HOSTILE), 
    CREEPER_PRIMED("CREEPER_PRIMED", 69, "creeper.primed", "entity.creeper.primed", SoundCategory.HOSTILE), 
    DIG_SAND("DIG_SAND", 70, "dig.sand", "block.sand.break", SoundCategory.BLOCK), 
    MOB_CHICKEN_SAY("MOB_CHICKEN_SAY", 71, "mob.chicken.say", "entity.chicken.ambient", SoundCategory.NEUTRAL), 
    RANDOM_DOOR_CLOSE("RANDOM_DOOR_CLOSE", 72, "random.door_close", "block.wooden_door.close", SoundCategory.BLOCK), 
    MOB_GUARDIAN_ELDER_DEATH("MOB_GUARDIAN_ELDER_DEATH", 73, "mob.guardian.elder.death", "entity.elder_guardian.death", SoundCategory.HOSTILE), 
    FIREWORKS_TWINKLE("FIREWORKS_TWINKLE", 74, "fireworks.twinkle", "entity.firework.twinkle", SoundCategory.AMBIENT), 
    MOB_HORSE_SKELETON_DEATH("MOB_HORSE_SKELETON_DEATH", 75, "mob.horse.skeleton.death", "entity.skeleton_horse.death", SoundCategory.NEUTRAL), 
    AMBIENT_WEATHER_RAIN("AMBIENT_WEATHER_RAIN", 76, "ambient.weather.rain", "weather.rain.above", SoundCategory.WEATHER), 
    PORTAL_TRIGGER("PORTAL_TRIGGER", 77, "portal.trigger", "block.portal.trigger", SoundCategory.BLOCK), 
    RANDOM_CHESTOPEN("RANDOM_CHESTOPEN", 78, "random.chestopen", "block.chest.open", SoundCategory.BLOCK), 
    MOB_HORSE_LAND("MOB_HORSE_LAND", 79, "mob.horse.land", "entity.horse.land", SoundCategory.NEUTRAL), 
    MOB_SILVERFISH_STEP("MOB_SILVERFISH_STEP", 80, "mob.silverfish.step", "entity.silverfish.step", SoundCategory.HOSTILE), 
    MOB_BAT_TAKEOFF("MOB_BAT_TAKEOFF", 81, "mob.bat.takeoff", "entity.bat.takeoff", SoundCategory.NEUTRAL), 
    MOB_VILLAGER_NO("MOB_VILLAGER_NO", 82, "mob.villager.no", "entity.villager.no", SoundCategory.NEUTRAL), 
    GAME_HOSTILE_HURT_FALL_BIG("GAME_HOSTILE_HURT_FALL_BIG", 83, "game.hostile.hurt.fall.big", "entity.hostile.big_fall", SoundCategory.HOSTILE), 
    MOB_IRONGOLEM_WALK("MOB_IRONGOLEM_WALK", 84, "mob.irongolem.walk", "entity.irongolem.step", SoundCategory.NEUTRAL), 
    NOTE_HAT("NOTE_HAT", 85, "note.hat", "block.note.hat", SoundCategory.RECORD), 
    MOB_ZOMBIE_METAL("MOB_ZOMBIE_METAL", 86, "mob.zombie.metal", "entity.zombie.attack_iron_door", SoundCategory.HOSTILE), 
    MOB_VILLAGER_HAGGLE("MOB_VILLAGER_HAGGLE", 87, "mob.villager.haggle", "entity.villager.trading", SoundCategory.NEUTRAL), 
    MOB_GHAST_FIREBALL("MOB_GHAST_FIREBALL", 88, "mob.ghast.fireball", "entity.blaze.shoot", SoundCategory.HOSTILE), 
    MOB_IRONGOLEM_DEATH("MOB_IRONGOLEM_DEATH", 89, "mob.irongolem.death", "entity.irongolem.death", SoundCategory.NEUTRAL), 
    RANDOM_BREAK("RANDOM_BREAK", 90, "random.break", "item.shield.break", SoundCategory.PLAYER), 
    MOB_ZOMBIE_REMEDY("MOB_ZOMBIE_REMEDY", 91, "mob.zombie.remedy", "entity.zombie_villager.cure", SoundCategory.HOSTILE), 
    RANDOM_BOW("RANDOM_BOW", 92, "random.bow", "entity.splash_potion.throw", SoundCategory.NEUTRAL), 
    MOB_VILLAGER_IDLE("MOB_VILLAGER_IDLE", 93, "mob.villager.idle", "entity.villager.ambient", SoundCategory.NEUTRAL), 
    STEP_CLOTH("STEP_CLOTH", 94, "step.cloth", "block.cloth.fall", SoundCategory.NEUTRAL), 
    MOB_SILVERFISH_HIT("MOB_SILVERFISH_HIT", 95, "mob.silverfish.hit", "entity.endermite.hurt", SoundCategory.HOSTILE), 
    LIQUID_LAVA("LIQUID_LAVA", 96, "liquid.lava", "block.lava.ambient", SoundCategory.BLOCK), 
    GAME_NEUTRAL_HURT_FALL_BIG("GAME_NEUTRAL_HURT_FALL_BIG", 97, "game.neutral.hurt.fall.big", "entity.hostile.big_fall", SoundCategory.NEUTRAL), 
    FIRE_FIRE("FIRE_FIRE", 98, "fire.fire", "block.fire.ambient", SoundCategory.BLOCK), 
    MOB_ZOMBIE_WOOD("MOB_ZOMBIE_WOOD", 99, "mob.zombie.wood", "entity.zombie.attack_door_wood", SoundCategory.HOSTILE), 
    MOB_CHICKEN_STEP("MOB_CHICKEN_STEP", 100, "mob.chicken.step", "entity.chicken.step", SoundCategory.NEUTRAL), 
    MOB_GUARDIAN_LAND_HIT("MOB_GUARDIAN_LAND_HIT", 101, "mob.guardian.land.hit", "entity.guardian.hurt_land", SoundCategory.HOSTILE), 
    MOB_CHICKEN_PLOP("MOB_CHICKEN_PLOP", 102, "mob.chicken.plop", "entity.donkey.chest", SoundCategory.NEUTRAL), 
    MOB_ENDERDRAGON_WINGS("MOB_ENDERDRAGON_WINGS", 103, "mob.enderdragon.wings", "entity.enderdragon.flap", SoundCategory.HOSTILE), 
    STEP_GRASS("STEP_GRASS", 104, "step.grass", "block.grass.hit", SoundCategory.NEUTRAL), 
    MOB_HORSE_BREATHE("MOB_HORSE_BREATHE", 105, "mob.horse.breathe", "entity.horse.breathe", SoundCategory.NEUTRAL), 
    GAME_PLAYER_HURT_FALL_BIG("GAME_PLAYER_HURT_FALL_BIG", 106, "game.player.hurt.fall.big", "entity.hostile.big_fall", SoundCategory.PLAYER), 
    MOB_HORSE_DONKEY_IDLE("MOB_HORSE_DONKEY_IDLE", 107, "mob.horse.donkey.idle", "entity.donkey.ambient", SoundCategory.NEUTRAL), 
    MOB_SPIDER_STEP("MOB_SPIDER_STEP", 108, "mob.spider.step", "entity.spider.step", SoundCategory.HOSTILE), 
    GAME_NEUTRAL_HURT("GAME_NEUTRAL_HURT", 109, "game.neutral.hurt", "entity.player.death", SoundCategory.NEUTRAL), 
    MOB_COW_SAY("MOB_COW_SAY", 110, "mob.cow.say", "entity.cow.ambient", SoundCategory.NEUTRAL), 
    MOB_HORSE_JUMP("MOB_HORSE_JUMP", 111, "mob.horse.jump", "entity.horse.jump", SoundCategory.NEUTRAL), 
    MOB_HORSE_SOFT("MOB_HORSE_SOFT", 112, "mob.horse.soft", "entity.horse.step", SoundCategory.NEUTRAL), 
    GAME_NEUTRAL_SWIM_SPLASH("GAME_NEUTRAL_SWIM_SPLASH", 113, "game.neutral.swim.splash", "entity.generic.splash", SoundCategory.NEUTRAL), 
    MOB_GUARDIAN_HIT("MOB_GUARDIAN_HIT", 114, "mob.guardian.hit", "entity.guardian.hurt", SoundCategory.HOSTILE), 
    MOB_ENDERDRAGON_END("MOB_ENDERDRAGON_END", 115, "mob.enderdragon.end", "entity.enderdragon.death", SoundCategory.HOSTILE), 
    MOB_ZOMBIE_STEP("MOB_ZOMBIE_STEP", 116, "mob.zombie.step", "entity.zombie.step", SoundCategory.HOSTILE), 
    MOB_ENDERDRAGON_GROWL("MOB_ENDERDRAGON_GROWL", 117, "mob.enderdragon.growl", "entity.enderdragon.growl", SoundCategory.HOSTILE), 
    MOB_WOLF_SHAKE("MOB_WOLF_SHAKE", 118, "mob.wolf.shake", "entity.wolf.shake", SoundCategory.NEUTRAL), 
    MOB_ENDERMEN_DEATH("MOB_ENDERMEN_DEATH", 119, "mob.endermen.death", "entity.endermen.death", SoundCategory.HOSTILE), 
    RANDOM_ANVIL_LAND("RANDOM_ANVIL_LAND", 120, "random.anvil_land", "block.anvil.land", SoundCategory.BLOCK), 
    GAME_HOSTILE_HURT("GAME_HOSTILE_HURT", 121, "game.hostile.hurt", "entity.player.death", SoundCategory.HOSTILE), 
    MINECART_INSIDE("MINECART_INSIDE", 122, "minecart.inside", "entity.minecart.inside", SoundCategory.PLAYER), 
    MOB_SLIME_BIG("MOB_SLIME_BIG", 123, "mob.slime.big", "entity.slime.death", SoundCategory.HOSTILE), 
    LIQUID_WATER("LIQUID_WATER", 124, "liquid.water", "block.water.ambient", SoundCategory.BLOCK), 
    MOB_PIG_SAY("MOB_PIG_SAY", 125, "mob.pig.say", "entity.pig.ambient", SoundCategory.NEUTRAL), 
    MOB_WITHER_SHOOT("MOB_WITHER_SHOOT", 126, "mob.wither.shoot", "entity.wither.shoot", SoundCategory.HOSTILE), 
    ITEM_FIRECHARGE_USE("ITEM_FIRECHARGE_USE", 127, "item.fireCharge.use", "entity.blaze.shoot", SoundCategory.BLOCK), 
    STEP_SAND("STEP_SAND", 128, "step.sand", "block.sand.fall", SoundCategory.NEUTRAL), 
    MOB_IRONGOLEM_HIT("MOB_IRONGOLEM_HIT", 129, "mob.irongolem.hit", "entity.irongolem.hurt", SoundCategory.NEUTRAL), 
    MOB_HORSE_DEATH("MOB_HORSE_DEATH", 130, "mob.horse.death", "entity.horse.death", SoundCategory.NEUTRAL), 
    MOB_BAT_HURT("MOB_BAT_HURT", 131, "mob.bat.hurt", "entity.bat.hurt", SoundCategory.NEUTRAL), 
    MOB_GHAST_AFFECTIONATE_SCREAM("MOB_GHAST_AFFECTIONATE_SCREAM", 132, "mob.ghast.affectionate_scream", "entity.ghast.scream", SoundCategory.HOSTILE), 
    MOB_GUARDIAN_ELDER_IDLE("MOB_GUARDIAN_ELDER_IDLE", 133, "mob.guardian.elder.idle", "entity.elder_guardian.ambient", SoundCategory.HOSTILE), 
    MOB_ZOMBIEPIG_ZPIGDEATH("MOB_ZOMBIEPIG_ZPIGDEATH", 134, "mob.zombiepig.zpigdeath", "entity.zombie_pig.death", SoundCategory.HOSTILE), 
    AMBIENT_WEATHER_THUNDER("AMBIENT_WEATHER_THUNDER", 135, "ambient.weather.thunder", "entity.lightning.thunder", SoundCategory.WEATHER), 
    MINECART_BASE("MINECART_BASE", 136, "minecart.base", "entity.minecart.riding", SoundCategory.NEUTRAL), 
    STEP_LADDER("STEP_LADDER", 137, "step.ladder", "block.ladder.hit", SoundCategory.NEUTRAL), 
    MOB_HORSE_DONKEY_ANGRY("MOB_HORSE_DONKEY_ANGRY", 138, "mob.horse.donkey.angry", "entity.donkey.angry", SoundCategory.NEUTRAL), 
    AMBIENT_CAVE_CAVE("AMBIENT_CAVE_CAVE", 139, "ambient.cave.cave", "ambient.cave", SoundCategory.AMBIENT), 
    FIREWORKS_BLAST_FAR("FIREWORKS_BLAST_FAR", 140, "fireworks.blast_far", "entity.firework.blast_far", SoundCategory.AMBIENT), 
    GAME_NEUTRAL_HURT_FALL_SMALL("GAME_NEUTRAL_HURT_FALL_SMALL", 141, "game.neutral.hurt.fall.small", "entity.generic.small_fall", SoundCategory.NEUTRAL), 
    GAME_HOSTILE_SWIM_SPLASH("GAME_HOSTILE_SWIM_SPLASH", 142, "game.hostile.swim.splash", "entity.generic.splash", SoundCategory.HOSTILE), 
    RANDOM_DRINK("RANDOM_DRINK", 143, "random.drink", "entity.generic.drink", SoundCategory.PLAYER), 
    GAME_HOSTILE_DIE("GAME_HOSTILE_DIE", 144, "game.hostile.die", "entity.player.death", SoundCategory.HOSTILE), 
    MOB_CAT_HISS("MOB_CAT_HISS", 145, "mob.cat.hiss", "entity.cat.hiss", SoundCategory.NEUTRAL), 
    NOTE_BD("NOTE_BD", 146, "note.bd", "block.note.basedrum", SoundCategory.RECORD), 
    MOB_SPIDER_SAY("MOB_SPIDER_SAY", 147, "mob.spider.say", "entity.spider.hurt", SoundCategory.HOSTILE), 
    STEP_STONE("STEP_STONE", 148, "step.stone", "block.anvil.hit", SoundCategory.NEUTRAL, true), 
    RANDOM_LEVELUP("RANDOM_LEVELUP", 149, "random.levelup", "entity.player.levelup", SoundCategory.PLAYER), 
    LIQUID_LAVAPOP("LIQUID_LAVAPOP", 150, "liquid.lavapop", "block.lava.pop", SoundCategory.BLOCK), 
    MOB_SHEEP_SAY("MOB_SHEEP_SAY", 151, "mob.sheep.say", "entity.sheep.ambient", SoundCategory.NEUTRAL), 
    MOB_SKELETON_SAY("MOB_SKELETON_SAY", 152, "mob.skeleton.say", "entity.skeleton.ambient", SoundCategory.HOSTILE), 
    MOB_BLAZE_BREATHE("MOB_BLAZE_BREATHE", 153, "mob.blaze.breathe", "entity.blaze.ambient", SoundCategory.HOSTILE), 
    MOB_BAT_IDLE("MOB_BAT_IDLE", 154, "mob.bat.idle", "entity.bat.ambient", SoundCategory.NEUTRAL), 
    MOB_MAGMACUBE_BIG("MOB_MAGMACUBE_BIG", 155, "mob.magmacube.big", "entity.magmacube.squish", SoundCategory.HOSTILE), 
    MOB_HORSE_IDLE("MOB_HORSE_IDLE", 156, "mob.horse.idle", "entity.horse.ambient", SoundCategory.NEUTRAL), 
    GAME_HOSTILE_HURT_FALL_SMALL("GAME_HOSTILE_HURT_FALL_SMALL", 157, "game.hostile.hurt.fall.small", "entity.generic.small_fall", SoundCategory.HOSTILE), 
    MOB_HORSE_ZOMBIE_HIT("MOB_HORSE_ZOMBIE_HIT", 158, "mob.horse.zombie.hit", "entity.zombie_horse.hurt", SoundCategory.NEUTRAL), 
    MOB_IRONGOLEM_THROW("MOB_IRONGOLEM_THROW", 159, "mob.irongolem.throw", "entity.irongolem.attack", SoundCategory.NEUTRAL), 
    DIG_CLOTH("DIG_CLOTH", 160, "dig.cloth", "block.cloth.place", SoundCategory.BLOCK), 
    STEP_GRAVEL("STEP_GRAVEL", 161, "step.gravel", "block.gravel.hit", SoundCategory.NEUTRAL), 
    MOB_SILVERFISH_SAY("MOB_SILVERFISH_SAY", 162, "mob.silverfish.say", "entity.silverfish.ambient", SoundCategory.HOSTILE), 
    MOB_CAT_PURR("MOB_CAT_PURR", 163, "mob.cat.purr", "entity.cat.purr", SoundCategory.NEUTRAL), 
    MOB_ZOMBIE_INFECT("MOB_ZOMBIE_INFECT", 164, "mob.zombie.infect", "entity.zombie.infect", SoundCategory.HOSTILE), 
    RANDOM_EAT("RANDOM_EAT", 165, "random.eat", "entity.generic.eat", SoundCategory.PLAYER), 
    MOB_WOLF_BARK("MOB_WOLF_BARK", 166, "mob.wolf.bark", "entity.wolf.ambient", SoundCategory.NEUTRAL), 
    GAME_TNT_PRIMED("GAME_TNT_PRIMED", 167, "game.tnt.primed", "entity.creeper.primed", SoundCategory.BLOCK), 
    MOB_SHEEP_STEP("MOB_SHEEP_STEP", 168, "mob.sheep.step", "entity.sheep.step", SoundCategory.NEUTRAL), 
    MOB_ZOMBIE_DEATH("MOB_ZOMBIE_DEATH", 169, "mob.zombie.death", "entity.zombie.death", SoundCategory.HOSTILE), 
    RANDOM_DOOR_OPEN("RANDOM_DOOR_OPEN", 170, "random.door_open", "block.wooden_door.open", SoundCategory.BLOCK), 
    MOB_ENDERMEN_PORTAL("MOB_ENDERMEN_PORTAL", 171, "mob.endermen.portal", "entity.endermen.teleport", SoundCategory.HOSTILE), 
    MOB_HORSE_ANGRY("MOB_HORSE_ANGRY", 172, "mob.horse.angry", "entity.horse.angry", SoundCategory.NEUTRAL), 
    MOB_WOLF_GROWL("MOB_WOLF_GROWL", 173, "mob.wolf.growl", "entity.wolf.growl", SoundCategory.NEUTRAL), 
    DIG_SNOW("DIG_SNOW", 174, "dig.snow", "block.snow.place", SoundCategory.BLOCK), 
    TILE_PISTON_OUT("TILE_PISTON_OUT", 175, "tile.piston.out", "block.piston.extend", SoundCategory.BLOCK), 
    RANDOM_BURP("RANDOM_BURP", 176, "random.burp", "entity.player.burp", SoundCategory.PLAYER), 
    MOB_COW_STEP("MOB_COW_STEP", 177, "mob.cow.step", "entity.cow.step", SoundCategory.NEUTRAL), 
    MOB_WITHER_HURT("MOB_WITHER_HURT", 178, "mob.wither.hurt", "entity.wither.hurt", SoundCategory.HOSTILE), 
    MOB_GUARDIAN_LAND_DEATH("MOB_GUARDIAN_LAND_DEATH", 179, "mob.guardian.land.death", "entity.elder_guardian.death_land", SoundCategory.HOSTILE), 
    MOB_CHICKEN_HURT("MOB_CHICKEN_HURT", 180, "mob.chicken.hurt", "entity.chicken.death", SoundCategory.NEUTRAL), 
    MOB_WOLF_STEP("MOB_WOLF_STEP", 181, "mob.wolf.step", "entity.wolf.step", SoundCategory.NEUTRAL), 
    MOB_WOLF_DEATH("MOB_WOLF_DEATH", 182, "mob.wolf.death", "entity.wolf.death", SoundCategory.NEUTRAL), 
    MOB_WOLF_WHINE("MOB_WOLF_WHINE", 183, "mob.wolf.whine", "entity.wolf.whine", SoundCategory.NEUTRAL), 
    NOTE_PLING("NOTE_PLING", 184, "note.pling", "block.note.pling", SoundCategory.RECORD), 
    GAME_PLAYER_HURT_FALL_SMALL("GAME_PLAYER_HURT_FALL_SMALL", 185, "game.player.hurt.fall.small", "entity.generic.small_fall", SoundCategory.PLAYER), 
    MOB_CAT_PURREOW("MOB_CAT_PURREOW", 186, "mob.cat.purreow", "entity.cat.purreow", SoundCategory.NEUTRAL), 
    FIREWORKS_LARGEBLAST_FAR("FIREWORKS_LARGEBLAST_FAR", 187, "fireworks.largeBlast_far", "entity.firework.large_blast_far", SoundCategory.AMBIENT), 
    MOB_SKELETON_HURT("MOB_SKELETON_HURT", 188, "mob.skeleton.hurt", "entity.skeleton.hurt", SoundCategory.HOSTILE), 
    MOB_SPIDER_DEATH("MOB_SPIDER_DEATH", 189, "mob.spider.death", "entity.spider.death", SoundCategory.HOSTILE), 
    RANDOM_ANVIL_BREAK("RANDOM_ANVIL_BREAK", 190, "random.anvil_break", "block.anvil.destroy", SoundCategory.BLOCK), 
    MOB_WITHER_IDLE("MOB_WITHER_IDLE", 191, "mob.wither.idle", "entity.wither.ambient", SoundCategory.HOSTILE), 
    MOB_GUARDIAN_ELDER_HIT("MOB_GUARDIAN_ELDER_HIT", 192, "mob.guardian.elder.hit", "entity.elder_guardian.hurt", SoundCategory.HOSTILE), 
    MOB_ENDERMEN_SCREAM("MOB_ENDERMEN_SCREAM", 193, "mob.endermen.scream", "entity.endermen.scream", SoundCategory.HOSTILE), 
    MOB_CAT_HITT("MOB_CAT_HITT", 194, "mob.cat.hitt", "entity.cat.hurt", SoundCategory.NEUTRAL), 
    MOB_MAGMACUBE_SMALL("MOB_MAGMACUBE_SMALL", 195, "mob.magmacube.small", "entity.small_magmacube.squish", SoundCategory.HOSTILE), 
    FIRE_IGNITE("FIRE_IGNITE", 196, "fire.ignite", "item.flintandsteel.use", SoundCategory.BLOCK, true), 
    MOB_ENDERDRAGON_HIT("MOB_ENDERDRAGON_HIT", 197, "mob.enderdragon.hit", "entity.enderdragon.hurt", SoundCategory.HOSTILE), 
    MOB_ZOMBIE_HURT("MOB_ZOMBIE_HURT", 198, "mob.zombie.hurt", "entity.zombie.hurt", SoundCategory.HOSTILE), 
    RANDOM_EXPLODE("RANDOM_EXPLODE", 199, "random.explode", "block.end_gateway.spawn", SoundCategory.BLOCK), 
    MOB_SLIME_ATTACK("MOB_SLIME_ATTACK", 200, "mob.slime.attack", "entity.slime.attack", SoundCategory.HOSTILE), 
    MOB_MAGMACUBE_JUMP("MOB_MAGMACUBE_JUMP", 201, "mob.magmacube.jump", "entity.magmacube.jump", SoundCategory.HOSTILE), 
    RANDOM_SPLASH("RANDOM_SPLASH", 202, "random.splash", "entity.bobber.splash", SoundCategory.PLAYER), 
    MOB_HORSE_SKELETON_HIT("MOB_HORSE_SKELETON_HIT", 203, "mob.horse.skeleton.hit", "entity.skeleton_horse.hurt", SoundCategory.NEUTRAL), 
    MOB_GHAST_MOAN("MOB_GHAST_MOAN", 204, "mob.ghast.moan", "entity.ghast.ambient", SoundCategory.HOSTILE), 
    MOB_GUARDIAN_CURSE("MOB_GUARDIAN_CURSE", 205, "mob.guardian.curse", "entity.elder_guardian.curse", SoundCategory.HOSTILE), 
    GAME_POTION_SMASH("GAME_POTION_SMASH", 206, "game.potion.smash", "block.glass.break", SoundCategory.NEUTRAL), 
    NOTE_BASSATTACK("NOTE_BASSATTACK", 207, "note.bassattack", "block.note.bass", SoundCategory.RECORD), 
    GUI_BUTTON_PRESS("GUI_BUTTON_PRESS", 208, "gui.button.press", "block.wood_pressureplate.click_on", SoundCategory.MASTER), 
    RANDOM_ORB("RANDOM_ORB", 209, "random.orb", "entity.experience_orb.pickup", SoundCategory.PLAYER), 
    MOB_ZOMBIE_WOODBREAK("MOB_ZOMBIE_WOODBREAK", 210, "mob.zombie.woodbreak", "entity.zombie.break_door_wood", SoundCategory.HOSTILE), 
    MOB_HORSE_ARMOR("MOB_HORSE_ARMOR", 211, "mob.horse.armor", "entity.horse.armor", SoundCategory.NEUTRAL), 
    TILE_PISTON_IN("TILE_PISTON_IN", 212, "tile.piston.in", "block.piston.contract", SoundCategory.BLOCK), 
    MOB_CAT_MEOW("MOB_CAT_MEOW", 213, "mob.cat.meow", "entity.cat.ambient", SoundCategory.NEUTRAL), 
    MOB_PIG_STEP("MOB_PIG_STEP", 214, "mob.pig.step", "entity.pig.step", SoundCategory.NEUTRAL), 
    STEP_WOOD("STEP_WOOD", 215, "step.wood", "block.wood.step", SoundCategory.NEUTRAL), 
    PORTAL_TRAVEL("PORTAL_TRAVEL", 216, "portal.travel", "block.portal.travel", SoundCategory.PLAYER), 
    MOB_GUARDIAN_DEATH("MOB_GUARDIAN_DEATH", 217, "mob.guardian.death", "entity.guardian.death", SoundCategory.HOSTILE), 
    MOB_SKELETON_DEATH("MOB_SKELETON_DEATH", 218, "mob.skeleton.death", "entity.skeleton.death", SoundCategory.HOSTILE), 
    MOB_HORSE_HIT("MOB_HORSE_HIT", 219, "mob.horse.hit", "entity.horse.hurt", SoundCategory.NEUTRAL), 
    MOB_VILLAGER_HIT("MOB_VILLAGER_HIT", 220, "mob.villager.hit", "entity.villager.hurt", SoundCategory.NEUTRAL), 
    MOB_HORSE_SKELETON_IDLE("MOB_HORSE_SKELETON_IDLE", 221, "mob.horse.skeleton.idle", "entity.skeleton_horse.ambient", SoundCategory.NEUTRAL), 
    RECORDS_CHIRP("RECORDS_CHIRP", 222, "records.chirp", "record.chirp", SoundCategory.RECORD), 
    MOB_RABBIT_HURT("MOB_RABBIT_HURT", 223, "mob.rabbit.hurt", "entity.rabbit.hurt", SoundCategory.NEUTRAL), 
    RECORDS_STAL("RECORDS_STAL", 224, "records.stal", "record.stal", SoundCategory.RECORD), 
    MUSIC_GAME_NETHER("MUSIC_GAME_NETHER", 225, "music.game.nether", "music.nether", SoundCategory.MUSIC), 
    MUSIC_MENU("MUSIC_MENU", 226, "music.menu", "music.menu", SoundCategory.MUSIC), 
    RECORDS_MELLOHI("RECORDS_MELLOHI", 227, "records.mellohi", "record.mellohi", SoundCategory.RECORD), 
    RECORDS_CAT("RECORDS_CAT", 228, "records.cat", "record.cat", SoundCategory.RECORD), 
    RECORDS_FAR("RECORDS_FAR", 229, "records.far", "record.far", SoundCategory.RECORD), 
    MUSIC_GAME_END_DRAGON("MUSIC_GAME_END_DRAGON", 230, "music.game.end.dragon", "music.dragon", SoundCategory.MUSIC), 
    MOB_RABBIT_DEATH("MOB_RABBIT_DEATH", 231, "mob.rabbit.death", "entity.rabbit.death", SoundCategory.NEUTRAL), 
    MOB_RABBIT_IDLE("MOB_RABBIT_IDLE", 232, "mob.rabbit.idle", "entity.rabbit.ambient", SoundCategory.NEUTRAL), 
    MUSIC_GAME_END("MUSIC_GAME_END", 233, "music.game.end", "music.end", SoundCategory.MUSIC), 
    MUSIC_GAME("MUSIC_GAME", 234, "music.game", "music.game", SoundCategory.MUSIC), 
    MOB_GUARDIAN_IDLE("MOB_GUARDIAN_IDLE", 235, "mob.guardian.idle", "entity.elder_guardian.ambient", SoundCategory.HOSTILE), 
    RECORDS_WARD("RECORDS_WARD", 236, "records.ward", "record.ward", SoundCategory.RECORD), 
    RECORDS_13("RECORDS_13", 237, "records.13", "record.13", SoundCategory.RECORD), 
    MOB_RABBIT_HOP("MOB_RABBIT_HOP", 238, "mob.rabbit.hop", "entity.rabbit.jump", SoundCategory.NEUTRAL), 
    RECORDS_STRAD("RECORDS_STRAD", 239, "records.strad", "record.strad", SoundCategory.RECORD), 
    RECORDS_11("RECORDS_11", 240, "records.11", "record.11", SoundCategory.RECORD), 
    RECORDS_MALL("RECORDS_MALL", 241, "records.mall", "record.mall", SoundCategory.RECORD), 
    RECORDS_BLOCKS("RECORDS_BLOCKS", 242, "records.blocks", "record.blocks", SoundCategory.RECORD), 
    RECORDS_WAIT("RECORDS_WAIT", 243, "records.wait", "record.wait", SoundCategory.RECORD), 
    MUSIC_GAME_END_CREDITS("MUSIC_GAME_END_CREDITS", 244, "music.game.end.credits", "music.credits", SoundCategory.MUSIC), 
    MUSIC_GAME_CREATIVE("MUSIC_GAME_CREATIVE", 245, "music.game.creative", "music.creative", SoundCategory.MUSIC);
    
    private final String name;
    private final String newName;
    private final SoundCategory category;
    private final boolean breaksound;
    private static final Map effects;
    private static final SoundEffect[] $VALUES;
    
    private SoundEffect(final String s, final int n, final String name, final String newName, final SoundCategory category) {
        this.category = category;
        this.newName = newName;
        this.name = name;
        this.breaksound = name.startsWith("dig.");
    }
    
    private SoundEffect(final String s, final int n, final String name, final String newName, final SoundCategory category, final boolean b) {
        this.category = category;
        this.newName = newName;
        this.name = name;
        this.breaksound = (name.startsWith("dig.") || b);
    }
    
    public static SoundEffect getByName(String lowerCase) {
        lowerCase = lowerCase.toLowerCase(Locale.ROOT);
        return SoundEffect.effects.get(lowerCase);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getNewName() {
        return this.newName;
    }
    
    public SoundCategory getCategory() {
        return this.category;
    }
    
    public boolean isBreaksound() {
        return this.breaksound;
    }
    
    static {
        $VALUES = new SoundEffect[] { SoundEffect.MOB_HORSE_ZOMBIE_IDLE, SoundEffect.NOTE_SNARE, SoundEffect.RANDOM_WOOD_CLICK, SoundEffect.DIG_GRAVEL, SoundEffect.RANDOM_BOWHIT, SoundEffect.DIG_GLASS, SoundEffect.MOB_ZOMBIE_SAY, SoundEffect.MOB_PIG_DEATH, SoundEffect.MOB_HORSE_DONKEY_HIT, SoundEffect.GAME_NEUTRAL_SWIM, SoundEffect.GAME_PLAYER_SWIM, SoundEffect.MOB_ENDERMEN_IDLE, SoundEffect.PORTAL_PORTAL, SoundEffect.RANDOM_FIZZ, SoundEffect.NOTE_HARP, SoundEffect.STEP_SNOW, SoundEffect.RANDOM_SUCCESSFUL_HIT, SoundEffect.MOB_ZOMBIEPIG_ZPIGHURT, SoundEffect.MOB_WOLF_HOWL, SoundEffect.FIREWORKS_LAUNCH, SoundEffect.MOB_COW_HURT, SoundEffect.FIREWORKS_LARGEBLAST, SoundEffect.MOB_BLAZE_HIT, SoundEffect.MOB_VILLAGER_DEATH, SoundEffect.MOB_BLAZE_DEATH, SoundEffect.MOB_HORSE_ZOMBIE_DEATH, SoundEffect.MOB_SILVERFISH_KILL, SoundEffect.MOB_WOLF_PANTING, SoundEffect.NOTE_BASS, SoundEffect.DIG_STONE, SoundEffect.MOB_ENDERMEN_STARE, SoundEffect.GAME_PLAYER_SWIM_SPLASH, SoundEffect.MOB_SLIME_SMALL, SoundEffect.MOB_GHAST_DEATH, SoundEffect.MOB_GUARDIAN_ATTACK, SoundEffect.RANDOM_CLICK, SoundEffect.MOB_ZOMBIEPIG_ZPIG, SoundEffect.GAME_PLAYER_DIE, SoundEffect.FIREWORKS_TWINKLE_FAR, SoundEffect.MOB_GUARDIAN_LAND_IDLE, SoundEffect.DIG_GRASS, SoundEffect.MOB_SKELETON_STEP, SoundEffect.MOB_WITHER_DEATH, SoundEffect.MOB_WOLF_HURT, SoundEffect.MOB_HORSE_LEATHER, SoundEffect.MOB_BAT_LOOP, SoundEffect.MOB_GHAST_SCREAM, SoundEffect.GAME_PLAYER_HURT, SoundEffect.GAME_NEUTRAL_DIE, SoundEffect.MOB_CREEPER_DEATH, SoundEffect.MOB_HORSE_GALLOP, SoundEffect.MOB_WITHER_SPAWN, SoundEffect.MOB_ENDERMEN_HIT, SoundEffect.MOB_CREEPER_SAY, SoundEffect.MOB_HORSE_WOOD, SoundEffect.MOB_ZOMBIE_UNFECT, SoundEffect.RANDOM_ANVIL_USE, SoundEffect.RANDOM_CHESTCLOSED, SoundEffect.MOB_SHEEP_SHEAR, SoundEffect.RANDOM_POP, SoundEffect.MOB_BAT_DEATH, SoundEffect.DIG_WOOD, SoundEffect.MOB_HORSE_DONKEY_DEATH, SoundEffect.FIREWORKS_BLAST, SoundEffect.MOB_ZOMBIEPIG_ZPIGANGRY, SoundEffect.GAME_HOSTILE_SWIM, SoundEffect.MOB_GUARDIAN_FLOP, SoundEffect.MOB_VILLAGER_YES, SoundEffect.MOB_GHAST_CHARGE, SoundEffect.CREEPER_PRIMED, SoundEffect.DIG_SAND, SoundEffect.MOB_CHICKEN_SAY, SoundEffect.RANDOM_DOOR_CLOSE, SoundEffect.MOB_GUARDIAN_ELDER_DEATH, SoundEffect.FIREWORKS_TWINKLE, SoundEffect.MOB_HORSE_SKELETON_DEATH, SoundEffect.AMBIENT_WEATHER_RAIN, SoundEffect.PORTAL_TRIGGER, SoundEffect.RANDOM_CHESTOPEN, SoundEffect.MOB_HORSE_LAND, SoundEffect.MOB_SILVERFISH_STEP, SoundEffect.MOB_BAT_TAKEOFF, SoundEffect.MOB_VILLAGER_NO, SoundEffect.GAME_HOSTILE_HURT_FALL_BIG, SoundEffect.MOB_IRONGOLEM_WALK, SoundEffect.NOTE_HAT, SoundEffect.MOB_ZOMBIE_METAL, SoundEffect.MOB_VILLAGER_HAGGLE, SoundEffect.MOB_GHAST_FIREBALL, SoundEffect.MOB_IRONGOLEM_DEATH, SoundEffect.RANDOM_BREAK, SoundEffect.MOB_ZOMBIE_REMEDY, SoundEffect.RANDOM_BOW, SoundEffect.MOB_VILLAGER_IDLE, SoundEffect.STEP_CLOTH, SoundEffect.MOB_SILVERFISH_HIT, SoundEffect.LIQUID_LAVA, SoundEffect.GAME_NEUTRAL_HURT_FALL_BIG, SoundEffect.FIRE_FIRE, SoundEffect.MOB_ZOMBIE_WOOD, SoundEffect.MOB_CHICKEN_STEP, SoundEffect.MOB_GUARDIAN_LAND_HIT, SoundEffect.MOB_CHICKEN_PLOP, SoundEffect.MOB_ENDERDRAGON_WINGS, SoundEffect.STEP_GRASS, SoundEffect.MOB_HORSE_BREATHE, SoundEffect.GAME_PLAYER_HURT_FALL_BIG, SoundEffect.MOB_HORSE_DONKEY_IDLE, SoundEffect.MOB_SPIDER_STEP, SoundEffect.GAME_NEUTRAL_HURT, SoundEffect.MOB_COW_SAY, SoundEffect.MOB_HORSE_JUMP, SoundEffect.MOB_HORSE_SOFT, SoundEffect.GAME_NEUTRAL_SWIM_SPLASH, SoundEffect.MOB_GUARDIAN_HIT, SoundEffect.MOB_ENDERDRAGON_END, SoundEffect.MOB_ZOMBIE_STEP, SoundEffect.MOB_ENDERDRAGON_GROWL, SoundEffect.MOB_WOLF_SHAKE, SoundEffect.MOB_ENDERMEN_DEATH, SoundEffect.RANDOM_ANVIL_LAND, SoundEffect.GAME_HOSTILE_HURT, SoundEffect.MINECART_INSIDE, SoundEffect.MOB_SLIME_BIG, SoundEffect.LIQUID_WATER, SoundEffect.MOB_PIG_SAY, SoundEffect.MOB_WITHER_SHOOT, SoundEffect.ITEM_FIRECHARGE_USE, SoundEffect.STEP_SAND, SoundEffect.MOB_IRONGOLEM_HIT, SoundEffect.MOB_HORSE_DEATH, SoundEffect.MOB_BAT_HURT, SoundEffect.MOB_GHAST_AFFECTIONATE_SCREAM, SoundEffect.MOB_GUARDIAN_ELDER_IDLE, SoundEffect.MOB_ZOMBIEPIG_ZPIGDEATH, SoundEffect.AMBIENT_WEATHER_THUNDER, SoundEffect.MINECART_BASE, SoundEffect.STEP_LADDER, SoundEffect.MOB_HORSE_DONKEY_ANGRY, SoundEffect.AMBIENT_CAVE_CAVE, SoundEffect.FIREWORKS_BLAST_FAR, SoundEffect.GAME_NEUTRAL_HURT_FALL_SMALL, SoundEffect.GAME_HOSTILE_SWIM_SPLASH, SoundEffect.RANDOM_DRINK, SoundEffect.GAME_HOSTILE_DIE, SoundEffect.MOB_CAT_HISS, SoundEffect.NOTE_BD, SoundEffect.MOB_SPIDER_SAY, SoundEffect.STEP_STONE, SoundEffect.RANDOM_LEVELUP, SoundEffect.LIQUID_LAVAPOP, SoundEffect.MOB_SHEEP_SAY, SoundEffect.MOB_SKELETON_SAY, SoundEffect.MOB_BLAZE_BREATHE, SoundEffect.MOB_BAT_IDLE, SoundEffect.MOB_MAGMACUBE_BIG, SoundEffect.MOB_HORSE_IDLE, SoundEffect.GAME_HOSTILE_HURT_FALL_SMALL, SoundEffect.MOB_HORSE_ZOMBIE_HIT, SoundEffect.MOB_IRONGOLEM_THROW, SoundEffect.DIG_CLOTH, SoundEffect.STEP_GRAVEL, SoundEffect.MOB_SILVERFISH_SAY, SoundEffect.MOB_CAT_PURR, SoundEffect.MOB_ZOMBIE_INFECT, SoundEffect.RANDOM_EAT, SoundEffect.MOB_WOLF_BARK, SoundEffect.GAME_TNT_PRIMED, SoundEffect.MOB_SHEEP_STEP, SoundEffect.MOB_ZOMBIE_DEATH, SoundEffect.RANDOM_DOOR_OPEN, SoundEffect.MOB_ENDERMEN_PORTAL, SoundEffect.MOB_HORSE_ANGRY, SoundEffect.MOB_WOLF_GROWL, SoundEffect.DIG_SNOW, SoundEffect.TILE_PISTON_OUT, SoundEffect.RANDOM_BURP, SoundEffect.MOB_COW_STEP, SoundEffect.MOB_WITHER_HURT, SoundEffect.MOB_GUARDIAN_LAND_DEATH, SoundEffect.MOB_CHICKEN_HURT, SoundEffect.MOB_WOLF_STEP, SoundEffect.MOB_WOLF_DEATH, SoundEffect.MOB_WOLF_WHINE, SoundEffect.NOTE_PLING, SoundEffect.GAME_PLAYER_HURT_FALL_SMALL, SoundEffect.MOB_CAT_PURREOW, SoundEffect.FIREWORKS_LARGEBLAST_FAR, SoundEffect.MOB_SKELETON_HURT, SoundEffect.MOB_SPIDER_DEATH, SoundEffect.RANDOM_ANVIL_BREAK, SoundEffect.MOB_WITHER_IDLE, SoundEffect.MOB_GUARDIAN_ELDER_HIT, SoundEffect.MOB_ENDERMEN_SCREAM, SoundEffect.MOB_CAT_HITT, SoundEffect.MOB_MAGMACUBE_SMALL, SoundEffect.FIRE_IGNITE, SoundEffect.MOB_ENDERDRAGON_HIT, SoundEffect.MOB_ZOMBIE_HURT, SoundEffect.RANDOM_EXPLODE, SoundEffect.MOB_SLIME_ATTACK, SoundEffect.MOB_MAGMACUBE_JUMP, SoundEffect.RANDOM_SPLASH, SoundEffect.MOB_HORSE_SKELETON_HIT, SoundEffect.MOB_GHAST_MOAN, SoundEffect.MOB_GUARDIAN_CURSE, SoundEffect.GAME_POTION_SMASH, SoundEffect.NOTE_BASSATTACK, SoundEffect.GUI_BUTTON_PRESS, SoundEffect.RANDOM_ORB, SoundEffect.MOB_ZOMBIE_WOODBREAK, SoundEffect.MOB_HORSE_ARMOR, SoundEffect.TILE_PISTON_IN, SoundEffect.MOB_CAT_MEOW, SoundEffect.MOB_PIG_STEP, SoundEffect.STEP_WOOD, SoundEffect.PORTAL_TRAVEL, SoundEffect.MOB_GUARDIAN_DEATH, SoundEffect.MOB_SKELETON_DEATH, SoundEffect.MOB_HORSE_HIT, SoundEffect.MOB_VILLAGER_HIT, SoundEffect.MOB_HORSE_SKELETON_IDLE, SoundEffect.RECORDS_CHIRP, SoundEffect.MOB_RABBIT_HURT, SoundEffect.RECORDS_STAL, SoundEffect.MUSIC_GAME_NETHER, SoundEffect.MUSIC_MENU, SoundEffect.RECORDS_MELLOHI, SoundEffect.RECORDS_CAT, SoundEffect.RECORDS_FAR, SoundEffect.MUSIC_GAME_END_DRAGON, SoundEffect.MOB_RABBIT_DEATH, SoundEffect.MOB_RABBIT_IDLE, SoundEffect.MUSIC_GAME_END, SoundEffect.MUSIC_GAME, SoundEffect.MOB_GUARDIAN_IDLE, SoundEffect.RECORDS_WARD, SoundEffect.RECORDS_13, SoundEffect.MOB_RABBIT_HOP, SoundEffect.RECORDS_STRAD, SoundEffect.RECORDS_11, SoundEffect.RECORDS_MALL, SoundEffect.RECORDS_BLOCKS, SoundEffect.RECORDS_WAIT, SoundEffect.MUSIC_GAME_END_CREDITS, SoundEffect.MUSIC_GAME_CREATIVE };
        effects = new HashMap();
        final SoundEffect[] values = values();
        while (0 < values.length) {
            final SoundEffect soundEffect = values[0];
            SoundEffect.effects.put(soundEffect.getName(), soundEffect);
            int n = 0;
            ++n;
        }
    }
}
