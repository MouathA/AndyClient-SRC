package org.apache.logging.log4j.core.pattern;

public final class FormattingInfo
{
    private static final char[] SPACES;
    private static final FormattingInfo DEFAULT;
    private final int minLength;
    private final int maxLength;
    private final boolean leftAlign;
    
    public FormattingInfo(final boolean leftAlign, final int minLength, final int maxLength) {
        this.leftAlign = leftAlign;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }
    
    public static FormattingInfo getDefault() {
        return FormattingInfo.DEFAULT;
    }
    
    public boolean isLeftAligned() {
        return this.leftAlign;
    }
    
    public int getMinLength() {
        return this.minLength;
    }
    
    public int getMaxLength() {
        return this.maxLength;
    }
    
    public void format(final int n, final StringBuilder sb) {
        final int n2 = sb.length() - n;
        if (n2 > this.maxLength) {
            sb.delete(n, sb.length() - this.maxLength);
        }
        else if (n2 < this.minLength) {
            if (this.leftAlign) {
                final int length = sb.length();
                sb.setLength(n + this.minLength);
                for (int i = length; i < sb.length(); ++i) {
                    sb.setCharAt(i, ' ');
                }
            }
            else {
                int j;
                for (j = this.minLength - n2; j > FormattingInfo.SPACES.length; j -= FormattingInfo.SPACES.length) {
                    sb.insert(n, FormattingInfo.SPACES);
                }
                sb.insert(n, FormattingInfo.SPACES, 0, j);
            }
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[leftAlign=");
        sb.append(this.leftAlign);
        sb.append(", maxLength=");
        sb.append(this.maxLength);
        sb.append(", minLength=");
        sb.append(this.minLength);
        sb.append("]");
        return sb.toString();
    }
    
    static {
        SPACES = new char[] { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };
        DEFAULT = new FormattingInfo(false, 0, Integer.MAX_VALUE);
    }
}
