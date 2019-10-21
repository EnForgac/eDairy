package com.iktpreobuka.e_diary.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.e_diary.entities.ClassEntity;
import com.iktpreobuka.e_diary.entities.SchoolYearEntity;

public interface ClassRepository extends CrudRepository<ClassEntity, Long> {

	
	 ArrayList<ClassEntity> findAllBySchoolYear(SchoolYearEntity sy);
}
