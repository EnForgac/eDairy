package com.iktpreobuka.e_diary.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.iktpreobuka.e_diary.entities.dto.ClassDTO;

@Entity
@Table(name = "class")
public class ClassEntity {

	private Long id;
	private Integer version;
	private String code;

	private String clas;
	private Integer department;

	private List<StudentEntity> students;
	private SchoolYearEntity schoolYear;
	private List<SubjectEntity> subject ;

	public ClassEntity() {
		super();
	}

	public ClassEntity(Integer version, String code, String clas, Integer department, List<StudentEntity> students,
			SchoolYearEntity schoolYear, List<SubjectEntity> subject) {
		super();
		this.version = version;
		this.code = code;
		this.clas = clas;
		this.department = department;
		this.students = students;
		this.schoolYear = schoolYear;
		this.subject = subject;
	}

	public ClassEntity(ClassDTO clas, SchoolYearEntity sy) {
		super();
		this.version = 1;
		this.code = UUID.randomUUID().toString();
		this.clas = clas.getClas();
		this.department = clas.getDepartment();
		this.students = new ArrayList<>();
		this.schoolYear = sy;
		this.subject = new ArrayList<>();
	}

	public ClassEntity(ClassDTO clas, SchoolYearEntity sy, ArrayList<SubjectEntity> subject,
			ArrayList<StudentEntity> students) {
		super();
		this.clas = clas.getClas();
		this.department = clas.getDepartment();
		this.students = students;
		this.schoolYear = sy;
		this.subject = subject;
	}

	public void updateClass(ClassEntity c) {
		setClas(c.getClas());
		setDepartment(c.getDepartment());
		setSchoolYear(c.getSchoolYear());
		setStudents(c.getStudents());
		setSubject(c.getSubject());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "class_id")
	public Long getId() {
		return id;
	}

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	@Column(name = "class", nullable = false)
	public String getClas() {
		return clas;
	}

	@Column(name = "department", nullable = false)
	public Integer getDepartment() {
		return department;
	}

	@OneToMany(mappedBy = "clas", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	public List<StudentEntity> getStudents() {
		return students;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolYear", nullable = false)
	public SchoolYearEntity getSchoolYear() {
		return schoolYear;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "subject_class", joinColumns = {
			@JoinColumn(name = "class_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "subject_id", nullable = false, updatable = false) })
	public List<SubjectEntity> getSubject() {
		return subject;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSubject(List<SubjectEntity> subject) {
		this.subject = subject;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setClas(String clas) {
		this.clas = clas;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}

	public void setSchoolYear(SchoolYearEntity schoolYear) {
		this.schoolYear = schoolYear;
	}

	@Override
	public String toString() {
		return "ClassEntity [clas=" + clas + ", department=" + department + ", subject=" + subject + "]";
	}

	
	
}
