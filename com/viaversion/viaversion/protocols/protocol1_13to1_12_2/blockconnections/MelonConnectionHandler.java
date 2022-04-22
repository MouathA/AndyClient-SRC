package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

public class MelonConnectionHandler extends AbstractStempConnectionHandler
{
    public MelonConnectionHandler(final String s) {
        super(s);
    }
    
    static ConnectionData.ConnectorInitAction init() {
        return new MelonConnectionHandler("minecraft:melon_stem[age=7]").getInitAction("minecraft:melon", "minecraft:attached_melon_stem");
    }
}
