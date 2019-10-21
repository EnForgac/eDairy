package com.iktpreobuka.e_diary.services;

import java.util.List;

import com.iktpreobuka.e_diary.entities.UserEntity;

public interface UserService {
	
	public List<UserEntity> getAllUsers();
	
	public UserEntity findUserById(Long id);
	
	public UserEntity saveUser(UserEntity user);
	
	public UserEntity editUser (UserEntity user, Long id);
	
	public boolean removeUser (Long id);

}
