package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.util.*;
import org.yaml.snakeyaml.reader.*;
import org.yaml.snakeyaml.scanner.*;
import org.yaml.snakeyaml.*;
import java.util.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.tokens.*;
import org.yaml.snakeyaml.events.*;

public class ParserImpl implements Parser
{
    private static final Map DEFAULT_TAGS;
    protected final Scanner scanner;
    private Event currentEvent;
    private final ArrayStack states;
    private final ArrayStack marks;
    private Production state;
    private VersionTagsTuple directives;
    
    public ParserImpl(final StreamReader streamReader) {
        this(new ScannerImpl(streamReader));
    }
    
    public ParserImpl(final Scanner scanner) {
        this.scanner = scanner;
        this.currentEvent = null;
        this.directives = new VersionTagsTuple(null, new HashMap(ParserImpl.DEFAULT_TAGS));
        this.states = new ArrayStack(100);
        this.marks = new ArrayStack(10);
        this.state = new ParseStreamStart(null);
    }
    
    @Override
    public boolean checkEvent(final Event.ID id) {
        this.peekEvent();
        return this.currentEvent != null && this.currentEvent.is(id);
    }
    
    @Override
    public Event peekEvent() {
        if (this.currentEvent == null && this.state != null) {
            this.currentEvent = this.state.produce();
        }
        return this.currentEvent;
    }
    
    @Override
    public Event getEvent() {
        this.peekEvent();
        final Event currentEvent = this.currentEvent;
        this.currentEvent = null;
        return currentEvent;
    }
    
    private VersionTagsTuple processDirectives() {
        DumperOptions.Version version = null;
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        while (this.scanner.checkToken(Token.ID.Directive)) {
            final DirectiveToken directiveToken = (DirectiveToken)this.scanner.getToken();
            if (directiveToken.getName().equals("YAML")) {
                if (version != null) {
                    throw new ParserException(null, null, "found duplicate YAML directive", directiveToken.getStartMark());
                }
                final List value = directiveToken.getValue();
                if (value.get(0) != 1) {
                    throw new ParserException(null, null, "found incompatible YAML document (version 1.* is required)", directiveToken.getStartMark());
                }
                switch (value.get(1)) {
                    case 0: {
                        version = DumperOptions.Version.V1_0;
                        continue;
                    }
                    default: {
                        version = DumperOptions.Version.V1_1;
                        continue;
                    }
                }
            }
            else {
                if (!directiveToken.getName().equals("TAG")) {
                    continue;
                }
                final List value2 = directiveToken.getValue();
                final String s = value2.get(0);
                final String s2 = value2.get(1);
                if (hashMap.containsKey(s)) {
                    throw new ParserException(null, null, "duplicate tag handle " + s, directiveToken.getStartMark());
                }
                hashMap.put(s, s2);
            }
        }
        if (version != null || !hashMap.isEmpty()) {
            for (final String s3 : ParserImpl.DEFAULT_TAGS.keySet()) {
                if (!hashMap.containsKey(s3)) {
                    hashMap.put(s3, (String)ParserImpl.DEFAULT_TAGS.get(s3));
                }
            }
            this.directives = new VersionTagsTuple(version, hashMap);
        }
        return this.directives;
    }
    
    private Event parseFlowNode() {
        return this.parseNode(false, false);
    }
    
    private Event parseBlockNodeOrIndentlessSequence() {
        return this.parseNode(true, true);
    }
    
