package com.viaversion.viaversion.platform;

import com.viaversion.viaversion.api.platform.*;
import java.util.function.*;
import java.lang.reflect.*;
import java.util.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.*;
import io.netty.channel.*;
import com.viaversion.viaversion.libs.gson.*;

public abstract class LegacyViaInjector implements ViaInjector
{
    protected final List injectedFutures;
    protected final List injectedLists;
    
    public LegacyViaInjector() {
        this.injectedFutures = new ArrayList();
        this.injectedLists = new ArrayList();
    }
    
    @Override
    public void inject() throws ReflectiveOperationException {
        final Object serverConnection = this.getServerConnection();
        if (serverConnection == null) {
            throw new RuntimeException("Failed to find the core component 'ServerConnection'");
        }
        final Field[] declaredFields = serverConnection.getClass().getDeclaredFields();
        while (0 < declaredFields.length) {
            final Field field = declaredFields[0];
            if (List.class.isAssignableFrom(field.getType())) {
                if (field.getGenericType().getTypeName().contains(ChannelFuture.class.getName())) {
                    field.setAccessible(true);
                    final List list = (List)field.get(serverConnection);
                    final SynchronizedListWrapper synchronizedListWrapper = new SynchronizedListWrapper(list, this::lambda$inject$0);
                    // monitorenter(list2 = list)
                    final Iterator<ChannelFuture> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        this.injectChannelFuture(iterator.next());
                    }
                    field.set(serverConnection, synchronizedListWrapper);
                    // monitorexit(list2)
                    this.injectedLists.add(new Pair(field, serverConnection));
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    private void injectChannelFuture(final ChannelFuture channelFuture) throws ReflectiveOperationException {
        final List names = channelFuture.channel().pipeline().names();
        ChannelHandler first = null;
        final Iterator<String> iterator = names.iterator();
        if (iterator.hasNext()) {
            final ChannelHandler value = channelFuture.channel().pipeline().get(iterator.next());
            ReflectionUtil.get(value, "childHandler", ChannelInitializer.class);
            first = value;
        }
        if (first == null) {
            first = channelFuture.channel().pipeline().first();
        }
        ReflectionUtil.set(first, "childHandler", this.createChannelInitializer((ChannelInitializer)ReflectionUtil.get(first, "childHandler", ChannelInitializer.class)));
        this.injectedFutures.add(channelFuture);
    }
    
    @Override
    public void uninject() throws ReflectiveOperationException {
        final Iterator<ChannelFuture> iterator = this.injectedFutures.iterator();
        while (iterator.hasNext()) {
            final ChannelPipeline pipeline = iterator.next().channel().pipeline();
            ChannelHandler first = pipeline.first();
            if (first == null) {
                Via.getPlatform().getLogger().info("Empty pipeline, nothing to uninject");
            }
            else {
                for (final String s : pipeline.names()) {
                    final ChannelHandler value = pipeline.get(s);
                    if (value == null) {
                        Via.getPlatform().getLogger().warning("Could not get handler " + s);
                    }
                    else {
                        if (ReflectionUtil.get(value, "childHandler", ChannelInitializer.class) instanceof WrappedChannelInitializer) {
                            first = value;
                            break;
                        }
                        continue;
                    }
                }
                final ChannelInitializer channelInitializer = (ChannelInitializer)ReflectionUtil.get(first, "childHandler", ChannelInitializer.class);
                if (!(channelInitializer instanceof WrappedChannelInitializer)) {
                    continue;
                }
                ReflectionUtil.set(first, "childHandler", ((WrappedChannelInitializer)channelInitializer).original());
            }
        }
        this.injectedFutures.clear();
        for (final Pair pair : this.injectedLists) {
            final Field field = (Field)pair.key();
            final Object value2 = field.get(pair.value());
            if (value2 instanceof SynchronizedListWrapper) {
                final List originalList = ((SynchronizedListWrapper)value2).originalList();
                // monitorenter(list = originalList)
                field.set(pair.value(), originalList);
            }
            // monitorexit(list)
        }
        this.injectedLists.clear();
    }
    
    @Override
    public boolean lateProtocolVersionSetting() {
        return true;
    }
    
    @Override
    public JsonObject getDump() {
        final JsonObject jsonObject = new JsonObject();
        final JsonArray jsonArray = new JsonArray();
        jsonObject.add("injectedChannelInitializers", jsonArray);
        for (final ChannelFuture channelFuture : this.injectedFutures) {
            final JsonObject jsonObject2 = new JsonObject();
            jsonArray.add(jsonObject2);
            jsonObject2.addProperty("futureClass", channelFuture.getClass().getName());
            jsonObject2.addProperty("channelClass", channelFuture.channel().getClass().getName());
            final JsonArray jsonArray2 = new JsonArray();
            jsonObject2.add("pipeline", jsonArray2);
            for (final String s : channelFuture.channel().pipeline().names()) {
                final JsonObject jsonObject3 = new JsonObject();
                jsonArray2.add(jsonObject3);
                jsonObject3.addProperty("name", s);
                final ChannelHandler value = channelFuture.channel().pipeline().get(s);
                if (value == null) {
                    jsonObject3.addProperty("status", "INVALID");
                }
                else {
                    jsonObject3.addProperty("class", value.getClass().getName());
                    final Object value2 = ReflectionUtil.get(value, "childHandler", ChannelInitializer.class);
                    jsonObject3.addProperty("childClass", ((WrappedChannelInitializer)value2).getClass().getName());
                    if (!(value2 instanceof WrappedChannelInitializer)) {
                        continue;
                    }
                    jsonObject3.addProperty("oldInit", ((WrappedChannelInitializer)value2).original().getClass().getName());
                }
            }
        }
        final JsonObject jsonObject4 = new JsonObject();
        final JsonObject jsonObject5 = new JsonObject();
        for (final Pair pair : this.injectedLists) {
            final Field field = (Field)pair.key();
            final Object value3 = field.get(pair.value());
            jsonObject5.addProperty(field.getName(), ((SynchronizedListWrapper)value3).getClass().getName());
            if (value3 instanceof SynchronizedListWrapper) {
                jsonObject4.addProperty(field.getName(), ((SynchronizedListWrapper)value3).originalList().getClass().getName());
            }
        }
        jsonObject.add("wrappedLists", jsonObject4);
        jsonObject.add("currentLists", jsonObject5);
        return jsonObject;
    }
    
    @Override
    public String getEncoderName() {
        return "encoder";
    }
    
    @Override
    public String getDecoderName() {
        return "decoder";
    }
    
    protected abstract Object getServerConnection() throws ReflectiveOperationException;
    
    protected abstract WrappedChannelInitializer createChannelInitializer(final ChannelInitializer p0);
    
    protected abstract void blame(final ChannelHandler p0) throws ReflectiveOperationException;
    
    private void lambda$inject$0(final Object o) {
        this.injectChannelFuture((ChannelFuture)o);
    }
}
