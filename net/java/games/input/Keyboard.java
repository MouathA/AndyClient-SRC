package net.java.games.input;

public abstract class Keyboard extends AbstractController
{
    protected Keyboard(final String s, final Component[] array, final Controller[] array2, final Rumbler[] array3) {
        super(s, array, array2, array3);
    }
    
    public Controller.Type getType() {
        return Controller.Type.KEYBOARD;
    }
    
    public final boolean isKeyDown(final Component.Identifier.Key key) {
        final Component component = this.getComponent(key);
        return component != null && component.getPollData() != 0.0f;
    }
}
