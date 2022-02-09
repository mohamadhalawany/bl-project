package com.bl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.entity.ColorsEntity;

public interface ColorsRepository extends JpaRepository<ColorsEntity , Integer> {

}
