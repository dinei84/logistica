package com.logistica.freight;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/freights")
public class FreightController {

    private final FreightService service;

    public FreightController(FreightService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FreightModel> createFreight(@RequestBody FreightDTO dto) {
        FreightModel freight = service.createFreight(dto);
        return ResponseEntity.ok(freight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FreightModel> update(@PathVariable Long id, @RequestBody FreightDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFreight(@PathVariable Long id) {
        service.deleteFreight(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<FreightModel>> getAllFreights(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<FreightModel> freightPage = service.findAll(pageable);
        return ResponseEntity.ok(freightPage);
    }
}
