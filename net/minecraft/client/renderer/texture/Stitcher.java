package net.minecraft.client.renderer.texture;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;

public class Stitcher
{
    private final int mipmapLevelStitcher;
    private final Set setStitchHolders;
    private final List stitchSlots;
    private int currentWidth;
    private int currentHeight;
    private final int maxWidth;
    private final int maxHeight;
    private final boolean forcePowerOf2;
    private final int maxTileDimension;
    private static final String __OBFID;
    
    public Stitcher(final int maxWidth, final int maxHeight, final boolean forcePowerOf2, final int maxTileDimension, final int mipmapLevelStitcher) {
        this.setStitchHolders = Sets.newHashSetWithExpectedSize(256);
        this.stitchSlots = Lists.newArrayListWithCapacity(256);
        this.mipmapLevelStitcher = mipmapLevelStitcher;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.forcePowerOf2 = forcePowerOf2;
        this.maxTileDimension = maxTileDimension;
    }
    
    public int getCurrentWidth() {
        return this.currentWidth;
    }
    
    public int getCurrentHeight() {
        return this.currentHeight;
    }
    
    public void addSprite(final TextureAtlasSprite textureAtlasSprite) {
        final Holder holder = new Holder(textureAtlasSprite, this.mipmapLevelStitcher);
        if (this.maxTileDimension > 0) {
            holder.setNewDimension(this.maxTileDimension);
        }
        this.setStitchHolders.add(holder);
    }
    
