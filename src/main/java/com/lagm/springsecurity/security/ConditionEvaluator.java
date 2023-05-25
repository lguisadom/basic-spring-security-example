package com.lagm.springsecurity.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ConditionEvaluator {
    public boolean canPreAuth3(String param, Authentication authentication) {
        // var auth = SecurityContextHolder.getContext().getAuthentication(); === authentication
        return param.equals(authentication.getName()) && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
