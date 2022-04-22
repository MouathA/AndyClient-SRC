package net.minecraft.client.renderer.block.model;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.client.model.pipeline.*;
import net.minecraft.client.*;
import optifine.*;

public class BakedQuad implements IVertexProducer
{
    protected int[] field_178215_a;
    protected final int field_178213_b;
    protected final EnumFacing face;
    private static final String __OBFID;
    private TextureAtlasSprite sprite;
    private int[] vertexDataSingle;
    
    public BakedQuad(final int[] field_178215_a, final int field_178213_b, final EnumFacing face, final TextureAtlasSprite sprite) {
        this.sprite = null;
        this.vertexDataSingle = null;
        this.field_178215_a = field_178215_a;
        this.field_178213_b = field_178213_b;
        this.face = face;
        this.sprite = sprite;
        this.fixVertexData();
    }
    
    public TextureAtlasSprite getSprite() {
        if (this.sprite == null) {
            this.sprite = getSpriteByUv(this.func_178209_a());
        }
        return this.sprite;
    }
    
    @Override
    public String toString() {
        return "vertex: " + this.field_178215_a.length / 7 + ", tint: " + this.field_178213_b + ", facing: " + this.face + ", sprite: " + this.sprite;
    }
    
    public BakedQuad(final int[] field_178215_a, final int field_178213_b, final EnumFacing face) {
        this.sprite = null;
        this.vertexDataSingle = null;
        this.field_178215_a = field_178215_a;
        this.field_178213_b = field_178213_b;
        this.face = face;
        this.fixVertexData();
    }
    
    public int[] func_178209_a() {
        this.fixVertexData();
        return this.field_178215_a;
    }
    
    public boolean func_178212_b() {
        return this.field_178213_b != -1;
    }
    
    public int func_178211_c() {
        return this.field_178213_b;
    }
    
    public EnumFacing getFace() {
        return this.face;
    }
    
    public int[] getVertexDataSingle() {
        if (this.vertexDataSingle == null) {
            this.vertexDataSingle = makeVertexDataSingle(this.func_178209_a(), this.getSprite());
        }
        return this.vertexDataSingle;
    }
    
    private static int[] makeVertexDataSingle(final int[] array, final TextureAtlasSprite textureAtlasSprite) {
        final int[] array2 = array.clone();
        final int n = textureAtlasSprite.sheetWidth / textureAtlasSprite.getIconWidth();
        final int n2 = textureAtlasSprite.sheetHeight / textureAtlasSprite.getIconHeight();
        final int n3 = array2.length / 4;
        return array2;
    }
    
    @Override
    public void pipe(final IVertexConsumer vertexConsumer) {
        Reflector.callVoid(Reflector.LightUtil_putBakedQuad, vertexConsumer, this);
    }
    
    private static TextureAtlasSprite getSpriteByUv(final int[] array) {
        final float n = 1.0f;
        final float n2 = 1.0f;
        final float n3 = 0.0f;
        final float n4 = 0.0f;
        final int n5 = array.length / 4;
        return Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV((n + n3) / 2.0f, (n2 + n4) / 2.0f);
    }
    
    private void fixVertexData() {
        if (Config.isShaders()) {
            if (this.field_178215_a.length == 28) {
                this.field_178215_a = expandVertexData(this.field_178215_a);
            }
        }
        else if (this.field_178215_a.length == 56) {
            this.field_178215_a = compactVertexData(this.field_178215_a);
        }
    }
    
    private static int[] expandVertexData(final int[] array) {
        return new int[array.length / 4 * 2 * 4];
    }
    
    private static int[] compactVertexData(final int[] array) {
        return new int[array.length / 4 / 2 * 4];
    }
    
    static {
        __OBFID = "CL_00002512";
    }
}
