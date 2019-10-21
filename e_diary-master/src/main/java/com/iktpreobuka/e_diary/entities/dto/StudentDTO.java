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
import com.iktpreobuka.e_diary.entities.StudentEntity;
import com.iktpreobuka.e_diary.security.Views;

public class StudentDTO {

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
	@NotNull(message="You have to add a parent!")
	private Long parentID;
	
	@JsonView (Views.Private.class)
	private Long classID;
	
	@JsonView (Views.Private.class)
	private ArrayList<Long> marksIDs;

	public StudentDTO(Long id, String name, String lastName, String address, String phoneNumber, String jmbg, String email,
			Date birthDate, Long parentID, Long classID, ArrayList<Long> marksIDs) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.jmbg = jmbg;
		this.email = email;
		this.birthDate = birthDate;
		this.parentID = parentID;
		this.classID = classID;
		this.marksIDs = marksIDs;
	}

	public StudentDTO(StudentEntity student) {
		this.id = student.getId();
		this.name = student.getName();
		this.lastName = student.getLastName();
		this.address = student.getAddress();
		this.phoneNumber = student.getPhoneNumber();
		this.jmbg = student.getJmbg();
		this.email = student.getEmail();
		this.birthDate = student.getBirthDate();
		
		// od liste objekata ocena [ocena1, ocena2] pravi listu id-eva [1, 2, 3]
		this.marksIDs = (ArrayList<Long>) student.getMark().stream().map(MarkEntity::getId).collect(Collectors.toList());
		this.parentID = student.getParent().getId();
		this.classID = (student.getClas()== null) ? null : student.getClas().getId();

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

	public Long getParentID() {
		return parentID;
	}

	public Long getClassID() {
		return classID;
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

	public void setParentID(Long parentID) {
		this.parentID = parentID;
	}

	public void setClassID(Long classID) {
		this.classID = classID;
	}

	public void setMarksIDs(ArrayList<Long> marksIDs) {
		this.marksIDs = marksIDs;
	}

	
	
}
