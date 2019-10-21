package com.iktpreobuka.e_diary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.e_diary.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Long> {

}
