package com.internship.medicines.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Adapter adapter;
    private final HeaderService headerService;

    public boolean verifyRole(UserRole... expectedRole) {
        ResponseEntity<String> response = adapter
                .getResponseEntity(MicroserviceURLS.PDM, headerService.getToken(),
                        PdmEndpoints.GET_ROLE_BY_TOKEN, null, String.class);
        String role = response.getBody();

        if (role == null) {
            return false;
        }
        boolean result = false;
        for (UserRole actual : expectedRole) {
            if (actual.getRole().equals(role)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
