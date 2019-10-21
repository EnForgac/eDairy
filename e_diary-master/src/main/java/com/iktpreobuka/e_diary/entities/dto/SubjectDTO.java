package com.iktpreobuka.e_diary.entities.dto;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.e_diary.entities.ClassEntity;
import com.iktpreobuka.e_diary.entities.MarkEntity;
import com.iktpreobuka.e_diary.entities.SubjectEntity;
import com.iktpreobuka.e_diary.entities.TeacherEntity;
import com.iktpreobuka.e_diary.security.Views;

public class SubjectDTO {
	
	@JsonView (Views.Public.class)
	private Long id;

	@JsonView (Views.Public.class)
	@NotNull(message = "Name must be provided!")
	@Size(min = 2, max = 20, message = "Name must be between {min} and {max} characters long!")
	private String name;

	@JsonView (Views.Private.class)
	@NotNull(message = "Fond must be provided!")
	@Min(value = 20, message = "Fond must have min {min} hours!")
	@Max(value = 31, message = "Fond must have max {max} hours!")
	private Integer fond;

	@JsonView (Views.Private.class)
	private ArrayList<Long> classesIDs;
	
	@JsonView (Views.Private.class)
	@NotNull(message = "School year must be provided!")
	private Long schoolYearID;
	
	@JsonView (Views.Private.class)
	private ArrayList<Long> marksIDs;

	@JsonView (Views.Private.class)
	private ArrayList<Long> teachersIDs;

	public SubjectDTO(Long id, String name, Integer fond, ArrayList<Long> classesIDs, Long schoolYearID,
			ArrayList<Long> marksIDs, ArrayList<Long> teachersIDs) {
		super();
		this.id = id;
		this.name = name;
		this.fond = fond;
		this.classesIDs = classesIDs;
		this.schoolYearID = schoolYearID;
		this.marksIDs = marksIDs;
		this.teachersIDs = teachersIDs;
	}

	public SubjectDTO(SubjectEntity subject) {
		this.id = subject.getId();
		this.name = subject.getName();
		this.fond = subject.getFond();
		this.classesIDs = (ArrayList<Long>) subject.getClasses().stream().map(ClassEntity::getId)
				.collect(Collectors.toList());
		this.schoolYearID = subject.getSchoolYear().getId();
		this.marksIDs = (ArrayList<Long>) subject.getMarks().stream().map(MarkEntity::getId)
				.collect(Collectors.toList());
		this.teachersIDs = (ArrayList<Long>) subject.getTeachers().stream().map(TeacherEntity::getId)
				.collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getFond() {
		return fond;
	}

	public ArrayList<Long> getClassesIDs() {
		return classesIDs;
	}

	public Long getSchoolYearID() {
		return schoolYearID;
	}

	public ArrayList<Long> getMarksIDs() {
		return marksIDs;
	}

	public ArrayList<Long> getTeachersIDs() {
		return teachersIDs;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFond(Integer fond) {
		this.fond = fond;
	}

	public void setClassesIDs(ArrayList<Long> classesIDs) {
		this.classesIDs = classesIDs;
	}

	public void setSchoolYearID(Long schoolYearID) {
		this.schoolYearID = schoolYearID;
	}

	public void setMarksIDs(ArrayList<Long> marksIDs) {
		this.marksIDs = marksIDs;
	}

	public void setTeachersIDs(ArrayList<Long> teachersIDs) {
		this.teachersIDs = teachersIDs;
	}

}
