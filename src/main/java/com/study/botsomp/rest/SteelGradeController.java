package com.study.botsomp.rest;

import com.study.botsomp.dto.SteelGradeDTO;
import com.study.botsomp.service.SteelGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class SteelGradeController {

    private final SteelGradeService steelGradeService;

    @PostMapping("/add")
    public @ResponseBody ResponseEntity addSteelGrade(@RequestBody SteelGradeDTO steelGradeDTO) {
        try {
            if(steelGradeDTO != null) {
                if (!steelGradeService.existsById(steelGradeDTO.getId())
                        && steelGradeService.findByDesignation(steelGradeDTO.getDesignation()) == null) {

                    steelGradeService.addOrUpdate(steelGradeDTO);

                    return new ResponseEntity<>("Steel grade has been added", HttpStatus.CREATED);
                } else return new ResponseEntity<>("Steel grade already exists", HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity<>("Missing request body", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Error saving", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity updateSteelGrade(@PathVariable long id,
                                                             @RequestBody SteelGradeDTO steelGradeDTO) {
        try {
            if(id > 0L && steelGradeDTO != null) {
                if(steelGradeService.existsById(id)) {
                    if ((steelGradeService.findByDesignation(steelGradeDTO.getDesignation()) == null)
                            || steelGradeService.findByDesignation(steelGradeDTO.getDesignation()).getId() == id) {

                        steelGradeService.addOrUpdate(steelGradeService
                                .getOne(id)
                                .toBuilder()
                                .designation(steelGradeDTO.getDesignation())
                                .gradeStandard(steelGradeDTO.getGradeStandard())
                                .build());

                        return new ResponseEntity<>("Steel Grade has been changed", HttpStatus.ACCEPTED);
                    } else return new ResponseEntity<>("Can not change steel grade", HttpStatus.NOT_MODIFIED);
                } else return new ResponseEntity<>("Can not found steel grade", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable or request body", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Update error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/id={id}")
    public @ResponseBody ResponseEntity deleteSteelGradeById(@PathVariable long id) {
        try {
            if(id > 0L) {
                if (steelGradeService.existsById(id)) {
                    steelGradeService.delete(id);

                    return new ResponseEntity<>("Steel grade deletion request accepted", HttpStatus.ACCEPTED);
                } else return new ResponseEntity<>("Can not found steel grade", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Deletion error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/designation={designation}")
    public @ResponseBody ResponseEntity deleteSteelGradeByDesignation(@PathVariable String designation) {
        try {
            if(!designation.isEmpty()) {
                if (steelGradeService.findByDesignation(designation) != null) {
                    steelGradeService.delete(designation);

                    return new ResponseEntity<>("Steel grade deletion request accepted", HttpStatus.ACCEPTED);
                } else return new ResponseEntity<>("Can not found steel grade", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Deletion error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsSteelGradeById(@PathVariable long id) {
        try {
            if(id > 0L) {
                return new ResponseEntity<>(steelGradeService.existsById(id), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/id={id}")
    public ResponseEntity<SteelGradeDTO> getOneSteelGradeById(@PathVariable long id) {
        try {
            if(id > 0L) {
                if(steelGradeService.existsById(id)) {
                    return new ResponseEntity<>(steelGradeService.getOne(id), HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/designation={designation}")
    public ResponseEntity<SteelGradeDTO> getOneStandardByDesignation(@PathVariable String designation) {
        try {
            if(!designation.isEmpty()) {
                SteelGradeDTO steelGradeDTO = steelGradeService.findByDesignation(designation);
                if(steelGradeDTO != null) {
                    return new ResponseEntity<>(steelGradeDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/all")
    public List<SteelGradeDTO> findAll() {
        return steelGradeService.findAll();
    }

}
