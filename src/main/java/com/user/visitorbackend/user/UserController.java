package com.user.visitorbackend.user;

import com.user.visitorbackend.user.dto.CreateUserRequest;
import com.user.visitorbackend.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/first-time")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createFirstTimeUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createFirstTimeUser(request);
    }
}
