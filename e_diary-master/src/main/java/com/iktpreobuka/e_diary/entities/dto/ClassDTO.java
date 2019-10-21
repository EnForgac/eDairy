package com.iktpreobuka.e_diary.entities.dto;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.e_diary.entities.ClassEntity;
import com.iktpreobuka.e_diary.entities.StudentEntity;
import com.iktpreobuka.e_diary.entities.SubjectEntity;
import com.iktpreobuka.e_diary.security.Views;

public class ClassDTO {
	
	@JsonView (Views.Public.class)
	private Long id;

	@JsonView (Views.Public.class)
	@NotNull(message = "Class must be provided!")
	@Size(max = 3, message = "Class must have a maximum of {max} characters!")
	private String clas;

	@JsonView (Views.Public.class)
	@NotNull(message = "Department must be provided!")
	@Min(value = 1, message = "Department must be min {min}!")
	@Max(value = 4, message = "Department must be max {max}!")
	private Integer department;

	@JsonView (Views.Private.class)
	private ArrayList<Long> studentsIDs;
	
	@JsonView (Views.Private.class)
	@NotNull(message = "School year must be provided!")
	private Long schoolYearID;
	
	@JsonView (Views.Private.class)
	private ArrayList<Long> subjectsIDs;

	public ClassDTO(Long id, String clas, Integer department, ArrayList<Long> studentsIDs, Long schoolYearID,
			ArrayList<Long> subjectsIDs) {
		super();
		this.id = id;
		this.clas = clas;
		this.department = department;
		this.studentsIDs = studentsIDs;
		this.schoolYearID = schoolYearID;
		this.subjectsIDs = subjectsIDs;
	}

	public ClassDTO(ClassEntity clas) {
		this.id = clas.getId();
		this.clas = clas.getClas();
		this.department = clas.getDepartment();
		this.studentsIDs = (ArrayList<Long>) clas.getStudents().stream().map(StudentEntity::getId)
				.collect(Collectors.toList());
		this.schoolYearID = clas.getSchoolYear().getId();
		this.subjectsIDs = (ArrayList<Long>) clas.getSubject().stream().map(SubjectEntity::getId)
				.collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public String getClas() {
		return clas;
	}

	public Integer getDepartment() {
		return department;
	}

	public ArrayList<Long> getStudentsIDs() {
		return studentsIDs;
	}

	public Long getSchoolYearID() {
		return schoolYearID;
	}

	public ArrayList<Long> getSubjectsIDs() {
		return subjectsIDs;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setClas(String clas) {
		this.clas = clas;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	public void setStudentsIDs(ArrayList<Long> studentsIDs) {
		this.studentsIDs = studentsIDs;
	}

	public void setSchoolYearID(Long schoolYearID) {
		this.schoolYearID = schoolYearID;
	}

	public void setSubjectsIDs(ArrayList<Long> subjectsIDs) {
		this.subjectsIDs = subjectsIDs;
	}

	@Override
	public String toString() {
		return "ClassDTO [id=" + id + ", clas=" + clas + ", department=" + department + ", studentsIDs=" + studentsIDs
				+ ", schoolYearID=" + schoolYearID + ", subjectsIDs=" + subjectsIDs + "]";
	}
	
	

}
