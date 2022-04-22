package com.viaversion.viaversion.bukkit.classgenerator;

import com.viaversion.viaversion.*;
import org.bukkit.*;
import com.viaversion.viaversion.bukkit.util.*;
import com.viaversion.viaversion.bukkit.handlers.*;
import com.viaversion.viaversion.libs.javassist.expr.*;
import com.viaversion.viaversion.libs.javassist.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import com.viaversion.viaversion.classgenerator.generated.*;

public final class ClassGenerator
{
    private static final boolean useModules;
    private static HandlerConstructor constructor;
    private static String psPackage;
    private static Class psConnectListener;
    
    public static HandlerConstructor getConstructor() {
        return ClassGenerator.constructor;
    }
    
    public static void generate() {
        if (ViaVersionPlugin.getInstance().isCompatSpigotBuild() || ViaVersionPlugin.getInstance().isProtocolSupport()) {
            final ClassPool default1 = ClassPool.getDefault();
            default1.insertClassPath(new LoaderClassPath(Bukkit.class.getClassLoader()));
            final Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
            while (0 < plugins.length) {
                default1.insertClassPath(new LoaderClassPath(plugins[0].getClass().getClassLoader()));
                int n = 0;
                ++n;
            }
            if (ViaVersionPlugin.getInstance().isCompatSpigotBuild()) {
                final Class nms = NMSUtil.nms("PacketDecoder", "net.minecraft.network.PacketDecoder");
                final Class nms2 = NMSUtil.nms("PacketEncoder", "net.minecraft.network.PacketEncoder");
                addSpigotCompatibility(default1, BukkitDecodeHandler.class, nms);
                addSpigotCompatibility(default1, BukkitEncodeHandler.class, nms2);
            }
            else {
                if (isMultiplatformPS()) {
                    ClassGenerator.psConnectListener = makePSConnectListener(default1);
                    return;
                }
                final String oldPSPackage = getOldPSPackage();
                final Class<?> forName = Class.forName(oldPSPackage.equals("unknown") ? "protocolsupport.protocol.pipeline.common.PacketDecoder" : (oldPSPackage + ".wrapped.WrappedDecoder"));
                final Class<?> forName2 = Class.forName(oldPSPackage.equals("unknown") ? "protocolsupport.protocol.pipeline.common.PacketEncoder" : (oldPSPackage + ".wrapped.WrappedEncoder"));
                addPSCompatibility(default1, BukkitDecodeHandler.class, forName);
                addPSCompatibility(default1, BukkitEncodeHandler.class, forName2);
            }
            final CtClass class1 = default1.makeClass("com.viaversion.viaversion.classgenerator.generated.GeneratedConstructor");
            class1.setInterfaces(new CtClass[] { default1.get(HandlerConstructor.class.getName()) });
            default1.importPackage("com.viaversion.viaversion.classgenerator.generated");
            default1.importPackage("com.viaversion.viaversion.classgenerator");
            default1.importPackage("com.viaversion.viaversion.api.connection");
            default1.importPackage("io.netty.handler.codec");
            class1.addMethod(CtMethod.make("public MessageToByteEncoder newEncodeHandler(UserConnection info, MessageToByteEncoder minecraftEncoder) {\n        return new BukkitEncodeHandler(info, minecraftEncoder);\n    }", class1));
            class1.addMethod(CtMethod.make("public ByteToMessageDecoder newDecodeHandler(UserConnection info, ByteToMessageDecoder minecraftDecoder) {\n        return new BukkitDecodeHandler(info, minecraftDecoder);\n    }", class1));
            ClassGenerator.constructor = toClass(class1).getConstructor((Class[])new Class[0]).newInstance(new Object[0]);
        }
    }
    
    private static void addSpigotCompatibility(final ClassPool classPool, final Class clazz, final Class clazz2) {
        final CtClass andRename = classPool.getAndRename(clazz.getName(), "com.viaversion.viaversion.classgenerator.generated." + clazz.getSimpleName());
        if (clazz2 != null) {
            andRename.setSuperclass(classPool.get(clazz2.getName()));
            if (clazz2.getName().startsWith("net.minecraft") && andRename.getConstructors().length != 0) {
                andRename.getConstructors()[0].instrument(new ExprEditor() {
                    @Override
                    public void edit(final ConstructorCall constructorCall) throws CannotCompileException {
                        if (constructorCall.isSuper()) {
                            constructorCall.replace("super(null);");
                        }
                        super.edit(constructorCall);
                    }
                });
            }
        }
        toClass(andRename);
    }
    
    private static void addPSCompatibility(final ClassPool classPool, final Class clazz, final Class clazz2) {
        final boolean equals = getOldPSPackage().equals("unknown");
        final CtClass andRename = classPool.getAndRename(clazz.getName(), "com.viaversion.viaversion.classgenerator.generated." + clazz.getSimpleName());
        if (clazz2 != null) {
            andRename.setSuperclass(classPool.get(clazz2.getName()));
            if (!equals) {
                classPool.importPackage(getOldPSPackage());
                classPool.importPackage(getOldPSPackage() + ".wrapped");
                if (clazz2.getName().endsWith("Decoder")) {
                    andRename.addMethod(CtMethod.make("public void setRealDecoder(IPacketDecoder dec) {\n        ((WrappedDecoder) this.minecraftDecoder).setRealDecoder(dec);\n    }", andRename));
                }
                else {
                    classPool.importPackage("protocolsupport.api");
                    classPool.importPackage("java.lang.reflect");
                    andRename.addMethod(CtMethod.make("public void setRealEncoder(IPacketEncoder enc) {\n         try {\n             Field field = enc.getClass().getDeclaredField(\"version\");\n             field.setAccessible(true);\n             ProtocolVersion version = (ProtocolVersion) field.get(enc);\n             if (version == ProtocolVersion.MINECRAFT_FUTURE) enc = enc.getClass().getConstructor(\n                 new Class[]{ProtocolVersion.class}).newInstance(new Object[] {ProtocolVersion.getLatest()});\n         } catch (Exception e) {\n         }\n        ((WrappedEncoder) this.minecraftEncoder).setRealEncoder(enc);\n    }", andRename));
                }
            }
        }
        toClass(andRename);
    }
    
