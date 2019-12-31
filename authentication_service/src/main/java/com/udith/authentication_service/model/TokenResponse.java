package com.udith.authentication_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse{
    private String token;
    private String userId;
}