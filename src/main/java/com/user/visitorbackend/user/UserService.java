package com.user.visitorbackend.user;

import com.user.visitorbackend.user.dto.CreateUserRequest;
import com.user.visitorbackend.user.dto.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse createFirstTimeUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            User existingUser = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new UserValidationException("User exists but could not be loaded"));
            return mapToResponse(existingUser);
        }

        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            User existingUser = userRepository.findByPhoneNumber(request.phoneNumber())
                    .orElseThrow(() -> new UserValidationException("User exists but could not be loaded"));
            return mapToResponse(existingUser);
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());
        user.setCountry(request.country());
        user.setState(request.state());
        user.setCity(request.city());
        user.setPostalCode(request.postalCode());
        user.setAddressLine(request.addressLine());
        user.setSignupSource(request.signupSource());
        user.setFirstTimeUser(true);

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCountry(),
                user.getState(),
                user.getCity(),
                user.getPostalCode(),
                user.getAddressLine(),
                user.getSignupSource(),
                user.getFirstTimeUser(),
                user.getCreatedAt()
        );
    }
}
