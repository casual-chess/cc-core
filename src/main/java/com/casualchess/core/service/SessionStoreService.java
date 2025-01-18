package com.casualchess.core.service;

import com.casualchess.core.store.InMemorySessionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionStoreService {
    private final InMemorySessionStore sessionStore;

    @Autowired
    public SessionStoreService(InMemorySessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    public void addSession(String sessionId, String userId) {
        assert sessionId != null;
        sessionStore.addSession(sessionId, userId);
    }

    public boolean containsSessionId(String sessionId) {
        if (sessionId == null) return false;
        return sessionStore.containsSessionId(sessionId);
    }

    public Optional<String> getUserId(String sessionId) {
        if (sessionId == null) return Optional.empty();
        return sessionStore.getUserId(sessionId);
    }

    public boolean sessionForUserExists(String userId) {
        return sessionStore.sessionForUserExists(userId);
    }

}
