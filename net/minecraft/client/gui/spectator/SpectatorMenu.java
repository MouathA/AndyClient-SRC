package net.minecraft.client.gui.spectator;

import net.minecraft.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.client.gui.spectator.categories.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class SpectatorMenu
{
    private static final ISpectatorMenuObject field_178655_b;
    private static final ISpectatorMenuObject field_178656_c;
    private static final ISpectatorMenuObject field_178653_d;
    private static final ISpectatorMenuObject field_178654_e;
    public static final ISpectatorMenuObject field_178657_a;
    private final ISpectatorMenuReciepient field_178651_f;
    private final List field_178652_g;
    private ISpectatorMenuView field_178659_h;
    private int field_178660_i;
    private int field_178658_j;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001927";
        field_178655_b = new EndSpectatorObject(null);
        field_178656_c = new MoveMenuObject(-1, true);
        field_178653_d = new MoveMenuObject(1, true);
        field_178654_e = new MoveMenuObject(1, false);
        field_178657_a = new ISpectatorMenuObject() {
            private static final String __OBFID;
            
            @Override
            public void func_178661_a(final SpectatorMenu spectatorMenu) {
            }
            
            @Override
            public IChatComponent func_178664_z_() {
                return new ChatComponentText("");
            }
            
            @Override
            public void func_178663_a(final float n, final int n2) {
            }
            
            @Override
            public boolean func_178662_A_() {
                return false;
            }
            
            static {
                __OBFID = "CL_00001926";
            }
        };
    }
    
    public SpectatorMenu(final ISpectatorMenuReciepient field_178651_f) {
        this.field_178652_g = Lists.newArrayList();
        this.field_178659_h = new BaseSpectatorGroup();
        this.field_178660_i = -1;
        this.field_178651_f = field_178651_f;
    }
    
    public ISpectatorMenuObject func_178643_a(final int n) {
        final int n2 = n + this.field_178658_j * 6;
        return (ISpectatorMenuObject)((this.field_178658_j > 0 && n == 0) ? SpectatorMenu.field_178656_c : ((n == 7) ? ((n2 < this.field_178659_h.func_178669_a().size()) ? SpectatorMenu.field_178653_d : SpectatorMenu.field_178654_e) : ((n == 8) ? SpectatorMenu.field_178655_b : ((n2 >= 0 && n2 < this.field_178659_h.func_178669_a().size()) ? Objects.firstNonNull(this.field_178659_h.func_178669_a().get(n2), SpectatorMenu.field_178657_a) : SpectatorMenu.field_178657_a))));
    }
    
    public List func_178642_a() {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 <= 8) {
            arrayList.add(this.func_178643_a(0));
            int n = 0;
            ++n;
        }
        return arrayList;
    }
    
    public ISpectatorMenuObject func_178645_b() {
        return this.func_178643_a(this.field_178660_i);
    }
    
    public ISpectatorMenuView func_178650_c() {
        return this.field_178659_h;
    }
    
    public void func_178644_b(final int field_178660_i) {
        final ISpectatorMenuObject func_178643_a = this.func_178643_a(field_178660_i);
        if (func_178643_a != SpectatorMenu.field_178657_a) {
            if (this.field_178660_i == field_178660_i && func_178643_a.func_178662_A_()) {
                func_178643_a.func_178661_a(this);
            }
            else {
                this.field_178660_i = field_178660_i;
            }
        }
    }
    
    public void func_178641_d() {
        this.field_178651_f.func_175257_a(this);
    }
    
    public int func_178648_e() {
        return this.field_178660_i;
    }
    
    public void func_178647_a(final ISpectatorMenuView field_178659_h) {
        this.field_178652_g.add(this.func_178646_f());
        this.field_178659_h = field_178659_h;
        this.field_178660_i = -1;
        this.field_178658_j = 0;
    }
    
    public SpectatorDetails func_178646_f() {
        return new SpectatorDetails(this.field_178659_h, this.func_178642_a(), this.field_178660_i);
    }
    
    static void access$0(final SpectatorMenu spectatorMenu, final int field_178658_j) {
        spectatorMenu.field_178658_j = field_178658_j;
    }
    
    static class EndSpectatorObject implements ISpectatorMenuObject
    {
        private static final String __OBFID;
        
        private EndSpectatorObject() {
        }
        
        @Override
        public void func_178661_a(final SpectatorMenu spectatorMenu) {
            spectatorMenu.func_178641_d();
        }
        
        @Override
        public IChatComponent func_178664_z_() {
            return new ChatComponentText("Close menu");
        }
        
        @Override
        public void func_178663_a(final float n, final int n2) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0f, 0.0f, 16, 16, 256.0f, 256.0f);
        }
        
        @Override
        public boolean func_178662_A_() {
            return true;
        }
        
        EndSpectatorObject(final Object o) {
            this();
        }
        
        static {
            __OBFID = "CL_00001925";
        }
    }
    
    static class MoveMenuObject implements ISpectatorMenuObject
    {
        private final int field_178666_a;
        private final boolean field_178665_b;
        private static final String __OBFID;
        
        public MoveMenuObject(final int field_178666_a, final boolean field_178665_b) {
            this.field_178666_a = field_178666_a;
            this.field_178665_b = field_178665_b;
        }
        
        @Override
        public void func_178661_a(final SpectatorMenu spectatorMenu) {
            SpectatorMenu.access$0(spectatorMenu, this.field_178666_a);
        }
        
        @Override
        public IChatComponent func_178664_z_() {
            return (this.field_178666_a < 0) ? new ChatComponentText("Previous Page") : new ChatComponentText("Next Page");
        }
        
        @Override
        public void func_178663_a(final float n, final int n2) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
            if (this.field_178666_a < 0) {
                Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0f, 0.0f, 16, 16, 256.0f, 256.0f);
            }
            else {
                Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0f, 0.0f, 16, 16, 256.0f, 256.0f);
            }
        }
        
        @Override
        public boolean func_178662_A_() {
            return this.field_178665_b;
        }
        
        static {
            __OBFID = "CL_00001924";
        }
    }
}
