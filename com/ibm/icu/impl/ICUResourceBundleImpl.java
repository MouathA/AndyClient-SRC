package com.ibm.icu.impl;

import java.util.*;
import com.ibm.icu.util.*;
import java.nio.*;

class ICUResourceBundleImpl extends ICUResourceBundle
{
    protected ICUResourceBundleImpl(final ICUResourceBundleReader icuResourceBundleReader, final String s, final String s2, final int n, final ICUResourceBundleImpl icuResourceBundleImpl) {
        super(icuResourceBundleReader, s, s2, n, icuResourceBundleImpl);
    }
    
    protected final ICUResourceBundle createBundleObject(final String s, final int n, final HashMap hashMap, final UResourceBundle uResourceBundle, final boolean[] array) {
        if (array != null) {
            array[0] = false;
        }
        final String string = this.resPath + "/" + s;
        switch (ICUResourceBundleReader.RES_GET_TYPE(n)) {
            case 0:
            case 6: {
                return new ResourceString(this.reader, s, string, n, this);
            }
            case 1: {
                return new ResourceBinary(this.reader, s, string, n, this);
            }
            case 3: {
                if (array != null) {
                    array[0] = true;
                }
                return this.findResource(s, string, n, hashMap, uResourceBundle);
            }
            case 7: {
                return new ResourceInt(this.reader, s, string, n, this);
            }
            case 14: {
                return new ResourceIntVector(this.reader, s, string, n, this);
            }
            case 8:
            case 9: {
                return new ResourceArray(this.reader, s, string, n, this);
            }
            case 2:
            case 4:
            case 5: {
                return new ResourceTable(this.reader, s, string, n, this);
            }
            default: {
                throw new IllegalStateException("The resource type is unknown");
            }
        }
    }
    
    static class ResourceTable extends ResourceContainer
    {
        @Override
        protected String getKey(final int n) {
            return ((ICUResourceBundleReader.Table)this.value).getKey(n);
        }
        
        @Override
        protected Set handleKeySet() {
            final TreeSet<String> set = new TreeSet<String>();
            final ICUResourceBundleReader.Table table = (ICUResourceBundleReader.Table)this.value;
            while (0 < table.getSize()) {
                set.add(table.getKey(0));
                int n = 0;
                ++n;
            }
            return set;
        }
        
        @Override
        protected int getTableResource(final String s) {
            return ((ICUResourceBundleReader.Table)this.value).getTableResource(s);
        }
        
        @Override
        protected int getTableResource(final int n) {
            return this.getContainerResource(n);
        }
        
        @Override
        protected UResourceBundle handleGetImpl(final String s, final HashMap hashMap, final UResourceBundle uResourceBundle, final int[] array, final boolean[] array2) {
            final int tableItem = ((ICUResourceBundleReader.Table)this.value).findTableItem(s);
            if (array != null) {
                array[0] = tableItem;
            }
            if (tableItem < 0) {
                return null;
            }
            return this.createBundleObject(tableItem, s, hashMap, uResourceBundle, array2);
        }
        
        @Override
        protected UResourceBundle handleGetImpl(final int n, final HashMap hashMap, final UResourceBundle uResourceBundle, final boolean[] array) {
            final String key = ((ICUResourceBundleReader.Table)this.value).getKey(n);
            if (key == null) {
                throw new IndexOutOfBoundsException();
            }
            return this.createBundleObject(n, key, hashMap, uResourceBundle, array);
        }
        
        ResourceTable(final ICUResourceBundleReader icuResourceBundleReader, final String s, final String s2, final int n, final ICUResourceBundleImpl icuResourceBundleImpl) {
            super(icuResourceBundleReader, s, s2, n, icuResourceBundleImpl);
            this.value = icuResourceBundleReader.getTable(n);
            this.createLookupCache();
        }
    }
    
    private static class ResourceContainer extends ICUResourceBundleImpl
    {
        protected ICUResourceBundleReader.Container value;
        
        @Override
        public int getSize() {
            return this.value.getSize();
        }
        
        protected int getContainerResource(final int n) {
            return this.value.getContainerResource(n);
        }
        
