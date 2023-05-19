package com.bl.repository.cms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.entity.cms.CareerEntity;

@Repository
public interface CareerRepository extends JpaRepository<CareerEntity , Integer> {

}
