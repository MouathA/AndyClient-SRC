package org.lwjgl.opengl;

final class WindowsFileVersion
{
    private final int product_version_ms;
    private final int product_version_ls;
    
    WindowsFileVersion(final int product_version_ms, final int product_version_ls) {
        this.product_version_ms = product_version_ms;
        this.product_version_ls = product_version_ls;
    }
    
    @Override
    public String toString() {
        return (this.product_version_ms >> 16 & 0xFFFF) + "." + (this.product_version_ms & 0xFFFF) + "." + (this.product_version_ls >> 16 & 0xFFFF) + "." + (this.product_version_ls & 0xFFFF);
    }
}
