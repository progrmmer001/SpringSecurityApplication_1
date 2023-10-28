package org.example;

import org.example.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceBean extends UserService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return super.loadUserByUsername(username);
    }
}
