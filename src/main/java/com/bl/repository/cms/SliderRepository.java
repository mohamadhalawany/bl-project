package com.bl.repository.cms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.entity.cms.SliderEntity;

@Repository
public interface SliderRepository extends JpaRepository<SliderEntity , Integer> {

}
