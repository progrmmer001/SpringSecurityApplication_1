package org.example.service;

import org.example.model.AuthUser;
import org.example.model.Permission;
import org.example.model.Role;
import org.example.repository.PermissionRepository;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser byName = userRepository.findByName(username);
        Set<GrantedAuthority> authorities = new HashSet<>();
        Long user_id = Long.valueOf(byName.getId());
        try {
            List<Role> roles = roleService.roles(user_id);
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
                List<Permission> permissions = permissionService.permissions(role.getId());
                for (Permission permission : permissions) {
                    authorities.add(new SimpleGrantedAuthority(permission.getPermissionName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User(byName.getUsername(), byName.getPassword(), authorities);
    }
}
