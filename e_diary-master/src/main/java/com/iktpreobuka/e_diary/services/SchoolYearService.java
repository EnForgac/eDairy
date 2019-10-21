package com.iktpreobuka.e_diary.services;

import java.util.List;

import com.iktpreobuka.e_diary.entities.SchoolYearEntity;

public interface SchoolYearService {
	
	public List<SchoolYearEntity> getAllYear();
	
	public SchoolYearEntity findYearById(Long id);
	
	public SchoolYearEntity saveYear(SchoolYearEntity year);
	
	public SchoolYearEntity updateYear(SchoolYearEntity year, Long id);
	
	public boolean removeParent(Long id);

}
