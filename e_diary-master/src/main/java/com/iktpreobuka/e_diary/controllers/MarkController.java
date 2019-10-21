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
import com.iktpreobuka.e_diary.entities.MarkEntity;
import com.iktpreobuka.e_diary.entities.SemesterEntity;
import com.iktpreobuka.e_diary.entities.StudentEntity;
import com.iktpreobuka.e_diary.entities.SubjectEntity;
import com.iktpreobuka.e_diary.entities.TeacherEntity;
import com.iktpreobuka.e_diary.entities.dto.MarkDTO;
import com.iktpreobuka.e_diary.security.Views;
import com.iktpreobuka.e_diary.services.MarkService;
import com.iktpreobuka.e_diary.services.SemesterService;
import com.iktpreobuka.e_diary.services.StudentService;
import com.iktpreobuka.e_diary.services.SubjectService;
import com.iktpreobuka.e_diary.services.TeacherService;
import com.iktpreobuka.e_diary.util.RESTError;

@RestController
@RequestMapping(path = "/api/v1/marks")
@CrossOrigin
public class MarkController {

	@Autowired
	private MarkService markService;

	@Autowired
	private SemesterService semesterService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private SubjectService subjectService;

	// GET ALL FOR PUBLIC
	@RequestMapping(method = RequestMethod.GET, value = "/public")
	@JsonView(Views.Public.class)
	public ResponseEntity<List<MarkDTO>> getAllMarksForPublic() {
		List<MarkDTO> marksDto = new ArrayList<>();
		List<MarkEntity> marks = markService.getAllMarks();

		for (MarkEntity m : marks) {
			marksDto.add(new MarkDTO(m));
		}
		return new ResponseEntity<>(marksDto, HttpStatus.OK);
	}

	// GET ALL FOR PRIVATE
	@Secured ({"TEACHER", "STUDENT", "PARENT"})
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	@JsonView(Views.Private.class)
	public ResponseEntity<List<MarkDTO>> getAllMarksForPrivate() {
		List<MarkDTO> marksDto = new ArrayList<>();
		List<MarkEntity> marks = markService.getAllMarks();

		for (MarkEntity m : marks) {
			marksDto.add(new MarkDTO(m));
		}
		return new ResponseEntity<>(marksDto, HttpStatus.OK);
	}

	// GET ALL FOR ADMIN
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	@JsonView(Views.Admin.class)
	public ResponseEntity<List<MarkDTO>> getAllMarksForAdmin() {
		List<MarkDTO> marksDto = new ArrayList<>();
		List<MarkEntity> marks = markService.getAllMarks();

		for (MarkEntity m : marks) {
			marksDto.add(new MarkDTO(m));
		}
		return new ResponseEntity<>(marksDto, HttpStatus.OK);
	}

	// GET BY ID
	@Secured ({"TEACHER", "ADMIN"})
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getMarkById(@PathVariable Long id) {

		try {
			MarkEntity m = markService.findMarkById(id);
			MarkDTO dto = new MarkDTO(m);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Mark not found!"), HttpStatus.NOT_FOUND);
		}
	}

	// POST
	@Secured ({"TEACHER", "ADMIN"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveMark(@Valid @RequestBody MarkDTO markDTO, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				
				TeacherEntity teacher = teacherService.findTeacherById(markDTO.getTeacherID());
				if (teacher == null) {
					return new ResponseEntity<>(("Teacher doesn't exist!"), HttpStatus.BAD_REQUEST);
				}
				
				SubjectEntity subject = subjectService.findSubjectById(markDTO.getSubjectID());
				if (subject == null) {
					return new ResponseEntity<>(("Subject doesn't exist!"), HttpStatus.BAD_REQUEST);
				}
				
				StudentEntity student = studentService.findStudentById(markDTO.getStudentID());
				if (student == null) {
					return new ResponseEntity<>(("Student doesn't exist!"), HttpStatus.BAD_REQUEST);
				}

				SemesterEntity semester = semesterService.findSemesterById(markDTO.getSemesterID());
				if (semester == null) {
					return new ResponseEntity<>(("Semester doesn't exist!"), HttpStatus.BAD_REQUEST);
				}

				MarkEntity mark = new MarkEntity(markDTO, student, teacher, subject, semester);
				MarkDTO newMark = new MarkDTO(markService.saveMark(mark));
				return new ResponseEntity<>(newMark, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(("Error occured!"), HttpStatus.BAD_REQUEST);
			}
		}
	}

	// PUT
	@Secured ({"TEACHER", "ADMIN"})
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> editMark(@Valid @RequestBody MarkDTO markDTO, BindingResult result,
			@PathVariable("id") Long id) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {

				TeacherEntity teacher = teacherService.findTeacherById(markDTO.getTeacherID());
				if (teacher == null) {
					return new ResponseEntity<>(("Teacher doesn't exist!"), HttpStatus.BAD_REQUEST);
				}

				SubjectEntity subject = subjectService.findSubjectById(markDTO.getSubjectID());
				if (subject == null) {
					return new ResponseEntity<>(("Subject doesn't exist!"), HttpStatus.BAD_REQUEST);
				}

				StudentEntity student = studentService.findStudentById(markDTO.getStudentID());
				if (student == null) {
					return new ResponseEntity<>(("Student doesn't exist!"), HttpStatus.BAD_REQUEST);
				}

				SemesterEntity semester = semesterService.findSemesterById(markDTO.getSemesterID());
				if (semester == null) {
					return new ResponseEntity<>(("Semester doesn't exist!"), HttpStatus.BAD_REQUEST);
				}

				MarkEntity mark = new MarkEntity(markDTO, student, teacher, subject, semester);
				MarkDTO newMark = new MarkDTO(markService.editMark(mark, id));
				return new ResponseEntity<>(newMark, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(("Error occured!"), HttpStatus.BAD_REQUEST);
			}
		}
	}

	// DELETE
	@Secured ("ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteMark(@PathVariable Long id) {
		try {
			if (markService.removeMark(id)) {
				return new ResponseEntity<RESTError>(new RESTError("Delete successfully!"), HttpStatus.OK);
			} else {
				return new ResponseEntity<RESTError>(new RESTError("Mark not found!"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Error occured!"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
