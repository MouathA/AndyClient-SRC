package viamcp.platform;

import com.viaversion.viaversion.*;
import java.util.*;
import io.netty.buffer.*;

public class MCPViaAPI extends ViaAPIBase
{
    @Override
    public int getPlayerVersion(final Object o) {
        return this.getPlayerVersion((UUID)o);
    }
    
    @Override
    public void sendRawPacket(final Object o, final ByteBuf byteBuf) {
        this.sendRawPacket((UUID)o, byteBuf);
    }
}
