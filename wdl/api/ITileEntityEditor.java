package wdl.api;

import net.minecraft.util.*;
import net.minecraft.nbt.*;

public interface ITileEntityEditor extends IWDLMod
{
    boolean shouldEdit(final BlockPos p0, final NBTTagCompound p1, final TileEntityCreationMode p2);
    
    void editTileEntity(final BlockPos p0, final NBTTagCompound p1, final TileEntityCreationMode p2);
    
    public enum TileEntityCreationMode
    {
        IMPORTED("IMPORTED", 0), 
        EXISTING("EXISTING", 1), 
        NEW("NEW", 2);
        
        private static final TileEntityCreationMode[] ENUM$VALUES;
        
        static {
            ENUM$VALUES = new TileEntityCreationMode[] { TileEntityCreationMode.IMPORTED, TileEntityCreationMode.EXISTING, TileEntityCreationMode.NEW };
        }
        
        private TileEntityCreationMode(final String s, final int n) {
        }
    }
}
