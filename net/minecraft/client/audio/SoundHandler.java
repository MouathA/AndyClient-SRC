package net.minecraft.client.audio;

import net.minecraft.server.gui.*;
import org.apache.logging.log4j.*;
import com.google.gson.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import java.io.*;
import org.apache.commons.io.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import org.apache.commons.lang3.*;
import java.util.*;
import java.nio.charset.*;

public class SoundHandler implements IResourceManagerReloadListener, IUpdatePlayerListBox
{
    private static final Logger logger;
    private static final Gson field_147699_c;
    private static final ParameterizedType field_147696_d;
    public static final SoundPoolEntry missing_sound;
    private final SoundRegistry sndRegistry;
    private final SoundManager sndManager;
    private final IResourceManager mcResourceManager;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001147";
        logger = LogManager.getLogger();
        field_147699_c = new GsonBuilder().registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
        field_147696_d = new ParameterizedType() {
            private static final String __OBFID;
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { String.class, SoundList.class };
            }
            
            @Override
            public Type getRawType() {
                return Map.class;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
            
            static {
                __OBFID = "CL_00001148";
            }
        };
        missing_sound = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0, 0.0, false);
    }
    
    public SoundHandler(final IResourceManager mcResourceManager, final GameSettings gameSettings) {
        this.sndRegistry = new SoundRegistry();
        this.mcResourceManager = mcResourceManager;
        this.sndManager = new SoundManager(this, gameSettings);
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.sndManager.reloadSoundSystem();
        this.sndRegistry.clearMap();
        for (final String s : resourceManager.getResourceDomains()) {
            final Iterator iterator2 = resourceManager.getAllResources(new ResourceLocation(s, "sounds.json")).iterator();
            while (iterator2.hasNext()) {
                for (final Map.Entry<String, V> entry : this.getSoundMap(iterator2.next().getInputStream()).entrySet()) {
                    this.loadSoundResource(new ResourceLocation(s, entry.getKey()), (SoundList)entry.getValue());
                }
            }
        }
    }
    
    protected Map getSoundMap(final InputStream inputStream) {
        final Map map = (Map)SoundHandler.field_147699_c.fromJson(new InputStreamReader(inputStream), SoundHandler.field_147696_d);
        IOUtils.closeQuietly(inputStream);
        return map;
    }
    
    private void loadSoundResource(final ResourceLocation resourceLocation, final SoundList list) {
        final boolean b = !this.sndRegistry.containsKey(resourceLocation);
        SoundEventAccessorComposite soundEventAccessorComposite;
        if (!b && !list.canReplaceExisting()) {
            soundEventAccessorComposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(resourceLocation);
        }
        else {
            if (!b) {
                SoundHandler.logger.debug("Replaced sound event location {}", resourceLocation);
            }
            soundEventAccessorComposite = new SoundEventAccessorComposite(resourceLocation, 1.0, 1.0, list.getSoundCategory());
            this.sndRegistry.registerSound(soundEventAccessorComposite);
        }
        for (final SoundList.SoundEntry soundEntry : list.getSoundList()) {
            final String soundEntryName = soundEntry.getSoundEntryName();
            final ResourceLocation resourceLocation2 = new ResourceLocation(soundEntryName);
            final String s = soundEntryName.contains(":") ? resourceLocation2.getResourceDomain() : resourceLocation.getResourceDomain();
            ISoundEventAccessor soundEventAccessor = null;
            switch (SwitchType.field_148765_a[soundEntry.getSoundEntryType().ordinal()]) {
                case 1: {
                    final ResourceLocation resourceLocation3 = new ResourceLocation(s, "sounds/" + resourceLocation2.getResourcePath() + ".ogg");
                    IOUtils.closeQuietly(this.mcResourceManager.getResource(resourceLocation3).getInputStream());
                    soundEventAccessor = new SoundEventAccessor(new SoundPoolEntry(resourceLocation3, soundEntry.getSoundEntryPitch(), soundEntry.getSoundEntryVolume(), soundEntry.isStreaming()), soundEntry.getSoundEntryWeight());
                    break;
                }
                case 2: {
                    soundEventAccessor = new ISoundEventAccessor(s, soundEntry) {
                        final ResourceLocation field_148726_a = new ResourceLocation(s, soundEntry.getSoundEntryName());
                        private static final String __OBFID;
                        final SoundHandler this$0;
                        
                        @Override
                        public int getWeight() {
                            final SoundEventAccessorComposite soundEventAccessorComposite = (SoundEventAccessorComposite)SoundHandler.access$0(this.this$0).getObject(this.field_148726_a);
                            return (soundEventAccessorComposite == null) ? 0 : soundEventAccessorComposite.getWeight();
                        }
                        
                        public SoundPoolEntry getEntry() {
                            final SoundEventAccessorComposite soundEventAccessorComposite = (SoundEventAccessorComposite)SoundHandler.access$0(this.this$0).getObject(this.field_148726_a);
                            return (SoundPoolEntry)((soundEventAccessorComposite == null) ? SoundHandler.missing_sound : soundEventAccessorComposite.cloneEntry());
                        }
                        
                        @Override
                        public Object cloneEntry() {
                            return this.getEntry();
                        }
                        
                        static {
                            __OBFID = "CL_00001149";
                        }
                    };
                    break;
                }
                default: {
                    throw new IllegalStateException("IN YOU FACE");
                }
            }
            soundEventAccessorComposite.addSoundToEventPool(soundEventAccessor);
        }
    }
    
    public SoundEventAccessorComposite getSound(final ResourceLocation resourceLocation) {
        return (SoundEventAccessorComposite)this.sndRegistry.getObject(resourceLocation);
    }
    
    public void playSound(final ISound sound) {
        this.sndManager.playSound(sound);
    }
    
    public void playDelayedSound(final ISound sound, final int n) {
        this.sndManager.playDelayedSound(sound, n);
    }
    
    public void setListener(final EntityPlayer entityPlayer, final float n) {
        this.sndManager.setListener(entityPlayer, n);
    }
    
    public void pauseSounds() {
        this.sndManager.pauseAllSounds();
    }
    
    public void stopSounds() {
        this.sndManager.stopAllSounds();
    }
    
    public void unloadSounds() {
        this.sndManager.unloadSoundSystem();
    }
    
    @Override
    public void update() {
        this.sndManager.updateAllSounds();
    }
    
    public void resumeSounds() {
        this.sndManager.resumeAllSounds();
    }
    
    public void setSoundLevel(final SoundCategory soundCategory, final float n) {
        if (soundCategory == SoundCategory.MASTER && n <= 0.0f) {
            this.stopSounds();
        }
        this.sndManager.setSoundCategoryVolume(soundCategory, n);
    }
    
    public void stopSound(final ISound sound) {
        this.sndManager.stopSound(sound);
    }
    
    public SoundEventAccessorComposite getRandomSoundFromCategories(final SoundCategory... array) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<ResourceLocation> iterator = this.sndRegistry.getKeys().iterator();
        while (iterator.hasNext()) {
            final SoundEventAccessorComposite soundEventAccessorComposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(iterator.next());
            if (ArrayUtils.contains(array, soundEventAccessorComposite.getSoundCategory())) {
                arrayList.add(soundEventAccessorComposite);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList.get(new Random().nextInt(arrayList.size()));
    }
    
    public boolean isSoundPlaying(final ISound sound) {
        return this.sndManager.isSoundPlaying(sound);
    }
    
    static SoundRegistry access$0(final SoundHandler soundHandler) {
        return soundHandler.sndRegistry;
    }
    
    static final class SwitchType
    {
        static final int[] field_148765_a;
        private static final String __OBFID;
        private static final String[] llIIlIIIIlllIll;
        private static String[] llIIlIIIIllllII;
        
        static {
            lIIlIIlllIlllIlI();
            lIIlIIlllIlllIIl();
            __OBFID = SwitchType.llIIlIIIIlllIll[0];
            field_148765_a = new int[SoundList.SoundEntry.Type.values().length];
            try {
                SwitchType.field_148765_a[SoundList.SoundEntry.Type.FILE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchType.field_148765_a[SoundList.SoundEntry.Type.SOUND_EVENT.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
        
        private static void lIIlIIlllIlllIIl() {
            (llIIlIIIIlllIll = new String[1])[0] = lIIlIIlllIlllIII(SwitchType.llIIlIIIIllllII[0], SwitchType.llIIlIIIIllllII[1]);
            SwitchType.llIIlIIIIllllII = null;
        }
        
        private static void lIIlIIlllIlllIlI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchType.llIIlIIIIllllII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIlIIlllIlllIII(String s, final String s2) {
            s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int n = 0;
            final char[] charArray2 = s.toCharArray();
            for (int length = charArray2.length, i = 0; i < length; ++i) {
                sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
                ++n;
            }
            return sb.toString();
        }
    }
}
