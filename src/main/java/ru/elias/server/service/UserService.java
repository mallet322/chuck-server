package ru.elias.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.elias.server.dto.UserAuthorityUpdateDto;
import ru.elias.server.dto.UserCreateDto;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.exception.ErrorType;
import ru.elias.server.mapper.UserMapper;
import ru.elias.server.repository.UserRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final MessageSourceHelper messageSourceHelper;

    @Transactional
    public void save(UserCreateDto dto) {
        var user = userMapper.map(dto);
        userRepository.save(user);
    }

    @Transactional
    public ResponseEntity<Void> updateUserAuthority(UserAuthorityUpdateDto dto) {
        var entity = userRepository.findById(dto.getId())
                                   .orElseThrow(() -> {
                                       var errorType = ErrorType.USER_NOT_FOUND_BY_ID;
                                       var msg = messageSourceHelper.getMessage(errorType, dto.getId());
                                       log.warn(msg);
                                       throw new BusinessException(errorType, msg);
                                   });
        userMapper.updateRole(entity, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                             .map(userMapper::map)
                             .orElseThrow(() -> {
                                 var errorType = ErrorType.USER_NOT_FOUND_BY_NAME;
                                 var msg = messageSourceHelper.getMessage(errorType, username);
                                 log.warn(msg);
                                 throw new BusinessException(errorType, msg);
                             });
    }

}
