package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.core.filter.*;
import org.apache.logging.log4j.core.net.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.lookup.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.helpers.*;
import java.util.*;
import org.apache.logging.log4j.status.*;

public class BaseConfiguration extends AbstractFilterable implements Configuration
{
    protected static final Logger LOGGER;
    protected Node rootNode;
    protected final List listeners;
    protected ConfigurationMonitor monitor;
    private Advertiser advertiser;
    protected Map advertisedConfiguration;
    private Node advertiserNode;
    private Object advertisement;
    protected boolean isShutdownHookEnabled;
    private String name;
    private ConcurrentMap appenders;
    private ConcurrentMap loggers;
    private final StrLookup tempLookup;
    private final StrSubstitutor subst;
    private LoggerConfig root;
    private final boolean started = false;
    private final ConcurrentMap componentMap;
    protected PluginManager pluginManager;
    
    protected BaseConfiguration() {
        this.listeners = new CopyOnWriteArrayList();
        this.monitor = new DefaultConfigurationMonitor();
        this.advertiser = new DefaultAdvertiser();
        this.advertiserNode = null;
        this.isShutdownHookEnabled = true;
        this.appenders = new ConcurrentHashMap();
        this.loggers = new ConcurrentHashMap();
        this.tempLookup = new Interpolator();
        this.subst = new StrSubstitutor(this.tempLookup);
        this.root = new LoggerConfig();
        this.componentMap = new ConcurrentHashMap();
        this.pluginManager = new PluginManager("Core");
        this.rootNode = new Node();
    }
    
    @Override
    public Map getProperties() {
        return (Map)this.componentMap.get("ContextProperties");
    }
    
