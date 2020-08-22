package com.study.botsomp.rest;

import com.study.botsomp.dto.StandardDTO;
import com.study.botsomp.service.StandardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/standards")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StandardController {

    private final StandardService standardService;

    @PostMapping
    public ResponseEntity<StandardDTO> add(@Validated(StandardDTO.New.class) @RequestBody StandardDTO standardDTO) {
        try {
            StandardDTO standard = standardService.add(standardDTO);
            if(standard != null) return ResponseEntity.ok(standard);
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<StandardDTO> update(@Validated(StandardDTO.Exist.class) @RequestBody StandardDTO standardDTO) {
        try {
            StandardDTO standard = standardService.update(standardDTO);
            if(standard != null) return ResponseEntity.ok(standard);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            if(standardService.delete(id)) return ResponseEntity.ok().build();
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardDTO> getOne(@PathVariable long id) {
        try {
            StandardDTO standard = standardService.getOne(id);
            if(standard != null) return ResponseEntity.ok(standard);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<StandardDTO> findAll() {
        return standardService.findAll();
    }

}
