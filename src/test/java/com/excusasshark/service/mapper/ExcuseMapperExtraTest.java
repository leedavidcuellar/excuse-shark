package com.excusasshark.service.mapper;

import com.excusasshark.dto.ExcuseResponseDTO;
import com.excusasshark.model.Excuse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ExcuseMapper Additional Branch Tests")
class ExcuseMapperExtraTest {

    @Test
    @DisplayName("toResponse con solo meme y sin ley")
    void toResponseOnlyMeme() {
        Excuse excuse = Excuse.builder().id(5L).contexto("c").causa("c").consecuencia("c").recomendacion("r").meme("meme X").build();
        ExcuseResponseDTO dto = ExcuseMapper.toResponse(excuse);
        assertNotNull(dto.getMeme());
        assertNull(dto.getLey());
    }

    @Test
    @DisplayName("toResponse con solo ley y sin meme")
    void toResponseOnlyLaw() {
        Excuse excuse = Excuse.builder().id(6L).contexto("c").causa("c").consecuencia("c").recomendacion("r").ley("Ley Y").build();
        ExcuseResponseDTO dto = ExcuseMapper.toResponse(excuse);
        assertNull(dto.getMeme());
        assertNotNull(dto.getLey());
    }

    @Test
    @DisplayName("toResponse con roleTarget null no debería fallar")
    void toResponseRoleNull() {
        Excuse excuse = Excuse.builder().id(7L).contexto("c").causa("c").consecuencia("c").recomendacion("r").build();
        ExcuseResponseDTO dto = ExcuseMapper.toResponse(excuse);
        assertNull(dto.getRoleTarget());
    }
}
