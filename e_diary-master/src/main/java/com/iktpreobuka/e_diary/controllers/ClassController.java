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
import com.iktpreobuka.e_diary.entities.ClassEntity;
import com.iktpreobuka.e_diary.entities.SchoolYearEntity;
import com.iktpreobuka.e_diary.entities.StudentEntity;
import com.iktpreobuka.e_diary.entities.SubjectEntity;
import com.iktpreobuka.e_diary.entities.dto.ClassDTO;
import com.iktpreobuka.e_diary.security.Views;
import com.iktpreobuka.e_diary.services.ClassService;
import com.iktpreobuka.e_diary.services.SchoolYearService;
import com.iktpreobuka.e_diary.services.StudentService;
import com.iktpreobuka.e_diary.services.SubjectService;
import com.iktpreobuka.e_diary.util.RESTError;

@RestController
@RequestMapping(path = "/api/v1/classes")
@CrossOrigin
public class ClassController {

	@Autowired
	private ClassService classService;

	@Autowired
	private SchoolYearService yearService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private StudentService studentService;

	// GET ALL FOR PUBLIC
	@Secured ("PARENT")
	@RequestMapping(method = RequestMethod.GET, value = "/public")
	@JsonView(Views.Public.class)
	public ResponseEntity<List<ClassDTO>> getAllClassesForPublic() {
		List<ClassDTO> classDto = new ArrayList<>();
		List<ClassEntity> clas = classService.getAllClasses();

		for (ClassEntity c : clas) {
			classDto.add(new ClassDTO(c));
		}
		return new ResponseEntity<>(classDto, HttpStatus.OK);
	}

	// GET ALL FOR PRIVATE
	@Secured ({"TEACHER", "STUDENT"})
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	@JsonView(Views.Private.class)
	public ResponseEntity<List<ClassDTO>> getAllClassesForPrivate() {
		List<ClassDTO> classDto = new ArrayList<>();
		List<ClassEntity> clas = classService.getAllClasses();

		for (ClassEntity c : clas) {
			classDto.add(new ClassDTO(c));
		}
		return new ResponseEntity<>(classDto, HttpStatus.OK);
	}

	// GET ALL FOR ADMIN
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	@JsonView(Views.Admin.class)
	public ResponseEntity<List<ClassDTO>> getAllClassesForAdmin() {
		List<ClassDTO> classDto = new ArrayList<>();
		List<ClassEntity> clas = classService.getAllClasses();

		for (ClassEntity c : clas) {
			classDto.add(new ClassDTO(c));
		}
		return new ResponseEntity<>(classDto, HttpStatus.OK);
	}

	// GET BY ID
	@Secured ({"TEACHER", "ADMIN"})
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getClassById(@PathVariable Long id) {

		try {
			ClassEntity c = classService.findClassById(id);
			ClassDTO dto = new ClassDTO(c);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Class not found!"), HttpStatus.NOT_FOUND);
		}
	}

	// POST
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveClass(@Valid @RequestBody ClassDTO classDTO, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				SchoolYearEntity sy = yearService.findYearById(classDTO.getSchoolYearID());
				if (sy == null) {
					return new ResponseEntity<>(("School year doesn't exist!"), HttpStatus.BAD_REQUEST);
				}

				ClassEntity s = new ClassEntity(classDTO, sy);
				ClassDTO newClass = new ClassDTO(classService.saveClass(s));
				return new ResponseEntity<>(newClass, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(("Class alredy exists!"), HttpStatus.BAD_REQUEST);
			}
		}
	}

	// PUT
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> editClass(@Valid @RequestBody ClassDTO classDTO, BindingResult result,
			@PathVariable("id") Long id) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				SchoolYearEntity sy = yearService.findYearById(classDTO.getSchoolYearID());
				if (sy == null) {
					return new ResponseEntity<>(("School year doesn't exist!"), HttpStatus.BAD_REQUEST);
				}

				ArrayList<SubjectEntity> subjects = subjectService.getAllSubjectsByID(classDTO.getSubjectsIDs());
				if (subjects == null) {
					return new ResponseEntity<>(("Error has occured! Subjects not found!"), HttpStatus.BAD_REQUEST);
				}

				ArrayList<StudentEntity> students = studentService.getAllStudentsByID(classDTO.getStudentsIDs());
				if (students == null) {
					return new ResponseEntity<>(("Error has occured! Student not found!"), HttpStatus.BAD_REQUEST);
				}

				ClassEntity c = new ClassEntity(classDTO, sy, subjects, students);
				ClassEntity cls = classService.editClass(c, id, students);
				ClassDTO newClass = new ClassDTO(cls);

				return new ResponseEntity<>(newClass, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(("Class alredy exists!"), HttpStatus.BAD_REQUEST);
			}
		}
	}

	// DELETE
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteClass(@PathVariable Long id) {
		try {
			if (classService.removeClass(id)) {
				return new ResponseEntity<RESTError>(new RESTError("Delete successfully!"), HttpStatus.OK);
			} else {
				return new ResponseEntity<RESTError>(new RESTError("Class not found!"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Can't delete that class"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
