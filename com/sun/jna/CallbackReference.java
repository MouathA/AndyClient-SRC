package com.sun.jna;

import java.lang.ref.*;
import java.lang.reflect.*;
import java.util.*;

class CallbackReference extends WeakReference
{
    static final Map callbackMap;
    static final Map allocations;
    private static final Method PROXY_CALLBACK_METHOD;
    private static final Map initializers;
    Pointer cbstruct;
    CallbackProxy proxy;
    Method method;
    static Class array$Ljava$lang$Object;
    static Class class$com$sun$jna$CallbackProxy;
    static Class class$com$sun$jna$AltCallingConvention;
    static Class class$com$sun$jna$Structure;
    static Class class$com$sun$jna$Structure$ByValue;
    static Class class$com$sun$jna$Pointer;
    static Class class$com$sun$jna$NativeMapped;
    static Class class$java$lang$String;
    static Class class$com$sun$jna$WString;
    static Class array$Ljava$lang$String;
    static Class array$Lcom$sun$jna$WString;
    static Class class$com$sun$jna$Callback;
    static Class class$java$lang$Boolean;
    static Class class$java$lang$Void;
    static Class class$java$lang$Byte;
    static Class class$java$lang$Short;
    static Class class$java$lang$Character;
    static Class class$java$lang$Integer;
    static Class class$java$lang$Long;
    static Class class$java$lang$Float;
    static Class class$java$lang$Double;
    
    static void setCallbackThreadInitializer(final Callback callback, final CallbackThreadInitializer callbackThreadInitializer) {
        // monitorenter(callbackMap = CallbackReference.callbackMap)
        if (callbackThreadInitializer != null) {
            CallbackReference.initializers.put(callback, callbackThreadInitializer);
        }
        else {
            CallbackReference.initializers.remove(callback);
        }
    }
    // monitorexit(callbackMap)
    
    private static ThreadGroup initializeThread(Callback callback, final AttachOptions attachOptions) {
        if (callback instanceof DefaultCallbackProxy) {
            callback = ((DefaultCallbackProxy)callback).getCallback();
        }
        // monitorenter(initializers = CallbackReference.initializers)
        final CallbackThreadInitializer callbackThreadInitializer = CallbackReference.initializers.get(callback);
        // monitorexit(initializers)
        ThreadGroup threadGroup = null;
        if (callbackThreadInitializer != null) {
            threadGroup = callbackThreadInitializer.getThreadGroup(callback);
            attachOptions.name = callbackThreadInitializer.getName(callback);
            attachOptions.daemon = callbackThreadInitializer.isDaemon(callback);
            attachOptions.detach = callbackThreadInitializer.detach(callback);
            attachOptions.write();
        }
        return threadGroup;
    }
    
    public static Callback getCallback(final Class clazz, final Pointer pointer) {
        return getCallback(clazz, pointer, false);
    }
    
