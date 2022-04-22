package Mood.Matrix;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import Mood.Matrix.Utils.*;

public enum MatrixBG
{
    INSTANCE("INSTANCE", 0);
    
    Minecraft mc;
    public int time;
    public int line10Random;
    public int line9Random;
    public int line8Random;
    public int line7Random;
    public int line6Random;
    public int line5Random;
    public int line4Random;
    public int line3Random;
    public int line2Random;
    public int line1Random;
    public int fade10;
    public int fade9;
    public int fade8;
    public int fade7;
    public int fade6;
    public String line10String;
    public String line9String;
    public String line8String;
    public String line7String;
    public String line6String;
    public int line10Height;
    public int line9Height;
    public int line8Height;
    public int line7Height;
    public int line6Height;
    public int line10With;
    public int line9With;
    public int line8With;
    public int line7With;
    public int line6With;
    public int fade5;
    public int fade4;
    public int fade3;
    public int fade2;
    public int fade1;
    public String line5String;
    public String line4String;
    public String line3String;
    public String line2String;
    public String line1String;
    public int line5Height;
    public int line4Height;
    public int line3Height;
    public int line2Height;
    public int line1Height;
    public int line5With;
    public int line4With;
    public int line3With;
    public int line2With;
    public int line1With;
    private static final MatrixBG[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new MatrixBG[] { MatrixBG.INSTANCE };
    }
    
