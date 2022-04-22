package net.java.games.input;

public abstract class Mouse extends AbstractController
{
    protected Mouse(final String s, final Component[] array, final Controller[] array2, final Rumbler[] array3) {
        super(s, array, array2, array3);
    }
    
    public Controller.Type getType() {
        return Controller.Type.MOUSE;
    }
    
    public Component getX() {
        return this.getComponent(Component.Identifier.Axis.X);
    }
    
    public Component getY() {
        return this.getComponent(Component.Identifier.Axis.Y);
    }
    
    public Component getWheel() {
        return this.getComponent(Component.Identifier.Axis.Z);
    }
    
    public Component getPrimaryButton() {
        Component component = this.getComponent(Component.Identifier.Button.LEFT);
        if (component == null) {
            component = this.getComponent(Component.Identifier.Button._1);
        }
        return component;
    }
    
    public Component getSecondaryButton() {
        Component component = this.getComponent(Component.Identifier.Button.RIGHT);
        if (component == null) {
            component = this.getComponent(Component.Identifier.Button._2);
        }
        return component;
    }
    
    public Component getTertiaryButton() {
        Component component = this.getComponent(Component.Identifier.Button.MIDDLE);
        if (component == null) {
            component = this.getComponent(Component.Identifier.Button._3);
        }
        return component;
    }
    
    public Component getLeft() {
        return this.getComponent(Component.Identifier.Button.LEFT);
    }
    
    public Component getRight() {
        return this.getComponent(Component.Identifier.Button.RIGHT);
    }
    
    public Component getMiddle() {
        return this.getComponent(Component.Identifier.Button.MIDDLE);
    }
    
    public Component getSide() {
        return this.getComponent(Component.Identifier.Button.SIDE);
    }
    
    public Component getExtra() {
        return this.getComponent(Component.Identifier.Button.EXTRA);
    }
    
    public Component getForward() {
        return this.getComponent(Component.Identifier.Button.FORWARD);
    }
    
    public Component getBack() {
        return this.getComponent(Component.Identifier.Button.BACK);
    }
    
    public Component getButton3() {
        return this.getComponent(Component.Identifier.Button._3);
    }
    
    public Component getButton4() {
        return this.getComponent(Component.Identifier.Button._4);
    }
}
