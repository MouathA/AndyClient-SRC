package net.minecraft.client.resources;

import com.google.common.base.*;
import java.util.regex.*;
import net.minecraft.util.*;
import java.util.*;
import java.io.*;
import org.apache.commons.io.*;
import com.google.common.collect.*;

public class Locale
{
    private static final Splitter splitter;
    private static final Pattern field_135031_c;
    Map field_135032_a;
    private boolean field_135029_d;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001097";
        splitter = Splitter.on('=').limit(2);
        field_135031_c = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    }
    
    public Locale() {
        this.field_135032_a = Maps.newHashMap();
    }
    
    public synchronized void loadLocaleDataFiles(final IResourceManager resourceManager, final List list) {
        this.field_135032_a.clear();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            final String format = String.format("lang/%s.lang", iterator.next());
            final Iterator<String> iterator2 = resourceManager.getResourceDomains().iterator();
            while (iterator2.hasNext()) {
                this.loadLocaleData(resourceManager.getAllResources(new ResourceLocation(iterator2.next(), format)));
            }
        }
        this.checkUnicode();
    }
    
    public boolean isUnicode() {
        return this.field_135029_d;
    }
    
    private void checkUnicode() {
        this.field_135029_d = false;
        for (final String s : this.field_135032_a.values()) {
            while (0 < s.length()) {
                if (s.charAt(0) >= '\u0100') {
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
        }
        this.field_135029_d = (0 / (float)0 > 0.1);
    }
    
    private void loadLocaleData(final List list) throws IOException {
        final Iterator<IResource> iterator = list.iterator();
        while (iterator.hasNext()) {
            final InputStream inputStream = iterator.next().getInputStream();
            this.loadLocaleData(inputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    private void loadLocaleData(final InputStream inputStream) throws IOException {
        for (final String s : IOUtils.readLines(inputStream, Charsets.UTF_8)) {
            if (!s.isEmpty() && s.charAt(0) != '#') {
                final String[] array = (String[])Iterables.toArray(Locale.splitter.split(s), String.class);
                if (array == null || array.length != 2) {
                    continue;
                }
                this.field_135032_a.put(array[0], Locale.field_135031_c.matcher(array[1]).replaceAll("%$1s"));
            }
        }
    }
    
    private String translateKeyPrivate(final String s) {
        final String s2 = this.field_135032_a.get(s);
        return (s2 == null) ? s : s2;
    }
    
    public String formatMessage(final String s, final Object[] array) {
        return String.format(this.translateKeyPrivate(s), array);
    }
}
