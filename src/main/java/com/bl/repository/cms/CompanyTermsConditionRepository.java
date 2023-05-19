package com.bl.repository.cms;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.entity.cms.CompanyTermsConditionEntity;

public interface CompanyTermsConditionRepository extends JpaRepository<CompanyTermsConditionEntity , Integer> {
	
	public CompanyTermsConditionEntity findByCompanyId(Integer companyId) ;
}
