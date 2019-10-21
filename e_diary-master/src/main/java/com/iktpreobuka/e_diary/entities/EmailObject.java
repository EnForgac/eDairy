package com.iktpreobuka.e_diary.entities;

import com.iktpreobuka.e_diary.enumerations.EMarkType;

public class EmailObject {

	private String emailParent;
	private String studentName;
	private String studentLastName;
	private Integer mark;
	private String markType;
	private String subjectName;
	private String teacherName;
	private String teacherLastName;

	public EmailObject() {
		super();
	}

	public EmailObject(String email, String studentName, String studentLastName, Integer mark, EMarkType markType,
			String subjName, String teacherName, String teacherLastName) {
		setEmailParent(email);
		setStudentName(studentName);
		setStudentLastName(studentLastName);
		setMark(mark);
		setMarkType(markType.toString());
		setSubjectName(subjName);
		setTeacherName(teacherName);
		setTeacherLastName(teacherLastName);

	}

	public String getEmailParent() {
		return emailParent;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public Integer getMark() {
		return mark;
	}

	public String getMarkType() {
		return markType;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public String getTeacherLastName() {
		return teacherLastName;
	}

	public void setEmailParent(String emailParent) {
		this.emailParent = emailParent;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public void setTeacherLastName(String teacherLastName) {
		this.teacherLastName = teacherLastName;
	}

}
