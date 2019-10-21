package com.iktpreobuka.e_diary.entities.dto;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.e_diary.entities.ClassEntity;
import com.iktpreobuka.e_diary.entities.SchoolYearEntity;
import com.iktpreobuka.e_diary.entities.SemesterEntity;
import com.iktpreobuka.e_diary.entities.SubjectEntity;
import com.iktpreobuka.e_diary.security.Views;

public class SchoolYearDTO {
	
	@JsonView (Views.Public.class)
	private Long id;

	@JsonView (Views.Public.class)
	@NotNull(message = "Year must be provided!")
	@Pattern(regexp = "^(20[0-9][0-9])/(20[0-9][0-9])$", message = "School year must be in format 20yy/20yy!")
	private String year;

	@JsonView (Views.Private.class)
	private ArrayList<Long> classIDs;
	
	@JsonView (Views.Private.class)
	private ArrayList<Long> semestersIDs;
	
	@JsonView (Views.Private.class)
	private ArrayList<Long> subjectsIDs;
	
	
	public SchoolYearDTO(Long id, String year, ArrayList<Long> classIDs, ArrayList<Long> semestersIDs,
			ArrayList<Long> subjectsIDs) {
		super();
		this.id = id;
		this.year = year;
		this.classIDs = classIDs;
		this.semestersIDs = semestersIDs;
		this.subjectsIDs = subjectsIDs;
	}
	
	public SchoolYearDTO (SchoolYearEntity schoolYear) {
		this.id = schoolYear.getId();
		this.year = schoolYear.getYear();
		this.classIDs = (ArrayList<Long>) schoolYear.getClasses().stream().map(ClassEntity::getId).collect(Collectors.toList());
		this.semestersIDs = (ArrayList<Long>) schoolYear.getSemesters().stream().map(SemesterEntity::getId).collect(Collectors.toList());
		this.subjectsIDs = (ArrayList<Long>) schoolYear.getSubjects().stream().map(SubjectEntity::getId).collect(Collectors.toList());
	}
	
	
	
	public Long getId() {
		return id;
	}
	public String getYear() {
		return year;
	}
	public ArrayList<Long> getClassIDs() {
		return classIDs;
	}
	public ArrayList<Long> getSemestersIDs() {
		return semestersIDs;
	}
	public ArrayList<Long> getSubjectsIDs() {
		return subjectsIDs;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public void setClassIDs(ArrayList<Long> classIDs) {
		this.classIDs = classIDs;
	}
	public void setSemestersIDs(ArrayList<Long> semestersIDs) {
		this.semestersIDs = semestersIDs;
	}
	public void setSubjectsIDs(ArrayList<Long> subjectsIDs) {
		this.subjectsIDs = subjectsIDs;
	}
	
	
}
