package com.iktpreobuka.e_diary.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_diary.entities.SchoolYearEntity;
import com.iktpreobuka.e_diary.repositories.SchoolYearRepository;

@Service
public class SchoolYearServiceImpl implements SchoolYearService {

	@Autowired
	private SchoolYearRepository yearRepo;

	// GET ALL
	@Override
	public List<SchoolYearEntity> getAllYear() {
		List<SchoolYearEntity> year = (List<SchoolYearEntity>) yearRepo.findAll();
		return year;
	}

	// GET BY ID
	@Override
	public SchoolYearEntity findYearById(Long id) {
		Optional<SchoolYearEntity> sy = yearRepo.findById(id);
		if (sy.isPresent()) {
			return sy.get();
		} else {
			return null;
		}
	}

	// POST
	@Override
	public SchoolYearEntity saveYear(SchoolYearEntity year) {
		try {
			return yearRepo.save(year);
		} catch (Exception e) {
			return null;
		}
	}

	// PUT
	@Override
	public SchoolYearEntity updateYear(SchoolYearEntity year, Long id) {
		try {
			Optional<SchoolYearEntity> sy = yearRepo.findById(id);
			SchoolYearEntity y = sy.get();
			y.setYear(year.getYear());
			return yearRepo.save(y);
		} catch (Exception e) {
			return null;
		}
	}

	// DELETE
	@Override
	public boolean removeParent(Long id) {
		Optional<SchoolYearEntity> sy = yearRepo.findById(id);
		if (sy.isPresent()) {
			yearRepo.deleteById(id);
			return true;
		}
		return false;
	}

}
