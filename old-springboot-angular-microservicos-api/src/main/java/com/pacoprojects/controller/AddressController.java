package com.pacoprojects.controller;

import com.pacoprojects.model.AddressResponse;
import com.pacoprojects.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "address")
public class AddressController {

    private AddressService addressService;

    @GetMapping(path = "{cep}")
    public ResponseEntity<AddressResponse> getAddress(@PathVariable String cep) {
        return addressService.getAddress2(cep);
    }

}
