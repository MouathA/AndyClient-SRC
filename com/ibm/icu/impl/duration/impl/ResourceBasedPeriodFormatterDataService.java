package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.*;
import java.util.*;
import java.io.*;

public class ResourceBasedPeriodFormatterDataService extends PeriodFormatterDataService
{
    private Collection availableLocales;
    private PeriodFormatterData lastData;
    private String lastLocale;
    private Map cache;
    private static final String PATH = "data/";
    private static final ResourceBasedPeriodFormatterDataService singleton;
    
    public static ResourceBasedPeriodFormatterDataService getInstance() {
        return ResourceBasedPeriodFormatterDataService.singleton;
    }
    
    private ResourceBasedPeriodFormatterDataService() {
        this.lastData = null;
        this.lastLocale = null;
        this.cache = new HashMap();
        final ArrayList<String> list = new ArrayList<String>();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ICUData.getRequiredStream(this.getClass(), "data/index.txt"), "UTF-8"));
        String line;
        while (null != (line = bufferedReader.readLine())) {
            final String trim = line.trim();
            if (!trim.startsWith("#")) {
                if (trim.length() == 0) {
                    continue;
                }
                list.add(trim);
            }
        }
        bufferedReader.close();
        this.availableLocales = Collections.unmodifiableList((List<?>)list);
    }
    
    @Override
    public PeriodFormatterData get(String substring) {
        final int index = substring.indexOf(64);
        if (index != -1) {
            substring = substring.substring(0, index);
        }
        // monitorenter(this)
        if (this.lastLocale != null && this.lastLocale.equals(substring)) {
            // monitorexit(this)
            return this.lastData;
        }
        PeriodFormatterData lastData2 = this.cache.get(substring);
        if (lastData2 == null) {
            String substring2 = substring;
            while (!this.availableLocales.contains(substring2)) {
                final int lastIndex = substring2.lastIndexOf("_");
                if (lastIndex > -1) {
                    substring2 = substring2.substring(0, lastIndex);
                }
                else {
                    if ("test".equals(substring2)) {
                        substring2 = null;
                        break;
                    }
                    substring2 = "test";
                }
            }
            if (substring2 == null) {
                throw new MissingResourceException("Duration data not found for  " + substring, "data/", substring);
            }
            final String string = "data/pfd_" + substring2 + ".xml";
            final InputStream stream = ICUData.getStream(this.getClass(), string);
            if (stream == null) {
                throw new MissingResourceException("no resource named " + string, string, "");
            }
            final DataRecord read = DataRecord.read(substring2, new XMLRecordReader(new InputStreamReader(stream, "UTF-8")));
            if (read != null) {
                lastData2 = new PeriodFormatterData(substring, read);
            }
            this.cache.put(substring, lastData2);
        }
        this.lastData = lastData2;
        this.lastLocale = substring;
        // monitorexit(this)
        return lastData2;
    }
    
    @Override
    public Collection getAvailableLocales() {
        return this.availableLocales;
    }
    
    static {
        singleton = new ResourceBasedPeriodFormatterDataService();
    }
}
