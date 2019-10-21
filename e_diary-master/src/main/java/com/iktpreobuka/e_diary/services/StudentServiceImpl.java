package com.iktpreobuka.e_diary.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_diary.entities.StudentEntity;
import com.iktpreobuka.e_diary.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepo;

	// GET ALL
	@Override
	public List<StudentEntity> getAllStudents() {
		List<StudentEntity> students = (List<StudentEntity>) studentRepo.findAll();
		return students;
	}

	// GET BY ID
	@Override
	public StudentEntity findStudentById(Long id) {
		Optional<StudentEntity> s = studentRepo.findById(id);
		if (s.isPresent()) {
			return s.get();
		} else {
			return null;
		}
	}

	// POST
	@Override
	public StudentEntity saveStudent(StudentEntity student) {
		try {
			return studentRepo.save(student);
		} catch (Exception e) {
			return null;
		}
	}

	// PUT
	@Override
	public StudentEntity updateStudent(StudentEntity student, Long id) {
		try {
			Optional<StudentEntity> op = studentRepo.findById(id);
			StudentEntity s = op.get();
			s.updateStudent(student);
			return studentRepo.save(s);
		} catch (Exception e) {
			return null;
		}
	}

	// DELETE
	@Override
	public boolean removeStudent(Long id) {
		Optional<StudentEntity> s = studentRepo.findById(id);
		if (s.isPresent()) {
			studentRepo.deleteById(id);
			return true;
		}
		return false;
	}

	// List of all students ids for put method in class controller
	@Override
	public ArrayList<StudentEntity> getAllStudentsByID(ArrayList<Long> ids) {
		ArrayList<StudentEntity> students = new ArrayList<>();

		for (Long studentsId : ids) {
			Optional<StudentEntity> indbStudent = studentRepo.findById(studentsId);
			if (indbStudent.isPresent()) {
				students.add(indbStudent.get());
			} else {
				return null;
			}
		}
		return students;
	}

}
