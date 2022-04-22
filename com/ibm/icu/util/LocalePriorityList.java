package com.ibm.icu.util;

import java.util.*;
import java.util.regex.*;

public class LocalePriorityList implements Iterable
{
    private static final double D0 = 0.0;
    private static final Double D1;
    private static final Pattern languageSplitter;
    private static final Pattern weightSplitter;
    private final Map languagesAndWeights;
    private static Comparator myDescendingDouble;
    
    public static Builder add(final ULocale uLocale) {
        return new Builder(null).add(uLocale);
    }
    
    public static Builder add(final ULocale uLocale, final double n) {
        return new Builder(null).add(uLocale, n);
    }
    
    public static Builder add(final LocalePriorityList list) {
        return new Builder(null).add(list);
    }
    
    public static Builder add(final String s) {
        return new Builder(null).add(s);
    }
    
    public Double getWeight(final ULocale uLocale) {
        return this.languagesAndWeights.get(uLocale);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final ULocale uLocale : this.languagesAndWeights.keySet()) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(uLocale);
            final double doubleValue = this.languagesAndWeights.get(uLocale);
            if (doubleValue != LocalePriorityList.D1) {
                sb.append(";q=").append(doubleValue);
            }
        }
        return sb.toString();
    }
    
    public Iterator iterator() {
        return this.languagesAndWeights.keySet().iterator();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && (this == o || this.languagesAndWeights.equals(((LocalePriorityList)o).languagesAndWeights));
    }
    
    @Override
    public int hashCode() {
        return this.languagesAndWeights.hashCode();
    }
    
    private LocalePriorityList(final Map languagesAndWeights) {
        this.languagesAndWeights = languagesAndWeights;
    }
    
    static Comparator access$100() {
        return LocalePriorityList.myDescendingDouble;
    }
    
    static Double access$200() {
        return LocalePriorityList.D1;
    }
    
    LocalePriorityList(final Map map, final LocalePriorityList$1 comparator) {
        this(map);
    }
    
    static Map access$400(final LocalePriorityList list) {
        return list.languagesAndWeights;
    }
    
    static Pattern access$500() {
        return LocalePriorityList.languageSplitter;
    }
    
    static Pattern access$600() {
        return LocalePriorityList.weightSplitter;
    }
    
    static {
        D1 = 1.0;
        languageSplitter = Pattern.compile("\\s*,\\s*");
        weightSplitter = Pattern.compile("\\s*(\\S*)\\s*;\\s*q\\s*=\\s*(\\S*)");
        LocalePriorityList.myDescendingDouble = new Comparator() {
            public int compare(final Double n, final Double n2) {
                return -n.compareTo(n2);
            }
            
            public int compare(final Object o, final Object o2) {
                return this.compare((Double)o, (Double)o2);
            }
        };
    }
    
    public static class Builder
    {
        private final Map languageToWeight;
        
        private Builder() {
            this.languageToWeight = new LinkedHashMap();
        }
        
        public LocalePriorityList build() {
            return this.build(false);
        }
        
        public LocalePriorityList build(final boolean b) {
            final TreeMap<Double, LinkedHashSet<ULocale>> treeMap = (TreeMap<Double, LinkedHashSet<ULocale>>)new TreeMap<Object, LinkedHashSet<ULocale>>(LocalePriorityList.access$100());
            for (final ULocale uLocale : this.languageToWeight.keySet()) {
                final Double n = this.languageToWeight.get(uLocale);
                LinkedHashSet<ULocale> set = treeMap.get(n);
                if (set == null) {
                    treeMap.put(n, set = new LinkedHashSet<ULocale>());
                }
                set.add(uLocale);
            }
            final LinkedHashMap<ULocale, Double> linkedHashMap = new LinkedHashMap<ULocale, Double>();
            for (final Map.Entry<Double, LinkedHashSet<ULocale>> entry : treeMap.entrySet()) {
                final Double n2 = entry.getKey();
                final Iterator<Object> iterator3 = entry.getValue().iterator();
                while (iterator3.hasNext()) {
                    linkedHashMap.put(iterator3.next(), b ? n2 : LocalePriorityList.access$200());
                }
            }
            return new LocalePriorityList(Collections.unmodifiableMap((Map<?, ?>)linkedHashMap), null);
        }
        
        public Builder add(final LocalePriorityList list) {
            for (final ULocale uLocale : LocalePriorityList.access$400(list).keySet()) {
                this.add(uLocale, (double)LocalePriorityList.access$400(list).get(uLocale));
            }
            return this;
        }
        
        public Builder add(final ULocale uLocale) {
            return this.add(uLocale, LocalePriorityList.access$200());
        }
        
        public Builder add(final ULocale... array) {
            while (0 < array.length) {
                this.add(array[0], LocalePriorityList.access$200());
                int n = 0;
                ++n;
            }
            return this;
        }
        
        public Builder add(final ULocale uLocale, double doubleValue) {
            if (this.languageToWeight.containsKey(uLocale)) {
                this.languageToWeight.remove(uLocale);
            }
            if (doubleValue <= 0.0) {
                return this;
            }
            if (doubleValue > LocalePriorityList.access$200()) {
                doubleValue = LocalePriorityList.access$200();
            }
            this.languageToWeight.put(uLocale, doubleValue);
            return this;
        }
        
        public Builder add(final String s) {
            final String[] split = LocalePriorityList.access$500().split(s.trim());
            final Matcher matcher = LocalePriorityList.access$600().matcher("");
            final String[] array = split;
            while (0 < array.length) {
                final String s2 = array[0];
                if (matcher.reset(s2).matches()) {
                    final ULocale uLocale = new ULocale(matcher.group(1));
                    final double double1 = Double.parseDouble(matcher.group(2));
                    if (double1 < 0.0 || double1 > LocalePriorityList.access$200()) {
                        throw new IllegalArgumentException("Illegal weight, must be 0..1: " + double1);
                    }
                    this.add(uLocale, double1);
                }
                else if (s2.length() != 0) {
                    this.add(new ULocale(s2));
                }
                int n = 0;
                ++n;
            }
            return this;
        }
        
        Builder(final LocalePriorityList$1 comparator) {
            this();
        }
    }
}
