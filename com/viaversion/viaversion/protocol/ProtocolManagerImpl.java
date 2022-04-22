package com.viaversion.viaversion.protocol;

import java.util.concurrent.locks.*;
import com.google.common.util.concurrent.*;
import com.google.common.collect.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.protocols.protocol1_9_1to1_9.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_9_1.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.*;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.*;
import com.viaversion.viaversion.protocols.protocol1_11_1to1_11.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.protocols.protocol1_12_2to1_12_1.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.*;
import com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.*;
import com.viaversion.viaversion.protocols.protocol1_14_2to1_14_1.*;
import com.viaversion.viaversion.protocols.protocol1_14_3to1_14_2.*;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.protocols.protocol1_15_1to1_15.*;
import com.viaversion.viaversion.protocols.protocol1_15_2to1_15_1.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.protocols.protocol1_16_1to1_16.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.protocols.protocol1_16_3to1_16_2.*;
import com.viaversion.viaversion.protocols.protocol1_16_4to1_16_3.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import com.viaversion.viaversion.protocols.protocol1_18_2to1_18.*;
import com.google.common.base.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import us.myles.ViaVersion.api.protocol.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocol.packet.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.protocols.base.*;

public class ProtocolManagerImpl implements ProtocolManager
{
    private static final Protocol BASE_PROTOCOL;
    private final Int2ObjectMap registryMap;
    private final Map protocols;
    private final Map pathCache;
    private final Set supportedVersions;
    private final List baseProtocols;
    private final List registerList;
    private final ReadWriteLock mappingLoaderLock;
    private Map mappingLoaderFutures;
    private ThreadPoolExecutor mappingLoaderExecutor;
    private boolean mappingsLoaded;
    private ServerProtocolVersion serverProtocolVersion;
    private boolean onlyCheckLoweringPathEntries;
    private int maxProtocolPathSize;
    
    public ProtocolManagerImpl() {
        this.registryMap = new Int2ObjectOpenHashMap(32);
        this.protocols = new HashMap();
        this.pathCache = new ConcurrentHashMap();
        this.supportedVersions = new HashSet();
        this.baseProtocols = Lists.newCopyOnWriteArrayList();
        this.registerList = new ArrayList();
        this.mappingLoaderLock = new ReentrantReadWriteLock();
        this.mappingLoaderFutures = new HashMap();
        this.serverProtocolVersion = new ServerProtocolVersionSingleton(-1);
        this.onlyCheckLoweringPathEntries = true;
        this.maxProtocolPathSize = 50;
        (this.mappingLoaderExecutor = new ThreadPoolExecutor(5, 16, 45L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("Via-Mappingloader-%d").build())).allowCoreThreadTimeOut(true);
    }
    
