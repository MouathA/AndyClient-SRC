package com.viaversion.viaversion.libs.javassist.util;

import java.lang.instrument.*;
import java.io.*;
import java.util.jar.*;
import java.util.zip.*;
import com.viaversion.viaversion.libs.javassist.*;

public class HotSwapAgent
{
    private static Instrumentation instrumentation;
    
    public Instrumentation instrumentation() {
        return HotSwapAgent.instrumentation;
    }
    
    public static void premain(final String s, final Instrumentation instrumentation) throws Throwable {
        agentmain(s, instrumentation);
    }
    
    public static void agentmain(final String s, final Instrumentation instrumentation) throws Throwable {
        if (!instrumentation.isRedefineClassesSupported()) {
            throw new RuntimeException("this JVM does not support redefinition of classes");
        }
        HotSwapAgent.instrumentation = instrumentation;
    }
    
    public static void redefine(final Class clazz, final CtClass ctClass) throws NotFoundException, IOException, CannotCompileException {
        redefine(new Class[] { clazz }, new CtClass[] { ctClass });
    }
    
    public static void redefine(final Class[] array, final CtClass[] array2) throws NotFoundException, IOException, CannotCompileException {
        final ClassDefinition[] array3 = new ClassDefinition[array.length];
        while (0 < array.length) {
            array3[0] = new ClassDefinition(array[0], array2[0].toBytecode());
            int n = 0;
            ++n;
        }
        HotSwapAgent.instrumentation.redefineClasses(array3);
    }
    
    private static void startAgent() throws NotFoundException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifnull          7
        //     6: return         
        //     7: invokestatic    com/viaversion/viaversion/libs/javassist/util/HotSwapAgent.createJarFile:()Ljava/io/File;
        //    10: astore_0       
        //    11: invokestatic    java/lang/management/ManagementFactory.getRuntimeMXBean:()Ljava/lang/management/RuntimeMXBean;
        //    14: invokeinterface java/lang/management/RuntimeMXBean.getName:()Ljava/lang/String;
        //    19: astore_1       
        //    20: aload_1        
        //    21: iconst_0       
        //    22: aload_1        
        //    23: bipush          64
        //    25: invokevirtual   java/lang/String.indexOf:(I)I
        //    28: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    31: astore_2       
        //    32: aload_2        
        //    33: invokestatic    com/sun/tools/attach/VirtualMachine.attach:(Ljava/lang/String;)Lcom/sun/tools/attach/VirtualMachine;
        //    36: astore_3       
        //    37: aload_3        
        //    38: aload_0        
        //    39: invokevirtual   java/io/File.getAbsolutePath:()Ljava/lang/String;
        //    42: aconst_null    
        //    43: invokevirtual   com/sun/tools/attach/VirtualMachine.loadAgent:(Ljava/lang/String;Ljava/lang/String;)V
        //    46: aload_3        
        //    47: invokevirtual   com/sun/tools/attach/VirtualMachine.detach:()V
        //    50: goto            65
        //    53: astore_0       
        //    54: new             Lcom/viaversion/viaversion/libs/javassist/NotFoundException;
        //    57: dup            
        //    58: ldc             "hotswap agent"
        //    60: aload_0        
        //    61: invokespecial   com/viaversion/viaversion/libs/javassist/NotFoundException.<init>:(Ljava/lang/String;Ljava/lang/Exception;)V
        //    64: athrow         
        //    65: getstatic       com/viaversion/viaversion/libs/javassist/util/HotSwapAgent.instrumentation:Ljava/lang/instrument/Instrumentation;
        //    68: ifnull          72
        //    71: return         
        //    72: ldc2_w          1000
        //    75: invokestatic    java/lang/Thread.sleep:(J)V
        //    78: goto            91
        //    81: astore_1       
        //    82: invokestatic    java/lang/Thread.currentThread:()Ljava/lang/Thread;
        //    85: invokevirtual   java/lang/Thread.interrupt:()V
        //    88: goto            97
        //    91: iinc            0, 1
        //    94: goto            65
        //    97: new             Lcom/viaversion/viaversion/libs/javassist/NotFoundException;
        //   100: dup            
        //   101: ldc             "hotswap agent (timeout)"
        //   103: invokespecial   com/viaversion/viaversion/libs/javassist/NotFoundException.<init>:(Ljava/lang/String;)V
        //   106: athrow         
        //    Exceptions:
        //  throws com.viaversion.viaversion.libs.javassist.NotFoundException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static File createAgentJarFile(final String s) throws IOException, CannotCompileException, NotFoundException {
        return createJarFile(new File(s));
    }
    
    private static File createJarFile() throws IOException, CannotCompileException, NotFoundException {
        final File tempFile = File.createTempFile("agent", ".jar");
        tempFile.deleteOnExit();
        return createJarFile(tempFile);
    }
    
    private static File createJarFile(final File file) throws IOException, CannotCompileException, NotFoundException {
        final Manifest manifest = new Manifest();
        final Attributes mainAttributes = manifest.getMainAttributes();
        mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        mainAttributes.put(new Attributes.Name("Premain-Class"), HotSwapAgent.class.getName());
        mainAttributes.put(new Attributes.Name("Agent-Class"), HotSwapAgent.class.getName());
        mainAttributes.put(new Attributes.Name("Can-Retransform-Classes"), "true");
        mainAttributes.put(new Attributes.Name("Can-Redefine-Classes"), "true");
        final JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(file), manifest);
        final String name = HotSwapAgent.class.getName();
        jarOutputStream.putNextEntry(new JarEntry(name.replace('.', '/') + ".class"));
        jarOutputStream.write(ClassPool.getDefault().get(name).toBytecode());
        jarOutputStream.closeEntry();
        if (jarOutputStream != null) {
            jarOutputStream.close();
        }
        return file;
    }
    
    static {
        HotSwapAgent.instrumentation = null;
    }
}
