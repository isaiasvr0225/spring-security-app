package com.example.service;

import com.example.persistence.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public @Service class UserDetailServiceImpl implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userEntity = userEntityRepository.findUserEntityByUsername(username)
                                                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();

        userEntity.getRoles()
                    .forEach(userRole -> grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_".concat(userRole.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                                .flatMap(role -> role.getPermissionSet().stream())
                                .forEach(permission -> grantedAuthorityList.add(new SimpleGrantedAuthority(permission.getName())));
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialsNoExpired(),
                userEntity.isAccountNoLocked(),
                grantedAuthorityList
        );
    }
}
