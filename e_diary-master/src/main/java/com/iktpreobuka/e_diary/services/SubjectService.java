package com.iktpreobuka.e_diary.services;


import java.util.ArrayList;
import java.util.List;

import com.iktpreobuka.e_diary.entities.SubjectEntity;

public interface SubjectService {
	
	public List<SubjectEntity> getAllSubjects();
	
	public SubjectEntity findSubjectById(Long id);
	
	public SubjectEntity saveSubject(SubjectEntity subjectEntity);
	
	public SubjectEntity editSubject(SubjectEntity subject, Long id);
	
	public boolean removeSubject(Long id);
	
	public ArrayList<SubjectEntity> getAllSubjectsByID(ArrayList<Long> ids);
	

}
