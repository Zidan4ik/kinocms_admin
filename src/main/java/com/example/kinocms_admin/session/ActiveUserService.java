package com.example.kinocms_admin.session;

import com.example.kinocms_admin.auth.UserDetailsImp;
import com.example.kinocms_admin.service.serviceimp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActiveUserService {
    @Autowired
    private SessionRegistry sessionRegistry;

    public List<UserDetailsImp> getOnlineUsers(String role) {
        return sessionRegistry.getAllPrincipals()
                .stream()
                .filter(principal -> principal instanceof UserDetails)
                .map(principal -> ((UserDetailsImp) principal))
                .filter(userDetails -> userDetails
                        .getAuthorities()
                        .stream()
                        .anyMatch(authority -> authority.getAuthority().equals(role)))
                        .collect(Collectors.toList());
    }
}
