package com.newspaper.repository;

import com.newspaper.model.User;
import com.newspaper.model.UserSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserSessionRepository {

    private static Map<String, UserSession> userSessions = new HashMap<>();

    public UserSession create(String sessionId, User user){
        UserSession userSession = UserSession.builder()
                .sessionId(sessionId)
                .user(user)
                .isValid(true)
                .build();
        userSessions.putIfAbsent(sessionId, userSession);
        return userSession;
    }

    public UserSession getBySessionId (String sessionId){
        UserSession userSession = userSessions.get(sessionId);
        if(userSession ==null || !userSession.getIsValid()){
            return null;
        }

        return userSession;
    }

    public void invalidateSession(String sessionId){
        UserSession userSession = userSessions.get(sessionId);
        if(userSession == null){
            return;
        }
        userSession.setIsValid(false);
    }


}