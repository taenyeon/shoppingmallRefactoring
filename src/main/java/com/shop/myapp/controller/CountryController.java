package com.shop.myapp.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.myapp.dto.Country;
import com.shop.myapp.interceptor.Auth;
import com.shop.myapp.service.CountryService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/country")
@Slf4j
@Auth(role = Auth.Role.SELLER)
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("getCountry")
    @Auth(role = Auth.Role.ADMIN)
    public String get() throws ParseException, URISyntaxException {
        int result = countryService.getCountryFromAPI();
        log.info("들어간 값(234) : {}",result);
        return "";
    }

    @ResponseBody
    @GetMapping("")
    @Auth(role = Auth.Role.USER)
    public ResponseEntity<Object> getCountries(){
        List<Country> countries = countryService.getCountries();
        return ResponseEntity.ok(countries);
    }

}