package com.user.visitorbackend.user.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String country,
        String state,
        String city,
        String postalCode,
        String addressLine,
        String signupSource,
        Boolean firstTimeUser,
        LocalDateTime createdAt
) {
}
