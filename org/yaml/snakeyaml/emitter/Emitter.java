package org.yaml.snakeyaml.emitter;

import java.util.regex.*;
import org.yaml.snakeyaml.util.*;
import org.yaml.snakeyaml.*;
import java.util.concurrent.*;
import java.io.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.scanner.*;
import org.yaml.snakeyaml.reader.*;
import java.util.*;
import org.yaml.snakeyaml.events.*;

public final class Emitter implements Emitable
{
    public static final int MIN_INDENT = 1;
    public static final int MAX_INDENT = 10;
    private static final char[] SPACE;
    private static final Pattern SPACES_PATTERN;
    private static final Set INVALID_ANCHOR;
    private static final Map ESCAPE_REPLACEMENTS;
    private static final Map DEFAULT_TAG_PREFIXES;
    private final Writer stream;
    private final ArrayStack states;
    private EmitterState state;
    private final Queue events;
    private Event event;
    private final ArrayStack indents;
    private Integer indent;
    private int flowLevel;
    private boolean rootContext;
    private boolean mappingContext;
    private boolean simpleKeyContext;
    private int column;
    private boolean whitespace;
    private boolean indention;
    private boolean openEnded;
    private final Boolean canonical;
    private final Boolean prettyFlow;
    private final boolean allowUnicode;
    private int bestIndent;
    private final int indicatorIndent;
    private final boolean indentWithIndicator;
    private int bestWidth;
    private final char[] bestLineBreak;
    private final boolean splitLines;
    private final int maxSimpleKeyLength;
    private Map tagPrefixes;
    private String preparedAnchor;
    private String preparedTag;
    private ScalarAnalysis analysis;
    private DumperOptions.ScalarStyle style;
    private static final Pattern HANDLE_FORMAT;
    
    public Emitter(final Writer stream, final DumperOptions dumperOptions) {
        this.stream = stream;
        this.states = new ArrayStack(100);
        this.state = new ExpectStreamStart(null);
        this.events = new ArrayBlockingQueue(100);
        this.event = null;
        this.indents = new ArrayStack(10);
        this.indent = null;
        this.flowLevel = 0;
        this.mappingContext = false;
        this.simpleKeyContext = false;
        this.column = 0;
        this.whitespace = true;
        this.indention = true;
        this.openEnded = false;
        this.canonical = dumperOptions.isCanonical();
        this.prettyFlow = dumperOptions.isPrettyFlow();
        this.allowUnicode = dumperOptions.isAllowUnicode();
        this.bestIndent = 2;
        if (dumperOptions.getIndent() > 1 && dumperOptions.getIndent() < 10) {
            this.bestIndent = dumperOptions.getIndent();
        }
        this.indicatorIndent = dumperOptions.getIndicatorIndent();
        this.indentWithIndicator = dumperOptions.getIndentWithIndicator();
        this.bestWidth = 80;
        if (dumperOptions.getWidth() > this.bestIndent * 2) {
            this.bestWidth = dumperOptions.getWidth();
        }
        this.bestLineBreak = dumperOptions.getLineBreak().getString().toCharArray();
        this.splitLines = dumperOptions.getSplitLines();
        this.maxSimpleKeyLength = dumperOptions.getMaxSimpleKeyLength();
        this.tagPrefixes = new LinkedHashMap();
        this.preparedAnchor = null;
        this.preparedTag = null;
        this.analysis = null;
        this.style = null;
    }
    
    @Override
    public void emit(final Event event) throws IOException {
        this.events.add(event);
        while (!this.needMoreEvents()) {
            this.event = this.events.poll();
            this.state.expect();
            this.event = null;
        }
    }
    
    private boolean needMoreEvents() {
        if (this.events.isEmpty()) {
            return true;
        }
        final Event event = this.events.peek();
        if (event instanceof DocumentStartEvent) {
            return this.needEvents(1);
        }
        if (event instanceof SequenceStartEvent) {
            return this.needEvents(2);
        }
        return event instanceof MappingStartEvent && this.needEvents(3);
    }
    
