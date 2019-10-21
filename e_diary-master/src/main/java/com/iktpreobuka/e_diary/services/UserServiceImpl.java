package com.iktpreobuka.e_diary.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_diary.entities.ParentEntity;
import com.iktpreobuka.e_diary.entities.StudentEntity;
import com.iktpreobuka.e_diary.entities.TeacherEntity;
import com.iktpreobuka.e_diary.entities.UserEntity;
import com.iktpreobuka.e_diary.enumerations.ERole;
import com.iktpreobuka.e_diary.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	// GET ALL
	@Override
	public List<UserEntity> getAllUsers() {
		List<UserEntity> users = (List<UserEntity>) userRepo.findAll();
		return users;
	}
	
	// GET BY ID
	@Override
	public UserEntity findUserById(Long id) {
		Optional<UserEntity> user= userRepo.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}

	// POST
	@Override
	public UserEntity saveUser(UserEntity user) {
		
		if ((user.getRole() == ERole.STUDENT) && user.getPerson() instanceof StudentEntity) {
			return userRepo.save(user);
		} else if ((user.getRole() == ERole.TEACHER || user.getRole() == ERole.PARENT)
				&& (user.getPerson() instanceof TeacherEntity || user.getPerson() instanceof ParentEntity)) {
			return userRepo.save(user);
		} else
			return null;
	}

	// PUT
	@Override
	public UserEntity editUser(UserEntity user, Long id) {

		Optional<UserEntity> op = userRepo.findById(id);
		
		if (op.isPresent()) {
			UserEntity u = op.get();
			
			if ((user.getRole() == ERole.STUDENT) && user.getPerson() instanceof StudentEntity) {
				u.updateUser(user);

			} else if ((user.getRole() == ERole.TEACHER || user.getRole() == ERole.PARENT)
					&& (user.getPerson() instanceof TeacherEntity || user.getPerson() instanceof ParentEntity)) {
				u.updateUser(user);
			}
			return userRepo.save(u);
		}
		return null;
	}

	// DELETE
	@Override
	public boolean removeUser(Long id) {
		Optional<UserEntity> u = userRepo.findById(id);
		if (u.isPresent()) {
			userRepo.deleteById(id);
			return true;
		}
		return false;
	}


}
