package com.phoneaggregator.api.service;

import com.phoneaggregator.api.service.integration.BusinessSectorCaller;
import com.phoneaggregator.api.service.integration.response.BusinessSectorResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class PhoneAggregatorService {

    private final PrefixService prefixService;
    private final BusinessSectorCaller caller;

    public PhoneAggregatorService(
        PrefixService prefixService, BusinessSectorCaller caller) {
        this.prefixService = prefixService;
        this.caller = caller;
    }

    public Map<String, Map<String,Integer>> aggregate(List<String> phones) {
        Map<String, Map<String,Integer>> prefixes = new TreeMap<>();
        phones.stream().forEach(phone -> {
            var response = caller.getSectorByPhone(phone);
            BusinessSectorResponse businessSector = response.getBody();
            var prefix = prefixService.findPrefixByPhone(phone);
            addPrefix(prefixes, businessSector.getSector(), prefix);
        });

        return prefixes;
    }

    private void addPrefix(Map<String, Map<String, Integer>> prefixes, String sector, String prefix) {
        if (prefixes.containsKey(prefix)) {
            if (prefixes.get(prefix).containsKey(sector)) {
                prefixes.get(prefix).merge(sector, 1, Integer::sum);
            } else {
                Map<String, Integer> sectors = prefixes.get(prefix);
                sectors.put(sector, 1);
                prefixes.replace(prefix, sectors);
            }
        } else {
            HashMap<String, Integer> sectors = new HashMap<>();
            sectors.put(sector, 1);
            prefixes.put(prefix, sectors);
        }
    }
}
