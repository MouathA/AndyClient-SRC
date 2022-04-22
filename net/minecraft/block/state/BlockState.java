package net.minecraft.block.state;

import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

public class BlockState
{
    private static final Joiner COMMA_JOINER;
    private static final Function GET_NAME_FUNC;
    private final Block block;
    private final ImmutableList properties;
    private final ImmutableList validStates;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002030";
        COMMA_JOINER = Joiner.on(", ");
        GET_NAME_FUNC = new Function() {
            private static final String __OBFID;
            
            public String apply(final IProperty property) {
                return (property == null) ? "<NULL>" : property.getName();
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((IProperty)o);
            }
            
            static {
                __OBFID = "CL_00002029";
            }
        };
    }
    
    public BlockState(final Block block, final IProperty... array) {
        this.block = block;
        Arrays.sort(array, new Comparator() {
            private static final String __OBFID;
            final BlockState this$0;
            
            public int compare(final IProperty property, final IProperty property2) {
                return property.getName().compareTo(property2.getName());
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((IProperty)o, (IProperty)o2);
            }
            
            static {
                __OBFID = "CL_00002028";
            }
        });
        this.properties = ImmutableList.copyOf(array);
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<List> iterator = Cartesian.cartesianProduct(this.getAllowedValues()).iterator();
        while (iterator.hasNext()) {
            final Map map = MapPopulator.createMap(this.properties, iterator.next());
            final StateImplemenation stateImplemenation = new StateImplemenation(block, ImmutableMap.copyOf(map), null);
            linkedHashMap.put(map, stateImplemenation);
            arrayList.add(stateImplemenation);
        }
        final Iterator<StateImplemenation> iterator2 = arrayList.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().buildPropertyValueTable(linkedHashMap);
        }
        this.validStates = ImmutableList.copyOf(arrayList);
    }
    
    public ImmutableList getValidStates() {
        return this.validStates;
    }
    
    private List getAllowedValues() {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < this.properties.size()) {
            arrayList.add(this.properties.get(0).getAllowedValues());
            int n = 0;
            ++n;
        }
        return arrayList;
    }
    
    public IBlockState getBaseState() {
        return this.validStates.get(0);
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public Collection getProperties() {
        return this.properties;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("block", Block.blockRegistry.getNameForObject(this.block)).add("properties", Iterables.transform(this.properties, BlockState.GET_NAME_FUNC)).toString();
    }
    
    static class StateImplemenation extends BlockStateBase
    {
        private final Block block;
        private final ImmutableMap properties;
        private ImmutableTable propertyValueTable;
        private static final String __OBFID;
        
        private StateImplemenation(final Block block, final ImmutableMap properties) {
            this.block = block;
            this.properties = properties;
        }
        
        @Override
        public Collection getPropertyNames() {
            return Collections.unmodifiableCollection((Collection<?>)this.properties.keySet());
        }
        
        @Override
        public Comparable getValue(final IProperty property) {
            if (!this.properties.containsKey(property)) {
                throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
            }
            return property.getValueClass().cast(this.properties.get(property));
        }
        
        @Override
        public IBlockState withProperty(final IProperty property, final Comparable comparable) {
            if (!this.properties.containsKey(property)) {
                throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState());
            }
            if (!property.getAllowedValues().contains(comparable)) {
                throw new IllegalArgumentException("Cannot set property " + property + " to " + comparable + " on block " + Block.blockRegistry.getNameForObject(this.block) + ", it is not an allowed value");
            }
            return (this.properties.get(property) == comparable) ? this : ((IBlockState)this.propertyValueTable.get(property, comparable));
        }
        
        @Override
        public ImmutableMap getProperties() {
            return this.properties;
        }
        
        @Override
        public Block getBlock() {
            return this.block;
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o;
        }
        
        @Override
        public int hashCode() {
            return this.properties.hashCode();
        }
        
        public void buildPropertyValueTable(final Map map) {
            if (this.propertyValueTable != null) {
                throw new IllegalStateException();
            }
            final HashBasedTable create = HashBasedTable.create();
            for (final IProperty property : this.properties.keySet()) {
                for (final Comparable comparable : property.getAllowedValues()) {
                    if (comparable != this.properties.get(property)) {
                        create.put(property, comparable, map.get(this.setPropertyValue(property, comparable)));
                    }
                }
            }
            this.propertyValueTable = ImmutableTable.copyOf(create);
        }
        
        private Map setPropertyValue(final IProperty property, final Comparable comparable) {
            final HashMap hashMap = Maps.newHashMap(this.properties);
            hashMap.put(property, comparable);
            return hashMap;
        }
        
        StateImplemenation(final Block block, final ImmutableMap immutableMap, final Object o) {
            this(block, immutableMap);
        }
        
        static {
            __OBFID = "CL_00002027";
        }
    }
}
