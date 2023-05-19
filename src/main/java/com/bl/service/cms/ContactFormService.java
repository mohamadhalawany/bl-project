package com.bl.service.cms;

import java.util.List;
import java.util.Map;

import com.bl.dto.cms.ContactFormDTO;

public interface ContactFormService {

	public ContactFormDTO findById(Integer id) ;
	public List<ContactFormDTO> findAll() ;
	public List<ContactFormDTO> next() ;
	public List<ContactFormDTO> previous() ;
	public void delete(Integer id) ;
	public void save(ContactFormDTO dto) ;
	public Map<String, Object> metaData() ;
}
