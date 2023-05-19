package com.bl.repository.cms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.entity.cms.ContactFormEntity;

@Repository
public interface ContactFormRepository extends JpaRepository<ContactFormEntity , Integer> {

}
