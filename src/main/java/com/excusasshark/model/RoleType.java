package com.excusasshark.model;

/**
 * Enumeración de roles para endpoints específicos
 */
public enum RoleType {
    DEV("Desarrollador"),
    QA("QA / Tester"),
    DEVOPS("DevOps Engineer"),
    PM("Project Manager"),
    ARCHITECT("Arquitecto"),
    DBA("Database Administrator");

    private final String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
