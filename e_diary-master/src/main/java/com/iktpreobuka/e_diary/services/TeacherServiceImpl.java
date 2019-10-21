package com.iktpreobuka.e_diary.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_diary.entities.TeacherEntity;
import com.iktpreobuka.e_diary.repositories.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRepository teacherRepo;

	// GET ALL
	@Override
	public List<TeacherEntity> getAllTeachers() {
		List<TeacherEntity> teachers = (List<TeacherEntity>) teacherRepo.findAll();
		return teachers;
	}

	// GET BY ID
	@Override
	public TeacherEntity findTeacherById(Long id) {
		Optional<TeacherEntity> teacher = teacherRepo.findById(id);
		if (teacher.isPresent()) {
			return teacher.get();
		} else {
			return null;
		}
	}

	// POST
	@Override
	public TeacherEntity saveTeacher(TeacherEntity teacher) {
		try {
			return teacherRepo.save(teacher);
		} catch (Exception e) {
			return null;
		}
	}

	// PUT
	@Override
	public TeacherEntity updateTeacher(TeacherEntity teacher, Long id) {
		try {
			Optional<TeacherEntity> op = teacherRepo.findById(id);
			TeacherEntity t = op.get();
			t.updateTeacher(teacher);
			return teacherRepo.save(t);
		} catch (Exception e) {
			return null;
		}
	}

	// DELETE
	@Override
	public boolean removeTeacher(Long id) {
		Optional<TeacherEntity> t = teacherRepo.findById(id);
		if (t.isPresent()) {
			teacherRepo.deleteById(id);
			return true;
		}
		return false;
	}
	
	// List of all teachers ids for put method in subjects
	public ArrayList<TeacherEntity> getAllTeachersByID(ArrayList<Long> ids) {
		ArrayList<TeacherEntity> teachers = new ArrayList<>();

		for (Long teacherId : ids) {
			Optional<TeacherEntity> indbTeacher = teacherRepo.findById(teacherId);
			if (indbTeacher.isPresent()) {
				teachers.add(indbTeacher.get());
			} else {
				return null;
			}
		}
		return teachers;
	}

}
