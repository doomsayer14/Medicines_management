package com.internship.medicines.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
class HeaderService {
    private final HttpServletRequest request;

    /**
     * @return string of authorization token from header
     */
    public String getToken(){
        return request.getHeader("Authorization");
    }
}
