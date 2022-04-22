package org.yaml.snakeyaml.scanner;

import java.util.regex.*;
import org.yaml.snakeyaml.reader.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.tokens.*;
import java.nio.*;
import org.yaml.snakeyaml.util.*;
import java.util.*;

public final class ScannerImpl implements Scanner
{
    private static final Pattern NOT_HEXA;
    public static final Map ESCAPE_REPLACEMENTS;
    public static final Map ESCAPE_CODES;
    private final StreamReader reader;
    private boolean done;
    private int flowLevel;
    private List tokens;
    private int tokensTaken;
    private int indent;
    private ArrayStack indents;
    private boolean allowSimpleKey;
    private Map possibleSimpleKeys;
    
    public ScannerImpl(final StreamReader reader) {
        this.done = false;
        this.flowLevel = 0;
        this.tokensTaken = 0;
        this.indent = -1;
        this.allowSimpleKey = true;
        this.reader = reader;
        this.tokens = new ArrayList(100);
        this.indents = new ArrayStack(10);
        this.possibleSimpleKeys = new LinkedHashMap();
        this.fetchStreamStart();
    }
    
    @Override
    public boolean checkToken(final Token.ID... array) {
        while (this.needMoreTokens()) {
            this.fetchMoreTokens();
        }
        if (!this.tokens.isEmpty()) {
            if (array.length == 0) {
                return true;
            }
            final Token.ID tokenId = this.tokens.get(0).getTokenId();
            while (0 < array.length) {
                if (tokenId == array[0]) {
                    return true;
                }
                int n = 0;
                ++n;
            }
        }
        return false;
    }
    
    @Override
    public Token peekToken() {
        while (this.needMoreTokens()) {
            this.fetchMoreTokens();
        }
        return this.tokens.get(0);
    }
    
    @Override
    public Token getToken() {
        ++this.tokensTaken;
        return this.tokens.remove(0);
    }
    
    private boolean needMoreTokens() {
        if (this.done) {
            return false;
        }
        if (this.tokens.isEmpty()) {
            return true;
        }
        this.stalePossibleSimpleKeys();
        return this.nextPossibleSimpleKey() == this.tokensTaken;
    }
    
    private void fetchMoreTokens() {
        this.scanToNextToken();
        this.stalePossibleSimpleKeys();
        this.unwindIndent(this.reader.getColumn());
        final int peek = this.reader.peek();
        switch (peek) {
            case 0: {
                this.fetchStreamEnd();
                return;
            }
            case 37: {
                if (this.checkDirective()) {
                    this.fetchDirective();
                    return;
                }
                break;
            }
            case 45: {
                if (this.checkDocumentStart()) {
                    this.fetchDocumentStart();
                    return;
                }
                if (this.checkBlockEntry()) {
                    this.fetchBlockEntry();
                    return;
                }
                break;
            }
            case 46: {
                if (this.checkDocumentEnd()) {
                    this.fetchDocumentEnd();
                    return;
                }
                break;
            }
            case 91: {
                this.fetchFlowSequenceStart();
                return;
            }
            case 123: {
                this.fetchFlowMappingStart();
                return;
            }
            case 93: {
                this.fetchFlowSequenceEnd();
                return;
            }
            case 125: {
                this.fetchFlowMappingEnd();
                return;
            }
            case 44: {
                this.fetchFlowEntry();
                return;
            }
            case 63: {
                if (this.checkKey()) {
                    this.fetchKey();
                    return;
                }
                break;
            }
            case 58: {
                if (this.checkValue()) {
                    this.fetchValue();
                    return;
                }
                break;
            }
            case 42: {
                this.fetchAlias();
                return;
            }
            case 38: {
                this.fetchAnchor();
                return;
            }
            case 33: {
                this.fetchTag();
                return;
            }
            case 124: {
                if (this.flowLevel == 0) {
                    this.fetchLiteral();
                    return;
                }
                break;
            }
            case 62: {
                if (this.flowLevel == 0) {
                    this.fetchFolded();
                    return;
                }
                break;
            }
            case 39: {
                this.fetchSingle();
                return;
            }
            case 34: {
                this.fetchDouble();
                return;
            }
        }
        if (this.checkPlain()) {
            this.fetchPlain();
            return;
        }
        String s = String.valueOf(Character.toChars(peek));
        for (final Character c : ScannerImpl.ESCAPE_REPLACEMENTS.keySet()) {
            if (((String)ScannerImpl.ESCAPE_REPLACEMENTS.get(c)).equals(s)) {
                s = "\\" + c;
                break;
            }
        }
        if (peek == 9) {
            s += "(TAB)";
        }
        throw new ScannerException("while scanning for the next token", null, String.format("found character '%s' that cannot start any token. (Do not use %s for indentation)", s, s), this.reader.getMark());
    }
    
    private int nextPossibleSimpleKey() {
        if (!this.possibleSimpleKeys.isEmpty()) {
            return this.possibleSimpleKeys.values().iterator().next().getTokenNumber();
        }
        return -1;
    }
    