    public void registerProtocols() {
        this.registerBaseProtocol(ProtocolManagerImpl.BASE_PROTOCOL, Range.lessThan(Integer.MIN_VALUE));
        this.registerBaseProtocol(new BaseProtocol1_7(), Range.lessThan(ProtocolVersion.v1_16.getVersion()));
        this.registerBaseProtocol(new BaseProtocol1_16(), Range.atLeast(ProtocolVersion.v1_16.getVersion()));
        this.registerProtocol(new Protocol1_9To1_8(), ProtocolVersion.v1_9, ProtocolVersion.v1_8);
        this.registerProtocol(new Protocol1_9_1To1_9(), Arrays.asList(ProtocolVersion.v1_9_1.getVersion(), ProtocolVersion.v1_9_2.getVersion()), ProtocolVersion.v1_9.getVersion());
        this.registerProtocol(new Protocol1_9_3To1_9_1_2(), ProtocolVersion.v1_9_3, ProtocolVersion.v1_9_2);
        this.registerProtocol(new Protocol1_9To1_9_1(), ProtocolVersion.v1_9, ProtocolVersion.v1_9_1);
        this.registerProtocol(new Protocol1_9_1_2To1_9_3_4(), Arrays.asList(ProtocolVersion.v1_9_1.getVersion(), ProtocolVersion.v1_9_2.getVersion()), ProtocolVersion.v1_9_3.getVersion());
        this.registerProtocol(new Protocol1_10To1_9_3_4(), ProtocolVersion.v1_10, ProtocolVersion.v1_9_3);
        this.registerProtocol(new Protocol1_11To1_10(), ProtocolVersion.v1_11, ProtocolVersion.v1_10);
        this.registerProtocol(new Protocol1_11_1To1_11(), ProtocolVersion.v1_11_1, ProtocolVersion.v1_11);
        this.registerProtocol(new Protocol1_12To1_11_1(), ProtocolVersion.v1_12, ProtocolVersion.v1_11_1);
        this.registerProtocol(new Protocol1_12_1To1_12(), ProtocolVersion.v1_12_1, ProtocolVersion.v1_12);
        this.registerProtocol(new Protocol1_12_2To1_12_1(), ProtocolVersion.v1_12_2, ProtocolVersion.v1_12_1);
        this.registerProtocol(new Protocol1_13To1_12_2(), ProtocolVersion.v1_13, ProtocolVersion.v1_12_2);
        this.registerProtocol(new Protocol1_13_1To1_13(), ProtocolVersion.v1_13_1, ProtocolVersion.v1_13);
        this.registerProtocol(new Protocol1_13_2To1_13_1(), ProtocolVersion.v1_13_2, ProtocolVersion.v1_13_1);
        this.registerProtocol(new Protocol1_14To1_13_2(), ProtocolVersion.v1_14, ProtocolVersion.v1_13_2);
        this.registerProtocol(new Protocol1_14_1To1_14(), ProtocolVersion.v1_14_1, ProtocolVersion.v1_14);
        this.registerProtocol(new Protocol1_14_2To1_14_1(), ProtocolVersion.v1_14_2, ProtocolVersion.v1_14_1);
        this.registerProtocol(new Protocol1_14_3To1_14_2(), ProtocolVersion.v1_14_3, ProtocolVersion.v1_14_2);
        this.registerProtocol(new Protocol1_14_4To1_14_3(), ProtocolVersion.v1_14_4, ProtocolVersion.v1_14_3);
        this.registerProtocol(new Protocol1_15To1_14_4(), ProtocolVersion.v1_15, ProtocolVersion.v1_14_4);
        this.registerProtocol(new Protocol1_15_1To1_15(), ProtocolVersion.v1_15_1, ProtocolVersion.v1_15);
        this.registerProtocol(new Protocol1_15_2To1_15_1(), ProtocolVersion.v1_15_2, ProtocolVersion.v1_15_1);
        this.registerProtocol(new Protocol1_16To1_15_2(), ProtocolVersion.v1_16, ProtocolVersion.v1_15_2);
        this.registerProtocol(new Protocol1_16_1To1_16(), ProtocolVersion.v1_16_1, ProtocolVersion.v1_16);
        this.registerProtocol(new Protocol1_16_2To1_16_1(), ProtocolVersion.v1_16_2, ProtocolVersion.v1_16_1);
        this.registerProtocol(new Protocol1_16_3To1_16_2(), ProtocolVersion.v1_16_3, ProtocolVersion.v1_16_2);
        this.registerProtocol(new Protocol1_16_4To1_16_3(), ProtocolVersion.v1_16_4, ProtocolVersion.v1_16_3);
        this.registerProtocol(new Protocol1_17To1_16_4(), ProtocolVersion.v1_17, ProtocolVersion.v1_16_4);
        this.registerProtocol(new Protocol1_17_1To1_17(), ProtocolVersion.v1_17_1, ProtocolVersion.v1_17);
        this.registerProtocol(new Protocol1_18To1_17_1(), ProtocolVersion.v1_18, ProtocolVersion.v1_17_1);
        this.registerProtocol(new Protocol1_18_2To1_18(), ProtocolVersion.v1_18_2, ProtocolVersion.v1_18);
    }
    
    @Override
    public void registerProtocol(final Protocol protocol, final ProtocolVersion protocolVersion, final ProtocolVersion protocolVersion2) {
        this.registerProtocol(protocol, Collections.singletonList(protocolVersion.getVersion()), protocolVersion2.getVersion());
    }
    
