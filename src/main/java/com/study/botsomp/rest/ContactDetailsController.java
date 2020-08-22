package com.study.botsomp.rest;

import com.study.botsomp.dto.ContactDetailsDTO;
import com.study.botsomp.service.ContactDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactDetailsController {

    private final ContactDetailsService contactDetailsService;

    @PostMapping
    public ResponseEntity<ContactDetailsDTO> add(@Validated(ContactDetailsDTO.New.class)
                                                     @RequestBody ContactDetailsDTO contactDetailsDTO) {
        try {
            ContactDetailsDTO contactDetails = contactDetailsService.add(contactDetailsDTO);
            if(contactDetails != null) return ResponseEntity.ok(contactDetails);
            else return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<ContactDetailsDTO> update(@Validated(ContactDetailsDTO.Exist.class)
                                                        @RequestBody ContactDetailsDTO contactDetailsDTO) {
        try {
            ContactDetailsDTO contactDetails = contactDetailsService.update(contactDetailsDTO);
            if(contactDetails != null) return ResponseEntity.ok(contactDetails);
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            if(contactDetailsService.delete(id)) return ResponseEntity.ok().build();
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDetailsDTO> getOne(@PathVariable long id) {
        try {
            ContactDetailsDTO contactDetails = contactDetailsService.getOne(id);
            if(contactDetails != null) return ResponseEntity.ok(contactDetails);
            else return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<ContactDetailsDTO> findAll() {
        return contactDetailsService.findAll();
    }

}
