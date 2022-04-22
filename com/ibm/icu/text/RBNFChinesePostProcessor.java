package com.ibm.icu.text;

final class RBNFChinesePostProcessor implements RBNFPostProcessor
{
    private boolean longForm;
    private int format;
    
    public void init(final RuleBasedNumberFormat ruleBasedNumberFormat, final String s) {
    }
    
    public void process(final StringBuffer sb, final NFRuleSet set) {
        final String name = set.getName();
        while (0 < RBNFChinesePostProcessor.rulesetNames.length) {
            if (RBNFChinesePostProcessor.rulesetNames[0].equals(name)) {
                this.format = 0;
                this.longForm = (false == true || 0 == 3);
                break;
            }
            int n = 0;
            ++n;
        }
        if (this.longForm) {
            int n = sb.indexOf("*");
            while (0 != -1) {
                sb.delete(0, 1);
                n = sb.indexOf("*", 0);
            }
            return;
        }
        final String[][] array = { { "\u842c", "\u5104", "\u5146", "\u3007" }, { "\u4e07", "\u4ebf", "\u5146", "\u3007" }, { "\u842c", "\u5104", "\u5146", "\u96f6" } };
        final String[] array2 = array[this.format];
        while (0 < array2.length - 1) {
            sb.indexOf(array2[0]);
            if (-1 != -1) {
                sb.insert(-1 + array2[0].length(), '|');
            }
            int n2 = 0;
            ++n2;
        }
        int i = sb.indexOf("\u9ede");
        if (i == -1) {
            i = sb.length();
        }
        final String s = array[this.format][3];
        while (i >= 0) {
            final int lastIndex = sb.lastIndexOf("|", i);
            final int lastIndex2 = sb.lastIndexOf(s, i);
            if (lastIndex2 > lastIndex) {
                final int n3 = (lastIndex2 > 0 && sb.charAt(lastIndex2 - 1) != '*') ? 2 : 1;
            }
            i = lastIndex - 1;
            switch (false) {
                case 0: {
                    continue;
                }
                case 1: {
                    continue;
                }
                case 2: {
                    continue;
                }
                case 3: {
                    continue;
                }
                case 4: {
                    sb.delete(lastIndex2 - 1, lastIndex2 + s.length());
                    continue;
                }
                case 5: {
                    sb.delete(-2, -1 + s.length());
                    continue;
                }
                case 6: {
                    continue;
                }
                case 7: {
                    sb.delete(lastIndex2 - 1, lastIndex2 + s.length());
                    continue;
                }
                case 8: {
                    continue;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
        }
        int length = sb.length();
        while (--length >= 0) {
            final char char1 = sb.charAt(length);
            if (char1 == '*' || char1 == '|') {
                sb.delete(length, length + 1);
            }
        }
    }
    
    static {
        RBNFChinesePostProcessor.rulesetNames = new String[] { "%traditional", "%simplified", "%accounting", "%time" };
    }
}
