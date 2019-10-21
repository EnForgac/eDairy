package com.iktpreobuka.e_diary.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.iktpreobuka.e_diary.entities.dto.TeacherDTO;

@Entity
@Table(name = "teacher")
public class TeacherEntity extends PersonEntity {

	private List<SubjectEntity> subjects = new ArrayList<>();
	private List<MarkEntity> marks = new ArrayList<>();

	public TeacherEntity() {
		super();
	}

	public TeacherEntity(Integer version, String code, String name, String lastName, String address, String phoneNumber,
			String jmbg, String email, Date birthDate, List<UserEntity> users, List<SubjectEntity> subject,
			List<MarkEntity> marks) {
		super(version, code, name, lastName, address, phoneNumber, jmbg, email, birthDate, users);
		this.subjects = subject;
		this.marks = marks;
	}

	public TeacherEntity(TeacherDTO teacher) {
		super(1, UUID.randomUUID().toString(), teacher.getName(), teacher.getLastName(), teacher.getAddress(),
				teacher.getPhoneNumber(), teacher.getJmbg(), teacher.getEmail(), teacher.getBirthDate(),
				new ArrayList<>());
		this.subjects = new ArrayList<>();
		this.marks = new ArrayList<>();
	}
	
	public TeacherEntity(TeacherDTO teacher, ArrayList<SubjectEntity> subjects) {
		super();
		this.name = teacher.getName();
		this.lastName = teacher.getLastName();
		this.address = teacher.getAddress();
		this.phoneNumber = teacher.getPhoneNumber();
		this.jmbg = teacher.getJmbg();
		this.email = teacher.getEmail();
		this.birthDate = teacher.getBirthDate();
		this.subjects = subjects;
		this.marks = new ArrayList<>();
	}
	
	public void updateTeacher(TeacherEntity t) {
		setAddress(t.getAddress());
		setBirthDate(t.getBirthDate());
		setName(t.getName());
		setLastName(t.getLastName()); 
		setEmail(t.getEmail());
		setJmbg(t.getJmbg());
		setPhoneNumber(t.getPhoneNumber());
		setMarks(t.getMarks());
		setSubjects(t.getSubjects());
	}
	
	

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "subject_teacher", joinColumns = {
			@JoinColumn(name = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "subject_id", nullable = false, updatable = false) })
	public List<SubjectEntity> getSubjects() {
		return subjects;
	}

	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	public List<MarkEntity> getMarks() {
		return marks;
	}

	public void setSubjects(List<SubjectEntity> subjects) {
		this.subjects = subjects;
	}

	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}

}
