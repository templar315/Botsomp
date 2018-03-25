package com.study.botsomp.rest;

import com.study.botsomp.dto.StandardDTO;
import com.study.botsomp.service.StandardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/standards")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class StandardController {

    private final StandardService standardService;

    @PostMapping("/add")
    public @ResponseBody ResponseEntity addStandard(@RequestBody StandardDTO standardDTO) {
        try {
            if(standardDTO != null) {
                if (!standardService.existsById(standardDTO.getId())
                        && standardService.findByDesignation(standardDTO.getDesignation()) == null) {

                    standardService.addOrUpdate(standardDTO);

                    return new ResponseEntity<>("Standard has been added", HttpStatus.CREATED);
                } else return new ResponseEntity<>("Standard already exists", HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity<>("Missing request body", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Error saving", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity updateStandard(@PathVariable long id,
                                                             @RequestBody StandardDTO standardDTO) {
        try {
            if(id > 0L && standardDTO != null) {
                if(standardService.existsById(id)) {
                    if (standardService.existsById(id)
                            && standardService.findByDesignation(standardDTO.getDesignation()) == null) {

                        standardService.addOrUpdate(standardService
                                .getOne(id)
                                .toBuilder()
                                .designation(standardDTO.getDesignation())
                                .build());

                        return new ResponseEntity<>("Standard has been changed", HttpStatus.ACCEPTED);
                    } else return new ResponseEntity<>("Can not change standard", HttpStatus.NOT_MODIFIED);
                } else return new ResponseEntity<>("Can not found standard", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable or request body", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Update error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/id={id}")
    public @ResponseBody ResponseEntity deleteStandard(@PathVariable long id) {
        try {
            if(id > 0L) {
                if (standardService.existsById(id)){

                    standardService.delete(id);

                    return new ResponseEntity<>("Standard deletion request accepted", HttpStatus.ACCEPTED);
                } else return new ResponseEntity<>("Can not found standard", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Deletion error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/designation={designation}")
    public @ResponseBody ResponseEntity deleteStandard(@PathVariable String designation) {
        try {
            if(!designation.isEmpty()) {
                if (standardService.findByDesignation(designation) != null) {

                    standardService.delete(designation);

                    return new ResponseEntity<>("Standard deletion request accepted", HttpStatus.ACCEPTED);
                } else return new ResponseEntity<>("Can not found standard", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Deletion error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsStandardById(@PathVariable long id) {
        try {
            if(id > 0L) {
                return new ResponseEntity<>(standardService.existsById(id), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/id={id}")
    public ResponseEntity<StandardDTO> getOneStandardById(@PathVariable long id) {
        try {
            if(id > 0L) {
                if(standardService.existsById(id)) {
                    return new ResponseEntity<>(standardService.getOne(id), HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/designation={designation}")
    public ResponseEntity<StandardDTO> getOneStandardByDesignation(@PathVariable String designation) {
        try {
            if(!designation.isEmpty()) {
                StandardDTO standardDTO = standardService.findByDesignation(designation);
                if(standardDTO != null) {
                    return new ResponseEntity<>(standardDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/all")
    public List<StandardDTO> findAll() {
        return standardService.findAll();
    }

}
