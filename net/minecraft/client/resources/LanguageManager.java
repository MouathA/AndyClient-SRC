package net.minecraft.client.resources;

import org.apache.logging.log4j.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class LanguageManager implements IResourceManagerReloadListener
{
    private static final Logger logger;
    private final IMetadataSerializer theMetadataSerializer;
    private String currentLanguage;
    protected static final Locale currentLocale;
    private Map languageMap;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001096";
        logger = LogManager.getLogger();
        currentLocale = new Locale();
    }
    
    public LanguageManager(final IMetadataSerializer theMetadataSerializer, final String currentLanguage) {
        this.languageMap = Maps.newHashMap();
        this.theMetadataSerializer = theMetadataSerializer;
        this.currentLanguage = currentLanguage;
        I18n.setLocale(LanguageManager.currentLocale);
    }
    
    public void parseLanguageMetadata(final List list) {
        this.languageMap.clear();
        final Iterator<IResourcePack> iterator = list.iterator();
        while (iterator.hasNext()) {
            final LanguageMetadataSection languageMetadataSection = (LanguageMetadataSection)iterator.next().getPackMetadata(this.theMetadataSerializer, "language");
            if (languageMetadataSection != null) {
                for (final Language language : languageMetadataSection.getLanguages()) {
                    if (!this.languageMap.containsKey(language.getLanguageCode())) {
                        this.languageMap.put(language.getLanguageCode(), language);
                    }
                }
            }
        }
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        final ArrayList arrayList = Lists.newArrayList("en_US");
        if (!"en_US".equals(this.currentLanguage)) {
            arrayList.add(this.currentLanguage);
        }
        LanguageManager.currentLocale.loadLocaleDataFiles(resourceManager, arrayList);
        StringTranslate.replaceWith(LanguageManager.currentLocale.field_135032_a);
    }
    
    public boolean isCurrentLocaleUnicode() {
        return LanguageManager.currentLocale.isUnicode();
    }
    
    public boolean isCurrentLanguageBidirectional() {
        return this.getCurrentLanguage() != null && this.getCurrentLanguage().isBidirectional();
    }
    
    public void setCurrentLanguage(final Language language) {
        this.currentLanguage = language.getLanguageCode();
    }
    
    public Language getCurrentLanguage() {
        return this.languageMap.containsKey(this.currentLanguage) ? this.languageMap.get(this.currentLanguage) : this.languageMap.get("en_US");
    }
    
    public SortedSet getLanguages() {
        return Sets.newTreeSet(this.languageMap.values());
    }
}
