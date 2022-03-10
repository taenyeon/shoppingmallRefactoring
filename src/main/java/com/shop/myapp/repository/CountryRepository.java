package com.shop.myapp.repository;

import com.shop.myapp.dto.Country;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CountryRepository {
    int insertCountries(@Param("list") List<Country> countries);
    Country findByCountryCode(String countryCode);
    List<Country> findAll();
}