    private Event parseNode(final boolean b, final boolean b2) {
        Mark startMark = null;
        Mark mark = null;
        Mark mark2 = null;
        NodeEvent nodeEvent;
        if (this.scanner.checkToken(Token.ID.Alias)) {
            final AliasToken aliasToken = (AliasToken)this.scanner.getToken();
            nodeEvent = new AliasEvent(aliasToken.getValue(), aliasToken.getStartMark(), aliasToken.getEndMark());
            this.state = (Production)this.states.pop();
        }
        else {
            String s = null;
            TagTuple tagTuple = null;
            if (this.scanner.checkToken(Token.ID.Anchor)) {
                final AnchorToken anchorToken = (AnchorToken)this.scanner.getToken();
                startMark = anchorToken.getStartMark();
                mark = anchorToken.getEndMark();
                s = anchorToken.getValue();
                if (this.scanner.checkToken(Token.ID.Tag)) {
                    final TagToken tagToken = (TagToken)this.scanner.getToken();
                    mark2 = tagToken.getStartMark();
                    mark = tagToken.getEndMark();
                    tagTuple = tagToken.getValue();
                }
            }
            else if (this.scanner.checkToken(Token.ID.Tag)) {
                final TagToken tagToken2 = (TagToken)this.scanner.getToken();
                startMark = (mark2 = tagToken2.getStartMark());
                mark = tagToken2.getEndMark();
                tagTuple = tagToken2.getValue();
                if (this.scanner.checkToken(Token.ID.Anchor)) {
                    final AnchorToken anchorToken2 = (AnchorToken)this.scanner.getToken();
                    mark = anchorToken2.getEndMark();
                    s = anchorToken2.getValue();
                }
            }
            String string = null;
            if (tagTuple != null) {
                final String handle = tagTuple.getHandle();
                final String suffix = tagTuple.getSuffix();
                if (handle != null) {
                    if (!this.directives.getTags().containsKey(handle)) {
                        throw new ParserException("while parsing a node", startMark, "found undefined tag handle " + handle, mark2);
                    }
                    string = this.directives.getTags().get(handle) + suffix;
                }
                else {
                    string = suffix;
                }
            }
            if (startMark == null) {
                startMark = (mark = this.scanner.peekToken().getStartMark());
            }
            final boolean b3 = string == null || string.equals("!");
            if (b2 && this.scanner.checkToken(Token.ID.BlockEntry)) {
                nodeEvent = new SequenceStartEvent(s, string, b3, startMark, this.scanner.peekToken().getEndMark(), DumperOptions.FlowStyle.BLOCK);
                this.state = new ParseIndentlessSequenceEntry(null);
            }
            else if (this.scanner.checkToken(Token.ID.Scalar)) {
                final ScalarToken scalarToken = (ScalarToken)this.scanner.getToken();
                final Mark endMark = scalarToken.getEndMark();
                ImplicitTuple implicitTuple;
                if ((scalarToken.getPlain() && string == null) || "!".equals(string)) {
                    implicitTuple = new ImplicitTuple(true, false);
                }
                else if (string == null) {
                    implicitTuple = new ImplicitTuple(false, true);
                }
                else {
                    implicitTuple = new ImplicitTuple(false, false);
                }
                nodeEvent = new ScalarEvent(s, string, implicitTuple, scalarToken.getValue(), startMark, endMark, scalarToken.getStyle());
                this.state = (Production)this.states.pop();
            }
            else if (this.scanner.checkToken(Token.ID.FlowSequenceStart)) {
                nodeEvent = new SequenceStartEvent(s, string, b3, startMark, this.scanner.peekToken().getEndMark(), DumperOptions.FlowStyle.FLOW);
                this.state = new ParseFlowSequenceFirstEntry(null);
            }
            else if (this.scanner.checkToken(Token.ID.FlowMappingStart)) {
                nodeEvent = new MappingStartEvent(s, string, b3, startMark, this.scanner.peekToken().getEndMark(), DumperOptions.FlowStyle.FLOW);
                this.state = new ParseFlowMappingFirstKey(null);
            }
            else if (b && this.scanner.checkToken(Token.ID.BlockSequenceStart)) {
                nodeEvent = new SequenceStartEvent(s, string, b3, startMark, this.scanner.peekToken().getStartMark(), DumperOptions.FlowStyle.BLOCK);
                this.state = new ParseBlockSequenceFirstEntry(null);
            }
            else if (b && this.scanner.checkToken(Token.ID.BlockMappingStart)) {
                nodeEvent = new MappingStartEvent(s, string, b3, startMark, this.scanner.peekToken().getStartMark(), DumperOptions.FlowStyle.BLOCK);
                this.state = new ParseBlockMappingFirstKey(null);
            }
            else {
                if (s == null && string == null) {
                    String s2;
                    if (b) {
                        s2 = "block";
                    }
                    else {
                        s2 = "flow";
                    }
                    final Token peekToken = this.scanner.peekToken();
                    throw new ParserException("while parsing a " + s2 + " node", startMark, "expected the node content, but found '" + peekToken.getTokenId() + "'", peekToken.getStartMark());
                }
                nodeEvent = new ScalarEvent(s, string, new ImplicitTuple(b3, false), "", startMark, mark, DumperOptions.ScalarStyle.PLAIN);
                this.state = (Production)this.states.pop();
            }
        }
        return nodeEvent;
    }
    
