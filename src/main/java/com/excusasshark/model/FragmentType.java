package com.excusasshark.model;

/**
 * Enumeración de tipos de fragmentos de excusas
 */
public enum FragmentType {
    CONTEXTO("Contexto: situación donde ocurrió"),
    CAUSA("Causa: razón root del problema"),
    CONSECUENCIA("Consecuencia: lo que pasó después"),
    RECOMENDACION("Recomendación: cómo evitarlo");

    private final String description;

    FragmentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