    @Override
    public void registerProtocol(final Protocol protocol, final List list, final int n) {
        protocol.initialize();
        if (!this.pathCache.isEmpty()) {
            this.pathCache.clear();
        }
        this.protocols.put(protocol.getClass(), protocol);
        for (final int intValue : list) {
            Preconditions.checkArgument(intValue != n);
            ((Int2ObjectMap)this.registryMap.computeIfAbsent(intValue, ProtocolManagerImpl::lambda$registerProtocol$0)).put(n, protocol);
        }
        if (Via.getPlatform().isPluginEnabled()) {
            protocol.register(Via.getManager().getProviders());
            this.refreshVersions();
        }
        else {
            this.registerList.add(protocol);
        }
        if (protocol.hasMappingDataToLoad()) {
            if (this.mappingLoaderExecutor != null) {
                this.addMappingLoaderFuture(protocol.getClass(), protocol::loadMappingData);
            }
            else {
                protocol.loadMappingData();
            }
        }
    }
    
    @Override
    public void registerBaseProtocol(final Protocol protocol, final Range range) {
        Preconditions.checkArgument(protocol.isBaseProtocol(), (Object)"Protocol is not a base protocol");
        protocol.initialize();
        this.baseProtocols.add(new Pair(range, protocol));
        if (Via.getPlatform().isPluginEnabled()) {
            protocol.register(Via.getManager().getProviders());
            this.refreshVersions();
        }
        else {
            this.registerList.add(protocol);
        }
    }
    
    public void refreshVersions() {
        this.supportedVersions.clear();
        this.supportedVersions.add(this.serverProtocolVersion.lowestSupportedVersion());
        for (final ProtocolVersion protocolVersion : ProtocolVersion.getProtocols()) {
            final List protocolPath = this.getProtocolPath(protocolVersion.getVersion(), this.serverProtocolVersion.lowestSupportedVersion());
            if (protocolPath == null) {
                continue;
            }
            this.supportedVersions.add(protocolVersion.getVersion());
            final Iterator<ProtocolPathEntry> iterator2 = protocolPath.iterator();
            while (iterator2.hasNext()) {
                this.supportedVersions.add(iterator2.next().outputProtocolVersion());
            }
        }
    }
    
    @Override
    public List getProtocolPath(final int n, final int n2) {
        if (n == n2) {
            return null;
        }
        final ProtocolPathKeyImpl protocolPathKeyImpl = new ProtocolPathKeyImpl(n, n2);
        final List list = this.pathCache.get(protocolPathKeyImpl);
        if (list != null) {
            return list;
        }
        final Int2ObjectSortedMap protocolPath = this.getProtocolPath(new Int2ObjectLinkedOpenHashMap(), n, n2);
        if (protocolPath == null) {
            return null;
        }
        final ArrayList list2 = new ArrayList<ProtocolPathEntryImpl>(protocolPath.size());
        for (final Int2ObjectMap.Entry entry : protocolPath.int2ObjectEntrySet()) {
            list2.add(new ProtocolPathEntryImpl(entry.getIntKey(), entry.getValue()));
        }
        this.pathCache.put(protocolPathKeyImpl, list2);
        return list2;
    }
    
    @Override
    public VersionedPacketTransformer createPacketTransformer(final ProtocolVersion protocolVersion, final Class clazz, final Class clazz2) {
        Preconditions.checkArgument(clazz != ClientboundPacketType.class && clazz2 != ServerboundPacketType.class);
        return new VersionedPacketTransformerImpl(protocolVersion, clazz, clazz2);
    }
    
