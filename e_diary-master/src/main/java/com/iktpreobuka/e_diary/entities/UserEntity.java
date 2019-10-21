package com.iktpreobuka.e_diary.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iktpreobuka.e_diary.entities.dto.UserDTO;
import com.iktpreobuka.e_diary.enumerations.ERole;
import com.iktpreobuka.e_diary.util.Encryption;

@Entity
@Table(name = "user")
public class UserEntity {

	private Long id;
	private String username;
	private String password;
	private PersonEntity person;
	private ERole role;

	public UserEntity() {
		super();
	}

	public UserEntity(Long id ,String username, String password, PersonEntity person, ERole role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.person = person;
		this.role = role;
	}
	
	public UserEntity(UserDTO u, PersonEntity p) {
		super();
		this.id = u.getId();
		this.username = u.getUsername();
		this.password = Encryption.getPassEncoded(u.getPassword());
		this.person = p;
		this.role = u.getRole();
	}
	
	public void updateUser (UserEntity u) {
		setUsername(u.getUsername());
		setPassword(u.getPassword());
		setPerson(u.getPerson());
		setRole(u.getRole());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	public Long getId() {
		return id;
	}

	@Column(name = "username", unique = true)
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	@Column(name = "role")
	public ERole getRole() {
		return role;
	}

	@ManyToOne
	@JoinColumn(name = "person")
	public PersonEntity getPerson() {
		return person;
	}

	public void setRole(ERole role) {
		this.role = role;
	}

	public void setPerson(PersonEntity person) {
		this.person = person;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	

}
