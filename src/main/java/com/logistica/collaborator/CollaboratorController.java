package com.logistica.collaborator;

import org.springframework.http.MediaType;
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

        @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public List<CollaboratorDTO> getAllCollaborators() {
            return service.getAllCollaborators();
        }

        @GetMapping(value = "/{id}")
        public CollaboratorDTO getCollaboratorById(@PathVariable Long id) {
            return service.getCollaboratorById(id);
        }

        @PostMapping
        public CollaboratorDTO createCollaborator(@RequestBody CollaboratorDTO collaboratorDTO) {
            return service.addCollaborator(collaboratorDTO);
        }

        @PutMapping("/{id}")
        public ResponseEntity<CollaboratorDTO> updateCollaborator(@PathVariable Long id, @RequestBody CollaboratorDTO collaboratorDTO) {
            CollaboratorDTO updatedCollaborator = service.updateCollaborator(id, collaboratorDTO);
            return ResponseEntity.ok(updatedCollaborator);
        }

        @DeleteMapping(value = "/{id}")
        public void deleteCollaborator(@PathVariable Long id) {
            service.deleteCollaborator(id);
        }
}
