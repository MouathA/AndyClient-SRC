package de.gerrygames.viarewind.replacement;

import java.util.*;

public interface EntityReplacement
{
    int getEntityId();
    
    void setLocation(final double p0, final double p1, final double p2);
    
    void relMove(final double p0, final double p1, final double p2);
    
    void setYawPitch(final float p0, final float p1);
    
    void setHeadYaw(final float p0);
    
    void spawn();
    
    void despawn();
    
    void updateMetadata(final List p0);
}