    private static Callback getCallback(final Class clazz, final Pointer pointer, final boolean b) {
        if (pointer == null) {
            return null;
        }
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("Callback type must be an interface");
        }
        final Map callbackMap = CallbackReference.callbackMap;
        // monitorenter(map = callbackMap)
        for (final Callback callback : callbackMap.keySet()) {
            if (clazz.isAssignableFrom(callback.getClass())) {
                final CallbackReference callbackReference = callbackMap.get(callback);
                if (pointer.equals((callbackReference != null) ? callbackReference.getTrampoline() : getNativeFunctionPointer(callback))) {
                    // monitorexit(map)
                    return callback;
                }
                continue;
            }
        }
        final boolean assignable = ((CallbackReference.class$com$sun$jna$AltCallingConvention == null) ? (CallbackReference.class$com$sun$jna$AltCallingConvention = class$("com.sun.jna.AltCallingConvention")) : CallbackReference.class$com$sun$jna$AltCallingConvention).isAssignableFrom(clazz);
        final HashMap<String, Method> hashMap = new HashMap<String, Method>();
        final Map libraryOptions = Native.getLibraryOptions(clazz);
        if (libraryOptions != null) {
            hashMap.putAll((Map<?, ?>)libraryOptions);
        }
        hashMap.put("invoking-method", getCallbackMethod(clazz));
        final Callback callback3 = (Callback)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new NativeFunctionHandler(pointer, (int)(assignable ? 1 : 0), hashMap));
        callbackMap.put(callback3, null);
        // monitorexit(map)
        return callback3;
    }
    
    private CallbackReference(final Callback p0, final int p1, final boolean p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokespecial   java/lang/ref/WeakReference.<init>:(Ljava/lang/Object;)V
        //     5: aload_1        
        //     6: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //     9: invokestatic    com/sun/jna/Native.getTypeMapper:(Ljava/lang/Class;)Lcom/sun/jna/TypeMapper;
        //    12: astore          4
        //    14: ldc             "os.arch"
        //    16: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //    19: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //    22: astore          7
        //    24: ldc             "ppc"
        //    26: aload           7
        //    28: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    31: ifne            44
        //    34: ldc             "powerpc"
        //    36: aload           7
        //    38: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    41: ifeq            48
        //    44: iconst_1       
        //    45: goto            49
        //    48: iconst_0       
        //    49: istore          8
        //    51: iconst_0       
        //    52: ifeq            151
        //    55: aload_1        
        //    56: invokestatic    com/sun/jna/CallbackReference.getCallbackMethod:(Lcom/sun/jna/Callback;)Ljava/lang/reflect/Method;
        //    59: astore          9
        //    61: aload           9
        //    63: invokevirtual   java/lang/reflect/Method.getParameterTypes:()[Ljava/lang/Class;
        //    66: astore          10
        //    68: iconst_0       
        //    69: aload           10
        //    71: arraylength    
        //    72: if_icmpge       131
        //    75: iload           8
        //    77: ifeq            103
        //    80: aload           10
        //    82: iconst_0       
        //    83: aaload         
        //    84: getstatic       java/lang/Float.TYPE:Ljava/lang/Class;
        //    87: if_acmpeq       100
        //    90: aload           10
        //    92: iconst_0       
        //    93: aaload         
        //    94: getstatic       java/lang/Double.TYPE:Ljava/lang/Class;
        //    97: if_acmpne       103
        //   100: goto            131
        //   103: aload           4
        //   105: ifnull          125
        //   108: aload           4
        //   110: aload           10
        //   112: iconst_0       
        //   113: aaload         
        //   114: invokeinterface com/sun/jna/TypeMapper.getFromNativeConverter:(Ljava/lang/Class;)Lcom/sun/jna/FromNativeConverter;
        //   119: ifnull          125
        //   122: goto            131
        //   125: iinc            11, 1
        //   128: goto            68
        //   131: aload           4
        //   133: ifnull          151
        //   136: aload           4
        //   138: aload           9
        //   140: invokevirtual   java/lang/reflect/Method.getReturnType:()Ljava/lang/Class;
        //   143: invokeinterface com/sun/jna/TypeMapper.getToNativeConverter:(Ljava/lang/Class;)Lcom/sun/jna/ToNativeConverter;
        //   148: ifnull          151
        //   151: iconst_0       
        //   152: ifeq            224
        //   155: aload_0        
        //   156: aload_1        
        //   157: invokestatic    com/sun/jna/CallbackReference.getCallbackMethod:(Lcom/sun/jna/Callback;)Ljava/lang/reflect/Method;
        //   160: putfield        com/sun/jna/CallbackReference.method:Ljava/lang/reflect/Method;
        //   163: aload_0        
        //   164: getfield        com/sun/jna/CallbackReference.method:Ljava/lang/reflect/Method;
        //   167: invokevirtual   java/lang/reflect/Method.getParameterTypes:()[Ljava/lang/Class;
        //   170: astore          5
        //   172: aload_0        
        //   173: getfield        com/sun/jna/CallbackReference.method:Ljava/lang/reflect/Method;
        //   176: invokevirtual   java/lang/reflect/Method.getReturnType:()Ljava/lang/Class;
        //   179: astore          6
        //   181: aload_1        
        //   182: aload_0        
        //   183: getfield        com/sun/jna/CallbackReference.method:Ljava/lang/reflect/Method;
        //   186: aload           5
        //   188: aload           6
        //   190: iload_2        
        //   191: iconst_1       
        //   192: invokestatic    com/sun/jna/Native.createNativeCallback:(Lcom/sun/jna/Callback;Ljava/lang/reflect/Method;[Ljava/lang/Class;Ljava/lang/Class;IZ)J
        //   195: lstore          9
        //   197: aload_0        
        //   198: lload           9
        //   200: lconst_0       
        //   201: lcmp           
        //   202: ifeq            217
        //   205: new             Lcom/sun/jna/Pointer;
        //   208: dup            
        //   209: lload           9
        //   211: invokespecial   com/sun/jna/Pointer.<init>:(J)V
        //   214: goto            218
        //   217: aconst_null    
        //   218: putfield        com/sun/jna/CallbackReference.cbstruct:Lcom/sun/jna/Pointer;
        //   221: goto            527
        //   224: aload_1        
        //   225: instanceof      Lcom/sun/jna/CallbackProxy;
        //   228: ifeq            242
        //   231: aload_0        
        //   232: aload_1        
        //   233: checkcast       Lcom/sun/jna/CallbackProxy;
        //   236: putfield        com/sun/jna/CallbackReference.proxy:Lcom/sun/jna/CallbackProxy;
        //   239: goto            260
        //   242: aload_0        
        //   243: new             Lcom/sun/jna/CallbackReference$DefaultCallbackProxy;
        //   246: dup            
        //   247: aload_0        
        //   248: aload_1        
        //   249: invokestatic    com/sun/jna/CallbackReference.getCallbackMethod:(Lcom/sun/jna/Callback;)Ljava/lang/reflect/Method;
        //   252: aload           4
        //   254: invokespecial   com/sun/jna/CallbackReference$DefaultCallbackProxy.<init>:(Lcom/sun/jna/CallbackReference;Ljava/lang/reflect/Method;Lcom/sun/jna/TypeMapper;)V
        //   257: putfield        com/sun/jna/CallbackReference.proxy:Lcom/sun/jna/CallbackProxy;
        //   260: aload_0        
        //   261: getfield        com/sun/jna/CallbackReference.proxy:Lcom/sun/jna/CallbackProxy;
        //   264: invokeinterface com/sun/jna/CallbackProxy.getParameterTypes:()[Ljava/lang/Class;
        //   269: astore          5
        //   271: aload_0        
        //   272: getfield        com/sun/jna/CallbackReference.proxy:Lcom/sun/jna/CallbackProxy;
        //   275: invokeinterface com/sun/jna/CallbackProxy.getReturnType:()Ljava/lang/Class;
        //   280: astore          6
        //   282: aload           4
        //   284: ifnull          354
        //   287: iconst_0       
        //   288: aload           5
        //   290: arraylength    
        //   291: if_icmpge       329
        //   294: aload           4
        //   296: aload           5
        //   298: iconst_0       
        //   299: aaload         
        //   300: invokeinterface com/sun/jna/TypeMapper.getFromNativeConverter:(Ljava/lang/Class;)Lcom/sun/jna/FromNativeConverter;
        //   305: astore          10
        //   307: aload           10
        //   309: ifnull          323
        //   312: aload           5
        //   314: iconst_0       
        //   315: aload           10
        //   317: invokeinterface com/sun/jna/FromNativeConverter.nativeType:()Ljava/lang/Class;
        //   322: aastore        
        //   323: iinc            9, 1
        //   326: goto            287
        //   329: aload           4
        //   331: aload           6
        //   333: invokeinterface com/sun/jna/TypeMapper.getToNativeConverter:(Ljava/lang/Class;)Lcom/sun/jna/ToNativeConverter;
        //   338: astore          9
        //   340: aload           9
        //   342: ifnull          354
        //   345: aload           9
        //   347: invokeinterface com/sun/jna/ToNativeConverter.nativeType:()Ljava/lang/Class;
        //   352: astore          6
        //   354: iconst_0       
        //   355: aload           5
        //   357: arraylength    
        //   358: if_icmpge       430
        //   361: aload           5
        //   363: iconst_0       
        //   364: aload_0        
        //   365: aload           5
        //   367: iconst_0       
        //   368: aaload         
        //   369: invokespecial   com/sun/jna/CallbackReference.getNativeType:(Ljava/lang/Class;)Ljava/lang/Class;
        //   372: aastore        
        //   373: aload           5
        //   375: iconst_0       
        //   376: aaload         
        //   377: invokestatic    com/sun/jna/CallbackReference.isAllowableNativeType:(Ljava/lang/Class;)Z
        //   380: ifne            424
        //   383: new             Ljava/lang/StringBuffer;
        //   386: dup            
        //   387: invokespecial   java/lang/StringBuffer.<init>:()V
        //   390: ldc_w           "Callback argument "
        //   393: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   396: aload           5
        //   398: iconst_0       
        //   399: aaload         
        //   400: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/Object;)Ljava/lang/StringBuffer;
        //   403: ldc_w           " requires custom type conversion"
        //   406: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   409: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   412: astore          10
        //   414: new             Ljava/lang/IllegalArgumentException;
        //   417: dup            
        //   418: aload           10
        //   420: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //   423: athrow         
        //   424: iinc            9, 1
        //   427: goto            354
        //   430: aload_0        
        //   431: aload           6
        //   433: invokespecial   com/sun/jna/CallbackReference.getNativeType:(Ljava/lang/Class;)Ljava/lang/Class;
        //   436: astore          6
        //   438: aload           6
        //   440: invokestatic    com/sun/jna/CallbackReference.isAllowableNativeType:(Ljava/lang/Class;)Z
        //   443: ifne            485
        //   446: new             Ljava/lang/StringBuffer;
        //   449: dup            
        //   450: invokespecial   java/lang/StringBuffer.<init>:()V
        //   453: ldc_w           "Callback return type "
        //   456: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   459: aload           6
        //   461: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/Object;)Ljava/lang/StringBuffer;
        //   464: ldc_w           " requires custom type conversion"
        //   467: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   470: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   473: astore          9
        //   475: new             Ljava/lang/IllegalArgumentException;
        //   478: dup            
        //   479: aload           9
        //   481: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //   484: athrow         
        //   485: aload_0        
        //   486: getfield        com/sun/jna/CallbackReference.proxy:Lcom/sun/jna/CallbackProxy;
        //   489: getstatic       com/sun/jna/CallbackReference.PROXY_CALLBACK_METHOD:Ljava/lang/reflect/Method;
        //   492: aload           5
        //   494: aload           6
        //   496: iload_2        
        //   497: iconst_0       
        //   498: invokestatic    com/sun/jna/Native.createNativeCallback:(Lcom/sun/jna/Callback;Ljava/lang/reflect/Method;[Ljava/lang/Class;Ljava/lang/Class;IZ)J
        //   501: lstore          9
        //   503: aload_0        
        //   504: lload           9
        //   506: lconst_0       
        //   507: lcmp           
        //   508: ifeq            523
        //   511: new             Lcom/sun/jna/Pointer;
        //   514: dup            
        //   515: lload           9
        //   517: invokespecial   com/sun/jna/Pointer.<init>:(J)V
        //   520: goto            524
        //   523: aconst_null    
        //   524: putfield        com/sun/jna/CallbackReference.cbstruct:Lcom/sun/jna/Pointer;
        //   527: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private Class getNativeType(final Class clazz) {
        if (((CallbackReference.class$com$sun$jna$Structure == null) ? (CallbackReference.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : CallbackReference.class$com$sun$jna$Structure).isAssignableFrom(clazz)) {
            Structure.newInstance(clazz);
            if (!((CallbackReference.class$com$sun$jna$Structure$ByValue == null) ? (CallbackReference.class$com$sun$jna$Structure$ByValue = class$("com.sun.jna.Structure$ByValue")) : CallbackReference.class$com$sun$jna$Structure$ByValue).isAssignableFrom(clazz)) {
                return (CallbackReference.class$com$sun$jna$Pointer == null) ? (CallbackReference.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : CallbackReference.class$com$sun$jna$Pointer;
            }
        }
        else {
            if (((CallbackReference.class$com$sun$jna$NativeMapped == null) ? (CallbackReference.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : CallbackReference.class$com$sun$jna$NativeMapped).isAssignableFrom(clazz)) {
                return NativeMappedConverter.getInstance(clazz).nativeType();
            }
            if (clazz == ((CallbackReference.class$java$lang$String == null) ? (CallbackReference.class$java$lang$String = class$("java.lang.String")) : CallbackReference.class$java$lang$String) || clazz == ((CallbackReference.class$com$sun$jna$WString == null) ? (CallbackReference.class$com$sun$jna$WString = class$("com.sun.jna.WString")) : CallbackReference.class$com$sun$jna$WString) || clazz == ((CallbackReference.array$Ljava$lang$String == null) ? (CallbackReference.array$Ljava$lang$String = class$("[Ljava.lang.String;")) : CallbackReference.array$Ljava$lang$String) || clazz == ((CallbackReference.array$Lcom$sun$jna$WString == null) ? (CallbackReference.array$Lcom$sun$jna$WString = class$("[Lcom.sun.jna.WString;")) : CallbackReference.array$Lcom$sun$jna$WString) || ((CallbackReference.class$com$sun$jna$Callback == null) ? (CallbackReference.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : CallbackReference.class$com$sun$jna$Callback).isAssignableFrom(clazz)) {
                return (CallbackReference.class$com$sun$jna$Pointer == null) ? (CallbackReference.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : CallbackReference.class$com$sun$jna$Pointer;
            }
        }
        return clazz;
    }
    
    private static Method checkMethod(final Method method) {
        if (method.getParameterTypes().length > 256) {
            throw new UnsupportedOperationException("Method signature exceeds the maximum parameter count: " + method);
        }
        return method;
    }
    
    static Class findCallbackClass(final Class clazz) {
        if (!((CallbackReference.class$com$sun$jna$Callback == null) ? (CallbackReference.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : CallbackReference.class$com$sun$jna$Callback).isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(clazz.getName() + " is not derived from com.sun.jna.Callback");
        }
        if (clazz.isInterface()) {
            return clazz;
        }
        final Class[] interfaces = clazz.getInterfaces();
        while (0 < interfaces.length) {
            if (((CallbackReference.class$com$sun$jna$Callback == null) ? (CallbackReference.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : CallbackReference.class$com$sun$jna$Callback).isAssignableFrom(interfaces[0])) {
                getCallbackMethod(interfaces[0]);
                return interfaces[0];
            }
            int n = 0;
            ++n;
        }
        if (((CallbackReference.class$com$sun$jna$Callback == null) ? (CallbackReference.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : CallbackReference.class$com$sun$jna$Callback).isAssignableFrom(clazz.getSuperclass())) {
            return findCallbackClass(clazz.getSuperclass());
        }
        return clazz;
    }
    
    private static Method getCallbackMethod(final Callback callback) {
        return getCallbackMethod(findCallbackClass(callback.getClass()));
    }
    
    private static Method getCallbackMethod(final Class p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/lang/Class.getDeclaredMethods:()[Ljava/lang/reflect/Method;
        //     4: astore_1       
        //     5: aload_0        
        //     6: invokevirtual   java/lang/Class.getMethods:()[Ljava/lang/reflect/Method;
        //     9: astore_2       
        //    10: new             Ljava/util/HashSet;
        //    13: dup            
        //    14: aload_1        
        //    15: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //    18: invokespecial   java/util/HashSet.<init>:(Ljava/util/Collection;)V
        //    21: astore_3       
        //    22: aload_3        
        //    23: aload_2        
        //    24: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //    27: invokeinterface java/util/Set.retainAll:(Ljava/util/Collection;)Z
        //    32: pop            
        //    33: aload_3        
        //    34: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    39: astore          4
        //    41: aload           4
        //    43: invokeinterface java/util/Iterator.hasNext:()Z
        //    48: ifeq            89
        //    51: aload           4
        //    53: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    58: checkcast       Ljava/lang/reflect/Method;
        //    61: astore          5
        //    63: getstatic       com/sun/jna/Callback.FORBIDDEN_NAMES:Ljava/util/Collection;
        //    66: aload           5
        //    68: invokevirtual   java/lang/reflect/Method.getName:()Ljava/lang/String;
        //    71: invokeinterface java/util/Collection.contains:(Ljava/lang/Object;)Z
        //    76: ifeq            86
        //    79: aload           4
        //    81: invokeinterface java/util/Iterator.remove:()V
        //    86: goto            41
        //    89: aload_3        
        //    90: aload_3        
        //    91: invokeinterface java/util/Set.size:()I
        //    96: anewarray       Ljava/lang/reflect/Method;
        //    99: invokeinterface java/util/Set.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   104: checkcast       [Ljava/lang/reflect/Method;
        //   107: checkcast       [Ljava/lang/reflect/Method;
        //   110: astore          4
        //   112: aload           4
        //   114: arraylength    
        //   115: iconst_1       
        //   116: if_icmpne       127
        //   119: aload           4
        //   121: iconst_0       
        //   122: aaload         
        //   123: invokestatic    com/sun/jna/CallbackReference.checkMethod:(Ljava/lang/reflect/Method;)Ljava/lang/reflect/Method;
        //   126: areturn        
        //   127: iconst_0       
        //   128: aload           4
        //   130: arraylength    
        //   131: if_icmpge       166
        //   134: aload           4
        //   136: iconst_0       
        //   137: aaload         
        //   138: astore          6
        //   140: ldc_w           "callback"
        //   143: aload           6
        //   145: invokevirtual   java/lang/reflect/Method.getName:()Ljava/lang/String;
        //   148: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   151: ifeq            160
        //   154: aload           6
        //   156: invokestatic    com/sun/jna/CallbackReference.checkMethod:(Ljava/lang/reflect/Method;)Ljava/lang/reflect/Method;
        //   159: areturn        
        //   160: iinc            5, 1
        //   163: goto            127
        //   166: ldc_w           "Callback must implement a single public method, or one public method named 'callback'"
        //   169: astore          5
        //   171: new             Ljava/lang/IllegalArgumentException;
        //   174: dup            
        //   175: aload           5
        //   177: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //   180: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void setCallbackOptions(final int n) {
        this.cbstruct.setInt(Pointer.SIZE, n);
    }
    
    public Pointer getTrampoline() {
        return this.cbstruct.getPointer(0L);
    }
    
    protected void finalize() {
        this.dispose();
    }
    
    protected synchronized void dispose() {
        if (this.cbstruct != null) {
            Native.freeNativeCallback(this.cbstruct.peer);
            this.cbstruct.peer = 0L;
            this.cbstruct = null;
        }
    }
    
    private Callback getCallback() {
        return this.get();
    }
    
    private static Pointer getNativeFunctionPointer(final Callback callback) {
        if (Proxy.isProxyClass(callback.getClass())) {
            final InvocationHandler invocationHandler = Proxy.getInvocationHandler(callback);
            if (invocationHandler instanceof NativeFunctionHandler) {
                return ((NativeFunctionHandler)invocationHandler).getPointer();
            }
        }
        return null;
    }
    
    public static Pointer getFunctionPointer(final Callback callback) {
        return getFunctionPointer(callback, false);
    }
    
    private static Pointer getFunctionPointer(final Callback callback, final boolean b) {
        if (callback == null) {
            return null;
        }
        final Pointer nativeFunctionPointer;
        if ((nativeFunctionPointer = getNativeFunctionPointer(callback)) != null) {
            return nativeFunctionPointer;
        }
        final boolean b2 = callback instanceof AltCallingConvention;
        final Map callbackMap = CallbackReference.callbackMap;
        // monitorenter(map = callbackMap)
        CallbackReference callbackReference = callbackMap.get(callback);
        if (callbackReference == null) {
            callbackReference = new CallbackReference(callback, b2 ? 1 : 0, b);
            callbackMap.put(callback, callbackReference);
            if (CallbackReference.initializers.containsKey(callback)) {
                callbackReference.setCallbackOptions(1);
            }
        }
        // monitorexit(map)
        return callbackReference.getTrampoline();
    }
    
    private static boolean isAllowableNativeType(final Class clazz) {
        return clazz == Void.TYPE || (clazz == ((CallbackReference.class$java$lang$Void == null) ? (CallbackReference.class$java$lang$Void = class$("java.lang.Void")) : CallbackReference.class$java$lang$Void) || clazz == Boolean.TYPE) || (clazz == ((CallbackReference.class$java$lang$Boolean == null) ? (CallbackReference.class$java$lang$Boolean = class$("java.lang.Boolean")) : CallbackReference.class$java$lang$Boolean) || clazz == Byte.TYPE) || (clazz == ((CallbackReference.class$java$lang$Byte == null) ? (CallbackReference.class$java$lang$Byte = class$("java.lang.Byte")) : CallbackReference.class$java$lang$Byte) || clazz == Short.TYPE) || (clazz == ((CallbackReference.class$java$lang$Short == null) ? (CallbackReference.class$java$lang$Short = class$("java.lang.Short")) : CallbackReference.class$java$lang$Short) || clazz == Character.TYPE) || (clazz == ((CallbackReference.class$java$lang$Character == null) ? (CallbackReference.class$java$lang$Character = class$("java.lang.Character")) : CallbackReference.class$java$lang$Character) || clazz == Integer.TYPE) || (clazz == ((CallbackReference.class$java$lang$Integer == null) ? (CallbackReference.class$java$lang$Integer = class$("java.lang.Integer")) : CallbackReference.class$java$lang$Integer) || clazz == Long.TYPE) || (clazz == ((CallbackReference.class$java$lang$Long == null) ? (CallbackReference.class$java$lang$Long = class$("java.lang.Long")) : CallbackReference.class$java$lang$Long) || clazz == Float.TYPE) || (clazz == ((CallbackReference.class$java$lang$Float == null) ? (CallbackReference.class$java$lang$Float = class$("java.lang.Float")) : CallbackReference.class$java$lang$Float) || clazz == Double.TYPE) || clazz == ((CallbackReference.class$java$lang$Double == null) ? (CallbackReference.class$java$lang$Double = class$("java.lang.Double")) : CallbackReference.class$java$lang$Double) || (((CallbackReference.class$com$sun$jna$Structure$ByValue == null) ? (CallbackReference.class$com$sun$jna$Structure$ByValue = class$("com.sun.jna.Structure$ByValue")) : CallbackReference.class$com$sun$jna$Structure$ByValue).isAssignableFrom(clazz) && ((CallbackReference.class$com$sun$jna$Structure == null) ? (CallbackReference.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : CallbackReference.class$com$sun$jna$Structure).isAssignableFrom(clazz)) || ((CallbackReference.class$com$sun$jna$Pointer == null) ? (CallbackReference.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : CallbackReference.class$com$sun$jna$Pointer).isAssignableFrom(clazz);
    }
    
    private static Pointer getNativeString(final Object o, final boolean b) {
        if (o != null) {
            final NativeString nativeString = new NativeString(o.toString(), b);
            CallbackReference.allocations.put(o, nativeString);
            return nativeString.getPointer();
        }
        return null;
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static Callback access$000(final CallbackReference callbackReference) {
        return callbackReference.getCallback();
    }
    
    static Pointer access$100(final Object o, final boolean b) {
        return getNativeString(o, b);
    }
    
    static {
        callbackMap = new WeakHashMap();
        allocations = new WeakHashMap();
        PROXY_CALLBACK_METHOD = ((CallbackReference.class$com$sun$jna$CallbackProxy == null) ? (CallbackReference.class$com$sun$jna$CallbackProxy = class$("com.sun.jna.CallbackProxy")) : CallbackReference.class$com$sun$jna$CallbackProxy).getMethod("callback", (CallbackReference.array$Ljava$lang$Object == null) ? (CallbackReference.array$Ljava$lang$Object = class$("[Ljava.lang.Object;")) : CallbackReference.array$Ljava$lang$Object);
        initializers = new WeakHashMap();
    }
    
    private static class NativeFunctionHandler implements InvocationHandler
    {
        private Function function;
        private Map options;
        
        public NativeFunctionHandler(final Pointer pointer, final int n, final Map options) {
            this.function = new Function(pointer, n);
            this.options = options;
        }
        
        public Object invoke(final Object o, final Method method, Object[] concatenateVarArgs) throws Throwable {
            if (Library.Handler.OBJECT_TOSTRING.equals(method)) {
                return "Proxy interface to " + this.function + " (" + CallbackReference.findCallbackClass(this.options.get("invoking-method").getDeclaringClass()).getName() + ")";
            }
            if (Library.Handler.OBJECT_HASHCODE.equals(method)) {
                return new Integer(this.hashCode());
            }
            if (!Library.Handler.OBJECT_EQUALS.equals(method)) {
                if (Function.isVarArgs(method)) {
                    concatenateVarArgs = Function.concatenateVarArgs(concatenateVarArgs);
                }
                return this.function.invoke(method.getReturnType(), concatenateVarArgs, this.options);
            }
            final Object o2 = concatenateVarArgs[0];
            if (o2 != null && Proxy.isProxyClass(o2.getClass())) {
                return Function.valueOf(Proxy.getInvocationHandler(o2) == this);
            }
            return Boolean.FALSE;
        }
        
        public Pointer getPointer() {
            return this.function;
        }
    }
    
    private class DefaultCallbackProxy implements CallbackProxy
    {
        private Method callbackMethod;
        private ToNativeConverter toNative;
        private FromNativeConverter[] fromNative;
        private final CallbackReference this$0;
        
        public DefaultCallbackProxy(final CallbackReference this$0, final Method callbackMethod, final TypeMapper typeMapper) {
            this.this$0 = this$0;
            this.callbackMethod = callbackMethod;
            final Class<?>[] parameterTypes = callbackMethod.getParameterTypes();
            final Class<?> returnType = callbackMethod.getReturnType();
            this.fromNative = new FromNativeConverter[parameterTypes.length];
            if (((CallbackReference.class$com$sun$jna$NativeMapped == null) ? (CallbackReference.class$com$sun$jna$NativeMapped = CallbackReference.class$("com.sun.jna.NativeMapped")) : CallbackReference.class$com$sun$jna$NativeMapped).isAssignableFrom(returnType)) {
                this.toNative = NativeMappedConverter.getInstance(returnType);
            }
            else if (typeMapper != null) {
                this.toNative = typeMapper.getToNativeConverter(returnType);
            }
            while (0 < this.fromNative.length) {
                if (((CallbackReference.class$com$sun$jna$NativeMapped == null) ? (CallbackReference.class$com$sun$jna$NativeMapped = CallbackReference.class$("com.sun.jna.NativeMapped")) : CallbackReference.class$com$sun$jna$NativeMapped).isAssignableFrom(parameterTypes[0])) {
                    this.fromNative[0] = new NativeMappedConverter(parameterTypes[0]);
                }
                else if (typeMapper != null) {
                    this.fromNative[0] = typeMapper.getFromNativeConverter(parameterTypes[0]);
                }
                int n = 0;
                ++n;
            }
            if (!callbackMethod.isAccessible()) {
                callbackMethod.setAccessible(true);
            }
        }
        
        public Callback getCallback() {
            return CallbackReference.access$000(this.this$0);
        }
        
        private Object invokeCallback(final Object[] p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: getfield        com/sun/jna/CallbackReference$DefaultCallbackProxy.callbackMethod:Ljava/lang/reflect/Method;
            //     4: invokevirtual   java/lang/reflect/Method.getParameterTypes:()[Ljava/lang/Class;
            //     7: astore_2       
            //     8: aload_1        
            //     9: arraylength    
            //    10: anewarray       Ljava/lang/Object;
            //    13: astore_3       
            //    14: iconst_0       
            //    15: aload_1        
            //    16: arraylength    
            //    17: if_icmpge       94
            //    20: aload_2        
            //    21: iconst_0       
            //    22: aaload         
            //    23: astore          5
            //    25: aload_1        
            //    26: iconst_0       
            //    27: aaload         
            //    28: astore          6
            //    30: aload_0        
            //    31: getfield        com/sun/jna/CallbackReference$DefaultCallbackProxy.fromNative:[Lcom/sun/jna/FromNativeConverter;
            //    34: iconst_0       
            //    35: aaload         
            //    36: ifnull          77
            //    39: new             Lcom/sun/jna/CallbackParameterContext;
            //    42: dup            
            //    43: aload           5
            //    45: aload_0        
            //    46: getfield        com/sun/jna/CallbackReference$DefaultCallbackProxy.callbackMethod:Ljava/lang/reflect/Method;
            //    49: aload_1        
            //    50: iconst_0       
            //    51: invokespecial   com/sun/jna/CallbackParameterContext.<init>:(Ljava/lang/Class;Ljava/lang/reflect/Method;[Ljava/lang/Object;I)V
            //    54: astore          7
            //    56: aload_3        
            //    57: iconst_0       
            //    58: aload_0        
            //    59: getfield        com/sun/jna/CallbackReference$DefaultCallbackProxy.fromNative:[Lcom/sun/jna/FromNativeConverter;
            //    62: iconst_0       
            //    63: aaload         
            //    64: aload           6
            //    66: aload           7
            //    68: invokeinterface com/sun/jna/FromNativeConverter.fromNative:(Ljava/lang/Object;Lcom/sun/jna/FromNativeContext;)Ljava/lang/Object;
            //    73: aastore        
            //    74: goto            88
            //    77: aload_3        
            //    78: iconst_0       
            //    79: aload_0        
            //    80: aload           6
            //    82: aload           5
            //    84: invokespecial   com/sun/jna/CallbackReference$DefaultCallbackProxy.convertArgument:(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
            //    87: aastore        
            //    88: iinc            4, 1
            //    91: goto            14
            //    94: aconst_null    
            //    95: astore          4
            //    97: aload_0        
            //    98: invokevirtual   com/sun/jna/CallbackReference$DefaultCallbackProxy.getCallback:()Lcom/sun/jna/Callback;
            //   101: astore          5
            //   103: aload           5
            //   105: ifnull          178
            //   108: aload_0        
            //   109: aload_0        
            //   110: getfield        com/sun/jna/CallbackReference$DefaultCallbackProxy.callbackMethod:Ljava/lang/reflect/Method;
            //   113: aload           5
            //   115: aload_3        
            //   116: invokevirtual   java/lang/reflect/Method.invoke:(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
            //   119: invokespecial   com/sun/jna/CallbackReference$DefaultCallbackProxy.convertResult:(Ljava/lang/Object;)Ljava/lang/Object;
            //   122: astore          4
            //   124: goto            178
            //   127: astore          6
            //   129: invokestatic    com/sun/jna/Native.getCallbackExceptionHandler:()Lcom/sun/jna/Callback$UncaughtExceptionHandler;
            //   132: aload           5
            //   134: aload           6
            //   136: invokeinterface com/sun/jna/Callback$UncaughtExceptionHandler.uncaughtException:(Lcom/sun/jna/Callback;Ljava/lang/Throwable;)V
            //   141: goto            178
            //   144: astore          6
            //   146: invokestatic    com/sun/jna/Native.getCallbackExceptionHandler:()Lcom/sun/jna/Callback$UncaughtExceptionHandler;
            //   149: aload           5
            //   151: aload           6
            //   153: invokeinterface com/sun/jna/Callback$UncaughtExceptionHandler.uncaughtException:(Lcom/sun/jna/Callback;Ljava/lang/Throwable;)V
            //   158: goto            178
            //   161: astore          6
            //   163: invokestatic    com/sun/jna/Native.getCallbackExceptionHandler:()Lcom/sun/jna/Callback$UncaughtExceptionHandler;
            //   166: aload           5
            //   168: aload           6
            //   170: invokevirtual   java/lang/reflect/InvocationTargetException.getTargetException:()Ljava/lang/Throwable;
            //   173: invokeinterface com/sun/jna/Callback$UncaughtExceptionHandler.uncaughtException:(Lcom/sun/jna/Callback;Ljava/lang/Throwable;)V
            //   178: iconst_0       
            //   179: aload_3        
            //   180: arraylength    
            //   181: if_icmpge       217
            //   184: aload_3        
            //   185: iconst_0       
            //   186: aaload         
            //   187: instanceof      Lcom/sun/jna/Structure;
            //   190: ifeq            211
            //   193: aload_3        
            //   194: iconst_0       
            //   195: aaload         
            //   196: instanceof      Lcom/sun/jna/Structure$ByValue;
            //   199: ifne            211
            //   202: aload_3        
            //   203: iconst_0       
            //   204: aaload         
            //   205: checkcast       Lcom/sun/jna/Structure;
            //   208: invokevirtual   com/sun/jna/Structure.autoWrite:()V
            //   211: iinc            6, 1
            //   214: goto            178
            //   217: aload           4
            //   219: areturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public Object callback(final Object[] array) {
            return this.invokeCallback(array);
        }
        
        private Object convertArgument(Object o, final Class clazz) {
            if (o instanceof Pointer) {
                if (clazz == ((CallbackReference.class$java$lang$String == null) ? (CallbackReference.class$java$lang$String = CallbackReference.class$("java.lang.String")) : CallbackReference.class$java$lang$String)) {
                    o = ((Pointer)o).getString(0L);
                }
                else if (clazz == ((CallbackReference.class$com$sun$jna$WString == null) ? (CallbackReference.class$com$sun$jna$WString = CallbackReference.class$("com.sun.jna.WString")) : CallbackReference.class$com$sun$jna$WString)) {
                    o = new WString(((Pointer)o).getString(0L, true));
                }
                else if (clazz == ((CallbackReference.array$Ljava$lang$String == null) ? (CallbackReference.array$Ljava$lang$String = CallbackReference.class$("[Ljava.lang.String;")) : CallbackReference.array$Ljava$lang$String) || clazz == ((CallbackReference.array$Lcom$sun$jna$WString == null) ? (CallbackReference.array$Lcom$sun$jna$WString = CallbackReference.class$("[Lcom.sun.jna.WString;")) : CallbackReference.array$Lcom$sun$jna$WString)) {
                    o = ((Pointer)o).getStringArray(0L, clazz == ((CallbackReference.array$Lcom$sun$jna$WString == null) ? (CallbackReference.array$Lcom$sun$jna$WString = CallbackReference.class$("[Lcom.sun.jna.WString;")) : CallbackReference.array$Lcom$sun$jna$WString));
                }
                else if (((CallbackReference.class$com$sun$jna$Callback == null) ? (CallbackReference.class$com$sun$jna$Callback = CallbackReference.class$("com.sun.jna.Callback")) : CallbackReference.class$com$sun$jna$Callback).isAssignableFrom(clazz)) {
                    final CallbackReference this$0 = this.this$0;
                    o = CallbackReference.getCallback(clazz, (Pointer)o);
                }
                else if (((CallbackReference.class$com$sun$jna$Structure == null) ? (CallbackReference.class$com$sun$jna$Structure = CallbackReference.class$("com.sun.jna.Structure")) : CallbackReference.class$com$sun$jna$Structure).isAssignableFrom(clazz)) {
                    final Structure instance = Structure.newInstance(clazz);
                    if (((CallbackReference.class$com$sun$jna$Structure$ByValue == null) ? (CallbackReference.class$com$sun$jna$Structure$ByValue = CallbackReference.class$("com.sun.jna.Structure$ByValue")) : CallbackReference.class$com$sun$jna$Structure$ByValue).isAssignableFrom(clazz)) {
                        final byte[] array = new byte[instance.size()];
                        ((Pointer)o).read(0L, array, 0, array.length);
                        instance.getPointer().write(0L, array, 0, array.length);
                    }
                    else {
                        instance.useMemory((Pointer)o);
                    }
                    instance.read();
                    o = instance;
                }
            }
            else if ((Boolean.TYPE == clazz || ((CallbackReference.class$java$lang$Boolean == null) ? (CallbackReference.class$java$lang$Boolean = CallbackReference.class$("java.lang.Boolean")) : CallbackReference.class$java$lang$Boolean) == clazz) && o instanceof Number) {
                o = Function.valueOf(((Number)o).intValue() != 0);
            }
            return o;
        }
        
        private Object convertResult(Object native1) {
            if (this.toNative != null) {
                native1 = this.toNative.toNative(native1, new CallbackResultContext(this.callbackMethod));
            }
            if (native1 == null) {
                return null;
            }
            final Class<?> class1 = native1.getClass();
            if (((CallbackReference.class$com$sun$jna$Structure == null) ? (CallbackReference.class$com$sun$jna$Structure = CallbackReference.class$("com.sun.jna.Structure")) : CallbackReference.class$com$sun$jna$Structure).isAssignableFrom(class1)) {
                if (((CallbackReference.class$com$sun$jna$Structure$ByValue == null) ? (CallbackReference.class$com$sun$jna$Structure$ByValue = CallbackReference.class$("com.sun.jna.Structure$ByValue")) : CallbackReference.class$com$sun$jna$Structure$ByValue).isAssignableFrom(class1)) {
                    return native1;
                }
                return ((Structure)native1).getPointer();
            }
            else {
                if (class1 == Boolean.TYPE || class1 == ((CallbackReference.class$java$lang$Boolean == null) ? (CallbackReference.class$java$lang$Boolean = CallbackReference.class$("java.lang.Boolean")) : CallbackReference.class$java$lang$Boolean)) {
                    return Boolean.TRUE.equals(native1) ? Function.INTEGER_TRUE : Function.INTEGER_FALSE;
                }
                if (class1 == ((CallbackReference.class$java$lang$String == null) ? (CallbackReference.class$java$lang$String = CallbackReference.class$("java.lang.String")) : CallbackReference.class$java$lang$String) || class1 == ((CallbackReference.class$com$sun$jna$WString == null) ? (CallbackReference.class$com$sun$jna$WString = CallbackReference.class$("com.sun.jna.WString")) : CallbackReference.class$com$sun$jna$WString)) {
                    return CallbackReference.access$100(native1, class1 == ((CallbackReference.class$com$sun$jna$WString == null) ? (CallbackReference.class$com$sun$jna$WString = CallbackReference.class$("com.sun.jna.WString")) : CallbackReference.class$com$sun$jna$WString));
                }
                if (class1 == ((CallbackReference.array$Ljava$lang$String == null) ? (CallbackReference.array$Ljava$lang$String = CallbackReference.class$("[Ljava.lang.String;")) : CallbackReference.array$Ljava$lang$String) || class1 == ((CallbackReference.class$com$sun$jna$WString == null) ? (CallbackReference.class$com$sun$jna$WString = CallbackReference.class$("com.sun.jna.WString")) : CallbackReference.class$com$sun$jna$WString)) {
                    final StringArray stringArray = (class1 == ((CallbackReference.array$Ljava$lang$String == null) ? (CallbackReference.array$Ljava$lang$String = CallbackReference.class$("[Ljava.lang.String;")) : CallbackReference.array$Ljava$lang$String)) ? new StringArray((String[])native1) : new StringArray((WString[])native1);
                    CallbackReference.allocations.put(native1, stringArray);
                    return stringArray;
                }
                if (((CallbackReference.class$com$sun$jna$Callback == null) ? (CallbackReference.class$com$sun$jna$Callback = CallbackReference.class$("com.sun.jna.Callback")) : CallbackReference.class$com$sun$jna$Callback).isAssignableFrom(class1)) {
                    return CallbackReference.getFunctionPointer((Callback)native1);
                }
                return native1;
            }
        }
        
        public Class[] getParameterTypes() {
            return this.callbackMethod.getParameterTypes();
        }
        
        public Class getReturnType() {
            return this.callbackMethod.getReturnType();
        }
    }
    
    static class AttachOptions extends Structure
    {
        public boolean daemon;
        public boolean detach;
        public String name;
    }
}
