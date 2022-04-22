package com.mojang.realmsclient.gui;

import net.minecraft.realms.*;

public class RealmsHideableButton extends RealmsButton
{
    boolean visible;
    
    public RealmsHideableButton(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        super(n, n2, n3, n4, n5, s);
        this.visible = true;
    }
    
    @Override
    public void render(final int n, final int n2) {
        if (!this.visible) {
            return;
        }
        super.render(n, n2);
    }
    
    @Override
    public void clicked(final int n, final int n2) {
        if (!this.visible) {
            return;
        }
        super.clicked(n, n2);
    }
    
    @Override
    public void released(final int n, final int n2) {
        if (!this.visible) {
            return;
        }
        super.released(n, n2);
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public boolean getVisible() {
        return this.visible;
    }
}
