package com.iktpreobuka.e_diary.entities.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.e_diary.entities.MarkEntity;
import com.iktpreobuka.e_diary.entities.SubjectEntity;
import com.iktpreobuka.e_diary.entities.TeacherEntity;
import com.iktpreobuka.e_diary.security.Views;

public class TeacherDTO {

	@JsonView (Views.Public.class)
	private Long id;
	
	@JsonView (Views.Public.class)
	@NotNull(message="Name must be provided!")
	@Size (min = 2, max = 20, message = "Name must be between {min} and {max} characters long!")
	private String name;
	
	@JsonView (Views.Public.class)
	@NotNull(message="Last name must be provided!")
	@Size (min = 2, max =30, message = "Last name must be between {min} and {max} characters long!")
	private String lastName;
	
	@JsonView (Views.Admin.class)
	@NotNull(message="Address must be provided!")
	@Size(max=50, message= "Category description can't be more than {max} characters long!")
	private String address;
	
	@JsonView (Views.Admin.class)
	@NotNull(message="Phone number must be provided!")
	@Size (max = 20, message = "Phone number must be between {min} and {max} characters long!")
	private String phoneNumber;
	
	@JsonView (Views.Admin.class)
	@NotNull(message="JMBG must be provided!")
	@Pattern(regexp = "^[0-9]{13}$", message = "JMBG must have 13 characters!")
	private String jmbg;
	
	@JsonView (Views.Admin.class)
	@NotNull(message = "Email must be provided!")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Email is not valid!")
	private String email;
	
	@JsonView (Views.Admin.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Europe/Belgrade")
	private Date birthDate;
	@JsonView (Views.Private.class)
	private ArrayList<Long> subjectsIDs;
	
	@JsonView (Views.Private.class)
	private ArrayList<Long> marksIDs;
	
	public TeacherDTO(Long id, String name, String lastName, String address, String phoneNumber, String jmbg, String email,
			Date birthDate, ArrayList<Long> marksIDs, ArrayList<Long> subjectsIDs) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.jmbg = jmbg;
		this.email = email;
		this.birthDate = birthDate;
		this.marksIDs = marksIDs;
		this.subjectsIDs = subjectsIDs;
	}
	
	public TeacherDTO(TeacherEntity teacher) {
		this.id = teacher.getId();
		this.name = teacher.getName();
		this.lastName = teacher.getLastName();
		this.address = teacher.getAddress();
		this.phoneNumber = teacher.getPhoneNumber();
		this.jmbg = teacher.getJmbg();
		this.email = teacher.getEmail();
		this.birthDate = teacher.getBirthDate();
		this.marksIDs = (ArrayList<Long>) teacher.getMarks().stream().map(MarkEntity::getId).collect(Collectors.toList());
		this.subjectsIDs = (ArrayList<Long>) teacher.getSubjects().stream().map(SubjectEntity::getId).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getJmbg() {
		return jmbg;
	}

	public String getEmail() {
		return email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public ArrayList<Long> getSubjectsIDs() {
		return subjectsIDs;
	}

	public ArrayList<Long> getMarksIDs() {
		return marksIDs;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setSubjectsIDs(ArrayList<Long> subjectsIDs) {
		this.subjectsIDs = subjectsIDs;
	}

	public void setMarksIDs(ArrayList<Long> marksIDs) {
		this.marksIDs = marksIDs;
	}
	
	
	
	
	
	
}
