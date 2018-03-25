package com.study.botsomp.rest;

import com.study.botsomp.dto.ManufacturerDTO;
import com.study.botsomp.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manufacturers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @PostMapping("/add")
    public @ResponseBody
    ResponseEntity addManufacturer(@RequestBody ManufacturerDTO manufacturerDTO) {
        try {
            if(manufacturerDTO != null) {
                boolean check = true;

                for (ManufacturerDTO manufacturer : manufacturerService.findAll()) {
                    if (manufacturer.getName().equals(manufacturerDTO.getName())
                            || manufacturer.getContactDetails().equals(manufacturerDTO.getContactDetails()))
                        check = false;
                }

                if (!manufacturerService.existsById(manufacturerDTO.getId()) && check) {

                    manufacturerService.addOrUpdate(manufacturerDTO);

                    return new ResponseEntity<>("Manufacturer has been added", HttpStatus.CREATED);
                } else return new ResponseEntity<>("Manufacturer already exists", HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity<>("Missing request body", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Error saving", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity updateManufacturer(@PathVariable long id,
                                                             @RequestBody ManufacturerDTO manufacturerDTO) {
        try {
            if(id > 0L && manufacturerDTO != null) {
                if (manufacturerService.existsById(id)) {
                    if (manufacturerService.getOne(id).getContactDetails().equals(manufacturerDTO.getContactDetails())) {

                        manufacturerDTO.setId(id);
                        manufacturerService.addOrUpdate(manufacturerDTO);

                        return new ResponseEntity<>("Manufacturer has been changed", HttpStatus.ACCEPTED);
                    } else return new ResponseEntity<>("Can not change manufacturer", HttpStatus.NOT_MODIFIED);
                } else return new ResponseEntity<>("Can not found manufacturer", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable or request body", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Update error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/id={id}")
    public @ResponseBody ResponseEntity deleteManufacturer(@PathVariable long id) {
        try {
            if(id > 0L) {
                if (manufacturerService.existsById(id)){

                    manufacturerService.delete(id);

                    return new ResponseEntity<>("Manufacturer deletion request accepted", HttpStatus.ACCEPTED);
                } else return new ResponseEntity<>("Can not found manufacturer", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Deletion error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/name={name}")
    public @ResponseBody ResponseEntity deleteManufacturer(@PathVariable String name) {
        try {
            if(!name.isEmpty()) {
                if (manufacturerService.findByName(name) != null) {

                    manufacturerService.delete(name);

                    return new ResponseEntity<>("Manufacturer deletion request accepted", HttpStatus.ACCEPTED);
                } else return new ResponseEntity<>("Can not found manufacturer", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>("Missing path variable", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>("Deletion error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsManufacturerById(@PathVariable long id) {
        try {
            if(id > 0L) {
                return new ResponseEntity<>(manufacturerService.existsById(id), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/id={id}")
    public ResponseEntity<ManufacturerDTO> getOneStandardById(@PathVariable long id) {
        try {
            if(id > 0L) {
                if(manufacturerService.existsById(id)) {
                    return new ResponseEntity<>(manufacturerService.getOne(id), HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/all")
    public List<ManufacturerDTO> findAll() {
        return manufacturerService.findAll();
    }

    @GetMapping("/get/name={name}")
    public ResponseEntity<ManufacturerDTO> getOneManufacturerByName(@PathVariable String name) {
        try {
            if(!name.isEmpty()) {
                ManufacturerDTO manufacturerDTO = manufacturerService.findByName(name);
                if(manufacturerDTO != null) {
                    return new ResponseEntity<>(manufacturerDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/country={country}")
    public ResponseEntity<List<ManufacturerDTO>> getManufacturersByCountry(@PathVariable String country) {
        try {
            if(!country.isEmpty()) {
                List<ManufacturerDTO> manufacturersDTO = manufacturerService.findByCountry(country);
                if(!manufacturersDTO.isEmpty()) {
                    return new ResponseEntity<>(manufacturersDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/country={country}&region={region}")
    public ResponseEntity<List<ManufacturerDTO>> getManufacturersByCountryAndRegion(@PathVariable String country,
                                                                                    @PathVariable String region) {
        try {
            if(!country.isEmpty() && !region.isEmpty()) {
                List<ManufacturerDTO> manufacturersDTO = manufacturerService.findByCountryAndRegion(country, region);
                if(!manufacturersDTO.isEmpty()) {
                    return new ResponseEntity<>(manufacturersDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/city={city}")
    public ResponseEntity<List<ManufacturerDTO>> getManufacturersByCountryAndRegion(@PathVariable String city) {
        try {
            if(!city.isEmpty()) {
                List<ManufacturerDTO> manufacturersDTO = manufacturerService.findByCity(city);
                if(!manufacturersDTO.isEmpty()) {
                    return new ResponseEntity<>(manufacturersDTO, HttpStatus.OK);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
