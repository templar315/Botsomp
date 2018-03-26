package com.study.botsomp.rest;

import com.study.botsomp.dto.ProductDTO;
import com.study.botsomp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> add(@RequestBody ProductDTO productDTO) {
        try {
            return ResponseEntity.ok(productService.add(productDTO));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO) {
        try {
            return ResponseEntity.ok(productService.update(productDTO));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getOne(@PathVariable long id) {
        try {
            return ResponseEntity.ok(productService.getOne(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping
    public List<ProductDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findByName(@RequestBody String name) {
        try {
            return ResponseEntity.ok(productService.findByName(name));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findByType(@RequestBody String type) {
        try {
            return ResponseEntity.ok(productService.findByType(type));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<ProductDTO> findByNameAndSteelGrade(@RequestBody String name,
                                                                    @RequestBody String grade) {
        try {
            return ResponseEntity.ok(productService.findByNameAndSteelGradeDesignation(name, grade));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findByTypeAndSteelGrade(@RequestBody String type,
                                                                           @RequestBody String grade) {
        try {
            return ResponseEntity.ok(productService.findByTypeAndSteelGradeDesignation(type, grade));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findBySteelGradeAndStandard(@RequestBody String grade,
                                                                        @RequestBody String standard) {
        try {
            return ResponseEntity.ok(productService.findBySteelGradeAndProductStandard(grade, standard));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
