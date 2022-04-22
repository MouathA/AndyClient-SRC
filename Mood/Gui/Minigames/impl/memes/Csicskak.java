package Mood.Gui.Minigames.impl.memes;

import net.minecraft.util.*;
import Mood.Gui.Minigames.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class Csicskak
{
    public static List GRAF_OPTIONS;
    private final ResourceLocation TRASH;
    private final List entries;
    private final MemeTimer timerTaskDefault;
    private int trashX;
    
    static {
        Csicskak.GRAF_OPTIONS = Arrays.asList(new ResourceLocation("MooDTool/Tesco/DCWITHMILAN.png"), new ResourceLocation("MooDTool/Tesco/pico.png"), new ResourceLocation("MooDTool/Tesco/DCWITHMILANWITHESP.png"));
    }
    
    public Csicskak() {
        this.TRASH = new ResourceLocation("MooDTool/Tesco/SzemetesKuka.png");
        this.entries = new CopyOnWriteArrayList();
        this.timerTaskDefault = new MemeTimer();
    }
    
    public void render(final int n, final int n2, final int n3) {
        if (this.timerTaskDefault.hasTimeReached(1000L)) {
            this.entries.add(new SidEntry(new Random().nextInt(n - 9), Csicskak.GRAF_OPTIONS.get(new Random().nextInt(2))));
            this.timerTaskDefault.reset();
        }
        this.entries.forEach(this::lambda$0);
        this.trashX = n3 - 25;
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.TRASH);
        Gui.drawModalRectWithCustomSizedTexture(this.trashX, n2 - 80, 0.0f, 0.0f, 80, 80, 80.0f, 80.0f);
    }
    
    private void lambda$0(final int n, final SidEntry sidEntry) {
        sidEntry.render();
        if (sidEntry.getY() >= n - 160 && sidEntry.getX() >= this.trashX && sidEntry.getX() <= this.trashX + 50) {
            this.entries.remove(sidEntry);
        }
    }
    
    public static class SidEntry
    {
        private final int x;
        private int y;
        private final ResourceLocation identifier;
        
        public SidEntry(final int x, final ResourceLocation identifier) {
            this.x = x;
            this.identifier = identifier;
        }
        
        public void render() {
            ++this.y;
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