    private Int2ObjectSortedMap getProtocolPath(final Int2ObjectSortedMap int2ObjectSortedMap, final int n, final int n2) {
        if (int2ObjectSortedMap.size() > this.maxProtocolPathSize) {
            return null;
        }
        final Int2ObjectMap int2ObjectMap = (Int2ObjectMap)this.registryMap.get(n);
        if (int2ObjectMap == null) {
            return null;
        }
        final Protocol protocol = (Protocol)int2ObjectMap.get(n2);
        if (protocol != null) {
            int2ObjectSortedMap.put(n2, protocol);
            return int2ObjectSortedMap;
        }
        Int2ObjectMap int2ObjectMap2 = null;
        for (final Int2ObjectMap.Entry entry : int2ObjectMap.int2ObjectEntrySet()) {
            final int intKey = entry.getIntKey();
            if (int2ObjectSortedMap.containsKey(intKey)) {
                continue;
            }
            if (this.onlyCheckLoweringPathEntries && Math.abs(n2 - intKey) > Math.abs(n2 - n)) {
                continue;
            }
            final Int2ObjectLinkedOpenHashMap int2ObjectLinkedOpenHashMap = new Int2ObjectLinkedOpenHashMap(int2ObjectSortedMap);
            int2ObjectLinkedOpenHashMap.put(intKey, entry.getValue());
            final Int2ObjectSortedMap protocolPath = this.getProtocolPath(int2ObjectLinkedOpenHashMap, intKey, n2);
            if (protocolPath == null || (int2ObjectMap2 != null && protocolPath.size() >= int2ObjectMap2.size())) {
                continue;
            }
            int2ObjectMap2 = protocolPath;
        }
        return (Int2ObjectSortedMap)int2ObjectMap2;
    }
    
    @Override
    public Protocol getProtocol(final Class clazz) {
        return this.protocols.get(clazz);
    }
    
    @Override
    public Protocol getProtocol(final int n, final int n2) {
        final Int2ObjectMap int2ObjectMap = (Int2ObjectMap)this.registryMap.get(n);
        return (int2ObjectMap != null) ? ((Protocol)int2ObjectMap.get(n2)) : null;
    }
    
    @Override
    public Protocol getBaseProtocol(final int n) {
        for (final Pair pair : Lists.reverse(this.baseProtocols)) {
            if (((Range)pair.key()).contains(n)) {
                return (Protocol)pair.value();
            }
        }
        throw new IllegalStateException("No Base Protocol for " + n);
    }
    
    @Override
    public ServerProtocolVersion getServerProtocolVersion() {
        return this.serverProtocolVersion;
    }
    
    public void setServerProtocol(final ServerProtocolVersion serverProtocolVersion) {
        this.serverProtocolVersion = serverProtocolVersion;
        ProtocolRegistry.SERVER_PROTOCOL = serverProtocolVersion.lowestSupportedVersion();
    }
    
