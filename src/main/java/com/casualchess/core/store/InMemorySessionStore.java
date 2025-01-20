package com.casualchess.core.store;


import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySessionStore {
    private static final String NO_MAPPING_SENTINEL = "";

    private final ConcurrentHashMap<String, String> sessionIdMap = new ConcurrentHashMap<>();

    public void addSession(@NonNull String sessionId, @Nullable String userName) {
        if (userName == null) userName = NO_MAPPING_SENTINEL;
        sessionIdMap.put(sessionId, userName);
    }

    public boolean containsSessionId(@NonNull String sessionId) {
        return sessionIdMap.containsKey(sessionId);
    }

    public Optional<String> getUserId(@NonNull String sessionId) {
        String userId = sessionIdMap.get(sessionId);
        if (userId == null || userId.equals(NO_MAPPING_SENTINEL))
            return Optional.empty();
        return Optional.of(userId);
    }

    public boolean sessionForUserExists(@NonNull String userName) {
        return sessionIdMap.contains(userName);
    }
}