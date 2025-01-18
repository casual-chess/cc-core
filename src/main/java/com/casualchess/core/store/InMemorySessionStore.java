package com.casualchess.core.store;


import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySessionStore {
    private static final String NO_MAPPING_SENTINEL = "";

    private final ConcurrentHashMap<String, String> sessionIdMap = new ConcurrentHashMap<>();

    public void addSession(String sessionId, String userId) {
        assert sessionId != null;
        if (userId == null) userId = NO_MAPPING_SENTINEL;
        sessionIdMap.put(sessionId, userId);
    }

    public boolean containsSessionId(String sessionId) {
        assert sessionId != null;
        return sessionIdMap.containsKey(sessionId);
    }

    public Optional<String> getUserId(String sessionId) {
        assert sessionId != null;
        String userId = sessionIdMap.get(sessionId);
        if (userId == null || userId.equals(NO_MAPPING_SENTINEL))
            return Optional.empty();
        return Optional.of(userId);
    }

    public boolean sessionForUserExists(String userId) {
        assert userId != null;
        return sessionIdMap.contains(userId);
    }
}