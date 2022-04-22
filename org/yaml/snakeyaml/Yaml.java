package org.yaml.snakeyaml;

import org.yaml.snakeyaml.resolver.*;
import org.yaml.snakeyaml.representer.*;
import org.yaml.snakeyaml.constructor.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.serializer.*;
import org.yaml.snakeyaml.emitter.*;
import org.yaml.snakeyaml.reader.*;
import org.yaml.snakeyaml.composer.*;
import org.yaml.snakeyaml.parser.*;
import java.util.*;
import java.util.regex.*;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.introspector.*;
import java.io.*;

public class Yaml
{
    protected final Resolver resolver;
    private String name;
    protected BaseConstructor constructor;
    protected Representer representer;
    protected DumperOptions dumperOptions;
    protected LoaderOptions loadingConfig;
    
    public Yaml() {
        this(new Constructor(), new Representer(), new DumperOptions(), new LoaderOptions(), new Resolver());
    }
    
    public Yaml(final DumperOptions dumperOptions) {
        this(new Constructor(), new Representer(dumperOptions), dumperOptions);
    }
    
    public Yaml(final LoaderOptions loaderOptions) {
        this(new Constructor(loaderOptions), new Representer(), new DumperOptions(), loaderOptions);
    }
    
    public Yaml(final Representer representer) {
        this(new Constructor(), representer);
    }
    
    public Yaml(final BaseConstructor baseConstructor) {
        this(baseConstructor, new Representer());
    }
    
    public Yaml(final BaseConstructor baseConstructor, final Representer representer) {
        this(baseConstructor, representer, initDumperOptions(representer));
    }
    
    private static DumperOptions initDumperOptions(final Representer representer) {
        final DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(representer.getDefaultFlowStyle());
        dumperOptions.setDefaultScalarStyle(representer.getDefaultScalarStyle());
        dumperOptions.setAllowReadOnlyProperties(representer.getPropertyUtils().isAllowReadOnlyProperties());
        dumperOptions.setTimeZone(representer.getTimeZone());
        return dumperOptions;
    }
    
    public Yaml(final Representer representer, final DumperOptions dumperOptions) {
        this(new Constructor(), representer, dumperOptions, new LoaderOptions(), new Resolver());
    }
    
    public Yaml(final BaseConstructor baseConstructor, final Representer representer, final DumperOptions dumperOptions) {
        this(baseConstructor, representer, dumperOptions, new LoaderOptions(), new Resolver());
    }
    
    public Yaml(final BaseConstructor baseConstructor, final Representer representer, final DumperOptions dumperOptions, final LoaderOptions loaderOptions) {
        this(baseConstructor, representer, dumperOptions, loaderOptions, new Resolver());
    }
    
    public Yaml(final BaseConstructor baseConstructor, final Representer representer, final DumperOptions dumperOptions, final Resolver resolver) {
        this(baseConstructor, representer, dumperOptions, new LoaderOptions(), resolver);
    }
    
    public Yaml(final BaseConstructor constructor, final Representer representer, final DumperOptions dumperOptions, final LoaderOptions loadingConfig, final Resolver resolver) {
        if (!constructor.isExplicitPropertyUtils()) {
            constructor.setPropertyUtils(representer.getPropertyUtils());
        }
        else if (!representer.isExplicitPropertyUtils()) {
            representer.setPropertyUtils(constructor.getPropertyUtils());
        }
        (this.constructor = constructor).setAllowDuplicateKeys(loadingConfig.isAllowDuplicateKeys());
        this.constructor.setWrappedToRootException(loadingConfig.isWrappedToRootException());
        if (!dumperOptions.getIndentWithIndicator() && dumperOptions.getIndent() <= dumperOptions.getIndicatorIndent()) {
            throw new YAMLException("Indicator indent must be smaller then indent.");
        }
        representer.setDefaultFlowStyle(dumperOptions.getDefaultFlowStyle());
        representer.setDefaultScalarStyle(dumperOptions.getDefaultScalarStyle());
        representer.getPropertyUtils().setAllowReadOnlyProperties(dumperOptions.isAllowReadOnlyProperties());
        representer.setTimeZone(dumperOptions.getTimeZone());
        this.representer = representer;
        this.dumperOptions = dumperOptions;
        this.loadingConfig = loadingConfig;
        this.resolver = resolver;
        this.name = "Yaml:" + System.identityHashCode(this);
    }
    
