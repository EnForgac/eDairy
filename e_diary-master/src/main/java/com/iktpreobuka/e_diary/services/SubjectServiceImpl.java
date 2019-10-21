package com.iktpreobuka.e_diary.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_diary.entities.SubjectEntity;
import com.iktpreobuka.e_diary.repositories.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepo;
	
	// GET ALL
	@Override
	public List<SubjectEntity> getAllSubjects() {
		List<SubjectEntity> subjects = (List<SubjectEntity>) subjectRepo.findAll();
		return subjects;
	}
	
	// GET BY ID
	@Override
	public SubjectEntity findSubjectById(Long id) {
		Optional<SubjectEntity> s = subjectRepo.findById(id);
		if (s.isPresent()) {
			return s.get();
		} else {
			return null;
		}
	}
	
	// POST
	@Override
	public SubjectEntity saveSubject(SubjectEntity subject) {
		try {
			return subjectRepo.save(subject);
		} catch (Exception e) {
			return null;
		}
	}
	
	// PUT
	public SubjectEntity editSubject(SubjectEntity subject, Long id) {
		try {
			Optional<SubjectEntity> op = subjectRepo.findById(id);
			SubjectEntity s = op.get();
			s.updateSubject(subject);
			return subjectRepo.save(s);
		} catch (Exception e) {
			return null;
		}
	}

	// DELETE
	@Override
	public boolean removeSubject(Long id) {
		Optional<SubjectEntity> s = subjectRepo.findById(id);
		if (s.isPresent()) {
			subjectRepo.deleteById(id);
			return true;
		}
		return false;
	}

	// List of all subjects ids for put method in teacher controller
	@Override
	public ArrayList<SubjectEntity> getAllSubjectsByID(ArrayList<Long> ids) {
		ArrayList<SubjectEntity> subjects = new ArrayList<>();

		for (Long subjectId : ids) {
			Optional<SubjectEntity> indbSubjects = subjectRepo.findById(subjectId);
			if (indbSubjects.isPresent()) {
				subjects.add(indbSubjects.get());
			} else {
				return null;
			}
		}
		return subjects;
	}




	
	

}
