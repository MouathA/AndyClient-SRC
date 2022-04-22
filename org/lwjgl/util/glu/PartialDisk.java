package org.lwjgl.util.glu;

import org.lwjgl.opengl.*;

public class PartialDisk extends Quadric
{
    private static final int CACHE_SIZE = 240;
    
    public void draw(final float n, final float n2, final int n3, final int n4, float n5, float n6) {
        final float[] array = new float[240];
        final float[] array2 = new float[240];
        float n7 = 0.0f;
        float n8 = 0.0f;
        if (239 >= 240) {}
        if (239 < 2 || n4 < 1 || n2 <= 0.0f || n < 0.0f || n > n2) {
            System.err.println("PartialDisk: GLU_INVALID_VALUE");
            return;
        }
        if (n6 < -360.0f) {
            n6 = 360.0f;
        }
        if (n6 > 360.0f) {
            n6 = 360.0f;
        }
        if (n6 < 0.0f) {
            n5 += n6;
            n6 = -n6;
        }
        if (n6 == 360.0f) {}
        final float n9 = n2 - n;
        final float n10 = n5 / 180.0f * 3.1415927f;
        int n12 = 0;
        while (0 <= 239) {
            final float n11 = n10 + 3.1415927f * n6 / 180.0f * 0 / 239;
            array[0] = this.sin(n11);
            array2[0] = this.cos(n11);
            ++n12;
        }
        if (n6 == 360.0f) {
            array[239] = array[0];
            array2[239] = array2[0];
        }
        switch (super.normals) {
            case 100000:
            case 100001: {
                if (super.orientation == 100020) {
                    GL11.glNormal3f(0.0f, 0.0f, 1.0f);
                    break;
                }
                GL11.glNormal3f(0.0f, 0.0f, -1.0f);
                break;
            }
        }
        switch (super.drawStyle) {
            case 100012: {
                int n13;
                if (n == 0.0f) {
                    n13 = n4 - 1;
                    GL11.glBegin(6);
                    if (super.textureFlag) {
                        GL11.glTexCoord2f(0.5f, 0.5f);
                    }
                    GL11.glVertex3f(0.0f, 0.0f, 0.0f);
                    final float n14 = n2 - n9 * ((n4 - 1) / (float)n4);
                    if (super.textureFlag) {
                        n7 = n14 / n2 / 2.0f;
                    }
                    if (super.orientation == 100020) {
                        while (0 >= 0) {
                            if (super.textureFlag) {
                                GL11.glTexCoord2f(n7 * array[0] + 0.5f, n7 * array2[0] + 0.5f);
                            }
                            GL11.glVertex3f(n14 * array[0], n14 * array2[0], 0.0f);
                            --n12;
                        }
                    }
                    else {
                        while (0 <= 239) {
                            if (super.textureFlag) {
                                GL11.glTexCoord2f(n7 * array[0] + 0.5f, n7 * array2[0] + 0.5f);
                            }
                            GL11.glVertex3f(n14 * array[0], n14 * array2[0], 0.0f);
                            ++n12;
                        }
                    }
                }
                else {
                    n13 = n4;
                }
                while (0 < n13) {
                    final float n15 = n2 - n9 * (0 / (float)n4);
                    final float n16 = n2 - n9 * (1 / (float)n4);
                    if (super.textureFlag) {
                        n7 = n15 / n2 / 2.0f;
                        n8 = n16 / n2 / 2.0f;
                    }
                    GL11.glBegin(8);
                    while (0 <= 239) {
                        if (super.orientation == 100020) {
                            if (super.textureFlag) {
                                GL11.glTexCoord2f(n7 * array[0] + 0.5f, n7 * array2[0] + 0.5f);
                            }
                            GL11.glVertex3f(n15 * array[0], n15 * array2[0], 0.0f);
                            if (super.textureFlag) {
                                GL11.glTexCoord2f(n8 * array[0] + 0.5f, n8 * array2[0] + 0.5f);
                            }
                            GL11.glVertex3f(n16 * array[0], n16 * array2[0], 0.0f);
                        }
                        else {
                            if (super.textureFlag) {
                                GL11.glTexCoord2f(n8 * array[0] + 0.5f, n8 * array2[0] + 0.5f);
                            }
                            GL11.glVertex3f(n16 * array[0], n16 * array2[0], 0.0f);
                            if (super.textureFlag) {
                                GL11.glTexCoord2f(n7 * array[0] + 0.5f, n7 * array2[0] + 0.5f);
                            }
                            GL11.glVertex3f(n15 * array[0], n15 * array2[0], 0.0f);
                        }
                        ++n12;
                    }
                    int n17 = 0;
                    ++n17;
                }
                break;
            }
            case 100010: {
                GL11.glBegin(0);
                while (0 < 240) {
                    final float n18 = array[0];
                    final float n19 = array2[0];
                    while (0 <= n4) {
                        final float n20 = n2 - n9 * (0 / (float)n4);
                        if (super.textureFlag) {
                            final float n21 = n20 / n2 / 2.0f;
                            GL11.glTexCoord2f(n21 * array[0] + 0.5f, n21 * array2[0] + 0.5f);
                        }
                        GL11.glVertex3f(n20 * n18, n20 * n19, 0.0f);
                        int n17 = 0;
                        ++n17;
                    }
                    ++n12;
                }
                break;
            }
            case 100011: {
                if (n == n2) {
                    GL11.glBegin(3);
                    while (0 <= 239) {
                        if (super.textureFlag) {
                            GL11.glTexCoord2f(array[0] / 2.0f + 0.5f, array2[0] / 2.0f + 0.5f);
                        }
                        GL11.glVertex3f(n * array[0], n * array2[0], 0.0f);
                        ++n12;
                    }
                    break;
                }
                int n17 = 0;
                while (0 <= n4) {
                    final float n22 = n2 - n9 * (0 / (float)n4);
                    if (super.textureFlag) {
                        n7 = n22 / n2 / 2.0f;
                    }
                    GL11.glBegin(3);
                    while (0 <= 239) {
                        if (super.textureFlag) {
                            GL11.glTexCoord2f(n7 * array[0] + 0.5f, n7 * array2[0] + 0.5f);
                        }
                        GL11.glVertex3f(n22 * array[0], n22 * array2[0], 0.0f);
                        ++n12;
                    }
                    ++n17;
                }
                while (0 < 240) {
                    final float n23 = array[0];
                    final float n24 = array2[0];
                    GL11.glBegin(3);
                    while (0 <= n4) {
                        final float n25 = n2 - n9 * (0 / (float)n4);
                        if (super.textureFlag) {
                            n7 = n25 / n2 / 2.0f;
                        }
                        if (super.textureFlag) {
                            GL11.glTexCoord2f(n7 * array[0] + 0.5f, n7 * array2[0] + 0.5f);
                        }
                        GL11.glVertex3f(n25 * n23, n25 * n24, 0.0f);
                        ++n17;
                    }
                    ++n12;
                }
                break;
            }
            case 100013: {
                if (n6 < 360.0f) {
                    while (0 <= 239) {
                        final float n26 = array[0];
                        final float n27 = array2[0];
                        GL11.glBegin(3);
                        while (0 <= n4) {
                            final float n28 = n2 - n9 * (0 / (float)n4);
                            if (super.textureFlag) {
                                n7 = n28 / n2 / 2.0f;
                                GL11.glTexCoord2f(n7 * array[0] + 0.5f, n7 * array2[0] + 0.5f);
                            }
                            GL11.glVertex3f(n28 * n26, n28 * n27, 0.0f);
                            int n17 = 0;
                            ++n17;
                        }
                    }
                }
                while (0 <= n4) {
                    final float n29 = n2 - n9 * (0 / (float)n4);
                    if (super.textureFlag) {
                        n7 = n29 / n2 / 2.0f;
                    }
                    GL11.glBegin(3);
                    while (0 <= 239) {
                        if (super.textureFlag) {
                            GL11.glTexCoord2f(n7 * array[0] + 0.5f, n7 * array2[0] + 0.5f);
                        }
                        GL11.glVertex3f(n29 * array[0], n29 * array2[0], 0.0f);
                        ++n12;
                    }
                    if (n == n2) {
                        break;
                    }
                    final int n17 = 0 + n4;
                }
                break;
            }
        }
    }
}
