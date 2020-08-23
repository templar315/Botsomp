package com.study.botsomp.rest;

import com.study.botsomp.dto.ProductDTO;
import com.study.botsomp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> add(@Validated(ProductDTO.New.class) @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO product = productService.add(productDTO);
            if(product != null) return ResponseEntity.ok(product);
            else return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<ProductDTO> update(@Validated(ProductDTO.Exist.class) @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO product = productService.update(productDTO);
            if(product != null) return ResponseEntity.ok(product);
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            if(productService.delete(id)) return ResponseEntity.ok().build();
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getOne(@PathVariable long id) {
        try {
            ProductDTO product = productService.getOne(id);
            if(product != null) return ResponseEntity.ok(product);
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping
    public List<ProductDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<ProductDTO>> findByName(@RequestParam("name") String name) {
        try {
            return ResponseEntity.ok(productService.findByName(name));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(params = "type")
    public ResponseEntity<List<ProductDTO>> findByType(@RequestParam("type") String type) {
        try {
            return ResponseEntity.ok(productService.findByType(type));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(params = {"name", "grade"})
    public ResponseEntity<ProductDTO> findByNameAndSteelGrade(@RequestParam("name") String name,
                                                              @RequestParam("grade") String grade) {
        try {
            return ResponseEntity.ok(productService.findByNameAndSteelGradeDesignation(name, grade));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(params = {"type", "grade"})
    public ResponseEntity<List<ProductDTO>> findByTypeAndSteelGrade(@RequestParam("type") String type,
                                                                    @RequestParam("grade") String grade) {
        try {
            return ResponseEntity.ok(productService.findByTypeAndSteelGradeDesignation(type, grade));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(params = {"grade", "standard"})
    public ResponseEntity<List<ProductDTO>> findBySteelGradeAndStandard(@RequestParam("grade") String grade,
                                                                        @RequestParam("standard") String standard) {
        try {
            return ResponseEntity.ok(productService.findBySteelGradeAndProductStandard(grade, standard));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
