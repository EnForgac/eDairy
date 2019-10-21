package com.iktpreobuka.e_diary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.e_diary.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
