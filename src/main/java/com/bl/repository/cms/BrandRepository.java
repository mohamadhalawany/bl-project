package com.bl.repository.cms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.entity.cms.BrandEntity;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity , Integer> {

	public List<BrandEntity> findAllByIsActive(Integer isActive) ;
}
