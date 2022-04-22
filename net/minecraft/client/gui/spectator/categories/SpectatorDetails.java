package net.minecraft.client.gui.spectator.categories;

import java.util.*;
import net.minecraft.client.gui.spectator.*;
import com.google.common.base.*;

public class SpectatorDetails
{
    private final ISpectatorMenuView field_178684_a;
    private final List field_178682_b;
    private final int field_178683_c;
    private static final String __OBFID;
    
    public SpectatorDetails(final ISpectatorMenuView field_178684_a, final List field_178682_b, final int field_178683_c) {
        this.field_178684_a = field_178684_a;
        this.field_178682_b = field_178682_b;
        this.field_178683_c = field_178683_c;
    }
    
    public ISpectatorMenuObject func_178680_a(final int n) {
        return (ISpectatorMenuObject)((n >= 0 && n < this.field_178682_b.size()) ? Objects.firstNonNull(this.field_178682_b.get(n), SpectatorMenu.field_178657_a) : SpectatorMenu.field_178657_a);
    }
    
    public int func_178681_b() {
        return this.field_178683_c;
    }
    
    static {
        __OBFID = "CL_00001923";
    }
}
