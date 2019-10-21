package com.iktpreobuka.e_diary.services;

import java.util.ArrayList;
import java.util.List;

import com.iktpreobuka.e_diary.entities.StudentEntity;

public interface StudentService {
	
	public List<StudentEntity> getAllStudents();
	
	public StudentEntity findStudentById (Long id);
	
	public StudentEntity saveStudent(StudentEntity student);

	public StudentEntity updateStudent(StudentEntity student, Long id);
	
	public boolean removeStudent (Long id);
	
	public ArrayList<StudentEntity> getAllStudentsByID(ArrayList<Long> ids);
}
