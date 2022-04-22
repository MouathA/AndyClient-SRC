package net.minecraft.client.resources;

import java.util.concurrent.locks.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.settings.*;
import com.google.common.collect.*;
import com.google.common.hash.*;
import com.google.common.io.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.util.concurrent.*;
import com.google.common.util.concurrent.*;
import java.util.*;
import net.minecraft.client.resources.data.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import org.apache.commons.io.*;
import net.minecraft.util.*;

public class ResourcePackRepository
{
    private static final Logger field_177320_c;
    private static final FileFilter resourcePackFilter;
    private final File dirResourcepacks;
    public final IResourcePack rprDefaultResourcePack;
    private final File field_148534_e;
    public final IMetadataSerializer rprMetadataSerializer;
    private IResourcePack field_148532_f;
    private final ReentrantLock field_177321_h;
    private ListenableFuture field_177322_i;
    private List repositoryEntriesAll;
    private List repositoryEntries;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001087";
        field_177320_c = LogManager.getLogger();
        resourcePackFilter = new FileFilter() {
            private static final String __OBFID;
            
            @Override
            public boolean accept(final File file) {
                final boolean b = file.isFile() && file.getName().endsWith(".zip");
                final boolean b2 = file.isDirectory() && new File(file, "pack.mcmeta").isFile();
                return b || b2;
            }
            
            static {
                __OBFID = "CL_00001088";
            }
        };
    }
    
    public ResourcePackRepository(final File dirResourcepacks, final File field_148534_e, final IResourcePack rprDefaultResourcePack, final IMetadataSerializer rprMetadataSerializer, final GameSettings gameSettings) {
        this.field_177321_h = new ReentrantLock();
        this.repositoryEntriesAll = Lists.newArrayList();
        this.repositoryEntries = Lists.newArrayList();
        this.dirResourcepacks = dirResourcepacks;
        this.field_148534_e = field_148534_e;
        this.rprDefaultResourcePack = rprDefaultResourcePack;
        this.rprMetadataSerializer = rprMetadataSerializer;
        this.fixDirResourcepacks();
        this.updateRepositoryEntriesAll();
        for (final String s : gameSettings.resourcePacks) {
            for (final Entry entry : this.repositoryEntriesAll) {
                if (entry.getResourcePackName().equals(s)) {
                    this.repositoryEntries.add(entry);
                    break;
                }
            }
        }
    }
    
    private void fixDirResourcepacks() {
        if (!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs())) {
            ResourcePackRepository.field_177320_c.debug("Unable to create resourcepack folder: " + this.dirResourcepacks);
        }
    }
    
    private List getResourcePackFiles() {
        return this.dirResourcepacks.isDirectory() ? Arrays.asList(this.dirResourcepacks.listFiles(ResourcePackRepository.resourcePackFilter)) : Collections.emptyList();
    }
    
    public void updateRepositoryEntriesAll() {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<File> iterator = this.getResourcePackFiles().iterator();
        while (iterator.hasNext()) {
            final Entry entry = new Entry(iterator.next(), null);
            if (!this.repositoryEntriesAll.contains(entry)) {
                entry.updateResourcePack();
                arrayList.add(entry);
            }
            else {
                final int index = this.repositoryEntriesAll.indexOf(entry);
                if (index <= -1 || index >= this.repositoryEntriesAll.size()) {
                    continue;
                }
                arrayList.add(this.repositoryEntriesAll.get(index));
            }
        }
        this.repositoryEntriesAll.removeAll(arrayList);
        final Iterator<Entry> iterator2 = this.repositoryEntriesAll.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().closeResourcePack();
        }
        this.repositoryEntriesAll = arrayList;
    }
    
    public List getRepositoryEntriesAll() {
        return ImmutableList.copyOf(this.repositoryEntriesAll);
    }
    
    public List getRepositoryEntries() {
        return ImmutableList.copyOf(this.repositoryEntries);
    }
    
    public void func_148527_a(final List list) {
        this.repositoryEntries.clear();
        this.repositoryEntries.addAll(list);
    }
    
    public File getDirResourcepacks() {
        return this.dirResourcepacks;
    }
    
    public ListenableFuture func_180601_a(final String s, final String s2) {
        String string;
        if (s2.matches("^[a-f0-9]{40}$")) {
            string = s2;
        }
        else {
            String s3 = s.substring(s.lastIndexOf("/") + 1);
            if (s3.contains("?")) {
                s3 = s3.substring(0, s3.indexOf("?"));
            }
            if (!s3.endsWith(".zip")) {
                return Futures.immediateFailedFuture(new IllegalArgumentException("Invalid filename; must end in .zip"));
            }
            string = "legacy_" + s3.replaceAll("\\W", "");
        }
        final File file = new File(this.field_148534_e, string);
        this.field_177321_h.lock();
        this.func_148529_f();
        if (file.exists() && s2.length() == 40) {
            final String string2 = Hashing.sha1().hashBytes(Files.toByteArray(file)).toString();
            if (string2.equals(s2)) {
                final ListenableFuture func_177319_a = this.func_177319_a(file);
                this.field_177321_h.unlock();
                return func_177319_a;
            }
            ResourcePackRepository.field_177320_c.warn("File " + file + " had wrong hash (expected " + s2 + ", found " + string2 + "). Deleting it.");
            FileUtils.deleteQuietly(file);
        }
        final GuiScreenWorking guiScreenWorking = new GuiScreenWorking();
        final Map func_175596_ai = Minecraft.func_175596_ai();
        final Minecraft minecraft = Minecraft.getMinecraft();
        Futures.getUnchecked(minecraft.addScheduledTask(new Runnable(minecraft, guiScreenWorking) {
            private static final String __OBFID;
            final ResourcePackRepository this$0;
            private final Minecraft val$var7;
            private final GuiScreenWorking val$var15;
            
            @Override
            public void run() {
                this.val$var7.displayGuiScreen(this.val$var15);
            }
            
            static {
                __OBFID = "CL_00001089";
            }
        }));
        Futures.addCallback(this.field_177322_i = HttpUtil.func_180192_a(file, s, func_175596_ai, 52428800, guiScreenWorking, minecraft.getProxy()), new FutureCallback(file, SettableFuture.create()) {
            private static final String __OBFID;
            final ResourcePackRepository this$0;
            private final File val$var4;
            private final SettableFuture val$var8;
            
            @Override
            public void onSuccess(final Object o) {
                this.this$0.func_177319_a(this.val$var4);
                this.val$var8.set(null);
            }
            
            @Override
            public void onFailure(final Throwable exception) {
                this.val$var8.setException(exception);
            }
            
            static {
                __OBFID = "CL_00002394";
            }
        });
        final ListenableFuture field_177322_i = this.field_177322_i;
        this.field_177321_h.unlock();
        return field_177322_i;
    }
    
    public ListenableFuture func_177319_a(final File file) {
        this.field_148532_f = new FileResourcePack(file);
        return Minecraft.getMinecraft().func_175603_A();
    }
    
    public IResourcePack getResourcePackInstance() {
        return this.field_148532_f;
    }
    
    public void func_148529_f() {
        this.field_177321_h.lock();
        if (this.field_177322_i != null) {
            this.field_177322_i.cancel(true);
        }
        this.field_177322_i = null;
        this.field_148532_f = null;
        this.field_177321_h.unlock();
    }
    
    public class Entry
    {
        private final File resourcePackFile;
        private IResourcePack reResourcePack;
        private PackMetadataSection rePackMetadataSection;
        private BufferedImage texturePackIcon;
        private ResourceLocation locationTexturePackIcon;
        private static final String __OBFID;
        final ResourcePackRepository this$0;
        
        private Entry(final ResourcePackRepository this$0, final File resourcePackFile) {
            this.this$0 = this$0;
            this.resourcePackFile = resourcePackFile;
        }
        
        public void updateResourcePack() throws IOException {
            this.reResourcePack = (this.resourcePackFile.isDirectory() ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile));
            this.rePackMetadataSection = (PackMetadataSection)this.reResourcePack.getPackMetadata(this.this$0.rprMetadataSerializer, "pack");
            this.texturePackIcon = this.reResourcePack.getPackImage();
            if (this.texturePackIcon == null) {
                this.texturePackIcon = this.this$0.rprDefaultResourcePack.getPackImage();
            }
            this.closeResourcePack();
        }
        
        public void bindTexturePackIcon(final TextureManager textureManager) {
            if (this.locationTexturePackIcon == null) {
                this.locationTexturePackIcon = textureManager.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
            }
            textureManager.bindTexture(this.locationTexturePackIcon);
        }
        
        public void closeResourcePack() {
            if (this.reResourcePack instanceof Closeable) {
                IOUtils.closeQuietly((Closeable)this.reResourcePack);
            }
        }
        
        public IResourcePack getResourcePack() {
            return this.reResourcePack;
        }
        
        public String getResourcePackName() {
            return this.reResourcePack.getPackName();
        }
        
        public String getTexturePackDescription() {
            return (this.rePackMetadataSection == null) ? (EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)") : this.rePackMetadataSection.func_152805_a().getFormattedText();
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o instanceof Entry && this.toString().equals(o.toString()));
        }
        
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
        
        @Override
        public String toString() {
            return String.format("%s:%s:%d", this.resourcePackFile.getName(), this.resourcePackFile.isDirectory() ? "folder" : "zip", this.resourcePackFile.lastModified());
        }
        
        Entry(final ResourcePackRepository resourcePackRepository, final File file, final Object o) {
            this(resourcePackRepository, file);
        }
        
        static {
            __OBFID = "CL_00001090";
        }
    }
}
