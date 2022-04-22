package wdl.gui;

import org.apache.logging.log4j.*;
import net.minecraft.client.gui.*;

class GuiNumericTextField extends GuiTextField
{
    private static Logger logger;
    private String lastSafeText;
    
    static {
        GuiNumericTextField.logger = LogManager.getLogger();
    }
    
    public GuiNumericTextField(final int n, final FontRenderer fontRenderer, final int n2, final int n3, final int n4, final int n5) {
        super(n, fontRenderer, n2, n3, n4, n5);
        this.setText(this.lastSafeText = "0");
    }
    
    @Override
    public void drawTextBox() {
        Integer.parseInt("0" + this.getText());
        this.lastSafeText = this.getText();
        super.drawTextBox();
    }
    
    public int getValue() {
        return Integer.parseInt("0" + this.getText());
    }
    
    public void setValue(final int n) {
        this.setText(this.lastSafeText = String.valueOf(n));
    }
    
    @Override
    public String getText() {
        return String.valueOf(Integer.parseInt("0" + super.getText()));
    }
    
    @Override
    public void setText(final String s) {
        final String value = String.valueOf(Integer.parseInt("0" + s));
        super.setText(value);
        this.lastSafeText = value;
    }
}
