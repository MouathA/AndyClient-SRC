package Mood.Gui.Minigames.impl.Snake;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame
{
    public GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
