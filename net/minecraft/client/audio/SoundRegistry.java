package net.minecraft.client.audio;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class SoundRegistry extends RegistrySimple
{
    private Map field_148764_a;
    private static final String __OBFID;
    
    @Override
    protected Map createUnderlyingMap() {
        return this.field_148764_a = Maps.newHashMap();
    }
    
    public void registerSound(final SoundEventAccessorComposite soundEventAccessorComposite) {
        this.putObject(soundEventAccessorComposite.getSoundEventLocation(), soundEventAccessorComposite);
    }
    
    public void clearMap() {
        this.field_148764_a.clear();
    }
    
    static {
        __OBFID = "CL_00001151";
    }
}
