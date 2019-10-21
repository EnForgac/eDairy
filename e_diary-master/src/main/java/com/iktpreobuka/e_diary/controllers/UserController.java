package com.iktpreobuka.e_diary.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.e_diary.entities.ParentEntity;
import com.iktpreobuka.e_diary.entities.PersonEntity;
import com.iktpreobuka.e_diary.entities.StudentEntity;
import com.iktpreobuka.e_diary.entities.TeacherEntity;
import com.iktpreobuka.e_diary.entities.UserEntity;
import com.iktpreobuka.e_diary.entities.dto.UserDTO;
import com.iktpreobuka.e_diary.security.Views;
import com.iktpreobuka.e_diary.services.ParentService;
import com.iktpreobuka.e_diary.services.StudentService;
import com.iktpreobuka.e_diary.services.TeacherService;
import com.iktpreobuka.e_diary.services.UserService;
import com.iktpreobuka.e_diary.util.RESTError;

@RestController
@RequestMapping(path = "/api/v1/users")
@Secured("ADMIN")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ParentService parentService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;

	// GET ALL ADMIN
	@RequestMapping(method = RequestMethod.GET)
	@JsonView (Views.Admin.class)
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> userDto = new ArrayList<>();
		List<UserEntity> users = userService.getAllUsers();

		for (UserEntity u : users) {
			userDto.add(new UserDTO(u));
		}
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
	

	// GET BY ID
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {

		try {
			UserEntity user = userService.findUserById(id);
			UserDTO dto = new UserDTO(user);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("User not found!"), HttpStatus.NOT_FOUND);
		}
	}

	// POST
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				ParentEntity p = parentService.findParentById(userDTO.getPersonID());
				StudentEntity s = studentService.findStudentById(userDTO.getPersonID());
				TeacherEntity t = teacherService.findTeacherById(userDTO.getPersonID());
				PersonEntity person = (PersonEntity) new StudentEntity();
				if (p == null && s == null && t == null) {
					return new ResponseEntity<>(("Person doesn't exist!"), HttpStatus.BAD_REQUEST);
				}else if(p != null) {
					 person = (PersonEntity) p;
				}else if(s != null) {
					 person = (PersonEntity) s;
				}else if(t != null) {
					 person = (PersonEntity) t;
				}
				
//				System.out.println("TU");
				UserEntity user = new UserEntity(userDTO, person);
				UserDTO newUserDto = new UserDTO(userService.saveUser(user));
				return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(("Error occured!"), HttpStatus.BAD_REQUEST);
			}
		}
	}
	

	// PUT
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> editUser(@Valid @RequestBody UserDTO userDTO, BindingResult result, @PathVariable("id") Long id) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				ParentEntity p = parentService.findParentById(userDTO.getPersonID());
				StudentEntity s = studentService.findStudentById(userDTO.getPersonID());
				TeacherEntity t = teacherService.findTeacherById(userDTO.getPersonID());
				PersonEntity person = (PersonEntity) new StudentEntity();
				if (p == null && s == null && t == null) {
					return new ResponseEntity<>(("Person doesn't exist!"), HttpStatus.BAD_REQUEST);
				}else if(p != null) {
					 person = (PersonEntity) p;
				}else if(s != null) {
					 person = (PersonEntity) s;
				}else if(t != null) {
					 person = (PersonEntity) t;
				}
				
				UserEntity user = new UserEntity(userDTO, person);
				UserDTO newUserDto = new UserDTO(userService.editUser(user, id));
				return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(("Error has occured!"), HttpStatus.BAD_REQUEST);
			}
		}
	}
	

	// DELETE
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		try {
			if (userService.removeUser(id)) {
				return new ResponseEntity<RESTError>(new RESTError("Delete successfully!"), HttpStatus.OK);
			} else {
				return new ResponseEntity<RESTError>(new RESTError("User not found!"), HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Error has occured!"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
