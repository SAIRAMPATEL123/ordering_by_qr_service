package com.user.visitorbackend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        @NotBlank String phoneNumber,
        @NotBlank String country,
        @NotBlank String state,
        @NotBlank String city,
        @NotBlank String postalCode,
        @NotBlank String addressLine,
        @NotBlank String signupSource
) {
}
