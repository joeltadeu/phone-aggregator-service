package com.phoneaggregator.api.service.integration;

import com.phoneaggregator.api.service.integration.response.BusinessSectorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${business-sector-url}", name = "BusinessSectorApi")
public interface BusinessSectorCaller {

    @GetMapping(value = "/sector/{phone}", produces = "application/json")
    ResponseEntity<BusinessSectorResponse> getSectorByPhone(@PathVariable String phone);
}