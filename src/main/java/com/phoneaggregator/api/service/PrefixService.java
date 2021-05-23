package com.phoneaggregator.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class PrefixService {

    private HashMap<Integer, HashMap<String, String>> prefixes;

    private final ResourceLoader resourceLoader;

    public PrefixService(ResourceLoader resourceLoader) throws IOException {
        this.prefixes = new HashMap<>();
        this.resourceLoader = resourceLoader;
        Resource resource = resourceLoader.getResource("classpath:prefixes.txt");

        try (Stream<String> stream = Files.lines(resource.getFile().toPath())) {
            stream.forEach( e ->  {
                if (prefixes.containsKey(e.length())) {
                    HashMap<String, String> elements = prefixes.get(e.length());
                    elements.put(e, e);
                } else {
                    HashMap<String, String> elements  = new HashMap<>();
                    elements.put(e, e);
                    prefixes.put(e.length(), elements );
                }
            });
        }
    }

    public String findPrefixByPhone(String phone) {
        Set<Integer> keys = prefixes.keySet();
        return keys.stream()
            .filter(k -> prefixes.get(k)
                .containsValue(phone.substring(0, k)))
            .map(key -> {
                return prefixes.get(key)
                    .get(phone.substring(0, key));
            })
            .findFirst()
            .orElse(null);
    }
}
