package com.study.botsomp.rest;

import com.study.botsomp.dto.ManufacturerDTO;
import com.study.botsomp.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manufacturers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @PostMapping
    public ResponseEntity<ManufacturerDTO> add(@Validated(ManufacturerDTO.New.class)
                                                   @RequestBody ManufacturerDTO manufacturerDTO) {
        try {
            ManufacturerDTO manufacturer = manufacturerService.add(manufacturerDTO);
            if(manufacturer != null) return ResponseEntity.ok(manufacturer);
            else return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<ManufacturerDTO> update(@Validated(ManufacturerDTO.Exist.class)
                                                      @RequestBody ManufacturerDTO manufacturerDTO) {
        try {
            ManufacturerDTO manufacturer = manufacturerService.update(manufacturerDTO);
            if(manufacturer != null) return ResponseEntity.ok(manufacturer);
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            if(manufacturerService.delete(id)) {
                return ResponseEntity.ok().build();
            }
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> getOne(@PathVariable long id) {
        try {
            ManufacturerDTO manufacturer = manufacturerService.getOne(id);
            if(manufacturer != null) return ResponseEntity.ok(manufacturer);
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<ManufacturerDTO> findAll() {
        return manufacturerService.findAll();
    }

    @GetMapping(params = "name")
    public ResponseEntity<ManufacturerDTO> findByName(@RequestParam("name") String name) {
        try {
            return ResponseEntity.ok(manufacturerService.findByName(name));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(params = "country")
    public ResponseEntity<List<ManufacturerDTO>> findByCountry(@RequestParam("country") String country) {
        try {
            return ResponseEntity.ok(manufacturerService.findByCountry(country));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(params = {"country", "region"})
    public ResponseEntity<List<ManufacturerDTO>> findByCountryAndRegion(@RequestParam("country") String country,
                                                                        @RequestParam("region") String region) {
        try {
            return ResponseEntity.ok(manufacturerService.findByCountryAndRegion(country, region));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(params = "city")
    public ResponseEntity<List<ManufacturerDTO>> findByCity(@RequestParam("city") String city) {
        try {
            return ResponseEntity.ok(manufacturerService.findByCity(city));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
