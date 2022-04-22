package Mood.Gui.Minigames.impl.badgirls.Ads;

import Mood.Gui.Minigames.*;
import net.minecraft.util.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class MegTobbReklam
{
    public static List GRAF_OPTIONS;
    private final List entries;
    private final MemeTimer timerTaskDefault;
    private int trashX;
    
    static {
        MegTobbReklam.GRAF_OPTIONS = Arrays.asList(new ResourceLocation("MooDTool/badminecraftgirls/xDDD.jpg"), new ResourceLocation("MooDTool/badminecraftgirls/xDDD3.jpg"), new ResourceLocation("MooDTool/badminecraftgirls/xDDD3.jpg"));
    }
    
    public MegTobbReklam() {
        this.entries = new CopyOnWriteArrayList();
        this.timerTaskDefault = new MemeTimer();
    }
    
    public void render(final int n, final int n2, final int n3) {
        if (this.timerTaskDefault.hasTimeReached(1000L)) {
            this.entries.add(new SidEntry(new Random().nextInt(n - 9), MegTobbReklam.GRAF_OPTIONS.get(new Random().nextInt(2))));
            this.timerTaskDefault.reset();
        }
        this.entries.forEach(this::lambda$0);
    }
    
    private void lambda$0(final int n, final SidEntry sidEntry) {
        sidEntry.render();
        if (sidEntry.getY() >= n - 160 && sidEntry.getX() >= this.trashX && sidEntry.getX() <= this.trashX + 50) {
            this.entries.remove(sidEntry);
        }
    }
    
    public static class SidEntry
    {
        private int x;
        private int y;
        private final ResourceLocation identifier;
        
        public SidEntry(final int x, final ResourceLocation identifier) {
            this.x = x;
            this.identifier = identifier;
        }
        
        public void render() {
            --this.x;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.getIdentifier());
            Gui.drawModalRectWithCustomSizedTexture(this.getX(), this.getY(), 0.0f, 0.0f, 80, 80, 80.0f, 80.0f);
        }
        
        public int getX() {
            return this.x;
        }
        
        public int getY() {
            return this.y;
        }
        
        public ResourceLocation getIdentifier() {
            return this.identifier;
        }
    }
}
