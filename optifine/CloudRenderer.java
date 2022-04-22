package optifine;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class CloudRenderer
{
    private Minecraft mc;
    private boolean updated;
    private boolean renderFancy;
    int cloudTickCounter;
    float partialTicks;
    private int glListClouds;
    private int cloudTickCounterUpdate;
    private double cloudPlayerX;
    private double cloudPlayerY;
    private double cloudPlayerZ;
    
    public CloudRenderer(final Minecraft mc) {
        this.updated = false;
        this.renderFancy = false;
        this.glListClouds = -1;
        this.cloudTickCounterUpdate = 0;
        this.cloudPlayerX = 0.0;
        this.cloudPlayerY = 0.0;
        this.cloudPlayerZ = 0.0;
        this.mc = mc;
        this.glListClouds = GLAllocation.generateDisplayLists(1);
    }
    
    public void prepareToRender(final boolean renderFancy, final int cloudTickCounter, final float partialTicks) {
        if (this.renderFancy != renderFancy) {
            this.updated = false;
        }
        this.renderFancy = renderFancy;
        this.cloudTickCounter = cloudTickCounter;
        this.partialTicks = partialTicks;
    }
    
    public boolean shouldUpdateGlList() {
        if (!this.updated) {
            return true;
        }
        if (this.cloudTickCounter >= this.cloudTickCounterUpdate + 20) {
            return true;
        }
        final Entity func_175606_aa = this.mc.func_175606_aa();
        return func_175606_aa.prevPosY + func_175606_aa.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f ^ this.cloudPlayerY + func_175606_aa.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f;
    }
    
    public void startUpdateGlList() {
        GL11.glNewList(this.glListClouds, 4864);
    }
    
    public void endUpdateGlList() {
        this.cloudTickCounterUpdate = this.cloudTickCounter;
        this.cloudPlayerX = this.mc.func_175606_aa().prevPosX;
        this.cloudPlayerY = this.mc.func_175606_aa().prevPosY;
        this.cloudPlayerZ = this.mc.func_175606_aa().prevPosZ;
        this.updated = true;
    }
    
    public void renderGlList() {
        final Entity func_175606_aa = this.mc.func_175606_aa();
        final double n = func_175606_aa.prevPosX + (func_175606_aa.posX - func_175606_aa.prevPosX) * this.partialTicks;
        final double n2 = func_175606_aa.prevPosY + (func_175606_aa.posY - func_175606_aa.prevPosY) * this.partialTicks;
        final double n3 = func_175606_aa.prevPosZ + (func_175606_aa.posZ - func_175606_aa.prevPosZ) * this.partialTicks;
        final float n4 = (float)(n - this.cloudPlayerX + (this.cloudTickCounter - this.cloudTickCounterUpdate + this.partialTicks) * 0.03);
        final float n5 = (float)(n2 - this.cloudPlayerY);
        final float n6 = (float)(n3 - this.cloudPlayerZ);
        if (this.renderFancy) {
            GlStateManager.translate(-n4 / 12.0f, -n5, -n6 / 12.0f);
        }
        else {
            GlStateManager.translate(-n4, -n5, -n6);
        }
        GlStateManager.callList(this.glListClouds);
    }
    
    public void reset() {
        this.updated = false;
    }
}
