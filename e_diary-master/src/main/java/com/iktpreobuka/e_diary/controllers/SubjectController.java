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
import com.iktpreobuka.e_diary.entities.SubjectEntity;
import com.iktpreobuka.e_diary.entities.TeacherEntity;
import com.iktpreobuka.e_diary.entities.dto.SubjectDTO;
import com.iktpreobuka.e_diary.security.Views;
import com.iktpreobuka.e_diary.services.ClassService;
import com.iktpreobuka.e_diary.services.SchoolYearService;
import com.iktpreobuka.e_diary.services.SubjectService;
import com.iktpreobuka.e_diary.services.TeacherService;
import com.iktpreobuka.e_diary.util.RESTError;

@RestController
@RequestMapping(path = "/api/v1/subjects")
@CrossOrigin
public class SubjectController {

	@Autowired
	private SchoolYearService yearService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ClassService classService;

	// GET ALL FOR PUBLIC
	@Secured ({"PARENT", "STUDENT"})
	@RequestMapping(method = RequestMethod.GET, value = "/public")
	@JsonView(Views.Public.class)
	public ResponseEntity<List<SubjectDTO>> getAllSubjectsForPublic() {
		List<SubjectDTO> subjectsDto = new ArrayList<>();
		List<SubjectEntity> subjects = subjectService.getAllSubjects();

		for (SubjectEntity s : subjects) {
			subjectsDto.add(new SubjectDTO(s));
		}
		return new ResponseEntity<>(subjectsDto, HttpStatus.OK);
	}

	// GET ALL FOR PRIVATE
	@Secured ({"TEACHER", "ADMIN" })
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	@JsonView(Views.Private.class)
	public ResponseEntity<List<SubjectDTO>> getAllSubjectsForPrivate() {
		List<SubjectDTO> subjectsDto = new ArrayList<>();
		List<SubjectEntity> subjects = subjectService.getAllSubjects();

		for (SubjectEntity s : subjects) {
			subjectsDto.add(new SubjectDTO(s));
		}
		return new ResponseEntity<>(subjectsDto, HttpStatus.OK);
	}
	
	// GET ALL FOR ADMIN
		@Secured ("ADMIN")
		@RequestMapping(method = RequestMethod.GET, value = "/admin")
		@JsonView(Views.Private.class)
		public ResponseEntity<List<SubjectDTO>> getAllSubjectsForAdmin() {
			List<SubjectDTO> subjectsDto = new ArrayList<>();
			List<SubjectEntity> subjects = subjectService.getAllSubjects();

			for (SubjectEntity s : subjects) {
				subjectsDto.add(new SubjectDTO(s));
			}
			return new ResponseEntity<>(subjectsDto, HttpStatus.OK);
		}

	// GET BY ID
	@Secured ({"TEACHER", "ADMIN"})
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getSubjectById(@PathVariable Long id) {

		try {
			SubjectEntity s = subjectService.findSubjectById(id);
			SubjectDTO dto = new SubjectDTO(s);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Subject not found!"), HttpStatus.NOT_FOUND);
		}
	}

	// POST
	@Secured ({"TEACHER", "ADMIN"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveSubjects(@Valid @RequestBody SubjectDTO subjectDTO, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				SchoolYearEntity sy = yearService.findYearById(subjectDTO.getSchoolYearID());
				if (sy == null) {
					return new ResponseEntity<>(("School year doesn't exist!"), HttpStatus.BAD_REQUEST);
				}

				SubjectEntity s = new SubjectEntity(subjectDTO, sy);
				SubjectDTO newSubject = new SubjectDTO(subjectService.saveSubject(s));
				return new ResponseEntity<>(newSubject, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(("Subject alredy exists!"), HttpStatus.BAD_REQUEST);
			}
		}
	}

	// PUT
	@Secured ({"TEACHER", "ADMIN"})
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> editSubject(@Valid @RequestBody SubjectDTO subjectDTO, BindingResult result,
			@PathVariable("id") Long id) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				SchoolYearEntity sy = yearService.findYearById(subjectDTO.getSchoolYearID());
				if (sy == null) {
					return new ResponseEntity<>(("School year doesn't exist!"), HttpStatus.BAD_REQUEST);
				}
				
				ArrayList<TeacherEntity> teachers = teacherService.getAllTeachersByID(subjectDTO.getTeachersIDs());
				if (teachers == null) {
					return new ResponseEntity<>(("Error has occured! Teachers not found!"), HttpStatus.BAD_REQUEST);
				}
				
				ArrayList<ClassEntity> classes = classService.getAllClassesByID(subjectDTO.getClassesIDs());
				if (classes == null) {
					return new ResponseEntity<>(("Error has occured! Classes not found!"), HttpStatus.BAD_REQUEST);
				}

				SubjectEntity s = new SubjectEntity(subjectDTO, sy, teachers, classes);
				SubjectDTO newSubject = new SubjectDTO(subjectService.editSubject(s, id));
				return new ResponseEntity<>(newSubject, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(("Subject alredy exists!"), HttpStatus.BAD_REQUEST);
			}
		}
	}

	// DELETE
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
		try {
			if (subjectService.removeSubject(id)) {
				return new ResponseEntity<RESTError>(new RESTError("Delete successfully!"), HttpStatus.OK);
			} else {
				return new ResponseEntity<RESTError>(new RESTError("Subject not found!"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Can't delete that subject"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