    private void stalePossibleSimpleKeys() {
        if (!this.possibleSimpleKeys.isEmpty()) {
            final Iterator<SimpleKey> iterator = this.possibleSimpleKeys.values().iterator();
            while (iterator.hasNext()) {
                final SimpleKey simpleKey = iterator.next();
                if (simpleKey.getLine() != this.reader.getLine() || this.reader.getIndex() - simpleKey.getIndex() > 1024) {
                    if (simpleKey.isRequired()) {
                        throw new ScannerException("while scanning a simple key", simpleKey.getMark(), "could not find expected ':'", this.reader.getMark());
                    }
                    iterator.remove();
                }
            }
        }
    }
    
    private void savePossibleSimpleKey() {
        final boolean b = this.flowLevel == 0 && this.indent == this.reader.getColumn();
        if (!this.allowSimpleKey && b) {
            throw new YAMLException("A simple key is required only if it is the first token in the current line");
        }
        if (this.allowSimpleKey) {
            this.removePossibleSimpleKey();
            this.possibleSimpleKeys.put(this.flowLevel, new SimpleKey(this.tokensTaken + this.tokens.size(), b, this.reader.getIndex(), this.reader.getLine(), this.reader.getColumn(), this.reader.getMark()));
        }
    }
    
    private void removePossibleSimpleKey() {
        final SimpleKey simpleKey = this.possibleSimpleKeys.remove(this.flowLevel);
        if (simpleKey != null && simpleKey.isRequired()) {
            throw new ScannerException("while scanning a simple key", simpleKey.getMark(), "could not find expected ':'", this.reader.getMark());
        }
    }
    
    private void unwindIndent(final int n) {
        if (this.flowLevel != 0) {
            return;
        }
        while (this.indent > n) {
            final Mark mark = this.reader.getMark();
            this.indent = (int)this.indents.pop();
            this.tokens.add(new BlockEndToken(mark, mark));
        }
    }
    
    private boolean addIndent(final int indent) {
        if (this.indent < indent) {
            this.indents.push(this.indent);
            this.indent = indent;
            return true;
        }
        return false;
    }
    
    private void fetchStreamStart() {
        final Mark mark = this.reader.getMark();
        this.tokens.add(new StreamStartToken(mark, mark));
    }
    
    private void fetchStreamEnd() {
        this.unwindIndent(-1);
        this.removePossibleSimpleKey();
        this.allowSimpleKey = false;
        this.possibleSimpleKeys.clear();
        final Mark mark = this.reader.getMark();
        this.tokens.add(new StreamEndToken(mark, mark));
        this.done = true;
    }
    
    private void fetchDirective() {
        this.unwindIndent(-1);
        this.removePossibleSimpleKey();
        this.allowSimpleKey = false;
        this.tokens.add(this.scanDirective());
    }
    
    private void fetchDocumentStart() {
        this.fetchDocumentIndicator(true);
    }
    
    private void fetchDocumentEnd() {
        this.fetchDocumentIndicator(false);
    }
    
    private void fetchDocumentIndicator(final boolean b) {
        this.unwindIndent(-1);
        this.removePossibleSimpleKey();
        this.allowSimpleKey = false;
        final Mark mark = this.reader.getMark();
        this.reader.forward(3);
        final Mark mark2 = this.reader.getMark();
        Token token;
        if (b) {
            token = new DocumentStartToken(mark, mark2);
        }
        else {
            token = new DocumentEndToken(mark, mark2);
        }
        this.tokens.add(token);
    }
    
    private void fetchFlowSequenceStart() {
        this.fetchFlowCollectionStart(false);
    }
    
    private void fetchFlowMappingStart() {
        this.fetchFlowCollectionStart(true);
    }
    
    private void fetchFlowCollectionStart(final boolean b) {
        this.savePossibleSimpleKey();
        ++this.flowLevel;
        this.allowSimpleKey = true;
        final Mark mark = this.reader.getMark();
        this.reader.forward(1);
        final Mark mark2 = this.reader.getMark();
        Token token;
        if (b) {
            token = new FlowMappingStartToken(mark, mark2);
        }
        else {
            token = new FlowSequenceStartToken(mark, mark2);
        }
        this.tokens.add(token);
    }
    
    private void fetchFlowSequenceEnd() {
        this.fetchFlowCollectionEnd(false);
    }
    
    private void fetchFlowMappingEnd() {
        this.fetchFlowCollectionEnd(true);
    }
    
    private void fetchFlowCollectionEnd(final boolean b) {
        this.removePossibleSimpleKey();
        --this.flowLevel;
        this.allowSimpleKey = false;
        final Mark mark = this.reader.getMark();
        this.reader.forward();
        final Mark mark2 = this.reader.getMark();
        Token token;
        if (b) {
            token = new FlowMappingEndToken(mark, mark2);
        }
        else {
            token = new FlowSequenceEndToken(mark, mark2);
        }
        this.tokens.add(token);
    }
    
    private void fetchFlowEntry() {
        this.allowSimpleKey = true;
        this.removePossibleSimpleKey();
        final Mark mark = this.reader.getMark();
        this.reader.forward();
        this.tokens.add(new FlowEntryToken(mark, this.reader.getMark()));
    }
    
