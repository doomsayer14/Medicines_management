package com.internship.medicines.auth;

/**
 * Marker interface for enums which contains
 * endpoints from other microservices
 */
public interface Endpoint {
    /**
     * @return full path (endpoint)
     */
    String getPath();
}
