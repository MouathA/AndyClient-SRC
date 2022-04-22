package net.minecraft.client.audio;

import net.minecraft.server.gui.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class MusicTicker implements IUpdatePlayerListBox
{
    private final Random rand;
    private final Minecraft mc;
    private ISound currentMusic;
    private int timeUntilNextMusic;
    private static final String __OBFID;
    
    public MusicTicker(final Minecraft mc) {
        this.rand = new Random();
        this.timeUntilNextMusic = 100;
        this.mc = mc;
    }
    
    @Override
    public void update() {
        final MusicType ambientMusicType = this.mc.getAmbientMusicType();
        if (this.currentMusic != null) {
            if (!ambientMusicType.getMusicLocation().equals(this.currentMusic.getSoundLocation())) {
                Minecraft.getSoundHandler().stopSound(this.currentMusic);
                this.timeUntilNextMusic = MathHelper.getRandomIntegerInRange(this.rand, 0, ambientMusicType.getMinDelay() / 2);
            }
            if (!Minecraft.getSoundHandler().isSoundPlaying(this.currentMusic)) {
                this.currentMusic = null;
                this.timeUntilNextMusic = Math.min(MathHelper.getRandomIntegerInRange(this.rand, ambientMusicType.getMinDelay(), ambientMusicType.getMaxDelay()), this.timeUntilNextMusic);
            }
        }
        if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0) {
            this.currentMusic = PositionedSoundRecord.createPositionedSoundRecord(ambientMusicType.getMusicLocation());
            Minecraft.getSoundHandler().playSound(this.currentMusic);
            this.timeUntilNextMusic = Integer.MAX_VALUE;
        }
    }
    
    static {
        __OBFID = "CL_00001138";
    }
    
    public enum MusicType
    {
        MENU("MENU", 0, "MENU", 0, new ResourceLocation("minecraft:music.menu"), 20, 600), 
        GAME("GAME", 1, "GAME", 1, new ResourceLocation("minecraft:music.game"), 12000, 24000), 
        CREATIVE("CREATIVE", 2, "CREATIVE", 2, new ResourceLocation("minecraft:music.game.creative"), 1200, 3600), 
        CREDITS("CREDITS", 3, "CREDITS", 3, new ResourceLocation("minecraft:music.game.end.credits"), Integer.MAX_VALUE, Integer.MAX_VALUE), 
        NETHER("NETHER", 4, "NETHER", 4, new ResourceLocation("minecraft:music.game.nether"), 1200, 3600), 
        END_BOSS("END_BOSS", 5, "END_BOSS", 5, new ResourceLocation("minecraft:music.game.end.dragon"), 0, 0), 
        END("END", 6, "END", 6, new ResourceLocation("minecraft:music.game.end"), 6000, 24000);
        
        private final ResourceLocation musicLocation;
        private final int minDelay;
        private final int maxDelay;
        private static final MusicType[] $VALUES;
        private static final String __OBFID;
        private static final MusicType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001139";
            ENUM$VALUES = new MusicType[] { MusicType.MENU, MusicType.GAME, MusicType.CREATIVE, MusicType.CREDITS, MusicType.NETHER, MusicType.END_BOSS, MusicType.END };
            $VALUES = new MusicType[] { MusicType.MENU, MusicType.GAME, MusicType.CREATIVE, MusicType.CREDITS, MusicType.NETHER, MusicType.END_BOSS, MusicType.END };
        }
        
        private MusicType(final String s, final int n, final String s2, final int n2, final ResourceLocation musicLocation, final int minDelay, final int maxDelay) {
            this.musicLocation = musicLocation;
            this.minDelay = minDelay;
            this.maxDelay = maxDelay;
        }
        
        public ResourceLocation getMusicLocation() {
            return this.musicLocation;
        }
        
        public int getMinDelay() {
            return this.minDelay;
        }
        
        public int getMaxDelay() {
            return this.maxDelay;
        }
    }
}
