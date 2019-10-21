package com.iktpreobuka.e_diary.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.iktpreobuka.e_diary.entities.dto.StudentDTO;


@Entity
@Table (name = "student")
public class StudentEntity extends PersonEntity {

	private ParentEntity parent;
	private ClassEntity clas;
	private List<MarkEntity> marks = new ArrayList<>();
	
	
	public StudentEntity() {
		super();
	}

	public StudentEntity(Integer version, String code, String name, String lastName, String address, String phoneNumber, String jmbg, String email,
			Date birthDate, List<UserEntity> users, ParentEntity parent, ClassEntity clas, List<MarkEntity>mark) {
		super(version, code, name, lastName, address, phoneNumber, jmbg, email, birthDate, users);
		this.parent = parent;
		this.clas = clas;
		this.marks = mark;
	}
	
	
	public StudentEntity(StudentDTO studentDTO, ParentEntity p) {
		super(1, UUID.randomUUID().toString(), studentDTO.getName(), studentDTO.getLastName(), studentDTO.getAddress(), 
				studentDTO.getPhoneNumber(), studentDTO.getJmbg(), studentDTO.getEmail(), studentDTO.getBirthDate(), new ArrayList<>());
		this.parent = p;
		this.marks = new ArrayList<>();
		//this.clas = new ArrayList<>();
	}
	
	public void updateStudent (StudentEntity s) {
		setAddress(s.getAddress());
		setBirthDate(s.getBirthDate());
		setName(s.getName());
		setLastName(s.getLastName()); 
		setEmail(s.getEmail());
		setJmbg(s.getJmbg());
		setPhoneNumber(s.getPhoneNumber());
		setParent(s.getParent());
		setUsers(s.getUsers());
		
	}

	
	@ManyToOne (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name = "parent", nullable = false)
	public ParentEntity getParent() {
		return parent;
	}
	
	@ManyToOne (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name = "clas")
	public ClassEntity getClas() {
		return clas;
	}
	
	@OneToMany (mappedBy = "student", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	public List<MarkEntity> getMark() {
		return marks;
	}

	public void setClas(ClassEntity clas) {
		this.clas = clas;
	}

	public void setMark(List<MarkEntity> mark) {
		this.marks = mark;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "StudentEntity [parent=" + parent + ", clas=" + clas + "]";
	}


	
	
	
	
}
