package org.apache.http.auth;

public enum ChallengeState
{
    TARGET("TARGET", 0), 
    PROXY("PROXY", 1);
    
    private static final ChallengeState[] $VALUES;
    
    private ChallengeState(final String s, final int n) {
    }
    
    static {
        $VALUES = new ChallengeState[] { ChallengeState.TARGET, ChallengeState.PROXY };
    }
}
