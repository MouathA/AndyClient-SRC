package org.lwjgl.opengl;

public final class PixelFormat implements PixelFormatLWJGL
{
    private int bpp;
    private int alpha;
    private int depth;
    private int stencil;
    private int samples;
    private int colorSamples;
    private int num_aux_buffers;
    private int accum_bpp;
    private int accum_alpha;
    private boolean stereo;
    private boolean floating_point;
    private boolean floating_point_packed;
    private boolean sRGB;
    
    public PixelFormat() {
        this(0, 8, 0);
    }
    
    public PixelFormat(final int n, final int n2, final int n3) {
        this(n, n2, n3, 0);
    }
    
    public PixelFormat(final int n, final int n2, final int n3, final int n4) {
        this(0, n, n2, n3, n4);
    }
    
    public PixelFormat(final int n, final int n2, final int n3, final int n4, final int n5) {
        this(n, n2, n3, n4, n5, 0, 0, 0, false);
    }
    
    public PixelFormat(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final boolean b) {
        this(n, n2, n3, n4, n5, n6, n7, n8, b, false);
    }
    
    public PixelFormat(final int bpp, final int alpha, final int depth, final int stencil, final int samples, final int num_aux_buffers, final int accum_bpp, final int accum_alpha, final boolean stereo, final boolean floating_point) {
        this.bpp = bpp;
        this.alpha = alpha;
        this.depth = depth;
        this.stencil = stencil;
        this.samples = samples;
        this.num_aux_buffers = num_aux_buffers;
        this.accum_bpp = accum_bpp;
        this.accum_alpha = accum_alpha;
        this.stereo = stereo;
        this.floating_point = floating_point;
        this.floating_point_packed = false;
        this.sRGB = false;
    }
    
    private PixelFormat(final PixelFormat pixelFormat) {
        this.bpp = pixelFormat.bpp;
        this.alpha = pixelFormat.alpha;
        this.depth = pixelFormat.depth;
        this.stencil = pixelFormat.stencil;
        this.samples = pixelFormat.samples;
        this.colorSamples = pixelFormat.colorSamples;
        this.num_aux_buffers = pixelFormat.num_aux_buffers;
        this.accum_bpp = pixelFormat.accum_bpp;
        this.accum_alpha = pixelFormat.accum_alpha;
        this.stereo = pixelFormat.stereo;
        this.floating_point = pixelFormat.floating_point;
        this.floating_point_packed = pixelFormat.floating_point_packed;
        this.sRGB = pixelFormat.sRGB;
    }
    
    public int getBitsPerPixel() {
        return this.bpp;
    }
    
    public PixelFormat withBitsPerPixel(final int bpp) {
        if (bpp < 0) {
            throw new IllegalArgumentException("Invalid number of bits per pixel specified: " + bpp);
        }
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.bpp = bpp;
        return pixelFormat;
    }
    
    public int getAlphaBits() {
        return this.alpha;
    }
    
    public PixelFormat withAlphaBits(final int alpha) {
        if (alpha < 0) {
            throw new IllegalArgumentException("Invalid number of alpha bits specified: " + alpha);
        }
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.alpha = alpha;
        return pixelFormat;
    }
    
    public int getDepthBits() {
        return this.depth;
    }
    
    public PixelFormat withDepthBits(final int depth) {
        if (depth < 0) {
            throw new IllegalArgumentException("Invalid number of depth bits specified: " + depth);
        }
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.depth = depth;
        return pixelFormat;
    }
    
    public int getStencilBits() {
        return this.stencil;
    }
    
    public PixelFormat withStencilBits(final int stencil) {
        if (stencil < 0) {
            throw new IllegalArgumentException("Invalid number of stencil bits specified: " + stencil);
        }
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.stencil = stencil;
        return pixelFormat;
    }
    
    public int getSamples() {
        return this.samples;
    }
    
    public PixelFormat withSamples(final int samples) {
        if (samples < 0) {
            throw new IllegalArgumentException("Invalid number of samples specified: " + samples);
        }
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.samples = samples;
        return pixelFormat;
    }
    
    public PixelFormat withCoverageSamples(final int n) {
        return this.withCoverageSamples(n, this.samples);
    }
    
    public PixelFormat withCoverageSamples(final int colorSamples, final int samples) {
        if (samples < 0 || colorSamples < 0 || (samples == 0 && 0 < colorSamples) || samples < colorSamples) {
            throw new IllegalArgumentException("Invalid number of coverage samples specified: " + samples + " - " + colorSamples);
        }
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.samples = samples;
        pixelFormat.colorSamples = colorSamples;
        return pixelFormat;
    }
    
    public int getAuxBuffers() {
        return this.num_aux_buffers;
    }
    
    public PixelFormat withAuxBuffers(final int num_aux_buffers) {
        if (num_aux_buffers < 0) {
            throw new IllegalArgumentException("Invalid number of auxiliary buffers specified: " + num_aux_buffers);
        }
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.num_aux_buffers = num_aux_buffers;
        return pixelFormat;
    }
    
    public int getAccumulationBitsPerPixel() {
        return this.accum_bpp;
    }
    
    public PixelFormat withAccumulationBitsPerPixel(final int accum_bpp) {
        if (accum_bpp < 0) {
            throw new IllegalArgumentException("Invalid number of bits per pixel in the accumulation buffer specified: " + accum_bpp);
        }
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.accum_bpp = accum_bpp;
        return pixelFormat;
    }
    
    public int getAccumulationAlpha() {
        return this.accum_alpha;
    }
    
    public PixelFormat withAccumulationAlpha(final int accum_alpha) {
        if (accum_alpha < 0) {
            throw new IllegalArgumentException("Invalid number of alpha bits in the accumulation buffer specified: " + accum_alpha);
        }
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.accum_alpha = accum_alpha;
        return pixelFormat;
    }
    
    public boolean isStereo() {
        return this.stereo;
    }
    
    public PixelFormat withStereo(final boolean stereo) {
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.stereo = stereo;
        return pixelFormat;
    }
    
    public boolean isFloatingPoint() {
        return this.floating_point;
    }
    
    public PixelFormat withFloatingPoint(final boolean floating_point) {
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.floating_point = floating_point;
        if (floating_point) {
            pixelFormat.floating_point_packed = false;
        }
        return pixelFormat;
    }
    
    public PixelFormat withFloatingPointPacked(final boolean floating_point_packed) {
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.floating_point_packed = floating_point_packed;
        if (floating_point_packed) {
            pixelFormat.floating_point = false;
        }
        return pixelFormat;
    }
    
    public boolean isSRGB() {
        return this.sRGB;
    }
    
    public PixelFormat withSRGB(final boolean srgb) {
        final PixelFormat pixelFormat = new PixelFormat(this);
        pixelFormat.sRGB = srgb;
        return pixelFormat;
    }
}