    private Event processEmptyScalar(final Mark mark) {
        return new ScalarEvent(null, null, new ImplicitTuple(true, false), "", mark, mark, DumperOptions.ScalarStyle.PLAIN);
    }
    
    static Production access$102(final ParserImpl parserImpl, final Production state) {
        return parserImpl.state = state;
    }
    
    static VersionTagsTuple access$302(final ParserImpl parserImpl, final VersionTagsTuple directives) {
        return parserImpl.directives = directives;
    }
    
    static Map access$400() {
        return ParserImpl.DEFAULT_TAGS;
    }
    
    static ArrayStack access$600(final ParserImpl parserImpl) {
        return parserImpl.states;
    }
    
    static VersionTagsTuple access$900(final ParserImpl parserImpl) {
        return parserImpl.processDirectives();
    }
    
    static ArrayStack access$1100(final ParserImpl parserImpl) {
        return parserImpl.marks;
    }
    
    static Event access$1200(final ParserImpl parserImpl, final Mark mark) {
        return parserImpl.processEmptyScalar(mark);
    }
    
    static Event access$1300(final ParserImpl parserImpl, final boolean b, final boolean b2) {
        return parserImpl.parseNode(b, b2);
    }
    
    static Event access$2200(final ParserImpl parserImpl) {
        return parserImpl.parseBlockNodeOrIndentlessSequence();
    }
    
    static Event access$2400(final ParserImpl parserImpl) {
        return parserImpl.parseFlowNode();
    }
    
    static {
        (DEFAULT_TAGS = new HashMap()).put("!", "!");
        ParserImpl.DEFAULT_TAGS.put("!!", "tag:yaml.org,2002:");
    }
    
    private class ParseFlowMappingEmptyValue implements Production
    {
        final ParserImpl this$0;
        
        private ParseFlowMappingEmptyValue(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowMappingKey(false));
            return ParserImpl.access$1200(this.this$0, this.this$0.scanner.peekToken().getStartMark());
        }
        
