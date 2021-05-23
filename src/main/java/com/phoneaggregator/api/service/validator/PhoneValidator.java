package com.phoneaggregator.api.service.validator;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class PhoneValidator {
    public List<String> validate(List<String> phoneNumbers ) {
        return phoneNumbers.stream()
            .filter( s -> null != s )
            .filter(s -> ! Pattern.matches(".*[a-zA-Z].*", s)  )
            .map( s -> s.replaceAll("\\+", "")
                .replaceAll(" ", "")
                .replaceFirst("^0+(?!$)", "" ))
            .collect(Collectors.toList());
    }
}
