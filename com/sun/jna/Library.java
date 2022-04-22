package com.sun.jna;

import java.util.*;
import java.lang.reflect.*;

public interface Library
{
    public static final String OPTION_TYPE_MAPPER = "type-mapper";
    public static final String OPTION_FUNCTION_MAPPER = "function-mapper";
    public static final String OPTION_INVOCATION_MAPPER = "invocation-mapper";
    public static final String OPTION_STRUCTURE_ALIGNMENT = "structure-alignment";
    public static final String OPTION_ALLOW_OBJECTS = "allow-objects";
    public static final String OPTION_CALLING_CONVENTION = "calling-convention";
    
    public static class Handler implements InvocationHandler
    {
        static final Method OBJECT_TOSTRING;
        static final Method OBJECT_HASHCODE;
        static final Method OBJECT_EQUALS;
        private final NativeLibrary nativeLibrary;
        private final Class interfaceClass;
        private final Map options;
        private FunctionMapper functionMapper;
        private final InvocationMapper invocationMapper;
        private final Map functions;
        
        public Handler(final String s, final Class interfaceClass, final Map map) {
            this.functions = new WeakHashMap();
            if (s != null && "".equals(s.trim())) {
                throw new IllegalArgumentException("Invalid library name \"" + s + "\"");
            }
            this.interfaceClass = interfaceClass;
            final HashMap<String, InvocationMapper> options = new HashMap<String, InvocationMapper>(map);
            final boolean assignable = ((Library$1.class$com$sun$jna$AltCallingConvention == null) ? (Library$1.class$com$sun$jna$AltCallingConvention = Library$1.class$("com.sun.jna.AltCallingConvention")) : Library$1.class$com$sun$jna$AltCallingConvention).isAssignableFrom(interfaceClass);
            if (options.get("calling-convention") == null) {
                options.put("calling-convention", (InvocationMapper)new Integer(assignable ? 1 : 0));
            }
            this.options = options;
            this.nativeLibrary = NativeLibrary.getInstance(s, options);
            this.functionMapper = (FunctionMapper)options.get("function-mapper");
            if (this.functionMapper == null) {
                this.functionMapper = new FunctionNameMap(options);
            }
            this.invocationMapper = (InvocationMapper)options.get("invocation-mapper");
        }
        
        public NativeLibrary getNativeLibrary() {
            return this.nativeLibrary;
        }
        
        public String getLibraryName() {
            return this.nativeLibrary.getName();
        }
        
        public Class getInterfaceClass() {
            return this.interfaceClass;
        }
        
        public Object invoke(final Object o, final Method method, Object[] concatenateVarArgs) throws Throwable {
            if (Handler.OBJECT_TOSTRING.equals(method)) {
                return "Proxy interface to " + this.nativeLibrary;
            }
            if (Handler.OBJECT_HASHCODE.equals(method)) {
                return new Integer(this.hashCode());
            }
            if (Handler.OBJECT_EQUALS.equals(method)) {
                final Object o2 = concatenateVarArgs[0];
                if (o2 != null && Proxy.isProxyClass(o2.getClass())) {
                    return Function.valueOf(Proxy.getInvocationHandler(o2) == this);
                }
                return Boolean.FALSE;
            }
            else {
                // monitorenter(functions = this.functions)
                FunctionInfo functionInfo = this.functions.get(method);
                if (functionInfo == null) {
                    functionInfo = new FunctionInfo(null);
                    functionInfo.isVarArgs = Function.isVarArgs(method);
                    if (this.invocationMapper != null) {
                        functionInfo.handler = this.invocationMapper.getInvocationHandler(this.nativeLibrary, method);
                    }
                    if (functionInfo.handler == null) {
                        String s = this.functionMapper.getFunctionName(this.nativeLibrary, method);
                        if (s == null) {
                            s = method.getName();
                        }
                        functionInfo.function = this.nativeLibrary.getFunction(s, method);
                        (functionInfo.options = new HashMap(this.options)).put("invoking-method", method);
                    }
                    this.functions.put(method, functionInfo);
                }
                // monitorexit(functions)
                if (functionInfo.isVarArgs) {
                    concatenateVarArgs = Function.concatenateVarArgs(concatenateVarArgs);
                }
                if (functionInfo.handler != null) {
                    return functionInfo.handler.invoke(o, method, concatenateVarArgs);
                }
                return functionInfo.function.invoke(method.getReturnType(), concatenateVarArgs, functionInfo.options);
            }
        }
        
        static {
            OBJECT_TOSTRING = ((Library$1.class$java$lang$Object == null) ? (Library$1.class$java$lang$Object = Library$1.class$("java.lang.Object")) : Library$1.class$java$lang$Object).getMethod("toString", (Class[])new Class[0]);
            OBJECT_HASHCODE = ((Library$1.class$java$lang$Object == null) ? (Library$1.class$java$lang$Object = Library$1.class$("java.lang.Object")) : Library$1.class$java$lang$Object).getMethod("hashCode", (Class[])new Class[0]);
            OBJECT_EQUALS = ((Library$1.class$java$lang$Object == null) ? (Library$1.class$java$lang$Object = Library$1.class$("java.lang.Object")) : Library$1.class$java$lang$Object).getMethod("equals", (Library$1.class$java$lang$Object == null) ? (Library$1.class$java$lang$Object = Library$1.class$("java.lang.Object")) : Library$1.class$java$lang$Object);
        }
        
        private static class FunctionInfo
        {
            InvocationHandler handler;
            Function function;
            boolean isVarArgs;
            Map options;
            
            private FunctionInfo() {
            }
            
            FunctionInfo(final Library$1 object) {
                this();
            }
        }
        
        private static class FunctionNameMap implements FunctionMapper
        {
            private final Map map;
            
            public FunctionNameMap(final Map map) {
                this.map = new HashMap(map);
            }
            
            public String getFunctionName(final NativeLibrary nativeLibrary, final Method method) {
                final String name = method.getName();
                if (this.map.containsKey(name)) {
                    return (String)this.map.get(name);
                }
                return name;
            }
        }
    }
}
