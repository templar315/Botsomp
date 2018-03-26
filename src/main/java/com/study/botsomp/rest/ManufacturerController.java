package com.study.botsomp.rest;

import com.study.botsomp.dto.ManufacturerDTO;
import com.study.botsomp.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manufacturers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @PostMapping
    public ResponseEntity<ManufacturerDTO> add(@RequestBody ManufacturerDTO manufacturerDTO) {
        try {
            return ResponseEntity.ok(manufacturerService.add(manufacturerDTO));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<ManufacturerDTO> update(@RequestBody ManufacturerDTO manufacturerDTO) {
        try {
            return ResponseEntity.ok(manufacturerService.update(manufacturerDTO));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            manufacturerService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> getOne(@PathVariable long id) {
        try {
            return ResponseEntity.ok(manufacturerService.getOne(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<ManufacturerDTO> getByName(@RequestBody String name) {
        try {
            return ResponseEntity.ok(manufacturerService.findByName(name));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<ManufacturerDTO> findAll() {
        return manufacturerService.findAll();
    }

    @GetMapping
    public ResponseEntity<List<ManufacturerDTO>> findByCountry(@RequestBody String country) {
        try {
            return ResponseEntity.ok(manufacturerService.findByCountry(country));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ManufacturerDTO>> findByCountryAndRegion(@RequestBody String country,
                                                                                    String region) {
        try {
            return ResponseEntity.ok(manufacturerService.findByCountryAndRegion(country, region));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ManufacturerDTO>> findByCity(@RequestBody String city) {
        try {
            return ResponseEntity.ok(manufacturerService.findByCity(city));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
