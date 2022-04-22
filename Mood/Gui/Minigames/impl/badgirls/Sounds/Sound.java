package Mood.Gui.Minigames.impl.badgirls.Sounds;

import net.minecraft.client.*;
import java.io.*;
import javax.sound.sampled.*;
import java.util.*;
import Mood.*;

public class Sound extends Client
{
    public static AudioInputStream audioInputStream;
    public static Clip clip;
    
    public static boolean loadClip(final String s) {
        final StringBuilder sb = new StringBuilder();
        Minecraft.getMinecraft();
        Sound.audioInputStream = AudioSystem.getAudioInputStream(new File(sb.append(Minecraft.mcDataDir).append("/Hangok/").toString(), String.valueOf(String.valueOf(s)) + ".wav").getAbsoluteFile());
        (Sound.clip = AudioSystem.getClip()).open(Sound.audioInputStream);
        return true;
    }
    
    public static void setFramePos(final int framePosition) {
        Sound.clip.setFramePosition(framePosition);
    }
    
    public static int getFramePos() {
        return Sound.clip.getFramePosition();
    }
    
    public static void pauseSound() {
        Sound.clip.stop();
    }
    
    public static void stopSound() {
        Sound.clip.setFramePosition(0);
        Sound.clip.stop();
    }
    
    public static void setVolume(final Float n) {
        if (Sound.clip != null) {
            final FloatControl floatControl = (FloatControl)Sound.clip.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(floatControl.getValue() + n);
        }
    }
    
    private static void startSound() {
        Sound.clip.start();
    }
    
    public static ArrayList getSongs() {
        final File file = new File(String.valueOf(String.valueOf(HackedItemUtils.path)) + "/Hangok");
        final ArrayList<String> list = new ArrayList<String>();
        while (0 != file.listFiles().length) {
            if (file.listFiles()[0].getName().endsWith(".wav")) {
                list.add(file.listFiles()[0].getName().substring(0, file.listFiles()[0].getName().length() - 4));
            }
            int n = 0;
            ++n;
        }
        return list;
    }
}
