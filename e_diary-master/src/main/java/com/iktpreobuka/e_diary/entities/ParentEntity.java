package com.iktpreobuka.e_diary.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.iktpreobuka.e_diary.entities.dto.ParentDTO;

@Entity
@Table (name = "parent")
public class ParentEntity extends PersonEntity {
	
	
	private List<StudentEntity> students = new ArrayList<>();

	
	public ParentEntity() {
		super();
	}

	public ParentEntity(Integer version, String code, String name, String lastName, String address, String phoneNumber, String jmbg, String email,
			Date birthDate, List<UserEntity> users, List<StudentEntity> students) {
		super(version, code, name, lastName, address, phoneNumber, jmbg, email, birthDate, users);
		this.students = students;
	}
	
	
	public ParentEntity(ParentDTO parent) {
		super(1, UUID.randomUUID().toString(), parent.getName(), parent.getLastName(), parent.getAddress(), parent.getPhoneNumber(), parent.getJmbg(),
				parent.getEmail(), parent.getBirthDate(), new ArrayList<>());
		this.students = new ArrayList<>();
	}

	public void updateParent(ParentEntity p) {
		setAddress(p.getAddress());
		setBirthDate(p.getBirthDate());
		setName(p.getName());
		setLastName(p.getLastName()); 
		setEmail(p.getEmail());
		setJmbg(p.getJmbg());
		setPhoneNumber(p.getPhoneNumber());
	}

	@OneToMany (mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}
	
	

}
