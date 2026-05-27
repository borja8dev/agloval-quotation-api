package com.agloval.application.port.in;

import com.agloval.application.dto.UserRequest;
import com.agloval.application.dto.UserResponse;

import java.util.List;

public interface UserUseCase {

    UserResponse createUser(UserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}
