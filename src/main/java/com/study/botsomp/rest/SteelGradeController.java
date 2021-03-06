package com.study.botsomp.rest;

import com.study.botsomp.dto.SteelGradeDTO;
import com.study.botsomp.service.SteelGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SteelGradeController {

    private final SteelGradeService steelGradeService;

    @PostMapping
    public ResponseEntity<SteelGradeDTO> add(@RequestBody SteelGradeDTO steelGradeDTO) {
        try {
            SteelGradeDTO steelGrade = steelGradeService.add(steelGradeDTO);
            if(steelGrade != null) return ResponseEntity.ok(steelGrade);
            else return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<SteelGradeDTO> update(@RequestBody SteelGradeDTO steelGradeDTO) {
        try {
            SteelGradeDTO steelGrade = steelGradeService.update(steelGradeDTO);
            if(steelGrade != null) return ResponseEntity.ok(steelGrade);
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            if(steelGradeService.delete(id)) return ResponseEntity.ok().build();
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SteelGradeDTO> getOne(@PathVariable long id) {
        try {
            SteelGradeDTO steelGrade = steelGradeService.getOne(id);
            if(steelGrade != null) return ResponseEntity.ok(steelGrade);
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<SteelGradeDTO> findAll() {
        return steelGradeService.findAll();
    }

}
