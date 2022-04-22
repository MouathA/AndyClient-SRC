package org.yaml.snakeyaml.error;

public class MarkedYAMLException extends YAMLException
{
    private static final long serialVersionUID = -9119388488683035101L;
    private String context;
    private Mark contextMark;
    private String problem;
    private Mark problemMark;
    private String note;
    
    protected MarkedYAMLException(final String s, final Mark mark, final String s2, final Mark mark2, final String s3) {
        this(s, mark, s2, mark2, s3, null);
    }
    
    protected MarkedYAMLException(final String context, final Mark contextMark, final String problem, final Mark problemMark, final String note, final Throwable t) {
        super(context + "; " + problem + "; " + problemMark, t);
        this.context = context;
        this.contextMark = contextMark;
        this.problem = problem;
        this.problemMark = problemMark;
        this.note = note;
    }
    
    protected MarkedYAMLException(final String s, final Mark mark, final String s2, final Mark mark2) {
        this(s, mark, s2, mark2, null, null);
    }
    
    protected MarkedYAMLException(final String s, final Mark mark, final String s2, final Mark mark2, final Throwable t) {
        this(s, mark, s2, mark2, null, t);
    }
    
    @Override
    public String getMessage() {
        return this.toString();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this.context != null) {
            sb.append(this.context);
            sb.append("\n");
        }
        if (this.contextMark != null && (this.problem == null || this.problemMark == null || this.contextMark.getName().equals(this.problemMark.getName()) || this.contextMark.getLine() != this.problemMark.getLine() || this.contextMark.getColumn() != this.problemMark.getColumn())) {
            sb.append(this.contextMark.toString());
            sb.append("\n");
        }
        if (this.problem != null) {
            sb.append(this.problem);
            sb.append("\n");
        }
        if (this.problemMark != null) {
            sb.append(this.problemMark.toString());
            sb.append("\n");
        }
        if (this.note != null) {
            sb.append(this.note);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public String getContext() {
        return this.context;
    }
    
    public Mark getContextMark() {
        return this.contextMark;
    }
    
    public String getProblem() {
        return this.problem;
    }
    
    public Mark getProblemMark() {
        return this.problemMark;
    }
}
