package viamcp.platform;

import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.libs.gson.*;

public class MCPViaInjector implements ViaInjector
{
    @Override
    public void inject() {
    }
    
    @Override
    public void uninject() {
    }
    
    @Override
    public int getServerProtocolVersion() {
        return 47;
    }
    
    @Override
    public String getEncoderName() {
        return "via-encoder";
    }
    
    @Override
    public String getDecoderName() {
        return "via-decoder";
    }
    
    @Override
    public JsonObject getDump() {
        return new JsonObject();
    }
}
