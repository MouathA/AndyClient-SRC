package net.minecraft.entity.item;

import net.minecraft.world.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class EntityPainting extends EntityHanging
{
    public EnumArt art;
    private static final String __OBFID;
    
    public EntityPainting(final World world) {
        super(world);
    }
    
    public EntityPainting(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        super(world, blockPos);
        final ArrayList arrayList = Lists.newArrayList();
        final EnumArt[] values = EnumArt.values();
        while (0 < values.length) {
            final EnumArt art = values[0];
            this.art = art;
            this.func_174859_a(enumFacing);
            if (this.onValidSurface()) {
                arrayList.add(art);
            }
            int n = 0;
            ++n;
        }
        if (!arrayList.isEmpty()) {
            this.art = arrayList.get(this.rand.nextInt(arrayList.size()));
        }
        this.func_174859_a(enumFacing);
    }
    
    public EntityPainting(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final String s) {
        this(world, blockPos, enumFacing);
        final EnumArt[] values = EnumArt.values();
        while (0 < values.length) {
            final EnumArt art = values[0];
            if (art.title.equals(s)) {
                this.art = art;
                break;
            }
            int n = 0;
            ++n;
        }
        this.func_174859_a(enumFacing);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setString("Motive", this.art.title);
        super.writeEntityToNBT(nbtTagCompound);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        final String string = nbtTagCompound.getString("Motive");
        final EnumArt[] values = EnumArt.values();
        while (0 < values.length) {
            final EnumArt art = values[0];
            if (art.title.equals(string)) {
                this.art = art;
            }
            int n = 0;
            ++n;
        }
        if (this.art == null) {
            this.art = EnumArt.KEBAB;
        }
        super.readEntityFromNBT(nbtTagCompound);
    }
    
    @Override
    public int getWidthPixels() {
        return this.art.sizeX;
    }
    
    @Override
    public int getHeightPixels() {
        return this.art.sizeY;
    }
    
    @Override
    public void onBroken(final Entity entity) {
        if (this.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
                return;
            }
            this.entityDropItem(new ItemStack(Items.painting), 0.0f);
        }
    }
    
    @Override
    public void setLocationAndAngles(final double n, final double n2, final double n3, final float n4, final float n5) {
        final BlockPos add = this.field_174861_a.add(new BlockPos(n - this.posX, n2 - this.posY, n3 - this.posZ));
        this.setPosition(add.getX(), add.getY(), add.getZ());
    }
    
    @Override
    public void func_180426_a(final double n, final double n2, final double n3, final float n4, final float n5, final int n6, final boolean b) {
        final BlockPos add = this.field_174861_a.add(new BlockPos(n - this.posX, n2 - this.posY, n3 - this.posZ));
        this.setPosition(add.getX(), add.getY(), add.getZ());
    }
    
    static {
        __OBFID = "CL_00001556";
    }
    
    public enum EnumArt
    {
        KEBAB("KEBAB", 0, "KEBAB", 0, "Kebab", 16, 16, 0, 0), 
        AZTEC("AZTEC", 1, "AZTEC", 1, "Aztec", 16, 16, 16, 0), 
        ALBAN("ALBAN", 2, "ALBAN", 2, "Alban", 16, 16, 32, 0), 
        AZTEC_2("AZTEC_2", 3, "AZTEC_2", 3, "Aztec2", 16, 16, 48, 0), 
        BOMB("BOMB", 4, "BOMB", 4, "Bomb", 16, 16, 64, 0), 
        PLANT("PLANT", 5, "PLANT", 5, "Plant", 16, 16, 80, 0), 
        WASTELAND("WASTELAND", 6, "WASTELAND", 6, "Wasteland", 16, 16, 96, 0), 
        POOL("POOL", 7, "POOL", 7, "Pool", 32, 16, 0, 32), 
        COURBET("COURBET", 8, "COURBET", 8, "Courbet", 32, 16, 32, 32), 
        SEA("SEA", 9, "SEA", 9, "Sea", 32, 16, 64, 32), 
        SUNSET("SUNSET", 10, "SUNSET", 10, "Sunset", 32, 16, 96, 32), 
        CREEBET("CREEBET", 11, "CREEBET", 11, "Creebet", 32, 16, 128, 32), 
        WANDERER("WANDERER", 12, "WANDERER", 12, "Wanderer", 16, 32, 0, 64), 
        GRAHAM("GRAHAM", 13, "GRAHAM", 13, "Graham", 16, 32, 16, 64), 
        MATCH("MATCH", 14, "MATCH", 14, "Match", 32, 32, 0, 128), 
        BUST("BUST", 15, "BUST", 15, "Bust", 32, 32, 32, 128), 
        STAGE("STAGE", 16, "STAGE", 16, "Stage", 32, 32, 64, 128), 
        VOID("VOID", 17, "VOID", 17, "Void", 32, 32, 96, 128), 
        SKULL_AND_ROSES("SKULL_AND_ROSES", 18, "SKULL_AND_ROSES", 18, "SkullAndRoses", 32, 32, 128, 128), 
        WITHER("WITHER", 19, "WITHER", 19, "Wither", 32, 32, 160, 128), 
        FIGHTERS("FIGHTERS", 20, "FIGHTERS", 20, "Fighters", 64, 32, 0, 96), 
        POINTER("POINTER", 21, "POINTER", 21, "Pointer", 64, 64, 0, 192), 
        PIGSCENE("PIGSCENE", 22, "PIGSCENE", 22, "Pigscene", 64, 64, 64, 192), 
        BURNING_SKULL("BURNING_SKULL", 23, "BURNING_SKULL", 23, "BurningSkull", 64, 64, 128, 192), 
        SKELETON("SKELETON", 24, "SKELETON", 24, "Skeleton", 64, 48, 192, 64), 
        DONKEY_KONG("DONKEY_KONG", 25, "DONKEY_KONG", 25, "DonkeyKong", 64, 48, 192, 112);
        
        public static final int field_180001_A;
        public final String title;
        public final int sizeX;
        public final int sizeY;
        public final int offsetX;
        public final int offsetY;
        private static final EnumArt[] $VALUES;
        private static final String __OBFID;
        private static final EnumArt[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001557";
            ENUM$VALUES = new EnumArt[] { EnumArt.KEBAB, EnumArt.AZTEC, EnumArt.ALBAN, EnumArt.AZTEC_2, EnumArt.BOMB, EnumArt.PLANT, EnumArt.WASTELAND, EnumArt.POOL, EnumArt.COURBET, EnumArt.SEA, EnumArt.SUNSET, EnumArt.CREEBET, EnumArt.WANDERER, EnumArt.GRAHAM, EnumArt.MATCH, EnumArt.BUST, EnumArt.STAGE, EnumArt.VOID, EnumArt.SKULL_AND_ROSES, EnumArt.WITHER, EnumArt.FIGHTERS, EnumArt.POINTER, EnumArt.PIGSCENE, EnumArt.BURNING_SKULL, EnumArt.SKELETON, EnumArt.DONKEY_KONG };
            field_180001_A = 13;
            $VALUES = new EnumArt[] { EnumArt.KEBAB, EnumArt.AZTEC, EnumArt.ALBAN, EnumArt.AZTEC_2, EnumArt.BOMB, EnumArt.PLANT, EnumArt.WASTELAND, EnumArt.POOL, EnumArt.COURBET, EnumArt.SEA, EnumArt.SUNSET, EnumArt.CREEBET, EnumArt.WANDERER, EnumArt.GRAHAM, EnumArt.MATCH, EnumArt.BUST, EnumArt.STAGE, EnumArt.VOID, EnumArt.SKULL_AND_ROSES, EnumArt.WITHER, EnumArt.FIGHTERS, EnumArt.POINTER, EnumArt.PIGSCENE, EnumArt.BURNING_SKULL, EnumArt.SKELETON, EnumArt.DONKEY_KONG };
        }
        
        private EnumArt(final String s, final int n, final String s2, final int n2, final String title, final int sizeX, final int sizeY, final int offsetX, final int offsetY) {
            this.title = title;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }
    }
}
