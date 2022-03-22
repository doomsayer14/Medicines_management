package com.internship.medicines.auth;

/**
 * Endpoints for <i>Patient Doctor Management</i>
 *
 * @see MicroserviceURLS
 * @see Endpoint
 */
public enum PdmEndpoints implements Endpoint {
    GET_ROLE_BY_TOKEN("/auth/role");

    private String path;

    PdmEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

