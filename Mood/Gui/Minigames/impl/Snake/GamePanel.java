package Mood.Gui.Minigames.impl.Snake;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener
{
    final int[] x;
    final int[] y;
    int bodyParts;
    int applesEaten;
    int appleX;
    int appleY;
    char direction;
    boolean running;
    Timer timer;
    Random random;
    
    GamePanel() {
        this.x = new int[390];
        this.y = new int[390];
        this.bodyParts = 6;
        this.direction = 'R';
        this.running = false;
        this.random = new Random();
        this.setPreferredSize(new Dimension(1300, 750));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.startGame();
    }
    
    public void startGame() {
        this.newApple();
        this.running = true;
        (this.timer = new Timer(175, this)).start();
    }
    
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        this.draw(graphics);
    }
    
    public void draw(final Graphics graphics) {
        if (this.running) {
            graphics.setColor(Color.red);
            graphics.fillOval(this.appleX, this.appleY, 50, 50);
            while (0 < this.bodyParts) {
                if (!false) {
                    graphics.setColor(Color.green);
                    graphics.fillRect(this.x[0], this.y[0], 50, 50);
                }
                else {
                    graphics.setColor(new Color(45, 180, 0));
                    graphics.fillRect(this.x[0], this.y[0], 50, 50);
                }
                int n = 0;
                ++n;
            }
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Ink Free", 1, 40));
            graphics.drawString("Pont sz\u00e1m: " + this.applesEaten, (1300 - this.getFontMetrics(graphics.getFont()).stringWidth("Pont sz\u00e1m: " + this.applesEaten)) / 2, graphics.getFont().getSize());
        }
        else {
            this.gameOver(graphics);
        }
    }
    
    public void newApple() {
        this.appleX = this.random.nextInt(26) * 50;
        this.appleY = this.random.nextInt(15) * 50;
    }
    
    public void move() {
        for (int i = this.bodyParts; i > 0; --i) {
            this.x[i] = this.x[i - 1];
            this.y[i] = this.y[i - 1];
        }
        switch (this.direction) {
            case 'U': {
                this.y[0] -= 50;
                break;
            }
            case 'D': {
                this.y[0] += 50;
                break;
            }
            case 'L': {
                this.x[0] -= 50;
                break;
            }
            case 'R': {
                this.x[0] += 50;
                break;
            }
        }
    }
    
    public void checkApple() {
        if (this.x[0] == this.appleX && this.y[0] == this.appleY) {
            ++this.bodyParts;
            ++this.applesEaten;
            this.newApple();
        }
    }
    
    public void checkCollisions() {
        for (int i = this.bodyParts; i > 0; --i) {
            if (this.x[0] == this.x[i] && this.y[0] == this.y[i]) {
                this.running = false;
            }
        }
        if (this.x[0] < 0) {
            this.running = false;
        }
        if (this.x[0] > 1300) {
            this.running = false;
        }
        if (this.y[0] < 0) {
            this.running = false;
        }
        if (this.y[0] > 750) {
            this.running = false;
        }
        if (!this.running) {
            this.timer.stop();
        }
    }
    
    public void gameOver(final Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free", 1, 40));
        graphics.drawString("Score: " + this.applesEaten, (1300 - this.getFontMetrics(graphics.getFont()).stringWidth("Score: " + this.applesEaten)) / 2, graphics.getFont().getSize());
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free", 1, 75));
        graphics.drawString("Game Over", (1300 - this.getFontMetrics(graphics.getFont()).stringWidth("Game Over")) / 2, 375);
    }
    
    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        if (this.running) {
            this.move();
            this.checkApple();
            this.checkCollisions();
        }
        this.repaint();
    }
    
    public class MyKeyAdapter extends KeyAdapter
    {
        final GamePanel this$0;
        
        public MyKeyAdapter(final GamePanel this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void keyPressed(final KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                case 37: {
                    if (this.this$0.direction != 'R') {
                        this.this$0.direction = 'L';
                        break;
                    }
                    break;
                }
                case 39: {
                    if (this.this$0.direction != 'L') {
                        this.this$0.direction = 'R';
                        break;
                    }
                    break;
                }
                case 38: {
                    if (this.this$0.direction != 'D') {
                        this.this$0.direction = 'U';
                        break;
                    }
                    break;
                }
                case 40: {
                    if (this.this$0.direction != 'U') {
                        this.this$0.direction = 'D';
                        break;
                    }
                    break;
                }
            }
        }
    }
}
