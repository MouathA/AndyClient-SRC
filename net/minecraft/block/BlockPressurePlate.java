package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;
import java.nio.charset.*;

public class BlockPressurePlate extends BlockBasePressurePlate
{
    public static final PropertyBool POWERED;
    private final Sensitivity sensitivity;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000289";
        POWERED = PropertyBool.create("powered");
    }
    
    protected BlockPressurePlate(final Material material, final Sensitivity sensitivity) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPressurePlate.POWERED, false));
        this.sensitivity = sensitivity;
    }
    
    @Override
    protected int getRedstoneStrength(final IBlockState blockState) {
        return blockState.getValue(BlockPressurePlate.POWERED) ? 15 : 0;
    }
    
    @Override
    protected IBlockState setRedstoneStrength(final IBlockState blockState, final int n) {
        return blockState.withProperty(BlockPressurePlate.POWERED, n > 0);
    }
    
    @Override
    protected int computeRedstoneStrength(final World world, final BlockPos blockPos) {
        final AxisAlignedBB sensitiveAABB = this.getSensitiveAABB(blockPos);
        List list = null;
        switch (SwitchSensitivity.SENSITIVITY_ARRAY[this.sensitivity.ordinal()]) {
            case 1: {
                list = world.getEntitiesWithinAABBExcludingEntity(null, sensitiveAABB);
                break;
            }
            case 2: {
                list = world.getEntitiesWithinAABB(EntityLivingBase.class, sensitiveAABB);
                break;
            }
            default: {
                return 0;
            }
        }
        if (!list.isEmpty()) {
            final Iterator<Entity> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().doesEntityNotTriggerPressurePlate()) {
                    return 15;
                }
            }
        }
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPressurePlate.POWERED, n == 1);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((boolean)blockState.getValue(BlockPressurePlate.POWERED)) ? 1 : 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPressurePlate.POWERED });
    }
    
    public enum Sensitivity
    {
        EVERYTHING("EVERYTHING", 0, "EVERYTHING", 0), 
        MOBS("MOBS", 1, "MOBS", 1);
        
        private static final Sensitivity[] $VALUES;
        private static final String __OBFID;
        private static final Sensitivity[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00000290";
            ENUM$VALUES = new Sensitivity[] { Sensitivity.EVERYTHING, Sensitivity.MOBS };
            $VALUES = new Sensitivity[] { Sensitivity.EVERYTHING, Sensitivity.MOBS };
        }
        
        private Sensitivity(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    static final class SwitchSensitivity
    {
        static final int[] SENSITIVITY_ARRAY;
        private static final String __OBFID;
        private static final String[] llIlIIIIllllIII;
        private static String[] llIlIIIIllllIIl;
        
        static {
            lIIllIIlllIlIIII();
            lIIllIIlllIIllll();
            __OBFID = SwitchSensitivity.llIlIIIIllllIII[0];
            SENSITIVITY_ARRAY = new int[Sensitivity.values().length];
            try {
                SwitchSensitivity.SENSITIVITY_ARRAY[Sensitivity.EVERYTHING.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchSensitivity.SENSITIVITY_ARRAY[Sensitivity.MOBS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
        
        private static void lIIllIIlllIIllll() {
            (llIlIIIIllllIII = new String[1])[0] = lIIllIIlllIIlllI(SwitchSensitivity.llIlIIIIllllIIl[0], SwitchSensitivity.llIlIIIIllllIIl[1]);
            SwitchSensitivity.llIlIIIIllllIIl = null;
        }
        
        private static void lIIllIIlllIlIIII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchSensitivity.llIlIIIIllllIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIllIIlllIIlllI(String s, final String s2) {
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
