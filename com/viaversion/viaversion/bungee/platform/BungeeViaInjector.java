package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.api.platform.*;
import java.lang.reflect.*;
import net.md_5.bungee.api.*;
import java.util.function.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.bungee.handlers.*;
import io.netty.channel.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.*;

public class BungeeViaInjector implements ViaInjector
{
    private static final Field LISTENERS_FIELD;
    private final List injectedChannels;
    
    public BungeeViaInjector() {
        this.injectedChannels = new ArrayList();
    }
    
    @Override
    public void inject() throws ReflectiveOperationException {
        final Set set = (Set)BungeeViaInjector.LISTENERS_FIELD.get(ProxyServer.getInstance());
        BungeeViaInjector.LISTENERS_FIELD.set(ProxyServer.getInstance(), new SetWrapper(set, this::lambda$inject$0));
        final Iterator<Channel> iterator = set.iterator();
        while (iterator.hasNext()) {
            this.injectChannel(iterator.next());
        }
    }
    
    @Override
    public void uninject() {
        Via.getPlatform().getLogger().severe("ViaVersion cannot remove itself from Bungee without a reboot!");
    }
    
    private void injectChannel(final Channel channel) throws ReflectiveOperationException {
        final List names = channel.pipeline().names();
        ChannelHandler first = null;
        final Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            final ChannelHandler value = channel.pipeline().get(iterator.next());
            ReflectionUtil.get(value, "childHandler", ChannelInitializer.class);
            first = value;
        }
        if (first == null) {
            first = channel.pipeline().first();
        }
        if (first.getClass().getName().equals("net.md_5.bungee.query.QueryHandler")) {
            return;
        }
        ReflectionUtil.set(first, "childHandler", new BungeeChannelInitializer((ChannelInitializer)ReflectionUtil.get(first, "childHandler", ChannelInitializer.class)));
        this.injectedChannels.add(channel);
    }
    
    @Override
    public int getServerProtocolVersion() throws Exception {
        return this.getBungeeSupportedVersions().get(0);
    }
    
    @Override
    public IntSortedSet getServerProtocolVersions() throws Exception {
        return new IntLinkedOpenHashSet(this.getBungeeSupportedVersions());
    }
    
    private List getBungeeSupportedVersions() throws Exception {
        return (List)ReflectionUtil.getStatic(Class.forName("net.md_5.bungee.protocol.ProtocolConstants"), "SUPPORTED_VERSION_IDS", List.class);
    }
    
    @Override
    public JsonObject getDump() {
        final JsonObject jsonObject = new JsonObject();
        final JsonArray jsonArray = new JsonArray();
        for (final Channel channel : this.injectedChannels) {
            final JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("channelClass", channel.getClass().getName());
            final JsonArray jsonArray2 = new JsonArray();
            for (final String s : channel.pipeline().names()) {
                final JsonObject jsonObject3 = new JsonObject();
                jsonObject3.addProperty("name", s);
                final ChannelHandler value = channel.pipeline().get(s);
                if (value == null) {
                    jsonObject3.addProperty("status", "INVALID");
                }
                else {
                    jsonObject3.addProperty("class", value.getClass().getName());
                    final Object value2 = ReflectionUtil.get(value, "childHandler", ChannelInitializer.class);
                    jsonObject3.addProperty("childClass", ((BungeeChannelInitializer)value2).getClass().getName());
                    if (value2 instanceof BungeeChannelInitializer) {
                        jsonObject3.addProperty("oldInit", ((BungeeChannelInitializer)value2).getOriginal().getClass().getName());
                    }
                    jsonArray2.add(jsonObject3);
                }
            }
            jsonObject2.add("pipeline", jsonArray2);
            jsonArray.add(jsonObject2);
        }
        jsonObject.add("injectedChannelInitializers", jsonArray);
        final Object value3 = BungeeViaInjector.LISTENERS_FIELD.get(ProxyServer.getInstance());
        jsonObject.addProperty("currentList", ((SetWrapper)value3).getClass().getName());
        if (value3 instanceof SetWrapper) {
            jsonObject.addProperty("wrappedList", ((SetWrapper)value3).originalSet().getClass().getName());
        }
        return jsonObject;
    }
    
    private void lambda$inject$0(final Channel channel) {
        this.injectChannel(channel);
    }
    
    static {
        (LISTENERS_FIELD = ProxyServer.getInstance().getClass().getDeclaredField("listeners")).setAccessible(true);
    }
}
