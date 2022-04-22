package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.nio.charset.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.core.helpers.*;
import java.io.*;

@Plugin(name = "Console", category = "Core", elementType = "appender", printObject = true)
public final class ConsoleAppender extends AbstractOutputStreamAppender
{
    private static final String JANSI_CLASS = "org.fusesource.jansi.WindowsAnsiOutputStream";
    private static ConsoleManagerFactory factory;
    
    private ConsoleAppender(final String s, final Layout layout, final Filter filter, final OutputStreamManager outputStreamManager, final boolean b) {
        super(s, layout, filter, b, true, outputStreamManager);
    }
    
    @PluginFactory
    public static ConsoleAppender createAppender(@PluginElement("Layout") Layout layout, @PluginElement("Filters") final Filter filter, @PluginAttribute("target") final String s, @PluginAttribute("name") final String s2, @PluginAttribute("follow") final String s3, @PluginAttribute("ignoreExceptions") final String s4) {
        if (s2 == null) {
            ConsoleAppender.LOGGER.error("No name provided for ConsoleAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createLayout(null, null, null, null, null);
        }
        return new ConsoleAppender(s2, layout, filter, getManager(Boolean.parseBoolean(s3), (s == null) ? Target.SYSTEM_OUT : Target.valueOf(s), layout), Booleans.parseBoolean(s4, true));
    }
    
    private static OutputStreamManager getManager(final boolean b, final Target target, final Layout layout) {
        return OutputStreamManager.getManager(target.name() + "." + b, new FactoryData(getOutputStream(b, target), target.name(), layout), ConsoleAppender.factory);
    }
    
    private static OutputStream getOutputStream(final boolean b, final Target target) {
        final String name = Charset.defaultCharset().name();
        final PrintStream printStream = (target == Target.SYSTEM_OUT) ? (b ? new PrintStream(new SystemOutStream(), true, name) : System.out) : (b ? new PrintStream(new SystemErrStream(), true, name) : System.err);
        final PropertiesUtil properties = PropertiesUtil.getProperties();
        if (!properties.getStringProperty("os.name").startsWith("Windows") || properties.getBooleanProperty("log4j.skipJansi")) {
            return printStream;
        }
        return (OutputStream)Loader.getClassLoader().loadClass("org.fusesource.jansi.WindowsAnsiOutputStream").getConstructor(OutputStream.class).newInstance(printStream);
    }
    
    static {
        ConsoleAppender.factory = new ConsoleManagerFactory(null);
    }
    
    private static class ConsoleManagerFactory implements ManagerFactory
    {
        private ConsoleManagerFactory() {
        }
        
        public OutputStreamManager createManager(final String s, final FactoryData factoryData) {
            return new OutputStreamManager(FactoryData.access$100(factoryData), FactoryData.access$200(factoryData), FactoryData.access$300(factoryData));
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        ConsoleManagerFactory(final ConsoleAppender$1 object) {
            this();
        }
    }
    
    private static class FactoryData
    {
        private final OutputStream os;
        private final String type;
        private final Layout layout;
        
        public FactoryData(final OutputStream os, final String type, final Layout layout) {
            this.os = os;
            this.type = type;
            this.layout = layout;
        }
        
        static OutputStream access$100(final FactoryData factoryData) {
            return factoryData.os;
        }
        
        static String access$200(final FactoryData factoryData) {
            return factoryData.type;
        }
        
        static Layout access$300(final FactoryData factoryData) {
            return factoryData.layout;
        }
    }
    
    private static class SystemOutStream extends OutputStream
    {
        public SystemOutStream() {
        }
        
        @Override
        public void close() {
        }
        
        @Override
        public void flush() {
            System.out.flush();
        }
        
        @Override
        public void write(final byte[] array) throws IOException {
            System.out.write(array);
        }
        
        @Override
        public void write(final byte[] array, final int n, final int n2) throws IOException {
            System.out.write(array, n, n2);
        }
        
        @Override
        public void write(final int n) throws IOException {
            System.out.write(n);
        }
    }
    
    private static class SystemErrStream extends OutputStream
    {
        public SystemErrStream() {
        }
        
        @Override
        public void close() {
        }
        
        @Override
        public void flush() {
            System.err.flush();
        }
        
        @Override
        public void write(final byte[] array) throws IOException {
            System.err.write(array);
        }
        
        @Override
        public void write(final byte[] array, final int n, final int n2) throws IOException {
            System.err.write(array, n, n2);
        }
        
        @Override
        public void write(final int n) {
            System.err.write(n);
        }
    }
    
    public enum Target
    {
        SYSTEM_OUT("SYSTEM_OUT", 0), 
        SYSTEM_ERR("SYSTEM_ERR", 1);
        
        private static final Target[] $VALUES;
        
        private Target(final String s, final int n) {
        }
        
        static {
            $VALUES = new Target[] { Target.SYSTEM_OUT, Target.SYSTEM_ERR };
        }
    }
}
