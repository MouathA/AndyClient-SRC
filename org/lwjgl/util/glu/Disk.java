package org.lwjgl.util.glu;

import org.lwjgl.opengl.*;

public class Disk extends Quadric
{
    public void draw(final float n, final float n2, final int n3, final int n4) {
        if (super.normals != 100002) {
            if (super.orientation == 100020) {
                GL11.glNormal3f(0.0f, 0.0f, 1.0f);
            }
            else {
                GL11.glNormal3f(0.0f, 0.0f, -1.0f);
            }
        }
        final float n5 = 6.2831855f / n3;
        final float n6 = (n2 - n) / n4;
        switch (super.drawStyle) {
            case 100012: {
                final float n7 = 2.0f * n2;
                float n8 = n;
                while (0 < n4) {
                    final float n9 = n8 + n6;
                    if (super.orientation == 100020) {
                        GL11.glBegin(8);
                        while (0 <= n3) {
                            float n10;
                            if (0 == n3) {
                                n10 = 0.0f;
                            }
                            else {
                                n10 = 0 * n5;
                            }
                            final float sin = this.sin(n10);
                            final float cos = this.cos(n10);
                            this.TXTR_COORD(0.5f + sin * n9 / n7, 0.5f + cos * n9 / n7);
                            GL11.glVertex2f(n9 * sin, n9 * cos);
                            this.TXTR_COORD(0.5f + sin * n8 / n7, 0.5f + cos * n8 / n7);
                            GL11.glVertex2f(n8 * sin, n8 * cos);
                            int n11 = 0;
                            ++n11;
                        }
                    }
                    else {
                        GL11.glBegin(8);
                        int n11 = n3;
                        while (0 >= 0) {
                            float n12;
                            if (0 == n3) {
                                n12 = 0.0f;
                            }
                            else {
                                n12 = 0 * n5;
                            }
                            final float sin2 = this.sin(n12);
                            final float cos2 = this.cos(n12);
                            this.TXTR_COORD(0.5f - sin2 * n9 / n7, 0.5f + cos2 * n9 / n7);
                            GL11.glVertex2f(n9 * sin2, n9 * cos2);
                            this.TXTR_COORD(0.5f - sin2 * n8 / n7, 0.5f + cos2 * n8 / n7);
                            GL11.glVertex2f(n8 * sin2, n8 * cos2);
                            --n11;
                        }
                    }
                    n8 = n9;
                    int n13 = 0;
                    ++n13;
                }
                break;
            }
            case 100011: {
                int n16 = 0;
                int n17 = 0;
                while (0 <= n4) {
                    final float n14 = n + 0 * n6;
                    GL11.glBegin(2);
                    while (0 < n3) {
                        final float n15 = 0 * n5;
                        GL11.glVertex2f(n14 * this.sin(n15), n14 * this.cos(n15));
                        ++n16;
                    }
                    ++n17;
                }
                while (0 < n3) {
                    final float n18 = 0 * n5;
                    final float sin3 = this.sin(n18);
                    final float cos3 = this.cos(n18);
                    GL11.glBegin(3);
                    while (0 <= n4) {
                        final float n19 = n + 0 * n6;
                        GL11.glVertex2f(n19 * sin3, n19 * cos3);
                        ++n17;
                    }
                    ++n16;
                }
                break;
            }
            case 100010: {
                GL11.glBegin(0);
                while (0 < n3) {
                    final float n20 = 0 * n5;
                    final float sin4 = this.sin(n20);
                    final float cos4 = this.cos(n20);
                    while (0 <= n4) {
                        final float n21 = n * 0 * n6;
                        GL11.glVertex2f(n21 * sin4, n21 * cos4);
                        int n13 = 0;
                        ++n13;
                    }
                    int n17 = 0;
                    ++n17;
                }
                break;
            }
            case 100013: {
                if (n != 0.0) {
                    GL11.glBegin(2);
                    for (float n22 = 0.0f; n22 < 6.2831854820251465; n22 += n5) {
                        GL11.glVertex2f(n * this.sin(n22), n * this.cos(n22));
                    }
                }
                GL11.glBegin(2);
                for (float n23 = 0.0f; n23 < 6.2831855f; n23 += n5) {
                    GL11.glVertex2f(n2 * this.sin(n23), n2 * this.cos(n23));
                }
                break;
            }
            default: {}
        }
    }
}
