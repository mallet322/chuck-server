package ru.elias.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.elias.server.dto.UserDto;
import ru.elias.server.mapper.UserMapper;
import ru.elias.server.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional
    public void save(UserDto dto) {
        var user = userMapper.map(dto);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var currentUser = userRepository.findByUsername(username)
                                        .map(userMapper::map)
                                        .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieved user: " + username));
        return currentUser;
    }

}
