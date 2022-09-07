package ru.elias.server.mapper;

import java.util.Collections;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.elias.server.dto.UserAuthorityUpdateDto;
import ru.elias.server.dto.UserCreateDto;
import ru.elias.server.model.Role;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public ru.elias.server.model.User map(UserCreateDto dto) {
        var user = new ru.elias.server.model.User()
                .setUsername(dto.getUsername())
                .setPassword(dto.getRawPassword())
                .setRole(Role.USER);
        Optional.ofNullable(user.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
        return user;
    }

    public User map(ru.elias.server.model.User user) {
        return new User(user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole()));
    }

    public void updateRole(ru.elias.server.model.User entity, UserAuthorityUpdateDto dto) {
        entity.setRole(dto.getAuthority());
    }

}
