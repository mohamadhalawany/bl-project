package com.bl.repository.cms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.entity.cms.MainPageBlockEntity;


@Repository
public interface MainPageBlockRepository extends JpaRepository<MainPageBlockEntity , Integer> {
	
	public List<MainPageBlockEntity> findAllByIsActive(Integer isActive) ;
	
}
