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

import com.iktpreobuka.e_diary.entities.dto.SubjectDTO;

@Entity
@Table(name = "subject")
public class SubjectEntity {

	private Long id;
	private Integer version;
	private String code;

	private String name;
	private Integer fond;

	private List<ClassEntity> classes = new ArrayList<>();
	private SchoolYearEntity schoolYear;
	private List<MarkEntity> marks = new ArrayList<>();
	private List<TeacherEntity> teachers = new ArrayList<>();

	public SubjectEntity() {
		super();
	}

	public SubjectEntity(Integer version, String code, String name, Integer fond, List<ClassEntity> classes,
			SchoolYearEntity schoolYear, List<MarkEntity> marks, List<TeacherEntity> teachers) {
		super();
		this.version = version;
		this.code = code;
		this.name = name;
		this.fond = fond;
		this.classes = classes;
		this.schoolYear = schoolYear;
		this.marks = marks;
		this.teachers = teachers;
	}

	public SubjectEntity(SubjectDTO subject, SchoolYearEntity sy) {
		super();
		this.version = 1;
		this.code = UUID.randomUUID().toString();
		this.name = subject.getName();
		this.fond = subject.getFond();
		this.classes = new ArrayList<>();
		this.schoolYear = sy;
		this.marks = new ArrayList<>();
		this.teachers = new ArrayList<>();
	}

	public SubjectEntity(SubjectDTO subject, SchoolYearEntity sy, ArrayList<TeacherEntity> teachers, ArrayList<ClassEntity> clas) {
		super();
		
		this.name = subject.getName();
		this.fond = subject.getFond();
		this.classes = clas;
		this.schoolYear = sy;
		this.marks = new ArrayList<>();
		this.teachers = teachers;
		
	}
	
	public void updateSubject(SubjectEntity s) {
		setFond(s.getFond());
		setMarks(s.getMarks());
		setName(s.getName());
		setClasses(s.getClasses());
		setSchoolYear(s.getSchoolYear());
		setTeachers(s.getTeachers());
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "subject_id")
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

	@Column(name = "name", nullable = false, unique = true)
	public String getName() {
		return name;
	}

	@Column(name = "fond", nullable = false)
	public Integer getFond() {
		return fond;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolYear", nullable = false)
	public SchoolYearEntity getSchoolYear() {
		return schoolYear;
	}

	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	public List<MarkEntity> getMarks() {
		return marks;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "subject_class", joinColumns = {
			@JoinColumn(name = "subject_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "class_id", nullable = false, updatable = false) })
	public List<ClassEntity> getClasses() {
		return classes;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "subject_teacher", joinColumns = {
			@JoinColumn(name = "subject_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id", nullable = false, updatable = false) })
	public List<TeacherEntity> getTeachers() {
		return teachers;
	}

	public void setClasses(List<ClassEntity> classes) {
		this.classes = classes;
	}

	public void setTeachers(List<TeacherEntity> teachers) {
		this.teachers = teachers;
	}

	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}

	public void setSchoolYear(SchoolYearEntity schoolYear) {
		this.schoolYear = schoolYear;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFond(Integer fond) {
		this.fond = fond;
	}

	@Override
	public String toString() {
		return "SubjectEntity [id=" + id + ", name=" + name + "]";
	}


	


}
