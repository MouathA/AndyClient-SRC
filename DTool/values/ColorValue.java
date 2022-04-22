package DTool.values;

import java.awt.*;

public class ColorValue extends Value
{
    private Color value;
    private boolean rainbow;
    private double rainbowSpeed;
    
    public double getRainbowSpeed() {
        return this.rainbowSpeed;
    }
    
    public void setRainbowSpeed(final double rainbowSpeed) {
        this.rainbowSpeed = rainbowSpeed;
    }
    
    public boolean isRainbow() {
        return this.rainbow;
    }
    
    public void setRainbow(final boolean rainbow) {
        this.rainbow = rainbow;
    }
    
    public ColorValue(final String group, final String key, final Color value, final boolean b) {
        this.rainbow = false;
        this.rainbowSpeed = 1.0;
        this.group = group;
        this.key = key;
        this.value = value;
        if (!b) {
            ValueManager.addValue(this);
        }
    }
    
    public ColorValue(final String s, final String s2, final Color color) {
        this(s, s2, color, false);
    }
    
    public void setValue(final Color value) {
        this.value = value;
    }
    
    public void setValueInt(final int n) {
        this.value = new Color(n);
    }
    
    @Override
    public Color getValue() {
        return this.value;
    }
    
    public int getColorInt() {
        return this.getColor().getRGB();
    }
    
    public float[] getColorHSB() {
        final Color value = this.value;
        return Color.RGBtoHSB(value.getRed(), value.getGreen(), value.getBlue(), null);
    }
    
    public Color getColor(final long n) {
        if (this.rainbow) {
            final float n2 = (float)Math.ceil(System.currentTimeMillis() / (15.1 + n) % 360.0 / 360.0);
            final float[] colorHSB = this.getColorHSB();
            return Color.getHSBColor(n2, colorHSB[1], colorHSB[2]);
        }
        return this.value;
    }
    
    public Color getColor() {
        return this.getColor(0L);
    }
    
    public void setRed(final int n) {
        final Color value = this.value;
        this.value = new Color(n, value.getGreen(), value.getBlue(), value.getAlpha());
    }
    
    public void setGreen(final int n) {
        final Color value = this.value;
        this.value = new Color(value.getRed(), n, value.getBlue(), value.getAlpha());
    }
    
    public void setBlue(final int n) {
        final Color value = this.value;
        this.value = new Color(value.getRed(), value.getGreen(), n, value.getAlpha());
    }
    
    public int getRed() {
        return this.value.getRed();
    }
    
    public int getGreen() {
        return this.value.getGreen();
    }
    
    public int getBlue() {
        return this.value.getBlue();
    }
    
    @Override
    public Object getValue() {
        return this.getValue();
    }
}
