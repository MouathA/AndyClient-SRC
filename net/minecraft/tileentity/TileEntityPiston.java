package net.minecraft.tileentity;

import net.minecraft.server.gui.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class TileEntityPiston extends TileEntity implements IUpdatePlayerListBox
{
    private IBlockState field_174932_a;
    private EnumFacing field_174931_f;
    private boolean extending;
    private boolean shouldHeadBeRendered;
    private float progress;
    private float lastProgress;
    private List field_174933_k;
    private static final String __OBFID;
    
    public TileEntityPiston() {
        this.field_174933_k = Lists.newArrayList();
    }
    
    public TileEntityPiston(final IBlockState field_174932_a, final EnumFacing field_174931_f, final boolean extending, final boolean shouldHeadBeRendered) {
        this.field_174933_k = Lists.newArrayList();
        this.field_174932_a = field_174932_a;
        this.field_174931_f = field_174931_f;
        this.extending = extending;
        this.shouldHeadBeRendered = shouldHeadBeRendered;
    }
    
    public IBlockState func_174927_b() {
        return this.field_174932_a;
    }
    
    @Override
    public int getBlockMetadata() {
        return 0;
    }
    
    public boolean isExtending() {
        return this.extending;
    }
    
    public EnumFacing func_174930_e() {
        return this.field_174931_f;
    }
    
    public boolean shouldPistonHeadBeRendered() {
        return this.shouldHeadBeRendered;
    }
    
    public float func_145860_a(float n) {
        if (n > 1.0f) {
            n = 1.0f;
        }
        return this.lastProgress + (this.progress - this.lastProgress) * n;
    }
    
    public float func_174929_b(final float n) {
        return this.extending ? ((this.func_145860_a(n) - 1.0f) * this.field_174931_f.getFrontOffsetX()) : ((1.0f - this.func_145860_a(n)) * this.field_174931_f.getFrontOffsetX());
    }
    
    public float func_174928_c(final float n) {
        return this.extending ? ((this.func_145860_a(n) - 1.0f) * this.field_174931_f.getFrontOffsetY()) : ((1.0f - this.func_145860_a(n)) * this.field_174931_f.getFrontOffsetY());
    }
    
    public float func_174926_d(final float n) {
        return this.extending ? ((this.func_145860_a(n) - 1.0f) * this.field_174931_f.getFrontOffsetZ()) : ((1.0f - this.func_145860_a(n)) * this.field_174931_f.getFrontOffsetZ());
    }
    
    private void func_145863_a(float n, final float n2) {
        if (this.extending) {
            n = 1.0f - n;
        }
        else {
            --n;
        }
        final AxisAlignedBB func_176424_a = Blocks.piston_extension.func_176424_a(this.worldObj, this.pos, this.field_174932_a, n, this.field_174931_f);
        if (func_176424_a != null) {
            final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, func_176424_a);
            if (!entitiesWithinAABBExcludingEntity.isEmpty()) {
                this.field_174933_k.addAll(entitiesWithinAABBExcludingEntity);
                for (final Entity entity : this.field_174933_k) {
                    if (this.field_174932_a.getBlock() == Blocks.slime_block && this.extending) {
                        switch (SwitchAxis.field_177248_a[this.field_174931_f.getAxis().ordinal()]) {
                            case 1: {
                                entity.motionX = this.field_174931_f.getFrontOffsetX();
                                continue;
                            }
                            case 2: {
                                entity.motionY = this.field_174931_f.getFrontOffsetY();
                                continue;
                            }
                            case 3: {
                                entity.motionZ = this.field_174931_f.getFrontOffsetZ();
                                continue;
                            }
                        }
                    }
                    else {
                        entity.moveEntity(n2 * this.field_174931_f.getFrontOffsetX(), n2 * this.field_174931_f.getFrontOffsetY(), n2 * this.field_174931_f.getFrontOffsetZ());
                    }
                }
                this.field_174933_k.clear();
            }
        }
    }
    
    public void clearPistonTileEntity() {
        if (this.lastProgress < 1.0f && this.worldObj != null) {
            final float n = 1.0f;
            this.progress = n;
            this.lastProgress = n;
            this.worldObj.removeTileEntity(this.pos);
            this.invalidate();
            if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
                this.worldObj.setBlockState(this.pos, this.field_174932_a, 3);
                this.worldObj.notifyBlockOfStateChange(this.pos, this.field_174932_a.getBlock());
            }
        }
    }
    
    @Override
    public void update() {
        this.lastProgress = this.progress;
        if (this.lastProgress >= 1.0f) {
            this.func_145863_a(1.0f, 0.25f);
            this.worldObj.removeTileEntity(this.pos);
            this.invalidate();
            if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
                this.worldObj.setBlockState(this.pos, this.field_174932_a, 3);
                this.worldObj.notifyBlockOfStateChange(this.pos, this.field_174932_a.getBlock());
            }
        }
        else {
            this.progress += 0.5f;
            if (this.progress >= 1.0f) {
                this.progress = 1.0f;
            }
            if (this.extending) {
                this.func_145863_a(this.progress, this.progress - this.lastProgress + 0.0625f);
            }
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.field_174932_a = Block.getBlockById(nbtTagCompound.getInteger("blockId")).getStateFromMeta(nbtTagCompound.getInteger("blockData"));
        this.field_174931_f = EnumFacing.getFront(nbtTagCompound.getInteger("facing"));
        final float float1 = nbtTagCompound.getFloat("progress");
        this.progress = float1;
        this.lastProgress = float1;
        this.extending = nbtTagCompound.getBoolean("extending");
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("blockId", Block.getIdFromBlock(this.field_174932_a.getBlock()));
        nbtTagCompound.setInteger("blockData", this.field_174932_a.getBlock().getMetaFromState(this.field_174932_a));
        nbtTagCompound.setInteger("facing", this.field_174931_f.getIndex());
        nbtTagCompound.setFloat("progress", this.lastProgress);
        nbtTagCompound.setBoolean("extending", this.extending);
    }
    
    static {
        __OBFID = "CL_00000369";
    }
    
    static final class SwitchAxis
    {
        static final int[] field_177248_a;
        private static final String __OBFID;
        private static final String[] lIlIlllIIllIIllI;
        private static String[] lIlIlllIIllIIlll;
        
        static {
            lIIIIIIIIlIlIllII();
            lIIIIIIIIlIlIlIll();
            __OBFID = SwitchAxis.lIlIlllIIllIIllI[0];
            field_177248_a = new int[EnumFacing.Axis.values().length];
            try {
                SwitchAxis.field_177248_a[EnumFacing.Axis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchAxis.field_177248_a[EnumFacing.Axis.Y.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchAxis.field_177248_a[EnumFacing.Axis.Z.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
        
        private static void lIIIIIIIIlIlIlIll() {
            (lIlIlllIIllIIllI = new String[1])[0] = lIIIIIIIIlIlIlIlI(SwitchAxis.lIlIlllIIllIIlll[0], SwitchAxis.lIlIlllIIllIIlll[1]);
            SwitchAxis.lIlIlllIIllIIlll = null;
        }
        
        private static void lIIIIIIIIlIlIllII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchAxis.lIlIlllIIllIIlll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIIIIIIIlIlIlIlI(final String s, final String s2) {
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
