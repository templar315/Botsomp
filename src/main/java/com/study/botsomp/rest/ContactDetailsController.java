package com.study.botsomp.rest;

import com.study.botsomp.dto.ContactDetailsDTO;
import com.study.botsomp.service.ContactDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class ContactDetailsController {

    private final ContactDetailsService contactDetailsService;

    @PostMapping("/add")
    public @ResponseBody ResponseEntity addContactDetails(@RequestBody ContactDetailsDTO contactDetailsDTO) {
        try {
            if(contactDetailsDTO != null) {
                boolean check = true;

                for (ContactDetailsDTO contactDetails : contactDetailsService.findAll()) {
                    if (contactDetails.getManufacturer() == contactDetailsDTO.getManufacturer()) check = false;
                }

                if (!contactDetailsService.existsById(contactDetailsDTO.getId())
                        && contactDetailsDTO.getManufacturer() > 0L
                        && check) {

                    contactDetailsService.addOrUpdate(contactDetailsDTO);

                    return new ResponseEntity<>("Contact details have been added", HttpStatus.CREATED);
                } else return new ResponseEntity<>("Contact details already exists", HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity<>("Missing request body", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Error saving", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity updateContactDetails(@PathVariable long id,
                                                             @RequestBody ContactDetailsDTO contactDetailsDTO) {
        try {
            if(id > 0L && contactDetailsDTO != null) {
                if(contactDetailsService.existsById(id)) {
                    if (contactDetailsDTO.getManufacturer() == contactDetailsService.getOne(id).getManufacturer()) {

                        contactDetailsDTO.setId(id);
                        contactDetailsService.addOrUpdate(contactDetailsDTO);

                        return new ResponseEntity<>("Contact details has been changed", HttpStatus.ACCEPTED);
                    } else return new ResponseEntity<>("Can not change contact information", HttpStatus.NOT_MODIFIED);
                } else return new ResponseEntity<>("Can not found contact details", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable or request body", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Update error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity deleteContactDetails(@PathVariable long id) {
        try {
            if(id > 0L) {
                if (contactDetailsService.existsById(id)) {

                    contactDetailsService.delete(id);

                    return new ResponseEntity<>("Deletion request accepted", HttpStatus.ACCEPTED);
                } else return new ResponseEntity<>("Contact details not found", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Deletion error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ContactDetailsDTO> getOneContactDetails(@PathVariable long id) {
        try {
            if(id > 0L) {
                if(contactDetailsService.existsById(id)) {
                    return new ResponseEntity<>(contactDetailsService.getOne(id), HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsContactDetailsById(@PathVariable long id) {
        try {
            if(id > 0L) {
                return new ResponseEntity<>(contactDetailsService.existsById(id), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/all")
    public List<ContactDetailsDTO> findAll() {
        return contactDetailsService.findAll();
    }

    @GetMapping("/get/manufacturer/{id}")
    public ResponseEntity<ContactDetailsDTO> findContactDetailsByManufacturerId(@PathVariable long id) {
        try {
            if(id > 0L) {
                if(contactDetailsService.existsById(id)) {
                    return new ResponseEntity<>(contactDetailsService.findByManufacturerId(id), HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/manufacturer/{name}")
    public ResponseEntity<ContactDetailsDTO> findContactDetailsByManufacturerName(@PathVariable String name) {
        try {
            if(!name.isEmpty()) {
                ContactDetailsDTO contactDetailsDTO = contactDetailsService.findByManufacturerName(name);
                if(contactDetailsDTO != null) {
                    return new ResponseEntity<>(contactDetailsDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
