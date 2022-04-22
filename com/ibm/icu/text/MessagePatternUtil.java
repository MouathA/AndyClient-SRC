package com.ibm.icu.text;

import java.util.*;

public final class MessagePatternUtil
{
    private MessagePatternUtil() {
    }
    
    public static MessageNode buildMessageNode(final String s) {
        return buildMessageNode(new MessagePattern(s));
    }
    
    public static MessageNode buildMessageNode(final MessagePattern messagePattern) {
        final int n = messagePattern.countParts() - 1;
        if (n < 0) {
            throw new IllegalArgumentException("The MessagePattern is empty");
        }
        if (messagePattern.getPartType(0) != MessagePattern.Part.Type.MSG_START) {
            throw new IllegalArgumentException("The MessagePattern does not represent a MessageFormat pattern");
        }
        return buildMessageNode(messagePattern, 0, n);
    }
    
    private static MessageNode buildMessageNode(final MessagePattern messagePattern, final int n, final int n2) {
        int n3 = messagePattern.getPart(n).getLimit();
        final MessageNode messageNode = new MessageNode(null);
        int n4 = n + 1;
        while (true) {
            MessagePattern.Part part = messagePattern.getPart(n4);
            final int index = part.getIndex();
            if (n3 < index) {
                MessageNode.access$500(messageNode, new TextNode(messagePattern.getPatternString().substring(n3, index), null));
            }
            if (n4 == n2) {
                break;
            }
            final MessagePattern.Part.Type type = part.getType();
            if (type == MessagePattern.Part.Type.ARG_START) {
                final int limitPartIndex = messagePattern.getLimitPartIndex(n4);
                MessageNode.access$500(messageNode, buildArgNode(messagePattern, n4, limitPartIndex));
                n4 = limitPartIndex;
                part = messagePattern.getPart(n4);
            }
            else if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                MessageNode.access$500(messageNode, MessageContentsNode.access$600());
            }
            n3 = part.getLimit();
            ++n4;
        }
        return MessageNode.access$700(messageNode);
    }
    
    private static ArgNode buildArgNode(final MessagePattern messagePattern, int n, final int n2) {
        final ArgNode access$800 = ArgNode.access$800();
        final MessagePattern.ArgType access$801 = ArgNode.access$902(access$800, messagePattern.getPart(n).getArgType());
        final MessagePattern.Part part = messagePattern.getPart(++n);
        ArgNode.access$1002(access$800, messagePattern.getSubstring(part));
        if (part.getType() == MessagePattern.Part.Type.ARG_NUMBER) {
            ArgNode.access$1102(access$800, part.getValue());
        }
        ++n;
        switch (access$801) {
            case SIMPLE: {
                ArgNode.access$1202(access$800, messagePattern.getSubstring(messagePattern.getPart(n++)));
                if (n < n2) {
                    ArgNode.access$1302(access$800, messagePattern.getSubstring(messagePattern.getPart(n)));
                    break;
                }
                break;
            }
            case CHOICE: {
                ArgNode.access$1202(access$800, "choice");
                ArgNode.access$1402(access$800, buildChoiceStyleNode(messagePattern, n, n2));
                break;
            }
            case PLURAL: {
                ArgNode.access$1202(access$800, "plural");
                ArgNode.access$1402(access$800, buildPluralStyleNode(messagePattern, n, n2, access$801));
                break;
            }
            case SELECT: {
                ArgNode.access$1202(access$800, "select");
                ArgNode.access$1402(access$800, buildSelectStyleNode(messagePattern, n, n2));
                break;
            }
            case SELECTORDINAL: {
                ArgNode.access$1202(access$800, "selectordinal");
                ArgNode.access$1402(access$800, buildPluralStyleNode(messagePattern, n, n2, access$801));
                break;
            }
        }
        return access$800;
    }
    
    private static ComplexArgStyleNode buildChoiceStyleNode(final MessagePattern messagePattern, int i, final int n) {
        final ComplexArgStyleNode complexArgStyleNode = new ComplexArgStyleNode(MessagePattern.ArgType.CHOICE, null);
        while (i < n) {
            final int n2 = i;
            final double numericValue = messagePattern.getNumericValue(messagePattern.getPart(i));
            i += 2;
            final int limitPartIndex = messagePattern.getLimitPartIndex(i);
            final VariantNode variantNode = new VariantNode(null);
            VariantNode.access$1702(variantNode, messagePattern.getSubstring(messagePattern.getPart(n2 + 1)));
            VariantNode.access$1802(variantNode, numericValue);
            VariantNode.access$1902(variantNode, buildMessageNode(messagePattern, i, limitPartIndex));
            ComplexArgStyleNode.access$2000(complexArgStyleNode, variantNode);
            i = limitPartIndex + 1;
        }
        return ComplexArgStyleNode.access$2100(complexArgStyleNode);
    }
    
    private static ComplexArgStyleNode buildPluralStyleNode(final MessagePattern messagePattern, int i, final int n, final MessagePattern.ArgType argType) {
        final ComplexArgStyleNode complexArgStyleNode = new ComplexArgStyleNode(argType, null);
        final MessagePattern.Part part = messagePattern.getPart(i);
        if (part.getType().hasNumericValue()) {
            ComplexArgStyleNode.access$2202(complexArgStyleNode, true);
            ComplexArgStyleNode.access$2302(complexArgStyleNode, messagePattern.getNumericValue(part));
            ++i;
        }
        while (i < n) {
            final MessagePattern.Part part2 = messagePattern.getPart(i++);
            double numericValue = -1.23456789E8;
            final MessagePattern.Part part3 = messagePattern.getPart(i);
            if (part3.getType().hasNumericValue()) {
                numericValue = messagePattern.getNumericValue(part3);
                ++i;
            }
            final int limitPartIndex = messagePattern.getLimitPartIndex(i);
            final VariantNode variantNode = new VariantNode(null);
            VariantNode.access$1702(variantNode, messagePattern.getSubstring(part2));
            VariantNode.access$1802(variantNode, numericValue);
            VariantNode.access$1902(variantNode, buildMessageNode(messagePattern, i, limitPartIndex));
            ComplexArgStyleNode.access$2000(complexArgStyleNode, variantNode);
            i = limitPartIndex + 1;
        }
        return ComplexArgStyleNode.access$2100(complexArgStyleNode);
    }
    
    private static ComplexArgStyleNode buildSelectStyleNode(final MessagePattern messagePattern, int i, final int n) {
        final ComplexArgStyleNode complexArgStyleNode = new ComplexArgStyleNode(MessagePattern.ArgType.SELECT, null);
        while (i < n) {
            final MessagePattern.Part part = messagePattern.getPart(i++);
            final int limitPartIndex = messagePattern.getLimitPartIndex(i);
            final VariantNode variantNode = new VariantNode(null);
            VariantNode.access$1702(variantNode, messagePattern.getSubstring(part));
            VariantNode.access$1902(variantNode, buildMessageNode(messagePattern, i, limitPartIndex));
            ComplexArgStyleNode.access$2000(complexArgStyleNode, variantNode);
            i = limitPartIndex + 1;
        }
        return ComplexArgStyleNode.access$2100(complexArgStyleNode);
    }
    
    public static class VariantNode extends Node
    {
        private String selector;
        private double numericValue;
        private MessageNode msgNode;
        
        public String getSelector() {
            return this.selector;
        }
        
        public boolean isSelectorNumeric() {
            return this.numericValue != -1.23456789E8;
        }
        
        public double getSelectorValue() {
            return this.numericValue;
        }
        
        public MessageNode getMessage() {
            return this.msgNode;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            if (this.isSelectorNumeric()) {
                sb.append(this.numericValue).append(" (").append(this.selector).append(") {");
            }
            else {
                sb.append(this.selector).append(" {");
            }
            return sb.append(this.msgNode.toString()).append('}').toString();
        }
        
        private VariantNode() {
            super(null);
            this.numericValue = -1.23456789E8;
        }
        
        VariantNode(final MessagePatternUtil$1 object) {
            this();
        }
        
        static String access$1702(final VariantNode variantNode, final String selector) {
            return variantNode.selector = selector;
        }
        
        static double access$1802(final VariantNode variantNode, final double numericValue) {
            return variantNode.numericValue = numericValue;
        }
        
        static MessageNode access$1902(final VariantNode variantNode, final MessageNode msgNode) {
            return variantNode.msgNode = msgNode;
        }
    }
    
    public static class MessageNode extends Node
    {
        private List list;
        
        public List getContents() {
            return this.list;
        }
        
        @Override
        public String toString() {
            return this.list.toString();
        }
        
        private MessageNode() {
            super(null);
            this.list = new ArrayList();
        }
        
        private void addContentsNode(final MessageContentsNode messageContentsNode) {
            if (messageContentsNode instanceof TextNode && !this.list.isEmpty()) {
                final MessageContentsNode messageContentsNode2 = this.list.get(this.list.size() - 1);
                if (messageContentsNode2 instanceof TextNode) {
                    final TextNode textNode = (TextNode)messageContentsNode2;
                    TextNode.access$102(textNode, TextNode.access$100(textNode) + TextNode.access$100((TextNode)messageContentsNode));
                    return;
                }
            }
            this.list.add(messageContentsNode);
        }
        
        private MessageNode freeze() {
            this.list = Collections.unmodifiableList((List<?>)this.list);
            return this;
        }
        
        MessageNode(final MessagePatternUtil$1 object) {
            this();
        }
        
        static void access$500(final MessageNode messageNode, final MessageContentsNode messageContentsNode) {
            messageNode.addContentsNode(messageContentsNode);
        }
        
        static MessageNode access$700(final MessageNode messageNode) {
            return messageNode.freeze();
        }
    }
    
    public static class MessageContentsNode extends Node
    {
        private Type type;
        
        public Type getType() {
            return this.type;
        }
        
        @Override
        public String toString() {
            return "{REPLACE_NUMBER}";
        }
        
        private MessageContentsNode(final Type type) {
            super(null);
            this.type = type;
        }
        
        private static MessageContentsNode createReplaceNumberNode() {
            return new MessageContentsNode(Type.REPLACE_NUMBER);
        }
        
        MessageContentsNode(final Type type, final MessagePatternUtil$1 object) {
            this(type);
        }
        
        static MessageContentsNode access$600() {
            return createReplaceNumberNode();
        }
        
        public enum Type
        {
            TEXT("TEXT", 0), 
            ARG("ARG", 1), 
            REPLACE_NUMBER("REPLACE_NUMBER", 2);
            
            private static final Type[] $VALUES;
            
            private Type(final String s, final int n) {
            }
            
            static {
                $VALUES = new Type[] { Type.TEXT, Type.ARG, Type.REPLACE_NUMBER };
            }
        }
    }
    
    public static class Node
    {
        private Node() {
        }
        
        Node(final MessagePatternUtil$1 object) {
            this();
        }
    }
    
    public static class TextNode extends MessageContentsNode
    {
        private String text;
        
        public String getText() {
            return this.text;
        }
        
        @Override
        public String toString() {
            return "«" + this.text + "»";
        }
        
        private TextNode(final String text) {
            super(Type.TEXT, null);
            this.text = text;
        }
        
        static String access$102(final TextNode textNode, final String text) {
            return textNode.text = text;
        }
        
        static String access$100(final TextNode textNode) {
            return textNode.text;
        }
        
        TextNode(final String s, final MessagePatternUtil$1 object) {
            this(s);
        }
    }
    
    public static class ComplexArgStyleNode extends Node
    {
        private MessagePattern.ArgType argType;
        private double offset;
        private boolean explicitOffset;
        private List list;
        
        public MessagePattern.ArgType getArgType() {
            return this.argType;
        }
        
        public boolean hasExplicitOffset() {
            return this.explicitOffset;
        }
        
        public double getOffset() {
            return this.offset;
        }
        
        public List getVariants() {
            return this.list;
        }
        
        public VariantNode getVariantsByType(final List list, final List list2) {
            if (list != null) {
                list.clear();
            }
            list2.clear();
            VariantNode variantNode = null;
            for (final VariantNode variantNode2 : this.list) {
                if (variantNode2.isSelectorNumeric()) {
                    list.add(variantNode2);
                }
                else if ("other".equals(variantNode2.getSelector())) {
                    if (variantNode != null) {
                        continue;
                    }
                    variantNode = variantNode2;
                }
                else {
                    list2.add(variantNode2);
                }
            }
            return variantNode;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append('(').append(this.argType.toString()).append(" style) ");
            if (this.hasExplicitOffset()) {
                sb.append("offset:").append(this.offset).append(' ');
            }
            return sb.append(this.list.toString()).toString();
        }
        
        private ComplexArgStyleNode(final MessagePattern.ArgType argType) {
            super(null);
            this.list = new ArrayList();
            this.argType = argType;
        }
        
        private void addVariant(final VariantNode variantNode) {
            this.list.add(variantNode);
        }
        
        private ComplexArgStyleNode freeze() {
            this.list = Collections.unmodifiableList((List<?>)this.list);
            return this;
        }
        
        ComplexArgStyleNode(final MessagePattern.ArgType argType, final MessagePatternUtil$1 object) {
            this(argType);
        }
        
        static void access$2000(final ComplexArgStyleNode complexArgStyleNode, final VariantNode variantNode) {
            complexArgStyleNode.addVariant(variantNode);
        }
        
        static ComplexArgStyleNode access$2100(final ComplexArgStyleNode complexArgStyleNode) {
            return complexArgStyleNode.freeze();
        }
        
        static boolean access$2202(final ComplexArgStyleNode complexArgStyleNode, final boolean explicitOffset) {
            return complexArgStyleNode.explicitOffset = explicitOffset;
        }
        
        static double access$2302(final ComplexArgStyleNode complexArgStyleNode, final double offset) {
            return complexArgStyleNode.offset = offset;
        }
    }
    
    public static class ArgNode extends MessageContentsNode
    {
        private MessagePattern.ArgType argType;
        private String name;
        private int number;
        private String typeName;
        private String style;
        private ComplexArgStyleNode complexStyle;
        
        public MessagePattern.ArgType getArgType() {
            return this.argType;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getNumber() {
            return this.number;
        }
        
        public String getTypeName() {
            return this.typeName;
        }
        
        public String getSimpleStyle() {
            return this.style;
        }
        
        public ComplexArgStyleNode getComplexStyle() {
            return this.complexStyle;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append('{').append(this.name);
            if (this.argType != MessagePattern.ArgType.NONE) {
                sb.append(',').append(this.typeName);
                if (this.argType == MessagePattern.ArgType.SIMPLE) {
                    if (this.style != null) {
                        sb.append(',').append(this.style);
                    }
                }
                else {
                    sb.append(',').append(this.complexStyle.toString());
                }
            }
            return sb.append('}').toString();
        }
        
        private ArgNode() {
            super(Type.ARG, null);
            this.number = -1;
        }
        
        private static ArgNode createArgNode() {
            return new ArgNode();
        }
        
        static ArgNode access$800() {
            return createArgNode();
        }
        
        static MessagePattern.ArgType access$902(final ArgNode argNode, final MessagePattern.ArgType argType) {
            return argNode.argType = argType;
        }
        
        static String access$1002(final ArgNode argNode, final String name) {
            return argNode.name = name;
        }
        
        static int access$1102(final ArgNode argNode, final int number) {
            return argNode.number = number;
        }
        
        static String access$1202(final ArgNode argNode, final String typeName) {
            return argNode.typeName = typeName;
        }
        
        static String access$1302(final ArgNode argNode, final String style) {
            return argNode.style = style;
        }
        
        static ComplexArgStyleNode access$1402(final ArgNode argNode, final ComplexArgStyleNode complexStyle) {
            return argNode.complexStyle = complexStyle;
        }
    }
}
