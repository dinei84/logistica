package com.logistica.collaborator;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collaborators")
public class CollaboratorController {

    private final CollaboratorService service;

    public CollaboratorController(CollaboratorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CollaboratorDTO> create(@RequestBody CollaboratorDTO dto) {
        return ResponseEntity.ok(service.addCollaborator(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollaboratorDTO> update(@PathVariable Long id, @RequestBody CollaboratorDTO dto) {
        return ResponseEntity.ok(service.updateCollaborator(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCollaborator(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CollaboratorDTO>> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllCollaborators(pageable));
    }


}
