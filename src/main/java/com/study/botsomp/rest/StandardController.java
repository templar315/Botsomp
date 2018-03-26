package com.study.botsomp.rest;

import com.study.botsomp.dto.StandardDTO;
import com.study.botsomp.service.StandardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/standards")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StandardController {

    private final StandardService standardService;

    @PostMapping
    public ResponseEntity<StandardDTO> add(@RequestBody StandardDTO standardDTO) {
        try {
            return ResponseEntity.ok(standardService.add(standardDTO));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<StandardDTO> update(@RequestBody StandardDTO standardDTO) {
        try {
            return ResponseEntity.ok(standardService.update(standardDTO));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            standardService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardDTO> getOne(@PathVariable long id) {
        try {
            return ResponseEntity.ok(standardService.getOne(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<StandardDTO> findAll() {
        return standardService.findAll();
    }

    @GetMapping
    public ResponseEntity<StandardDTO> findByDesignation(@RequestBody String designation) {
        try {
            return ResponseEntity.ok(standardService.findByDesignation(designation));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
