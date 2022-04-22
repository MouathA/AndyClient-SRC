package DTool.ui;

import net.minecraft.client.*;
import Mood.*;
import java.util.function.*;
import net.minecraft.client.renderer.*;
import DTool.modules.*;
import java.util.concurrent.atomic.*;
import java.awt.*;
import DTool.util.*;
import DTool.events.listeners.*;
import DTool.events.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class HUD
{
    public Minecraft mc;
    
    public HUD() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public void draw() {
        final Random random = new Random();
        final char[] array = { '1', '2', '3', '4', '5', '6', '7', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        final char c = array[random.nextInt(array.length)];
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        final FontRenderer fontRendererObj = Minecraft.fontRendererObj;
        Client.modules.sort(Comparator.comparingInt((ToIntFunction<? super Object>)HUD::lambda$0).reversed());
        GlStateManager.translate(4.0f, 4.0f, 0.0f);
        GlStateManager.scale(2.0f, 2.0f, 1.0f);
        GlStateManager.translate(-5.0f, -4.0f, 0.0f);
        for (final Module module : Client.modules) {
            if (module.toggled) {
                if (module.name.equals("TabGUI")) {
                    continue;
                }
                final double n = 0 * (fontRendererObj.FONT_HEIGHT + 3);
                final AtomicInteger atomicInteger = new AtomicInteger();
                Gui.drawRect(ScaledResolution.getScaledWidth() - fontRendererObj.getStringWidth(module.name) - 5, n, ScaledResolution.getScaledWidth(), 3 + fontRendererObj.FONT_HEIGHT + n, Integer.MIN_VALUE + Color.BLACK.getRGB());
                fontRendererObj.drawStringWithShadow(module.name, ScaledResolution.getScaledWidth() - fontRendererObj.getStringWidth(module.name) - 2, (float)(2.0 + n), ColorUtil.getRainbow(8.0f, 0.9f, 1.0f, 0));
                int n2 = 0;
                ++n2;
            }
        }
        Client.onEvent(new EventRenderGUI());
    }
    
    private static int lambda$0(final FontRenderer fontRenderer, final Object o) {
        return fontRenderer.getStringWidth(((Module)o).name);
    }
}
