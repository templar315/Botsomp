package com.study.botsomp.rest;

import com.study.botsomp.dto.ContactDetailsDTO;
import com.study.botsomp.service.ContactDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactDetailsController {

    private final ContactDetailsService contactDetailsService;
/*
    private final ProductService productService;

    private final StandardService standardService;

    private final ManufacturerService manufacturerService;

    private final SteelGradeService steelGradeService;
*/
    @PostMapping
    public ResponseEntity<ContactDetailsDTO> add(@RequestBody ContactDetailsDTO contactDetailsDTO) {
        try {
            return ResponseEntity.ok(contactDetailsService.add(contactDetailsDTO));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<ContactDetailsDTO> update(@RequestBody ContactDetailsDTO contactDetailsDTO) {
        try {
            return ResponseEntity.ok(contactDetailsService.update(contactDetailsDTO));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            contactDetailsService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDetailsDTO> getOne(@PathVariable long id) {
        try {
            return ResponseEntity.ok(contactDetailsService.getOne(id));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<ContactDetailsDTO> findAll() {
        return contactDetailsService.findAll();
    }



/*
    @GetMapping("/start")
    public ContactDetailsDTO start() {

        StandardDTO standard1 = StandardDTO.builder()
                .designation("GOST8240-89")
                .build();
        standardService.addOrUpdate(standard1);
        StandardDTO standard2 = StandardDTO.builder()
                .designation("GOST8509-93")
                .build();
        standardService.addOrUpdate(standard2);
        StandardDTO standard3 = StandardDTO.builder()
                .designation("GOST380-2005")
                .build();
        standardService.addOrUpdate(standard3);
        StandardDTO standard4 = StandardDTO.builder()
                .designation("GOST5058-65")
                .build();
        standardService.addOrUpdate(standard4);

        SteelGradeDTO steelGrade1 = SteelGradeDTO.builder()
                .designation("St5sp")
                .gradeStandard("GOST380-2005")
                .build();
        steelGradeService.addOrUpdate(steelGrade1);

        SteelGradeDTO steelGrade2 = SteelGradeDTO.builder()
                .designation("St3sp")
                .gradeStandard("GOST380-2005")
                .build();
        steelGradeService.addOrUpdate(steelGrade2);

        SteelGradeDTO steelGrade3 = SteelGradeDTO.builder()
                .designation("14G")
                .gradeStandard("GOST5058-65")
                .build();
        steelGradeService.addOrUpdate(steelGrade3);

        SteelGradeDTO steelGrade4 = SteelGradeDTO.builder()
                .designation("35GS")
                .gradeStandard("GOST5058-65")
                .build();
        steelGradeService.addOrUpdate(steelGrade4);

        ProductDTO product1 = ProductDTO.builder()
                .name("Steel hot-rolled channel #5")
                .type("Channel bars")
                .steelGrade("St5sp")
                .standard("GOST8240-89")
                .build();
        productService.addOrUpdate(product1);

        ProductDTO product2 = ProductDTO.builder()
                .name("Steel hot-rolled channel #5")
                .type("Channel bars")
                .steelGrade("St3sp")
                .standard("GOST8240-89")
                .build();
        productService.addOrUpdate(product2);

        ProductDTO product3 = ProductDTO.builder()
                .name("Corner steel hot-rolled equipotential #3")
                .type("Corners")
                .steelGrade("14G")
                .standard("GOST8509-93")
                .build();
        productService.addOrUpdate(product3);

        ProductDTO product4 = ProductDTO.builder()
                .name("Corner steel hot-rolled equipotential #3")
                .type("Corners")
                .steelGrade("35GS")
                .standard("GOST8509-93")
                .build();
        productService.addOrUpdate(product4);

        List<Long> productList1 = new ArrayList<>();
        productList1.add(productService
                .findByNameAndSteelGradeDesignation("Steel hot-rolled channel #5", "St5sp")
                .getId());
        productList1.add(productService
                .findByNameAndSteelGradeDesignation("Corner steel hot-rolled equipotential #3", "14G")
                .getId());

        ManufacturerDTO manufacturer = ManufacturerDTO.builder()
                .name("Azovstal")
                .country("Ukraine")
                .region("Donetsk region")
                .city("Mariupol")
                .address("Str. Unclearly, 100")
                .manufacturedProducts(productList1)
                .build();
        manufacturerService.addOrUpdate(manufacturer);

        ContactDetailsDTO contactDetails = ContactDetailsDTO.builder()
                .firstName("Vladimir")
                .lastName("Drobyshev")
                .position("Director of Commercial Affairs")
                .phone(380998541212L)
                .email("azovstal@com.ua")
                .manufacturer(manufacturerService.findByName("Azovstal").getId())
                .build();
        contactDetailsService.addOrUpdate(contactDetails);
        return contactDetailsService.findByManufacturerName("Azovstal");
    }
    */
}
