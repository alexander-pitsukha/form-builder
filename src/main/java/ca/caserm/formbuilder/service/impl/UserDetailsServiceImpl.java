package ca.caserm.formbuilder.service.impl;

import ca.caserm.formbuilder.repositoty.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserCache userCache;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        var userDetails = userCache.getUserFromCache(username);
        if (userDetails == null) {
            userDetails = Optional.ofNullable(userRepository.findByEmail(username)).map(user ->
                            User.builder().username(user.getEmail()).password(user.getPassword()).roles(user.getRole().name()).build())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found."));
            userCache.putUserInCache(userDetails);
        }
        return userDetails;
    }

}
