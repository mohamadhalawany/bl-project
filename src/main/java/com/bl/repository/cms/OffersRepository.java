package com.bl.repository.cms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.entity.cms.OffersEntity;

@Repository
public interface OffersRepository extends JpaRepository<OffersEntity , Integer> {
	
}
