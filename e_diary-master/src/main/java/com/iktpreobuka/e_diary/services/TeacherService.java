package com.iktpreobuka.e_diary.services;

import java.util.ArrayList;
import java.util.List;

import com.iktpreobuka.e_diary.entities.TeacherEntity;

public interface TeacherService {
	
	public List<TeacherEntity> getAllTeachers();
	
	public TeacherEntity findTeacherById(Long id);
	
	public TeacherEntity saveTeacher(TeacherEntity teacher);
	
	public TeacherEntity updateTeacher(TeacherEntity teacher, Long id);
	
	public boolean removeTeacher(Long id);
	
	public ArrayList<TeacherEntity> getAllTeachersByID(ArrayList<Long> id);

}