    @Override
    public boolean isWorkingPipe() {
        for (final Int2ObjectMap int2ObjectMap : this.registryMap.values()) {
            final IntBidirectionalIterator iterator2 = this.serverProtocolVersion.supportedVersions().iterator();
            while (iterator2.hasNext()) {
                if (int2ObjectMap.containsKey((int)iterator2.next())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public SortedSet getSupportedVersions() {
        return Collections.unmodifiableSortedSet(new TreeSet<Object>(this.supportedVersions));
    }
    
    @Override
    public void setOnlyCheckLoweringPathEntries(final boolean onlyCheckLoweringPathEntries) {
        this.onlyCheckLoweringPathEntries = onlyCheckLoweringPathEntries;
    }
    
    @Override
    public boolean onlyCheckLoweringPathEntries() {
        return this.onlyCheckLoweringPathEntries;
    }
    
    @Override
    public int getMaxProtocolPathSize() {
        return this.maxProtocolPathSize;
    }
    
    @Override
    public void setMaxProtocolPathSize(final int maxProtocolPathSize) {
        this.maxProtocolPathSize = maxProtocolPathSize;
    }
    
    @Override
    public Protocol getBaseProtocol() {
        return ProtocolManagerImpl.BASE_PROTOCOL;
    }
    
    @Override
    public void completeMappingDataLoading(final Class clazz) throws Exception {
        if (this.mappingsLoaded) {
            return;
        }
        final CompletableFuture mappingLoaderFuture = this.getMappingLoaderFuture(clazz);
        if (mappingLoaderFuture != null) {
            mappingLoaderFuture.get();
        }
    }
    
    @Override
    public boolean checkForMappingCompletion() {
        this.mappingLoaderLock.readLock().lock();
        if (this.mappingsLoaded) {
            this.mappingLoaderLock.readLock().unlock();
            return true;
        }
        final Iterator<CompletableFuture> iterator = this.mappingLoaderFutures.values().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isDone()) {
                this.mappingLoaderLock.readLock().unlock();
                return false;
            }
        }
        this.shutdownLoaderExecutor();
        this.mappingLoaderLock.readLock().unlock();
        return true;
    }
    
    @Override
    public void addMappingLoaderFuture(final Class clazz, final Runnable runnable) {
        final CompletableFuture<Void> exceptionally = CompletableFuture.runAsync(runnable, this.mappingLoaderExecutor).exceptionally((Function<Throwable, ? extends Void>)this.mappingLoaderThrowable(clazz));
        this.mappingLoaderLock.writeLock().lock();
        this.mappingLoaderFutures.put(clazz, exceptionally);
        this.mappingLoaderLock.writeLock().unlock();
    }
    
    @Override
    public void addMappingLoaderFuture(final Class clazz, final Class clazz2, final Runnable runnable) {
        final CompletableFuture exceptionally = this.getMappingLoaderFuture(clazz2).whenCompleteAsync((BiConsumer)ProtocolManagerImpl::lambda$addMappingLoaderFuture$1, (Executor)this.mappingLoaderExecutor).exceptionally(this.mappingLoaderThrowable(clazz));
        this.mappingLoaderLock.writeLock().lock();
        this.mappingLoaderFutures.put(clazz, exceptionally);
        this.mappingLoaderLock.writeLock().unlock();
    }
    
    @Override
    public CompletableFuture getMappingLoaderFuture(final Class clazz) {
        this.mappingLoaderLock.readLock().lock();
        final CompletableFuture completableFuture = this.mappingsLoaded ? null : this.mappingLoaderFutures.get(clazz);
        this.mappingLoaderLock.readLock().unlock();
        return completableFuture;
    }
    
    @Override
    public PacketWrapper createPacketWrapper(final PacketType packetType, final ByteBuf byteBuf, final UserConnection userConnection) {
        return new PacketWrapperImpl(packetType, byteBuf, userConnection);
    }
    
    @Deprecated
    @Override
    public PacketWrapper createPacketWrapper(final int n, final ByteBuf byteBuf, final UserConnection userConnection) {
        return new PacketWrapperImpl(n, byteBuf, userConnection);
    }
    
    public void onServerLoaded() {
        final Iterator<Protocol> iterator = this.registerList.iterator();
        while (iterator.hasNext()) {
            iterator.next().register(Via.getManager().getProviders());
        }
        this.registerList.clear();
    }
    
    private void shutdownLoaderExecutor() {
        Preconditions.checkArgument(!this.mappingsLoaded);
        Via.getPlatform().getLogger().info("Finished mapping loading, shutting down loader executor!");
        this.mappingsLoaded = true;
        this.mappingLoaderExecutor.shutdown();
        this.mappingLoaderExecutor = null;
        this.mappingLoaderFutures.clear();
        this.mappingLoaderFutures = null;
        if (MappingDataLoader.isCacheJsonMappings()) {
            MappingDataLoader.getMappingsCache().clear();
        }
    }
    
    private Function mappingLoaderThrowable(final Class clazz) {
        return ProtocolManagerImpl::lambda$mappingLoaderThrowable$2;
    }
    
    private static Void lambda$mappingLoaderThrowable$2(final Class clazz, final Throwable t) {
        Via.getPlatform().getLogger().severe("Error during mapping loading of " + clazz.getSimpleName());
        t.printStackTrace();
        return null;
    }
    
    private static void lambda$addMappingLoaderFuture$1(final Runnable runnable, final Void void1, final Throwable t) {
        runnable.run();
    }
    
    private static Int2ObjectMap lambda$registerProtocol$0(final int n) {
        return new Int2ObjectOpenHashMap(2);
    }
    
    static {
        BASE_PROTOCOL = new BaseProtocol();
    }
}
