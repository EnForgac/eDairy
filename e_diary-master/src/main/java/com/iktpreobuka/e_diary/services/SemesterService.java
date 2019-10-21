package com.iktpreobuka.e_diary.services;

import java.util.List;

import com.iktpreobuka.e_diary.entities.SemesterEntity;

public interface SemesterService {
	
	public List<SemesterEntity> getAllSemesters();
	
	public SemesterEntity findSemesterById(Long id);
	
	public SemesterEntity saveSemester(SemesterEntity semester);
	
	public SemesterEntity updateSemester(SemesterEntity semester, Long id);
	
	public boolean removeSemester(Long id);

}
