package com.bl.repository.cms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.entity.cms.MainMenuEntity;

public interface MainMenuRepository extends JpaRepository<MainMenuEntity , Integer> {
	
}
