package com.excusasshark.controller;

import com.excusasshark.dto.FragmentRequestDTO;
import com.excusasshark.dto.FragmentResponseDTO;
import com.excusasshark.service.FragmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de fragmentos
 * Endpoints: GET /fragments, POST, PUT, DELETE
 */
@RestController
@RequestMapping("/api/fragments")
@RequiredArgsConstructor
@Tag(name = "Fragments", description = "Gestión de fragmentos de excusas")
public class FragmentController {

    private final FragmentService fragmentService;

    @GetMapping
    @Operation(summary = "Obtener todos los fragmentos")
    @ApiResponse(responseCode = "200", description = "Lista de fragmentos")
    public ResponseEntity<List<FragmentResponseDTO>> getAllFragments() {
        List<FragmentResponseDTO> fragments = fragmentService.getAll();
        return ResponseEntity.ok(fragments);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un fragmento por ID")
    @ApiResponse(responseCode = "200", description = "Fragmento encontrado")
    @ApiResponse(responseCode = "404", description = "Fragmento no encontrado")
    public ResponseEntity<FragmentResponseDTO> getFragmentById(@PathVariable Long id) {
        FragmentResponseDTO fragment = fragmentService.getById(id);
        if (fragment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fragment);
    }

    @GetMapping("/by-type")
    @Operation(summary = "Obtener fragmentos por tipo")
    @ApiResponse(responseCode = "200", description = "Fragmentos del tipo solicitado")
    public ResponseEntity<List<FragmentResponseDTO>> getFragmentsByType(
            @RequestParam String tipo) {
        List<FragmentResponseDTO> fragments = fragmentService.getByType(tipo);
        return ResponseEntity.ok(fragments);
    }

    @GetMapping("/active")
    @Operation(summary = "Obtener fragmentos activos")
    @ApiResponse(responseCode = "200", description = "Fragmentos activos")
    public ResponseEntity<List<FragmentResponseDTO>> getActiveFragments() {
        List<FragmentResponseDTO> fragments = fragmentService.getActive();
        return ResponseEntity.ok(fragments);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo fragmento")
    @ApiResponse(responseCode = "201", description = "Fragmento creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<FragmentResponseDTO> createFragment(
            @Valid @RequestBody FragmentRequestDTO dto) {
        FragmentResponseDTO created = fragmentService.createFromDTOResponse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un fragmento")
    @ApiResponse(responseCode = "200", description = "Fragmento actualizado")
    @ApiResponse(responseCode = "404", description = "Fragmento no encontrado")
    public ResponseEntity<FragmentResponseDTO> updateFragment(
            @PathVariable Long id,
            @Valid @RequestBody FragmentRequestDTO dto) {
        FragmentResponseDTO updated = fragmentService.updateFromDTO(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un fragmento")
    @ApiResponse(responseCode = "204", description = "Fragmento eliminado")
    @ApiResponse(responseCode = "404", description = "Fragmento no encontrado")
    public ResponseEntity<Void> deleteFragment(@PathVariable Long id) {
        boolean deleted = fragmentService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
