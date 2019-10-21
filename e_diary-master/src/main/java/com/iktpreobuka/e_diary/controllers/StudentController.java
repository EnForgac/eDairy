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
import com.iktpreobuka.e_diary.entities.StudentEntity;
import com.iktpreobuka.e_diary.entities.dto.StudentDTO;
import com.iktpreobuka.e_diary.security.Views;
import com.iktpreobuka.e_diary.services.ParentService;
import com.iktpreobuka.e_diary.services.StudentService;
import com.iktpreobuka.e_diary.util.RESTError;

@RestController
@RequestMapping(path = "/api/v1/students")
@CrossOrigin
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private ParentService parentService;

	// GET ALL FOR PUBLIC
	@RequestMapping(method = RequestMethod.GET, value = "/public")
	@JsonView(Views.Public.class)
	public ResponseEntity<List<StudentDTO>> getAllStudentsForPublic() {
		List<StudentDTO> studentsDto = new ArrayList<>();
		List<StudentEntity> students = studentService.getAllStudents();

		for (StudentEntity s : students) {
			studentsDto.add(new StudentDTO(s));
		}
		return new ResponseEntity<>(studentsDto, HttpStatus.OK);
	}

	// GET ALL FOR PRIVATE
	@Secured ({"TEACHER", "STUDENT", "PARENT"})
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	@JsonView(Views.Private.class)
	public ResponseEntity<List<StudentDTO>> getAllStudentsForPrivate() {
		List<StudentDTO> studentsDto = new ArrayList<>();
		List<StudentEntity> students = studentService.getAllStudents();

		for (StudentEntity s : students) {
			studentsDto.add(new StudentDTO(s));
		}
		return new ResponseEntity<>(studentsDto, HttpStatus.OK);
	}

	// GET ALL FOR ADMIN
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	@JsonView(Views.Admin.class)
	public ResponseEntity<List<StudentDTO>> getAllStudentsForAdmin() {
		List<StudentDTO> studentsDto = new ArrayList<>();
		List<StudentEntity> students = studentService.getAllStudents();

		for (StudentEntity s : students) {
			studentsDto.add(new StudentDTO(s));
		}
		return new ResponseEntity<>(studentsDto, HttpStatus.OK);
	}

	// GET BY ID
	@Secured ({"ADMIN", "TEACHER"})
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Long id) {
		try {
			StudentEntity s = studentService.findStudentById(id);
			StudentDTO dto = new StudentDTO(s);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Student not found!"), HttpStatus.NOT_FOUND);
		}
	}

	// POST
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveStudent(@Valid @RequestBody StudentDTO studentDTO, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				ParentEntity p = parentService.findParentById(studentDTO.getParentID());
				if (p == null) {
					return new ResponseEntity<>(("Parent doesn't exist!"), HttpStatus.BAD_REQUEST);
				}
				StudentEntity s = new StudentEntity(studentDTO, p);
				StudentDTO newStudent = new StudentDTO(studentService.saveStudent(s));
				return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(("Student alredy exists!"), HttpStatus.BAD_REQUEST);
			}
		}
	}

	// PUT
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateItem(@Valid @RequestBody StudentDTO studentDTO, BindingResult result,
			@PathVariable("id") Long id) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				ParentEntity p = parentService.findParentById(studentDTO.getParentID());
				if (p == null) {
					return new ResponseEntity<>(("Parent doesn't exist!"), HttpStatus.BAD_REQUEST);
				}
				StudentEntity student = new StudentEntity(studentDTO, p);
				StudentEntity updated = studentService.updateStudent(student, id);
				StudentDTO updateStudent = new StudentDTO(updated);
				return new ResponseEntity<>(updateStudent, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(("Error has occured!"), HttpStatus.BAD_REQUEST);
			}
		}
	}

	// DELETE
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
		try {
			if (studentService.removeStudent(id)) {
				return new ResponseEntity<RESTError>(new RESTError("Delete successfully!"), HttpStatus.OK);
			} else {
				return new ResponseEntity<RESTError>(new RESTError("Student not found!"), HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Can't delete that student"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
