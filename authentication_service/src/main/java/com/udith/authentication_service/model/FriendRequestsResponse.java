package com.udith.authentication_service.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendRequestsResponse{
    private List<UserResponse> friendResquests = new ArrayList<>();
}