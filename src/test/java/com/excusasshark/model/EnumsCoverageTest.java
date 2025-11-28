package com.excusasshark.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Enum Coverage Tests - 100%")
class EnumsCoverageTest {

    @Test
    @DisplayName("RoleType.values() retorna todos los valores")
    void roleType_Values_ReturnsAllValues() {
        RoleType[] values = RoleType.values();
        
        assertEquals(6, values.length);
        assertArrayEquals(
            new RoleType[]{
                RoleType.DEV,
                RoleType.QA,
                RoleType.DEVOPS,
                RoleType.PM,
                RoleType.ARCHITECT,
                RoleType.DBA
            },
            values
        );
    }

    @Test
    @DisplayName("FragmentType.values() retorna todos los valores")
    void fragmentType_Values_ReturnsAllValues() {
        FragmentType[] values = FragmentType.values();
        
        assertEquals(4, values.length);
        assertArrayEquals(
            new FragmentType[]{
                FragmentType.CONTEXTO,
                FragmentType.CAUSA,
                FragmentType.CONSECUENCIA,
                FragmentType.RECOMENDACION
            },
            values
        );
    }

    @Test
    @DisplayName("LawType.values() retorna todos los valores")
    void lawType_Values_ReturnsAllValues() {
        LawType[] values = LawType.values();
        
        assertEquals(10, values.length);
        assertArrayEquals(
            new LawType[]{
                LawType.MURPHY,
                LawType.HOFSTADTER,
                LawType.PARKINSON,
                LawType.CONWAY,
                LawType.POSTEL,
                LawType.BROOKS,
                LawType.WIRTH,
                LawType.DILBERT,
                LawType.DEVOPS,
                LawType.DEVELOPER
            },
            values
        );
    }

    @Test
    @DisplayName("RoleType.valueOf() funciona correctamente")
    void roleType_ValueOf_WorksCorrectly() {
        assertEquals(RoleType.DEV, RoleType.valueOf("DEV"));
        assertEquals(RoleType.QA, RoleType.valueOf("QA"));
        assertEquals(RoleType.DEVOPS, RoleType.valueOf("DEVOPS"));
        assertEquals(RoleType.PM, RoleType.valueOf("PM"));
        assertEquals(RoleType.ARCHITECT, RoleType.valueOf("ARCHITECT"));
        assertEquals(RoleType.DBA, RoleType.valueOf("DBA"));
    }

    @Test
    @DisplayName("RoleType.getDisplayName() retorna el nombre correcto")
    void roleType_GetDisplayName_ReturnsCorrectName() {
        assertEquals("Desarrollador", RoleType.DEV.getDisplayName());
        assertEquals("QA / Tester", RoleType.QA.getDisplayName());
        assertEquals("DevOps Engineer", RoleType.DEVOPS.getDisplayName());
        assertEquals("Project Manager", RoleType.PM.getDisplayName());
        assertEquals("Arquitecto", RoleType.ARCHITECT.getDisplayName());
        assertEquals("Database Administrator", RoleType.DBA.getDisplayName());
    }

    @Test
    @DisplayName("FragmentType.valueOf() funciona correctamente")
    void fragmentType_ValueOf_WorksCorrectly() {
        assertEquals(FragmentType.CONTEXTO, FragmentType.valueOf("CONTEXTO"));
        assertEquals(FragmentType.CAUSA, FragmentType.valueOf("CAUSA"));
        assertEquals(FragmentType.CONSECUENCIA, FragmentType.valueOf("CONSECUENCIA"));
        assertEquals(FragmentType.RECOMENDACION, FragmentType.valueOf("RECOMENDACION"));
    }

    @Test
    @DisplayName("LawType.valueOf() funciona correctamente")
    void lawType_ValueOf_WorksCorrectly() {
        assertEquals(LawType.MURPHY, LawType.valueOf("MURPHY"));
        assertEquals(LawType.HOFSTADTER, LawType.valueOf("HOFSTADTER"));
        assertEquals(LawType.DILBERT, LawType.valueOf("DILBERT"));
        assertEquals(LawType.DEVOPS, LawType.valueOf("DEVOPS"));
        assertEquals(LawType.DEVELOPER, LawType.valueOf("DEVELOPER"));
    }

    @Test
    @DisplayName("LawType.getDescription() retorna la descripción correcta")
    void lawType_GetDescription_ReturnsCorrectDescription() {
        assertEquals("Ley de Murphy - Si algo puede salir mal, saldrá mal", LawType.MURPHY.getDescription());
        assertEquals("Ley de Hofstadter - Los plazos siempre son más largos de lo esperado", LawType.HOFSTADTER.getDescription());
        assertEquals("Principio Dilbert - El caos es inevitable en la oficina", LawType.DILBERT.getDescription());
        assertEquals("Principio DevOps - La automatización es la salvación", LawType.DEVOPS.getDescription());
        assertEquals("Axioma del Developer - Stack overflow es nuestro mejor amigo", LawType.DEVELOPER.getDescription());
    }

    @Test
    @DisplayName("FragmentType.getDescription() retorna la descripción correcta")
    void fragmentType_GetDescription_ReturnsCorrectDescription() {
        assertEquals("Contexto: situación donde ocurrió", FragmentType.CONTEXTO.getDescription());
        assertEquals("Causa: razón root del problema", FragmentType.CAUSA.getDescription());
        assertEquals("Consecuencia: lo que pasó después", FragmentType.CONSECUENCIA.getDescription());
        assertEquals("Recomendación: cómo evitarlo", FragmentType.RECOMENDACION.getDescription());
    }
}
