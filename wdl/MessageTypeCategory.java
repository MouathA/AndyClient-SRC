package wdl;

import net.minecraft.client.resources.*;

public abstract class MessageTypeCategory
{
    public final String internalName;
    static final MessageTypeCategory CORE_RECOMMENDED;
    static final MessageTypeCategory CORE_DEBUG;
    
    static {
        CORE_RECOMMENDED = new I18nableMessageTypeCategory("CORE_RECOMMENDED", "wdl.messages.category.core_recommended");
        CORE_DEBUG = new I18nableMessageTypeCategory("CORE_DEBUG", "wdl.messages.category.core_debug");
    }
    
    public MessageTypeCategory(final String internalName) {
        this.internalName = internalName;
    }
    
    public abstract String getDisplayName();
    
    @Override
    public String toString() {
        return "MessageTypeCategory [internalName=" + this.internalName + ", displayName=" + this.getDisplayName() + "]";
    }
    
    @Override
    public int hashCode() {
        final int n = 31 + ((this.internalName == null) ? 0 : this.internalName.hashCode());
        return 1;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final MessageTypeCategory messageTypeCategory = (MessageTypeCategory)o;
        if (this.internalName == null) {
            if (messageTypeCategory.internalName != null) {
                return false;
            }
        }
        else if (!this.internalName.equals(messageTypeCategory.internalName)) {
            return false;
        }
        return true;
    }
    
    public static class I18nableMessageTypeCategory extends MessageTypeCategory
    {
        public final String i18nKey;
        
        public I18nableMessageTypeCategory(final String s, final String i18nKey) {
            super(s);
            this.i18nKey = i18nKey;
        }
        
        @Override
        public String getDisplayName() {
            return I18n.format(this.i18nKey, new Object[0]);
        }
    }
}