    private MatrixBG(final String s, final int n) {
        this.mc = Minecraft.getMinecraft();
        this.line1With = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.width / 800);
        this.line2With = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.width / 800);
        this.line3With = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.width / 800);
        this.line4With = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.width / 800);
        this.line5With = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.width / 800);
        this.line1Height = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.height);
        this.line2Height = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.height);
        this.line3Height = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.height);
        this.line4Height = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.height);
        this.line5Height = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.height);
        this.line1String = "§kD";
        this.line3String = "§kD";
        this.line4String = "§kD";
        this.line5String = "§kD";
        this.fade1 = this.line1Height;
        this.fade2 = this.line2Height;
        this.fade3 = this.line3Height;
        this.fade4 = this.line4Height;
        this.fade5 = this.line5Height;
        this.line6With = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.width);
        this.line7With = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.width);
        this.line8With = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.width);
        this.line9With = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.width);
        this.line10With = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.width);
        this.line6Height = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.height);
        this.line7Height = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.height);
        this.line8Height = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.height);
        this.line9Height = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.height);
        this.line10Height = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.height);
        this.line6String = "§kD";
        this.line7String = "§kD";
        this.line8String = "§kD";
        this.line9String = "§kD";
        this.line10String = "§kD";
        this.fade6 = this.line6Height;
        this.fade7 = this.line7Height;
        this.fade8 = this.line8Height;
        this.fade9 = this.line9Height;
        this.fade10 = this.line10Height;
        this.line1Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        this.line2Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        this.line3Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        this.line4Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        this.line5Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        this.line6Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        this.line7Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        this.line8Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        this.line9Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        this.line10Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        this.time = 0;
    }
    
    public void drawMatrixBackground() {
        ++this.time;
        if (this.time == 500) {
            this.line1String = "§kD";
            this.line2String = "§kD";
            this.line3String = "§kD";
            this.line4String = "§kD";
            this.line5String = "§kD";
            this.line6String = "§kD";
            this.line7String = "§kD";
            this.line8String = "§kD";
            this.line9String = "§kD";
            this.line10String = "§kD";
            this.time = 0;
        }
        ++this.fade1;
        ++this.fade2;
        ++this.fade3;
        ++this.fade4;
        ++this.fade5;
        ++this.fade6;
        ++this.fade7;
        ++this.fade8;
        ++this.fade9;
        ++this.fade10;
        if (this.fade1 - 40 > GuiScreen.height) {
            this.line1Height = RandomUtils.INSTANCE.getRandomInt(0, 100);
            this.line1With = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.width / 800);
            this.fade1 = this.line1Height;
            this.line1Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        }
        if (this.fade2 - 30 > GuiScreen.height) {
            this.line2Height = RandomUtils.INSTANCE.getRandomInt(0, 100);
            this.line2With = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.width / 800);
            this.fade2 = this.line2Height;
            this.line2Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        }
        if (this.fade3 - 20 > GuiScreen.height) {
            this.line3Height = RandomUtils.INSTANCE.getRandomInt(0, 100);
            this.line3With = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.width / 800);
            this.fade3 = this.line3Height;
            this.line3Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        }
        if (this.fade4 - 10 > GuiScreen.height) {
            this.line4Height = RandomUtils.INSTANCE.getRandomInt(0, 100);
            this.line4With = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.width / 800);
            this.fade4 = this.line4Height;
            this.line4Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        }
        if (this.fade5 > GuiScreen.height) {
            this.line5Height = RandomUtils.INSTANCE.getRandomInt(0, 100);
            this.line5With = RandomUtils.INSTANCE.getRandomInt(0, GuiScreen.width / 800);
            this.fade5 = this.line5Height;
            this.line5Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        }
        if (this.fade6 - 40 > GuiScreen.height) {
            this.line6Height = RandomUtils.INSTANCE.getRandomInt(0, 100);
            this.line6With = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.width);
            this.fade6 = this.line6Height;
            this.line6Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        }
        if (this.fade7 - 30 > GuiScreen.height) {
            this.line7Height = RandomUtils.INSTANCE.getRandomInt(0, 100);
            this.line7With = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.width);
            this.fade7 = this.line7Height;
            this.line7Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        }
        if (this.fade8 - 20 > GuiScreen.height) {
            this.line8Height = RandomUtils.INSTANCE.getRandomInt(0, 100);
            this.line8With = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.width);
            this.fade8 = this.line8Height;
            this.line8Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        }
        if (this.fade9 - 10 > GuiScreen.height) {
            this.line9Height = RandomUtils.INSTANCE.getRandomInt(0, 100);
            this.line9With = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.width);
            this.fade9 = this.line9Height;
            this.line9Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        }
        if (this.fade10 > GuiScreen.height) {
            this.line10Height = RandomUtils.INSTANCE.getRandomInt(0, 100);
            this.line10With = RandomUtils.INSTANCE.getRandomInt(GuiScreen.width / 800, GuiScreen.width);
            this.fade10 = this.line10Height;
            this.line10Random = RandomUtils.INSTANCE.getRandomInt(1, 200);
        }
        if (this.line1Random == 1) {
            Minecraft.fontRendererObj.drawStringWithShadow(this.line1String, this.line1With, this.fade1 - 70, -16041205);
            Minecraft.fontRendererObj.drawStringWithShadow(this.line1String, this.line1With, this.fade1 - 60, -16041205);
        }
        if (this.line2Random == 2) {
            Minecraft.fontRendererObj.drawStringWithShadow(this.line2String, this.line2With, this.fade2 - 70, -16041205);
            Minecraft.fontRendererObj.drawStringWithShadow(this.line2String, this.line2With, this.fade2 - 60, -16041205);
        }
        if (this.line3Random == 1) {
            Minecraft.fontRendererObj.drawStringWithShadow(this.line3String, this.line3With, this.fade3 - 70, -16041205);
            Minecraft.fontRendererObj.drawStringWithShadow(this.line3String, this.line3With, this.fade3 - 60, -16041205);
        }
        if (this.line4Random == 2) {
            Minecraft.fontRendererObj.drawStringWithShadow(this.line4String, this.line4With, this.fade4 - 70, -16041205);
            Minecraft.fontRendererObj.drawStringWithShadow(this.line4String, this.line4With, this.fade4 - 60, -16041205);
        }
        if (this.line5Random == 1) {
            Minecraft.fontRendererObj.drawStringWithShadow(this.line5String, this.line5With, this.fade5 - 70, -16041205);
            Minecraft.fontRendererObj.drawStringWithShadow(this.line5String, this.line5With, this.fade5 - 60, -16041205);
        }
        if (this.line1Random == 2) {
            Minecraft.fontRendererObj.drawStringWithShadow(this.line1String, this.line1With, this.fade1 - 60, -16041205);
        }
        if (this.line2Random == 1) {
            Minecraft.fontRendererObj.drawStringWithShadow(this.line2String, this.line2With, this.fade2 - 60, -16041205);
        }
        if (this.line3Random == 2) {
            Minecraft.fontRendererObj.drawStringWithShadow(this.line3String, this.line3With, this.fade3 - 60, -16041205);
        }
        if (this.line4Random == 1) {
            Minecraft.fontRendererObj.drawStringWithShadow(this.line4String, this.line4With, this.fade4 - 60, -16041205);
        }
        if (this.line5Random == 2) {
            Minecraft.fontRendererObj.drawStringWithShadow(this.line5String, this.line5With, this.fade5 - 60, -16041205);
        }
        Minecraft.fontRendererObj.drawStringWithShadow(this.line6String, this.line6With, this.fade6 - 50, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line7String, this.line7With, this.fade7 - 50, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line8String, this.line8With, this.fade8 - 50, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line9String, this.line9With, this.fade9 - 50, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line10String, this.line10With, this.fade10 - 50, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line6String, this.line6With, this.fade6 - 40, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line7String, this.line7With, this.fade7 - 40, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line8String, this.line8With, this.fade8 - 40, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line9String, this.line9With, this.fade9 - 40, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line10String, this.line10With, this.fade10 - 40, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line6String, this.line6With, this.fade6 - 30, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line7String, this.line7With, this.fade7 - 30, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line8String, this.line8With, this.fade8 - 30, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line9String, this.line9With, this.fade9 - 30, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line10String, this.line10With, this.fade10 - 30, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line6String, this.line6With, this.fade6 - 20, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line7String, this.line7With, this.fade7 - 20, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line8String, this.line8With, this.fade8 - 20, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line9String, this.line9With, this.fade9 - 20, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line10String, this.line10With, this.fade10 - 20, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line6String, this.line6With, this.fade6 - 10, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line7String, this.line7With, this.fade7 - 10, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line8String, this.line8With, this.fade8 - 10, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line9String, this.line9With, this.fade9 - 10, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line10String, this.line10With, this.fade10 - 10, MatrixColor.rainbow(1L));
        Minecraft.fontRendererObj.drawStringWithShadow(this.line6String, this.line6With, this.fade6, -1);
        Minecraft.fontRendererObj.drawStringWithShadow(this.line7String, this.line7With, this.fade7, -1);
        Minecraft.fontRendererObj.drawStringWithShadow(this.line8String, this.line8With, this.fade8, -1);
        Minecraft.fontRendererObj.drawStringWithShadow(this.line9String, this.line9With, this.fade9, -1);
        Minecraft.fontRendererObj.drawStringWithShadow(this.line10String, this.line10With, this.fade10, -1);
    }
}
