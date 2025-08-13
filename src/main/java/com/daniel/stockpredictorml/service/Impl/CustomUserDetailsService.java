package com.daniel.stockpredictorml.service.Impl;



import com.daniel.stockpredictorml.models.entities.UserEntity;
import com.daniel.stockpredictorml.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user)
        );
    }

    private static java.util.Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) {
        String role = user.getRole().name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return Collections.singletonList(authority);
    }
}
