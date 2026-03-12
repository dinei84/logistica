package com.logistica.administrator;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrators")
public class AdministratorController {

    private final AdministratorService service;

    public AdministratorController(AdministratorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AdministratorDTO> create(@RequestBody AdministratorDTO dto) {
        return ResponseEntity.ok(service.addAdministrator(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministratorDTO> update(@PathVariable Long id, @RequestBody AdministratorDTO dto) {
        return ResponseEntity.ok(service.updateAdministrator(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteAdministrator(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AdministratorDTO>> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllAdministrators(pageable));
    }
}
