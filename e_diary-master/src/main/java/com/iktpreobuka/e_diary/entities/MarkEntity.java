package com.iktpreobuka.e_diary.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.iktpreobuka.e_diary.entities.dto.MarkDTO;
import com.iktpreobuka.e_diary.enumerations.EMarkType;

@Entity
@Table (name = "mark")
public class MarkEntity {

	private Long id;
	private Integer version;
	private String code;
	
	private Integer mark;
	private Date date;
	private EMarkType markType;
	
	private StudentEntity student;
	private TeacherEntity teacher;
	private SubjectEntity subject;
	private SemesterEntity semester;
	

	public MarkEntity() {
		super();
	}
	
	public MarkEntity(Integer version, String code, Integer mark, Date date, EMarkType markType, StudentEntity student, TeacherEntity teacher,
			SubjectEntity subject, SemesterEntity semester) {
		super();
		this.version = version;
		this.code = code;
		this.mark = mark;
		this.date = date;
		this.markType = markType;
		this.student = student;
		this.teacher = teacher;
		this.subject = subject;
		this.semester = semester;
	}
	
	public MarkEntity(MarkDTO mark, StudentEntity student, TeacherEntity teacher, SubjectEntity subject, SemesterEntity semester) {
		super();
		this.version = 1;
		this.code = UUID.randomUUID().toString();
		this.mark = mark.getMark() ;
		this.date = new Date();
		this.markType =  mark.getMarkType();
		this.student = student;
		this.teacher = teacher;
		this.subject = subject;
		this.semester = semester;
		
	}
	
	public void updateMark(MarkEntity m) {
		setDate(m.getDate());
		setMark(m.getMark());
		setMarkType(m.getMarkType());
		setSemester(m.getSemester());
		setStudent(m.getStudent());
		setSubject(m.getSubject());
		setTeacher(m.getTeacher());
	}

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name = "mark_id")
	public Long getId() {
		return id;
	}
	
	@Version
	@Column (name = "version")
	public Integer getVersion() {
		return version;
	}
	
	@Column (name = "code")
	public String getCode() {
		return code;
	}
	
	@Column (name = "mark", nullable = false)
	public Integer getMark() {
		return mark;
	}
	
	@Column (name = "date")
	public Date getDate() {
		return date;
	}
	
	@Column (name = "mark_type", nullable = false)
	public EMarkType getMarkType() {
		return markType;
	}
	
	@ManyToOne (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name = "student", nullable = false)
	public StudentEntity getStudent() {
		return student;
	}
	
	@ManyToOne (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name = "teacher", nullable = false)
	public TeacherEntity getTeacher() {
		return teacher;
	}
	
	@ManyToOne (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name = "subject", nullable = false)
	public SubjectEntity getSubject() {
		return subject;
	}
	
	@ManyToOne (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name = "semester", nullable = false)
	public SemesterEntity getSemester() {
		return semester;
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
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setMarkType(EMarkType markType) {
		this.markType = markType;
	}
	public void setStudent(StudentEntity student) {
		this.student = student;
	}
	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}
	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}
	public void setSemester(SemesterEntity semester) {
		this.semester = semester;
	}
	
	
	

	
	
}
