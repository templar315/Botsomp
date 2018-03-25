package com.study.botsomp.rest;

import com.study.botsomp.dto.ProductDTO;
import com.study.botsomp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public @ResponseBody
    ResponseEntity addProduct(@RequestBody ProductDTO productDTO) {
        try {
            if(productDTO != null) {
                boolean check = true;

                for (ProductDTO product : productService.findAll()) {
                    if (product.getName().equals(productDTO.getName())
                            && product.getSteelGrade().equals(productDTO.getSteelGrade()))
                        check = false;
                }

                if (!productService.existsById(productDTO.getId()) && check) {

                    productService.addOrUpdate(productDTO);

                    return new ResponseEntity<>("Product has been added", HttpStatus.CREATED);
                } else return new ResponseEntity<>("Product already exists", HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity<>("Missing request body", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Error saving", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity updateManufacturer(@PathVariable long id,
                                                           @RequestBody ProductDTO productDTO) {
        try {
            if(id > 0L && productDTO != null) {
                if (productService.existsById(id)) {
                    ProductDTO product = productService.getOne(id);
                    if (product.getName().equals(productDTO.getName())
                            && product.getSteelGrade().equals(productDTO.getSteelGrade())
                            && product.getManufacturers() == productDTO.getManufacturers()) {

                        productDTO.setId(id);
                        productService.addOrUpdate(productDTO);

                        return new ResponseEntity<>("Manufacturer has been changed", HttpStatus.ACCEPTED);
                    } else return new ResponseEntity<>("Can not change product", HttpStatus.NOT_MODIFIED);
                } else return new ResponseEntity<>("Can not found product", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable or request body", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Update error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity deleteProduct(@PathVariable long id) {
        try {
            if(id > 0L) {
                if (productService.existsById(id)){

                    productService.delete(id);

                    return new ResponseEntity<>("Product deletion request accepted", HttpStatus.ACCEPTED);
                } else return new ResponseEntity<>("Can not found product", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Deletion error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsProductById(@PathVariable long id) {
        try {
            if(id > 0L) {
                return new ResponseEntity<>(productService.existsById(id), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/id={id}")
    public ResponseEntity<ProductDTO> getOneProductById(@PathVariable long id) {
        try {
            if(id > 0L) {
                if(productService.existsById(id)) {
                    return new ResponseEntity<>(productService.getOne(id), HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/all")
    public List<ProductDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping("/get/name={name}")
    public ResponseEntity<List<ProductDTO>> getProductsByName(@PathVariable String name) {
        try {
            if(!name.isEmpty()) {
                List<ProductDTO> productsDTO = productService.findByName(name);
                if(!productsDTO.isEmpty()) {
                    return new ResponseEntity<>(productsDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/type={type}")
    public ResponseEntity<List<ProductDTO>> getProductsByType(@PathVariable String type) {
        try {
            if(!type.isEmpty()) {
                List<ProductDTO> productsDTO = productService.findByType(type);
                if(!productsDTO.isEmpty()) {
                    return new ResponseEntity<>(productsDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/name={name}&grade={grade}")
    public ResponseEntity<ProductDTO> getProductByNameAndSteelGrade(@PathVariable String name,
                                                                            @PathVariable String grade) {
        try {
            if(!name.isEmpty() && !grade.isEmpty()) {
                ProductDTO productDTO = productService.findByNameAndSteelGradeDesignation(name, grade);
                if(productDTO != null) {
                    return new ResponseEntity<>(productDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/type={type}&grade={grade}")
    public ResponseEntity<List<ProductDTO>> getProductsByTypeAndSteelGrade(@PathVariable String type,
                                                                            @PathVariable String grade) {
        try {
            if(!type.isEmpty() && !grade.isEmpty()) {
                List<ProductDTO> productsDTO = productService.findByTypeAndSteelGradeDesignation(type, grade);
                if(productsDTO != null) {
                    return new ResponseEntity<>(productsDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/grade={grade}&standard={standard}")
    public ResponseEntity<List<ProductDTO>> getProductsBySteelGradeAndStandard(@PathVariable String grade,
                                                                                  @PathVariable String standard) {
        try {
            if(!grade.isEmpty() && !standard.isEmpty()) {
                List<ProductDTO> productsDTO = productService.findBySteelGradeAndProductStandard(grade, standard);
                if(productsDTO != null) {
                    return new ResponseEntity<>(productsDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
