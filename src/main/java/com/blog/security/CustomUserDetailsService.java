package com.blog.security;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

///@Service annotation indicates that this class is a Spring service component.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Injecting UserRepository dependency through constructor
    private UserRepository userRepository; //search the user record in user database table i.e. is requeired  UserRepository

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //SEARCHING the record in database  based on Email or UserName
    //loadUserByUsername() method called by spring security automatically
    //loadUserByUsername method is part of the UserDetailsService interface and is automatically called by Spring Security when attempting to authenticate a user.
    // It retrieves a user from the database based on the provided username or email.
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {//here data fatching from database
        // Find user by username or email; throw exception if not found
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(
                        ()->new UsernameNotFoundException("User not found with usernme or email :"+usernameOrEmail)//this exception class is given by spring security
    );

// Return a UserDetails object representing the authenticated user
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));  // User class is inbult class from security
    }

    //mapRoleAuthorities method is used to convert the user's roles to a collection of GrantedAuthority objects, which are required by Spring Security.
   // Map roles to authorities
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {

        // Map roles to SimpleGrantedAuthority objects

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
