package com.iktpreobuka.e_diary.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PersonEntity {

	protected Long id;

	protected Integer version;
	protected String code;
	protected String name;
	protected String lastName;
	protected String address;
	protected String phoneNumber;
	protected String jmbg;
	protected String email;
	protected Date birthDate;

	protected List<UserEntity> users;

	public PersonEntity() {
		super();
	}

	public PersonEntity(Integer version, String code, String name, String lastName, String address, String phoneNumber,
			String jmbg, String email, Date birthDate, List<UserEntity> users) {
		super();
		this.version = version;
		this.code = code;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.jmbg = jmbg;
		this.email = email;
		this.birthDate = birthDate;
		this.users = users;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
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

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return lastName;
	}

	@Column(name = "address", nullable = false)
	public String getAddress() {
		return address;
	}

	@Column(name = "phone_number", nullable = false)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Column(name = "jmbg", nullable = false, length = 13, unique = true)
	public String getJmbg() {
		return jmbg;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	@Column(name = "birth_date")
	public Date getBirthDate() {
		return birthDate;
	}

	@OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
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

}