        ParseFlowMappingEmptyValue(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseFlowMappingKey implements Production
    {
        private boolean first;
        final ParserImpl this$0;
        
        public ParseFlowMappingKey(final ParserImpl this$0, final boolean first) {
            this.this$0 = this$0;
            this.first = false;
            this.first = first;
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.FlowMappingEnd)) {
                if (!this.first) {
                    if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry)) {
                        final Token peekToken = this.this$0.scanner.peekToken();
                        throw new ParserException("while parsing a flow mapping", (Mark)ParserImpl.access$1100(this.this$0).pop(), "expected ',' or '}', but got " + peekToken.getTokenId(), peekToken.getStartMark());
                    }
                    this.this$0.scanner.getToken();
                }
                if (this.this$0.scanner.checkToken(Token.ID.Key)) {
                    final Token token = this.this$0.scanner.getToken();
                    if (!this.this$0.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
                        ParserImpl.access$600(this.this$0).push(this.this$0.new ParseFlowMappingValue(null));
                        return ParserImpl.access$2400(this.this$0);
                    }
                    ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowMappingValue(null));
                    return ParserImpl.access$1200(this.this$0, token.getEndMark());
                }
                else if (!this.this$0.scanner.checkToken(Token.ID.FlowMappingEnd)) {
                    ParserImpl.access$600(this.this$0).push(this.this$0.new ParseFlowMappingEmptyValue(null));
                    return ParserImpl.access$2400(this.this$0);
                }
            }
            final Token token2 = this.this$0.scanner.getToken();
            final MappingEndEvent mappingEndEvent = new MappingEndEvent(token2.getStartMark(), token2.getEndMark());
            ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$600(this.this$0).pop());
            ParserImpl.access$1100(this.this$0).pop();
            return mappingEndEvent;
        }
    }
    
    private class ParseFlowMappingValue implements Production
    {
        final ParserImpl this$0;
        
        private ParseFlowMappingValue(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.Value)) {
                ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowMappingKey(false));
                return ParserImpl.access$1200(this.this$0, this.this$0.scanner.peekToken().getStartMark());
            }
            final Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
                ParserImpl.access$600(this.this$0).push(this.this$0.new ParseFlowMappingKey(false));
                return ParserImpl.access$2400(this.this$0);
            }
            ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowMappingKey(false));
            return ParserImpl.access$1200(this.this$0, token.getEndMark());
        }
        
        ParseFlowMappingValue(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseFlowMappingFirstKey implements Production
    {
        final ParserImpl this$0;
        
        private ParseFlowMappingFirstKey(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            ParserImpl.access$1100(this.this$0).push(this.this$0.scanner.getToken().getStartMark());
            return this.this$0.new ParseFlowMappingKey(true).produce();
        }
        
        ParseFlowMappingFirstKey(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseFlowSequenceEntryMappingEnd implements Production
    {
        final ParserImpl this$0;
        
        private ParseFlowSequenceEntryMappingEnd(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowSequenceEntry(false));
            final Token peekToken = this.this$0.scanner.peekToken();
            return new MappingEndEvent(peekToken.getStartMark(), peekToken.getEndMark());
        }
        
        ParseFlowSequenceEntryMappingEnd(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseFlowSequenceEntry implements Production
    {
        private boolean first;
        final ParserImpl this$0;
        
        public ParseFlowSequenceEntry(final ParserImpl this$0, final boolean first) {
            this.this$0 = this$0;
            this.first = false;
            this.first = first;
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
                if (!this.first) {
                    if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry)) {
                        final Token peekToken = this.this$0.scanner.peekToken();
                        throw new ParserException("while parsing a flow sequence", (Mark)ParserImpl.access$1100(this.this$0).pop(), "expected ',' or ']', but got " + peekToken.getTokenId(), peekToken.getStartMark());
                    }
                    this.this$0.scanner.getToken();
                }
                if (this.this$0.scanner.checkToken(Token.ID.Key)) {
                    final Token peekToken2 = this.this$0.scanner.peekToken();
                    final MappingStartEvent mappingStartEvent = new MappingStartEvent(null, null, true, peekToken2.getStartMark(), peekToken2.getEndMark(), DumperOptions.FlowStyle.FLOW);
                    ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowSequenceEntryMappingKey(null));
                    return mappingStartEvent;
                }
                if (!this.this$0.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
                    ParserImpl.access$600(this.this$0).push(this.this$0.new ParseFlowSequenceEntry(false));
                    return ParserImpl.access$2400(this.this$0);
                }
            }
            final Token token = this.this$0.scanner.getToken();
            final SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$600(this.this$0).pop());
            ParserImpl.access$1100(this.this$0).pop();
            return sequenceEndEvent;
        }
    }
    
    private class ParseFlowSequenceEntryMappingKey implements Production
    {
        final ParserImpl this$0;
        
        private ParseFlowSequenceEntryMappingKey(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            final Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
                ParserImpl.access$600(this.this$0).push(this.this$0.new ParseFlowSequenceEntryMappingValue(null));
                return ParserImpl.access$2400(this.this$0);
            }
            ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowSequenceEntryMappingValue(null));
            return ParserImpl.access$1200(this.this$0, token.getEndMark());
        }
        
        ParseFlowSequenceEntryMappingKey(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseFlowSequenceEntryMappingValue implements Production
    {
        final ParserImpl this$0;
        
        private ParseFlowSequenceEntryMappingValue(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.Value)) {
                ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowSequenceEntryMappingEnd(null));
                return ParserImpl.access$1200(this.this$0, this.this$0.scanner.peekToken().getStartMark());
            }
            final Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
                ParserImpl.access$600(this.this$0).push(this.this$0.new ParseFlowSequenceEntryMappingEnd(null));
                return ParserImpl.access$2400(this.this$0);
            }
            ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowSequenceEntryMappingEnd(null));
            return ParserImpl.access$1200(this.this$0, token.getEndMark());
        }
        
        ParseFlowSequenceEntryMappingValue(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseFlowSequenceFirstEntry implements Production
    {
        final ParserImpl this$0;
        
        private ParseFlowSequenceFirstEntry(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            ParserImpl.access$1100(this.this$0).push(this.this$0.scanner.getToken().getStartMark());
            return this.this$0.new ParseFlowSequenceEntry(true).produce();
        }
        
        ParseFlowSequenceFirstEntry(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseBlockMappingValue implements Production
    {
        final ParserImpl this$0;
        
        private ParseBlockMappingValue(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.Value)) {
                ParserImpl.access$102(this.this$0, this.this$0.new ParseBlockMappingKey(null));
                return ParserImpl.access$1200(this.this$0, this.this$0.scanner.peekToken().getStartMark());
            }
            final Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                ParserImpl.access$600(this.this$0).push(this.this$0.new ParseBlockMappingKey(null));
                return ParserImpl.access$2200(this.this$0);
            }
            ParserImpl.access$102(this.this$0, this.this$0.new ParseBlockMappingKey(null));
            return ParserImpl.access$1200(this.this$0, token.getEndMark());
        }
        
        ParseBlockMappingValue(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseBlockMappingKey implements Production
    {
        final ParserImpl this$0;
        
        private ParseBlockMappingKey(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Key)) {
                final Token token = this.this$0.scanner.getToken();
                if (!this.this$0.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                    ParserImpl.access$600(this.this$0).push(this.this$0.new ParseBlockMappingValue(null));
                    return ParserImpl.access$2200(this.this$0);
                }
                ParserImpl.access$102(this.this$0, this.this$0.new ParseBlockMappingValue(null));
                return ParserImpl.access$1200(this.this$0, token.getEndMark());
            }
            else {
                if (!this.this$0.scanner.checkToken(Token.ID.BlockEnd)) {
                    final Token peekToken = this.this$0.scanner.peekToken();
                    throw new ParserException("while parsing a block mapping", (Mark)ParserImpl.access$1100(this.this$0).pop(), "expected <block end>, but found '" + peekToken.getTokenId() + "'", peekToken.getStartMark());
                }
                final Token token2 = this.this$0.scanner.getToken();
                final MappingEndEvent mappingEndEvent = new MappingEndEvent(token2.getStartMark(), token2.getEndMark());
                ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$600(this.this$0).pop());
                ParserImpl.access$1100(this.this$0).pop();
                return mappingEndEvent;
            }
        }
        
        ParseBlockMappingKey(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseBlockMappingFirstKey implements Production
    {
        final ParserImpl this$0;
        
        private ParseBlockMappingFirstKey(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            ParserImpl.access$1100(this.this$0).push(this.this$0.scanner.getToken().getStartMark());
            return this.this$0.new ParseBlockMappingKey(null).produce();
        }
        
        ParseBlockMappingFirstKey(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseIndentlessSequenceEntry implements Production
    {
        final ParserImpl this$0;
        
        private ParseIndentlessSequenceEntry(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry)) {
                final Token peekToken = this.this$0.scanner.peekToken();
                final SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(peekToken.getStartMark(), peekToken.getEndMark());
                ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$600(this.this$0).pop());
                return sequenceEndEvent;
            }
            final Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry, Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                ParserImpl.access$600(this.this$0).push(this.this$0.new ParseIndentlessSequenceEntry());
                return this.this$0.new ParseBlockNode(null).produce();
            }
            ParserImpl.access$102(this.this$0, this.this$0.new ParseIndentlessSequenceEntry());
            return ParserImpl.access$1200(this.this$0, token.getEndMark());
        }
        
        ParseIndentlessSequenceEntry(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseBlockNode implements Production
    {
        final ParserImpl this$0;
        
        private ParseBlockNode(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            return ParserImpl.access$1300(this.this$0, true, false);
        }
        
        ParseBlockNode(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseBlockSequenceEntry implements Production
    {
        final ParserImpl this$0;
        
        private ParseBlockSequenceEntry(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.BlockEntry)) {
                final BlockEntryToken blockEntryToken = (BlockEntryToken)this.this$0.scanner.getToken();
                if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry, Token.ID.BlockEnd)) {
                    ParserImpl.access$600(this.this$0).push(this.this$0.new ParseBlockSequenceEntry());
                    return this.this$0.new ParseBlockNode(null).produce();
                }
                ParserImpl.access$102(this.this$0, this.this$0.new ParseBlockSequenceEntry());
                return ParserImpl.access$1200(this.this$0, blockEntryToken.getEndMark());
            }
            else {
                if (!this.this$0.scanner.checkToken(Token.ID.BlockEnd)) {
                    final Token peekToken = this.this$0.scanner.peekToken();
                    throw new ParserException("while parsing a block collection", (Mark)ParserImpl.access$1100(this.this$0).pop(), "expected <block end>, but found '" + peekToken.getTokenId() + "'", peekToken.getStartMark());
                }
                final Token token = this.this$0.scanner.getToken();
                final SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
                ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$600(this.this$0).pop());
                ParserImpl.access$1100(this.this$0).pop();
                return sequenceEndEvent;
            }
        }
        
        ParseBlockSequenceEntry(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseBlockSequenceFirstEntry implements Production
    {
        final ParserImpl this$0;
        
        private ParseBlockSequenceFirstEntry(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            ParserImpl.access$1100(this.this$0).push(this.this$0.scanner.getToken().getStartMark());
            return this.this$0.new ParseBlockSequenceEntry(null).produce();
        }
        
        ParseBlockSequenceFirstEntry(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseDocumentContent implements Production
    {
        final ParserImpl this$0;
        
        private ParseDocumentContent(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.DocumentEnd, Token.ID.StreamEnd)) {
                final Event access$1200 = ParserImpl.access$1200(this.this$0, this.this$0.scanner.peekToken().getStartMark());
                ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$600(this.this$0).pop());
                return access$1200;
            }
            return this.this$0.new ParseBlockNode(null).produce();
        }
        
        ParseDocumentContent(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseDocumentEnd implements Production
    {
        final ParserImpl this$0;
        
        private ParseDocumentEnd(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            Mark mark = this.this$0.scanner.peekToken().getStartMark();
            if (this.this$0.scanner.checkToken(Token.ID.DocumentEnd)) {
                mark = this.this$0.scanner.getToken().getEndMark();
            }
            final DocumentEndEvent documentEndEvent = new DocumentEndEvent(mark, mark, true);
            ParserImpl.access$102(this.this$0, this.this$0.new ParseDocumentStart(null));
            return documentEndEvent;
        }
        
        ParseDocumentEnd(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseDocumentStart implements Production
    {
        final ParserImpl this$0;
        
        private ParseDocumentStart(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            while (this.this$0.scanner.checkToken(Token.ID.DocumentEnd)) {
                this.this$0.scanner.getToken();
            }
            Event event;
            if (!this.this$0.scanner.checkToken(Token.ID.StreamEnd)) {
                final Mark startMark = this.this$0.scanner.peekToken().getStartMark();
                final VersionTagsTuple access$900 = ParserImpl.access$900(this.this$0);
                if (!this.this$0.scanner.checkToken(Token.ID.DocumentStart)) {
                    throw new ParserException(null, null, "expected '<document start>', but found '" + this.this$0.scanner.peekToken().getTokenId() + "'", this.this$0.scanner.peekToken().getStartMark());
                }
                event = new DocumentStartEvent(startMark, this.this$0.scanner.getToken().getEndMark(), true, access$900.getVersion(), access$900.getTags());
                ParserImpl.access$600(this.this$0).push(this.this$0.new ParseDocumentEnd(null));
                ParserImpl.access$102(this.this$0, this.this$0.new ParseDocumentContent(null));
            }
            else {
                final StreamEndToken streamEndToken = (StreamEndToken)this.this$0.scanner.getToken();
                event = new StreamEndEvent(streamEndToken.getStartMark(), streamEndToken.getEndMark());
                if (!ParserImpl.access$600(this.this$0).isEmpty()) {
                    throw new YAMLException("Unexpected end of stream. States left: " + ParserImpl.access$600(this.this$0));
                }
                if (!ParserImpl.access$1100(this.this$0).isEmpty()) {
                    throw new YAMLException("Unexpected end of stream. Marks left: " + ParserImpl.access$1100(this.this$0));
                }
                ParserImpl.access$102(this.this$0, null);
            }
            return event;
        }
        
        ParseDocumentStart(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseImplicitDocumentStart implements Production
    {
        final ParserImpl this$0;
        
        private ParseImplicitDocumentStart(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.StreamEnd)) {
                ParserImpl.access$302(this.this$0, new VersionTagsTuple(null, ParserImpl.access$400()));
                final Mark startMark = this.this$0.scanner.peekToken().getStartMark();
                final DocumentStartEvent documentStartEvent = new DocumentStartEvent(startMark, startMark, false, null, null);
                ParserImpl.access$600(this.this$0).push(this.this$0.new ParseDocumentEnd(null));
                ParserImpl.access$102(this.this$0, this.this$0.new ParseBlockNode(null));
                return documentStartEvent;
            }
            return this.this$0.new ParseDocumentStart(null).produce();
        }
        
        ParseImplicitDocumentStart(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
    
    private class ParseStreamStart implements Production
    {
        final ParserImpl this$0;
        
        private ParseStreamStart(final ParserImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Event produce() {
            final StreamStartToken streamStartToken = (StreamStartToken)this.this$0.scanner.getToken();
            final StreamStartEvent streamStartEvent = new StreamStartEvent(streamStartToken.getStartMark(), streamStartToken.getEndMark());
            ParserImpl.access$102(this.this$0, this.this$0.new ParseImplicitDocumentStart(null));
            return streamStartEvent;
        }
        
        ParseStreamStart(final ParserImpl parserImpl, final ParserImpl$1 object) {
            this(parserImpl);
        }
    }
}
