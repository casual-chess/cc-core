package com.casualchess.core.service;

import com.casualchess.core.entity.SessionEntity;
import com.casualchess.core.repository.SessionRepository;
import com.casualchess.core.security.JwtCore;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/// SessionService maps sessionTokens (JWT) to userId.
/// Operations:
/// - createSession(userId): sessionToken
/// - deleteSession(sessionToken): void
/// - getUserId(sessionToken): userId
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final JwtCore jwtCore;

    @Autowired
    public SessionService(SessionRepository sessionRepository, JwtCore jwtCore) {
        this.sessionRepository = sessionRepository;
        this.jwtCore = jwtCore;
    }

    public String createSession(long userId) {
        SessionEntity session = sessionRepository.save(new SessionEntity(userId));
        return jwtCore.generateToken(session.getSessionId().toString());
    }

    public void deleteSession(String sessionToken) {
        extractSessionId(sessionToken).ifPresent(sessionId ->
            sessionRepository.deleteById(UUID.fromString(sessionId))
        );
    }

    public Optional<Long> getUserId(String sessionToken) {
        return extractSessionId(sessionToken).flatMap(sessionId ->
            sessionRepository.findById(UUID.fromString(sessionId))
                .map(SessionEntity::getUserId)
        );
    }

    private Optional<String> extractSessionId(String sessionToken) {
        try {
            return Optional.of(jwtCore.extractToken(sessionToken));
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
