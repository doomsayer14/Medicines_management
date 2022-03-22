package com.internship.medicines.auth;

/**
 * Domain urls for other microservices
 */
public enum MicroserviceURLS {
    /**
     * PDM - common beginning for url from <i>Patient Doctor Management</i>
     */
    PDM("https://pdm-service.herokuapp.com");

    private final String path;

    MicroserviceURLS(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