    public String dump(final Object o) {
        final ArrayList<Object> list = new ArrayList<Object>(1);
        list.add(o);
        return this.dumpAll(list.iterator());
    }
    
    public Node represent(final Object o) {
        return this.representer.represent(o);
    }
    
    public String dumpAll(final Iterator iterator) {
        final StringWriter stringWriter = new StringWriter();
        this.dumpAll(iterator, stringWriter, null);
        return stringWriter.toString();
    }
    
    public void dump(final Object o, final Writer writer) {
        final ArrayList<Object> list = new ArrayList<Object>(1);
        list.add(o);
        this.dumpAll(list.iterator(), writer, null);
    }
    
    public void dumpAll(final Iterator iterator, final Writer writer) {
        this.dumpAll(iterator, writer, null);
    }
    
    private void dumpAll(final Iterator iterator, final Writer writer, final Tag tag) {
        final Serializer serializer = new Serializer(new Emitter(writer, this.dumperOptions), this.resolver, this.dumperOptions, tag);
        serializer.open();
        while (iterator.hasNext()) {
            serializer.serialize(this.representer.represent(iterator.next()));
        }
        serializer.close();
    }
    
    public String dumpAs(final Object o, final Tag tag, final DumperOptions.FlowStyle defaultFlowStyle) {
        final DumperOptions.FlowStyle defaultFlowStyle2 = this.representer.getDefaultFlowStyle();
        if (defaultFlowStyle != null) {
            this.representer.setDefaultFlowStyle(defaultFlowStyle);
        }
        final ArrayList<Object> list = new ArrayList<Object>(1);
        list.add(o);
        final StringWriter stringWriter = new StringWriter();
        this.dumpAll(list.iterator(), stringWriter, tag);
        this.representer.setDefaultFlowStyle(defaultFlowStyle2);
        return stringWriter.toString();
    }
    
    public String dumpAsMap(final Object o) {
        return this.dumpAs(o, Tag.MAP, DumperOptions.FlowStyle.BLOCK);
    }
    
    public void serialize(final Node node, final Writer writer) {
        final Serializer serializer = new Serializer(new Emitter(writer, this.dumperOptions), this.resolver, this.dumperOptions, null);
        serializer.open();
        serializer.serialize(node);
        serializer.close();
    }
    
    public List serialize(final Node node) {
        final SilentEmitter silentEmitter = new SilentEmitter(null);
        final Serializer serializer = new Serializer(silentEmitter, this.resolver, this.dumperOptions, null);
        serializer.open();
        serializer.serialize(node);
        serializer.close();
        return silentEmitter.getEvents();
    }
    
    public Object load(final String s) {
        return this.loadFromReader(new StreamReader(s), Object.class);
    }
    
    public Object load(final InputStream inputStream) {
        return this.loadFromReader(new StreamReader(new UnicodeReader(inputStream)), Object.class);
    }
    
    public Object load(final Reader reader) {
        return this.loadFromReader(new StreamReader(reader), Object.class);
    }
    
    public Object loadAs(final Reader reader, final Class clazz) {
        return this.loadFromReader(new StreamReader(reader), clazz);
    }
    
    public Object loadAs(final String s, final Class clazz) {
        return this.loadFromReader(new StreamReader(s), clazz);
    }
    
    public Object loadAs(final InputStream inputStream, final Class clazz) {
        return this.loadFromReader(new StreamReader(new UnicodeReader(inputStream)), clazz);
    }
    
