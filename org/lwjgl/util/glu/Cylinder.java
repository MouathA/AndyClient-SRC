package org.lwjgl.util.glu;

import org.lwjgl.opengl.*;

public class Cylinder extends Quadric
{
    public void draw(final float n, final float n2, final float n3, final int n4, final int n5) {
        float n6;
        if (super.orientation == 100021) {
            n6 = -1.0f;
        }
        else {
            n6 = 1.0f;
        }
        final float n7 = 6.2831855f / n4;
        final float n8 = (n2 - n) / n5;
        final float n9 = n3 / n5;
        final float n10 = (n - n2) / n3;
        if (super.drawStyle == 100010) {
            GL11.glBegin(0);
            while (0 < n4) {
                final float cos = this.cos(0 * n7);
                final float sin = this.sin(0 * n7);
                this.normal3f(cos * n6, sin * n6, n10 * n6);
                float n11 = 0.0f;
                float n12 = n;
                while (0 <= n5) {
                    GL11.glVertex3f(cos * n12, sin * n12, n11);
                    n11 += n9;
                    n12 += n8;
                    int n13 = 0;
                    ++n13;
                }
                int n14 = 0;
                ++n14;
            }
        }
        else if (super.drawStyle == 100011 || super.drawStyle == 100013) {
            int n14 = 0;
            if (super.drawStyle == 100011) {
                float n15 = 0.0f;
                float n16 = n;
                while (0 <= n5) {
                    GL11.glBegin(2);
                    while (0 < n4) {
                        final float cos2 = this.cos(0 * n7);
                        final float sin2 = this.sin(0 * n7);
                        this.normal3f(cos2 * n6, sin2 * n6, n10 * n6);
                        GL11.glVertex3f(cos2 * n16, sin2 * n16, n15);
                        ++n14;
                    }
                    n15 += n9;
                    n16 += n8;
                    int n13 = 0;
                    ++n13;
                }
            }
            else if (n != 0.0) {
                GL11.glBegin(2);
                while (0 < n4) {
                    final float cos3 = this.cos(0 * n7);
                    final float sin3 = this.sin(0 * n7);
                    this.normal3f(cos3 * n6, sin3 * n6, n10 * n6);
                    GL11.glVertex3f(cos3 * n, sin3 * n, 0.0f);
                    ++n14;
                }
                GL11.glBegin(2);
                while (0 < n4) {
                    final float cos4 = this.cos(0 * n7);
                    final float sin4 = this.sin(0 * n7);
                    this.normal3f(cos4 * n6, sin4 * n6, n10 * n6);
                    GL11.glVertex3f(cos4 * n2, sin4 * n2, n3);
                    ++n14;
                }
            }
            GL11.glBegin(1);
            while (0 < n4) {
                final float cos5 = this.cos(0 * n7);
                final float sin5 = this.sin(0 * n7);
                this.normal3f(cos5 * n6, sin5 * n6, n10 * n6);
                GL11.glVertex3f(cos5 * n, sin5 * n, 0.0f);
                GL11.glVertex3f(cos5 * n2, sin5 * n2, n3);
                ++n14;
            }
        }
        else if (super.drawStyle == 100012) {
            final float n17 = 1.0f / n4;
            final float n18 = 1.0f / n5;
            float n19 = 0.0f;
            float n20 = 0.0f;
            float n21 = n;
            while (0 < n5) {
                float n22 = 0.0f;
                GL11.glBegin(8);
                while (0 <= n4) {
                    float n23;
                    float n24;
                    if (0 == n4) {
                        n23 = this.sin(0.0f);
                        n24 = this.cos(0.0f);
                    }
                    else {
                        n23 = this.sin(0 * n7);
                        n24 = this.cos(0 * n7);
                    }
                    if (n6 == 1.0f) {
                        this.normal3f(n23 * n6, n24 * n6, n10 * n6);
                        this.TXTR_COORD(n22, n19);
                        GL11.glVertex3f(n23 * n21, n24 * n21, n20);
                        this.normal3f(n23 * n6, n24 * n6, n10 * n6);
                        this.TXTR_COORD(n22, n19 + n18);
                        GL11.glVertex3f(n23 * (n21 + n8), n24 * (n21 + n8), n20 + n9);
                    }
                    else {
                        this.normal3f(n23 * n6, n24 * n6, n10 * n6);
                        this.TXTR_COORD(n22, n19);
                        GL11.glVertex3f(n23 * n21, n24 * n21, n20);
                        this.normal3f(n23 * n6, n24 * n6, n10 * n6);
                        this.TXTR_COORD(n22, n19 + n18);
                        GL11.glVertex3f(n23 * (n21 + n8), n24 * (n21 + n8), n20 + n9);
                    }
                    n22 += n17;
                    int n14 = 0;
                    ++n14;
                }
                n21 += n8;
                n19 += n18;
                n20 += n9;
                int n13 = 0;
                ++n13;
            }
        }
    }
}
