package com.logistica.controlelog.freight;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/freights")
public class FreightController {

    private final FreightService service;

    public FreightController(FreightService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FreightModel> createFreight(@RequestBody FreightDTO dto) {
        FreightModel freight = service.createFreight(dto);
        return ResponseEntity.ok(freight);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FreightModel> update(@PathVariable Long id, @RequestBody FreightDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFreight(@PathVariable Long id) {
        service.deleteFreight(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<Page<FreightModel>> getAllFreights(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<FreightModel> freightPage = service.findAll(pageable);
        return ResponseEntity.ok(freightPage);
    }
}
