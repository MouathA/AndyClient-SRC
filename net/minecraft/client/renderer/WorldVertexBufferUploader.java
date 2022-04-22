package net.minecraft.client.renderer;

import org.lwjgl.opengl.*;
import optifine.*;
import shadersmod.client.*;
import net.minecraft.client.renderer.vertex.*;
import java.nio.*;
import java.util.*;

public class WorldVertexBufferUploader
{
    public int draw(final WorldRenderer worldRenderer, final int n) {
        if (n > 0) {
            final VertexFormat func_178973_g = worldRenderer.func_178973_g();
            final int func_177338_f = func_178973_g.func_177338_f();
            final ByteBuffer func_178966_f = worldRenderer.func_178966_f();
            final List func_177343_g = func_178973_g.func_177343_g();
            final Iterator<VertexFormatElement> iterator = func_177343_g.iterator();
            final boolean exists = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
            final boolean exists2 = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();
            while (iterator.hasNext()) {
                final VertexFormatElement vertexFormatElement = iterator.next();
                final VertexFormatElement.EnumUseage func_177375_c = vertexFormatElement.func_177375_c();
                if (exists) {
                    Reflector.callVoid(func_177375_c, Reflector.ForgeVertexFormatElementEnumUseage_preDraw, vertexFormatElement, func_177338_f, func_178966_f);
                }
                else {
                    final int func_177397_c = vertexFormatElement.func_177367_b().func_177397_c();
                    final int func_177369_e = vertexFormatElement.func_177369_e();
                    switch (SwitchEnumUseage.field_178958_a[func_177375_c.ordinal()]) {
                        default: {
                            continue;
                        }
                        case 1: {
                            func_178966_f.position(vertexFormatElement.func_177373_a());
                            GL11.glVertexPointer(vertexFormatElement.func_177370_d(), func_177397_c, func_177338_f, func_178966_f);
                            GL11.glEnableClientState(32884);
                            continue;
                        }
                        case 2: {
                            func_178966_f.position(vertexFormatElement.func_177373_a());
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + func_177369_e);
                            GL11.glTexCoordPointer(vertexFormatElement.func_177370_d(), func_177397_c, func_177338_f, func_178966_f);
                            GL11.glEnableClientState(32888);
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            continue;
                        }
                        case 3: {
                            func_178966_f.position(vertexFormatElement.func_177373_a());
                            GL11.glColorPointer(vertexFormatElement.func_177370_d(), func_177397_c, func_177338_f, func_178966_f);
                            GL11.glEnableClientState(32886);
                            continue;
                        }
                        case 4: {
                            func_178966_f.position(vertexFormatElement.func_177373_a());
                            GL11.glNormalPointer(func_177397_c, func_177338_f, func_178966_f);
                            GL11.glEnableClientState(32885);
                            continue;
                        }
                    }
                }
            }
            if (worldRenderer.isMultiTexture()) {
                worldRenderer.drawMultiTexture();
            }
            else if (Config.isShaders()) {
                SVertexBuilder.drawArrays(worldRenderer.getDrawMode(), 0, worldRenderer.func_178989_h(), worldRenderer);
            }
            else {
                GL11.glDrawArrays(worldRenderer.getDrawMode(), 0, worldRenderer.func_178989_h());
            }
            for (final VertexFormatElement vertexFormatElement2 : func_177343_g) {
                final VertexFormatElement.EnumUseage func_177375_c2 = vertexFormatElement2.func_177375_c();
                if (exists2) {
                    Reflector.callVoid(func_177375_c2, Reflector.ForgeVertexFormatElementEnumUseage_postDraw, vertexFormatElement2, func_177338_f, func_178966_f);
                }
                else {
                    final int func_177369_e2 = vertexFormatElement2.func_177369_e();
                    switch (SwitchEnumUseage.field_178958_a[func_177375_c2.ordinal()]) {
                        default: {
                            continue;
                        }
                        case 1: {
                            GL11.glDisableClientState(32884);
                            continue;
                        }
                        case 2: {
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + func_177369_e2);
                            GL11.glDisableClientState(32888);
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            continue;
                        }
                        case 3: {
                            GL11.glDisableClientState(32886);
                            continue;
                        }
                        case 4: {
                            GL11.glDisableClientState(32885);
                            continue;
                        }
                    }
                }
            }
        }
        worldRenderer.reset();
        return n;
    }
    
    static final class SwitchEnumUseage
    {
        static final int[] field_178958_a;
        
        static {
            field_178958_a = new int[VertexFormatElement.EnumUseage.values().length];
            try {
                SwitchEnumUseage.field_178958_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumUseage.field_178958_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumUseage.field_178958_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumUseage.field_178958_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
