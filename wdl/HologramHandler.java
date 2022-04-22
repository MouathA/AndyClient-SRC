package wdl;

import wdl.api.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public class HologramHandler implements ISpecialEntityHandler, IWDLModDescripted
{
    @Override
    public boolean isValidEnvironment(final String s) {
        return true;
    }
    
    @Override
    public String getEnvironmentErrorMessage(final String s) {
        return null;
    }
    
    @Override
    public String getDisplayName() {
        return "Hologram support";
    }
    
    @Override
    public Multimap getSpecialEntities() {
        final HashMultimap create = HashMultimap.create();
        create.put("ArmorStand", "Hologram");
        return create;
    }
    
    @Override
    public String getSpecialEntityName(final Entity entity) {
        if (entity instanceof EntityArmorStand && entity.isInvisible() && entity.hasCustomName()) {
            return "Hologram";
        }
        return null;
    }
    
    @Override
    public String getSpecialEntityCategory(final String s) {
        if (s.equals("Hologram")) {
            return "Other";
        }
        return null;
    }
    
    @Override
    public int getSpecialEntityTrackDistance(final String s) {
        return -1;
    }
    
    @Override
    public String getMainAuthor() {
        return "Pokechu22";
    }
    
    @Override
    public String[] getAuthors() {
        return null;
    }
    
    @Override
    public String getURL() {
        return null;
    }
    
    @Override
    public String getDescription() {
        return "Provides basic support for disabling holograms.";
    }
}
