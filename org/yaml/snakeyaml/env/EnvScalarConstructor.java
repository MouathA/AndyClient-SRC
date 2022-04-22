package org.yaml.snakeyaml.env;

import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.constructor.*;
import org.yaml.snakeyaml.nodes.*;
import java.util.regex.*;

public class EnvScalarConstructor extends Constructor
{
    public static final Tag ENV_TAG;
    public static final Pattern ENV_FORMAT;
    
    public EnvScalarConstructor() {
        this.yamlConstructors.put(EnvScalarConstructor.ENV_TAG, new ConstructEnv(null));
    }
    
    public String apply(final String s, final String s2, final String s3, final String s4) {
        if (s4 != null && !s4.isEmpty()) {
            return s4;
        }
        if (s2 != null) {
            if (s2.equals("?") && s4 == null) {
                throw new MissingEnvironmentVariableException("Missing mandatory variable " + s + ": " + s3);
            }
            if (s2.equals(":?")) {
                if (s4 == null) {
                    throw new MissingEnvironmentVariableException("Missing mandatory variable " + s + ": " + s3);
                }
                if (s4.isEmpty()) {
                    throw new MissingEnvironmentVariableException("Empty mandatory variable " + s + ": " + s3);
                }
            }
            if (s2.startsWith(":")) {
                if (s4 == null || s4.isEmpty()) {
                    return s3;
                }
            }
            else if (s4 == null) {
                return s3;
            }
        }
        return "";
    }
    
    public String getEnv(final String s) {
        return System.getenv(s);
    }
    
    static String access$100(final EnvScalarConstructor envScalarConstructor, final ScalarNode scalarNode) {
        return envScalarConstructor.constructScalar(scalarNode);
    }
    
    static {
        ENV_TAG = new Tag("!ENV");
        ENV_FORMAT = Pattern.compile("^\\$\\{\\s*((?<name>\\w+)((?<separator>:?(-|\\?))(?<value>\\w+)?)?)\\s*\\}$");
    }
    
    private class ConstructEnv extends AbstractConstruct
    {
        final EnvScalarConstructor this$0;
        
        private ConstructEnv(final EnvScalarConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            final Matcher matcher = EnvScalarConstructor.ENV_FORMAT.matcher(EnvScalarConstructor.access$100(this.this$0, (ScalarNode)node));
            matcher.matches();
            final String group = matcher.group("name");
            final String group2 = matcher.group("value");
            return this.this$0.apply(group, matcher.group("separator"), (group2 != null) ? group2 : "", this.this$0.getEnv(group));
        }
        
        ConstructEnv(final EnvScalarConstructor envScalarConstructor, final EnvScalarConstructor$1 object) {
            this(envScalarConstructor);
        }
    }
}