    private boolean needEvents(final int n) {
        final Iterator iterator = this.events.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            final Event event = iterator.next();
            if (event instanceof DocumentStartEvent || event instanceof CollectionStartEvent) {
                int n2 = 0;
                ++n2;
            }
            else if (event instanceof DocumentEndEvent || event instanceof CollectionEndEvent) {
                int n2 = 0;
                --n2;
            }
            else if (event instanceof StreamEndEvent) {}
            if (-1 < 0) {
                return false;
            }
        }
        return this.events.size() < n + 1;
    }
    
    private void increaseIndent(final boolean b, final boolean b2) {
        this.indents.push(this.indent);
        if (this.indent == null) {
            if (b) {
                this.indent = this.bestIndent;
            }
            else {
                this.indent = 0;
            }
        }
        else if (!b2) {
            this.indent += this.bestIndent;
        }
    }
    
    private void expectNode(final boolean rootContext, final boolean mappingContext, final boolean simpleKeyContext) throws IOException {
        this.rootContext = rootContext;
        this.mappingContext = mappingContext;
        this.simpleKeyContext = simpleKeyContext;
        if (this.event instanceof AliasEvent) {
            this.expectAlias();
        }
        else {
            if (!(this.event instanceof ScalarEvent) && !(this.event instanceof CollectionStartEvent)) {
                throw new EmitterException("expected NodeEvent, but got " + this.event);
            }
            this.processAnchor("&");
            this.processTag();
            if (this.event instanceof ScalarEvent) {
                this.expectScalar();
            }
            else if (this.event instanceof SequenceStartEvent) {
                if (this.flowLevel != 0 || this.canonical || ((SequenceStartEvent)this.event).isFlow() || this.checkEmptySequence()) {
                    this.expectFlowSequence();
                }
                else {
                    this.expectBlockSequence();
                }
            }
            else if (this.flowLevel != 0 || this.canonical || ((MappingStartEvent)this.event).isFlow() || this.checkEmptyMapping()) {
                this.expectFlowMapping();
            }
            else {
                this.expectBlockMapping();
            }
        }
    }
    
    private void expectAlias() throws IOException {
        if (!(this.event instanceof AliasEvent)) {
            throw new EmitterException("Alias must be provided");
        }
        this.processAnchor("*");
        this.state = (EmitterState)this.states.pop();
    }
    
    private void expectScalar() throws IOException {
        this.increaseIndent(true, false);
        this.processScalar();
        this.indent = (Integer)this.indents.pop();
        this.state = (EmitterState)this.states.pop();
    }
    
    private void expectFlowSequence() throws IOException {
        this.writeIndicator("[", true, true, false);
        ++this.flowLevel;
        this.increaseIndent(true, false);
        if (this.prettyFlow) {
            this.writeIndent();
        }
        this.state = new ExpectFirstFlowSequenceItem(null);
    }
    
    private void expectFlowMapping() throws IOException {
        this.writeIndicator("{", true, true, false);
        ++this.flowLevel;
        this.increaseIndent(true, false);
        if (this.prettyFlow) {
            this.writeIndent();
        }
        this.state = new ExpectFirstFlowMappingKey(null);
    }
    
    private void expectBlockSequence() throws IOException {
        this.increaseIndent(false, this.mappingContext && !this.indention);
        this.state = new ExpectFirstBlockSequenceItem(null);
    }
    
    private void expectBlockMapping() throws IOException {
        this.increaseIndent(false, false);
        this.state = new ExpectFirstBlockMappingKey(null);
    }
    
    private boolean checkEmptySequence() {
        return this.event instanceof SequenceStartEvent && !this.events.isEmpty() && this.events.peek() instanceof SequenceEndEvent;
    }
    
    private boolean checkEmptyMapping() {
        return this.event instanceof MappingStartEvent && !this.events.isEmpty() && this.events.peek() instanceof MappingEndEvent;
    }
    
    private boolean checkEmptyDocument() {
        if (!(this.event instanceof DocumentStartEvent) || this.events.isEmpty()) {
            return false;
        }
        final Event event = this.events.peek();
        if (event instanceof ScalarEvent) {
            final ScalarEvent scalarEvent = (ScalarEvent)event;
            return scalarEvent.getAnchor() == null && scalarEvent.getTag() == null && scalarEvent.getImplicit() != null && scalarEvent.getValue().length() == 0;
        }
        return false;
    }
    
    private boolean checkSimpleKey() {
        if (this.event instanceof NodeEvent && ((NodeEvent)this.event).getAnchor() != null) {
            if (this.preparedAnchor == null) {
                this.preparedAnchor = prepareAnchor(((NodeEvent)this.event).getAnchor());
            }
            final int n = 0 + this.preparedAnchor.length();
        }
        String s = null;
        if (this.event instanceof ScalarEvent) {
            s = ((ScalarEvent)this.event).getTag();
        }
        else if (this.event instanceof CollectionStartEvent) {
            s = ((CollectionStartEvent)this.event).getTag();
        }
        if (s != null) {
            if (this.preparedTag == null) {
                this.preparedTag = this.prepareTag(s);
            }
            final int n2 = 0 + this.preparedTag.length();
        }
        if (this.event instanceof ScalarEvent) {
            if (this.analysis == null) {
                this.analysis = this.analyzeScalar(((ScalarEvent)this.event).getValue());
            }
            final int n3 = 0 + this.analysis.getScalar().length();
        }
        return 0 < this.maxSimpleKeyLength && (this.event instanceof AliasEvent || (this.event instanceof ScalarEvent && !this.analysis.isEmpty() && !this.analysis.isMultiline()) || this.checkEmptySequence() || this.checkEmptyMapping());
    }
    
    private void processAnchor(final String s) throws IOException {
        final NodeEvent nodeEvent = (NodeEvent)this.event;
        if (nodeEvent.getAnchor() == null) {
            this.preparedAnchor = null;
            return;
        }
        if (this.preparedAnchor == null) {
            this.preparedAnchor = prepareAnchor(nodeEvent.getAnchor());
        }
        this.writeIndicator(s + this.preparedAnchor, true, false, false);
        this.preparedAnchor = null;
    }
    
    private void processTag() throws IOException {
        String s;
        if (this.event instanceof ScalarEvent) {
            final ScalarEvent scalarEvent = (ScalarEvent)this.event;
            s = scalarEvent.getTag();
            if (this.style == null) {
                this.style = this.chooseScalarStyle();
            }
            if ((!this.canonical || s == null) && ((this.style == null && scalarEvent.getImplicit().canOmitTagInPlainScalar()) || (this.style != null && scalarEvent.getImplicit().canOmitTagInNonPlainScalar()))) {
                this.preparedTag = null;
                return;
            }
            if (scalarEvent.getImplicit().canOmitTagInPlainScalar() && s == null) {
                s = "!";
                this.preparedTag = null;
            }
        }
        else {
            final CollectionStartEvent collectionStartEvent = (CollectionStartEvent)this.event;
            s = collectionStartEvent.getTag();
            if ((!this.canonical || s == null) && collectionStartEvent.getImplicit()) {
                this.preparedTag = null;
                return;
            }
        }
        if (s == null) {
            throw new EmitterException("tag is not specified");
        }
        if (this.preparedTag == null) {
            this.preparedTag = this.prepareTag(s);
        }
        this.writeIndicator(this.preparedTag, true, false, false);
        this.preparedTag = null;
    }
    
    private DumperOptions.ScalarStyle chooseScalarStyle() {
        final ScalarEvent scalarEvent = (ScalarEvent)this.event;
        if (this.analysis == null) {
            this.analysis = this.analyzeScalar(scalarEvent.getValue());
        }
        if ((!scalarEvent.isPlain() && scalarEvent.getScalarStyle() == DumperOptions.ScalarStyle.DOUBLE_QUOTED) || this.canonical) {
            return DumperOptions.ScalarStyle.DOUBLE_QUOTED;
        }
        if (scalarEvent.isPlain() && scalarEvent.getImplicit().canOmitTagInPlainScalar() && (!this.simpleKeyContext || (!this.analysis.isEmpty() && !this.analysis.isMultiline())) && ((this.flowLevel != 0 && this.analysis.isAllowFlowPlain()) || (this.flowLevel == 0 && this.analysis.isAllowBlockPlain()))) {
            return null;
        }
        if (!scalarEvent.isPlain() && (scalarEvent.getScalarStyle() == DumperOptions.ScalarStyle.LITERAL || scalarEvent.getScalarStyle() == DumperOptions.ScalarStyle.FOLDED) && this.flowLevel == 0 && !this.simpleKeyContext && this.analysis.isAllowBlock()) {
            return scalarEvent.getScalarStyle();
        }
        if ((scalarEvent.isPlain() || scalarEvent.getScalarStyle() == DumperOptions.ScalarStyle.SINGLE_QUOTED) && this.analysis.isAllowSingleQuoted() && (!this.simpleKeyContext || !this.analysis.isMultiline())) {
            return DumperOptions.ScalarStyle.SINGLE_QUOTED;
        }
        return DumperOptions.ScalarStyle.DOUBLE_QUOTED;
    }
    
    private void processScalar() throws IOException {
        final ScalarEvent scalarEvent = (ScalarEvent)this.event;
        if (this.analysis == null) {
            this.analysis = this.analyzeScalar(scalarEvent.getValue());
        }
        if (this.style == null) {
            this.style = this.chooseScalarStyle();
        }
        final boolean b = !this.simpleKeyContext && this.splitLines;
        if (this.style == null) {
            this.writePlain(this.analysis.getScalar(), b);
        }
        else {
            switch (this.style) {
                case DOUBLE_QUOTED: {
                    this.writeDoubleQuoted(this.analysis.getScalar(), b);
                    break;
                }
                case SINGLE_QUOTED: {
                    this.writeSingleQuoted(this.analysis.getScalar(), b);
                    break;
                }
                case FOLDED: {
                    this.writeFolded(this.analysis.getScalar(), b);
                    break;
                }
                case LITERAL: {
                    this.writeLiteral(this.analysis.getScalar());
                    break;
                }
                default: {
                    throw new YAMLException("Unexpected style: " + this.style);
                }
            }
        }
        this.analysis = null;
        this.style = null;
    }
    
    private String prepareVersion(final DumperOptions.Version version) {
        if (version.major() != 1) {
            throw new EmitterException("unsupported YAML version: " + version);
        }
        return version.getRepresentation();
    }
    
    private String prepareTagHandle(final String s) {
        if (s.length() == 0) {
            throw new EmitterException("tag handle must not be empty");
        }
        if (s.charAt(0) != '!' || s.charAt(s.length() - 1) != '!') {
            throw new EmitterException("tag handle must start and end with '!': " + s);
        }
        if (!"!".equals(s) && !Emitter.HANDLE_FORMAT.matcher(s).matches()) {
            throw new EmitterException("invalid character in the tag handle: " + s);
        }
        return s;
    }
    
    private String prepareTagPrefix(final String s) {
        if (s.length() == 0) {
            throw new EmitterException("tag prefix must not be empty");
        }
        final StringBuilder sb = new StringBuilder();
        if (s.charAt(0) == '!') {}
        while (1 < s.length()) {
            int n = 0;
            ++n;
        }
        if (0 < 1) {
            sb.append(s, 0, 1);
        }
        return sb.toString();
    }
    
    private String prepareTag(final String s) {
        if (s.length() == 0) {
            throw new EmitterException("tag must not be empty");
        }
        if ("!".equals(s)) {
            return s;
        }
        String s2 = null;
        String substring = s;
        for (final String s3 : this.tagPrefixes.keySet()) {
            if (s.startsWith(s3) && ("!".equals(s3) || s3.length() < s.length())) {
                s2 = s3;
            }
        }
        if (s2 != null) {
            substring = s.substring(s2.length());
            s2 = (String)this.tagPrefixes.get(s2);
        }
        final int length = substring.length();
        final String s4 = (length > 0) ? substring.substring(0, length) : "";
        if (s2 != null) {
            return s2 + s4;
        }
        return "!<" + s4 + ">";
    }
    
    static String prepareAnchor(final String s) {
        if (s.length() == 0) {
            throw new EmitterException("anchor must not be empty");
        }
        for (final Character c : Emitter.INVALID_ANCHOR) {
            if (s.indexOf(c) > -1) {
                throw new EmitterException("Invalid character '" + c + "' in the anchor: " + s);
            }
        }
        if (Emitter.SPACES_PATTERN.matcher(s).find()) {
            throw new EmitterException("Anchor may not contain spaces: " + s);
        }
        return s;
    }
    
    private ScalarAnalysis analyzeScalar(final String s) {
        if (s.length() == 0) {
            return new ScalarAnalysis(s, true, false, false, true, true, false);
        }
        if (s.startsWith("---") || s.startsWith("...")) {}
        final boolean b = s.length() == 1 || Constant.NULL_BL_T_LINEBR.has(s.codePointAt(1));
        while (0 < s.length()) {
            s.codePointAt(0);
            if (!false) {
                if ("#,[]{}&*!|>'\"%@`".indexOf(0) != -1) {}
                if ((0 != 63 && 0 != 58) || true) {}
                if (0 == 45 && true) {}
            }
            else {
                if (",?[]{}".indexOf(0) != -1) {}
                if (0 != 58 || true) {}
                if (0 != 35 || true) {}
            }
            Constant.LINEBR.has(0);
            if (false) {}
            if (0 == 10 || (32 <= 0 && 0 <= 126) || (0 != 133 && (0 < 160 || 0 > 55295) && (0 < 57344 || 0 > 65533) && (0 < 65536 || 0 > 1114111)) || !this.allowUnicode) {}
            if (0 == 32) {
                if (!false) {}
                if (0 == s.length() - 1) {}
                if (false) {}
            }
            else if (false) {
                if (!false) {}
                if (0 == s.length() - 1) {}
                if (false) {}
            }
            final int n = 0 + Character.charCount(0);
            final boolean b2 = Constant.NULL_BL_T.has(0) || false;
            if (1 < s.length()) {
                final int n2 = 0 + Character.charCount(s.codePointAt(0));
                if (1 >= s.length()) {
                    continue;
                }
                final boolean b3 = Constant.NULL_BL_T.has(s.codePointAt(1)) || false;
            }
        }
        if (true || true || true || true) {}
        if (true) {}
        if (true) {}
        if (true || true) {}
        if (true) {}
        if (true) {}
        if (true) {}
        return new ScalarAnalysis(s, false, true, false, false, true, false);
    }
    
    void flushStream() throws IOException {
        this.stream.flush();
    }
    
    void writeStreamStart() {
    }
    
    void writeStreamEnd() throws IOException {
        this.flushStream();
    }
    
    void writeIndicator(final String s, final boolean b, final boolean whitespace, final boolean b2) throws IOException {
        if (!this.whitespace && b) {
            ++this.column;
            this.stream.write(Emitter.SPACE);
        }
        this.whitespace = whitespace;
        this.indention = (this.indention && b2);
        this.column += s.length();
        this.openEnded = false;
        this.stream.write(s);
    }
    
    void writeIndent() throws IOException {
        if (this.indent != null) {
            this.indent;
        }
        if (!this.indention || this.column > 0 || (this.column == 0 && !this.whitespace)) {
            this.writeLineBreak(null);
        }
        this.writeWhitespace(0 - this.column);
    }
    
    private void writeWhitespace(final int n) throws IOException {
        if (n <= 0) {
            return;
        }
        this.whitespace = true;
        final char[] array = new char[n];
        while (0 < array.length) {
            array[0] = ' ';
            int n2 = 0;
            ++n2;
        }
        this.column += n;
        this.stream.write(array);
    }
    
    private void writeLineBreak(final String s) throws IOException {
        this.whitespace = true;
        this.indention = true;
        this.column = 0;
        if (s == null) {
            this.stream.write(this.bestLineBreak);
        }
        else {
            this.stream.write(s);
        }
    }
    
    void writeVersionDirective(final String s) throws IOException {
        this.stream.write("%YAML ");
        this.stream.write(s);
        this.writeLineBreak(null);
    }
    
    void writeTagDirective(final String s, final String s2) throws IOException {
        this.stream.write("%TAG ");
        this.stream.write(s);
        this.stream.write(Emitter.SPACE);
        this.stream.write(s2);
        this.writeLineBreak(null);
    }
    
    private void writeSingleQuoted(final String s, final boolean b) throws IOException {
        this.writeIndicator("'", true, false, false);
        while (0 <= s.length()) {
            if (0 < s.length()) {
                s.charAt(0);
            }
            if (false) {
                if (!false || 0 != 32) {
                    if (!true && this.column > this.bestWidth && b && false && 0 != s.length()) {
                        this.writeIndent();
                    }
                    else {
                        this.column += 0;
                        this.stream.write(s, 0, 0);
                    }
                }
            }
            else if (false) {
                if (!false || Constant.LINEBR.hasNo(0)) {
                    if (s.charAt(0) == '\n') {
                        this.writeLineBreak(null);
                    }
                    final char[] charArray = s.substring(0, 0).toCharArray();
                    while (0 < charArray.length) {
                        final char c = charArray[0];
                        if (c == '\n') {
                            this.writeLineBreak(null);
                        }
                        else {
                            this.writeLineBreak(String.valueOf(c));
                        }
                        int n = 0;
                        ++n;
                    }
                    this.writeIndent();
                }
            }
            else if (Constant.LINEBR.has(0, "\u0000 '") && 0 < 0) {
                this.column += 0;
                this.stream.write(s, 0, 0);
            }
            if (0 == 39) {
                this.column += 2;
                this.stream.write("''");
            }
            if (false) {
                final boolean b2 = 0 == 32;
                Constant.LINEBR.has(0);
            }
            int n2 = 0;
            ++n2;
        }
        this.writeIndicator("'", false, false, false);
    }
    
    private void writeDoubleQuoted(final String s, final boolean b) throws IOException {
        this.writeIndicator("\"", true, false, false);
        while (0 <= s.length()) {
            Character value = null;
            if (0 < s.length()) {
                value = s.charAt(0);
            }
            int n = 0;
            if (value == null || "\"\\\u0085\u2028\u2029\ufeff".indexOf(value) != -1 || ' ' > value || value > '~') {
                if (0 < 0) {
                    this.column += 0;
                    this.stream.write(s, 0, 0);
                }
                if (value != null) {
                    String s2;
                    if (Emitter.ESCAPE_REPLACEMENTS.containsKey(value)) {
                        s2 = "\\" + Emitter.ESCAPE_REPLACEMENTS.get(value);
                    }
                    else if (!this.allowUnicode || !StreamReader.isPrintable(value)) {
                        if (value <= '\u00ff') {
                            final String string = "0" + Integer.toString(value, 16);
                            s2 = "\\x" + string.substring(string.length() - 2);
                        }
                        else if (value >= '\ud800' && value <= '\udbff') {
                            if (1 < s.length()) {
                                ++n;
                                final String string2 = "000" + Long.toHexString(Character.toCodePoint(value, s.charAt(0)));
                                s2 = "\\U" + string2.substring(string2.length() - 8);
                            }
                            else {
                                final String string3 = "000" + Integer.toString(value, 16);
                                s2 = "\\u" + string3.substring(string3.length() - 4);
                            }
                        }
                        else {
                            final String string4 = "000" + Integer.toString(value, 16);
                            s2 = "\\u" + string4.substring(string4.length() - 4);
                        }
                    }
                    else {
                        s2 = String.valueOf(value);
                    }
                    this.column += s2.length();
                    this.stream.write(s2);
                }
            }
            if (0 < 0 && 0 < s.length() - 1 && (value == ' ' || 0 >= 0) && this.column + 0 > this.bestWidth && b) {
                String string5;
                if (0 >= 0) {
                    string5 = "\\";
                }
                else {
                    string5 = s.substring(0, 0) + "\\";
                }
                if (0 < 0) {}
                this.column += string5.length();
                this.stream.write(string5);
                this.writeIndent();
                this.whitespace = false;
                this.indention = false;
                if (s.charAt(0) == ' ') {
                    final String s3 = "\\";
                    this.column += s3.length();
                    this.stream.write(s3);
                }
            }
            ++n;
        }
        this.writeIndicator("\"", false, false, false);
    }
    
    private String determineBlockHints(final String s) {
        final StringBuilder sb = new StringBuilder();
        if (Constant.LINEBR.has(s.charAt(0), " ")) {
            sb.append(this.bestIndent);
        }
        if (Constant.LINEBR.hasNo(s.charAt(s.length() - 1))) {
            sb.append("-");
        }
        else if (s.length() == 1 || Constant.LINEBR.has(s.charAt(s.length() - 2))) {
            sb.append("+");
        }
        return sb.toString();
    }
    
    void writeFolded(final String s, final boolean b) throws IOException {
        final String determineBlockHints = this.determineBlockHints(s);
        this.writeIndicator(">" + determineBlockHints, true, false, false);
        if (determineBlockHints.length() > 0 && determineBlockHints.charAt(determineBlockHints.length() - 1) == '+') {
            this.openEnded = true;
        }
        this.writeLineBreak(null);
        while (0 <= s.length()) {
            if (0 < s.length()) {
                s.charAt(0);
            }
            if (true) {
                if (!false || Constant.LINEBR.hasNo(0)) {
                    if (!true && false && 0 != 32 && s.charAt(0) == '\n') {
                        this.writeLineBreak(null);
                    }
                    final boolean b2 = 0 == 32;
                    final char[] charArray = s.substring(0, 0).toCharArray();
                    while (0 < charArray.length) {
                        final char c = charArray[0];
                        if (c == '\n') {
                            this.writeLineBreak(null);
                        }
                        else {
                            this.writeLineBreak(String.valueOf(c));
                        }
                        int n = 0;
                        ++n;
                    }
                    if (false) {
                        this.writeIndent();
                    }
                }
            }
            else if (false) {
                if (0 != 32) {
                    if (!true && this.column > this.bestWidth && b) {
                        this.writeIndent();
                    }
                    else {
                        this.column += 0;
                        this.stream.write(s, 0, 0);
                    }
                }
            }
            else if (Constant.LINEBR.has(0, "\u0000 ")) {
                this.column += 0;
                this.stream.write(s, 0, 0);
                if (!false) {
                    this.writeLineBreak(null);
                }
            }
            if (false) {
                Constant.LINEBR.has(0);
                final boolean b3 = 0 == 32;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    void writeLiteral(final String s) throws IOException {
        final String determineBlockHints = this.determineBlockHints(s);
        this.writeIndicator("|" + determineBlockHints, true, false, false);
        if (determineBlockHints.length() > 0 && determineBlockHints.charAt(determineBlockHints.length() - 1) == '+') {
            this.openEnded = true;
        }
        this.writeLineBreak(null);
        while (0 <= s.length()) {
            if (0 < s.length()) {
                s.charAt(0);
            }
            if (true) {
                if (!false || Constant.LINEBR.hasNo(0)) {
                    final char[] charArray = s.substring(0, 0).toCharArray();
                    while (0 < charArray.length) {
                        final char c = charArray[0];
                        if (c == '\n') {
                            this.writeLineBreak(null);
                        }
                        else {
                            this.writeLineBreak(String.valueOf(c));
                        }
                        int n = 0;
                        ++n;
                    }
                    if (false) {
                        this.writeIndent();
                    }
                }
            }
            else if (!false || Constant.LINEBR.has(0)) {
                this.stream.write(s, 0, 0);
                if (!false) {
                    this.writeLineBreak(null);
                }
            }
            if (false) {
                Constant.LINEBR.has(0);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    void writePlain(final String s, final boolean b) throws IOException {
        if (this.rootContext) {
            this.openEnded = true;
        }
        if (s.length() == 0) {
            return;
        }
        if (!this.whitespace) {
            ++this.column;
            this.stream.write(Emitter.SPACE);
        }
        this.whitespace = false;
        this.indention = false;
        while (0 <= s.length()) {
            if (0 < s.length()) {
                s.charAt(0);
            }
            if (false) {
                if (0 != 32) {
                    if (!true && this.column > this.bestWidth && b) {
                        this.writeIndent();
                        this.whitespace = false;
                        this.indention = false;
                    }
                    else {
                        this.column += 0;
                        this.stream.write(s, 0, 0);
                    }
                }
            }
            else if (false) {
                if (Constant.LINEBR.hasNo(0)) {
                    if (s.charAt(0) == '\n') {
                        this.writeLineBreak(null);
                    }
                    final char[] charArray = s.substring(0, 0).toCharArray();
                    while (0 < charArray.length) {
                        final char c = charArray[0];
                        if (c == '\n') {
                            this.writeLineBreak(null);
                        }
                        else {
                            this.writeLineBreak(String.valueOf(c));
                        }
                        int n = 0;
                        ++n;
                    }
                    this.writeIndent();
                    this.whitespace = false;
                    this.indention = false;
                }
            }
            else if (Constant.LINEBR.has(0, "\u0000 ")) {
                this.column += 0;
                this.stream.write(s, 0, 0);
            }
            if (false) {
                final boolean b2 = 0 == 32;
                Constant.LINEBR.has(0);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    static Event access$100(final Emitter emitter) {
        return emitter.event;
    }
    
    static EmitterState access$202(final Emitter emitter, final EmitterState state) {
        return emitter.state = state;
    }
    
    static boolean access$400(final Emitter emitter) {
        return emitter.openEnded;
    }
    
    static String access$500(final Emitter emitter, final DumperOptions.Version version) {
        return emitter.prepareVersion(version);
    }
    
    static Map access$602(final Emitter emitter, final Map tagPrefixes) {
        return emitter.tagPrefixes = tagPrefixes;
    }
    
    static Map access$700() {
        return Emitter.DEFAULT_TAG_PREFIXES;
    }
    
    static Map access$600(final Emitter emitter) {
        return emitter.tagPrefixes;
    }
    
    static String access$800(final Emitter emitter, final String s) {
        return emitter.prepareTagHandle(s);
    }
    
    static String access$900(final Emitter emitter, final String s) {
        return emitter.prepareTagPrefix(s);
    }
    
    static Boolean access$1000(final Emitter emitter) {
        return emitter.canonical;
    }
    
    static boolean access$1100(final Emitter emitter) {
        return emitter.checkEmptyDocument();
    }
    
    static ArrayStack access$1500(final Emitter emitter) {
        return emitter.states;
    }
    
    static void access$1600(final Emitter emitter, final boolean b, final boolean b2, final boolean b3) throws IOException {
        emitter.expectNode(b, b2, b3);
    }
    
    static Integer access$1802(final Emitter emitter, final Integer indent) {
        return emitter.indent = indent;
    }
    
    static ArrayStack access$1900(final Emitter emitter) {
        return emitter.indents;
    }
    
    static int access$2010(final Emitter emitter) {
        return emitter.flowLevel--;
    }
    
    static int access$2100(final Emitter emitter) {
        return emitter.column;
    }
    
    static int access$2200(final Emitter emitter) {
        return emitter.bestWidth;
    }
    
    static boolean access$2300(final Emitter emitter) {
        return emitter.splitLines;
    }
    
    static Boolean access$2400(final Emitter emitter) {
        return emitter.prettyFlow;
    }
    
    static boolean access$2700(final Emitter emitter) {
        return emitter.checkSimpleKey();
    }
    
    static boolean access$3200(final Emitter emitter) {
        return emitter.indentWithIndicator;
    }
    
    static int access$3300(final Emitter emitter) {
        return emitter.indicatorIndent;
    }
    
    static void access$3400(final Emitter emitter, final int n) throws IOException {
        emitter.writeWhitespace(n);
    }
    
    static Integer access$1800(final Emitter emitter) {
        return emitter.indent;
    }
    
    static {
        SPACE = new char[] { ' ' };
        SPACES_PATTERN = Pattern.compile("\\s");
        (INVALID_ANCHOR = new HashSet()).add('[');
        Emitter.INVALID_ANCHOR.add(']');
        Emitter.INVALID_ANCHOR.add('{');
        Emitter.INVALID_ANCHOR.add('}');
        Emitter.INVALID_ANCHOR.add(',');
        Emitter.INVALID_ANCHOR.add('*');
        Emitter.INVALID_ANCHOR.add('&');
        (ESCAPE_REPLACEMENTS = new HashMap()).put('\0', "0");
        Emitter.ESCAPE_REPLACEMENTS.put('\u0007', "a");
        Emitter.ESCAPE_REPLACEMENTS.put('\b', "b");
        Emitter.ESCAPE_REPLACEMENTS.put('\t', "t");
        Emitter.ESCAPE_REPLACEMENTS.put('\n', "n");
        Emitter.ESCAPE_REPLACEMENTS.put('\u000b', "v");
        Emitter.ESCAPE_REPLACEMENTS.put('\f', "f");
        Emitter.ESCAPE_REPLACEMENTS.put('\r', "r");
        Emitter.ESCAPE_REPLACEMENTS.put('\u001b', "e");
        Emitter.ESCAPE_REPLACEMENTS.put('\"', "\"");
        Emitter.ESCAPE_REPLACEMENTS.put('\\', "\\");
        Emitter.ESCAPE_REPLACEMENTS.put('\u0085', "N");
        Emitter.ESCAPE_REPLACEMENTS.put(' ', "_");
        Emitter.ESCAPE_REPLACEMENTS.put('\u2028', "L");
        Emitter.ESCAPE_REPLACEMENTS.put('\u2029', "P");
        (DEFAULT_TAG_PREFIXES = new LinkedHashMap()).put("!", "!");
        Emitter.DEFAULT_TAG_PREFIXES.put("tag:yaml.org,2002:", "!!");
        HANDLE_FORMAT = Pattern.compile("^![-_\\w]*!$");
    }
    
    private class ExpectBlockMappingValue implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectBlockMappingValue(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.writeIndent();
            this.this$0.writeIndicator(":", true, false, true);
            Emitter.access$1500(this.this$0).push(this.this$0.new ExpectBlockMappingKey(false));
            Emitter.access$1600(this.this$0, false, true, false);
        }
        
        ExpectBlockMappingValue(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectBlockMappingKey implements EmitterState
    {
        private final boolean first;
        final Emitter this$0;
        
        public ExpectBlockMappingKey(final Emitter this$0, final boolean first) {
            this.this$0 = this$0;
            this.first = first;
        }
        
        @Override
        public void expect() throws IOException {
            if (!this.first && Emitter.access$100(this.this$0) instanceof MappingEndEvent) {
                Emitter.access$1802(this.this$0, (Integer)Emitter.access$1900(this.this$0).pop());
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1500(this.this$0).pop());
            }
            else {
                this.this$0.writeIndent();
                if (Emitter.access$2700(this.this$0)) {
                    Emitter.access$1500(this.this$0).push(this.this$0.new ExpectBlockMappingSimpleValue(null));
                    Emitter.access$1600(this.this$0, false, true, true);
                }
                else {
                    this.this$0.writeIndicator("?", true, false, true);
                    Emitter.access$1500(this.this$0).push(this.this$0.new ExpectBlockMappingValue(null));
                    Emitter.access$1600(this.this$0, false, true, false);
                }
            }
        }
    }
    
    private class ExpectBlockMappingSimpleValue implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectBlockMappingSimpleValue(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.writeIndicator(":", false, false, false);
            Emitter.access$1500(this.this$0).push(this.this$0.new ExpectBlockMappingKey(false));
            Emitter.access$1600(this.this$0, false, true, false);
        }
        
        ExpectBlockMappingSimpleValue(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectFirstBlockMappingKey implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectFirstBlockMappingKey(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.new ExpectBlockMappingKey(true).expect();
        }
        
        ExpectFirstBlockMappingKey(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectBlockSequenceItem implements EmitterState
    {
        private final boolean first;
        final Emitter this$0;
        
        public ExpectBlockSequenceItem(final Emitter this$0, final boolean first) {
            this.this$0 = this$0;
            this.first = first;
        }
        
        @Override
        public void expect() throws IOException {
            if (!this.first && Emitter.access$100(this.this$0) instanceof SequenceEndEvent) {
                Emitter.access$1802(this.this$0, (Integer)Emitter.access$1900(this.this$0).pop());
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1500(this.this$0).pop());
            }
            else {
                this.this$0.writeIndent();
                if (!Emitter.access$3200(this.this$0) || this.first) {
                    Emitter.access$3400(this.this$0, Emitter.access$3300(this.this$0));
                }
                this.this$0.writeIndicator("-", true, false, true);
                if (Emitter.access$3200(this.this$0) && this.first) {
                    Emitter.access$1802(this.this$0, Emitter.access$1800(this.this$0) + Emitter.access$3300(this.this$0));
                }
                Emitter.access$1500(this.this$0).push(this.this$0.new ExpectBlockSequenceItem(false));
                Emitter.access$1600(this.this$0, false, false, false);
            }
        }
    }
    
    private class ExpectFirstBlockSequenceItem implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectFirstBlockSequenceItem(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.new ExpectBlockSequenceItem(true).expect();
        }
        
        ExpectFirstBlockSequenceItem(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectFlowMappingValue implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectFlowMappingValue(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            if (Emitter.access$1000(this.this$0) || Emitter.access$2100(this.this$0) > Emitter.access$2200(this.this$0) || Emitter.access$2400(this.this$0)) {
                this.this$0.writeIndent();
            }
            this.this$0.writeIndicator(":", true, false, false);
            Emitter.access$1500(this.this$0).push(this.this$0.new ExpectFlowMappingKey(null));
            Emitter.access$1600(this.this$0, false, true, false);
        }
        
        ExpectFlowMappingValue(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectFlowMappingKey implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectFlowMappingKey(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            if (Emitter.access$100(this.this$0) instanceof MappingEndEvent) {
                Emitter.access$1802(this.this$0, (Integer)Emitter.access$1900(this.this$0).pop());
                Emitter.access$2010(this.this$0);
                if (Emitter.access$1000(this.this$0)) {
                    this.this$0.writeIndicator(",", false, false, false);
                    this.this$0.writeIndent();
                }
                if (Emitter.access$2400(this.this$0)) {
                    this.this$0.writeIndent();
                }
                this.this$0.writeIndicator("}", false, false, false);
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1500(this.this$0).pop());
            }
            else {
                this.this$0.writeIndicator(",", false, false, false);
                if (Emitter.access$1000(this.this$0) || (Emitter.access$2100(this.this$0) > Emitter.access$2200(this.this$0) && Emitter.access$2300(this.this$0)) || Emitter.access$2400(this.this$0)) {
                    this.this$0.writeIndent();
                }
                if (!Emitter.access$1000(this.this$0) && Emitter.access$2700(this.this$0)) {
                    Emitter.access$1500(this.this$0).push(this.this$0.new ExpectFlowMappingSimpleValue(null));
                    Emitter.access$1600(this.this$0, false, true, true);
                }
                else {
                    this.this$0.writeIndicator("?", true, false, false);
                    Emitter.access$1500(this.this$0).push(this.this$0.new ExpectFlowMappingValue(null));
                    Emitter.access$1600(this.this$0, false, true, false);
                }
            }
        }
        
        ExpectFlowMappingKey(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectFlowMappingSimpleValue implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectFlowMappingSimpleValue(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.writeIndicator(":", false, false, false);
            Emitter.access$1500(this.this$0).push(this.this$0.new ExpectFlowMappingKey(null));
            Emitter.access$1600(this.this$0, false, true, false);
        }
        
        ExpectFlowMappingSimpleValue(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectFirstFlowMappingKey implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectFirstFlowMappingKey(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            if (Emitter.access$100(this.this$0) instanceof MappingEndEvent) {
                Emitter.access$1802(this.this$0, (Integer)Emitter.access$1900(this.this$0).pop());
                Emitter.access$2010(this.this$0);
                this.this$0.writeIndicator("}", false, false, false);
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1500(this.this$0).pop());
            }
            else {
                if (Emitter.access$1000(this.this$0) || (Emitter.access$2100(this.this$0) > Emitter.access$2200(this.this$0) && Emitter.access$2300(this.this$0)) || Emitter.access$2400(this.this$0)) {
                    this.this$0.writeIndent();
                }
                if (!Emitter.access$1000(this.this$0) && Emitter.access$2700(this.this$0)) {
                    Emitter.access$1500(this.this$0).push(this.this$0.new ExpectFlowMappingSimpleValue(null));
                    Emitter.access$1600(this.this$0, false, true, true);
                }
                else {
                    this.this$0.writeIndicator("?", true, false, false);
                    Emitter.access$1500(this.this$0).push(this.this$0.new ExpectFlowMappingValue(null));
                    Emitter.access$1600(this.this$0, false, true, false);
                }
            }
        }
        
        ExpectFirstFlowMappingKey(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectFlowSequenceItem implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectFlowSequenceItem(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            if (Emitter.access$100(this.this$0) instanceof SequenceEndEvent) {
                Emitter.access$1802(this.this$0, (Integer)Emitter.access$1900(this.this$0).pop());
                Emitter.access$2010(this.this$0);
                if (Emitter.access$1000(this.this$0)) {
                    this.this$0.writeIndicator(",", false, false, false);
                    this.this$0.writeIndent();
                }
                this.this$0.writeIndicator("]", false, false, false);
                if (Emitter.access$2400(this.this$0)) {
                    this.this$0.writeIndent();
                }
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1500(this.this$0).pop());
            }
            else {
                this.this$0.writeIndicator(",", false, false, false);
                if (Emitter.access$1000(this.this$0) || (Emitter.access$2100(this.this$0) > Emitter.access$2200(this.this$0) && Emitter.access$2300(this.this$0)) || Emitter.access$2400(this.this$0)) {
                    this.this$0.writeIndent();
                }
                Emitter.access$1500(this.this$0).push(this.this$0.new ExpectFlowSequenceItem());
                Emitter.access$1600(this.this$0, false, false, false);
            }
        }
        
        ExpectFlowSequenceItem(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectFirstFlowSequenceItem implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectFirstFlowSequenceItem(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            if (Emitter.access$100(this.this$0) instanceof SequenceEndEvent) {
                Emitter.access$1802(this.this$0, (Integer)Emitter.access$1900(this.this$0).pop());
                Emitter.access$2010(this.this$0);
                this.this$0.writeIndicator("]", false, false, false);
                Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1500(this.this$0).pop());
            }
            else {
                if (Emitter.access$1000(this.this$0) || (Emitter.access$2100(this.this$0) > Emitter.access$2200(this.this$0) && Emitter.access$2300(this.this$0)) || Emitter.access$2400(this.this$0)) {
                    this.this$0.writeIndent();
                }
                Emitter.access$1500(this.this$0).push(this.this$0.new ExpectFlowSequenceItem(null));
                Emitter.access$1600(this.this$0, false, false, false);
            }
        }
        
        ExpectFirstFlowSequenceItem(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectDocumentRoot implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectDocumentRoot(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            Emitter.access$1500(this.this$0).push(this.this$0.new ExpectDocumentEnd(null));
            Emitter.access$1600(this.this$0, true, false, false);
        }
        
        ExpectDocumentRoot(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectDocumentEnd implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectDocumentEnd(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            if (Emitter.access$100(this.this$0) instanceof DocumentEndEvent) {
                this.this$0.writeIndent();
                if (((DocumentEndEvent)Emitter.access$100(this.this$0)).getExplicit()) {
                    this.this$0.writeIndicator("...", true, false, false);
                    this.this$0.writeIndent();
                }
                this.this$0.flushStream();
                Emitter.access$202(this.this$0, this.this$0.new ExpectDocumentStart(false));
                return;
            }
            throw new EmitterException("expected DocumentEndEvent, but got " + Emitter.access$100(this.this$0));
        }
        
        ExpectDocumentEnd(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectDocumentStart implements EmitterState
    {
        private final boolean first;
        final Emitter this$0;
        
        public ExpectDocumentStart(final Emitter this$0, final boolean first) {
            this.this$0 = this$0;
            this.first = first;
        }
        
        @Override
        public void expect() throws IOException {
            if (Emitter.access$100(this.this$0) instanceof DocumentStartEvent) {
                final DocumentStartEvent documentStartEvent = (DocumentStartEvent)Emitter.access$100(this.this$0);
                if ((documentStartEvent.getVersion() != null || documentStartEvent.getTags() != null) && Emitter.access$400(this.this$0)) {
                    this.this$0.writeIndicator("...", true, false, false);
                    this.this$0.writeIndent();
                }
                if (documentStartEvent.getVersion() != null) {
                    this.this$0.writeVersionDirective(Emitter.access$500(this.this$0, documentStartEvent.getVersion()));
                }
                Emitter.access$602(this.this$0, new LinkedHashMap(Emitter.access$700()));
                if (documentStartEvent.getTags() != null) {
                    for (final String s : new TreeSet<Object>(documentStartEvent.getTags().keySet())) {
                        final String s2 = documentStartEvent.getTags().get(s);
                        Emitter.access$600(this.this$0).put(s2, s);
                        this.this$0.writeTagDirective(Emitter.access$800(this.this$0, s), Emitter.access$900(this.this$0, s2));
                    }
                }
                if (!this.first || documentStartEvent.getExplicit() || Emitter.access$1000(this.this$0) || documentStartEvent.getVersion() != null || (documentStartEvent.getTags() != null && !documentStartEvent.getTags().isEmpty()) || Emitter.access$1100(this.this$0)) {
                    this.this$0.writeIndent();
                    this.this$0.writeIndicator("---", true, false, false);
                    if (Emitter.access$1000(this.this$0)) {
                        this.this$0.writeIndent();
                    }
                }
                Emitter.access$202(this.this$0, this.this$0.new ExpectDocumentRoot(null));
            }
            else {
                if (!(Emitter.access$100(this.this$0) instanceof StreamEndEvent)) {
                    throw new EmitterException("expected DocumentStartEvent, but got " + Emitter.access$100(this.this$0));
                }
                this.this$0.writeStreamEnd();
                Emitter.access$202(this.this$0, this.this$0.new ExpectNothing(null));
            }
        }
    }
    
    private class ExpectNothing implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectNothing(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            throw new EmitterException("expecting nothing, but got " + Emitter.access$100(this.this$0));
        }
        
        ExpectNothing(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectFirstDocumentStart implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectFirstDocumentStart(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.new ExpectDocumentStart(true).expect();
        }
        
        ExpectFirstDocumentStart(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
    
    private class ExpectStreamStart implements EmitterState
    {
        final Emitter this$0;
        
        private ExpectStreamStart(final Emitter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void expect() throws IOException {
            if (Emitter.access$100(this.this$0) instanceof StreamStartEvent) {
                this.this$0.writeStreamStart();
                Emitter.access$202(this.this$0, this.this$0.new ExpectFirstDocumentStart(null));
                return;
            }
            throw new EmitterException("expected StreamStartEvent, but got " + Emitter.access$100(this.this$0));
        }
        
        ExpectStreamStart(final Emitter emitter, final Emitter$1 object) {
            this(emitter);
        }
    }
}
