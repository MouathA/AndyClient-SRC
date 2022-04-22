package DTool.modules;

import net.minecraft.client.*;
import DTool.settings.*;
import java.util.*;
import DTool.events.*;
import net.minecraft.network.*;

public class Module
{
    public String name;
    private String modul;
    public boolean toggled;
    public int keyCode;
    public Category category;
    public static Minecraft mc;
    public List settingList;
    public boolean expanded;
    public int index;
    public List settings;
    double height;
    
    static {
        Module.mc = Minecraft.getMinecraft();
    }
    
    public Module(final String name, final int keyCode, final Category category) {
        this.settingList = new ArrayList();
        this.settings = new ArrayList();
        this.height = 6.0;
        this.name = name;
        this.keyCode = keyCode;
        this.category = category;
    }
    
    public void addSettings(final Setting... array) {
        this.settings.addAll(Arrays.asList(array));
    }
    
    public boolean isEnable() {
        return this.toggled;
    }
    
    public List getSettingList() {
        return this.settingList;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public int getKey() {
        return this.keyCode;
    }
    
    public void onEvent(final Event event) {
    }
    
    public String getName() {
        return this.name;
    }
    
    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }
    
    public Packet onClientSendPacket(final Packet packet) {
        return packet;
    }
    
    public boolean onEnable() {
        return this.toggled;
    }
    
    public void onDisable() {
    }
    
    public final void setState(final boolean toggled) {
        this.toggled = toggled;
        if (this.isEnable()) {
            Minecraft.thePlayer.playSound("random.wood_click", 1.0f, 3.0f);
            this.onEnable();
        }
        else {
            Minecraft.thePlayer.playSound("random.wood_click", 1.0f, 1.0f);
            this.onDisable();
        }
    }
    
    public void execute() {
        if (this.isEnable()) {
            this.onDisable();
            return;
        }
        this.onEnable();
    }
    
    public void onRender() {
    }
    
    public void setup() {
    }
    
    public double getAdditionalJumpMotion() {
        return this.isEnable() ? (this.height * 0.1) : 0.0;
    }
    
    public enum Category
    {
        Combat("Combat", 0, "Combat"), 
        Movement("Movement", 1, "Movement"), 
        Player("Player", 2, "Player"), 
        Exploits("Exploits", 3, "Exploits"), 
        World("World", 4, "World"), 
        Visuals("Visuals", 5, "Visuals");
        
        public String name;
        public int moduleIndex;
        private static final Category[] ENUM$VALUES;
        
        static {
            ENUM$VALUES = new Category[] { Category.Combat, Category.Movement, Category.Player, Category.Exploits, Category.World, Category.Visuals };
        }
        
        private Category(final String s, final int n, final String name) {
            this.name = name;
        }
    }
}
