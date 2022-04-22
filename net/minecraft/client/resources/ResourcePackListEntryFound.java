package net.minecraft.client.resources;

import net.minecraft.client.gui.*;

public class ResourcePackListEntryFound extends ResourcePackListEntry
{
    private final ResourcePackRepository.Entry field_148319_c;
    private static final String __OBFID;
    
    public ResourcePackListEntryFound(final GuiScreenResourcePacks guiScreenResourcePacks, final ResourcePackRepository.Entry field_148319_c) {
        super(guiScreenResourcePacks);
        this.field_148319_c = field_148319_c;
    }
    
    @Override
    protected void func_148313_c() {
        this.field_148319_c.bindTexturePackIcon(this.field_148317_a.getTextureManager());
    }
    
    @Override
    protected String func_148311_a() {
        return this.field_148319_c.getTexturePackDescription();
    }
    
    @Override
    protected String func_148312_b() {
        return this.field_148319_c.getResourcePackName();
    }
    
    public ResourcePackRepository.Entry func_148318_i() {
        return this.field_148319_c;
    }
    
    static {
        __OBFID = "CL_00000823";
    }
}
