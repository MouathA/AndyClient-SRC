package viamcp.loader;

import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.bungee.providers.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.protocols.base.*;
import com.viaversion.viaversion.api.connection.*;
import viamcp.*;

public class MCPViaLoader implements ViaPlatformLoader
{
    @Override
    public void load() {
        Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BungeeMovementTransmitter());
        Via.getManager().getProviders().use(VersionProvider.class, new BaseVersionProvider() {
            final MCPViaLoader this$0;
            
            @Override
            public int getClosestServerProtocol(final UserConnection userConnection) throws Exception {
                if (userConnection.isClientSide()) {
                    return ViaMCP.getInstance().getVersion();
                }
                return super.getClosestServerProtocol(userConnection);
            }
        });
    }
    
    @Override
    public void unload() {
    }
}
