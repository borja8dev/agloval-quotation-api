package com.agloval.application.port.in;

import com.agloval.application.dto.AuthResponse;
import com.agloval.application.dto.LoginRequest;
import com.agloval.application.dto.RefreshRequest;
import com.agloval.application.dto.RegisterRequest;

public interface AuthUseCase {

    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);

    AuthResponse refresh(RefreshRequest request);

    void logout(Long userId);
}