    public void doStitch() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/renderer/texture/Stitcher.setStitchHolders:Ljava/util/Set;
        //     4: aload_0        
        //     5: getfield        net/minecraft/client/renderer/texture/Stitcher.setStitchHolders:Ljava/util/Set;
        //     8: invokeinterface java/util/Set.size:()I
        //    13: anewarray       Lnet/minecraft/client/renderer/texture/Stitcher$Holder;
        //    16: invokeinterface java/util/Set.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //    21: checkcast       [Lnet/minecraft/client/renderer/texture/Stitcher$Holder;
        //    24: astore_1       
        //    25: aload_1        
        //    26: invokestatic    java/util/Arrays.sort:([Ljava/lang/Object;)V
        //    29: aload_1        
        //    30: astore_2       
        //    31: aload_1        
        //    32: arraylength    
        //    33: istore_3       
        //    34: goto            155
        //    37: aload_2        
        //    38: iconst_0       
        //    39: aaload         
        //    40: astore          5
        //    42: aload_0        
        //    43: aload           5
        //    45: ifeq            152
        //    48: ldc             "Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?"
        //    50: bipush          7
        //    52: anewarray       Ljava/lang/Object;
        //    55: dup            
        //    56: iconst_0       
        //    57: aload           5
        //    59: invokevirtual   net/minecraft/client/renderer/texture/Stitcher$Holder.getAtlasSprite:()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //    62: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getIconName:()Ljava/lang/String;
        //    65: aastore        
        //    66: dup            
        //    67: iconst_1       
        //    68: aload           5
        //    70: invokevirtual   net/minecraft/client/renderer/texture/Stitcher$Holder.getAtlasSprite:()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //    73: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getIconWidth:()I
        //    76: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    79: aastore        
        //    80: dup            
        //    81: iconst_2       
        //    82: aload           5
        //    84: invokevirtual   net/minecraft/client/renderer/texture/Stitcher$Holder.getAtlasSprite:()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //    87: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getIconHeight:()I
        //    90: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    93: aastore        
        //    94: dup            
        //    95: iconst_3       
        //    96: aload_0        
        //    97: getfield        net/minecraft/client/renderer/texture/Stitcher.currentWidth:I
        //   100: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   103: aastore        
        //   104: dup            
        //   105: iconst_4       
        //   106: aload_0        
        //   107: getfield        net/minecraft/client/renderer/texture/Stitcher.currentHeight:I
        //   110: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   113: aastore        
        //   114: dup            
        //   115: iconst_5       
        //   116: aload_0        
        //   117: getfield        net/minecraft/client/renderer/texture/Stitcher.maxWidth:I
        //   120: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   123: aastore        
        //   124: dup            
        //   125: bipush          6
        //   127: aload_0        
        //   128: getfield        net/minecraft/client/renderer/texture/Stitcher.maxHeight:I
        //   131: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   134: aastore        
        //   135: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   138: astore          6
        //   140: new             Lnet/minecraft/client/renderer/StitcherException;
        //   143: dup            
        //   144: aload           5
        //   146: aload           6
        //   148: invokespecial   net/minecraft/client/renderer/StitcherException.<init>:(Lnet/minecraft/client/renderer/texture/Stitcher$Holder;Ljava/lang/String;)V
        //   151: athrow         
        //   152: iinc            4, 1
        //   155: iconst_0       
        //   156: iload_3        
        //   157: if_icmplt       37
        //   160: aload_0        
        //   161: getfield        net/minecraft/client/renderer/texture/Stitcher.forcePowerOf2:Z
        //   164: ifeq            189
        //   167: aload_0        
        //   168: aload_0        
        //   169: getfield        net/minecraft/client/renderer/texture/Stitcher.currentWidth:I
        //   172: invokestatic    net/minecraft/util/MathHelper.roundUpToPowerOfTwo:(I)I
        //   175: putfield        net/minecraft/client/renderer/texture/Stitcher.currentWidth:I
        //   178: aload_0        
        //   179: aload_0        
        //   180: getfield        net/minecraft/client/renderer/texture/Stitcher.currentHeight:I
        //   183: invokestatic    net/minecraft/util/MathHelper.roundUpToPowerOfTwo:(I)I
        //   186: putfield        net/minecraft/client/renderer/texture/Stitcher.currentHeight:I
        //   189: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0155 (coming from #0152).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public List getStichSlots() {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Slot> iterator = this.stitchSlots.iterator();
        while (iterator.hasNext()) {
            iterator.next().getAllStitchSlots(arrayList);
        }
        final ArrayList arrayList2 = Lists.newArrayList();
        for (final Slot slot : arrayList) {
            final Holder stitchHolder = slot.getStitchHolder();
            final TextureAtlasSprite atlasSprite = stitchHolder.getAtlasSprite();
            atlasSprite.initSprite(this.currentWidth, this.currentHeight, slot.getOriginX(), slot.getOriginY(), stitchHolder.isRotated());
            arrayList2.add(atlasSprite);
        }
        return arrayList2;
    }
    
    private static int getMipmapDimension(final int n, final int n2) {
        return (n >> n2) + (((n & (1 << n2) - 1) != 0x0) ? 1 : 0) << n2;
    }
    
    private boolean expandAndAllocateSlot(final Holder holder) {
        final int min = Math.min(holder.getWidth(), holder.getHeight());
        final boolean b = this.currentWidth == 0 && this.currentHeight == 0;
        boolean b5;
        if (this.forcePowerOf2) {
            final int roundUpToPowerOfTwo = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
            final int roundUpToPowerOfTwo2 = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
            final int roundUpToPowerOfTwo3 = MathHelper.roundUpToPowerOfTwo(this.currentWidth + min);
            final int roundUpToPowerOfTwo4 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + min);
            final boolean b2 = roundUpToPowerOfTwo3 <= this.maxWidth;
            final boolean b3 = roundUpToPowerOfTwo4 <= this.maxHeight;
            if (!b2 && !b3) {
                return false;
            }
            final boolean b4 = roundUpToPowerOfTwo != roundUpToPowerOfTwo3;
            if (b4 ^ roundUpToPowerOfTwo2 != roundUpToPowerOfTwo4) {
                b5 = !b4;
            }
            else {
                b5 = (b2 && roundUpToPowerOfTwo <= roundUpToPowerOfTwo2);
            }
        }
        else {
            final boolean b6 = this.currentWidth + min <= this.maxWidth;
            final boolean b7 = this.currentHeight + min <= this.maxHeight;
            if (!b6 && !b7) {
                return false;
            }
            b5 = (b6 && (b || this.currentWidth <= this.currentHeight));
        }
        if (MathHelper.roundUpToPowerOfTwo(((!b5) ? this.currentHeight : this.currentWidth) + Math.max(holder.getWidth(), holder.getHeight())) > ((!b5) ? this.maxHeight : this.maxWidth)) {
            return false;
        }
        Slot slot;
        if (b5) {
            if (holder.getWidth() > holder.getHeight()) {
                holder.rotate();
            }
            if (this.currentHeight == 0) {
                this.currentHeight = holder.getHeight();
            }
            slot = new Slot(this.currentWidth, 0, holder.getWidth(), this.currentHeight);
            this.currentWidth += holder.getWidth();
        }
        else {
            slot = new Slot(0, this.currentHeight, this.currentWidth, holder.getHeight());
            this.currentHeight += holder.getHeight();
        }
        slot.addSlot(holder);
        this.stitchSlots.add(slot);
        return true;
    }
    
    static int access$0(final int n, final int n2) {
        return getMipmapDimension(n, n2);
    }
    
    static {
        __OBFID = "CL_00001054";
    }
    
    public static class Holder implements Comparable
    {
        private final TextureAtlasSprite theTexture;
        private final int width;
        private final int height;
        private final int mipmapLevelHolder;
        private boolean rotated;
        private float scaleFactor;
        private static final String __OBFID;
        
        public Holder(final TextureAtlasSprite theTexture, final int mipmapLevelHolder) {
            this.scaleFactor = 1.0f;
            this.theTexture = theTexture;
            this.width = theTexture.getIconWidth();
            this.height = theTexture.getIconHeight();
            this.mipmapLevelHolder = mipmapLevelHolder;
            this.rotated = (Stitcher.access$0(this.height, mipmapLevelHolder) > Stitcher.access$0(this.width, mipmapLevelHolder));
        }
        
        public TextureAtlasSprite getAtlasSprite() {
            return this.theTexture;
        }
        
        public int getWidth() {
            return this.rotated ? Stitcher.access$0((int)(this.height * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.access$0((int)(this.width * this.scaleFactor), this.mipmapLevelHolder);
        }
        
        public int getHeight() {
            return this.rotated ? Stitcher.access$0((int)(this.width * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.access$0((int)(this.height * this.scaleFactor), this.mipmapLevelHolder);
        }
        
        public void rotate() {
            this.rotated = !this.rotated;
        }
        
        public boolean isRotated() {
            return this.rotated;
        }
        
        public void setNewDimension(final int n) {
            if (this.width > n && this.height > n) {
                this.scaleFactor = n / (float)Math.min(this.width, this.height);
            }
        }
        
        @Override
        public String toString() {
            return "Holder{width=" + this.width + ", height=" + this.height + ", name=" + this.theTexture.getIconName() + '}';
        }
        
        public int compareTo(final Holder holder) {
            int n;
            if (this.getHeight() == holder.getHeight()) {
                if (this.getWidth() == holder.getWidth()) {
                    if (this.theTexture.getIconName() == null) {
                        return (holder.theTexture.getIconName() == null) ? 0 : -1;
                    }
                    return this.theTexture.getIconName().compareTo(holder.theTexture.getIconName());
                }
                else {
                    n = ((this.getWidth() < holder.getWidth()) ? 1 : -1);
                }
            }
            else {
                n = ((this.getHeight() < holder.getHeight()) ? 1 : -1);
            }
            return n;
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((Holder)o);
        }
        
        static {
            __OBFID = "CL_00001055";
        }
    }
    
    public static class Slot
    {
        private final int originX;
        private final int originY;
        private final int width;
        private final int height;
        private List subSlots;
        private Holder holder;
        private static final String __OBFID;
        
        public Slot(final int originX, final int originY, final int width, final int height) {
            this.originX = originX;
            this.originY = originY;
            this.width = width;
            this.height = height;
        }
        
        public Holder getStitchHolder() {
            return this.holder;
        }
        
        public int getOriginX() {
            return this.originX;
        }
        
        public int getOriginY() {
            return this.originY;
        }
        
        public void getAllStitchSlots(final List list) {
            if (this.holder != null) {
                list.add(this);
            }
            else if (this.subSlots != null) {
                final Iterator<Slot> iterator = (Iterator<Slot>)this.subSlots.iterator();
                while (iterator.hasNext()) {
                    iterator.next().getAllStitchSlots(list);
                }
            }
        }
        
        @Override
        public String toString() {
            return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
        }
        
        static {
            __OBFID = "CL_00001056";
        }
    }
}
