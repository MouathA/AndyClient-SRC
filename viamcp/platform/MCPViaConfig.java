package viamcp.platform;

import com.viaversion.viaversion.configuration.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MCPViaConfig extends AbstractViaConfig
{
    private static final List UNSUPPORTED;
    
    static {
        UNSUPPORTED = Arrays.asList("anti-xray-patch", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "quick-move-action-fix", "nms-player-ticking", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox");
    }
    
    public MCPViaConfig(final File file) {
        super(file);
        this.reloadConfig();
    }
    
    @Override
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viaversion/config.yml");
    }
    
    @Override
    protected void handleConfig(final Map map) {
    }
    
    @Override
    public List getUnsupportedOptions() {
        return MCPViaConfig.UNSUPPORTED;
    }
    
    @Override
    public boolean isAntiXRay() {
        return false;
    }
    
    @Override
    public boolean isNMSPlayerTicking() {
        return false;
    }
    
    @Override
    public boolean is1_12QuickMoveActionFix() {
        return false;
    }
    
    @Override
    public String getBlockConnectionMethod() {
        return "packet";
    }
    
    @Override
    public boolean is1_9HitboxFix() {
        return false;
    }
    
    @Override
    public boolean is1_14HitboxFix() {
        return false;
    }
}
