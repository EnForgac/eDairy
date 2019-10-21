package com.iktpreobuka.e_diary.entities;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.iktpreobuka.e_diary.entities.dto.SemesterDTO;

@Entity
@Table (name = "semester")
public class SemesterEntity {
	
	private Long id;
	private Integer version;
	private String code;
	
	private Integer orderNumber;
	private Date dateFrom;
	private Date dateTo;
	
	private SchoolYearEntity schoolYear;
	private List<MarkEntity> marks = new ArrayList<>();
	
	
	public SemesterEntity() {
		super();
	}
	
	public SemesterEntity(Integer version, String code, Integer orderNumber, Date dateFrom, Date dateTo, SchoolYearEntity schoolYear,
			List<MarkEntity> marks) {
		super();
		this.version = version;
		this.code = code;
		this.orderNumber = orderNumber;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.schoolYear = schoolYear;
		this.marks = marks;
	}
	
	public SemesterEntity (SemesterDTO s, SchoolYearEntity sy) {
		super();
		this.version = 1;
		this.code = UUID.randomUUID().toString();
		this.orderNumber = s.getOrderNumber();
		this.dateFrom = s.getDateFrom();
		this.dateTo = s.getDateTo();
		this.schoolYear = sy;
		this.marks = new ArrayList<>();
	}
	
	public void updateSemester (SemesterEntity s) {
		setOrderNumber(s.getOrderNumber());
		setDateFrom(s.getDateFrom());
		setDateTo(s.getDateTo());
		setSchoolYear(s.getSchoolYear());
		setMarks(s.getMarks());
	}

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name= "semester_id")
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
	
	@Column (name = "order_number", nullable = false)
	public Integer getOrderNumber() {
		return orderNumber;
	}
	
	@Column (name = "date_from", nullable = false)
	public Date getDateFrom() {
		return dateFrom;
	}
	
	@Column (name = "date_to", nullable = false)
	public Date getDateTo() {
		return dateTo;
	}
	
	@ManyToOne (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name = "school_year", nullable = false)
	public SchoolYearEntity getSchoolYear() {
		return schoolYear;
	}
	
	@OneToMany (mappedBy = "semester", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	public List<MarkEntity> getMarks() {
		return marks;
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
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public void setSchoolYear(SchoolYearEntity schoolYear) {
		this.schoolYear = schoolYear;
	}
	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}
	
	

}
