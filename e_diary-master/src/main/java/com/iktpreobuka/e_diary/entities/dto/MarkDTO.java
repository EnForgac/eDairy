package com.iktpreobuka.e_diary.entities.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.e_diary.entities.MarkEntity;
import com.iktpreobuka.e_diary.enumerations.EMarkType;
import com.iktpreobuka.e_diary.security.Views;

public class MarkDTO {

	@JsonView (Views.Public.class)
	private Long id;

	@JsonView (Views.Public.class)
	@NotNull(message = "Mark must be provided!")
	@Min(value = 1, message = "Mark must be min {min}!")
	@Max(value = 5, message = "Mark must be max {max}!")
	private Integer mark;

	@JsonView (Views.Public.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Belgrade")
	private Date date;
	
	@JsonView (Views.Private.class)
	@NotNull(message = "Mark type must be provided!")
	private EMarkType markType;

	@JsonView (Views.Private.class)
	@NotNull(message = "Student must be provided!")
	private Long studentID;
	
	@JsonView (Views.Private.class)
	@NotNull(message = "Teacher must be provided!")
	private Long teacherID;

	@JsonView (Views.Private.class)
	@NotNull(message = "Subject must be provided!")
	private Long subjectID;

	@JsonView (Views.Private.class)
	@NotNull(message = "Semester must be provided!")
	private Long semesterID;

	public MarkDTO(Long id, Integer mark, Date date, EMarkType markType, Long studentID, Long teacherID, Long subjectID,
			Long semesterID) {
		super();
		this.id = id;
		this.mark = mark;
		this.date = date;
		this.markType = markType;
		this.studentID = studentID;
		this.teacherID = teacherID;
		this.subjectID = subjectID;
		this.semesterID = semesterID;
	}

	public MarkDTO(MarkEntity mark) {
		this.id = mark.getId();
		this.mark = mark.getMark();
		this.date = mark.getDate();
		this.markType = mark.getMarkType();
		this.studentID = mark.getStudent().getId();
		this.teacherID = mark.getTeacher().getId();
		this.subjectID = mark.getSubject().getId();
		this.semesterID = mark.getSemester().getId();
	}

	public Long getId() {
		return id;
	}

	public Integer getMark() {
		return mark;
	}

	public Date getDate() {
		return date;
	}

	public EMarkType getMarkType() {
		return markType;
	}

	public Long getStudentID() {
		return studentID;
	}

	public Long getTeacherID() {
		return teacherID;
	}

	public Long getSubjectID() {
		return subjectID;
	}

	public Long getSemesterID() {
		return semesterID;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setMarkType(EMarkType markType) {
		this.markType = markType;
	}

	public void setStudentID(Long studentID) {
		this.studentID = studentID;
	}

	public void setTeacherID(Long teacherID) {
		this.teacherID = teacherID;
	}

	public void setSubjectID(Long subjectID) {
		this.subjectID = subjectID;
	}

	public void setSemesterID(Long semesterID) {
		this.semesterID = semesterID;
	}

}
