package wdl;

import wdl.api.*;
import net.minecraft.entity.*;

public class EntityRealigner implements IEntityEditor, IWDLModDescripted
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
        return "Entity realigner";
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
        return "Realigns entities to their serverside position to deal with entities that drift clientside (for example, boats).";
    }
    
    @Override
    public boolean shouldEdit(final Entity entity) {
        return entity.serverPosX != 0 || entity.serverPosY != 0 || entity.serverPosZ != 0;
    }
    
    @Override
    public void editEntity(final Entity entity) {
        entity.posX = convertServerPos(entity.serverPosX);
        entity.posY = convertServerPos(entity.serverPosY);
        entity.posZ = convertServerPos(entity.serverPosZ);
    }
    
    private static double convertServerPos(final int n) {
        return n / 32.0;
    }
}