    private Object loadFromReader(final StreamReader streamReader, final Class clazz) {
        this.constructor.setComposer(new Composer(new ParserImpl(streamReader), this.resolver, this.loadingConfig));
        return this.constructor.getSingleData(clazz);
    }
    
    public Iterable loadAll(final Reader reader) {
        this.constructor.setComposer(new Composer(new ParserImpl(new StreamReader(reader)), this.resolver, this.loadingConfig));
        return new YamlIterable(new Iterator() {
            final Yaml this$0;
            
            @Override
            public boolean hasNext() {
                return this.this$0.constructor.checkData();
            }
            
            @Override
            public Object next() {
                return this.this$0.constructor.getData();
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        });
    }
    
    public Iterable loadAll(final String s) {
        return this.loadAll(new StringReader(s));
    }
    
    public Iterable loadAll(final InputStream inputStream) {
        return this.loadAll(new UnicodeReader(inputStream));
    }
    
    public Node compose(final Reader reader) {
        return new Composer(new ParserImpl(new StreamReader(reader)), this.resolver, this.loadingConfig).getSingleNode();
    }
    
    public Iterable composeAll(final Reader reader) {
        return new NodeIterable(new Iterator(new Composer(new ParserImpl(new StreamReader(reader)), this.resolver, this.loadingConfig)) {
            final Composer val$composer;
            final Yaml this$0;
            
            @Override
            public boolean hasNext() {
                return this.val$composer.checkNode();
            }
            
            @Override
            public Node next() {
                final Node node = this.val$composer.getNode();
                if (node != null) {
                    return node;
                }
                throw new NoSuchElementException("No Node is available.");
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public Object next() {
                return this.next();
            }
        });
    }
    
    public void addImplicitResolver(final Tag tag, final Pattern pattern, final String s) {
        this.resolver.addImplicitResolver(tag, pattern, s);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Iterable parse(final Reader reader) {
        return new EventIterable(new Iterator((Parser)new ParserImpl(new StreamReader(reader))) {
            final Parser val$parser;
            final Yaml this$0;
            
            @Override
            public boolean hasNext() {
                return this.val$parser.peekEvent() != null;
            }
            
            @Override
            public Event next() {
                final Event event = this.val$parser.getEvent();
                if (event != null) {
                    return event;
                }
                throw new NoSuchElementException("No Event is available.");
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public Object next() {
                return this.next();
            }
        });
    }
    
    public void setBeanAccess(final BeanAccess beanAccess) {
        this.constructor.getPropertyUtils().setBeanAccess(beanAccess);
        this.representer.getPropertyUtils().setBeanAccess(beanAccess);
    }
    
    public void addTypeDescription(final TypeDescription typeDescription) {
        this.constructor.addTypeDescription(typeDescription);
        this.representer.addTypeDescription(typeDescription);
    }
    
    private static class EventIterable implements Iterable
    {
        private Iterator iterator;
        
        public EventIterable(final Iterator iterator) {
            this.iterator = iterator;
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator;
        }
    }
    
    private static class NodeIterable implements Iterable
    {
        private Iterator iterator;
        
        public NodeIterable(final Iterator iterator) {
            this.iterator = iterator;
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator;
        }
    }
    
    private static class YamlIterable implements Iterable
    {
        private Iterator iterator;
        
        public YamlIterable(final Iterator iterator) {
            this.iterator = iterator;
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator;
        }
    }
    
    private static class SilentEmitter implements Emitable
    {
        private List events;
        
        private SilentEmitter() {
            this.events = new ArrayList(100);
        }
        
        public List getEvents() {
            return this.events;
        }
        
        @Override
        public void emit(final Event event) throws IOException {
            this.events.add(event);
        }
        
        SilentEmitter(final Yaml$1 iterator) {
            this();
        }
    }
}
