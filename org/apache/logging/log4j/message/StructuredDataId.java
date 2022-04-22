package org.apache.logging.log4j.message;

import java.io.*;

public class StructuredDataId implements Serializable
{
    public static final StructuredDataId TIME_QUALITY;
    public static final StructuredDataId ORIGIN;
    public static final StructuredDataId META;
    public static final int RESERVED = -1;
    private static final long serialVersionUID = 9031746276396249990L;
    private static final int MAX_LENGTH = 32;
    private final String name;
    private final int enterpriseNumber;
    private final String[] required;
    private final String[] optional;
    
    protected StructuredDataId(final String name, final String[] required, final String[] optional) {
        if (name != null) {
            if (name.length() > 32) {
                throw new IllegalArgumentException(String.format("Length of id %s exceeds maximum of %d characters", name, 32));
            }
            name.indexOf("@");
        }
        if (-1 > 0) {
            this.name = name.substring(0, -1);
            this.enterpriseNumber = Integer.parseInt(name.substring(0));
        }
        else {
            this.name = name;
            this.enterpriseNumber = -1;
        }
        this.required = required;
        this.optional = optional;
    }
    
    public StructuredDataId(final String name, final int enterpriseNumber, final String[] required, final String[] optional) {
        if (name == null) {
            throw new IllegalArgumentException("No structured id name was supplied");
        }
        if (name.contains("@")) {
            throw new IllegalArgumentException("Structured id name cannot contain an '@");
        }
        if (enterpriseNumber <= 0) {
            throw new IllegalArgumentException("No enterprise number was supplied");
        }
        this.name = name;
        final String s = ((this.enterpriseNumber = enterpriseNumber) < 0) ? name : (name + "@" + enterpriseNumber);
        if (s.length() > 32) {
            throw new IllegalArgumentException("Length of id exceeds maximum of 32 characters: " + s);
        }
        this.required = required;
        this.optional = optional;
    }
    
    public StructuredDataId makeId(final StructuredDataId structuredDataId) {
        if (structuredDataId == null) {
            return this;
        }
        return this.makeId(structuredDataId.getName(), structuredDataId.getEnterpriseNumber());
    }
    
    public StructuredDataId makeId(final String s, final int n) {
        if (n <= 0) {
            return this;
        }
        String name;
        String[] required;
        String[] optional;
        if (this.name != null) {
            name = this.name;
            required = this.required;
            optional = this.optional;
        }
        else {
            name = s;
            required = null;
            optional = null;
        }
        return new StructuredDataId(name, n, required, optional);
    }
    
    public String[] getRequired() {
        return this.required;
    }
    
    public String[] getOptional() {
        return this.optional;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getEnterpriseNumber() {
        return this.enterpriseNumber;
    }
    
    public boolean isReserved() {
        return this.enterpriseNumber <= 0;
    }
    
    @Override
    public String toString() {
        return this.isReserved() ? this.name : (this.name + "@" + this.enterpriseNumber);
    }
    
    static {
        TIME_QUALITY = new StructuredDataId("timeQuality", null, new String[] { "tzKnown", "isSynced", "syncAccuracy" });
        ORIGIN = new StructuredDataId("origin", null, new String[] { "ip", "enterpriseId", "software", "swVersion" });
        META = new StructuredDataId("meta", null, new String[] { "sequenceId", "sysUpTime", "language" });
    }
}