    private void fetchBlockEntry() {
        if (this.flowLevel == 0) {
            if (!this.allowSimpleKey) {
                throw new ScannerException(null, null, "sequence entries are not allowed here", this.reader.getMark());
            }
            if (this.addIndent(this.reader.getColumn())) {
                final Mark mark = this.reader.getMark();
                this.tokens.add(new BlockSequenceStartToken(mark, mark));
            }
        }
        this.allowSimpleKey = true;
        this.removePossibleSimpleKey();
        final Mark mark2 = this.reader.getMark();
        this.reader.forward();
        this.tokens.add(new BlockEntryToken(mark2, this.reader.getMark()));
    }
    
    private void fetchKey() {
        if (this.flowLevel == 0) {
            if (!this.allowSimpleKey) {
                throw new ScannerException(null, null, "mapping keys are not allowed here", this.reader.getMark());
            }
            if (this.addIndent(this.reader.getColumn())) {
                final Mark mark = this.reader.getMark();
                this.tokens.add(new BlockMappingStartToken(mark, mark));
            }
        }
        this.allowSimpleKey = (this.flowLevel == 0);
        this.removePossibleSimpleKey();
        final Mark mark2 = this.reader.getMark();
        this.reader.forward();
        this.tokens.add(new KeyToken(mark2, this.reader.getMark()));
    }
    
    private void fetchValue() {
        final SimpleKey simpleKey = this.possibleSimpleKeys.remove(this.flowLevel);
        if (simpleKey != null) {
            this.tokens.add(simpleKey.getTokenNumber() - this.tokensTaken, new KeyToken(simpleKey.getMark(), simpleKey.getMark()));
            if (this.flowLevel == 0 && this.addIndent(simpleKey.getColumn())) {
                this.tokens.add(simpleKey.getTokenNumber() - this.tokensTaken, new BlockMappingStartToken(simpleKey.getMark(), simpleKey.getMark()));
            }
            this.allowSimpleKey = false;
        }
        else {
            if (this.flowLevel == 0 && !this.allowSimpleKey) {
                throw new ScannerException(null, null, "mapping values are not allowed here", this.reader.getMark());
            }
            if (this.flowLevel == 0 && this.addIndent(this.reader.getColumn())) {
                final Mark mark = this.reader.getMark();
                this.tokens.add(new BlockMappingStartToken(mark, mark));
            }
            this.allowSimpleKey = (this.flowLevel == 0);
            this.removePossibleSimpleKey();
        }
        final Mark mark2 = this.reader.getMark();
        this.reader.forward();
        this.tokens.add(new ValueToken(mark2, this.reader.getMark()));
    }
    
    private void fetchAlias() {
        this.savePossibleSimpleKey();
        this.allowSimpleKey = false;
        this.tokens.add(this.scanAnchor(false));
    }
    
    private void fetchAnchor() {
        this.savePossibleSimpleKey();
        this.allowSimpleKey = false;
        this.tokens.add(this.scanAnchor(true));
    }
    
    private void fetchTag() {
        this.savePossibleSimpleKey();
        this.allowSimpleKey = false;
        this.tokens.add(this.scanTag());
    }
    
    private void fetchLiteral() {
        this.fetchBlockScalar('|');
    }
    
    private void fetchFolded() {
        this.fetchBlockScalar('>');
    }
    
    private void fetchBlockScalar(final char c) {
        this.allowSimpleKey = true;
        this.removePossibleSimpleKey();
        this.tokens.add(this.scanBlockScalar(c));
    }
    
    private void fetchSingle() {
        this.fetchFlowScalar('\'');
    }
    
    private void fetchDouble() {
        this.fetchFlowScalar('\"');
    }
    
    private void fetchFlowScalar(final char c) {
        this.savePossibleSimpleKey();
        this.allowSimpleKey = false;
        this.tokens.add(this.scanFlowScalar(c));
    }
    
    private void fetchPlain() {
        this.savePossibleSimpleKey();
        this.allowSimpleKey = false;
        this.tokens.add(this.scanPlain());
    }
    
    private boolean checkDirective() {
        return this.reader.getColumn() == 0;
    }
    
