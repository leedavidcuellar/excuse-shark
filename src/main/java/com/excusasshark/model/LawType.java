package com.excusasshark.model;

/**
 * Enumeración de tipos de leyes/axiomas
 */
public enum LawType {
    MURPHY("Ley de Murphy - Si algo puede salir mal, saldrá mal"),
    HOFSTADTER("Ley de Hofstadter - Los plazos siempre son más largos de lo esperado"),
    PARKINSON("Ley de Parkinson - El trabajo se expande para llenar el tiempo disponible"),
    CONWAY("Ley de Conway - La arquitectura refleja la estructura organizacional"),
    POSTEL("Ley de robustez de Postel - Sé liberal en lo que aceptas"),
    BROOKS("Ley de Brooks - Agregar gente a un proyecto tard lo retrasa más"),
    WIRTH("Ley de Wirth - El software se vuelve lento más rápido que el hardware rápido"),
    DILBERT("Principio Dilbert - El caos es inevitable en la oficina"),
    DEVOPS("Principio DevOps - La automatización es la salvación"),
    DEVELOPER("Axioma del Developer - Stack overflow es nuestro mejor amigo");

    private final String description;

    LawType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
