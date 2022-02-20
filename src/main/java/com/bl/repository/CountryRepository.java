package com.bl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.entity.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity , Integer> {

}
