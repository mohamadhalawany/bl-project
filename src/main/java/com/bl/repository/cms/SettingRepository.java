package com.bl.repository.cms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bl.entity.cms.SettingEntity;

@Repository
public interface SettingRepository extends JpaRepository<SettingEntity , Integer> {
	
	@Query("SELECT s FROM SettingEntity s WHERE s.id != 1 AND s.sliceItems = 1")
	public List<SettingEntity> findAllForSlice() ;
}