    private static Class makePSConnectListener(final ClassPool classPool) {
        final HandshakeProtocolType handshakeVersionMethod = handshakeVersionMethod();
        final CtClass value = classPool.get("protocolsupport.api.Connection$PacketListener");
        final CtClass class1 = classPool.makeClass("com.viaversion.viaversion.classgenerator.generated.ProtocolSupportConnectListener");
        class1.setSuperclass(value);
        classPool.importPackage("java.util.Arrays");
        classPool.importPackage("protocolsupport.api.ProtocolVersion");
        classPool.importPackage("protocolsupport.api.ProtocolType");
        classPool.importPackage("protocolsupport.api.Connection");
        classPool.importPackage("protocolsupport.api.Connection.PacketListener");
        classPool.importPackage("protocolsupport.api.Connection.PacketListener.PacketEvent");
        classPool.importPackage("protocolsupport.protocol.ConnectionImpl");
        classPool.importPackage(NMSUtil.nms("PacketHandshakingInSetProtocol", "net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol").getName());
        class1.addField(CtField.make("private ConnectionImpl connection;", class1));
        class1.addConstructor(CtNewConstructor.make("public ProtocolSupportConnectListener (ConnectionImpl connection) {\n    this.connection = connection;\n}", class1));
        class1.addMethod(CtNewMethod.make("public void onPacketReceiving(protocolsupport.api.Connection.PacketListener.PacketEvent event) {\n    if (event.getPacket() instanceof PacketHandshakingInSetProtocol) {\n        PacketHandshakingInSetProtocol packet = (PacketHandshakingInSetProtocol) event.getPacket();\n        int protoVersion = packet." + handshakeVersionMethod.methodName() + "();\n        if (connection.getVersion() == ProtocolVersion.MINECRAFT_FUTURE && protoVersion == com.viaversion.viaversion.api.Via.getAPI().getServerVersion().lowestSupportedVersion()) {\n            connection.setVersion(ProtocolVersion.getLatest(ProtocolType.PC));\n        }\n    }\n    connection.removePacketListener(this);\n}", class1));
        return toClass(class1);
    }
    
    public static void registerPSConnectListener(final ViaVersionPlugin viaVersionPlugin) {
        if (ClassGenerator.psConnectListener != null) {
            Bukkit.getPluginManager().registerEvent((Class)Class.forName("protocolsupport.api.events.ConnectionOpenEvent"), (Listener)new Listener() {}, EventPriority.HIGH, (EventExecutor)new EventExecutor() {
                public void execute(final Listener listener, final Event event) throws EventException {
                    final Object invoke = event.getClass().getMethod("getConnection", (Class<?>[])new Class[0]).invoke(event, new Object[0]);
                    invoke.getClass().getMethod("addPacketListener", Class.forName("protocolsupport.api.Connection$PacketListener")).invoke(invoke, ClassGenerator.access$000().getConstructor(invoke.getClass()).newInstance(invoke));
                }
            }, (Plugin)viaVersionPlugin);
        }
    }
    
    public static Class getPSConnectListener() {
        return ClassGenerator.psConnectListener;
    }
    
    public static String getOldPSPackage() {
        if (ClassGenerator.psPackage == null) {
            Class.forName("protocolsupport.protocol.core.IPacketDecoder");
            ClassGenerator.psPackage = "protocolsupport.protocol.core";
        }
        return ClassGenerator.psPackage;
    }
    
    public static boolean isMultiplatformPS() {
        Class.forName("protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketEncoder");
        return true;
    }
    
    public static HandshakeProtocolType handshakeVersionMethod() {
        NMSUtil.nms("PacketHandshakingInSetProtocol", "net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol").getMethod("getProtocolVersion", (Class[])new Class[0]);
        return HandshakeProtocolType.MAPPED;
    }
    
    private static Class toClass(final CtClass ctClass) throws CannotCompileException {
        return ClassGenerator.useModules ? ctClass.toClass(HandlerConstructor.class) : ctClass.toClass(HandlerConstructor.class.getClassLoader());
    }
    
    private static boolean hasModuleMethod() {
        Class.class.getDeclaredMethod("getModule", (Class<?>[])new Class[0]);
        return true;
    }
    
    static Class access$000() {
        return ClassGenerator.psConnectListener;
    }
    
    static {
        useModules = hasModuleMethod();
        ClassGenerator.constructor = new BasicHandlerConstructor();
    }
    
    private enum HandshakeProtocolType
    {
        MAPPED("MAPPED", 0, "getProtocolVersion"), 
        OBFUSCATED_OLD("OBFUSCATED_OLD", 1, "b"), 
        OBFUSCATED_NEW("OBFUSCATED_NEW", 2, "c");
        
        private final String methodName;
        private static final HandshakeProtocolType[] $VALUES;
        
        private HandshakeProtocolType(final String s, final int n, final String methodName) {
            this.methodName = methodName;
        }
        
        public String methodName() {
            return this.methodName;
        }
        
        static {
            $VALUES = new HandshakeProtocolType[] { HandshakeProtocolType.MAPPED, HandshakeProtocolType.OBFUSCATED_OLD, HandshakeProtocolType.OBFUSCATED_NEW };
        }
    }
}
