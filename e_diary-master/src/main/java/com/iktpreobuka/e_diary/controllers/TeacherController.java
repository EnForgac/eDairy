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
import com.iktpreobuka.e_diary.entities.SubjectEntity;
import com.iktpreobuka.e_diary.entities.TeacherEntity;
import com.iktpreobuka.e_diary.entities.dto.TeacherDTO;
import com.iktpreobuka.e_diary.security.Views;
import com.iktpreobuka.e_diary.services.SubjectService;
import com.iktpreobuka.e_diary.services.TeacherService;
import com.iktpreobuka.e_diary.util.RESTError;

@RestController
@RequestMapping(path = "/api/v1/teachers")
@CrossOrigin
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private SubjectService subjectService;

	// GET ALL FOR PUBLIC
	@Secured ({"PARENT", "STUDENT"})
	@RequestMapping(method = RequestMethod.GET, value = "/public")
	@JsonView(Views.Public.class)
	public ResponseEntity<List<TeacherDTO>> getAllTeachersForPublic() {
		List<TeacherDTO> teacherDto = new ArrayList<>();
		List<TeacherEntity> teachers = teacherService.getAllTeachers();

		for (TeacherEntity t : teachers) {
			teacherDto.add(new TeacherDTO(t));
		}
		return new ResponseEntity<>(teacherDto, HttpStatus.OK);
	}

	// GET ALL FOR PRIVATE
	@Secured ("TEACHER")
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	@JsonView(Views.Private.class)
	public ResponseEntity<List<TeacherDTO>> getAllTeachersForPrivate() {
		List<TeacherDTO> teacherDto = new ArrayList<>();
		List<TeacherEntity> teachers = teacherService.getAllTeachers();

		for (TeacherEntity t : teachers) {
			teacherDto.add(new TeacherDTO(t));
		}
		return new ResponseEntity<>(teacherDto, HttpStatus.OK);
	}

	// GET ALL FOR ADMIN
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	@JsonView(Views.Admin.class)
	public ResponseEntity<List<TeacherDTO>> getAllTeachersForAdmin() {
		List<TeacherDTO> teacherDto = new ArrayList<>();
		List<TeacherEntity> teachers = teacherService.getAllTeachers();

		for (TeacherEntity t : teachers) {
			teacherDto.add(new TeacherDTO(t));
		}
		return new ResponseEntity<>(teacherDto, HttpStatus.OK);
	}

	// GET BY ID
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getTeacherById(@PathVariable Long id) {

		try {
			TeacherEntity teacher = teacherService.findTeacherById(id);
			TeacherDTO dto = new TeacherDTO(teacher);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Teacher not found!"), HttpStatus.NOT_FOUND);
		}
	}

	// POST
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveTeacher(@Valid @RequestBody TeacherDTO teacherDTO, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				TeacherEntity teacher = new TeacherEntity(teacherDTO);
				TeacherDTO newTeacherDto = new TeacherDTO(teacherService.saveTeacher(teacher));
				return new ResponseEntity<>(newTeacherDto, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(("Teacher alredy exist!"), HttpStatus.BAD_REQUEST);
			}
		}
	}

	// PUT
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateItem(@Valid @RequestBody TeacherDTO teacherDTO, BindingResult result,
			@PathVariable("id") Long id) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				ArrayList<SubjectEntity> subjects = subjectService.getAllSubjectsByID(teacherDTO.getSubjectsIDs());
				if (subjects == null) {
					return new ResponseEntity<>(("Error has occured! Subjects not found!"), HttpStatus.BAD_REQUEST);
				}

				TeacherEntity teacher = new TeacherEntity(teacherDTO, subjects);
				TeacherDTO updateTeacher = new TeacherDTO(teacherService.updateTeacher(teacher, id));
				return new ResponseEntity<>(updateTeacher, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(("Error has occured!"), HttpStatus.BAD_REQUEST);
			}
		}
	}

	// DELETE
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
		try {
			if (teacherService.removeTeacher(id)) {
				return new ResponseEntity<RESTError>(new RESTError("Delete successfully!"), HttpStatus.OK);
			} else {
				return new ResponseEntity<RESTError>(new RESTError("Teacher not found!"), HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Can't delete that teacher"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
