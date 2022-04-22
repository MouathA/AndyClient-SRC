package net.minecraft.client.resources;

import org.apache.logging.log4j.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.util.*;

public class ResourcePackListEntryDefault extends ResourcePackListEntry
{
    private static final Logger logger;
    private final IResourcePack field_148320_d;
    private final ResourceLocation field_148321_e;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000822";
        logger = LogManager.getLogger();
    }
    
    public ResourcePackListEntryDefault(final GuiScreenResourcePacks guiScreenResourcePacks) {
        super(guiScreenResourcePacks);
        this.field_148320_d = this.field_148317_a.getResourcePackRepository().rprDefaultResourcePack;
        this.field_148321_e = this.field_148317_a.getTextureManager().getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.field_148320_d.getPackImage()));
    }
    
    @Override
    protected String func_148311_a() {
        final PackMetadataSection packMetadataSection = (PackMetadataSection)this.field_148320_d.getPackMetadata(this.field_148317_a.getResourcePackRepository().rprMetadataSerializer, "pack");
        if (packMetadataSection != null) {
            return packMetadataSection.func_152805_a().getFormattedText();
        }
        return EnumChatFormatting.RED + "Missing " + "pack.mcmeta" + " :(";
    }
    
    protected boolean func_148309_e() {
        return false;
    }
    
    @Override
    protected boolean func_148308_f() {
        return false;
    }
    
    protected boolean func_148314_g() {
        return false;
    }
    
    protected boolean func_148307_h() {
        return false;
    }
    
    @Override
    protected String func_148312_b() {
        return "Default";
    }
    
    @Override
    protected void func_148313_c() {
        this.field_148317_a.getTextureManager().bindTexture(this.field_148321_e);
    }
    
    @Override
    protected boolean func_148310_d() {
        return false;
    }
}