        protected UResourceBundle createBundleObject(final int n, final String s, final HashMap hashMap, final UResourceBundle uResourceBundle, final boolean[] array) {
            final int containerResource = this.getContainerResource(n);
            if (containerResource == -1) {
                throw new IndexOutOfBoundsException();
            }
            return this.createBundleObject(s, containerResource, hashMap, uResourceBundle, array);
        }
        
        ResourceContainer(final ICUResourceBundleReader icuResourceBundleReader, final String s, final String s2, final int n, final ICUResourceBundleImpl icuResourceBundleImpl) {
            super(icuResourceBundleReader, s, s2, n, icuResourceBundleImpl);
        }
    }
    
    private static class ResourceArray extends ResourceContainer
    {
        @Override
        protected String[] handleGetStringArray() {
            final String[] array = new String[this.value.getSize()];
            final UResourceBundleIterator iterator = this.getIterator();
            while (iterator.hasNext()) {
                final String[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = iterator.next().getString();
            }
            return array;
        }
        
        @Override
        public String[] getStringArray() {
            return this.handleGetStringArray();
        }
        
        @Override
        protected UResourceBundle handleGetImpl(final String s, final HashMap hashMap, final UResourceBundle uResourceBundle, final int[] array, final boolean[] array2) {
            final int n = (s.length() > 0) ? Integer.valueOf(s) : -1;
            if (array != null) {
                array[0] = n;
            }
            if (n < 0) {
                throw new UResourceTypeMismatchException("Could not get the correct value for index: " + s);
            }
            return this.createBundleObject(n, s, hashMap, uResourceBundle, array2);
        }
        
        @Override
        protected UResourceBundle handleGetImpl(final int n, final HashMap hashMap, final UResourceBundle uResourceBundle, final boolean[] array) {
            return this.createBundleObject(n, Integer.toString(n), hashMap, uResourceBundle, array);
        }
        
        ResourceArray(final ICUResourceBundleReader icuResourceBundleReader, final String s, final String s2, final int n, final ICUResourceBundleImpl icuResourceBundleImpl) {
            super(icuResourceBundleReader, s, s2, n, icuResourceBundleImpl);
            this.value = icuResourceBundleReader.getArray(n);
            this.createLookupCache();
        }
    }
    
    private static final class ResourceIntVector extends ICUResourceBundleImpl
    {
        private int[] value;
        
        @Override
        public int[] getIntVector() {
            return this.value;
        }
        
        ResourceIntVector(final ICUResourceBundleReader icuResourceBundleReader, final String s, final String s2, final int n, final ICUResourceBundleImpl icuResourceBundleImpl) {
            super(icuResourceBundleReader, s, s2, n, icuResourceBundleImpl);
            this.value = icuResourceBundleReader.getIntVector(n);
        }
    }
    
    private static final class ResourceString extends ICUResourceBundleImpl
    {
        private String value;
        
        @Override
        public String getString() {
            return this.value;
        }
        
        ResourceString(final ICUResourceBundleReader icuResourceBundleReader, final String s, final String s2, final int n, final ICUResourceBundleImpl icuResourceBundleImpl) {
            super(icuResourceBundleReader, s, s2, n, icuResourceBundleImpl);
            this.value = icuResourceBundleReader.getString(n);
        }
    }
    
    private static final class ResourceInt extends ICUResourceBundleImpl
    {
        @Override
        public int getInt() {
            return ICUResourceBundleReader.RES_GET_INT(this.resource);
        }
        
        @Override
        public int getUInt() {
            return ICUResourceBundleReader.RES_GET_UINT(this.resource);
        }
        
        ResourceInt(final ICUResourceBundleReader icuResourceBundleReader, final String s, final String s2, final int n, final ICUResourceBundleImpl icuResourceBundleImpl) {
            super(icuResourceBundleReader, s, s2, n, icuResourceBundleImpl);
        }
    }
    
    private static final class ResourceBinary extends ICUResourceBundleImpl
    {
        @Override
        public ByteBuffer getBinary() {
            return this.reader.getBinary(this.resource);
        }
        
        @Override
        public byte[] getBinary(final byte[] array) {
            return this.reader.getBinary(this.resource, array);
        }
        
        ResourceBinary(final ICUResourceBundleReader icuResourceBundleReader, final String s, final String s2, final int n, final ICUResourceBundleImpl icuResourceBundleImpl) {
            super(icuResourceBundleReader, s, s2, n, icuResourceBundleImpl);
        }
    }
}