    @Override
    public void start() {
        this.pluginManager.collectPlugins();
        this.setup();
        this.setupAdvertisement();
        this.doConfigure();
        final Iterator iterator = this.loggers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().startFilter();
        }
        final Iterator iterator2 = this.appenders.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().start();
        }
        this.root.startFilter();
        this.startFilter();
    }
    
    @Override
    public void stop() {
        final Appender[] array = (Appender[])this.appenders.values().toArray(new Appender[this.appenders.size()]);
        for (int i = array.length - 1; i >= 0; --i) {
            array[i].stop();
        }
        for (final LoggerConfig loggerConfig : this.loggers.values()) {
            loggerConfig.clearAppenders();
            loggerConfig.stopFilter();
        }
        this.root.stopFilter();
        this.stopFilter();
        if (this.advertiser != null && this.advertisement != null) {
            this.advertiser.unadvertise(this.advertisement);
        }
    }
    
    @Override
    public boolean isShutdownHookEnabled() {
        return this.isShutdownHookEnabled;
    }
    
    protected void setup() {
    }
    
    protected Level getDefaultStatus() {
        return Level.toLevel(PropertiesUtil.getProperties().getStringProperty("Log4jDefaultStatusLevel", Level.ERROR.name()));
    }
    
    protected void createAdvertiser(final String s, final ConfigurationFactory.ConfigurationSource configurationSource, final byte[] array, final String s2) {
        if (s != null) {
            final Node advertiserNode = new Node(null, s, null);
            final Map attributes = advertiserNode.getAttributes();
            attributes.put("content", new String(array));
            attributes.put("contentType", s2);
            attributes.put("name", "configuration");
            if (configurationSource.getLocation() != null) {
                attributes.put("location", configurationSource.getLocation());
            }
            this.advertiserNode = advertiserNode;
        }
    }
    
    private void setupAdvertisement() {
        if (this.advertiserNode != null) {
            final PluginType pluginType = this.pluginManager.getPluginType(this.advertiserNode.getName());
            if (pluginType != null) {
                this.advertiser = (Advertiser)pluginType.getPluginClass().newInstance();
                this.advertisement = this.advertiser.advertise(this.advertiserNode.getAttributes());
            }
        }
    }
    
    @Override
    public Object getComponent(final String s) {
        return this.componentMap.get(s);
    }
    
    @Override
    public void addComponent(final String s, final Object o) {
        this.componentMap.putIfAbsent(s, o);
    }
    
    protected void doConfigure() {
        for (final Node node : this.rootNode.getChildren()) {
            this.createConfiguration(node, null);
            if (node.getObject() == null) {
                continue;
            }
            if (node.getName().equalsIgnoreCase("Properties")) {
                if (this.tempLookup == this.subst.getVariableResolver()) {
                    this.subst.setVariableResolver((StrLookup)node.getObject());
                }
                else {
                    BaseConfiguration.LOGGER.error("Properties declaration must be the first element in the configuration");
                }
            }
            else {
                if (this.tempLookup == this.subst.getVariableResolver()) {
                    final Map map = (Map)this.componentMap.get("ContextProperties");
                    this.subst.setVariableResolver(new Interpolator((map == null) ? null : new MapLookup(map)));
                }
                if (node.getName().equalsIgnoreCase("Appenders")) {
                    this.appenders = (ConcurrentMap)node.getObject();
                }
                else if (node.getObject() instanceof Filter) {
                    this.addFilter((Filter)node.getObject());
                }
                else if (node.getName().equalsIgnoreCase("Loggers")) {
                    final Loggers loggers = (Loggers)node.getObject();
                    this.loggers = loggers.getMap();
                    if (loggers.getRoot() == null) {
                        continue;
                    }
                    this.root = loggers.getRoot();
                }
                else {
                    BaseConfiguration.LOGGER.error("Unknown object \"" + node.getName() + "\" of type " + node.getObject().getClass().getName() + " is ignored");
                }
            }
        }
        if (!true) {
            BaseConfiguration.LOGGER.warn("No Loggers were configured, using default. Is the Loggers element missing?");
            this.setToDefault();
            return;
        }
        if (!true) {
            BaseConfiguration.LOGGER.warn("No Root logger was configured, creating default ERROR-level Root logger with Console appender");
            this.setToDefault();
        }
        final Iterator iterator2 = this.loggers.entrySet().iterator();
        while (iterator2.hasNext()) {
            final LoggerConfig loggerConfig = iterator2.next().getValue();
            for (final AppenderRef appenderRef : loggerConfig.getAppenderRefs()) {
                final Appender appender = (Appender)this.appenders.get(appenderRef.getRef());
                if (appender != null) {
                    loggerConfig.addAppender(appender, appenderRef.getLevel(), appenderRef.getFilter());
                }
                else {
                    BaseConfiguration.LOGGER.error("Unable to locate appender " + appenderRef.getRef() + " for logger " + loggerConfig.getName());
                }
            }
        }
        this.setParents();
    }
    
    private void setToDefault() {
        this.setName("Default");
        final ConsoleAppender appender = ConsoleAppender.createAppender(PatternLayout.createLayout("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n", null, null, null, null), null, "SYSTEM_OUT", "Console", "false", "true");
        appender.start();
        this.addAppender(appender);
        final LoggerConfig rootLogger = this.getRootLogger();
        rootLogger.addAppender(appender, null, null);
        final String stringProperty = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.level");
        rootLogger.setLevel((stringProperty != null && Level.valueOf(stringProperty) != null) ? Level.valueOf(stringProperty) : Level.ERROR);
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public void addListener(final ConfigurationListener configurationListener) {
        this.listeners.add(configurationListener);
    }
    
    @Override
    public void removeListener(final ConfigurationListener configurationListener) {
        this.listeners.remove(configurationListener);
    }
    
    public Appender getAppender(final String s) {
        return (Appender)this.appenders.get(s);
    }
    
    @Override
    public Map getAppenders() {
        return this.appenders;
    }
    
    public void addAppender(final Appender appender) {
        this.appenders.put(appender.getName(), appender);
    }
    
    @Override
    public StrSubstitutor getStrSubstitutor() {
        return this.subst;
    }
    
    @Override
    public void setConfigurationMonitor(final ConfigurationMonitor monitor) {
        this.monitor = monitor;
    }
    
    @Override
    public ConfigurationMonitor getConfigurationMonitor() {
        return this.monitor;
    }
    
    @Override
    public void setAdvertiser(final Advertiser advertiser) {
        this.advertiser = advertiser;
    }
    
    @Override
    public Advertiser getAdvertiser() {
        return this.advertiser;
    }
    
    @Override
    public synchronized void addLoggerAppender(final org.apache.logging.log4j.core.Logger logger, final Appender appender) {
        final String name = logger.getName();
        this.appenders.putIfAbsent(appender.getName(), appender);
        final LoggerConfig loggerConfig = this.getLoggerConfig(name);
        if (loggerConfig.getName().equals(name)) {
            loggerConfig.addAppender(appender, null, null);
        }
        else {
            final LoggerConfig loggerConfig2 = new LoggerConfig(name, loggerConfig.getLevel(), loggerConfig.isAdditive());
            loggerConfig2.addAppender(appender, null, null);
            loggerConfig2.setParent(loggerConfig);
            this.loggers.putIfAbsent(name, loggerConfig2);
            this.setParents();
            logger.getContext().updateLoggers();
        }
    }
    
    @Override
    public synchronized void addLoggerFilter(final org.apache.logging.log4j.core.Logger logger, final Filter filter) {
        final String name = logger.getName();
        final LoggerConfig loggerConfig = this.getLoggerConfig(name);
        if (loggerConfig.getName().equals(name)) {
            loggerConfig.addFilter(filter);
        }
        else {
            final LoggerConfig loggerConfig2 = new LoggerConfig(name, loggerConfig.getLevel(), loggerConfig.isAdditive());
            loggerConfig2.addFilter(filter);
            loggerConfig2.setParent(loggerConfig);
            this.loggers.putIfAbsent(name, loggerConfig2);
            this.setParents();
            logger.getContext().updateLoggers();
        }
    }
    
    @Override
    public synchronized void setLoggerAdditive(final org.apache.logging.log4j.core.Logger logger, final boolean additive) {
        final String name = logger.getName();
        final LoggerConfig loggerConfig = this.getLoggerConfig(name);
        if (loggerConfig.getName().equals(name)) {
            loggerConfig.setAdditive(additive);
        }
        else {
            final LoggerConfig loggerConfig2 = new LoggerConfig(name, loggerConfig.getLevel(), additive);
            loggerConfig2.setParent(loggerConfig);
            this.loggers.putIfAbsent(name, loggerConfig2);
            this.setParents();
            logger.getContext().updateLoggers();
        }
    }
    
    public synchronized void removeAppender(final String s) {
        final Iterator iterator = this.loggers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().removeAppender(s);
        }
        final Appender appender = (Appender)this.appenders.remove(s);
        if (appender != null) {
            appender.stop();
        }
    }
    
    @Override
    public LoggerConfig getLoggerConfig(final String s) {
        if (this.loggers.containsKey(s)) {
            return (LoggerConfig)this.loggers.get(s);
        }
        String subName = s;
        while ((subName = NameUtil.getSubName(subName)) != null) {
            if (this.loggers.containsKey(subName)) {
                return (LoggerConfig)this.loggers.get(subName);
            }
        }
        return this.root;
    }
    
    public LoggerConfig getRootLogger() {
        return this.root;
    }
    
    @Override
    public Map getLoggers() {
        return Collections.unmodifiableMap((Map<?, ?>)this.loggers);
    }
    
    public LoggerConfig getLogger(final String s) {
        return (LoggerConfig)this.loggers.get(s);
    }
    
    public void addLogger(final String s, final LoggerConfig loggerConfig) {
        this.loggers.put(s, loggerConfig);
        this.setParents();
    }
    
    public void removeLogger(final String s) {
        this.loggers.remove(s);
        this.setParents();
    }
    
    @Override
    public void createConfiguration(final Node node, final LogEvent logEvent) {
        final PluginType type = node.getType();
        if (type != null && type.isDeferChildren()) {
            node.setObject(this.createPluginObject(type, node, logEvent));
        }
        else {
            final Iterator<Node> iterator = node.getChildren().iterator();
            while (iterator.hasNext()) {
                this.createConfiguration(iterator.next(), logEvent);
            }
            if (type == null) {
                if (node.getParent() != null) {
                    BaseConfiguration.LOGGER.error("Unable to locate plugin for " + node.getName());
                }
            }
            else {
                node.setObject(this.createPluginObject(type, node, logEvent));
            }
        }
    }
    
    private Object createPluginObject(final PluginType p0, final Node p1, final LogEvent p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   org/apache/logging/log4j/core/config/plugins/PluginType.getPluginClass:()Ljava/lang/Class;
        //     4: astore          4
        //     6: ldc             Ljava/util/Map;.class
        //     8: aload           4
        //    10: invokevirtual   java/lang/Class.isAssignableFrom:(Ljava/lang/Class;)Z
        //    13: ifeq            127
        //    16: aload           4
        //    18: invokevirtual   java/lang/Class.newInstance:()Ljava/lang/Object;
        //    21: checkcast       Ljava/util/Map;
        //    24: astore          5
        //    26: aload_2        
        //    27: invokevirtual   org/apache/logging/log4j/core/config/Node.getChildren:()Ljava/util/List;
        //    30: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    35: astore          6
        //    37: aload           6
        //    39: invokeinterface java/util/Iterator.hasNext:()Z
        //    44: ifeq            80
        //    47: aload           6
        //    49: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    54: checkcast       Lorg/apache/logging/log4j/core/config/Node;
        //    57: astore          7
        //    59: aload           5
        //    61: aload           7
        //    63: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //    66: aload           7
        //    68: invokevirtual   org/apache/logging/log4j/core/config/Node.getObject:()Ljava/lang/Object;
        //    71: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //    76: pop            
        //    77: goto            37
        //    80: aload           5
        //    82: areturn        
        //    83: astore          5
        //    85: getstatic       org/apache/logging/log4j/core/config/BaseConfiguration.LOGGER:Lorg/apache/logging/log4j/Logger;
        //    88: new             Ljava/lang/StringBuilder;
        //    91: dup            
        //    92: invokespecial   java/lang/StringBuilder.<init>:()V
        //    95: ldc_w           "Unable to create Map for "
        //    98: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   101: aload_1        
        //   102: invokevirtual   org/apache/logging/log4j/core/config/plugins/PluginType.getElementName:()Ljava/lang/String;
        //   105: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   108: ldc_w           " of class "
        //   111: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   114: aload           4
        //   116: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   119: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   122: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;)V
        //   127: ldc_w           Ljava/util/List;.class
        //   130: aload           4
        //   132: invokevirtual   java/lang/Class.isAssignableFrom:(Ljava/lang/Class;)Z
        //   135: ifeq            244
        //   138: aload           4
        //   140: invokevirtual   java/lang/Class.newInstance:()Ljava/lang/Object;
        //   143: checkcast       Ljava/util/List;
        //   146: astore          5
        //   148: aload_2        
        //   149: invokevirtual   org/apache/logging/log4j/core/config/Node.getChildren:()Ljava/util/List;
        //   152: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   157: astore          6
        //   159: aload           6
        //   161: invokeinterface java/util/Iterator.hasNext:()Z
        //   166: ifeq            197
        //   169: aload           6
        //   171: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   176: checkcast       Lorg/apache/logging/log4j/core/config/Node;
        //   179: astore          7
        //   181: aload           5
        //   183: aload           7
        //   185: invokevirtual   org/apache/logging/log4j/core/config/Node.getObject:()Ljava/lang/Object;
        //   188: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   193: pop            
        //   194: goto            159
        //   197: aload           5
        //   199: areturn        
        //   200: astore          5
        //   202: getstatic       org/apache/logging/log4j/core/config/BaseConfiguration.LOGGER:Lorg/apache/logging/log4j/Logger;
        //   205: new             Ljava/lang/StringBuilder;
        //   208: dup            
        //   209: invokespecial   java/lang/StringBuilder.<init>:()V
        //   212: ldc_w           "Unable to create List for "
        //   215: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   218: aload_1        
        //   219: invokevirtual   org/apache/logging/log4j/core/config/plugins/PluginType.getElementName:()Ljava/lang/String;
        //   222: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   225: ldc_w           " of class "
        //   228: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   231: aload           4
        //   233: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   236: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   239: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;)V
        //   244: aconst_null    
        //   245: astore          5
        //   247: aload           4
        //   249: invokevirtual   java/lang/Class.getMethods:()[Ljava/lang/reflect/Method;
        //   252: astore          6
        //   254: aload           6
        //   256: arraylength    
        //   257: istore          7
        //   259: iconst_0       
        //   260: iload           7
        //   262: if_icmpge       295
        //   265: aload           6
        //   267: iconst_0       
        //   268: aaload         
        //   269: astore          9
        //   271: aload           9
        //   273: ldc_w           Lorg/apache/logging/log4j/core/config/plugins/PluginFactory;.class
        //   276: invokevirtual   java/lang/reflect/Method.isAnnotationPresent:(Ljava/lang/Class;)Z
        //   279: ifeq            289
        //   282: aload           9
        //   284: astore          5
        //   286: goto            295
        //   289: iinc            8, 1
        //   292: goto            259
        //   295: aload           5
        //   297: ifnonnull       302
        //   300: aconst_null    
        //   301: areturn        
        //   302: aload           5
        //   304: invokevirtual   java/lang/reflect/Method.getParameterAnnotations:()[[Ljava/lang/annotation/Annotation;
        //   307: astore          6
        //   309: aload           5
        //   311: invokevirtual   java/lang/reflect/Method.getParameterTypes:()[Ljava/lang/Class;
        //   314: astore          7
        //   316: aload           6
        //   318: arraylength    
        //   319: aload           7
        //   321: arraylength    
        //   322: if_icmpeq       336
        //   325: getstatic       org/apache/logging/log4j/core/config/BaseConfiguration.LOGGER:Lorg/apache/logging/log4j/Logger;
        //   328: ldc_w           "Number of parameter annotations does not equal the number of paramters"
        //   331: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;)V
        //   336: aload           7
        //   338: arraylength    
        //   339: anewarray       Ljava/lang/Object;
        //   342: astore          8
        //   344: aload_2        
        //   345: invokevirtual   org/apache/logging/log4j/core/config/Node.getAttributes:()Ljava/util/Map;
        //   348: astore          10
        //   350: aload_2        
        //   351: invokevirtual   org/apache/logging/log4j/core/config/Node.getChildren:()Ljava/util/List;
        //   354: astore          11
        //   356: new             Ljava/lang/StringBuilder;
        //   359: dup            
        //   360: invokespecial   java/lang/StringBuilder.<init>:()V
        //   363: astore          12
        //   365: new             Ljava/util/ArrayList;
        //   368: dup            
        //   369: invokespecial   java/util/ArrayList.<init>:()V
        //   372: astore          13
        //   374: aload           6
        //   376: astore          14
        //   378: aload           14
        //   380: arraylength    
        //   381: istore          15
        //   383: iconst_0       
        //   384: iload           15
        //   386: if_icmpge       1380
        //   389: aload           14
        //   391: iconst_0       
        //   392: aaload         
        //   393: astore          17
        //   395: aconst_null    
        //   396: astore          18
        //   398: aload           17
        //   400: astore          19
        //   402: aload           19
        //   404: arraylength    
        //   405: istore          20
        //   407: iconst_0       
        //   408: iload           20
        //   410: if_icmpge       445
        //   413: aload           19
        //   415: iconst_0       
        //   416: aaload         
        //   417: astore          22
        //   419: aload           22
        //   421: instanceof      Lorg/apache/logging/log4j/core/config/plugins/PluginAliases;
        //   424: ifeq            439
        //   427: aload           22
        //   429: checkcast       Lorg/apache/logging/log4j/core/config/plugins/PluginAliases;
        //   432: invokeinterface org/apache/logging/log4j/core/config/plugins/PluginAliases.value:()[Ljava/lang/String;
        //   437: astore          18
        //   439: iinc            21, 1
        //   442: goto            407
        //   445: aload           17
        //   447: astore          19
        //   449: aload           19
        //   451: arraylength    
        //   452: istore          20
        //   454: iconst_0       
        //   455: iload           20
        //   457: if_icmpge       1371
        //   460: aload           19
        //   462: iconst_0       
        //   463: aaload         
        //   464: astore          22
        //   466: aload           22
        //   468: instanceof      Lorg/apache/logging/log4j/core/config/plugins/PluginAliases;
        //   471: ifeq            477
        //   474: goto            1365
        //   477: aload           12
        //   479: invokevirtual   java/lang/StringBuilder.length:()I
        //   482: ifne            497
        //   485: aload           12
        //   487: ldc_w           " with params("
        //   490: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   493: pop            
        //   494: goto            506
        //   497: aload           12
        //   499: ldc_w           ", "
        //   502: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   505: pop            
        //   506: aload           22
        //   508: instanceof      Lorg/apache/logging/log4j/core/config/plugins/PluginNode;
        //   511: ifeq            538
        //   514: aload           8
        //   516: iconst_0       
        //   517: aload_2        
        //   518: aastore        
        //   519: aload           12
        //   521: ldc_w           "Node="
        //   524: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   527: aload_2        
        //   528: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //   531: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   534: pop            
        //   535: goto            1365
        //   538: aload           22
        //   540: instanceof      Lorg/apache/logging/log4j/core/config/plugins/PluginConfiguration;
        //   543: ifeq            595
        //   546: aload           8
        //   548: iconst_0       
        //   549: aload_0        
        //   550: aastore        
        //   551: aload_0        
        //   552: getfield        org/apache/logging/log4j/core/config/BaseConfiguration.name:Ljava/lang/String;
        //   555: ifnull          583
        //   558: aload           12
        //   560: ldc_w           "Configuration("
        //   563: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   566: aload_0        
        //   567: getfield        org/apache/logging/log4j/core/config/BaseConfiguration.name:Ljava/lang/String;
        //   570: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   573: ldc_w           ")"
        //   576: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   579: pop            
        //   580: goto            1365
        //   583: aload           12
        //   585: ldc_w           "Configuration"
        //   588: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   591: pop            
        //   592: goto            1365
        //   595: aload           22
        //   597: instanceof      Lorg/apache/logging/log4j/core/config/plugins/PluginValue;
        //   600: ifeq            684
        //   603: aload           22
        //   605: checkcast       Lorg/apache/logging/log4j/core/config/plugins/PluginValue;
        //   608: invokeinterface org/apache/logging/log4j/core/config/plugins/PluginValue.value:()Ljava/lang/String;
        //   613: astore          23
        //   615: aload_2        
        //   616: invokevirtual   org/apache/logging/log4j/core/config/Node.getValue:()Ljava/lang/String;
        //   619: astore          24
        //   621: aload           24
        //   623: ifnonnull       638
        //   626: aload_0        
        //   627: ldc_w           "value"
        //   630: aconst_null    
        //   631: aload           10
        //   633: invokespecial   org/apache/logging/log4j/core/config/BaseConfiguration.getAttrValue:(Ljava/lang/String;[Ljava/lang/String;Ljava/util/Map;)Ljava/lang/String;
        //   636: astore          24
        //   638: aload_0        
        //   639: getfield        org/apache/logging/log4j/core/config/BaseConfiguration.subst:Lorg/apache/logging/log4j/core/lookup/StrSubstitutor;
        //   642: aload_3        
        //   643: aload           24
        //   645: invokevirtual   org/apache/logging/log4j/core/lookup/StrSubstitutor.replace:(Lorg/apache/logging/log4j/core/LogEvent;Ljava/lang/String;)Ljava/lang/String;
        //   648: astore          25
        //   650: aload           12
        //   652: aload           23
        //   654: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   657: ldc_w           "=\""
        //   660: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   663: aload           25
        //   665: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   668: ldc_w           "\""
        //   671: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   674: pop            
        //   675: aload           8
        //   677: iconst_0       
        //   678: aload           25
        //   680: aastore        
        //   681: goto            1365
        //   684: aload           22
        //   686: instanceof      Lorg/apache/logging/log4j/core/config/plugins/PluginAttribute;
        //   689: ifeq            762
        //   692: aload           22
        //   694: checkcast       Lorg/apache/logging/log4j/core/config/plugins/PluginAttribute;
        //   697: astore          23
        //   699: aload           23
        //   701: invokeinterface org/apache/logging/log4j/core/config/plugins/PluginAttribute.value:()Ljava/lang/String;
        //   706: astore          24
        //   708: aload_0        
        //   709: getfield        org/apache/logging/log4j/core/config/BaseConfiguration.subst:Lorg/apache/logging/log4j/core/lookup/StrSubstitutor;
        //   712: aload_3        
        //   713: aload_0        
        //   714: aload           24
        //   716: aload           18
        //   718: aload           10
        //   720: invokespecial   org/apache/logging/log4j/core/config/BaseConfiguration.getAttrValue:(Ljava/lang/String;[Ljava/lang/String;Ljava/util/Map;)Ljava/lang/String;
        //   723: invokevirtual   org/apache/logging/log4j/core/lookup/StrSubstitutor.replace:(Lorg/apache/logging/log4j/core/LogEvent;Ljava/lang/String;)Ljava/lang/String;
        //   726: astore          25
        //   728: aload           12
        //   730: aload           24
        //   732: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   735: ldc_w           "=\""
        //   738: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   741: aload           25
        //   743: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   746: ldc_w           "\""
        //   749: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   752: pop            
        //   753: aload           8
        //   755: iconst_0       
        //   756: aload           25
        //   758: aastore        
        //   759: goto            1365
        //   762: aload           22
        //   764: instanceof      Lorg/apache/logging/log4j/core/config/plugins/PluginElement;
        //   767: ifeq            1365
        //   770: aload           22
        //   772: checkcast       Lorg/apache/logging/log4j/core/config/plugins/PluginElement;
        //   775: astore          23
        //   777: aload           23
        //   779: invokeinterface org/apache/logging/log4j/core/config/plugins/PluginElement.value:()Ljava/lang/String;
        //   784: astore          24
        //   786: aload           7
        //   788: iconst_0       
        //   789: aaload         
        //   790: invokevirtual   java/lang/Class.isArray:()Z
        //   793: ifeq            1221
        //   796: aload           7
        //   798: iconst_0       
        //   799: aaload         
        //   800: invokevirtual   java/lang/Class.getComponentType:()Ljava/lang/Class;
        //   803: astore          25
        //   805: new             Ljava/util/ArrayList;
        //   808: dup            
        //   809: invokespecial   java/util/ArrayList.<init>:()V
        //   812: astore          26
        //   814: aload           12
        //   816: aload           24
        //   818: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   821: ldc_w           "={"
        //   824: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   827: pop            
        //   828: aload           11
        //   830: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   835: astore          28
        //   837: aload           28
        //   839: invokeinterface java/util/Iterator.hasNext:()Z
        //   844: ifeq            1038
        //   847: aload           28
        //   849: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   854: checkcast       Lorg/apache/logging/log4j/core/config/Node;
        //   857: astore          29
        //   859: aload           29
        //   861: invokevirtual   org/apache/logging/log4j/core/config/Node.getType:()Lorg/apache/logging/log4j/core/config/plugins/PluginType;
        //   864: astore          30
        //   866: aload           23
        //   868: invokeinterface org/apache/logging/log4j/core/config/plugins/PluginElement.value:()Ljava/lang/String;
        //   873: aload           30
        //   875: invokevirtual   org/apache/logging/log4j/core/config/plugins/PluginType.getElementName:()Ljava/lang/String;
        //   878: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   881: ifne            897
        //   884: aload           25
        //   886: aload           30
        //   888: invokevirtual   org/apache/logging/log4j/core/config/plugins/PluginType.getPluginClass:()Ljava/lang/Class;
        //   891: invokevirtual   java/lang/Class.isAssignableFrom:(Ljava/lang/Class;)Z
        //   894: ifeq            1035
        //   897: aload           13
        //   899: aload           29
        //   901: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   906: pop            
        //   907: iconst_0       
        //   908: ifne            920
        //   911: aload           12
        //   913: ldc_w           ", "
        //   916: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   919: pop            
        //   920: aload           29
        //   922: invokevirtual   org/apache/logging/log4j/core/config/Node.getObject:()Ljava/lang/Object;
        //   925: astore          31
        //   927: aload           31
        //   929: ifnonnull       980
        //   932: getstatic       org/apache/logging/log4j/core/config/BaseConfiguration.LOGGER:Lorg/apache/logging/log4j/Logger;
        //   935: new             Ljava/lang/StringBuilder;
        //   938: dup            
        //   939: invokespecial   java/lang/StringBuilder.<init>:()V
        //   942: ldc_w           "Null object returned for "
        //   945: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   948: aload           29
        //   950: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //   953: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   956: ldc_w           " in "
        //   959: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   962: aload_2        
        //   963: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //   966: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   969: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   972: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;)V
        //   977: goto            837
        //   980: aload           31
        //   982: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //   985: invokevirtual   java/lang/Class.isArray:()Z
        //   988: ifeq            1014
        //   991: aload_0        
        //   992: aload           12
        //   994: aload           31
        //   996: checkcast       [Ljava/lang/Object;
        //   999: checkcast       [Ljava/lang/Object;
        //  1002: invokespecial   org/apache/logging/log4j/core/config/BaseConfiguration.printArray:(Ljava/lang/StringBuilder;[Ljava/lang/Object;)V
        //  1005: aload           8
        //  1007: iconst_0       
        //  1008: aload           31
        //  1010: aastore        
        //  1011: goto            1038
        //  1014: aload           12
        //  1016: aload           29
        //  1018: invokevirtual   org/apache/logging/log4j/core/config/Node.toString:()Ljava/lang/String;
        //  1021: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1024: pop            
        //  1025: aload           26
        //  1027: aload           31
        //  1029: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //  1034: pop            
        //  1035: goto            837
        //  1038: aload           12
        //  1040: ldc_w           "}"
        //  1043: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1046: pop            
        //  1047: aload           8
        //  1049: iconst_0       
        //  1050: aaload         
        //  1051: ifnull          1057
        //  1054: goto            1371
        //  1057: aload           26
        //  1059: invokeinterface java/util/List.size:()I
        //  1064: ifle            1152
        //  1067: aload           25
        //  1069: aload           26
        //  1071: iconst_0       
        //  1072: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //  1077: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //  1080: invokevirtual   java/lang/Class.isAssignableFrom:(Ljava/lang/Class;)Z
        //  1083: ifne            1152
        //  1086: getstatic       org/apache/logging/log4j/core/config/BaseConfiguration.LOGGER:Lorg/apache/logging/log4j/Logger;
        //  1089: new             Ljava/lang/StringBuilder;
        //  1092: dup            
        //  1093: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1096: ldc_w           "Attempted to assign List containing class "
        //  1099: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1102: aload           26
        //  1104: iconst_0       
        //  1105: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //  1110: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //  1113: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //  1116: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1119: ldc_w           " to array of type "
        //  1122: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1125: aload           25
        //  1127: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //  1130: ldc_w           " for attribute "
        //  1133: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1136: aload           24
        //  1138: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1141: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1144: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;)V
        //  1149: goto            1371
        //  1152: aload           25
        //  1154: aload           26
        //  1156: invokeinterface java/util/List.size:()I
        //  1161: invokestatic    java/lang/reflect/Array.newInstance:(Ljava/lang/Class;I)Ljava/lang/Object;
        //  1164: checkcast       [Ljava/lang/Object;
        //  1167: checkcast       [Ljava/lang/Object;
        //  1170: astore          28
        //  1172: aload           26
        //  1174: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  1179: astore          30
        //  1181: aload           30
        //  1183: invokeinterface java/util/Iterator.hasNext:()Z
        //  1188: ifeq            1212
        //  1191: aload           30
        //  1193: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1198: astore          31
        //  1200: aload           28
        //  1202: iconst_0       
        //  1203: aload           31
        //  1205: aastore        
        //  1206: iinc            29, 1
        //  1209: goto            1181
        //  1212: aload           8
        //  1214: iconst_0       
        //  1215: aload           28
        //  1217: aastore        
        //  1218: goto            1365
        //  1221: aload           7
        //  1223: iconst_0       
        //  1224: aaload         
        //  1225: astore          25
        //  1227: aload           11
        //  1229: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  1234: astore          27
        //  1236: aload           27
        //  1238: invokeinterface java/util/Iterator.hasNext:()Z
        //  1243: ifeq            1352
        //  1246: aload           27
        //  1248: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1253: checkcast       Lorg/apache/logging/log4j/core/config/Node;
        //  1256: astore          28
        //  1258: aload           28
        //  1260: invokevirtual   org/apache/logging/log4j/core/config/Node.getType:()Lorg/apache/logging/log4j/core/config/plugins/PluginType;
        //  1263: astore          29
        //  1265: aload           23
        //  1267: invokeinterface org/apache/logging/log4j/core/config/plugins/PluginElement.value:()Ljava/lang/String;
        //  1272: aload           29
        //  1274: invokevirtual   org/apache/logging/log4j/core/config/plugins/PluginType.getElementName:()Ljava/lang/String;
        //  1277: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //  1280: ifne            1296
        //  1283: aload           25
        //  1285: aload           29
        //  1287: invokevirtual   org/apache/logging/log4j/core/config/plugins/PluginType.getPluginClass:()Ljava/lang/Class;
        //  1290: invokevirtual   java/lang/Class.isAssignableFrom:(Ljava/lang/Class;)Z
        //  1293: ifeq            1349
        //  1296: aload           12
        //  1298: aload           28
        //  1300: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //  1303: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1306: ldc_w           "("
        //  1309: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1312: aload           28
        //  1314: invokevirtual   org/apache/logging/log4j/core/config/Node.toString:()Ljava/lang/String;
        //  1317: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1320: ldc_w           ")"
        //  1323: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1326: pop            
        //  1327: aload           13
        //  1329: aload           28
        //  1331: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //  1336: pop            
        //  1337: aload           8
        //  1339: iconst_0       
        //  1340: aload           28
        //  1342: invokevirtual   org/apache/logging/log4j/core/config/Node.getObject:()Ljava/lang/Object;
        //  1345: aastore        
        //  1346: goto            1352
        //  1349: goto            1236
        //  1352: iconst_1       
        //  1353: ifne            1365
        //  1356: aload           12
        //  1358: ldc_w           "null"
        //  1361: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1364: pop            
        //  1365: iinc            21, 1
        //  1368: goto            454
        //  1371: iinc            9, 1
        //  1374: iinc            16, 1
        //  1377: goto            383
        //  1380: aload           12
        //  1382: invokevirtual   java/lang/StringBuilder.length:()I
        //  1385: ifle            1397
        //  1388: aload           12
        //  1390: ldc_w           ")"
        //  1393: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1396: pop            
        //  1397: aload           10
        //  1399: invokeinterface java/util/Map.size:()I
        //  1404: ifle            1565
        //  1407: new             Ljava/lang/StringBuilder;
        //  1410: dup            
        //  1411: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1414: astore          14
        //  1416: aload           10
        //  1418: invokeinterface java/util/Map.keySet:()Ljava/util/Set;
        //  1423: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //  1428: astore          15
        //  1430: aload           15
        //  1432: invokeinterface java/util/Iterator.hasNext:()Z
        //  1437: ifeq            1552
        //  1440: aload           15
        //  1442: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1447: checkcast       Ljava/lang/String;
        //  1450: astore          16
        //  1452: aload           14
        //  1454: invokevirtual   java/lang/StringBuilder.length:()I
        //  1457: ifne            1514
        //  1460: aload           14
        //  1462: aload_2        
        //  1463: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //  1466: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1469: pop            
        //  1470: aload           14
        //  1472: ldc_w           " contains "
        //  1475: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1478: pop            
        //  1479: aload           10
        //  1481: invokeinterface java/util/Map.size:()I
        //  1486: iconst_1       
        //  1487: if_icmpne       1502
        //  1490: aload           14
        //  1492: ldc_w           "an invalid element or attribute "
        //  1495: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1498: pop            
        //  1499: goto            1523
        //  1502: aload           14
        //  1504: ldc_w           "invalid attributes "
        //  1507: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1510: pop            
        //  1511: goto            1523
        //  1514: aload           14
        //  1516: ldc_w           ", "
        //  1519: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1522: pop            
        //  1523: aload           14
        //  1525: ldc_w           "\""
        //  1528: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1531: pop            
        //  1532: aload           14
        //  1534: aload           16
        //  1536: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1539: pop            
        //  1540: aload           14
        //  1542: ldc_w           "\""
        //  1545: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1548: pop            
        //  1549: goto            1430
        //  1552: getstatic       org/apache/logging/log4j/core/config/BaseConfiguration.LOGGER:Lorg/apache/logging/log4j/Logger;
        //  1555: aload           14
        //  1557: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1560: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;)V
        //  1565: aload_1        
        //  1566: invokevirtual   org/apache/logging/log4j/core/config/plugins/PluginType.isDeferChildren:()Z
        //  1569: ifne            1733
        //  1572: aload           13
        //  1574: invokeinterface java/util/List.size:()I
        //  1579: aload           11
        //  1581: invokeinterface java/util/List.size:()I
        //  1586: if_icmpeq       1733
        //  1589: aload           11
        //  1591: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  1596: astore          14
        //  1598: aload           14
        //  1600: invokeinterface java/util/Iterator.hasNext:()Z
        //  1605: ifeq            1733
        //  1608: aload           14
        //  1610: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1615: checkcast       Lorg/apache/logging/log4j/core/config/Node;
        //  1618: astore          15
        //  1620: aload           13
        //  1622: aload           15
        //  1624: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //  1629: ifeq            1635
        //  1632: goto            1598
        //  1635: aload_2        
        //  1636: invokevirtual   org/apache/logging/log4j/core/config/Node.getType:()Lorg/apache/logging/log4j/core/config/plugins/PluginType;
        //  1639: invokevirtual   org/apache/logging/log4j/core/config/plugins/PluginType.getElementName:()Ljava/lang/String;
        //  1642: astore          16
        //  1644: aload           16
        //  1646: aload_2        
        //  1647: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //  1650: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //  1653: ifeq            1663
        //  1656: aload_2        
        //  1657: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //  1660: goto            1691
        //  1663: new             Ljava/lang/StringBuilder;
        //  1666: dup            
        //  1667: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1670: aload           16
        //  1672: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1675: ldc_w           " "
        //  1678: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1681: aload_2        
        //  1682: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //  1685: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1688: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1691: astore          17
        //  1693: getstatic       org/apache/logging/log4j/core/config/BaseConfiguration.LOGGER:Lorg/apache/logging/log4j/Logger;
        //  1696: new             Ljava/lang/StringBuilder;
        //  1699: dup            
        //  1700: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1703: aload           17
        //  1705: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1708: ldc_w           " has no parameter that matches element "
        //  1711: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1714: aload           15
        //  1716: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //  1719: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1722: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1725: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;)V
        //  1730: goto            1598
        //  1733: aload           5
        //  1735: invokevirtual   java/lang/reflect/Method.getModifiers:()I
        //  1738: istore          14
        //  1740: iload           14
        //  1742: invokestatic    java/lang/reflect/Modifier.isStatic:(I)Z
        //  1745: ifne            1803
        //  1748: getstatic       org/apache/logging/log4j/core/config/BaseConfiguration.LOGGER:Lorg/apache/logging/log4j/Logger;
        //  1751: new             Ljava/lang/StringBuilder;
        //  1754: dup            
        //  1755: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1758: aload           5
        //  1760: invokevirtual   java/lang/reflect/Method.getName:()Ljava/lang/String;
        //  1763: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1766: ldc_w           " method is not static on class "
        //  1769: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1772: aload           4
        //  1774: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //  1777: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1780: ldc_w           " for element "
        //  1783: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1786: aload_2        
        //  1787: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //  1790: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1793: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1796: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;)V
        //  1801: aconst_null    
        //  1802: areturn        
        //  1803: getstatic       org/apache/logging/log4j/core/config/BaseConfiguration.LOGGER:Lorg/apache/logging/log4j/Logger;
        //  1806: ldc_w           "Calling {} on class {} for element {}{}"
        //  1809: iconst_4       
        //  1810: anewarray       Ljava/lang/Object;
        //  1813: dup            
        //  1814: iconst_0       
        //  1815: aload           5
        //  1817: invokevirtual   java/lang/reflect/Method.getName:()Ljava/lang/String;
        //  1820: aastore        
        //  1821: dup            
        //  1822: iconst_1       
        //  1823: aload           4
        //  1825: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //  1828: aastore        
        //  1829: dup            
        //  1830: iconst_2       
        //  1831: aload_2        
        //  1832: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //  1835: aastore        
        //  1836: dup            
        //  1837: iconst_3       
        //  1838: aload           12
        //  1840: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1843: aastore        
        //  1844: invokeinterface org/apache/logging/log4j/Logger.debug:(Ljava/lang/String;[Ljava/lang/Object;)V
        //  1849: aload           5
        //  1851: aconst_null    
        //  1852: aload           8
        //  1854: invokevirtual   java/lang/reflect/Method.invoke:(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
        //  1857: areturn        
        //  1858: astore          14
        //  1860: getstatic       org/apache/logging/log4j/core/config/BaseConfiguration.LOGGER:Lorg/apache/logging/log4j/Logger;
        //  1863: new             Ljava/lang/StringBuilder;
        //  1866: dup            
        //  1867: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1870: ldc_w           "Unable to invoke method "
        //  1873: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1876: aload           5
        //  1878: invokevirtual   java/lang/reflect/Method.getName:()Ljava/lang/String;
        //  1881: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1884: ldc_w           " in class "
        //  1887: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1890: aload           4
        //  1892: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //  1895: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1898: ldc_w           " for element "
        //  1901: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1904: aload_2        
        //  1905: invokevirtual   org/apache/logging/log4j/core/config/Node.getName:()Ljava/lang/String;
        //  1908: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1911: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1914: aload           14
        //  1916: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //  1921: aconst_null    
        //  1922: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void printArray(final StringBuilder sb, final Object... array) {
        while (0 < array.length) {
            final Object o = array[0];
            if (!false) {
                sb.append(", ");
            }
            sb.append(o.toString());
            int n = 0;
            ++n;
        }
    }
    
    private String getAttrValue(final String s, final String[] array, final Map map) {
        for (final String s2 : map.keySet()) {
            if (s2.equalsIgnoreCase(s)) {
                final String s3 = (String)map.get(s2);
                map.remove(s2);
                return s3;
            }
            if (array == null) {
                continue;
            }
            while (0 < array.length) {
                if (s2.equalsIgnoreCase(array[0])) {
                    final String s4 = (String)map.get(s2);
                    map.remove(s2);
                    return s4;
                }
                int n = 0;
                ++n;
            }
        }
        return null;
    }
    
    private void setParents() {
        for (final Map.Entry<K, LoggerConfig> entry : this.loggers.entrySet()) {
            final LoggerConfig loggerConfig = entry.getValue();
            final String s = (String)entry.getKey();
            if (!s.equals("")) {
                final int lastIndex = s.lastIndexOf(46);
                if (lastIndex > 0) {
                    LoggerConfig parent = this.getLoggerConfig(s.substring(0, lastIndex));
                    if (parent == null) {
                        parent = this.root;
                    }
                    loggerConfig.setParent(parent);
                }
                else {
                    loggerConfig.setParent(this.root);
                }
            }
        }
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
