package com.mojang.authlib.yggdrasil;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.mojang.authlib.yggdrasil.response.*;
import com.mojang.authlib.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class YggdrasilGameProfileRepository implements GameProfileRepository
{
    private static final Logger LOGGER;
    private static final String BASE_URL = "https://api.mojang.com/";
    private static final String SEARCH_PAGE_URL = "https://api.mojang.com/profiles/";
    private static final int ENTRIES_PER_PAGE = 2;
    private static final int MAX_FAIL_COUNT = 3;
    private static final int DELAY_BETWEEN_PAGES = 100;
    private static final int DELAY_BETWEEN_FAILURES = 750;
    private final YggdrasilAuthenticationService authenticationService;
    
    public YggdrasilGameProfileRepository(final YggdrasilAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    @Override
    public void findProfilesByNames(final String[] array, final Agent agent, final ProfileLookupCallback profileLookupCallback) {
        final HashSet hashSet = Sets.newHashSet();
        while (0 < array.length) {
            final String s = array[0];
            if (!Strings.isNullOrEmpty(s)) {
                hashSet.add(s.toLowerCase());
            }
            int n = 0;
            ++n;
        }
        for (final List list : Iterables.partition(hashSet, 2)) {
            do {
                final ProfileSearchResultsResponse profileSearchResultsResponse = (ProfileSearchResultsResponse)this.authenticationService.makeRequest(HttpAuthenticationService.constantURL("https://api.mojang.com/profiles/" + agent.getName().toLowerCase()), list, ProfileSearchResultsResponse.class);
                YggdrasilGameProfileRepository.LOGGER.debug("Page {} returned {} results, parsing", 0, profileSearchResultsResponse.getProfiles().length);
                final HashSet hashSet2 = Sets.newHashSet(list);
                final GameProfile[] profiles = profileSearchResultsResponse.getProfiles();
                while (0 < profiles.length) {
                    final GameProfile gameProfile = profiles[0];
                    YggdrasilGameProfileRepository.LOGGER.debug("Successfully looked up profile {}", gameProfile);
                    hashSet2.remove(gameProfile.getName().toLowerCase());
                    profileLookupCallback.onProfileLookupSucceeded(gameProfile);
                    int n2 = 0;
                    ++n2;
                }
                for (final String s2 : hashSet2) {
                    YggdrasilGameProfileRepository.LOGGER.debug("Couldn't find profile {}", s2);
                    profileLookupCallback.onProfileLookupFailed(new GameProfile(null, s2), new ProfileNotFoundException("Server did not find the requested profile"));
                }
                Thread.sleep(100L);
            } while (true);
        }
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
