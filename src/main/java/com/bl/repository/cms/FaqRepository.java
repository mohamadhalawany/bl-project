package com.bl.repository.cms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.entity.cms.FaqEntity;

@Repository
public interface FaqRepository extends JpaRepository<FaqEntity , Integer> {

}
