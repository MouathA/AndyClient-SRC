package com.sun.jna.platform.dnd;

import java.awt.geom.*;
import java.awt.*;
import com.sun.jna.platform.*;
import javax.swing.*;
import java.awt.event.*;

public class GhostedDragImage
{
    private static final float DEFAULT_ALPHA = 0.5f;
    private Window dragImage;
    private Point origin;
    private static final int SLIDE_INTERVAL = 33;
    
    public GhostedDragImage(final Component component, final Icon icon, final Point point, final Point point2) {
        (this.dragImage = new Window((Window)JOptionPane.getRootFrame(), ((component instanceof Window) ? component : SwingUtilities.getWindowAncestor(component)).getGraphicsConfiguration(), icon) {
            private static final long serialVersionUID = 1L;
            final Icon val$icon;
            final GhostedDragImage this$0;
            
            @Override
            public void paint(final Graphics graphics) {
                this.val$icon.paintIcon(this, graphics, 0, 0);
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(this.val$icon.getIconWidth(), this.val$icon.getIconHeight());
            }
            
            @Override
            public Dimension getMinimumSize() {
                return this.getPreferredSize();
            }
            
            @Override
            public Dimension getMaximumSize() {
                return this.getPreferredSize();
            }
        }).setFocusableWindowState(false);
        this.dragImage.setName("###overrideRedirect###");
        final Icon icon2 = new Icon(icon, point2) {
            final Icon val$icon;
            final Point val$cursorOffset;
            final GhostedDragImage this$0;
            
            public int getIconHeight() {
                return this.val$icon.getIconHeight();
            }
            
            public int getIconWidth() {
                return this.val$icon.getIconWidth();
            }
            
            public void paintIcon(final Component component, Graphics create, final int n, final int n2) {
                create = create.create();
                final Area clip = new Area(new Rectangle(n, n2, this.getIconWidth(), this.getIconHeight()));
                clip.subtract(new Area(new Rectangle(n + this.val$cursorOffset.x - 1, n2 + this.val$cursorOffset.y - 1, 3, 3)));
                create.setClip(clip);
                this.val$icon.paintIcon(component, create, n, n2);
                create.dispose();
            }
        };
        this.dragImage.pack();
        WindowUtils.setWindowMask(this.dragImage, icon2);
        WindowUtils.setWindowAlpha(this.dragImage, 0.5f);
        this.move(point);
        this.dragImage.setVisible(true);
    }
    
    public void setAlpha(final float n) {
        WindowUtils.setWindowAlpha(this.dragImage, n);
    }
    
    public void dispose() {
        this.dragImage.dispose();
        this.dragImage = null;
    }
    
    public void move(final Point origin) {
        if (this.origin == null) {
            this.origin = origin;
        }
        this.dragImage.setLocation(origin.x, origin.y);
    }
    
    public void returnToOrigin() {
        final Timer timer = new Timer(33, null);
        timer.addActionListener(new ActionListener(timer) {
            final Timer val$timer;
            final GhostedDragImage this$0;
            
            public void actionPerformed(final ActionEvent actionEvent) {
                final Point locationOnScreen = GhostedDragImage.access$000(this.this$0).getLocationOnScreen();
                final Point point = new Point(GhostedDragImage.access$100(this.this$0));
                final int n = (point.x - locationOnScreen.x) / 2;
                final int n2 = (point.y - locationOnScreen.y) / 2;
                if (n != 0 || n2 != 0) {
                    locationOnScreen.translate(n, n2);
                    this.this$0.move(locationOnScreen);
                }
                else {
                    this.val$timer.stop();
                    this.this$0.dispose();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }
    
    static Window access$000(final GhostedDragImage ghostedDragImage) {
        return ghostedDragImage.dragImage;
    }
    
    static Point access$100(final GhostedDragImage ghostedDragImage) {
        return ghostedDragImage.origin;
    }
}
