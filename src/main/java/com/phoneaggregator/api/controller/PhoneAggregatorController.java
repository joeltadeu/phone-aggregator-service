package com.phoneaggregator.api.controller;

import com.phoneaggregator.api.service.PhoneAggregatorService;
import com.phoneaggregator.api.service.validator.PhoneValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/aggregate")
public class PhoneAggregatorController {

    private final PhoneAggregatorService service;
    private final PhoneValidator validator;

    public PhoneAggregatorController(
        PhoneAggregatorService service, PhoneValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping(produces="application/json", consumes="application/json")
    public ResponseEntity<Map<String, Map<String,Integer>>> send(@RequestBody List<String> phones){
        List<String> validPhones = validator.validate(phones);
        Map<String, Map<String,Integer>> response = service.aggregate(validPhones);
        return ResponseEntity.ok(response);
    }
}
