package com.iktpreobuka.e_diary.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_diary.entities.SemesterEntity;
import com.iktpreobuka.e_diary.repositories.SemesterRepository;

@Service
public class SemesterServiceImpl implements SemesterService {

	@Autowired
	private SemesterRepository semesterRepo;

	// GET ALL
	@Override
	public List<SemesterEntity> getAllSemesters() {
		List<SemesterEntity> semesters = (List<SemesterEntity>) semesterRepo.findAll();
		return semesters;
	}

	// GET BY ID
	@Override
	public SemesterEntity findSemesterById(Long id) {
		Optional<SemesterEntity> s = semesterRepo.findById(id);
		if (s.isPresent()) {
			return s.get();
		} else {
			return null;
		}
	}

	// POST
	@Override
	public SemesterEntity saveSemester(SemesterEntity semester) {
		Long id = semester.getSchoolYear().getId();

		ArrayList<SemesterEntity> semesters = semesterRepo.findBySchoolYearId(id);

		if (semesters.size() == 2) {
			return null;
		} else if ((semesters.size() == 0)
				|| ((semesters.size() == 1) && (semesters.get(0).getOrderNumber() != semester.getOrderNumber()))) {
			return semesterRepo.save(semester);

		} else
			return null;

	}

	// PUT
	@Override
	public SemesterEntity updateSemester(SemesterEntity semester, Long id) {
		try {
			Optional<SemesterEntity> op = semesterRepo.findById(id);
			SemesterEntity s = op.get();
			s.updateSemester(semester);
			return semesterRepo.save(s);
		} catch (Exception e) {
			return null;
		}
	}

	// DELETE
	@Override
	public boolean removeSemester(Long id) {
		Optional<SemesterEntity> s = semesterRepo.findById(id);
		if (s.isPresent()) {
			semesterRepo.deleteById(id);
			return true;
		}
		return false;
	}

}