    private boolean checkDocumentStart() {
        return this.reader.getColumn() == 0 && "---".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3));
    }
    
    private boolean checkDocumentEnd() {
        return this.reader.getColumn() == 0 && "...".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3));
    }
    
    private boolean checkBlockEntry() {
        return Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
    }
    
    private boolean checkKey() {
        return this.flowLevel != 0 || Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
    }
    
    private boolean checkValue() {
        return this.flowLevel != 0 || Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
    }
    
    private boolean checkPlain() {
        final int peek = this.reader.peek();
        return Constant.NULL_BL_T_LINEBR.hasNo(peek, "-?:,[]{}#&*!|>'\"%@`") || (Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(1)) && (peek == 45 || (this.flowLevel == 0 && "?:".indexOf(peek) != -1)));
    }
    
    private void scanToNextToken() {
        if (this.reader.getIndex() == 0 && this.reader.peek() == 65279) {
            this.reader.forward();
        }
        while (!true) {
            int n = 0;
            while (this.reader.peek(0) == 32) {
                ++n;
            }
            if (0 > 0) {
                this.reader.forward(0);
            }
            if (this.reader.peek() == 35) {
                while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(0))) {
                    ++n;
                }
                if (0 > 0) {
                    this.reader.forward(0);
                }
            }
            if (this.scanLineBreak().length() != 0 && this.flowLevel == 0) {
                this.allowSimpleKey = true;
            }
        }
    }
    
    private Token scanDirective() {
        final Mark mark = this.reader.getMark();
        this.reader.forward();
        final String scanDirectiveName = this.scanDirectiveName(mark);
        List list = null;
        Mark mark2;
        if ("YAML".equals(scanDirectiveName)) {
            list = this.scanYamlDirectiveValue(mark);
            mark2 = this.reader.getMark();
        }
        else if ("TAG".equals(scanDirectiveName)) {
            list = this.scanTagDirectiveValue(mark);
            mark2 = this.reader.getMark();
        }
        else {
            mark2 = this.reader.getMark();
            while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(0))) {
                int n = 0;
                ++n;
            }
            if (0 > 0) {
                this.reader.forward(0);
            }
        }
        this.scanDirectiveIgnoredLine(mark);
        return new DirectiveToken(scanDirectiveName, list, mark, mark2);
    }
    
    private String scanDirectiveName(final Mark mark) {
        int n;
        for (n = this.reader.peek(0); Constant.ALPHA.has(n); n = this.reader.peek(0)) {
            int n2 = 0;
            ++n2;
        }
        if (!false) {
            throw new ScannerException("while scanning a directive", mark, "expected alphabetic or numeric character, but found " + String.valueOf(Character.toChars(n)) + "(" + n + ")", this.reader.getMark());
        }
        final String prefixForward = this.reader.prefixForward(0);
        final int peek = this.reader.peek();
        if (Constant.NULL_BL_LINEBR.hasNo(peek)) {
            throw new ScannerException("while scanning a directive", mark, "expected alphabetic or numeric character, but found " + String.valueOf(Character.toChars(peek)) + "(" + peek + ")", this.reader.getMark());
        }
        return prefixForward;
    }
    
    private List scanYamlDirectiveValue(final Mark mark) {
        while (this.reader.peek() == 32) {
            this.reader.forward();
        }
        final Integer scanYamlDirectiveNumber = this.scanYamlDirectiveNumber(mark);
        final int peek = this.reader.peek();
        if (peek != 46) {
            throw new ScannerException("while scanning a directive", mark, "expected a digit or '.', but found " + String.valueOf(Character.toChars(peek)) + "(" + peek + ")", this.reader.getMark());
        }
        this.reader.forward();
        final Integer scanYamlDirectiveNumber2 = this.scanYamlDirectiveNumber(mark);
        final int peek2 = this.reader.peek();
        if (Constant.NULL_BL_LINEBR.hasNo(peek2)) {
            throw new ScannerException("while scanning a directive", mark, "expected a digit or ' ', but found " + String.valueOf(Character.toChars(peek2)) + "(" + peek2 + ")", this.reader.getMark());
        }
        final ArrayList<Integer> list = new ArrayList<Integer>(2);
        list.add(scanYamlDirectiveNumber);
        list.add(scanYamlDirectiveNumber2);
        return list;
    }
    
    private Integer scanYamlDirectiveNumber(final Mark mark) {
        final int peek = this.reader.peek();
        if (!Character.isDigit(peek)) {
            throw new ScannerException("while scanning a directive", mark, "expected a digit, but found " + String.valueOf(Character.toChars(peek)) + "(" + peek + ")", this.reader.getMark());
        }
        while (Character.isDigit(this.reader.peek(0))) {
            int n = 0;
            ++n;
        }
        return Integer.parseInt(this.reader.prefixForward(0));
    }
    
    private List scanTagDirectiveValue(final Mark mark) {
        while (this.reader.peek() == 32) {
            this.reader.forward();
        }
        final String scanTagDirectiveHandle = this.scanTagDirectiveHandle(mark);
        while (this.reader.peek() == 32) {
            this.reader.forward();
        }
        final String scanTagDirectivePrefix = this.scanTagDirectivePrefix(mark);
        final ArrayList<String> list = new ArrayList<String>(2);
        list.add(scanTagDirectiveHandle);
        list.add(scanTagDirectivePrefix);
        return list;
    }
    
    private String scanTagDirectiveHandle(final Mark mark) {
        final String scanTagHandle = this.scanTagHandle("directive", mark);
        final int peek = this.reader.peek();
        if (peek != 32) {
            throw new ScannerException("while scanning a directive", mark, "expected ' ', but found " + String.valueOf(Character.toChars(peek)) + "(" + peek + ")", this.reader.getMark());
        }
        return scanTagHandle;
    }
    
    private String scanTagDirectivePrefix(final Mark mark) {
        final String scanTagUri = this.scanTagUri("directive", mark);
        final int peek = this.reader.peek();
        if (Constant.NULL_BL_LINEBR.hasNo(peek)) {
            throw new ScannerException("while scanning a directive", mark, "expected ' ', but found " + String.valueOf(Character.toChars(peek)) + "(" + peek + ")", this.reader.getMark());
        }
        return scanTagUri;
    }
    
    private void scanDirectiveIgnoredLine(final Mark mark) {
        while (this.reader.peek() == 32) {
            this.reader.forward();
        }
        if (this.reader.peek() == 35) {
            while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek())) {
                this.reader.forward();
            }
        }
        final int peek = this.reader.peek();
        if (this.scanLineBreak().length() == 0 && peek != 0) {
            throw new ScannerException("while scanning a directive", mark, "expected a comment or a line break, but found " + String.valueOf(Character.toChars(peek)) + "(" + peek + ")", this.reader.getMark());
        }
    }
    
    private Token scanAnchor(final boolean b) {
        final Mark mark = this.reader.getMark();
        final String s = (this.reader.peek() == 42) ? "alias" : "anchor";
        this.reader.forward();
        int n;
        for (n = this.reader.peek(0); Constant.NULL_BL_T_LINEBR.hasNo(n, ":,[]{}"); n = this.reader.peek(0)) {
            int n2 = 0;
            ++n2;
        }
        if (!false) {
            throw new ScannerException("while scanning an " + s, mark, "unexpected character found " + String.valueOf(Character.toChars(n)) + "(" + n + ")", this.reader.getMark());
        }
        final String prefixForward = this.reader.prefixForward(0);
        final int peek = this.reader.peek();
        if (Constant.NULL_BL_T_LINEBR.hasNo(peek, "?:,]}%@`")) {
            throw new ScannerException("while scanning an " + s, mark, "unexpected character found " + String.valueOf(Character.toChars(peek)) + "(" + peek + ")", this.reader.getMark());
        }
        final Mark mark2 = this.reader.getMark();
        Token token;
        if (b) {
            token = new AnchorToken(prefixForward, mark, mark2);
        }
        else {
            token = new AliasToken(prefixForward, mark, mark2);
        }
        return token;
    }
    
    private Token scanTag() {
        final Mark mark = this.reader.getMark();
        int n = this.reader.peek(1);
        String scanTagHandle = null;
        String s;
        if (n == 60) {
            this.reader.forward(2);
            s = this.scanTagUri("tag", mark);
            final int peek = this.reader.peek();
            if (peek != 62) {
                throw new ScannerException("while scanning a tag", mark, "expected '>', but found '" + String.valueOf(Character.toChars(peek)) + "' (" + peek + ")", this.reader.getMark());
            }
            this.reader.forward();
        }
        else if (Constant.NULL_BL_T_LINEBR.has(n)) {
            s = "!";
            this.reader.forward();
        }
        else {
            while (Constant.NULL_BL_LINEBR.hasNo(n) && n != 33) {
                int n2 = 0;
                ++n2;
                n = this.reader.peek(1);
            }
            if (true) {
                scanTagHandle = this.scanTagHandle("tag", mark);
            }
            else {
                scanTagHandle = "!";
                this.reader.forward();
            }
            s = this.scanTagUri("tag", mark);
        }
        final int peek2 = this.reader.peek();
        if (Constant.NULL_BL_LINEBR.hasNo(peek2)) {
            throw new ScannerException("while scanning a tag", mark, "expected ' ', but found '" + String.valueOf(Character.toChars(peek2)) + "' (" + peek2 + ")", this.reader.getMark());
        }
        return new TagToken(new TagTuple(scanTagHandle, s), mark, this.reader.getMark());
    }
    
    private Token scanBlockScalar(final char c) {
        if (c == '>') {}
        final StringBuilder sb = new StringBuilder();
        final Mark mark = this.reader.getMark();
        this.reader.forward();
        final Chomping scanBlockScalarIndicators = this.scanBlockScalarIndicators(mark);
        final int increment = scanBlockScalarIndicators.getIncrement();
        this.scanBlockScalarIgnoredLine(mark);
        final int n = this.indent + 1;
        if (1 < 1) {}
        String s;
        Mark mark2;
        int max;
        if (increment == -1) {
            final Object[] scanBlockScalarIndentation = this.scanBlockScalarIndentation();
            s = (String)scanBlockScalarIndentation[0];
            final int intValue = (int)scanBlockScalarIndentation[1];
            mark2 = (Mark)scanBlockScalarIndentation[2];
            max = Math.max(1, intValue);
        }
        else {
            max = 1 + increment - 1;
            final Object[] scanBlockScalarBreaks = this.scanBlockScalarBreaks(max);
            s = (String)scanBlockScalarBreaks[0];
            mark2 = (Mark)scanBlockScalarBreaks[1];
        }
        String scanLineBreak = "";
        while (this.reader.getColumn() == max && this.reader.peek() != 0) {
            sb.append(s);
            final boolean b = " \t".indexOf(this.reader.peek()) == -1;
            while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(0))) {
                int n2 = 0;
                ++n2;
            }
            sb.append(this.reader.prefixForward(0));
            scanLineBreak = this.scanLineBreak();
            final Object[] scanBlockScalarBreaks2 = this.scanBlockScalarBreaks(max);
            s = (String)scanBlockScalarBreaks2[0];
            mark2 = (Mark)scanBlockScalarBreaks2[1];
            if (this.reader.getColumn() != max || this.reader.peek() == 0) {
                break;
            }
            if (false && "\n".equals(scanLineBreak) && b && " \t".indexOf(this.reader.peek()) == -1) {
                if (s.length() != 0) {
                    continue;
                }
                sb.append(" ");
            }
            else {
                sb.append(scanLineBreak);
            }
        }
        if (scanBlockScalarIndicators.chompTailIsNotFalse()) {
            sb.append(scanLineBreak);
        }
        if (scanBlockScalarIndicators.chompTailIsTrue()) {
            sb.append(s);
        }
        return new ScalarToken(sb.toString(), false, mark, mark2, DumperOptions.ScalarStyle.createStyle(c));
    }
    
    private Chomping scanBlockScalarIndicators(final Mark mark) {
        Boolean b = null;
        final int peek = this.reader.peek();
        if (peek == 45 || peek == 43) {
            if (peek == 43) {
                b = Boolean.TRUE;
            }
            else {
                b = Boolean.FALSE;
            }
            this.reader.forward();
            final int peek2 = this.reader.peek();
            if (Character.isDigit(peek2)) {
                Integer.parseInt(String.valueOf(Character.toChars(peek2)));
                if (-1 == 0) {
                    throw new ScannerException("while scanning a block scalar", mark, "expected indentation indicator in the range 1-9, but found 0", this.reader.getMark());
                }
                this.reader.forward();
            }
        }
        else if (Character.isDigit(peek)) {
            Integer.parseInt(String.valueOf(Character.toChars(peek)));
            if (-1 == 0) {
                throw new ScannerException("while scanning a block scalar", mark, "expected indentation indicator in the range 1-9, but found 0", this.reader.getMark());
            }
            this.reader.forward();
            final int peek3 = this.reader.peek();
            if (peek3 == 45 || peek3 == 43) {
                if (peek3 == 43) {
                    b = Boolean.TRUE;
                }
                else {
                    b = Boolean.FALSE;
                }
                this.reader.forward();
            }
        }
        final int peek4 = this.reader.peek();
        if (Constant.NULL_BL_LINEBR.hasNo(peek4)) {
            throw new ScannerException("while scanning a block scalar", mark, "expected chomping or indentation indicators, but found " + String.valueOf(Character.toChars(peek4)) + "(" + peek4 + ")", this.reader.getMark());
        }
        return new Chomping(b, -1);
    }
    
    private String scanBlockScalarIgnoredLine(final Mark mark) {
        while (this.reader.peek() == 32) {
            this.reader.forward();
        }
        if (this.reader.peek() == 35) {
            while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek())) {
                this.reader.forward();
            }
        }
        final int peek = this.reader.peek();
        final String scanLineBreak = this.scanLineBreak();
        if (scanLineBreak.length() == 0 && peek != 0) {
            throw new ScannerException("while scanning a block scalar", mark, "expected a comment or a line break, but found " + String.valueOf(Character.toChars(peek)) + "(" + peek + ")", this.reader.getMark());
        }
        return scanLineBreak;
    }
    
    private Object[] scanBlockScalarIndentation() {
        final StringBuilder sb = new StringBuilder();
        Mark mark = this.reader.getMark();
        while (Constant.LINEBR.has(this.reader.peek(), " \r")) {
            if (this.reader.peek() != 32) {
                sb.append(this.scanLineBreak());
                mark = this.reader.getMark();
            }
            else {
                this.reader.forward();
                if (this.reader.getColumn() <= 0) {
                    continue;
                }
                this.reader.getColumn();
            }
        }
        return new Object[] { sb.toString(), 0, mark };
    }
    
    private Object[] scanBlockScalarBreaks(final int n) {
        final StringBuilder sb = new StringBuilder();
        Mark mark = this.reader.getMark();
        for (int column = this.reader.getColumn(); column < n && this.reader.peek() == 32; ++column) {
            this.reader.forward();
        }
        String scanLineBreak;
        while ((scanLineBreak = this.scanLineBreak()).length() != 0) {
            sb.append(scanLineBreak);
            mark = this.reader.getMark();
            for (int column2 = this.reader.getColumn(); column2 < n && this.reader.peek() == 32; ++column2) {
                this.reader.forward();
            }
        }
        return new Object[] { sb.toString(), mark };
    }
    
    private Token scanFlowScalar(final char c) {
        if (c == '\"') {}
        final StringBuilder sb = new StringBuilder();
        final Mark mark = this.reader.getMark();
        final int peek = this.reader.peek();
        this.reader.forward();
        sb.append(this.scanFlowScalarNonSpaces(false, mark));
        while (this.reader.peek() != peek) {
            sb.append(this.scanFlowScalarSpaces(mark));
            sb.append(this.scanFlowScalarNonSpaces(false, mark));
        }
        this.reader.forward();
        return new ScalarToken(sb.toString(), false, mark, this.reader.getMark(), DumperOptions.ScalarStyle.createStyle(c));
    }
    
    private String scanFlowScalarNonSpaces(final boolean b, final Mark mark) {
        final StringBuilder sb = new StringBuilder();
        while (true) {
            if (Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(0), "'\"\\")) {
                int intValue = 0;
                ++intValue;
            }
            else {
                if (false) {
                    sb.append(this.reader.prefixForward(0));
                }
                final int peek = this.reader.peek();
                if (!b && peek == 39 && this.reader.peek(1) == 39) {
                    sb.append("'");
                    this.reader.forward(2);
                }
                else if ((b && peek == 39) || (!b && "\"\\".indexOf(peek) != -1)) {
                    sb.appendCodePoint(peek);
                    this.reader.forward();
                }
                else {
                    if (!b || peek != 92) {
                        return sb.toString();
                    }
                    this.reader.forward();
                    final int peek2 = this.reader.peek();
                    if (!Character.isSupplementaryCodePoint(peek2) && ScannerImpl.ESCAPE_REPLACEMENTS.containsKey((char)peek2)) {
                        sb.append((String)ScannerImpl.ESCAPE_REPLACEMENTS.get((char)peek2));
                        this.reader.forward();
                    }
                    else if (!Character.isSupplementaryCodePoint(peek2) && ScannerImpl.ESCAPE_CODES.containsKey((char)peek2)) {
                        final int intValue = (int)ScannerImpl.ESCAPE_CODES.get((char)peek2);
                        this.reader.forward();
                        final String prefix = this.reader.prefix(0);
                        if (ScannerImpl.NOT_HEXA.matcher(prefix).find()) {
                            throw new ScannerException("while scanning a double-quoted scalar", mark, "expected escape sequence of " + 0 + " hexadecimal numbers, but found: " + prefix, this.reader.getMark());
                        }
                        sb.append(new String(Character.toChars(Integer.parseInt(prefix, 16))));
                        this.reader.forward(0);
                    }
                    else {
                        if (this.scanLineBreak().length() == 0) {
                            throw new ScannerException("while scanning a double-quoted scalar", mark, "found unknown escape character " + String.valueOf(Character.toChars(peek2)) + "(" + peek2 + ")", this.reader.getMark());
                        }
                        sb.append(this.scanFlowScalarBreaks(mark));
                    }
                }
            }
        }
    }
    
    private String scanFlowScalarSpaces(final Mark mark) {
        final StringBuilder sb = new StringBuilder();
        while (" \t".indexOf(this.reader.peek(0)) != -1) {
            int n = 0;
            ++n;
        }
        final String prefixForward = this.reader.prefixForward(0);
        if (this.reader.peek() == 0) {
            throw new ScannerException("while scanning a quoted scalar", mark, "found unexpected end of stream", this.reader.getMark());
        }
        final String scanLineBreak = this.scanLineBreak();
        if (scanLineBreak.length() != 0) {
            final String scanFlowScalarBreaks = this.scanFlowScalarBreaks(mark);
            if (!"\n".equals(scanLineBreak)) {
                sb.append(scanLineBreak);
            }
            else if (scanFlowScalarBreaks.length() == 0) {
                sb.append(" ");
            }
            sb.append(scanFlowScalarBreaks);
        }
        else {
            sb.append(prefixForward);
        }
        return sb.toString();
    }
    
    private String scanFlowScalarBreaks(final Mark mark) {
        final StringBuilder sb = new StringBuilder();
        while (true) {
            final String prefix = this.reader.prefix(3);
            if (("---".equals(prefix) || "...".equals(prefix)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))) {
                throw new ScannerException("while scanning a quoted scalar", mark, "found unexpected document separator", this.reader.getMark());
            }
            while (" \t".indexOf(this.reader.peek()) != -1) {
                this.reader.forward();
            }
            final String scanLineBreak = this.scanLineBreak();
            if (scanLineBreak.length() == 0) {
                return sb.toString();
            }
            sb.append(scanLineBreak);
        }
    }
    
    private Token scanPlain() {
        final StringBuilder sb = new StringBuilder();
        Mark mark = this.reader.getMark();
        final int n = this.indent + 1;
        String scanPlainSpaces = "";
        while (this.reader.peek() != 35) {
            while (true) {
                final int peek = this.reader.peek(0);
                if (Constant.NULL_BL_T_LINEBR.has(peek) || (peek == 58 && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1), (this.flowLevel != 0) ? ",[]{}" : "")) || (this.flowLevel != 0 && ",?[]{}".indexOf(peek) != -1)) {
                    break;
                }
                int n2 = 0;
                ++n2;
            }
            if (false) {
                this.allowSimpleKey = false;
                sb.append(scanPlainSpaces);
                sb.append(this.reader.prefixForward(0));
                mark = this.reader.getMark();
                scanPlainSpaces = this.scanPlainSpaces();
                if (scanPlainSpaces.length() != 0 && this.reader.peek() != 35) {
                    if (this.flowLevel != 0 || this.reader.getColumn() >= n) {
                        continue;
                    }
                }
            }
            return new ScalarToken(sb.toString(), mark, mark, true);
        }
        return new ScalarToken(sb.toString(), mark, mark, true);
    }
    
    private String scanPlainSpaces() {
        while (this.reader.peek(0) == 32 || this.reader.peek(0) == 9) {
            int n = 0;
            ++n;
        }
        final String prefixForward = this.reader.prefixForward(0);
        final String scanLineBreak = this.scanLineBreak();
        if (scanLineBreak.length() == 0) {
            return prefixForward;
        }
        this.allowSimpleKey = true;
        final String prefix = this.reader.prefix(3);
        if ("---".equals(prefix) || ("...".equals(prefix) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3)))) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        while (true) {
            if (this.reader.peek() == 32) {
                this.reader.forward();
            }
            else {
                final String scanLineBreak2 = this.scanLineBreak();
                if (scanLineBreak2.length() != 0) {
                    sb.append(scanLineBreak2);
                    final String prefix2 = this.reader.prefix(3);
                    if ("---".equals(prefix2) || ("...".equals(prefix2) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3)))) {
                        return "";
                    }
                    continue;
                }
                else {
                    if (!"\n".equals(scanLineBreak)) {
                        return scanLineBreak + (Object)sb;
                    }
                    if (sb.length() == 0) {
                        return " ";
                    }
                    return sb.toString();
                }
            }
        }
    }
    
    private String scanTagHandle(final String s, final Mark mark) {
        final int peek = this.reader.peek();
        if (peek != 33) {
            throw new ScannerException("while scanning a " + s, mark, "expected '!', but found " + String.valueOf(Character.toChars(peek)) + "(" + peek + ")", this.reader.getMark());
        }
        int n = this.reader.peek(1);
        if (n != 32) {
            int n2 = 0;
            while (Constant.ALPHA.has(n)) {
                ++n2;
                n = this.reader.peek(1);
            }
            if (n != 33) {
                this.reader.forward(1);
                throw new ScannerException("while scanning a " + s, mark, "expected '!', but found " + String.valueOf(Character.toChars(n)) + "(" + n + ")", this.reader.getMark());
            }
            ++n2;
        }
        return this.reader.prefixForward(1);
    }
    
    private String scanTagUri(final String s, final Mark mark) {
        final StringBuilder sb = new StringBuilder();
        int n;
        for (n = this.reader.peek(0); Constant.URI_CHARS.has(n); n = this.reader.peek(0)) {
            if (n == 37) {
                sb.append(this.reader.prefixForward(0));
                sb.append(this.scanUriEscapes(s, mark));
            }
            else {
                int n2 = 0;
                ++n2;
            }
        }
        if (false) {
            sb.append(this.reader.prefixForward(0));
        }
        if (sb.length() == 0) {
            throw new ScannerException("while scanning a " + s, mark, "expected URI, but found " + String.valueOf(Character.toChars(n)) + "(" + n + ")", this.reader.getMark());
        }
        return sb.toString();
    }
    
    private String scanUriEscapes(final String s, final Mark mark) {
        while (this.reader.peek(3) == 37) {
            int n = 0;
            ++n;
        }
        this.reader.getMark();
        final ByteBuffer allocate = ByteBuffer.allocate(1);
        while (this.reader.peek() == 37) {
            this.reader.forward();
            allocate.put((byte)Integer.parseInt(this.reader.prefix(2), 16));
            this.reader.forward(2);
        }
        allocate.flip();
        return UriEncoder.decode(allocate);
    }
    
    private String scanLineBreak() {
        final int peek = this.reader.peek();
        if (peek == 13 || peek == 10 || peek == 133) {
            if (peek == 13 && 10 == this.reader.peek(1)) {
                this.reader.forward(2);
            }
            else {
                this.reader.forward();
            }
            return "\n";
        }
        if (peek == 8232 || peek == 8233) {
            this.reader.forward();
            return String.valueOf(Character.toChars(peek));
        }
        return "";
    }
    
    static {
        NOT_HEXA = Pattern.compile("[^0-9A-Fa-f]");
        ESCAPE_REPLACEMENTS = new HashMap();
        ESCAPE_CODES = new HashMap();
        ScannerImpl.ESCAPE_REPLACEMENTS.put('0', "\u0000");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('a', "\u0007");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('b', "\b");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('t', "\t");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('n', "\n");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('v', "\u000b");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('f', "\f");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('r', "\r");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('e', "\u001b");
        ScannerImpl.ESCAPE_REPLACEMENTS.put(' ', " ");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('\"', "\"");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('\\', "\\");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('N', "\u0085");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('_', " ");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('L', "\u2028");
        ScannerImpl.ESCAPE_REPLACEMENTS.put('P', "\u2029");
        ScannerImpl.ESCAPE_CODES.put('x', 2);
        ScannerImpl.ESCAPE_CODES.put('u', 4);
        ScannerImpl.ESCAPE_CODES.put('U', 8);
    }
    
    private static class Chomping
    {
        private final Boolean value;
        private final int increment;
        
        public Chomping(final Boolean value, final int increment) {
            this.value = value;
            this.increment = increment;
        }
        
        public boolean chompTailIsNotFalse() {
            return this.value == null || this.value;
        }
        
        public boolean chompTailIsTrue() {
            return this.value != null && this.value;
        }
        
        public int getIncrement() {
            return this.increment;
        }
    }
}
