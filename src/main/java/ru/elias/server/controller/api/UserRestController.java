package ru.elias.server.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.server.dto.UserAuthorityUpdateDto;
import ru.elias.server.service.UserService;
import ru.elias.server.util.ApiPathConstants;

@Tag(name = "Users")
@RestController
@RequestMapping(ApiPathConstants.API_V_1 + ApiPathConstants.USERS)
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @Operation(summary = "Update user authority")
    @PatchMapping
    public ResponseEntity<Void> updateUserAuthority(@Validated @ParameterObject UserAuthorityUpdateDto dto) {
        return userService.updateUserAuthority(dto);
    }

}
