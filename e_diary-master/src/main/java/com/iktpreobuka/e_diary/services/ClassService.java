package com.iktpreobuka.e_diary.services;

import java.util.ArrayList;
import java.util.List;

import com.iktpreobuka.e_diary.entities.ClassEntity;
import com.iktpreobuka.e_diary.entities.StudentEntity;

public interface ClassService {
	
	public List<ClassEntity> getAllClasses();
	
	public ClassEntity findClassById(Long id);
	
	public ClassEntity saveClass (ClassEntity clas);
	
	public ClassEntity editClass(ClassEntity clas, Long id, ArrayList<StudentEntity> studs);
	
	public boolean removeClass (Long id);
	
	public ArrayList<ClassEntity> getAllClassesByID(ArrayList<Long> ids);

}
