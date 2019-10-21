package com.iktpreobuka.e_diary.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.e_diary.entities.SemesterEntity;

public interface SemesterRepository extends CrudRepository<SemesterEntity, Long> {

	ArrayList<SemesterEntity> findBySchoolYearId(Long id);
	
}
